import java.sql.*; import java.util.*;
public class MarksDB implements GenericDAO<Marks,Integer> {
    public void insert(Marks x)throws SQLException{try(Connection c=DB.open();PreparedStatement p=c.prepareStatement("INSERT INTO marks(student_id,course_id,score) VALUES(?,?,?)")){p.setInt(1,x.getStudentId());p.setInt(2,x.getCourseId());p.setInt(3,x.getScore());p.executeUpdate();}}
    public Optional<Marks> findById(Integer id)throws SQLException{try(Connection c=DB.open();PreparedStatement p=c.prepareStatement("SELECT student_id,course_id,score FROM marks WHERE student_id=?")){p.setInt(1,id);try(ResultSet r=p.executeQuery()){return r.next()?Optional.of(new Marks(r.getInt(1),r.getInt(2),r.getInt(3))):Optional.empty();}}}
    public List<Marks> findAll()throws SQLException{List<Marks>a=new ArrayList<>();try(Connection c=DB.open();PreparedStatement p=c.prepareStatement("SELECT student_id,course_id,score FROM marks");ResultSet r=p.executeQuery()){while(r.next())a.add(new Marks(r.getInt(1),r.getInt(2),r.getInt(3)));}return a;}
    public boolean update(Marks x)throws SQLException{try(Connection c=DB.open();PreparedStatement p=c.prepareStatement("UPDATE marks SET score=? WHERE student_id=? AND course_id=?")){p.setInt(1,x.getScore());p.setInt(2,x.getStudentId());p.setInt(3,x.getCourseId());return p.executeUpdate()>0;}}
    public boolean delete(Integer id)throws SQLException{try(Connection c=DB.open();PreparedStatement p=c.prepareStatement("DELETE FROM marks WHERE student_id=?")){p.setInt(1,id);return p.executeUpdate()>0;}}
}
