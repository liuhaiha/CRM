package com.tydic.traffic.crm.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tydic.traffic.crm.entity.TCrmMeetingRecord;
import com.tydic.traffic.crm.entity.TSaleFiles;
import com.tydic.traffic.crm.service.SaleTrackService;
import com.tydic.traffic.crm.utils.CommonUtil;
import com.tydic.traffic.crm.vo.UserInfo;
import com.tydic.traffic.tafa.daf.page.Page;
import com.tydic.traffic.tafa.web.JsonResult;

@Controller
@RequestMapping("/track")
public class SalelTrackController extends BaseController {
	
	private static final Logger logger= LoggerFactory.getLogger(SalelTrackController.class);
	
	@Autowired
	private SaleTrackService trackService;
	
	/**
	 * 新建会议纪要，保存文本框数据到数据库，并生成word会议纪要文档
	 * @param record
	 * @return
	 */
	@RequestMapping("/create")
	@ResponseBody
	public int create(@ModelAttribute("TCrmMeetingRecord") TCrmMeetingRecord record) {
		// 1.将会议内容存入数据库--record表
		UserInfo userInfo = super.getUser();
		if (null != userInfo) {
			record.setCreator(userInfo.getEname());
			record.setModifier(userInfo.getEname());
		}
		record.setCreatetime(new Date());
		record.setModifytime(new Date());
		
		// 2.生成文档
		HashMap<String, Object> dataMap = new HashMap<>();
		dataMap.put("theme", record.getTheme());
		dataMap.put("mtime", record.getMtime());
		dataMap.put("maddr", record.getMaddr());
		dataMap.put("customers", record.getCustomers());
		
		String filePath = "D:\\crmfile\\";
		String fileName = "会议纪要";
		String fileType = "doc";
		try {
			// 将会议交流保存为会议纪要文档
			FreeMark freeMark = new FreeMark("template/");
			freeMark.setTemplateName("meeting.xml");
			SimpleDateFormat mtime = new SimpleDateFormat("yyyy年MM月dd日");
			freeMark.setFileName(fileName + mtime + "." + fileType);
			freeMark.setFilePath(filePath);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("theme", record.getTheme());
			map.put("time", mtime.format(record.getMtime()).toString());
			map.put("addr", record.getMaddr());
			map.put("customers", record.getCustomers());
			map.put("name", record.getRecordname());
			map.put("process", record.getProcess());
			map.put("nextplan", record.getNextPlan());
			map.put("summary", record.getSummary());
			freeMark.createWord(map);
		} catch (Exception e) {
			logger.error("生成会议纪要文档出错", e);
		}
		
		// 3.保存文件信息到文件表--file表
		TSaleFiles saleFiles = new TSaleFiles();
		saleFiles.setFname(fileName);
		saleFiles.setFtype(fileType);
		File file = new File(filePath + fileName + "." + fileType);
		if (file.exists() && file.isFile()) {
			saleFiles.setFsize((float)file.length());
		}
		saleFiles.setFpath(filePath + fileName + "." + fileType);
		saleFiles.setFclass("1");
		saleFiles.setCommon("0");
		if (null != userInfo) {
			saleFiles.setCreator(userInfo.getEname());
			saleFiles.setModifier(userInfo.getEname());
		}
		saleFiles.setCreatetime(new Date());
		saleFiles.setModifytime(new Date());
		TSaleFiles saleFile = trackService.createSaleFiles(saleFiles);
		record.setFileno(saleFile.getFid());
		
		// 4.返回会议记录保存结果
		return trackService.createCom(record);
	}
	@RequestMapping("/updateMeetingRecord")
	@ResponseBody
	public int updateMeetingRecord(@ModelAttribute("TCrmMeetingRecord") TCrmMeetingRecord record) {
		return trackService.updateMeetingRecord(record);
	}
	
	/**
	 * 根据客户id、项目id，查询交流过程列表数据
	 * @param cid
	 * @param pid
	 * @return
	 */
	@RequestMapping("/getMeetingList")
	@ResponseBody
	public Page<TCrmMeetingRecord> getMeetingList(@Param("cid") Integer cid,@Param("pid") Integer pid,
			@Param("pageNumber") Integer pageNumber,@Param("pageSize") Integer pageSize) {
		if (null == pageNumber) {
			pageNumber = 1;
		}
		if (null == pageSize) {
			pageSize = 5;
		}
		Page<TCrmMeetingRecord> result = new Page<>();
		result.setPageNo(pageNumber);
		result.setPageSize(pageSize);
		trackService.getMeetingList(result,cid, pid);
		return result;
	}
	
	/**
	 * 根据客户id、项目id、交流申请id获取一次交流过程数据
	 * @param cid
	 * @param pid
	 * @param aid
	 * @return
	 */
	@RequestMapping("/getMeeting/{id}")
	@ResponseBody
	public TCrmMeetingRecord getMeeting(@PathVariable Integer id) {
		return trackService.getMeeting(id);
	}
//	public TCrmMeetingRecord getMeeting(@PathVariable Integer id) {
//		List<TCrmMeetingRecord> rList =  trackService.getMeeting(id);
//		return CommonUtil.getLen(rList) > 0?rList.get(0):null;
//	}
	
	@RequestMapping("/delMeeting/{id}")
	@ResponseBody
	public int delMeeting(@PathVariable Integer id) {
		return trackService.delMeeting(id);
	}
	
}
