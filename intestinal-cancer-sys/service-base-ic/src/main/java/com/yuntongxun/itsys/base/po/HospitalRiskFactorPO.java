/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: HospitalRiskFactorPO
 * Author:   lcy
 * Date:     2018/8/27 15:06
 * History:
 * <author>          <time>                <version>
 *     lcy         2018/8/27 15:06           v1.0.0
 */
package com.yuntongxun.itsys.base.po;

import java.io.Serializable;

/**
 * 危险因素PO
 *
 * @author lcy
 * @create 2018/8/27
 * @since v1.0.0
 */
public class HospitalRiskFactorPO extends HospitalRiskFactor implements Serializable {

    private Integer siteId;
    private String name;
    private Integer sex;
    private  String idCard;
    private String phone;
    private String address;
    private String surveyDate;
    private Integer overallStatusCy;

    //zongtong 2018-09-20
    private String township;
    private String province;
    private String city;
    private String village;
    private String city_other;
    private String area;


    public Integer getSiteId() {
        return siteId;
    }

    public String getName() {
        return name;
    }

    public Integer getSex() {
        return sex;
    }

    public String getIdCard() {
        return idCard;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(String surveyDate) {
        this.surveyDate = surveyDate;
    }

    public Integer getOverallStatusCy() {
        return overallStatusCy;
    }

    public void setOverallStatusCy(Integer overallStatusCy) {
        this.overallStatusCy = overallStatusCy;
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
