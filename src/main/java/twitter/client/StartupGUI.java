package twitter.client;
import twitter.client.oauth.OAuthResource;
import javax.swing.*;

/**
 * Created by Mathieu on 20/09/2015.
 */
public class StartupGUI extends JFrame {
    private JPanel panel1;
    private JButton seConnecterButton;

    public StartupGUI() {
        super("Home");

        OAuthResource resource = OAuthResource.getInstance();
        setContentPane(panel1);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        seConnecterButton.addActionListener(e -> resource.requestAccessToken(this));
    }
}
