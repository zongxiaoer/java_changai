package com.yuntongxun.itsys.base.service;

import com.yuntongxun.itsys.base.common.exception.ServiceException;
import com.yuntongxun.itsys.base.vo.Result;

public interface DictionaryTypeService {
	
	public Result query(String body) throws ServiceException;
	
	public Result insert(String body) throws ServiceException;
	
	public Result update(String body) throws ServiceException;
	
	public Result del(String id) throws ServiceException;
	
	public Result get(String id) throws ServiceException;
	
}
