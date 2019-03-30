package com.yuntongxun.itsys.gateway.common.constants;

public class StatusConstant {

    public static final String SUCCESS_CODE = "000000";
    public static final String SUCCESS_MSG = "success";
    
    
    public static final String LOGIN_NO_PARAM_CODE = "811000";
    public static final String LOGIN_NO_PARAM_MSG = "账号和密码不能为空";
    
//    public static final String COOKIE_NO_TOKEN_LOGINNAME_CODE = "811001";
    public static final String COOKIE_NO_TOKEN_LOGINNAME_MSG = "cookie YTX_LOGINNAME不能为空或YTX_TOKEN不能为空";
    
    public static final String ACCOUNT_PASSWORD_CODE = "811002";
    public static final String ACCOUNT_PASSWORD_MSG = "账号或密码错误";
    
    public static final String PASSWORD_ERROR_CODE = "811004";
    public static final String PASSWORD_ERROR_MSG = "密码错误";
    
//    public static final String TOKEN_TIMEOUT_CODE = "811005";
    public static final String TOKEN_TIMEOUT_MSG = "token已超时，请重新登录";
    
    public static final String TOKEN_EXPIRE_CODE = "811006";
    public static final String TOKEN_EXPIRE_MSG = "token已过期，请重置token";
    
    public static final String VALIDATE_FAIL_CODE = "811007";
    public static final String VALIDATE_FAIL_MSG = "验证token失败";
    
    public static final String PERMISSIONS_ERROR_CODE = "811008";
    public static final String PERMISSIONS_ERROR_MSG = "没有权限";
    
    
    public static final String TOKEN_NOT_RENEW_CODE = "811009";
    public static final String TOKEN_NOT_RENEW_MSG = "token无需重置";

    public static final String ERR_SEND_PCODE_CODE="824015";
    public static final String ERR_SEND_PCODE_MSG="短信验证码发送失败";

    public static final String FAILURE_SEND_PCODE_CODE="811010";
    public static final String FAILURE_SEND_PCODE_MSG="短信验证码无效";

    public static final String INPUT_SEND_PCODE_CODE="811013";
    public static final String INPUT_SEND_PCODE_MSG="短信验证码输入错误";

    public static final String LOGIN_NO_PHONE_CODE="811011";
    public static final String LOGIN_NO_PHONE_MSG="未输入手机号";

    public static final String LOGIN_NO_AUTHCODE_CODE="811012";
    public static final String LOGIN_NO_AUTHCODE_MSG="未输入短信验证码";

    public static final String LOGIN_NO_USER_CODE="811015";
    public static final String LOGIN_NO_USER_MSG="用户不存在";

    public static final String LOGIN_AUTHCODE_CODE="811016";
    public static final String LOGIN_AUTHCODE_MSG="未输入图片校验码";

    public static final String LOGIN_VERIFY_CODE="811017";
    public static final String LOGIN_VERIFY_MSG="图片校验码错误";

    public static final String LOGIN_VERIFY_EFFICACY_CODE="811018";
    public static final String LOGIN_VERIFY_EFFICACY_MSG="图片校验码失效,请重新获取";

    public static final String LOGIN_NO_PHONE_SEND_CODE="811019";
    public static final String LOGIN_NO_PHONE_SEND_MSG="请输入手机号";

    public static final String LOGIN_NO_ENVIRONMENT_CODE="811020";
    public static final String LOGIN_NO_ENVIRONMENT_MSG="开发环境，不能发送验证码";

    public static final String LOGIN_NO_USER_PHONE_CODE="811021";
    public static final String LOGIN_NO_USER_PHONE_MSG="该用户没有手机号,请联系管理员";

    public static final String LOGIN_NO_USER_OLDPASS_CODE="811022";
    public static final String LOGIN_NO_USER_OLDPASS_MSG="您输入的旧密码有误，请重新输入";

    public static final String LOGIN_NO_USER_UPDATE_PASS_CODE="811023";
    public static final String LOGIN_NO_USER_UPDATE_PASS_MSG="修改密码异常,请联系管理员";

    public static final String LOGIN_NO_USER_UPDATE_PASS_OLDPASS_CODE="811024";
    public static final String LOGIN_NO_USER_UPDATE_PASS_OLDPASS_MSG="您输入的原密码与新密码不能一致，请重新输入";



    public static final String LOGIN_IS_FIRST_PASS_CODE="811025";//不能被复用！！！
    public static final String LOGIN_IS_FIRST_PASS_MSG="初始登录状态,请修改密码";//不能被复用！！！


    public static final String LOGIN_IS_LAN_FIRST_PASS_CODE="811027";//拦截初始状态！！！
    public static final String LOGIN_IS_LAN_FIRST_PASS_MSG="初始登录状态,请修改密码";//不能被复用！！！




    public static final String LOGIN_IS_PASS_LENGTH_CODE="811026";//不能被复用！！！
    public static final String LOGIN_IS_PASS_LENGTH_MSG="新密码长度在6～16之间，请重新输入";//不能被复用！！！
    
    /**
     * 返回值状态码key字符串
     */
    public static final String STATUS_CODE_STR = "statusCode";
    /**
     * 返回值数据key字符串
     */
    public static final String DATA_STR = "data";
    /**
     * 获取权限请求key字符串
     */
    public static final String REQUESTS_STR = "requests";

    public static final String REDIS_KEY="itsys|";

    public static final String UPDATE_IMAGE_KEY="UPDATE_IMAGE_KEY|";

    
}
