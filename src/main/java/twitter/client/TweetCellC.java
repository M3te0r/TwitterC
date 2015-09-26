package twitter.client;

import javax.swing.*;

/**
 * Created by Mathieu on 26/09/2015.
 */
public class TweetCellC extends JLabel {

    public TweetCellC() {
    }

    public void setUserTweet(String text) {
        setText(text);
    }
}