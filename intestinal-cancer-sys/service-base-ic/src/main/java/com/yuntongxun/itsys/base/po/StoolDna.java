/**
 * Project Name:service-base-ic
 * File Name:StoolDna.java
 * Package Name:com.yuntongxun.itsys.base.po
 * Date:2018年4月17日下午8:15:45
 * Copyright (c) 2018, ty All Rights Reserved.
 *
*/

package com.yuntongxun.itsys.base.po;

import java.util.Date;

/**
 * 粪便DNA表
 * ClassName:StoolDna <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年4月17日 下午8:15:45 <br/>
 * @author   ty
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class StoolDna extends HospitalStoolDnaPO{

	private String name;//受试者姓名
	private String phone;//受试者手机号
	private String group;//分组
	private Integer overall_status_cy;//年度状态
	private String communityName;//所属社区名称
	private Integer dna_check_inform_status;//国家审核状态
	private Integer dna_community_inform_status;//社区发放状
	private Integer dna_check_result;//DNA检测结果  1---阳性 2--阴性3--无效
	private Integer dna_community_inform_mode;//dna告知书发放方式，1：受试者/家属到社区中心自取；2：社区工作人员入户递送；3：邻居从社区中心捎带取走；4：受试者/家属到医院自取；5：其它，请备注；
	private String dna_community_inform_worker_code;//告知书发放工作人员编码
	private String	dna_community_inform_note;//
	private String dna_community_inform_work_time;//告知书发放时间 业务真实时间
	private String editoperationSource;
	private Integer operationSourceId;


	private int pageNo;
	private int pageSize;
	private String dnaCheckEnterStartDate;//DNA检测结果开始时间
	private String dnaCheckEnterEndDate;//DNA检测结果结束时间
	private String loginName;   //登陆名称
	private Integer resultStatus;//肠镜录入状态
	private  String dnaCheckEnterDateForSql;
	private String nickName;

	private String applyStatus;
	private String editStatus;
	private String approvalStatus;

	//2018-09-03 zongtong
	private String depName;
	private String areaName;


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

	public Integer getOverall_status_cy() {
		return overall_status_cy;
	}

	public void setOverall_status_cy(Integer overall_status_cy) {
		this.overall_status_cy = overall_status_cy;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public Integer getDna_check_inform_status() {
		return dna_check_inform_status;
	}

	public void setDna_check_inform_status(Integer dna_check_inform_status) {
		this.dna_check_inform_status = dna_check_inform_status;
	}

	public Integer getDna_community_inform_status() {
		return dna_community_inform_status;
	}

	public void setDna_community_inform_status(Integer dna_community_inform_status) {
		this.dna_community_inform_status = dna_community_inform_status;
	}

	public Integer getDna_check_result() {
		return dna_check_result;
	}

	public void setDna_check_result(Integer dna_check_result) {
		this.dna_check_result = dna_check_result;
	}

	public Integer getDna_community_inform_mode() {
		return dna_community_inform_mode;
	}

	public void setDna_community_inform_mode(Integer dna_community_inform_mode) {
		this.dna_community_inform_mode = dna_community_inform_mode;
	}

	public String getDna_community_inform_worker_code() {
		return dna_community_inform_worker_code;
	}

	public void setDna_community_inform_worker_code(String dna_community_inform_worker_code) {
		this.dna_community_inform_worker_code = dna_community_inform_worker_code;
	}

	public String getDna_community_inform_note() {
		return dna_community_inform_note;
	}

	public void setDna_community_inform_note(String dna_community_inform_note) {
		this.dna_community_inform_note = dna_community_inform_note;
	}

	public String getDna_community_inform_work_time() {
		return dna_community_inform_work_time;
	}

	public void setDna_community_inform_work_time(String dna_community_inform_work_time) {
		this.dna_community_inform_work_time = dna_community_inform_work_time;
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

	public String getDnaCheckEnterStartDate() {
		return dnaCheckEnterStartDate;
	}

	public void setDnaCheckEnterStartDate(String dnaCheckEnterStartDate) {
		this.dnaCheckEnterStartDate = dnaCheckEnterStartDate;
	}

	public String getDnaCheckEnterEndDate() {
		return dnaCheckEnterEndDate;
	}

	public void setDnaCheckEnterEndDate(String dnaCheckEnterEndDate) {
		this.dnaCheckEnterEndDate = dnaCheckEnterEndDate;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
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

	public Integer getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(Integer resultStatus) {
		this.resultStatus = resultStatus;
	}

	public String getDnaCheckEnterDateForSql() {
		return dnaCheckEnterDateForSql;
	}

	public void setDnaCheckEnterDateForSql(String dnaCheckEnterDateForSql) {
		this.dnaCheckEnterDateForSql = dnaCheckEnterDateForSql;
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

