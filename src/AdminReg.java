import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == submitButton) {
            int x = 0;
            String s1 = nameText.getText();

            char[] s2 = passText.getPassword();
            char[] s3 = passText1.getPassword();
            String s4 = new String(s2);
            String s5 = new String(s3);

            String s6 = userText.getText();

            if (s4.equals(s5))
            {
                x++;
                if (x > 0)
                {
                    JOptionPane.showMessageDialog(this, "Data Saved Successfully");
                    this.submitButton.setEnabled(false);
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
            this.passText1.setText("");
            this.submitButton.setEnabled(true);

        }

    }

    public AdminReg(){
        super("Admin Registration");

        this.submitButton.addActionListener(this);
        this.resetButton.addActionListener(this);

        this.setContentPane(panel1);
        this.pack();
        this.setVisible(true);
    }
}
