package com.yuntongxun.itsys.base.service;

import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.Operation;

public interface OperationService {
	
	public void saveOperation(Operation op) throws ItSysException;
	
	public ListPageUtil getOperation(String body) throws ItSysException;
}
