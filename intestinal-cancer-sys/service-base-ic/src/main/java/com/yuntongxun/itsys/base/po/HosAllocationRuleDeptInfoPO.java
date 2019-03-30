package com.yuntongxun.itsys.base.po;

import java.io.Serializable;

public class HosAllocationRuleDeptInfoPO implements Serializable {
    private Integer id;

    private Integer communityDeptId;

    private Integer ruleId;

    private String beginTime;

    private String endTime;

    private Integer num;

    private String communityDeptIdInfo;

    private Integer issueType;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommunityDeptId() {
        return communityDeptId;
    }

    public void setCommunityDeptId(Integer communityDeptId) {
        this.communityDeptId = communityDeptId;
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime == null ? null : beginTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getCommunityDeptIdInfo() {
        return communityDeptIdInfo;
    }

    public void setCommunityDeptIdInfo(String communityDeptIdInfo) {
        this.communityDeptIdInfo = communityDeptIdInfo == null ? null : communityDeptIdInfo.trim();
    }

    public Integer getIssueType() {
        return issueType;
    }

    public void setIssueType(Integer issueType) {
        this.issueType = issueType;
    }
}