/*
 * 文 件 名:  TSysEmployeeService.java
 * 版    权:   Tydic Copyright 2018,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  wlhuang
 * 修 改 人:  
 * 修改时间:  
 * 修改内容:  <修改内容>
 */
package com.tydic.traffic.crm.service;

import com.tydic.traffic.crm.entity.TSysEmployee;
import com.tydic.traffic.crm.vo.EmployeeVo;
import com.tydic.traffic.tafa.daf.page.Page;
import com.tydic.traffic.tafa.daf.page.annotion.Pageable;

/**
 * 
 * TSysEmployeeService
 * @desc 
 * @author wlhuang
 * @version V1.0 2018年7月21日
 * @since V1.0
 */
public interface TSysEmployeeService {
	TSysEmployee getEmployeeInfo(String eno);
	public int updateInfo(TSysEmployee emp);
	@Pageable
	public void listEmpByPage(Page<EmployeeVo> pageResult, EmployeeVo employee);
	
	public boolean isExistEmp(String eno);
	
	public boolean addEmp(TSysEmployee employee);
	
	public boolean delEmp(String eids);
	
	public boolean modEmo(TSysEmployee employee);
	
	public TSysEmployee geEmp(Integer eid);
}
