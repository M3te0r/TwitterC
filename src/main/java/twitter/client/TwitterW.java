package twitter.client;

import org.json.JSONObject;
import org.scribe.model.Response;
import twitter.client.rest.TwitterRest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Mathieu on 19/09/2015.
 */
public class TwitterW extends JFrame {
    private JPanel rootPanel;
    private JButton button1;
    private JPanel leftDockPanel;
    private JTextField textField1;
    private JButton tweetButton;
    private JButton dismissButton;
    private JPanel newTweetPanel;
    private JLabel userProfilePicture;
    private JLabel userProfileBanner;
    private JTextArea userProfileDescription;
    private JList list1;
    private JList list2;
    private JButton button2;
    private JButton button3;
    private JLabel screenName;
    private TwitterRest twitterRest;

    public TwitterW(){
        super("Twitter Client");
        twitterRest = new TwitterRest();
        userProfilePicture.setIcon(new ImageIcon(TwitterW.class.getResource("/icons/blank_pp.png")));
        setIconImage(new ImageIcon(TwitterW.class.getResource("/icons/main_twitterC.png")).getImage());
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newTweetPanel.setVisible(true);
            }
        });
        dismissButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newTweetPanel.setVisible(false);
            }
        });
        initGUI();
    }

    private void initGUI(){
        Response responseUserInfo = twitterRest.getUserInformationsUponAuth();
        JSONObject obj = new JSONObject(responseUserInfo.getBody());
        userProfileDescription.setText(obj.getString("description"));
        screenName.setText("@" + obj.getString("screen_name"));
        try {
            userProfileBanner.setIcon(new ImageIcon(new ImageIcon(new URL(obj.getString("profile_banner_url"))).getImage().getScaledInstance(160, 120, Image.SCALE_SMOOTH)));
            userProfilePicture.setIcon(new ImageIcon(new URL(obj.getString("profile_image_url_https"))));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}
