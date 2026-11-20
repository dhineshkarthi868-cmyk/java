import java.io.*;
import java.nio.file.*;
import java.util.*;

/** Delimited text pass store. Damaged records are skipped during searches. */
public class PassStore {
    private final Path file;
    public PassStore(Path file) { this.file = file; }

    private static String encode(String s) {
        return s.replace("\\", "\\\\").replace("|", "\\|").replace("\n", "\\n");
    }
    private static String[] decode(String line) {
        List<String> out = new ArrayList<>();
        StringBuilder b = new StringBuilder();
        boolean esc = false;
        for (int i=0;i<line.length();i++) {
            char c=line.charAt(i);
            if (esc) {
                if (c=='n') b.append('\n'); else b.append(c);
                esc=false;
            } else if (c=='\\') esc=true;
            else if (c=='|') { out.add(b.toString()); b.setLength(0); }
            else b.append(c);
        }
        if (esc) return new String[0];
        out.add(b.toString());
        return out.toArray(new String[0]);
    }
    public void add(String passId, String regNo, String name, int route, double amount) throws IOException {
        if (passId==null || regNo==null || name==null || !Double.isFinite(amount) || amount<0)
            throw new IllegalArgumentException("Invalid pass fields");
        Files.createDirectories(file.toAbsolutePath().getParent());
        try (BufferedWriter w=Files.newBufferedWriter(file, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            w.write(String.join("|", encode(passId), encode(regNo), encode(name),
                    Integer.toString(route), Double.toString(amount)));
            w.newLine();
        }
    }
    /** Returns five decoded fields, or null if not found. */
    public String[] find(String passId) throws IOException {
        if (!Files.exists(file)) return null;
        try (BufferedReader r=Files.newBufferedReader(file)) {
            String line;
            while ((line=r.readLine())!=null) {
                String[] f=decode(line);
                if (f.length!=5) continue;
                try {
                    Integer.parseInt(f[3]); double a=Double.parseDouble(f[4]);
                    if (!Double.isFinite(a) || a<0) continue;
                } catch (NumberFormatException ex) { continue; }
                if (f[0].equals(passId)) return f;
            }
        }
        return null;
    }
    public List<String[]> listByRoute(int route) throws IOException {
        List<String[]> result=new ArrayList<>();
        if (!Files.exists(file)) return result;
        try (BufferedReader r=Files.newBufferedReader(file)) {
            String line;
            while ((line=r.readLine())!=null) {
                String[] f=decode(line);
                if (f.length!=5) continue;
                try {
                    if (Integer.parseInt(f[3])==route && Double.isFinite(Double.parseDouble(f[4])))
                        result.add(f);
                } catch (NumberFormatException ignored) { }
            }
        }
        return result;
    }
    public static void main(String[] args) throws Exception {
        Path p=Files.createTempFile("passes",".txt");
        PassStore s=new PassStore(p);
        s.add("P1001","192311001","Arun",12,4500);
        Files.writeString(p,"P1002|DAMAGED LINE\n",StandardOpenOption.APPEND);
        s.add("P1003","192311003","Divya",7,3800);
        System.out.println(Arrays.toString(s.find("P1003")));
        System.out.println(s.find("P1002"));
        Files.deleteIfExists(p);
    }
}
