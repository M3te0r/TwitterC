package twitter.client;

import utils.TaskLoader;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Mathieu on 25/09/2015.
 */
public class CellRenderer extends JPanel implements ListCellRenderer<TweetModel>{

    public CellRenderer(){
        super();
        setOpaque(true);
        setLayout(new FlowLayout(FlowLayout.LEADING));
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends TweetModel> list, TweetModel value, int index, boolean isSelected, boolean cellHasFocus) {
        TweetCellPanel tweetCellPanel = new TweetCellPanel();
        JPanel panel1 = tweetCellPanel.getMainPanel();
        panel1.setOpaque(true);
        tweetCellPanel.setUserTweet("<html><div style=\\\"width:300px;\\\">" + value.getTweetText().replaceAll("\\n", "<br>") + "</div></html>");
        tweetCellPanel.setUserName(value.getName());
        tweetCellPanel.setUserScreenName('@' + value.getScreenName());
        if(value.getRetweetedBy() != null)
            tweetCellPanel.setRetweetedByLabel(value.getRetweetedBy());
        if(value.getRetweetCount() > 0) tweetCellPanel.setRTCount(value.getRetweetCount());
        if (value.getFavoriteCount() > 0) tweetCellPanel.setFavCount(value.getFavoriteCount());
        if (!value.isImageLoaded()){
            CompletableFuture.supplyAsync(() -> TaskLoader.downloadImageFromString(value.getUserURLProfilIcon())).thenAccept(a -> {
                if (a != null) {
                    value.setImageLoaded();
                    value.setUserTweetIcon(a);
                    tweetCellPanel.setUserProfilePicture(a);
                    ((DefaultListModel<TweetModel>) list.getModel()).setElementAt(value, index);
                }
            });
        }
        else tweetCellPanel.setUserProfilePicture(value.getUserTweetIcon());
        return panel1;
    }
}
//mat.pequin@gmail.com