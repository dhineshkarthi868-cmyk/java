import javax.swing.*;import java.awt.*;import java.awt.event.*;import java.util.concurrent.*;
public class FeeWindow extends JFrame {
 JTextField reg=new JTextField(14),amount=new JTextField(14); JLabel status=new JLabel("Enter fee details");String receipt="";
 FeeService service=new FeeService();
 public FeeWindow(){super("Student Fee Payment");setLayout(new BorderLayout(8,8));JPanel form=new JPanel(new GridLayout(2,2,8,8));
 form.setBorder(BorderFactory.createEmptyBorder(15,15,5,15));form.add(new JLabel("Registration number"));form.add(reg);form.add(new JLabel("Amount (INR)"));form.add(amount);
 JButton pay=new JButton("Pay"),clear=new JButton("Clear"),show=new JButton("Receipt");JPanel actions=new JPanel();actions.add(pay);actions.add(clear);actions.add(show);
 add(status,BorderLayout.NORTH);add(form,BorderLayout.CENTER);add(actions,BorderLayout.SOUTH);
 pay.addActionListener(e->{pay.setEnabled(false);status.setText("Processing...");String r=reg.getText(),a=amount.getText();
 new SwingWorker<String,Void>(){protected String doInBackground()throws Exception{return service.pay(r,a);}
 protected void done(){pay.setEnabled(true);try{receipt=get();status.setText("Payment successful");JOptionPane.showMessageDialog(FeeWindow.this,receipt);}
 catch(Exception x){status.setText("Payment failed");JOptionPane.showMessageDialog(FeeWindow.this,x.getCause()==null?x.getMessage():x.getCause().getMessage(),"Error",0);}}}.execute();});
 clear.addActionListener(e->{reg.setText("");amount.setText("");status.setText("Cleared");});
 show.addActionListener(e->JOptionPane.showMessageDialog(this,receipt.isEmpty()?"No receipt yet":receipt));
 setDefaultCloseOperation(EXIT_ON_CLOSE);pack();setLocationRelativeTo(null);}
 public static void main(String[]x){SwingUtilities.invokeLater(()->new FeeWindow().setVisible(true));}
}