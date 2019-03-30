package com.yuntongxun.itsys.base.common.exception;

/**
 * Description: 异常捕获
 * Created by LuoKun on 2017-09-01.
 */
public class DaoException extends RuntimeException {
    private String code;

    public DaoException() {super();}

    public DaoException(String code, String message) {
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
