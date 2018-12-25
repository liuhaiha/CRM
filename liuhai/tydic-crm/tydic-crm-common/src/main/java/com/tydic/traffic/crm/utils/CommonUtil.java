package com.tydic.traffic.crm.utils;


import java.util.Collection;

/**
 * 常用方法工具类
 * @author wlhuang
 *
 */
public class CommonUtil {

	/**
	 * 将输入值转为Long,为空时返回0
	 * 
	 * @param value
	 * @return
	 */
	public static Long replaceNullLong(Object value) {
		return Long.valueOf(isNull(value) ? "0" : value.toString().trim());
	}

	/**
	 * 将输入值转为Float,为空时返回0
	 * 
	 * @param value
	 * @return
	 */
	public static Float replaceNullFloat(Object value) {
		return Float.valueOf(isNull(value) ? "0" : value.toString().trim());
	}

	/**
	 * 将输入值转为Integer,为空时返回0
	 * 
	 * @param value
	 * @return
	 */
	public static Integer replaceNullInt(Object value) {
		return Integer.valueOf(isNull(value) ? "0" : value.toString());
	}

	/**
	 * 输入值为空时返回""
	 * 
	 * @param value
	 * @return
	 */
	public static String replaceNullStr(Object value) {
		return null == value ? "" : value.toString().trim();
	}

	/**
	 * 输入值为空时返回0
	 * 
	 * @param value
	 * @return
	 */
	public static Double replaceNullDouble(Object value) {
		return Double.valueOf(isNull(value) ? "0" : value.toString().trim());
	}

	public static Short replaceNullShort(Object value) {
		return Short.valueOf(isNull(value) ? "0" : value.toString().trim());
	}

	/**
	 * 判断输入值是否为空
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNull(Object value) {
		return null == value || "".equals(value.toString().trim());
	}

	/**
	 * 判断输入值是否不为空
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNotNull(Object value) {
		return !isNull(value);
	}

	/**
	 * 获取数组的长度,为空时返回0
	 * 
	 * @param obj
	 * @return
	 */
	public static int getLen(Object[] obj) {
		return null == obj ? 0 : obj.length;
	}

	/**
	 * 获取集合的长度,为空时返回0
	 * 
	 * @param col
	 * @return
	 */
	public static int getLen(Collection<?> col) {
		return null == col ? 0 : col.size();
	}

	/**
	 * 
	 * 验证是否是数字
	 * 
	 * @param number
	 *            数字
	 * @return
	 */
	public static boolean isNumber(String number) {
		boolean isNumber = false;
		int index = number.indexOf(",");
		if (index >= 0) {
			// 有逗号等分隔符的数字
			isNumber = number
					.matches("[+-]?[0-9]+[0-9]*(,[0-9]{3})+(\\.[0-9]+)?");
		} else {
			isNumber = number.matches("[+-]?[0-9]+[0-9]*(\\.[0-9]+)?");
		}
		return isNumber;
	}
}
