import java.io.*;
import java.nio.file.*;

/** Counts lines using buffered character input; includes a comparison runner. */
public class BufferedCounter {
    public static long countLinesUnbuffered(Path file) throws IOException {
        long lines=0; boolean hasData=false; int ch;
        try (InputStream in=Files.newInputStream(file)) {
            while ((ch=in.read())!=-1) { hasData=true; if (ch=='\n') lines++; }
        }
        if (hasData) {
            try (RandomAccessFile raf=new RandomAccessFile(file.toFile(),"r")) {
                if (raf.length()>0) { raf.seek(raf.length()-1); if (raf.read()!='\n') lines++; }
            }
        }
        return lines;
    }
    public static long countLinesBuffered(Path file) throws IOException {
        long lines=0; boolean hasData=false; String line;
        try (BufferedReader r=Files.newBufferedReader(file)) {
            while ((line=r.readLine())!=null) { lines++; hasData=true; }
        }
        return lines;
    }
    public static void main(String[] args) throws Exception {
        Path p=Files.createTempFile("attendance",".txt");
        try (BufferedWriter w=Files.newBufferedWriter(p)) {
            for(int i=0;i<100_000;i++) { w.write("attendance-record-"+i); w.newLine(); }
        }
        long a=System.nanoTime(); long n1=countLinesUnbuffered(p); long t1=System.nanoTime()-a;
        a=System.nanoTime(); long n2=countLinesBuffered(p); long t2=System.nanoTime()-a;
        System.out.printf("Unbuffered: %d lines, %.3f ms%n",n1,t1/1e6);
        System.out.printf("Buffered:   %d lines, %.3f ms%n",n2,t2/1e6);
        Files.deleteIfExists(p);
    }
}
