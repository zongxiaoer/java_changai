package com.yuntongxun.itsys.base.dao;

import java.util.List;

import com.yuntongxun.itsys.base.common.exception.DaoException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.DictionaryType;

public interface DictionaryTypeDao {

	public ListPageUtil query(String id,String enname,String cnname,int pageNo,int pageSize) throws DaoException;

	public void insert(DictionaryType type) throws DaoException;

	public void update(DictionaryType type) throws DaoException;

	public void del(String id) throws DaoException;

	public List get(String id) throws DaoException;
}
