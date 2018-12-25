package com.tydic.traffic.crm.service;

import java.util.List;
import com.tydic.traffic.crm.entity.TCrmProject;
import com.tydic.traffic.crm.entity.TSysCode;
import com.tydic.traffic.crm.entity.TSysPermission;
import com.tydic.traffic.tafa.daf.page.Page;
import com.tydic.traffic.tafa.daf.page.annotion.Pageable;

/**
 * 
 * SaleTrackService
 * @desc 
 * @author wlhuang
 * @version V1.0 2018年7月29日
 * @since V1.0
 */
public interface PermissionService {
	
	public List<TCrmProject> findAllLinkman();
	
	public TSysCode getName(int pid);
	
	public List<TSysPermission> getAllPermission(String pcode);
	
	public TSysPermission addTree(TSysPermission permission);
	
	public int delTree(Integer id);
	
	public List<TSysPermission> getListCount(String code);
	@Pageable
	public void queryPermissionList(Page<TSysPermission> pageResult, String pcode);
	public TSysPermission findPermissionById(int id);
	public int updatePermission(TSysPermission permission);
	public int delPermission(int id);
	
	@Pageable
	public void search(Page<TSysPermission> pageResult, TSysPermission permission);

}
