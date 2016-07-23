import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.sql.*;
import java.text.MessageFormat;
import java.util.Vector;

/**
 * Created by Charmy Garg on 15-Apr-16.
 */
public class StudentAccess extends JFrame implements ActionListener {
    private JLabel nameLabel;
    private JLabel semLabel;
    private JLabel depLabel;
    private JTable table1;
    private JPanel panel1;
    private JTextField idText;
    private JButton showButton;
    private JButton logOut;
    private JButton exit;
    private JLabel decLabel;
    private JButton printButton;
    public float per;

    public StudentAccess() {
        super("Student Access");
        this.setContentPane(panel1);
        this.pack();
        this.showButton.addActionListener(this);
        this.printButton.addActionListener(this);
        this.logOut.addActionListener(this);
        this.exit.addActionListener(this);
        this.setVisible(true);
    }

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    @Override
    public void actionPerformed(ActionEvent e) {
        String id = this.idText.getText();
        if(e.getSource()==this.showButton) {

            try {

                Class.forName("org.sqlite.JDBC");
                con = DriverManager.getConnection("jdbc:sqlite:src/StudentProfile.sqlite");
                Statement stat = con.createStatement();
                rs = stat.executeQuery("select * from Sem4 where id ='" + id + "' ");
                this.nameLabel.setText(rs.getString(1));
                this.per=rs.getFloat(10);

                if(per<40) {
                    this.decLabel.setText("Retake Exam");
                }else if(per>40 && per<=80){
                    this.decLabel.setText("Proceed");
                }
                else if(per>80){
                    this.decLabel.setText("Proceed with A");
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
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
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
}
