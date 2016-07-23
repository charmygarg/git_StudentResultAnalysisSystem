import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * Created by Charmy Garg on 3/1/2016.
 */
public class StudentLogin extends JFrame implements ActionListener {
    public JPanel panel1;
    public JTextField userText;
    public JButton submitButton;
    public JButton resetButton;
    public JButton registerButton;
    public JPasswordField passText;
    private JComboBox comboDep;
    public JComboBox comboSem;
    private JButton backButton;
    public String us;
    public String password;
    public String sem;
    public boolean flag = false;

    ResultSet rs;

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton button = (JButton) e.getSource();
        if (button == this.submitButton) {

            try {

                Class.forName("org.sqlite.JDBC");
                Connection con = DriverManager.getConnection("jdbc:sqlite:src/StudentReg.sqlite");

                Statement st = con.createStatement();
                String user = userText.getText();
                char[] pass = passText.getPassword();
                String comboS = comboSem.getSelectedItem().toString();
                String pass1 = new String(pass);
                ResultSet rs = st.executeQuery("SELECT * from StudentSem4");

                while (rs.next()) {

                    us = rs.getString(3);
                    password = rs.getString(4);
                    //sem = rs.getString(6);

                    if (us.equals(user) && password.equals(pass1)) {
                        this.flag = true;
                        this.display();
                        break;

                    }
                }

                con.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }

        } else if (button == this.registerButton) {
            this.reg();
        } else if (button == this.resetButton) {
            this.userText.setText("");
            this.passText.setText("");
            this.comboSem.setSelectedItem("");
        } else if (button == this.backButton) {
            this.setVisible(false);
        }

    }

    public void reg() {
        this.registerButton.setEnabled(false);
        StudentReg screen = new StudentReg();
    }

    public StudentLogin() {
        super("Student Login");
        this.submitButton.addActionListener(this);
        this.registerButton.addActionListener(this);
        this.resetButton.addActionListener(this);
        this.backButton.addActionListener(this);
        this.setContentPane(this.panel1);
        this.pack();
        this.setVisible(true);

    }

    public void display() {
        if (flag == true) {
            JOptionPane.showMessageDialog(this, "Successfully login");
            this.userText.setText("");
            this.passText.setText("");
            StudentAccess access = new StudentAccess();

        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or Password");
        }
    }
}
