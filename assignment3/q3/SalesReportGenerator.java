import java.io.*;
import java.nio.file.*;
import java.util.*;

public class SalesReportGenerator {
    static void generate(List<Path> inputFiles, Path output) throws IOException {
        long total = 0;
        try (BufferedWriter writer = Files.newBufferedWriter(output)) {
            writer.write("DAILY SALES SUMMARY");
            writer.newLine();
            for (Path input : inputFiles) {
                try (BufferedReader reader = Files.newBufferedReader(input)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        try {
                            total += Long.parseLong(line.trim());
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid sales value in " + input + ": " + line);
                        }
                    }
                    writer.write("Processed: " + input.getFileName());
                    writer.newLine();
                } catch (IOException e) {
                    System.err.println("Could not process " + input + ": " + e.getMessage());
                    writer.write("FAILED: " + input.getFileName());
                    writer.newLine();
                }
            }
            writer.write("TOTAL=" + total);
            writer.newLine();
        }
        System.out.println("Report created: " + output.toAbsolutePath());
        System.out.println("Total sales: " + total);
    }

    public static void main(String[] args) throws IOException {
        Path dir = Paths.get("q3-data");
        Files.createDirectories(dir);
        Files.write(dir.resolve("store1.txt"), Arrays.asList("120", "80", "50"));
        Files.write(dir.resolve("store2.txt"), Arrays.asList("200", "150"));
        // store-missing.txt intentionally does not exist to demonstrate error handling.
        generate(Arrays.asList(dir.resolve("store1.txt"), dir.resolve("store2.txt"),
                dir.resolve("store-missing.txt")), dir.resolve("summary.txt"));
    }
}
