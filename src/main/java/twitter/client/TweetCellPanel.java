package twitter.client;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TweetCellPanel {
    private JLabel UserProfilePicture;
    private JPanel userNamesPanel;
    private JLabel userName;
    private JLabel userScreenName;
    private JPanel mainPanel;
    private JTextArea userTweet;
    private final Font font = UIManager.getFont("Label.font").deriveFont(14f);

    public TweetCellPanel(){
        userTweet.setBorder(null);
        userTweet.setFont(font);
        userTweet.setEditable(false);
        userTweet.requestFocusInWindow();
        userTweet.setMinimumSize(new Dimension(((int) userTweet.getPreferredSize().getWidth()) + 1, ((int) userTweet.getPreferredSize().getHeight())));
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
