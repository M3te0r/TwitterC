import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;
import twitter.client.OauthRequest;
import twitter.client.StartupGUI;
import twitter.client.TwitterW;

import javax.crypto.spec.OAEPParameterSpec;
import javax.swing.*;
import java.util.Scanner;

public class Main {




    public static void main(String[] args) {



        try {
            // Set System L&F
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            // handle exception
        }
        SwingUtilities.invokeLater(StartupGUI::new);



}}
