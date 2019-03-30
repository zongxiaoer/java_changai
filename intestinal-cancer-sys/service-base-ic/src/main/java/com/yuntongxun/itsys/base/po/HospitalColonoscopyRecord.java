package com.yuntongxun.itsys.base.po;

import java.util.Date;

/**
 * Description: 结肠镜检查记录表 hospital_colonoscopy_record
 *
 * @author LuoKun on 2018-04-16.
 */
public class HospitalColonoscopyRecord {

    private Integer id;

    private String sid;
    private Integer stage;
    private String allocation_id;
    private Integer reserve_id;
    private Integer reserve_status;
    private Date reserve_date;
    private Integer reserve_operator;
    private Integer examination_status;
    private Date examination_date;
    private Date examination_check_date;
    private Integer examination_operator;
    private Integer finished_status;
    private Integer result_id;
    private Integer result_status;
    private Date result_date;
    private Integer result_operator;
    private Integer pathology_id;
    private Integer pathology_status;
    private Date pathology_date;
    private Integer pathology_operator;
    private Integer notification_id;
    private Integer notification_entry_status;
    private Date notification_entry_date;
    private Integer notification_entry_operator;
    private Integer notification_issue_status;
    private Date notification_issue_date;
    private Integer notification_issue_operator;
    private Integer community_dept_id;
    private Integer area_dept_id;
    private String notification_issue_worker_code;
    private String notification_issue_note;
    private Integer notification_issue_mode;
    private Date reserve_status_date;

    private Integer source_type;//肠镜检查记录 生成来源  1.表示预约  2.新增

    private String operationSource;//录入操作来源
    private Integer operationSourceId;//操作id

    private String editoperationSource;//渲染操作来源


    public HospitalColonoscopyRecord() {
        super();
    }


    public HospitalColonoscopyRecord(Integer id, String sid, Integer stage, String allocation_id, Integer reserve_id, Integer reserve_status, Date reserve_date, Integer reserve_operator, Integer examination_status, Date examination_date, Date examination_check_date, Integer examination_operator, Integer finished_status, Integer result_id, Integer result_status, Date result_date, Integer result_operator, Integer pathology_id, Integer pathology_status, Date pathology_date, Integer pathology_operator, Integer notification_id, Integer notification_entry_status, Date notification_entry_date, Integer notification_entry_operator, Integer notification_issue_status, Date notification_issue_date, Integer notification_issue_operator, Integer community_dept_id, Integer area_dept_id, String notification_issue_worker_code, String notification_issue_note, Integer notification_issue_mode, Date reserve_status_date, Integer source_type, String operationSource, Integer operationSourceId) {
        this.id = id;
        this.sid = sid;
        this.stage = stage;
        this.allocation_id = allocation_id;
        this.reserve_id = reserve_id;
        this.reserve_status = reserve_status;
        this.reserve_date = reserve_date;
        this.reserve_operator = reserve_operator;
        this.examination_status = examination_status;
        this.examination_date = examination_date;
        this.examination_check_date = examination_check_date;
        this.examination_operator = examination_operator;
        this.finished_status = finished_status;
        this.result_id = result_id;
        this.result_status = result_status;
        this.result_date = result_date;
        this.result_operator = result_operator;
        this.pathology_id = pathology_id;
        this.pathology_status = pathology_status;
        this.pathology_date = pathology_date;
        this.pathology_operator = pathology_operator;
        this.notification_id = notification_id;
        this.notification_entry_status = notification_entry_status;
        this.notification_entry_date = notification_entry_date;
        this.notification_entry_operator = notification_entry_operator;
        this.notification_issue_status = notification_issue_status;
        this.notification_issue_date = notification_issue_date;
        this.notification_issue_operator = notification_issue_operator;
        this.community_dept_id = community_dept_id;
        this.area_dept_id = area_dept_id;
        this.notification_issue_worker_code = notification_issue_worker_code;
        this.notification_issue_note = notification_issue_note;
        this.notification_issue_mode = notification_issue_mode;
        this.reserve_status_date = reserve_status_date;
        this.source_type = source_type;
        this.operationSource = operationSource;
        this.operationSourceId = operationSourceId;
    }

    public String getOperationSource() {
        return operationSource;
    }

    public void setOperationSource(String operationSource) {
        this.operationSource = operationSource;
    }

    public Integer getOperationSourceId() {
        return operationSourceId;
    }

    public void setOperationSourceId(Integer operationSourceId) {
        this.operationSourceId = operationSourceId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public String getAllocation_id() {
        return allocation_id;
    }

    public void setAllocation_id(String allocation_id) {
        this.allocation_id = allocation_id;
    }

    public Integer getReserve_id() {
        return reserve_id;
    }

    public void setReserve_id(Integer reserve_id) {
        this.reserve_id = reserve_id;
    }

    public Integer getReserve_status() {
        return reserve_status;
    }

    public void setReserve_status(Integer reserve_status) {
        this.reserve_status = reserve_status;
    }

    public Date getReserve_date() {
        return reserve_date;
    }

    public void setReserve_date(Date reserve_date) {
        this.reserve_date = reserve_date;
    }

    public Integer getReserve_operator() {
        return reserve_operator;
    }

    public void setReserve_operator(Integer reserve_operator) {
        this.reserve_operator = reserve_operator;
    }

    public Integer getExamination_status() {
        return examination_status;
    }

    public void setExamination_status(Integer examination_status) {
        this.examination_status = examination_status;
    }

    public Date getExamination_date() {
        return examination_date;
    }

    public void setExamination_date(Date examination_date) {
        this.examination_date = examination_date;
    }

    public Integer getExamination_operator() {
        return examination_operator;
    }

    public void setExamination_operator(Integer examination_operator) {
        this.examination_operator = examination_operator;
    }

    public Integer getFinished_status() {
        return finished_status;
    }

    public void setFinished_status(Integer finished_status) {
        this.finished_status = finished_status;
    }

    public Integer getResult_id() {
        return result_id;
    }

    public void setResult_id(Integer result_id) {
        this.result_id = result_id;
    }

    public Integer getResult_status() {
        return result_status;
    }

    public void setResult_status(Integer result_status) {
        this.result_status = result_status;
    }

    public Date getResult_date() {
        return result_date;
    }

    public void setResult_date(Date result_date) {
        this.result_date = result_date;
    }

    public Integer getResult_operator() {
        return result_operator;
    }

    public void setResult_operator(Integer result_operator) {
        this.result_operator = result_operator;
    }

    public Integer getPathology_id() {
        return pathology_id;
    }

    public void setPathology_id(Integer pathology_id) {
        this.pathology_id = pathology_id;
    }

    public Integer getPathology_status() {
        return pathology_status;
    }

    public void setPathology_status(Integer pathology_status) {
        this.pathology_status = pathology_status;
    }

    public Date getPathology_date() {
        return pathology_date;
    }

    public void setPathology_date(Date pathology_date) {
        this.pathology_date = pathology_date;
    }

    public Integer getPathology_operator() {
        return pathology_operator;
    }

    public void setPathology_operator(Integer pathology_operator) {
        this.pathology_operator = pathology_operator;
    }

    public Integer getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(Integer notification_id) {
        this.notification_id = notification_id;
    }

    public Integer getNotification_entry_status() {
        return notification_entry_status;
    }

    public void setNotification_entry_status(Integer notification_entry_status) {
        this.notification_entry_status = notification_entry_status;
    }

    public Date getNotification_entry_date() {
        return notification_entry_date;
    }

    public void setNotification_entry_date(Date notification_entry_date) {
        this.notification_entry_date = notification_entry_date;
    }

    public Integer getNotification_entry_operator() {
        return notification_entry_operator;
    }

    public void setNotification_entry_operator(Integer notification_entry_operator) {
        this.notification_entry_operator = notification_entry_operator;
    }

    public Integer getNotification_issue_status() {
        return notification_issue_status;
    }

    public void setNotification_issue_status(Integer notification_issue_status) {
        this.notification_issue_status = notification_issue_status;
    }

    public Date getNotification_issue_date() {
        return notification_issue_date;
    }

    public void setNotification_issue_date(Date notification_issue_date) {
        this.notification_issue_date = notification_issue_date;
    }

    public Integer getNotification_issue_operator() {
        return notification_issue_operator;
    }

    public void setNotification_issue_operator(Integer notification_issue_operator) {
        this.notification_issue_operator = notification_issue_operator;
    }

    public Integer getCommunity_dept_id() {
        return community_dept_id;
    }

    public void setCommunity_dept_id(Integer community_dept_id) {
        this.community_dept_id = community_dept_id;
    }

    public Integer getArea_dept_id() {
        return area_dept_id;
    }

    public void setArea_dept_id(Integer area_dept_id) {
        this.area_dept_id = area_dept_id;
    }

    public String getNotification_issue_worker_code() {
        return notification_issue_worker_code;
    }

    public void setNotification_issue_worker_code(String notification_issue_worker_code) {
        this.notification_issue_worker_code = notification_issue_worker_code;
    }

    public String getNotification_issue_note() {
        return notification_issue_note;
    }

    public void setNotification_issue_note(String notification_issue_note) {
        this.notification_issue_note = notification_issue_note;
    }

    public Integer getNotification_issue_mode() {
        return notification_issue_mode;
    }

    public void setNotification_issue_mode(Integer notification_issue_mode) {
        this.notification_issue_mode = notification_issue_mode;
    }

    public Integer getSource_type() {
        return source_type;
    }

    public void setSource_type(Integer source_type) {
        this.source_type = source_type;
    }

    public Date getReserve_status_date() {
        return reserve_status_date;
    }

    public void setReserve_status_date(Date reserve_status_date) {
        this.reserve_status_date = reserve_status_date;
    }

    public Date getExamination_check_date() {
        return examination_check_date;
    }

    public void setExamination_check_date(Date examination_check_date) {
        this.examination_check_date = examination_check_date;
    }

    public String getEditoperationSource() {
        return editoperationSource;
    }

    public void setEditoperationSource(String editoperationSource) {
        this.editoperationSource = editoperationSource;
    }
}
