import javax.swing.*;import java.awt.*;import java.awt.event.*;
public class NamedInnerConverter extends JFrame {
 JTextField amount=new JTextField(10);JComboBox<String>target=new JComboBox<>(new String[]{"USD","EUR"});JLabel result=new JLabel(" ");
 class Handler implements ActionListener{public void actionPerformed(ActionEvent e){ConverterSupport.convert(NamedInnerConverter.this,amount,target,result);}}
 public NamedInnerConverter(){setTitle("Named Inner");setLayout(new FlowLayout());add(amount);add(target);JButton b=new JButton("Convert");add(b);add(result);Handler h=new Handler();b.addActionListener(h);amount.addActionListener(h);ConverterSupport.close(this);pack();}
 public static void main(String[]a){SwingUtilities.invokeLater(()->new NamedInnerConverter().setVisible(true));}
}