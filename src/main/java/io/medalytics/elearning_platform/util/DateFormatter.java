package io.medalytics.elearning_platform.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    private static final String DEFAULT_PATTERN = "dd/mm/yyy";

    public static Date formatDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_PATTERN);
        return dateFormat.parse(dateString);
    }

//    public static Date formatDate(String dateString, String pattern) throws ParseException {
//        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
//        return dateFormat.parse(dateString);
//    }
}
