package com.yuntongxun.itsys.base.po;

import java.io.Serializable;
import java.util.Date;

/**
 * fit第三方同步实体类
 */
public class FitSynLogPO implements Serializable {
    private Integer id;
    private Integer fitId;
    private String paramValue;
    private Integer result;
    private String result_code;
    private String result_cont;
    private Date dateCreated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFitId() {
        return fitId;
    }

    public void setFitId(Integer fitId) {
        this.fitId = fitId;
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

    public String getResult_cont() {
        return result_cont;
    }

    public void setResult_cont(String result_cont) {
        this.result_cont = result_cont;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }
}