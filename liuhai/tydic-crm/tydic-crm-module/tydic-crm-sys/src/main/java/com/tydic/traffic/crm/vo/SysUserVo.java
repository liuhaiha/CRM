/*
 * 文 件 名:  SysUserVo.java
 * 版    权:   Tydic Copyright 2018,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  wlhuang
 * 修 改 人:  
 * 修改时间:  
 * 修改内容:  <修改内容>
 */
package com.tydic.traffic.crm.vo;

import com.tydic.traffic.crm.entity.TSysUser;

/**
 * 
 * SysUserVo
 * @desc 
 * @author wlhuang
 * @version V1.0 2018年8月1日
 * @since V1.0
 */
public class SysUserVo extends TSysUser {
	
	/**
	 * 角色ID列表
	 */
	private String roleids;

	public String getRoleids() {
		return roleids;
	}

	public void setRoleids(String roleids) {
		this.roleids = roleids;
	}

	@Override
	public String toString() {
		return super.toString() + "SysUserVo [roleids=" + roleids + "]";
	}
	
	
}

