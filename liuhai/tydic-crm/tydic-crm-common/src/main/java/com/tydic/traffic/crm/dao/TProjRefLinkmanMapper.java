package com.tydic.traffic.crm.dao;

import java.util.List;

import com.tydic.traffic.crm.entity.TProjRefLinkman;
import com.tydic.traffic.crm.vo.TCrmLinkmanVo;
import com.tydic.traffic.tafa.daf.mybatis.tex.DataSourceName;
import com.tydic.traffic.tafa.daf.mybatis.tk.common.Mapper;

@DataSourceName(name = "crmSqlSessionFactory")
public interface TProjRefLinkmanMapper extends Mapper<TProjRefLinkman> {
	
	List<TCrmLinkmanVo> queryLinkmanListByPid(Integer pid);
	
}