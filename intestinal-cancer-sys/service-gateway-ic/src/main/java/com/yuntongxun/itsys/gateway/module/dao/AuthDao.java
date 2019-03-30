package com.yuntongxun.itsys.gateway.module.dao;

import java.util.List;

import com.yuntongxun.itsys.gateway.module.pojo.Request;
import com.yuntongxun.itsys.gateway.module.pojo.UserToken;

public interface AuthDao {

    /**
     * 设置权限缓存
     * @param key
     * @param json
     * @param expire
     * @return
     */
    public List<Request> setPermissions(String key,String json,long expire);
    
    /**
     * 获取权限缓存
     * @param key
     * @return
     */
    public List<Request> getPermissionsForKey(String key);
    
    /**
     * 设置token
     * @param key
     * @param userToken
     * @param expire
     * @return
     */
    public UserToken setToken(String key,UserToken userToken,long expire);
    
    /**
     * 获取token
     * @param key
     * @return
     */
    public UserToken getTokenForKey(String key);
    /**
     * 删除token
     * @param key
     * @return
     */
    public void removeToken(String key);
}
