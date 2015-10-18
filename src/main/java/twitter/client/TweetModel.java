package twitter.client;

import com.sun.istack.internal.Nullable;
import utils.DateUtils;

import javax.swing.*;
import java.net.URL;
import java.util.Date;

/**
 * Created by Mathieu on 25/09/2015.
 */
public class TweetModel implements Comparable<TweetModel>{

    private final String screenName;
    private final String name;
    private final String tweetText;
    private final String userURLProfilIcon;
    private ImageIcon userTweetIcon;
    private boolean imageLoaded;
    private long internalId;
    private Date createdAt;

    public TweetModel(String tweetText, String userURLProfileIcon, String screenName, String name,long internalId, String formattedDate) {
        this.tweetText = tweetText;
        this.screenName = screenName;
        this.name = name;
        this.imageLoaded = false;
        this.userURLProfilIcon = userURLProfileIcon;
        this.internalId = internalId;
        this.createdAt = DateUtils.getTwitterDate(formattedDate);
    }

    public String getName() {
        return name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public long getInternalId() {
        return internalId;
    }

    public String getUserURLProfilIcon() {
        return userURLProfilIcon;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setUserTweetIcon(ImageIcon userTweetIcon) {
        this.userTweetIcon = userTweetIcon;
    }

    public String getTweetText() {
        return tweetText;
    }

    public ImageIcon getUserTweetIcon() {
        return userTweetIcon;
    }

    public boolean isImageLoaded() {
        return imageLoaded;
    }

    public void setImageLoaded(boolean imageLoaded) {
        this.imageLoaded = imageLoaded;
    }

    @Override
    public int compareTo(TweetModel o) {
        if (this.getInternalId() > o.getInternalId()) return  -1;
        else if (this.getInternalId() < o.getInternalId()) return 1;
        else if (this.getInternalId() == o.getInternalId()) return 0;
        return 0;
    }
}
