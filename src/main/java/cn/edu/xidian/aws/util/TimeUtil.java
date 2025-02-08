package cn.edu.xidian.aws.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author akynazh@gmail.com
 * @date 2/8/25
 * @description
 */
public class TimeUtil {
    public static int getYear(Long timestampMillis) {
        Instant instant = Instant.ofEpochMilli(timestampMillis);
        ZonedDateTime dateTime = instant.atZone(ZoneId.systemDefault());
        return dateTime.getYear();
    }
}
