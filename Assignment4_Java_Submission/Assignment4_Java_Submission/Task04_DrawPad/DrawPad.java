import javax.swing.*;import java.awt.*;import java.awt.event.*;import java.util.*;
public class DrawPad extends JPanel {
 java.util.List<Point> points=new ArrayList<>();JLabel status=new JLabel("Clicks: 0");
 public DrawPad(){setLayout(new BorderLayout());add(status,BorderLayout.SOUTH);setBackground(Color.WHITE);
 addMouseListener(new MouseAdapter(){@Override public void mouseClicked(MouseEvent e){points.add(e.getPoint());status.setText("Clicks: "+points.size());repaint();}});}
 @Override protected void paintComponent(Graphics g){super.paintComponent(g);g.setColor(Color.BLUE);for(Point p:points)g.fillOval(p.x-3,p.y-3,6,6);}
 public static void main(String[]a){SwingUtilities.invokeLater(()->{JFrame f=new JFrame("Draw Pad");f.setContentPane(new DrawPad());f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);f.setSize(400,300);f.setLocationRelativeTo(null);f.setVisible(true);});}
}