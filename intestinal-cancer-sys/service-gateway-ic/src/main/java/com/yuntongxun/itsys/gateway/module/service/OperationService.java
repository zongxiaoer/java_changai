package com.yuntongxun.itsys.gateway.module.service;

import java.util.List;

import com.yuntongxun.itsys.gateway.module.pojo.Operation;

public interface OperationService {
	
	public void saveOperation(Operation op);
	public List getResourceByUrl(String url);
	
}
