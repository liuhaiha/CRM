package com.tydic.traffic.crm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tydic.traffic.crm.common.cache.SysCodeCache;
import com.tydic.traffic.crm.dao.TSysCodeMapper;
import com.tydic.traffic.crm.entity.TSysCode;
import com.tydic.traffic.crm.service.SysCodeService;
import com.tydic.traffic.crm.utils.CommonUtil;
import com.tydic.traffic.crm.vo.SysCodeVo;
import com.tydic.traffic.tafa.daf.mybatis.tk.entity.Example;
import com.tydic.traffic.tafa.daf.page.Page;
import com.tydic.traffic.tafa.daf.page.annotion.Pageable;

@Service(value = "sysCodeService")
public class SysCodeServiceImpl implements SysCodeService {

	private static final Logger logger= LoggerFactory.getLogger(SysCodeServiceImpl.class); 
	@Resource
	private TSysCodeMapper tSysCodeMapper;
	
	/**
	 * 查询所有数据字典
	 */
	public List<TSysCode> queryAll() {
//		Example example = new Example(TSysCode.class);
//		Example.Criteria criteria=example.createCriteria();
		List<TSysCode> codeList = null;
		try
		{
			codeList = tSysCodeMapper.selectAll();
		}
		catch(Exception ex)
		{
			logger.error("查询所有字典时出错",ex);
		}
		return codeList;
	}

	@Override
	public boolean deleteCodeById(Integer id) {
		int num = 0;
		TSysCode code = tSysCodeMapper.selectByPrimaryKey(id);
		num = tSysCodeMapper.deleteByPrimaryKey(id);
		// 清除缓存中的数据字典
		if (num > 0)
		{
			SysCodeCache.removeSysCode(code.getCategory(), code.getCid());
		}
		return num!=0;
	}
	
	@Override
	public List<Map<String, Object>> getMenu(){
		List<TSysCode> list =tSysCodeMapper.primaryMenu();
		List<Map<String, Object>> menu=new ArrayList<Map<String, Object>>();
		SysCodeVo code;
		for(TSysCode vo :list){
			code=SysCodeCache.getSysCode(vo.getCategory());
			if(code==null)
				continue;
			else{
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("name", vo.getName());
				map.put("cid", vo.getCid());
				map.put("spread", false);
				map.put("category", vo.getCategory());
				map.put("code", vo.getCode());
				map.put("type", vo.getType());
				map.put("pid", vo.getPid());
				map.put("children", getMenuDeep(code));
				menu.add(map);
			}
		}
		List<Map<String, Object>> menusys=new ArrayList<Map<String,Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "数据字典");
		map.put("cid", 0);
		map.put("spread", true);
		map.put("children", menu);
		menusys.add(map);
		return menusys;
	}
	
	public List<Map<String, Object>> getMenuDeep(SysCodeVo root) {
		List<SysCodeVo> list = root.getChildList();
		if (list == null || list.isEmpty() || list.size() == 0) {
			return null;
		}
		List<Map<String, Object>> menu = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (SysCodeVo vo : list) {
			map = new HashMap<String, Object>();
			map.put("name", vo.getName());
			map.put("cid", vo.getCid());
			map.put("spread", false);
			map.put("category", vo.getCategory());
			map.put("code", vo.getCode());
			map.put("type", vo.getType());
			map.put("pid", vo.getPid());
			List<Map<String, Object>> children = getMenuDeep(vo);
			if (children != null) {
				map.put("children", children);
			}
			menu.add(map);
		}
		return menu;
	}
	
	
	/**
	 * 检查添加或修改的数据字典是否重复
	 * @param code
	 * @return
	 */
	public boolean checkDuplicateCode(TSysCode code){
		if (null != code.getCid() && code.getCid()!=0)
		{
			Example example = new Example(TSysCode.class);
			Example.Criteria criteria = example.createCriteria();
			criteria.andEqualTo("code", code.getCode());
			if (CommonUtil.isNull(code.getCategory()) || (null == code.getPid() || code.getPid() == 0))
			{
				code = tSysCodeMapper.selectByPrimaryKey(code.getCid());
			}
			criteria.andEqualTo("category", code.getCategory());
			criteria.andEqualTo("pid", code.getPid());
			criteria.andNotEqualTo("cid", code.getCid());
			List<TSysCode> list = tSysCodeMapper.selectByExample(example);
			
			return CommonUtil.getLen(list)==0;
		}
		return true;
	}
	
	@Override
	public boolean addCodeMenu(TSysCode code){
		
		if(code.getCid()==null || code.getCid()==0){//添加根菜单
			String category = String.valueOf(tSysCodeMapper.maxCategory()+1);
			int len=5-category.length();
			for(int i=0;i<len;i++){
				category="0"+category;
			}
			code.setCategory(category);
			code.setPid(0);
			code.setCid(null);
		}else{//添加子菜单
			TSysCode tcode=new TSysCode();
			tcode.setCategory(code.getCategory());
			code.setPid(code.getCid());
			code.setCid(null);
		}
		SysCodeVo codeVo = new SysCodeVo();
		try {
			BeanUtils.copyProperties(codeVo, code);
		} catch (Exception e) {
			logger.error("", e);
		}
		SysCodeCache.addSysCode(codeVo.getCategory(), codeVo);
		
		return tSysCodeMapper.insertByObject(code)>0;
	}
	
	@Override
	public boolean updateCodeMenu(TSysCode code){
		int num = tSysCodeMapper.updateById(code);
		TSysCode dbCode = tSysCodeMapper.selectByPrimaryKey(code.getCid());
		// name=#{name},code=#{code},sequence=#{sequence},modifier=#{modifier},modifytime=#{modifytime}
		dbCode.setName(code.getName());
		dbCode.setCode(code.getCode());
		dbCode.setSequence(code.getSequence());
		dbCode.setModifier(code.getModifier());
		dbCode.setModifytime(code.getModifytime());
		if (num > 0)
		{
			SysCodeVo codeVo = new SysCodeVo();
			try {
				BeanUtils.copyProperties(codeVo, dbCode);
			} catch (Exception e) {
				logger.error("", e);
			}
			// 更新缓存中的数据字典
			SysCodeCache.replaceSysCode(codeVo.getCategory(), codeVo);
		}
		return num>0;
	}
	
	@Override
	public boolean deleteById(Integer id){
		TSysCode code = tSysCodeMapper.selectByPrimaryKey(id);
		int num = tSysCodeMapper.deleteByPrimaryKey(id);
		// 清除缓存中的数据字典
		if (num > 0)
		{
			SysCodeCache.removeSysCode(code.getCategory(), code.getCid());
		}
		return num>0;
	}
	
	@Pageable
	@Override
	public List<TSysCode> queryCodes(Page<TSysCode> result, TSysCode code) {
		return tSysCodeMapper.queryByObject(code);
	}

	@Override
	public TSysCode getCode(Integer cid) {
		return tSysCodeMapper.selectByPrimaryKey(cid);
	}
}