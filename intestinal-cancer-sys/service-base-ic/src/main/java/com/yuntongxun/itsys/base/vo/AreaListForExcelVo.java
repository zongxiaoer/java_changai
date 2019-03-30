package com.yuntongxun.itsys.base.vo;

public class AreaListForExcelVo {
	//社区名称
	private String commName;
	//预约时间
	private String period;
	//放号人数
	private Integer amount;
	//已预约人数
	private Integer alSums1;
	//已检查人数
	private Integer alSums2;
	//sid
	private String sid;
	//姓名
	private String name;
	//性别
	private Integer sex;
	//年龄
	private Integer age;
	//手机号码
	private String phone;
	//分组
	private String group;
	//年度状态
	private Integer overallStatusCy;
	//预约状态
	private Integer reserveStatus;
	//检查状态
	private Integer examinationStatus;
	//完成状态
    private Integer finishedStatus;
    //社区id
    private Integer communityDeptId;
    //id
    private Integer id;
    //创建者
    private String nickName;
    
	public String getCommName() {
		return commName;
	}
	public void setCommName(String commName) {
		this.commName = commName;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getAlSums1() {
		return alSums1;
	}
	public void setAlSums1(Integer alSums1) {
		this.alSums1 = alSums1;
	}
	public Integer getAlSums2() {
		return alSums2;
	}
	public void setAlSums2(Integer alSums2) {
		this.alSums2 = alSums2;
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
	public Integer getExaminationStatus() {
		return examinationStatus;
	}
	public void setExaminationStatus(Integer examinationStatus) {
		this.examinationStatus = examinationStatus;
	}
	public Integer getFinishedStatus() {
		return finishedStatus;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public void setFinishedStatus(Integer finishedStatus) {
		this.finishedStatus = finishedStatus;
	}
	public Integer getCommunityDeptId() {
		return communityDeptId;
	}
	public void setCommunityDeptId(Integer communityDeptId) {
		this.communityDeptId = communityDeptId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
