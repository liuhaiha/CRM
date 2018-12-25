package com.tydic.traffic.tafa.utils.mail;

import org.springframework.beans.factory.DisposableBean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ClassName: PoolThreadMail <br/>
 * Function: 发送邮件连接池. <br/>
 */
public class PoolThreadMail implements DisposableBean{
	// 线程池
	private ExecutorService pool;
	public PoolThreadMail(int poolNum){
		pool = Executors.newScheduledThreadPool(poolNum);
		for (int i = 0; i < poolNum; i++) {
			pool.execute(new TaskMailThread());
		}
	}
	/**
	 *
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	@Override
	public void destroy() throws Exception {
		pool.shutdown();
	}
}

