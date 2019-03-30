package com.yuntongxun.itsys.base.vo;

import com.yuntongxun.itsys.base.po.FileUploadLogPO;

import java.util.List;

/**
 * @author zongt
 * @date 2018/4/20
 */
public class HospitalColonoscopyResultVo {

    private Integer id;
    private String sid;
    private Integer stage;
    private String surveyDate;
    private Integer item_2_1;
    private Integer item_2_1_a;//int 有无肿块 1：有，2：无
    private Double item_2_1_b;//double 肿块距肛cm
    private Integer item_2_1_c;//几点钟
    private Integer item_2_1_d;//占据肠腔 分子
    private Integer item_2_1_e;//占据肠腔 分母
    private Integer item_2_2;
    private Integer item_3_1;
    private Integer item_3_2;
    private Integer item_3_3;
    private Integer item_3_4;
    private Integer item_3_5;
    private Integer item_3_6_1;
    private Integer item_3_6_2;
    private Integer item_3_6_3;
    private String item_3_6_3_1;
    private String item_3_6_3_2;
    private Integer item_3_6_4;
    private String item_3_6_4_other;
    private Integer item_3_7;
    private String item_3_7_a;  //检出息肉的具体数（息肉具体个数的限制为：1-20的整数）
    private Integer item_3_8;
    private String item_3_8_other;
    private String otherLesions;
    private String endoscopicDiagnosis;
    private String diagnosisDoctor;
    private Integer pathology;
    private  Integer overallStatusCy;
    private Integer communityDeptId;// int 社区ID
    private Integer areaDeptId;// int 地区医院ID
    private Integer pathologyId; //病例结果id
    private String imagePath;    //图片路径
    private List<FileUploadLogPO> fileUploads;  //图片详情
    private List<ColonoscopyLesionsRecordVo> lesionsRecordList;
    private String applyStatus;
    private String editStatus;
    private String approvalStatus;
    //2018-08-29 宗曈
    private String name;
    private String id_card;
    private String sex;
    private String age;
    private String survey_date;
    private String item_2_1_d_e;

    private Integer item1_1;
    private Double item2_1;
    private String item3_1;
    private String item4_1;
    private Double item5_1;
    private Integer item6_1;
    private Integer item7_1;
    private Integer item8_1;
    private Integer item9_1;
    private Integer item10_1;

    private Integer item1_2;
    private Double item2_2;
    private String item3_2;
    private String item4_2;
    private Double item5_2;
    private Integer item6_2;
    private Integer item7_2;
    private Integer item8_2;
    private Integer item9_2;
    private Integer item10_2;

    private Integer item1_3;
    private Double item2_3;
    private String item3_3;
    private String item4_3;
    private Double item5_3;
    private Integer item6_3;
    private Integer item7_3;
    private Integer item8_3;
    private Integer item9_3;
    private Integer item10_3;

    private Integer item1_4;
    private Double item2_4;
    private String item3_4;
    private String item4_4;
    private Double item5_4;
    private Integer item6_4;
    private Integer item7_4;
    private Integer item8_4;
    private Integer item9_4;
    private Integer item10_4;

    private Integer item1_5;
    private Double item2_5;
    private String item3_5;
    private String item4_5;
    private Double item5_5;
    private Integer item6_5;
    private Integer item7_5;
    private Integer item8_5;
    private Integer item9_5;
    private Integer item10_5;

    private Integer item1_6;
    private Double item2_6;
    private String item3_6;
    private String item4_6;
    private Double item5_6;
    private Integer item6_6;
    private Integer item7_6;
    private Integer item8_6;
    private Integer item9_6;
    private Integer item10_6;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public String getSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(String surveyDate) {
        this.surveyDate = surveyDate;
    }

    public Integer getItem_2_1() {
        return item_2_1;
    }

    public void setItem_2_1(Integer item_2_1) {
        this.item_2_1 = item_2_1;
    }

    public Integer getItem_2_2() {
        return item_2_2;
    }

    public void setItem_2_2(Integer item_2_2) {
        this.item_2_2 = item_2_2;
    }

    public Integer getItem_3_1() {
        return item_3_1;
    }

    public void setItem_3_1(Integer item_3_1) {
        this.item_3_1 = item_3_1;
    }

    public Integer getItem_3_2() {
        return item_3_2;
    }

    public void setItem_3_2(Integer item_3_2) {
        this.item_3_2 = item_3_2;
    }

    public Integer getItem_3_3() {
        return item_3_3;
    }

    public void setItem_3_3(Integer item_3_3) {
        this.item_3_3 = item_3_3;
    }

    public Integer getItem_3_4() {
        return item_3_4;
    }

    public void setItem_3_4(Integer item_3_4) {
        this.item_3_4 = item_3_4;
    }

    public Integer getItem_3_5() {
        return item_3_5;
    }

    public void setItem_3_5(Integer item_3_5) {
        this.item_3_5 = item_3_5;
    }

    public Integer getItem_3_6_1() {
        return item_3_6_1;
    }

    public void setItem_3_6_1(Integer item_3_6_1) {
        this.item_3_6_1 = item_3_6_1;
    }

    public Integer getItem_3_6_2() {
        return item_3_6_2;
    }

    public void setItem_3_6_2(Integer item_3_6_2) {
        this.item_3_6_2 = item_3_6_2;
    }

    public Integer getItem_3_6_3() {
        return item_3_6_3;
    }

    public void setItem_3_6_3(Integer item_3_6_3) {
        this.item_3_6_3 = item_3_6_3;
    }

    public String getItem_3_6_3_1() {
        return item_3_6_3_1;
    }

    public void setItem_3_6_3_1(String item_3_6_3_1) {
        this.item_3_6_3_1 = item_3_6_3_1;
    }

    public String getItem_3_6_3_2() {
        return item_3_6_3_2;
    }

    public void setItem_3_6_3_2(String item_3_6_3_2) {
        this.item_3_6_3_2 = item_3_6_3_2;
    }

    public Integer getItem_3_6_4() {
        return item_3_6_4;
    }

    public void setItem_3_6_4(Integer item_3_6_4) {
        this.item_3_6_4 = item_3_6_4;
    }

    public String getItem_3_6_4_other() {
        return item_3_6_4_other;
    }

    public void setItem_3_6_4_other(String item_3_6_4_other) {
        this.item_3_6_4_other = item_3_6_4_other;
    }

    public Integer getItem_3_7() {
        return item_3_7;
    }

    public void setItem_3_7(Integer item_3_7) {
        this.item_3_7 = item_3_7;
    }

    public Integer getItem_3_8() {
        return item_3_8;
    }

    public void setItem_3_8(Integer item_3_8) {
        this.item_3_8 = item_3_8;
    }

    public String getItem_3_8_other() {
        return item_3_8_other;
    }

    public void setItem_3_8_other(String item_3_8_other) {
        this.item_3_8_other = item_3_8_other;
    }

    public String getOtherLesions() {
        return otherLesions;
    }

    public void setOtherLesions(String otherLesions) {
        this.otherLesions = otherLesions;
    }

    public String getEndoscopicDiagnosis() {
        return endoscopicDiagnosis;
    }

    public void setEndoscopicDiagnosis(String endoscopicDiagnosis) {
        this.endoscopicDiagnosis = endoscopicDiagnosis;
    }

    public String getDiagnosisDoctor() {
        return diagnosisDoctor;
    }

    public void setDiagnosisDoctor(String diagnosisDoctor) {
        this.diagnosisDoctor = diagnosisDoctor;
    }

    public Integer getPathology() {
        return pathology;
    }

    public void setPathology(Integer pathology) {
        this.pathology = pathology;
    }

    public List<ColonoscopyLesionsRecordVo> getLesionsRecordList() {
        return lesionsRecordList;
    }

    public void setLesionsRecordList(List<ColonoscopyLesionsRecordVo> lesionsRecordList) {
        this.lesionsRecordList = lesionsRecordList;
    }

    public Integer getCommunityDeptId() {
        return communityDeptId;
    }

    public void setCommunityDeptId(Integer communityDeptId) {
        this.communityDeptId = communityDeptId;
    }

    public Integer getAreaDeptId() {
        return areaDeptId;
    }

    public void setAreaDeptId(Integer areaDeptId) {
        this.areaDeptId = areaDeptId;
    }

    public Integer getItem_2_1_a() {
        return item_2_1_a;
    }

    public void setItem_2_1_a(Integer item_2_1_a) {
        this.item_2_1_a = item_2_1_a;
    }

    public Double getItem_2_1_b() {
        return item_2_1_b;
    }

    public void setItem_2_1_b(Double item_2_1_b) {
        this.item_2_1_b = item_2_1_b;
    }

    public Integer getItem_2_1_c() {
        return item_2_1_c;
    }

    public void setItem_2_1_c(Integer item_2_1_c) {
        this.item_2_1_c = item_2_1_c;
    }

    public Integer getItem_2_1_d() {
        return item_2_1_d;
    }

    public void setItem_2_1_d(Integer item_2_1_d) {
        this.item_2_1_d = item_2_1_d;
    }

    public Integer getItem_2_1_e() {
        return item_2_1_e;
    }

    public void setItem_2_1_e(Integer item_2_1_e) {
        this.item_2_1_e = item_2_1_e;
    }

    public String getItem_3_7_a() {
        return item_3_7_a;
    }

    public void setItem_3_7_a(String item_3_7_a) {
        this.item_3_7_a = item_3_7_a;
    }

    public Integer getPathologyId() {
        return pathologyId;
    }

    public void setPathologyId(Integer pathologyId) {
        this.pathologyId = pathologyId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<FileUploadLogPO> getFileUploads() {
        return fileUploads;
    }

    public void setFileUploads(List<FileUploadLogPO> fileUploads) {
        this.fileUploads = fileUploads;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSurvey_date() {
        return survey_date;
    }

    public void setSurvey_date(String survey_date) {
        this.survey_date = survey_date;
    }

    public String getItem_2_1_d_e() {
        return item_2_1_d_e;
    }

    public void setItem_2_1_d_e(String item_2_1_d_e) {
        this.item_2_1_d_e = item_2_1_d_e;
    }

    public Integer getItem1_1() {
        return item1_1;
    }

    public void setItem1_1(Integer item1_1) {
        this.item1_1 = item1_1;
    }

    public Double getItem2_1() {
        return item2_1;
    }

    public void setItem2_1(Double item2_1) {
        this.item2_1 = item2_1;
    }

    public String getItem3_1() {
        return item3_1;
    }

    public void setItem3_1(String item3_1) {
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

    public Integer getItem6_1() {
        return item6_1;
    }

    public void setItem6_1(Integer item6_1) {
        this.item6_1 = item6_1;
    }

    public Integer getItem7_1() {
        return item7_1;
    }

    public void setItem7_1(Integer item7_1) {
        this.item7_1 = item7_1;
    }

    public Integer getItem8_1() {
        return item8_1;
    }

    public void setItem8_1(Integer item8_1) {
        this.item8_1 = item8_1;
    }

    public Integer getItem9_1() {
        return item9_1;
    }

    public void setItem9_1(Integer item9_1) {
        this.item9_1 = item9_1;
    }

    public Integer getItem10_1() {
        return item10_1;
    }

    public void setItem10_1(Integer item10_1) {
        this.item10_1 = item10_1;
    }

    public Integer getItem1_2() {
        return item1_2;
    }

    public void setItem1_2(Integer item1_2) {
        this.item1_2 = item1_2;
    }

    public Double getItem2_2() {
        return item2_2;
    }

    public void setItem2_2(Double item2_2) {
        this.item2_2 = item2_2;
    }

    public String getItem3_2() {
        return item3_2;
    }

    public void setItem3_2(String item3_2) {
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

    public Integer getItem6_2() {
        return item6_2;
    }

    public void setItem6_2(Integer item6_2) {
        this.item6_2 = item6_2;
    }

    public Integer getItem7_2() {
        return item7_2;
    }

    public void setItem7_2(Integer item7_2) {
        this.item7_2 = item7_2;
    }

    public Integer getItem8_2() {
        return item8_2;
    }

    public void setItem8_2(Integer item8_2) {
        this.item8_2 = item8_2;
    }

    public Integer getItem9_2() {
        return item9_2;
    }

    public void setItem9_2(Integer item9_2) {
        this.item9_2 = item9_2;
    }

    public Integer getItem10_2() {
        return item10_2;
    }

    public void setItem10_2(Integer item10_2) {
        this.item10_2 = item10_2;
    }

    public Integer getItem1_3() {
        return item1_3;
    }

    public void setItem1_3(Integer item1_3) {
        this.item1_3 = item1_3;
    }

    public Double getItem2_3() {
        return item2_3;
    }

    public void setItem2_3(Double item2_3) {
        this.item2_3 = item2_3;
    }

    public String getItem3_3() {
        return item3_3;
    }

    public void setItem3_3(String item3_3) {
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

    public Integer getItem6_3() {
        return item6_3;
    }

    public void setItem6_3(Integer item6_3) {
        this.item6_3 = item6_3;
    }

    public Integer getItem7_3() {
        return item7_3;
    }

    public void setItem7_3(Integer item7_3) {
        this.item7_3 = item7_3;
    }

    public Integer getItem8_3() {
        return item8_3;
    }

    public void setItem8_3(Integer item8_3) {
        this.item8_3 = item8_3;
    }

    public Integer getItem9_3() {
        return item9_3;
    }

    public void setItem9_3(Integer item9_3) {
        this.item9_3 = item9_3;
    }

    public Integer getItem10_3() {
        return item10_3;
    }

    public void setItem10_3(Integer item10_3) {
        this.item10_3 = item10_3;
    }

    public Integer getItem1_4() {
        return item1_4;
    }

    public void setItem1_4(Integer item1_4) {
        this.item1_4 = item1_4;
    }

    public Double getItem2_4() {
        return item2_4;
    }

    public void setItem2_4(Double item2_4) {
        this.item2_4 = item2_4;
    }

    public String getItem3_4() {
        return item3_4;
    }

    public void setItem3_4(String item3_4) {
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

    public Integer getItem6_4() {
        return item6_4;
    }

    public void setItem6_4(Integer item6_4) {
        this.item6_4 = item6_4;
    }

    public Integer getItem7_4() {
        return item7_4;
    }

    public void setItem7_4(Integer item7_4) {
        this.item7_4 = item7_4;
    }

    public Integer getItem8_4() {
        return item8_4;
    }

    public void setItem8_4(Integer item8_4) {
        this.item8_4 = item8_4;
    }

    public Integer getItem9_4() {
        return item9_4;
    }

    public void setItem9_4(Integer item9_4) {
        this.item9_4 = item9_4;
    }

    public Integer getItem10_4() {
        return item10_4;
    }

    public void setItem10_4(Integer item10_4) {
        this.item10_4 = item10_4;
    }

    public Integer getItem1_5() {
        return item1_5;
    }

    public void setItem1_5(Integer item1_5) {
        this.item1_5 = item1_5;
    }

    public Double getItem2_5() {
        return item2_5;
    }

    public void setItem2_5(Double item2_5) {
        this.item2_5 = item2_5;
    }

    public String getItem3_5() {
        return item3_5;
    }

    public void setItem3_5(String item3_5) {
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

    public Integer getItem6_5() {
        return item6_5;
    }

    public void setItem6_5(Integer item6_5) {
        this.item6_5 = item6_5;
    }

    public Integer getItem7_5() {
        return item7_5;
    }

    public void setItem7_5(Integer item7_5) {
        this.item7_5 = item7_5;
    }

    public Integer getItem8_5() {
        return item8_5;
    }

    public void setItem8_5(Integer item8_5) {
        this.item8_5 = item8_5;
    }

    public Integer getItem9_5() {
        return item9_5;
    }

    public void setItem9_5(Integer item9_5) {
        this.item9_5 = item9_5;
    }

    public Integer getItem10_5() {
        return item10_5;
    }

    public void setItem10_5(Integer item10_5) {
        this.item10_5 = item10_5;
    }

    public Integer getItem1_6() {
        return item1_6;
    }

    public void setItem1_6(Integer item1_6) {
        this.item1_6 = item1_6;
    }

    public Double getItem2_6() {
        return item2_6;
    }

    public void setItem2_6(Double item2_6) {
        this.item2_6 = item2_6;
    }

    public String getItem3_6() {
        return item3_6;
    }

    public void setItem3_6(String item3_6) {
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

    public Integer getItem6_6() {
        return item6_6;
    }

    public void setItem6_6(Integer item6_6) {
        this.item6_6 = item6_6;
    }

    public Integer getItem7_6() {
        return item7_6;
    }

    public void setItem7_6(Integer item7_6) {
        this.item7_6 = item7_6;
    }

    public Integer getItem8_6() {
        return item8_6;
    }

    public void setItem8_6(Integer item8_6) {
        this.item8_6 = item8_6;
    }

    public Integer getItem9_6() {
        return item9_6;
    }

    public void setItem9_6(Integer item9_6) {
        this.item9_6 = item9_6;
    }

    public Integer getItem10_6() {
        return item10_6;
    }

    public void setItem10_6(Integer item10_6) {
        this.item10_6 = item10_6;
    }

    public Integer getOverallStatusCy() {
        return overallStatusCy;
    }

    public void setOverallStatusCy(Integer overallStatusCy) {
        this.overallStatusCy = overallStatusCy;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(String editStatus) {
        this.editStatus = editStatus;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
}
