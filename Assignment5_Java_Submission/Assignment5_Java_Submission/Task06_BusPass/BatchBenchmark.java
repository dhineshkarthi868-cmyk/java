import java.sql.*; import java.util.*;
/** Creates reproducible synthetic rows and measures batch insertion. Run against a test database only. */
public class BatchBenchmark {
    public static List<String[]> generate(int n){
        List<String[]> rows=new ArrayList<>(n);
        for(int i=1;i<=n;i++)rows.add(new String[]{"BP"+i,"REG"+i,"Student"+i,""+(i%12+1),"450.00"});
        return rows;
    }
    public static void main(String[] args)throws Exception{
        int n=args.length==0?1000:Integer.parseInt(args[0]);
        try(Connection c=DriverManager.getConnection(
            System.getenv().getOrDefault("DB_URL","jdbc:mysql://localhost:3306/java_assignment5"),
            System.getenv().getOrDefault("DB_USER","root"),System.getenv().getOrDefault("DB_PASSWORD",""))){
            long start=System.nanoTime();int inserted=PassLoader.loadAll(c,generate(n));
            System.out.printf("Inserted %d rows in %.3f ms%n",inserted,(System.nanoTime()-start)/1e6);
        }
    }
}
