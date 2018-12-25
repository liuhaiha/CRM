package com.tydic.traffic.crm.init;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.tydic.traffic.crm.common.cache.SysCodeCache;

@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent>{

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		SysCodeCache.init();
	}

}
