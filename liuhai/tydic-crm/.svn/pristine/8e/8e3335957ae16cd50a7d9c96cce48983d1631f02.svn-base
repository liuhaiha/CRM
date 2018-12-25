/*
 * 文 件 名:  TSysRoleController.java
 * 版    权:   Tydic Copyright 2018,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  wlhuang
 * 修 改 人:  
 * 修改时间:  
 * 修改内容:  <修改内容>
 */
package com.tydic.traffic.crm.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.tydic.traffic.crm.vo.TSysRoleVo;
import com.tydic.traffic.crm.vo.TreeNode;
import com.tydic.traffic.tafa.daf.page.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tydic.traffic.crm.dao.TSysRoleMapper;
import com.tydic.traffic.crm.entity.TSysPermission;
import com.tydic.traffic.crm.entity.TSysRole;
import com.tydic.traffic.crm.entity.TSysRoleRefPermission;
import com.tydic.traffic.crm.service.PermissionService;
import com.tydic.traffic.crm.service.TSysRoleService;
import com.tydic.traffic.crm.utils.CommonUtil;
import com.tydic.traffic.crm.vo.UserInfo;
import com.tydic.traffic.tafa.web.JsonResult;

import springfox.documentation.spring.web.json.Json;

/**
 * 
 * TSysRoleController
 * @desc 角色管理类
 * @author wlhuang
 * @version V1.0 2018年8月1日
 * @since V1.0
 */
@Controller
@RequestMapping("/role")
public class TSysRoleController  extends BaseController{
	
	@Resource
	private TSysRoleService tSysRoleService = null;
	
	@Autowired
	private PermissionService trackService;
	
	@Autowired
	TSysRoleMapper tSysRoleMapper;
	
	@RequestMapping("/queryRole")
	@ResponseBody
	public JsonResult queryRole(@ModelAttribute("role") TSysRole role)
	{
		JsonResult result = new JsonResult();
		List<TSysRole> roleList = tSysRoleService.queryRole(role);
		if (null == roleList)
		{
			result.setCode("-1");
		}
		else
		{
			result.setCode("1");
		}
		result.setData(roleList);
		return result;
	}
	
	/**
	 * 获取创建人列表
	 * @return
	 */
	@RequestMapping("/getCreater")
	@ResponseBody
	public String getCreater(){
		return tSysRoleService.getCreater();
	}
	
	/**
	 * 保存角色
	 */
	@RequestMapping("/saveTSysRole")
	@ResponseBody
	public String saveRole(@ModelAttribute() TSysRole tSysRole){
		String num = null;
		UserInfo user = super.getUser();
		tSysRole.setCreator(user.getEname());
		tSysRole.setCreatetime(new Date());
		tSysRole.setModifier(user.getEname());
		tSysRole.setModifytime(new Date());
		num = tSysRoleService.saveRole(tSysRole);
		return num;
	}
	/**
	 * getRole
	 * 获取角色列表(未分页)
	 */
	@RequestMapping("/getRole")
	@ResponseBody
	public List<TSysRole> getRole(){
		List<TSysRole> roles = tSysRoleMapper.selectAll();
		for(TSysRole tSysRole : roles){
			if(tSysRole.getCreator() == null){
				tSysRole.setCreator("");
			}
			if(tSysRole.getModifier() == null){
				tSysRole.setModifier("");
			}
		}
		return roles;
	}
	/**
	 *
	 * getRoleByPage
	 * 获取角色列表（分页）
	 */
	@RequestMapping("/getRoleByPage")
	@ResponseBody
	public Page<TSysRoleVo> getRoleByPage(@Param("pageNumber") Integer pageNumber,@Param("pageSize") Integer pageSize,@ModelAttribute("tSysRoleVo") TSysRoleVo tSysRoleVo){
		if(null == pageNumber){
			pageNumber = 1;
		}
		if(null == pageSize){
			pageSize = 10;
		}
		Page<TSysRoleVo> pageResult = new Page<>();
		pageResult.setPageNo(pageNumber);
		pageResult.setPageSize(pageSize);
		tSysRoleService.getRoleByPage(pageResult,tSysRoleVo);
		return pageResult;
	}


	/**
	 * delRole
	 * 删除选中角色
	 */
	@RequestMapping("/delRole")
	@ResponseBody
	public JsonResult delRole(@Param("checkids") String checkids){
		JsonResult result = new JsonResult();
		boolean succ = tSysRoleService.delRole(checkids);
		result.setCode(succ ? "1" : "-1");
		return result;
	}
	
	/**
	 * editRoleInfo
	 * 回显要编辑的角色信息
	 */
	@RequestMapping("/editRoleInfo")
	@ResponseBody
	public String editRoleInfo(@Param("checkid") Integer checkid){
		TSysRole tSysRole = tSysRoleMapper.selectByPrimaryKey(checkid);
		return JSON.toJSONString(tSysRole);
	}
	
	/**
	 * editRole
	 * 编辑选中的角色
	 */
	@RequestMapping("/editRole")
	@ResponseBody
	public JsonResult editRole(@ModelAttribute("tSysRole") TSysRole tSysRole){
		JsonResult result = new JsonResult();
		UserInfo user = super.getUser();
		tSysRole.setModifier(user.getEname());
		tSysRole.setModifytime(new Date());
		boolean succ = tSysRoleService.editRole(tSysRole);
		result.setCode(succ ? "1" : "-1");
		return result;
	}
	
	/**
	 * 获取权限列表
	 */
	@RequestMapping("/getRoleList")
	@ResponseBody
	public List<TreeNode> getRoleList(@Param("rid") Integer rid) {
		List<TreeNode> treeList = new ArrayList<>();
		List<TSysPermission> permList = trackService.getAllPermission("1");
		List<TSysRoleRefPermission> role=tSysRoleService.getPermissionManager(rid);
		if (permList != null) {
			for (int i = 0, len = CommonUtil.getLen(permList); i < len; i++) {
				TSysPermission permission = permList.get(i);
				TreeNode parentNode = convert(permission,role);
				parentNode.setLevel(1);
				List<TreeNode> nodeList = getChild(parentNode, permission,role);
				parentNode.setChildren(nodeList);
				treeList.add(parentNode);
			}
		}
		return treeList;
	}

	private TreeNode convert(TSysPermission permission,List<TSysRoleRefPermission> role) {
		TreeNode node = new TreeNode();
		node.setId("" + permission.getId());
		node.setName(permission.getTitle());
		node.setSpread(false);
		node.setCode(permission.getCode());
		node.setPcode(permission.getPcode());
		node.setType(permission.getType());
		node.setChecked(false);
		node.setFlag(false);
		for(TSysRoleRefPermission list:role) {
			if(permission.getId().equals(list.getId())) {
				node.setFlag(true);
				break;
			}
		}
		node.setCheckboxValue(permission.getId().toString());
		return node;
	}
	
	private List<TreeNode> getChild(TreeNode parentNode, TSysPermission permission,List<TSysRoleRefPermission> role) {
		List<TreeNode> childList = new ArrayList<>();
		List<TSysPermission> permList = trackService.getAllPermission(permission.getCode());
		if (permList == null) {
			return null; 
		}
		for (TSysPermission sysPermission : permList) {
			TreeNode node = convert(sysPermission,role);
			node.setLevel(parentNode.getLevel() + 1);
			childList.add(node);
			List<TreeNode> list = getChild(node, sysPermission,role);
			List<TreeNode> typeList = new ArrayList<>();
			if (list != null && list.size() > 0) {
				for (TreeNode treeNode : list) {
					if (treeNode.getType().equals("02")) {
						typeList.add(treeNode);
					}
				}
			}
			node.setChildren(typeList);
		}
		return childList;
	}
	
	/**
	 * 授权保存
	 * @param rpid
	 * @param rid
	 * @param permissionCheckIds
	 * @return
	 */
	@RequestMapping("/permissionManager")
	@ResponseBody
	public JsonResult permissionManager(@Param("rid") Integer rid ,@Param("permissionCheckIds") String permissionCheckIds){
		JsonResult result = new JsonResult();
		boolean succ = tSysRoleService.permissionManager(permissionCheckIds,rid);
		result.setCode(succ ? "1" : "-1");
		return result;
	}
	
}

