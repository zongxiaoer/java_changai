/**
 * Project Name:service-base-yl
 * File Name:PersonService.java
 * Package Name:com.yuntongxun.itsys.base.service.impl
 * Date:2018年4月9日下午6:29:22
 * Copyright (c) 2018, ty All Rights Reserved.
 */

package com.yuntongxun.itsys.base.service.impl;

import java.util.*;

import com.yuntongxun.itsys.base.common.util.*;
import com.yuntongxun.itsys.base.dao.*;
import com.yuntongxun.itsys.base.po.*;
import com.yuntongxun.itsys.base.po.dto.HospitalRiskFactorDto;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
import com.yuntongxun.itsys.base.po.dto.quitlog.QuitInResultDto;
import com.yuntongxun.itsys.base.po.dto.quitlog.QuitSearchDto;
import com.yuntongxun.itsys.base.po.dto.sidrule.DepartmentSidRuleDto;
import com.yuntongxun.itsys.base.service.ColonoscopyService;
import com.yuntongxun.itsys.base.service.UserService;
import com.yuntongxun.itsys.base.vo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.yuntongxun.itsys.base.cache.RedisManager;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.constans.RedisConstant;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.po.HospitalReview;
import com.yuntongxun.itsys.base.po.HtEvent;
import com.yuntongxun.itsys.base.po.ReviewResult;
import com.yuntongxun.itsys.base.po.StoolDna;
import com.yuntongxun.itsys.base.service.PersonService;
import com.yuntongxun.itsys.base.vo.DoctorInfo;

/**
 * ClassName:PersonService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年4月9日 下午6:29:22 <br/>
 *
 * @author ty
 * @see
 * @since JDK 1.8
 */
@Service
public class PersonServiceImpl implements PersonService {


    @Autowired
    private SidRuleDao sidRuleDao;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisManager redis;

    private final Logger log = LogManager.getLogger(PersonServiceImpl.class);

    @Autowired
    private SendSms sendSms;

    @Autowired
    private BiologSampleDao biologSampleDao;

    @Autowired
    private ColonoscopyService colonoscopyService;

    @Autowired
    private ReserveDetailDao reserveDetailDao;


    @Autowired
    private HospitalReferenceRecordDao hospitalReferenceRecordDao;

    public static final int COLONOSCOPY_RESERVE_STATUS_NOT_RESERVED = 1;

    public static final Integer STAGE_T0 = 1;

    public static final int EVENT_STATUS_NOT_DO = 1;

    public static final int EVENT_TYPE_5 = 5;

    public static final int EVENT_TYPE_4 = 4;

    @Autowired
    private HtEventDao eventDao;


    @Autowired
    private ColonoscopyDao colonoscopyDao;

    @Override
    public List<HospitalReview> query(HospitalReview person, String loginName) {
        Object[] arr = getRedisKeyByLoginName(loginName);
        Integer communityId = (Integer) arr[0];//社区id
        Integer areaId = (Integer) arr[1];//地区
        person.setCommunityDeptId(communityId);
        person.setAreaDeptId(areaId);
        List<HospitalReview> list = personDao.query(person);
        log.info("@Service-person-query  根据条件查询受试者结果和条数  参数：list={},size={}.", list == null ? null : list.toString(), list == null ? 0 : list.size());
        return list;
    }

    @Override
    @Transactional
    public void quit(String sid) {
        personDao.quit(sid);
    }

    /**
     * 身份证校验
     * TODO 简单描述该方法的实现功能（可选）.
     *
     * @see com.yuntongxun.itsys.base.service.PersonService
     */
    @Override
    public int checkIdCard(String idCard, boolean isok) {
        //获取生日信息，判断年龄是否符合
        int age = getAge(idCard);//获取受试者年龄
        //根据身份证号查询受试者资料
        if (isok) {
            queryReviewByIdCard(idCard);//查询受试者资料
        }
        return age;
    }

    /**
     * getAge:(根据身份证号获取年龄). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @param idcard
     * @return
     * @author ty
     * @since JDK 1.8
     */
    private int getAge(String idcard) {
        int length = idcard.length();//身份证号长度
        int age = 0;
        String birth;
        log.info("@Service-person-checkIdCard  身份证号长度  参数：length={}.", length);
        if (length == 18) {//长度是18位
            birth = idcard.substring(6, 14);
        } else {
            birth = "19" + idcard.substring(6, 11);//拼接15位身份证号生日
        }
        log.info("@Service-person-checkIdCard  受试者生日是 ：birth={}.", birth);
        Date date = DateUtil.formatDateStr(birth, "yyyyMMdd");
        try {
            age = DateUtil.getAge(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("@Service-person-checkIdCard  受试者年龄是 ：age={}.", age);
        if (age < 50 || age > 74) {
            throw new ItSysException(
                    GlobalErrorCode.ERR_PERSON_AGE_EROOR_CODE, GlobalErrorCode.ERR_PERSON_AGE_EROOR_MSG);
        }
        return age;
    }

//    /**
//     * 添加受试者
//     * TODO 简单描述该方法的实现功能（可选）.
//     *
//     * @see com.yuntongxun.itsys.base.service.PersonService#personInsert(com.yuntongxun.itsys.base.po.HospitalReview, java.lang.String)
//     */
//    @Transactional
//    @Override
//    public ReviewResult personInsert(HospitalReview review, String loginName) {
//        ReviewResult results = new ReviewResult();
//        Long personNumber = 0l;
//        Long siteIdNum = 0l;
//        Integer siteId = null;
//        String personKey = "";
//        try {
//            String idCard = review.getIdCard();//获取身份证号
//            int age = getAge(idCard);//获取受试者年龄
//            queryReviewByIdCard(idCard);
//            Integer item2 = review.getItem2();
//            Integer item3 = review.getItem3();
//            Integer item4 = review.getItem4();
//            Integer item5 = review.getItem5();
//            Integer item6 = review.getItem6();
//            Integer item7 = review.getItem7();
//            Integer item8 = review.getItem8();
//            Integer item9 = review.getItem9();
//            Integer item10 = review.getItem10();
//            String items = "" + item2 + item3 + item4 + item5 + item6 + item7 + item8 + item9 + item10;
//            log.info("@Service-person-personInsert 2-10项的字段值  参数：items={}.", items);
//
//            //判断2-10项是否全为2
//            if (!"222222222".equals(items)) {
//                throw new ItSysException(
//                        GlobalErrorCode.PARAMETER_ERR_CODE, "纳入标准2-10项的字段必须为2");
//            }
//
//            //获取地区医院的id
//            Object[] arr = getRedisKeyByLoginName(loginName);
//            Integer communityId = (Integer) arr[0];//社区id
//            Integer areaId = (Integer) arr[1];//地区
//            //拼接受试者序号key
//            personKey = RedisConstant.HOSPITAL_AREA_PERSON_NUMBER + areaId;
//            log.info("@Service-person-personInsert 拼接受试者序号key  参数：personKey={}.", personKey);
//            //获取受试者序号
//            personNumber = redisManager.incrRedis(personKey);
//            //向分组记录表添加一条数据
//            addGroupRecord(areaId, personNumber);
//            //根据受试者序号查询规则表
//            String groupName = personDao.getGroupRuleByPNumber(personNumber);
//            log.info("@Service-person-personInsert 受试者被分组到的值  参数：groupName={}.", groupName);
//            //生成sid
//            siteId = review.getSiteId();//获取筛查现场id
//            if (siteId == null) {
//                siteId = personDao.getSiteIdByAreaId(areaId);
//            }
//            log.info("@Service-person-personInsert 筛查现场id的值  参数：siteId={}.", siteId);
//            //从redis里获取筛查现场id序号
//            siteIdNum = redisManager.incrRedis(String.valueOf(siteId));
//            log.info("@Service-person-personInsert 筛查现场id的序号  参数：siteIdNum={}.", siteIdNum);
//            int length = siteIdNum.toString().length();
//            String number = "";//后四位数
//            if (length == 1) {
//                number = "000" + siteIdNum;
//            } else if (length == 2) {
//                number = "00" + siteIdNum;
//            } else if (length == 3) {
//                number = "0" + siteIdNum;
//            }
//            //拼接sid
//            String sid = Constans.PERSON_SID_ACC + siteId + number;
//            log.info("@Service-person-personInsert 生成的sid值  参数：sid={}.", sid);
//            //添加一条资格审核表数据
//            review.setAreaDeptId(areaId);
//            review.setCommunityDeptId(communityId);
//            //获取受试者生日
//            Date date = getBirth(idCard);
//            review.setAge(age);
//            review.setBirthDate(date);
//            Date inGroupDate = new Date();
//            log.info("@Service-person-personInsert 入组日期  参数：inGroupDate={}.", inGroupDate.toString());
//            review.setInGroupDate(inGroupDate);
//            review.setGroup(groupName);
//            review.setGroupStatus(Integer.valueOf(Constans.GROUP_STATUS_CODE2));//已入组
//            review.setSiteId(siteId);
//            review.setSid(sid);
//            review.setStageCy(Constans.PERSON_STAGE_CY_STATUS0);
//            review.setReviewStatus(Constans.PERSON_REVIEW_STATUS2);
//            review.setRiskFactorStatus(Constans.PERSON_RISK_FACTOR_STATUS1);
//            review.setOverallStatusCy(Constans.PERSON_OVERALL_STATUS1);
//            log.info("@Service-person-personInsert 添加资格审核数据为  参数：review={}.", review.toString());
//            int reviewId = personDao.addReview(review, "hospital_intestine_review");
//
//            HtEvent todo = new HtEvent();
//            if (communityId != null) {
//                todo.setCommunityDeptId(communityId);
//            }
//            if (areaId != null) {
//                todo.setAreaDeptId(areaId);
//            }
//            todo.setSid(sid);
//            todo.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
//            //判断分组是A或者B
//            if ("A".equals(groupName)) {
//                //新增一条结肠镜检查记录
//                HospitalColonoscopyRecord result = new HospitalColonoscopyRecord();
//                result.setSid(sid);
//                result.setStage(Constans.PERSON_COLONOSCOPY_STATUS1);
//                result.setCommunity_dept_id(communityId);
//                result.setArea_dept_id(areaId);
//                result.setReserve_status(Constans.RESERVE_STATUS1);
//                result.setSource_type(Constans.SOURCE_TYPE1);
//                log.info("@Service-person-personInsert 添加结肠镜检查记录数据为  参数：result={}.", result.toString());
//                int dataId = personDao.addColonoscopyRecord(result);
//                todo.setDataId(dataId);
//                todo.setType(Constans.PERSON_TODO_EVENT_TYPE5);
//                log.info("@Service-person-personInsert 新增一条待预约结肠镜检查待办事件  参数：todo={}.", todo.toString());
//                personDao.addTodoEvent(todo);
//
//                StoolDna dna = new StoolDna();
//                dna.setSid(sid);
//                dna.setStage(1);
//                dna.setCodeEntryStatus(1);
//                dna.setAreaDeptId(areaId);
//                dna.setCommunityDeptId(communityId);
//                log.info("@Service-person-personInsert 新增一条粪便DNA数据  参数：dna={}.", dna.toString());
//                int dnaId = personDao.addStoolDna(dna);
//                todo.setType(Constans.PERSON_TODO_EVENT_TYPE4);
//                todo.setDataId(dnaId);
//                log.info("@Service-person-personInsert 新增一条未录入粪便DNA装置编号待办事件  参数：todo={}.", todo.toString());
//                personDao.addTodoEvent(todo);
//            }
//
//            if ("B".equals(groupName)) {
//                HospitalFitResult fitResult = new HospitalFitResult();
//                fitResult.setSid(sid);
//                fitResult.setStage(1);
//                fitResult.setCodeEntryStatus(1);
//                fitResult.setAreaDeptId(areaId);
//                fitResult.setCommunityDeptId(communityId);
//                int fitId = personDao.addFitResult(fitResult);
//                log.info("@Service-person-personInsert 新增 一条FIT数据返回的id  参数：fitId={}.", fitId);
//                todo.setDataId(fitId);
//                todo.setType(Constans.PERSON_TODO_EVENT_TYPE2);
//                log.info("@Service-person-personInsert 新增 一条 未录入FIT编号待办事件  参数：todo={}.", todo.toString());
//                personDao.addTodoEvent(todo);
//            }
//
//            todo.setDataId(reviewId);
//            todo.setType(Constans.PERSON_TODO_EVENT_TYPE1);
//            log.info("@Service-person-personInsert 新增一条未完成危险因素待办事件  参数：todo={}.", todo.toString());
//            personDao.addTodoEvent(todo);
//
//
//            results.setGroup(groupName);
//            results.setInGroupDate(inGroupDate);
//            results.setSid(sid);
//            results.setId(reviewId);
//            log.info("@Service-person-personInsert 返回结果是  参数：results={}.", results.toString());
//        } catch (Exception e) {
//            log.error("insert review error,exception:{}.", e);
//            if (personNumber > 0 && StringUtil.isNotBlank(personKey)) {
//                redisManager.decrRedis(personKey);
//            }
//            if (siteIdNum > 0 && siteId != null) {
//                redisManager.decrRedis(String.valueOf(siteId));
//            }
//        }
//        return results;
//    }

    /**
     * 添加受试者
     * TODO 简单描述该方法的实现功能（可选）.
     *
     * @see com.yuntongxun.itsys.base.service.PersonService#(com.yuntongxun.itsys.base.po.HospitalReview, java.lang.String)
     */
    @Override
    @Transactional
//    @Override
    public ReviewResult personInsert_test(HospitalReview review, String loginName) {
        String idCard = review.getIdCard();//获取身份证号
        int age = getAge(idCard);//获取受试者年龄
        queryReviewByIdCard(idCard);
        Integer item2 = review.getItem2();
        Integer item3 = review.getItem3();
        Integer item4 = review.getItem4();
        Integer item5 = review.getItem5();
        Integer item6 = review.getItem6();
        Integer item7 = review.getItem7();
        Integer item8 = review.getItem8();
        Integer item9 = review.getItem9();
        Integer item10 = review.getItem10();
        String items = "" + item2 + item3 + item4 + item5 + item6 + item7 + item8 + item9 + item10;
        log.info("@Service-person-personInsert 2-10项的字段值  参数：items={}.", items);


        //获取地区医院的id and  rule_id
        //ItsysDepartment itsysDepartment = userService.getCommunityIdAndAreaIdByLoginName(loginName);
        //部门ID
        // Integer communityId = itsysDepartment.getId();
        //父类部门ID
        //Integer areaId = itsysDepartment.getPid();

        List<DepartmentSidRuleDto> departmentSidRuleDtos = sidRuleDao.querySidRuleList(loginName);
        if (departmentSidRuleDtos.size() != 1) {
            throw new ItSysException(
                    GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
        }
        DepartmentSidRuleDto departmentSidRuleDto = departmentSidRuleDtos.get(0);

        //部门ID
        Integer communityId = departmentSidRuleDto.getDepartmentId();
        //父类部门ID
        Integer areaId = departmentSidRuleDto.getPid();

        //判断2-10项是否全为2
        if (!"222222222".equals(items)) {

            //新需求,不符合分组入库
            //判断该库是否存在这个身份证的数据

            try {

                HospitalReview hospitalReview = personDao.queryByCardId(review.getIdCard());
                if (hospitalReview == null) {
                    review.setCommunityDeptId(communityId);
                    review.setAreaDeptId(areaId);
                    personDao.addReview(review, "hospital_intestine_review_no_conform");
                }
            } catch (Exception e) {
                log.info(e);
            }
            return null;
        }

        //拼接受试者序号key
//        String personKey = RedisConstant.HOSPITAL_AREA_PERSON_NUMBER + areaId;
//        log.info("@Service-person-personInsert 拼接受试者序号key  参数：personKey={}.", personKey);


        Boolean flag = sidRuleDao.flag(loginName);
        if (!flag) {
            throw new ItSysException(
                    GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
        }


        //生成sid
        Integer siteId = review.getSiteId();//获取筛查现场id
        if (siteId == null) {
            siteId = personDao.getSiteIdByAreaId(areaId);
        }


        int personNumber = userService.getPersonNumberByRule(departmentSidRuleDto);



        //int personNumber = userService.getPersonNumber(itsysDepartment);

        if (personNumber == -1) {
            throw new ItSysException(
                    GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
        }

        //向分组记录表添加一条数据
        //        addGroupRecord(areaId, personNumber);

        //根据受试者序号查询规则表

        int length = String.valueOf(personNumber).length();
        //yanx
/*         int orderCode = 0;
        if (length == 4) {
            orderCode = Integer.parseInt(strPersonNumber.substring(1, strPersonNumber.length()));
        } else {
            orderCode = personNumber;
        }*/
        String groupName = "";

        //校验来自A还是来自B
        if (Constans.ADD_SID_RULE_0.equals(departmentSidRuleDto.getRuleType())) {//A直接
            //根据父类id获取对象
            List<DepartmentSidRuleDto> fdepartmentSidRuleDtos = sidRuleDao.findById(departmentSidRuleDto.getPid());
            if (fdepartmentSidRuleDtos.size() != 1) {
                throw new ItSysException(
                        GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
            }
            //根据社区ID获取order
            int orderCode =personDao.queryByCommunityDeptId(areaId,1);
            if(orderCode==0){
                throw new ItSysException(
                        GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
            }
            groupName = personDao.getGroupRuleByPNumber(orderCode, fdepartmentSidRuleDtos.get(0).getGroupRuleId());
        } else if (Constans.ADD_SID_RULE_1.equals(departmentSidRuleDto.getRuleType())) {
            //根据社区ID获取order
            int orderCode =personDao.queryByCommunityDeptId(communityId,2);
            if(orderCode==0){
                throw new ItSysException(
                        GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
            }
            groupName = personDao.getGroupRuleByPNumber(orderCode, departmentSidRuleDto.getGroupRuleId());
        }

        if (StringUtils.isEmpty(groupName)) {
            throw new ItSysException(
                    GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
        }

        log.info("@Service-person-personInsert 受试者被分组到的值  参数：groupName={}.", groupName);
        log.info("@Service-person-personInsert 筛查现场id的值  参数：siteId={}.", siteId);

        //拼接后半部分编码
        String number;//后四位数
        if (length == 1) {
            number = "000" + personNumber;
        } else if (length == 2) {
            number = "00" + personNumber;
        } else if (length == 3) {
            number = "0" + personNumber;
        } else {
            number = String.valueOf(personNumber);
        }

        //拼接sid
        String sid = Constans.PERSON_SID_ACC + siteId + number;
        log.info("@Service-person-personInsert 生成的sid值  参数：sid={}.", sid);
        //添加一条资格审核表数据
        review.setAreaDeptId(areaId);
        review.setCommunityDeptId(communityId);
        //获取受试者生日
        Date date = getBirth(idCard);
        review.setAge(age);
        review.setBirthDate(date);
        Date inGroupDate = new Date();
        log.info("@Service-person-personInsert 入组日期  参数：inGroupDate={}.", inGroupDate.toString());
        review.setInGroupDate(inGroupDate);
        review.setGroup(groupName);
        review.setGroupStatus(Integer.valueOf(Constans.GROUP_STATUS_CODE2));//已入组
        review.setSiteId(siteId);
        review.setSid(sid);
        review.setStageCy(Constans.PERSON_STAGE_CY_STATUS0);
        review.setReviewStatus(Constans.PERSON_REVIEW_STATUS2);
        review.setRiskFactorStatus(Constans.PERSON_RISK_FACTOR_STATUS1);
        review.setOverallStatusCy(Constans.PERSON_OVERALL_STATUS1);
        review.setCreateUser(loginName);//添加受试者名称
        log.info("@Service-person-personInsert 添加资格审核数据为  参数：review={}.", review.toString());
        int reviewId = personDao.addReview(review, "hospital_intestine_review");

        HtEvent todo = new HtEvent();
        if (communityId != null) {
            todo.setCommunityDeptId(communityId);
        }
        if (areaId != null) {
            todo.setAreaDeptId(areaId);
        }
        todo.setSid(sid);
        todo.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
        //判断分组是A或者B
        if ("A".equals(groupName)) {
            //新增一条结肠镜检查记录
            HospitalColonoscopyRecord result = new HospitalColonoscopyRecord();
            result.setSid(sid);
            result.setStage(Constans.PERSON_COLONOSCOPY_STATUS1);
            result.setCommunity_dept_id(communityId);
            result.setArea_dept_id(areaId);
            result.setReserve_status(Constans.RESERVE_STATUS1);
            result.setSource_type(Constans.SOURCE_TYPE1);
            result.setOperationSource(Constans.A_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW_HOSPITAL_COLONOSCOPY_RECORD);
            result.setOperationSourceId(reviewId);

            log.info("@Service-person-personInsert 添加结肠镜检查记录数据为  参数：result={}.", result.toString());
            int dataId = personDao.addColonoscopyRecord(result);
            todo.setDataId(dataId);
            todo.setType(Constans.PERSON_TODO_EVENT_TYPE5);
            log.info("@Service-person-personInsert 新增一条待预约结肠镜检查待办事件  参数：todo={}.", todo.toString());
            todo.setOperationSource(Constans.A_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW_HOSPITAL_COLONOSCOPY_RECORD);
            todo.setOperationSourceId(reviewId);
            personDao.addTodoEvent(todo);

            StoolDna dna = new StoolDna();
            dna.setSid(sid);
            dna.setStage(1);
            dna.setCodeEntryStatus(1);
            dna.setAreaDeptId(areaId);
            dna.setCommunityDeptId(communityId);
            dna.setOperationSource(Constans.OPERATION_SOURCE_TYPE_BY_SYSTEM);

            dna.setEditoperationSource(Constans.A_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW_DNA);
            dna.setOperationSourceId(reviewId);

            log.info("@Service-person-personInsert 新增一条粪便DNA数据  参数：dna={}.", dna.toString());
            int dnaId = personDao.addStoolDna(dna);
            todo.setType(Constans.PERSON_TODO_EVENT_TYPE4);
            todo.setDataId(dnaId);
            todo.setOperationSource(Constans.A_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW_DNA);
            todo.setOperationSourceId(reviewId);
            log.info("@Service-person-personInsert 新增一条未录入粪便DNA装置编号待办事件  参数：todo={}.", todo.toString());
            personDao.addTodoEvent(todo);

            HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO = new HospitalBiologicalSampleResultVo();
            String associatedSampleId = UUID.randomUUID().toString().replace("-", "");

            //新增一条生物样本粪便
            hospitalBiologicalSampleResultPO.setSid(sid);
            hospitalBiologicalSampleResultPO.setCommunityDeptId(communityId);
            hospitalBiologicalSampleResultPO.setAreaDeptId(areaId);
            hospitalBiologicalSampleResultPO.setStage(Constans.PERSON_COLONOSCOPY_STATUS1);
            hospitalBiologicalSampleResultPO.setOperationSource(Constans.OPERATION_SOURCE_TYPE_BY_SYSTEM);
            hospitalBiologicalSampleResultPO.setAssociatedSampleId(associatedSampleId);
            hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE5);

            hospitalBiologicalSampleResultPO.setEditoperationSource(Constans.A_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW_SAMPLE);
            hospitalBiologicalSampleResultPO.setOperationSourceId(reviewId);
            log.info("@Service-person-personInsert 新增生物样本数据粪便  参数：dna={}.", hospitalBiologicalSampleResultPO.toString());
            int sampleid = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO, Constans.BIOLOGICAL_SAMPLE_TABLE);
            todo.setType(Constans.PERSON_TODO_EVENT_TYPE12);
            todo.setDataId(sampleid);
            todo.setOperationSource(Constans.A_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW_SAMPLE);
            todo.setOperationSourceId(reviewId);
            log.info("@Service-person-personInsert 新增一条未录入生物样本数据粪便待办事件  参数：todo={}.", hospitalBiologicalSampleResultPO.toString());
            personDao.addTodoEvent(todo);

            //新增一条生物样本唾液
            hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE4);

            log.info("@Service-person-personInsert 新增生物样本数据粪便  参数：dna={}.", hospitalBiologicalSampleResultPO.toString());
            int sampleidByM = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO, Constans.BIOLOGICAL_SAMPLE_TABLE);
            todo.setType(Constans.PERSON_TODO_EVENT_TYPE13);
            todo.setDataId(sampleidByM);

            log.info("@Service-person-personInsert 新增一条未录入生物样本数据唾液待办事件  参数：todo={}.", hospitalBiologicalSampleResultPO.toString());
            personDao.addTodoEvent(todo);


            //添加血液样本
            hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE6);

            log.info("@Service-person-personInsert 新增生物样本数据粪便  参数：dna={}.", hospitalBiologicalSampleResultPO.toString());
            int sampleidByA = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO, Constans.BIOLOGICAL_SAMPLE_TABLE);
            todo.setType(Constans.PERSON_TODO_EVENT_TYPE18);
            todo.setDataId(sampleidByA);
            log.info("@Service-person-personInsert 新增一条未录入生物样本数据唾液待办事件  参数：todo={}.", hospitalBiologicalSampleResultPO.toString());
            personDao.addTodoEvent(todo);

            //新增一条生物样本白细胞
            hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE3);
            hospitalBiologicalSampleResultPO.setBloodSampleId(sampleidByA);

            log.info("@Service-person-personInsert 新增生物样本数据白细胞  参数：dna={}.", hospitalBiologicalSampleResultPO.toString());
            int sampleidByW = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO, Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE);
/*            todo.setType(Constans.PERSON_TODO_EVENT_TYPE15);
            todo.setDataId(sampleidByW);
            log.info("@Service-person-personInsert 新增一条未录入生物样本数据白细胞待办事件  参数：todo={}.", hospitalBiologicalSampleResultPO.toString());
            personDao.addTodoEvent(todo);*/

            //新增一条生物样本血清
            hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE1);

            log.info("@Service-person-personInsert 新增生物样本数据血清  参数：dna={}.", hospitalBiologicalSampleResultPO.toString());
            int sampleidByS = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO, Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE);
/*            todo.setType(Constans.PERSON_TODO_EVENT_TYPE11);
            todo.setDataId(sampleidByS);
            log.info("@Service-person-personInsert 新增一条未录入生物样本数据粪便待办事件  参数：todo={}.", hospitalBiologicalSampleResultPO.toString());
            personDao.addTodoEvent(todo);*/

            //新增一条生物样本血浆
            hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE2);
            log.info("@Service-person-personInsert 新增生物样本数据血浆  参数：dna={}.", hospitalBiologicalSampleResultPO.toString());
            int sampleidByP = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO, Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE);
/*             todo.setType(Constans.PERSON_TODO_EVENT_TYPE14);
            todo.setDataId(sampleidByP);
            log.info("@Service-person-personInsert 新增一条未录入生物样本数据粪便待办事件  参数：todo={}.", hospitalBiologicalSampleResultPO.toString());
            personDao.addTodoEvent(todo);*/


        }

        if ("B".equals(groupName)) {
            HospitalFitResult fitResult = new HospitalFitResult();
            fitResult.setSid(sid);
            fitResult.setStage(1);
            fitResult.setCodeEntryStatus(1);
            fitResult.setAreaDeptId(areaId);
            fitResult.setCommunityDeptId(communityId);
            fitResult.setOperationSource(Constans.OPERATION_SOURCE_TYPE_BY_SYSTEM);
            fitResult.setEditoperationSource(Constans.B_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW_FIT);
            fitResult.setOperationSourceId(reviewId);

            int fitId = personDao.addFitResult(fitResult);
            log.info("@Service-person-personInsert 新增 一条FIT数据返回的id  参数：fitId={}.", fitId);
            todo.setDataId(fitId);
            todo.setType(Constans.PERSON_TODO_EVENT_TYPE2);
            todo.setOperationSource(Constans.B_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW_FIT);
            todo.setOperationSourceId(reviewId);
            log.info("@Service-person-personInsert 新增 一条 未录入FIT编号待办事件  参数：todo={}.", todo.toString());
            personDao.addTodoEvent(todo);
        }
        todo.setDataId(reviewId);
        todo.setType(Constans.PERSON_TODO_EVENT_TYPE1);
        if("B".equals(groupName)){
            todo.setOperationSource(Constans.B_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW_HOSPITAL_INTESTINE_RISK_FACTOR);
        }else if("A".equals(groupName)){
            todo.setOperationSource(Constans.A_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW_HOSPITAL_INTESTINE_RISK_FACTOR);
        }else if("C".equals(groupName)){
            todo.setOperationSource(Constans.C_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW_HOSPITAL_INTESTINE_RISK_FACTOR);
        }
        todo.setOperationSourceId(reviewId);
        log.info("@Service-person-personInsert 新增一条未完成危险因素待办事件  参数：todo={}.", todo.toString());
        personDao.addTodoEvent(todo);

        ReviewResult results = new ReviewResult();
        results.setGroup(groupName);
        results.setInGroupDate(inGroupDate);
        results.setSid(sid);
        results.setId(reviewId);
        log.info("@Service-person-personInsert 返回结果是  参数：results={}.", results.toString());
        return results;

    }

    private void queryReviewByIdCard(String idCard) {
        if (StringUtils.isEmpty(idCard)) {
            throw new ItSysException(
                    GlobalErrorCode.ERR_PERSON_BIRTH_NULL_CODE, GlobalErrorCode.ERR_PERSON_BIRTH_NULL_MSG);
        }
        HospitalReview review = new HospitalReview();
        review.setIdCard(idCard);
        List<HospitalReview> list = personDao.query(review);//查询受试者资料
        log.info("@Service-person-queryReviewByIdCard  根据身份证号查询受试者数据结果  参数：list={}.", list == null ? null : list.toString());
        if (list != null && list.size() > 0) {
            throw new ItSysException(
                    GlobalErrorCode.ERR_PERSON_IN_GROUP_CODE, GlobalErrorCode.ERR_PERSON_IN_GROUP_MSG);
        }

    }

    private Date getBirth(String idCard) {
        if (StringUtils.isEmpty(idCard)) {
            throw new ItSysException(
                    GlobalErrorCode.ERR_PERSON_BIRTH_NULL_CODE, GlobalErrorCode.ERR_PERSON_BIRTH_NULL_MSG);
        }
        int length = idCard.length();
        String birth;
        //log.info("@Service-person-checkIdCard  身份证号长度  参数：length={}.",length);
        if (length == 18) {//长度是18位
            birth = idCard.substring(6, 14);
        } else {
            birth = "19" + idCard.substring(6, 11);//拼接15位身份证号生日
        }
        //log.info("@Service-person-getBirth  受试者生日是 ：birth={}.",birth);
        Date date = DateUtil.formatDateStr(birth, "yyyyMMdd");
        return date;
    }

    /**
     * 在分组记录表添加一条数据
     * addGroupRecord:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @param areaId       地区id
     * @param personNumber 受试者序号
     * @author ty
     * @since JDK 1.8
     */
    @Transactional
    public void addGroupRecord(Integer areaId, Long personNumber) {
        log.info("@Service-person-addGroupRecord 分组记录表添加的请求参数  ：dept_id={},person_order={}.", areaId, personNumber);
        //根据areaId查询分组记录表
        List<String> list = personDao.getPersonNumByAreaId(areaId);
        log.info("@Service-person-addGroupRecord 根据areaId查询分组记录查询个数  参数  ：size={}.", list == null ? 0 : list.size());
        if (list == null || list.size() == 0) {
            personDao.addGroupRecord(areaId, personNumber);
            log.info("@Service-person-addGroupRecord 分组记录表添加成功");
        } else {
            personDao.updateGroupRecord(areaId, personNumber);
            log.info("@Service-person-addGroupRecord 分组记录表更新成功");
        }
    }

    /**
     * 根据用户名获取社区和地区医院id
     * getRedisKeyByLoginName:(这里用一句话描述这个方法的作用). <br/>
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
    public Object[] getRedisKeyByLoginName(String loginName) {
        if (StringUtils.isEmpty(loginName)) {
            throw new ItSysException(
                    GlobalErrorCode.ERR_PARAM_NULL, "用户名为空");
        }
        //拼接rediskey
        String result = redisManager.get(RedisConstant.HOSPITAL_KEY_INFO + loginName);
        log.info("@Service-person-getRedisKeyByLoginName redis key  参数：key={}.", RedisConstant.HOSPITAL_KEY_INFO + loginName);
        DoctorInfo hospitalInfo;
        if (StringUtils.isEmpty(result)) {
            hospitalInfo = userService.getHospitalInfo(loginName);
        } else {
            try {
                hospitalInfo = JSONUtils.toBean(result, DoctorInfo.class);
            } catch (Exception e) {
                throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, e.getMessage());
            }
        }
        if (hospitalInfo == null) {
            throw new ItSysException(GlobalErrorCode.ERR_AREA_DEPID_NULL_CODE, GlobalErrorCode.ERR_AREA_DEPID_NULL_MSG);
        }
        log.info("@Service-person-getRedisKeyByLoginName redis value值  参数：value={}.", hospitalInfo.toString());
        Integer communityDeptId = hospitalInfo.getCommunityDeptId();
        Integer areaDeptId = hospitalInfo.getAreaDeptId();
        if (areaDeptId == null || areaDeptId == null) {//判断地区医院id是否为null
            hospitalInfo = userService.getHospitalInfo(loginName);
            if (hospitalInfo == null) {
                throw new ItSysException(GlobalErrorCode.ERR_USER_DEPID_NULL_CODE, GlobalErrorCode.ERR_USER_DEPID_NULL_MSG);
            }
            areaDeptId = hospitalInfo.getAreaDeptId();
            communityDeptId = hospitalInfo.getCommunityDeptId();
        }
        log.info("@Service-person-getRedisKeyByLoginName 社区医院id和地区医院id的值分别为  参数：communityDeptId={},areaDeptId={}.", communityDeptId, areaDeptId);
        if (areaDeptId == null) {
            throw new ItSysException(GlobalErrorCode.ERR_USER_DEPID_NULL_CODE, GlobalErrorCode.ERR_USER_DEPID_NULL_MSG);
        }
        if (communityDeptId == null) {
            throw new ItSysException(GlobalErrorCode.ERR_COMMUNITY_DEPID_NULL_CODE, GlobalErrorCode.ERR_COMMUNITY_DEPID_NULL_MSG);
        }
        Object[] arr = {communityDeptId, areaDeptId};
        return arr;
    }

    @Override
    @Transactional
    public Result quitResearch(IntestineReason intestineReason) {
        Result result = new Result();
        List<QuitInResultDto> quitResultDtos = null;
        try {

            List<HospitalReview> hospitalReviews = new ArrayList<>();
            //sid 集合
            String[] sids = intestineReason.getSid();

            for (String sid : sids) {
                hospitalReviews.add(getBySid(sid));
            }
            log.info("@Service-person-quitResearch 查询到全部SID对象数量  参数：hospitalReviews={}", hospitalReviews.size());
            quitResultDtos = personDao.updateState(hospitalReviews, intestineReason);

            //修改接口宗曈

            if (quitResultDtos.size() != 1 || hospitalReviews.size() != 1) {
                result = ResultUtil.success(GlobalErrorCode.ERR_GROUP_STATUS_NULL_CODE, GlobalErrorCode.ERR_GROUP_STATUS_NULL_MSG);
                return result;
            }
            HospitalReview hospitalReview = hospitalReviews.get(0);
            hospitalReview.setAreaDeptId(intestineReason.getDepartmentPId());
            hospitalReview.setCommunityDeptId(intestineReason.getDepartmentId());

            //消息中心添加异常事件

            Integer integer = personDao.saveScheme(hospitalReview, quitResultDtos.get(0));
            for (QuitInResultDto quitInResultDto : quitResultDtos) {
                quitInResultDto.setSchemeId(integer.toString());
            }
            String stageCy = "violation_plan_status_t" + (hospitalReview.getStageCy() - 1);
            personDao.updateViolationPlanStatus(1, hospitalReview.getSid(), stageCy);


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
            result = ResultUtil.success(GlobalErrorCode.ERR_GROUP_STATUS_NULL_CODE, GlobalErrorCode.ERR_GROUP_STATUS_NULL_MSG);
        }
        if (quitResultDtos.size() == 0) {
            result = ResultUtil.success(GlobalErrorCode.ERR_GROUP_STATUS_NULL_CODE, GlobalErrorCode.ERR_GROUP_STATUS_NULL_MSG);
            return result;
        }
        result.setData(quitResultDtos);
        return result;
    }

    @Override
    @Transactional
    public Result reresearch(IntestineReason intestineReason) {
        Result result = new Result();

        try {
            List<HospitalReview> hospitalReviews = new ArrayList<>();

            //sid 集合
            String[] sids = intestineReason.getSid();

            for (String sid : sids) {
                hospitalReviews.add(getBySid(sid));
            }

            personDao.reresearchUpdateState(hospitalReviews, intestineReason);
        } catch (Exception e) {
            e.printStackTrace();
            result = ResultUtil.success(GlobalErrorCode.ERR_GROUP_STATUS_RERESEARCH_CODE, GlobalErrorCode.ERR_GROUP_STATUS_RERESEARCH_MSG);
        }

        return result;
    }

    @Override
    public List<HospitalReview> queryBySidID(String sid, Integer area_dept_id, Integer community_dept_id,String userName) {
        return personDao.queryBySidAreaDeptId(sid, area_dept_id, community_dept_id,userName);
    }

    @Override
    public List<HospitalReview> queryAreaBySidID(String sid, Integer area_dept_id) {
        return personDao.queryAreaBySid(sid, area_dept_id);
    }

    @Override
    public List<HospitalReview> queryByAreaID(String sid, Integer area_dept_id) {
        return personDao.queryByAreaId(sid, area_dept_id);
    }

    @Override
    public boolean checkIdentityBlacklist(String identity, String type) {
        List<IdentityBlacklistPO> identityBlacklistPOS = personDao.checkIdentityBlacklist(identity, type);
        if (identityBlacklistPOS.size() > 0) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public Result queryPersonDetail(String sid) {
        PersonInfo personInfo = new PersonInfo();
        //1、查询个人的基本信息hospital_intestine_review
        HospitalReview hospitalReview = this.personDao.findInfoBySid(sid);
        if (hospitalReview != null) {
            personInfo.setBase(hospitalReview);
        }

        //根据sid查询
        HospitalRiskFactor hospitalRiskFactorBySid = personDao.findHospitalRiskFactorBySid(sid);
        if(hospitalRiskFactorBySid!=null){
            personInfo.setHospitalRiskFactor(hospitalRiskFactorBySid);
        }

        //2、查询T0阶段，FIT检查信息 hospital_fit_result
        List<HospitalFitResult> hospitalFitResult = this.personDao.findFitBySidAndState(sid);
        if (hospitalFitResult != null && hospitalFitResult.size() > 0) {
            personInfo.setFit(hospitalFitResult);
        }

        //3、查询T0阶段，粪便DNA相关信息 hospital_stool_dna
        List<HospitalStoolDNA> hospitalStoolDNA = this.personDao.findDNABySidAndState(sid);
        if (hospitalStoolDNA != null && hospitalStoolDNA.size() > 0) {
            personInfo.setDna(hospitalStoolDNA);
        }

        //修改生物样本相关查询---宗曈
        //根据sid查询
        List<HospitalBiologicalSampleResultVo> sample = this.personDao.findSampleBySid(sid);
        StringBuffer addId = new StringBuffer();
        //根据血液id获取对应信息
        for (HospitalBiologicalSampleResultVo vo : sample) {
            addId.append(vo.getId() + ",");
        }
        List<HospitalBiologicalSampleResultVo> vos = new ArrayList<>();
        if (!org.apache.commons.lang.StringUtils.isEmpty(addId.toString())) {
            vos = biologSampleDao.queryInBloodSampleIds(addId.substring(0, addId.toString().length() - 1).toString());
        }
        List<String> associatedSampleIdList = new ArrayList<>();

        for (HospitalBiologicalSampleResultVo vo : sample) {
            if (associatedSampleIdList.size() == 0) {
                associatedSampleIdList.add(vo.getAssociatedSampleId());
            } else if (!associatedSampleIdList.contains(vo.getAssociatedSampleId())) {
                associatedSampleIdList.add(vo.getAssociatedSampleId());
            }
        }

        List<HospitalBiologicalSampleResultVo> results = new ArrayList<>();
        for (String associatedSampleId : associatedSampleIdList) {
            HospitalBiologicalSampleResultVo entity = new HospitalBiologicalSampleResultVo();
            for (HospitalBiologicalSampleResultVo vo : sample) {
                if (associatedSampleId.equals(vo.getAssociatedSampleId())) {
                    Integer collectStatus = vo.getCollectStatus();
                    if (Constans.SAMPLE_TYPE4.equals(vo.getSampleType())) {
                        entity.setMsid(vo.getId());
                        entity.setSample_M(collectStatus);
                        entity.setmCourierStatus(vo.getCourierStatus());
                    } else if (Constans.SAMPLE_TYPE5.equals(vo.getSampleType())) {
                        entity.setSample_F(collectStatus);
                        entity.setFsid(vo.getId());
                        entity.setfCourierStatus(vo.getCourierStatus());
                    } else if (Constans.SAMPLE_TYPE6.equals(vo.getSampleType())) {
                        entity.setAsid(vo.getId());
                        entity.setSample_A(collectStatus);
                        List<Map<String, String>> mapCourierStatus = new ArrayList<>();
                        for (HospitalBiologicalSampleResultVo resultVo : vos) {//获取血液下面类型和快递状态
                            if (vo.getId().equals(resultVo.getBloodSampleId())) {
                                Map<String, String> map = new HashMap<>();
                                map.put(resultVo.getSampleType(), resultVo.getCourierStatus() == null ? "" : resultVo.getCourierStatus().toString());
                                mapCourierStatus.add(map);
                            }

                        }
                        entity.setMapCourierStatus(mapCourierStatus);
                    }
                }
            }
            results.add(entity);
        }
        if (results.size() > 0) {
            personInfo.setSample(results);
        }
/*        //新增生物样本根据sid查询
        List<HospitalBiologicalSampleResultVo> sample = this.personDao.findSamPle(sid);
        if (sample != null && sample.size() > 0) {
            personInfo.setSample(sample);
        }*/


        //4、查询T0阶段，肠镜相关数据表 hospital_colonoscopy_record
        List<HospitalColonoscopyRecord> hospitalColonoscopyRecord = this.personDao.findRecordBySidAndState(sid);
        if (hospitalColonoscopyRecord != null && hospitalColonoscopyRecord.size() > 0) {
            personInfo.setColonoscopy(hospitalColonoscopyRecord);
        }

        //5、查询T0阶段，违反方案记录表 hospital_abnormal_event
        List<HospitalAbnormalEvent> hospitalAbnormalEvent = this.personDao.findAbnormalBySidAndState(sid);
        if (hospitalAbnormalEvent != null && hospitalAbnormalEvent.size() > 0) {
            personInfo.setHospitalAbnormalEvent(hospitalAbnormalEvent);
        }

        return ResultUtil.success(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, personInfo);
    }

    @Transactional
    @Override
    public Result addHospitalRiskfactor(HospitalRiskFactor hospitalRiskFactor, String loginName) {
        Result result = new Result();

        //1、进行风险评估，判断高低危；更新hospital_intestine_review risk_level状态
        int score = 0;//分值累加
        String sid = hospitalRiskFactor.getSid();
        HospitalReview hospitalReview = personDao.findInfoBySid(sid);//获取个人信息
        if (hospitalReview != null) {
            AddRiskResult addRiskStatueResult = new AddRiskResult();
            DoctorInfo doctorInfo = new DoctorInfo();
            String infoString = redisManager.get(RedisConstant.HOSPITAL_KEY_INFO + loginName);
            if (StringUtil.isEmpty(infoString)) {
                doctorInfo = userService.getHospitalInfo(loginName);
            } else {
                doctorInfo = JSONUtils.toBean(infoString, DoctorInfo.class);
            }
            Integer age = hospitalReview.getAge();
            if (age >= 50 && age <= 54) {
                score = score + 0;
            }
            if (age >= 55 && age <= 64) {
                score = score + 1;
            }
            if (age >= 65 && age <= 74) {
                score = score + 2;
            }
            Integer sex = hospitalReview.getSex();
            if (sex == 1) { //男
                score = score + 1;
            }
            if (sex == 2) { //女
                score = score + 0;
            }
            //是否家族史
            if (hospitalRiskFactor.getItem_5_3() == 1) {
                score = score + 1;
            }
            if (hospitalRiskFactor.getItem_5_3() == 2) {
                score = score + 0;
            }
            //是否吸烟
            if (hospitalRiskFactor.getItem_4_1() == 1) {
                score = score + 0;
            }
            if (hospitalRiskFactor.getItem_4_1() == 2) {
                score = score + 1;
            }
            //体脂率 体重除以身高的平方
            double height = (double) hospitalRiskFactor.getHeight() / 100;
            double bmi = (double) hospitalRiskFactor.getWeight() / (height * height);
            if (bmi >= 23) {
                score = score + 1;
            }
            int updateResult = 0;
            int addResult = 0;
            int addDNAResult = 0;
            int addSample = 0;
            int addsampleidByM = 0;
            int addsampleidByW = 0;
            int addsampleidByS = 0;
            int addsampleidByP = 0;
            addRiskStatueResult.setGroup(hospitalReview.getGroup());
            if (score >= 4) { //高风险 更新状态等级

                addRiskStatueResult.setRiskLevel(Constans.PERSON_RISK_LEVEL2);
                updateResult = personDao.updateRiskLevelBySid(Constans.PERSON_RISK_LEVEL2, sid, score);
                if (updateResult != 1) {
                    return ResultUtil.success(GlobalErrorCode.ERR_RISK_STATUS_CODE, GlobalErrorCode.ERR_RISK_STATUS_MSG);
                }

                //2、判断如果高危，并且是C组，新增 待预约结肠镜检查待办；新增未录入粪便DNA编号待办（hospital_todo_event）
                addRiskStatueResult.setGroup(hospitalReview.getGroup());
                if (hospitalReview.getGroup().equals("C")) {

                    //高危 新增一条肠镜记录、
                    //生成结肠镜检查记录；
                    HospitalColonoscopyRecord record = new HospitalColonoscopyRecord();
                    record.setSid(sid);
                    record.setStage(STAGE_T0);
                    record.setArea_dept_id(doctorInfo.getAreaDeptId());
                    record.setCommunity_dept_id(doctorInfo.getCommunityDeptId());
                    record.setReserve_status(COLONOSCOPY_RESERVE_STATUS_NOT_RESERVED);
                    record.setSource_type(Constans.SOURCE_TYPE1);
                    record.setOperationSource(Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                    record.setOperationSourceId(hospitalReview.getId());
                    int colonoscopyId = colonoscopyDao.add(record);
                    log.info("Insert colonoscopy record,id:{},body:{}", colonoscopyId, JSONUtils.toJson(record));

//                    //生成 未预约结肠镜检查待办；
//                    HtEvent event1 = new HtEvent();
//                    event1.setAreaDeptId(doctorInfo.getAreaDeptId());
//                    event1.setCommunityDeptId(doctorInfo.getCommunityDeptId());
//                    event1.setDataId(colonoscopyId);
//                    event1.setSid(sid);
//                    event1.setStatus(EVENT_STATUS_NOT_DO);
//                    event1.setType(EVENT_TYPE_5);
//                    log.info("Insert [not reserve colonoscopy todo event],body:{}",JSONUtils.toJson(event1));
//                    eventDao.insert(event1);

                    //生成 粪便DNA结果记录
                    StoolDna stoolDna = new StoolDna();
                    stoolDna.setSid(sid);
                    stoolDna.setStage(STAGE_T0);
                    stoolDna.setAreaDeptId(doctorInfo.getAreaDeptId());
                    stoolDna.setCommunityDeptId(doctorInfo.getCommunityDeptId());
                    stoolDna.setOperationSource(Constans.OPERATION_SOURCE_TYPE_BY_SYSTEM);
                    stoolDna.setEditoperationSource(Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                    stoolDna.setOperationSourceId(hospitalReview.getId());
                    int dnaId = personDao.addStoolDna(stoolDna);
                    log.info("Insert dna record,id:{},body:{}", dnaId, JSONUtils.toJson(stoolDna));

                    //生成生物样本
                    HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO = new HospitalBiologicalSampleResultVo();
                    String associatedSampleId = UUID.randomUUID().toString().replace("-", "");
                    //新增一条生物样本粪便
                    hospitalBiologicalSampleResultPO.setSid(sid);
                    hospitalBiologicalSampleResultPO.setCommunityDeptId(doctorInfo.getCommunityDeptId());
                    hospitalBiologicalSampleResultPO.setAreaDeptId(doctorInfo.getAreaDeptId());
                    hospitalBiologicalSampleResultPO.setStage(Constans.PERSON_COLONOSCOPY_STATUS1);
                    hospitalBiologicalSampleResultPO.setOperationSource(Constans.OPERATION_SOURCE_TYPE_BY_SYSTEM);
                    hospitalBiologicalSampleResultPO.setAssociatedSampleId(associatedSampleId);
                    hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE5);
                    hospitalBiologicalSampleResultPO.setEditoperationSource(Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                    hospitalBiologicalSampleResultPO.setOperationSourceId(hospitalReview.getId());
                    int sampleid = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO, Constans.BIOLOGICAL_SAMPLE_TABLE);
                    log.info("Insert sample,id:{},body:{}", sampleid, JSONUtils.toJson(hospitalBiologicalSampleResultPO));

                    //新增一条生物样本唾液
                    hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE4);
                    int sampleidByM = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO, Constans.BIOLOGICAL_SAMPLE_TABLE);
                    log.info("Insert sample,id:{},body:{}", sampleidByM, JSONUtils.toJson(hospitalBiologicalSampleResultPO));

                    //新增一条血液样本
                    hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE6);
                    int sampleidByA = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO, Constans.BIOLOGICAL_SAMPLE_TABLE);
                    log.info("Insert sample,id:{},body:{}", sampleidByM, JSONUtils.toJson(hospitalBiologicalSampleResultPO));

                    //新增一条生物样本白细胞
                    hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE3);
                    hospitalBiologicalSampleResultPO.setBloodSampleId(sampleidByA);
                    int sampleidByW = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO, Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE);
                    log.info("Insert sample,id:{},body:{}", sampleidByW, JSONUtils.toJson(hospitalBiologicalSampleResultPO));

                    //新增一条生物样本血清
                    hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE1);
                    int sampleidByS = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO, Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE);
                    log.info("Insert sample,id:{},body:{}", sampleidByS, JSONUtils.toJson(hospitalBiologicalSampleResultPO));

                    //新增一条生物样本血浆
                    hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE2);
                    int sampleidByP = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO, Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE);
                    log.info("Insert sample,id:{},body:{}", sampleidByP, JSONUtils.toJson(hospitalBiologicalSampleResultPO));


                    //生成 未录入粪便DNA装置编号 待办
//                    HtEvent event2 = new HtEvent();
//                    event2.setAreaDeptId(doctorInfo.getAreaDeptId());
//                    event2.setCommunityDeptId(doctorInfo.getCommunityDeptId());
//                    event2.setDataId(dnaId);
//                    event2.setSid(sid);
//                    event2.setStatus(EVENT_STATUS_NOT_DO);
//                    event2.setType(EVENT_TYPE_4);
//                    eventDao.insert(event2);
//                    log.info("Insert [not entry dna code todo event],body:{}",JSONUtils.toJson(event2));

                    //
                    //新增 待预约结肠镜检查待办
                    HospitalTodoEvent hospitalTodoEvent = new HospitalTodoEvent();
                    hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE5);
                    Integer communityDeptId = doctorInfo.getCommunityDeptId();
                    if (communityDeptId != null) {

                        hospitalTodoEvent.setCommunityDeptId(communityDeptId);
                    }
                    Integer areaId = doctorInfo.getAreaDeptId();
                    if (communityDeptId != null) {

                        hospitalTodoEvent.setAreaDeptId(areaId);
                    }
                    hospitalTodoEvent.setSid(sid);
                    hospitalTodoEvent.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
                    hospitalTodoEvent.setDataId(colonoscopyId);
                    hospitalTodoEvent.setOperationSource(Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                    hospitalTodoEvent.setOperationSourceId(hospitalReview.getId());
                    addResult = personDao.addTodoEvent(hospitalTodoEvent);
                    if (addResult != 1) {
                        return ResultUtil.success(GlobalErrorCode.ERR_TODOEVENT_ADD_CODE, GlobalErrorCode.ERR_TODOEVENT_ADD_MSG);
                    }

                    //新增未录入粪便DNA编号待办
                    hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE4);
                    hospitalTodoEvent.setDataId(dnaId);
                    hospitalTodoEvent.setOperationSource(Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                    hospitalTodoEvent.setOperationSourceId(hospitalReview.getId());
                    addDNAResult = personDao.addTodoEvent(hospitalTodoEvent);
                    if (addDNAResult != 1) {
                        return ResultUtil.success(GlobalErrorCode.ERR_TODOEVENT_ADD_CODE, GlobalErrorCode.ERR_TODOEVENT_ADD_MSG);
                    }

                    //粪便系类
                    hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE12);
                    hospitalTodoEvent.setDataId(sampleid);
                    hospitalTodoEvent.setOperationSource(Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                    hospitalTodoEvent.setOperationSourceId(hospitalReview.getId());
                    addSample = personDao.addTodoEvent(hospitalTodoEvent);
                    if (addSample != 1) {
                        return ResultUtil.success(GlobalErrorCode.ERR_TODOEVENT_ADD_CODE, GlobalErrorCode.ERR_TODOEVENT_ADD_MSG);
                    }
                    //录入生物样本数据唾液待办事件
                    hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE13);
                    hospitalTodoEvent.setDataId(sampleidByM);
                    hospitalTodoEvent.setOperationSource(Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                    hospitalTodoEvent.setOperationSourceId(hospitalReview.getId());
                    log.info("@Service-person-personInsert 新增一条未录入生物样本数据唾液待办事件  参数：todo={}.", hospitalBiologicalSampleResultPO.toString());
                    addsampleidByM = personDao.addTodoEvent(hospitalTodoEvent);
                    if (addsampleidByM != 1) {
                        return ResultUtil.success(GlobalErrorCode.ERR_TODOEVENT_ADD_CODE, GlobalErrorCode.ERR_TODOEVENT_ADD_MSG);
                    }
                    //血液代办
                    hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE18);
                    hospitalTodoEvent.setDataId(sampleidByA);
                    hospitalTodoEvent.setOperationSource(Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                    hospitalTodoEvent.setOperationSourceId(hospitalReview.getId());
                    log.info("@Service-person-personInsert 新增一条未录入生物样本数据血液待办事件  参数：todo={}.", hospitalBiologicalSampleResultPO.toString());
                    addsampleidByM = personDao.addTodoEvent(hospitalTodoEvent);
                    if (addsampleidByM != 1) {
                        return ResultUtil.success(GlobalErrorCode.ERR_TODOEVENT_ADD_CODE, GlobalErrorCode.ERR_TODOEVENT_ADD_MSG);
                    }

/*                    hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE15);
                    hospitalTodoEvent.setDataId(sampleidByW);
                    log.info("@Service-person-personInsert 新增一条未录入生物样本数据白细胞待办事件  参数：todo={}.", hospitalBiologicalSampleResultPO.toString());
                    addsampleidByW = personDao.addTodoEvent(hospitalTodoEvent);
                    if (addsampleidByW != 1) {
                        return ResultUtil.success(GlobalErrorCode.ERR_TODOEVENT_ADD_CODE, GlobalErrorCode.ERR_TODOEVENT_ADD_MSG);
                    }*/

/*                    hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE11);
                    hospitalTodoEvent.setDataId(sampleidByS);
                    log.info("@Service-person-personInsert 新增一条未录入生物样本数据唾液待办事件  参数：todo={}.", hospitalBiologicalSampleResultPO.toString());
                    addsampleidByS = personDao.addTodoEvent(hospitalTodoEvent);
                    if (addsampleidByS != 1) {
                        return ResultUtil.success(GlobalErrorCode.ERR_TODOEVENT_ADD_CODE, GlobalErrorCode.ERR_TODOEVENT_ADD_MSG);
                    }*/

/*                    hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE14);
                    hospitalTodoEvent.setDataId(sampleidByP);
                    log.info("@Service-person-personInsert 新增一条未录入生物样本数据唾液待办事件  参数：todo={}.", hospitalBiologicalSampleResultPO.toString());
                    addsampleidByP = personDao.addTodoEvent(hospitalTodoEvent);
                    if (addsampleidByP != 1) {
                        return ResultUtil.success(GlobalErrorCode.ERR_TODOEVENT_ADD_CODE, GlobalErrorCode.ERR_TODOEVENT_ADD_MSG);
                    }*/

                }

            } else { //低风险

                if (hospitalReview.getGroup().equals("C")) {
                    //新增一条fit记录
                    HospitalFitResult fitResult = new HospitalFitResult();
                    fitResult.setSid(sid);
                    fitResult.setStage(1);
                    fitResult.setCodeEntryStatus(1);
                    fitResult.setAreaDeptId(doctorInfo.getAreaDeptId());
                    fitResult.setCommunityDeptId(doctorInfo.getCommunityDeptId());
                    fitResult.setOperationSource(Constans.OPERATION_SOURCE_TYPE_BY_SYSTEM);
                    fitResult.setEditoperationSource(Constans.CD_OR_BY_SYSTEM_SAVE_FIT);
                    fitResult.setOperationSourceId(hospitalReview.getId());
                    int fitId = personDao.addFitResult(fitResult);
                    log.info("@Service-person-personInsert 新增 一条FIT数据返回的id  参数：fitId={}.", fitId);

                    HtEvent todo = new HtEvent();
                    todo.setCommunityDeptId(doctorInfo.getCommunityDeptId());
                    todo.setAreaDeptId(doctorInfo.getAreaDeptId());
                    todo.setSid(sid);
                    todo.setDataId(fitId);
                    todo.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
                    todo.setType(Constans.PERSON_TODO_EVENT_TYPE2);
                    todo.setOperationSource(Constans.CD_OR_BY_SYSTEM_SAVE_FIT);
                    todo.setOperationSourceId(hospitalReview.getId());
                    personDao.addTodoEvent(todo);
                    log.info("@Service-person-personInsert 新增 一条 未录入FIT编号待办事件  参数：todo={}.", todo.toString());
                }

                addRiskStatueResult.setRiskLevel(Constans.PERSON_RISK_LEVEL1);
                hospitalRiskFactor.setScore(score);//添加得分字段  by maxiang at 2018-06-25
                updateResult = personDao.updateRiskLevelBySid(Constans.PERSON_RISK_LEVEL1, sid, score);
                if (updateResult != 1) {
                    return ResultUtil.success(GlobalErrorCode.ERR_RISK_STATUS_CODE, GlobalErrorCode.ERR_RISK_STATUS_MSG);
                }
            }


            //3、新一条危险因素记录（hospital_intestine_risk_factor）
            hospitalRiskFactor.setScore(score);//添加得分字段  by maxiang at 2018-06-25

            int addRiskResult = personDao.addRiskFactor(hospitalRiskFactor);

            if (addRiskResult == 1) {
                result = ResultUtil.success(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, addRiskStatueResult);
                //更新待办状态
                personDao.updateTodoEventByType(sid);
                //更新收集状态
                personDao.updateRiskStatusBySid(sid, 2);
            } else {
                result = ResultUtil.success(GlobalErrorCode.ERR_RISKFACTOR_ADD_CODE, GlobalErrorCode.ERR_RISKFACTOR_ADD_MSG);
            }

        }
        return result;
    }

    @Override
    public Result findRiskfactorBySid(String sid) {
        Result result = new Result();
        HospitalRiskFactor hospitalRiskFactor = this.personDao.findHospitalRiskFactorBySid(sid);
        if (hospitalRiskFactor != null) {
            result = ResultUtil.success(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, hospitalRiskFactor);
        } else {
            result = ResultUtil.error(GlobalErrorCode.ERR_RISKFACTOR_NULL_CODE, GlobalErrorCode.ERR_RISKFACTOR_NULL_MSG);
        }
        return result;
    }

    @Override
    public Result sendMesage(String sid) {
        Result result = new Result();
        HospitalReview hospitalReview = personDao.findInfoBySid(sid);
        if (hospitalReview != null) {
            String phone = hospitalReview.getPhone();
            String smsValue = redis.get(Constans.TOKEN_KEY_NOTICESID_PREFIX + phone);
            if (StringUtil.isEmpty(smsValue)) {
                String[] parm = new String[1];
                parm[0] = sid;
                boolean isSend = sendSms.getAuthCode(phone, "244639", parm);
                if (isSend == true) {
                    redis.set(Constans.TOKEN_KEY_NOTICESID_PREFIX + phone, phone, 60);
                    result = ResultUtil.success(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS);
                } else {
                    result = ResultUtil.success(GlobalErrorCode.ERR_SEND_SMS_CODE, GlobalErrorCode.ERR_SEND_SMS_MSG);
                }
            } else {
                result = ResultUtil.success(GlobalErrorCode.ERR_SEND_SMS_OFTEN_CODE, GlobalErrorCode.ERR_SEND_SMS_OFTEN_MSG);
            }
        }

        return result;
    }

    @Override
    public Result sendColonoscopyMesage(String sid) {
        Result result = new Result();
        HospitalReview hospitalReview = personDao.findInfoBySid(sid);
        if (hospitalReview != null) {
            String phone = hospitalReview.getPhone();
            String smsValue = redis.get(Constans.TOKEN_KEY_COLONOSCOPY_PREFIX + phone);
            if (StringUtil.isEmpty(smsValue)) {
                String[] parm = new String[1];
                boolean isSend = sendSms.getAuthCode(phone, "244640", parm);
                if (isSend == true) {
                    redis.set(Constans.TOKEN_KEY_COLONOSCOPY_PREFIX + phone, phone, 60);
                    result = ResultUtil.success(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS);
                } else {
                    result = ResultUtil.success(GlobalErrorCode.ERR_SEND_SMS_CODE, GlobalErrorCode.ERR_SEND_SMS_MSG);
                }
            } else {
                result = ResultUtil.success(GlobalErrorCode.ERR_SEND_SMS_OFTEN_CODE, GlobalErrorCode.ERR_SEND_SMS_OFTEN_MSG);
            }

        }

        return result;
    }

    @Override
    public ListPageUtil queryPage(HospitalReview person, String loginName) {
        Object[] arr = getRedisKeyByLoginName(loginName);
        Integer communityId = (Integer) arr[0];//社区id
        Integer areaId = (Integer) arr[1];//地区
        person.setCommunityDeptId(communityId);
        person.setAreaDeptId(areaId);
        log.info("@Service-person-queryPage 社区医院id和地区医院id的值分别为  参数：communityDeptId={},areaDeptId={}.", communityId, areaId);
        //ListPageUtil listPage = personDao.queryPage(person);
        ListPageUtil listPage = personDao.queryReviewByNationIdPageByG(person);
        return listPage;
    }

    @Override
    public List<HospitalReview> queryExcel(HospitalReview person, String loginName) {
        Object[] arr = getRedisKeyByLoginName(loginName);
        Integer communityId = (Integer) arr[0];//社区id
        Integer areaId = (Integer) arr[1];//地区
        person.setCommunityDeptId(communityId);
        person.setAreaDeptId(areaId);
        log.info("@Service-person-queryPage 社区医院id和地区医院id的值分别为  参数：communityDeptId={},areaDeptId={}.", communityId, areaId);
        List<HospitalReview> list = personDao.queryExcel(person);
        return list;
    }

    @Override
    public HospitalReview getBySid(String sid) {
        HospitalReview hospitalReview = personDao.getBySid(sid);
        return hospitalReview;
    }

    @Override
    public ListPageUtil queryAreaPage(HospitalReview person, String loginName) {
        Integer areaId = getAreaIdByLoginName(loginName);//获取地区医院id
        person.setAreaDeptId(areaId);
        log.info("@Service-person-queryAreaPage 地区医院id的值为  参数：areaDeptId={}.", areaId);
        //ListPageUtil listPage = personDao.queryPage(person);

        //2018-08-30  zongtong
        ListPageUtil listPage = personDao.queryReviewByNationIdPageByG(person);
        return listPage;
    }

    /**
     * 根据用户名获取地区医院id
     * getRedisKeyByLoginName:(这里用一句话描述这个方法的作用). <br/>
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
    public Integer getAreaIdByLoginName(String loginName) {
        if (StringUtils.isEmpty(loginName)) {
            throw new ItSysException(
                    GlobalErrorCode.ERR_PARAM_NULL, "用户名为空");
        }
        //拼接rediskey
        String result = redisManager.get(RedisConstant.HOSPITAL_KEY_INFO + loginName);
        log.info("@Service-person-getAreaIdByLoginName redis key  参数：key={}.", RedisConstant.HOSPITAL_KEY_INFO + loginName);
        DoctorInfo hospitalInfo;
        if (StringUtils.isEmpty(result)) {
            hospitalInfo = userService.getHospitalInfo(loginName);
        } else {
            try {
                hospitalInfo = JSONUtils.toBean(result, DoctorInfo.class);
            } catch (Exception e) {
                throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, e.getMessage());
            }
        }
        if (hospitalInfo == null) {
            throw new ItSysException(GlobalErrorCode.ERR_AREA_DEPID_NULL_CODE, GlobalErrorCode.ERR_AREA_DEPID_NULL_MSG);
        }
        log.info("@Service-person-getAreaIdByLoginName redis value值  参数：value={}.", hospitalInfo.toString());
        Integer areaDeptId = hospitalInfo.getAreaDeptId();
        Integer communityDeptId = hospitalInfo.getCommunityDeptId();
        if (areaDeptId == null) {//判断地区医院id是否为null
            hospitalInfo = userService.getHospitalInfo(loginName);
            if (hospitalInfo == null) {
                throw new ItSysException(GlobalErrorCode.ERR_USER_DEPID_NULL_CODE, GlobalErrorCode.ERR_USER_DEPID_NULL_MSG);
            }
            areaDeptId = hospitalInfo.getAreaDeptId();
            communityDeptId = hospitalInfo.getCommunityDeptId();
        }
        log.info("@Service-person-getAreaIdByLoginName 地区医院id的值为  参数：areaDeptId={}.", areaDeptId);
        if (areaDeptId == null) {
            throw new ItSysException(GlobalErrorCode.ERR_USER_DEPID_NULL_CODE, GlobalErrorCode.ERR_USER_DEPID_NULL_MSG);
        }
        if (communityDeptId != null) {
            throw new ItSysException(GlobalErrorCode.ERR_USER_DEPID_ERRO_CODE, GlobalErrorCode.ERR_USER_DEPID_ERRO_MSG);
        }
        return areaDeptId;
    }

    /**
     * 查看受试者个人信息
     *
     * @param sid
     * @return
     */
    @Override
    public Result queryPersonInfo(String sid, String loginName) {
        Result result = new Result();
        //获取当前登录者的信息
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        HospitalReview hospitalReview = personDao.findInfoBySid(sid);
        if (hospitalReview != null) {
            //身份证首尾保留各4位数，中间*替换
            String idCard = hospitalReview.getIdCard();
            if (idCard != null && !("").equals(idCard) && idCard.length() > 8) {
                idCard = idCard.substring(0, 4) + "***********" + idCard.substring(idCard.length() - 4, idCard.length());
                hospitalReview.setIdCard(idCard);
            }
        } else {
            log.info("根据sid" + sid + "查看受试者个人信息queryPersonInfo为null");
            throw new ItSysException(GlobalErrorCode.ERR_USER_INFO_INCOMPLETE_ERROR_CODE, GlobalErrorCode.ERR_USER_INFO_INCOMPLETE_ERROR_MSG);
        }


        if (Constans.PERSON_OVERALL_STATUS2.equals(hospitalReview.getOverallStatusCy())) {
            throw new ItSysException(GlobalErrorCode.PERSON_OVERALL_STATUS2_ERROR_CODE, GlobalErrorCode.PERSON_OVERALL_STATUS2_ERROR_MSG);
        }

        hospitalReview.setDoctorInfo(doctorInfo);

        return ResultUtil.success(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, hospitalReview);
    }

    @Override
    public void checkPhone(String phone) {
        log.info("@Service-person-checkPhone 受试者手机号  参数：phone={}.", phone);
        HospitalReview person = new HospitalReview();
        person.setPhone(phone);
        List<HospitalReview> list = personDao.query(person);
        log.info("@Service-person-checkPhone 受试者手机号查询结果  参数：size={}.", list == null ? 0 : list.size());
        if (list != null && list.size() > 0) {
            throw new ItSysException(
                    GlobalErrorCode.ERR_PERSON_INSERT_PHONE_IS_NULL_CODE, GlobalErrorCode.ERR_PERSON_INSERT_PHONE_IS_NULL_MSG);
        }

    }

    @Override
    public ListPageUtil queryQuitLog(QuitSearchDto quitSearchDto, ItsysDepartment itsysDepartment, Integer HOSPITAL_TYPE) {
        log.info("@Service-person-queryQuitLog 查询退出试验受试者列表  参数：quitSearchDto={}.", quitSearchDto);
        return personDao.queryQuitLog(quitSearchDto, itsysDepartment, HOSPITAL_TYPE);
    }

    @Override
    public ListPageUtil queryQuitList(QuitSearchDto quitSearchDto, ItsysDepartment itsysDepartment, Integer HOSPITAL_TYPE) {
        log.info("@Service-person-queryQuitList 查询异常事件列表  参数：quitSearchDto={}.", quitSearchDto);
        return personDao.queryQuitList(quitSearchDto, itsysDepartment, HOSPITAL_TYPE);
    }

/*
    public static void main(String[] args) {
        double height = (double) 156 / 100;
        double bmi = (double) 65/(height * height);
        System.out.println(height);
        System.out.println(bmi);
    }
*/

    public static void removeDuplicate(List list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
        System.out.println(list);
    }

    public static void main(String[] args) {
        List<String> l = new ArrayList<>();
        l.add("1");
        List<String> l2 = new ArrayList<>();
        l2.add("1");
        if (l.size() != 1 || l2.size() != 1) {
            System.out.println("as1");
        }
    }

    @Override
    public List<HospitalReview> queryBySidIDAndAreaDeptId(String sid, Integer area_dept_id) {
        // TODO Auto-generated method stub
        return personDao.queryBySidIDAndAreaDeptId(sid, area_dept_id);
    }

    @Override
    @Transactional
    public void update(HospitalReview review, HospitalReferenceRecordDto hospitalReferenceRecordDto, boolean isok) {
        String idCard = review.getIdCard();//获取身份证号
        int age = getAge(idCard);//获取受试者年龄
        if (isok) {
            queryReviewByIdCard(idCard);
        }
        Integer item2 = review.getItem2();
        Integer item3 = review.getItem3();
        Integer item4 = review.getItem4();
        Integer item5 = review.getItem5();
        Integer item6 = review.getItem6();
        Integer item7 = review.getItem7();
        Integer item8 = review.getItem8();
        Integer item9 = review.getItem9();
        Integer item10 = review.getItem10();
        String items = "" + item2 + item3 + item4 + item5 + item6 + item7 + item8 + item9 + item10;
        log.info("@Service-person-update 2-10项的字段值  参数：items={}.", items);

        //获取受试者生日
        Date date = getBirth(idCard);
        review.setAge(age);
        review.setBirthDate(date);
        

        //查看A2表单是否录入

        //给予一个状态值，默认false
        //根据AI表单，看分组|还有是否有等级  判断
        HospitalReview oldHospitalReview = personDao.findInfoBySid(review.getSid());

        Integer score = 0;
        if("C".equals(oldHospitalReview.getGroup()) && oldHospitalReview.getRiskLevel()!=null){
            String sid=oldHospitalReview.getSid();
            //拿出原来数据得到分数
            Integer oldSorce = oldHospitalReview.getRiskLevel();
            //根据id查
            HospitalRiskFactor hospitalRiskFactorBySid = personDao.findHospitalRiskFactorBySid(review.getSid());
            //现在分数 修改的(age+sex) + A2表单（getItem_5_3是否家族史+getItem_4_1//是否吸烟+bmi//体脂率 体重除以身高的平方））
            if (age >= 50 && age <= 54) {
                score = score + 0;
            }
            if (age >= 55 && age <= 64) {
                score = score + 1;
            }
            if (age >= 65 && age <= 74) {
                score = score + 2;
            }
            Integer sex = review.getSex();
            if (sex == 1) { //男
                score = score + 1;
            }
            if (sex == 2) { //女
                score = score + 0;
            }
            //是否家族史
            if (hospitalRiskFactorBySid.getItem_5_3() == 1) {
                score = score + 1;
            }
            if (hospitalRiskFactorBySid.getItem_5_3() == 2) {
                score = score + 0;
            }
            //是否吸烟
            if (hospitalRiskFactorBySid.getItem_4_1() == 1) {
                score = score + 0;
            }
            if (hospitalRiskFactorBySid.getItem_4_1() == 2) {
                score = score + 1;
            }
            //体脂率 体重除以身高的平方
            double height = (double) hospitalRiskFactorBySid.getHeight() / 100;
            double bmi = (double) hospitalRiskFactorBySid.getWeight() / (height * height);
            if (bmi >= 23) {
                score = score + 1;
            }
            if(oldSorce==2 && score<4){
                //判断是否检查
                List<HospitalColonoscopyRecord> hospitalColonoscopyRecords = personDao.queryRecordByIdAndOperation(oldHospitalReview.getId(), Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                if(hospitalColonoscopyRecords.get(0).getExamination_status()==null){

                    //判断是否预约
                    if(hospitalColonoscopyRecords.get(0).getReserve_status()!=null&&hospitalColonoscopyRecords.get(0).getReserve_status()==2){
                        //删除放号信息
                        if(hospitalColonoscopyRecords.get(0).getAllocation_id()!=null){
                            reserveDetailDao.delReserveDetail(Integer.parseInt(hospitalColonoscopyRecords.get(0).getAllocation_id()),hospitalColonoscopyRecords.get(0).getSid());
                        }
                        //删除录入结果代办
                        int i= personDao.deleteEventBySourceIdAndType(hospitalColonoscopyRecords.get(0).getOperationSourceId(),hospitalColonoscopyRecords.get(0).getEditoperationSource(),Constans.PERSON_TODO_EVENT_TYPE8);
                        if(i<1){
                            throw new ItSysException(
                                    GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
                        }
                        //eventDao.updateStatus(hospitalColonoscopyRecords.get(0).getSid(),hospitalColonoscopyRecords.get(0).getId(),Constans.PERSON_TODO_EVENT_TYPE8,Constans.PERSON_TODO_EVENT_STATUS3);
                    }else{
                        //删除预约代办
                        int i= personDao.deleteEventBySourceIdAndType(oldHospitalReview.getId(),Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW,Constans.PERSON_TODO_EVENT_TYPE5);
                        if(i<1){
                            throw new ItSysException(
                                    GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
                        }
                    }

                    //删除预约数据 hospital_colonoscopy_record
                    int j =personDao.deleeBySourceId(oldHospitalReview.getId(),Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW,Constans.HOSPITAL_COLONOSCOPY_RECORD);
                    if(j<1){
                        throw new ItSysException(
                                GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
                    }
                }
                //查看DNA是否删除
                //判断DNA是否有录入结果
                List<StoolDna> stoolDnas =personDao.queryDNAByIdAndOperation(oldHospitalReview.getId(), Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                if(StringUtils.isEmpty(stoolDnas.get(0).getDnaCode())){
                    //删除代办
                    int i= personDao.deleteEventBySourceIdAndType(oldHospitalReview.getId(),Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW,Constans.PERSON_TODO_EVENT_TYPE4);
                    if(i<1){
                        throw new ItSysException(
                                GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
                    }
                    //删除数据
                    int j =personDao.deleeBySourceId(oldHospitalReview.getId(),Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW,Constans.HOSPITAL_STOOL_DNA);
                    if(j<1){
                        throw new ItSysException(
                                GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
                    }
                }
                //查看生物样本是否删除
                List<HospitalBiologicalSampleResultVo>  samples =personDao.querySampleByIdAndOperation(oldHospitalReview.getId(), Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                boolean idok=true;
                for (HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo:samples) {
                    if(Constans.COLLECT_STATUS_NO!=hospitalBiologicalSampleResultVo.getCollectStatus()){
                        idok=false;
                    }
                }
                //删除生物样本数据+代办
                if(idok){
                    //删除代办
                    int i= personDao.deleteEventBySourceIdAndType(oldHospitalReview.getId(),Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW,Constans.PERSON_TODO_EVENT_TYPE12);
                    if(i<1){
                        throw new ItSysException(
                                GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
                    }
                    int i1= personDao.deleteEventBySourceIdAndType(oldHospitalReview.getId(),Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW,Constans.PERSON_TODO_EVENT_TYPE13);
                    if(i1<1){
                        throw new ItSysException(
                                GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
                    }
                    int i2= personDao.deleteEventBySourceIdAndType(oldHospitalReview.getId(),Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW,Constans.PERSON_TODO_EVENT_TYPE18);
                    if(i2<1){
                        throw new ItSysException(
                                GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
                    }
                    //删除生物数据
                    int j =personDao.deleeBySourceId(oldHospitalReview.getId(),Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW,Constans.HOSPITAL_BIOLOGICAL_SAMPLE_RESULT);
                    if(j<1){
                        throw new ItSysException(
                                GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
                    }
                    //删除血液数据
                    int j1 =personDao.deleeBySourceId(oldHospitalReview.getId(),Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW,Constans.HOSPITAL_BIOLOGICAL_BLOOD_SAMPLE_RESULT);
                    if(j1<1){
                        throw new ItSysException(
                                GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
                    }

                }
                List<HospitalFitResult> hospitalFitResults = personDao.queryFitByIdAndOperation(oldHospitalReview.getId(), Constans.CD_OR_BY_SYSTEM_SAVE_FIT);
                if(hospitalFitResults.size()==0){
                    //新增一条fit记录
                    HospitalFitResult fitResult = new HospitalFitResult();
                    fitResult.setSid(sid);
                    fitResult.setStage(oldHospitalReview.getStageCy());
                    fitResult.setCodeEntryStatus(1);
                    fitResult.setAreaDeptId(oldHospitalReview.getAreaDeptId());
                    fitResult.setCommunityDeptId(oldHospitalReview.getCommunityDeptId());
                    fitResult.setOperationSource(Constans.OPERATION_SOURCE_TYPE_BY_SYSTEM);
                    fitResult.setEditoperationSource(Constans.CD_OR_BY_SYSTEM_SAVE_FIT);
                    fitResult.setOperationSourceId(oldHospitalReview.getId());
                    int fitId = personDao.addFitResult(fitResult);
                    log.info("@Service-person-update 新增 一条FIT数据返回的id  参数：fitId={}.", fitId);

                    HtEvent todo = new HtEvent();
                    todo.setCommunityDeptId(oldHospitalReview.getCommunityDeptId());
                    todo.setAreaDeptId(oldHospitalReview.getAreaDeptId());
                    todo.setSid(sid);
                    todo.setDataId(fitId);
                    todo.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
                    todo.setType(Constans.PERSON_TODO_EVENT_TYPE2);
                    todo.setOperationSource(Constans.CD_OR_BY_SYSTEM_SAVE_FIT);
                    todo.setOperationSourceId(oldHospitalReview.getId());
                    personDao.addTodoEvent(todo);
                    log.info("@Service-person-update 新增 一条 未录入FIT编号待办事件  参数：todo={}.", todo.toString());
                }
                int updateResult = personDao.updateRiskLevelBySid(Constans.PERSON_RISK_LEVEL1, sid, score);
                if (updateResult != 1) {
                    throw new ItSysException(
                            GlobalErrorCode.ERR_RISK_STATUS_CODE, GlobalErrorCode.ERR_RISK_STATUS_MSG);
                }

            }
            //判断原数据是低危并且新数据是高危
            if(oldSorce==1 && score>=4){
                // 查询fit有编码没有
                List<HospitalFitResult> hospitalFitResults = personDao.queryFitByIdAndOperation(oldHospitalReview.getId(), Constans.CD_OR_BY_SYSTEM_SAVE_FIT);

                if(StringUtils.isEmpty(hospitalFitResults.get(0).getFitCode())){
                    //删除fit代办
                    int i= personDao.deleteEventBySourceIdAndType(oldHospitalReview.getId(),Constans.CD_OR_BY_SYSTEM_SAVE_FIT,Constans.PERSON_TODO_EVENT_TYPE2);
                    if(i<1){
                        throw new ItSysException(
                                GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
                    }
                    //删除fit数据
                    int j =personDao.deleeBySourceId(oldHospitalReview.getId(),Constans.CD_OR_BY_SYSTEM_SAVE_FIT,Constans.HOSPITAL_FIT_RESULT);
                    if(j<1){
                        throw new ItSysException(
                                GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
                    }
                }

                List<HospitalColonoscopyRecord> hospitalColonoscopyRecords = personDao.queryRecordByIdAndOperation(oldHospitalReview.getId(), Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                HospitalTodoEvent hospitalTodoEvent = new HospitalTodoEvent();
                if(hospitalColonoscopyRecords.size()==0){
                    //高危 新增一条肠镜记录、
                    //生成结肠镜检查记录；
                    HospitalColonoscopyRecord record = new HospitalColonoscopyRecord();
                    record.setSid(sid);
                    record.setStage(oldHospitalReview.getStageCy());
                    record.setArea_dept_id(oldHospitalReview.getAreaDeptId());
                    record.setCommunity_dept_id(oldHospitalReview.getCommunityDeptId());
                    record.setReserve_status(COLONOSCOPY_RESERVE_STATUS_NOT_RESERVED);
                    record.setSource_type(Constans.SOURCE_TYPE1);
                    record.setOperationSource(Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                    record.setOperationSourceId(oldHospitalReview.getId());
                    int colonoscopyId = colonoscopyDao.add(record);
                    log.info("Insert colonoscopy record,id:{},body:{}", colonoscopyId, JSONUtils.toJson(record));

                    //新增 待预约结肠镜检查待办

                    hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE5);
                    Integer communityDeptId = oldHospitalReview.getCommunityDeptId();
                    if (communityDeptId != null) {

                        hospitalTodoEvent.setCommunityDeptId(communityDeptId);
                    }
                    Integer areaId = oldHospitalReview.getAreaDeptId();
                    if (communityDeptId != null) {

                        hospitalTodoEvent.setAreaDeptId(areaId);
                    }
                    hospitalTodoEvent.setSid(sid);
                    hospitalTodoEvent.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
                    hospitalTodoEvent.setDataId(colonoscopyId);
                    hospitalTodoEvent.setOperationSource(Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                    hospitalTodoEvent.setOperationSourceId(oldHospitalReview.getId());
                    int  addResult = personDao.addTodoEvent(hospitalTodoEvent);
                    if (addResult != 1) {
                        throw new ItSysException(
                                GlobalErrorCode.ERR_TODOEVENT_ADD_CODE, GlobalErrorCode.ERR_TODOEVENT_ADD_MSG);
                    }
                }

                //查看DNA是否删除
                //判断DNA是否有录入结果
                List<StoolDna> stoolDnas =personDao.queryDNAByIdAndOperation(oldHospitalReview.getId(), Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                if(stoolDnas.size()==0){
                    //生成 粪便DNA结果记录
                    StoolDna stoolDna = new StoolDna();
                    stoolDna.setSid(sid);
                    stoolDna.setStage(oldHospitalReview.getStageCy());
                    stoolDna.setAreaDeptId(oldHospitalReview.getAreaDeptId());
                    stoolDna.setCommunityDeptId(oldHospitalReview.getCommunityDeptId());
                    stoolDna.setOperationSource(Constans.OPERATION_SOURCE_TYPE_BY_SYSTEM);
                    stoolDna.setEditoperationSource(Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                    stoolDna.setOperationSourceId(oldHospitalReview.getId());
                    int dnaId = personDao.addStoolDna(stoolDna);
                    log.info("Insert dna record,id:{},body:{}", dnaId, JSONUtils.toJson(stoolDna));



                    //新增未录入粪便DNA编号待办
                    Integer communityDeptId = oldHospitalReview.getCommunityDeptId();
                    if (communityDeptId != null) {

                        hospitalTodoEvent.setCommunityDeptId(communityDeptId);
                    }
                    Integer areaId = oldHospitalReview.getAreaDeptId();
                    if (communityDeptId != null) {

                        hospitalTodoEvent.setAreaDeptId(areaId);
                    }
                    hospitalTodoEvent.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
                    hospitalTodoEvent.setSid(sid);
                    hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE4);
                    hospitalTodoEvent.setDataId(dnaId);
                    hospitalTodoEvent.setOperationSource(Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                    hospitalTodoEvent.setOperationSourceId(oldHospitalReview.getId());


                    int addDNAResult = personDao.addTodoEvent(hospitalTodoEvent);
                    if (addDNAResult != 1) {
                        throw new ItSysException(
                                GlobalErrorCode.ERR_TODOEVENT_ADD_CODE, GlobalErrorCode.ERR_TODOEVENT_ADD_MSG);
                    }
                }
                //查看生物样本是否删除
                List<HospitalBiologicalSampleResultVo>  samples =personDao.querySampleByIdAndOperation(oldHospitalReview.getId(), Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                if(samples.size()==0){
                    //生成生物样本
                    HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO = new HospitalBiologicalSampleResultVo();
                    String associatedSampleId = UUID.randomUUID().toString().replace("-", "");
                    //新增一条生物样本粪便
                    hospitalBiologicalSampleResultPO.setSid(sid);
                    hospitalBiologicalSampleResultPO.setCommunityDeptId(oldHospitalReview.getCommunityDeptId());
                    hospitalBiologicalSampleResultPO.setAreaDeptId(oldHospitalReview.getAreaDeptId());
                    hospitalBiologicalSampleResultPO.setStage(Constans.PERSON_COLONOSCOPY_STATUS1);
                    hospitalBiologicalSampleResultPO.setOperationSource(Constans.OPERATION_SOURCE_TYPE_BY_SYSTEM);
                    hospitalBiologicalSampleResultPO.setAssociatedSampleId(associatedSampleId);
                    hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE5);
                    hospitalBiologicalSampleResultPO.setEditoperationSource(Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                    hospitalBiologicalSampleResultPO.setOperationSourceId(oldHospitalReview.getId());
                    int sampleid = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO, Constans.BIOLOGICAL_SAMPLE_TABLE);
                    log.info("Insert sample,id:{},body:{}", sampleid, JSONUtils.toJson(hospitalBiologicalSampleResultPO));

                    //新增一条生物样本唾液
                    hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE4);
                    int sampleidByM = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO, Constans.BIOLOGICAL_SAMPLE_TABLE);
                    log.info("Insert sample,id:{},body:{}", sampleidByM, JSONUtils.toJson(hospitalBiologicalSampleResultPO));

                    //新增一条血液样本
                    hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE6);
                    int sampleidByA = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO, Constans.BIOLOGICAL_SAMPLE_TABLE);
                    log.info("Insert sample,id:{},body:{}", sampleidByM, JSONUtils.toJson(hospitalBiologicalSampleResultPO));

                    //新增一条生物样本白细胞
                    hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE3);
                    hospitalBiologicalSampleResultPO.setBloodSampleId(sampleidByA);
                    int sampleidByW = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO, Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE);
                    log.info("Insert sample,id:{},body:{}", sampleidByW, JSONUtils.toJson(hospitalBiologicalSampleResultPO));

                    //新增一条生物样本血清
                    hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE1);
                    int sampleidByS = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO, Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE);
                    log.info("Insert sample,id:{},body:{}", sampleidByS, JSONUtils.toJson(hospitalBiologicalSampleResultPO));

                    //新增一条生物样本血浆
                    hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE2);
                    int sampleidByP = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO, Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE);
                    log.info("Insert sample,id:{},body:{}", sampleidByP, JSONUtils.toJson(hospitalBiologicalSampleResultPO));

                    //新增未录入粪便DNA编号待办
                    Integer communityDeptId = oldHospitalReview.getCommunityDeptId();
                    if (communityDeptId != null) {

                        hospitalTodoEvent.setCommunityDeptId(communityDeptId);
                    }
                    Integer areaId = oldHospitalReview.getAreaDeptId();
                    if (communityDeptId != null) {

                        hospitalTodoEvent.setAreaDeptId(areaId);
                    }
                    hospitalTodoEvent.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
                    hospitalTodoEvent.setSid(sid);
                    //粪便系类
                    hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE12);
                    hospitalTodoEvent.setDataId(sampleid);
                    hospitalTodoEvent.setOperationSource(Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                    hospitalTodoEvent.setOperationSourceId(oldHospitalReview.getId());

                    int addSample = personDao.addTodoEvent(hospitalTodoEvent);
                    if (addSample != 1) {
                        throw new ItSysException(
                                GlobalErrorCode.ERR_TODOEVENT_ADD_CODE, GlobalErrorCode.ERR_TODOEVENT_ADD_MSG);
                    }
                    //录入生物样本数据唾液待办事件
                    hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE13);
                    hospitalTodoEvent.setDataId(sampleidByM);
                    hospitalTodoEvent.setOperationSource(Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                    hospitalTodoEvent.setOperationSourceId(oldHospitalReview.getId());
                    log.info("@Service-person-update 新增一条未录入生物样本数据唾液待办事件  参数：todo={}.", hospitalBiologicalSampleResultPO.toString());
                    int addsampleidByM = personDao.addTodoEvent(hospitalTodoEvent);
                    if (addsampleidByM != 1) {
                        throw new ItSysException(
                                GlobalErrorCode.ERR_TODOEVENT_ADD_CODE, GlobalErrorCode.ERR_TODOEVENT_ADD_MSG);
                    }
                    //血液代办
                    hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE18);
                    hospitalTodoEvent.setDataId(sampleidByA);
                    hospitalTodoEvent.setOperationSource(Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                    hospitalTodoEvent.setOperationSourceId(oldHospitalReview.getId());
                    log.info("@Service-person-update 新增一条未录入生物样本数据血液待办事件  参数：todo={}.", hospitalBiologicalSampleResultPO.toString());
                    int  addsampleidByA = personDao.addTodoEvent(hospitalTodoEvent);
                    if (addsampleidByA != 1) {
                        throw new ItSysException(
                                GlobalErrorCode.ERR_TODOEVENT_ADD_CODE, GlobalErrorCode.ERR_TODOEVENT_ADD_MSG);
                    }
                }

                int updateResult = personDao.updateRiskLevelBySid(Constans.PERSON_RISK_LEVEL2, sid, score);
                if (updateResult != 1) {
                    throw new ItSysException(
                            GlobalErrorCode.ERR_RISK_STATUS_CODE, GlobalErrorCode.ERR_RISK_STATUS_MSG);
                }

            }

        }else if(oldHospitalReview.getScore()!=null){
            String sid=oldHospitalReview.getSid();
            //拿出原来数据得到分数
            Integer oldSorce = oldHospitalReview.getRiskLevel();
            //根据id查
            HospitalRiskFactor hospitalRiskFactorBySid = personDao.findHospitalRiskFactorBySid(review.getSid());
            //现在分数 修改的(age+sex) + A2表单（getItem_5_3是否家族史+getItem_4_1//是否吸烟+bmi//体脂率 体重除以身高的平方））
            if (age >= 50 && age <= 54) {
                score = score + 0;
            }
            if (age >= 55 && age <= 64) {
                score = score + 1;
            }
            if (age >= 65 && age <= 74) {
                score = score + 2;
            }
            Integer sex = review.getSex();
            if (sex == 1) { //男
                score = score + 1;
            }
            if (sex == 2) { //女
                score = score + 0;
            }
            //是否家族史
            if (hospitalRiskFactorBySid.getItem_5_3() == 1) {
                score = score + 1;
            }
            if (hospitalRiskFactorBySid.getItem_5_3() == 2) {
                score = score + 0;
            }
            //是否吸烟
            if (hospitalRiskFactorBySid.getItem_4_1() == 1) {
                score = score + 0;
            }
            if (hospitalRiskFactorBySid.getItem_4_1() == 2) {
                score = score + 1;
            }
            //体脂率 体重除以身高的平方
            double height = (double) hospitalRiskFactorBySid.getHeight() / 100;
            double bmi = (double) hospitalRiskFactorBySid.getWeight() / (height * height);
            if (bmi >= 23) {
                score = score + 1;
            }
        }
        if(oldHospitalReview.getScore()!=null){
            //修改
            HospitalRiskFactor hospitalRiskFactor=new HospitalRiskFactor();
            hospitalRiskFactor.setScore(score);
            hospitalRiskFactor.setSid(oldHospitalReview.getSid());
            int i1 = personDao.updateSroceRiskFactor(hospitalRiskFactor);
            if(i1<1){
                throw new ItSysException(
                        GlobalErrorCode.ERR_RISK_STATUS_CODE, GlobalErrorCode.ERR_RISK_STATUS_MSG);
            }
            int i = personDao.updateHospitalIntestineReview(hospitalRiskFactor);
            if(i<1){
                throw new ItSysException(
                        GlobalErrorCode.ERR_RISK_STATUS_CODE, GlobalErrorCode.ERR_RISK_STATUS_MSG);
            }
        }
        hospitalReferenceRecordDao.updateForEdir(hospitalReferenceRecordDto);

        review.setApplyStatus(Constans.APPLY_EDIT_STATUS1);
        review.setApprovalStatus(null);
        review.setEditStatus(Constans.EDIT_STATUS1);
        personDao.updateReview(review, "hospital_intestine_review");
    }

    @Override
    public List<HospitalRiskFactorDto> findRiskfactorById(Integer id) {
        return personDao.findRiskfactorById(id);
    }

    @Override
    @Transactional
    public void updateHospitalRiskfactor(HospitalRiskFactorDto hospitalRiskFactorDto, HospitalRiskFactor hospitalRiskFactor, HospitalReferenceRecordDto hospitalReferenceRecordDto) {

        int score = 0;//分值累加

        //获取原来数据
        String sid = hospitalRiskFactorDto.getSid();
        HospitalReview OldhospitalReview = personDao.findInfoBySid(sid);
        //根据sid获取肠镜结果
        if (OldhospitalReview != null) {
            //获取分值和分组
           // Integer oldScore = OldhospitalReview.getScore();
            Integer oldScore= OldhospitalReview.getRiskLevel();
            String oldGroup = OldhospitalReview.getGroup();

            //获取新数据

            Integer age = OldhospitalReview.getAge();
            if (age >= 50 && age <= 54) {
                score = score + 0;
            }
            if (age >= 55 && age <= 64) {
                score = score + 1;
            }
            if (age >= 65 && age <= 74) {
                score = score + 2;
            }
            Integer sex = OldhospitalReview.getSex();
            if (sex == 1) { //男
                score = score + 1;
            }
            if (sex == 2) { //女
                score = score + 0;
            }
            //是否家族史
            if (hospitalRiskFactor.getItem_5_3() == 1) {
                score = score + 1;
            }
            if (hospitalRiskFactor.getItem_5_3() == 2) {
                score = score + 0;
            }
            //是否吸烟
            if (hospitalRiskFactor.getItem_4_1() == 1) {
                score = score + 0;
            }
            if (hospitalRiskFactor.getItem_4_1() == 2) {
                score = score + 1;
            }
            //体脂率 体重除以身高的平方
            double height = (double) hospitalRiskFactor.getHeight() / 100;
            double bmi = (double) hospitalRiskFactor.getWeight() / (height * height);
            if (bmi >= 23) {
                score = score + 1;
            }



            //判断是不是c组
            if ("C".equals(oldGroup)) {
                //判断原数聚是高危并且新数据是低危
                if(oldScore==2 && score<4){
                    //判断是否检查
                    List<HospitalColonoscopyRecord> hospitalColonoscopyRecords = personDao.queryRecordByIdAndOperation(OldhospitalReview.getId(), Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                    if(hospitalColonoscopyRecords.get(0).getExamination_status()==null){

                        //判断是否预约
                        if(hospitalColonoscopyRecords.get(0).getReserve_status()!=null&&hospitalColonoscopyRecords.get(0).getReserve_status()==2){
                            //删除放号信息
                            if(hospitalColonoscopyRecords.get(0).getAllocation_id()!=null){
                                reserveDetailDao.delReserveDetail(Integer.parseInt(hospitalColonoscopyRecords.get(0).getAllocation_id()),hospitalColonoscopyRecords.get(0).getSid());
                            }
                           //删除录入结果代办
                            int i= personDao.deleteEventBySourceIdAndType(hospitalColonoscopyRecords.get(0).getOperationSourceId(),hospitalColonoscopyRecords.get(0).getEditoperationSource(),Constans.PERSON_TODO_EVENT_TYPE8);
                            if(i<1){
                                throw new ItSysException(
                                        GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
                            }
                            //eventDao.updateStatus(hospitalColonoscopyRecords.get(0).getSid(),hospitalColonoscopyRecords.get(0).getId(),Constans.PERSON_TODO_EVENT_TYPE8,Constans.PERSON_TODO_EVENT_STATUS3);

                        }else{
                            //删除预约代办
                            int i= personDao.deleteEventBySourceIdAndType(OldhospitalReview.getId(),Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW,Constans.PERSON_TODO_EVENT_TYPE5);
                            if(i<1){
                                throw new ItSysException(
                                        GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
                            }
                        }

                        //删除预约数据 hospital_colonoscopy_record
                       int j =personDao.deleeBySourceId(OldhospitalReview.getId(),Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW,Constans.HOSPITAL_COLONOSCOPY_RECORD);
                        if(j<1){
                            throw new ItSysException(
                                    GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
                        }
                    }
                    //查看DNA是否删除
                    //判断DNA是否有录入结果
                    List<StoolDna> stoolDnas =personDao.queryDNAByIdAndOperation(OldhospitalReview.getId(), Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                    if(StringUtils.isEmpty(stoolDnas.get(0).getDnaCode())){
                        //删除代办
                        int i= personDao.deleteEventBySourceIdAndType(OldhospitalReview.getId(),Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW,Constans.PERSON_TODO_EVENT_TYPE4);
                        if(i<1){
                            throw new ItSysException(
                                    GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
                        }
                        //删除数据
                        int j =personDao.deleeBySourceId(OldhospitalReview.getId(),Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW,Constans.HOSPITAL_STOOL_DNA);
                        if(j<1){
                            throw new ItSysException(
                                    GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
                        }
                    }
                    //查看生物样本是否删除
                    List<HospitalBiologicalSampleResultVo>  samples =personDao.querySampleByIdAndOperation(OldhospitalReview.getId(), Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                    boolean idok=true;
                    for (HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo:samples) {
                        if(Constans.COLLECT_STATUS_NO!=hospitalBiologicalSampleResultVo.getCollectStatus()){
                            idok=false;
                        }
                    }
                    //删除生物样本数据+代办
                    if(idok){
                        //删除代办
                        int i= personDao.deleteEventBySourceIdAndType(OldhospitalReview.getId(),Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW,Constans.PERSON_TODO_EVENT_TYPE12);
                        if(i<1){
                            throw new ItSysException(
                                    GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
                        }
                        int i1= personDao.deleteEventBySourceIdAndType(OldhospitalReview.getId(),Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW,Constans.PERSON_TODO_EVENT_TYPE13);
                        if(i1<1){
                            throw new ItSysException(
                                    GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
                        }
                        int i2= personDao.deleteEventBySourceIdAndType(OldhospitalReview.getId(),Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW,Constans.PERSON_TODO_EVENT_TYPE18);
                        if(i2<1){
                            throw new ItSysException(
                                    GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
                        }
                        //删除生物数据
                        int j =personDao.deleeBySourceId(OldhospitalReview.getId(),Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW,Constans.HOSPITAL_BIOLOGICAL_SAMPLE_RESULT);
                        if(j<1){
                            throw new ItSysException(
                                    GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
                        }
                        //删除血液数据
                        int j1 =personDao.deleeBySourceId(OldhospitalReview.getId(),Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW,Constans.HOSPITAL_BIOLOGICAL_BLOOD_SAMPLE_RESULT);
                        if(j1<1){
                            throw new ItSysException(
                                    GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
                        }

                    }
                    List<HospitalFitResult> hospitalFitResults = personDao.queryFitByIdAndOperation(OldhospitalReview.getId(), Constans.CD_OR_BY_SYSTEM_SAVE_FIT);
                    if(hospitalFitResults.size()==0){
                        //新增一条fit记录
                        HospitalFitResult fitResult = new HospitalFitResult();
                        fitResult.setSid(sid);
                        fitResult.setStage(1);
                        fitResult.setCodeEntryStatus(1);
                        fitResult.setAreaDeptId(OldhospitalReview.getAreaDeptId());
                        fitResult.setCommunityDeptId(OldhospitalReview.getCommunityDeptId());
                        fitResult.setOperationSource(Constans.OPERATION_SOURCE_TYPE_BY_SYSTEM);
                        fitResult.setEditoperationSource(Constans.CD_OR_BY_SYSTEM_SAVE_FIT);
                        fitResult.setOperationSourceId(OldhospitalReview.getId());
                        int fitId = personDao.addFitResult(fitResult);
                        log.info("@Service-person-personInsert 新增 一条FIT数据返回的id  参数：fitId={}.", fitId);

                        HtEvent todo = new HtEvent();
                        todo.setCommunityDeptId(OldhospitalReview.getCommunityDeptId());
                        todo.setAreaDeptId(OldhospitalReview.getAreaDeptId());
                        todo.setSid(sid);
                        todo.setDataId(fitId);
                        todo.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
                        todo.setType(Constans.PERSON_TODO_EVENT_TYPE2);
                        todo.setOperationSource(Constans.CD_OR_BY_SYSTEM_SAVE_FIT);
                        todo.setOperationSourceId(OldhospitalReview.getId());
                        personDao.addTodoEvent(todo);
                        log.info("@Service-person-personInsert 新增 一条 未录入FIT编号待办事件  参数：todo={}.", todo.toString());
                    }
                    int updateResult = personDao.updateRiskLevelBySid(Constans.PERSON_RISK_LEVEL1, sid, score);
                    if (updateResult != 1) {
                        throw new ItSysException(
                                GlobalErrorCode.ERR_RISK_STATUS_CODE, GlobalErrorCode.ERR_RISK_STATUS_MSG);
                    }

                }
                //判断原数据是低危并且新数据是高危
                if(oldScore==1 && score>=4){
                    // 查询fit有编码没有
                    List<HospitalFitResult> hospitalFitResults = personDao.queryFitByIdAndOperation(OldhospitalReview.getId(), Constans.CD_OR_BY_SYSTEM_SAVE_FIT);

                    if(StringUtils.isEmpty(hospitalFitResults.get(0).getFitCode())){
                        //删除fit代办
                        int i= personDao.deleteEventBySourceIdAndType(OldhospitalReview.getId(),Constans.CD_OR_BY_SYSTEM_SAVE_FIT,Constans.PERSON_TODO_EVENT_TYPE2);
                        if(i<1){
                            throw new ItSysException(
                                    GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
                        }
                        //删除fit数据
                        int j =personDao.deleeBySourceId(OldhospitalReview.getId(),Constans.CD_OR_BY_SYSTEM_SAVE_FIT,Constans.HOSPITAL_FIT_RESULT);
                        if(j<1){
                            throw new ItSysException(
                                    GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
                        }
                    }

                    List<HospitalColonoscopyRecord> hospitalColonoscopyRecords = personDao.queryRecordByIdAndOperation(OldhospitalReview.getId(), Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                    HospitalTodoEvent hospitalTodoEvent = new HospitalTodoEvent();
                    if(hospitalColonoscopyRecords.size()==0){
                        //高危 新增一条肠镜记录、
                        //生成结肠镜检查记录；
                        HospitalColonoscopyRecord record = new HospitalColonoscopyRecord();
                        record.setSid(sid);
                        record.setStage(OldhospitalReview.getStageCy());
                        record.setArea_dept_id(OldhospitalReview.getAreaDeptId());
                        record.setCommunity_dept_id(OldhospitalReview.getCommunityDeptId());
                        record.setReserve_status(COLONOSCOPY_RESERVE_STATUS_NOT_RESERVED);
                        record.setSource_type(Constans.SOURCE_TYPE1);
                        record.setOperationSource(Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                        record.setOperationSourceId(OldhospitalReview.getId());
                        int colonoscopyId = colonoscopyDao.add(record);
                        log.info("Insert colonoscopy record,id:{},body:{}", colonoscopyId, JSONUtils.toJson(record));

                        //新增 待预约结肠镜检查待办

                        hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE5);
                        Integer communityDeptId = OldhospitalReview.getCommunityDeptId();
                        if (communityDeptId != null) {

                            hospitalTodoEvent.setCommunityDeptId(communityDeptId);
                        }
                        Integer areaId = OldhospitalReview.getAreaDeptId();
                        if (communityDeptId != null) {

                            hospitalTodoEvent.setAreaDeptId(areaId);
                        }
                        hospitalTodoEvent.setSid(sid);
                        hospitalTodoEvent.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
                        hospitalTodoEvent.setDataId(colonoscopyId);
                        hospitalTodoEvent.setOperationSource(Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                        hospitalTodoEvent.setOperationSourceId(OldhospitalReview.getId());
                        int  addResult = personDao.addTodoEvent(hospitalTodoEvent);
                        if (addResult != 1) {
                            throw new ItSysException(
                                    GlobalErrorCode.ERR_TODOEVENT_ADD_CODE, GlobalErrorCode.ERR_TODOEVENT_ADD_MSG);
                        }
                    }

                    //查看DNA是否删除
                    //判断DNA是否有录入结果
                    List<StoolDna> stoolDnas =personDao.queryDNAByIdAndOperation(OldhospitalReview.getId(), Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                    if(stoolDnas.size()==0){
                        //生成 粪便DNA结果记录
                        StoolDna stoolDna = new StoolDna();
                        stoolDna.setSid(sid);
                        stoolDna.setStage(STAGE_T0);
                        stoolDna.setAreaDeptId(OldhospitalReview.getAreaDeptId());
                        stoolDna.setCommunityDeptId(OldhospitalReview.getCommunityDeptId());
                        stoolDna.setOperationSource(Constans.OPERATION_SOURCE_TYPE_BY_SYSTEM);
                        stoolDna.setEditoperationSource(Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                        stoolDna.setOperationSourceId(OldhospitalReview.getId());
                        int dnaId = personDao.addStoolDna(stoolDna);
                        log.info("Insert dna record,id:{},body:{}", dnaId, JSONUtils.toJson(stoolDna));


                        Integer communityDeptId = OldhospitalReview.getCommunityDeptId();
                        if (communityDeptId != null) {

                            hospitalTodoEvent.setCommunityDeptId(communityDeptId);
                        }
                        Integer areaId = OldhospitalReview.getAreaDeptId();
                        if (communityDeptId != null) {

                            hospitalTodoEvent.setAreaDeptId(areaId);
                        }
                        hospitalTodoEvent.setSid(sid);
                        //新增未录入粪便DNA编号待办
                        hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE4);
                        hospitalTodoEvent.setDataId(dnaId);
                        hospitalTodoEvent.setOperationSource(Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                        hospitalTodoEvent.setOperationSourceId(OldhospitalReview.getId());
                        int addDNAResult = personDao.addTodoEvent(hospitalTodoEvent);
                        if (addDNAResult != 1) {
                            throw new ItSysException(
                                    GlobalErrorCode.ERR_TODOEVENT_ADD_CODE, GlobalErrorCode.ERR_TODOEVENT_ADD_MSG);
                        }
                    }
                    //查看生物样本是否删除
                    List<HospitalBiologicalSampleResultVo>  samples =personDao.querySampleByIdAndOperation(OldhospitalReview.getId(), Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                    if(samples.size()==0){
                        //生成生物样本
                        HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO = new HospitalBiologicalSampleResultVo();
                        String associatedSampleId = UUID.randomUUID().toString().replace("-", "");
                        //新增一条生物样本粪便
                        hospitalBiologicalSampleResultPO.setSid(sid);
                        hospitalBiologicalSampleResultPO.setCommunityDeptId(OldhospitalReview.getCommunityDeptId());
                        hospitalBiologicalSampleResultPO.setAreaDeptId(OldhospitalReview.getAreaDeptId());
                        hospitalBiologicalSampleResultPO.setStage(Constans.PERSON_COLONOSCOPY_STATUS1);
                        hospitalBiologicalSampleResultPO.setOperationSource(Constans.OPERATION_SOURCE_TYPE_BY_SYSTEM);
                        hospitalBiologicalSampleResultPO.setAssociatedSampleId(associatedSampleId);
                        hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE5);
                        hospitalBiologicalSampleResultPO.setEditoperationSource(Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                        hospitalBiologicalSampleResultPO.setOperationSourceId(OldhospitalReview.getId());
                        int sampleid = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO, Constans.BIOLOGICAL_SAMPLE_TABLE);
                        log.info("Insert sample,id:{},body:{}", sampleid, JSONUtils.toJson(hospitalBiologicalSampleResultPO));

                        //新增一条生物样本唾液
                        hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE4);
                        int sampleidByM = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO, Constans.BIOLOGICAL_SAMPLE_TABLE);
                        log.info("Insert sample,id:{},body:{}", sampleidByM, JSONUtils.toJson(hospitalBiologicalSampleResultPO));

                        //新增一条血液样本
                        hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE6);
                        int sampleidByA = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO, Constans.BIOLOGICAL_SAMPLE_TABLE);
                        log.info("Insert sample,id:{},body:{}", sampleidByM, JSONUtils.toJson(hospitalBiologicalSampleResultPO));

                        //新增一条生物样本白细胞
                        hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE3);
                        hospitalBiologicalSampleResultPO.setBloodSampleId(sampleidByA);
                        int sampleidByW = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO, Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE);
                        log.info("Insert sample,id:{},body:{}", sampleidByW, JSONUtils.toJson(hospitalBiologicalSampleResultPO));

                        //新增一条生物样本血清
                        hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE1);
                        int sampleidByS = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO, Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE);
                        log.info("Insert sample,id:{},body:{}", sampleidByS, JSONUtils.toJson(hospitalBiologicalSampleResultPO));

                        //新增一条生物样本血浆
                        hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE2);
                        int sampleidByP = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO, Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE);
                        log.info("Insert sample,id:{},body:{}", sampleidByP, JSONUtils.toJson(hospitalBiologicalSampleResultPO));

                        Integer communityDeptId = OldhospitalReview.getCommunityDeptId();
                        if (communityDeptId != null) {

                            hospitalTodoEvent.setCommunityDeptId(communityDeptId);
                        }
                        Integer areaId = OldhospitalReview.getAreaDeptId();
                        if (communityDeptId != null) {

                            hospitalTodoEvent.setAreaDeptId(areaId);
                        }
                        hospitalTodoEvent.setSid(sid);
                        //粪便系类
                        hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE12);
                        hospitalTodoEvent.setDataId(sampleid);
                        hospitalTodoEvent.setOperationSource(Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                        hospitalTodoEvent.setOperationSourceId(OldhospitalReview.getId());
                        int addSample = personDao.addTodoEvent(hospitalTodoEvent);
                        if (addSample != 1) {
                            throw new ItSysException(
                                    GlobalErrorCode.ERR_TODOEVENT_ADD_CODE, GlobalErrorCode.ERR_TODOEVENT_ADD_MSG);
                        }
                        //录入生物样本数据唾液待办事件
                        hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE13);
                        hospitalTodoEvent.setDataId(sampleidByM);
                        hospitalTodoEvent.setOperationSource(Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                        hospitalTodoEvent.setOperationSourceId(OldhospitalReview.getId());
                        log.info("@Service-person-personInsert 新增一条未录入生物样本数据唾液待办事件  参数：todo={}.", hospitalBiologicalSampleResultPO.toString());
                        int addsampleidByM = personDao.addTodoEvent(hospitalTodoEvent);
                        if (addsampleidByM != 1) {
                            throw new ItSysException(
                                    GlobalErrorCode.ERR_TODOEVENT_ADD_CODE, GlobalErrorCode.ERR_TODOEVENT_ADD_MSG);
                        }
                        //血液代办
                        hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE18);
                        hospitalTodoEvent.setDataId(sampleidByA);
                        hospitalTodoEvent.setOperationSource(Constans.CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW);
                        hospitalTodoEvent.setOperationSourceId(OldhospitalReview.getId());
                        log.info("@Service-person-personInsert 新增一条未录入生物样本数据血液待办事件  参数：todo={}.", hospitalBiologicalSampleResultPO.toString());
                        int  addsampleidByA = personDao.addTodoEvent(hospitalTodoEvent);
                        if (addsampleidByA != 1) {
                            throw new ItSysException(
                                    GlobalErrorCode.ERR_TODOEVENT_ADD_CODE, GlobalErrorCode.ERR_TODOEVENT_ADD_MSG);
                        }
                    }



                    int updateResult = personDao.updateRiskLevelBySid(Constans.PERSON_RISK_LEVEL2, sid, score);
                    if (updateResult != 1) {
                        throw new ItSysException(
                                GlobalErrorCode.ERR_RISK_STATUS_CODE, GlobalErrorCode.ERR_RISK_STATUS_MSG);
                    }

                }


            }

            //修改危险因素表
            hospitalRiskFactor.setScore(score);//添加得分字段  by maxiang at 2018-06-25
            hospitalRiskFactor.setApplyStatus(Constans.APPLY_EDIT_STATUS1);
            hospitalRiskFactor.setApprovalStatus(null);
            hospitalRiskFactor.setEditStatus(Constans.EDIT_STATUS1);
            int addRiskResult = personDao.updateRiskFactor(hospitalRiskFactor);

            if (addRiskResult != 1) {
                throw new ItSysException(
                        GlobalErrorCode.ERR_RISKFACTOR_ADD_CODE, GlobalErrorCode.ERR_RISKFACTOR_ADD_MSG);
            }
            //添加编辑记录
            try{
                hospitalReferenceRecordDao.updateForEdir(hospitalReferenceRecordDto);
            }catch (Exception e){
                throw new ItSysException(
                        GlobalErrorCode.HOSPITAL_MESSAGE_CENTER_UPDATE_ERROR_CODE, GlobalErrorCode.HOSPITAL_MESSAGE_CENTER_UPDATE_ERROR_MSG);
            }

        } else {
            throw new ItSysException(
                    GlobalErrorCode.HOSPITAL_MESSAGE_CENTER_UPDATE_ERROR_CODE, GlobalErrorCode.HOSPITAL_MESSAGE_CENTER_UPDATE_ERROR_MSG);
        }


    }
}

