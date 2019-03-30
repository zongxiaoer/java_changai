/**
 * Project Name:service-base-ic
 * File Name:HospitalReview.java
 * Package Name:com.yuntongxun.itsys.base.po
 * Date:2018年4月16日下午2:34:45
 * Copyright (c) 2018, ty All Rights Reserved.
 *
*/

package com.yuntongxun.itsys.base.po.dto;

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
public class HospitalReviewExport {

	private String sid;
	private String name;
	private String sex;
	private String age;
	private String phone;
	private String areaName;//地区名称
	private String commName;//社区名称
	private String nickName;//创建人昵称
	private String address;//家庭住址
	private String group;//组名称
	private String overallStatusCy;//人员当前状态
	private String inGroupDate;//入组时间
	private String qrsState;//资格审核录入状态
	private String riskState;//危险因素录入状态
	private String score;//危险因素得分
	private String changjing_reserve_status;//肠镜预约状态
	private String changjing_examination_status;//肠镜检查状态
	private String changjing_finished_status;//肠镜完成状态
	private String changjing_result_status;//肠镜结果状态
	private String changjing_pathology_status;//肠镜病理结果状态
	private String changjing_notification_entry_status;//肠镜告知书录入状态
	private String changjing_notification_issue_status;//肠镜告知书发放状态
	private String xueye_collect_status;//生物样本血液录入状态
	private String tuoye_collect_status;//生物样本唾液录入状态
	private String fenbian_collect_status;//生物样本粪便录入状态
	private String dna_code_entry_status;//dna 编码录入状态
	private String dna_gj_dna_check_enter_status;//dna 国家的结果返回状态
	private String dna_dna_check_inform_status;//dna 结果审核状态
	private String dna_dna_community_inform_status;//dna 结果发放状态
	private String dna_other_dna_check_enter_status;//dna 除了国家的结果返回状态
	private String fit_code_entry_status;//fit编码录入状态
	private String fit_insert_status;//fit 结果录入状态






	//2018-08-30 宗曈
    private String item1;
	private String item2;
	private  String item3;

    private String dna_code;
    private String dna_check_result;
    private String fit_code;
    private String fit_code_entry_time;
    private String fit_result;
    private String idCard;

    private String reserve_date;//肠镜预约时间
    private String resultSurveyDate;//肠镜检查时间
    private String fit_result_date;//fit录入时间

	private String no_dna_check_result;//非国家dna返回结果



	//2018-09-21     宗曈
	private String township;
	private String province;
	private String city;
	private String village;
	private String city_other;
	private String area;


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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCommName() {
		return commName;
	}

	public void setCommName(String commName) {
		this.commName = commName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getOverallStatusCy() {
		return overallStatusCy;
	}

	public void setOverallStatusCy(String overallStatusCy) {
		this.overallStatusCy = overallStatusCy;
	}

	public String getInGroupDate() {
		return inGroupDate;
	}

	public void setInGroupDate(String inGroupDate) {
		this.inGroupDate = inGroupDate;
	}

	public String getQrsState() {
		return qrsState;
	}

	public void setQrsState(String qrsState) {
		this.qrsState = qrsState;
	}

	public String getRiskState() {
		return riskState;
	}

	public void setRiskState(String riskState) {
		this.riskState = riskState;
	}

	public String getChangjing_reserve_status() {
		return changjing_reserve_status;
	}

	public void setChangjing_reserve_status(String changjing_reserve_status) {
		this.changjing_reserve_status = changjing_reserve_status;
	}

	public String getChangjing_examination_status() {
		return changjing_examination_status;
	}

	public void setChangjing_examination_status(String changjing_examination_status) {
		this.changjing_examination_status = changjing_examination_status;
	}

	public String getChangjing_finished_status() {
		return changjing_finished_status;
	}

	public void setChangjing_finished_status(String changjing_finished_status) {
		this.changjing_finished_status = changjing_finished_status;
	}

	public String getChangjing_result_status() {
		return changjing_result_status;
	}

	public void setChangjing_result_status(String changjing_result_status) {
		this.changjing_result_status = changjing_result_status;
	}

	public String getChangjing_pathology_status() {
		return changjing_pathology_status;
	}

	public void setChangjing_pathology_status(String changjing_pathology_status) {
		this.changjing_pathology_status = changjing_pathology_status;
	}

	public String getChangjing_notification_entry_status() {
		return changjing_notification_entry_status;
	}

	public void setChangjing_notification_entry_status(String changjing_notification_entry_status) {
		this.changjing_notification_entry_status = changjing_notification_entry_status;
	}

	public String getChangjing_notification_issue_status() {
		return changjing_notification_issue_status;
	}

	public void setChangjing_notification_issue_status(String changjing_notification_issue_status) {
		this.changjing_notification_issue_status = changjing_notification_issue_status;
	}

	public String getXueye_collect_status() {
		return xueye_collect_status;
	}

	public void setXueye_collect_status(String xueye_collect_status) {
		this.xueye_collect_status = xueye_collect_status;
	}

	public String getTuoye_collect_status() {
		return tuoye_collect_status;
	}

	public void setTuoye_collect_status(String tuoye_collect_status) {
		this.tuoye_collect_status = tuoye_collect_status;
	}

	public String getFenbian_collect_status() {
		return fenbian_collect_status;
	}

	public void setFenbian_collect_status(String fenbian_collect_status) {
		this.fenbian_collect_status = fenbian_collect_status;
	}

	public String getDna_code_entry_status() {
		return dna_code_entry_status;
	}

	public void setDna_code_entry_status(String dna_code_entry_status) {
		this.dna_code_entry_status = dna_code_entry_status;
	}

	public String getDna_gj_dna_check_enter_status() {
		return dna_gj_dna_check_enter_status;
	}

	public void setDna_gj_dna_check_enter_status(String dna_gj_dna_check_enter_status) {
		this.dna_gj_dna_check_enter_status = dna_gj_dna_check_enter_status;
	}

	public String getDna_dna_check_inform_status() {
		return dna_dna_check_inform_status;
	}

	public void setDna_dna_check_inform_status(String dna_dna_check_inform_status) {
		this.dna_dna_check_inform_status = dna_dna_check_inform_status;
	}

	public String getDna_dna_community_inform_status() {
		return dna_dna_community_inform_status;
	}

	public void setDna_dna_community_inform_status(String dna_dna_community_inform_status) {
		this.dna_dna_community_inform_status = dna_dna_community_inform_status;
	}

	public String getDna_other_dna_check_enter_status() {
		return dna_other_dna_check_enter_status;
	}

	public void setDna_other_dna_check_enter_status(String dna_other_dna_check_enter_status) {
		this.dna_other_dna_check_enter_status = dna_other_dna_check_enter_status;
	}

	public String getFit_code_entry_status() {
		return fit_code_entry_status;
	}

	public void setFit_code_entry_status(String fit_code_entry_status) {
		this.fit_code_entry_status = fit_code_entry_status;
	}

	public String getFit_insert_status() {
		return fit_insert_status;
	}

	public void setFit_insert_status(String fit_insert_status) {
		this.fit_insert_status = fit_insert_status;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getItem2() {
		return item2;
	}

	public void setItem2(String item2) {
		this.item2 = item2;
	}

	public String getItem3() {
		return item3;
	}

	public void setItem3(String item3) {
		this.item3 = item3;
	}

    public String getDna_code() {
        return dna_code;
    }

    public void setDna_code(String dna_code) {
        this.dna_code = dna_code;
    }

    public String getDna_check_result() {
        return dna_check_result;
    }

    public void setDna_check_result(String dna_check_result) {
        this.dna_check_result = dna_check_result;
    }

    public String getFit_code() {
        return fit_code;
    }

    public void setFit_code(String fit_code) {
        this.fit_code = fit_code;
    }

    public String getFit_code_entry_time() {
        return fit_code_entry_time;
    }

    public void setFit_code_entry_time(String fit_code_entry_time) {
        this.fit_code_entry_time = fit_code_entry_time;
    }

    public String getFit_result() {
        return fit_result;
    }

    public void setFit_result(String fit_result) {
        this.fit_result = fit_result;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getReserve_date() {
        return reserve_date;
    }

    public void setReserve_date(String reserve_date) {
        this.reserve_date = reserve_date;
    }

    public String getResultSurveyDate() {
        return resultSurveyDate;
    }

    public void setResultSurveyDate(String resultSurveyDate) {
        this.resultSurveyDate = resultSurveyDate;
    }

    public String getFit_result_date() {
        return fit_result_date;
    }

    public void setFit_result_date(String fit_result_date) {
        this.fit_result_date = fit_result_date;
    }

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

	public String getNo_dna_check_result() {
		return no_dna_check_result;
	}

	public void setNo_dna_check_result(String no_dna_check_result) {
		this.no_dna_check_result = no_dna_check_result;
	}


	public String getTownship() {
		return township;
	}

	public void setTownship(String township) {
		this.township = township;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getCity_other() {
		return city_other;
	}

	public void setCity_other(String city_other) {
		this.city_other = city_other;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
}

