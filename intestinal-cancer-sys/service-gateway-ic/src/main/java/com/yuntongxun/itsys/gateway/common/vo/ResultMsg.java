package com.yuntongxun.itsys.gateway.common.vo;

import com.yuntongxun.itsys.gateway.common.constants.StatusConstant;

public class ResultMsg {

    private String statusCode;//错误码
    private String statusMsg;//信息
    private Object data;//数据
    
    
    public ResultMsg(){
        this(StatusConstant.SUCCESS_CODE,StatusConstant.SUCCESS_MSG);
    }
    
    public ResultMsg(Object data){
        this(StatusConstant.SUCCESS_CODE,StatusConstant.SUCCESS_MSG);
        this.data = data;
    }
    public ResultMsg(String statusCode,String statusMsg){
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
        this.data = "";
    }
    
    public ResultMsg(String statusCode,String statusMsg,Object data){
        this(statusCode,statusMsg);
        this.data = data;
    }
    
    public Object getData() {
        return data;
    }


    public void setData(Object data) {
        this.data = data;
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
    
    
    
}
