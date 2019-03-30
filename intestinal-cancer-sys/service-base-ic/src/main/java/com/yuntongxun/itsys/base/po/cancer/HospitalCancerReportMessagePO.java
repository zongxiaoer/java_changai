package com.yuntongxun.itsys.base.po.cancer;

import java.io.Serializable;
import java.util.Date;

public class HospitalCancerReportMessagePO implements Serializable {
    private Integer id;

    private Integer cancerReportId;

    private String cancerTypeSite;

    private Date reportDate;

    private String diagnoseSource;

    private String diagnoseSourceOther;

    private Date dateCreated;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCancerReportId() {
        return cancerReportId;
    }

    public void setCancerReportId(Integer cancerReportId) {
        this.cancerReportId = cancerReportId;
    }

    public String getCancerTypeSite() {
        return cancerTypeSite;
    }

    public void setCancerTypeSite(String cancerTypeSite) {
        this.cancerTypeSite = cancerTypeSite == null ? null : cancerTypeSite.trim();
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getDiagnoseSource() {
        return diagnoseSource;
    }

    public void setDiagnoseSource(String diagnoseSource) {
        this.diagnoseSource = diagnoseSource == null ? null : diagnoseSource.trim();
    }

    public String getDiagnoseSourceOther() {
        return diagnoseSourceOther;
    }

    public void setDiagnoseSourceOther(String diagnoseSourceOther) {
        this.diagnoseSourceOther = diagnoseSourceOther == null ? null : diagnoseSourceOther.trim();
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
}