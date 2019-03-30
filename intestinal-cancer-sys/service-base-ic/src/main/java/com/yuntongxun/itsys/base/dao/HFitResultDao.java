package com.yuntongxun.itsys.base.dao;

import java.util.List;

import com.yuntongxun.itsys.base.common.exception.DaoException;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.HospitalFitResult;
import com.yuntongxun.itsys.base.vo.TodoVo;

public interface HFitResultDao {
	//查询 未录入FIT编号 受试者列表
	public ListPageUtil notEntryFitCodeList(int dept_id,int code_entry_status,TodoVo vo) throws ItSysException;
	// 查询 未录入fit编号，按照地区查询
	public ListPageUtil notEntryFitCodeList1(int areaId,int code_entry_status,TodoVo vo) throws ItSysException;
	//查询 未录入FIT结果 受试者列表
	public ListPageUtil notEntryFitResultList(int dept_id,int insert_status,TodoVo vo) throws ItSysException;
	//查询 未录入fit结果 按照地区查询
	public ListPageUtil notEntryFitResultList1(int areaId,int insert_status,TodoVo vo) throws ItSysException;
	//根据id查询详情
	public HospitalFitResult get(int id) throws DaoException;
	//数据新增fit
	public void insert(HospitalFitResult fitResult) throws DaoException;
	//根据市区id查询未录入FIT编号受试者列表
	public List notFitCodeByAreaIdList(int areaId,int code_entry_status,TodoVo vo) throws ItSysException;
	//根据市区id查询未录入FIT结果
	public List notFitResultByAreaIdlist(int areaId,int insert_status,TodoVo vo) throws DaoException;
}
