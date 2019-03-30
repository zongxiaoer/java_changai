package com.yuntongxun.itsys.base.po.cancer;

import java.io.Serializable;
import java.util.Date;

public class HospitalCancerReportPO implements Serializable {
    private Integer id;

    private String sid;

    private Date tbTableDate;

    private String checkYears;

    private String investigatorCode;

    private String tbTablePerson;

    private String qualityControlPerson;

    private Date dateCreated;

    private Date updateTime;

    private Integer deptCode;

    private Integer createUser;

    private Integer stage;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    public Date getTbTableDate() {
        return tbTableDate;
    }

    public void setTbTableDate(Date tbTableDate) {
        this.tbTableDate = tbTableDate;
    }

    public String getCheckYears() {
        return checkYears;
    }

    public void setCheckYears(String checkYears) {
        this.checkYears = checkYears == null ? null : checkYears.trim();
    }

    public String getInvestigatorCode() {
        return investigatorCode;
    }

    public void setInvestigatorCode(String investigatorCode) {
        this.investigatorCode = investigatorCode == null ? null : investigatorCode.trim();
    }

    public String getTbTablePerson() {
        return tbTablePerson;
    }

    public void setTbTablePerson(String tbTablePerson) {
        this.tbTablePerson = tbTablePerson == null ? null : tbTablePerson.trim();
    }

    public String getQualityControlPerson() {
        return qualityControlPerson;
    }

    public void setQualityControlPerson(String qualityControlPerson) {
        this.qualityControlPerson = qualityControlPerson == null ? null : qualityControlPerson.trim();
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(Integer deptCode) {
        this.deptCode = deptCode;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }
}