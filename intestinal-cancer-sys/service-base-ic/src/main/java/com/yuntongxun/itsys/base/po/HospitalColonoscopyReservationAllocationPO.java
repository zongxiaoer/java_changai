package com.yuntongxun.itsys.base.po;

import java.io.Serializable;
import java.util.Date;

public class HospitalColonoscopyReservationAllocationPO implements Serializable {
    private Integer id;

    private String areaDeptId;

    private Integer amount;

    private String communityDeptId;

    private Date reservationDate;

    private String startTime;

    private String endTime;

    private String examinationPlace;

    private String signature;

    private Date dateCreated;

    private Date updateTime;

    private Integer ruleId;

    private Integer useStatus;

    private Integer issueType;

    private String communityDeptIdInfo;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAreaDeptId() {
        return areaDeptId;
    }

    public void setAreaDeptId(String areaDeptId) {
        this.areaDeptId = areaDeptId == null ? null : areaDeptId.trim();
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCommunityDeptId() {
        return communityDeptId;
    }

    public void setCommunityDeptId(String communityDeptId) {
        this.communityDeptId = communityDeptId == null ? null : communityDeptId.trim();
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public String getExaminationPlace() {
        return examinationPlace;
    }

    public void setExaminationPlace(String examinationPlace) {
        this.examinationPlace = examinationPlace == null ? null : examinationPlace.trim();
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
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

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public Integer getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(Integer useStatus) {
        this.useStatus = useStatus;
    }

    public Integer getIssueType() {
        return issueType;
    }

    public void setIssueType(Integer issueType) {
        this.issueType = issueType;
    }

    public String getCommunityDeptIdInfo() {
        return communityDeptIdInfo;
    }

    public void setCommunityDeptIdInfo(String communityDeptIdInfo) {
        this.communityDeptIdInfo = communityDeptIdInfo == null ? null : communityDeptIdInfo.trim();
    }
}