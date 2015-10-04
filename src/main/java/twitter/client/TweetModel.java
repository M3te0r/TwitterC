package twitter.client;

import com.sun.istack.internal.Nullable;

import javax.swing.*;

/**
 * Created by Mathieu on 25/09/2015.
 */
public class TweetModel {

    private String tweetText;
    private ImageIcon userTweetIcon;



    public TweetModel(String tweetText) {
        this.tweetText = tweetText;
//        this.userTweetIcon = new ImageIcon(TweetModel.class.getResource("/icons/blank_pp.png"));
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
}
