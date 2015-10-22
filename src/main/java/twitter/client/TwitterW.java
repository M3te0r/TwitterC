package twitter.client;

import border.JRoundedCornerBorder;
import model.TweetListModel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.scribe.model.Response;
import twitter.client.rest.TwitterRest;
import utils.TaskLoader;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Mathieu on 19/09/2015.
 */
public class TwitterW extends JFrame {
    private JPanel rootPanel;
    private JPanel leftDockPanel;
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
    private JScrollPane scrollPane1;
    private JScrollPane scrollPane2;
    private JPanel listContainerPanel;
    private JPanel listPanel;
    private JButton newTweetButton;
    private JPanel nbTweetsPanel;
    private JPanel nbFollowsPanel;
    private JPanel nbFollowersPanel;
    private JLabel nbTweetLabel;
    private JLabel nbFollowsLabel;
    private JLabel nbFollowersLabel;
    private final TwitterRest twitterRest;
    private TweetListModel model1;
    private TweetListModel model2;
    private final Color DARK1 = new Color(47,47,47);
    private final Color LIGHT1 = new Color(221,221,211);

    public TwitterW(){
        super("Twitter Client");
        twitterRest = new TwitterRest();
        Toolkit tkMain = Toolkit.getDefaultToolkit();
        Dimension dimScreenSize = tkMain.getScreenSize();
        Insets scnMax = tkMain.getScreenInsets(getGraphicsConfiguration());
        final int taskBarSize = scnMax.bottom;
        setPreferredSize(new Dimension(dimScreenSize.width, dimScreenSize.height - taskBarSize));
        leftDockPanel.setBorder(new JRoundedCornerBorder());
        scrollPane1.setBorder(new JRoundedCornerBorder());
        scrollPane2.setBorder(new JRoundedCornerBorder());
        JLabel rowLabelHome = new JLabel("Home");
        rowLabelHome.setBackground(DARK1);
        rowLabelHome.setForeground(LIGHT1);
        rowLabelHome.setFont(rowLabelHome.getFont().deriveFont(20f));
        JLabel rowLabelUser = new JLabel("User timeline");
        rowLabelUser.setBackground(DARK1);
        rowLabelUser.setForeground(LIGHT1);
        rowLabelUser.setFont(rowLabelHome.getFont().deriveFont(20f));
        scrollPane2.setColumnHeaderView(rowLabelUser);
        scrollPane1.setColumnHeaderView(rowLabelHome);
        scrollPane1.getColumnHeader().setBackground(DARK1);
        scrollPane2.getColumnHeader().setBackground(DARK1);
        list1.setBorder(new JRoundedCornerBorder());
        list2.setBorder(new JRoundedCornerBorder());
        userProfilePicture.setIcon(new ImageIcon(TwitterW.class.getResource("/icons/blank_pp.png")));
        setIconImage(new ImageIcon(TwitterW.class.getResource("/icons/main_twitterC.png")).getImage());
        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        model1 = new TweetListModel();
        model2 = new TweetListModel();
        list1.setModel(model1);
        list2.setModel(model2);
        CellRenderer renderer1 = new CellRenderer();
        CellRenderer renderer2 = new CellRenderer();
        list1.setCellRenderer(renderer1);
        list2.setCellRenderer(renderer2);
        reloadHomeButton.addActionListener(e -> loadUserTweetData());
        reloadTimelineButton.addActionListener(e -> loadHomeTimeline());
        pack();
        setLocation(dimScreenSize.width - getWidth(), dimScreenSize.height - taskBarSize - getHeight());
        setVisible(true);
        initGUI();
    }

    private void renderProfileImage(ImageIcon icon){
        userProfilePicture.setIcon(icon);
    }

    private void renderProfileBanner(ImageIcon icon){
        userProfileBanner.setIcon(new ImageIcon(icon.getImage().getScaledInstance(200, 140, Image.SCALE_SMOOTH)));
    }
    //TODO  : Load timelines and process
    private void loadUserTweetData(){
        if (model1.isEmpty())
        {
            CompletableFuture.supplyAsync(() -> twitterRest.getUserTweets(null, null)).thenAccept(a -> this.processTimeline(a, 2));
        }
        else {
            CompletableFuture.supplyAsync(() -> twitterRest.getUserTweets(model2.getMaxId(), null)).thenAccept(a -> this.processTimeline(a, 2));
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
        nbFollowsLabel.setText(String.valueOf(obj.getInt("friends_count")));
        nbTweetLabel.setText(String.valueOf(obj.getInt("statuses_count")));
        nbFollowersLabel.setText(String.valueOf(obj.getInt("followers_count")));
        CompletableFuture.supplyAsync(() -> TaskLoader.downloadImageFromString(obj.getString("profile_image_url"))).thenAccept(this::renderProfileImage);
        CompletableFuture.supplyAsync(() -> TaskLoader.downloadImageFromString(obj.getString("profile_banner_url"))).thenAccept(this::renderProfileBanner);
    }

    private void initGUI(){
        loadUserInformation();
        loadUserTweetData();
        loadHomeTimeline();
    }

    private void createUIComponents() {
        newTweetButton = new JButton("New Tweet")
        {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setColor(new Color(43,123,185));
                g2.fillRoundRect(0, 0, getSize().width, getSize().height, 7, 7);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        newTweetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newTweetPanel.setVisible(true);
            }
        });
        newTweetButton.setFont(newTweetButton.getFont().deriveFont(20f));
        newTweetButton.setIcon(new ImageIcon(getClass().getResource("/icons/new_tweet_button2.png")));
        newTweetButton.setContentAreaFilled(false);
    }
}