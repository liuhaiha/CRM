package com.tydic.traffic.tafa.utils.date;


import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author acer
 * @since 2017.06.12
 */
public abstract class DateUtil {
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
    /**
     * 获取前一天日期yyyyMMdd
     * @return 返回的日期格式为yyyyMMdd
     */
    public static String getYestoday(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
    }

    /**
     * 获取当前的日期yyyyMMdd
     */
    public static String getCurrentDate(){
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }


    /**
     * 获取当前的时间yyyyMMddHHmmss
     */
    public static String getCurrentTime(){
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    public static String dateToString(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public static Date stringToDate(String dateString) {
        if (dateString == null || dateString.trim().length() == 0) {
            return new Date(0);
        }

        try {
            String strFormat = "";
            switch (dateString.length()) {
                case 6: // yymmdd
                    strFormat = "yyMMdd";
                    break;
                case 8: // yyyymmdd
                    strFormat = "yyyyMMdd";
                    break;
                case 10: // yyyy-mm-dd
                    strFormat = "yyyy-MM-dd";
                    break;
                case 14:
                    strFormat = "yyyyMMddHHmmss";
                    break;
                default:
                    strFormat = "yyyy-MM-dd HH:mm:ss";
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strFormat);
            java.util.Date timeDate = simpleDateFormat.parse(dateString);
            return timeDate;
        }
        catch (Exception e) {
            return new Date(0);
        }
    }
    /**
     *
     * isDate:(检查日期是否合法). <br/>
     *
     * @param dateString
     * @return boolean true：日期合法；false:日期非法
     * @since 1.0
     */
    public static boolean isDate(String dateString) {
        boolean rs = true;
        if (null == dateString || "".equals(dateString)) {
            rs = false;
        }
        try {
            String str = dateString;
            str = dateString.replaceAll("\\.", "-");
            str = str.replaceAll("\\_", "-");
            str = str.replaceAll("\\/", "-");
            DateTime.parse(str);
        }
        catch (Exception e) {
            rs = false;
        }
        return rs;
    }


    /**
     *
     * getDateTimeOffset:(基于日期时间进行的偏移计算). <br/>
     *
     * @param dateString
     *            日期字符串
     * @param offset
     *            偏移量，正数为天数相加，负数为天数相减
     * @param format
     *            格式化输出
     * @param type
     *            偏移计算类型
     * @return String
     * @since 1.0
     */
    public static String getDateTimeOffset(String dateString, int offset, String format, int type) {
        if (null == dateString || "".equals(dateString)) {
            return null;
        }

        DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
        DateTime dt = DateTime.parse(dateString, fmt);

        switch (type){
            // 年
            case 1:
                dt = dt.plusYears(offset);
                break;
            // 月
            case 2:
                dt = dt.plusMonths(offset);
                break;
            // 周
            case 3:
                dt = dt.plusWeeks(offset);
                // 日
            case 4:
                dt = dt.plusDays(offset);
                break;
            // 时
            case 5:
                dt = dt.plusHours(offset);
                break;
            // 分
            case 6:
                dt = dt.plusMinutes(offset);
                break;
            // 秒
            case 7:
                dt = dt.plusSeconds(offset);
                break;
            // 毫秒
            case 8:
                dt = dt.plusMillis(offset);
                break;
            default :
                logger.error("错误的偏移类型！请在1-8的数字做出选择。");
                break;
        }

        return fmt.print(dt);
    }

    /**
     *
     * isLeap:(判读是否为闰年). <br/>
     *
     * @param dateString
     * @return boolean
     * @since 1.0
     */
    public static boolean isLeap(String dateString) {
        if (null == dateString || "".equals(dateString)) {
            return false;
        }
        DateTime dt = DateTime.parse(dateString);
        return dt.year().isLeap();
    }

    /**
     *
     * getDayOfYear:(取得日期为本年的第几天). <br/>
     *
     * @param dateString
     * @return int
     * @since 1.0
     */
    public static int getDayOfYear(String dateString) {
        if (null == dateString || "".equals(dateString)) {
            return 0;
        }
        DateTime dt = DateTime.parse(dateString);
        return dt.getDayOfYear();
    }

    /**
     *
     * getDayOfYear:(取得日期为本周的第几天). <br/>
     *
     * @param dateString
     * @return int
     * @since 1.0
     */
    public static int getDayOfWeek(String dateString) {
        if (null == dateString || "".equals(dateString)) {
            throw new IllegalArgumentException("非法的传入参数,当前传入参数不能为空或者null");
        }
        DateTime dt = DateTime.parse(dateString);
        return dt.getDayOfWeek();
    }

    /**
     *
     * getDayOfWeekText:(取得日期为星期几). <br/>
     *
     * @param dateString
     * @return String
     * @since 1.0
     */
    public static String getDayOfWeekText(String dateString) {
        if (null == dateString || "".equals(dateString)) {
            throw new IllegalArgumentException("非法的传入参数,当前传入参数不能为空或者null");
        }
        DateTime dt = DateTime.parse(dateString);
        return dt.dayOfWeek().getAsText();
    }

    /**
     *
     * getWeekOfWeekyear:(取得日期为本年的第几周). <br/>
     *
     * @param dateString
     * @return int
     * @since 1.0
     */
    public static int getWeekOfWeekyear(String dateString) {
        if (null == dateString || "".equals(dateString)) {
            throw new IllegalArgumentException("非法的传入参数,当前传入参数不能为空或者null");
        }
        DateTime dt = DateTime.parse(dateString);
        return dt.getWeekOfWeekyear();
    }

    /**
     *
     * getMonthMaxDay:(取得月day里面最大的值). <br/>
     *
     * @param dateString
     * @return int
     * @since 1.0
     */
    public static int getMonthMaxDay(String dateString) {
        if (null == dateString || "".equals(dateString)) {
            throw new IllegalArgumentException("非法的传入参数,当前传入参数不能为空或者null");
        }
        DateTime dt = DateTime.parse(dateString);
        return dt.dayOfMonth().getMaximumValue();
    }

    /**
     *
     * getDaysBetween:(计算两个日期间的天数). <br/>
     *
     * @param beginDate
     *                 格式为yyyy-MM-dd
     * @param endDate
     *                 格式为yyyy-MM-dd
     * @return int
     * @since 1.0
     */
    public static int getDaysBetween(String beginDate, String endDate) {
        LocalDate beginDT = new LocalDate(beginDate);
        LocalDate endDT = new LocalDate(endDate);
        return Days.daysBetween(beginDT, endDT).getDays();
    }

    /**
     *
     * compare:(两个日期进行大小比较). <br/>
     *
     * @param date1
     * @param date2
     * @return int -1：date1 < date2；0：date1 = date2；1：date1 > date2
     * @since 1.0
     */
    public static int compare(String date1, String date2) {
        int comRes = 0;
        DateTime dt1 = DateTime.parse(date1);
        DateTime dt2 = DateTime.parse(date2);
        if (dt1.isEqual(dt2)) {
            // date1 = date2
            comRes = 0;
        }
        else if (dt1.isBefore(dt2)) {
            // date1 < date2
            comRes = -1;
        }
        else {
            // date1 > date2
            comRes = 1;
        }
        return comRes;
    }
}
