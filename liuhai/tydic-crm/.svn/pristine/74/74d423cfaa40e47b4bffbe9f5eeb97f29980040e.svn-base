package com.tydic.traffic.crm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tydic.traffic.crm.entity.TCrmLog;
import com.tydic.traffic.crm.vo.TCrmLogVo;
import com.tydic.traffic.tafa.daf.mybatis.tex.DataSourceName;
import com.tydic.traffic.tafa.daf.mybatis.tk.common.Mapper;

@DataSourceName(name = "crmSqlSessionFactory")
public interface TCrmLogMapper extends Mapper<TCrmLog> {
	List<TCrmLogVo> queryLog(TCrmLogVo log);
	List<Map<String, Object>> queryBirthdayWeekly();
	public int updateByLogID(@Param("logidList") List<Integer> logidList);
}