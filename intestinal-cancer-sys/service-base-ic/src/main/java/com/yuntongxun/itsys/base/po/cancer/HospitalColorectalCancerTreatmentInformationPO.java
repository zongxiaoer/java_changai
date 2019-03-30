package com.yuntongxun.itsys.base.po.cancer;

import java.io.Serializable;
import java.util.Date;

public class HospitalColorectalCancerTreatmentInformationPO implements Serializable {
    private Integer id;

    private String sid;

    private Date tbTableDate;

    private Integer deptCode;

    private String checkYears;

    private String investigatorCode;

    private String tbTablePerson;

    private String qualityControlPerson;

    private Integer item1;

    private Integer item2;

    private String item21;

    private Integer item3;

    private Date item31;

    private Integer item4;

    private Date item41;

    private Integer item5;

    private Date item51;

    private Integer item6;

    private String item61;

    private String item62;

    private Date dateCreated;

    private Date updateTime;

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

    public Integer getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(Integer deptCode) {
        this.deptCode = deptCode;
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

    public Integer getItem1() {
        return item1;
    }

    public void setItem1(Integer item1) {
        this.item1 = item1;
    }

    public Integer getItem2() {
        return item2;
    }

    public void setItem2(Integer item2) {
        this.item2 = item2;
    }

    public String getItem21() {
        return item21;
    }

    public void setItem21(String item21) {
        this.item21 = item21 == null ? null : item21.trim();
    }

    public Integer getItem3() {
        return item3;
    }

    public void setItem3(Integer item3) {
        this.item3 = item3;
    }

    public Date getItem31() {
        return item31;
    }

    public void setItem31(Date item31) {
        this.item31 = item31;
    }

    public Integer getItem4() {
        return item4;
    }

    public void setItem4(Integer item4) {
        this.item4 = item4;
    }

    public Date getItem41() {
        return item41;
    }

    public void setItem41(Date item41) {
        this.item41 = item41;
    }

    public Integer getItem5() {
        return item5;
    }

    public void setItem5(Integer item5) {
        this.item5 = item5;
    }

    public Date getItem51() {
        return item51;
    }

    public void setItem51(Date item51) {
        this.item51 = item51;
    }

    public Integer getItem6() {
        return item6;
    }

    public void setItem6(Integer item6) {
        this.item6 = item6;
    }

    public String getItem61() {
        return item61;
    }

    public void setItem61(String item61) {
        this.item61 = item61 == null ? null : item61.trim();
    }

    public String getItem62() {
        return item62;
    }

    public void setItem62(String item62) {
        this.item62 = item62 == null ? null : item62.trim();
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