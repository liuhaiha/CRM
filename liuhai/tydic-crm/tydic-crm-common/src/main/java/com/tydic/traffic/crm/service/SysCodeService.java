/*
 * 文 件 名:  SysCodeService.java
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

import com.tydic.traffic.crm.entity.TSysCode;
import com.tydic.traffic.tafa.daf.page.Page;

/**
 * 
 * SysCodeService
 * @desc 
 * @author wlhuang
 * @version V1.0 2018年7月21日
 * @since V1.0
 */
public interface SysCodeService {
	List<TSysCode> queryAll();
	boolean deleteCodeById(Integer id);
	public List<Map<String, Object>> getMenu();
	public boolean updateCodeMenu(TSysCode code);
	public boolean addCodeMenu(TSysCode code);
	public boolean deleteById(Integer id);
	public List<TSysCode> queryCodes(Page<TSysCode> result,TSysCode code);
	public boolean checkDuplicateCode(TSysCode code);
	public TSysCode getCode(Integer cid);
}
