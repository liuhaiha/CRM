package com.tydic.traffic.crm.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class TimeUtil 
{
	// 转换的日期格式
	public static String PATTERN_YYYYMMDD = "yyyy-MM-dd";
	public static String PATTERN_YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
	public static String PATTERN_YYYYMMDD_HHMM = "yyyy-MM-dd HH:mm";
	public static String PATTERN_YYYYMMDD_HHMMSSSSS = "yyyy-MM-dd HH:mm:ss.SSS";
	public static String TIME_START = " 00:00:00";
    public static String TIME_END_ = " 23:59:59";
	/**
	 * 将时间转为指定格式的字符串
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getTimeStr(Date date, String pattern)
	{
		if (null == date)
		{
			return "";
		}
		return new SimpleDateFormat(pattern).format(date);
	}
	
	/**
     * 将Timestamp时间转为指定格式的字符串
     * @param date
     * @param pattern
     * @return
     */
	public static String getTimestampStr(Timestamp timestamp, String pattern)
    {
        if (null == timestamp)
        {
            return "";
        }
        
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp.getTime());
        return new SimpleDateFormat(pattern).format(c.getTime());
    }
    public static String getTimeStr(String pattern)
    {
        return getTimeStr(new Date(System.currentTimeMillis()), pattern);
    }
	
	/**
     * 获取某年某月最大天数
     * @param year 年
     * @param month 月
     * @return
     */
    public static int getMaxDay(String year,String month)
    {       
        // 计算某一月份的最大天数
         Calendar time = Calendar.getInstance();
         time.clear(); //若不clear，很多信息会继承自系统当前时间
         time.set(Calendar.YEAR,Integer.valueOf(year));
         time.set(Calendar.MONTH,Integer.valueOf(month)-1);
         int day = time.getActualMaximum(Calendar.DAY_OF_MONTH); //本月总天数
         return day;
    }
    
    public static Timestamp getTimeStamp(String timeStr, String pattern)
    {
        DateFormat format = new SimpleDateFormat(pattern);
        format.setLenient(false);
        try 
        {
            Timestamp ts = new Timestamp(format.parse(timeStr).getTime());
            return ts;
        } 
        catch (ParseException e) 
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Timestamp getTimeStamp(String timeStr)
    {
        Date date = getTimestampDateFromString(timeStr);
        Timestamp time = new Timestamp(date.getTime());
        return time;
    }
    
    public static Timestamp getTimeStamp(Date date)
    {
        Timestamp time = new Timestamp(date.getTime());
        return time;
    }
    
    public static Date getTimestampDateFromString(String string)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN_YYYYMMDD_HHMMSS);
        try
        {
            Date date = dateFormat.parse(string);
            Timestamp timestamp = new Timestamp(date.getTime());
            return timestamp;
        }
        catch (Exception e)
        {
        }
        return new Date();
    }
    
   /**
    * 计算传入时间与当前时间的相隔天数
    * @param timestamp 
    * @return
    */
    public static long getDays(Timestamp timestamp){
        long currentTime= System.currentTimeMillis();
        long temp=timestamp.getTime();
        long dayslong=currentTime-temp;
        long days=(dayslong>>10)/84375;
        return days;
    } 
    
    /**
     * 将字符串转换为时间
     * @param str
     * @param pattern
     * @return
     */
    public static Date getDateFormatString(String str,String pattern)
    {
       if(str!= null && !str.trim().equals(""))
       {
           try 
           {
               return new SimpleDateFormat(pattern).parse(str);
           } 
           catch (ParseException e) 
           {
               e.printStackTrace();
               return null;
           }
       }
       else
       {
           return null;
       }
    }
    
    public static int countDays(Date begin,Date end){
        int days = 0;
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        String beginStr=TimeUtil.getTimeStr(begin, TimeUtil.PATTERN_YYYYMMDD);
        String endStr=TimeUtil.getTimeStr(end, TimeUtil.PATTERN_YYYYMMDD);
        try{
         
          long milliseconds = df1.parse(beginStr).getTime() - df.parse(endStr).getTime();
          long day = milliseconds /(long)(1000 * 60 * 60 * 24);
          days=(int)day;
        }catch(ParseException pe){
            System.out.println("日期格式必须为：yyyy-MM-dd；如：2010-4-4.");
        }
        
        return days; 
      } 
    
    /**
     * 获取两个时间的间隔天数
     * @param startTimeInMillis
     * @param endTimeInMillis
     * @throws ParseException
     */
    public static Long getTwoDay(Date startDate, Date endDate) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String endTempDate = myFormatter.format(endDate.clone());
        String startTempDate = myFormatter.format(startDate.clone());
        try {
            return ((myFormatter.parse(endTempDate).getTime()) - (myFormatter.parse(startTempDate).getTime())) / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            return -1L;
        }
    }
    
    /**
     *  @Description    : 测试;
     *  @Method_Name    : main;
     *  @return         : void;
     */
    public static void main(String[] args) {
    	System.out.println("-------"+TimeUtil.getCulFirstDate());
    	System.out.println("-------"+TimeUtil.getCulNowDate());
    	System.out.println("-------"+TimeUtil.getPreFirstDate());
    	System.out.println("-------"+TimeUtil.getPreDate());
    	System.out.println("-------"+TimeUtil.getMaxMonthDate());
    	System.out.println("-------"+TimeUtil.getPerFirstDayOfMonth());
	}
    
    /**
     * 获取当前时间日期
     * @param args
     */
    public static String getCulFirstDate() {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        String culDate = dft.format(cal.getTime());
        return culDate;
    }
    /**
     *  @Description    : 获取前月的今天;
     *  @return         : String;
     */
    public static String getPreDate() {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date date =  new Date();
        //获取当前日期
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-1);// 设置为1号，当前日期既要为本月第一天
        String dates=dft.format(cal.getTime());
        return dates;
    }
    
    /**
     *  @Description    : 获取前月的第一天;
     *  @Method_Name    : getNowTime;
     *  @return         : String;
     */
    public static String getPreFirstDate() {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前日期
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号，当前日期既要为本月第一天
        String culFirstDate = dft.format(cal.getTime());
        return culFirstDate;
    }
    
    /**
     * 获取当前时间日期
     * @param args
     */
    public static String getCulNowDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        String lastMonth = dft.format(cal.getTime());
        return lastMonth;
    }
    
    
 
    /**
     * 判断当天是否为本月第一天
     * 
     * @return
     */
    public static boolean isFirstDayOfMonth() {
        boolean flag = false;
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(calendar.DAY_OF_MONTH);
        if (1 == today) {
            flag = true;
        }
        return flag;
    }
 
    /**
     * 获取当前月份最后一天
     * 
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getMaxMonthDate() {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        // calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dft.format(calendar.getTime());
    }
 
    /**
     * 
     * 描述:获取下一个月的第一天.
     * 
     * @return
     */
    public static String getPerFirstDayOfMonth() {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return dft.format(calendar.getTime());
    }
 
    /**
     * 
     * 描述:获取上个月的最后一天.
     * 
     * @return
     */
    public static String getLastMaxMonthDate() {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dft.format(calendar.getTime());
    }
 
    /**
     * 获取上一个月
     * 
     * @return
     */
    public static String getLastMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, -1);
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
        String lastMonth = dft.format(cal.getTime());
        return lastMonth;
    }
 
    /**
     * 
     * 描述:获取下一个月.
     * 
     * @return
     */
    public static String getPreMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, 1);
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
        String preMonth = dft.format(cal.getTime());
        return preMonth;
    }
 
    // 是否是最后一天
    public static boolean isLastDayOfMonth() {
        boolean flag = false;
        if (StringUtils.isNotBlank(getCulNowDate()) && StringUtils.isNotBlank(getMaxMonthDate()) && StringUtils.equals(getCulNowDate(), getMaxMonthDate())) { // getMaxMonthDate().equals(getNowTime())
            flag = true;
        }
        return flag;
    }
    
    /**
     * 获取任意时间的下一个月
     * 描述:<描述函数实现的功能>.
     * @param repeatDate
     * @return
     */
    public static String getPreMonth(String repeatDate) {
        String lastMonth = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
        int year = Integer.parseInt(repeatDate.substring(0, 4));
        String monthsString = repeatDate.substring(4, 6);
        int month;
        if ("0".equals(monthsString.substring(0, 1))) {
            month = Integer.parseInt(monthsString.substring(1, 2));
        } else {
            month = Integer.parseInt(monthsString.substring(0, 2));
        }
        cal.set(year,month,Calendar.DATE);
        lastMonth = dft.format(cal.getTime());
        return lastMonth;
    }
    
    /**
     * 获取任意时间的上一个月
     * 描述:<描述函数实现的功能>.
     * @param repeatDate
     * @return
     */
    public static String getLastMonth(String repeatDate) {
        String lastMonth = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
        int year = Integer.parseInt(repeatDate.substring(0, 4));
        String monthsString = repeatDate.substring(4, 6);
        int month;
        if ("0".equals(monthsString.substring(0, 1))) {
            month = Integer.parseInt(monthsString.substring(1, 2));
        } else {
            month = Integer.parseInt(monthsString.substring(0, 2));
        }
        cal.set(year,month-2,Calendar.DATE);
        lastMonth = dft.format(cal.getTime());
        return lastMonth;
    }
    /**
     * 获取任意时间的月的最后一天
     * 描述:<描述函数实现的功能>.
     * @param repeatDate
     * @return
     */
    private static String getMaxMonthDate(String repeatDate) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            if(StringUtils.isNotBlank(repeatDate) && !"null".equals(repeatDate)){
                calendar.setTime(dft.parse(repeatDate));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dft.format(calendar.getTime());
    }
    
    /**
     * 获取任意时间的月第一天
     * 描述:<描述函数实现的功能>.
     * @param repeatDate
     * @return
     */
    private static String getMinMonthDate(String repeatDate){
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            if(StringUtils.isNotBlank(repeatDate) && !"null".equals(repeatDate)){
                calendar.setTime(dft.parse(repeatDate));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
       return dft.format(calendar.getTime());        
    }
    /**
     * 不论是当前时间，还是历史时间  皆是时间点的前天
     * repeatDate  任意时间
     */
    public static String getModify2DaysAgo(String repeatDate) {
        Calendar cal = Calendar.getInstance();
        String daysAgo = "";
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        if (repeatDate == null || "".equals(repeatDate)) {
            cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 2);
 
        } else {
            int year = Integer.parseInt(repeatDate.substring(0, 4));
            String monthsString = repeatDate.substring(4, 6);
            int month;
            if ("0".equals(monthsString.substring(0, 1))) {
                month = Integer.parseInt(monthsString.substring(1, 2));
            } else {
                month = Integer.parseInt(monthsString.substring(0, 2));
            }
            String dateString = repeatDate.substring(6, 8);
            int date;
            if ("0".equals(dateString.subSequence(0, 1))) {
                date = Integer.parseInt(dateString.substring(1, 2));
            } else {
                date = Integer.parseInt(dateString.substring(0, 2));
            }
            cal.set(year, month-1, date - 1);
            System.out.println(dft.format(cal.getTime()));
        }
        daysAgo = dft.format(cal.getTime());
        return daysAgo;
    }
    
    /**
     * 不论是当前时间，还是历史时间  皆是时间点的T-N天
     * repeatDate 任意时间    param 数字 可以表示前几天
     */
    public static String getModifyNumDaysAgo(String repeatDate,int param) {
        Calendar cal = Calendar.getInstance();
        String daysAgo = "";
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        if (repeatDate == null || "".equals(repeatDate)) {
            cal.set(Calendar.DATE, cal.get(Calendar.DATE) - param);
 
        } else {
            int year = Integer.parseInt(repeatDate.substring(0, 4));
            String monthsString = repeatDate.substring(4, 6);
            int month;
            if ("0".equals(monthsString.substring(0, 1))) {
                month = Integer.parseInt(monthsString.substring(1, 2));
            } else {
                month = Integer.parseInt(monthsString.substring(0, 2));
            }
            String dateString = repeatDate.substring(6, 8);
            int date;
            if ("0".equals(dateString.subSequence(0, 1))) {
                date = Integer.parseInt(dateString.substring(1, 2));
            } else {
                date = Integer.parseInt(dateString.substring(0, 2));
            }
            cal.set(year, month-1, date - param+1);
            System.out.println(dft.format(cal.getTime()));
        }
        daysAgo = dft.format(cal.getTime());
        return daysAgo;
    }
    
    
}
