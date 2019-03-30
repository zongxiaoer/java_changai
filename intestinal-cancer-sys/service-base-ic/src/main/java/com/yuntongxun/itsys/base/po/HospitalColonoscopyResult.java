/**
 * Project Name:service-base-ic
 * File Name:HospitalColonoscopyResult.java
 * Package Name:com.yuntongxun.itsys.base.po
 * Date:2018年4月16日下午6:34:40
 * Copyright (c) 2018, ty All Rights Reserved.
 *
*/

package com.yuntongxun.itsys.base.po;

import java.util.Date;

/**
 * 结肠镜结果记录表
 * ClassName:HospitalColonoscopyResult <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年4月16日 下午6:34:40 <br/>
 * @author   ty
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class HospitalColonoscopyResult {
	private Integer id;
	private String sid;
	private Integer stage;
	private Date surveyDate;
	private Integer item_2_1;
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
	private Integer item_3_8;
	private String item_3_8_other;
	private String otherLesions;
	private String endoscopicDiagnosis;
	private String diagnosisDoctor;
	private Integer pathology;
	private Date dateCreated;
	private Date updateTime;

	/**
	 * 前端传值的类型  非数据库字段
	 */
	private String idType;

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
	public Date getSurveyDate() {
		return surveyDate;
	}
	public void setSurveyDate(Date surveyDate) {
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
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	@Override
	public String toString() {
		return "HospitalColonoscopyResult [id=" + id + ", sid=" + sid + ", stage=" + stage + ", surveyDate="
				+ surveyDate + ", item_2_1=" + item_2_1 + ", item_2_2=" + item_2_2 + ", item_3_1=" + item_3_1
				+ ", item_3_2=" + item_3_2 + ", item_3_3=" + item_3_3 + ", item_3_4=" + item_3_4 + ", item_3_5="
				+ item_3_5 + ", item_3_6_1=" + item_3_6_1 + ", item_3_6_2=" + item_3_6_2 + ", item_3_6_3=" + item_3_6_3
				+ ", item_3_6_3_1=" + item_3_6_3_1 + ", item_3_6_3_2=" + item_3_6_3_2 + ", item_3_6_4=" + item_3_6_4
				+ ", item_3_6_4_other=" + item_3_6_4_other + ", item_3_7=" + item_3_7 + ", item_3_8=" + item_3_8
				+ ", item_3_8_other=" + item_3_8_other + ", otherLesions=" + otherLesions + ", endoscopicDiagnosis="
				+ endoscopicDiagnosis + ", diagnosisDoctor=" + diagnosisDoctor + ", pathology=" + pathology
				+ ", dateCreated=" + dateCreated + ", updateTime=" + updateTime + "]";
	}
	
	
}

