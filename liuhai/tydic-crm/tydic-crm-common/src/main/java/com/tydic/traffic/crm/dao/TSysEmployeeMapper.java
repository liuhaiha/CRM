/*
 * 文 件 名:  TSysEmployeeMapper.java
 * 版    权:   Tydic Copyright 2018,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  wlhuang
 * 修 改 人:  
 * 修改时间:  
 * 修改内容:  <修改内容>
 */
package com.tydic.traffic.crm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tydic.traffic.crm.entity.TSysEmployee;
import com.tydic.traffic.crm.vo.EmployeeVo;
import com.tydic.traffic.tafa.daf.mybatis.tex.DataSourceName;
import com.tydic.traffic.tafa.daf.mybatis.tk.common.Mapper;

/**
 * 
 * TSysEmployeeMapper
 * @desc 
 * @author wlhuang
 * @version V1.0 2018年7月21日
 * @since V1.0
 */
@DataSourceName(name = "crmSqlSessionFactory")
public interface TSysEmployeeMapper extends Mapper<TSysEmployee> {
	public List<EmployeeVo> queryEmployee(EmployeeVo emp);
	public List<String> selectStaffEno(@Param("eno") String eno);
	public List<String> selectTeamEno(@Param("eno") String eno);
	public List<String> isLeader(@Param("leaderNo") String leaderNo);
	public String selectByLeaderEno(@Param("eno") String leaderEno);
}