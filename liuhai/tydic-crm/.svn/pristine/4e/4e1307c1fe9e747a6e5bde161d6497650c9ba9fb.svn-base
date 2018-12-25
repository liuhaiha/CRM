package com.tydic.traffic.crm.vo;

import java.util.List;

/**
 * 
 * TreeNode
 * @desc 
 * @author wlhuang
 * @version V1.0 2018年7月25日
 * @since V1.0
 */
public class TreeNode {
	
	//节点id
	private String id;
	
	private String alias;
	
	//节点名称
	private String name;
	//节点是否打开
	private boolean spread;
	//子节点
	private List<TreeNode> children;
	
	private int level = 0;
	
	// 上一节点ID/code
	private int pid;
	private String code;
	private String pcode;
	private String type;
	private boolean checked;
	private boolean flag;
	private String checkboxValue;
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public TreeNode() {
		// TODO Auto-generated constructor stub
	}

	public TreeNode(String id, String name, boolean spread, List<TreeNode> children) {
		super();
		this.id = id;
		this.name = name;
		this.spread = spread;
		this.children = children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSpread() {
		return spread;
	}

	public void setSpread(boolean spread) {
		this.spread = spread;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "TreeNode [id=" + id + ", name=" + name + ", spread=" + spread + ", children=" + children + "]";
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCheckboxValue() {
		return checkboxValue;
	}

	public void setCheckboxValue(String checkboxValue) {
		this.checkboxValue = checkboxValue;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
