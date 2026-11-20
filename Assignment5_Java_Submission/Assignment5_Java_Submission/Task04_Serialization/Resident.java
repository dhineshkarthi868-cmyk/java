import java.io.*;
public class Resident implements Serializable {
    private static final long serialVersionUID=2L;
    private String name,regNo,phone;
    private transient String password;
    private transient Object databaseConnection; // live connection must never be serialized
    public Resident(String name,String regNo,String password,Object connection){this.name=name;this.regNo=regNo;this.password=password;this.databaseConnection=connection;}
    public String getName(){return name;} public String getRegNo(){return regNo;} public String getPhone(){return phone;}
    public void setPhone(String phone){this.phone=phone;}
    public String toString(){return name+" ("+regNo+"), phone="+phone;}
}
