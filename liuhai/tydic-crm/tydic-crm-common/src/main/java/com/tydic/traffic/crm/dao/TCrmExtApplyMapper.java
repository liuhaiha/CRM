package com.tydic.traffic.crm.dao;

import com.tydic.traffic.crm.entity.TCrmExtApply;
import com.tydic.traffic.tafa.daf.mybatis.tex.DataSourceName;
import com.tydic.traffic.tafa.daf.mybatis.tk.common.Mapper;
@DataSourceName(name = "crmSqlSessionFactory")
public interface TCrmExtApplyMapper extends Mapper<TCrmExtApply> {
}