import javax.swing.*;import java.awt.event.*;import java.text.DecimalFormat;
final class ConverterSupport {
 static double rate(String c){return c.equals("USD")?0.012:0.011;}
 static void convert(JFrame f,JTextField a,JComboBox<String>t,JLabel r){try{double n=Double.parseDouble(a.getText().trim());if(!Double.isFinite(n)||n<0)throw new NumberFormatException();
 String s=new DecimalFormat("0.00").format(n*rate((String)t.getSelectedItem()))+" "+t.getSelectedItem();r.setText(s);JOptionPane.showMessageDialog(f,s);}
 catch(NumberFormatException e){r.setText("Invalid amount");JOptionPane.showMessageDialog(f,"Invalid amount","Error",0);}}
 static void close(JFrame f){f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);f.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){f.dispose();}});}
}