package com.yuntongxun.itsys.gateway.module.pojo;

/**
 * Description:
 *
 * @author LuoKun on 2018-04-17.
 */
public class DoctorInfo {

    private String id;

    private String loginName;

    private String communityDeptId;

    private String areaDeptId;

    private String nationDeptId;

    private String screeningType;

    private String hospitalType;

    public DoctorInfo(String id, String loginName, String communityDeptId, String areaDeptId, String nationDeptId, String screeningType, String hospitalType) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getCommunityDeptId() {
        return communityDeptId;
    }

    public void setCommunityDeptId(String communityDeptId) {
        this.communityDeptId = communityDeptId;
    }

    public String getAreaDeptId() {
        return areaDeptId;
    }

    public void setAreaDeptId(String areaDeptId) {
        this.areaDeptId = areaDeptId;
    }

    public String getNationDeptId() {
        return nationDeptId;
    }

    public void setNationDeptId(String nationDeptId) {
        this.nationDeptId = nationDeptId;
    }

    public String getScreeningType() {
        return screeningType;
    }

    public void setScreeningType(String screeningType) {
        this.screeningType = screeningType;
    }

    public String getHospitalType() {
        return hospitalType;
    }

    public void setHospitalType(String hospitalType) {
        this.hospitalType = hospitalType;
    }
}
