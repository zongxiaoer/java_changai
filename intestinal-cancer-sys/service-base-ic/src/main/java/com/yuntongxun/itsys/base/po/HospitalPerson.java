/**
 * Project Name:service-base-yl
 * File Name:HospitalPerson.java
 * Package Name:com.yuntongxun.itsys.base.po
 * Date:2018年4月9日下午5:48:00
 * Copyright (c) 2018, ty All Rights Reserved.
 *
*/

package com.yuntongxun.itsys.base.po;

import java.math.BigDecimal;
import java.util.Date;


/**
 * ClassName:HospitalPerson <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年4月9日 下午5:48:00 <br/>
 * @author   ty
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class HospitalPerson {
	private Integer id;
	private String sid;//系统对受试者唯一标识
	private String name;//姓名
	private Integer sex;//性别，1：男，2：女
	private Integer age;//年龄
	private Date birth;//出生日期
	private Integer race;//民族，1：汉族，2：蒙古族，3：回族，4：满族，5：壮族，6：维吾尔族，7：哈萨，8：其他，9：未知
	private String raceOther;//对民族 “其他”的说明
	private String idCard;//身份证号
	private String telephone;//固定电话
	private String cellPhone;//手机
	private String contactTel;//联系人固定电话
	private String contactCellPhone;//联系人手机
	private String contactRelationship;//与受试者关系
	private String homeAddr;//家庭地址
	private String changZhuAddr;//常住地址
	private String workCompany;//工作单位
	private String investigator;//调查员姓名
	private Date surveyDate;//调查日期
	private Date inGroupDate;//入组日期
	private Integer groupStatus;//入组状态，1：正常，2：脱组
	private String offGroupReason;//脱组原因
	private String group;//分组，A，B，C三组
	private String deptId;//医院唯一标识
	private Integer overallStatus;//总体状态，1：正常，2：退出，3：终点事件，4：违反方案
	private Integer deleteFlag;//删除标记，1：未删除，2：已删除；
	private Date dateCreated;//创建时间
	private Date updateTime;//更新时间
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
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public Integer getRace() {
		return race;
	}
	public void setRace(Integer race) {
		this.race = race;
	}
	public String getRaceOther() {
		return raceOther;
	}
	public void setRaceOther(String raceOther) {
		this.raceOther = raceOther;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getCellPhone() {
		return cellPhone;
	}
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
	public String getContactTel() {
		return contactTel;
	}
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	public String getContactCellPhone() {
		return contactCellPhone;
	}
	public void setContactCellPhone(String contactCellPhone) {
		this.contactCellPhone = contactCellPhone;
	}
	public String getContactRelationship() {
		return contactRelationship;
	}
	public void setContactRelationship(String contactRelationship) {
		this.contactRelationship = contactRelationship;
	}
	public String getHomeAddr() {
		return homeAddr;
	}
	public void setHomeAddr(String homeAddr) {
		this.homeAddr = homeAddr;
	}
	public String getChangZhuAddr() {
		return changZhuAddr;
	}
	public void setChangZhuAddr(String changZhuAddr) {
		this.changZhuAddr = changZhuAddr;
	}
	public String getWorkCompany() {
		return workCompany;
	}
	public void setWorkCompany(String workCompany) {
		this.workCompany = workCompany;
	}
	public String getInvestigator() {
		return investigator;
	}
	public void setInvestigator(String investigator) {
		this.investigator = investigator;
	}
	public Date getSurveyDate() {
		return surveyDate;
	}
	public void setSurveyDate(Date surveyDate) {
		this.surveyDate = surveyDate;
	}
	public Date getInGroupDate() {
		return inGroupDate;
	}
	public void setInGroupDate(Date inGroupDate) {
		this.inGroupDate = inGroupDate;
	}
	public Integer getGroupStatus() {
		return groupStatus;
	}
	public void setGroupStatus(Integer groupStatus) {
		this.groupStatus = groupStatus;
	}
	public String getOffGroupReason() {
		return offGroupReason;
	}
	public void setOffGroupReason(String offGroupReason) {
		this.offGroupReason = offGroupReason;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public Integer getOverallStatus() {
		return overallStatus;
	}
	public void setOverallStatus(Integer overallStatus) {
		this.overallStatus = overallStatus;
	}
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
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
	@Override
	public String toString() {
		return "HospitalPerson [id=" + id + ", sid=" + sid + ", name=" + name + ", sex=" + sex + ", age=" + age
				+ ", birth=" + birth + ", race=" + race + ", raceOther=" + raceOther + ", idCard=" + idCard
				+ ", telephone=" + telephone + ", cellPhone=" + cellPhone + ", contactTel=" + contactTel
				+ ", contactCellPhone=" + contactCellPhone + ", contactRelationship=" + contactRelationship
				+ ", homeAddr=" + homeAddr + ", changZhuAddr=" + changZhuAddr + ", workCompany=" + workCompany
				+ ", investigator=" + investigator + ", surveyDate=" + surveyDate + ", inGroupDate=" + inGroupDate
				+ ", groupStatus=" + groupStatus + ", offGroupReason=" + offGroupReason + ", group=" + group
				+ ", deptId=" + deptId + ", overallStatus=" + overallStatus + ", deleteFlag=" + deleteFlag
				+ ", dateCreated=" + dateCreated + ", updateTime=" + updateTime + "]";
	}
	
	
}

