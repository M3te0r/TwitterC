package twitter.client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Mathieu on 19/09/2015.
 */
public class TwitterW extends JFrame {
    private JPanel rootPanel;
    private JButton button1;
    private JButton NEWTWEETButton;
    private JPanel leftDockPanel;
    private JPanel leftDockNewTweetPanel;

    public TwitterW(){
        super("Twitter Client");

        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leftDockNewTweetPanel.setVisible(true);
            }
        });
    }
}
