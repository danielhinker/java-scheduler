package models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Utilities {

    public static String getCurrentDateTime() {
        final Date currentTime = new Date();
        final SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate.setTimeZone(TimeZone.getTimeZone("GMT"));
        return formattedDate.format(currentTime);
    }

}
