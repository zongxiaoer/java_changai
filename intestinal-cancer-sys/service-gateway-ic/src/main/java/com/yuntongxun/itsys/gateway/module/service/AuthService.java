package com.yuntongxun.itsys.gateway.module.service;

import com.yuntongxun.itsys.gateway.common.vo.CookieParam;
import com.yuntongxun.itsys.gateway.common.vo.ResultMsg;

public interface AuthService {

    /**
     * 重置token
     * @param cookieParam cookie参数
     * @return
     */
    public ResultMsg renewToken(CookieParam cookieParam);
    
    public ResultMsg removeToken(CookieParam cookieParam);
}
