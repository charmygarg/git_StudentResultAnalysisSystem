import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    AdminReg reg;

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton button = (JButton) e.getSource();
        if(button == this.registerButton)
        {
            this.registerButton.setEnabled(false);
            AdminReg reg = new AdminReg();

        }

    }
    public AdminLogin()
    {
        super("Admin Login Form");

        this.subButton.addActionListener(this);
        this.registerButton.addActionListener(this);
        this.resetButton.addActionListener(this);

        this.setContentPane(this.panel1);
        this.setVisible(true);
        this.pack();
    }
}
