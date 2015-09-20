package twitter.client;

import javax.swing.*;

/**
 * Created by Mathieu on 19/09/2015.
 */
public class TwitterW extends JFrame {
    private JButton button1;
    private JTextField textField1;
    private JList list1;
    private JPanel rootPanel;

    public TwitterW(){
        super("Twitter Client");

        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
