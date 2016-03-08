import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

            if (s4.equals(s5))
            {
                x++;
                if (x > 0)
                {
                    JOptionPane.showMessageDialog(this, "Data Saved Successfully");
                    this.subButton.setEnabled(false);
                    StudentProfile profile = new StudentProfile();
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
            this.subButton.setEnabled(true);

        }

    }

    public FacultyReg(){
        super("Faculty Registration");

        this.subButton.addActionListener(this);
        this.resetButton.addActionListener(this);

        this.setContentPane(panel1);
        this.setVisible(true);
        this.pack();
    }

}
