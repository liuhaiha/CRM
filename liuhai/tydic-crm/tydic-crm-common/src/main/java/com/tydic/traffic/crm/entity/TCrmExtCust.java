package com.tydic.traffic.crm.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_crm_ext_cust")
public class TCrmExtCust {
    @Id
    private Integer id;

    private Integer aid;

    private String name;

    /**
     * 编码,来源数据字典
     */
    private String sex;

    private String dept;

    @Column(name = "super_dept")
    private String superDept;

    /**
     * 编码,来源数据字典
     */
    private String post;

    private String officephone;

    /**
     * 可以填多个号码
     */
    private String telephone;

    private String email;

    private String creator;

    private Date createtime;

    private String modifier;

    private Date modifytime;
    
    private String extend1;
    private String extend2;
    private String extend3;

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
     * @return aid
     */
    public Integer getAid() {
        return aid;
    }

    public String getExtend1() {
		return extend1;
	}

	public void setExtend1(String extend1) {
		this.extend1 = extend1;
	}

	public String getExtend2() {
		return extend2;
	}

	public void setExtend2(String extend2) {
		this.extend2 = extend2;
	}

	public String getExtend3() {
		return extend3;
	}

	public void setExtend3(String extend3) {
		this.extend3 = extend3;
	}

	/**
     * @param aid
     */
    public void setAid(Integer aid) {
        this.aid = aid;
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
     * 获取编码,来源数据字典
     *
     * @return sex - 编码,来源数据字典
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置编码,来源数据字典
     *
     * @param sex 编码,来源数据字典
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return dept
     */
    public String getDept() {
        return dept;
    }

    /**
     * @param dept
     */
    public void setDept(String dept) {
        this.dept = dept;
    }

    /**
     * @return super_dept
     */
    public String getSuperDept() {
        return superDept;
    }

    /**
     * @param superDept
     */
    public void setSuperDept(String superDept) {
        this.superDept = superDept;
    }

    /**
     * 获取编码,来源数据字典
     *
     * @return post - 编码,来源数据字典
     */
    public String getPost() {
        return post;
    }

    /**
     * 设置编码,来源数据字典
     *
     * @param post 编码,来源数据字典
     */
    public void setPost(String post) {
        this.post = post;
    }

    /**
     * @return officephone
     */
    public String getOfficephone() {
        return officephone;
    }

    /**
     * @param officephone
     */
    public void setOfficephone(String officephone) {
        this.officephone = officephone;
    }

    /**
     * 获取可以填多个号码
     *
     * @return telephone - 可以填多个号码
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * 设置可以填多个号码
     *
     * @param telephone 可以填多个号码
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
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