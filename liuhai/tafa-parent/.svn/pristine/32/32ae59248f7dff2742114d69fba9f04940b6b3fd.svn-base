package com.tydic.traffic.tafa.utils;

import org.springframework.beans.factory.DisposableBean;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ClassName: PoolThread <br/>
 * Function: 线程连接池. <br/>
 * @since 1.0
 */
public class PoolThread implements DisposableBean{
	// 线程池
	private ExecutorService pool;
	public PoolThread(int poolNum,List<String> list){
		pool = Executors.newScheduledThreadPool(poolNum * list.size());
		for (String string : list) {
			try {
				Class<Runnable> runnableClass =  (Class<Runnable>) Class.forName(string);
				for (int i = 0; i < poolNum; i++) {
					pool.execute(runnableClass.newInstance());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 线程池回收
	 */
	@Override
	public void destroy() throws Exception {
		pool.shutdown();
	}

}

