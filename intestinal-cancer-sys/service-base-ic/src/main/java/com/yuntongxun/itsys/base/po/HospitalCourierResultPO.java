package com.yuntongxun.itsys.base.po;

import java.io.Serializable;
import java.util.Date;

public class HospitalCourierResultPO implements Serializable {
    private Integer id;

    private String courierNumber;

    private Date sendDate;

    private String sendPerson;

    private Date acceptDate;

    private String acceptPerson;

    private String courierCompany;

    private String screeningtype;

    private String sendPersonPhone;

    private Integer communityDeptId;

    private Integer areaDeptId;

    private Date dateCreated;

    private Date updateTime;

    private Integer courierStatus;

    private String applyStatus;

    private String editStatus;

    private String approvalStatus;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourierNumber() {
        return courierNumber;
    }

    public void setCourierNumber(String courierNumber) {
        this.courierNumber = courierNumber == null ? null : courierNumber.trim();
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getSendPerson() {
        return sendPerson;
    }

    public void setSendPerson(String sendPerson) {
        this.sendPerson = sendPerson == null ? null : sendPerson.trim();
    }

    public Date getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
    }

    public String getAcceptPerson() {
        return acceptPerson;
    }

    public void setAcceptPerson(String acceptPerson) {
        this.acceptPerson = acceptPerson == null ? null : acceptPerson.trim();
    }

    public String getCourierCompany() {
        return courierCompany;
    }

    public void setCourierCompany(String courierCompany) {
        this.courierCompany = courierCompany == null ? null : courierCompany.trim();
    }

    public String getScreeningtype() {
        return screeningtype;
    }

    public void setScreeningtype(String screeningtype) {
        this.screeningtype = screeningtype == null ? null : screeningtype.trim();
    }

    public String getSendPersonPhone() {
        return sendPersonPhone;
    }

    public void setSendPersonPhone(String sendPersonPhone) {
        this.sendPersonPhone = sendPersonPhone == null ? null : sendPersonPhone.trim();
    }

    public Integer getCommunityDeptId() {
        return communityDeptId;
    }

    public void setCommunityDeptId(Integer communityDeptId) {
        this.communityDeptId = communityDeptId;
    }

    public Integer getAreaDeptId() {
        return areaDeptId;
    }

    public void setAreaDeptId(Integer areaDeptId) {
        this.areaDeptId = areaDeptId;
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

    public Integer getCourierStatus() {
        return courierStatus;
    }

    public void setCourierStatus(Integer courierStatus) {
        this.courierStatus = courierStatus;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus == null ? null : applyStatus.trim();
    }

    public String getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(String editStatus) {
        this.editStatus = editStatus == null ? null : editStatus.trim();
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus == null ? null : approvalStatus.trim();
    }
}