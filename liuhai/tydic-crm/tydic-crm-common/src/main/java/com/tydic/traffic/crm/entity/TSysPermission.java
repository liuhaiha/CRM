package com.tydic.traffic.crm.entity;

import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "t_sys_permission")
public class TSysPermission {
    @Id
    private Integer id;

    private String title;

    private String code;

    /**
     * 主要用于配置功能菜单的图标
     */
    private String icon;

    private String url;

    /**
     * 父级权限编码，用于构建权限菜单时区分上下级关系
     */
    private String pcode;

    /**
     * 1、功能菜单 2、功能（比如：增、删除、改、查询等功能）
     */
    private String type;

    private String creator;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    private String modifier;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date modifytime;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取主要用于配置功能菜单的图标
     *
     * @return icon - 主要用于配置功能菜单的图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置主要用于配置功能菜单的图标
     *
     * @param icon 主要用于配置功能菜单的图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取父级权限编码，用于构建权限菜单时区分上下级关系
     *
     * @return pcode - 父级权限编码，用于构建权限菜单时区分上下级关系
     */
    public String getPcode() {
        return pcode;
    }

    /**
     * 设置父级权限编码，用于构建权限菜单时区分上下级关系
     *
     * @param pcode 父级权限编码，用于构建权限菜单时区分上下级关系
     */
    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    /**
     * 获取1、功能菜单 2、功能（比如：增、删除、改、查询等功能）
     *
     * @return type - 1、功能菜单 2、功能（比如：增、删除、改、查询等功能）
     */
    public String getType() {
        return type;
    }

    /**
     * 设置1、功能菜单 2、功能（比如：增、删除、改、查询等功能）
     *
     * @param type 1、功能菜单 2、功能（比如：增、删除、改、查询等功能）
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * @param creator
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * @return createtime
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * @param createtime
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * @return modifier
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * @param modifier
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    /**
     * @return modifytime
     */
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * @param modifytime
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }
}