package com.reliefzk.middleware.ets.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * Created by kui.zhouk on 15/10/10.
 */
public class DateUtil {

    private static final TimeZone beijingTimeZone = TimeZone.getTimeZone("GMT+8");
    private final static String DATEFORMAT_YYYYMMMDD = "yyyy-MM-dd";
    private final static String DATEFORMAT_YYYYMMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    private final static String DATEFORMAT_YYYYMMMDD_HHMM_EXT = "yyyy-MM-dd.HH.mm";

    public static Date parseExpireDate(String expireDateStr){
        if(StringUtils.isEmpty(expireDateStr)){
            return null;
        }
        try {
            return DateUtils.parseDate(expireDateStr, DATEFORMAT_YYYYMMMDD_HHMM_EXT);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date getDateByTimeZone(TimeZone timeZone){
        if(timeZone == null){
            timeZone = beijingTimeZone;
        }
        Calendar calendar = Calendar.getInstance(timeZone);
        return calendar.getTime();
    }

    public static Date parseDateYyyyMmDD(String dateStr) {
        if(StringUtils.isEmpty(dateStr)){
            return null;
        }
        try {
            return DateUtils.parseDate(dateStr, DATEFORMAT_YYYYMMMDD);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date parseLong(String dateStr) {
        if(StringUtils.isEmpty(dateStr)){
            return null;
        }
        try {
            return DateUtils.parseDate(dateStr, DATEFORMAT_YYYYMMMDD_HHMMSS);
        } catch (Exception e) {
            return null;
        }
    }

    public static String formatShort(Date date) {
        if(date == null){
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT_YYYYMMMDD);
            return sdf.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date addHour(Date date, int hour) {
        if(date == null){
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR_OF_DAY, hour);
        return c.getTime();
    }

    public static String formatDateStrYyyyMmDdHhMmSs(Date date) {
        if(date == null){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT_YYYYMMMDD_HHMMSS);
        return sdf.format(date);
    }

    public static int getWeekIndex(Date date){
        if(date == null){
            return -1;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static Date addMonth(Date date, int intervalMonth) {
        if(date == null){
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, intervalMonth);
        return c.getTime();
    }

    public static Date addMinute(Date date, int minute) {
        if(date == null){
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, minute);
        return c.getTime();
    }

}
