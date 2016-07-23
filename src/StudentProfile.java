import com.sun.xml.internal.ws.api.ha.StickyFeature;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;
import java.awt.geom.Arc2D;
import java.awt.print.PrinterException;
import java.sql.*;
import java.text.MessageFormat;
import java.util.Vector;


/**
 * Created by Charmy Garg on 3/5/2016.
 */
public class StudentProfile extends JFrame implements ActionListener,MouseListener,KeyListener{

    private JPanel panel1;
    private JTable table1;
    private JScrollPane scroll1;
    private JButton showButton;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton addButton;
    private JTextField misText;
    private JTextField mobileText;
    private JTextField wtText;
    private JTextField disText;
    private JTextField aiText;
    private JTextField PracText;
    private JLabel label2;
    private JLabel label3;
    private JLabel label5;
    private JLabel label1;
    private JLabel label6;
    private JLabel label4;
    private JLabel nameLabel;
    private JLabel idLabel;
    private JTextField nameText;
    private JTextField idText;
    private JLabel RedLabel;
    private JButton updateButton;
    private JButton delButton;
    private JButton logOut;
    private JButton totalButton;
    private JTextField totalText;
    private JLabel totalLabel;
    private JButton percentButton;
    private JTextField PercentText;
    private JButton stButton;
    private JButton exit;
    private JButton backButton;
    private JTextField filterText;
    private JButton printButton;
    private JLabel perLabel;

    DefaultTableModel model;
    public String id1;
    public String sem;

    DefaultTableModel tbModel;

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public StudentProfile() {
        super("Student Profile");

        this.showButton.addActionListener(this);
        this.addButton.addActionListener(this);
        this.updateButton.addActionListener(this);
        this.delButton.addActionListener(this);
        this.logOut.addActionListener(this);
        this.stButton.addActionListener(this);
        this.printButton.addActionListener(this);
        this.filterText.addKeyListener(this);
        this.backButton.addActionListener(this);
        this.exit.addActionListener(this);
        this.table1.addMouseListener(this);

        this.setContentPane(panel1);
        this.pack();
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.showButton) {

            try {
                Class.forName("org.sqlite.JDBC");
                sem = comboBox1.getSelectedItem().toString();

                if (sem.equals("1")) {
                    con = DriverManager.getConnection("jdbc:sqlite:src/StudentProfile.sqlite");
                    Statement stat = con.createStatement();
                    rs = stat.executeQuery("select * from Sem1");
                    this.label1.setText("C Prog");
                    this.label2.setText("Accounts");
                    this.label3.setText("Discrete Maths");
                    this.label4.setText("Professional Communication");
                    this.label5.setText("DLD");

                }
                else if (sem.equals("2")) {
                    con = DriverManager.getConnection("jdbc:sqlite:src/StudentProfile.sqlite");
                    Statement stat = con.createStatement();
                    rs = stat.executeQuery("select * from Sem2");
                    this.label1.setText("Data Structure");
                    this.label2.setText("Automata");
                    this.label3.setText("CBNST");
                    this.label4.setText("Computer Organization");
                    this.label5.setText("EVS");
                }
                else if (sem.equals("3")) {
                    con = DriverManager.getConnection("jdbc:sqlite:src/StudentProfile.sqlite");
                    Statement stat = con.createStatement();
                    rs = stat.executeQuery("select * from Sem3");
                    this.label1.setText("DAA");
                    this.label2.setText("Operating System");
                    this.label3.setText("Java");
                    this.label4.setText("DBMS");
                    this.label5.setText("CBOT");
                }
                else if (sem.equals("4")) {
                    con = DriverManager.getConnection("jdbc:sqlite:src/StudentProfile.sqlite");
                    Statement stat = con.createStatement();
                    rs = stat.executeQuery("select * from Sem4");

                    this.label1.setText("Artificial Intelligence");
                    this.label2.setText("MIS");
                    this.label3.setText("Mobile Computing");
                    this.label4.setText("Distributed System");
                    this.label5.setText("Web Tech");
                }

                ResultSetMetaData md = rs.getMetaData();
                int columnCount = md.getColumnCount();
                Vector columns = new Vector(columnCount);

                //store column names
                for (int i = 1; i <= columnCount; i++)
                    columns.add(md.getColumnName(i));

                Vector data = new Vector();
                Vector row;

                while (rs.next()) {

                    row = new Vector(columnCount);
                    for (int i = 1; i <= columnCount; i++) {
                        row.add(rs.getString(i));
                    }
                    data.add(row);

                }

                //Display in JTable
                 tbModel = new DefaultTableModel(data, columns);
                table1.setModel(tbModel);

                rs.close();
                con.close();
            } catch (SQLException sqle) {
                JOptionPane.showMessageDialog(new JFrame(), sqle + "\n" + "Please contact your system administrator.", "Error", JOptionPane.ERROR_MESSAGE);

                sqle.printStackTrace();
            } catch (ClassNotFoundException cnfe) {
                JOptionPane.showMessageDialog(new JFrame(), cnfe + "\n" + "Please contact your system administrator.", "Error", JOptionPane.ERROR_MESSAGE);

                cnfe.printStackTrace();
            }

// Inserting details of marks in JTable
        } else if (e.getSource() == this.addButton) {

                    this.initMethod();
                     tbModel = (DefaultTableModel) table1.getModel();
                    if (!nameText.getText().trim().equals("")) {

                        String name=nameText.getText(), id=idText.getText(), mis= misText.getText(),mobile= mobileText.getText();
                        String wt= wtText.getText(), ai= aiText.getText(), practical= PracText.getText(), dis= disText.getText();

                        int tot = Integer.parseInt(ai) +Integer.parseInt(mis) + Integer.parseInt(mobile) + Integer.parseInt(dis) + Integer.parseInt(wt) + Integer.parseInt(practical);
                        this.totalText.setText(Integer.toString(tot));

                        int per = ((tot *100)/600);
                        this.PercentText.setText(Float.toString(per));

                        if (id.length() < 8 && mis.length() < 3 && mobile.length() < 3 && wt.length() < 3 && ai.length() < 3 && practical.length() < 3 && dis.length() < 3) {
                        String sql = "INSERT INTO Sem4(name,id,ArtificialIntelligence,MIS,MobileComputing,DistributedSystem,WebTech,Practical,Total,Percentage)VALUES('" + name + "','" + id + "','" + ai + "','" + mis + "', '" + mobile + "','" + dis + "','" + wt + "','" + practical + "','" + tot + "','" + per + "')";
                            try {
                                Connection con = DriverManager.getConnection("jdbc:sqlite:src/StudentProfile.sqlite");
                                PreparedStatement s = con.prepareStatement(sql);
                                int rows = s.executeUpdate();

                                Statement st = con.createStatement();
                                rs = st.executeQuery("select * from Sem4");
                                String id1=rs.getString(2);

                                tbModel.addRow(new Object[]{nameText.getText(), idText.getText(), aiText.getText(), misText.getText(), mobileText.getText(),
                                                disText.getText(), wtText.getText(), PracText.getText(), totalText.getText(),PercentText.getText()});

                            }
                            catch (Exception ex)
                            {
                                ex.printStackTrace();
                            }
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(this,"marks must not be more than 99 or ID must not be more than length of 8");
                        }

            } else {
                        this.RedLabel.setText("Name should not be left blank");
                    }

//Updating marks of students in JTable
        } else if (e.getSource() == this.updateButton) {

            this.initMethod();
             tbModel = (DefaultTableModel) table1.getModel();
            if (table1.getSelectedRow() == -1) {
                if (table1.getRowCount() == 0) {
                    this.RedLabel.setText("Table is empty");
                } else {
                    this.RedLabel.setText("Row not selected");
                }
            } else {

                String name1=nameText.getText(), id1=idText.getText(), mis1= misText.getText(),mobile1= mobileText.getText();
                String wt1= wtText.getText(), ai1= aiText.getText(), practical1= PracText.getText(), dis1= disText.getText();

                int tot = Integer.parseInt(ai1) +Integer.parseInt(mis1) + Integer.parseInt(mobile1) + Integer.parseInt(dis1) + Integer.parseInt(wt1) + Integer.parseInt(practical1);
                this.totalText.setText(Integer.toString(tot));

                int per = ((tot *100)/600);
                this.PercentText.setText(Float.toString(per));

                tbModel.setValueAt(nameText.getText(), table1.getSelectedRow(), 0);
                if (id1.length() < 8 && mis1.length() < 3 && mobile1.length() < 3 && wt1.length() < 3 && ai1.length() < 3 && practical1.length() < 3 && dis1.length() < 3) {
                    tbModel.setValueAt(idText.getText(), table1.getSelectedRow(), 1);
                    tbModel.setValueAt(aiText.getText(), table1.getSelectedRow(), 2);
                    tbModel.setValueAt(misText.getText(), table1.getSelectedRow(), 3);
                    tbModel.setValueAt(mobileText.getText(), table1.getSelectedRow(), 4);
                    tbModel.setValueAt(disText.getText(), table1.getSelectedRow(), 5);
                    tbModel.setValueAt(wtText.getText(), table1.getSelectedRow(), 6);
                    tbModel.setValueAt(PracText.getText(), table1.getSelectedRow(), 7);
                    tbModel.setValueAt(totalText.getText(), table1.getSelectedRow(), 8);
                    tbModel.setValueAt(PercentText.getText(), table1.getSelectedRow(), 9);

                    String name=nameText.getText();
                    String sql = "UPDATE Sem4"+" SET Name=' " + name + " '," + "ID=' " +  id1 + " ',"+"ArtificialIntelligence=' " + ai1 + " ', "+"MIS=' " + mis1 + " ',"+" MobileComputing=' " + mobile1 + " ',"+"DistributedSystem=' " + dis1 +" ',"+"WebTech=' " + wt1 +" ',"+"Practical=' " + practical1 + " ',"+"Total=' " + tot + " ',"+"Percentage=' " + per + " ' "+"WHERE ID='" + id1 + "'";
                    try {
                        Connection con = DriverManager.getConnection("jdbc:sqlite:src/StudentProfile.sqlite");
                        Statement s = con.createStatement();
                        int count =s.executeUpdate(sql);

                    } catch (Exception ex) {
                    ex.printStackTrace();
                    }

                }
                else
                {
                    JOptionPane.showMessageDialog(this,"marks must not be more than 99 or ID must not be more than length of 8");
                }

            }

        }

//Deleting marks of students from JTable
        else if (e.getSource() == this.delButton) {
             tbModel = (DefaultTableModel) table1.getModel();
            if (table1.getSelectedRow() == -1) {
                if (table1.getRowCount() == 0) {
                    this.RedLabel.setText("Table is empty");
                } else {
                    this.RedLabel.setText("Row not selected");
                }
            } else {
                String id = idText.getText();
                tbModel.removeRow(table1.getSelectedRow());
                String sql = "DELETE FROM Sem4 WHERE ID='" + id + "'";
                try {
                    Connection con = DriverManager.getConnection("jdbc:sqlite:src/StudentProfile.sqlite");
                    Statement s = con.createStatement();
                    s.execute(sql);

                } catch (Exception ex) {
                    ex.printStackTrace();

                }
                try {
                    con.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        }

        else if(e.getSource()==this.stButton){
            StudentDetails sd =new StudentDetails();
        }
        else
            if(e.getSource()==this.logOut){
                this.setVisible(false);

            }
        else if(e.getSource()==this.exit){
                System.exit(0);
            }
        else if(e.getSource()==this.backButton){
                Manage m = new Manage();
            }
            else if(e.getSource()==this.printButton){

                MessageFormat header=new MessageFormat("Report Print");
                MessageFormat footer=new MessageFormat("Page {0,number,integer}");

                try {
                    table1.print(JTable.PrintMode.NORMAL, header, footer);
                } catch (PrinterException e1) {
                    e1.printStackTrace();
                }
            }

    }

    private void filter(String query){
        TableRowSorter<DefaultTableModel> tr=new TableRowSorter<DefaultTableModel>(tbModel);
        table1.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }


    public void initMethod() {
        this.label2.setEnabled(true);
        this.label3.setEnabled(true);
        this.label5.setEnabled(true);
        this.label1.setEnabled(true);
        this.label4.setEnabled(true);
        this.label6.setEnabled(true);
        this.nameLabel.setEnabled(true);
        this.idLabel.setEnabled(true);
        this.misText.setEnabled(true);
        this.mobileText.setEnabled(true);
        this.PracText.setEnabled(true);
        this.disText.setEnabled(true);
        this.wtText.setEnabled(true);
        this.aiText.setEnabled(true);
        this.nameText.setEnabled(true);
        this.idText.setEnabled(true);
        this.totalText.setEnabled(true);
        this.PercentText.setEnabled(true);
        this.totalLabel.setEnabled(true);
        this.perLabel.setEnabled(true);

        this.RedLabel.setText("");

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.initMethod();
        DefaultTableModel tbModel = (DefaultTableModel) table1.getModel();
        this.nameText.setText(tbModel.getValueAt(table1.getSelectedRow(), 0).toString());
        this.idText.setText(tbModel.getValueAt(table1.getSelectedRow(), 1).toString());
        this.aiText.setText(tbModel.getValueAt(table1.getSelectedRow(), 2).toString());
        this.misText.setText(tbModel.getValueAt(table1.getSelectedRow(), 3).toString());
        this.mobileText.setText(tbModel.getValueAt(table1.getSelectedRow(), 4).toString());
        this.disText.setText(tbModel.getValueAt(table1.getSelectedRow(), 5).toString());
        this.wtText.setText(tbModel.getValueAt(table1.getSelectedRow(), 6).toString());
        this.PracText.setText(tbModel.getValueAt(table1.getSelectedRow(), 7).toString());
        this.totalText.setText(tbModel.getValueAt(table1.getSelectedRow(), 8).toString());
        this.PercentText.setText(tbModel.getValueAt(table1.getSelectedRow(), 9).toString());

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        String query=filterText.getText();
        filter(query);

    }
}









