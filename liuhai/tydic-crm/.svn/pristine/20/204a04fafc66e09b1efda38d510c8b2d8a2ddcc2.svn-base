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

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.tydic.traffic.crm.entity.TCrmLog;
import com.tydic.traffic.crm.entity.TCrmLogItem;

/**
 * 
 * TCrmWorklogVo
 * @desc 
 * @author wlhuang
 * @version V1.0 2018年7月25日
 * @since V1.0
 */
public class TCrmLogVo extends TCrmLog {
	
	/**
	 * 查询条件：开始时间-结束时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date beginDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate;
	
	private String sort;
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	private String field;
	
	private List<TCrmLogItem> itemList;
	

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

	public List<TCrmLogItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<TCrmLogItem> itemList) {
		this.itemList = itemList;
	}
	
	
}

