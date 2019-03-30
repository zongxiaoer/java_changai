package com.yuntongxun.itsys.base.po;

/**
 * Description: 异常事件记录表 hospital_abnormal_event
 *
 * @author LuoKun on 2018-04-16.
 */
public class HospitalAbnormalEvent {

    private Integer id;

    private String sid;

    private String stage;

    private String event_type;

    private String data_id;

    private String community_dept_id;

    private String area_dept_id;

    private String create_user;

    private String update_user;

    public HospitalAbnormalEvent() {
        super();
    }

    public HospitalAbnormalEvent(Integer id, String sid, String stage, String event_type, String data_id, String community_dept_id, String area_dept_id, String create_user, String update_user) {
        this.id = id;
        this.sid = sid;
        this.stage = stage;
        this.event_type = event_type;
        this.data_id = data_id;
        this.community_dept_id = community_dept_id;
        this.area_dept_id = area_dept_id;
        this.create_user = create_user;
        this.update_user = update_user;
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

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getData_id() {
        return data_id;
    }

    public void setData_id(String data_id) {
        this.data_id = data_id;
    }

    public String getCommunity_dept_id() {
        return community_dept_id;
    }

    public void setCommunity_dept_id(String community_dept_id) {
        this.community_dept_id = community_dept_id;
    }

    public String getArea_dept_id() {
        return area_dept_id;
    }

    public void setArea_dept_id(String area_dept_id) {
        this.area_dept_id = area_dept_id;
    }

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    public String getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(String update_user) {
        this.update_user = update_user;
    }
}
