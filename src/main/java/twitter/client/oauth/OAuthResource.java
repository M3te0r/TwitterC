package twitter.client.oauth;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;
import twitter.client.OauthRequest;

/**
 * Created by Mathieu on 20/09/2015.
 */
public class OAuthResource {

    private Token accessToken;
    private Token requestToken;
    private OAuthService service;

    private static final String REST_API_URL = "https://api.twitter.com/1.1/";
    private static final String ACCOUNT_VERIF = "account/verify_credentials.json";
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

    public void requestAccessToken(){
        requestToken = service.getRequestToken();
        String authUrl = service.getAuthorizationUrl(requestToken);
        new OauthRequest().loadPage(authUrl);
    }

    public Token getRequestToken() {
        return requestToken;
    }

    public Token getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(Token accessToken) {
        this.accessToken = accessToken;
    }
    public void createAccessToken(String code){
        accessToken = service.getAccessToken(requestToken, new Verifier(code));
        OAuthRequest request = new OAuthRequest(Verb.GET, REST_API_URL + ACCOUNT_VERIF);
        service.signRequest(accessToken, request);
        Response response = request.send();
        System.out.println(response.getCode());
        System.out.println(response.getBody());
    }
}
