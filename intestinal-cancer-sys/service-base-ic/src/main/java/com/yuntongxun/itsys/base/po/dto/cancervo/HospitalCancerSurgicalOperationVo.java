package com.yuntongxun.itsys.base.po.dto.cancervo;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: HospitalCancerSurgicalOperationVo
 * Author:   zongtong
 * Date:     2018/9/11 上午10:23
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/9/11 上午10:23           v1.0.0
 */

import com.yuntongxun.itsys.base.po.cancer.HospitalCancerSurgicalOperationPO;

public class HospitalCancerSurgicalOperationVo  extends HospitalCancerSurgicalOperationPO {
    private String  finshDateToString;

    private String surgicalOperationCodeOther;

    public String getFinshDateToString() {
        return finshDateToString;
    }

    public void setFinshDateToString(String finshDateToString) {
        this.finshDateToString = finshDateToString;
    }

    public String getSurgicalOperationCodeOther() {
        return surgicalOperationCodeOther;
    }

    public void setSurgicalOperationCodeOther(String surgicalOperationCodeOther) {
        this.surgicalOperationCodeOther = surgicalOperationCodeOther;
    }
}
