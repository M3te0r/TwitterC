package twitter.client;

import javax.swing.*;

/**
 * Created by Mathieu on 26/09/2015.
 */
public class TweetCellC extends JLabel {
    public TweetCellC() {
        super();

        setOpaque(true);


    }

    public void setUserTweet(String text) {
        setText(text);
    }

    public void setTweetPorfilePicture(ImageIcon icon){
        setIcon(icon);
    }
}