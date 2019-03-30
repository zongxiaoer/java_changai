package com.yuntongxun.itsys.base.po;

import java.io.Serializable;

public class ItsysSidRulePO implements Serializable {
    private Integer id;

    private Integer departmentId;

    private Integer maxPerson;

    private String communityScope;

    private String ruleType;

    private Integer communityScopeStatus;

    private Integer groupRuleId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getMaxPerson() {
        return maxPerson;
    }

    public void setMaxPerson(Integer maxPerson) {
        this.maxPerson = maxPerson;
    }

    public String getCommunityScope() {
        return communityScope;
    }

    public void setCommunityScope(String communityScope) {
        this.communityScope = communityScope == null ? null : communityScope.trim();
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType == null ? null : ruleType.trim();
    }

    public Integer getCommunityScopeStatus() {
        return communityScopeStatus;
    }

    public void setCommunityScopeStatus(Integer communityScopeStatus) {
        this.communityScopeStatus = communityScopeStatus;
    }

    public Integer getGroupRuleId() {
        return groupRuleId;
    }

    public void setGroupRuleId(Integer groupRuleId) {
        this.groupRuleId = groupRuleId;
    }
}