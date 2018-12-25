package com.tydic.traffic.crm.service;

import java.util.List;
import java.util.Map;

import com.tydic.traffic.crm.entity.TCrmCustomer;
import com.tydic.traffic.crm.entity.TCrmExtApply;
import com.tydic.traffic.crm.entity.TCrmExtCust;
import com.tydic.traffic.crm.entity.TCrmLinkman;
import com.tydic.traffic.crm.entity.TCrmProject;
import com.tydic.traffic.crm.entity.TSysEmployee;
import com.tydic.traffic.crm.vo.TCrmExtApplyVo;
import com.tydic.traffic.crm.vo.TreeNode;

/*
 * 文 件 名:  SaleService.java
 * 版    权:   Tydic Copyright 2018,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  liuhai
 * 修 改 人:  
 * 修改时间:  
 * 修改内容:  <修改内容>
 */
public interface SaleService {
	public String getSex();
	public String getCustomerSource();
	public String getCustomerArea();
	public String getCustomerCategory();
	public String getDept();
	public String getSuperDept();
	public String getPost();
	public String saveCustomerInfo(TCrmCustomer tCrmCustomer);
	public TCrmExtCust saveTCrmExtCust(TCrmExtCust tCrmExtCust);
	public String saveTCrmExtApplyInfo(TCrmExtApplyVo tCrmExtApply);
	public String getApprMan();
	public String getItemStage();
	public String getExtImport();
	public String getExtTheme();
	public TCrmExtCust getTCrmExtCust(Integer extcustid);
	public TCrmLinkman saveLinkManInfo(TCrmLinkman tCrmLinkman);
	public boolean delTCrmExtCust(String checkids);
	public String editTCrmLinkman(Integer checkid);
	public TCrmExtCust editTCrmExtCustInfo(TCrmExtCust tCrmExtCust);
	public String saveTCrmProject(TCrmProject tCrmProject);
	public List<Object> getApplyDetail(int aid);
	public boolean approval(TCrmExtApply tCrmExtApply);
	public TCrmProject getProDetail(int pid);
	public List<TSysEmployee> getAllEmp();
}
