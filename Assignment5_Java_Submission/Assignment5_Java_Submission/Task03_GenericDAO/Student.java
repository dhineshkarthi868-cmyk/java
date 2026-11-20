public class Student {
    private final int id; private final String name; private final String regNo;
    public Student(int id,String name,String regNo){this.id=id;this.name=name;this.regNo=regNo;}
    public int getId(){return id;} public String getName(){return name;} public String getRegNo(){return regNo;}
    public String toString(){return id+" | "+name+" | "+regNo;}
}
