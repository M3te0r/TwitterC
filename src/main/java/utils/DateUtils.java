package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Mathieu on 18/10/2015.
 */
public class DateUtils {

    private static final String FORMAT = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
    private static final SimpleDateFormat sf = new SimpleDateFormat(FORMAT, Locale.ENGLISH);

    public static Date getTwitterDate(String formattedDate){

        sf.setLenient(true);
        try {
            return sf.parse(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
