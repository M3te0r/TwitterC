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
    private String internalId;

    public TweetModel(String tweetText, String userURLProfilIcon, String screenName, String internalId) {
        this.tweetText = tweetText;
        this.screenName = screenName;
        this.imageLoaded = false;
        this.userURLProfilIcon = userURLProfilIcon;
        this.internalId = internalId;
    }

    public String getInternalId() {
        return internalId;
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
