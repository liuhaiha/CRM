package com.tydic.traffic.crm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tydic.traffic.crm.dao.TCrmProjectMapper;
import com.tydic.traffic.crm.dao.TSysCodeMapper;
import com.tydic.traffic.crm.dao.TSysPermissionMapper;
import com.tydic.traffic.crm.entity.TCrmProject;
import com.tydic.traffic.crm.entity.TSysCode;
import com.tydic.traffic.crm.entity.TSysPermission;
import com.tydic.traffic.crm.service.PermissionService;
import com.tydic.traffic.crm.utils.CommonUtil;
import com.tydic.traffic.tafa.daf.mybatis.tk.entity.Example;
import com.tydic.traffic.tafa.daf.mybatis.tk.entity.Example.Criteria;
import com.tydic.traffic.tafa.daf.page.Page;
import com.tydic.traffic.tafa.daf.page.annotion.Pageable;

/**
 * 
 * SaleTrackServiceImpl
 * @desc 
 * @author wlhuang
 * @version V1.0 2018年7月29日
 * @since V1.0
 */
@Service
public class PermissionServiceImpl implements PermissionService {
	
	@Autowired
	private TCrmProjectMapper projectMapper;
	@Autowired
	private TSysCodeMapper codeMapper;
	@Autowired
	private TSysPermissionMapper permMapper;

	@Override
	public List<TCrmProject> findAllLinkman() {
		return projectMapper.selectByExample(null);
	}

	@Override
	public TSysCode getName(int pid) {
		return codeMapper.selectByPrimaryKey(pid);
	}

	@Override
	public List<TSysPermission> getAllPermission(String pcode) {
		Example example = new Example(TSysPermission.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("pcode", pcode);
		return permMapper.selectByExample(example);
	}

	@Override
	public TSysPermission addTree(TSysPermission permission) {
		int index = permMapper.insert(permission);
		if (index == 0) {
			return null;
		}
		Example example = new Example(TSysPermission.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("pcode", permission.getPcode());
		criteria.andEqualTo("title", permission.getTitle());
		criteria.andEqualTo("code", permission.getCode());
		List<TSysPermission> list = permMapper.selectByExample(example);
		TSysPermission sysPermission = CommonUtil.getLen(list) > 0 ? list.get(0) : null;
		return sysPermission;
	}

	@Override
	public int delTree(Integer id) {
		return permMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Pageable
	public void queryPermissionList(Page<TSysPermission> pageResult, String pcode) {
		
		List<TSysPermission> permList = null;
		
		Example example = new Example(TSysPermission.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("pcode", pcode);
		permList = permMapper.selectByExample(example);
	}

	@Override
	public TSysPermission findPermissionById(int id) {
		return permMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updatePermission(TSysPermission permission) {
		return permMapper.updateByPrimaryKeySelective(permission);
	}

	@Override
	public int delPermission(int id) {
		return permMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Pageable
	public void search(Page<TSysPermission> pageResult, TSysPermission permission) {
		List<TSysPermission> list = permMapper.searchByText(permission);
	}

	@Override
	public List<TSysPermission> getListCount(String code) {
		return permMapper.getListCount();
	}

}
