import java.sql.*; import java.util.*;
public class CourseDB implements GenericDAO<Course,Integer> {
    public void insert(Course x)throws SQLException{try(Connection c=DB.open();PreparedStatement p=c.prepareStatement("INSERT INTO course(id,title) VALUES(?,?)")){p.setInt(1,x.getId());p.setString(2,x.getTitle());p.executeUpdate();}}
    public Optional<Course> findById(Integer id)throws SQLException{try(Connection c=DB.open();PreparedStatement p=c.prepareStatement("SELECT id,title FROM course WHERE id=?")){p.setInt(1,id);try(ResultSet r=p.executeQuery()){return r.next()?Optional.of(new Course(r.getInt(1),r.getString(2))):Optional.empty();}}}
    public List<Course> findAll()throws SQLException{List<Course>a=new ArrayList<>();try(Connection c=DB.open();PreparedStatement p=c.prepareStatement("SELECT id,title FROM course");ResultSet r=p.executeQuery()){while(r.next())a.add(new Course(r.getInt(1),r.getString(2)));}return a;}
    public boolean update(Course x)throws SQLException{try(Connection c=DB.open();PreparedStatement p=c.prepareStatement("UPDATE course SET title=? WHERE id=?")){p.setString(1,x.getTitle());p.setInt(2,x.getId());return p.executeUpdate()>0;}}
    public boolean delete(Integer id)throws SQLException{try(Connection c=DB.open();PreparedStatement p=c.prepareStatement("DELETE FROM course WHERE id=?")){p.setInt(1,id);return p.executeUpdate()>0;}}
}
