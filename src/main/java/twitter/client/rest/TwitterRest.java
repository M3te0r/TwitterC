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


    public TwitterRest(){
        this.authResource = OAuthResource.getInstance();
    }

    public Response getHomeTimeline(@Nullable Long idToLoad, @Nullable Long idMax){
        if (idToLoad != null){
            return authResource.makeGETRequest(HOME_TIMELINE, new HashMap<String, String>(){{put("count", "300"); put("since_id", idToLoad.toString());}});
        }
        else if (idMax != null){
            return authResource.makeGETRequest(HOME_TIMELINE, new HashMap<String, String>(){{put("count", "300"); put("max_id", idMax.toString());}});
        }
        else return authResource.makeGETRequest(HOME_TIMELINE, new HashMap<String, String>(){{put("count", "300");}});
    }

    public Response getUserTweets(){
        return authResource.makeGETRequest(USER_TIMELINE, new HashMap<String, String>(){{put("count", "300");}});
    }

    public Response getUserInformationsUponAuth()
    {
        return authResource.makeGETRequest(ACCOUNT_VERIF, null);
    }
}
