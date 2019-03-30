package com.yuntongxun.itsys.base.po.dto.cancervo;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: HospitalCancerInformationComplicationsVo
 * Author:   zongtong
 * Date:     2018/9/12 下午5:53
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/9/12 下午5:53           v1.0.0
 */

import com.yuntongxun.itsys.base.po.cancer.HospitalCancerInformationComplicationsPO;

public class HospitalCancerInformationComplicationsVo extends HospitalCancerInformationComplicationsPO {
    private String complicationsDateToString;

    public String getComplicationsDateToString() {
        return complicationsDateToString;
    }

    public void setComplicationsDateToString(String complicationsDateToString) {
        this.complicationsDateToString = complicationsDateToString;
    }
}

