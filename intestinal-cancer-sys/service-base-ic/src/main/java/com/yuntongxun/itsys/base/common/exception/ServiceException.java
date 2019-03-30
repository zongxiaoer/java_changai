package com.yuntongxun.itsys.base.common.exception;

/**
 * Description: 异常捕获
 * Created by LuoKun on 2017-09-01.
 */
public class ServiceException extends RuntimeException {
    private String code;

    public ServiceException() {super();}

    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
