package com.tydic.traffic.tafa.utils.mail;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Set;

/**
 * ClassName:MailSender <br/>
 * Function: 邮件发送者<br/>
 */
public class MailSender extends MailProcessor {
	private static final Logger logger = LoggerFactory.getLogger(MailSender.class);

	private Transport transport;

	public MailSender(Session session) throws MailException {
		super(session);
	}

	public void close() throws MailException {
		if (transport != null) {
			try {
				transport.close();
			}
			catch (MessagingException e) {
				logger.error(e.getMessage(), e);
				throw new MailException(e.getMessage(), e);
			}
		}
	}

	public void send(Mail mail) throws MailException {
		try {
			Transport.send(toMessage(mail));
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new MailException(e.getMessage(), e);
		}
	}

	private Message toMessage(Mail mail) throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = new MimeMessage(session);

		message.setFlags(mail.getMailFlags(), true);
		if (mail.hasSender()) {
			message.addFrom(mail.getSenderAddresses());
		}
		else {
			InternetAddress fromAddress = new InternetAddress(super.getCurrentEmailAccount(), mail.getNickname());
			message.setFrom(fromAddress);
		}
		for (Address toAddress : mail.getToAddressList()) {
			message.addRecipient(Message.RecipientType.TO, toAddress);
		}
		for (Address ccAddress : mail.getCcAddressList()) {
			message.addRecipient(Message.RecipientType.CC, ccAddress);
		}
		for (Address bccAddress : mail.getBccAddressList()) {
			message.addRecipient(Message.RecipientType.BCC, bccAddress);
		}
		message.setSubject(mail.getSubject());
		message.setSentDate(mail.getDate());

		setMessageBody(message, mail);

		return message;
	}

	private void setMessageBody(Message message, Mail mail) throws MessagingException, UnsupportedEncodingException {
		if (!mail.hasFile()) {
			message.setContent(super.encode(mail.getText()), mail.getMailType().value() + ";charset=UTF-8");
			return;
		}
		MimeMultipart multipart = new MimeMultipart();
		message.setContent(multipart);

		MimeBodyPart bodyPart = new MimeBodyPart();
		bodyPart.setContent(super.encode(mail.getText()), mail.getMailType().value() + ";charset=UTF-8");
		multipart.addBodyPart(bodyPart);

		for (File attachment : mail.getAttachment()) {
			MimeBodyPart attachmentPart = new MimeBodyPart();
			attachmentPart.setDataHandler(new DataHandler(new FileDataSource(attachment)));
			attachmentPart.setDisposition(Part.ATTACHMENT);
			attachmentPart.setFileName(super.encode(attachment.getName()));
			multipart.addBodyPart(attachmentPart);
		}

		if (mail.getInline().isEmpty()) {
			return;
		}
		Set<String> inlineNameSet = mail.getInline().keySet();
		for (String inlineName : inlineNameSet) {
			MimeBodyPart inlinePart = new MimeBodyPart();
			inlinePart.setDataHandler(new DataHandler(new FileDataSource(mail.getInline().get(inlineName))));
			inlinePart.setDisposition(Part.INLINE);
			inlinePart.setContentID(inlineName);
			multipart.addBodyPart(inlinePart);
		}
	}
}
