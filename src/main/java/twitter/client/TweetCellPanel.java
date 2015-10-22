package twitter.client;
import javax.swing.*;

public class TweetCellPanel {
    private JLabel UserProfilePicture;
    private JPanel userNamesPanel;
    private JLabel userName;
    private JLabel userScreenName;
    private JPanel mainPanel;
    private JLabel userTweet;

    public TweetCellPanel(){
        userTweet.setFont(userTweet.getFont().deriveFont(14f));
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

    public void setUserName(String userName){
        this.userName.setText(userName);
    }

    public void setUserScreenName(String userScreenName){
        this.userScreenName.setText(userScreenName);
    }
}
