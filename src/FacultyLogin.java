import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Charmy Garg on 3/1/2016.
 */
public class FacultyLogin extends JFrame implements ActionListener{

    public JPanel panel1;
    public JComboBox comboButton;
    public JTextField nameText;
    public JTextField userText;
    public JButton subButton;
    public JButton resetButton;
    public JButton registerButton;

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button1 = (JButton)e.getSource();

        if(button1 == this.registerButton)
        {
            this.registerButton.setEnabled(false);
            FacultyReg reg = new FacultyReg();

        }
    }

    public FacultyLogin()
    {
        super("Faculty Login");
       // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.subButton.addActionListener(this);
        this.resetButton.addActionListener(this);
        this.registerButton.addActionListener(this);
        this.setContentPane(panel1);
        this.pack();
        this.setVisible(true);
    }

}
