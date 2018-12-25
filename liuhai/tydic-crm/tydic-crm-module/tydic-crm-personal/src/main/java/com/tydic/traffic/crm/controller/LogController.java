/*
 * 文 件 名:  LogController.java
 * 版    权:   Tydic Copyright 2018,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  wlhuang
 * 修 改 人:  
 * 修改时间:  
 * 修改内容:  <修改内容>
 */
package com.tydic.traffic.crm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.traffic.crm.entity.TCrmLog;
import com.tydic.traffic.crm.entity.TCrmLogItem;
import com.tydic.traffic.crm.service.TCrmLogService;
import com.tydic.traffic.crm.utils.CommonUtil;
import com.tydic.traffic.crm.utils.ExcelTool;
import com.tydic.traffic.crm.utils.TimeUtil;
import com.tydic.traffic.crm.vo.TCrmLogVo;
import com.tydic.traffic.crm.vo.UserInfo;
import com.tydic.traffic.tafa.daf.page.Page;
import com.tydic.traffic.tafa.web.JsonResult;

/**
 * 
 * LogController
 * @desc 
 * @author wlhuang
 * @version V1.0 2018年8月15日
 * @since V1.0
 */
@Controller
@RequestMapping("/log")
public class LogController extends BaseController {
	
	@Resource
	private TCrmLogService tcrmLogService;

	/**
	 * 跳转到日志列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toLogPage")
	public String toLogPage() {
		return "personal/log/worklog_list";
	}
	
	/**
	 * 查询日志
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param worklog
	 * @return
	 */
	@PostMapping("/queryLog")
	@ResponseBody
	public Page<TCrmLog> queryLogByPage(@Param("eno") String eno,@Param("pageNumber") Integer pageNumber,
			@Param("pageSize") Integer pageSize, @ModelAttribute("log") TCrmLogVo log) {
		if (null == pageNumber) {
			pageNumber = 1;
		}
		if (null == pageSize) {
			pageSize = 10;
		}
		UserInfo userInfo = super.getUser();
		if (null != userInfo) {
			log.setCreatorNo(userInfo.getEno());
		}
		Page<TCrmLog> pageResult = new Page<>();
		pageResult.setPageNo(pageNumber);
		pageResult.setPageSize(pageSize);
		tcrmLogService.listLogByPage(pageResult,log,eno);
		return pageResult;
	}
	
	
	/**
	 * 跳转到日志添加页面
	 * 
	 * @return
	 */
	@RequestMapping("/toLogAddPage")
	public String toLogAddPage() {
		return "personal/log/worklog_add";
	}

	/**
	 * 跳转到日志编辑页面
	 * 
	 * @return
	 */
	@RequestMapping("/toLogEditPage")
	public String toLogEditPage(@Param("logid") Integer logid) {
		request.getSession().setAttribute("logid", logid);
		return "personal/log/worklog_edit";
	}
	
	/**
	 * 跳转到日志详细页面
	 * 
	 * @return
	 */
	@RequestMapping("/toLogDetailPage")
	public String toLogDetailPage(@Param("logid") Integer logid) {
		request.getSession().setAttribute("logid", logid);
		return "personal/log/worklog_detail";
	}

	@PostMapping("/getLogDetail")
	@ResponseBody
	public JsonResult getLogDetail(@Param("logid") Integer logid) {
		if (CommonUtil.isNull(logid)) {
			logid = CommonUtil.replaceNullInt(request.getSession().getAttribute("logid"));
		}
		JsonResult result = new JsonResult();
		TCrmLogVo log = tcrmLogService.geTCrmLog(logid);
		result.setData(log);
		result.setCode(null == log ? "0" : "1");
		// Map<String, Object> dataMap = new HashMap<>();
		// dataMap.put("log", log);
		// result.setData(dataMap);
		return result;
	}

	@PostMapping("/saveOrUpdLog")
	@ResponseBody
	public JsonResult saveOrUpdWorklog(@ModelAttribute("worklog") TCrmLogVo log) {
		JsonResult result = new JsonResult();
	
		// 清空删除记录的日志项记录
		List<TCrmLogItem> itemList = new ArrayList<>();
		for (int i = 0, len = CommonUtil.getLen(log.getItemList()); i < len; i++)
		{
			TCrmLogItem item = log.getItemList().get(i);
			item.setDelstate(0);
			item.setPid(log.getItemList().get(i).getPid());
			if (CommonUtil.isNotNull(item.getContent()) && CommonUtil.isNotNull(item.getLogtype()))
			{
				itemList.add(item);
			}
			
			if (CommonUtil.isNotNull(item.getLogtype()) && item.getLogtype().split("_").length == 2)
			{
				item.setLogtype(item.getLogtype().split("_")[1]);
			}
		}
		log.setItemList(itemList);
		
		
		boolean saveResult = false;
		if (CommonUtil.isNotNull(log.getLogid())) {
			UserInfo userInfo = super.getUser();
			if (null != userInfo) {
				log.setModifier(userInfo.getEno());
				log.setDelstate(0);
				log.setState(0);
				log.setReadstate(0);
			}
			log.setModifytime(new Date());
			saveResult = tcrmLogService.modLog(log);
		} else {
			UserInfo userInfo = super.getUser();
			if (null != userInfo) {
				log.setCreator(userInfo.getEname());
				log.setCreatorNo(userInfo.getEno());
				log.setModifier(userInfo.getEno());
				log.setDelstate(0);
				log.setState(0);
				log.setReadstate(0);
			}
			log.setCreatetime(new Date());
			log.setModifytime(log.getCreatetime());
			saveResult = tcrmLogService.addLog(log);
		}
		result.setCode(saveResult ? "1" : "0");
		return result;
	}

	@PostMapping("/delLog")
	@ResponseBody
	public JsonResult delWorklog(@Param("logids") String logids) {
		JsonResult result = new JsonResult();
		UserInfo userInfo = super.getUser();
		boolean isOwner = tcrmLogService.isOwner(userInfo.getEno(), logids);
		if (!isOwner)
		{
			result.setCode("-1");
			return result;
		}
		boolean succ = tcrmLogService.delLog(logids);
		result.setCode(succ ? "1" : "0");
		return result;
	}
	
	@RequestMapping("/isOwner")
	@ResponseBody
	public JsonResult isOwner(@Param("logid") String logid) {
		JsonResult result = new JsonResult();
		UserInfo userInfo = super.getUser();
		boolean isOwner = tcrmLogService.isOwner(userInfo.getEno(), logid);
		if (!isOwner)
		{
			result.setCode("-1");
		}
		else
		{
			result.setCode("1");
		}
		return result;
	}
	
	@RequestMapping("/isLeader")
	@ResponseBody
	public JsonResult isLeader(@Param("creatorNo") String creatorNo) {
		JsonResult result = new JsonResult();
		UserInfo userInfo = super.getUser();
		boolean isLeader = tcrmLogService.isLeader(userInfo.getEno(), creatorNo);
		if (!isLeader)
		{
			result.setCode("-1");
		}
		else
		{
			result.setCode("1");
		}
		return result;
	}

	@PostMapping("/myLog")
	@ResponseBody
	public JsonResult myLog(){
		JsonResult result = new JsonResult();
		UserInfo userInfo = super.getUser();
		String eno = userInfo.getEno();
		if(CommonUtil.isNotNull(eno)){
			result.setCode("1");
			result.setData(eno);
		}
		else{
			result.setCode("-1");
		}
		return result;
	}
	@PostMapping("/myStaffLog")
	@ResponseBody
	public JsonResult myStaffLog(){
		JsonResult result = new JsonResult();
		UserInfo userInfo = super.getUser();
		String eno = userInfo.getEno();
		if(CommonUtil.isNotNull(eno)){
			List<String> staffEno = tcrmLogService.getMyStaffEno(eno);
			if(!staffEno.isEmpty()){
				result.setData(staffEno);
				result.setCode("1");
			}else{
				result.setCode("-1");
			}
		}
		else{
			result.setCode("-1");
		}
		return result;
	}
	@PostMapping("/myTeamLog")
	@ResponseBody
	public JsonResult myTeamLog(){
		JsonResult result = new JsonResult();
		UserInfo userInfo = super.getUser();
		String eno = userInfo.getEno();
		if(CommonUtil.isNotNull(eno)){
			List<String> teamEno = tcrmLogService.getMyTeamEno(eno);
			if(!teamEno.isEmpty()){
				result.setData(teamEno);
				result.setCode("1");
			}else{
				result.setCode("-1");
			}
		}
		else{
			result.setCode("-1");
		}
		return result;
	}
	
	@RequestMapping("/saveOpinion")
	@ResponseBody
	public JsonResult saveOpinion(@Param("opinion") String opinion,@Param("logid") Integer logid){
		JsonResult result = new JsonResult();
		UserInfo userInfo = super.getUser();
		String eno = userInfo.getEno();
		TCrmLog tCrmLog = tcrmLogService.getInfo(logid);
		if(eno.equals(tCrmLog.getCreatorNo())){
			result.setCode("-1");
		}else
		{
			tCrmLog.setReadstate(1);
			tCrmLog.setOpinion(opinion);
			boolean succ = tcrmLogService.updateLogInfo(tCrmLog);
			result.setCode(succ ? "1" : "0");
		}
		return result;
	}
	
	@RequestMapping("/updateLogState")
	@ResponseBody
	public JsonResult updateLogState(@Param("logid") Integer logid){
		JsonResult result = new JsonResult();
		UserInfo userInfo = super.getUser();
		String eno = userInfo.getEno();
		TCrmLog tCrmLog = tcrmLogService.getInfo(logid);
		if(eno.equals(tCrmLog.getCreatorNo())){
			result.setCode("-1");
		}else
		{
			if(tCrmLog.getState() == 1){
				tCrmLog.setState(0);
			}else{
				tCrmLog.setState(1);
			}
			boolean succ = tcrmLogService.updateLogInfo(tCrmLog);
			result.setCode(succ ? "1" : "0");
		}
		return result;
	}
	
	@RequestMapping("/exportLog")
	public void exportLog(@ModelAttribute("log") TCrmLogVo log) {
		UserInfo userInfo = super.getUser();
		if (null != userInfo) {
			log.setCreatorNo(userInfo.getEno());
		}
		List<TCrmLog> logList = tcrmLogService.queryLog(log);

		List<LinkedHashMap<String, Object>> list = new ArrayList<>();
		String[] titles = new String[] { "工作日志", "存在的问题", "下一步计划", "需要协调", "填报人", "填报时间" };
		for (int i = 0, len = CommonUtil.getLen(logList); i < len; i++) {
			TCrmLog tCrmLog = logList.get(i);
			LinkedHashMap<String, Object> logMap = new LinkedHashMap<>();
			logMap.put("content", CommonUtil.replaceNullStr(tCrmLog.getContent()));
			logMap.put("problem",  CommonUtil.replaceNullStr(tCrmLog.getProblem()));
			logMap.put("nextplan",  CommonUtil.replaceNullStr(tCrmLog.getNextplan()));
			logMap.put("needhelp",  CommonUtil.replaceNullStr(tCrmLog.getNeedhelp()));
			logMap.put("creator",  CommonUtil.replaceNullStr(tCrmLog.getCreator()));
			logMap.put("createtime", TimeUtil.getTimeStr(tCrmLog.getCreatetime(), TimeUtil.PATTERN_YYYYMMDD_HHMMSS));
			list.add(logMap);
		}
		InputStream inputStream = null;
		ServletOutputStream os = null;
		File file = null;
		try {
			String saveDir = request.getSession().getServletContext().getRealPath("/") + "upload";
			String urls = ExcelTool.getExcel(titles, list, 50000, System.currentTimeMillis() + ".xls", saveDir);
			response.setHeader("Content-Disposition",
					"attachment; filename=\"" + URLEncoder.encode("个人工作日志_" + TimeUtil.getTimeStr(TimeUtil.PATTERN_YYYYMMDD) + ".xls", "UTF-8") + "\"");
			file = new File(urls);
			inputStream = new FileInputStream(file);
			os = response.getOutputStream();
			byte[] b = new byte[1024];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

