import javax.swing.*;import java.awt.*;import java.awt.event.*;
public class FrameListenerConverter extends JFrame implements ActionListener {
 JTextField amount=new JTextField(10);JComboBox<String>target=new JComboBox<>(new String[]{"USD","EUR"});JLabel result=new JLabel(" ");
 public FrameListenerConverter(){setTitle("Frame Listener");setLayout(new FlowLayout());add(amount);add(target);JButton b=new JButton("Convert");add(b);add(result);b.addActionListener(this);amount.addActionListener(this);ConverterSupport.close(this);pack();}
 public void actionPerformed(ActionEvent e){ConverterSupport.convert(this,amount,target,result);}
 public static void main(String[]a){SwingUtilities.invokeLater(()->new FrameListenerConverter().setVisible(true));}
}