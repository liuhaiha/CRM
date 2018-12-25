package com.tydic.traffic.crm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tydic.traffic.crm.entity.TCrmLogItem;
import com.tydic.traffic.tafa.daf.mybatis.tex.DataSourceName;
import com.tydic.traffic.tafa.daf.mybatis.tk.common.Mapper;
@DataSourceName(name = "crmSqlSessionFactory")
public interface TCrmLogItemMapper extends Mapper<TCrmLogItem> {
	public int updateByLogID(@Param("logidList") List<Integer> logidList);
}