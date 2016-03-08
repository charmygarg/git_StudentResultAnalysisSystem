import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Charmy Garg on 3/1/2016.
 */
public class StudentLogin extends JFrame implements ActionListener{
    public JPanel panel1;
    public JTextField userText;
    public JTextField textField2;
    public JButton submitButton;
    public JButton resetButton;
    public JButton registerButton;

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton button = (JButton)e.getSource();
        if(button == this.submitButton)
        {

        }
        else if(button == this.registerButton)
        {
              this.reg();
        }


    }

    public void reg()
    {
        this.registerButton.setEnabled(false);
        StudentReg screen = new StudentReg();
    }
    public StudentLogin()
    {
        super("Student Login");
       // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.submitButton.addActionListener(this);
        this.registerButton.addActionListener(this);
        this.setContentPane(this.panel1);
        this.pack();
        this.setVisible(true);

    }
}
