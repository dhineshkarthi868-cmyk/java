import java.sql.*; import java.util.*;
public class StudentDB implements GenericDAO<Student,Integer> {
    public void insert(Student s)throws SQLException{
        try(Connection c=DB.open();PreparedStatement p=c.prepareStatement("INSERT INTO student(id,name,reg_no) VALUES(?,?,?)")){
            p.setInt(1,s.getId());p.setString(2,s.getName());p.setString(3,s.getRegNo());p.executeUpdate();
        }
    }
    public Optional<Student> findById(Integer id)throws SQLException{
        try(Connection c=DB.open();PreparedStatement p=c.prepareStatement("SELECT id,name,reg_no FROM student WHERE id=?")){
            p.setInt(1,id);try(ResultSet r=p.executeQuery()){return r.next()?Optional.of(new Student(r.getInt(1),r.getString(2),r.getString(3))):Optional.empty();}
        }
    }
    public List<Student> findAll()throws SQLException{
        List<Student> a=new ArrayList<>();
        try(Connection c=DB.open();PreparedStatement p=c.prepareStatement("SELECT id,name,reg_no FROM student");ResultSet r=p.executeQuery()){
            while(r.next())a.add(new Student(r.getInt(1),r.getString(2),r.getString(3)));
        } return a;
    }
    public boolean update(Student s)throws SQLException{
        try(Connection c=DB.open();PreparedStatement p=c.prepareStatement("UPDATE student SET name=?,reg_no=? WHERE id=?")){
            p.setString(1,s.getName());p.setString(2,s.getRegNo());p.setInt(3,s.getId());return p.executeUpdate()>0;
        }
    }
    public boolean delete(Integer id)throws SQLException{
        try(Connection c=DB.open();PreparedStatement p=c.prepareStatement("DELETE FROM student WHERE id=?")){
            p.setInt(1,id);return p.executeUpdate()>0;
        }
    }
}
