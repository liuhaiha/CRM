/*
 * 文 件 名:  PermissMenu.java
 * 版    权:   Tydic Copyright 2018,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  wlhuang
 * 修 改 人:  
 * 修改时间:  
 * 修改内容:  <修改内容>
 */
package com.tydic.traffic.crm.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * PermissMenu
 * 
 * @desc
 * @author wlhuang
 * @version V1.0 2018年8月20日
 * @since V1.0
 */
public class PermissMenu 
{
	/**
	 * 菜单名称
	 */
	private String title;
	
	/**
	 * 菜单图标
	 */
	private String icon;
	
	/**
	 * 是否展开下一级菜单，默认为
	 */
	private boolean spread = false;
	
	private List<PermissMenu> children = new ArrayList<>();
	
	private String href;
	
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isSpread() {
		return spread;
	}

	public void setSpread(boolean spread) {
		this.spread = spread;
	}

	public List<PermissMenu> getChildren() {
		return children;
	}

	public void setChildren(List<PermissMenu> children) {
		this.children = children;
	}
}
