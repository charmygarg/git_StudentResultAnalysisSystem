import javax.swing.*;

/**
 * Created by Charmy Garg on 3/5/2016.
 */
public class StudentProfile extends JFrame {
    private JPanel panel1;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JList list1;

    public StudentProfile(){
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setContentPane(panel1);

    }
}
