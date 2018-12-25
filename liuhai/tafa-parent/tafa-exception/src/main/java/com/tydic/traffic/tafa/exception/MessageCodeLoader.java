package com.tydic.traffic.tafa.exception;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

import com.tydic.traffic.tafa.exception.result.CodeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.Assert;

/**
 * 从资源文件中读取异常信息
 * 
 * <p>默认会从根类路径(包括第三方jar包)读取如下配置文件
 * <ul>
 * 	<li>exceptionMessages_zh_CN.properties</li>
 * 	<li>exceptionMessages_zh.properties</li>
 * 	<li>exceptionMessages.properties</li>
 * </ul>
 * @author acer
 * @version
 * @since 1.5.1
 * @see
 */
public class MessageCodeLoader {

	private static final Logger logger=LoggerFactory.getLogger(MessageCodeLoader.class);

	public static final String CODE_UNIT="META-INF/codeunit";
	public static final String CODE_UNIT_PREFIX="codeunit.prefix";

	private MessageCodeLoader() {
	}

	private static void checkExistRepeatPrefix() {
		Map<String, String> codeUnitMap = new HashMap<>();
		try {
			ClassLoader classLoader = getDefaultClassLoader();
			Enumeration<URL> urls = classLoader.getResources(CODE_UNIT);
			while (urls.hasMoreElements()) {
				URL url = (URL) urls.nextElement();
				Properties properties = PropertiesLoaderUtils.loadProperties(new EncodedResource(new UrlResource(url),
						Charset.forName("utf-8")));
				String prefix = String.valueOf(properties.get(CODE_UNIT_PREFIX));
				Assert.hasLength(prefix, "the path =>" + url.toString() + CODE_UNIT_PREFIX + " value should not be empty");
				String urlPath = codeUnitMap.get(prefix);
				if (urlPath != null && !urlPath.equals(url.toString())) {
					throw new BizException(CodeUnit.UNKNOWN_ERROR, "存在重复的异常提示信息前缀在" +
							urlPath + "," + url.toString() + " 两个文件之间");
				}
				codeUnitMap.put(prefix, url.toString());
			}
		} catch (IOException e) {
			throw new BizException(CodeUnit.UNKNOWN_ERROR, e, "检测codeunit文件中是否存在重复的prefix出错");
		}
	}


	public static Map<String,String> load(){
		List<String> filenames = new ArrayList<String>(7);
		filenames.addAll(calculateFilenamesForLocale(Constants.PRE_FLAG, Locale.CHINA));
		filenames.add(Constants.PRE_FLAG);

		if(logger.isInfoEnabled()){
			logger.info("read default exception message file. the files:{} " +
					"will be add to resource bundle",Arrays.toString( filenames.toArray() ));
		}

		//检测codeunit文件中是否存在重复的prefix
		checkExistRepeatPrefix();

		return loadMessages(filenames);
	}

	/**
	 * Calculate the filenames for the given bundle basename and Locale,
	 * appending language code, country code, and variant code.
	 * E.g.: basename "messages", Locale "de_AT_oo" -> "messages_de_AT_OO",
	 * "messages_de_AT", "messages_de".
	 * <p>Follows the rules defined by {@link Locale#toString()}.
	 * @param basename the basename of the bundle
	 * @param locale the locale
	 * @return the List of filenames to check
	 */
	private static List<String> calculateFilenamesForLocale(String basename, Locale locale) {
		List<String> result = new ArrayList<String>(3);
		String language = locale.getLanguage();
		String country = locale.getCountry();
		String variant = locale.getVariant();
		StringBuilder temp = new StringBuilder(basename);

		temp.append('_');
		if (language.length() > 0) {
			temp.append(language);
			result.add(0, temp.toString());
		}

		temp.append('_');
		if (country.length() > 0) {
			temp.append(country);
			result.add(0, temp.toString());
		}

		if (variant.length() > 0 && (language.length() > 0 || country.length() > 0)) {
			temp.append('_').append(variant);
			result.add(0, temp.toString());
		}

		return result;
	}
	
	/**
	 * 添加资源
	 * @param filenames 资源文件名称
	 */
	private static Map<String,String> loadMessages(List<String> filenames) {
		List<ResourceBundle> resources = new ArrayList<ResourceBundle>();
		ClassLoader classLoader=getDefaultClassLoader();

		Map<String,String> msgMap=new HashMap<String,String>();
		Map<String,Properties> codeUnitMap=new HashMap<String,Properties>();
		for (String filename : filenames) {
			try {
				Enumeration<URL> urls= classLoader.getResources(filename+Constants.SUF_FLAG);
				while (urls.hasMoreElements()) {
					URL url = (URL) urls.nextElement();

					String path=url.toString();
					path=path.substring(0,path.lastIndexOf("/"));
					if(!path.endsWith("/")){path=path+"/";}
					path=path+CODE_UNIT;

					if(!codeUnitMap.containsKey(path)){
						URL url2=new URL(path);
						Properties p = PropertiesLoaderUtils.loadProperties(new UrlResource(url2));
						codeUnitMap.put(path,p);
					}

					Properties codeUnitProps=codeUnitMap.get(path);
					String prefix=String.valueOf(codeUnitProps.get(CODE_UNIT_PREFIX));

					Properties properties = PropertiesLoaderUtils.loadProperties(new UrlResource(url));
					Enumeration<Object> en=properties.keys();
					while (en.hasMoreElements()){
						Object key=en.nextElement();
						msgMap.put(prefix+":"+key,String.valueOf(properties.get(key)));
					}

					if(logger.isInfoEnabled()){
						logger.info("get resource with the url [java.net.URL] ===>{}",url.toString());
					}
				}
			}catch (IOException e){
				throw new BizException(CodeUnit.UNKNOWN_ERROR,e,"加载异常提示信息资源文件出错");
			}
		}

		return msgMap;
	}
	
	/**
	 * Return the default ClassLoader to use: typically the thread context
	 * ClassLoader, if available; the ClassLoader that loaded the ClassUtils
	 * class will be used as fallback.
	 * <p>Call this method if you intend to use the thread context ClassLoader
	 * in a scenario where you absolutely need a non-null ClassLoader reference:
	 * for example, for class path resource loading (but not necessarily for
	 * {@code Class.forName}, which accepts a {@code null} ClassLoader
	 * reference as well).
	 * @return the default ClassLoader (never {@code null})
	 * @see Thread#getContextClassLoader()
	 */
	public static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		}
		catch (Throwable ex) {
			logger.error("Cannot access thread context ClassLoader - falling back to system class loader...",ex);
		}
		if (cl == null) {
			// No thread context class loader -> use class loader of this class.
			cl = MessageCodeLoader.class.getClassLoader();
		}
		return cl;
	}
}
