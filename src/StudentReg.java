import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Charmy Garg on 2/23/2016.
 */
public class StudentReg extends JFrame implements ActionListener {
    public JPanel panel1;
    public JTextField mailText;
    public JTextField countryText;
    public JTextField stateText;
    public JTextField phoneText;
    public JButton subButton;
    public JButton resetButton;
    public JTextField nameText;
    public JTextField userText;
    public JPasswordField passText;
    public JPasswordField confirmText;


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
            String s9 = countryText.getText();
            int s10 = phoneText.getX();

            if (s4.equals(s5))
            {
                x++;
                if (x > 0)
                {
                    JOptionPane.showMessageDialog(this, "Data Saved Successfully");
                    this.subButton.setEnabled(false);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Password Does Not Match");

            }
        }
        if(e.getSource() == resetButton){

            this.userText.setText(" ");
            this.nameText.setText(" ");
            this.passText.setText("");
            this.confirmText.setText("");
            this.countryText.setText(" ");
            this.phoneText.setText(" ");
            this.mailText.setText(" ");
            this.stateText.setText(" ");
            this.subButton.setEnabled(true);

        }
    }

    public StudentReg(){

        super("Student Registration");

        this.subButton.addActionListener(this);
        this.resetButton.addActionListener(this);

        this.setContentPane(panel1);
        this.setVisible(true);
        this.pack();
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

