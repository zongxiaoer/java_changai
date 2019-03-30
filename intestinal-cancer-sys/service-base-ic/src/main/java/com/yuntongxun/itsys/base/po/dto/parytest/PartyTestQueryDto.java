package com.yuntongxun.itsys.base.po.dto.parytest;

import com.yuntongxun.itsys.base.common.Page;

/**
 * @author wp_sp
 * @date 2018/5/10
 */
public class PartyTestQueryDto extends Page {
    private String dnaCode;

    private Integer dnaCheckResult;

    private Integer dnaCheckEnterStatus;

    public String getDnaCode() {
        return dnaCode;
    }

    public void setDnaCode(String dnaCode) {
        this.dnaCode = dnaCode;
    }

    public Integer getDnaCheckResult() {
        return dnaCheckResult;
    }

    public void setDnaCheckResult(Integer dnaCheckResult) {
        this.dnaCheckResult = dnaCheckResult;
    }

    public Integer getDnaCheckEnterStatus() {
        return dnaCheckEnterStatus;
    }

    public void setDnaCheckEnterStatus(Integer dnaCheckEnterStatus) {
        this.dnaCheckEnterStatus = dnaCheckEnterStatus;
    }
}