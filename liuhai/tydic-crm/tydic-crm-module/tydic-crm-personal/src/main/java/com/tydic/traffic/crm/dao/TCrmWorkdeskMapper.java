package com.tydic.traffic.crm.dao;

import java.util.List;
import java.util.Map;

import com.tydic.traffic.tafa.daf.mybatis.tex.DataSourceName;

@DataSourceName(name = "crmSqlSessionFactory")
public interface TCrmWorkdeskMapper {
	List<Map<String, Object>> queryBirthdayWeekly();
	List<Map<String, Object>> queryBacklog(Map<String, Object> params);
	public List<String> queryEname(Map<String, Object> params);
	List<Map<String, Object>> queryBacklogInCurMonth(Map<String, Object> params);
	List<Map<String, Object>> queryProjectBeyondMonth();
	List<Map<String, Object>> queryUnapprove(Map<String, Object> params);
}
