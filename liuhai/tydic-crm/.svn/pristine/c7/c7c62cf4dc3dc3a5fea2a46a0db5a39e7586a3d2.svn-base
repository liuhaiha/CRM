package com.tydic.traffic.crm.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tydic.traffic.crm.entity.TCrmProject;
import com.tydic.traffic.crm.entity.TSysCode;
import com.tydic.traffic.crm.entity.TSysPermission;
import com.tydic.traffic.crm.service.PermissionService;
import com.tydic.traffic.crm.utils.CommonUtil;
import com.tydic.traffic.crm.vo.TCrmProjectVo;
import com.tydic.traffic.crm.vo.TreeNode;
import com.tydic.traffic.crm.vo.UserInfo;
import com.tydic.traffic.tafa.daf.page.Page;

/**
 * 
 * SaleTrackController
 * @desc 
 * @version V1.0 2018年7月30日
 * @since V1.0
 */
@Controller
@RequestMapping("/saleTrack")
public class PermissionController extends BaseController {
	
	@Autowired
	private PermissionService trackService;
	
	@RequestMapping("/findAllLinkman")
	@ResponseBody
	public List<TCrmProjectVo> findAllLinkman() {
		List<TCrmProjectVo> proList = new ArrayList<TCrmProjectVo>();
		List<TCrmProject> list = trackService.findAllLinkman();
		for (TCrmProject pro : list) {
			TCrmProjectVo projectVo = new TCrmProjectVo();
			try {
				BeanUtils.copyProperties(projectVo, pro);
				TSysCode code = trackService.getName(Integer.valueOf(pro.getStage()));
				projectVo.setStageName(code.getName());
				proList.add(projectVo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return proList;
	}
	
	/**
	 * 获取权限树数据
	 * @return
	 */
	@RequestMapping("/getAllPermission")
	@ResponseBody
	public List<TreeNode> getAllPermission() {
		List<TreeNode> treeList = new ArrayList<>();
		List<TSysPermission> permList = trackService.getAllPermission("0");
		if (permList != null) {
			for (int i = 0, len = CommonUtil.getLen(permList); i < len; i++) {
				TSysPermission permission = permList.get(i);
				TreeNode parentNode = convert(permission);
				parentNode.setLevel(1);
				List<TreeNode> nodeList = getChild(parentNode, permission);
				parentNode.setChildren(nodeList);
				treeList.add(parentNode);
			}
		}
		return treeList;
	}

	private TreeNode convert(TSysPermission permission) {
		TreeNode node = new TreeNode();
		node.setId("" + permission.getId());
		node.setName(permission.getTitle());
		node.setSpread(true);
		node.setCode(permission.getCode());
		node.setPcode(permission.getPcode());
		node.setType(permission.getType());
		return node;
	}
	
	private List<TreeNode> getChild(TreeNode parentNode, TSysPermission permission) {
		List<TreeNode> childList = new ArrayList<>();
		List<TSysPermission> permList = trackService.getAllPermission(permission.getCode());
		if (permList == null) {
			return null;
		}
		for (TSysPermission sysPermission : permList) {
			TreeNode node = convert(sysPermission);
			node.setLevel(parentNode.getLevel() + 1);
			childList.add(node);
			List<TreeNode> list = getChild(node, sysPermission);
			List<TreeNode> typeList = new ArrayList<>();
			if (list != null && list.size() > 0) {
				for (TreeNode treeNode : list) {
					if (treeNode.getType().equals("01")) {
						typeList.add(treeNode);
					}
				}
			}
			node.setChildren(typeList);
		}
		return childList;
	}
	
	/**
	 * 增加树节点
	 * @param permission
	 * @return
	 */
	@RequestMapping("/addTree")
	@ResponseBody
	public TSysPermission addTree(@ModelAttribute("TSysPermission") TSysPermission permission) {
		UserInfo userInfo = super.getUser();
		if (null != userInfo) {
			permission.setCreator(userInfo.getEname());
			permission.setModifier(userInfo.getEname());
		}
		permission.setCreatetime(new Date());
		permission.setModifytime(new Date());
		return trackService.addTree(permission);
	}
	
	/**
	 * 删除树节点
	 * @param id
	 * @return
	 */
	@RequestMapping("/delTree/{id}")
	@ResponseBody
	public int delTree(@PathVariable Integer id) {
		return trackService.delTree(id);
	}
	
	/**
	 * 得到子节点数量
	 * @param code
	 * @return
	 */
	@RequestMapping("/getListCount/{code}")
	@ResponseBody
	public TSysPermission getListCount(@PathVariable String code) {
		List<TSysPermission> list= trackService.getListCount(code);
		return list.get(list.size() - 1);
	}
	
	/**
	 * 查询权限列表
	 * @return
	 */
	@RequestMapping("/queryPermissionList")
	@ResponseBody
	public Page<TSysPermission> queryPermissionList(@Param("pageNumber") Integer pageNumber,
			@Param("pageSize") Integer pageSize, @Param("pcode") String pcode) {
		if (null == pageNumber) {
			pageNumber = 1;
		}
		if (null == pageSize) {
			pageSize = 2;
		}
		Page<TSysPermission> pageResult = new Page<>();
		pageResult.setPageNo(pageNumber);
		pageResult.setPageSize(pageSize);
		trackService.queryPermissionList(pageResult, pcode);
		return pageResult;
	}
	
	/**
	 * 获取单条权限信息数据
	 * @param id
	 * @return
	 */
	@RequestMapping("/findPermissionById/{id}")
	@ResponseBody
	public TSysPermission findPermissionById(@PathVariable Integer id) {
		return trackService.findPermissionById(id);
	}
	
	/**
	 * 更新权限信息数据
	 * @param permission
	 * @return
	 */
	@RequestMapping("permission/update")
	@ResponseBody
	public int updatePermission(@ModelAttribute("TSysPermission") TSysPermission permission) {
		UserInfo userInfo = super.getUser();
		if (null != userInfo) {
			permission.setModifier(userInfo.getEname());
		}
		permission.setModifytime(new Date());
		return trackService.updatePermission(permission);
	}
	
	/**
	 * 删除单条权限信息数据
	 * @param id
	 * @return
	 */
	@RequestMapping("delPermission/{id}")
	@ResponseBody
	public int delPermission(@PathVariable Integer id) {
		return trackService.delPermission(id);
	}
	
	/**
	 * 搜索权限名称
	 * @param pageNumber
	 * @param pageSize
	 * @param permission
	 * @return
	 */
	@RequestMapping("/search")
	@ResponseBody
	public Page<TSysPermission> search(@Param("pageNumber") Integer pageNumber,
			@Param("pageSize") Integer pageSize, @ModelAttribute("TSysPermission") TSysPermission permission) {
		if (null == pageNumber) {
			pageNumber = 1;
		}
		if (null == pageSize) {
			pageSize = 10;
		}
		Page<TSysPermission> pageResult = new Page<>();
		pageResult.setPageNo(pageNumber);
		pageResult.setPageSize(pageSize);
		trackService.search(pageResult, permission);
		return pageResult;
	}

}
