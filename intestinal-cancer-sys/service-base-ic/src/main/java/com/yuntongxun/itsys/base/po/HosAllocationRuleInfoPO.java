package com.yuntongxun.itsys.base.po;

import java.io.Serializable;
import java.util.Date;

public class HosAllocationRuleInfoPO implements Serializable {
    private Integer id;

    private Integer areaDeptId;

    private Integer communityDeptId;

    private Date ruleBegin;

    private Date ruleEnd;

    private Integer ruleType;

    private String weekInfo;

    private Integer beginStatus;

    private Date aTime;

    private Date cTime;

    private Integer aUser;

    private Integer cUser;

    private Integer useStatus;

    private Integer flag;

    private Integer issueType;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAreaDeptId() {
        return areaDeptId;
    }

    public void setAreaDeptId(Integer areaDeptId) {
        this.areaDeptId = areaDeptId;
    }

    public Integer getCommunityDeptId() {
        return communityDeptId;
    }

    public void setCommunityDeptId(Integer communityDeptId) {
        this.communityDeptId = communityDeptId;
    }

    public Date getRuleBegin() {
        return ruleBegin;
    }

    public void setRuleBegin(Date ruleBegin) {
        this.ruleBegin = ruleBegin;
    }

    public Date getRuleEnd() {
        return ruleEnd;
    }

    public void setRuleEnd(Date ruleEnd) {
        this.ruleEnd = ruleEnd;
    }

    public Integer getRuleType() {
        return ruleType;
    }

    public void setRuleType(Integer ruleType) {
        this.ruleType = ruleType;
    }

    public String getWeekInfo() {
        return weekInfo;
    }

    public void setWeekInfo(String weekInfo) {
        this.weekInfo = weekInfo == null ? null : weekInfo.trim();
    }

    public Integer getBeginStatus() {
        return beginStatus;
    }

    public void setBeginStatus(Integer beginStatus) {
        this.beginStatus = beginStatus;
    }

    public Date getaTime() {
        return aTime;
    }

    public void setaTime(Date aTime) {
        this.aTime = aTime;
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public Integer getaUser() {
        return aUser;
    }

    public void setaUser(Integer aUser) {
        this.aUser = aUser;
    }

    public Integer getcUser() {
        return cUser;
    }

    public void setcUser(Integer cUser) {
        this.cUser = cUser;
    }

    public Integer getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(Integer useStatus) {
        this.useStatus = useStatus;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getIssueType() {
        return issueType;
    }

    public void setIssueType(Integer issueType) {
        this.issueType = issueType;
    }
}