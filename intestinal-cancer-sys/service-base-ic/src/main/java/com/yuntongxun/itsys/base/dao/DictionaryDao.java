package com.yuntongxun.itsys.base.dao;

import java.util.List;

import com.yuntongxun.itsys.base.common.exception.DaoException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.Dictionary;
/**
 * 数据字典接口
 * @author Lake.zhang
 *
 */
public interface DictionaryDao {
	
	//根据条件查询数据字典 带分页
	ListPageUtil queryDictionary(String key,int pageNo,int pageSize)throws DaoException;
	//根据id查询数据字典
	Dictionary getDictionary(String id)throws DaoException;
	//新增数据字典
	void insertDictionary(Dictionary dictionary)throws DaoException;
	//修改数据字典
	void updateDictionary(Dictionary dictionary)throws DaoException;
	//删除数据字典
	void delDictionary(String id)throws DaoException;
	
	List queryByKeyArray(String[] keys);
}
