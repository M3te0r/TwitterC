package twitter.client;

import model.TweetListModel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.scribe.model.Response;
import twitter.client.rest.TwitterRest;
import utils.TaskLoader;
import javax.swing.*;
import java.awt.*;
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
    private JList<TweetModel> list2;
    private JButton reloadTimelineButton;
    private JButton reloadHomeButton;
    private JLabel screenName;
    private final TwitterRest twitterRest;
    private TweetListModel model1;
    private TweetListModel model2;

    public TwitterW(){
        super("Twitter Client");
        twitterRest = new TwitterRest();
        userProfilePicture.setIcon(new ImageIcon(TwitterW.class.getResource("/icons/blank_pp.png")));
        setIconImage(new ImageIcon(TwitterW.class.getResource("/icons/main_twitterC.png")).getImage());
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        model1 = new TweetListModel();
        model2 = new TweetListModel();
        list1.setModel(model1);
        list2.setModel(model2);
        CellRenderer renderer1 = new CellRenderer();
        CellRenderer renderer2 = new CellRenderer();
        list1.setCellRenderer(renderer1);
        list2.setCellRenderer(renderer2);
        setVisible(true);
        reloadHomeButton.addActionListener(e -> loadUserTweetData());
        reloadTimelineButton.addActionListener(e -> loadHomeTimeline());
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
        if (model1.isEmpty())
        {
            CompletableFuture.supplyAsync(() -> twitterRest.getUserTweets()).thenAccept(a -> this.processTimeline(a, 2));
        }
    }

    private void loadHomeTimeline(){
        reloadTimelineButton.setEnabled(false);
        reloadTimelineButton.setText("In Progress...");
        if (model2.isEmpty()){
            CompletableFuture.supplyAsync(() -> twitterRest.getHomeTimeline(null, null)).thenAccept(a -> this.processTimeline(a, 1));
        }
        else {
            CompletableFuture.supplyAsync(() -> twitterRest.getHomeTimeline(model1.getMaxId(), null)).thenAccept(a -> this.processTimeline(a, 1));
        }
    }

    public void enableButton(){
        reloadTimelineButton.setEnabled(true);
        reloadTimelineButton.setText("Reload timeline");
    }

    private void processTimeline(Response response, int list){
        JSONArray array = new JSONArray(response.getBody());
        for (int i = 0; i < array.length(); i++){
            JSONObject object = array.getJSONObject(i);
            if (object.getBoolean("retweeted")){
                JSONObject rted = object.getJSONObject("retweeted_status");
                String rtedText = rted.getString("text");
                JSONObject user = rted.getJSONObject("user");
                String userProfileURL = user.getString("profile_image_url_https");
                String screenName = user.getString("screen_name");
                String userName = user.getString("name");
                TweetModel tweetModel = new TweetModel(rtedText, userProfileURL, screenName, userName, object.getLong("id"), rted.getString("created_at"));
                runInvokeLater(tweetModel, list);
            }
            else if (object.has("retweeted_status")){
                JSONObject rted = object.getJSONObject("retweeted_status");
                String rtedText = rted.getString("text");
                JSONObject user = rted.getJSONObject("user");
                String userProfileURL = user.getString("profile_image_url_https");
                String screenName = user.getString("screen_name");
                String userName = user.getString("name");
                TweetModel tweetModel = new TweetModel(rtedText, userProfileURL, screenName, userName, object.getLong("id"), rted.getString("created_at"));
                runInvokeLater(tweetModel, list);
            }
            else {
                JSONObject userObject = object.getJSONObject("user");
                String urlProfilePicture = userObject.getString("profile_image_url_https");
                String twee = object.getString("text");
                String userScreenName = userObject.getString("screen_name");
                String userName = userObject.getString("name");
                TweetModel tweetModel = new TweetModel(twee, urlProfilePicture, userScreenName, userName, object.getLong("id"), object.getString("created_at"));
                runInvokeLater(tweetModel, list);
            }
        }
        enableButton();
    }

    private void runInvokeLater(TweetModel tweetModel, int list){
        SwingUtilities.invokeLater(list == 1 ? () -> {model1.addElement(tweetModel); model1.sortModel();} : () -> {model2.addElement(tweetModel); model2.sortModel();});
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
        loadHomeTimeline();
    }
}