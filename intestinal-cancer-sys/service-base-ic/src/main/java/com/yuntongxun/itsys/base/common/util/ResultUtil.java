package com.yuntongxun.itsys.base.common.util;

import com.yuntongxun.itsys.base.vo.Result;

/**
 * Description: 返回结果数据集
 * Created by LuoKun on 2017-08-31
 */
public class ResultUtil {
    /**
     * 标准的返回结果集
     *
     * @param statusCode 编码
     * @param statusMsg  信息
     * @param data       结果
     * @return 返回封装好的结果集
     */
    private static Result standardResult(Object data, Object pageInfo, String statusCode, String statusMsg) {
        Result result = new Result();
        result.setData(data);
        result.setPageInfo(pageInfo);
        result.setStatusCode(statusCode);
        result.setStatusMsg(statusMsg);
        return result;
    }

    /**
     * 请求成功返回的结果集，code：0，msg：success
     *
     * @param data 需要返回的值
     * @return 返回封装好的结果集
     */
    public static Result success(Object data, Object pageInfo) {
        return standardResult(data, pageInfo, GlobalErrorCode.NORMAL_RESPONSE, "success");
    }

    public static Result success(Object data, Object pageInfo, String statusCode, String message) {
        return standardResult(data, pageInfo, statusCode, message);
    }

    public static Result success(Object data, String message) {
        return standardResult(data, null, GlobalErrorCode.NORMAL_RESPONSE, message);
    }

    public static Result success(String statusCode, String message) {
        return standardResult(null, null, statusCode, message);
    }


    public static Result success(String statusCode, String message, Object data) {
        return standardResult(data, null, statusCode, message);
    }

    /**
     * 请求成功返回的结果集
     *
     * @param data    需要返回的值
     * @param message 成功的文字描述
     * @return 返回封装好的结果集
     */
    public static Result success(String message, Object data, Object pageInfo) {
        return standardResult(data, pageInfo, GlobalErrorCode.NORMAL_RESPONSE, message);
    }

    /**
     * 请求成功返回的结果集
     *
     * @return 返回封装好的结果集，但data为空
     */
    public static Result success(String message) {
        return success(GlobalErrorCode.NORMAL_RESPONSE, message);
    }

    /**
     * 请求失败返回的结果集
     *
     * @param code 出现错误的code
     * @param msg  出现错误的信息
     * @return 结果集
     */
    public static Result error(String code, String msg, Object pageInfo) {
        return standardResult(null, null, code, msg);
    }

    public static Result error(String code, String msg) {
        return standardResult(null, null, code, msg);
    }

}
