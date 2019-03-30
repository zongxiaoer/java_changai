package com.yuntongxun.itsys.base.vo;

import java.util.Date;

public class TodoVoPo {

	private String name;
	private String sid;
	private String phone;
	private String group;
	private int pageSize;
	private int pageNo;

	private String deptId;
	
	private String startDate;
	private String endDate;
	

	private Integer riskFactors;
	private Integer notEntryFITCode;
	private Integer notEntryFITResult;
	private Integer notStoolDnaCode;
	private Integer notReserve;
	private Integer notFinishExamination;
	private Integer notIssueNotification;

	private Integer notEntryColonoscopyResult;
	private Integer notEntryPathologyResult;
	private Integer notEntryNotificationResult;
	private Integer noBloodSampleResult;
	private Integer noFecesSampleResult;
	private Integer noSalivaSampleResult;
	
	//新增社区id
	private String communityDeptId;

	private Integer areaDeptId;

	//记录编号
	private String dnaCode;

	//发放经手人工作编码
	private String personCode;

	private String operationSource;

	//发放日期
	private String releaseDate;
	private Date releaseDateForSql;

	private Integer codeEntryStatus;

	private Integer stage;

	private String sampleType;
	private String sampleTypeAll3;
	private String loginName;
	private Integer resultStatus;//肠镜录入状态

	private Integer cancerFormType;//癌症表单类型   -----zongtong 2018-09-10


	public Integer getStage() {
		return stage;
	}

	public void setStage(Integer stage) {
		this.stage = stage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getRiskFactors() {
		return riskFactors;
	}

	public void setRiskFactors(Integer riskFactors) {
		this.riskFactors = riskFactors;
	}

	public Integer getNotEntryFITCode() {
		return notEntryFITCode;
	}

	public void setNotEntryFITCode(Integer notEntryFITCode) {
		this.notEntryFITCode = notEntryFITCode;
	}

	public Integer getNotEntryFITResult() {
		return notEntryFITResult;
	}

	public void setNotEntryFITResult(Integer notEntryFITResult) {
		this.notEntryFITResult = notEntryFITResult;
	}

	public Integer getNotStoolDnaCode() {
		return notStoolDnaCode;
	}

	public void setNotStoolDnaCode(Integer notStoolDnaCode) {
		this.notStoolDnaCode = notStoolDnaCode;
	}

	public Integer getNotReserve() {
		return notReserve;
	}

	public void setNotReserve(Integer notReserve) {
		this.notReserve = notReserve;
	}

	public Integer getNotFinishExamination() {
		return notFinishExamination;
	}

	public void setNotFinishExamination(Integer notFinishExamination) {
		this.notFinishExamination = notFinishExamination;
	}

	public Integer getNotIssueNotification() {
		return notIssueNotification;
	}

	public void setNotIssueNotification(Integer notIssueNotification) {
		this.notIssueNotification = notIssueNotification;
	}

	public Integer getNotEntryColonoscopyResult() {
		return notEntryColonoscopyResult;
	}

	public void setNotEntryColonoscopyResult(Integer notEntryColonoscopyResult) {
		this.notEntryColonoscopyResult = notEntryColonoscopyResult;
	}

	public Integer getNotEntryPathologyResult() {
		return notEntryPathologyResult;
	}

	public void setNotEntryPathologyResult(Integer notEntryPathologyResult) {
		this.notEntryPathologyResult = notEntryPathologyResult;
	}

	public Integer getNotEntryNotificationResult() {
		return notEntryNotificationResult;
	}

	public void setNotEntryNotificationResult(Integer notEntryNotificationResult) {
		this.notEntryNotificationResult = notEntryNotificationResult;
	}

	public String getCommunityDeptId() {
		return communityDeptId;
	}

	public void setCommunityDeptId(String communityDeptId) {
		this.communityDeptId = communityDeptId;
	}

	public Integer getAreaDeptId() {
		return areaDeptId;
	}

	public void setAreaDeptId(Integer areaDeptId) {
		this.areaDeptId = areaDeptId;
	}

	public String getDnaCode() {
		return dnaCode;
	}

	public void setDnaCode(String dnaCode) {
		this.dnaCode = dnaCode;
	}

	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Date getReleaseDateForSql() {
		return releaseDateForSql;
	}

	public void setReleaseDateForSql(Date releaseDateForSql) {
		this.releaseDateForSql = releaseDateForSql;
	}

	public Integer getCodeEntryStatus() {
		return codeEntryStatus;
	}

	public void setCodeEntryStatus(Integer codeEntryStatus) {
		this.codeEntryStatus = codeEntryStatus;
	}

	public String getOperationSource() {
		return operationSource;
	}

	public void setOperationSource(String operationSource) {
		this.operationSource = operationSource;
	}

	public Integer getNoBloodSampleResult() {
		return noBloodSampleResult;
	}

	public void setNoBloodSampleResult(Integer noBloodSampleResult) {
		this.noBloodSampleResult = noBloodSampleResult;
	}

	public Integer getNoFecesSampleResult() {
		return noFecesSampleResult;
	}

	public void setNoFecesSampleResult(Integer noFecesSampleResult) {
		this.noFecesSampleResult = noFecesSampleResult;
	}

	public Integer getNoSalivaSampleResult() {
		return noSalivaSampleResult;
	}

	public void setNoSalivaSampleResult(Integer noSalivaSampleResult) {
		this.noSalivaSampleResult = noSalivaSampleResult;
	}

	public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}

	public String getSampleTypeAll3() {
		return sampleTypeAll3;
	}

	public void setSampleTypeAll3(String sampleTypeAll3) {
		this.sampleTypeAll3 = sampleTypeAll3;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Integer getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(Integer resultStatus) {
		this.resultStatus = resultStatus;
	}

	public Integer getCancerFormType() {
		return cancerFormType;
	}

	public void setCancerFormType(Integer cancerFormType) {
		this.cancerFormType = cancerFormType;
	}
}
