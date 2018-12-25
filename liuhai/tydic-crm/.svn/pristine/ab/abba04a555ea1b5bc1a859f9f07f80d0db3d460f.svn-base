/*
 * 文 件 名:  EmployeeVo.java
 * 版    权:   Tydic Copyright 2018,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  wlhuang
 * 修 改 人:  
 * 修改时间:  
 * 修改内容:  <修改内容>
 */
package com.tydic.traffic.crm.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.tydic.traffic.crm.entity.TSysEmployee;

/**
 * 
 * EmployeeVo
 * @desc 
 * @author wlhuang
 * @version V1.0 2018年7月31日
 * @since V1.0
 */
public class EmployeeVo extends TSysEmployee{
	/**
	 * 查询条件：开始时间-结束时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date beginDate;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endDate;
	
	// 部门名称
	private String deptName;
	
	// 岗位名称
	private String statName;
	
	/**
	 * 用户帐号记录ID
	 */
	private Integer uid;
	
	/**
	 * 员工帐号状态 1、正常 2、锁定 
	 * 为空时表示未开通帐号
	 */
	private String ustatus;

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getStatName() {
		return statName;
	}

	public void setStatName(String statName) {
		this.statName = statName;
	}

	public String toString() {
		return "EmployeeVo [beginDate=" + beginDate + ", endDate=" + endDate + ", deptName=" + deptName + ", statName="
				+ statName + "]," + super.toString();
	}

	public String getUstatus() {
		return ustatus;
	}

	public void setUstatus(String ustatus) {
		this.ustatus = ustatus;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}
	
}

