package com.yuntongxun.itsys.base.vo;

/**
 * Description: 新增危险因素返回
 *
 * @author LuoKun on 2018-04-22.
 */
public class AddRiskResult {

    private String group;

    private Integer riskLevel;

    public AddRiskResult(String group, Integer riskLevel) {
        this.group = group;
        this.riskLevel = riskLevel;
    }

    public AddRiskResult() {
        super();
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(Integer riskLevel) {
        this.riskLevel = riskLevel;
    }
}
