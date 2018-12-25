package com.tydic.traffic.crm.dao;

import com.tydic.traffic.crm.entity.TSysRole;
import com.tydic.traffic.crm.vo.TSysRoleVo;
import com.tydic.traffic.tafa.daf.mybatis.tex.DataSourceName;
import com.tydic.traffic.tafa.daf.mybatis.tk.common.Mapper;

import java.util.List;

/**
 * 
 * TSysRoleMapper
 * @desc 
 * @author wlhuang
 * @version V1.0 2018年8月1日
 * @since V1.0
 */
@DataSourceName(name = "crmSqlSessionFactory")
public interface TSysRoleMapper extends Mapper<TSysRole> {
    public List<TSysRoleVo> selectByPage(TSysRoleVo roleVo);
}