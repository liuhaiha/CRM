package com.tydic.traffic.tafa.utils.mail;

import javax.mail.Session;
import java.util.Properties;

public class MailServer {
	
	private MailConfig mailConfig;
	
	public MailServer(MailConfig mailConfig){
		this.mailConfig = mailConfig;
	}
	
	private Session getSession(final String username, final String password){
		DefaultEmailAuthenticator auth = new DefaultEmailAuthenticator(username, password);
		Properties props = mailConfig.getProperties();
		props.put("mail.smtp.user", username);
		props.put("mail.smtp.password", password);
		String currentEmailAccount = username;
		if(username.indexOf("@") == -1){
			currentEmailAccount += "@" + mailConfig.getMailSuffix();
		}
		props.put("currentEmailAccount", currentEmailAccount);
		props.put("username", username);
		props.put("password", password);
		return Session.getInstance(props, auth);
	}
	
	public MailReceiver getMailReceiver(final String username, final String password)throws MailException{
		return new MailReceiver(getSession(username, password));
	}
	
	public MailSender getMailSender(final String username, final String password)throws MailException{
		return new MailSender(getSession(username, password));
	}

}
