/*
 * 文 件 名:  TCrmLogService.java
 * 版    权:   Tydic Copyright 2018,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  wlhuang
 * 修 改 人:  
 * 修改时间:  
 * 修改内容:  <修改内容>
 */
package com.tydic.traffic.crm.service;

import java.util.List;
import java.util.Map;

import com.tydic.traffic.crm.entity.TCrmLog;
import com.tydic.traffic.crm.vo.TCrmLogVo;
import com.tydic.traffic.tafa.daf.page.Page;

/**
 * 
 * TCrmLogService
 * @desc 
 * @author wlhuang
 * @version V1.0 2018年8月15日
 * @since V1.0
 */
public interface TCrmLogService {
	public void listLogByPage(Page<TCrmLog> pageResult, TCrmLogVo log,String eno);
	public List<TCrmLog> queryLog(TCrmLogVo log);
	public TCrmLogVo geTCrmLog(Integer logid);
	public boolean isOwner(String creatorNo,String logids);
	public boolean isLeader(String leaderNo,String eno);
	public boolean addLog(TCrmLogVo log);
	public boolean delLog(String logids);
	public boolean modLog(TCrmLogVo log);
	public List<String> getMyStaffEno(String eno);
	public List<String> getMyTeamEno(String eno);
	public boolean updateLogInfo(TCrmLog tCrmLog);
	public TCrmLog getInfo(Integer logid);
}

