package com.tydic.traffic.crm.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_sys_code")
public class TSysCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cid;

    private String name;

    private String code;

    private String category;
    
    private Integer sequence;

    private Integer pid;

    /**
     * 1、扩展类，允许修改 2、系统类，不允许修改，修改可能影响系统功能
     */
    private String type;

    private String creator;

    private Date createtime;

    private String modifier;
    
    private Date modifytime;

    /**
     * @return cid
     */
    public Integer getCid() {
        return cid;
    }

    /**
     * @param cid
     */
    public void setCid(Integer cid) {
        this.cid = cid;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
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
     * @return category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	/**
     * @return pid
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * @param pid
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * 获取1、扩展类，允许修改 2、系统类，不允许修改，修改可能影响系统功能
     *
     * @return type - 1、扩展类，允许修改 2、系统类，不允许修改，修改可能影响系统功能
     */
    public String getType() {
        return type;
    }

    /**
     * 设置1、扩展类，允许修改 2、系统类，不允许修改，修改可能影响系统功能
     *
     * @param type 1、扩展类，允许修改 2、系统类，不允许修改，修改可能影响系统功能
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