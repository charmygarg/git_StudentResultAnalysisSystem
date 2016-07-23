import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Created by Charmy Garg on 3/1/2016.
 */
public class FacultyReg extends JFrame implements ActionListener{

    public JPanel panel1;
    public JTextField nameText;
    public JTextField userText;
    public JPasswordField passText;
    public JTextField depText;
    public JButton subButton;
    public JButton resetButton;
    public JComboBox comboDep;
    public JPasswordField passText1;
    private JComboBox comboSem;
    public JComboBox subCombo;
    private JButton backButton;
    private JButton exit;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == subButton) {
            int x = 0;
            String s1 = nameText.getText();

            char[] s2 = passText.getPassword();
            char[] s3 = passText1.getPassword();
            String s4 = new String(s2);
            String s5 = new String(s3);

            String s6 = userText.getText();
            String s7 = comboDep.getSelectedItem().toString();
            String s8 = comboSem.getSelectedItem().toString();
            String s9 = subCombo.getSelectedItem().toString();

            if (s4.equals(s5))
            {
                try {
                    Class.forName("org.sqlite.JDBC");
                    Connection con = DriverManager.getConnection("jdbc:sqlite:src/Facultyreg1.sqlite");
                    PreparedStatement ps = con.prepareStatement("insert into FacultySem4 values(?,?,?,?,?,?,?)");
                    ps.setString(1, s1);
                    ps.setString(2, s6);
                    ps.setString(3, s4);
                    ps.setString(4, s5);
                    ps.setString(5, s7);
                    ps.setString(6, s8);
                    ps.setString(7, s9);
                    int rows = ps.executeUpdate();
                    x++;
                    if (x > 0) {
                        JOptionPane.showMessageDialog(this, "Data Saved Successfully");
                        this.subButton.setEnabled(false);
                      //  StudentProfile profile = new StudentProfile();
                    }
                }catch (Exception ex) {
                    System.out.println(ex);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Password Does Not Match");
                this.passText.setText("");
                this.passText1.setText("");

            }
        }
        if(e.getSource() == resetButton){

            this.userText.setText(" ");
            this.nameText.setText(" ");
            this.passText.setText("");
            this.passText1.setText("");
            this.comboDep.setSelectedItem("");
            this.comboSem.setSelectedItem("");
            this.subCombo.setSelectedItem("");
            this.subButton.setEnabled(true);

        }
        else
            if(e.getSource()==this.backButton){
                this.setVisible(false);
            }
        else
                if(e.getSource() ==this.exit){
                    System.exit(0);
                }

    }

    public FacultyReg(){
        super("Faculty Registration");

        this.subButton.addActionListener(this);
        this.resetButton.addActionListener(this);
        this.backButton.addActionListener(this);
        this.exit.addActionListener(this);

        this.setContentPane(panel1);
        this.setVisible(true);
        this.pack();

    }

}
