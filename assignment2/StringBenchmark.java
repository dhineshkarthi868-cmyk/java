import java.util.Locale;

public class StringBenchmark {
    private static volatile String sink;
    private static final int WARMUP = 3, RUNS = 7;

    private static String plus(int tickets, int fields) {
        String last = "";
        for (int t = 0; t < tickets; t++) {
            String ticket = "";
            for (int f = 0; f < fields; f++) ticket = ticket + "FIELD" + f + "=" + f + ";";
            last = ticket;
        }
        return last;
    }
    private static String builder(int tickets, int fields) {
        String last = "";
        for (int t = 0; t < tickets; t++) {
            StringBuilder ticket = new StringBuilder();
            for (int f = 0; f < fields; f++) ticket.append("FIELD").append(f).append("=").append(f).append(";");
            last = ticket.toString();
        }
        return last;
    }
    private static String buffer(int tickets, int fields) {
        String last = "";
        for (int t = 0; t < tickets; t++) {
            StringBuffer ticket = new StringBuffer();
            for (int f = 0; f < fields; f++) ticket.append("FIELD").append(f).append("=").append(f).append(";");
            last = ticket.toString();
        }
        return last;
    }
    private static double measure(java.util.function.Supplier<String> task) {
        long start = System.nanoTime();
        sink = task.get();
        return (System.nanoTime() - start) / 1_000_000.0;
    }
    private static double[] benchmark(int tickets, int fields) {
        for (int i = 0; i < WARMUP; i++) {
            sink = plus(Math.min(tickets, 100), fields);
            sink = builder(Math.min(tickets, 100), fields);
            sink = buffer(Math.min(tickets, 100), fields);
        }
        double[] totals = new double[3];
        for (int i = 0; i < RUNS; i++) {
            totals[0] += measure(() -> plus(tickets, fields));
            totals[1] += measure(() -> builder(tickets, fields));
            totals[2] += measure(() -> buffer(tickets, fields));
        }
        for (int i = 0; i < 3; i++) totals[i] /= RUNS;
        return totals;
    }
    public static void main(String[] args) {
        Locale.setDefault(Locale.ROOT);
        int[][] workloads = {{10, 6}, {10000, 50}, {20000, 100}};
        for (int i = 0; i < workloads.length; i++) {
            double[] x = benchmark(workloads[i][0], workloads[i][1]);
            System.out.printf("Q1 Workload %d (%d tickets x %d fields): + %.3f ms | Builder %.3f ms | Buffer %.3f ms%n",
                i + 1, workloads[i][0], workloads[i][1], x[0], x[1], x[2]);
        }
        System.out.println("Output checksum length = " + (sink == null ? 0 : sink.length()));
    }
}
