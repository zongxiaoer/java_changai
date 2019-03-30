package com.yuntongxun.itsys.base.vo;

/**
 * @author zongt
 * @date 2018/4/21
 */
public class ReserveAllocationVo {

    private Integer id;
    private String areaDeptId;
    private Integer amount;
    private Integer communityDeptId;
    private String  reservationDate;
    private Integer examinationStatus;
    private Integer reserveStatus;


    private String communityName;//社区名称


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAreaDeptId() {
        return areaDeptId;
    }

    public void setAreaDeptId(String areaDeptId) {
        this.areaDeptId = areaDeptId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getCommunityDeptId() {
        return communityDeptId;
    }

    public void setCommunityDeptId(Integer communityDeptId) {
        this.communityDeptId = communityDeptId;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Integer getExaminationStatus() {
        return examinationStatus;
    }

    public void setExaminationStatus(Integer examinationStatus) {
        this.examinationStatus = examinationStatus;
    }

    public Integer getReserveStatus() {
        return reserveStatus;
    }

    public void setReserveStatus(Integer reserveStatus) {
        this.reserveStatus = reserveStatus;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }
}
