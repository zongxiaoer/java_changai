package com.yuntongxun.itsys.base.service;

import com.yuntongxun.itsys.base.common.exception.DaoException;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.exception.ServiceException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.HospitalFitResult;
import com.yuntongxun.itsys.base.vo.Result;
import com.yuntongxun.itsys.base.vo.TodoVo;

public interface HFitResultService {
	//查询 未录入FIT编号 受试者列表接口
	public ListPageUtil notEntryFitCodeList(TodoVo vo, String loginName) throws ItSysException;
	//查询 未录入FIT结果 受试者列表
	public ListPageUtil notEntryFitResultList(TodoVo vo, String loginName) throws ItSysException;	
	//查看FIT结果接口；
	public HospitalFitResult getFitResultByid(int id)throws ServiceException,DaoException;
	//新增FIT结果
	public Result insertFitResult(HospitalFitResult fitResult)throws ServiceException,DaoException;
	//根据市区id查询未录入FIT编号
	public Result notFitCodeByAreaIdList(TodoVo vo, String loginName);
	//根据市区id查询未录入FIT结果 列表
	public Result notFitResultByAreaIdList(TodoVo vo, String loginName) throws ItSysException;	
	
}
