import java.util.*;

public class PharmacyInventory {
    static final class Medicine {
        final String code, name; final int stock;
        Medicine(String code,String name,int stock){this.code=code;this.name=name;this.stock=stock;}
    }
    static String[] keys(int n) {
        String[] a=new String[n];
        for(int i=0;i<n;i++) a[i]=String.format(Locale.ROOT,"MED%06d",i);
        return a;
    }
    static Map<String,Medicine> make(int n, int kind, String[] keys) {
        Map<String,Medicine> m = kind==0 ? new HashMap<>() : kind==1 ? new TreeMap<>() : new LinkedHashMap<>();
        for(int i=0;i<n;i++) m.put(keys[i],new Medicine(keys[i],"Medicine-"+i,100+i));
        return m;
    }
    static long lookupTime(Map<String,Medicine> m,String[] keys) {
        long start=System.nanoTime(), sum=0;
        for(int i=0;i<100000;i++) sum+=m.get(keys[i%keys.length]).stock;
        if(sum==Long.MIN_VALUE) System.out.println(sum);
        return System.nanoTime()-start;
    }
    static long reportTime(Map<String,Medicine> m, boolean sorted) {
        long start=System.nanoTime(), sum=0;
        if(sorted) {
            TreeMap<String,Medicine> ordered = new TreeMap<>(m);
            for(Medicine x:ordered.values()) sum+=x.stock;
        } else {
            for(Medicine x:m.values()) sum+=x.stock;
        }
        if(sum==Long.MIN_VALUE) System.out.println(sum);
        return System.nanoTime()-start;
    }
    static void test(int n) {
        String[] k=keys(n);
        Map<String,Medicine> h=make(n,0,k), t=make(n,1,k), l=make(n,2,k);
        System.out.printf("Q4 n=%d | lookup 100k (ms): HashMap %.3f, TreeMap %.3f, LinkedHashMap %.3f%n",
            n,lookupTime(h,k)/1e6,lookupTime(t,k)/1e6,lookupTime(l,k)/1e6);
        System.out.printf("Q4 report (ms): HashMap+sort %.3f, TreeMap ordered %.3f, LinkedHashMap insertion %.3f%n",
            reportTime(h,true)/1e6,reportTime(t,false)/1e6,reportTime(l,false)/1e6);
    }
    public static void main(String[] args) {
        test(1000); test(10000); test(50000);
        System.out.println("HashMap: average O(1) lookup; TreeMap: O(log n) lookup and sorted keys; LinkedHashMap: average O(1) lookup and insertion order.");
    }
}
