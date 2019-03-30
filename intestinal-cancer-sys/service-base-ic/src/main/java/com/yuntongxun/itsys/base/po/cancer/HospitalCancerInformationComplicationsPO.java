package com.yuntongxun.itsys.base.po.cancer;

import java.io.Serializable;
import java.util.Date;

public class HospitalCancerInformationComplicationsPO implements Serializable {
    private Integer id;

    private Integer colorectalCancerDiagnoseInformationId;

    private Date complicationsDate;

    private String complicationsCode;

    private String complicationsCodeOther;

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

    public Date getComplicationsDate() {
        return complicationsDate;
    }

    public void setComplicationsDate(Date complicationsDate) {
        this.complicationsDate = complicationsDate;
    }

    public String getComplicationsCode() {
        return complicationsCode;
    }

    public void setComplicationsCode(String complicationsCode) {
        this.complicationsCode = complicationsCode == null ? null : complicationsCode.trim();
    }

    public String getComplicationsCodeOther() {
        return complicationsCodeOther;
    }

    public void setComplicationsCodeOther(String complicationsCodeOther) {
        this.complicationsCodeOther = complicationsCodeOther == null ? null : complicationsCodeOther.trim();
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