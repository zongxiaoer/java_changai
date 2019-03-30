package com.yuntongxun.itsys.base.po.dto.cancervo;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: HospitalCancerReportMessageVo
 * Author:   zongtong
 * Date:     2018/9/7 下午2:42
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/9/7 下午2:42           v1.0.0
 */


import com.yuntongxun.itsys.base.po.cancer.HospitalCancerReportMessagePO;

public class HospitalCancerReportMessageVo extends HospitalCancerReportMessagePO {
    private String reportDateToString;

    public String getReportDateToString() {
        return reportDateToString;
    }

    public void setReportDateToString(String reportDateToString) {
        this.reportDateToString = reportDateToString;
    }
}
