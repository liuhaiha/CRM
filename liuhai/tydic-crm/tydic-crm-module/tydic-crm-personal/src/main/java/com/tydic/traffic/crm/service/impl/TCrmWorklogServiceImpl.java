/*
 * 文 件 名:  TCrmWorklogServiceImpl.java
 * 版    权:   Tydic Copyright 2018,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  wlhuang
 * 修 改 人:  
 * 修改时间:  
 * 修改内容:  <修改内容>
 */
package com.tydic.traffic.crm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tydic.traffic.crm.common.cache.SysCodeCache;
import com.tydic.traffic.crm.dao.TCrmWorklogMapper;
import com.tydic.traffic.crm.entity.TCrmWorklog;
import com.tydic.traffic.crm.service.TCrmWorklogService;
import com.tydic.traffic.crm.utils.CommonUtil;
import com.tydic.traffic.crm.vo.TCrmWorklogVo;
import com.tydic.traffic.tafa.daf.mybatis.tk.entity.Example;
import com.tydic.traffic.tafa.daf.page.Page;
import com.tydic.traffic.tafa.daf.page.annotion.Pageable;

/**
 * 
 * TCrmWorklogServiceImpl
 * @desc 
 * @author wlhuang
 * @version V1.0 2018年7月25日
 * @since V1.0
 */
@Service
public class TCrmWorklogServiceImpl implements TCrmWorklogService{

	@Resource
	private TCrmWorklogMapper tCrmWorklogMapper = null;
	
	private static final Logger logger= LoggerFactory.getLogger(TCrmWorklogServiceImpl.class); 
	
	/**
	 * 分页查询个人日志
	 */
	@Pageable
	public void listWorklogByPage(Page<TCrmWorklogVo> pageResult, TCrmWorklogVo worklog) 
	{
		List<TCrmWorklogVo> logList = null;
		
		try
		{
			logList = tCrmWorklogMapper.queryLog(worklog);
			for (int i = 0, len = CommonUtil.getLen(logList); i < len; i++)
			{
				TCrmWorklogVo log = logList.get(i);
				log.setLogtypeName(SysCodeCache.getCodeText(SysCodeCache.CODE_WORKTYPE, log.getLogtype()));
			}
		}
		catch(Exception ex)
		{
			logger.error("分页查询工作日志出错,参数={" + worklog.toString() + "}", ex);
		}
	}
	
	public List<TCrmWorklogVo> queryWorklog(TCrmWorklogVo worklog) 
	{
		List<TCrmWorklogVo> logList = null;
		try
		{
			logList = tCrmWorklogMapper.queryLog(worklog);
			for (int i = 0, len = CommonUtil.getLen(logList); i < len; i++)
			{
				TCrmWorklogVo log = logList.get(i);
				log.setLogtypeName(SysCodeCache.getCodeText(SysCodeCache.CODE_WORKTYPE, log.getLogtype()));
			}
		}
		catch(Exception ex)
		{
			logger.error("查询工作日志出错,参数={" + worklog.toString() + "}", ex);
		}
		return logList;
	}
	
	/**
	 * 查询所有客户信息
	 */
	public List<Map<String, Object>> queryAllCustomer()
	{
		try
		{
			return tCrmWorklogMapper.queryAllCustomer();
		}
		catch(Exception ex)
		{
			logger.error("获取客户信息时出错", ex);
		}
		return null;
	}
	
	/**
	 * 根据客户ID查询该客户下的所有项目
	 */
	public List<Map<String, Object>> queryProByCusId(Integer cid)
	{
		try
		{
			return tCrmWorklogMapper.queryProByCusId(cid);
		}
		catch(Exception ex)
		{
			logger.error("获取客户项目信息时出错,参数={cid=" + cid + "}", ex);
		}
		return null;
	}
	
	/**
	 * 查询所有员工
	 */
	public List<Map<String, Object>> queryAllEmployee()
	{
		try
		{
			return tCrmWorklogMapper.queryAllEmployee();
		}
		catch(Exception ex)
		{
			logger.error("获取员列表出错", ex);
		}
		return null;
	}
	
	
	/**
	 * 添加工作日志
	 */
	public boolean addWorklog(TCrmWorklog worklog)
	{
		int num = 0;
		try
		{
			num = tCrmWorklogMapper.insert(worklog);
		}
		catch(Exception ex)
		{
			logger.error("添加工作日志出错,参数={" + worklog.toString() + "}", ex);
		}
		return num > 0;
	}
	
	/**
	 * 删除工作日志
	 * @param logid
	 * @return
	 */
	public boolean delWorklog(String logids)
	{
		if (CommonUtil.isNull(logids))
		{
			return false;
		}
		int num = 0;
		List<Integer> logidList = new ArrayList<>();
		Example example = new Example(TCrmWorklog.class);
		Example.Criteria c = example.createCriteria();
		
		try
		{
			String[] logidArray = logids.split(",");
			for (String logid:logidArray)
			{
				logidList.add(CommonUtil.replaceNullInt(logid));
			}
			c.andIn("logid", logidList);
			num = tCrmWorklogMapper.deleteByExample(example);
		}
		catch(Exception ex)
		{
			logger.error("删除工作日志出错,参数={logids=" + logids + "}", ex);
		}
		return num > 0;
	}
	
	/**
	 * 修改工作日志
	 * @param worklog
	 * @return
	 */
	public boolean modWorklog(TCrmWorklog worklog)
	{
		TCrmWorklog dbWorklog = tCrmWorklogMapper.selectByPrimaryKey(worklog.getLogid());
		int num = 0;
		try
		{
			worklog.setCreator(dbWorklog.getCreator());
			worklog.setCreatorNo(dbWorklog.getCreatorNo());
			worklog.setCreatetime(dbWorklog.getCreatetime());
			num = tCrmWorklogMapper.updateByPrimaryKey(worklog);
		}
		catch(Exception ex)
		{
			logger.error("修改工作日志出错,参数={" + worklog.toString()
			+ "}", ex);
		}
		return num > 0;
	}

	/**
	 * 查询日志详细信息
	 */
	public TCrmWorklog geTCrmWorklog(Integer logid) 
	{
		try
		{
			return tCrmWorklogMapper.selectByPrimaryKey(logid);
		}
		catch(Exception ex)
		{
			logger.error("查询日志详细信息出错,参数={logid=" + logid+ "}", ex);
		}
		return null;
	}

}

