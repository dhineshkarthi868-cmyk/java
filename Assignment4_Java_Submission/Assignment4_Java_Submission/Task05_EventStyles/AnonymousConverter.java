import javax.swing.*;import java.awt.*;import java.awt.event.*;
public class AnonymousConverter extends JFrame {
 JTextField amount=new JTextField(10);JComboBox<String>target=new JComboBox<>(new String[]{"USD","EUR"});JLabel result=new JLabel(" ");
 public AnonymousConverter(){setTitle("Anonymous Inner");setLayout(new FlowLayout());add(amount);add(target);JButton b=new JButton("Convert");add(b);add(result);
 ActionListener h=new ActionListener(){public void actionPerformed(ActionEvent e){ConverterSupport.convert(AnonymousConverter.this,amount,target,result);}};b.addActionListener(h);amount.addActionListener(h);ConverterSupport.close(this);pack();}
 public static void main(String[]a){SwingUtilities.invokeLater(()->new AnonymousConverter().setVisible(true));}
}