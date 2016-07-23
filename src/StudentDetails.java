import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.Vector;

/**
 * Created by Charmy Garg on 13-Apr-16.
 */
public class StudentDetails extends JFrame implements ActionListener, MouseListener {
    private JTable table1;
    private JTextField nameText;
    private JButton addButton;
    private JButton upButton;
    private JButton delButton;
    private JButton showButton;
    private JPanel panel1;
    private JTextField mailText;
    private JTextField userText;
    private JTextField passText;
    private JTextField cityText;
    private JTextField confirmText;
    private JTextField stateText;
    private JTextField phText;
    private JLabel RedLabel;
    private JLabel nameLabel;
    private JLabel mailLabel;
    private JLabel userLabel;
    private JLabel passLabel;
    private JLabel cityLabel;
    private JLabel confirmLabel;
    private JLabel stateLabel;
    private JLabel phLabel;
    private JComboBox depCombo;
    private JLabel depLabel;
    private JLabel semLabel;
    private JComboBox semCombo;
    private JButton backButton;

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public StudentDetails(){
        super("Student Details");

        this.addButton.addActionListener(this);
        this.upButton.addActionListener(this);
        this.delButton.addActionListener(this);
        this.showButton.addActionListener(this);
        this.backButton.addActionListener(this);
        this.table1.addMouseListener(this);

        this.setContentPane(panel1);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==this.showButton) {
            try {
                Class.forName("org.sqlite.JDBC");
                String sem = semCombo.getSelectedItem().toString();

                if(sem.equals("4")) {

                    con = DriverManager.getConnection("jdbc:sqlite:src/StudentReg.sqlite");
                    Statement stat = con.createStatement();
                    rs = stat.executeQuery("select * from StudentSem4");
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
        }
         catch (SQLException sqle) {
            JOptionPane.showMessageDialog(new JFrame(), sqle + "\n" + "Please contact your system administrator.", "Error", JOptionPane.ERROR_MESSAGE);

            sqle.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            JOptionPane.showMessageDialog(new JFrame(), cnfe + "\n" + "Please contact your system administrator.", "Error", JOptionPane.ERROR_MESSAGE);

            cnfe.printStackTrace();
        }

        }else if (e.getSource() == this.addButton) {

            this.initMethod();
            DefaultTableModel tbModel = (DefaultTableModel) table1.getModel();
            if (!nameText.getText().trim().equals("")) {

                String name=nameText.getText(), mail=mailText.getText(), user= userText.getText(),pass= passText.getText(),dep=depCombo.getSelectedItem().toString();
                String confirm= confirmText.getText(), city= cityText.getText(), state= stateText.getText(), phone= phText.getText(),sem=semCombo.getSelectedItem().toString();
                if(phone.length()<11)
                {
                    String sql = "INSERT INTO StudentSem4(name,Email,Username,Password1,Password2,City,State,Phone_No,Department,Semester)VALUES('" + name + "','" + mail + "','" + user + "','" + pass + "', '" + confirm + "','" + city + "','" + state + "','" + phone + "','" + dep + "','" + sem + "')";
                    try {
                        Connection con = DriverManager.getConnection("jdbc:sqlite:src/StudentReg.sqlite");
                        PreparedStatement s = con.prepareStatement(sql);
                        int rows = s.executeUpdate();

                        tbModel.addRow(new Object[]{nameText.getText(), mailText.getText(), userText.getText(), passText.getText(), confirmText.getText(),
                                cityText.getText(), stateText.getText(), phText.getText(), depCombo.getSelectedItem(), semCombo.getSelectedItem()});

                    } catch (Exception ex) {
                        ex.printStackTrace();

                    }
                }else
                {
                    JOptionPane.showMessageDialog(this,"Phone number must not be more than 10 digit");
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
                tbModel.setValueAt(nameText.getText(), table1.getSelectedRow(), 0);
                tbModel.setValueAt(mailText.getText(), table1.getSelectedRow(), 1);
                tbModel.setValueAt(userText.getText(), table1.getSelectedRow(), 2);
                tbModel.setValueAt(passText.getText(), table1.getSelectedRow(), 3);
                tbModel.setValueAt(confirmText.getText(), table1.getSelectedRow(), 4);
                tbModel.setValueAt(cityText.getText(), table1.getSelectedRow(), 5);
                tbModel.setValueAt(stateText.getText(), table1.getSelectedRow(), 6);

                String ph=phText.getText();
                if(ph.length()<11) {
                    tbModel.setValueAt(phText.getText(), table1.getSelectedRow(), 7);

                    String name = nameText.getText();
                    String mail = mailText.getText(), user = userText.getText(), pass = passText.getText(), confirm = confirmText.getText(), city = cityText.getText(),
                            state = stateText.getText(), phone = phText.getText(), dep = depCombo.getSelectedItem().toString(), sem = semCombo.getSelectedItem().toString();
                    String sql = "UPDATE StudentSem4" + " SET Name=' " + name + " '," + "Email=' " + mail + " '," + "Username=' " + user + " '," + "Password1=' " + pass + " ', " + "Password2=' " + confirm + " '," + " city=' " + city + " '," + "state=' " + state + " '," + "Phone_No=' " + phone + " '," + "Department=' " + dep + " '," + "Semester=' " + sem + " '" + "WHERE Username='" + user + "'";
                    try {
                        Connection con = DriverManager.getConnection("jdbc:sqlite:src/StudentReg.sqlite");
                        Statement s = con.createStatement();
                        int count = s.executeUpdate(sql);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(this,"Phone number must not be more than 10 digit");
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
                String sql = "DELETE FROM StudentSem4 WHERE Username='" + user + "'";
                try {
                    Connection con = DriverManager.getConnection("jdbc:sqlite:src/StudentReg.sqlite");
                    Statement s = con.createStatement();
                    s.execute(sql);
                    tbModel.removeRow(table1.getSelectedRow());

                } catch (Exception ex) {
                    ex.printStackTrace();

                }

            }
        }else
            if(e.getSource()==this.backButton){
                this.setVisible(false);
            }

    }

    public void initMethod() {
        this.nameLabel.setEnabled(true);
        this.mailLabel.setEnabled(true);
        this.userLabel.setEnabled(true);
        this.passLabel.setEnabled(true);
        this.confirmLabel.setEnabled(true);
        this.cityLabel.setEnabled(true);
        this.stateLabel.setEnabled(true);
        this.phLabel.setEnabled(true);
        this.nameText.setEnabled(true);
        this.mailText.setEnabled(true);
        this.userText.setEnabled(true);
        this.passText.setEnabled(true);
        this.confirmText.setEnabled(true);
        this.cityText.setEnabled(true);
        this.stateText.setEnabled(true);
        this.phText.setEnabled(true);
        this.depLabel.setEnabled(true);
        this.depCombo.setEnabled(true);

        this.RedLabel.setText("");

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        this.initMethod();
        DefaultTableModel tbModel = (DefaultTableModel) table1.getModel();
        this.nameText.setText(tbModel.getValueAt(table1.getSelectedRow(), 0).toString());
        this.mailText.setText(tbModel.getValueAt(table1.getSelectedRow(), 1).toString());
        this.userText.setText(tbModel.getValueAt(table1.getSelectedRow(), 2).toString());
        this.passText.setText(tbModel.getValueAt(table1.getSelectedRow(), 3).toString());
        this.confirmText.setText(tbModel.getValueAt(table1.getSelectedRow(), 4).toString());
        this.cityText.setText(tbModel.getValueAt(table1.getSelectedRow(), 5).toString());
        this.stateText.setText(tbModel.getValueAt(table1.getSelectedRow(), 6).toString());
        this.phText.setText(tbModel.getValueAt(table1.getSelectedRow(), 7).toString());
        this.depCombo.setSelectedItem(tbModel.getValueAt(table1.getSelectedRow(), 8).toString());
        this.semCombo.setSelectedItem(tbModel.getValueAt(table1.getSelectedRow(), 9).toString());

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
}
