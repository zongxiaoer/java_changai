package com.yuntongxun.itsys.base.po.dto.quitlog;

import com.yuntongxun.itsys.base.po.PageInfo;

/**
 * @author wp_sp
 * @date 2018/5/27
 */
public class QuitResultDpo extends PageInfo {
    /**
     * 流水ID
     */
    private String id;
    /**
     * 受试者标识
     */
    private String sid;
    /**
     * 受试者名称
     */
    private String name;
    /**
     * 受试者性别
     */
    private Integer sex;
    /**
     * 受试者年龄
     */
    private Integer age;
    /**
     * 受试者手机号
     */
    private String phone;
    /**
     * 受试者分组
     */
    private String group;
    /**
     * 受试者入组开始时间
     */
    private String inGroupDateStart;

    /**
     * 受试者入组结束时间
     */
    private String inGroupDateEND;
    /**
     * 受试者退出开始时间
     */
    private String quitGroupDateStart;
    /**
     * 受试者退出结束时间
     */
    private String quitGroupDateEnd;
    /**
     * 受试者退出理由
     */
    private String offGroupReason;
    /**
     * 受试者D2表单录入状态
     */
    private Integer inD2Status;

    /**
     * 所属社区ID
     */
    private Integer communityDeptId;

    /**
     * 所属地区ID
     */
    private Integer areaDeptId;

    /**
     * 总体状态
     */
    private Integer overallStatusCy;
    //登陆名称
    private String loginName;

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

    public String getInGroupDateStart() {
        return inGroupDateStart;
    }

    public void setInGroupDateStart(String inGroupDateStart) {
        this.inGroupDateStart = inGroupDateStart;
    }

    public String getInGroupDateEND() {
        return inGroupDateEND;
    }

    public void setInGroupDateEND(String inGroupDateEND) {
        this.inGroupDateEND = inGroupDateEND;
    }

    public String getQuitGroupDateStart() {
        return quitGroupDateStart;
    }

    public void setQuitGroupDateStart(String quitGroupDateStart) {
        this.quitGroupDateStart = quitGroupDateStart;
    }

    public String getQuitGroupDateEnd() {
        return quitGroupDateEnd;
    }

    public void setQuitGroupDateEnd(String quitGroupDateEnd) {
        this.quitGroupDateEnd = quitGroupDateEnd;
    }

    public String getOffGroupReason() {
        return offGroupReason;
    }

    public void setOffGroupReason(String offGroupReason) {
        this.offGroupReason = offGroupReason;
    }

    public Integer getInD2Status() {
        return inD2Status;
    }

    public void setInD2Status(Integer inD2Status) {
        this.inD2Status = inD2Status;
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

    public Integer getOverallStatusCy() {
        return overallStatusCy;
    }

    public void setOverallStatusCy(Integer overallStatusCy) {
        this.overallStatusCy = overallStatusCy;
    }

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
}
