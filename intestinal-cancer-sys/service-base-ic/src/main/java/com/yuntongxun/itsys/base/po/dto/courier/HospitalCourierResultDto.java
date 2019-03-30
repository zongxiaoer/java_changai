package com.yuntongxun.itsys.base.po.dto.courier;

import com.yuntongxun.itsys.base.po.HospitalCourierResultPO;

import java.util.List;

/**
 * @author zongt
 * @date 2018/6/22
 */
public class HospitalCourierResultDto extends HospitalCourierResultPO {

    private String sendDateByString;//发出时间
    private String acceptDateByString;//接受时间

    private List<FrozenBoxCodeDto> frozenBoxCodeDtos;

    private String sendDateStart;//发出开始时间
    private String sendDateEnd;//发出结束时间

    private String  acceptDateStart;//接受开始时间
    private String  acceptDateEnd;//接受结束时间




    private int pageSize;
    private int pageNo;

    public List<FrozenBoxCodeDto> getFrozenBoxCodeDtos() {
        return frozenBoxCodeDtos;
    }

    public void setFrozenBoxCodeDtos(List<FrozenBoxCodeDto> frozenBoxCodeDtos) {
        this.frozenBoxCodeDtos = frozenBoxCodeDtos;
    }

    public String getSendDateByString() {
        return sendDateByString;
    }

    public void setSendDateByString(String sendDateByString) {
        this.sendDateByString = sendDateByString;
    }

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

    public String getSendDateStart() {
        return sendDateStart;
    }

    public void setSendDateStart(String sendDateStart) {
        this.sendDateStart = sendDateStart;
    }

    public String getSendDateEnd() {
        return sendDateEnd;
    }

    public void setSendDateEnd(String sendDateEnd) {
        this.sendDateEnd = sendDateEnd;
    }

    public String getAcceptDateStart() {
        return acceptDateStart;
    }

    public void setAcceptDateStart(String acceptDateStart) {
        this.acceptDateStart = acceptDateStart;
    }

    public String getAcceptDateEnd() {
        return acceptDateEnd;
    }

    public void setAcceptDateEnd(String acceptDateEnd) {
        this.acceptDateEnd = acceptDateEnd;
    }

    public String getAcceptDateByString() {
        return acceptDateByString;
    }

    public void setAcceptDateByString(String acceptDateByString) {
        this.acceptDateByString = acceptDateByString;
    }
}
