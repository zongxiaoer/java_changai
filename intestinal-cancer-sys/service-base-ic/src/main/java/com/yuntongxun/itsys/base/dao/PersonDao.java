/**
 * Project Name:service-base-yl
 * File Name:PersonDao.java
 * Package Name:com.yuntongxun.itsys.base.dao
 * Date:2018年4月9日下午6:32:16
 * Copyright (c) 2018, ty All Rights Reserved.
 */

package com.yuntongxun.itsys.base.dao;

import java.util.List;

import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.*;
import com.yuntongxun.itsys.base.po.dto.HospitalRiskFactorDto;
import com.yuntongxun.itsys.base.po.dto.quitlog.QuitInResultDto;
import com.yuntongxun.itsys.base.po.dto.quitlog.QuitSearchDto;
import com.yuntongxun.itsys.base.vo.HospitalBiologicalSampleResultVo;
import com.yuntongxun.itsys.base.vo.IntestineReason;

/**
 * ClassName:PersonDao <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年4月9日 下午6:32:16 <br/>
 *
 * @author ty
 * @see
 * @since JDK 1.8
 */
public interface PersonDao {

    /**
     * 查询受试者列表
     * query:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @param person
     * @return
     * @author ty
     * @since JDK 1.8
     */
    public List<HospitalReview> query(HospitalReview person);

    public HospitalReview queryByCardId(String CardId);

    public void quit(String sid);

    /**
     * 退出研究更新受试者状态
     *
     * @param intestineReason
     * @return
     */
    List<QuitInResultDto> updateState(List<HospitalReview> list, IntestineReason intestineReason);

    /**
     * 重新入组受试者更新状态以及待办
     *
     * @param intestineReason
     */
    void reresearchUpdateState(List<HospitalReview> hospitalReviews, IntestineReason intestineReason);

    /**
     * 查询个人的基本信息
     *
     * @param sid
     * @return
     */
    HospitalReview findInfoBySid(String sid);

    /**
     * 通过Sid查询T0阶段FIT检查信息
     *
     * @param sid
     * @return
     */
    List<HospitalFitResult> findFitBySidAndState(String sid);

    /**
     * 通过Sid查询T0阶段DNA信息
     *
     * @param sid
     * @return
     */
    List<HospitalStoolDNA> findDNABySidAndState(String sid);

    /**
     * 通过sid查询生物样本信息
     *
     * @param sid
     * @return
     */
    List<HospitalBiologicalSampleResultVo> findSamPle(String sid);

    /**
     * 通过Sid查询T0阶段结肠镜检查记录信息
     *
     * @param sid
     * @return
     */
    List<HospitalColonoscopyRecord> findRecordBySidAndState(String sid);

    /**
     * 通过Sid查询T0阶段异常事件记录信息
     *
     * @param sid
     * @return
     */
    List<HospitalAbnormalEvent> findAbnormalBySidAndState(String sid);

    /**
     * 更新危险等级
     *
     * @param sid
     * @return
     */
    int updateRiskLevelBySid(Integer level, String sid,Integer score);

    /**
     * sid更新危险因素收集状态
     *
     * @param sid
     * @return
     */
    int updateRiskStatusBySid(String sid, Integer status);

    /**
     * 新增待办事件表
     *
     * @param hospitalTodoEvent
     * @return
     */
    int addTodoEvent(HospitalTodoEvent hospitalTodoEvent);

    /**
     * 新增一条危险因素记录
     *
     * @param hospitalRiskFactor
     * @return
     */
    int addRiskFactor(HospitalRiskFactor hospitalRiskFactor);

    /**
     * sid查询危险因素
     *
     * @param sid
     * @return
     */
    HospitalRiskFactor findHospitalRiskFactorBySid(String sid);


    /**
     * 根据sid更新代办状态
     *
     * @param sid
     */
    void updateTodoEventByType(String sid);

    /**
     * 根据登录名获取
     * getIdByLoginName:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @param loginName
     * @return
     * @author ty
     * @since JDK 1.8
     */
    public Integer getIdByLoginName(String loginName);

    public Integer getIdByUserId(Integer userId);

    public Integer getAreaIdBydepId(Integer depMebId);

    public void addGroupRecord(Integer areaId, Long personNumber);

    public String getGroupRuleByPNumber(int personNumber, Integer groupRuleId);

    public Integer getSiteIdByAreaId(Integer areaId);

    public int addReview(HospitalReview review, String table);

    public int addColonoscopyResult(HospitalColonoscopyResult result);

    public void addTodoEvent(HtEvent todo);

    public HospitalReview getBySid(String sid);

    public int addStoolDna(StoolDna dna);

    public int addFitResult(HospitalFitResult fitResult);

    public ListPageUtil queryPage(HospitalReview person);

    /**
     * 根据机构id查询分组记录表
     * getPersonNumByAreaId:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @param areaId
     * @return
     * @author ty
     * @since JDK 1.8
     */
    public List<String> getPersonNumByAreaId(Integer areaId);

    /**
     * 根据机构id更新分组记录表
     * getPersonNumByAreaId:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @param areaId
     * @return
     * @author ty
     * @since JDK 1.8
     */
    public void updateGroupRecord(Integer areaId, Long personNumber);

    /**
     * 更新违反方案状态
     *
     * @param sid
     * @return
     */
    int updateViolationPlanStatus(Integer status, String sid, String stageCy);

    /**
     * 更新总体状态
     *
     * @param status
     * @param sid
     * @param stageCy
     * @return
     */
    int updateOverallStatus(Integer status, String sid, String stageCy);

    /**
     * 添加结肠镜检查记录
     * addColonoscopyRecord:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @param result
     * @return
     * @author ty
     * @since JDK 1.8
     */
    public int addColonoscopyRecord(HospitalColonoscopyRecord result);

    public HospitalColonoscopyRecord findRecordByRecordId(Integer id);

    public List<HospitalReview> queryExcel(HospitalReview person);

    public List<HospitalReview> queryBySidAreaDeptId(String sid, Integer area_dept_id, Integer community_dept_id,String userName);

    public List<HospitalReview> queryAreaBySid(String sid, Integer area_dept_id);

    public List<HospitalReview> queryByAreaId(String sid, Integer area_dept_id);

    List<IdentityBlacklistPO> checkIdentityBlacklist(String ident, String type);

    ListPageUtil queryQuitLog(QuitSearchDto quitSearchDto, ItsysDepartment itsysDepartment, Integer HOSPITAL_TYPE);

    List<HospitalBiologicalSampleResultVo> findSampleBySid(String sid);

    List getHospitalReviewBySid(String sid);

    ListPageUtil queryQuitList(QuitSearchDto quitSearchDto, ItsysDepartment itsysDepartment, Integer HOSPITAL_TYPE);

    Integer saveScheme(HospitalReview hospitalReviews, QuitInResultDto quitResultDtos);

	public List<HospitalReview> queryBySidIDAndAreaDeptId(String sid, Integer area_dept_id);

    void updateReview(HospitalReview review, String hospital_intestine_review);

    List<HospitalRiskFactorDto> findRiskfactorById(Integer id);

    List<HospitalColonoscopyRecord> queryRecordByIdAndOperation(Integer id, String cgSystemSaveHospitalIntestineReview);

    int deleteEventBySourceIdAndType(Integer id, String cgSystemSaveHospitalIntestineReview, Integer personTodoEventType5);

    int deleeBySourceId(Integer id, String cgSystemSaveHospitalIntestineReview,String table);


    List<StoolDna> queryDNAByIdAndOperation(Integer id, String cgSystemSaveHospitalIntestineReview);

    List<HospitalBiologicalSampleResultVo> querySampleByIdAndOperation(Integer id, String cgSystemSaveHospitalIntestineReview);

    List<HospitalFitResult> queryFitByIdAndOperation(Integer id, String cgSystemSaveHospitalIntestineReview);

    int updateRiskFactor(HospitalRiskFactor hospitalRiskFactor);

    void updateEdirStatus(String applyEditStatus1, String editStatus1, Object o, Integer id, String table);

    Integer queryByCommunityDeptId(Integer communityId,Integer areaId);

    int updateSroceRiskFactor(HospitalRiskFactor hospitalRiskFactor);


    int updateHospitalIntestineReview(HospitalRiskFactor hospitalRiskFactor);

    void addTodoEventList(List<HtEvent> todoList);

    ListPageUtil queryReviewByNationIdPageByG(HospitalReview person);

    HospitalColonoscopyRecord findRecordByResultId(Integer id);

    HospitalColonoscopyRecord findRecordByPathologyId(Integer id);

    HospitalColonoscopyRecord findRecordByNotificationId(Integer id);

    HospitalColonoscopyRecord findRecordById(int communityDeptId);
}

