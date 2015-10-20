package utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.scribe.model.Response;
import twitter.client.TweetModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mathieu on 18/10/2015.
 */
public class TweetParser {
    public static List<TweetModel> getTweets(Response response) {

        List<TweetModel> list = new ArrayList<>();
        JSONArray array = new JSONArray(response.getBody());
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            if (object.getBoolean("retweeted")) {
                JSONObject rted = object.getJSONObject("retweeted_status");
                String rtedText = rted.getString("text");
                JSONObject user = rted.getJSONObject("user");
                String userProfileURL = user.getString("profile_image_url_https");
                String screenName = user.getString("screen_name");
                String userName = user.getString("name");
                TweetModel tweetModel = new TweetModel(rtedText, userProfileURL, screenName, userName, object.getLong("id"), object.getString("created_at"));

            } else {
                JSONObject userObject = object.getJSONObject("user");
                String urlProfilePicture = userObject.getString("profile_image_url_https");
                String twee = object.getString("text");
                String userScreenName = userObject.getString("screen_name");
                String userName = userObject.getString("name");
                TweetModel tweetModel = new TweetModel(twee, urlProfilePicture, userScreenName, userName, object.getLong("id"), object.getString("created_at"));
            }



        }
        return list;
    }
}
