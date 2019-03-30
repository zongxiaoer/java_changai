package com.yuntongxun.itsys.base.dao;

import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.Operation;

public interface OperationDao {
	
	public void saveOperation(Operation op);
	
	public ListPageUtil getOperation(String user,String startTime,String endTime,int pageNo,int pageSize) throws ItSysException;
}
