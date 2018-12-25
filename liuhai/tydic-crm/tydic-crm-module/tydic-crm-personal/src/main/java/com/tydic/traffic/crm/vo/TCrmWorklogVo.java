/*
 * 文 件 名:  TCrmWorklogVo.java
 * 版    权:   Tydic Copyright 2018,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  wlhuang
 * 修 改 人:  
 * 修改时间:  
 * 修改内容:  <修改内容>
 */
package com.tydic.traffic.crm.vo;

import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.tydic.traffic.crm.entity.TCrmWorklog;
import com.tydic.traffic.crm.utils.CommonUtil;

/**
 * 
 * TCrmWorklogVo
 * @desc 
 * @author wlhuang
 * @version V1.0 2018年7月25日
 * @since V1.0
 */
public class TCrmWorklogVo extends TCrmWorklog {
	
	/**
	 * 查询条件：开始时间-结束时间
	 */
	private String beginDate;
	
	private String endDate;
	
	// 日志分类文字描述
	private String logtypeName;
	
	// 客户名称
	private String custName;
	
	// 项目名称
	private String projName;
	
	private List<String> join;
	

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getLogtypeName() {
		return CommonUtil.replaceNullStr(logtypeName);
	}

	public void setLogtypeName(String logtypeName) {
		this.logtypeName = logtypeName;
	}

	public String getCustName() {
		return CommonUtil.replaceNullStr(custName);
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getProjName() {
		return CommonUtil.replaceNullStr(projName);
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public String toString() {
		return super.toString() + ",TCrmWorklogVo [beginDate=" + beginDate + ", endDate=" + endDate + ", logtypeName=" + logtypeName
				+ ", custName=" + custName + ", projName=" + projName + "]";
	}

	public List<String> getJoin() {
		return join;
	}

	public void setJoin(List<String> join) {
		this.join = join;
	}

	
}

