package com.tydic.traffic.crm.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tydic.traffic.crm.constant.Constant;

/**
 * 拦截器，拦截未登录用户请求
 * @author wlhuang
 *
 */
@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter{
	
	@Bean
	public HandlerInterceptorAdapter getSecurityInterceptor()
	{
		return new SecurityInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());
		addInterceptor.addPathPatterns("/code/*");
		addInterceptor.addPathPatterns("/base/*");
		addInterceptor.addPathPatterns("/saleFile/*");
		addInterceptor.addPathPatterns("/log/*");
		addInterceptor.addPathPatterns("/sale/*");
		addInterceptor.addPathPatterns("/track/*");
		addInterceptor.addPathPatterns("/employee/*");
		addInterceptor.addPathPatterns("/saleTrack/*");
		addInterceptor.addPathPatterns("/sys/*");
		addInterceptor.addPathPatterns("/role/*");
		addInterceptor.addPathPatterns("/user/*");
		addInterceptor.excludePathPatterns("/user/login.html");
		addInterceptor.excludePathPatterns("/user/code");
		addInterceptor.excludePathPatterns("/user/login");
	}
	
	
	private class SecurityInterceptor extends HandlerInterceptorAdapter
	{
		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception
		{
			if (null  == request.getSession().getAttribute(Constant.AUTH_USER))
			{
				return false;
			}
			return true;
		}
	}
}
