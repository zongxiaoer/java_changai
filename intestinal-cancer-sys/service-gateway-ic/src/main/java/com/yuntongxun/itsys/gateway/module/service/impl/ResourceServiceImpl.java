package com.yuntongxun.itsys.gateway.module.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yuntongxun.itsys.gateway.common.cache.redis.RedisManager;
import com.yuntongxun.itsys.gateway.common.constants.CacheConstant;
import com.yuntongxun.itsys.gateway.common.util.JSONUtil;
import com.yuntongxun.itsys.gateway.common.util.StringUtil;
import com.yuntongxun.itsys.gateway.module.dao.ResourceDao;
import com.yuntongxun.itsys.gateway.module.pojo.Resource;
import com.yuntongxun.itsys.gateway.module.service.ResourceService;

@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceDao resdao;
	@Autowired
	private RedisManager redisManager;
	@Value("${ytx.gateway.httpresource.expire}")
	private long expire;// 过期时间
	private final Logger log = LogManager.getLogger(ResourceServiceImpl.class);

	/**
	 * 对资源表所有type=3的资源增加缓存
	 */
	@Override
	public List<Resource> getResourceByType(String type) {
		List<Resource> resourceList = new ArrayList();
		try {
			String cacheResource = redisManager.get(CacheConstant.REDIS_CACHE_HTTPRESOURCE_PREFIX);
			if (StringUtil.isEmpty(cacheResource)) {// 缓存为空，需要重新加载
				log.info("@ServiceImpl......redis获取所有请求资源为空,重新加载");
				resourceList = resdao.getResourceByType(type);
				redisManager.set(CacheConstant.REDIS_CACHE_HTTPRESOURCE_PREFIX, JSONUtil.toJson(resourceList), expire);
			} else {
				resourceList = JSONUtil.toBean(cacheResource, new ArrayList<Resource>().getClass());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resourceList;
	}

}
