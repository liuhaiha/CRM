package com.tydic.traffic.tafa.exception;

import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.Map;

/**
 * Created by acer on 2017-06-13.
 */
@Configuration
public class HandlerExceptionResolverAutoConfiguration {
   private static final Logger logger= LoggerFactory.getLogger(HandlerExceptionResolverAutoConfiguration.class);

   @Bean
   public HandlerExceptionResolver handlerExceptionResolver(){
       Map<String,String> map=MessageCodeLoader.load();
       if(logger.isInfoEnabled()){
           logger.info("from the classpath load message resources =>{}",
                   Joiner.on(",").withKeyValueSeparator("=").join(map));
       }
       CustomExceptionHandler exceptionHandler=new CustomExceptionHandler(map);
       return exceptionHandler;
   }

}
