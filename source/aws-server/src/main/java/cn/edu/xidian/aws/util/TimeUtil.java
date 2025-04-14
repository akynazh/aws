package cn.edu.xidian.aws.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author akynazh@gmail.com
 * @date 2/8/25
 * @description
 */
public class TimeUtil {
    public static final String timeFormatFull = "yyyy-MM-dd HH:mm:ss";

    public static int getYear(Long timestampMillis) {
        Instant instant = Instant.ofEpochMilli(timestampMillis);
        ZonedDateTime dateTime = instant.atZone(ZoneId.systemDefault());
        return dateTime.getYear();
    }

    public static long getTimestampFromFormatDate(String timeString, String timeFormat) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
        Date date = sdf.parse(timeString);
        return date.getTime();
    }

    public static long getTimestampFromFormatDate(String timeString) throws ParseException {
        return getTimestampFromFormatDate(timeString, timeFormatFull);
    }
}
