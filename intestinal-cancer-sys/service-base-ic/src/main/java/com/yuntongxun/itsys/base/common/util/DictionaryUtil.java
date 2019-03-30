package com.yuntongxun.itsys.base.common.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yuntongxun.itsys.base.cache.RedisManager;
import com.yuntongxun.itsys.base.service.DictionaryService;

@Component
public class DictionaryUtil {

	@Autowired
	private RedisManager redis;
	@Autowired
	private DictionaryService dictionaryService;
	private static DictionaryUtil dictionaryUtil;
	private static final Logger log = LogManager.getLogger(DictionaryUtil.class);
	@PostConstruct
	public void init() {
		dictionaryUtil = this;
		dictionaryUtil.redis = redis;
		dictionaryUtil.dictionaryService=dictionaryService;
	}
	/**
	 * 翻译数据字典
	 * <br>
	 * <p>传入原始数据，该数据中真实值的那个属性和从缓存中获取的key。redis中每条翻译数据key的格式为：object_1='xxx'</p>
	 * @param tranList  原始数据
	 * @param key1 原始数据待翻译的字段
	 * @param key2 缓存字段
	 * @return
	 */
	public static List translateList(List tranList,String key1,String key2){
		List result=new  ArrayList();
		if(tranList.size()>0&&tranList!=null){
			for(int i=0;i<tranList.size();i++){
				JsonObject json=new JsonParser().parse(JSONUtils.objectToJsonDateSerializer(tranList.get(i),"yyyy-MM-dd HH:mm:ss")).getAsJsonObject();
				log.info("@DictionaryUtil 数据对象{}",json);
				String key=json.get(key1).isJsonNull()?"":json.get(key1).getAsString();//数字--真实值
				if(StringUtil.isEmpty(key)){
					log.info("@DictionaryUtil 属性{}，获取值为空,数据翻译自动添加翻译后的值为空......",key1);
					json.addProperty(key2+"DisplayName", "");
					result.add(json);
					continue;
				}
				StringBuffer sb=new StringBuffer("");
				sb.append(key2);
				sb.append("_");
				sb.append(key);
				log.info("@DictionaryUtil  根据key获取缓存数据.......key={}",sb.toString());
				String value=dictionaryUtil.redis.get(sb.toString());
				if(StringUtils.isEmpty(value)){
					log.info("@DictionaryUtil  根据key获取缓存数据为空,重载缓存");
					dictionaryUtil.dictionaryService.queryDictionaryOnCache();
					value=dictionaryUtil.redis.get(sb.toString());
					log.info("@DictionaryUtil  根据key重新获取缓存数据......key={},value={}",sb.toString(),value);
				}
				log.info("@DictionaryUtil  获取到的value={}",value);
				json.addProperty(key2+"DisplayName", value);
				result.add(json);
			}
		}
		return result;
	}
}
