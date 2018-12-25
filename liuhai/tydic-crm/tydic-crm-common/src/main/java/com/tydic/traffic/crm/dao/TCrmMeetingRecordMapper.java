package com.tydic.traffic.crm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tydic.traffic.crm.entity.TCrmMeetingRecord;
import com.tydic.traffic.crm.entity.TSysEmployee;
import com.tydic.traffic.tafa.daf.mybatis.tex.DataSourceName;
import com.tydic.traffic.tafa.daf.mybatis.tk.common.Mapper;

@DataSourceName(name = "crmSqlSessionFactory")
public interface TCrmMeetingRecordMapper extends Mapper<TCrmMeetingRecord> {
	
	public List<String> queryPeerName(Map<String, Object> params);
	public List<TSysEmployee> queryPeerNameList(Map<String, Object> params);
	List<TCrmMeetingRecord> queryMeetingListByCid(@Param("cid")Integer cid);
	List<TCrmMeetingRecord> queryLinkmanListByPid(@Param("cid")Integer cid, @Param("pid")Integer pid);
	
}