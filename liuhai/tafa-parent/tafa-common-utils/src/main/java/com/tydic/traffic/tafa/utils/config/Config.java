package com.tydic.traffic.tafa.utils.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

/**
 * ClassName:Config <br/>
 * Reason: 相对路径方式读取.properties文件取得配置信息 <br/>
 */
public class Config {

	private static Logger logger = LoggerFactory.getLogger(Config.class);

	private static Hashtable<String, String> ht = new Hashtable<String, String>();

	/**
	 * 
	 * getValue:(取得key对应的的value值). <br/>
	 * @param key
	 * @return
	 * @since 1.0
	 */
	public String getValue(String key) {
		return ht.get(key);
	}

	/**
	 * 
	 * getConfig:(取得properties文件全部的键/值对Hashtable). <br/>
	 * @return
	 * @since 1.0
	 */
	public Hashtable<String, String> getConfig() {
		return ht;
	}
	
	/**
	 * 
	 * Creates a new instance of Config.
	 * 默认以相对路径读取
	 *
	 * @param configName	配置文件名
	 */
	public Config(String configName) {
		_Config(configName, false);
	}
	
	/**
	 * 
	 * Creates a new instance of Config.
	 * 以指定的路径方式加载
	 *
	 * @param configName	//	配置文件名
	 * @param pathType		// 	路径类型（true:绝对路径，false:相对路径）
	 */
	public Config(String configName, boolean pathType) {
		_Config(configName, pathType);
	}
	
	private void _Config(String configFileName, boolean flag){
		if(flag){	// 绝对路径读取properties
			_Config2(configFileName);
		}else{		// 相对路径读取properties			
			_Config1(configFileName);
		}
	}

	/**
	 * 
	 * _Config:(相对路径方式加载properties文件). <br/>
	 * @param configFileName 	// 配置文件名
	 * @since 1.0
	 */
	private void _Config1(String configFileName) {
		loadProperties(Config.class.getClassLoader().getResourceAsStream(configFileName));		

	}
	
	/**
	 * 
	 * _Config2:(绝对路径方式加载properties文件). <br/>
	 * @param filePath	// 配置文件名
	 * @since 1.0
	 */
	private void _Config2(String filePath) {
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(filePath));
			loadProperties(in);
		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		finally {
			if (null != in) {
				try {
					in.close();
				}
				catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}
	}

	/**
	 * 
	 * loadProperties:(InputStream方式加载properties). <br/>
	 * @param is
	 * @since 1.0
	 */
	private void loadProperties(InputStream is) {
		if(null == is){
			logger.warn("file load failure.");
			return;
		}		
		Properties props = new Properties();
		try {
			props.load(is);
			Enumeration<?> en = props.propertyNames();
			String key = "";
			while (en.hasMoreElements()) {
				key = (String) en.nextElement();
				ht.put(key, props.getProperty(key));
			}
		}catch (Exception e) {
			logger.error(e.getMessage());
		}finally {
			try{
				is.close();
			}catch (IOException e){

			}
		}
	}

}
