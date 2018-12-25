package com.tydic.traffic.crm;

import javax.servlet.MultipartConfigElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * SpringBoot方式启动类
 *
 */
@SpringBootApplication(scanBasePackages={"com.tydic"})
@EnableScheduling
public class Application {

    protected final static Logger logger = LoggerFactory.getLogger(Application.class);


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        logger.info("Application is sussess!");
    }
    
    // 设置 ajax 跨域
    @Bean
    public WebMvcConfigurer coresConfigurer(){
		return  new WebMvcConfigurerAdapter() {
			@SuppressWarnings("unused")
			public void addCorsMapping(CorsRegistry registry){
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
    }
    @Bean
    public MultipartConfigElement multipartConfigElement(){
    	MultipartConfigFactory factory = new  MultipartConfigFactory();
    	factory.setMaxFileSize("20MB");
    	factory.setMaxRequestSize("20MB");
    	return factory.createMultipartConfig();
    }
    
}
