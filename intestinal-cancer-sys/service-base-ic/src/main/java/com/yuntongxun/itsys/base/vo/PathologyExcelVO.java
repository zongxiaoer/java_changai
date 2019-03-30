/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: PathologyExcelVO
 * Author:   lcy
 * Date:     2018/8/29 10:02
 * History:
 * <author>          <time>                <version>
 *     lcy         2018/8/29 10:02           v1.0.0
 */
package com.yuntongxun.itsys.base.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 导出病理和病理诊断VO
 *
 * @author lcy
 * @create 2018/8/29
 * @since v1.0.0
 */
public class PathologyExcelVO implements Serializable{

    private String sid;
    private String name;
    private String idCard;
    private Integer age;
    private String sex;
    private String surveyDate;
    private Integer item1;// int 是否需要国家癌症中心会诊（复阅）？1：是，2：否
    private Integer item2;// int 是否诊断为结直肠癌前病变：1：是，2：否
    private Integer item3;// int 是否诊断为结直肠癌：1：是，2：否
    private String diagnosisDate;//  诊断日期
    private String doctorSign;// varchar(32) 医师签名
    private Integer stage;// int 阶段，1：T0，2：T1，3：T2
    private Integer overallStatusCy;

    private String item1_0;
    private Integer item2_0;
    private Double item3_0;
    private String item4_0;
    private Double item5_0;
    private Double item6_1_0;
    private Double item6_2_0;
    private String item7_0;

    private String item1_1;
    private Integer item2_1;
    private Double item3_1;
    private String item4_1;
    private Double item5_1;
    private Double item6_1_1;
    private Double item6_2_1;
    private String item7_1;

    private String item1_2;
    private Integer item2_2;
    private Double item3_2;
    private String item4_2;
    private Double item5_2;
    private Double item6_1_2;
    private Double item6_2_2;
    private String item7_2;

    private String item1_3;
    private Integer item2_3;
    private Double item3_3;
    private String item4_3;
    private Double item5_3;
    private Double item6_1_3;
    private Double item6_2_3;
    private String item7_3;

    private String item1_4;
    private Integer item2_4;
    private Double item3_4;
    private String item4_4;
    private Double item5_4;
    private Double item6_1_4;
    private Double item6_2_4;
    private String item7_4;

    private String item1_5;
    private Integer item2_5;
    private Double item3_5;
    private String item4_5;
    private Double item5_5;
    private Double item6_1_5;
    private Double item6_2_5;
    private String item7_5;

    private String item1_6;
    private Integer item2_6;
    private Double item3_6;
    private String item4_6;
    private Double item5_6;
    private Double item6_1_6;
    private Double item6_2_6;
    private String item7_6;

    private String item1_7;
    private Integer item2_7;
    private Double item3_7;
    private String item4_7;
    private Double item5_7;
    private Double item6_1_7;
    private Double item6_2_7;
    private String item7_7;

    private String item1_8;
    private Integer item2_8;
    private Double item3_8;
    private String item4_8;
    private Double item5_8;
    private Double item6_1_8;
    private Double item6_2_8;
    private String item7_8;

    private String item1_9;
    private Integer item2_9;
    private Double item3_9;
    private String item4_9;
    private Double item5_9;
    private Double item6_1_9;
    private Double item6_2_9;
    private String item7_9;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(String surveyDate) {
        this.surveyDate = surveyDate;
    }

    public Integer getItem1() {
        return item1;
    }

    public void setItem1(Integer item1) {
        this.item1 = item1;
    }

    public Integer getItem2() {
        return item2;
    }

    public void setItem2(Integer item2) {
        this.item2 = item2;
    }

    public Integer getItem3() {
        return item3;
    }

    public void setItem3(Integer item3) {
        this.item3 = item3;
    }

    public String getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(String diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public String getDoctorSign() {
        return doctorSign;
    }

    public void setDoctorSign(String doctorSign) {
        this.doctorSign = doctorSign;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public String getItem1_0() {
        return item1_0;
    }

    public void setItem1_0(String item1_0) {
        this.item1_0 = item1_0;
    }

    public Integer getItem2_0() {
        return item2_0;
    }

    public void setItem2_0(Integer item2_0) {
        this.item2_0 = item2_0;
    }

    public Double getItem3_0() {
        return item3_0;
    }

    public void setItem3_0(Double item3_0) {
        this.item3_0 = item3_0;
    }

    public String getItem4_0() {
        return item4_0;
    }

    public void setItem4_0(String item4_0) {
        this.item4_0 = item4_0;
    }

    public Double getItem5_0() {
        return item5_0;
    }

    public void setItem5_0(Double item5_0) {
        this.item5_0 = item5_0;
    }

    public Double getItem6_1_0() {
        return item6_1_0;
    }

    public void setItem6_1_0(Double item6_1_0) {
        this.item6_1_0 = item6_1_0;
    }

    public Double getItem6_2_0() {
        return item6_2_0;
    }

    public void setItem6_2_0(Double item6_2_0) {
        this.item6_2_0 = item6_2_0;
    }

    public String getItem7_0() {
        return item7_0;
    }

    public void setItem7_0(String item7_0) {
        this.item7_0 = item7_0;
    }

    public String getItem1_1() {
        return item1_1;
    }

    public void setItem1_1(String item1_1) {
        this.item1_1 = item1_1;
    }

    public Integer getItem2_1() {
        return item2_1;
    }

    public void setItem2_1(Integer item2_1) {
        this.item2_1 = item2_1;
    }

    public Double getItem3_1() {
        return item3_1;
    }

    public void setItem3_1(Double item3_1) {
        this.item3_1 = item3_1;
    }

    public String getItem4_1() {
        return item4_1;
    }

    public void setItem4_1(String item4_1) {
        this.item4_1 = item4_1;
    }

    public Double getItem5_1() {
        return item5_1;
    }

    public void setItem5_1(Double item5_1) {
        this.item5_1 = item5_1;
    }

    public Double getItem6_1_1() {
        return item6_1_1;
    }

    public void setItem6_1_1(Double item6_1_1) {
        this.item6_1_1 = item6_1_1;
    }

    public Double getItem6_2_1() {
        return item6_2_1;
    }

    public void setItem6_2_1(Double item6_2_1) {
        this.item6_2_1 = item6_2_1;
    }

    public String getItem7_1() {
        return item7_1;
    }

    public void setItem7_1(String item7_1) {
        this.item7_1 = item7_1;
    }

    public String getItem1_2() {
        return item1_2;
    }

    public void setItem1_2(String item1_2) {
        this.item1_2 = item1_2;
    }

    public Integer getItem2_2() {
        return item2_2;
    }

    public void setItem2_2(Integer item2_2) {
        this.item2_2 = item2_2;
    }

    public Double getItem3_2() {
        return item3_2;
    }

    public void setItem3_2(Double item3_2) {
        this.item3_2 = item3_2;
    }

    public String getItem4_2() {
        return item4_2;
    }

    public void setItem4_2(String item4_2) {
        this.item4_2 = item4_2;
    }

    public Double getItem5_2() {
        return item5_2;
    }

    public void setItem5_2(Double item5_2) {
        this.item5_2 = item5_2;
    }

    public Double getItem6_1_2() {
        return item6_1_2;
    }

    public void setItem6_1_2(Double item6_1_2) {
        this.item6_1_2 = item6_1_2;
    }

    public Double getItem6_2_2() {
        return item6_2_2;
    }

    public void setItem6_2_2(Double item6_2_2) {
        this.item6_2_2 = item6_2_2;
    }

    public String getItem7_2() {
        return item7_2;
    }

    public void setItem7_2(String item7_2) {
        this.item7_2 = item7_2;
    }

    public String getItem1_3() {
        return item1_3;
    }

    public void setItem1_3(String item1_3) {
        this.item1_3 = item1_3;
    }

    public Integer getItem2_3() {
        return item2_3;
    }

    public void setItem2_3(Integer item2_3) {
        this.item2_3 = item2_3;
    }

    public Double getItem3_3() {
        return item3_3;
    }

    public void setItem3_3(Double item3_3) {
        this.item3_3 = item3_3;
    }

    public String getItem4_3() {
        return item4_3;
    }

    public void setItem4_3(String item4_3) {
        this.item4_3 = item4_3;
    }

    public Double getItem5_3() {
        return item5_3;
    }

    public void setItem5_3(Double item5_3) {
        this.item5_3 = item5_3;
    }

    public Double getItem6_1_3() {
        return item6_1_3;
    }

    public void setItem6_1_3(Double item6_1_3) {
        this.item6_1_3 = item6_1_3;
    }

    public Double getItem6_2_3() {
        return item6_2_3;
    }

    public void setItem6_2_3(Double item6_2_3) {
        this.item6_2_3 = item6_2_3;
    }

    public String getItem7_3() {
        return item7_3;
    }

    public void setItem7_3(String item7_3) {
        this.item7_3 = item7_3;
    }

    public String getItem1_4() {
        return item1_4;
    }

    public void setItem1_4(String item1_4) {
        this.item1_4 = item1_4;
    }

    public Integer getItem2_4() {
        return item2_4;
    }

    public void setItem2_4(Integer item2_4) {
        this.item2_4 = item2_4;
    }

    public Double getItem3_4() {
        return item3_4;
    }

    public void setItem3_4(Double item3_4) {
        this.item3_4 = item3_4;
    }

    public String getItem4_4() {
        return item4_4;
    }

    public void setItem4_4(String item4_4) {
        this.item4_4 = item4_4;
    }

    public Double getItem5_4() {
        return item5_4;
    }

    public void setItem5_4(Double item5_4) {
        this.item5_4 = item5_4;
    }

    public Double getItem6_1_4() {
        return item6_1_4;
    }

    public void setItem6_1_4(Double item6_1_4) {
        this.item6_1_4 = item6_1_4;
    }

    public Double getItem6_2_4() {
        return item6_2_4;
    }

    public void setItem6_2_4(Double item6_2_4) {
        this.item6_2_4 = item6_2_4;
    }

    public String getItem7_4() {
        return item7_4;
    }

    public void setItem7_4(String item7_4) {
        this.item7_4 = item7_4;
    }

    public String getItem1_5() {
        return item1_5;
    }

    public void setItem1_5(String item1_5) {
        this.item1_5 = item1_5;
    }

    public Integer getItem2_5() {
        return item2_5;
    }

    public void setItem2_5(Integer item2_5) {
        this.item2_5 = item2_5;
    }

    public Double getItem3_5() {
        return item3_5;
    }

    public void setItem3_5(Double item3_5) {
        this.item3_5 = item3_5;
    }

    public String getItem4_5() {
        return item4_5;
    }

    public void setItem4_5(String item4_5) {
        this.item4_5 = item4_5;
    }

    public Double getItem5_5() {
        return item5_5;
    }

    public void setItem5_5(Double item5_5) {
        this.item5_5 = item5_5;
    }

    public Double getItem6_1_5() {
        return item6_1_5;
    }

    public void setItem6_1_5(Double item6_1_5) {
        this.item6_1_5 = item6_1_5;
    }

    public Double getItem6_2_5() {
        return item6_2_5;
    }

    public void setItem6_2_5(Double item6_2_5) {
        this.item6_2_5 = item6_2_5;
    }

    public String getItem7_5() {
        return item7_5;
    }

    public void setItem7_5(String item7_5) {
        this.item7_5 = item7_5;
    }

    public String getItem1_6() {
        return item1_6;
    }

    public void setItem1_6(String item1_6) {
        this.item1_6 = item1_6;
    }

    public Integer getItem2_6() {
        return item2_6;
    }

    public void setItem2_6(Integer item2_6) {
        this.item2_6 = item2_6;
    }

    public Double getItem3_6() {
        return item3_6;
    }

    public void setItem3_6(Double item3_6) {
        this.item3_6 = item3_6;
    }

    public String getItem4_6() {
        return item4_6;
    }

    public void setItem4_6(String item4_6) {
        this.item4_6 = item4_6;
    }

    public Double getItem5_6() {
        return item5_6;
    }

    public void setItem5_6(Double item5_6) {
        this.item5_6 = item5_6;
    }

    public Double getItem6_1_6() {
        return item6_1_6;
    }

    public void setItem6_1_6(Double item6_1_6) {
        this.item6_1_6 = item6_1_6;
    }

    public Double getItem6_2_6() {
        return item6_2_6;
    }

    public void setItem6_2_6(Double item6_2_6) {
        this.item6_2_6 = item6_2_6;
    }

    public String getItem7_6() {
        return item7_6;
    }

    public void setItem7_6(String item7_6) {
        this.item7_6 = item7_6;
    }

    public String getItem1_7() {
        return item1_7;
    }

    public void setItem1_7(String item1_7) {
        this.item1_7 = item1_7;
    }

    public Integer getItem2_7() {
        return item2_7;
    }

    public void setItem2_7(Integer item2_7) {
        this.item2_7 = item2_7;
    }

    public Double getItem3_7() {
        return item3_7;
    }

    public void setItem3_7(Double item3_7) {
        this.item3_7 = item3_7;
    }

    public String getItem4_7() {
        return item4_7;
    }

    public void setItem4_7(String item4_7) {
        this.item4_7 = item4_7;
    }

    public Double getItem5_7() {
        return item5_7;
    }

    public void setItem5_7(Double item5_7) {
        this.item5_7 = item5_7;
    }

    public Double getItem6_1_7() {
        return item6_1_7;
    }

    public void setItem6_1_7(Double item6_1_7) {
        this.item6_1_7 = item6_1_7;
    }

    public Double getItem6_2_7() {
        return item6_2_7;
    }

    public void setItem6_2_7(Double item6_2_7) {
        this.item6_2_7 = item6_2_7;
    }

    public String getItem7_7() {
        return item7_7;
    }

    public void setItem7_7(String item7_7) {
        this.item7_7 = item7_7;
    }

    public String getItem1_8() {
        return item1_8;
    }

    public void setItem1_8(String item1_8) {
        this.item1_8 = item1_8;
    }

    public Integer getItem2_8() {
        return item2_8;
    }

    public void setItem2_8(Integer item2_8) {
        this.item2_8 = item2_8;
    }

    public Double getItem3_8() {
        return item3_8;
    }

    public void setItem3_8(Double item3_8) {
        this.item3_8 = item3_8;
    }

    public String getItem4_8() {
        return item4_8;
    }

    public void setItem4_8(String item4_8) {
        this.item4_8 = item4_8;
    }

    public Double getItem5_8() {
        return item5_8;
    }

    public void setItem5_8(Double item5_8) {
        this.item5_8 = item5_8;
    }

    public Double getItem6_1_8() {
        return item6_1_8;
    }

    public void setItem6_1_8(Double item6_1_8) {
        this.item6_1_8 = item6_1_8;
    }

    public Double getItem6_2_8() {
        return item6_2_8;
    }

    public void setItem6_2_8(Double item6_2_8) {
        this.item6_2_8 = item6_2_8;
    }

    public String getItem7_8() {
        return item7_8;
    }

    public void setItem7_8(String item7_8) {
        this.item7_8 = item7_8;
    }

    public String getItem1_9() {
        return item1_9;
    }

    public void setItem1_9(String item1_9) {
        this.item1_9 = item1_9;
    }

    public Integer getItem2_9() {
        return item2_9;
    }

    public void setItem2_9(Integer item2_9) {
        this.item2_9 = item2_9;
    }

    public Double getItem3_9() {
        return item3_9;
    }

    public void setItem3_9(Double item3_9) {
        this.item3_9 = item3_9;
    }

    public String getItem4_9() {
        return item4_9;
    }

    public void setItem4_9(String item4_9) {
        this.item4_9 = item4_9;
    }

    public Double getItem5_9() {
        return item5_9;
    }

    public void setItem5_9(Double item5_9) {
        this.item5_9 = item5_9;
    }

    public Double getItem6_1_9() {
        return item6_1_9;
    }

    public void setItem6_1_9(Double item6_1_9) {
        this.item6_1_9 = item6_1_9;
    }

    public Double getItem6_2_9() {
        return item6_2_9;
    }

    public void setItem6_2_9(Double item6_2_9) {
        this.item6_2_9 = item6_2_9;
    }

    public String getItem7_9() {
        return item7_9;
    }

    public void setItem7_9(String item7_9) {
        this.item7_9 = item7_9;
    }

    public Integer getOverallStatusCy() {
        return overallStatusCy;
    }

    public void setOverallStatusCy(Integer overallStatusCy) {
        this.overallStatusCy = overallStatusCy;
    }
}
