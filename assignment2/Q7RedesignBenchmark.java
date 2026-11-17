import java.util.*;

public class Q7RedesignBenchmark {
    static volatile int sink;
    static double average(Runnable r){
        long total=0; for(int i=0;i<7;i++){long s=System.nanoTime();r.run();total+=System.nanoTime()-s;}
        return total/7.0/1e6;
    }
    static void test(int n){
        System.out.printf("Q7 size %d | 200 front insertions: ArrayList %.3f ms, ArrayDeque %.3f ms%n",n,
            average(()->{ArrayList<Integer>a=new ArrayList<>(n+200);for(int i=0;i<n;i++)a.add(i);for(int i=0;i<200;i++)a.add(0,-i);sink=a.size();}),
            average(()->{ArrayDeque<Integer>d=new ArrayDeque<>(n+200);for(int i=0;i<n;i++)d.addLast(i);for(int i=0;i<200;i++)d.addFirst(-i);sink=d.size();}));
        System.out.printf("Q7 size %d | remove 200 from front: ArrayList %.3f ms, ArrayDeque %.3f ms%n",n,
            average(()->{ArrayList<Integer>a=new ArrayList<>(n);for(int i=0;i<n;i++)a.add(i);for(int i=0;i<200;i++)a.remove(0);sink=a.size();}),
            average(()->{ArrayDeque<Integer>d=new ArrayDeque<>(n);for(int i=0;i<n;i++)d.addLast(i);for(int i=0;i<200;i++)d.removeFirst();sink=d.size();}));
    }
    public static void main(String[] args){test(1000);test(10000);test(50000);}
}
