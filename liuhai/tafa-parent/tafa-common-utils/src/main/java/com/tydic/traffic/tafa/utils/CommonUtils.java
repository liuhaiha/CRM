package com.tydic.traffic.tafa.utils;

import com.tydic.traffic.tafa.utils.date.DateUtil;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Random;

/**
 * ClassName:CommonUtils
 */
public class CommonUtils {
	
	/**
	 * 
	 * getPID:(得到应用执行期进程号). <br/>
	 * @return
	 * @since 1.0
	 */
	public static int getPID(){
		int pid = 0;
		final RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
		final String info = runtime.getName();
		int index = info.indexOf("@");
		if(index != -1){
			pid = Integer.parseInt(info.substring(0, index));
		}
		return pid;
	}

	/**
	 * 
	 * isNumber:(是否为数字). <br/>
	 * 
	 * @param obj
	 * @return boolean
	 * @since 1.0
	 */
	public static boolean isNumber(Object obj) {
		try {
			Double.parseDouble(obj.toString());

			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * isInt:(是否来整型). <br/>
	 * 
	 * @param obj
	 * @return boolean
	 * @since 1.0
	 */
	public static boolean isInt(Object obj) {
		try {
			Integer.parseInt(obj.toString());

			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * isLong:(是否为long型). <br/>
	 * 
	 * @param obj
	 * @return boolean
	 * @since 1.0
	 */
	public static boolean isLong(Object obj) {
		try {
			Long.parseLong(obj.toString());

			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * isDouble:(是否为double). <br/>
	 * 
	 * @param obj
	 * @return boolean
	 * @since 1.0
	 */
	public static boolean isDouble(Object obj) {
		try {
			Double.parseDouble(obj.toString());

			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 
	 * isDate:(是否为日期格式). <br/>
	 * @param obj
	 * @return boolean
	 * @since 1.0
	 */
	public static boolean isDate(Object obj){
		return DateUtil.isDate(obj.toString());
	}

	/**
	 * 
	 * toInt:(object转为int). <br/>
	 * 
	 * @param obj
	 * @return int
	 * @since 1.0
	 */
	public static int toInt(Object obj) {
		try {
			return (int) toDouble(obj);
		}
		catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 
	 * toLong:(object转为long). <br/>
	 * 
	 * @param obj
	 * @return long
	 * @since 1.0
	 */
	public static long toLong(Object obj) {
		try {
			return (long) toDouble(obj);
		}
		catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 
	 * toFloat:(object转float). <br/>
	 *
	 * @return float
	 * @since 1.0
	 */
	public static float toFloat(Object obj) {
		try {
			if (obj == null)
				return 0;
			return Float.parseFloat(obj.toString());
		}
		catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 
	 * toDouble:(object转为dobule).
	 *
	 * @param obj
	 * @return double
	 * @since 1.0
	 */
	public static double toDouble(Object obj) {
		try {
			if (obj == null)
				return 0;
			return Double.parseDouble(obj.toString());
		}
		catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 
	 * toBoolean:(将字符转为boolean型). <br/>
	 * 
	 * @param _strData
	 * @return boolean	
	 * 			_strData以“0”、“false”起头转为false，否则转为true
	 * @since 1.0
	 */
	public static boolean toBoolean(String _strData) {
		try {
			if (_strData == null || _strData.length() <= 0)
				return false;
			if (_strData.startsWith("0") || _strData.toLowerCase().startsWith("false"))
				return false;
			else
				return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * byte2hex:(将byte[]转为16进制字符). <br/>
	 * 
	 * @param _buffer
	 * @return String
	 * @since 1.0
	 */
	public static String byte2hex(byte[] _buffer) {
		String strHex = "";
		String strTemp = "";
		for (int i = 0; i < _buffer.length; i++) {
			strTemp = (java.lang.Integer.toHexString(_buffer[i] & 0XFF));
			if (strTemp.length() == 1)
				strHex = strHex + "0" + strTemp;
			else
				strHex = strHex + strTemp;
			if (i < _buffer.length - 1)
				strHex = strHex + ":";
		}
		return strHex.toUpperCase();
	}

	/**
	 * 
	 * getRandomString:(取得指定长度的随机字符串). <br/>
	 * 
	 * @param length
	 * @return String
	 * @since 1.0
	 */
	public static String getRandomString(int length) {
		StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		int range = buffer.length();
		for (int i = 0; i < length; i++) {
			sb.append(buffer.charAt(r.nextInt(range)));
		}
		return sb.toString();
	}	
	
	/**
	 * 
	 * getRandomint:(取得指定长度的随机字符串). <br/>
	 * 
	 * @param length
	 * @return String
	 * @since 1.0
	 */
	public static String getRandomInt(int length) {
		StringBuffer buffer = new StringBuffer("0123456789");
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		int range = buffer.length();
		for (int i = 0; i < length; i++) {
			sb.append(buffer.charAt(r.nextInt(range)));
		}
		return sb.toString();
	}	

	/**
	 * 
	 * sleep:(休眠指定的毫秒数). <br/>
	 * 
	 * @param millis
	 *            void
	 * @since 1.0
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		}
		catch (InterruptedException e) {
		}
	}

	/**
	 * 
	 * sleepSeconds:(休眠指定的秒数). <br/>
	 * 
	 * @param seconds
	 *            void
	 * @since 1.0
	 */
	public static void sleepSeconds(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		}
		catch (InterruptedException e) {
		}
	}

	/**
	 * 
	 * sleepMinutes:(休眠指定的分钟数). <br/>
	 * 
	 * @param minutes
	 *            void
	 * @since 1.0
	 */
	public static void sleepMinutes(int minutes) {
		try {
			Thread.sleep(minutes * 60 * 1000);
		}
		catch (InterruptedException e) {
		}
	}
}
