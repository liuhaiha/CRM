package com.tydic.traffic.crm.dao;

import java.util.List;
import java.util.Map;

import com.tydic.traffic.crm.entity.TCrmWorklog;
import com.tydic.traffic.crm.vo.TCrmWorklogVo;
import com.tydic.traffic.tafa.daf.mybatis.tex.DataSourceName;
import com.tydic.traffic.tafa.daf.mybatis.tk.common.Mapper;

@DataSourceName(name = "crmSqlSessionFactory")
public interface TCrmWorklogMapper extends Mapper<TCrmWorklog> {
	List<TCrmWorklogVo> queryLog(TCrmWorklogVo log);
	List<Map<String, Object>> queryAllCustomer();
	List<Map<String, Object>> queryProByCusId(Integer cid);
	List<Map<String, Object>> queryAllEmployee();
}