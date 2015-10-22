package twitter.client;

import border.JRoundedCornerBorder;
import model.TweetListModel;
import org.json.JSONObject;
import org.scribe.model.Response;
import twitter.client.rest.TwitterRest;
import utils.TaskLoader;
import utils.TweetParser;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Mathieu on 19/09/2015.
 */
public class TwitterW extends JFrame {
    private JPanel rootPanel;
    private JPanel leftDockPanel;
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
    private JButton closeTweetPanelButton;
    private JTextArea textArea1;
    private JButton tweetButton;
    private JLabel nbCharTweet;
    private final TwitterRest twitterRest;
    private final TweetListModel model1;
    private final TweetListModel model2;
    private final Color DARK1 = new Color(47,47,47);
    private final Color LIGHT1 = new Color(221,221,211);
    private final int NBMAXCHARTWEET = 140;

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
        closeTweetPanelButton.addActionListener(e -> newTweetPanel.setVisible(false));
        scrollPane1.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            boolean ready = true;
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                if (list1.getLastVisibleIndex() <= model1.size() && list1.getLastVisibleIndex() >= model1.size() - 15 && ready)
                {
                    ready = false;
                    CompletableFuture.supplyAsync(() -> twitterRest.getHomeTimeline(null, model1.lastElement().getInternalId())).thenAccept(a -> processTimeline(a, 1)).handle((ok, err) -> ready = true);
                }
            }
        });
        scrollPane2.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {

            boolean ready = true;
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                if (list2.getLastVisibleIndex() <= model2.getSize() && list2.getLastVisibleIndex() >= model2.getSize() - 15 && ready)
                {
                    ready = false;
                    CompletableFuture.supplyAsync(() -> twitterRest.getUserTweets(null, model2.lastElement().getInternalId())).thenAccept(a -> processTimeline(a, 2)).handle((ok, err) -> ready = true);
                }
            }
        });
        tweetButton.addActionListener(e -> {
            if (!textArea1.getText().isEmpty())
            {
                CompletableFuture.supplyAsync(() -> twitterRest.postTweetMessage(textArea1.getText())).thenAccept(TwitterW.this::parseNewTweet);
            }
        });
        pack();
        setLocation(dimScreenSize.width - getWidth(), dimScreenSize.height - taskBarSize - getHeight());
        setVisible(true);
        initGUI();
    }

    private void parseNewTweet(String tweetResult){
        if (tweetResult != null){
            runInvokeLater(TweetParser.parseSingle(tweetResult), 2);
        }
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

    private void processTimeline(String response, int list){
        runInvokeLater(TweetParser.parseMultiple(response), list);
        enableButton();
    }

    private void runInvokeLater(java.util.List<TweetModel> tweets, int list){
        if(tweets != null){
            SwingUtilities.invokeLater(() -> {
                switch (list) {
                    case 1:
                        tweets.forEach(model1::addElement);
                        model1.sortModel();
                        break;
                    default:
                        tweets.forEach(model2::addElement);
                        model2.sortModel();
                        break;
                }
            });
        }
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


        textArea1 = new JTextArea(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if(getText().isEmpty() && !(FocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == this))
                {
                    Graphics2D g2 = (Graphics2D)g.create();
                    g2.setBackground(Color.GRAY);
                    g2.setColor(Color.GRAY);
                    g2.setFont(UIManager.getFont("Label.font").deriveFont(20f));
                    g2.drawString("Quoi de neuf ?",8,20);
                    g2.dispose();
                }
            }
        };

        textArea1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                int nbchar = NBMAXCHARTWEET - e.getDocument().getLength();
                nbCharTweet.setText(String.valueOf(nbchar));
                if (nbchar >= 0 && nbchar < 140){
                    tweetButton.setEnabled(true);
                }
                else tweetButton.setEnabled(false);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                int nbchar = NBMAXCHARTWEET - e.getDocument().getLength();
                nbCharTweet.setText(String.valueOf(nbchar));
                if (nbchar >= 0 && nbchar < 140){
                    tweetButton.setEnabled(true);
                }
                else tweetButton.setEnabled(false);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                int nbchar = NBMAXCHARTWEET - e.getDocument().getLength();
                nbCharTweet.setText(String.valueOf(nbchar));
                if (nbchar >= 0 && nbchar < 140){
                    tweetButton.setEnabled(true);
                }
                else tweetButton.setEnabled(false);
            }
        });
        textArea1.setFont(UIManager.getFont("Label.font").deriveFont(20f));
    }
}