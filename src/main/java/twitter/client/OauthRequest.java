package twitter.client;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;
import org.w3c.dom.NodeList;
import twitter.client.oauth.OAuthResource;
import javax.swing.*;

/**
 * Created by Mathieu on 20/09/2015.
 */
public class OauthRequest extends JFrame {
    private JPanel panel1;
    private JFXPanel jfpanel2;
    private WebEngine webEngine;
    private WebView webBrowser;
    private AnchorPane anchorPane;
    public OauthRequest() {
        super();
        setContentPane(panel1);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    public void loadPage(String urlPage){
        Platform.runLater(() -> {
            webBrowser = new WebView();
            webEngine = webBrowser.getEngine();
            anchorPane = new AnchorPane();
            anchorPane.getChildren().add(webBrowser);
            final Scene scene = new Scene(anchorPane);
            jfpanel2.setScene(scene);
            webEngine.load(urlPage);
            webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) ->
            {
                if (newValue == Worker.State.SUCCEEDED) {
                    org.w3c.dom.Document doc = webEngine.getDocument();
                    NodeList a = doc.getDocumentElement().getElementsByTagName("code");
                    if (a.getLength() == 1){
                        OAuthResource oAuthResource = OAuthResource.getInstance();
                        oAuthResource.createAccessToken(a.item(0).getTextContent());
                    }
                }
            });

        });
    }
}
