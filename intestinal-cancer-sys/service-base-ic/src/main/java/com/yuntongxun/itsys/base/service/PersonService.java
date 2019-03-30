/**
 * Project Name:service-base-yl
 * File Name:PersonService.java
 * Package Name:com.yuntongxun.itsys.base.service
 * Date:2018年4月9日下午6:28:46
 * Copyright (c) 2018, ty All Rights Reserved.
 */

package com.yuntongxun.itsys.base.service;

import java.util.List;

import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.*;
import com.yuntongxun.itsys.base.po.dto.HospitalRiskFactorDto;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
import com.yuntongxun.itsys.base.po.dto.quitlog.QuitSearchDto;
import com.yuntongxun.itsys.base.vo.IntestineReason;
import com.yuntongxun.itsys.base.vo.Result;

/**
 * ClassName:PersonService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年4月9日 下午6:28:46 <br/>
 * @author ty
 * @version
 * @since JDK 1.8
 * @see
 */
public interface PersonService {

    /**
     *
     * query:(查询受试者列表). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author ty
     * @param person
     * @return
     * @since JDK 1.8
     */
    public List<HospitalReview> query(HospitalReview person, String loginName);

    public void quit(String sid);

    /**
     * 验证身份证号
     * checkIdCard:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author ty
     * @param idcard
     * @return
     * @since JDK 1.8
     */
    public int checkIdCard(String idcard,boolean isok);

    /**
     * 提交资格审核
     * personInsert:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author ty
     * @param review
     * @param loginName
     * @return
     * @since JDK 1.8
     */
//	public ReviewResult personInsert(HospitalReview review, String loginName);
    public ReviewResult personInsert_test(HospitalReview review, String loginName);


    /**
     * 受试者退出研究
     * @param intestineReason
     * @return
     */
    Result quitResearch(IntestineReason intestineReason);

    /**
     * 受试者详情
     * @param sid
     * @return
     */
    Result queryPersonDetail(String sid);

    /**
     * 新增危险因素
     * @param hospitalRiskFactor
     * @return
     */
    Result addHospitalRiskfactor(HospitalRiskFactor hospitalRiskFactor, String loginName);


    /**
     * sid查询危险因素
     * @param hospitalRiskFactor
     * @return
     */
    Result findRiskfactorBySid(String sid);



    /**
     * 短信通知受试者
     * @param sid
     * @return
     */
    Result sendMesage(String sid);

    /**
     * 肠镜短信通知受试者
     * @param sid
     * @return
     */
    Result sendColonoscopyMesage(String sid);

    /**
     * 查询受试者列表
     * queryPage:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author ty
     * @param person
     * @return
     * @since JDK 1.8
     */
    public ListPageUtil queryPage(HospitalReview person, String loginName);


    public HospitalReview getBySid(String sid);

    /**
     * 查询地区医院受试者列表
     * queryAreaPage:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author ty
     * @param person
     * @param loginName
     * @return
     * @since JDK 1.8
     */
    public ListPageUtil queryAreaPage(HospitalReview person, String loginName);

    /**
     * 查看受试者个人信息
     * @param sid
     * @return
     */
    public Result queryPersonInfo(String sid, String loginName);

    /**
     * 校验受试者手机号
     * checkPhone:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author ty
     * @param phone
     * @since JDK 1.8
     */
    public void checkPhone(String phone);

    public List<HospitalReview> queryExcel(HospitalReview person, String loginName);

    /**
     * 重新入组
     * @param intestineReason
     * @return
     */
    Result reresearch(IntestineReason intestineReason);

    List<HospitalReview> queryBySidID(String sid, Integer area_dept_id, Integer community_dept_id,String userName);

    List<HospitalReview> queryAreaBySidID(String sid, Integer area_dept_id);

    List<HospitalReview> queryByAreaID(String sid, Integer area_dept_id);


    boolean checkIdentityBlacklist(String identity, String type);

    ListPageUtil queryQuitLog(QuitSearchDto quitSearchDto, ItsysDepartment itsysDepartment,Integer HOSPITAL_TYPE);

    ListPageUtil queryQuitList(QuitSearchDto quitSearchDto, ItsysDepartment itsysDepartment,Integer HOSPITAL_TYPE);

    List<HospitalReview> queryBySidIDAndAreaDeptId(String sid, Integer areaDeptId1);

    void update(HospitalReview review,HospitalReferenceRecordDto hospitalReferenceRecordDto,boolean isok);

    List<HospitalRiskFactorDto> findRiskfactorById(Integer id);

    void updateHospitalRiskfactor(HospitalRiskFactorDto hospitalRiskFactorDto, HospitalRiskFactor hospitalRiskFactor, HospitalReferenceRecordDto hospitalReferenceRecordDto);
}
