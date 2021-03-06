package twitter.client.rest;

import com.sun.istack.internal.Nullable;
import org.scribe.model.Response;
import twitter.client.oauth.OAuthResource;

import java.util.HashMap;

/**
 * Created by Mathieu on 21/09/2015.
 */
public class TwitterRest {

    private final OAuthResource authResource;
    private static final String ACCOUNT_VERIF = "account/verify_credentials.json";
    private static final String USER_TIMELINE = "statuses/user_timeline.json";
    private static final String HOME_TIMELINE ="statuses/home_timeline.json";
    private static final String POST_STATUS = "statuses/update.json";

    public TwitterRest(){
        this.authResource = OAuthResource.getInstance();
    }

    public String getHomeTimeline(@Nullable Long idToLoad, @Nullable Long idMax){
        if (idToLoad != null){
            return authResource.makeGETRequest(HOME_TIMELINE, new HashMap<String, String>(){{put("count", "100"); put("since_id", idToLoad.toString());}}).getBody();
        }
        else if (idMax != null){
            return authResource.makeGETRequest(HOME_TIMELINE, new HashMap<String, String>(){{put("count", "100"); put("max_id", String.valueOf(idMax - 1));}}).getBody();
        }
        return authResource.makeGETRequest(HOME_TIMELINE, new HashMap<String, String>(){{put("count", "100");}}).getBody();
    }

    public String getUserTweets(@Nullable Long idToLoad, @Nullable Long idMax){
        if(idToLoad != null){
            return authResource.makeGETRequest(USER_TIMELINE, new HashMap<String, String>(){{put("count", "100"); put("since_id", idToLoad.toString());}}).getBody();
        }
        else if (idMax != null)
        {
            return authResource.makeGETRequest(USER_TIMELINE, new HashMap<String, String>(){{put("count", "100"); put("max_id", String.valueOf(idMax - 1));}}).getBody();
        }
        return authResource.makeGETRequest(USER_TIMELINE, new HashMap<String, String>(){{put("count", "100");}}).getBody();
    }

    public String postTweetMessage(String tweetMessage){
        Response response = authResource.makePOSTRequest(POST_STATUS, new HashMap<String, String>(){{put("status", tweetMessage);}});
        return response.getCode() == 200 ? response.getBody() : null;
    }

    public Response getUserInformationsUponAuth()
    {
        return authResource.makeGETRequest(ACCOUNT_VERIF, null);
    }
}
