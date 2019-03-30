package com.yuntongxun.itsys.base.service;

import java.util.List;

import com.yuntongxun.itsys.base.common.exception.ServiceException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.HospitalReview;
import com.yuntongxun.itsys.base.vo.Result;
import com.yuntongxun.itsys.base.vo.TodoVo;

public interface HiReviewService {
	//未完成危险因素调查表
	public ListPageUtil notEntryRiskfactors(TodoVo vo, String loginName) throws ServiceException;	
	//入组的人数
	public String getGroupBycounts(String loginName,TodoVo vo);
	//根据市区id查询社区所有入组的人数
	public String getGroupByAreaIds(String loginName,TodoVo vo);
	//根据市区id查询所对应社区未完成危险因素调查表
	public String notRiskFactorsByAreaId(TodoVo vo, String loginName);
	//查询国家受试者资格列表
	public ListPageUtil queryReviewByNationIdPage(HospitalReview person, String loginName);
	//根据国家id查询所对应的地区
	public Result getAreaByNationId(int nationId);
	//根据id查询患者基本信息
	public Result getAreaListById(String sid);
	//根据sid查询fit列表
	public Result getFitListByNationId(String sid);
	//根据sid查询DNA列表
	public Result getStollDNABySid(String sid);
	//根据sid查询国家受试者结肠镜列表
	public Result getColonoscopyBySid(String sid);
	//根据sid查询生物样本
	public String getSampleType(String sid);
	//根据id查询生物样本详情
	public Result getSampleDetailById(int id);
	//国家统计按照地区统计各组人数
	public String getGroupSumsByNationId(String loginName,TodoVo vo);
    //国家统计病变结果各组人数
    String lesionStatisticsByNation(String loginName, TodoVo vo);
    //地区统计病变结果各组人数
    String lesionStatisticsByAreaId(String loginName, TodoVo vo);
}
