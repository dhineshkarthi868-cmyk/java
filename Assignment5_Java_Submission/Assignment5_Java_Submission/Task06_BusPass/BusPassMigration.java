import java.io.*; import java.nio.file.*; import java.sql.*; import java.util.*;
/** Validates legacy pipe-delimited records, records rejected line numbers and loads valid rows. */
public class BusPassMigration {
    public static void migrate(Path source,Connection c,Path failureFile)throws IOException,SQLException{
        List<String[]> valid=new ArrayList<>(); List<String> rejected=new ArrayList<>();
        try(BufferedReader r=Files.newBufferedReader(source)){
            String line;int lineNo=0;
            while((line=r.readLine())!=null){lineNo++;
                String[] f=line.split("\\|",-1);
                try{
                    if(f.length!=5 || f[0].isBlank() || f[1].isBlank() || f[2].isBlank())throw new IllegalArgumentException();
                    int route=Integer.parseInt(f[3]);double amount=Double.parseDouble(f[4]);
                    if(route<0 || !Double.isFinite(amount) || amount<0)throw new IllegalArgumentException();
                    valid.add(f);
                }catch(RuntimeException ex){rejected.add("Line "+lineNo+": "+line);}
            }
        }
        try{PassLoader.loadAll(c,valid);}catch(SQLException ex){rejected.add("Database batch failed: "+ex.getMessage());}
        Files.write(failureFile,rejected);
        System.out.println("Valid records: "+valid.size()+", rejected entries: "+rejected.size());
    }
}
