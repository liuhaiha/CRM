/*
 * 文 件 名:  TSysRoleService.java
 * 版    权:   Tydic Copyright 2018,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  wlhuang
 * 修 改 人:  
 * 修改时间:  
 * 修改内容:  <修改内容>
 */
package com.tydic.traffic.crm.service;

import java.util.List;

import com.tydic.traffic.crm.entity.TSysRole;
import com.tydic.traffic.crm.entity.TSysRoleRefPermission;
import com.tydic.traffic.crm.vo.TSysRoleVo;
import com.tydic.traffic.tafa.daf.page.Page;
import com.tydic.traffic.tafa.daf.page.annotion.Pageable;

/**
 * 
 * TSysRoleService
 * @desc 
 * @author wlhuang
 * @version V1.0 2018年8月1日
 * @since V1.0
 */
public interface TSysRoleService {
	public List<TSysRole> queryRole(TSysRole role);
	public String getCreater();
	public String saveRole(TSysRole tSysRole);
	public boolean delRole(String checkids);
	public boolean editRole(TSysRole tSysRole);
	@Pageable
    public void getRoleByPage(Page<TSysRoleVo> pageResult, TSysRoleVo tSysRoleVo);
	public boolean permissionManager(String permissionCheckIds, Integer rid);
	public List<TSysRoleRefPermission> getPermissionManager(Integer rpid);
}

