import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Created by Charmy Garg on 3/1/2016.
 */
public class FrontScreen extends JFrame  implements ActionListener{
    public JPanel mainpanel;
    public JButton adminButton;
    public JButton facultyButton;
    public JButton studentButton;
    public JLabel iconLabel;

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton clickedButton = (JButton)e.getSource();
        if(clickedButton == this.adminButton)
        {

            AdminLogin login1 = new AdminLogin();
        }
        else if(clickedButton == this.studentButton)
        {

            StudentLogin login1 = new StudentLogin();
        }
        else if(clickedButton == this.facultyButton)
        {

            FacultyLogin reg1 = new FacultyLogin();
        }

    }

    public FrontScreen(){
        super("Front Screen");

        this.setContentPane(mainpanel);
        this.pack();
        this.setVisible(true);

    }


}
