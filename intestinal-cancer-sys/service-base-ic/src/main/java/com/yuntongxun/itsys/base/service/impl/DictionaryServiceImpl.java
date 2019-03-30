package com.yuntongxun.itsys.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yuntongxun.itsys.base.cache.RedisManager;
import com.yuntongxun.itsys.base.common.exception.DaoException;
import com.yuntongxun.itsys.base.common.exception.ServiceException;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.ResultUtil;
import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.dao.DictionaryDao;
import com.yuntongxun.itsys.base.po.Dictionary;
import com.yuntongxun.itsys.base.po.DictionaryManyModel;
import com.yuntongxun.itsys.base.po.PageInfo;
import com.yuntongxun.itsys.base.service.DictionaryService;
import com.yuntongxun.itsys.base.vo.Result;

@Service
public class DictionaryServiceImpl implements DictionaryService {

	final Logger log = LogManager.getLogger(DictionaryServiceImpl.class);
	public static final String KEY_DICTIONARY_INDICATOR_LIST = "ytx_dic_indicator_list";// redis 缓存远程获取的数据指标的数据字典
	@Autowired
	private DictionaryDao dictionarydao;
	@Autowired
	private RedisManager redis;

	@Override
	public Result queryDictionary(String body) throws ServiceException, DaoException {
		// TODO Auto-generated method stub
		PageInfo pageInfo = null;
		JsonObject obj = new JsonParser().parse(body).getAsJsonObject();
		String key = obj.get("key")==null ? null : obj.get("key").getAsString();
		int pageNo = obj.get("pageNo")==null ? -1 : obj.get("pageNo").getAsInt();
		int pageSize = obj.get("pageSize")==null ? -1 : obj.get("pageSize").getAsInt();
		log.info("@Service queryDictionary parm:  key={},pageNo={},pageSize={}", key, pageNo, pageSize);
		ListPageUtil listPage = dictionarydao.queryDictionary(key, pageNo, pageSize);
		return ResultUtil.success(listPage.getResultList(), listPage.getPageInfo());
	}

	@Override
	public Result getDictionary(String id) throws ServiceException, DaoException {
		// TODO Auto-generated method stub
		Dictionary dictionary = dictionarydao.getDictionary(id);
		return ResultUtil.success(dictionary, "success");
	}

	@Override
	@Transactional
	public Result insertDictionary(Dictionary dictionary) throws ServiceException, DaoException {
		// TODO Auto-generated method stub
		if (dictionary == null)
			return ResultUtil.error(GlobalErrorCode.ERR_DICTIONARY_NULL_CODE, GlobalErrorCode.ERR_DICTIONARY_NULL_MSG,
					null);
		if (StringUtil.isEmpty(dictionary.getKey()))
			return ResultUtil.error(GlobalErrorCode.ERR_DICTIONARY_KEY_NULL_CODE,
					GlobalErrorCode.ERR_DICTIONARY_KEY_NULL_MSG, null);
		if (StringUtil.isEmpty(dictionary.getValue()))
			return ResultUtil.error(GlobalErrorCode.ERR_DICTIONARY_VALUE_NULL_CODE,
					GlobalErrorCode.ERR_DICTIONARY_VALUE_NULL_MSG, null);
		log.info("@Service insertDictionary parm: key={},value={},label={}", dictionary.getKey(), dictionary.getValue(),dictionary.getLabel());
		dictionarydao.insertDictionary(dictionary);
		queryDictionaryOnCache();//刷新缓存
		return ResultUtil.success("success");
	}

	@Override
	@Transactional
	public Result updateDictionary(Dictionary dictionary) throws ServiceException, DaoException {
		// TODO Auto-generated method stub
		if (dictionary == null)
			return ResultUtil.error(GlobalErrorCode.ERR_DICTIONARY_NULL_CODE, GlobalErrorCode.ERR_DICTIONARY_NULL_MSG,
					null);
		if (dictionary.getId()<=0)
			return ResultUtil.error(GlobalErrorCode.ERR_DICTIONARY_ID_NULL_CODE,
					GlobalErrorCode.ERR_DICTIONARY_ID_NULL_MSG, null);
		Dictionary db_dictionary = dictionarydao.getDictionary(dictionary.getId() + "");
		if (db_dictionary == null)
			return ResultUtil.error(GlobalErrorCode.ERR_DICTIONARY_UPDATE_NULL_CODE,
					GlobalErrorCode.ERR_DICTIONARY_UPDATE_NULL_MSG, null);
		if (StringUtil.isEmpty(dictionary.getKey()))
			return ResultUtil.error(GlobalErrorCode.ERR_DICTIONARY_KEY_NULL_CODE,
					GlobalErrorCode.ERR_DICTIONARY_KEY_NULL_MSG, null);
		if (StringUtil.isEmpty(dictionary.getValue()))
			return ResultUtil.error(GlobalErrorCode.ERR_DICTIONARY_VALUE_NULL_CODE,
					GlobalErrorCode.ERR_DICTIONARY_VALUE_NULL_MSG, null);
		log.info("@Service updateDictionary parm: id={},key={},value={}", dictionary.getId(), dictionary.getKey(),
				dictionary.getValue());
		dictionarydao.updateDictionary(dictionary);
		queryDictionaryOnCache();//刷新缓存
		return ResultUtil.success("success");
	}

	@Override
	@Transactional
	public Result delDictionary(String id) throws ServiceException, DaoException {
		// TODO Auto-generated method stub
		if (StringUtil.isEmpty(id))
			return ResultUtil.error(GlobalErrorCode.ERR_DICTIONARY_ID_NULL_CODE,
					GlobalErrorCode.ERR_DICTIONARY_ID_NULL_MSG, null);
		dictionarydao.delDictionary(id);
		queryDictionaryOnCache();//刷新缓存
		return ResultUtil.success("success");
	}
	
	
	@Override
	@PostConstruct
	public void queryDictionaryOnCache() throws ServiceException, DaoException {
		// TODO Auto-generated method stub
		ListPageUtil listPage = dictionarydao.queryDictionary(null, -1, -1);
		if(listPage.getResultList().size()>0&&listPage.getResultList()!=null){
			loadCache(listPage.getResultList());
		}
	}

	/**
	 * 便于翻译用 
	 * @param list
	 */
	public void loadCache(List list){
		redis.delete(KEY_DICTIONARY_INDICATOR_LIST);//删除jfservice模块缓存的字典数据，避免脏读
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Dictionary dic=JSONUtils.toBean(JSONUtils.toJson(list.get(i)), Dictionary.class);
				String key=dic.getKey()+"_"+dic.getValue();
				String value=dic.getLabel();
				redis.set(key, value,600l);//10小时
			}
		}
	}

	@Override
	public Result queryByKeyArray(String[] keys) {
		// TODO Auto-generated method stub
		if(keys.length>0&&keys!=null){
			List queryResult=dictionarydao.queryByKeyArray(keys);
			List DictionaryHold=new ArrayList();
			for(int i=0;i<keys.length;i++){
				DictionaryManyModel dmm=new DictionaryManyModel();
				List dmmList=new ArrayList();
				dmm.setKey(keys[i]);
				if(queryResult!=null&&queryResult.size()>0){
					for(int x=0;x<queryResult.size();x++){
						Dictionary dic=JSONUtils.toBean(JSONUtils.toJson(queryResult.get(x)),Dictionary.class);
						if(dic.getKey().equals(dmm.getKey())){//相等
							dmmList.add(dic);
						}
					}
				}
				dmm.setInfo(dmmList);
				DictionaryHold.add(dmm);
			}
			
			return ResultUtil.success(DictionaryHold, "success");
		}
		return ResultUtil.error(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
	}
}
