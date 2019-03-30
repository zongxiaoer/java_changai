package com.yuntongxun.itsys.base.po.cancer;

import java.io.Serializable;
import java.util.Date;

public class HospitalInformationDiagnoseEvaluationPO implements Serializable {
    private Integer id;

    private Integer colorectalCancerDiagnoseInformationId;

    private Date checkDate;

    private String diagnosticMethods;

    private String diagnosticMethodsOther;

    private Date dateCreated;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getColorectalCancerDiagnoseInformationId() {
        return colorectalCancerDiagnoseInformationId;
    }

    public void setColorectalCancerDiagnoseInformationId(Integer colorectalCancerDiagnoseInformationId) {
        this.colorectalCancerDiagnoseInformationId = colorectalCancerDiagnoseInformationId;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public String getDiagnosticMethods() {
        return diagnosticMethods;
    }

    public void setDiagnosticMethods(String diagnosticMethods) {
        this.diagnosticMethods = diagnosticMethods == null ? null : diagnosticMethods.trim();
    }

    public String getDiagnosticMethodsOther() {
        return diagnosticMethodsOther;
    }

    public void setDiagnosticMethodsOther(String diagnosticMethodsOther) {
        this.diagnosticMethodsOther = diagnosticMethodsOther == null ? null : diagnosticMethodsOther.trim();
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