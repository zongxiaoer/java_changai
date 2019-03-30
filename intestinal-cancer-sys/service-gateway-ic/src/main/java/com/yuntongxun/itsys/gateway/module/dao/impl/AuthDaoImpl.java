package com.yuntongxun.itsys.gateway.module.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yuntongxun.itsys.gateway.common.cache.CacheManager;
import com.yuntongxun.itsys.gateway.common.constants.CacheConstant;
import com.yuntongxun.itsys.gateway.common.util.JSONUtil;
import com.yuntongxun.itsys.gateway.common.util.StringUtil;
import com.yuntongxun.itsys.gateway.module.dao.AuthDao;
import com.yuntongxun.itsys.gateway.module.pojo.Request;
import com.yuntongxun.itsys.gateway.module.pojo.UserToken;

@Repository
public class AuthDaoImpl implements AuthDao {


    @Autowired
    private CacheManager cacheManager;
    private final Logger log = LogManager.getLogger(AuthDaoImpl.class);
    @Override
    public List<Request> setPermissions(String key, String json, long expire) {
    	log.debug("set key {},json{}",key,json);
        cacheManager.set(CacheConstant.EH_CACHE_NAME_PERMISSIONS, key, json, expire);
        return (List<Request>) JSONUtil.toList(json, Request.class);
    }

    @Override
    public List<Request> getPermissionsForKey(String key) {
    	log.debug("get key {}",key);
        String json = cacheManager.get(CacheConstant.EH_CACHE_NAME_PERMISSIONS, key);
        if(StringUtil.isBlank(json)){
            return null;
        }
        return (List<Request>) JSONUtil.toList(json, Request.class);
    }
    
    
    @Override
    public UserToken setToken(String key, UserToken userToken, long expire) {
        String token = JSONUtil.toJson(userToken);
        cacheManager.set(CacheConstant.EH_CACHE_NAME_USER_TOKEN, key, token, expire);
        return userToken;
    }


    @Override
    public UserToken getTokenForKey(String key) {
        String json = cacheManager.get(CacheConstant.EH_CACHE_NAME_USER_TOKEN, key);
        if(StringUtil.isBlank(json)){
            return null;
        }
        return JSONUtil.toBean(json, UserToken.class);
    }

	@Override
	public void removeToken(String key) {
		// TODO Auto-generated method stub
		cacheManager.delete(CacheConstant.EH_CACHE_NAME_USER_TOKEN, key);
	}
}
