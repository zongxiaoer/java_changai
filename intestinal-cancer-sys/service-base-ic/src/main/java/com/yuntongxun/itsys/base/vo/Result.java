package com.yuntongxun.itsys.base.vo;

import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;

/**
 * Description: 结果信息
 * Created by LuoKun on 2017-09-01.
 */
public class Result {
    private String statusCode;
    private String statusMsg;
    private Object data;
    private Object pageInfo;

    public Result() {
        this.statusCode = GlobalErrorCode.NORMAL_RESPONSE;
        this.statusMsg = GlobalErrorCode.SUCCESS;
    }

    public Result(Object data) {
        this.statusCode = GlobalErrorCode.NORMAL_RESPONSE;
        this.statusMsg = GlobalErrorCode.SUCCESS;
        this.data = data;
    }

    public Result(Object data, Object pageInfo) {
        this.statusCode = GlobalErrorCode.NORMAL_RESPONSE;
        this.statusMsg = GlobalErrorCode.SUCCESS;
        this.data = data;
        this.pageInfo = pageInfo;
    }

    public Result(String statusCode, String statusMsg) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
        this.data = null;
        this.pageInfo = null;
    }

    public Result(String statusCode, String statusMsg, Object data, Object pageInfo) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
        this.data = data;
        this.pageInfo = pageInfo;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(Object pageInfo) {
        this.pageInfo = pageInfo;
    }
}
