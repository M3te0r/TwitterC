package twitter.client;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mathieu on 25/09/2015.
 */
public class CellRenderer extends TweetCellC implements ListCellRenderer<TweetModel> {


    @Override
    public Component getListCellRendererComponent(JList<? extends TweetModel> list, TweetModel value, int index, boolean isSelected, boolean cellHasFocus) {
//        setUserIcon(value.getUserTweetIcon());

        setUserTweet(value.getTweetText());

        return this;
    }
}
