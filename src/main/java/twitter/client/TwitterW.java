package twitter.client;

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
    private DefaultListModel<TweetModel> model1;
    private DefaultListModel<TweetModel> model2;

    public TwitterW(){
        super("Twitter Client");
        twitterRest = new TwitterRest();
        userProfilePicture.setIcon(new ImageIcon(TwitterW.class.getResource("/icons/blank_pp.png")));
        setIconImage(new ImageIcon(TwitterW.class.getResource("/icons/main_twitterC.png")).getImage());
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        model1 = new DefaultListModel<>();
        model2 = new DefaultListModel<>();
        list1.setModel(model1);
        list2.setModel(model2);
        CellRenderer renderer1 = new CellRenderer();
        CellRenderer renderer2 = new CellRenderer();
        list1.setCellRenderer(renderer1);
        list2.setCellRenderer(renderer2);
        setVisible(true);
        reloadHomeButton.addActionListener(e -> loadUserTweetData(true));
        reloadTimelineButton.addActionListener(e -> loadHomeTimeline(true));
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
    private void loadUserTweetData(boolean append){
        if (model1.isEmpty())
        {
            CompletableFuture.supplyAsync(() -> twitterRest.getUserTweets()).thenAccept(a -> this.processTimeline(a, 1, append));
        }
    }

    private void loadHomeTimeline(boolean append){
        System.out.println(append);
        if (model2.isEmpty()){
            CompletableFuture.supplyAsync(() -> twitterRest.getHomeTimeline(null,null)).thenAccept(a -> this.processTimeline(a, 2, append));
        }
        else {
            CompletableFuture.supplyAsync(() -> twitterRest.getHomeTimeline(model2.firstElement().getInternalId(),null)).thenAccept(a -> this.processTimeline(a, 2, append));

        }
    }

    private void processTimeline(Response response, int list, boolean append){
        JSONArray array = new JSONArray(response.getBody());
        for (int i = 0; i < array.length(); i++){
            JSONObject object = array.getJSONObject(i);
            if (object.getBoolean("retweeted")){
                JSONObject rted = object.getJSONObject("retweeted_status");
                String rtedText = rted.getString("text");
                JSONObject user = rted.getJSONObject("user");
                String userProfileURL = user.getString("profile_image_url_https");
                String screenName = user.getString("screen_name");
                TweetModel tweetModel = new TweetModel(rtedText, userProfileURL, screenName, object.getString("id_str"));
                runInvokeLater(tweetModel, list, append);
            }
            else {
                JSONObject userObject = object.getJSONObject("user");
                String urlProfilePicture = userObject.getString("profile_image_url_https");
                String twee = object.getString("text");
                String userScreenName = userObject.getString("screen_name");
                TweetModel tweetModel = new TweetModel(twee, urlProfilePicture, userScreenName, object.getString("id_str"));
                runInvokeLater(tweetModel, list, append);
            }
        }
    }

    private void runInvokeLater(TweetModel tweetModel, int list, boolean append){
        SwingUtilities.invokeLater(append ? list == 1 ? () -> model1.add(0, tweetModel) : () -> model2.add(0, tweetModel) : list == 1 ? () -> model1.addElement(tweetModel) : () -> model2.addElement(tweetModel));
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
        loadUserTweetData(false);
        loadHomeTimeline(false);
    }
}