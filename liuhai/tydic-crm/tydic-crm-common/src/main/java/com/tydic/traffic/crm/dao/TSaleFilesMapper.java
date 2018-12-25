package com.tydic.traffic.crm.dao;

import java.util.List;
import java.util.Map;

import com.tydic.traffic.crm.entity.TSaleFiles;
import com.tydic.traffic.crm.entity.TSysCode;
import com.tydic.traffic.tafa.daf.mybatis.tex.DataSourceName;
import com.tydic.traffic.tafa.daf.mybatis.tk.common.Mapper;

@DataSourceName(name = "crmSqlSessionFactory")
public interface TSaleFilesMapper extends Mapper<TSaleFiles> {
	public int insertFile(TSaleFiles file);
	public List<TSaleFiles> queryFile(Map<String ,Object> param);
	public int addMenu(TSysCode menu);
	public Map<String, Double> nextCode(TSysCode menu);
	public int deleteMenu(int cid);
}