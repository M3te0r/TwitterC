package twitter.client;
import twitter.client.oauth.OAuthResource;
import javax.swing.*;
import java.awt.*;

/**
 * Created by Mathieu on 20/09/2015.
 */
public class StartupGUI extends JFrame {
    private JPanel panel1;
    private JButton seConnecterButton;

    public StartupGUI() {
        super("Home");
        setIconImage(new ImageIcon(StartupGUI.class.getResource("/icons/main_twitterC.png")).getImage());
        OAuthResource resource = OAuthResource.getInstance();
        setBackground(new Color(68,68,72));
        panel1.setBackground(new Color(68,68,72));
        setContentPane(panel1);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        seConnecterButton.addActionListener(e -> {
            seConnecterButton.setEnabled(false);
            resource.requestAccessToken(StartupGUI.this);
        });
    }
}
