import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.Vector;

/**
 * Created by Charmy Garg on 13-Apr-16.
 */
public class FacultyManage extends JFrame implements ActionListener,MouseListener{
    private JLabel nameLabel;
    private JTextField nameText;
    private JTable table1;
    private JButton addButton;
    private JComboBox semCombo;
    private JButton backButton;
    private JPanel panel1;
    private JButton upButton;
    private JButton delButton;
    private JButton clearButton;
    private JButton showButton;
    private JLabel RedLabel;
    private JLabel userLabel;
    private JLabel passLabel;
    private JLabel confirmLabel;
    private JTextField userText;
    private JTextField passText;
    private JTextField confirmText;
    private JComboBox depCombo;
    private JTextField subText;
    private JLabel subLabel;
    private JButton logOut;
    private JButton exit;

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public FacultyManage()
    {
        super("Faculty Manage");

        this.addButton.addActionListener(this);
        this.upButton.addActionListener(this);
        this.delButton.addActionListener(this);
        this.showButton.addActionListener(this);
        this.logOut.addActionListener(this);
        this.exit.addActionListener(this);
        this.table1.addMouseListener(this);

        this.setContentPane(panel1);
        this.pack();
        this.setVisible(true);
        this.setPreferredSize(new Dimension(4500,4000));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.showButton) {

            try {
                Class.forName("org.sqlite.JDBC");
                String sem = semCombo.getSelectedItem().toString();

                if (sem.equals("1")) {
                    con = DriverManager.getConnection("jdbc:sqlite:src/Facultyreg1.sqlite");
                    Statement stat = con.createStatement();
                    rs = stat.executeQuery("select * from FacultySem1");

                } else if (sem.equals("2")) {
                    con = DriverManager.getConnection("jdbc:sqlite:src/Facultyreg1.sqlite");
                    Statement stat = con.createStatement();
                    rs = stat.executeQuery("select * from FacultySem2");

                } else if (sem.equals("3")) {
                    con = DriverManager.getConnection("jdbc:sqlite:src/Facultyreg1.sqlite");
                    Statement stat = con.createStatement();
                    rs = stat.executeQuery("select * from FacultySem3");

                } else if (sem.equals("4")) {
                    con = DriverManager.getConnection("jdbc:sqlite:src/Facultyreg1.sqlite");
                    Statement stat = con.createStatement();
                    rs = stat.executeQuery("select * from FacultySem4");
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
                DefaultTableModel tableModel = new DefaultTableModel(data, columns);
                table1.setModel(tableModel);

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
        else if (e.getSource() == this.addButton) {

            this.initMethod();
            DefaultTableModel tbModel = (DefaultTableModel) table1.getModel();
            if (!nameText.getText().trim().equals("")) {

                String name=nameText.getText(), user=userText.getText(), pass= passText.getText(),confirm= confirmText.getText();
                String sem= depCombo.getSelectedItem().toString(), dep= semCombo.getSelectedItem().toString(),sub=subText.getText();
                String sql = "INSERT INTO FacultySem4(Name,Username,Password1,Password2,Department,Semester,Subject)VALUES('" + name + "','" + user + "','" + pass + "','" + confirm + "', '" + dep + "','" + sem + "  ','"+sub+" ')";
                try {
                    Connection con = DriverManager.getConnection("jdbc:sqlite:src/Facultyreg1.sqlite");
                    PreparedStatement s = con.prepareStatement(sql);
                    int rows = s.executeUpdate();
                    // if(ct1<=10 && ct2<=10 && st1<=30 && st2<=30 && practical<=30 && put<=30 )
                    tbModel.addRow(new Object[]{nameText.getText(), userText.getText(),  passText.getText(),confirmText.getText(),
                    depCombo.getSelectedItem().toString(),  semCombo.getSelectedItem().toString(), subText.getText()});

                } catch (Exception ex) {
                    ex.printStackTrace();

                }

            } else {
                this.RedLabel.setText("Name should not be left blank");
            }

        }
        else if (e.getSource() == this.upButton) {

            this.initMethod();
            DefaultTableModel tbModel = (DefaultTableModel) table1.getModel();
            if (table1.getSelectedRow() == -1) {
                if (table1.getRowCount() == 0) {
                    this.RedLabel.setText("Table is empty");
                } else {
                    this.RedLabel.setText("Row not selected");
                }
            } else {



                String name=nameText.getText();
                String user=userText.getText(), pass=passText.getText(),confirm= confirmText.getText(), dep=depCombo.getSelectedItem().toString(), sem=semCombo.getSelectedItem().toString(),sub=subText.getText();
                String sql = "UPDATE FacultySem4"+" SET Name=' " + name + " '," + "Username=' " + user + " ',"+"Password1=' " + pass + " ', "+"Password2=' " + confirm + " ',"+" Department=' " + dep + " ',"+"Semester=' " + sem +" ' ,"+" Subject=' " + sub + " '"+"WHERE Username='" + user + "'";
                try {
                    Connection con = DriverManager.getConnection("jdbc:sqlite:src/Facultyreg1.sqlite");
                    Statement s = con.createStatement();
                    int count =s.executeUpdate(sql);

                    tbModel.setValueAt(nameText.getText(), table1.getSelectedRow(), 0);
                    tbModel.setValueAt(userText.getText(), table1.getSelectedRow(), 1);
                    tbModel.setValueAt(passText.getText(), table1.getSelectedRow(), 2);
                    tbModel.setValueAt(confirmText.getText(), table1.getSelectedRow(), 3);
                    tbModel.setValueAt(depCombo.getSelectedItem(), table1.getSelectedRow(), 4);
                    tbModel.setValueAt(semCombo.getSelectedItem(), table1.getSelectedRow(), 5);
                    tbModel.setValueAt(subText.getText(), table1.getSelectedRow(), 6);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

        } else if (e.getSource() == this.delButton) {
            DefaultTableModel tbModel = (DefaultTableModel) table1.getModel();
            if (table1.getSelectedRow() == -1) {
                if (table1.getRowCount() == 0) {
                    this.RedLabel.setText("Table is empty");
                } else {
                    this.RedLabel.setText("Row not selected");
                }
            } else {
                String user = userText.getText();
                String sql = "DELETE FROM FacultySem4 WHERE Username='" + user + "'";
                try {
                    Connection con = DriverManager.getConnection("jdbc:sqlite:src/Facultyreg1.sqlite");
                    Statement s = con.createStatement();
                    s.execute(sql);

                    tbModel.removeRow(table1.getSelectedRow());

                } catch (Exception ex) {
                    ex.printStackTrace();

                }

            }
        }
        else if(e.getSource()==this.logOut)
        {

            this.setVisible(false);
        }
        else if(e.getSource()==exit){
            System.exit(0);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.initMethod();
        DefaultTableModel tbModel = (DefaultTableModel) table1.getModel();
        this.nameText.setText(tbModel.getValueAt(table1.getSelectedRow(), 0).toString());
        this.userText.setText(tbModel.getValueAt(table1.getSelectedRow(), 1).toString());
        this.passText.setText(tbModel.getValueAt(table1.getSelectedRow(), 2).toString());
        this.confirmText.setText(tbModel.getValueAt(table1.getSelectedRow(), 3).toString());
        this.depCombo.setSelectedItem(tbModel.getValueAt(table1.getSelectedRow(), 4).toString());
        this.semCombo.setSelectedItem(tbModel.getValueAt(table1.getSelectedRow(), 5).toString());
        this.subText.setText(tbModel.getValueAt(table1.getSelectedRow(), 6).toString());

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

    public void initMethod() {

        this.nameLabel.setEnabled(true);
        this.userLabel.setEnabled(true);
        this.passLabel.setEnabled(true);
        this.confirmLabel.setEnabled(true);
        this.subLabel.setEnabled(true);

        this.nameText.setEnabled(true);
        this.userText.setEnabled(true);
        this.passText.setEnabled(true);
        this.confirmText.setEnabled(true);
        this.subText.setEnabled(true);

        this.RedLabel.setText("");

    }

}
