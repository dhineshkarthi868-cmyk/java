import java.sql.*;
public class FeeService {
 private final String url=System.getenv().getOrDefault("DB_URL","jdbc:mysql://localhost:3306/java_assignment");
 private final String user=System.getenv().getOrDefault("DB_USER","root"), password=System.getenv().getOrDefault("DB_PASSWORD","");
 public String pay(String reg,String text) throws Exception {
  if(reg==null||!reg.trim().matches("[A-Za-z0-9-]{4,20}")) throw new IllegalArgumentException("Invalid registration number");
  double amount; try{amount=Double.parseDouble(text.trim());}catch(Exception e){throw new IllegalArgumentException("Amount must be numeric");}
  if(!Double.isFinite(amount)||amount<=0)throw new IllegalArgumentException("Amount must be positive");
  try(Connection c=DriverManager.getConnection(url,user,password);PreparedStatement p=c.prepareStatement("INSERT INTO fee_payments(reg_no,amount,paid_at) VALUES(?,?,CURRENT_TIMESTAMP)")){
   p.setString(1,reg.trim());p.setDouble(2,amount);p.executeUpdate();
  } return String.format("Payment recorded: %s | INR %.2f",reg,amount);
 }
}