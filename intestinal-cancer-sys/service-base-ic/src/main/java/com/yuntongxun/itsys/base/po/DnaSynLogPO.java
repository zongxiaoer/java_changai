package com.yuntongxun.itsys.base.po;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: DnaSynLogPO
 * Author:   zongtong
 * Date:     2018/10/11 下午2:54
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/10/11 下午2:54           v1.0.0
 */

public class DnaSynLogPO {
    private String id;
    private Integer dnaId;;
    private String paramValue;
    private Integer result;
    private String resultCode;
    private String resultCont;
    private String dnaCode;
    private String dataNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getDnaId() {
        return dnaId;
    }

    public void setDnaId(Integer dnaId) {
        this.dnaId = dnaId;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultCont() {
        return resultCont;
    }

    public void setResultCont(String resultCont) {
        this.resultCont = resultCont;
    }

    public String getDnaCode() {
        return dnaCode;
    }

    public void setDnaCode(String dnaCode) {
        this.dnaCode = dnaCode;
    }

    public String getDataNumber() {
        return dataNumber;
    }

    public void setDataNumber(String dataNumber) {
        this.dataNumber = dataNumber;
    }
}
