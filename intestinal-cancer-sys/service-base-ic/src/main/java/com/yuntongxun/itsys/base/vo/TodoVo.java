package com.yuntongxun.itsys.base.vo;

import java.util.Date;

public class TodoVo {

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
	private Integer noBloodSampleResult;//血液代办数量
	private Integer noFecesSampleResult;//粪便
	private Integer noSalivaSampleResult;//唾液

	private Integer noStoolDNAResult;//DNA录入结果

	private Integer notIssueDna;//社区DNA发放代办数量

    //新增DNA结果
	private Integer dnaCheckResult;    //DNA检测结果  1---阴性 2--阳性 3--无效
	
	//新增社区id
	private Integer communityDeptId;

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
	//新增登陆名称
	private String loginName;
	//新增类型
	private String type;

	//新增社区名称
	private String commdeptName;
	private String cellPhone;

	private Integer  noChangResultPicture;

	private Integer noCancerResult;//癌症    zongtong  2018-09-07

	private String ruleId;   //ruleid

	private Integer issueType; //rule规则类型

	private String allocationId;


	private String reservationDateToString;


	private Integer useStatus;

	public String getAllocationId() {
		return allocationId;
	}

	public void setAllocationId(String allocationId) {
		this.allocationId = allocationId;
	}

	public String getReservationDateToString() {
		return reservationDateToString;
	}

	public void setReservationDateToString(String reservationDateToString) {
		this.reservationDateToString = reservationDateToString;
	}

	public Integer getIssueType() {
		return issueType;
	}

	public void setIssueType(Integer issueType) {
		this.issueType = issueType;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getType() {
		return type;
	}
	public String getCommdeptName() {
		return commdeptName;
	}

	public void setCommdeptName(String commdeptName) {
		this.commdeptName = commdeptName;
	}
	public void setType(String type) {
		this.type = type;
	}
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

	public Integer getDnaCheckResult() {
		return dnaCheckResult;
	}

	public void setDnaCheckResult(Integer dnaCheckResult) {
		this.dnaCheckResult = dnaCheckResult;
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

	public Integer getNoStoolDNAResult() {
		return noStoolDNAResult;
	}

	public void setNoStoolDNAResult(Integer noStoolDNAResult) {
		this.noStoolDNAResult = noStoolDNAResult;
	}

	public Integer getNotIssueDna() {
		return notIssueDna;
	}

	public void setNotIssueDna(Integer notIssueDna) {
		this.notIssueDna = notIssueDna;
	}

	public Integer getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(Integer useStatus) {
		this.useStatus = useStatus;
	}

	@Override
	public String toString() {
		return "TodoVo{" +
				"name='" + name + '\'' +
				", sid='" + sid + '\'' +
				", phone='" + phone + '\'' +
				", group='" + group + '\'' +
				", pageSize=" + pageSize +
				", pageNo=" + pageNo +
				", deptId='" + deptId + '\'' +
				", startDate='" + startDate + '\'' +
				", endDate='" + endDate + '\'' +
				", riskFactors=" + riskFactors +
				", notEntryFITCode=" + notEntryFITCode +
				", notEntryFITResult=" + notEntryFITResult +
				", notStoolDnaCode=" + notStoolDnaCode +
				", notReserve=" + notReserve +
				", notFinishExamination=" + notFinishExamination +
				", notIssueNotification=" + notIssueNotification +
				", notEntryColonoscopyResult=" + notEntryColonoscopyResult +
				", notEntryPathologyResult=" + notEntryPathologyResult +
				", notEntryNotificationResult=" + notEntryNotificationResult +
				", noBloodSampleResult=" + noBloodSampleResult +
				", noFecesSampleResult=" + noFecesSampleResult +
				", noSalivaSampleResult=" + noSalivaSampleResult +
				", noStoolDNAResult=" + noStoolDNAResult +
				", notIssueDna=" + notIssueDna +
				", communityDeptId=" + communityDeptId +
				", areaDeptId=" + areaDeptId +
				", dnaCode='" + dnaCode + '\'' +
				", personCode='" + personCode + '\'' +
				", operationSource='" + operationSource + '\'' +
				", releaseDate='" + releaseDate + '\'' +
				", releaseDateForSql=" + releaseDateForSql +
				", codeEntryStatus=" + codeEntryStatus +
				", stage=" + stage +
				", sampleType='" + sampleType + '\'' +
				", sampleTypeAll3='" + sampleTypeAll3 + '\'' +
				'}';
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public Integer getNoChangResultPicture() {
		return noChangResultPicture;
	}

	public void setNoChangResultPicture(Integer noChangResultPicture) {
		this.noChangResultPicture = noChangResultPicture;
	}

	public Integer getNoCancerResult() {
		return noCancerResult;
	}

	public void setNoCancerResult(Integer noCancerResult) {
		this.noCancerResult = noCancerResult;
	}
}
