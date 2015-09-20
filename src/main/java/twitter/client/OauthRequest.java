package twitter.client;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;
import org.w3c.dom.NodeList;
import javax.swing.*;

/**
 * Created by Mathieu on 20/09/2015.
 */
public class OauthRequest extends JFrame {
    private JPanel panel1;
    private JFXPanel jfpanel2;
    private WebEngine webEngine;
    private WebView webBrowser;
    private Token requestToken;
    private Token accessToken;
    private AnchorPane anchorPane;
    private OAuthService service;
    private static final String REST_API_URL = "https://api.twitter.com/1.1/";
    private static final String ACCOUNT_VERIF = "account/verify_credentials.json";

    public OauthRequest() {
        super("Twitter Authorization");
        setContentPane(panel1);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        createTokenRequest();
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
                        accessToken = service.getAccessToken(requestToken, new Verifier(a.item(0).getTextContent()));
                        OAuthRequest request = new OAuthRequest(Verb.GET, REST_API_URL + ACCOUNT_VERIF);
                        service.signRequest(accessToken, request);
                        Response response = request.send();
                        System.out.println(response.getCode());
                        System.out.println(response.getBody());
                    }
                }
            });

        });
    }

    public void createTokenRequest(){
        service = new ServiceBuilder()
        .provider(TwitterApi.class)
        .apiKey("api key here")
        .apiSecret("secret api here")
        .build();
        requestToken = service.getRequestToken();
        String authUrl = service.getAuthorizationUrl(requestToken);
        loadPage(authUrl);
    }

//    public String makeOauthRequest(String ressourceURI){
//
//    }
}
