import java.io.*; import java.util.*;
public class Hostel implements Serializable {
    private static final long serialVersionUID=2L;
    private final String hostelName; private final List<Room> rooms=new ArrayList<>();
    public Hostel(String name){hostelName=name;} public void addRoom(Room r){rooms.add(r);}
    public void save(PathLike path)throws IOException{try(ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(path.value))){out.writeObject(this);}}
    public static Hostel load(PathLike path)throws IOException,ClassNotFoundException{try(ObjectInputStream in=new ObjectInputStream(new FileInputStream(path.value))){return (Hostel)in.readObject();}}
    public String toString(){return hostelName+" rooms="+rooms.size();}
    public static class PathLike { final String value; public PathLike(String v){value=v;} }
}
