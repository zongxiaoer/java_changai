/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: BiService
 * Author:   sun
 * Date:     2018/9/5 11:32
 * History:
 * <author>          <time>                <version>
 * sun           2018/9/5 11:32           v1.0.0
 */
package com.yuntongxun.itsys.base.service;

import com.yuntongxun.itsys.base.vo.UserPramVo;

/**
 * 一句话功能简述
 *
 * @author sun
 * @create 2018/9/5
 * @since v1.0.0
 */
public interface BiService {
    /**
     * 向BI项目发送accessToken
     * @param accessToken
     * @return
     */
    String sendAccessToken(String method, String path, int timeout, int readTimeout, String accessToken) throws Exception;

    /**
     * 根据登录名查询在BI项目的权限
     * @param loginName
     * @return
     */
    UserPramVo getBiAuthorityByLoginName(String loginName);
}
