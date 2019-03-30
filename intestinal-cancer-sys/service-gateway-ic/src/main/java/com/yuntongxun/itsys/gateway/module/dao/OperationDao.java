package com.yuntongxun.itsys.gateway.module.dao;

import java.util.List;

import com.yuntongxun.itsys.gateway.module.pojo.Operation;

public interface OperationDao {
	
	public void saveOperation(Operation op);
	
	public List getResourceByUrl(String url);
}
