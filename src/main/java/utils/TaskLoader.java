package utils;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Utils class mostly used to load asynchronous tasks
 *
 */
public class TaskLoader {

    /** Method top downlaod Image from url
     * @param urlProfile the URL where image is located
     * @return @
     */
    public static ImageIcon downloadImageFromString(String urlProfile){
        try {
            return new ImageIcon(new URL(urlProfile));
        } catch (MalformedURLException e) {
            return new ImageIcon(TaskLoader.class.getResource("/icons/blank_pp.png"));
        }
    }
}
