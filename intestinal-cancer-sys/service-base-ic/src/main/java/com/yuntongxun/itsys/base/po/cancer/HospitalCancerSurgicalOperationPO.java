package com.yuntongxun.itsys.base.po.cancer;

import java.io.Serializable;
import java.util.Date;

public class HospitalCancerSurgicalOperationPO implements Serializable {
    private Integer id;

    private Integer colorectalCancerTreatmentInformationId;

    private String surgicalOperationCode;

    private Date finshDate;

    private Date dateCreated;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getColorectalCancerTreatmentInformationId() {
        return colorectalCancerTreatmentInformationId;
    }

    public void setColorectalCancerTreatmentInformationId(Integer colorectalCancerTreatmentInformationId) {
        this.colorectalCancerTreatmentInformationId = colorectalCancerTreatmentInformationId;
    }

    public String getSurgicalOperationCode() {
        return surgicalOperationCode;
    }

    public void setSurgicalOperationCode(String surgicalOperationCode) {
        this.surgicalOperationCode = surgicalOperationCode == null ? null : surgicalOperationCode.trim();
    }

    public Date getFinshDate() {
        return finshDate;
    }

    public void setFinshDate(Date finshDate) {
        this.finshDate = finshDate;
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