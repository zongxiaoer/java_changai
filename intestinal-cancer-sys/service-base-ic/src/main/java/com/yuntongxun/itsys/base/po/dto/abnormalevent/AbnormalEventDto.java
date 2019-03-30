package com.yuntongxun.itsys.base.po.dto.abnormalevent;

import com.yuntongxun.itsys.base.po.PageInfo;

/**
 * @author wp_sp
 * @date 2018/6/5
 */
public class AbnormalEventDto extends PageInfo {
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
     * 所属社区ID
     */
    private Integer communityDeptId;

    /**
     * 所属地区ID
     */
    private Integer areaDeptId;

    private Integer overallStatusCy;

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
}
