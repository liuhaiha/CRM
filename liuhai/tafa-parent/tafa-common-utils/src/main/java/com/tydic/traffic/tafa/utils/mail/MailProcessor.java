package com.tydic.traffic.tafa.utils.mail;

import javax.mail.Session;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class MailProcessor {
	
	protected Session session;
	
	public MailProcessor(Session session){
		this.session = session;
	}
	
	public String getCurrentEmailAccount(){
		return session.getProperty("currentEmailAccount");
	}
	
	public String getLocalAttachmentPath(){
		String tempDir = session.getProperty("tempDir");
		String username = session.getProperty("username");
		return tempDir + File.separator + username;
	}
	
	public String getMailEncoding(){
		return session.getProperty("mailEncoding"); 
	}
	
	public String getUsername(){
		return session.getProperty("username"); 
	}
	
	public String getPassword(){
		return session.getProperty("password"); 
	}
	
	public String getSendHost(){
		return session.getProperty("mail.smtp.host"); 
	}
	
	protected String encode(String s)throws UnsupportedEncodingException {
//		return new String(s.getBytes(getMailEncoding()), "ISO-8859-1");
		return new String(s.getBytes(), Charset.forName(getMailEncoding()));
	}
	
	protected String decode(String s)throws UnsupportedEncodingException {
//		return new String(s.getBytes("ISO-8859-1"), getMailEncoding());
		return new String(s.getBytes(), Charset.forName(getMailEncoding()));
	}
	
}
