package com.admin.shopkeeper.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.admin.shopkeeper.entity.TimeTypeBean;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/9/8.
 */

public class Tools {

    public static List<TimeTypeBean> getTimeType(){
        List<TimeTypeBean> list = new ArrayList<>();
        list.add(new TimeTypeBean("营业时间",false));
        list.add(new TimeTypeBean("自定义时间",false));
        return list;
    }

    public static String valueOf(double d) {
        BigDecimal bg = new BigDecimal(d);
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1 + "";
    }

    public static String formatNowDate(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    public static String formatNowDate(String format, Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static String formatLastMonthDate(String format) {
        long l = System.currentTimeMillis() - 60 * 60 * 24 * 30 * 1000L;
        return new SimpleDateFormat(format).format(new Date(l));
    }

    public static long getLastMonth() {
        long l = System.currentTimeMillis() - 60 * 60 * 24 * 30 * 1000L;
        return l;
    }

    public static long getLastWeek() {
        long l = System.currentTimeMillis() - 60 * 60 * 24 * 7 * 1000L;
        return l;
    }

    //获取某个日期的开始时间
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    //获取某个日期的结束时间
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    //获取今年是哪一年
    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }

    //获取本月是哪一月
    public static int getNowMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }

    //获取本周的开始时间
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }

    //获取本周的结束时间
    public static Date getEndDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    //获取本月的开始时间
    public static Date getBeginDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        return getDayStartTime(calendar.getTime());
    }

    //获取本月的结束时间
    public static Date getEndDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 1, day);
        return getDayEndTime(calendar.getTime());
    }

    public static Date toDate(String str) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
            return sdf.parse(str);
        } catch (Exception e) {
        }
        return null;
    }

    public static boolean comparaDate(Date startDate, Date endDate) {
        long l = startDate.getTime();
        long l1 = endDate.getTime();
        if (l1 < l) {
            return true;
        }
        return false;
    }

    public static boolean checkDate(Date startDate, Date endDate) {
        long l = startDate.getTime() + 60 * 60 * 24 * 30 * 1000L;
        long l1 = endDate.getTime();
        if (l1 > l) {
            return true;
        }
        return false;
    }

    public static int getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        int versionCode = 0;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static void hideSoftKeyboard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

    }

    public static String convertHanzi2Pinyin(String hanzi, boolean full) {
        /***  * ^[\u2E80-\u9FFF]+$ 匹配所有东亚区的语言    ^[\u4E00-\u9FFF]+$ 匹配简体和繁体   * ^[\u4E00-\u9FA5]+$ 匹配简体  */
        String regExp = "^[\u4E00-\u9FFF]+$";
        StringBuffer sb = new StringBuffer();
        if (hanzi == null || "".equals(hanzi.trim())) {
            return "";
        }
        String pinyin = "";
        for (int i = 0; i < hanzi.length(); i++) {
            char unit = hanzi.charAt(i);
            if (match(String.valueOf(unit), regExp))
            //是汉字，则转拼音
            {
                pinyin = convertSingleHanzi2Pinyin(unit);
                if (full) {
                    sb.append(pinyin);
                } else {
                    sb.append(pinyin.charAt(0));
                }
            } else {
                sb.append(unit);
            }
        }
        return sb.toString();
    }

    /***
     * 将单个汉字转成拼音
     * @param hanzi
     * @return
     */
    private static String convertSingleHanzi2Pinyin(char hanzi)
    {
        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        String[] res;
        StringBuffer sb=new StringBuffer();
        try {
            res = PinyinHelper.toHanyuPinyinStringArray(hanzi,outputFormat);
            sb.append(res[0]);//对于多音字，只用第一个拼音
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return sb.toString();
    }

    /***
     * @param str 源字符串
     * @param regex 正则表达式
     * @return 是否匹配
     */
    public static boolean match(String str,String regex)
    {
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(str);
        return matcher.find();
    }
}
