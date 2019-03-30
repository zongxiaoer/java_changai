package com.yuntongxun.itsys.base.po.cancer;

import java.io.Serializable;
import java.util.Date;

public class HospitalCancerDiagnosePO implements Serializable {
    private Integer id;

    private String sid;

    private Date writeTableDate;

    private Date ysCancerReportDate;

    private String checkYears;

    private String investigatorCode;

    private String tbTablePerson;

    private String qualityControlPerson;

    private String item1;

    private Integer item21;

    private Integer item22;

    private Integer item3;

    private Integer item3a;

    private Date yfCancerDiagnosisDate;

    private String item51;

    private String item52;

    private String item53;

    private String item54;

    private String item55;

    private String informationZlPerson;

    private Date dateCreated;

    private Date updateTime;

    private Integer deptCode;

    private Integer createUser;

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

    public Date getWriteTableDate() {
        return writeTableDate;
    }

    public void setWriteTableDate(Date writeTableDate) {
        this.writeTableDate = writeTableDate;
    }

    public Date getYsCancerReportDate() {
        return ysCancerReportDate;
    }

    public void setYsCancerReportDate(Date ysCancerReportDate) {
        this.ysCancerReportDate = ysCancerReportDate;
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

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1 == null ? null : item1.trim();
    }

    public Integer getItem21() {
        return item21;
    }

    public void setItem21(Integer item21) {
        this.item21 = item21;
    }

    public Integer getItem22() {
        return item22;
    }

    public void setItem22(Integer item22) {
        this.item22 = item22;
    }

    public Integer getItem3() {
        return item3;
    }

    public void setItem3(Integer item3) {
        this.item3 = item3;
    }

    public Integer getItem3a() {
        return item3a;
    }

    public void setItem3a(Integer item3a) {
        this.item3a = item3a;
    }

    public Date getYfCancerDiagnosisDate() {
        return yfCancerDiagnosisDate;
    }

    public void setYfCancerDiagnosisDate(Date yfCancerDiagnosisDate) {
        this.yfCancerDiagnosisDate = yfCancerDiagnosisDate;
    }

    public String getItem51() {
        return item51;
    }

    public void setItem51(String item51) {
        this.item51 = item51 == null ? null : item51.trim();
    }

    public String getItem52() {
        return item52;
    }

    public void setItem52(String item52) {
        this.item52 = item52 == null ? null : item52.trim();
    }

    public String getItem53() {
        return item53;
    }

    public void setItem53(String item53) {
        this.item53 = item53 == null ? null : item53.trim();
    }

    public String getItem54() {
        return item54;
    }

    public void setItem54(String item54) {
        this.item54 = item54 == null ? null : item54.trim();
    }

    public String getItem55() {
        return item55;
    }

    public void setItem55(String item55) {
        this.item55 = item55 == null ? null : item55.trim();
    }

    public String getInformationZlPerson() {
        return informationZlPerson;
    }

    public void setInformationZlPerson(String informationZlPerson) {
        this.informationZlPerson = informationZlPerson == null ? null : informationZlPerson.trim();
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
}