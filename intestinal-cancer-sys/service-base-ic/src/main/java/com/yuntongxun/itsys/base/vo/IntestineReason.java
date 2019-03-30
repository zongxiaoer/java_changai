package com.yuntongxun.itsys.base.vo;

/**
 * Description: 退出研究请求包体
 *
 * @author LuoKun on 2018-04-16.
 */
public class IntestineReason {

    private String[] sid;

    private String[] reason;

    private String quitDate;

    private Integer departmentId;

    private Integer departmentPId;

    public String[] getSid() {
        return sid;
    }

    public void setSid(String[] sid) {
        this.sid = sid;
    }

    public String[] getReason() {
        return reason;
    }

    public void setReason(String[] reason) {
        this.reason = reason;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getDepartmentPId() {
        return departmentPId;
    }

    public void setDepartmentPId(Integer departmentPId) {
        this.departmentPId = departmentPId;
    }

    public String getQuitDate() {
        return quitDate;
    }

    public void setQuitDate(String quitDate) {
        this.quitDate = quitDate;
    }
}
