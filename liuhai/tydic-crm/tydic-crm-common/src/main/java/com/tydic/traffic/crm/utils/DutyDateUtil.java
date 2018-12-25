package com.tydic.traffic.crm.utils;

import java.util.Calendar;
import java.util.Date;

import com.tydic.traffic.tafa.utils.date.DateUtil;

public class DutyDateUtil extends DateUtil	{
	public static Date getBeforeDay(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();
	} 
	
	public static Date getAfterDay(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	} 
	
	/**
	 * 判断是否为周六、周日
	 * @return
	 */
	public static boolean isWeekEnd(Date date)
	{
		if (null == date)
		{
			return false;
		}
		
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		int daySeq = calendar.get(Calendar.DAY_OF_WEEK);
		return daySeq == Calendar.SUNDAY || daySeq == Calendar.SATURDAY;
	}
}
