import javax.swing.*;import javax.swing.table.*;import java.awt.*;import java.util.*;
public class LibrarySearchScreen extends JFrame {
 JTextField query=new JTextField(20);JComboBox<String> cat=new JComboBox<>(new String[]{"All","Java","Database","Networking"});
 DefaultTableModel model=new DefaultTableModel(new String[]{"ID","Title","Category","Available"},0){public boolean isCellEditable(int r,int c){return false;}};
 JTable table=new JTable(model);JLabel status=new JLabel("Ready");int page=0;
 java.util.List<String[]> books=Arrays.asList(new String[]{"B01","Java Fundamentals","Java","Yes"},new String[]{"B02","JDBC in Practice","Database","Yes"},new String[]{"B03","Computer Networks","Networking","No"},new String[]{"B04","Advanced Java","Java","Yes"},new String[]{"B05","SQL Essentials","Database","Yes"});
 public LibrarySearchScreen(){super("Campus Library Search");setLayout(new BorderLayout(6,6));add(new JLabel("Campus Library Search",SwingConstants.CENTER),BorderLayout.NORTH);
 JPanel search=new JPanel(new FlowLayout(FlowLayout.LEFT));JButton find=new JButton("Search");search.add(new JLabel("Search"));search.add(query);search.add(cat);search.add(find);
 JPanel center=new JPanel(new BorderLayout());center.add(search,BorderLayout.NORTH);center.add(new JScrollPane(table),BorderLayout.CENTER);add(center,BorderLayout.CENTER);
 JPanel buttons=new JPanel(new FlowLayout());JButton prev=new JButton("Prev"),next=new JButton("Next"),reserve=new JButton("Reserve");buttons.add(prev);buttons.add(next);buttons.add(reserve);
 JPanel south=new JPanel(new BorderLayout());south.add(buttons,BorderLayout.CENTER);south.add(status,BorderLayout.SOUTH);add(south,BorderLayout.SOUTH);
 find.addActionListener(e->search());query.addActionListener(e->search());prev.addActionListener(e->{page=Math.max(0,page-1);search();});next.addActionListener(e->{page++;search();});
 reserve.addActionListener(e->{int r=table.getSelectedRow();status.setText(r<0?"Select a book": "Reservation requested: "+model.getValueAt(r,1));});
 setDefaultCloseOperation(EXIT_ON_CLOSE);setSize(760,480);setLocationRelativeTo(null);search();}
 void search(){model.setRowCount(0);java.util.List<String[]> f=new ArrayList<>();String q=query.getText().toLowerCase(),c=(String)cat.getSelectedItem();
 for(String[]b:books)if((c.equals("All")||b[2].equals(c))&&(b[1].toLowerCase().contains(q)||b[0].toLowerCase().contains(q)))f.add(b);
 int s=Math.min(page*3,f.size()),e=Math.min(s+3,f.size());for(int i=s;i<e;i++)model.addRow(f.get(i));status.setText("Showing "+(f.isEmpty()?0:s+1)+"-"+e+" of "+f.size());}
 public static void main(String[]a){SwingUtilities.invokeLater(()->new LibrarySearchScreen().setVisible(true));}
}