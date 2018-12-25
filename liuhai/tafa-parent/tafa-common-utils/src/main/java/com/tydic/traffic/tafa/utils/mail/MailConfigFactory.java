package com.tydic.traffic.tafa.utils.mail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class MailConfigFactory {
	
	public static MailConfig createMailConfig()throws MailException{
		return createMailConfig(MailConfigFactory.class);
	}
	
	/**
	 * 
	 * createMailConfig:(基于mail.properties方式配置邮件相关参数). <br/>
	 * @param clazz
	 * @return
	 * @throws MailException
	 * @since 1.0
	 */
	public static MailConfig createMailConfig(Class<?> clazz)throws MailException{
		InputStream is = clazz.getResourceAsStream("/mail.properties");
		Properties props = new Properties();
		try {
			props.load(is);
		} catch (IOException e) {
			throw new MailException(e.getMessage() ,e);
		}
		MailConfig mailConfig = new MailConfig();
		mailConfig.setSendProtocol(props.getProperty("mail.send_protocol"));
		mailConfig.setSendHost(props.getProperty("mail.send_host"));
		mailConfig.setSendPort(Integer.parseInt(props.getProperty("mail.send_port")));
		if(props.getProperty("mail.send_auth").equals("true")){
			mailConfig.setSendAuth(true);
		}else{
			mailConfig.setSendAuth(false);
		}
		mailConfig.setSendTimeout(Integer.parseInt(props.getProperty("mail.send_timeout")));
		
		mailConfig.setReceiveProtocol(props.getProperty("mail.receive_protocol"));
		mailConfig.setReceiveHost(props.getProperty("mail.receive_host"));
		mailConfig.setReceivePort(Integer.parseInt(props.getProperty("mail.receive_port")));
		
		mailConfig.setMailEncoding(props.getProperty("mail.encoding"));
		mailConfig.setMailSuffix(props.getProperty("mail.suffix"));
		mailConfig.setTempDir(props.getProperty("mail.tempdir"));
		mailConfig.setAttachmentDir(props.getProperty("mail.attachmentdir"));
		
		try {
			is.close();
		} catch (IOException e) {
			throw new MailException(e.getMessage() ,e);
		}
		return mailConfig;
	}

	public static MailConfig createMailConfig(Map<String, String> mailCfg)throws MailException{
		if(null == mailCfg || mailCfg.isEmpty()){
			throw new IllegalArgumentException("Wrong number of mail configuration parameters, pls check!");
		}
		
		MailConfig mailConfig = new MailConfig();		
		
		mailConfig.setSendProtocol(mailCfg.get("mail.send_protocol"));
		mailConfig.setSendHost(mailCfg.get("mail.send_host"));
		mailConfig.setSendPort(Integer.parseInt(mailCfg.get("mail.send_port")));
		if(mailCfg.get("mail.send_auth").equals("true")){
			mailConfig.setSendAuth(true);
		}else{
			mailConfig.setSendAuth(false);
		}
		mailConfig.setSendTimeout(Integer.parseInt(mailCfg.get("mail.send_timeout")));
		
		mailConfig.setReceiveProtocol(mailCfg.get("mail.receive_protocol"));
		mailConfig.setReceiveHost(mailCfg.get("mail.receive_host"));
		mailConfig.setReceivePort(Integer.parseInt(mailCfg.get("mail.receive_port")));
		
		mailConfig.setMailEncoding(mailCfg.get("mail.encoding"));
		mailConfig.setMailSuffix(mailCfg.get("mail.suffix"));
		mailConfig.setTempDir(mailCfg.get("mail.tempdir"));
		mailConfig.setAttachmentDir(mailCfg.get("mail.attachmentdir"));
		
		return mailConfig;
	}
}
