package twitter.client;

import org.json.JSONArray;
import org.json.JSONObject;
import org.scribe.model.Response;
import twitter.client.rest.TwitterRest;
import utils.TaskLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

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
    private JList<TweetModel> list1;
    private JList list2;
    private JButton reloadTimelineButton;
    private JButton reloadHomeButton;
    private JLabel screenName;
    private final TwitterRest twitterRest;
    private DefaultListModel<TweetModel> model;

    public TwitterW(){
        super("Twitter Client");
        twitterRest = new TwitterRest();
        userProfilePicture.setIcon(new ImageIcon(TwitterW.class.getResource("/icons/blank_pp.png")));
        setIconImage(new ImageIcon(TwitterW.class.getResource("/icons/main_twitterC.png")).getImage());
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        model = new DefaultListModel<>();
        list1.setModel(model);
        CellRenderer renderer = new CellRenderer();
        list1.setCellRenderer(renderer);
        setVisible(true);
        reloadHomeButton.addActionListener(e -> loadUserTweetData());
        button1.addActionListener(e -> newTweetPanel.setVisible(true));
        dismissButton.addActionListener(e -> newTweetPanel.setVisible(false));
        initGUI();
    }

    private void renderProfileImage(ImageIcon icon){
        userProfilePicture.setIcon(icon);
    }

    private void renderProfileBanner(ImageIcon icon){
        userProfileBanner.setIcon(new ImageIcon(icon.getImage().getScaledInstance(160, 120, Image.SCALE_SMOOTH)));
    }
    //TODO  : Load timelines and process
    private void loadUserTweetData(){
        CompletableFuture.supplyAsync(twitterRest::getUserTweets).thenAccept(this::processTimeline);
    }

    private void processTimeline(Response response){
        JSONArray array = new JSONArray(response.getBody());
        for (int i = 0; i < array.length(); i++){
            JSONObject object = array.getJSONObject(i);
            if (object.getBoolean("retweeted")){
                JSONObject rted = object.getJSONObject("retweeted_status");
                String rtedText = rted.getString("text");
                JSONObject user = rted.getJSONObject("user");
                String userProfileURL = user.getString("profile_image_url_https");
                String screenName = user.getString("screen_name");
                TweetModel tweetModel = new TweetModel(rtedText, userProfileURL, screenName);
                runInvokeLater(tweetModel);
            }
            else {
                JSONObject userObject = object.getJSONObject("user");
                String urlProfilePicture = userObject.getString("profile_image_url_https");
                String twee = object.getString("text");
                String userScreenName = userObject.getString("screen_name");
                TweetModel tweetModel = new TweetModel(twee, urlProfilePicture, userScreenName);
                runInvokeLater(tweetModel);
            }
        }
    }

    private void runInvokeLater(TweetModel tweetModel){
        SwingUtilities.invokeLater(() -> model.addElement(tweetModel));
    }

    /**
     * Process loading user tweets in an asyncTask
     */
    private void loadUserInformation(){
        CompletableFuture.supplyAsync(twitterRest::getUserInformationsUponAuth).thenAccept(this::processUserInformation);
    }

    private void processUserInformation(Response response){
        JSONObject obj = new JSONObject(response.getBody());
        userProfileDescription.setText(obj.getString("description"));
        screenName.setText("@" + obj.getString("screen_name"));
        CompletableFuture.supplyAsync(() -> TaskLoader.downloadImageFromString(obj.getString("profile_image_url"))).thenAccept(this::renderProfileImage);
        CompletableFuture.supplyAsync(() -> TaskLoader.downloadImageFromString(obj.getString("profile_banner_url"))).thenAccept(this::renderProfileBanner);
    }

    private void initGUI(){
        loadUserInformation();
        loadUserTweetData();
    }
}