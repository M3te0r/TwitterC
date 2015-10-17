package twitter.client;

import javax.jws.soap.SOAPBinding;
import javax.swing.*;
import java.awt.*;

public class TweetCellPanel {
    private JLabel UserProfilePicture;
    private JPanel userNamesPanel;
    private JLabel userTweet;
    private JPanel detailsPanel;
    private JLabel userScreenName;
    private JLabel userProfileAddress;
    private JPanel mainPanel;

    public TweetCellPanel(){
        mainPanel.setIgnoreRepaint(true);
        UserProfilePicture.setIcon(new ImageIcon(getClass().getResource("/icons/blank_pp.png")));
    }

    public JPanel getMainPanel()
    {
        return mainPanel;
    }

    public void setUserTweet(String userTweet){
        this.userTweet.setText(userTweet);
    }

    public void setUserProfilePicture(ImageIcon userProfilePicture){
        this.UserProfilePicture.setIcon(userProfilePicture);
    }

    public void setUserScreenName(String userScreenName){
        this.userScreenName.setText(userScreenName);
    }

    public void setUserProfileAddress(String userProfileAddress){
        this.userProfileAddress.setText(userProfileAddress);
    }
}
