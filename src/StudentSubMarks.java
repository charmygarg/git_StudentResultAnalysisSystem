import com.sun.imageio.plugins.png.*;
import com.sun.org.apache.xerces.internal.util.MessageFormatter;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.sql.*;
import java.text.MessageFormat;
import java.util.Vector;

/**
 * Created by Charmy Garg on 15-Apr-16.
 */
public class StudentSubMarks extends JFrame implements ActionListener,MouseListener,KeyListener
{
    private JTable table1;
    private JPanel panel1;
    private JLabel nameLabel;
    private JTextField nameText;
    private JLabel RedLabel;
    private JButton updateButton;
    private JLabel idLabel;
    private JTextField idText;
    private JButton showButton;
    private JTextField subText;
    private JLabel subLabel;
    private JComboBox semCombo;
    private JLabel facName;
    private JTextField facText;
    private JLabel semLabel;
    private JButton logOut;
    private JButton exit;
    private JButton addButton;
    private JTextField filterText;
    private JButton printButton;
    private JLabel pracLabel;
    private JTextField pracText;
    private JButton resultButton;
    private JButton backButton;
    DefaultTableModel tbModel;

    public  String sub;
    public  String marks;


    Connection con = null;
    ResultSet rs = null;
    ResultSet rs1 = null;
    PreparedStatement pst = null;

    public StudentSubMarks(){
        super("StudentSubMarks");

        this.showButton.addActionListener(this);
        this.updateButton.addActionListener(this);
        this.logOut.addActionListener(this);
        this.addButton.addActionListener(this);
        this.printButton.addActionListener(this);
        this.filterText.addKeyListener(this);
        this.exit.addActionListener(this);
        this.table1.addMouseListener(this);

        this.setContentPane(panel1);
        this.pack();
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.showButton) {

            String sem = semCombo.getSelectedItem().toString();
            String name = facText.getText();

            try {
                Class.forName("org.sqlite.JDBC");
                con = DriverManager.getConnection("jdbc:sqlite:src/Facultyreg1.sqlite");
                Statement stat = con.createStatement();
                rs1 = stat.executeQuery("select * from FacultySem4  where Name='" + name + "' ");

                this.subLabel.setText(rs1.getString(7));
                sub = subLabel.getText();

                if (sem.equals("4")) {
                    con = DriverManager.getConnection("jdbc:sqlite:src/StudentProfile.sqlite");
                    Statement stat1 = con.createStatement();
                    rs = stat1.executeQuery("select * from Sem4 ");

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
        }

//Adding marks by particular subject teacher
        else if(e.getSource()==this.addButton) {

            this.initMethod();
            tbModel = (DefaultTableModel) table1.getModel();
            if (!nameText.getText().trim().equals("")) {

                String name = nameText.getText(), id = idText.getText(), sub = subLabel.getText();
                if (sub.equals("Artificial Intelligence")) {
                    String AIsub = subText.getText();
                    if (AIsub.length() < 3) {

                        String sql = "INSERT INTO Sem4(Name,Id,ArtificialIntelligence)VALUES('" + name + "','" + id + "','" + AIsub + "')";
                        try {
                            Connection con = DriverManager.getConnection("jdbc:sqlite:src/StudentProfile.sqlite");
                            PreparedStatement s = con.prepareStatement(sql);
                            int rows = s.executeUpdate();

                            tbModel.addRow(new Object[]{nameText.getText(), idText.getText()});
                            tbModel.setValueAt(subText.getText(), table1.getSelectedRow(), 2);
                            String ai = tbModel.getValueAt(table1.getSelectedRow(), 2).toString();
                            String mis = tbModel.getValueAt(table1.getSelectedRow(), 3).toString();
                            String mc = tbModel.getValueAt(table1.getSelectedRow(), 4).toString();
                            String dis = tbModel.getValueAt(table1.getSelectedRow(), 5).toString();
                            String wt = tbModel.getValueAt(table1.getSelectedRow(), 6).toString();
                            String prac = tbModel.getValueAt(table1.getSelectedRow(), 7).toString();

                            int tot = Integer.parseInt(ai) +Integer.parseInt(mis) + Integer.parseInt(mc) + Integer.parseInt(dis) + Integer.parseInt(wt) + Integer.parseInt(prac);
                            tbModel.setValueAt(tot, table1.getSelectedRow(), 8);

                            int per = ((tot *100)/600);
                            tbModel.setValueAt(per, table1.getSelectedRow(), 9);

                        } catch (Exception ex) {
                            ex.printStackTrace();

                        }
                    }
                }else if (sub.equals("MIS")) {
                    String MISsub = subText.getText();
                    if (MISsub.length() < 3) {

                        String sql = "INSERT INTO Sem4(Name,Id,MIS)VALUES('" + name + "','" + id + "','" + MISsub + "')";
                        try {
                            Connection con = DriverManager.getConnection("jdbc:sqlite:src/StudentProfile.sqlite");
                            PreparedStatement s = con.prepareStatement(sql);
                            int rows = s.executeUpdate();

                            tbModel.addRow(new Object[]{nameText.getText(), idText.getText()});
                            tbModel.setValueAt(subText.getText(), table1.getSelectedRow(), 3);

                        } catch (Exception ex) {
                            ex.printStackTrace();

                        }
                    }
                }else if (sub.equals("Mobile Computing")) {
                    String MCsub = subText.getText();
                    if (MCsub.length() < 3) {

                        String sql = "INSERT INTO Sem4(Name,Id,MobileComputing)VALUES('" + name + "','" + id + "','" + MCsub + "')";
                        try {
                            Connection con = DriverManager.getConnection("jdbc:sqlite:src/StudentProfile.sqlite");
                            PreparedStatement s = con.prepareStatement(sql);
                            int rows = s.executeUpdate();

                            tbModel.addRow(new Object[]{nameText.getText(), idText.getText()});
                            tbModel.setValueAt(subText.getText(), table1.getSelectedRow(), 4);

                        } catch (Exception ex) {
                            ex.printStackTrace();

                        }
                    }
                }else if (sub.equals("Distributed System")) {
                    String DSsub = subText.getText();
                    if (DSsub.length() < 3) {

                        String sql = "INSERT INTO Sem4(Name,Id,DistributedSystem)VALUES('" + name + "','" + id + "','" + DSsub + "')";
                        try {
                            Connection con = DriverManager.getConnection("jdbc:sqlite:src/StudentProfile.sqlite");
                            PreparedStatement s = con.prepareStatement(sql);
                            int rows = s.executeUpdate();

                            tbModel.addRow(new Object[]{nameText.getText(), idText.getText()});
                            tbModel.setValueAt(subText.getText(), table1.getSelectedRow(), 5);

                        } catch (Exception ex) {
                            ex.printStackTrace();

                        }
                    }
                }else if (sub.equals("Web Tech")) {
                    String WTsub = subText.getText();
                    String Prac=pracText.getText();
                    if (WTsub.length() < 3 && Prac.length()<3) {

                        String sql = "INSERT INTO Sem4(Name,Id,WebTech,Practical)VALUES('" + name + "','" + id + "','" + WTsub + "','" + Prac + "')";
                        try {
                            Connection con = DriverManager.getConnection("jdbc:sqlite:src/StudentProfile.sqlite");
                            PreparedStatement s = con.prepareStatement(sql);
                            int rows = s.executeUpdate();

                            tbModel.addRow(new Object[]{nameText.getText(), idText.getText()});
                            tbModel.setValueAt(subText.getText(), table1.getSelectedRow(), 6);
                            tbModel.setValueAt(subText.getText(), table1.getSelectedRow(), 7);

                        } catch (Exception ex) {
                            ex.printStackTrace();

                        }
                    }
                }
            }
        }

        else if (e.getSource() == this.updateButton) {

            this.initMethod();
            tbModel = (DefaultTableModel) table1.getModel();
            if (table1.getSelectedRow() == -1) {
                if (table1.getRowCount() == 0) {
                    this.RedLabel.setText("Table is empty");
                } else {
                    this.RedLabel.setText("Row not selected");
                }
            } else {
                tbModel.setValueAt(nameText.getText(), table1.getSelectedRow(), 0);
                tbModel.setValueAt(idText.getText(), table1.getSelectedRow(), 1);
                String name = nameText.getText();
                int id = Integer.parseInt(idText.getText());


                if (sub.equals("MIS")) {
                    String MISmarks = subText.getText();

                    if (MISmarks.length() < 3) {

                        tbModel.setValueAt(subText.getText(), table1.getSelectedRow(), 3);
                        this.totPer();
                        String tot = tbModel.getValueAt(table1.getSelectedRow(), 8).toString();
                        String per = tbModel.getValueAt(table1.getSelectedRow(), 9).toString();

                        String sql = "UPDATE Sem4" + " SET Name=' " + name + " '," + "ID=' " + id + " ', " + "MIS=' " + MISmarks + " ', " + "Total=' " + tot + " ', " + "Percentage=' " + per + " ' " + "WHERE ID='" + id + "'";
                        try {
                            Connection con = DriverManager.getConnection("jdbc:sqlite:src/StudentProfile.sqlite");
                            Statement s = con.createStatement();
                            int count = s.executeUpdate(sql);

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    } else {
                        JOptionPane.showMessageDialog(this, "marks must not be more than 99 ");
                    }


                } else if (sub.equals("Artificial Intelligence")) {
                    String AImarks = subText.getText();


                    if (AImarks.length() < 3) {

                        tbModel.setValueAt(subText.getText(), table1.getSelectedRow(), 2);
                        this.totPer();
                        String tot = tbModel.getValueAt(table1.getSelectedRow(), 8).toString();
                        String per = tbModel.getValueAt(table1.getSelectedRow(), 9).toString();

                        String sql = "UPDATE Sem4" + " SET Name=' " + name + " '," + "ID=' " + id + " ', " + "ArtificialIntelligence=' " + AImarks + " ', " + "Total=' " + tot + " ', " + "Percentage=' " + per + " ' " + "WHERE ID='" + id + "'";
                        try {
                            Connection con = DriverManager.getConnection("jdbc:sqlite:src/StudentProfile.sqlite");
                            Statement s = con.createStatement();
                            int count = s.executeUpdate(sql);

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "marks must not be more than 99 ");
                    }

                } else if (sub.equals("Mobile Computing")) {

                    String MCmarks = subText.getText();

                    if (MCmarks.length() < 3) {


                        tbModel.setValueAt(subText.getText(), table1.getSelectedRow(), 4);
                        this.totPer();
                        String tot = tbModel.getValueAt(table1.getSelectedRow(), 8).toString();
                        String per = tbModel.getValueAt(table1.getSelectedRow(), 9).toString();

                        String sql = "UPDATE Sem4" + " SET Name=' " + name + " '," + "ID=' " + id + " ', " + "MobileComputing=' " + MCmarks + " ', " + "Total=' " + tot + " ', " + "Percentage=' " + per + " '" + "WHERE ID='" + id + "'";
                        try {
                            Connection con = DriverManager.getConnection("jdbc:sqlite:src/StudentProfile.sqlite");
                            Statement s = con.createStatement();
                            int count = s.executeUpdate(sql);

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "marks must not be more than 99 ");
                    }

                } else if (sub.equals("Distributed System")) {
                    String DSmarks = subText.getText();

                    if (DSmarks.length() < 3) {

                        tbModel.setValueAt(subText.getText(), table1.getSelectedRow(), 5);
                        this.totPer();
                        String tot = tbModel.getValueAt(table1.getSelectedRow(), 8).toString();
                        String per = tbModel.getValueAt(table1.getSelectedRow(), 9).toString();

                        String sql = "UPDATE Sem4" + " SET Name=' " + name + " '," + "ID=' " + id + " ', " + "DistributedSystem=' " + DSmarks + " ', " + "Total=' " + tot + " ', " + "Percentage=' " + per + " ' " + "WHERE ID='" + id + "'";
                        try {
                            Connection con = DriverManager.getConnection("jdbc:sqlite:src/StudentProfile.sqlite");
                            Statement s = con.createStatement();
                            int count = s.executeUpdate(sql);

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "marks must not be more than 99 ");
                    }

                } else if (sub.equals("Web Tech")) {
                    String WTmarks = subText.getText();
                    String prac = pracText.getText();
                    if (WTmarks.length() < 3 && prac.length() < 3) {


                        tbModel.setValueAt(subText.getText(), table1.getSelectedRow(), 6);
                        tbModel.setValueAt(pracText.getText(), table1.getSelectedRow(), 7);
                        this.totPer();
                        String tot = tbModel.getValueAt(table1.getSelectedRow(), 8).toString();
                        String per = tbModel.getValueAt(table1.getSelectedRow(), 9).toString();

                        String sql = "UPDATE Sem4" + " SET Name=' " + name + " '," + "ID=' " + id + " ', " + "WebTech=' " + WTmarks + " '," + "Practical='" + prac + " ', " + "Total=' " + tot + " ', " + "Percentage=' " + per + " '" + "WHERE ID='" + id + "'";
                        try {
                            Connection con = DriverManager.getConnection("jdbc:sqlite:src/StudentProfile.sqlite");
                            Statement s = con.createStatement();
                            int count = s.executeUpdate(sql);

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "marks must not be more than 99 ");
                    }
                }
            }

        }
        else
            if(e.getSource()==this.logOut){
                this.setVisible(false);
            }
        else if(e.getSource()==this.exit){
                System.exit(0);
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
        this.subLabel.setEnabled(true);
        this.subText.setEnabled(true);
        this.nameLabel.setEnabled(true);
        this.idLabel.setEnabled(true);
        this.nameText.setEnabled(true);
        this.idText.setEnabled(true);

        this.RedLabel.setText("");

    }

    public void totPer(){
        String ai = tbModel.getValueAt(table1.getSelectedRow(), 2).toString();
        String mis = tbModel.getValueAt(table1.getSelectedRow(), 3).toString();
        String mc = tbModel.getValueAt(table1.getSelectedRow(), 4).toString();
        String dis = tbModel.getValueAt(table1.getSelectedRow(), 5).toString();
        String wt = tbModel.getValueAt(table1.getSelectedRow(), 6).toString();
        String prac = tbModel.getValueAt(table1.getSelectedRow(), 7).toString();

        int tot = Integer.parseInt(ai) +Integer.parseInt(mis) + Integer.parseInt(mc) + Integer.parseInt(dis) + Integer.parseInt(wt) + Integer.parseInt(prac);
        tbModel.setValueAt(tot, table1.getSelectedRow(), 8);

        int per = ((tot *100)/600);
        tbModel.setValueAt(per, table1.getSelectedRow(), 9);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.initMethod();
        DefaultTableModel tbModel = (DefaultTableModel) table1.getModel();
        this.nameText.setText(tbModel.getValueAt(table1.getSelectedRow(), 0).toString());
        this.idText.setText(tbModel.getValueAt(table1.getSelectedRow(), 1).toString());

        if(sub.equals("MIS")) {
            this.subText.setText(tbModel.getValueAt(table1.getSelectedRow(), 3).toString());

        }else
            if(sub.equals("Artificial Intelligence")){
                this.subText.setText(tbModel.getValueAt(table1.getSelectedRow(), 2).toString());
            }
            else
            if(sub.equals("Mobile Computing")){
                this.subText.setText(tbModel.getValueAt(table1.getSelectedRow(), 4).toString());
            }
            else
            if(sub.equals("Distributed System")){
                this.subText.setText(tbModel.getValueAt(table1.getSelectedRow(), 5).toString());
            }
            else
            if(sub.equals("Web Tech")){
                this.pracText.setEnabled(true);
                this.pracLabel.setEnabled(true);
                this.pracLabel.setText("Practical");

                this.subText.setText(tbModel.getValueAt(table1.getSelectedRow(), 6).toString());

                this.pracText.setText(tbModel.getValueAt(table1.getSelectedRow(), 7).toString());

            }
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
