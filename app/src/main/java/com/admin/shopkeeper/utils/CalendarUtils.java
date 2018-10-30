package com.admin.shopkeeper.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarUtils {
    public static boolean isSameDay(Date date1, Date date2) {
        return formatDateOnly(date1).equals(formatDateOnly(date2));
    }

    public static String formatDateOnly(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
    public static String formatDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static Date stringToDate(String strTime) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}