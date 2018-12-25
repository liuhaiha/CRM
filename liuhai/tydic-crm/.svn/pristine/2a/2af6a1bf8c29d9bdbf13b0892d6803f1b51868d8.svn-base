/*
 * 文 件 名:  TSysUserMapper.java
 * 版    权:   Tydic Copyright 2018,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  wlhuang
 * 修 改 人:  
 * 修改时间:  
 * 修改内容:  <修改内容>
 */
package com.tydic.traffic.crm.dao;

import java.util.List;

import com.tydic.traffic.crm.entity.TSysUser;
import com.tydic.traffic.crm.vo.UserInfo;
import com.tydic.traffic.tafa.daf.mybatis.tex.DataSourceName;
import com.tydic.traffic.tafa.daf.mybatis.tk.common.Mapper;

/**
 * 
 * TSysUserMapper
 * @desc 
 * @author wlhuang
 * @version V1.0 2018年7月21日
 * @since V1.0
 */
@DataSourceName(name = "crmSqlSessionFactory")
public interface TSysUserMapper extends Mapper<TSysUser> {
	public List<UserInfo> queryUser(TSysUser user);
	public List<String> queryPerCode(String uname);
}