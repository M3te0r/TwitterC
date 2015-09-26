package twitter.client;

import javax.swing.*;

/**
 * Created by Mathieu on 25/09/2015.
 */
public class TweetModel {

    private String tweetText;
    Icon userTweetIcon;

    public TweetModel(String tweetText) {
        this.tweetText = tweetText;
    }

    public String getTweetText() {
        return tweetText;
    }

    public Icon getUserTweetIcon() {
        return userTweetIcon;
    }
}
