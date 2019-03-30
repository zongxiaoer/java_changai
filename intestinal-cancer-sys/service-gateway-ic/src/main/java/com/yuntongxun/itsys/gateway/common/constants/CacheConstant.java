package com.yuntongxun.itsys.gateway.common.constants;

public class CacheConstant {

    /**
     * 用户token键前缀 例：itsys001|admin
     */
    public static final String TOKEN_KEY_PREFIX = "itsys001|";
    /**
     * 用户权限键前缀 例：itsys002|admin
     */
    public static final String PERMISSIONS_KEY_PREFIX = "itsys002|";
    
    
    /**
     * ehCache配置文件cache name：permissions
     */
    public static final String EH_CACHE_NAME_PERMISSIONS = "permissions";
    /**
     * ehCache配置文件cache name：userToken
     */
    public static final String EH_CACHE_NAME_USER_TOKEN = "userToken";
    /**
     * 缓存Http请求资源到redis  默认ttl=1H
     */
    public static final String REDIS_CACHE_HTTPRESOURCE_PREFIX="http_resource_cache";
    
    
    
    
    
}
