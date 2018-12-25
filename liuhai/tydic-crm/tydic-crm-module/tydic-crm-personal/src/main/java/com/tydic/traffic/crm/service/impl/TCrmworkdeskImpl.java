package com.tydic.traffic.crm.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.axis2.databinding.types.xsd.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tydic.traffic.crm.dao.TCrmWorkdeskMapper;
import com.tydic.traffic.crm.service.TCrmworkdeskService;

@Service
public class TCrmworkdeskImpl implements TCrmworkdeskService {
	
	private static final Logger logger= LoggerFactory.getLogger(TCrmWorklogServiceImpl.class); 
	
	@Resource
	private TCrmWorkdeskMapper tCrmWorkdeskMapper;
	
	/**
	 * 	查询本周内的顾客生日
	 **/
	@Override
	public List<Map<String, Object>> queryBirthdayWeekly() {
		List<Map<String, Object>> list=tCrmWorkdeskMapper.queryBirthdayWeekly();
		//排除闰年2月29号这个坑货日期,MYSQL居然可以承认STR_TO_DATE('2018-02-29','%Y-%m-%d')这样的日期
		for(int i=0;i<list.size();i++) {
			String t=list.get(i).get("birthday").toString();
			String[] birthday=t.substring(0, t.indexOf(" ")).split("[/|-]");
			if(Integer.parseInt(birthday[1])==2 && Integer.parseInt(birthday[2])==29) {
				int year=Calendar.getInstance().get(Calendar.YEAR);
				if(!(year%4==0 && year%100!=0 || year%400==0 )) {
					list.remove(i);
					i--;
				}
			}
		}
		//排除显示生日时间的跨年问题
		for(int i=0;i<list.size();i++) {
			String t=list.get(i).get("birthday").toString();
			String[] birthday=t.substring(0, t.indexOf(" ")).split("[/|-]");
			int year=Calendar.getInstance().get(Calendar.YEAR);
			int month=Calendar.getInstance().get(Calendar.MONTH);
			if(Integer.parseInt(birthday[1])<month) {
				year+=1;
			}
			birthday[0]=String.valueOf(year);
			list.get(i).put("next_birthday", birthday[0]+"-"+birthday[1]+"-"+birthday[2]);
		}
		return list;
	}
	
	/**
	 * 	查询待办事情
	 * */
	@Override
	public List<Map<String, Object>> queryBacklogList(Map<String, Object> params) {
		List<Map<String, Object>> list=tCrmWorkdeskMapper.queryBacklog(params);
		if(list==null) {
			return null;
		}else {
			String[] temp;
			Map<String, Object> param=new HashMap<String, Object>();
			for(Map<String, Object> map:list) {
				if(map.get("ext_owner")==null)
					continue;
				temp=map.get("ext_owner").toString().split(",");
				param.put("eno",temp);
				map.put("ext_owner",tCrmWorkdeskMapper.queryEname(param));
			}
			return list;
		}
	}
	/**
	 * 查询未审批事件
	 */
	@Override
	public java.util.List<java.util.Map<String,Object>> queryUnapproveList(java.util.Map<String,Object> params) {
		List<Map<String, Object>> list=tCrmWorkdeskMapper.queryUnapprove(params);
		if(list==null) {
			return null;
		}else {
			String[] temp;
			Map<String, Object> param=new HashMap<String, Object>();
			for(Map<String, Object> map:list) {
				if(map.get("ext_owner")==null)
					continue;
				temp=map.get("ext_owner").toString().split(",");
				param.put("eno",temp);
				map.put("ext_owner",tCrmWorkdeskMapper.queryEname(param));
			}
			return list;
		}
	};
	
	/**
	 * 	查询当月事情
	 */
	@Override
	public List<Map<String, Object>> queryBacklogInCurMonth(Map<String, Object> params) {
		return tCrmWorkdeskMapper.queryBacklogInCurMonth(params);
	}
	/**
	 *	查询一个月以上未跟进项目 
	 */
	@Override
	public List<Map<String, Object>> queryProjectBeyondMonth() {
		return tCrmWorkdeskMapper.queryProjectBeyondMonth();
	}
}
