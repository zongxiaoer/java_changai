package com.yuntongxun.itsys.base.vo;

public class ColonoscopyQueryResult {

	private String id;
	private String sid;
	private String name;
	private Integer sex;
	private Integer age;
	private String phone;
	private String group;
	private String inGroupDate;
	private Integer overallStatusCy;
	private Integer reserveStatus;
	private String reserveDate;
	private Integer examinationStatus;
	private Integer finishedStatus;
	private String examinationDate;
	private Integer notificationIssueStatus;
	
	//新增翻页字段
	private int pageSize;
	private int pageNo;
	private Integer communityDeptId;   //社区id
	private String loginName;          //登陆名称
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
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

	public String getInGroupDate() {
		return inGroupDate;
	}

	public void setInGroupDate(String inGroupDate) {
		this.inGroupDate = inGroupDate;
	}

	public Integer getOverallStatusCy() {
		return overallStatusCy;
	}

	public void setOverallStatusCy(Integer overallStatusCy) {
		this.overallStatusCy = overallStatusCy;
	}

	public Integer getReserveStatus() {
		return reserveStatus;
	}

	public void setReserveStatus(Integer reserveStatus) {
		this.reserveStatus = reserveStatus;
	}

	public String getReserveDate() {
		return reserveDate;
	}

	public void setReserveDate(String reserveDate) {
		this.reserveDate = reserveDate;
	}

	public Integer getExaminationStatus() {
		return examinationStatus;
	}

	public void setExaminationStatus(Integer examinationStatus) {
		this.examinationStatus = examinationStatus;
	}

	public Integer getFinishedStatus() {
		return finishedStatus;
	}

	public void setFinishedStatus(Integer finishedStatus) {
		this.finishedStatus = finishedStatus;
	}

	public String getExaminationDate() {
		return examinationDate;
	}

	public void setExaminationDate(String examinationDate) {
		this.examinationDate = examinationDate;
	}

	public Integer getNotificationIssueStatus() {
		return notificationIssueStatus;
	}


	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Integer getCommunityDeptId() {
		return communityDeptId;
	}

	public void setCommunityDeptId(Integer communityDeptId) {
		this.communityDeptId = communityDeptId;
	}

	public void setNotificationIssueStatus(Integer notificationIssueStatus) {
		this.notificationIssueStatus = notificationIssueStatus;
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

}
