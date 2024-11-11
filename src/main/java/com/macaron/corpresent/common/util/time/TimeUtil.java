package com.macaron.corpresent.common.util.time;


import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.macaron.corpresent.common.constants.DateTimeConstants.*;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-09-26
 * Time: 8:17
 */
public class TimeUtil {

    public static String getDate(Date date) {
        return DATE_FORMAT.format(date);
    }

    public static String getDateTime(Date date) {
        return DATE_TIME_FORMAT.format(date);
    }

    public static String getDateTime(long date) {
        return DATE_TIME_FORMAT.format(new Date(date));
    }

    public static long secondToMillis(long timestamp) {
        return TimeUnit.SECONDS.toMillis(timestamp);
    }

    public static long millisToSecond(long timestamp) {
        return TimeUnit.MILLISECONDS.toSeconds(timestamp);
    }

}
