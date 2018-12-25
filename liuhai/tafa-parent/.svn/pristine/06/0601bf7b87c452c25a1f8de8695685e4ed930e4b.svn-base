package com.tydic.traffic.tafa.utils.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 验证口令
 *
 */
public class DefaultEmailAuthenticator extends Authenticator {

	private String username;

	private String password;

	public DefaultEmailAuthenticator() {
		super();
	}

	public DefaultEmailAuthenticator(final String username, final String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public PasswordAuthentication getPasswordAuthentication() {

		return new PasswordAuthentication(username, password);

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
