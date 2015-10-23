package twitter.client;
import javax.swing.*;

public class TweetCellPanel {
    private JLabel UserProfilePicture;
    private JPanel userNamesPanel;
    private JLabel userName;
    private JLabel userScreenName;
    private JPanel mainPanel;
    private JLabel userTweet;
    private JLabel retweetCountLabel;
    private JLabel favoriteCountLabel;
    private JLabel retweetedByLabel;

    public TweetCellPanel(){
        userTweet.setFont(userTweet.getFont().deriveFont(14f));
        UserProfilePicture.setIcon(new ImageIcon(getClass().getResource("/icons/blank_pp.png")));
    }

    public void setFavCount(int favCount){
        favoriteCountLabel.setText(String.valueOf(favCount));
    }

    public void setRTCount(int rtCount){
        retweetCountLabel.setText(String.valueOf(rtCount));
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

    public void setUserName(String userName){
        this.userName.setText(userName);
    }

    public void setUserScreenName(String userScreenName){
        this.userScreenName.setText(userScreenName);
    }

    public void setRetweetedByLabel(String user){
        retweetedByLabel.setVisible(true);
        retweetedByLabel.setText("Retweeted by " + user);
    }
}
