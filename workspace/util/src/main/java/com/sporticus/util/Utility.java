package com.sporticus.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mark on 11/06/2017.
 */
public class Utility {

    static String pattern = "yyyy/MM/dd HH:mm:ss";
    static SimpleDateFormat SDF = null;

    static {
        SDF = new SimpleDateFormat(pattern);
    }

    public static String format(final Date date) {
        if (date == null) {
            return "";
        }
        synchronized (SDF) {
            return SDF.format(date);
        }
    }
}
