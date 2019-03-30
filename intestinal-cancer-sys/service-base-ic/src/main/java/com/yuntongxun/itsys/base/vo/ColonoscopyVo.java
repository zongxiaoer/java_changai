package com.yuntongxun.itsys.base.vo;

import java.util.Date;

public class ColonoscopyVo {

	private Integer id;
	private String sid;
	private String name;
	private String phone;
	private String group;
	private String reserveStatus;
	private String examinationStatus;
	private String finishedStatus;
	private String notificationIssueStatus;
	private String notificationEntryStatus;//1：未录入，2：已录入
	private String nickName;

	private String resultId;
	private String pathologyId;
	private String notificationId;
	private String notificationIssueMode;
	private String notificationIssueWorkerCode;
	private String notificationIssueNote;
	private String notificationIssueDate;
	private String resultStatus;
	private String pathologyStatus;

	private Integer communityDeptId;
	private Integer areaDeptId;

	//预约入口
	private Integer bookingEntrance;

	private int pageNo;
	private int pageSize;

	private Integer allocationId;
	private Integer colonoscopyRecordId;
	//结肠镜结果状态，1：未录入，2：已录入
	private Integer resultState;
	//删除标记，1：未删除，2：已删除；
	private Integer deleteFlag;
	
	//新增当前年总体状态，1：正常，2：退出，3：肺癌，4：死亡
	private Integer overallStatusCy;
	//结肠镜检查预约日期
	private Date reserveDate;
	//就诊时间
	private Date examinationDate;
	//性别
	private Integer sex;
	//年龄
	private Integer age;
	//入组时间
	private Date inGroupDate;
	//社区名称
	private String depName;
	//地区名称
	private String areaName;
	//创建人
	private String createUser;
	//登陆名称
	private String loginName;

	private String signState;//签到状态  1--未签到  2---签到


	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public Integer getResultState() {
		return resultState;
	}
	public void setResultState(Integer resultState) {
		this.resultState = resultState;
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
	public String getReserveStatus() {
		return reserveStatus;
	}
	public void setReserveStatus(String reserveStatus) {
		this.reserveStatus = reserveStatus;
	}
	public String getExaminationStatus() {
		return examinationStatus;
	}
	public void setExaminationStatus(String examinationStatus) {
		this.examinationStatus = examinationStatus;
	}
	public String getFinishedStatus() {
		return finishedStatus;
	}
	public void setFinishedStatus(String finishedStatus) {
		this.finishedStatus = finishedStatus;
	}
	public String getNotificationIssueStatus() {
		return notificationIssueStatus;
	}
	public void setNotificationIssueStatus(String notificationIssueStatus) {
		this.notificationIssueStatus = notificationIssueStatus;
	}
	public String getNotificationEntryStatus() {
		return notificationEntryStatus;
	}
	public void setNotificationEntryStatus(String notificationEntryStatus) {
		this.notificationEntryStatus = notificationEntryStatus;
	}
	public String getResultId() {
		return resultId;
	}
	public void setResultId(String resultId) {
		this.resultId = resultId;
	}
	public String getPathologyId() {
		return pathologyId;
	}
	public void setPathologyId(String pathologyId) {
		this.pathologyId = pathologyId;
	}
	public String getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}
	public String getNotificationIssueMode() {
		return notificationIssueMode;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public void setNotificationIssueMode(String notificationIssueMode) {
		this.notificationIssueMode = notificationIssueMode;
	}
	public String getNotificationIssueWorkerCode() {
		return notificationIssueWorkerCode;
	}
	public void setNotificationIssueWorkerCode(String notificationIssueWorkerCode) {
		this.notificationIssueWorkerCode = notificationIssueWorkerCode;
	}
	public String getNotificationIssueNote() {
		return notificationIssueNote;
	}
	public void setNotificationIssueNote(String notificationIssueNote) {
		this.notificationIssueNote = notificationIssueNote;
	}
	public String getNotificationIssueDate() {
		return notificationIssueDate;
	}
	public void setNotificationIssueDate(String notificationIssueDate) {
		this.notificationIssueDate = notificationIssueDate;
	}
	public String getResultStatus() {
		return resultStatus;
	}
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}
	public String getPathologyStatus() {
		return pathologyStatus;
	}
	public void setPathologyStatus(String pathologyStatus) {
		this.pathologyStatus = pathologyStatus;
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
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getAllocationId() {
		return allocationId;
	}
	public void setAllocationId(Integer allocationId) {
		this.allocationId = allocationId;
	}
	public Integer getColonoscopyRecordId() {
		return colonoscopyRecordId;
	}
	public void setColonoscopyRecordId(Integer colonoscopyRecordId) {
		this.colonoscopyRecordId = colonoscopyRecordId;
	}

	public Integer getBookingEntrance() {
		return bookingEntrance;
	}

	public void setBookingEntrance(Integer bookingEntrance) {
		this.bookingEntrance = bookingEntrance;
	}
	public Integer getOverallStatusCy() {
		return overallStatusCy;
	}
	public void setOverallStatusCy(Integer overallStatusCy) {
		this.overallStatusCy = overallStatusCy;
	}
	public Date getReserveDate() {
		return reserveDate;
	}
	public void setReserveDate(Date reserveDate) {
		this.reserveDate = reserveDate;
	}
	public Date getExaminationDate() {
		return examinationDate;
	}
	public void setExaminationDate(Date examinationDate) {
		this.examinationDate = examinationDate;
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
	public Date getInGroupDate() {
		return inGroupDate;
	}
	public void setInGroupDate(Date inGroupDate) {
		this.inGroupDate = inGroupDate;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getSignState() {
		return signState;
	}

	public void setSignState(String signState) {
		this.signState = signState;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
