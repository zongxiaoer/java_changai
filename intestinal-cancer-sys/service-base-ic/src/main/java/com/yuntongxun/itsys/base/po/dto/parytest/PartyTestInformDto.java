package com.yuntongxun.itsys.base.po.dto.parytest;

import com.yuntongxun.itsys.base.po.HospitalStoolDnaPO;

/**
 * @author wp_sp
 * @date 2018/5/11
 */
public class PartyTestInformDto extends HospitalStoolDnaPO {
    private String dataNumber;
    private String timestamp;
    private String sign;
    private String dnaCheckGoalToString;
    //private HospitalStoolDnaPO data;

    public String getDataNumber() {
        return dataNumber;
    }

    public void setDataNumber(String dataNumber) {
        this.dataNumber = dataNumber;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getDnaCheckGoalToString() {
        return dnaCheckGoalToString;
    }

    public void setDnaCheckGoalToString(String dnaCheckGoalToString) {
        this.dnaCheckGoalToString = dnaCheckGoalToString;
    }
}
