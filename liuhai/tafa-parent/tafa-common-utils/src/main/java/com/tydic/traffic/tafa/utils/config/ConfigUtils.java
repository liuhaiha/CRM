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
 * ClassName:ConfigUtils<br/>
 * Reason: 相对路径方式读取.properties文件取得配置信息 <br/>
 */
public class ConfigUtils {

    private static Logger logger = LoggerFactory.getLogger(ConfigUtils.class);

    private Hashtable<String, String> ht = new Hashtable<String, String>();

    /**
     * getValue:(取得key对应的的value值). <br/>
     */
    public String getValue(String key) {
        return ht.get(key);
    }

    /**
     * getConfig:(取得properties文件全部的键/值对Hashtable). <br/>
     */
    public Hashtable<String, String> getConfig() {
        return ht;
    }

    /**
     * Creates a new instance of Config.
     * 默认以相对路径读取
     *
     */
    public ConfigUtils(String... configName) {
        _Config(false, configName);
    }

    /**
     * Creates a new instance of Config.
     * 以指定的路径方式加载
     *
     * @param configName //	配置文件名
     * @param pathType   // 	路径类型（true:绝对路径，false:相对路径）
     */
    public ConfigUtils(boolean pathType, String... configName) {
        _Config(pathType, configName);
    }

    private void _Config(boolean flag, String... configFileName) {
        if (flag) {    // 绝对路径读取properties
            _Config2(configFileName);
        } else {        // 相对路径读取properties
            _Config1(configFileName);
        }
    }

    /**
     * _Config:(相对路径方式加载properties文件). <br/>
     *
     */
    private void _Config1(String... configFileName) {
        for (String fileName : configFileName) {

            InputStream inputStream = ConfigUtils.class.getClassLoader().getResourceAsStream(fileName);
            try {
                loadProperties(inputStream);
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {

                }
            }

        }
    }

    /**
     * _Config2:(绝对路径方式加载properties文件). <br/>
     *
     */
    private void _Config2(String... filePath) {
        InputStream in = null;
        try {
            for (String fileName : filePath) {
                in = new BufferedInputStream(new FileInputStream(fileName));
                try {
                    loadProperties(in);
                } finally {
                    try {
                        in.close();
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                    }

                }

            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

    /**
     * loadProperties:(InputStream方式加载properties). <br/>
     *
     */
    private void loadProperties(InputStream is) {
        if (null == is) {
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
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
    }

}
