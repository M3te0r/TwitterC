package twitter.client;

import utils.TaskLoader;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Mathieu on 25/09/2015.
 */
public class CellRenderer extends JPanel implements ListCellRenderer<TweetModel>{
    private TweetCellPanel tweetCellPanel;
    private JPanel panel;

    public CellRenderer(){
        super();
        tweetCellPanel = new TweetCellPanel();
        setOpaque(true);
        setLayout(new FlowLayout(FlowLayout.LEADING));
        panel = tweetCellPanel.getMainPanel();
        add(panel);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends TweetModel> list, TweetModel value, int index, boolean isSelected, boolean cellHasFocus) {
        tweetCellPanel.setUserTweet(value.getTweetText());
        tweetCellPanel.setUserName(value.getName());
        tweetCellPanel.setUserScreenName('@' + value.getScreenName());
        if (!value.isImageLoaded()){
            CompletableFuture.supplyAsync(() -> TaskLoader.downloadImageFromString(value.getUserURLProfilIcon())).thenAccept(a -> {
                value.setImageLoaded(true);
                value.setUserTweetIcon(a);
                tweetCellPanel.setUserProfilePicture(a);
                ((DefaultListModel) list.getModel()).setElementAt(value, index);
            });
        }
        else tweetCellPanel.setUserProfilePicture(value.getUserTweetIcon());
        return this;
    }
}
//mat.pequin@gmail.com