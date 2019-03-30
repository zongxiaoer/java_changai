/**
 * Project Name:service-base-ic
 * File Name:HospitalReview.java
 * Package Name:com.yuntongxun.itsys.base.po
 * Date:2018年4月16日下午2:34:45
 * Copyright (c) 2018, ty All Rights Reserved.
 *
*/

package com.yuntongxun.itsys.base.po;

import com.yuntongxun.itsys.base.vo.DoctorInfo;
import com.yuntongxun.itsys.base.vo.SortDto;

import java.util.Date;

/**
 * 受试者资格审核表
 * ClassName:HospitalReview <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年4月16日 下午2:34:45 <br/>
 * @author   ty
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class HospitalReview extends SortDto{
	private Integer id;
	private String name;//姓名
	private String phone;//手机号
	private Integer sex;
	//private Integer deptId;//医院ID
	private Integer communityDeptId;//社区id
	private Integer areaDeptId;//地区医院ID
	private String idCard;//身份证号
	private String sid;//受试者唯一标识，在提交资格审核表时生成
	private Integer age;//50-75岁
	private Date birthDate;//出生日期
	private Integer item1;//实足年龄
	private Integer item2;//是否曾有医生告诉过您患有结直肠癌
	private Integer item3;//是否进行过结肠切除手术
	private Integer item4;//是否正在接受任何癌症相关的治疗
	private Integer item5;//过去5年中是否有过结肠镜、纤维乙状结肠镜、气钡双对比造影，CT仿真结肠镜检查
	private Integer item6;//近1年内是否接受过粪便潜血检测（FOBT）或者粪便DNA检测
	private Integer item7;//是否有下列下消化道疾病或症状提示需要结肠镜进行确诊
	private Integer item8;//是否有其他严重疾病
	private Integer item9;//非本市户籍常驻人口，且本地居住不足3
	private Integer item10;//他/她是否不愿意或者不能自己签署知情同意书
	private String investigator;//调查人
	private Date surveyDate;//调查日期
	private String reviewer;//审核人
	private String address;//家庭地址
	private Date inGroupDate;//入组日期
	private String group;//分组，A，B，C三组
	private Integer groupStatus;//入组状态，1：未入组，2：入组，3：脱组
	private String offGroupReason;//脱组原因
	private Integer stageCy;//年度阶段，1：T0，2：T1,，3：T2，4：T3
	private Integer reviewStatus;//资格审核表状态，1：未通过，2：已通过
	private Integer riskFactorStatus;//危险因素收集状态，1：未收集，2：已收集
	private Integer overallStatusCy;//当前年总体状态，1：正常，2：退出，3：肺癌，4：死亡
	private Integer overallStatusT0;//T0总体状态，1：正常，2：退出，3：肺癌，4：死亡
	private Integer overallStatusT1;//T1总体状态，1：正常，2：退出，3：肺癌，4：死亡
	private Integer overallStatusT2;//T2总体状态，1：正常，2：退出，3：肺癌，4：死亡
	private Integer overallStatusT3;//T3总体状态，1：正常，2：退出，3：肺癌，4：死亡
	private Integer violationPlanStatusCy;//当前年度违反方案状态，1：是，2：否
	private Integer violationPlanStatusT0;//T0年度违反方案状态，1：是，2：否
	private Integer violationPlanStatusT1;//T1年度违反方案状态，1：是，2：否
	private Integer violationPlanStatusT2;//T2年度违反方案状态，1：是，2：否
	private Integer violationPlanStatusT3;//T3年度违反方案状态，1：是，2：否
	private Integer riskLevel;//危险等级，1：低危，2：高危
	private Integer siteId;//筛查现场id
	private Integer deleteFlag;//删除标记，1：未删除，2：已删除
	private Date dateCreated;//创建时间
	private Date updateTime;//更新时间
	private String inGroupDateStart;
	private String inGroupDateEnd;
	private int pageNo;
	private int pageSize;
	private DoctorInfo doctorInfo;//当前登录人信息
	private String depName;//社区名称
	private String createUser;//创建人登录名
	private String loginName;   //登陆人

	private String applyStatus;
	private String editStatus;
	private String approvalStatus;



	private Integer score;
	private String birthDateToString;// 2018-08-27 宗曈
	private Integer number;//2018-08-27 宗曈
	private String surveyDateToString;//调查日期 2018-08-27 宗曈


	//2018-08-30 zongtong  查询

	private Integer resultStatus;//结肠镜结果状态，1：未录入，2：已录入
	private Integer examinationStatus; //结肠镜检查就诊状态，1：未就诊，2：已就诊 0--
	private Integer finishedStatus;//结肠镜完成状态，1：未完成，2：已完成

	private String fitCode;
	private Integer fitResult;//fit结果，1：阴性，2：阳性，3：无效
	private Integer insertStatus;  //录入状态 1：未录入，2：已录入，3待审核


	private Integer dnaResult; // 'DNA检测结果  1---阴性 2--阳性 3--无效',
	private String dnaCode;
	private Integer dnaCheckInformStatus; //-1其他  1-国家审批通过 2国家审批不通过 ' int  default -1   ''DNA检测发放状态''',
	private Integer codeEntryStatus;//'编码录入状态，1：未录入，2：已录入' | 1--'代返回' 2--'已返回',

	private String province;   //省
	private String city;		//市
	private String area;		//区
	private String township;	//乡
	private String village;	//村
	private String cityOther; //其它市



	public Integer getCodeEntryStatus() {
		return codeEntryStatus;
	}

	public void setCodeEntryStatus(Integer codeEntryStatus) {
		this.codeEntryStatus = codeEntryStatus;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
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
	
	public String getInGroupDateStart() {
		return inGroupDateStart;
	}
	public void setInGroupDateStart(String inGroupDateStart) {
		this.inGroupDateStart = inGroupDateStart;
	}
	public String getInGroupDateEnd() {
		return inGroupDateEnd;
	}
	public void setInGroupDateEnd(String inGroupDateEnd) {
		this.inGroupDateEnd = inGroupDateEnd;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public Integer getItem1() {
		return item1;
	}
	public void setItem1(Integer item1) {
		this.item1 = item1;
	}
	public Integer getItem2() {
		return item2;
	}
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public void setItem2(Integer item2) {
		this.item2 = item2;
	}
	public Integer getItem3() {
		return item3;
	}
	public void setItem3(Integer item3) {
		this.item3 = item3;
	}
	public Integer getItem4() {
		return item4;
	}
	public void setItem4(Integer item4) {
		this.item4 = item4;
	}
	public Integer getItem5() {
		return item5;
	}
	public void setItem5(Integer item5) {
		this.item5 = item5;
	}
	public Integer getItem6() {
		return item6;
	}
	public void setItem6(Integer item6) {
		this.item6 = item6;
	}
	public Integer getItem7() {
		return item7;
	}
	public void setItem7(Integer item7) {
		this.item7 = item7;
	}
	public Integer getItem8() {
		return item8;
	}
	public void setItem8(Integer item8) {
		this.item8 = item8;
	}
	public Integer getItem9() {
		return item9;
	}
	public void setItem9(Integer item9) {
		this.item9 = item9;
	}
	public Integer getItem10() {
		return item10;
	}
	public void setItem10(Integer item10) {
		this.item10 = item10;
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
	public String getReviewer() {
		return reviewer;
	}
	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getInGroupDate() {
		return inGroupDate;
	}
	public void setInGroupDate(Date inGroupDate) {
		this.inGroupDate = inGroupDate;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
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
	public Integer getStageCy() {
		return stageCy;
	}
	public void setStageCy(Integer stageCy) {
		this.stageCy = stageCy;
	}
	public Integer getReviewStatus() {
		return reviewStatus;
	}
	public void setReviewStatus(Integer reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
	public Integer getRiskFactorStatus() {
		return riskFactorStatus;
	}
	public void setRiskFactorStatus(Integer riskFactorStatus) {
		this.riskFactorStatus = riskFactorStatus;
	}
	public Integer getOverallStatusCy() {
		return overallStatusCy;
	}
	public void setOverallStatusCy(Integer overallStatusCy) {
		this.overallStatusCy = overallStatusCy;
	}
	public Integer getOverallStatusT0() {
		return overallStatusT0;
	}
	public void setOverallStatusT0(Integer overallStatusT0) {
		this.overallStatusT0 = overallStatusT0;
	}
	public Integer getOverallStatusT1() {
		return overallStatusT1;
	}
	public void setOverallStatusT1(Integer overallStatusT1) {
		this.overallStatusT1 = overallStatusT1;
	}
	public Integer getOverallStatusT2() {
		return overallStatusT2;
	}
	public void setOverallStatusT2(Integer overallStatusT2) {
		this.overallStatusT2 = overallStatusT2;
	}
	public Integer getOverallStatusT3() {
		return overallStatusT3;
	}
	public void setOverallStatusT3(Integer overallStatusT3) {
		this.overallStatusT3 = overallStatusT3;
	}
	public Integer getViolationPlanStatusCy() {
		return violationPlanStatusCy;
	}
	public void setViolationPlanStatusCy(Integer violationPlanStatusCy) {
		this.violationPlanStatusCy = violationPlanStatusCy;
	}
	public Integer getViolationPlanStatusT0() {
		return violationPlanStatusT0;
	}
	public void setViolationPlanStatusT0(Integer violationPlanStatusT0) {
		this.violationPlanStatusT0 = violationPlanStatusT0;
	}
	public Integer getViolationPlanStatusT1() {
		return violationPlanStatusT1;
	}
	public void setViolationPlanStatusT1(Integer violationPlanStatusT1) {
		this.violationPlanStatusT1 = violationPlanStatusT1;
	}
	public Integer getViolationPlanStatusT2() {
		return violationPlanStatusT2;
	}
	public void setViolationPlanStatusT2(Integer violationPlanStatusT2) {
		this.violationPlanStatusT2 = violationPlanStatusT2;
	}
	public Integer getViolationPlanStatusT3() {
		return violationPlanStatusT3;
	}
	public void setViolationPlanStatusT3(Integer violationPlanStatusT3) {
		this.violationPlanStatusT3 = violationPlanStatusT3;
	}
	public Integer getRiskLevel() {
		return riskLevel;
	}
	public void setRiskLevel(Integer riskLevel) {
		this.riskLevel = riskLevel;
	}
	public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
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

	public DoctorInfo getDoctorInfo() {
		return doctorInfo;
	}

	public void setDoctorInfo(DoctorInfo doctorInfo) {
		this.doctorInfo = doctorInfo;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
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

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "HospitalReview [id=" + id + ", name=" + name + ", phone=" + phone + ", sex=" + sex
				+ ", communityDeptId=" + communityDeptId + ", areaDeptId=" + areaDeptId + ", idCard=" + idCard
				+ ", sid=" + sid + ", age=" + age + ", birthDate=" + birthDate + ", item1=" + item1 + ", item2=" + item2
				+ ", item3=" + item3 + ", item4=" + item4 + ", item5=" + item5 + ", item6=" + item6 + ", item7=" + item7
				+ ", item8=" + item8 + ", item9=" + item9 + ", item10=" + item10 + ", investigator=" + investigator
				+ ", surveyDate=" + surveyDate + ", reviewer=" + reviewer + ", address=" + address + ", inGroupDate="
				+ inGroupDate + ", group=" + group + ", groupStatus=" + groupStatus + ", offGroupReason="
				+ offGroupReason + ", stageCy=" + stageCy + ", reviewStatus=" + reviewStatus + ", riskFactorStatus="
				+ riskFactorStatus + ", overallStatusCy=" + overallStatusCy + ", overallStatusT0=" + overallStatusT0
				+ ", overallStatusT1=" + overallStatusT1 + ", overallStatusT2=" + overallStatusT2 + ", overallStatusT3="
				+ overallStatusT3 + ", violationPlanStatusCy=" + violationPlanStatusCy + ", violationPlanStatusT0="
				+ violationPlanStatusT0 + ", violationPlanStatusT1=" + violationPlanStatusT1
				+ ", violationPlanStatusT2=" + violationPlanStatusT2 + ", violationPlanStatusT3="
				+ violationPlanStatusT3 + ", riskLevel=" + riskLevel + ", siteId=" + siteId + ", deleteFlag="
				+ deleteFlag + ", dateCreated=" + dateCreated + ", updateTime=" + updateTime + ", inGroupDateStart="
				+ inGroupDateStart + ", inGroupDateEnd=" + inGroupDateEnd + ", pageNo=" + pageNo + ", pageSize="
				+ pageSize + "]";
	}

	public String getBirthDateToString() {
		return birthDateToString;
	}

	public void setBirthDateToString(String birthDateToString) {
		this.birthDateToString = birthDateToString;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getSurveyDateToString() {
		return surveyDateToString;
	}

	public void setSurveyDateToString(String surveyDateToString) {
		this.surveyDateToString = surveyDateToString;
	}

	public Integer getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(Integer resultStatus) {
		this.resultStatus = resultStatus;
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

	public String getFitCode() {
		return fitCode;
	}

	public void setFitCode(String fitCode) {
		this.fitCode = fitCode;
	}

	public Integer getFitResult() {
		return fitResult;
	}

	public void setFitResult(Integer fitResult) {
		this.fitResult = fitResult;
	}

	public Integer getInsertStatus() {
		return insertStatus;
	}

	public void setInsertStatus(Integer insertStatus) {
		this.insertStatus = insertStatus;
	}

	public Integer getDnaResult() {
		return dnaResult;
	}

	public void setDnaResult(Integer dnaResult) {
		this.dnaResult = dnaResult;
	}

	public String getDnaCode() {
		return dnaCode;
	}

	public void setDnaCode(String dnaCode) {
		this.dnaCode = dnaCode;
	}

	public Integer getDnaCheckInformStatus() {
		return dnaCheckInformStatus;
	}

	public void setDnaCheckInformStatus(Integer dnaCheckInformStatus) {
		this.dnaCheckInformStatus = dnaCheckInformStatus;
	}

	//  zhaoli  添加省市县等6级  字段信息

	public String getProvince() {
		return province;
	}

	public String getCity() {
		return city;
	}

	public String getArea() {
		return area;
	}

	public String getTownship() {
		return township;
	}

	public String getVillage() {
		return village;
	}

	public String getCityOther() {
		return cityOther;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setTownship(String township) {
		this.township = township;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public void setCityOther(String cityOther) {
		this.cityOther = cityOther;
	}
}

