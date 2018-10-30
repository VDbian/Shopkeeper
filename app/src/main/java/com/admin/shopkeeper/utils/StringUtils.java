package com.admin.shopkeeper.utils;

import android.util.Log;


import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class StringUtils {
    public static String doubleToString(double num) {
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }
    public static String friendly_time(Date date) {
        if (date == null) {
            return "未知";
        } else {
            Calendar calendar = Calendar.getInstance();
            Date curTime = calendar.getTime();
            String time = "";
            /**
             * 判断传入时间和当前时间是否是同一天
             */
            if (CalendarUtils.isSameDay(date, curTime)) {
                int hour = (int) ((curTime.getTime() - date.getTime()) / 3600000);
                if (hour == 0)
                    time = Math.max(
                            (curTime.getTime() - date.getTime()) / 60000, 1)
                            + "m";
                else
                    time = hour + "h";
                return time;
            }

            long lt = date.getTime() / 86400000;
            long ct = curTime.getTime() / 86400000;
            int days = (int) (ct - lt);
            if (days == 0) {
                int hour = (int) ((curTime.getTime() - date.getTime()) / 3600000);
                if (hour == 0)
                    time = Math.max(
                            (curTime.getTime() - date.getTime()) / 60000, 1)
                            + "m";
                else
                    time = hour + "h";
            } else if (days == 1) {
                time = "昨天";
            } else if (days == 2) {
                time = "前天";
            } else if (days > 2 && days < 31) {
                time = days + "天前";
            } else if (days >= 31 && days <= 2 * 31) {
                time = "一个月前";
            } else if (days > 2 * 31 && days <= 3 * 31) {
                time = "2个月前";
            } else if (days > 3 * 31 && days <= 4 * 31) {
                time = "3个月前";
            } else {
                time = CalendarUtils.formatDateOnly(date);
            }

            return time;
        }
    }

}
