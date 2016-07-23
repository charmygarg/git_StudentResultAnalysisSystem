import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Created by Charmy Garg on 2/23/2016.
 */
public class StudentReg extends JFrame implements ActionListener {
    public JPanel panel1;
    public JTextField mailText;
    public JTextField cityText;
    public JTextField stateText;
    public JTextField phoneText;
    public JButton subButton;
    public JButton resetButton;
    public JTextField nameText;
    public JTextField userText;
    public JPasswordField passText;
    public JPasswordField confirmText;
    private JComboBox comboDep;
    private JComboBox comboSem;
    private JButton backButton;
    private JButton exit;


    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == subButton) {
            int x = 0;
            String s1 = nameText.getText();

            char[] s2 = passText.getPassword();
            char[] s3 = confirmText.getPassword();
            String s4 = new String(s2);
            String s5 = new String(s3);

            String s6 = userText.getText();
            String s7 = stateText.getText();
            String s8 = mailText.getText();
            String s9 = cityText.getText();
            String s10 = phoneText.getText();
            String s11 = comboDep.getSelectedItem().toString();
            String s12 = comboSem.getSelectedItem().toString();

            if (s4.equals(s5))
            {
                try {
                    Class.forName("org.sqlite.JDBC");
                    Connection con = DriverManager.getConnection("jdbc:sqlite:src/StudentReg.sqlite");
                    PreparedStatement ps = con.prepareStatement("INSERT into StudentSem4 values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setString(1, s1);
                    ps.setString(2, s8);
                    ps.setString(3, s6);
                    ps.setString(4, s4);
                    ps.setString(5, s5);
                    ps.setString(6, s9);
                    ps.setString(7, s7);
                    ps.setString(8, s10);
                    ps.setString(9, s11);
                    ps.setString(10, s12);
                    ps.executeUpdate();
//                    int rows = ps.executeUpdate();
                    x++;
                    if (x > 0) {
                        JOptionPane.showMessageDialog(this, "Data Saved Successfully");
                        this.subButton.setEnabled(false);

                    }
                } catch (NumberFormatException e1){

                } catch (Exception ex){

                }
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Password Does Not Match");
                this.passText.setText("");
                this.confirmText.setText("");

            }

        }
        if(e.getSource() == resetButton){

            this.userText.setText(" ");
            this.nameText.setText(" ");
            this.passText.setText("");
            this.confirmText.setText("");
            this.cityText.setText(" ");
            this.phoneText.setText(" ");
            this.mailText.setText(" ");
            this.stateText.setText(" ");
            this.comboSem.setSelectedItem(" ");
            this.comboDep.setSelectedItem(" ");
            this.subButton.setEnabled(true);
            this.nameText.setFocusable(true);

        }
        else
            if(e.getSource()==this.backButton){
                this.setVisible(false);
            }
        else if(e.getSource()==this.exit){
                System.exit(0);
            }
    }

    public StudentReg(){

        super("Student Registration");

        this.subButton.addActionListener(this);
        this.resetButton.addActionListener(this);
        this.exit.addActionListener(this);
        this.backButton.addActionListener(this);
        this.setContentPane(panel1);
        this.setVisible(true);
        this.pack();
      //  this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

