package com.tydic.traffic.tafa.utils.mail;

import org.apache.velocity.VelocityContext;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: MailBean
 * Function: 发送邮件bean.
 */
public class MailBean {

	/**
	 * 邮箱标题
	 */
	private String title;

	/**
	 * 模板地址
	 */
	private String templatePath;

	/**
	 * 替换模板里面的参数
	 */
	private VelocityContext context;

	/**
	 * 发给谁
	 */
	private String toMail;
	
	/**
	 * 接收人列表  @fzf
	 * */
	private List<String> toMailList = new ArrayList<String>();

	/**
	 * 发送失败参数
	 */
	private int sendErrorNum;

	/**
	 * 附件
	 */
	private File file;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public VelocityContext getContext() {
		return context;
	}

	public void setContext(VelocityContext context) {
		this.context = context;
	}

	public String getToMail() {
		return toMail;
	}

	public void setToMail(String toMail) {
		this.toMail = toMail;
	}
	
	public List<String> getToMailList() {
		return toMailList;
	}

	
	public void setToMailList(List<String> toMailList) {
		this.toMailList = toMailList;
	}

	public int getSendErrorNum() {
		return sendErrorNum;
	}

	public void setSendErrorNum(int sendErrorNum) {
		this.sendErrorNum = sendErrorNum;
	}
	
	/**
	 * 添加到收件人列表中
	 * */
	public void addToList(String to){
		this.toMailList.add(to);
	}
}
