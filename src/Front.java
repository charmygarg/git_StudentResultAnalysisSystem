import javax.swing.*;

/**
 * Created by Charmy Garg on 3/1/2016.
 */
public class Front {

    public static void main(String[] args) {

        JFrame frame1 = new JFrame("Result");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FrontScreen screen1 = new FrontScreen();
        frame1.setContentPane(screen1.mainpanel);

        screen1.adminButton.addActionListener(screen1);
        screen1.studentButton.addActionListener(screen1);
        screen1.facultyButton.addActionListener(screen1);

        frame1.setVisible(true);
        frame1.pack();
    }
}
