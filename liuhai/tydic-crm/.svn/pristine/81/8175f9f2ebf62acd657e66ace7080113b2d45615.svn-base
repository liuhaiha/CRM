package com.tydic.traffic.crm.config;

import java.io.File;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


// @Configuration
public class WebConfig {

    @Bean
    @Primary
    public TomcatEmbeddedServletContainerFactoryBeanPostProcessor TomcatEmbeddedServletContainerFactoryBeanPostProcessor(
    		@Autowired ApplicationContext context){
        return new TomcatEmbeddedServletContainerFactoryBeanPostProcessor(context);
    }

    private static class  TomcatEmbeddedServletContainerFactoryBeanPostProcessor implements BeanPostProcessor{
    	private static final String DOC_ROOT="server.tomcat.documentRoot";
    	private ApplicationContext context;
    	
    	public TomcatEmbeddedServletContainerFactoryBeanPostProcessor(ApplicationContext context) {
			this.context = context;
		}

		@Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            if(bean instanceof  TomcatEmbeddedServletContainerFactory){
                if(!context.getEnvironment().containsProperty(DOC_ROOT)){
                	throw new BeanCreationException("配置文件中缺少配置项,server.tomcat.documentRoot");
                }
            	TomcatEmbeddedServletContainerFactory tomcat=(TomcatEmbeddedServletContainerFactory)bean;
                tomcat.setDocumentRoot(new File(context.getEnvironment().getProperty(DOC_ROOT)));
            }
            return bean;
        }
    }

}