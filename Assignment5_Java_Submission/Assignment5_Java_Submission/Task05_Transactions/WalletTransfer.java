import java.sql.*;
public class WalletTransfer {
    public static void transfer(Connection c,int fromId,int toId,double amount)throws SQLException{
        if(amount<=0 || !Double.isFinite(amount))throw new IllegalArgumentException("Amount must be positive");
        boolean old=c.getAutoCommit(); c.setAutoCommit(false);
        try{
            try(PreparedStatement debit=c.prepareStatement("UPDATE wallet SET balance=balance-? WHERE id=? AND balance>=?");
                PreparedStatement credit=c.prepareStatement("UPDATE wallet SET balance=balance+? WHERE id=?")){
                debit.setDouble(1,amount);debit.setInt(2,fromId);debit.setDouble(3,amount);
                if(debit.executeUpdate()!=1)throw new SQLException("Insufficient funds or source wallet missing");
                credit.setDouble(1,amount);credit.setInt(2,toId);
                if(credit.executeUpdate()!=1)throw new SQLException("Destination wallet missing");
            }
            c.commit();
        }catch(SQLException|RuntimeException e){c.rollback();throw e;}
        finally{c.setAutoCommit(old);}
    }
    public static boolean unsafeLogin(Connection c,String id,String pwd)throws SQLException{
        String sql="SELECT * FROM users WHERE id='"+id+"' AND pwd='"+pwd+"'";
        try(Statement s=c.createStatement();ResultSet r=s.executeQuery(sql)){return r.next();}
    }
    public static boolean safeLogin(Connection c,String id,String pwd)throws SQLException{
        try(PreparedStatement p=c.prepareStatement("SELECT 1 FROM users WHERE id=? AND pwd=?")){
            p.setString(1,id);p.setString(2,pwd);try(ResultSet r=p.executeQuery()){return r.next();}
        }
    }
}
