package com.yuntongxun.itsys.base.cache;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuntongxun.itsys.base.common.util.StringUtil;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;



@Component
public class CacheManager{
    

    @Autowired
    private net.sf.ehcache.CacheManager ehCacheManager;
    private final Logger log = LogManager.getLogger(CacheManager.class);
    /**
     * 设置缓存
     * @param name ehCache配置文件 name
     * @param key 键
     * @param value 值
     * @param expire 失效时间 秒
     */
    public void set(String name,String key,String value,long expire){
        log.debug("EhCache设置缓存start： name={},key={},value={},expire={}", name,key,value,expire);
        Cache cache = ehCacheManager.getCache(name);
        Element element = new Element(key, value);
        cache.put(element);
        log.debug("EhCache设置缓存成功");
       log.debug("EhCache设置缓存End");
    }
    
    public int getCacheSize(String name,String key){
    	int size=0;
    	if(!StringUtil.isEmpty(name)){
    		 Cache cache = ehCacheManager.getCache(name);
    		 size=cache.getSize();
    	}
    	if(!StringUtil.isEmpty(key)){
    		 Cache cache = ehCacheManager.getCache(name);
    	     Element element = cache.get(key);
    	     if(element != null){
    	    	 size=1;
    	        }
    	}
    	return size;
    }
    
    
    /**
     * 获取缓存
     * @param name ehCache配置文件 name
     * @param key 键
     * @return
     */
    public String get(String name,String key){
        log.debug("EhCache获取缓存 Start : name={},key={}", name,key);
        Cache cache = ehCacheManager.getCache(name);
        Element element = cache.get(key);
        String value = null;
        if(element != null){
            value = (String) element.getObjectValue();
            log.debug("获取EhCache缓存成功：value={}", value);
            return value;
        }
        log.debug("EhCache获取缓存 End");
        return value;
    }
    
    
    /**
     * 删除值
     * @param name
     * @param key
     */
    public void delete(String name,String key){
        log.debug("EhCache删除缓存 Start name={},key={}", name,key);
        Cache cache = ehCacheManager.getCache(name);
        cache.remove(key);
        log.debug("删除EhCache缓存成功");
        log.debug("EhCache删除缓存 End");
    }

}
