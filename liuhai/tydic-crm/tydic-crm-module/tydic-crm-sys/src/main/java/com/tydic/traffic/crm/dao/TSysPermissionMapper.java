package com.tydic.traffic.crm.dao;

import java.util.List;

import com.tydic.traffic.crm.entity.TSysPermission;
import com.tydic.traffic.tafa.daf.mybatis.tex.DataSourceName;
import com.tydic.traffic.tafa.daf.mybatis.tk.common.Mapper;

@DataSourceName(name = "crmSqlSessionFactory")
public interface TSysPermissionMapper extends Mapper<TSysPermission> {
	
	List<TSysPermission> getListCount();
	List<TSysPermission> getListCountByCode(String pcode);
	List<TSysPermission> searchByText(TSysPermission permission);
	
}