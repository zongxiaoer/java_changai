package com.yuntongxun.itsys.base.dao;

import java.util.List;

import com.yuntongxun.itsys.base.common.exception.DaoException;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.AllocationTodo;
import com.yuntongxun.itsys.base.po.HospitalColonoscopyRecord;
import com.yuntongxun.itsys.base.po.HospitalReview;
import com.yuntongxun.itsys.base.po.ItsysDepartment;
import com.yuntongxun.itsys.base.po.dto.allocation.*;
import com.yuntongxun.itsys.base.vo.*;

/**
 * 社区医生首页的未过期预约分配
 * @author liugb
 * Date 2018 4 16
 */
public interface HcrAllocationDao {
		
	public ListPageUtil queryAllocation(int communityDeptId ,int pageNo,int pageSize) throws ItSysException;
	//市区放号接口
	public void insert(AllocationTodo allocationTodo,int areaId) throws DaoException;
	//根据市区id查询所有的社区信息
	public List<ItsysDepartmentVo> getByAreaId(int id) throws DaoException;
	//根据市区id查询所有的社区信息详情
	public ListPageUtil getlistByAreaId(Integer areaDeptId,TodoVo vo) throws DaoException;

	/**
	 * @func
	 * @desc	开始时间、结束时间、地区di、社区Id查询预约记录信息
	 * @author zongt
	 * @create 2018/4/下午12:05:05
	 * @request
	 * @response
	 **/
	ListPageUtil queryColonoscopyReservationAllocation(String startTime,String endTime,String communityDeptId,Integer areaDeptId ,int pageNo,int pageSize);
	//根据市区id查询所对应的社区
	public List getcommdeptsByAreaId(int areaId) throws DaoException;


	List<HospitalColonoscopyRecord> queryByCommunityDeptId(String communityDeptId);
	//根据条件查询地区放号情况一览表
	public ListPageUtil queryPutCodeByAreaId(Integer areaId,TodoVo vo);
	//根据条件查询该社区和预约时间段之内的详情
	public ListPageUtil queryPutCodeDetailListByCommitId(ColonoscopyQueryResult queryparam);
	//地区一览表导出
	public List<AreaListForExcelVo> queryForAreaExcel(Integer areaId,TodoVo vo);

	ListPageUtil queryAllocationInArea(int communityDeptId, int pageNo, int pageSize);

    void save(AllocationDto allocationDto1);

    ListPageUtil queryRule(HosAllocationRuleInfoDto hosAllocationRuleInfoDto);

    List<DoctorInfo> queryCountryByRuleid(String ruleId);


    void updateRule(Integer id,String  areaid);

	void updateAllocationS(Integer id,String  areaid);

	List<HospitalReview> queryPhoneByRuleId(Integer id);

	List<HosAllocationRuleDeptInfoDto>  queryPutCodeByRuleId(int areaId, TodoVo vo);

	ListPageUtil queryPutCodeByRuleId1(int areaId, TodoVo vo);

    List<ResultAllocation> queryByRuleIdAndDate(String date, String ruleId);

	Integer queryDetailByRuleIdAndDate(String date, String ruleId);

	List<ResultAllocation> queryRuleById(String ruleId);

	int queryNumByRuleIdAndDate(String ruleId, String reservationDateToString, String communityDeptId);

	List<HosAllocationRuleDeptInfoDto> queryRuleDeptByRuleid(String ruleId);

	int querySumNumBy(String ruleId, String reservationDateToString,String communityDeptId);

	ListPageUtil queryEntityByRuleIdAndRule(String ruleId, String reservationDateToString, Integer communityDeptId,Integer pageNo,Integer pageSize);

	ListPageUtil queryPerSonByRuleIdAndRule(TodoVo vo);

	ListPageUtil queryFanghao(TodoVo vo);

	int queryNumByAreaIdAndDate(String area_dept_id, String reservation_date,Integer usestatus);

	List<AllocationDto> queryEntityByAreaIdAndDate(String area_dept_id, String reservation_date);

    List<ColonoscopyDto> queryEntrtyByRuleId(ColonoscopyDto colonoscopyDto);

	void updateAllocationSByDate(String reservationDate, Integer area_dept_id);


	ListPageUtil queryPerSonByRuleIdAndRuleByTingZhen(TodoVo vo);

	List<AllocationDto> queryAllocationByRuleIdAndDate(TodoVo vo);


	void saveByFuBiao(AllocationDto allocationDto1);

	void updateAllocationSALLDAy(Integer id,String area_dept_id);

	void updateAllocationSByALLDay(String reservationDate, Integer area_dept_id);

	int queryByUseStatus(AllocationDto allocationDto1);

	int queryFanghaoNum(TodoVo vo);

	List<AllocationDto> queryEntityByAreaIdAndDateNo(String area_dept_id, String reservation_date,Integer usestatus);

	List<ResultAllocation> queryByRuleIdAndDateByUseStatus(String reservationDateToString, String ruleId);
}
