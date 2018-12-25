/*
 * 文 件 名:  SysUserService.java
 * 版    权:   Tydic Copyright 2018,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  wlhuang
 * 修 改 人:  
 * 修改时间:  
 * 修改内容:  <修改内容>
 */
package com.tydic.traffic.crm.service;

import java.util.List;

import com.tydic.traffic.crm.entity.TSysUser;
import com.tydic.traffic.crm.entity.TSysUserRefRole;
import com.tydic.traffic.crm.vo.PermissMenu;
import com.tydic.traffic.crm.vo.SysUserVo;
import com.tydic.traffic.crm.vo.UserInfo;

/**
 * 
 * SysUserService
 * @desc 
 * @author wlhuang
 * @version V1.0 2018年7月21日
 * @since V1.0
 */
public interface SysUserService {
	public UserInfo login(TSysUser user);
	public List<PermissMenu> queryPerMenu(List<String> perCodeList);
	public int updatePwd(TSysUser user, String newPwd);
	public boolean aggsign(SysUserVo user);
	public boolean chageStatus(TSysUser user);
	public boolean resetPwd(TSysUser user);
	public List<TSysUserRefRole> queryRefRole(Integer uid);
	public boolean grant(Integer uid,String roleids);
}
