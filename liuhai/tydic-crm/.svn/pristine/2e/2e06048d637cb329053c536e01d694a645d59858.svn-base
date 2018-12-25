package com.tydic.traffic.crm.dao;

import java.util.List;
import com.tydic.traffic.crm.entity.TCrmLinkman;
import com.tydic.traffic.crm.vo.TCrmLinkmanVo;
import com.tydic.traffic.tafa.daf.mybatis.tex.DataSourceName;
import com.tydic.traffic.tafa.daf.mybatis.tk.common.Mapper;

@DataSourceName(name = "crmSqlSessionFactory")
public interface TCrmLinkmanMapper extends Mapper<TCrmLinkman> {
	
	List<TCrmLinkmanVo> queryLinkmanList();
	List<TCrmLinkmanVo> queryLinkmanListById(Integer cid);
	List<TCrmLinkman> queryLinkmanListByName(TCrmLinkman linkman);
}