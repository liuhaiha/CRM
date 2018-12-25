package com.tydic.traffic.crm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tydic.traffic.crm.entity.TCrmProject;
import com.tydic.traffic.crm.vo.TCrmProjectVo;
import com.tydic.traffic.tafa.daf.mybatis.tex.DataSourceName;
import com.tydic.traffic.tafa.daf.mybatis.tk.common.Mapper;

@DataSourceName(name = "crmSqlSessionFactory")
public interface TCrmProjectMapper extends Mapper<TCrmProject> {
	
	List<TCrmProjectVo> queryProjectList();
	List<TCrmProjectVo> queryProjectListById(@Param("cid") Integer cid);
}