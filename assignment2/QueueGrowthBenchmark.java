import java.util.*;

public class QueueGrowthBenchmark {
    static final class Patient {
        final int severity; final long arrival;
        Patient(int severity,long arrival){this.severity=severity;this.arrival=arrival;}
    }
    static volatile long sink;
    static double run(int n,int repeats){
        long total=0;
        for(int r=0;r<repeats;r++){
            long start=System.nanoTime();
            PriorityQueue<Patient> q=new PriorityQueue<>(Comparator.comparingInt((Patient p)->p.severity).thenComparingLong(p->p.arrival));
            for(int i=0;i<n;i++)q.offer(new Patient((i%5)+1,i));
            while(!q.isEmpty())sink=q.poll().arrival;
            total+=System.nanoTime()-start;
        }
        return total/(double)repeats/1e6;
    }
    public static void main(String[] args){
        for(int n:new int[]{1000,10000,50000})
            System.out.printf("Q6 enqueue + serve %d patients (5-run avg): %.3f ms%n",n,run(n,5));
        System.out.println("Complexity: offer O(log n), poll O(log n), peek O(1), storage O(n).");
    }
}
