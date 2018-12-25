/*
 * 文 件 名:  SysCodeController.java
 * 版    权:   Tydic Copyright 2018,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  wlhuang
 * 修 改 人:  
 * 修改时间:  
 * 修改内容:  <修改内容>
 */
package com.tydic.traffic.crm.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.traffic.crm.common.cache.SysCodeCache;
import com.tydic.traffic.crm.utils.CommonUtil;
import com.tydic.traffic.crm.vo.SysCodeVo;

/**
 * 
 * SysCodeController
 * @desc 数据字典
 * @author wlhuang
 * @version V1.0 2018年7月26日
 * @since V1.0
 */
@Controller
@RequestMapping("/code")
public class SysCodeController extends BaseController{
	/**
	 * 获取数据字典
	 * @return
	 */
	@RequestMapping("/getCode")
	@ResponseBody
	public SysCodeVo getCode(@Param("category") String category)
	{
		return SysCodeCache.getSysCode(category);
	}
	
	/**
	 * 获取多个数据字典
	 * @return
	 */
	@RequestMapping("/getMultCode")
	@ResponseBody
	public List<SysCodeVo> getMultCode(@Param("category") String category)
	{
		if (CommonUtil.isNull(category))
		{
			return new ArrayList<SysCodeVo>();
		}
		List<SysCodeVo> codeList = new ArrayList<>();
		String[] categorys = category.trim().split(",");
		for (String str:categorys)
		{
			SysCodeVo vo = SysCodeCache.getSysCode(str);
			if (null != vo)
			{
				codeList.add(vo);
			}
		}
		return codeList;
	}
}

