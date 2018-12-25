package com.tydic.traffic.tafa.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * @author acer
 * @since 2017-06-24
 */
public class WebContext {

	private static final ThreadLocal<WebContext> LOCAL = new ThreadLocal<WebContext>() {

		@Override
		protected WebContext initialValue() {
			return new WebContext();
		}
	};

	private String view; // 视图

	/**
	 * get context.
	 * 
	 * @return context
	 */
	public static WebContext getContext() {
		return LOCAL.get();
	}

	/**
	 * remove context.
	 * 
	 */
	public static void removeContext() {
		LOCAL.remove();
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public static String getSufix() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return getSufix(request);
	}

	public static String getSufix(HttpServletRequest request) {
		String URI = request.getRequestURI();
		int index = URI.lastIndexOf(".");
		if (index > -1) {
			return URI.substring(index + 1);
		}
		return null;
	}
	
	public static String getIpAddr(HttpServletRequest request) {
		if(request == null){
			return null;
		}
		String proxs[] = {"X-Forwarded-For","Proxy-Client-IP","WL-Proxy-Client-IP","HTTP_CLIENT-IP","HTTP_X_FORWARDED_FOR"};
		String ip = null;
		for(String prox : proxs){
			ip = request.getHeader(prox);
			if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
				continue;
			}else{
				break;
			}
		}
		if (StringUtils.isBlank(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
