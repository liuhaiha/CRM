package com.tydic.traffic.tafa.utils.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ClassName: SendMail
 * Function: 发送邮件.
 */
public class SendMail {
	/**
	 * 邮箱服务器
	 */
	private  MailServer mailServer ;

	private  MailSender mailSender ;
	/**
	 * 配置文件
	 */
	private static String mailPatameterFilePaht;
	/**
	 * 发送邮件名字
	 */
	private static String mailName;
	/**
	 * 发送邮件密码
	 */
	private static String mailPwd;
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());


	/**
	 * 
	 * Creates a new instance of TaskMailThread.
	 *
	 */
	public void init(){
		mailServer = new MailServer(createMailConfig(mailPatameterFilePaht));
		mailSender = mailServer.getMailSender(mailName, mailPwd);
	}
	/**
	 * 
	 * createMailConfig:初始化MailConfig. <br/>
	 * @since 1.0
	 */
	private MailConfig createMailConfig(String mailPatameterFilePaht)throws MailException{
		Resource resource = new FileSystemResource(mailPatameterFilePaht);
		InputStream is = null;
		Properties props = new Properties();
		try {
			is = resource.getInputStream();
			props.load(is);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
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
			logger.error(e.getMessage(),e);
			throw new MailException(e.getMessage() ,e);
		}
		return mailConfig;
	}
	/**
	 * 
	 * send:发送. <br/>
	 */
	public synchronized boolean send(Mail mail){
		try {
			mailSender.send(mail);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return false;
	}
	public String getMailPatameterFilePaht() {
		return mailPatameterFilePaht;
	}
	public void setMailPatameterFilePaht(String mailPatameterFilePaht) {
		SendMail.mailPatameterFilePaht = mailPatameterFilePaht;
	}
	public String getMailName() {
		return mailName;
	}
	public void setMailName(String mailName) {
		SendMail.mailName = mailName;
	}
	public String getMailPwd() {
		return mailPwd;
	}
	public void setMailPwd(String mailPwd) {
		SendMail.mailPwd = mailPwd;
	}
	
	
}

