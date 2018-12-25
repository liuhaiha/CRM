package com.tydic.traffic.tafa.utils.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * ClassName: TaskMailThread
 */
public class TaskMailThread extends Thread {
	// 任务队列
	private static BlockingQueue<MailBean> queue = new LinkedBlockingQueue<MailBean>();

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private SendMail sendMail;
	/**
	 * 失败后可以重 发次数
	 */
	private static int allowSendNum = 1;

	public TaskMailThread(){
		/**
		 * 初始化
		 */
		sendMail = new SendMail();
		sendMail.init();
	}
	/**
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		MailBean mailBean = null;
		while (!Thread.currentThread().isInterrupted()) {
			try {
				mailBean = TaskMailThread.queue.take();

			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
				continue;
			}

			String body = null;
			//获取模板数据
			try {
				body = ReadMVByVelocity.readMv(mailBean.getTemplatePath(), mailBean.getContext());
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				continue;
			}

			Mail mail = new Mail();
			mail.setHtml(body);
			mail.setSubject(mailBean.getTitle());
			//对收件人进行处理 @fzf
			if(mailBean.getToMail() != null && mailBean.getToMail() != ""){
				mail.addTo(mailBean.getToMail());
			}
			for(String to : mailBean.getToMailList()){
				mail.addTo(to);
			}
			if(null != mailBean.getFile()){
				mail.addAttachment(mailBean.getFile());
			}
			
			//发送
			boolean success = sendMail.send(mail);
			//发送失败
			if(!success){
				if(mailBean.getSendErrorNum() < allowSendNum){
					mailBean.setSendErrorNum(mailBean.getSendErrorNum() + 1);
					TaskMailThread.queue.add(mailBean);
				}else{
					logger.warn("邮件发送失败, mail:"+mailBean.getToMail());
				}
			}else{
				logger.info("邮件发送成功, mail:"+mailBean.getToMail());
			}

		}

	}

	public void setAllowSendNum(int allowSendNum) {
		TaskMailThread.allowSendNum = allowSendNum;
	}
	public static BlockingQueue<MailBean> getQueue() {
		return queue;
	}

}

