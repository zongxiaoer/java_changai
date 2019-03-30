package com.yuntongxun.itsys.base.po.dto.message;

import com.yuntongxun.itsys.base.po.HospitalMessageCenterPO;

public class HospitalMessageCenterDto extends HospitalMessageCenterPO {

    private int pageSize;
    private int pageNo;

    private Integer data_id;
    private String  form_type;
    private String sample_type;
    private String courierNumber;

    private String apply_status;
    private String edit_status;
    private String approval_status;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getData_id() {
        return data_id;
    }

    public void setData_id(Integer data_id) {
        this.data_id = data_id;
    }

    public String getForm_type() {
        return form_type;
    }

    public void setForm_type(String form_type) {
        this.form_type = form_type;
    }

    public String getSample_type() {
        return sample_type;
    }

    public void setSample_type(String sample_type) {
        this.sample_type = sample_type;
    }

    public String getApply_status() {
        return apply_status;
    }

    public void setApply_status(String apply_status) {
        this.apply_status = apply_status;
    }

    public String getEdit_status() {
        return edit_status;
    }

    public void setEdit_status(String edit_status) {
        this.edit_status = edit_status;
    }

    public String getApproval_status() {
        return approval_status;
    }

    public void setApproval_status(String approval_status) {
        this.approval_status = approval_status;
    }

    public String getCourierNumber() {
        return courierNumber;
    }

    public void setCourierNumber(String courierNumber) {
        this.courierNumber = courierNumber == null ? null : courierNumber.trim();
    }
}
