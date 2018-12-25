package com.tydic.traffic.tafa.utils.string;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName:StringUtils <br/>
 * Reason: 文本工具类. <br/>
 */
public class StringUtils {
	private static final Logger logger = LoggerFactory.getLogger(StringUtils.class);

	/**
	 * 换行符
	 */
	public static final String CRIF = System.getProperty("line.separator");
	
	/**
	 *  操作系统名称
	 */
	public static final String OS = System.getProperty("os.name");
	
	/**
	 * 文件分隔符
	 */
	public static final String FILE_SEPARATOR = System.getProperty("file.separator");


	/**
	 * 空白字符串
	 */
	public static final String EMPTY = "";

	/**
	 * null
	 */
	public static final String NULL = null;

	/**
	 * 
	 * nvl:(去null，去空白字符串). <br/>
	 *
	 */
	public static String nvl(String str) {
		if (str != null && !str.trim().equals("")) {
			return str.trim();
		}
		return "";
	}

	/**
	 * 
	 * createEmptyString:(得到指定长度的空白字符串). <br/>
	 *
	 */
	public static String createEmptyString(int num) {
		String str = "";
		while (num > 0) {
			str += " ";
			num--;
		}
		return str;
	}

	/**
	 * 
	 * countChineseNumber:(判断有几个中文字符). <br/>
	 */
	public static int countChineseNumber(String str) {
		String temp = null;
		Pattern p = Pattern.compile("[\u4E00-\u9FA5]+");
		Matcher m = p.matcher(str);
		int count = 0;
		while (m.find()) {
			temp = m.group(0);
			count += temp.length();
		}
		return count;
	}
	
	/**
	 * 
	 * countByteLength:(得到字符串的字节长度， 中文 占2个字节). <br/>
	 */
	public static int countByteLength(String source) {
		int len = 0;
		for (int i = 0; i < source.length(); i++) {
			char c = source.charAt(i);
			int highByte = c >>> 8;
			len += highByte == 0 ? 1 : 2;
		}
		return len;
	}

	
	/**
	 * 
	 * isNull:(为null 或 ""). <br/>
	 */
	public static boolean isNull(String str) {
		if (str == null || "".equals(str)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * isEmpty:(为null 或 "" 或 空白字串). <br/>
	 *
	 */
	public static boolean isEmpty(String str) {
		if (str != null && !str.trim().equals("")) {
			return false;
		}
		return true;
	}	

	/**
	 * 
	 * equals:(检查两个字符串是否相等). <br/>
	 */
	public static boolean equals(String s1, String s2) {
		return s1 == null ? s2 == null : s1.equals(s2);
	}

	/**
	 * 
	 * equalsIgnoreCase:(检查两个字符串的忽略大小写后是否相等.). <br/>
	public static boolean equalsIgnoreCase(String s1, String s2) {
		return s1 == null ? s2 == null : s1.equalsIgnoreCase(s2);
	}
	
	/**
	 * 
	 * Blob2String:(blob二进制流转字符串). <br/>
	 */
	public static String Blob2String(Blob blob){
		String result = "";
		try{
			ByteArrayInputStream content = (ByteArrayInputStream)blob.getBinaryStream();
			byte[] byte_data = new byte[content.available()];
			content.read(byte_data, 0, byte_data.length);
			result = new String(byte_data);
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}		
		return result;
	}
	
	/**
	 * 
	 * replace:(高效字符替换). <br/>
	 * @param strSource		源字符串
	 * @param strFrom		待替换旧字符
	 * @param strTo			替换为新字符
	 */
	public static String replace(String strSource, String strFrom, String strTo){
		if(strSource == null){
			return null;
		}
		int i = 0;
		if((i = strSource.indexOf(strFrom, i)) >= 0){
			char[] cSrc = strSource.toCharArray();
			char[] cTo = strTo.toCharArray();
			int len = strFrom.length();
			StringBuffer buf = new StringBuffer(cSrc.length);
			buf.append(cSrc, 0, i).append(cTo);
			i += len;
			int j = i;
			while((i = strSource.indexOf(strFrom, i)) > 0){
				buf.append(cSrc, j, i - j).append(cTo);
				i += len;
				j = i;
			}
			buf.append(cSrc, j, cSrc.length - j);
			return buf.toString();
		}
		return strSource;
	}

	/**
	 * 
	 * revert:(字串逆排). <br/>
	 * @param src
	 */
	public static String revert(String src) {
		if (isEmpty(src))
			return "";
		StringBuffer result = new StringBuffer();
		for (int i = src.length() - 1; i >= 0; i--) {
			result.append(src.charAt(i));
		}
		return result.toString();
	}

	/**
	 * 
	 * fillChar:(设置填充字符). <br/>
	 * @param str
	 *            原字符
	 * @param fill
	 *            填充字符
	 * @param digit
	 *            填充后总位数
	 * @param startOrend
	 *            填充在原字符首尾(1,-1)  1:字首填充；2:字尾填充
	 */
	public static String fillChar(String str, String fill, int digit, int startOrend) throws Exception {
		if (!(str.length() > digit)) {
			int slength = str.length();
			int i = digit - slength;
			StringBuffer fillStr = new StringBuffer();
			for (int x = 0; x < i; x++)
				fillStr.append(fill);
			if (startOrend >= 0)
				return fillStr + str;
			else
				return str + fillStr;
		}
		else {
			throw new Exception("fillCharacter Exception");
		}
	}

	/**
	 * 
	 * trimRight:(去除右边多余的空格). <br/>
	 * @param value
	 *            待去右边空格的字符串
	 * @return 去右边空格的字符串
	 */
	public static String trimRight(String value) {
		String result = value;
		if (result == null)
			return result;
		char ch[] = result.toCharArray();
		int endIndex = -1;
		for (int i = ch.length - 1; i > -1; i--) {
			if (Character.isWhitespace(ch[i])) {
				endIndex = i;
			}
			else {
				break;
			}
		}
		if (endIndex != -1) {
			result = result.substring(0, endIndex);
		}
		return result;
	}

	/**
	 * 
	 * trimLeft:(去除左边多余的空格). <br/>
	 * @param value
	 *            待去左边空格的字符串
	 */
	public static String trimLeft(String value) {
		String result = value;
		if (result == null)
			return result;
		char ch[] = result.toCharArray();
		int index = -1;
		for (int i = 0; i < ch.length; i++) {
			if (Character.isWhitespace(ch[i])) {
				index = i;
			}
			else {
				break;
			}
		}
		if (index != -1) {
			result = result.substring(index + 1);
		}
		return result;
	}

	/**
	 * 
	 * combineStringArray:(将字符串数组使用指定的分隔符合并成一个字符串). <br/>
	 * @param array
	 *            字符串数组
	 * @param delim
	 *            分隔符，为null的时候使用""作为分隔符（即没有分隔符）
	 * @return 合并后的字符串
	 */
	public static String combineStringArray(String[] array, String delim) {
		int length = array.length - 1;
		if (delim == null) {
			delim = "";
		}
		StringBuffer result = new StringBuffer(length * 8);
		for (int i = 0; i < length; i++) {
			result.append(array[i]);
			result.append(delim);
		}
		result.append(array[length]);
		return result.toString();
	}

	/**
	 * 
	 * contains:(字符串数组中是否包含指定的字符串). <br/>
	 * @param strings
	 *            字符串数组
	 * @param string
	 *            字符串
	 * @param caseSensitive
	 *            是否大小写敏感
	 * @return 包含时返回true，否则返回false
	 */
	public static boolean contains(String[] strings, String string, boolean caseSensitive) {
		for (int i = 0; i < strings.length; i++) {
			if (caseSensitive == true) {
				if (strings[i].equals(string)) {
					return true;
				}
			}
			else {
				if (strings[i].equalsIgnoreCase(string)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * lowerWord:(将一个字符串由驼峰式命名变成分割符分隔单词). <br/>
	 * 			eg: lowerWord("helloWorld", '-') => "hello-world"
	 * @param cs
	 *            字符串
	 * @param c
	 *            分隔符
	 * @return 转换后字符串
	 */
	public static String lowerWord(CharSequence cs, char c) {
		StringBuilder sb = new StringBuilder();
		int len = cs.length();
		for (int i = 0; i < len; i++) {
			char ch = cs.charAt(i);
			if (Character.isUpperCase(ch)) {
				if (i > 0)
					sb.append(c);
				sb.append(Character.toLowerCase(ch));
			}
			else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * upperWord:(将一个字符串某一个字符后面的字母变成大写). <br/>
	 * 			eg: upperWord("hello-world", '-') => "helloWorld"
	 * @param cs
	 *            字符串
	 * @param c
	 *            分隔符
	 * @return 转换后字符串
	 */
	public static String upperWord(CharSequence cs, char c) {
		StringBuilder sb = new StringBuilder();
		int len = cs.length();
		for (int i = 0; i < len; i++) {
			char ch = cs.charAt(i);
			if (ch == c) {
				do {
					i++;
					if (i >= len)
						return sb.toString();
					ch = cs.charAt(i);
				}
				while (ch == c);
				sb.append(Character.toUpperCase(ch));
			}
			else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * removeHtmlTags:(根据传入的内容，去掉所有html标签). <br/>
	 * @param htmlSource
	 * @return String
	 */
	public static String removeHtmlTags(String htmlSource) {
		return htmlSource.replaceAll("\\<.*?>", "");
	}

	/**
	 * 
	 * removeScript:(去掉script). <br/>
	 * @param htmlSource
	 * @return String
	 * @since 1.0
	 */
	public static String removeScript(String htmlSource) {
		return htmlSource.replaceAll("\\<script.*?/script>", "");
	}

	/**
	 * 
	 * removeHreflink:(去除超链接). <br/>
	 * @param htmlSource
	 * @return String
	 */
	public static String removeHreflink(String htmlSource) {
		htmlSource = htmlSource.replaceAll("\\<a[^>]*>", "");
		htmlSource = htmlSource.replaceAll("\\</a>", "");
		return htmlSource;
	}

	/**
	 * 
	 * removeScriptAndHrefTags:(删除script标签及超链接). <br/>
	 * @param htmlSource
	 * @return String
	 */
	public static String removeScriptAndHrefTags(String htmlSource) {
		htmlSource = removeScript(htmlSource);
		htmlSource = removeHreflink(htmlSource);
		return htmlSource;
	}

	/**
     * 在字符串中替换html标记所用的六个特殊字符：& \ " ' &lt; &gt;
     *
     */
    public static String htmlEncode(String str) {
    	if(null == str){
    		return null;
    	}
        String sTemp = str;
        sTemp = sTemp.replace("&", "&amp;");
        sTemp = sTemp.replace("\"", "&quot;");
        sTemp = sTemp.replace("'", "&#039;");
        sTemp = sTemp.replace( "<", "&lt;");
        sTemp = sTemp.replace(">", "&gt;");
        return sTemp;
    }
	
	/**
	 * 
	 * text:(获取html文档中的文本 并仅提取文本中的前maxLength个 超出部分使用……补充). <br/>
	 * @param html
	 */
    public static String text(String html, int maxLength) {
        String text = removeHtmlTags(html);
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength) + "……";
    }

	/**
	 * 
	 * substringBeforeLast:(根据指定的分隔符，从源字符串查找到最后一个分隔符，然后返回此分隔符前面的内容，如果找不到此分隔符则返回原字符串). <br/>
	 * @param str
	 * @param separator
	 * @return String
	 * @since 1.0
	 */
	public static String substringBeforeLast(String str, String separator) {
		if ((str == null) || (separator == null) || (str.length() == 0) || (separator.length() == 0)) {
			return str;
		}

		int pos = str.lastIndexOf(separator);

		if (pos == -1) {
			return str;
		}

		return str.substring(0, pos);
	}
	
	/**
	 * 
	 * startsWithIgnoreCase:(判断开始部分是否与参数二相同。不区分大小写  ). <br/>
	 * @param str
	 * @param prefix
	 */
	public static boolean startsWithIgnoreCase(String str, String prefix) {
		return startsWith(str, prefix, true);
	}
	
	private static boolean startsWith(String str, String prefix, boolean ignoreCase) {
		if (str == null || prefix == null) {
			return (str == null && prefix == null);
		}
		if (prefix.length() > str.length()) {
			return false;
		}
		return str.regionMatches(ignoreCase, 0, prefix, 0, prefix.length());
	}
	
	/**
	 * 
	 * subString:(从原始字符串中，取得从start字符串开始但不包括start字符串，到end字符串为止其中所有的字符). <br/>
	 *        eg : str= 'aabbccddee',start='bb',end='ee',则此时结果为'ccdd'。 开始及结束字符串必须存在，否则就报错。
	 * 
	 * @param src
	 *            源字符串
	 * @param start
	 *            开始字符串
	 * @param end
	 *            结尾字符串
	 * @return 开始字符串到结尾字符串的中间字符串
	 * @since 1.0
	 */
	public static String subString(String src, String start, String end) {
		src = src.substring(src.indexOf(start)).substring(start.length());
		if (end != null) {
			src = src.substring(0, src.indexOf(end));
		}
		return src;
	}
	
	/**
	 * 
	 * getListFromStart2End:(从给定的内容content中获取给定的start字符串到end字符串的所有内容，其中start和end的配对可以是多处，最终以List结果返回). <br/>
	 * 				eg: 给定的内容content为：adfadfsdfasBBttttCCxaafdsfdsBBddadfasfsfdCCxxxx，<br>
	 * start为:BB<br>
	 * end为：CC<br>
	 * 则返回的list结果包括两条记录：tttt 和 ddadfasfsfd.<br>
	 * 在获取时，end字符串所处的位置一定要大于start所处的位置，否则不以处理，默认来配对不成功，跳出处理的循环。
	 * 
	 * @param content
	 *            待获取回复的内容
	 * @param start
	 *            一个回复的开始分隔字符串
	 * @param end
	 *            一个回复的结束分隔字符串
	 * @param isFirstMainContent
	 *            第一项是否主内容，如论坛的内容和回复的分隔符都是一样的，此时在这个址为true的情况下，第一项不做为后回复，后面的才做为回复
	 * @return List<String>
	 * @since 1.0
	 */
	public static List<String> getListFromStart2End(String content, String start, String end, boolean isFirstMainContent) {
		List<String> replysList = new ArrayList<String>();
		if (content.indexOf(start) < 0) {
			return replysList;
		}
		int index_start = -1;
		int num = 1;
		while ((index_start = content.indexOf(start)) > 0) {
			content = content.substring(index_start);
			int index_end = content.indexOf(end);
			if (index_end > 0) {
				String reply = subString(content, start, end);
				if (!isFirstMainContent || (isFirstMainContent && num > 1)) {
					replysList.add(reply);
				}
				content = content.substring(content.indexOf(end) + end.length());
			}
			else {
				break;
			}
			num++;
		}
		return replysList;
	}
	
	/**
	 * 
	 * checkSubPattern:(找出含？的字符串). <br/>
	 * @param src
	 * @param begin
	 * @param pat
	 * @return int
	 * @since 1.0
	 */
	private static int checkSubPattern(String src, int begin, String pat) {
		boolean isFound = true;
		if (src.length() - begin < pat.length()) {
			return -1;
		}

		for (int i = begin; i < src.length() - pat.length() + 1; i++) {
			for (int j = 0; j < pat.length(); j++) {
				if (pat.charAt(j) != '?' && src.charAt(i + j) != pat.charAt(j)) {
					isFound = false;
					break;
				}
				isFound = true;
			}

			if (isFound) {
				return i;
			}
		}
		return -1;
	}


	/**
	 * 
	 * isMatching:(通配符“?”，“*”的字符串匹配). <br/>
	 * @param src	
	 * 			源字符串
	 * @param pat
	 * 			可包含通配符的字符串表达示
	 * @return boolean
	 * @since 1.0
	 */
	public static boolean isMatching(String src, String pat) {
		String[] sub_p = pat.split("\\*");
		int begin = 0;

		for (int i = 0; i < sub_p.length; i++) {
			begin = checkSubPattern(src, begin, sub_p[i]);			
			if (begin == -1) {
				return false;
			}
			if (i == 0 && pat.charAt(i) != '*' && begin != 0) {
				return false;
			}
			if ((i == sub_p.length - 1) && pat.charAt(i) != '*' && begin != (src.length() - sub_p[i].length())) {
				return false;
			}
		}
		return true;
	}
}
