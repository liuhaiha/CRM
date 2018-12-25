/*
 * 文 件 名:  WorkdeskController.java
 * 版    权:   Tydic Copyright 2018,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  wlhuang
 * 修 改 人:  
 * 修改时间:  
 * 修改内容:  <修改内容>
 */
package com.tydic.traffic.crm.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.tydic.traffic.crm.dao.TCrmExtApplyMapper;
import com.tydic.traffic.crm.entity.TCrmExtApply;
import com.tydic.traffic.crm.service.TCrmLogService;
import com.tydic.traffic.crm.service.TCrmWorklogService;
import com.tydic.traffic.crm.service.TCrmworkdeskService;
import com.tydic.traffic.tafa.web.JsonResult;

/**
 * 
 * WorkdeskController
 * @desc 工作桌面处理类
 * @author wlhuang
 * @version V1.0 2018年7月21日
 * @since V1.0
 */
@RestController
@RequestMapping("/workDesk")
public class WorkdeskController extends BaseController{
	
	private static final Logger logger=LoggerFactory.getLogger(WorkdeskController.class);
	
	@Autowired
	private TCrmworkdeskService tCrmworkdeskService;
	
	@GetMapping("/getBirthdayList")
	public JsonResult getBirthdayList(){
		JsonResult result=new JsonResult();
		result.setCode("200");
		result.setData(tCrmworkdeskService.queryBirthdayWeekly());
		return result;
	}
	
	@GetMapping("/getBacklogList")
	public JsonResult getBacklogList(){
		JsonResult result=new JsonResult();
		Map<String, Object> params=new HashMap<>();
		params.put("eno", super.getUser().getEno());
		result.setData(tCrmworkdeskService.queryBacklogList(params));
		result.setCode("200");
		return result;
	}
	
	@GetMapping("/getBacklogListInCurMonth")
	public JsonResult getBacklogListInCurMonth(){
		JsonResult result=new JsonResult();
		Map<String, Object> params=new HashMap<>();
		params.put("eno", super.getUser().getEno());
		result.setData(tCrmworkdeskService.queryBacklogInCurMonth(params));
		result.setCode("200");
		return result;
	}
	
	@GetMapping("/getUnapproveList")
	public JsonResult getUnapproveList(){
		JsonResult result=new JsonResult();
		Map<String, Object> params=new HashMap<>();
		params.put("eno", super.getUser().getEno());
		result.setData(tCrmworkdeskService.queryUnapproveList(params));
		result.setCode("200");
		return result;
	}
	
	@GetMapping("/getProjectBeyondMonth")
	public JsonResult getProjectBeyondMonth() {
		JsonResult result=new JsonResult();
		result.setData(tCrmworkdeskService.queryProjectBeyondMonth());
		result.setCode("200");
		return result;
	}
	
}
