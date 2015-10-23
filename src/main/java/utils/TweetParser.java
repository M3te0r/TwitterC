package utils;

import org.json.JSONArray;
import org.json.JSONObject;
import twitter.client.TweetModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mathieu on 18/10/2015.
 */
public class TweetParser {

    public static TweetModel parseSingle(String response){
        return getTweet(new JSONObject(response));
    }

    public static List<TweetModel> parseMultiple(String response){
        if(response != null){
            JSONArray array = new JSONArray(response);
            if (array.length() > 0)
            {
                List<TweetModel> tweets = new ArrayList<>();
                for (int i = 0; i < array.length(); i++)
                {
                    tweets.add(getTweet(array.getJSONObject(i)));
                }
                return tweets;
            }
        }
        return null;
    }

    private static TweetModel getTweet(JSONObject object){
        if (object.getBoolean("retweeted")){
            JSONObject rted = object.getJSONObject("retweeted_status");
            String rtedText = rted.getString("text");
            JSONObject user = rted.getJSONObject("user");
            String userProfileURL = user.getString("profile_image_url_https");
            String screenName = user.getString("screen_name");
            String userName = user.getString("name");
            int rt = rted.getInt("retweet_count");
            int fav = rted.getInt("favorite_count");
            return new TweetModel(rtedText, userProfileURL, screenName, userName, object.getLong("id"), rted.getString("created_at"), rt, fav, null);
        }
        else if (object.has("retweeted_status")){
            JSONObject rted = object.getJSONObject("retweeted_status");
            String rtedText = rted.getString("text");
            JSONObject user = rted.getJSONObject("user");
            String userProfileURL = user.getString("profile_image_url_https");
            String screenName = user.getString("screen_name");
            String userName = user.getString("name");
            int rt = rted.getInt("retweet_count");
            int fav = rted.getInt("favorite_count");
            String userRT = object.getJSONObject("user").getString("name");
            return new TweetModel(rtedText, userProfileURL, screenName, userName, object.getLong("id"), rted.getString("created_at"), rt, fav, userRT);
        }
        else {
            JSONObject userObject = object.getJSONObject("user");
            String urlProfilePicture = userObject.getString("profile_image_url_https");
            String twee = object.getString("text");
            String userScreenName = userObject.getString("screen_name");
            String userName = userObject.getString("name");
            int fav = object.getInt("favorite_count");
            int rt = object.getInt("retweet_count");
            return new TweetModel(twee, urlProfilePicture, userScreenName, userName, object.getLong("id"), object.getString("created_at"), rt, fav, null);
        }
    }
}
