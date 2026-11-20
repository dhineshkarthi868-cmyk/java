public class SerializationDemo {
    public static void main(String[] args)throws Exception{
        Hostel h=new Hostel("SIMATS Hostel"); Room r=new Room(101);
        r.add(new Resident("Arun","192311001","not-saved","live-db-connection")); h.addRoom(r);
        Hostel.PathLike p=new Hostel.PathLike("hostel.dat");h.save(p);
        System.out.println(Hostel.load(p)); new java.io.File(p.value).delete();
    }
}
