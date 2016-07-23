import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Charmy Garg on 3/1/2016.
 */
public class AdminLogin extends JFrame implements ActionListener {
    public JPanel panel1;
    public JTextField userText;
    public JButton subButton;
    public JButton resetButton;
    public JButton registerButton;
    private JPasswordField passText;
    private JButton backButton;
    public boolean flag=false;

    AdminReg reg;

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton button = (JButton) e.getSource();
        if(button == this.registerButton)
        {
            this.registerButton.setEnabled(false);
            AdminReg reg = new AdminReg();

        }else
        if(e.getSource()==this.backButton){
            this.setVisible(false);

        }
        else
        if(button == this.subButton) {

            try {
                Class.forName("org.sqlite.JDBC");
                Connection con = DriverManager.getConnection("jdbc:sqlite:src/Registration.sqlite");
                Statement st = con.createStatement();
                String user = userText.getText();
                char[] pass = passText.getPassword();
                String pass1 = new String(pass);
                ResultSet rs = st.executeQuery("SELECT * from Registration ");

                while (rs.next()) {

                    String us = rs.getString("Username");
                    String password = rs.getString("Password1");

                    if (us.equals(user) && password.equals(pass1)) {
                        this.flag = true;
                        display();
                        break;
                    }
                    con.close();
                }
            }

            catch (Exception ex) {
                System.out.println(ex);
            }
        }
            else if(button == this.resetButton){

                this.userText.setText("");
                this.passText.setText("");

            }

    }
    public AdminLogin()
    {
        super("Admin Login");

        this.subButton.addActionListener(this);
        this.registerButton.addActionListener(this);
        this.resetButton.addActionListener(this);
        this.backButton.addActionListener(this);

        this.setContentPane(this.panel1);
        this.setDefaultCloseOperation(1);
        this.setVisible(true);
        this.pack();

    }

    public void display(){
        if(flag==true){
        JOptionPane.showMessageDialog(this,"Successfully login");
        this.userText.setText("");
        this.passText.setText("");
        Manage mange=new Manage();

    }else{
        JOptionPane.showMessageDialog(this,"Invalid username or Password");
    }
    }
}
