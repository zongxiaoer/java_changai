package com.yuntongxun.itsys.base.po;

import java.io.Serializable;
import java.util.Date;

public class HospitalStoolDnaMechanismPO implements Serializable {
    private Integer id;

    private String sid;

    private String dnaCode;

    private Integer fitDnaScore;

    private Integer fitDnaResults;

    private Integer fitDnaQuantitative;

    private String fitDnaPdfPath;

    private Integer step;

    private Date dateCreated;

    private Date updateTime;

    private Integer status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    public String getDnaCode() {
        return dnaCode;
    }

    public void setDnaCode(String dnaCode) {
        this.dnaCode = dnaCode == null ? null : dnaCode.trim();
    }

    public Integer getFitDnaScore() {
        return fitDnaScore;
    }

    public void setFitDnaScore(Integer fitDnaScore) {
        this.fitDnaScore = fitDnaScore;
    }

    public Integer getFitDnaResults() {
        return fitDnaResults;
    }

    public void setFitDnaResults(Integer fitDnaResults) {
        this.fitDnaResults = fitDnaResults;
    }

    public Integer getFitDnaQuantitative() {
        return fitDnaQuantitative;
    }

    public void setFitDnaQuantitative(Integer fitDnaQuantitative) {
        this.fitDnaQuantitative = fitDnaQuantitative;
    }

    public String getFitDnaPdfPath() {
        return fitDnaPdfPath;
    }

    public void setFitDnaPdfPath(String fitDnaPdfPath) {
        this.fitDnaPdfPath = fitDnaPdfPath == null ? null : fitDnaPdfPath.trim();
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}