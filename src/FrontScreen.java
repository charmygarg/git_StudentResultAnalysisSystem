import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Charmy Garg on 3/1/2016.
 */
public class FrontScreen implements ActionListener{
    public JPanel mainpanel;
    public JButton adminButton;
    public JButton facultyButton;
    public JButton studentButton;


    @Override
    public void actionPerformed(ActionEvent e) {

        JButton clickedButton = (JButton)e.getSource();
        if(clickedButton == this.adminButton)
        {
            this.login();
        }
        else if(clickedButton == this.studentButton)
        {
            this.login1();
        }
        else if(clickedButton == this.facultyButton) {
            this.facultyLogin();
        }

    }

    public void login()
    {
        this.adminButton.setEnabled(false);

        AdminLogin login1 = new AdminLogin();

    }

    public void login1(){
        this.studentButton.setEnabled(false);
        StudentLogin login1 = new StudentLogin();

    }

    public void facultyLogin()
    {
        this.facultyButton.setEnabled(false);
        FacultyLogin reg1 = new FacultyLogin();
    }
}
