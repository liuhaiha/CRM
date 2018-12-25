package com.tydic.traffic.crm.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_sys_role")
public class TSysRole {
    @Override
	public String toString() {
		return "TSysRole [rid=" + rid + ", rname=" + rname + ", rdesc=" + rdesc + ", creator=" + creator
				+ ", createtime=" + createtime + ", modifier=" + modifier + ", modifytime=" + modifytime + "]";
	}

	@Id
    private Integer rid;

    private String rname;

    private String rdesc;

    private String creator;

    private Date createtime;

    private String modifier;

    private Date modifytime;

    /**
     * @return rid
     */
    public Integer getRid() {
        return rid;
    }

    /**
     * @param rid
     */
    public void setRid(Integer rid) {
        this.rid = rid;
    }

    /**
     * @return rname
     */
    public String getRname() {
        return rname;
    }

    /**
     * @param rname
     */
    public void setRname(String rname) {
        this.rname = rname;
    }

    /**
     * @return rdesc
     */
    public String getRdesc() {
        return rdesc;
    }

    /**
     * @param rdesc
     */
    public void setRdesc(String rdesc) {
        this.rdesc = rdesc;
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