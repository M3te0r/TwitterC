package twitter.client;

import com.sun.istack.internal.Nullable;

import javax.swing.*;
import java.net.URL;

/**
 * Created by Mathieu on 25/09/2015.
 */
public class TweetModel {

    private final String screenName;
    private final String tweetText;
    private final String userURLProfilIcon;
    private ImageIcon userTweetIcon;
    private boolean imageLoaded;

    public TweetModel(String tweetText, String userURLProfilIcon, String screenName) {
        this.tweetText = tweetText;
        this.screenName = screenName;
        this.imageLoaded = false;
        this.userURLProfilIcon = userURLProfilIcon;
    }

    public String getUserURLProfilIcon() {
        return userURLProfilIcon;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setUserTweetIcon(ImageIcon userTweetIcon) {
        this.userTweetIcon = userTweetIcon;
    }

    public String getTweetText() {
        return tweetText;
    }

    public ImageIcon getUserTweetIcon() {
        return userTweetIcon;
    }

    public boolean isImageLoaded() {
        return imageLoaded;
    }

    public void setImageLoaded(boolean imageLoaded) {
        this.imageLoaded = imageLoaded;
    }
}
