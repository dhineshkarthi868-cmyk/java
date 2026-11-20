import java.sql.*; import java.util.*;
public class PassLoader {
    /** Inserts batches of 1,000 in one transaction; rolls back all rows on failure. */
    public static int loadAll(Connection con,List<String[]> passes)throws SQLException{
        String sql="INSERT INTO bus_pass(pass_id,reg_no,name,route_no,amount) VALUES(?,?,?,?,?)";
        boolean old=con.getAutoCommit();con.setAutoCommit(false);int count=0;
        try(PreparedStatement ps=con.prepareStatement(sql)){
            for(String[] p:passes){
                if(p.length!=5)throw new SQLException("Invalid record field count");
                ps.setString(1,p[0]);ps.setString(2,p[1]);ps.setString(3,p[2]);
                ps.setInt(4,Integer.parseInt(p[3]));ps.setDouble(5,Double.parseDouble(p[4]));
                ps.addBatch();count++;
                if(count%1000==0)ps.executeBatch();
            }
            ps.executeBatch();con.commit();return count;
        }catch(SQLException|RuntimeException e){con.rollback();throw e;}
        finally{con.setAutoCommit(old);}
    }
}
