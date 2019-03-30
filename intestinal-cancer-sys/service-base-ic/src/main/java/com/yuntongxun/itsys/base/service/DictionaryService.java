package com.yuntongxun.itsys.base.service;

import com.yuntongxun.itsys.base.common.exception.DaoException;
import com.yuntongxun.itsys.base.common.exception.ServiceException;
import com.yuntongxun.itsys.base.po.Dictionary;
import com.yuntongxun.itsys.base.vo.Result;
/**
 * 数据字典接口
 * @author Lake.zhang
 *
 */
public interface DictionaryService {
	
	//根据条件查询数据字典 带分页
	Result queryDictionary(String body)throws ServiceException,DaoException;
	//根据id查询数据字典
	Result getDictionary(String id)throws ServiceException,DaoException;
	//新增数据字典
	Result insertDictionary(Dictionary dictionary)throws ServiceException,DaoException;
	//修改数据字典
	Result updateDictionary(Dictionary dictionary)throws ServiceException,DaoException;
	//删除数据字典
	Result delDictionary(String id)throws ServiceException,DaoException;
	
	void queryDictionaryOnCache() throws ServiceException,DaoException;
	
	Result queryByKeyArray(String [] keys);
}
