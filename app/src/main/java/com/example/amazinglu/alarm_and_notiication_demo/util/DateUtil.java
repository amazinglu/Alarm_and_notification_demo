package com.example.amazinglu.alarm_and_notiication_demo.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    private static DateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy", Locale.getDefault());
    private static DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private static DateFormat DateTimeFormat = new SimpleDateFormat("yyyy MM dd HH:mm", Locale.getDefault());

    public static String dateTimeToString(Date date) {
        return DateTimeFormat.format(date);
    }

    public static Date StringToDateTime(String str) {
        try {
            return DateTimeFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return  null;
        }
    }

    public static String dateToString(Date date) {
        return dateFormat.format(date);
    }

    public static String timeToString(Date time) {
        return timeFormat.format(time);
    }
}
