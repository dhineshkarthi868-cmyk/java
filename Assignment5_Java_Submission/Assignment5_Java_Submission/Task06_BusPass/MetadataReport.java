import java.sql.*;
public class MetadataReport {
    public static void print(Connection c)throws SQLException{
        DatabaseMetaData m=c.getMetaData();
        System.out.println("Product: "+m.getDatabaseProductName()+" "+m.getDatabaseProductVersion());
        System.out.println("Driver: "+m.getDriverName()+" "+m.getDriverVersion());
        try(ResultSet r=m.getTables(c.getCatalog(),null,"%",new String[]{"TABLE"})){
            while(r.next())System.out.println("Table: "+r.getString("TABLE_NAME"));
        }
    }
}
