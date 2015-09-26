package twitter.client.rest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.scribe.model.Response;
import twitter.client.oauth.OAuthResource;

/**
 * Created by Mathieu on 21/09/2015.
 */
public class TwitterRest {

    private OAuthResource authResource;
    private static final String ACCOUNT_VERIF = "account/verify_credentials.json";
    private static final String USER_TIMELINE = "statuses/user_timeline.json";
    private static final String FRIENDS_TIMELINE ="";


    public TwitterRest(){
        this.authResource = OAuthResource.getInstance();
    }

    public Response getUserTweets(){
        return authResource.makeGETRequest(USER_TIMELINE, null);
    }

    public Response getUserInformationsUponAuth()
    {
        return authResource.makeGETRequest(ACCOUNT_VERIF, null);

    }


}
