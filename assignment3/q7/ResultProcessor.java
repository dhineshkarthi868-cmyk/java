import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

public class ResultProcessor {
    static class Result {
        final String file;
        final boolean success;
        final int score;
        final String message;
        Result(String file, boolean success, int score, String message) {
            this.file=file; this.success=success; this.score=score; this.message=message;
        }
    }

    static Result process(Path file) {
        try {
            if (!Files.exists(file)) return new Result(file.getFileName().toString(), false, 0, "MISSING");
            String raw = new String(Files.readAllBytes(file)).trim();
            if (raw.isEmpty()) return new Result(file.getFileName().toString(), false, 0, "EMPTY");
            int score = Integer.parseInt(raw);
            if (score < 0 || score > 100) return new Result(file.getFileName().toString(), false, 0, "INVALID_SCORE");
            return new Result(file.getFileName().toString(), true, score, "OK");
        } catch (IOException | NumberFormatException e) {
            return new Result(file.getFileName().toString(), false, 0, "CORRUPT: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        Path dir = Paths.get("q7-input");
        Files.createDirectories(dir);
        for (int i=1;i<=10000;i++) {
            Path file = dir.resolve("answer"+i+".txt");
            if (!Files.exists(file))
                Files.write(file, Collections.singletonList(Integer.toString((i*7)%101)));
        }
        Files.write(dir.resolve("answer_bad.txt"), Collections.singletonList("not-a-score"));
        // answer_missing.txt intentionally does not exist.
        List<Path> inputs = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path p : stream) inputs.add(p);
        }
        inputs.add(dir.resolve("answer_missing.txt"));

        int poolSize = args.length > 0 ? Integer.parseInt(args[0]) : 4;
        ExecutorService pool = Executors.newFixedThreadPool(poolSize);
        List<Future<Result>> futures = new ArrayList<>();
        long start = System.nanoTime();
        try {
            for (Path p : inputs) futures.add(pool.submit(() -> process(p)));
            int success=0, failure=0, totalScore=0;
            for (Future<Result> future : futures) {
                try {
                    Result r = future.get();
                    if (r.success) { success++; totalScore += r.score; }
                    else { failure++; System.out.println("FAILED " + r.file + " : " + r.message); }
                } catch (ExecutionException e) {
                    failure++;
                    System.out.println("Unexpected task failure: " + e.getCause());
                }
            }
            long elapsedMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime()-start);
            System.out.println("Files processed: " + inputs.size());
            System.out.println("Successes: " + success);
            System.out.println("Failures: " + failure);
            System.out.println("Total valid score: " + totalScore);
            System.out.println("Pool size: " + poolSize);
            System.out.println("Elapsed time (ms): " + elapsedMs);
        } finally {
            pool.shutdown();
            if (!pool.awaitTermination(30, TimeUnit.SECONDS)) pool.shutdownNow();
        }
    }
}
