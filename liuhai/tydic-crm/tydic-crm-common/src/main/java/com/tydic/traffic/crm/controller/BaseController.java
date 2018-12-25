package com.tydic.traffic.crm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.traffic.crm.constant.Constant;
import com.tydic.traffic.crm.vo.UserInfo;

public class BaseController {
	
	@Autowired
	protected HttpServletRequest request;
	
	@Autowired
	protected HttpServletResponse response;
	
	public UserInfo getUser()
	{
		if (null != request.getSession().getAttribute(Constant.AUTH_USER))
		{
			return (UserInfo)request.getSession().getAttribute(Constant.AUTH_USER);
		}
		return null;
	}
	
}
