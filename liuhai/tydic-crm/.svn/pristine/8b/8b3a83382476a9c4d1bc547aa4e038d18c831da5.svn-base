/*
 * 文 件 名:  WorklogController.java
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
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.traffic.crm.entity.TCrmWorklog;
import com.tydic.traffic.crm.service.TCrmWorklogService;
import com.tydic.traffic.crm.utils.CommonUtil;
import com.tydic.traffic.crm.utils.ExcelTool;
import com.tydic.traffic.crm.utils.TimeUtil;
import com.tydic.traffic.crm.vo.TCrmWorklogVo;
import com.tydic.traffic.crm.vo.UserInfo;
import com.tydic.traffic.tafa.daf.page.Page;
import com.tydic.traffic.tafa.web.JsonResult;

/**
 * 
 * WorklogController
 * 
 * @desc 工作日志Controller类
 * @author wlhuang
 * @version V1.0 2018年7月25日
 * @since V1.0
 */
@Controller
@RequestMapping("/log2")
public class WorklogController extends BaseController {

	@Resource
	private TCrmWorklogService tCrmWorklogService;

	/**
	 * 跳转到日志列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toLogPage")
	public String toLogPage() {
		return "personal/log/worklog_list";
	}

	@PostMapping("/queryAllCustomer")
	@ResponseBody
	public List<Map<String, Object>> queryAllCustomer() {
		return tCrmWorklogService.queryAllCustomer();
	}

	@PostMapping("/queryProByCusId")
	@ResponseBody
	List<Map<String, Object>> queryProByCusId(Integer cid) {
		return tCrmWorklogService.queryProByCusId(cid);
	}

	@PostMapping("/queryAllEmployee")
	@ResponseBody
	List<Map<String, Object>> queryAllEmployee() {
		return tCrmWorklogService.queryAllEmployee();
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
	public Page<TCrmWorklogVo> queryLogByPage(@Param("pageNumber") Integer pageNumber,
			@Param("pageSize") Integer pageSize, @ModelAttribute("worklog") TCrmWorklogVo worklog) {
		if (null == pageNumber) {
			pageNumber = 1;
		}
		if (null == pageSize) {
			pageSize = 10;
		}
		UserInfo userInfo = super.getUser();
		if (null != userInfo) {
			worklog.setCreatorNo(userInfo.getEno());
		}
		Page<TCrmWorklogVo> pageResult = new Page<>();
		pageResult.setPageNo(pageNumber);
		pageResult.setPageSize(pageSize);
		tCrmWorklogService.listWorklogByPage(pageResult, worklog);
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
	 * 跳转到日志添加页面
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
		TCrmWorklog log = tCrmWorklogService.geTCrmWorklog(logid);
		result.setData(log);
		result.setCode(null == log ? "0" : "1");
		// Map<String, Object> dataMap = new HashMap<>();
		// dataMap.put("log", log);
		// result.setData(dataMap);
		return result;
	}

	@PostMapping("/saveOrUpdWorklog")
	@ResponseBody
	public JsonResult saveOrUpdWorklog(@ModelAttribute("worklog") TCrmWorklogVo worklog) {
		JsonResult result = new JsonResult();
		int joinLen = CommonUtil.getLen(worklog.getJoin());
		String byzd4 = "";
		for (int i = 0; i < joinLen; i++) {
			if (CommonUtil.isNull(worklog.getJoin().get(i))) {
				continue;
			}
			byzd4 += worklog.getJoin().get(i);
			if (i != joinLen - 1) {
				byzd4 += ",";
			}
		}
		worklog.setByzd4(byzd4);

		boolean saveResult = false;
		if (CommonUtil.isNotNull(worklog.getLogid())) {
			UserInfo userInfo = super.getUser();
			if (null != userInfo) {
				worklog.setModifier(userInfo.getEno());
			}
			worklog.setModifytime(new Date());
			saveResult = tCrmWorklogService.modWorklog(worklog);
		} else {
			UserInfo userInfo = super.getUser();
			if (null != userInfo) {
				worklog.setCreator(userInfo.getEname());
				worklog.setCreatorNo(userInfo.getEno());
				worklog.setModifier(userInfo.getEno());
			}
			worklog.setCreatetime(new Date());
			worklog.setModifytime(worklog.getCreatetime());
			saveResult = tCrmWorklogService.addWorklog(worklog);
		}
		result.setCode(saveResult ? "1" : "0");
		return result;
	}

	@PostMapping("/delWorklog")
	@ResponseBody
	public JsonResult delWorklog(@Param("logids") String logids) {
		JsonResult result = new JsonResult();
		boolean succ = tCrmWorklogService.delWorklog(logids);
		result.setCode(succ ? "1" : "-1");
		return result;
	}

	@RequestMapping("/exportLog")
	public void exportLog(@ModelAttribute("worklog") TCrmWorklogVo worklog) {
		UserInfo userInfo = super.getUser();
		if (null != userInfo) {
			worklog.setCreatorNo(userInfo.getEno());
		}
		List<TCrmWorklogVo> logList = tCrmWorklogService.queryWorklog(worklog);

		List<LinkedHashMap<String, Object>> list = new ArrayList<>();
		String[] titles = new String[] { "日志分类", "客户名称", "项目名称", "日志内容", "填报人", "填报时间" };
		for (int i = 0, len = CommonUtil.getLen(logList); i < len; i++) {
			TCrmWorklogVo worklogVo = logList.get(i);
			LinkedHashMap<String, Object> logMap = new LinkedHashMap<>();
			logMap.put("logtypeName", worklogVo.getLogtypeName());
			logMap.put("custName", worklogVo.getCustName());
			logMap.put("projName", worklogVo.getProjName());
			logMap.put("content", worklogVo.getContent());
			logMap.put("creator", worklogVo.getCreator());
			logMap.put("createtime", TimeUtil.getTimeStr(worklogVo.getCreatetime(), TimeUtil.PATTERN_YYYYMMDD_HHMM));
			list.add(logMap);
		}
		InputStream inputStream = null;
		ServletOutputStream os = null;
		File file = null;
		try {
			String saveDir = request.getSession().getServletContext().getRealPath("/") + "upload";
			String urls = ExcelTool.getExcel(titles, list, 50000, System.currentTimeMillis() + ".xls", saveDir);
			response.setHeader("Content-Disposition",
					"attachment; filename=\"" + URLEncoder.encode("个人工作日志_" + userInfo.getEname() + ".xls", "UTF-8") + "\"");
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
