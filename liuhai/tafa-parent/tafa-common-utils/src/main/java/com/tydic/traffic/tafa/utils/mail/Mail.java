package com.tydic.traffic.tafa.utils.mail;

import com.tydic.traffic.tafa.utils.date.DateUtil;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class Mail {

	private String id; // 唯一区分邮件的序号

	private String nickname;

	private List<Address> senderAddressList = new ArrayList<Address>(); // 发件人

	private List<Address> toAddressList = new ArrayList<Address>(); // 收件人

	private List<Address> ccAddressList = new ArrayList<Address>(); // 抄送人

	private List<Address> bccAddressList = new ArrayList<Address>(); // 暗送人

	private String subject; // 主题

	private Date date; // 日期

	private List<File> attachment = new ArrayList<File>(); // 附件

	private Map<String, File> inlineMap = new HashMap<String, File>(); // 内嵌文件

	private String text; // 邮件正文

	private MailType mailType = MailType.TXT; // 邮件正文类型, 默认纯文本

	private List<Flags.Flag> mailFlagList = new ArrayList<Flags.Flag>(); // 邮件标记

	private String htmlFileName;

	public boolean hasFile() {
		return !attachment.isEmpty() || !inlineMap.isEmpty();
	}

	public static String getEmailAddress(Address[] addresses) {
		return getEmailAddress(Arrays.asList(addresses));
	}

	private static String getEmailAddress(List<Address> addressList) {
		StringBuilder sb = new StringBuilder();
		for (Address address : addressList) {
			InternetAddress internetAddress = (InternetAddress) address;
			sb.append(internetAddress.getAddress()).append(",");
		}
		if (sb.charAt(sb.length() - 1) == ',') {
			sb = sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	private List<String> getEmailList(List<Address> addressList) {
		List<String> emailList = new ArrayList<String>();
		for (Address address : addressList) {
			InternetAddress internetAddress = (InternetAddress) address;
			emailList.add(internetAddress.getAddress());
		}
		return emailList;
	}

	private String getNickName(List<Address> addressList) {
		StringBuilder sb = new StringBuilder();
		for (Address address : addressList) {
			InternetAddress internetAddress = (InternetAddress) address;
			sb.append(internetAddress.getPersonal()).append(",");
		}
		if (sb.charAt(sb.length() - 1) == ',') {
			sb = sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	public boolean hasSender() {
		return !senderAddressList.isEmpty();
	}

	public void addSender(String sender) throws MailException {
		addSender(sender, sender);
	}

	public void addSender(String sender, String nickname) throws MailException {
		try {
			senderAddressList.add(new InternetAddress(sender, nickname));
		}
		catch (UnsupportedEncodingException e) {
			throw new MailException(e.getMessage(), e);
		}
	}

	public void addSender(Address[] addresses) {
		senderAddressList.addAll(Arrays.asList(addresses));
	}

	public String getSenderEmail() {
		return getEmailAddress(senderAddressList);
	}

	public String getSenderNickName() {
		return getNickName(senderAddressList);
	}

	public void addTo(String to) throws MailException {
		addTo(to, to);
	}

	public void addTo(String to, String nickname) throws MailException {
		try {
			toAddressList.add(new InternetAddress(to, nickname));
		}
		catch (UnsupportedEncodingException e) {
			throw new MailException(e.getMessage(), e);
		}
	}

	public void addTo(Address[] addresses) {
		if (addresses == null) {
			return;
		}
		toAddressList.addAll(Arrays.asList(addresses));
	}

	public void addCc(String cc) throws MailException {
		addCc(cc, cc);
	}

	public void addCc(String cc, String nickname) throws MailException {
		try {
			ccAddressList.add(new InternetAddress(cc, nickname));
		}
		catch (UnsupportedEncodingException e) {
			throw new MailException(e.getMessage(), e);
		}
	}

	public void addCc(Address[] addresses) {
		if (addresses == null) {
			return;
		}
		ccAddressList.addAll(Arrays.asList(addresses));
	}

	public void addBcc(String bcc) throws MailException {
		addBcc(bcc, bcc);
	}

	public void addBcc(String bcc, String nickname) throws MailException {
		try {
			bccAddressList.add(new InternetAddress(bcc, nickname));
		}
		catch (UnsupportedEncodingException e) {
			throw new MailException(e.getMessage(), e);
		}
	}

	public void addBcc(Address[] addresses) {
		if (addresses == null) {
			return;
		}
		bccAddressList.addAll(Arrays.asList(addresses));
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<File> getAttachment() {
		return attachment;
	}

	public void setAttachment(List<File> attachment) {
		this.attachment = attachment;
	}

	public void addAttachment(File f) {
		this.attachment.add(f);
	}

	public void addInline(String name, File f) {
		this.inlineMap.put(name, f);
	}

	public Map<String, File> getInline() {
		return inlineMap;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		this.mailType = MailType.TXT;
	}

	public void setHtml(String html) {
		this.text = html;
		this.mailType = MailType.HTML;
	}

	public Date getDate() {
		return date;
	}

	public String getDate2() {
		return DateUtil.dateToString(date, "yyyy-MM-dd HH:mm");
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void addMailFlag(Flags.Flag flag) {
		mailFlagList.add(flag);
	}

	public Flags getMailFlags() {
		Flags flags = new Flags();
		for (Flags.Flag flag : mailFlagList) {
			flags.add(flag);
		}
		return flags;
	}

	public boolean isNew() {
		for (Flags.Flag flag : mailFlagList) {
			if (flag == Flags.Flag.SEEN) {
				return false;
			}
		}
		return true;
	}

	public boolean isHtml() {
		return mailType == MailType.HTML;
	}

	public String getHtmlFileName() {
		return htmlFileName;
	}

	public void setHtmlFileName(String htmlFileName) {
		this.htmlFileName = htmlFileName;
	}

	public Address[] getSenderAddresses() {
		return senderAddressList.toArray(new Address[0]);
	}

	public List<Address> getSenderAddressList() {
		return senderAddressList;
	}

	public List<Address> getToAddressList() {
		return toAddressList;
	}

	public List<String> getToEmailList() {

		return getEmailList(toAddressList);
	}

	public List<Address> getCcAddressList() {
		return ccAddressList;
	}

	public List<Address> getBccAddressList() {
		return bccAddressList;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public MailType getMailType() {
		return mailType;
	}

}
