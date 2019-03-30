package com.yuntongxun.itsys.base.po.dto.cancervo;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: HospitalInformationDiagnoseEvaluationVo
 * Author:   zongtong
 * Date:     2018/9/12 上午10:32
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/9/12 上午10:32           v1.0.0
 */

import com.yuntongxun.itsys.base.po.cancer.HospitalInformationDiagnoseEvaluationPO;

public class HospitalInformationDiagnoseEvaluationVo  extends HospitalInformationDiagnoseEvaluationPO {
    private String  checkDateToString;

    public String getCheckDateToString() {
        return checkDateToString;
    }

    public void setCheckDateToString(String checkDateToString) {
        this.checkDateToString = checkDateToString;
    }
}
