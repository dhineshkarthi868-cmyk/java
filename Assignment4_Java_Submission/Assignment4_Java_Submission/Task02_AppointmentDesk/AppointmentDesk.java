import javax.swing.*;import java.awt.*;import java.awt.event.*;import java.util.*;
public class AppointmentDesk extends JFrame {
 JTextField patient=new JTextField(12);JComboBox<String> dept=new JComboBox<>(new String[]{"Cardiology","Dermatology","Orthopaedics"}),doctor=new JComboBox<>(),slot=new JComboBox<>(new String[]{"09:00","10:00","11:30","14:00"});
 JLabel status=new JLabel("Ready");boolean booking=false;Map<String,java.util.List<String>> data=new HashMap<>();
 public AppointmentDesk(){super("Hospital Outpatient Appointment Desk");data.put("Cardiology",Arrays.asList("Dr Anand","Dr Meera"));data.put("Dermatology",Arrays.asList("Dr Kiran","Dr Nisha"));data.put("Orthopaedics",Arrays.asList("Dr Ravi","Dr Sahana"));
 setLayout(new BorderLayout());JPanel p=new JPanel(new GridLayout(4,2,8,8));p.add(new JLabel("Patient ID"));p.add(patient);p.add(new JLabel("Department"));p.add(dept);p.add(new JLabel("Doctor"));p.add(doctor);p.add(new JLabel("Time"));p.add(slot);
 JButton book=new JButton("Book");JPanel b=new JPanel();b.add(book);b.add(status);add(p,BorderLayout.CENTER);add(b,BorderLayout.SOUTH);
 dept.addActionListener(e->refreshDoctors());book.addActionListener(e->book());addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){if(!booking||JOptionPane.showConfirmDialog(AppointmentDesk.this,"Booking in progress. Close?")==0)dispose();}});
 setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);refreshDoctors();setSize(500,250);setLocationRelativeTo(null);}
 void refreshDoctors(){doctor.removeAllItems();for(String d:data.getOrDefault(dept.getSelectedItem(),Collections.emptyList()))doctor.addItem(d);}
 void book(){if(!patient.getText().trim().matches("[A-Za-z0-9-]{3,20}")||doctor.getSelectedItem()==null){status.setText("Invalid patient ID or doctor");return;}
 booking=true;JOptionPane.showMessageDialog(this,"Appointment confirmed: "+patient.getText()+" / "+dept.getSelectedItem()+" / "+doctor.getSelectedItem()+" / "+slot.getSelectedItem());booking=false;status.setText("Booked");}
 public static void main(String[]a){SwingUtilities.invokeLater(()->new AppointmentDesk().setVisible(true));}
}