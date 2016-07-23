import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Charmy Garg on 13-Apr-16.
 */
public class Manage extends JFrame implements ActionListener {
    private JPanel panel1;
    private JButton facButton;
    private JButton studentButton;
    private JButton backButton;
    Manage manage;

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==this.backButton){
            this.setVisible(false);
        }
        else if(e.getSource()==this.facButton){
            this.setVisible(false);
            FacultyManage manage = new FacultyManage();
        }
        else if(e.getSource()==this.studentButton){
            this.setVisible(false);
            StudentProfile profile = new StudentProfile();
        }

    }

    public Manage(){
        super("Manage");

        this.backButton.addActionListener(this);
        this.facButton.addActionListener(this);
        this.studentButton.addActionListener(this);
        this.setPreferredSize(new Dimension(4500,4000));

        this.setContentPane(panel1);
        this.pack();
        this.setVisible(true);

    }
}
