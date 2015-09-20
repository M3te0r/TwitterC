package twitter.client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Mathieu on 20/09/2015.
 */
public class StartupGUI extends JFrame {
    private JPanel panel1;
    private JButton seConnecterButton;

    public StartupGUI() {
        super("Home");

        setContentPane(panel1);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        seConnecterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OauthRequest();
            }
        });
    }
}
