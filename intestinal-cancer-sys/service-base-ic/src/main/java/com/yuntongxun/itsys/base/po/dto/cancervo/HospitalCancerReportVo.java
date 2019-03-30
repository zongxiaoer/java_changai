package com.yuntongxun.itsys.base.po.dto.cancervo;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: HospitalCancerReportVo
 * Author:   zongtong
 * Date:     2018/9/6 下午4:49
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/9/6 下午4:49           v1.0.0
 */

import com.yuntongxun.itsys.base.po.cancer.HospitalCancerReportMessagePO;
import com.yuntongxun.itsys.base.po.cancer.HospitalCancerReportPO;

import java.util.List;

public class HospitalCancerReportVo extends HospitalCancerReportPO {
    private Integer cancerRecordId;

    private String tbTableDateToString;


    private List<HospitalCancerReportMessageVo> hospitalCancerReportMessagePOS;

    public Integer getCancerRecordId() {
        return cancerRecordId;
    }

    public void setCancerRecordId(Integer cancerRecordId) {
        this.cancerRecordId = cancerRecordId;
    }

    public List<HospitalCancerReportMessageVo> getHospitalCancerReportMessagePOS() {
        return hospitalCancerReportMessagePOS;
    }

    public void setHospitalCancerReportMessagePOS(List<HospitalCancerReportMessageVo> hospitalCancerReportMessagePOS) {
        this.hospitalCancerReportMessagePOS = hospitalCancerReportMessagePOS;
    }

    public String getTbTableDateToString() {
        return tbTableDateToString;
    }

    public void setTbTableDateToString(String tbTableDateToString) {
        this.tbTableDateToString = tbTableDateToString == null ? null : tbTableDateToString.trim();

    }
}
