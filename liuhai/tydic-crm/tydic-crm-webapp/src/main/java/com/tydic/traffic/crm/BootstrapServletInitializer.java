package com.tydic.traffic.crm;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * war Web程序启动类
 *
 */
@SpringBootApplication(scanBasePackages={"com.tydic"})
public class BootstrapServletInitializer extends SpringBootServletInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
//        if(logger.isInfoEnabled()){
//            logger.info("BootstrapServletInitializer is startup");
//        }
        super.onStartup(servletContext);
    }

}
