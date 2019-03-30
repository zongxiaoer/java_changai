package com.yuntongxun.itsys.gateway.common.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.yuntongxun.itsys.gateway.common.base.AbstractLogger;
import com.yuntongxun.itsys.gateway.common.cache.redis.RedisManager;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;



@Component
public class CacheManager extends AbstractLogger{
    

    @Autowired
    @Qualifier("ehCacheManager")
    private net.sf.ehcache.CacheManager ehCacheManager;
    @Autowired
    private RedisManager redisManager;
    
    /**
     * 设置缓存
     * @param name ehCache配置文件 name
     * @param key 键
     * @param value 值
     * @param expire 失效时间 秒
     */
    public void set(String name,String key,String value,long expire){
        logger.debug("set cache [name:{},key:{},value:{},expire in second:{}]", name,key,value,expire);
        Cache cache = ehCacheManager.getCache(name);
        Element element = new Element(key, value);
        cache.put(element);
        logger.debug("EhCache set success");
        if(expire>-1 && redisManager.set(key, value, expire)){
            logger.debug("redis set success");
        }
    }
    
    /**
     * 获取缓存
     * @param name ehCache配置文件 name
     * @param key 键
     * @return
     */
    public String get(String name,String key){
        logger.debug("get cache [name:{},key:{}]", name,key);
        Cache cache = ehCacheManager.getCache(name);
        Element element = cache.get(key);
        String value = null;
        if(element != null){
            value = (String) element.getObjectValue();
            logger.debug("get EhCache success：{}", value);
            return value;
        }
        value = redisManager.get(key);
        logger.debug("get redis success：{}", value);
        if(value != null){
            logger.debug("because ehCache has not，set ehCache");
            this.set(name, key, value, -1);
        }
        return value;
    }
    
    
    /**
     * 删除值
     * @param name
     * @param key
     */
    public void delete(String name,String key){
        logger.debug("delete cache [name:{},key:{}]", name,key);
        Cache cache = ehCacheManager.getCache(name);
        cache.remove(key);
        logger.debug("delete EhCache success");
        redisManager.delete(key);
        logger.debug("delete redis success");
    }

    @Override
    protected Class<?> getClassName() {
        return CacheManager.class;
    }
}
