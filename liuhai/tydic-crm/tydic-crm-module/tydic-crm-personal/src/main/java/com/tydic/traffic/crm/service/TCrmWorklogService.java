/*
 * 文 件 名:  TCrmWorklogService.java
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

import com.tydic.traffic.crm.entity.TCrmWorklog;
import com.tydic.traffic.crm.vo.TCrmWorklogVo;
import com.tydic.traffic.tafa.daf.page.Page;
import com.tydic.traffic.tafa.daf.page.annotion.Pageable;

/**
 * 
 * TCrmWorklogService
 * @desc 
 * @author wlhuang
 * @version V1.0 2018年7月25日
 * @since V1.0
 */
public interface TCrmWorklogService {
	@Pageable
    public void listWorklogByPage(Page<TCrmWorklogVo> pageResult, TCrmWorklogVo worklog) ;
	
	public List<TCrmWorklogVo> queryWorklog(TCrmWorklogVo worklog);
	
	public boolean addWorklog(TCrmWorklog worklog);
	
	public boolean delWorklog(String logids);
	
	public boolean modWorklog(TCrmWorklog worklog);
	
	public TCrmWorklog geTCrmWorklog(Integer logid);
	
	List<Map<String, Object>> queryAllCustomer();
	List<Map<String, Object>> queryProByCusId(Integer cid);
	List<Map<String, Object>> queryAllEmployee();
}

