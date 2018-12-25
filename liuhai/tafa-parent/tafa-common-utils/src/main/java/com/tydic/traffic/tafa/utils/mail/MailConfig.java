package com.tydic.traffic.tafa.utils.mail;

import java.util.Properties;


public class MailConfig {	
	//影响发送邮件的配置
	private String sendProtocol = "smtp";
	private String sendHost = "smtp.126.com";
	private int sendPort = 25;
	private boolean sendAuth = true;
	private int sendTimeout = 300;
	
	//影响接收邮件的配置
	private String receiveProtocol = "pop3";
	private String receiveHost = "pop.126.com";
	private int receivePort = 110;
	
	private String mailEncoding = "UTF-8"; //邮件编码
	private String mailSuffix = ".eml"; //邮箱后缀
	private String tempDir = ""; //存放上传或下载文件的临时路径
	private String attachmentDir = ""; //存放上传或下载附件的临时路径
	
	public Properties getProperties(){
		Properties props = new Properties();
		
		//设置接收邮件的配置
		props.put("mail.store.protocol", receiveProtocol);
		props.put("mail.pop3.host", receiveHost);
		props.put("mail.pop3.port", receivePort);
		
		//设置发送邮件的配置
		props.put("mail.transport.protocol", sendProtocol);
		props.put("mail.smtp.host", sendHost);
		props.put("mail.smtp.port", sendPort);
		props.put("mail.smtp.auth", sendAuth);
		props.put("mail.smtp.timeout", sendTimeout);
		
		//自定义配置
		props.put("mailEncoding", mailEncoding);
		props.put("mailSuffix", mailSuffix);
		props.put("tempDir", tempDir);
		props.put("attachmentDir", attachmentDir);
		
		return props;
	}
	
	public String getSendProtocol() {
		return sendProtocol;
	}

	public void setSendProtocol(String sendProtocol) {
		this.sendProtocol = sendProtocol;
	}

	public String getSendHost() {
		return sendHost;
	}

	public void setSendHost(String sendHost) {
		this.sendHost = sendHost;
	}

	public int getSendPort() {
		return sendPort;
	}

	public void setSendPort(int sendPort) {
		this.sendPort = sendPort;
	}

	public boolean isSendAuth() {
		return sendAuth;
	}

	public void setSendAuth(boolean sendAuth) {
		this.sendAuth = sendAuth;
	}

	public int getSendTimeout() {
		return sendTimeout;
	}

	public void setSendTimeout(int sendTimeout) {
		this.sendTimeout = sendTimeout;
	}

	public String getReceiveProtocol() {
		return receiveProtocol;
	}

	public void setReceiveProtocol(String receiveProtocol) {
		this.receiveProtocol = receiveProtocol;
	}

	public String getReceiveHost() {
		return receiveHost;
	}

	public void setReceiveHost(String receiveHost) {
		this.receiveHost = receiveHost;
	}

	public int getReceivePort() {
		return receivePort;
	}

	public void setReceivePort(int receivePort) {
		this.receivePort = receivePort;
	}

	public String getMailEncoding() {
		return mailEncoding;
	}

	public void setMailEncoding(String mailEncoding) {
		this.mailEncoding = mailEncoding;
	}

	public String getMailSuffix() {
		return mailSuffix;
	}

	public void setMailSuffix(String mailSuffix) {
		this.mailSuffix = mailSuffix;
	}

	public String getTempDir() {
		return tempDir;
	}

	public void setTempDir(String tempDir) {
		this.tempDir = tempDir;
	}
	
	public String getAttachmentDir() {
		return attachmentDir;
	}
	
	public void setAttachmentDir(String attachmentDir) {
		this.attachmentDir = attachmentDir;
	}
}
