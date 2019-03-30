package com.yuntongxun.itsys.base.po.dto.cancervo;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: HospitalColorectalCancerTreatmentInformationVo
 * Author:   zongtong
 * Date:     2018/9/10 下午3:49
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/9/10 下午3:49           v1.0.0
 */

import com.yuntongxun.itsys.base.po.cancer.HospitalColorectalCancerTreatmentInformationPO;

import java.util.Date;
import java.util.List;

public class HospitalColorectalCancerTreatmentInformationVo extends HospitalColorectalCancerTreatmentInformationPO {
    private Integer cancerRecordId;

    private String tbTableDateToString;


    private Integer excerptPurpose;

    private String  item31ToString;

    private String  item41ToString;

    private String  item51ToString;

    private List<HospitalCancerSurgicalOperationVo> hospitalCancerSurgicalOperationVos;

    private List<HospitalCancerTreatmentInformationVo> hospitalCancerTreatmentInformationVos;

    public Integer getCancerRecordId() {
        return cancerRecordId;
    }

    public void setCancerRecordId(Integer cancerRecordId) {
        this.cancerRecordId = cancerRecordId;
    }

    public String getTbTableDateToString() {
        return tbTableDateToString;
    }

    public void setTbTableDateToString(String tbTableDateToString) {
        this.tbTableDateToString = tbTableDateToString;
    }

    public Integer getExcerptPurpose() {
        return excerptPurpose;
    }

    public void setExcerptPurpose(Integer excerptPurpose) {
        this.excerptPurpose = excerptPurpose;
    }

    public String getItem31ToString() {
        return item31ToString;
    }

    public void setItem31ToString(String item31ToString) {
        this.item31ToString = item31ToString;
    }

    public String getItem41ToString() {
        return item41ToString;
    }

    public void setItem41ToString(String item41ToString) {
        this.item41ToString = item41ToString;
    }

    public String getItem51ToString() {
        return item51ToString;
    }

    public void setItem51ToString(String item51ToString) {
        this.item51ToString = item51ToString;
    }

    public List<HospitalCancerSurgicalOperationVo> getHospitalCancerSurgicalOperationVos() {
        return hospitalCancerSurgicalOperationVos;
    }

    public void setHospitalCancerSurgicalOperationVos(List<HospitalCancerSurgicalOperationVo> hospitalCancerSurgicalOperationVos) {
        this.hospitalCancerSurgicalOperationVos = hospitalCancerSurgicalOperationVos;
    }

    public List<HospitalCancerTreatmentInformationVo> getHospitalCancerTreatmentInformationVos() {
        return hospitalCancerTreatmentInformationVos;
    }

    public void setHospitalCancerTreatmentInformationVos(List<HospitalCancerTreatmentInformationVo> hospitalCancerTreatmentInformationVos) {
        this.hospitalCancerTreatmentInformationVos = hospitalCancerTreatmentInformationVos;
    }
}
