package twitter.client.oauth;

import com.sun.istack.internal.Nullable;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;
import twitter.client.OauthRequest;
import twitter.client.TwitterW;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mathieu on 20/09/2015.
 */
public class OAuthResource {

    private Token accessToken;
    private Token requestToken;
    private final OAuthService service;

    private static final String REST_API_URL = "https://api.twitter.com/1.1/";
    private static class SingletonHandler{
        private static final OAuthResource instance = new OAuthResource();
    }

    public static OAuthResource getInstance(){
        return SingletonHandler.instance;
    }

    private OAuthResource(){
        service = new ServiceBuilder()
                .provider(TwitterApi.class)
                .apiKey("api key here")
                .apiSecret("secret api here")
                .build();
    }

    public void requestAccessToken(JFrame frame){
        requestToken = service.getRequestToken();
        String authUrl = service.getAuthorizationUrl(requestToken);
        new OauthRequest().loadPage(authUrl, frame);
    }

    public Token getRequestToken() {
        return requestToken;
    }

    public Token getAccessToken() {
        return accessToken;
    }

    /**
     *
     * @param resourceUri Distant ressource URI completing API Host
     * @param parameters
     * @return String representation of returned JSON
     *
     * Call in AsyncTask
     */
    public Response makeGETRequest(String resourceUri,@Nullable Map<String, String> parameters){
        OAuthRequest request = new OAuthRequest(Verb.GET, REST_API_URL + resourceUri);
        if (parameters != null) parameters.forEach(request::addQuerystringParameter);
        service.signRequest(accessToken, request);
        return request.send();
    }

    /* POST form encoded ?*/

    /**
     *
     * @param resourceUri
     * @param parameters
     * @return
     * //Call in AsyncTask
     */
    public Response makePOSTRequest(String resourceUri, Map<String, String> parameters){
        OAuthRequest request = new OAuthRequest(Verb.POST, REST_API_URL + resourceUri);
        parameters.forEach(request::addQuerystringParameter);
        service.signRequest(accessToken, request);
        return request.send();
    }

    public void createAccessToken(String code){
        accessToken = service.getAccessToken(requestToken, new Verifier(code));
    }
}
