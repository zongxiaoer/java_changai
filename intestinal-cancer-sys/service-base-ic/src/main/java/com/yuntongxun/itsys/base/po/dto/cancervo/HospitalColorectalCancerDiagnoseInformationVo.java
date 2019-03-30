package com.yuntongxun.itsys.base.po.dto.cancervo;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: HospitalColorectalCancerDiagnoseInformationVo
 * Author:   zongtong
 * Date:     2018/9/10 上午11:53
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/9/10 上午11:53           v1.0.0
 */

import com.yuntongxun.itsys.base.po.FileUploadLogPO;
import com.yuntongxun.itsys.base.po.cancer.HospitalColorectalCancerDiagnoseInformationPO;

import java.util.List;

public class HospitalColorectalCancerDiagnoseInformationVo extends HospitalColorectalCancerDiagnoseInformationPO {
    private Integer cancerRecordId;

    private String excerptsDateToString;

    private String item3CheckDateToString;
    private String item8b1ToString;
    private String item8d11ToString;
    private String item9ToString;

    private Integer item157;

    private List<FileUploadLogPO> reportUrls;

    private List<HospitalInformationDiagnoseEvaluationVo> hospitalInformationDiagnoseEvaluationVos;


    private List<HospitalCancerInformationComplicationsVo> hospitalCancerInformationComplicationsVos;

    public Integer getCancerRecordId() {
        return cancerRecordId;
    }

    public void setCancerRecordId(Integer cancerRecordId) {
        this.cancerRecordId = cancerRecordId;
    }

    public String getExcerptsDateToString() {
        return excerptsDateToString;
    }

    public void setExcerptsDateToString(String excerptsDateToString) {
        this.excerptsDateToString = excerptsDateToString;
    }

    public String getItem3CheckDateToString() {
        return item3CheckDateToString;
    }

    public void setItem3CheckDateToString(String item3CheckDateToString) {
        this.item3CheckDateToString = item3CheckDateToString;
    }

    public String getItem8b1ToString() {
        return item8b1ToString;
    }

    public void setItem8b1ToString(String item8b1ToString) {
        this.item8b1ToString = item8b1ToString;
    }

    public String getItem8d11ToString() {
        return item8d11ToString;
    }

    public void setItem8d11ToString(String item8d11ToString) {
        this.item8d11ToString = item8d11ToString;
    }

    public String getItem9ToString() {
        return item9ToString;
    }

    public void setItem9ToString(String item9ToString) {
        this.item9ToString = item9ToString;
    }

    public List<HospitalInformationDiagnoseEvaluationVo> getHospitalInformationDiagnoseEvaluationVos() {
        return hospitalInformationDiagnoseEvaluationVos;
    }

    public void setHospitalInformationDiagnoseEvaluationVos(List<HospitalInformationDiagnoseEvaluationVo> hospitalInformationDiagnoseEvaluationVos) {
        this.hospitalInformationDiagnoseEvaluationVos = hospitalInformationDiagnoseEvaluationVos;
    }

    public Integer getItem157() {
        return item157;
    }

    public void setItem157(Integer item157) {
        this.item157 = item157;
    }

    public List<HospitalCancerInformationComplicationsVo> getHospitalCancerInformationComplicationsVos() {
        return hospitalCancerInformationComplicationsVos;
    }

    public void setHospitalCancerInformationComplicationsVos(List<HospitalCancerInformationComplicationsVo> hospitalCancerInformationComplicationsVos) {
        this.hospitalCancerInformationComplicationsVos = hospitalCancerInformationComplicationsVos;
    }

    public List<FileUploadLogPO> getReportUrls() {
        return reportUrls;
    }

    public void setReportUrls(List<FileUploadLogPO> reportUrls) {
        this.reportUrls = reportUrls;
    }
}
