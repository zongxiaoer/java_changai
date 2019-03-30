package com.yuntongxun.itsys.base.po;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Description: 危险因素
 *
 * @author LuoKun on 2018-04-17.
 */
public class HospitalRiskFactor {

    private Integer id;

    private String sid;

    private String investigator;

    private String survey_date;

    private String reviewer;
    private String investigator_code;

    private String contact_relationship;

    private String contact_cell_phone;

    private String contact_telephone;

    private Integer height;

    private Integer weight;

    private Integer yao_wei;

    private Integer education;

    private Integer marriage;

    private String marriage_other;
    private Integer item_2_1;
    private String item_2_1_1;
    private Integer item_2_1_2_1;
    private Integer item_2_1_2_2;
    private Integer item_2_1_2_3;
    private Integer item_2_1_2_4;
    private String item_2_1_2_4_other;
    private Integer item_2_2;
    private String item_2_2_1;
    private Integer item_2_3;
    private String item_2_3_1;
    private String item_2_3_2;
    private Integer item_2_4;
    private String item_2_4_1;
    private Integer item_2_4_2;
    private Integer item_2_4_3;
    private Integer item_3_1;
    private Integer item_3_1_1;
    private Integer item_3_1_2;
    private String item_3_1_2_1;
    private Integer item_3_1_2_2;
    private Integer item_3_2;
    private Integer item_3_2_1;
    private Integer item_3_2_2;
    private String item_3_2_2_1;
    private Integer item_3_2_2_2;
    private Integer item_3_3;
    private Integer item_3_3_years;
    private Integer item_3_3_1;
    private String item_3_3_1_1;
    private String item_3_3_1_2;
    private Integer item_4_1;//您是否曾经吸烟？1：不吸烟，2：吸烟
    private Integer item_4_2;
    private Integer item_4_3;
    private Integer item_4_3_1;
    private Integer item_4_4;
    private Integer item_4_5;
    private Integer item_4_6;
    private Integer item_4_7;
    private String item_4_8;
    private Integer item_4_9;
    private Integer item_4_10;
    private Integer item_4_11_1;
    private Integer item_4_11_2;
    private Integer item_4_11_3;
    private Integer item_4_11_4;
    private Integer item_4_11_5;
    private Integer item_4_11_6;
    private Integer item_4_11_7;
    private Integer item_4_11_8;
    private Integer item_4_11_9;
    private Double item_4_12_1;
    private Double item_4_12_2;
    private Double item_4_12_3;
    private Double item_4_12_4;
    private Double item_4_12_5;
    private Double item_4_12_6;
    private Double item_4_12_7;
    private Double item_4_12_8;
    private Double item_4_12_9;
    private Double item_4_12_10;
    private Integer item_5_1;
    private Integer item_5_1_1;
    private String item_5_1_1_age;
    private Integer item_5_1_2;
    private Integer item_5_1_2_age;
    private Integer item_5_1_3;
    private Integer item_5_1_3_age;
    private Integer item_5_1_4;
    private Integer item_5_1_4_age;
    private Integer item_5_1_5;
    private Integer item_5_1_5_age;
    private Integer item_5_1_6;
    private Integer item_5_1_6_age;
    private Integer item_5_1_7;
    private Integer item_5_1_7_age;
    private Integer item_5_1_8;
    private Integer item_5_1_8_age;
    private Integer item_5_1_9;
    private Integer item_5_1_9_age;
    private Integer item_5_1_10;
    private Integer item_5_1_10_age;
    private Integer item_5_2;
    private Integer item_5_2_1;
    private Integer item_5_2_1_age;
    private Integer item_5_2_2;
    private Integer item_5_2_2_age;
    private Integer item_5_2_3;
    private Integer item_5_2_3_age;
    private Integer item_5_2_4;
    private Integer item_5_2_4_age;
    private Integer item_5_2_5;
    private Integer item_5_2_5_age;
    private Integer item_5_2_6;
    private Integer item_5_2_6_age;
    private Integer item_5_2_7;
    private Integer item_5_2_7_age;
    private Integer item_5_2_8;
    private Integer item_5_2_8_age;
    private Integer item_5_2_9;
    private Integer item_5_2_9_age;
    private Integer item_5_2_10;
    private Integer item_5_2_10_age;
    private Integer item_5_2_11;
    private Integer item_5_2_11_age;
    private Integer item_5_2_12;
    private Integer item_5_2_12_age;
    private Integer item_5_2_13;
    private Integer item_5_2_13_age;
    private Integer item_5_3;//您的直系亲属是否患过结直肠癌？1：是，2：否，3：不清楚
    private Integer item_5_3_1;
    private Integer item_5_3_1_age;
    private Integer item_5_3_2;
    private Integer item_5_3_2_age;
    private Integer item_5_3_3;
    private Integer item_5_3_3_age;
    private Integer item_5_3_4;
    private Integer item_5_3_4_age;
    private Integer area_dept_id;
    private Integer community_dept_id;
    private Integer delete_flag;
    private Integer score;

    private String applyStatus;

    private String editStatus;

    private String approvalStatus;



    public HospitalRiskFactor() {
        super();
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

    public HospitalRiskFactor(Integer id, String sid, String investigator, String survey_date, String reviewer, String investigator_code, String contact_relationship, String contact_cell_phone, String contact_telephone, Integer height, Integer weight, Integer yao_wei, Integer education, Integer marriage, String marriage_other, Integer item_2_1, String item_2_1_1, Integer item_2_1_2_1, Integer item_2_1_2_2, Integer item_2_1_2_3, Integer item_2_1_2_4, String item_2_1_2_4_other, Integer item_2_2, String item_2_2_1, Integer item_2_3, String item_2_3_1, String item_2_3_2, Integer item_2_4, String item_2_4_1, Integer item_2_4_2, Integer item_2_4_3, Integer item_3_1, Integer item_3_1_1, Integer item_3_1_2, String item_3_1_2_1, Integer item_3_1_2_2, Integer item_3_2, Integer item_3_2_1, Integer item_3_2_2, String item_3_2_2_1, Integer item_3_2_2_2, Integer item_3_3, Integer item_3_3_years, Integer item_3_3_1, String item_3_3_1_1, String item_3_3_1_2, Integer item_4_1, Integer item_4_2, Integer item_4_3, Integer item_4_3_1, Integer item_4_4, Integer item_4_5, Integer item_4_6, Integer item_4_7, String item_4_8, Integer item_4_9, Integer item_4_10, Integer item_4_11_1, Integer item_4_11_2, Integer item_4_11_3, Integer item_4_11_4, Integer item_4_11_5, Integer item_4_11_6, Integer item_4_11_7, Integer item_4_11_8, Integer item_4_11_9, Double item_4_12_1, Double item_4_12_2, Double item_4_12_3, Double item_4_12_4, Double item_4_12_5, Double item_4_12_6, Double item_4_12_7, Double item_4_12_8, Double item_4_12_9, Double item_4_12_10, Integer item_5_1, Integer item_5_1_1, String item_5_1_1_age, Integer item_5_1_2, Integer item_5_1_2_age, Integer item_5_1_3, Integer item_5_1_3_age, Integer item_5_1_4, Integer item_5_1_4_age, Integer item_5_1_5, Integer item_5_1_5_age, Integer item_5_1_6, Integer item_5_1_6_age, Integer item_5_1_7, Integer item_5_1_7_age, Integer item_5_1_8, Integer item_5_1_8_age, Integer item_5_1_9, Integer item_5_1_9_age, Integer item_5_1_10, Integer item_5_1_10_age, Integer item_5_2, Integer item_5_2_1, Integer item_5_2_1_age, Integer item_5_2_2, Integer item_5_2_2_age, Integer item_5_2_3, Integer item_5_2_3_age, Integer item_5_2_4, Integer item_5_2_4_age, Integer item_5_2_5, Integer item_5_2_5_age, Integer item_5_2_6, Integer item_5_2_6_age, Integer item_5_2_7, Integer item_5_2_7_age, Integer item_5_2_8, Integer item_5_2_8_age, Integer item_5_2_9, Integer item_5_2_9_age, Integer item_5_2_10, Integer item_5_2_10_age, Integer item_5_2_11, Integer item_5_2_11_age, Integer item_5_2_12, Integer item_5_2_12_age, Integer item_5_2_13, Integer item_5_2_13_age, Integer item_5_3, Integer item_5_3_1, Integer item_5_3_1_age, Integer item_5_3_2, Integer item_5_3_2_age, Integer item_5_3_3, Integer item_5_3_3_age, Integer item_5_3_4, Integer item_5_3_4_age, Integer area_dept_id, Integer community_dept_id, Integer delete_flag, Integer score, String applyStatus, String editStatus, String approvalStatus) {
        this.id = id;
        this.sid = sid;
        this.investigator = investigator;
        this.survey_date = survey_date;
        this.reviewer = reviewer;
        this.investigator_code = investigator_code;
        this.contact_relationship = contact_relationship;
        this.contact_cell_phone = contact_cell_phone;
        this.contact_telephone = contact_telephone;
        this.height = height;
        this.weight = weight;
        this.yao_wei = yao_wei;
        this.education = education;
        this.marriage = marriage;
        this.marriage_other = marriage_other;
        this.item_2_1 = item_2_1;
        this.item_2_1_1 = item_2_1_1;
        this.item_2_1_2_1 = item_2_1_2_1;
        this.item_2_1_2_2 = item_2_1_2_2;
        this.item_2_1_2_3 = item_2_1_2_3;
        this.item_2_1_2_4 = item_2_1_2_4;
        this.item_2_1_2_4_other = item_2_1_2_4_other;
        this.item_2_2 = item_2_2;
        this.item_2_2_1 = item_2_2_1;
        this.item_2_3 = item_2_3;
        this.item_2_3_1 = item_2_3_1;
        this.item_2_3_2 = item_2_3_2;
        this.item_2_4 = item_2_4;
        this.item_2_4_1 = item_2_4_1;
        this.item_2_4_2 = item_2_4_2;
        this.item_2_4_3 = item_2_4_3;
        this.item_3_1 = item_3_1;
        this.item_3_1_1 = item_3_1_1;
        this.item_3_1_2 = item_3_1_2;
        this.item_3_1_2_1 = item_3_1_2_1;
        this.item_3_1_2_2 = item_3_1_2_2;
        this.item_3_2 = item_3_2;
        this.item_3_2_1 = item_3_2_1;
        this.item_3_2_2 = item_3_2_2;
        this.item_3_2_2_1 = item_3_2_2_1;
        this.item_3_2_2_2 = item_3_2_2_2;
        this.item_3_3 = item_3_3;
        this.item_3_3_years = item_3_3_years;
        this.item_3_3_1 = item_3_3_1;
        this.item_3_3_1_1 = item_3_3_1_1;
        this.item_3_3_1_2 = item_3_3_1_2;
        this.item_4_1 = item_4_1;
        this.item_4_2 = item_4_2;
        this.item_4_3 = item_4_3;
        this.item_4_3_1 = item_4_3_1;
        this.item_4_4 = item_4_4;
        this.item_4_5 = item_4_5;
        this.item_4_6 = item_4_6;
        this.item_4_7 = item_4_7;
        this.item_4_8 = item_4_8;
        this.item_4_9 = item_4_9;
        this.item_4_10 = item_4_10;
        this.item_4_11_1 = item_4_11_1;
        this.item_4_11_2 = item_4_11_2;
        this.item_4_11_3 = item_4_11_3;
        this.item_4_11_4 = item_4_11_4;
        this.item_4_11_5 = item_4_11_5;
        this.item_4_11_6 = item_4_11_6;
        this.item_4_11_7 = item_4_11_7;
        this.item_4_11_8 = item_4_11_8;
        this.item_4_11_9 = item_4_11_9;
        this.item_4_12_1 = item_4_12_1;
        this.item_4_12_2 = item_4_12_2;
        this.item_4_12_3 = item_4_12_3;
        this.item_4_12_4 = item_4_12_4;
        this.item_4_12_5 = item_4_12_5;
        this.item_4_12_6 = item_4_12_6;
        this.item_4_12_7 = item_4_12_7;
        this.item_4_12_8 = item_4_12_8;
        this.item_4_12_9 = item_4_12_9;
        this.item_4_12_10 = item_4_12_10;
        this.item_5_1 = item_5_1;
        this.item_5_1_1 = item_5_1_1;
        this.item_5_1_1_age = item_5_1_1_age;
        this.item_5_1_2 = item_5_1_2;
        this.item_5_1_2_age = item_5_1_2_age;
        this.item_5_1_3 = item_5_1_3;
        this.item_5_1_3_age = item_5_1_3_age;
        this.item_5_1_4 = item_5_1_4;
        this.item_5_1_4_age = item_5_1_4_age;
        this.item_5_1_5 = item_5_1_5;
        this.item_5_1_5_age = item_5_1_5_age;
        this.item_5_1_6 = item_5_1_6;
        this.item_5_1_6_age = item_5_1_6_age;
        this.item_5_1_7 = item_5_1_7;
        this.item_5_1_7_age = item_5_1_7_age;
        this.item_5_1_8 = item_5_1_8;
        this.item_5_1_8_age = item_5_1_8_age;
        this.item_5_1_9 = item_5_1_9;
        this.item_5_1_9_age = item_5_1_9_age;
        this.item_5_1_10 = item_5_1_10;
        this.item_5_1_10_age = item_5_1_10_age;
        this.item_5_2 = item_5_2;
        this.item_5_2_1 = item_5_2_1;
        this.item_5_2_1_age = item_5_2_1_age;
        this.item_5_2_2 = item_5_2_2;
        this.item_5_2_2_age = item_5_2_2_age;
        this.item_5_2_3 = item_5_2_3;
        this.item_5_2_3_age = item_5_2_3_age;
        this.item_5_2_4 = item_5_2_4;
        this.item_5_2_4_age = item_5_2_4_age;
        this.item_5_2_5 = item_5_2_5;
        this.item_5_2_5_age = item_5_2_5_age;
        this.item_5_2_6 = item_5_2_6;
        this.item_5_2_6_age = item_5_2_6_age;
        this.item_5_2_7 = item_5_2_7;
        this.item_5_2_7_age = item_5_2_7_age;
        this.item_5_2_8 = item_5_2_8;
        this.item_5_2_8_age = item_5_2_8_age;
        this.item_5_2_9 = item_5_2_9;
        this.item_5_2_9_age = item_5_2_9_age;
        this.item_5_2_10 = item_5_2_10;
        this.item_5_2_10_age = item_5_2_10_age;
        this.item_5_2_11 = item_5_2_11;
        this.item_5_2_11_age = item_5_2_11_age;
        this.item_5_2_12 = item_5_2_12;
        this.item_5_2_12_age = item_5_2_12_age;
        this.item_5_2_13 = item_5_2_13;
        this.item_5_2_13_age = item_5_2_13_age;
        this.item_5_3 = item_5_3;
        this.item_5_3_1 = item_5_3_1;
        this.item_5_3_1_age = item_5_3_1_age;
        this.item_5_3_2 = item_5_3_2;
        this.item_5_3_2_age = item_5_3_2_age;
        this.item_5_3_3 = item_5_3_3;
        this.item_5_3_3_age = item_5_3_3_age;
        this.item_5_3_4 = item_5_3_4;
        this.item_5_3_4_age = item_5_3_4_age;
        this.area_dept_id = area_dept_id;
        this.community_dept_id = community_dept_id;
        this.delete_flag = delete_flag;
        this.score = score;
        this.applyStatus = applyStatus;
        this.editStatus = editStatus;
        this.approvalStatus = approvalStatus;
    }

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

    public String getInvestigator() {
        return investigator;
    }

    public void setInvestigator(String investigator) {
        this.investigator = investigator;
    }

    public String getSurvey_date() {
        return survey_date;
    }

    public void setSurvey_date(String survey_date) {
        this.survey_date = survey_date;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getInvestigator_code() {
        return investigator_code;
    }

    public void setInvestigator_code(String investigator_code) {
        this.investigator_code = investigator_code;
    }

    public String getContact_relationship() {
        return contact_relationship;
    }

    public void setContact_relationship(String contact_relationship) {
        this.contact_relationship = contact_relationship;
    }

    public String getContact_cell_phone() {
        return contact_cell_phone;
    }

    public void setContact_cell_phone(String contact_cell_phone) {
        this.contact_cell_phone = contact_cell_phone;
    }

    public String getContact_telephone() {
        return contact_telephone;
    }

    public void setContact_telephone(String contact_telephone) {
        this.contact_telephone = contact_telephone;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getYao_wei() {
        return yao_wei;
    }

    public void setYao_wei(Integer yao_wei) {
        this.yao_wei = yao_wei;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public Integer getMarriage() {
        return marriage;
    }

    public void setMarriage(Integer marriage) {
        this.marriage = marriage;
    }

    public String getMarriage_other() {
        return marriage_other;
    }

    public void setMarriage_other(String marriage_other) {
        this.marriage_other = marriage_other;
    }

    public Integer getItem_2_1() {
        return item_2_1;
    }

    public void setItem_2_1(Integer item_2_1) {
        this.item_2_1 = item_2_1;
    }

    public String getItem_2_1_1() {
        return item_2_1_1;
    }

    public void setItem_2_1_1(String item_2_1_1) {
        this.item_2_1_1 = item_2_1_1;
    }

    public Integer getItem_2_1_2_1() {
        return item_2_1_2_1;
    }

    public void setItem_2_1_2_1(Integer item_2_1_2_1) {
        this.item_2_1_2_1 = item_2_1_2_1;
    }

    public Integer getItem_2_1_2_2() {
        return item_2_1_2_2;
    }

    public void setItem_2_1_2_2(Integer item_2_1_2_2) {
        this.item_2_1_2_2 = item_2_1_2_2;
    }

    public Integer getItem_2_1_2_3() {
        return item_2_1_2_3;
    }

    public void setItem_2_1_2_3(Integer item_2_1_2_3) {
        this.item_2_1_2_3 = item_2_1_2_3;
    }

    public Integer getItem_2_1_2_4() {
        return item_2_1_2_4;
    }

    public void setItem_2_1_2_4(Integer item_2_1_2_4) {
        this.item_2_1_2_4 = item_2_1_2_4;
    }

    public String getItem_2_1_2_4_other() {
        return item_2_1_2_4_other;
    }

    public void setItem_2_1_2_4_other(String item_2_1_2_4_other) {
        this.item_2_1_2_4_other = item_2_1_2_4_other;
    }

    public Integer getItem_2_2() {
        return item_2_2;
    }

    public void setItem_2_2(Integer item_2_2) {
        this.item_2_2 = item_2_2;
    }

    public String getItem_2_2_1() {
        return item_2_2_1;
    }

    public void setItem_2_2_1(String item_2_2_1) {
        this.item_2_2_1 = item_2_2_1;
    }

    public Integer getItem_2_3() {
        return item_2_3;
    }

    public void setItem_2_3(Integer item_2_3) {
        this.item_2_3 = item_2_3;
    }

    public String getItem_2_3_1() {
        return item_2_3_1;
    }

    public void setItem_2_3_1(String item_2_3_1) {
        this.item_2_3_1 = item_2_3_1;
    }

    public String getItem_2_3_2() {
        return item_2_3_2;
    }

    public void setItem_2_3_2(String item_2_3_2) {
        this.item_2_3_2 = item_2_3_2;
    }

    public Integer getItem_2_4() {
        return item_2_4;
    }

    public void setItem_2_4(Integer item_2_4) {
        this.item_2_4 = item_2_4;
    }

    public String getItem_2_4_1() {
        return item_2_4_1;
    }

    public void setItem_2_4_1(String item_2_4_1) {
        this.item_2_4_1 = item_2_4_1;
    }

    public Integer getItem_2_4_2() {
        return item_2_4_2;
    }

    public void setItem_2_4_2(Integer item_2_4_2) {
        this.item_2_4_2 = item_2_4_2;
    }

    public Integer getItem_2_4_3() {
        return item_2_4_3;
    }

    public void setItem_2_4_3(Integer item_2_4_3) {
        this.item_2_4_3 = item_2_4_3;
    }

    public Integer getItem_3_1() {
        return item_3_1;
    }

    public void setItem_3_1(Integer item_3_1) {
        this.item_3_1 = item_3_1;
    }

    public Integer getItem_3_1_1() {
        return item_3_1_1;
    }

    public void setItem_3_1_1(Integer item_3_1_1) {
        this.item_3_1_1 = item_3_1_1;
    }

    public Integer getItem_3_1_2() {
        return item_3_1_2;
    }

    public void setItem_3_1_2(Integer item_3_1_2) {
        this.item_3_1_2 = item_3_1_2;
    }

    public String getItem_3_1_2_1() {
        return item_3_1_2_1;
    }

    public void setItem_3_1_2_1(String item_3_1_2_1) {
        this.item_3_1_2_1 = item_3_1_2_1;
    }

    public Integer getItem_3_1_2_2() {
        return item_3_1_2_2;
    }

    public void setItem_3_1_2_2(Integer item_3_1_2_2) {
        this.item_3_1_2_2 = item_3_1_2_2;
    }

    public Integer getItem_3_2() {
        return item_3_2;
    }

    public void setItem_3_2(Integer item_3_2) {
        this.item_3_2 = item_3_2;
    }

    public Integer getItem_3_2_1() {
        return item_3_2_1;
    }

    public void setItem_3_2_1(Integer item_3_2_1) {
        this.item_3_2_1 = item_3_2_1;
    }

    public Integer getItem_3_2_2() {
        return item_3_2_2;
    }

    public void setItem_3_2_2(Integer item_3_2_2) {
        this.item_3_2_2 = item_3_2_2;
    }

    public String getItem_3_2_2_1() {
        return item_3_2_2_1;
    }

    public void setItem_3_2_2_1(String item_3_2_2_1) {
        this.item_3_2_2_1 = item_3_2_2_1;
    }

    public Integer getItem_3_2_2_2() {
        return item_3_2_2_2;
    }

    public void setItem_3_2_2_2(Integer item_3_2_2_2) {
        this.item_3_2_2_2 = item_3_2_2_2;
    }

    public Integer getItem_3_3() {
        return item_3_3;
    }

    public void setItem_3_3(Integer item_3_3) {
        this.item_3_3 = item_3_3;
    }

    public Integer getItem_3_3_years() {
        return item_3_3_years;
    }

    public void setItem_3_3_years(Integer item_3_3_years) {
        this.item_3_3_years = item_3_3_years;
    }

    public Integer getItem_3_3_1() {
        return item_3_3_1;
    }

    public void setItem_3_3_1(Integer item_3_3_1) {
        this.item_3_3_1 = item_3_3_1;
    }

    public String getItem_3_3_1_1() {
        return item_3_3_1_1;
    }

    public void setItem_3_3_1_1(String item_3_3_1_1) {
        this.item_3_3_1_1 = item_3_3_1_1;
    }

    public String getItem_3_3_1_2() {
        return item_3_3_1_2;
    }

    public void setItem_3_3_1_2(String item_3_3_1_2) {
        this.item_3_3_1_2 = item_3_3_1_2;
    }

    public Integer getItem_4_1() {
        return item_4_1;
    }

    public void setItem_4_1(Integer item_4_1) {
        this.item_4_1 = item_4_1;
    }

    public Integer getItem_4_2() {
        return item_4_2;
    }

    public void setItem_4_2(Integer item_4_2) {
        this.item_4_2 = item_4_2;
    }

    public Integer getItem_4_3() {
        return item_4_3;
    }

    public void setItem_4_3(Integer item_4_3) {
        this.item_4_3 = item_4_3;
    }

    public Integer getItem_4_3_1() {
        return item_4_3_1;
    }

    public void setItem_4_3_1(Integer item_4_3_1) {
        this.item_4_3_1 = item_4_3_1;
    }

    public Integer getItem_4_4() {
        return item_4_4;
    }

    public void setItem_4_4(Integer item_4_4) {
        this.item_4_4 = item_4_4;
    }

    public Integer getItem_4_5() {
        return item_4_5;
    }

    public void setItem_4_5(Integer item_4_5) {
        this.item_4_5 = item_4_5;
    }

    public Integer getItem_4_6() {
        return item_4_6;
    }

    public void setItem_4_6(Integer item_4_6) {
        this.item_4_6 = item_4_6;
    }

    public Integer getItem_4_7() {
        return item_4_7;
    }

    public void setItem_4_7(Integer item_4_7) {
        this.item_4_7 = item_4_7;
    }

    public String getItem_4_8() {
        return item_4_8;
    }

    public void setItem_4_8(String item_4_8) {
        this.item_4_8 = item_4_8;
    }

    public Integer getItem_4_9() {
        return item_4_9;
    }

    public void setItem_4_9(Integer item_4_9) {
        this.item_4_9 = item_4_9;
    }

    public Integer getItem_4_10() {
        return item_4_10;
    }

    public void setItem_4_10(Integer item_4_10) {
        this.item_4_10 = item_4_10;
    }

    public Integer getItem_4_11_1() {
        return item_4_11_1;
    }

    public void setItem_4_11_1(Integer item_4_11_1) {
        this.item_4_11_1 = item_4_11_1;
    }

    public Integer getItem_4_11_2() {
        return item_4_11_2;
    }

    public void setItem_4_11_2(Integer item_4_11_2) {
        this.item_4_11_2 = item_4_11_2;
    }

    public Integer getItem_4_11_3() {
        return item_4_11_3;
    }

    public void setItem_4_11_3(Integer item_4_11_3) {
        this.item_4_11_3 = item_4_11_3;
    }

    public Integer getItem_4_11_4() {
        return item_4_11_4;
    }

    public void setItem_4_11_4(Integer item_4_11_4) {
        this.item_4_11_4 = item_4_11_4;
    }

    public Integer getItem_4_11_5() {
        return item_4_11_5;
    }

    public void setItem_4_11_5(Integer item_4_11_5) {
        this.item_4_11_5 = item_4_11_5;
    }

    public Integer getItem_4_11_6() {
        return item_4_11_6;
    }

    public void setItem_4_11_6(Integer item_4_11_6) {
        this.item_4_11_6 = item_4_11_6;
    }

    public Integer getItem_4_11_7() {
        return item_4_11_7;
    }

    public void setItem_4_11_7(Integer item_4_11_7) {
        this.item_4_11_7 = item_4_11_7;
    }

    public Integer getItem_4_11_8() {
        return item_4_11_8;
    }

    public void setItem_4_11_8(Integer item_4_11_8) {
        this.item_4_11_8 = item_4_11_8;
    }

    public Integer getItem_4_11_9() {
        return item_4_11_9;
    }

    public void setItem_4_11_9(Integer item_4_11_9) {
        this.item_4_11_9 = item_4_11_9;
    }

    public Double getItem_4_12_1() {
        return item_4_12_1;
    }

    public void setItem_4_12_1(Double item_4_12_1) {
        this.item_4_12_1 = item_4_12_1;
    }

    public Double getItem_4_12_2() {
        return item_4_12_2;
    }

    public void setItem_4_12_2(Double item_4_12_2) {
        this.item_4_12_2 = item_4_12_2;
    }

    public Double getItem_4_12_3() {
        return item_4_12_3;
    }

    public void setItem_4_12_3(Double item_4_12_3) {
        this.item_4_12_3 = item_4_12_3;
    }

    public Double getItem_4_12_4() {
        return item_4_12_4;
    }

    public void setItem_4_12_4(Double item_4_12_4) {
        this.item_4_12_4 = item_4_12_4;
    }

    public Double getItem_4_12_5() {
        return item_4_12_5;
    }

    public void setItem_4_12_5(Double item_4_12_5) {
        this.item_4_12_5 = item_4_12_5;
    }

    public Double getItem_4_12_6() {
        return item_4_12_6;
    }

    public void setItem_4_12_6(Double item_4_12_6) {
        this.item_4_12_6 = item_4_12_6;
    }

    public Double getItem_4_12_7() {
        return item_4_12_7;
    }

    public void setItem_4_12_7(Double item_4_12_7) {
        this.item_4_12_7 = item_4_12_7;
    }

    public Double getItem_4_12_8() {
        return item_4_12_8;
    }

    public void setItem_4_12_8(Double item_4_12_8) {
        this.item_4_12_8 = item_4_12_8;
    }

    public Double getItem_4_12_9() {
        return item_4_12_9;
    }

    public void setItem_4_12_9(Double item_4_12_9) {
        this.item_4_12_9 = item_4_12_9;
    }

    public Double getItem_4_12_10() {
        return item_4_12_10;
    }

    public void setItem_4_12_10(Double item_4_12_10) {
        this.item_4_12_10 = item_4_12_10;
    }

    public Integer getItem_5_1() {
        return item_5_1;
    }

    public void setItem_5_1(Integer item_5_1) {
        this.item_5_1 = item_5_1;
    }

    public Integer getItem_5_1_1() {
        return item_5_1_1;
    }

    public void setItem_5_1_1(Integer item_5_1_1) {
        this.item_5_1_1 = item_5_1_1;
    }

    public String getItem_5_1_1_age() {
        return item_5_1_1_age;
    }

    public void setItem_5_1_1_age(String item_5_1_1_age) {
        this.item_5_1_1_age = item_5_1_1_age;
    }

    public Integer getItem_5_1_2() {
        return item_5_1_2;
    }

    public void setItem_5_1_2(Integer item_5_1_2) {
        this.item_5_1_2 = item_5_1_2;
    }

    public Integer getItem_5_1_2_age() {
        return item_5_1_2_age;
    }

    public void setItem_5_1_2_age(Integer item_5_1_2_age) {
        this.item_5_1_2_age = item_5_1_2_age;
    }

    public Integer getItem_5_1_3() {
        return item_5_1_3;
    }

    public void setItem_5_1_3(Integer item_5_1_3) {
        this.item_5_1_3 = item_5_1_3;
    }

    public Integer getItem_5_1_3_age() {
        return item_5_1_3_age;
    }

    public void setItem_5_1_3_age(Integer item_5_1_3_age) {
        this.item_5_1_3_age = item_5_1_3_age;
    }

    public Integer getItem_5_1_4() {
        return item_5_1_4;
    }

    public void setItem_5_1_4(Integer item_5_1_4) {
        this.item_5_1_4 = item_5_1_4;
    }

    public Integer getItem_5_1_4_age() {
        return item_5_1_4_age;
    }

    public void setItem_5_1_4_age(Integer item_5_1_4_age) {
        this.item_5_1_4_age = item_5_1_4_age;
    }

    public Integer getItem_5_1_5() {
        return item_5_1_5;
    }

    public void setItem_5_1_5(Integer item_5_1_5) {
        this.item_5_1_5 = item_5_1_5;
    }

    public Integer getItem_5_1_5_age() {
        return item_5_1_5_age;
    }

    public void setItem_5_1_5_age(Integer item_5_1_5_age) {
        this.item_5_1_5_age = item_5_1_5_age;
    }

    public Integer getItem_5_1_6() {
        return item_5_1_6;
    }

    public void setItem_5_1_6(Integer item_5_1_6) {
        this.item_5_1_6 = item_5_1_6;
    }

    public Integer getItem_5_1_6_age() {
        return item_5_1_6_age;
    }

    public void setItem_5_1_6_age(Integer item_5_1_6_age) {
        this.item_5_1_6_age = item_5_1_6_age;
    }

    public Integer getItem_5_1_7() {
        return item_5_1_7;
    }

    public void setItem_5_1_7(Integer item_5_1_7) {
        this.item_5_1_7 = item_5_1_7;
    }

    public Integer getItem_5_1_7_age() {
        return item_5_1_7_age;
    }

    public void setItem_5_1_7_age(Integer item_5_1_7_age) {
        this.item_5_1_7_age = item_5_1_7_age;
    }

    public Integer getItem_5_1_8() {
        return item_5_1_8;
    }

    public void setItem_5_1_8(Integer item_5_1_8) {
        this.item_5_1_8 = item_5_1_8;
    }

    public Integer getItem_5_1_8_age() {
        return item_5_1_8_age;
    }

    public void setItem_5_1_8_age(Integer item_5_1_8_age) {
        this.item_5_1_8_age = item_5_1_8_age;
    }

    public Integer getItem_5_1_9() {
        return item_5_1_9;
    }

    public void setItem_5_1_9(Integer item_5_1_9) {
        this.item_5_1_9 = item_5_1_9;
    }

    public Integer getItem_5_1_9_age() {
        return item_5_1_9_age;
    }

    public void setItem_5_1_9_age(Integer item_5_1_9_age) {
        this.item_5_1_9_age = item_5_1_9_age;
    }

    public Integer getItem_5_1_10() {
        return item_5_1_10;
    }

    public void setItem_5_1_10(Integer item_5_1_10) {
        this.item_5_1_10 = item_5_1_10;
    }

    public Integer getItem_5_1_10_age() {
        return item_5_1_10_age;
    }

    public void setItem_5_1_10_age(Integer item_5_1_10_age) {
        this.item_5_1_10_age = item_5_1_10_age;
    }

    public Integer getItem_5_2() {
        return item_5_2;
    }

    public void setItem_5_2(Integer item_5_2) {
        this.item_5_2 = item_5_2;
    }

    public Integer getItem_5_2_1() {
        return item_5_2_1;
    }

    public void setItem_5_2_1(Integer item_5_2_1) {
        this.item_5_2_1 = item_5_2_1;
    }

    public Integer getItem_5_2_1_age() {
        return item_5_2_1_age;
    }

    public void setItem_5_2_1_age(Integer item_5_2_1_age) {
        this.item_5_2_1_age = item_5_2_1_age;
    }

    public Integer getItem_5_2_2() {
        return item_5_2_2;
    }

    public void setItem_5_2_2(Integer item_5_2_2) {
        this.item_5_2_2 = item_5_2_2;
    }

    public Integer getItem_5_2_2_age() {
        return item_5_2_2_age;
    }

    public void setItem_5_2_2_age(Integer item_5_2_2_age) {
        this.item_5_2_2_age = item_5_2_2_age;
    }

    public Integer getItem_5_2_3() {
        return item_5_2_3;
    }

    public void setItem_5_2_3(Integer item_5_2_3) {
        this.item_5_2_3 = item_5_2_3;
    }

    public Integer getItem_5_2_3_age() {
        return item_5_2_3_age;
    }

    public void setItem_5_2_3_age(Integer item_5_2_3_age) {
        this.item_5_2_3_age = item_5_2_3_age;
    }

    public Integer getItem_5_2_4() {
        return item_5_2_4;
    }

    public void setItem_5_2_4(Integer item_5_2_4) {
        this.item_5_2_4 = item_5_2_4;
    }

    public Integer getItem_5_2_4_age() {
        return item_5_2_4_age;
    }

    public void setItem_5_2_4_age(Integer item_5_2_4_age) {
        this.item_5_2_4_age = item_5_2_4_age;
    }

    public Integer getItem_5_2_5() {
        return item_5_2_5;
    }

    public void setItem_5_2_5(Integer item_5_2_5) {
        this.item_5_2_5 = item_5_2_5;
    }

    public Integer getItem_5_2_5_age() {
        return item_5_2_5_age;
    }

    public void setItem_5_2_5_age(Integer item_5_2_5_age) {
        this.item_5_2_5_age = item_5_2_5_age;
    }

    public Integer getItem_5_2_6() {
        return item_5_2_6;
    }

    public void setItem_5_2_6(Integer item_5_2_6) {
        this.item_5_2_6 = item_5_2_6;
    }

    public Integer getItem_5_2_6_age() {
        return item_5_2_6_age;
    }

    public void setItem_5_2_6_age(Integer item_5_2_6_age) {
        this.item_5_2_6_age = item_5_2_6_age;
    }

    public Integer getItem_5_2_7() {
        return item_5_2_7;
    }

    public void setItem_5_2_7(Integer item_5_2_7) {
        this.item_5_2_7 = item_5_2_7;
    }

    public Integer getItem_5_2_7_age() {
        return item_5_2_7_age;
    }

    public void setItem_5_2_7_age(Integer item_5_2_7_age) {
        this.item_5_2_7_age = item_5_2_7_age;
    }

    public Integer getItem_5_2_8() {
        return item_5_2_8;
    }

    public void setItem_5_2_8(Integer item_5_2_8) {
        this.item_5_2_8 = item_5_2_8;
    }

    public Integer getItem_5_2_8_age() {
        return item_5_2_8_age;
    }

    public void setItem_5_2_8_age(Integer item_5_2_8_age) {
        this.item_5_2_8_age = item_5_2_8_age;
    }

    public Integer getItem_5_2_9() {
        return item_5_2_9;
    }

    public void setItem_5_2_9(Integer item_5_2_9) {
        this.item_5_2_9 = item_5_2_9;
    }

    public Integer getItem_5_2_9_age() {
        return item_5_2_9_age;
    }

    public void setItem_5_2_9_age(Integer item_5_2_9_age) {
        this.item_5_2_9_age = item_5_2_9_age;
    }

    public Integer getItem_5_2_10() {
        return item_5_2_10;
    }

    public void setItem_5_2_10(Integer item_5_2_10) {
        this.item_5_2_10 = item_5_2_10;
    }

    public Integer getItem_5_2_10_age() {
        return item_5_2_10_age;
    }

    public void setItem_5_2_10_age(Integer item_5_2_10_age) {
        this.item_5_2_10_age = item_5_2_10_age;
    }

    public Integer getItem_5_2_11() {
        return item_5_2_11;
    }

    public void setItem_5_2_11(Integer item_5_2_11) {
        this.item_5_2_11 = item_5_2_11;
    }

    public Integer getItem_5_2_11_age() {
        return item_5_2_11_age;
    }

    public void setItem_5_2_11_age(Integer item_5_2_11_age) {
        this.item_5_2_11_age = item_5_2_11_age;
    }

    public Integer getItem_5_2_12() {
        return item_5_2_12;
    }

    public void setItem_5_2_12(Integer item_5_2_12) {
        this.item_5_2_12 = item_5_2_12;
    }

    public Integer getItem_5_2_12_age() {
        return item_5_2_12_age;
    }

    public void setItem_5_2_12_age(Integer item_5_2_12_age) {
        this.item_5_2_12_age = item_5_2_12_age;
    }

    public Integer getItem_5_2_13() {
        return item_5_2_13;
    }

    public void setItem_5_2_13(Integer item_5_2_13) {
        this.item_5_2_13 = item_5_2_13;
    }

    public Integer getItem_5_2_13_age() {
        return item_5_2_13_age;
    }

    public void setItem_5_2_13_age(Integer item_5_2_13_age) {
        this.item_5_2_13_age = item_5_2_13_age;
    }

    public Integer getItem_5_3() {
        return item_5_3;
    }

    public void setItem_5_3(Integer item_5_3) {
        this.item_5_3 = item_5_3;
    }

    public Integer getItem_5_3_1() {
        return item_5_3_1;
    }

    public void setItem_5_3_1(Integer item_5_3_1) {
        this.item_5_3_1 = item_5_3_1;
    }

    public Integer getItem_5_3_1_age() {
        return item_5_3_1_age;
    }

    public void setItem_5_3_1_age(Integer item_5_3_1_age) {
        this.item_5_3_1_age = item_5_3_1_age;
    }

    public Integer getItem_5_3_2() {
        return item_5_3_2;
    }

    public void setItem_5_3_2(Integer item_5_3_2) {
        this.item_5_3_2 = item_5_3_2;
    }

    public Integer getItem_5_3_2_age() {
        return item_5_3_2_age;
    }

    public void setItem_5_3_2_age(Integer item_5_3_2_age) {
        this.item_5_3_2_age = item_5_3_2_age;
    }

    public Integer getItem_5_3_3() {
        return item_5_3_3;
    }

    public void setItem_5_3_3(Integer item_5_3_3) {
        this.item_5_3_3 = item_5_3_3;
    }

    public Integer getItem_5_3_3_age() {
        return item_5_3_3_age;
    }

    public void setItem_5_3_3_age(Integer item_5_3_3_age) {
        this.item_5_3_3_age = item_5_3_3_age;
    }

    public Integer getItem_5_3_4() {
        return item_5_3_4;
    }

    public void setItem_5_3_4(Integer item_5_3_4) {
        this.item_5_3_4 = item_5_3_4;
    }

    public Integer getItem_5_3_4_age() {
        return item_5_3_4_age;
    }

    public void setItem_5_3_4_age(Integer item_5_3_4_age) {
        this.item_5_3_4_age = item_5_3_4_age;
    }

    public Integer getArea_dept_id() {
        return area_dept_id;
    }

    public void setArea_dept_id(Integer area_dept_id) {
        this.area_dept_id = area_dept_id;
    }

    public Integer getCommunity_dept_id() {
        return community_dept_id;
    }

    public void setCommunity_dept_id(Integer community_dept_id) {
        this.community_dept_id = community_dept_id;
    }

    public Integer getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(Integer delete_flag) {
        this.delete_flag = delete_flag;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
