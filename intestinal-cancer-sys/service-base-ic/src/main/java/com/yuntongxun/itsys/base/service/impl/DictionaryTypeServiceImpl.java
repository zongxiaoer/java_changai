package com.yuntongxun.itsys.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yuntongxun.itsys.base.common.exception.ServiceException;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.ResultUtil;
import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.dao.DictionaryTypeDao;
import com.yuntongxun.itsys.base.po.DictionaryType;
import com.yuntongxun.itsys.base.service.DictionaryTypeService;
import com.yuntongxun.itsys.base.vo.Result;

@Service
public class DictionaryTypeServiceImpl implements DictionaryTypeService {

	@Autowired
	private DictionaryTypeDao typedao;
	private final Logger log = LogManager.getLogger(DictionaryTypeServiceImpl.class.getName());

	@Override
	public Result query(String body) throws ServiceException {
		log.info("@Service 数据字典类型query Start  ");
		JsonObject json = new JsonParser().parse(body).getAsJsonObject();
		String id = json.get("id") == null ? "" : json.get("id").getAsString();
		String enname = json.get("enName") == null ? "" : json.get("enName").getAsString();
		String cnname = json.get("cnName") == null ? "" : json.get("cnName").getAsString();
		int pageNo = json.get("pageNo") == null ? 0 : json.get("pageNo").getAsInt();
		int pageSize = json.get("pageSize") == null ? -1 : json.get("pageSize").getAsInt();
		ListPageUtil listPage = typedao.query(id, enname, cnname, pageNo, pageSize);
		log.info("@Service 数据字典类型query end  ");

		return ResultUtil.success(listPage.getResultList(), listPage.getPageInfo());
	}

	@Override
	@Transactional
	public Result insert(String body) throws ServiceException {
		log.info("@Service 数据字典类型insert Start  ");
		if (!StringUtil.isEmpty(body)) {
			DictionaryType type = (DictionaryType) JSONUtils.JsonToObject(body, DictionaryType.class);
			typedao.insert(type);
		}
		log.info("@Service 数据字典类型insert End  ");
		return ResultUtil.success("success");
	}

	@Override
	@Transactional
	public Result update(String body) throws ServiceException {
		log.info("@Service 数据字典类型update Start  ");
		if (!StringUtil.isEmpty(body)) {
			DictionaryType type = (DictionaryType) JSONUtils.JsonToObject(body, DictionaryType.class);
			String id = type.getId() + "";
			if (StringUtil.isEmpty(id)) {
				return ResultUtil.error(GlobalErrorCode.ERR_DICTIONARY_TYPE_ID_NULL_CODE, GlobalErrorCode.ERR_DICTIONARY_TYPE_ID_NULL_MSG,
						null);
			}
			List list = typedao.get(id);
			if (list.size() == 0) {
				return ResultUtil.error(GlobalErrorCode.ERR_DICTIONARY_TYPE_NULL_CODE, GlobalErrorCode.ERR_DICTIONARY_TYPE_NULL_CODE,
						null);
			}
			typedao.update(type);
		}
		log.info("@Service 数据字典类型update End  ");
		return ResultUtil.success("success");
	}

	@Override
	@Transactional
	public Result del(String id) throws ServiceException {
		log.info("@Service 数据字典类型del Start  ");
		if (!StringUtil.isEmpty(id)) {
			typedao.del(id);
		} else {
			return ResultUtil.error(GlobalErrorCode.ERR_DICTIONARY_TYPE_ID_NULL_CODE, GlobalErrorCode.ERR_DICTIONARY_TYPE_ID_NULL_MSG,
					null);
		}
		log.info("@Service 数据字典类型del End  ");
		return ResultUtil.success("success");
	}

	@Override
	public Result get(String id) throws ServiceException {
		log.info("@Service 数据字典类型get Start  ");
		List list = new ArrayList();
		if (!StringUtil.isEmpty(id)) {
			list = typedao.get(id);
		} else {
			return ResultUtil.error(GlobalErrorCode.ERR_DICTIONARY_TYPE_ID_NULL_CODE, GlobalErrorCode.ERR_DICTIONARY_TYPE_ID_NULL_MSG,
					null);
		}
		Object obj=null;
		if(list.size()>0&&list!=null){
			obj=list.get(0);
		}
		log.info("@Service 数据字典类型get end  ");
		return ResultUtil.success(obj, null);
	}

}
