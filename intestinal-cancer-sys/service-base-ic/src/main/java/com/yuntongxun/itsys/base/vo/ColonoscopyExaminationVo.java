package com.yuntongxun.itsys.base.vo;

import com.yuntongxun.itsys.base.po.HospitalColonoscopyRecord;

import java.util.Date;
import java.util.List;

public class ColonoscopyExaminationVo {

	private List<HospitalColonoscopyRecord> hospitalColonoscopyRecords;
	private Integer examinationStatus;
	private Integer examinationOperator;
	private String recordIds;

	public Integer getExaminationStatus() {
		return examinationStatus;
	}

	public void setExaminationStatus(Integer examinationStatus) {
		this.examinationStatus = examinationStatus;
	}

	public Integer getExaminationOperator() {
		return examinationOperator;
	}

	public void setExaminationOperator(Integer examinationOperator) {
		this.examinationOperator = examinationOperator;
	}

	public List<HospitalColonoscopyRecord> getHospitalColonoscopyRecords() {
		return hospitalColonoscopyRecords;
	}

	public void setHospitalColonoscopyRecords(List<HospitalColonoscopyRecord> hospitalColonoscopyRecords) {
		this.hospitalColonoscopyRecords = hospitalColonoscopyRecords;
	}

	public String getRecordIds() {
		return recordIds;
	}

	public void setRecordIds(String recordIds) {
		this.recordIds = recordIds;
	}
}
