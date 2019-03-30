package com.yuntongxun.itsys.base.dao;
import com.yuntongxun.itsys.base.common.exception.DaoException;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.HospitalReview;
import com.yuntongxun.itsys.base.vo.TodoVo;

import java.util.List;

public interface HiReviewDao {
	// 查询 未完成危险因素调查表 受试者列表
	public ListPageUtil notEntryRiskfactors(int dept_id, int reserve_status, TodoVo vo) throws ItSysException;
	//查询 未完成危险因素调查表(按照地区查询)
	public ListPageUtil notEntryRiskfactors1(int areaId,int reserve_status, TodoVo vo) throws ItSysException;
	// 根据社区id查询所对应的人数
	public List getGroupBycounts(int communityDeptId, TodoVo vo,String userName) throws DaoException;
	// 根据市区id查询所对应社区的人数
	public List getGroupByAreaId(int areaId, TodoVo vo) throws DaoException;
	// 按照地区查询所属社区未完成因素调查表
	public List notRiskFactorsByAreaId(int areaId, int reserve_status, TodoVo vo) throws DaoException;
	//国家查询受试者列表
	public ListPageUtil queryReviewByNationIdPage(HospitalReview person,int nationId);
	//根据国家id查询所辖地区医院
	public List getAreaByNationId(int nationId);

	//根据地区id查询所辖地区医院
	public List getAreaByAreaId(int areaId);
	//根据id查询国家受试者详情
	public List getNationListByid(String sid);
	//根据sid查询fit列表
	public List getFitListByNationId(String sid);
	//根据sid查询粪便DNA列表
	public List getStollDNAListByNationId(String sid);
	//根据sid查询结肠镜列表
	public List getColonoscopyRecordBy(String sid);
	//根据sid查询生物样本
	public List getSampleResult(String sid);
	//根据id查询生物样本基本信息
	public List getSampleDetailById(int id);
	//国家统计按照地区医院各组人数
	public List getGroupSumsByNationId(int nationId, TodoVo vo);
    //国家统计按照地区各组人数
	public List getGroupSumsByNationId1(int nationId, TodoVo vo);

	List getGroupSumsByAreaId(int areaId, TodoVo vo);

	ListPageUtil queryReviewByNationIdPageByG(HospitalReview person, int nationId);
    //国家统计病变结果各组人数
    List lesionStatisticsByNation(int nationId, TodoVo vo);
    //地区统计病变结果各组人数
    List lesionStatisticsByArea(int areaId, TodoVo vo);
}
