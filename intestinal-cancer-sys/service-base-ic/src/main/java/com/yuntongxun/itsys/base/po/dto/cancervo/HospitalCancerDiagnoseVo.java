package com.yuntongxun.itsys.base.po.dto.cancervo;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: HospitalCancerDiagnoseVo
 * Author:   zongtong
 * Date:     2018/9/10 上午11:16
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/9/10 上午11:16           v1.0.0
 */

import com.yuntongxun.itsys.base.po.cancer.HospitalCancerDiagnosePO;

import java.util.Date;

public class HospitalCancerDiagnoseVo extends HospitalCancerDiagnosePO {
    private Integer cancerRecordId;


    private String  writeTableDateToString;

    private String  ysCancerReportDateToString;

    private String  yfCancerDiagnosisDateToString;

    private Integer stage;

    public Integer getCancerRecordId() {
        return cancerRecordId;
    }

    public void setCancerRecordId(Integer cancerRecordId) {
        this.cancerRecordId = cancerRecordId;
    }

    public String getWriteTableDateToString() {
        return writeTableDateToString;
    }

    public void setWriteTableDateToString(String writeTableDateToString) {
        this.writeTableDateToString = writeTableDateToString;
    }

    public String getYsCancerReportDateToString() {
        return ysCancerReportDateToString;
    }

    public void setYsCancerReportDateToString(String ysCancerReportDateToString) {
        this.ysCancerReportDateToString = ysCancerReportDateToString;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public String getYfCancerDiagnosisDateToString() {
        return yfCancerDiagnosisDateToString;
    }

    public void setYfCancerDiagnosisDateToString(String yfCancerDiagnosisDateToString) {
        this.yfCancerDiagnosisDateToString = yfCancerDiagnosisDateToString;
    }
}
