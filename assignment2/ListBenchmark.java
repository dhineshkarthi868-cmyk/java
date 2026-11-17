import java.util.*;

public class ListBenchmark {
    private static final int RUNS = 5;
    private static volatile long sink;
    private interface Task { void run(); }
    private static double average(Task task) {
        long total = 0;
        for (int i = 0; i < RUNS; i++) {
            long start = System.nanoTime(); task.run(); total += System.nanoTime() - start;
        }
        return total / (double) RUNS / 1_000_000.0;
    }
    private static ArrayList<Integer> arrayData(int n) {
        ArrayList<Integer> a = new ArrayList<>(n);
        for (int i = 0; i < n; i++) a.add(i);
        return a;
    }
    private static LinkedList<Integer> linkedData(int n) {
        LinkedList<Integer> a = new LinkedList<>();
        for (int i = 0; i < n; i++) a.add(i);
        return a;
    }
    private static void test(int n) {
        System.out.println("\\nQ2 LIST SIZE = " + n);
        System.out.printf("Append ArrayList %.3f ms%n", average(() -> {
            ArrayList<Integer> a = new ArrayList<>(); for (int i=0;i<n;i++) a.add(i); sink=a.size();
        }));
        System.out.printf("Append LinkedList %.3f ms%n", average(() -> {
            LinkedList<Integer> a = new LinkedList<>(); for (int i=0;i<n;i++) a.add(i); sink=a.size();
        }));
        System.out.printf("100 front inserts ArrayList %.3f ms%n", average(() -> {
            ArrayList<Integer> a=arrayData(n); for(int i=0;i<100;i++) a.add(0,-i); sink=a.size();
        }));
        System.out.printf("100 front inserts LinkedList %.3f ms%n", average(() -> {
            LinkedList<Integer> a=linkedData(n); for(int i=0;i<100;i++) a.add(0,-i); sink=a.size();
        }));
        System.out.printf("100 middle inserts ArrayList %.3f ms%n", average(() -> {
            ArrayList<Integer> a=arrayData(n); for(int i=0;i<100;i++) a.add(a.size()/2,-i); sink=a.size();
        }));
        System.out.printf("100 middle inserts LinkedList %.3f ms%n", average(() -> {
            LinkedList<Integer> a=linkedData(n); for(int i=0;i<100;i++) a.add(a.size()/2,-i); sink=a.size();
        }));
        final ArrayList<Integer> a=arrayData(n);
        final LinkedList<Integer> l=linkedData(n);
        final int probes=Math.min(500,n);
        System.out.printf("Indexed reads ArrayList %.3f ms%n", average(() -> {
            long s=0; for(int i=0;i<probes;i++) s+=a.get((i*97)%n); sink=s;
        }));
        System.out.printf("Indexed reads LinkedList %.3f ms%n", average(() -> {
            long s=0; for(int i=0;i<probes;i++) s+=l.get((i*97)%n); sink=s;
        }));
        System.out.printf("Iteration ArrayList %.3f ms%n", average(() -> { long s=0; for(int v:a)s+=v; sink=s; }));
        System.out.printf("Iteration LinkedList %.3f ms%n", average(() -> { long s=0; for(int v:l)s+=v; sink=s; }));
        System.out.printf("ArrayDeque addLast/removeFirst %.3f ms%n", average(() -> {
            ArrayDeque<Integer> d=new ArrayDeque<>(); for(int i=0;i<n;i++)d.addLast(i);
            for(int i=0;i<n;i++)sink=d.removeFirst();
        }));
    }
    public static void main(String[] args) { test(1000); test(10000); test(50000); }
}
