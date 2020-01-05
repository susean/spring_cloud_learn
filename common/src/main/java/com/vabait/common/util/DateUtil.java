package com.vabait.common.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DATE);

        return day;
    }

    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.YEAR);

        return day;
    }

    public static long minutesBetween(Date beginDate, Date endDate) {
        //SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        long from = beginDate.getTime();
        long to = endDate.getTime();
        long minutes = (to - from) / (1000 * 60);

        return minutes;
    }

    public static long secondsBetween(Date beginDate, Date endDate) {
        //SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        long from = beginDate.getTime();
        long to = endDate.getTime();
        long seconds = (to - from) / 1000;

        return seconds;
    }

    public static long[] getTimestamps(String beginDate, String endDate) {
        long beginTimestamp = 0;
        long endTimestamp = 0;
        if (beginDate != null && beginDate.length() > 0) {
            beginTimestamp = StringUtil.dateFromStr8(beginDate).getTime();
        }
        if (endDate != null && endDate.length() > 0) {
            endTimestamp = StringUtil.dateFromStr8(endDate).getTime() + 24 * 60 * 60 * 1000 - 1;
        }

        return new long[]{beginTimestamp, endTimestamp};
    }

    public static String getDateStringYYYYMMDD(Date date) {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
        return time.format(date);
    }

    public static String getDateStringYYYYMMDD8(Date date) {
        SimpleDateFormat time = new SimpleDateFormat("yyyyMMdd");
        return time.format(date);
    }

    public static String getDateStringYYYYMMDDHHMMSS(Date date) {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return time.format(date);
    }

    public static Date getDateFromYYYYMMDDHHMMSS(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(dateStr, pos);

        return strtodate;
    }

    public static Date getDateFromYYYYMMDD8(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(dateStr, pos);

        return strtodate;
    }

    public static Date getFutureDate(String timeStr) {
        Date now = new Date();
        String yyyymmdd = getDateStringYYYYMMDD(now);
        String futureTime = yyyymmdd + " " + timeStr;

        Date futureDate = DateUtil.getDateFromYYYYMMDDHHMMSS(futureTime);
        long endTimestamp = futureDate.getTime();
        long nowTimestamp = now.getTime();
        if (endTimestamp < nowTimestamp) {
            long futureTimestamp = endTimestamp + 24 * 60 * 60 * 1000;
            futureDate = new Date(futureTimestamp);
        }

        return futureDate;
    }

    public static int[] adjustTime(int hour, int minute, int second, long adjustseconds) {
        final int HourSeconds = 60 * 60;
        final int MinuteSeconds = 60;

        long seconds = adjustseconds + hour * HourSeconds + minute * MinuteSeconds + second;

        hour = (int) seconds / HourSeconds;
        minute = (int) (seconds - hour * HourSeconds) / MinuteSeconds;
        second = (int) seconds - hour * HourSeconds - minute * MinuteSeconds;

        return new int[]{hour, minute, second};
    }
}
