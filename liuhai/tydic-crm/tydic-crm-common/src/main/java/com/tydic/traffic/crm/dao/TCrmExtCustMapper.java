package com.tydic.traffic.crm.dao;

import com.tydic.traffic.crm.entity.TCrmExtCust;
import com.tydic.traffic.tafa.daf.mybatis.tex.DataSourceName;
import com.tydic.traffic.tafa.daf.mybatis.tk.common.Mapper;

@DataSourceName(name = "crmSqlSessionFactory")
public interface TCrmExtCustMapper extends Mapper<TCrmExtCust> {
}