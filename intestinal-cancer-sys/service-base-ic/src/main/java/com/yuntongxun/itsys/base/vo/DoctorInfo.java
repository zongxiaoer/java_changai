package com.yuntongxun.itsys.base.vo;

/**
 * Description: redis医生信息json对应实体
 *
 * @author LuoKun on 2018-04-17.
 */
public class DoctorInfo {

    private Integer id;

    private String loginName;//登录名

    private Integer communityDeptId;//社区医院id

    private Integer areaDeptId;//地区医院id

    private Integer nationDeptId;//国家医院id

    private Integer screeningType;//筛查现场id

    private Integer hospitalType;//医院类型

    private String communityDeptIdInfo;

    public DoctorInfo(Integer id, String loginName, Integer communityDeptId, Integer areaDeptId, Integer nationDeptId, Integer screeningType, Integer hospitalType) {
        this.id = id;
        this.loginName = loginName;
        this.communityDeptId = communityDeptId;
        this.areaDeptId = areaDeptId;
        this.nationDeptId = nationDeptId;
        this.screeningType = screeningType;
        this.hospitalType = hospitalType;
    }

    public DoctorInfo() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getAreaDeptId() {
        return areaDeptId;
    }

    public void setAreaDeptId(Integer areaDeptId) {
        this.areaDeptId = areaDeptId;
    }

    public Integer getNationDeptId() {
        return nationDeptId;
    }

    public void setNationDeptId(Integer nationDeptId) {
        this.nationDeptId = nationDeptId;
    }

    public Integer getScreeningType() {
        return screeningType;
    }

    public void setScreeningType(Integer screeningType) {
        this.screeningType = screeningType;
    }

    public Integer getHospitalType() {
        return hospitalType;
    }

    public void setHospitalType(Integer hospitalType) {
        this.hospitalType = hospitalType;
    }

	@Override
	public String toString() {
		return "DoctorInfo [id=" + id + ", loginName=" + loginName + ", communityDeptId=" + communityDeptId
				+ ", areaDeptId=" + areaDeptId + ", nationDeptId=" + nationDeptId + ", screeningType=" + screeningType
				+ ", hospitalType=" + hospitalType + "]";
	}


    public String getCommunityDeptIdInfo() {
        return communityDeptIdInfo;
    }

    public void setCommunityDeptIdInfo(String communityDeptIdInfo) {
        this.communityDeptIdInfo = communityDeptIdInfo;
    }
}
