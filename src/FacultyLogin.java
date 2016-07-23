import com.sun.org.apache.xpath.internal.SourceTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * Created by Charmy Garg on 3/1/2016.
 */
public class FacultyLogin extends JFrame implements ActionListener {

    public JPanel panel1;
    public JComboBox comboButton;
    public JTextField nameText;
    public JButton subButton;
    public JButton resetButton;
    public JButton registerButton;
    public JPasswordField passText;
    private JComboBox comboSem;
    private JButton backButton;
    public String us;
    public String password;
    public String sem;
    public boolean flag = false;


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button1 = (JButton) e.getSource();


        if (button1 == this.registerButton) {
            this.registerButton.setEnabled(false);
            FacultyReg reg = new FacultyReg();

        } else if (button1 == this.subButton) {

            try {
                Class.forName("org.sqlite.JDBC");
                Connection con = DriverManager.getConnection("jdbc:sqlite:src/Facultyreg1.sqlite");
                Statement st = con.createStatement();
                String user = nameText.getText();
                char[] pass = passText.getPassword();
                String comboD = comboButton.getSelectedItem().toString();
                String comboS = comboSem.getSelectedItem().toString();
                String pass1 = new String(pass);
                ResultSet rs = st.executeQuery("SELECT * from FacultySem4");

                while (rs.next()) {

                    us = rs.getString(2);
                    password = rs.getString(3);
                    sem = rs.getString(6);

                    if (us.equals(user) && password.equals(pass1) && sem.equals(comboS)) {
                        this.flag = true;
                        this.display();
                        break;

                    }
                }

                con.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else if (button1 == this.resetButton) {

            this.nameText.setText("");
            this.passText.setText("");
            this.comboButton.setSelectedItem("");
            this.comboSem.setSelectedItem("");
        }
        else if(button1==this.backButton){
            this.setVisible(false);
        }
    }

    public FacultyLogin() {
        super("Faculty Login");

        this.subButton.addActionListener(this);
        this.resetButton.addActionListener(this);
        this.registerButton.addActionListener(this);
        this.backButton.addActionListener(this);
        this.setContentPane(panel1);
        this.pack();
        this.setVisible(true);

    }

    public void display() {
        if (flag == true) {
            JOptionPane.showMessageDialog(this, "Successfully login");
            this.nameText.setText("");
            this.passText.setText("");
            StudentSubMarks marks = new StudentSubMarks();

        }
        else {
            JOptionPane.showMessageDialog(this, "Invalid username or Password");
        }
    }
}
