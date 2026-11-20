import javax.swing.*;import java.awt.*;import java.awt.event.*;
public class LambdaConverter extends JFrame {
 JTextField amount=new JTextField(10);JComboBox<String>target=new JComboBox<>(new String[]{"USD","EUR"});JLabel result=new JLabel(" ");
 public LambdaConverter(){setTitle("Lambda Converter");setLayout(new FlowLayout());add(amount);add(target);JButton b=new JButton("Convert");add(b);add(result);
 ActionListener h=e->ConverterSupport.convert(this,amount,target,result);b.addActionListener(h);amount.addActionListener(h);ConverterSupport.close(this);pack();}
 public static void main(String[]a){SwingUtilities.invokeLater(()->new LambdaConverter().setVisible(true));}
}