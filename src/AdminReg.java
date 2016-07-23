import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Charmy Garg on 3/1/2016.
 */
public class AdminReg extends JFrame implements ActionListener {
    public JButton submitButton;
    public JPanel panel1;
    public JButton resetButton;
    public JTextField nameText;
    public JPasswordField passText;
    public JTextField userText;
    public JPasswordField passText1;
    private JButton backButton;
    private JButton exit;
    public String s1;
    public String s4;
    public String s5;
    public String s6;

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == submitButton) {
            int x = 0;
            s1 = nameText.getText();

            char[] s2 = passText.getPassword();
            char[] s3 = passText1.getPassword();
            s4 = new String(s2);
            s5 = new String(s3);

            s6 = userText.getText();

            if (s4.equals(s5)) {
                try {
                    Class.forName("org.sqlite.JDBC");
                    Connection con = DriverManager.getConnection("jdbc:sqlite:src/Registration.sqlite");
                    PreparedStatement ps = con.prepareStatement("insert into Registration values(?,?,?,?)");
                    ps.setString(1, s1);
                    ps.setString(2, s4);
                    ps.setString(3, s5);
                    ps.setString(4, s6);
                    int rows = ps.executeUpdate();

                    x++;
                    if (x > 0) {
                        JOptionPane.showMessageDialog(this, "Data Saved Successfully");

                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Password Does Not Match");
                this.passText.setText("");
                this.passText1.setText("");

            }
        } else {
            userText.setText("");
            passText1.setText("");
            passText1.setText("");
            nameText.setText("");
        }

        if (e.getSource() == resetButton) {

            this.userText.setText(" ");
            this.nameText.setText(" ");
            this.passText.setText("");
            this.passText1.setText("");
            this.submitButton.setEnabled(true);

        }
        else if (e.getSource() == backButton) {
            this.setVisible(false);
        }
        else if(e.getSource()==exit){
            System.exit(0);
        }
    }

    public AdminReg() {
        super(" Admin Registration");

        this.submitButton.addActionListener(this);
        this.resetButton.addActionListener(this);
        this.backButton.addActionListener(this);
        this.exit.addActionListener(this);

        this.setContentPane(panel1);
        this.pack();
        this.setVisible(true);

    }

}


