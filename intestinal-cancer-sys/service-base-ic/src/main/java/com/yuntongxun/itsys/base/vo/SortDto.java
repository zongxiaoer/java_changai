package com.yuntongxun.itsys.base.vo;

/**
 * @author zongt
 * @date 2018/6/5
 */
public class SortDto {
    private String sortParameter;//排序参数
    private String sortRule;//排序规则

    public String getSortParameter() {
        return sortParameter;
    }

    public void setSortParameter(String sortParameter) {
        this.sortParameter = sortParameter;
    }

    public String getSortRule() {
        return sortRule;
    }

    public void setSortRule(String sortRule) {
        this.sortRule = sortRule;
    }
}