package com.tydic.traffic.tafa.utils.mail;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.Properties;

/**
 * ClassName: ReadMVByVelocity <br/>
 * Function: 通过velocity读取模板数据. <br/>
 */
public class ReadMVByVelocity {
	private static Properties properties = null;
	/**
	 * 初始化参数
	 */
	static{
		properties = new Properties();
		//"设置vm存放路径，这里设置到放在目录。"
		properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, System.getProperty("user.dir"));
		properties.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");     
		properties.setProperty(Velocity.INPUT_ENCODING, "UTF-8");   
	}
	/**
	 * 
	 * readMv:获取邮件内容. <br/>
	 */
	public static String readMv(String path , VelocityContext context){
		VelocityEngine velocity = new VelocityEngine();
		velocity.init(properties);  
		Template template = velocity.getTemplate(path, "UTF-8");
		StringWriter stringWriter= new StringWriter();
		template.merge(context, stringWriter);
		return stringWriter.toString();
	}
}

