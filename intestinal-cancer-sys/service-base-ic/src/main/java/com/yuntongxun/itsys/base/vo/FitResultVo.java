package com.yuntongxun.itsys.base.vo;

import java.util.Date;

public class FitResultVo {


	// 检索条件
	private String sid;
	private String name;
	private String phone;
	private String group;
	private Integer codeEntryStatus;
	private Integer insertStatus;
	private Integer result;

	private int pageSize;
	private int pageNo;

	private Integer id;

	private String releasePersonCode;
	private String releaseDate;
	private String fitCode;
	private Date releaseDateForSql;
	private Date resultDateForSql;
	private String resultDate;
	private Integer resultStatus;
	private Integer upLineValue;
	private Integer downLineValue;
	private String noResonResult;
	private Integer  areaDeptId;
	private Integer communityDeptId;
	private Integer stage;
	private String operationSource;
	private String inTenMin;
	private String loginName;

	private String applyStatus;

	private String editStatus;

	private String approvalStatus;

	private String	editoperationSource;

	private Integer	operationSourceId;

	private String pathUrl;


	private Integer number;

	private String nickName;//2018-8-28 zongtong

	private String depName;//2018-9-03 zongtong
	private String areaName;//2018-9-03 zongtong


	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid == null ? null : sid.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();

	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Integer getCodeEntryStatus() {
		return codeEntryStatus;
	}

	public void setCodeEntryStatus(Integer codeEntryStatus) {
		this.codeEntryStatus = codeEntryStatus;
	}

	public Integer getInsertStatus() {
		return insertStatus;
	}

	public void setInsertStatus(Integer insertStatus) {
		this.insertStatus = insertStatus;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReleasePersonCode() {
		return releasePersonCode;
	}

	public void setReleasePersonCode(String releasePersonCode) {
		this.releasePersonCode = releasePersonCode;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getFitCode() {
		return fitCode;
	}

	public void setFitCode(String fitCode) {
		this.fitCode = fitCode;
	}

	public Date getReleaseDateForSql() {
		return releaseDateForSql;
	}

	public void setReleaseDateForSql(Date releaseDateForSql) {
		this.releaseDateForSql = releaseDateForSql;
	}

	public Date getResultDateForSql() {
		return resultDateForSql;
	}

	public void setResultDateForSql(Date resultDateForSql) {
		this.resultDateForSql = resultDateForSql;
	}

	public String getResultDate() {
		return resultDate;
	}

	public void setResultDate(String resultDate) {
		this.resultDate = resultDate;
	}

	public Integer getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(Integer resultStatus) {
		this.resultStatus = resultStatus;
	}

	public Integer getUpLineValue() {
		return upLineValue;
	}

	public void setUpLineValue(Integer upLineValue) {
		this.upLineValue = upLineValue;
	}

	public Integer getDownLineValue() {
		return downLineValue;
	}

	public void setDownLineValue(Integer downLineValue) {
		this.downLineValue = downLineValue;
	}

	public String getNoResonResult() {
		return noResonResult;
	}

	public void setNoResonResult(String noResonResult) {
		this.noResonResult = noResonResult;
	}

	public Integer getAreaDeptId() {
		return areaDeptId;
	}

	public void setAreaDeptId(Integer areaDeptId) {
		this.areaDeptId = areaDeptId;
	}

	public Integer getCommunityDeptId() {
		return communityDeptId;
	}

	public void setCommunityDeptId(Integer communityDeptId) {
		this.communityDeptId = communityDeptId;
	}

	public Integer getStage() {
		return stage;
	}

	public void setStage(Integer stage) {
		this.stage = stage;
	}

	public String getOperationSource() {
		return operationSource;
	}

	public void setOperationSource(String operationSource) {
		this.operationSource = operationSource;
	}

	public String getInTenMin() {
		return inTenMin;
	}

	public void setInTenMin(String inTenMin) {
		this.inTenMin = inTenMin;
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

	public String getEditoperationSource() {
		return editoperationSource;
	}

	public void setEditoperationSource(String editoperationSource) {
		this.editoperationSource = editoperationSource;
	}

	public Integer getOperationSourceId() {
		return operationSourceId;
	}

	public void setOperationSourceId(Integer operationSourceId) {
		this.operationSourceId = operationSourceId;
	}

	public String getPathUrl() {
		return pathUrl;
	}

	public void setPathUrl(String pathUrl) {
		this.pathUrl = pathUrl;
	}

	@Override
	public String toString() {
		return "FitResultVo{" +
				"sid='" + sid + '\'' +
				", name='" + name + '\'' +
				", phone='" + phone + '\'' +
				", group='" + group + '\'' +
				", codeEntryStatus=" + codeEntryStatus +
				", insertStatus=" + insertStatus +
				", result=" + result +
				", pageSize=" + pageSize +
				", pageNo=" + pageNo +
				", id=" + id +
				", releasePersonCode='" + releasePersonCode + '\'' +
				", releaseDate='" + releaseDate + '\'' +
				", fitCode='" + fitCode + '\'' +
				", releaseDateForSql=" + releaseDateForSql +
				", resultDateForSql=" + resultDateForSql +
				", resultDate='" + resultDate + '\'' +
				", resultStatus=" + resultStatus +
				", upLineValue=" + upLineValue +
				", downLineValue=" + downLineValue +
				", noResonResult='" + noResonResult + '\'' +
				", areaDeptId=" + areaDeptId +
				", communityDeptId=" + communityDeptId +
				", stage=" + stage +
				", operationSource='" + operationSource + '\'' +
				", inTenMin='" + inTenMin + '\'' +
				", loginName='" + loginName + '\'' +
				", applyStatus='" + applyStatus + '\'' +
				", editStatus='" + editStatus + '\'' +
				", approvalStatus='" + approvalStatus + '\'' +
				", editoperationSource='" + editoperationSource + '\'' +
				", operationSourceId='" + operationSourceId + '\'' +
				'}';
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
}
