/*
 * 文 件 名:  TCrmLogServiceImpl.java
 * 版    权:   Tydic Copyright 2018,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  wlhuang
 * 修 改 人:  
 * 修改时间:  
 * 修改内容:  <修改内容>
 */
package com.tydic.traffic.crm.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tydic.traffic.crm.dao.TCrmLogItemMapper;
import com.tydic.traffic.crm.dao.TCrmLogMapper;
import com.tydic.traffic.crm.dao.TSysEmployeeMapper;
import com.tydic.traffic.crm.entity.TCrmLog;
import com.tydic.traffic.crm.entity.TCrmLogItem;
import com.tydic.traffic.crm.entity.TSysEmployee;
import com.tydic.traffic.crm.service.TCrmLogService;
import com.tydic.traffic.crm.utils.CommonUtil;
import com.tydic.traffic.crm.vo.TCrmLogVo;
import com.tydic.traffic.tafa.daf.mybatis.tk.entity.Example;
import com.tydic.traffic.tafa.daf.page.Page;
import com.tydic.traffic.tafa.daf.page.annotion.Pageable;
import com.tydic.traffic.tafa.utils.uuid.UUIDUtil;

/**
 * 
 * TCrmLogServiceImpl
 * @desc 
 * @author wlhuang
 * @version V1.0 2018年8月15日
 * @since V1.0
 */
@Service
public class TCrmLogServiceImpl implements TCrmLogService {
	@Resource
	private TCrmLogMapper tCrmLogMapper;
	@Resource
	private TSysEmployeeMapper tSysEmployeeMapper;
	@Resource
	private TCrmLogItemMapper tCrmLogItemMapper;
	
	private static final Logger logger= LoggerFactory.getLogger(TCrmLogServiceImpl.class); 
	
	@Pageable
	public void listLogByPage(Page<TCrmLog> pageResult, TCrmLogVo log, String eno)
	{
		Example example = new Example(TCrmLog.class);
		Example.Criteria criteria = example.createCriteria();
		if (StringUtils.isNoneBlank(log.getContent()))
		{
			criteria.andCondition("content like '%{0}%' or problem like '%{0}%' or nextplan like '%{0}%' or needhelp like '%{0}%'".replace("{0}", log.getContent()));
		}
		
		if (null != log.getBeginDate())
		{
			criteria.andGreaterThan("logtime", new Date(log.getBeginDate().getTime() -1));
		}
		
		if (null != log.getEndDate())
		{
			criteria.andLessThan("logtime", new Date(log.getEndDate().getTime() + 1000 * 60 * 60 * 24 -1));
		}
		
		if (StringUtils.isNoneBlank(log.getCreator()))
		{
			criteria.andCondition("creator_no='{0}' or creator like '%{0}%'".replace("{0}", log.getCreator()));
		}
		if(CommonUtil.isNotNull(eno))
		{
			List<String> staffEnos= java.util.Arrays.asList(eno.split(","));
			criteria.andIn("creatorNo", staffEnos);
		}
		if(CommonUtil.isNotNull(log.getSort()) && CommonUtil.isNotNull(log.getField())){
			example.setOrderByClause(log.getSort() +' '+ log.getField());
		}
		else{
			example.setOrderByClause("modifytime desc");
		}
		try
		{
			criteria.andEqualTo("delstate", 0);
			tCrmLogMapper.selectByExample(example);
		}
		catch(Exception ex)
		{
			logger.error("查询工作日志时出错,参数={" + log.toString() + "}", ex);
		}
	}
	
	public List<TCrmLog> queryLog(TCrmLogVo log) 
	{
		List<TCrmLog> logList = null;
		
		Example example = new Example(TCrmLog.class);
		Example.Criteria criteria = example.createCriteria();
		if (StringUtils.isNoneBlank(log.getContent()))
		{
			criteria.andCondition("content like '%{0}%' or problem like '%{0}%' or nextplan like '%{0}%' or needhelp like '%{0}%'".replace("{0}", log.getContent()));
		}
		
		if (null != log.getBeginDate())
		{
			criteria.andGreaterThan("createtime", log.getBeginDate());
		}
		
		if (null != log.getEndDate())
		{
			criteria.andLessThan("createtime", new Date(log.getBeginDate().getTime() + 1000 * 60 * 60 * 24 -1));
		}
		
		if (StringUtils.isNoneBlank(log.getCreator()))
		{
			criteria.andCondition("creator_no='{0}' or creator like '%{0}%'".replace("{0}", log.getCreator()));
		}
		example.setOrderByClause("modifytime desc");
		try
		{
			logList = tCrmLogMapper.selectByExample(example);
		}
		catch(Exception ex)
		{
			logger.error("导出工作日志时出错,参数={" + log.toString() + "}", ex);
		}
		return logList;
	}
	
	/**
	 * 查询日志详细信息
	 */
	public TCrmLogVo geTCrmLog(Integer logid) 
	{
		TCrmLogVo vo = new TCrmLogVo();
		try
		{
			TCrmLog log = tCrmLogMapper.selectByPrimaryKey(logid);
			if (null != log)
			{
				BeanUtils.copyProperties(vo, log);
				Example example = new Example(TCrmLogItem.class);
				Example.Criteria criteria = example.createCriteria();
				criteria.andEqualTo("logid", logid);
				List<TCrmLogItem> itemList = tCrmLogItemMapper.selectByExample(example);
				vo.setItemList(itemList);
			}
			return vo;
		}
		catch(Exception ex)
		{
			logger.error("查询日志详细信息出错,参数={logid=" + logid+ "}", ex);
			return null;
		}
	}
	
	/**
	 * 添加工作日志
	 */
	public boolean addLog(TCrmLogVo log)
	{
		int num = 0;
		try
		{
			List<TCrmLogItem> itemList = log.getItemList();
			String content = "";
			for (int i = 0, len = CommonUtil.getLen(itemList); i< len; i++)
			{
				content+="工作" + (i+1) + ":" + itemList.get(i).getContent() + "\r\n";
				itemList.get(i).setCreatetime(log.getCreatetime());
				itemList.get(i).setModifytime(log.getModifytime());
				itemList.get(i).setCreator(log.getCreator());
				itemList.get(i).setModifier(log.getModifier());
			}
			log.setContent(content);
			String uuid = UUIDUtil.getUUID();
			log.setExtend1(uuid);
			num = tCrmLogMapper.insert(log);
			TCrmLog dbLog = geTCrmLog(log);
			
			for (int i = 0, len = CommonUtil.getLen(itemList); i< len; i++)
			{
				if (null != dbLog)
				{
					itemList.get(i).setLogid(dbLog.getLogid());
					tCrmLogItemMapper.insert(itemList.get(i));
				}
			}
		}
		catch(Exception ex)
		{
			logger.error("添加工作日志出错,参数={" + log.toString() + "}", ex);
		}
		return num > 0;
	}
	
	private TCrmLog geTCrmLog(TCrmLogVo log)
	{
		Example example = new Example(TCrmLog.class);
		Example.Criteria criteria = example.createCriteria();
//		criteria.andEqualTo("content",log.getContent());
		criteria.andEqualTo("extend1",log.getExtend1());
		criteria.andEqualTo("creator",log.getCreator());
		criteria.andEqualTo("creatorNo",log.getCreatorNo());
//		criteria.andGreaterThan("createtime",log.getCreatetime());
//		criteria.andGreaterThan("modifytime",log.getModifytime());
//		criteria.andEqualTo("needhelp",log.getNeedhelp());
//		criteria.andEqualTo("nextplan",log.getNextplan());
//		criteria.andEqualTo("problem",log.getProblem());
		
		List<TCrmLog> logList = tCrmLogMapper.selectByExample(example);
		return CommonUtil.getLen(logList) > 0?logList.get(0):null;
	}
	
	/**
	 * 删除工作日志
	 * @param logid
	 * @return
	 */
	public boolean delLog(String logids)
	{
		if (CommonUtil.isNull(logids))
		{
			return false;
		}
		List<Integer> logidList = new ArrayList<>();
		int dellog = 0 ;
		try
		{
			String[] logidArray = logids.split(",");
			for (String logid:logidArray)
			{
				logidList.add(CommonUtil.replaceNullInt(logid));
			}
			dellog = tCrmLogMapper.updateByLogID(logidList);
			
			dellog = tCrmLogItemMapper.updateByLogID(logidList);
			return dellog >0 ;
		}
		catch(Exception ex)
		{
			logger.error("删除工作日志出错,参数={logids=" + logids + "}", ex);
			return false;
		}
	}
	
	/**
	 * 修改工作日志
	 * @param worklog
	 * @return
	 */
	public boolean modLog(TCrmLogVo log)
	{
		int num = 0;
		try
		{
			TCrmLog dbLog = tCrmLogMapper.selectByPrimaryKey(log.getLogid());
			log.setCreator(dbLog.getCreator());
			log.setCreatorNo(dbLog.getCreatorNo());
			log.setCreatetime(dbLog.getCreatetime());
			log.setExtend1(dbLog.getExtend1());
			
			// 筛选出增/删/改的记录
			Map<String, List<TCrmLogItem>> operatorMap = this.group(log.getLogid(),log.getItemList());
			
			// 新增的工作日志项
			List<TCrmLogItem> insertList = operatorMap.get(OPERATOR_ADD);
			for (int i = 0, len = CommonUtil.getLen(insertList); i< len; i++)
			{
				insertList.get(i).setCreator(log.getCreator());
				insertList.get(i).setCreatetime(log.getCreatetime());
				insertList.get(i).setModifier(log.getModifier());
				insertList.get(i).setModifytime(log.getModifytime());
				tCrmLogItemMapper.insert(insertList.get(i));
			}
			// 删除的工作日志项
			List<TCrmLogItem> delList =  operatorMap.get(OPERATOR_DEL);
			for (int i = 0, len = CommonUtil.getLen(delList); i< len; i++)
			{
				tCrmLogItemMapper.deleteByPrimaryKey(delList.get(i).getItemid());
			}
			// 更新工作日志项
			List<TCrmLogItem> modList =  operatorMap.get(OPERATOR_MOD);
			for (int i = 0, len = CommonUtil.getLen(modList); i< len; i++)
			{
				modList.get(i).setCreator(log.getCreator());
				modList.get(i).setCreatetime(log.getCreatetime());
				modList.get(i).setModifier(log.getModifier());
				modList.get(i).setModifytime(log.getModifytime());
				tCrmLogItemMapper.updateByPrimaryKey(modList.get(i));
			}
			insertList.addAll(modList);
			Collections.sort(insertList, new Comparator<TCrmLogItem>(){
				public int compare(TCrmLogItem o1, TCrmLogItem o2) {
					if (o1.getCreatetime().getTime() > o2.getCreatetime().getTime())
					{
						return 1;
					}
					else if (o1.getCreatetime().getTime() < o2.getCreatetime().getTime())
					{
						return -1;
					}
					return 0;
				}});
			String content = "";
			for (int i = 0, len = CommonUtil.getLen(insertList); i< len; i++)
			{
				content+="工作" + (i+1) + ":" + insertList.get(i).getContent() + "\r\n";
			}
			log.setContent(content);
			
			num = tCrmLogMapper.updateByPrimaryKey(log);
		}
		catch(Exception ex)
		{
			logger.error("修改工作日志出错,参数={" + log.toString()
			+ "}", ex);
			return false;
		}
		return num > 0;
	}
	
	private static final String OPERATOR_ADD = "OPERATOR_ADD";
	private static final String OPERATOR_DEL = "OPERATOR_DEL";
	private static final String OPERATOR_MOD = "OPERATOR_MOD";
	private Map<String, List<TCrmLogItem>> group(Integer logid,List<TCrmLogItem> pageItemList)
	{
		 Example example = new Example(TCrmLogItem.class);
		 Example.Criteria c = example.createCriteria();
		 c.andEqualTo("logid", logid);
		 List<TCrmLogItem> dbItmsList = tCrmLogItemMapper.selectByExample(example);
		 Map<Integer, TCrmLogItem> dbMap = new HashMap<>();
		 for (int i = 0, len = CommonUtil.getLen(dbItmsList); i < len; i++)
		 {
			 dbMap.put(dbItmsList.get(i).getItemid(), dbItmsList.get(i));
		 }
		 
		 List<TCrmLogItem> insertList = new ArrayList<>();
		 List<TCrmLogItem> delList = new ArrayList<>();
		 List<TCrmLogItem> modList = new ArrayList<>();
		 for (int i = 0, len = CommonUtil.getLen(pageItemList); i < len; i++)
		 {
			 TCrmLogItem item = pageItemList.get(i);
			 item.setLogid(logid);
			 if (CommonUtil.isNull(item.getItemid()))
			 {
				 insertList.add(item);
			 }
			 else
			 {
				 TCrmLogItem dbItem = dbMap.remove(item.getItemid());
				 if (dbItem.getItemid().intValue() == item.getItemid().intValue())
				 {
					 modList.add(item);
					 item.setCreator(dbItem.getCreator());
					 item.setCreatetime(dbItem.getCreatetime());
				 }
			 }
		 }
		 Map<String, List<TCrmLogItem>> operatorMap = new HashMap<>();
		 operatorMap.put(OPERATOR_ADD, insertList);
		 operatorMap.put(OPERATOR_DEL, delList);
		 operatorMap.put(OPERATOR_MOD, modList);
		 
		 if (dbMap.size() == 0)
		 {
			 return operatorMap;
		 }
		 
		 java.util.Iterator<Map.Entry<Integer, TCrmLogItem>> iterator = dbMap.entrySet().iterator();
		 while (iterator.hasNext())
		 {
			 Map.Entry<Integer, TCrmLogItem> entry = iterator.next();
			 delList.add(entry.getValue());
		 }
		 return operatorMap;
	}

	@Override
	public boolean isOwner(String creatorNo,String logids) {
		if (CommonUtil.isNull(logids))
		{
			return false;
		}
		List<Integer> logidList = new ArrayList<>();
		Example example = new Example(TCrmLog.class);
		Example.Criteria c = example.createCriteria();
		
		try
		{
			String[] logidArray = logids.split(",");
			for (String logid:logidArray)
			{
				logidList.add(CommonUtil.replaceNullInt(logid));
			}
			c.andIn("logid", logidList);
			c.andNotEqualTo("creatorNo", creatorNo);
			List<TCrmLog> logList = tCrmLogMapper.selectByExample(example);
			return CommonUtil.getLen(logList) == 0;
		}
		catch(Exception ex)
		{
			logger.error("检查工作日志所有权时出错,参数={logids=" + logids + "}", ex);
			return false;
		}
	}

	@Override
	public List<String> getMyStaffEno(String eno) {
		if(CommonUtil.isNull(eno)){
			return null;
		}
		List<String> staffEnoList = new ArrayList<>();
		try
		{
			staffEnoList = tSysEmployeeMapper.selectStaffEno(eno);
			return staffEnoList;
		}
		catch(Exception ex)
		{
			return null;
		}
	}

	@Override
	public List<String> getMyTeamEno(String eno) {
		if(CommonUtil.isNull(eno)){
			return null;
		}
		List<String> teamEnoList = new ArrayList<>();
		try
		{
			teamEnoList = tSysEmployeeMapper.selectTeamEno(eno);
			return teamEnoList;
		}
		catch(Exception ex)
		{
			return null;
		}
	}

	@Override
	public boolean updateLogInfo(TCrmLog tCrmLog) {
		if(CommonUtil.isNull(tCrmLog)){
			return false;
		}
		int num ;
		num = tCrmLogMapper.updateByPrimaryKey(tCrmLog);
		return num >0 ;
	}

	@Override
	public TCrmLog getInfo(Integer logid) {
		TCrmLog tcrmLog = tCrmLogMapper.selectByPrimaryKey(logid);
		return tcrmLog;
	}

	@Override
	public boolean isLeader(String leaderNo,String eno) {
		if (CommonUtil.isNull(eno))
		{
			return false;
		}
		List<String> creatorNos = new ArrayList<>();
		try
		{
			creatorNos = tSysEmployeeMapper.isLeader(leaderNo);
			for(String creatorNo : creatorNos){
				if(eno.equals(creatorNo)){
					return true;
				}
			}
			return false;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
}

