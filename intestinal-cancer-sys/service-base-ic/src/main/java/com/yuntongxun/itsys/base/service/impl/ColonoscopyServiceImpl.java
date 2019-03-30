package com.yuntongxun.itsys.base.service.impl;

import com.yuntongxun.itsys.base.cache.RedisManager;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.constans.RedisConstant;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.*;
import com.yuntongxun.itsys.base.dao.*;
import com.yuntongxun.itsys.base.po.*;
import com.yuntongxun.itsys.base.po.dto.ItsysUserDto;
import com.yuntongxun.itsys.base.po.dto.allocation.AllocationDto;
import com.yuntongxun.itsys.base.service.ColonoscopyService;
import com.yuntongxun.itsys.base.service.UserService;
import com.yuntongxun.itsys.base.vo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ColonoscopyServiceImpl implements ColonoscopyService {

    private final Logger log = LogManager.getLogger(ColonoscopyServiceImpl.class);

    @Autowired
    private ColonoscopyDao colonoscopyDao;
    @Autowired
    private ReserveAllocationDao reserveAllocationDao;
    @Autowired
    private ReserveDetailDao reserveDetailDao;
    @Autowired
    private PersonDao personDao;
    @Autowired
    private HtEventDao eventDao;
    @Autowired
    private UserService userService;
    @Autowired
    private ScreeningNotificationDao screeningNotificationDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisManager redis;
    //就诊状态，1：未就诊，2：已就诊
    public static final int EXAMINATION_STATUS_NOT_DO = 1;
    public static final int EXAMINATION_STATUS_DO = 2;

    //结肠镜预约状态，1：未预约，2：已预约
    public static final int COLONOSCOPY_RESERVE_STATUS_NOT_RESERVED = 1;
    public static final int COLONOSCOPY_RESERVE_STATUS_RESERVED = 2;

    //结肠镜告知书发放状态，1：未发放，2：已发放
    public static final int COLONOSCOPY_NOTIFICATION_NOT_ISSUE = 1;
    public static final int COLONOSCOPY_NOTIFICATION_ISSUED = 2;

    //结肠镜告知书发放方式，1：受试者/家属到社区中心自取；2：社区工作人员入户递送；3：邻居从社区中心捎带取走；4：受试者/家属到医院自取；5：其它，请备注；
    public static final int COLONOSCOPY_NOTIFICATION_ISSUE_MODE_1 = 1;
    public static final int COLONOSCOPY_NOTIFICATION_ISSUE_MODE_2 = 2;
    public static final int COLONOSCOPY_NOTIFICATION_ISSUE_MODE_3 = 3;
    public static final int COLONOSCOPY_NOTIFICATION_ISSUE_MODE_4 = 4;
    public static final int COLONOSCOPY_NOTIFICATION_ISSUE_MODE_5 = 5;


    //事件状态，1：待办，2：已办
    public static final int EVENT_STATUS_NOT_DO = 1;
    public static final int EVENT_STATUS_DONE = 2;

    //事件类型1：未完成危险因素调查表，2：未录入FIT编号，3：未录入FIT结果，4：未录入粪便DNA装置编号，5：未预约结肠镜检查，
    //6：未完成结肠镜检查，7：未发放筛查结果告知书，8：待录入肠镜结果，9：待录入病理结果，10：待录入筛查结果告知书，
    //11：待接收生物样本
    public static final int EVENT_TYPE_1 = 1;
    public static final int EVENT_TYPE_2 = 2;
    public static final int EVENT_TYPE_3 = 3;
    public static final int EVENT_TYPE_4 = 4;
    public static final int EVENT_TYPE_5 = 5;
    public static final int EVENT_TYPE_6 = 6;
    public static final int EVENT_TYPE_7 = 7;
    public static final int EVENT_TYPE_8 = 8;
    public static final int EVENT_TYPE_9 = 9;
    public static final int EVENT_TYPE_10 = 10;
    public static final int EVENT_TYPE_11 = 11;


    public final static int DEPARTMENT_TYPE_COMMUNITY = 1;
    public final static int DEPARTMENT_TYPE_AREA = 2;
    public final static int DEPARTMENT_TYPE_NATION = 3;

    @Override
    public ListPageUtil query(ColonoscopyVo queryCondition, String loginName) {
        //查询登录用户信息
        DoctorInfo doctorInfo = getDoctorInfo(loginName);

        //分页查询结肠镜检查数据
        ListPageUtil listPage = colonoscopyDao.query(queryCondition, doctorInfo.getCommunityDeptId(), doctorInfo.getAreaDeptId(), doctorInfo.getHospitalType(), true);
        //计算是否可以取消预约
        if (listPage != null && listPage.getResultList() != null && listPage.getResultList().size() > 0) {
            List<Object> resultList = new ArrayList<Object>();
            Map tempMap = null;
            for (Object obj : listPage.getResultList()) {
                tempMap = (Map) obj;
                // 预约状态为已预约 检查状态为空 并且通过系统预约 并且在预约时间的前
                Integer reserveStatus = (Integer) tempMap.get("reserveStatus");
                Integer reserveDateStatus = 1;
                if (tempMap.get("reserveDate") != null) {
                    Date date = (Date) tempMap.get("reserveDate");
                    if (date.getTime() > new Date().getTime()) {
                        reserveDateStatus = 2;
                    }
                }
                if (reserveStatus != null && reserveStatus == 2 && tempMap.get("examinationStatus") == null && tempMap.get("allocationId") != null && reserveDateStatus == 2) {
                    tempMap.put("cancelBookingState", 2);//表示能取消预约
                } else {
                    tempMap.put("cancelBookingState", 1);//表示不能取消预约
                }
                resultList.add(tempMap);
            }
            listPage.setResultList(resultList);
        }

        return listPage;
    }

    @Override
    public ListPageUtil queryForArea(ColonoscopyVo queryCondition, String loginName) {
        //查询登录用户信息
        DoctorInfo doctorInfo = getDoctorInfo(loginName);
        //分页查询结肠镜检查数据
        ListPageUtil listPage = colonoscopyDao.queryForArea(queryCondition, queryCondition.getCommunityDeptId(), doctorInfo.getAreaDeptId(), doctorInfo.getHospitalType(), true);
        //计算是否可以取消预约
        if (listPage != null && listPage.getResultList() != null && listPage.getResultList().size() > 0) {
            List<Object> resultList = new ArrayList<Object>();
            Map tempMap = null;
            for (Object obj : listPage.getResultList()) {
                tempMap = (Map) obj;
                // 预约状态为已预约 检查状态为空 并且通过系统预约 并且在预约时间的前
                Integer reserveStatus = (Integer) tempMap.get("reserveStatus");
                Integer reserveDateStatus = 1;
                if (tempMap.get("reserveDate") != null) {
                    Date date = (Date) tempMap.get("reserveDate");
                    if (date.getTime() > new Date().getTime()) {
                        reserveDateStatus = 2;
                    }
                }
                if (reserveStatus != null && reserveStatus == 2 && tempMap.get("examinationStatus") == null && tempMap.get("allocationId") != null && reserveDateStatus == 2) {
                    tempMap.put("cancelBookingState", 2);//表示能取消预约
                } else {
                    tempMap.put("cancelBookingState", 1);//表示不能取消预约
                }
                resultList.add(tempMap);
            }
            listPage.setResultList(resultList);
        }

        return listPage;
    }

    @Override
    @Transactional
    public void booking(ColonoscopyVo vo, String loginName) {



        if (vo.getBookingEntrance() != null && vo.getBookingEntrance() == 2) {//表示不用本系统的预约
            DoctorInfo doctorInfo = getDoctorInfo(loginName);
            //查询资格审核表数据
            HospitalReview review = personDao.getBySid(vo.getSid());

            //根据id查询Record表
            //修改来源
            List<HospitalColonoscopyRecord> hospitalColonoscopyRecords = colonoscopyDao.queryById(vo.getColonoscopyRecordId());
            if (hospitalColonoscopyRecords.size() != 1) {
                throw new ItSysException(GlobalErrorCode.ERR_COLONOSCOPY_RESERVE_NUMBER_FULL_ERROR_CODE, GlobalErrorCode.ERR_COLONOSCOPY_RESERVE_NUMBER_FULL_ERROR_MSG);
            }

            //更新结肠镜预约状态为  已预约
            colonoscopyDao.updateReserveStatusByOtherSys(vo.getColonoscopyRecordId(), doctorInfo.getId(), COLONOSCOPY_RESERVE_STATUS_RESERVED, vo.getReserveDate());
            //完成待办数据,（未预约结肠镜检查）
            eventDao.updateStatus(vo.getSid(), vo.getColonoscopyRecordId(), EVENT_TYPE_5, EVENT_STATUS_DONE);
            //生成待办数据 ---增加待录入肠镜结果modify by maxiang 2018-05-06
            HospitalTodoEvent hospitalTodoEvent = new HospitalTodoEvent();
            hospitalTodoEvent.setCommunityDeptId(review.getCommunityDeptId());
            hospitalTodoEvent.setAreaDeptId(review.getAreaDeptId());
            hospitalTodoEvent.setDataId(vo.getColonoscopyRecordId());
            hospitalTodoEvent.setSid(vo.getSid());
            hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE8);
            hospitalTodoEvent.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
            //修改来源
            hospitalTodoEvent.setOperationSource(hospitalColonoscopyRecords.get(0).getEditoperationSource());
            hospitalTodoEvent.setOperationSourceId(hospitalColonoscopyRecords.get(0).getOperationSourceId());
            personDao.addTodoEvent(hospitalTodoEvent);
        } else {
            //校验id放号是否停诊
            List<AllocationDto> allocationDtos = colonoscopyDao.queryAllocationById(vo.getAllocationId());

            if(Constans.USESTATUS2.equals(allocationDtos.get(0).getUseStatus())){
                throw new ItSysException(GlobalErrorCode.ERR_COLONOSCOPY_RESERVE_NUMBER_FULL_ERROR_CODE, GlobalErrorCode.ERR_COLONOSCOPY_RESERVE_NUMBER_FULL_ERROR_MSG);
            }

            //查询预约分配数据
            ReserveAllocation reserveAllocation = reserveAllocationDao.getReserveAllocation(vo.getAllocationId());
            int maxReserveCount = reserveAllocation.getAmount();
            //查询该预约分配记录已预约人数
            int reservedCount = reserveDetailDao.getReservedCount(vo.getAllocationId());
            if (reservedCount < maxReserveCount) {
                //当已预约人数<最大预约人数时，可预约
                //获取当前用户信息
                DoctorInfo doctorInfo = getDoctorInfo(loginName);
                //查询资格审核表数据
                HospitalReview review = personDao.getBySid(vo.getSid());

                //根据id查询Record表
                //修改来源
                List<HospitalColonoscopyRecord> hospitalColonoscopyRecords = colonoscopyDao.queryById(vo.getColonoscopyRecordId());
                if (hospitalColonoscopyRecords.size() != 1) {
                    throw new ItSysException(GlobalErrorCode.ERR_COLONOSCOPY_RESERVE_NUMBER_FULL_ERROR_CODE, GlobalErrorCode.ERR_COLONOSCOPY_RESERVE_NUMBER_FULL_ERROR_MSG);
                }
/*
                if(COLONOSCOPY_RESERVE_STATUS_RESERVED==hospitalColonoscopyRecords.get(0).getReserve_status()){
                    throw new ItSysException(GlobalErrorCode.ERR_COLONOSCOPY_RESERVE_NUMBER_FULL_ERROR_CODE, GlobalErrorCode.ERR_COLONOSCOPY_RESERVE_NUMBER_FULL_ERROR_MSG);
                }
*/

                //新增一条预约详情数据
                ReservationDetail detail = new ReservationDetail();
                detail.setAllocationId(vo.getAllocationId());
                detail.setAreaDeptId(review.getAreaDeptId());
                detail.setCommunityDeptId(review.getCommunityDeptId());
                detail.setSid(vo.getSid());
                detail.setStage(review.getStageCy());
                detail.setStatus(EXAMINATION_STATUS_NOT_DO);
                int reserveId = reserveDetailDao.save(detail);

                //查询结肠镜记录

                //更新结肠镜预约状态为  已预约
                colonoscopyDao.updateReserveStatus(vo.getColonoscopyRecordId(), reserveAllocation.getReservationDate(), doctorInfo.getId(), reserveId, COLONOSCOPY_RESERVE_STATUS_RESERVED, reserveAllocation.getId());

                //完成待办数据,（未预约结肠镜检查）
                eventDao.updateStatus(vo.getSid(), vo.getColonoscopyRecordId(), EVENT_TYPE_5, EVENT_STATUS_DONE);

                //完成待办数据,（未完成结肠镜检查）
                eventDao.updateStatus(vo.getSid(), vo.getColonoscopyRecordId(), EVENT_TYPE_6, EVENT_STATUS_DONE);

                //生成待办数据 ---增加待录入肠镜结果modify by maxiang 2018-05-06
                HospitalTodoEvent hospitalTodoEvent = new HospitalTodoEvent();
                hospitalTodoEvent.setCommunityDeptId(review.getCommunityDeptId());
                hospitalTodoEvent.setAreaDeptId(review.getAreaDeptId());
                hospitalTodoEvent.setDataId(vo.getColonoscopyRecordId());
                hospitalTodoEvent.setSid(vo.getSid());
                hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE8);
                hospitalTodoEvent.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);

                //修改来源
                hospitalTodoEvent.setOperationSource(hospitalColonoscopyRecords.get(0).getEditoperationSource());
                hospitalTodoEvent.setOperationSourceId(hospitalColonoscopyRecords.get(0).getOperationSourceId());


                personDao.addTodoEvent(hospitalTodoEvent);
//			hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE9);
//			personDao.addTodoEvent(hospitalTodoEvent);
//			hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE10);
//			personDao.addTodoEvent(hospitalTodoEvent);
            } else {
                throw new ItSysException(GlobalErrorCode.ERR_COLONOSCOPY_RESERVE_NUMBER_FULL_ERROR_CODE, GlobalErrorCode.ERR_COLONOSCOPY_RESERVE_NUMBER_FULL_ERROR_MSG);
            }
        }
    }

    @Override
    @Transactional
    public void rebooking(ColonoscopyVo vo, String loginName) {
        log.info("@Service-rebooking start.");
        //查询预约分配数据
        ReserveAllocation reserveAllocation = reserveAllocationDao.getReserveAllocation(vo.getAllocationId());
        int maxReserveCount = reserveAllocation.getAmount();
        //查询该预约分配记录已预约人数
        int reservedCount = reserveDetailDao.getReservedCount(vo.getAllocationId());

        if (reservedCount < maxReserveCount) {
            //当已预约人数<最大预约人数时，可预约
            //获取当前用户信息
            DoctorInfo doctorInfo = getDoctorInfo(loginName);
            //查询资格审核表数据
            HospitalReview review = personDao.getBySid(vo.getSid());
            //完成待办数据,（未完成结肠镜检查）
            eventDao.updateStatus(vo.getSid(), vo.getColonoscopyRecordId(), EVENT_TYPE_6, EVENT_STATUS_DONE);

            //新增一条预约详情数据
            ReservationDetail detail = new ReservationDetail();
            detail.setAllocationId(vo.getAllocationId());
            detail.setAreaDeptId(review.getAreaDeptId());
            detail.setCommunityDeptId(review.getCommunityDeptId());
            detail.setSid(vo.getSid());
            detail.setStage(review.getStageCy());
            detail.setStatus(EXAMINATION_STATUS_NOT_DO);
            int reserveId = reserveDetailDao.save(detail);

            //新增一条肠镜检查记录
            HospitalColonoscopyRecord result = new HospitalColonoscopyRecord();
            result.setSid(vo.getSid());
            result.setStage(Constans.PERSON_COLONOSCOPY_STATUS1);
            result.setCommunity_dept_id(review.getCommunityDeptId());
            result.setArea_dept_id(review.getAreaDeptId());
            result.setReserve_status(Constans.RESERVE_STATUS2);
            result.setReserve_id(reserveId);
            result.setAllocation_id(String.valueOf(reserveAllocation.getId()));
            result.setReserve_date(reserveAllocation.getReservationDate());
            result.setReserve_operator(doctorInfo.getId());
            result.setSource_type(Constans.SOURCE_TYPE1);
            result.setReserve_status_date(new Date());
            //待录入状态
            result.setResult_status(Constans.RESULT_STATUS1);
//			result.setPathology_status(Constans.PATHOLOGY_STATUS1);
//			result.setNotification_entry_status(Constans.NOTIFICATION_ENTRY_STATUS1);
            int dataId = personDao.addColonoscopyRecord(result);
            //生成待办数据 ---增加待录入肠镜结果modify by maxiang 2018-04-30
            HospitalTodoEvent hospitalTodoEvent = new HospitalTodoEvent();
            hospitalTodoEvent.setCommunityDeptId(review.getCommunityDeptId());
            hospitalTodoEvent.setAreaDeptId(review.getAreaDeptId());
            hospitalTodoEvent.setDataId(dataId);
            hospitalTodoEvent.setSid(vo.getSid());
            hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE8);
            hospitalTodoEvent.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
            personDao.addTodoEvent(hospitalTodoEvent);
//			hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE9);
//			personDao.addTodoEvent(hospitalTodoEvent);
//			hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE10);
//			personDao.addTodoEvent(hospitalTodoEvent);

            log.info("@Service-rebooking 重新预约成功，添加结肠镜检查记录数据为  参数：result={}.", result.toString());

        } else {
            throw new ItSysException(GlobalErrorCode.ERR_COLONOSCOPY_RESERVE_NUMBER_FULL_ERROR_CODE, GlobalErrorCode.ERR_COLONOSCOPY_RESERVE_NUMBER_FULL_ERROR_MSG);
        }
        log.info("@Service-rebooking end.");
    }

    @Transactional
    @Override
    public void cancelBooking(ColonoscopyVo vo, String loginName) {
        if (vo.getColonoscopyRecordId() == null) {
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }
        if (vo.getSid() == null) {
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }
        HospitalColonoscopyRecord record = personDao.findRecordByRecordId(vo.getColonoscopyRecordId());
        if (record == null) {
            throw new ItSysException(GlobalErrorCode.OBJECT_NOT_EXISTS + "", GlobalErrorCode.OBJECT_NOT_EXISTS_MSG);
        }
        //删除放号信息
        if (record.getAllocation_id() != null) {
            reserveDetailDao.delReserveDetail(Integer.parseInt(record.getAllocation_id()), vo.getSid());
        }
        //完成待办数据,（待录入结肠镜检查）
        eventDao.updateStatus(vo.getSid(), vo.getColonoscopyRecordId(), EVENT_TYPE_8, EVENT_STATUS_DONE);

        //根据id查询Record表
        //修改来源
        List<HospitalColonoscopyRecord> hospitalColonoscopyRecords = colonoscopyDao.queryById(vo.getColonoscopyRecordId());
        if (hospitalColonoscopyRecords.size() != 1) {
            throw new ItSysException(GlobalErrorCode.ERR_COLONOSCOPY_RESERVE_NUMBER_FULL_ERROR_CODE, GlobalErrorCode.ERR_COLONOSCOPY_RESERVE_NUMBER_FULL_ERROR_MSG);
        }

        //更改预约状态
        colonoscopyDao.updateReserveStatusForCancelBooking(vo.getColonoscopyRecordId());
        //获取当前用户信息
        DoctorInfo doctorInfo = getDoctorInfo(loginName);
        //查询资格审核表数据
        HospitalReview review = personDao.getBySid(vo.getSid());
        //生成待办数据 ---增加待预约肠镜
        HospitalTodoEvent hospitalTodoEvent = new HospitalTodoEvent();
        hospitalTodoEvent.setCommunityDeptId(review.getCommunityDeptId());
        hospitalTodoEvent.setAreaDeptId(review.getAreaDeptId());
        hospitalTodoEvent.setDataId(vo.getColonoscopyRecordId());
        hospitalTodoEvent.setSid(vo.getSid());
        hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE5);
        hospitalTodoEvent.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);

        //修改来源
        hospitalTodoEvent.setOperationSource(hospitalColonoscopyRecords.get(0).getEditoperationSource());
        hospitalTodoEvent.setOperationSourceId(hospitalColonoscopyRecords.get(0).getOperationSourceId());


        personDao.addTodoEvent(hospitalTodoEvent);
    }

    @Transactional
    @Override
    public void cancelBooking(ColonoscopyVo vo) {
        if (vo.getColonoscopyRecordId() == null) {
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }
        if (vo.getSid() == null) {
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }
        HospitalColonoscopyRecord record = personDao.findRecordByRecordId(vo.getColonoscopyRecordId());
        if (record == null) {
            throw new ItSysException(GlobalErrorCode.OBJECT_NOT_EXISTS + "", GlobalErrorCode.OBJECT_NOT_EXISTS_MSG);
        }
        //删除放号信息
        if (record.getAllocation_id() != null && record.getReserve_id() != null) {
            reserveDetailDao.delReserveDetailByids(Integer.parseInt(record.getAllocation_id()), vo.getSid(), record.getReserve_id());
        }
        //完成待办数据,（待录入结肠镜检查）
        eventDao.updateStatus(vo.getSid(), vo.getColonoscopyRecordId(), EVENT_TYPE_8, EVENT_STATUS_DONE);

        //根据id查询Record表
        //修改来源
        List<HospitalColonoscopyRecord> hospitalColonoscopyRecords = colonoscopyDao.queryById(vo.getColonoscopyRecordId());
        if (hospitalColonoscopyRecords.size() != 1) {
            throw new ItSysException(GlobalErrorCode.ERR_COLONOSCOPY_RESERVE_NUMBER_FULL_ERROR_CODE, GlobalErrorCode.ERR_COLONOSCOPY_RESERVE_NUMBER_FULL_ERROR_MSG);
        }

        //更改预约状态
        colonoscopyDao.updateReserveStatusForCancelBooking(vo.getColonoscopyRecordId());
        //查询资格审核表数据
        HospitalReview review = personDao.getBySid(vo.getSid());
        //生成待办数据 ---增加待预约肠镜
        HospitalTodoEvent hospitalTodoEvent = new HospitalTodoEvent();
        hospitalTodoEvent.setCommunityDeptId(review.getCommunityDeptId());
        hospitalTodoEvent.setAreaDeptId(review.getAreaDeptId());
        hospitalTodoEvent.setDataId(vo.getColonoscopyRecordId());
        hospitalTodoEvent.setSid(vo.getSid());
        hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE5);
        hospitalTodoEvent.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);

        //修改来源
        hospitalTodoEvent.setOperationSource(hospitalColonoscopyRecords.get(0).getEditoperationSource());
        hospitalTodoEvent.setOperationSourceId(hospitalColonoscopyRecords.get(0).getOperationSourceId());


        personDao.addTodoEvent(hospitalTodoEvent);
    }

    @Override
    public List<ItsysUserDto> queryloginNamesByloginName(String loginName) {
        return userDao.getParentName(loginName);
    }

    @Override
    public List<ItsysUserDto> queryAllloginNamesByloginName(String loginName) {
        return userDao.getAllParentName(loginName);
    }

    @Override
    public List<ItsysUserDto> queryloginNameRootByloginName(String loginName) {
        return userDao.queryloginNameRootByloginName(loginName);
    }

    @Override
    public ReserveDetail getReserveDetail(int id) {
        //根据id查询预约单所需信息
        ReserveDetail reserveDetail = colonoscopyDao.getReserveDetail(id);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String reservationDate = df.format(reserveDetail.getReservationDate());
        reserveDetail.setBookingTime(reservationDate + " " + reserveDetail.getStartTime() + "-" + reserveDetail.getEndTime());
        reserveDetail.setProName("结肠镜检查");


//		String phone = reserveDetail.getPhone();
//		if (phone != null && !"".equals(phone) && phone.length() > 7) {
//			phone = phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4, phone.length());
//			reserveDetail.setPhone(phone);
//		}
        //身份证脱敏  update by maxiang at 2018-07-30
        String idCard = reserveDetail.getIdCard();
        if (idCard != null && !"".equals(idCard) && idCard.length() == 18) {
            idCard = idCard.substring(0, 10) + "****" + idCard.substring(idCard.length() - 4, idCard.length());
            reserveDetail.setIdCard(idCard);
        }

        if (idCard != null && !"".equals(idCard) && idCard.length() == 15) {
            idCard = idCard.substring(0, 8) + "****" + idCard.substring(idCard.length() - 3, idCard.length());
            reserveDetail.setIdCard(idCard);
        }
        return reserveDetail;
    }

    @Override
    @Transactional
    public void issueNotification(ColonoscopyIssueVo vo, String loginName) {
        //获取当前用户信息
        DoctorInfo doctorInfo = getDoctorInfo(loginName);
        //1、更新 结肠镜检查记录表 hospital_colonoscopy_record 表的相关字段，notification_issue_status=2，notification_issue_date=当前日期，notification_issue_operator=当前操作用户id；
        colonoscopyDao.updateNotificationIssueStatus(vo, doctorInfo.getId(), COLONOSCOPY_NOTIFICATION_ISSUED);

        //2、完成 发放告知书待办事件，更新 待办事件表 hospital_todo_event，where type=7，sid=受试者sid，data_id = hospital_colonoscopy_record 主键；set status = 2；
        eventDao.updateStatus(vo.getSid(), vo.getId(), EVENT_TYPE_7, EVENT_STATUS_DONE);

    }

    @Override
    public DoctorInfo getDoctorInfo(String loginName) {
        //获取当前用户信息
        String key = RedisConstant.HOSPITAL_KEY_INFO + loginName;
        String value = redis.get(key);
        DoctorInfo doctorInfo = null;
        if (StringUtil.isNotBlank(value)) {
            doctorInfo = JSONUtils.toBean(value, DoctorInfo.class);
        }
        if (doctorInfo == null) {
            doctorInfo = userService.getHospitalInfo(loginName);
        }
        if (doctorInfo == null) {
            throw new ItSysException(GlobalErrorCode.ERR_USER_INFO_INCOMPLETE_ERROR_CODE, GlobalErrorCode.ERR_USER_INFO_INCOMPLETE_ERROR_MSG);
        }
        return doctorInfo;
    }

    @Override
    public ColonoscopyNotificationVo getNotification(int id, HospitalColonoscopyResult hospitalColonoscopyResult) {

        if (hospitalColonoscopyResult != null && hospitalColonoscopyResult.getIdType().equals("record")) {
            //传输的id 为检查记录表中的主键，需要用检查记录表主键去查询肠镜结果表的id
            HospitalColonoscopyRecord record = personDao.findRecordByRecordId(id);
            id = record.getNotification_id();
        }
        ColonoscopyNotificationVo vo = screeningNotificationDao.get(id);
        return vo;
    }

    @Override
    public ReservationVo getReservation(int id) {
        ReserveAllocation reserveAllocation = reserveAllocationDao.getReserveAllocation(id);
        ReservationVo vo = null;
        if (reserveAllocation != null) {
            int count = reserveDetailDao.getReservedCount(id);
            vo = new ReservationVo();
            vo.setDeptName(reserveAllocation.getDeptName());
            vo.setExaminationName("结肠镜检查");
            vo.setId(id);
            vo.setName(reserveAllocation.getExaminationPlace());
            vo.setPeriod(reserveAllocation.getReservationDate() + " " + reserveAllocation.getStartTime() + "-" + reserveAllocation.getEndTime());
            vo.setReserveable(reserveAllocation.getAmount() - count);
        } else {
            throw new ItSysException(GlobalErrorCode.ERR_GET_ALLOCATION_ERROR, GlobalErrorCode.ERR_GET_ALLOCATION_MSG);
        }
        return vo;
    }

    /**
     * 批量或单个修改用户的检查状态
     * maxiang
     */
    @Override
    @Transactional
    public void updateExaminationStatus(String body, String loginName) {
        ColonoscopyExaminationVo colonoscopyExaminationVo = JSONUtils.toBean(body, ColonoscopyExaminationVo.class);
        //获取登录人信息
        DoctorInfo doctorInfo = this.getDoctorInfo(loginName);
        if (colonoscopyExaminationVo.getHospitalColonoscopyRecords() == null || colonoscopyExaminationVo.getHospitalColonoscopyRecords().size() == 0) {
            log.info("updateExaminationStatus 参数异常 getHospitalColonoscopyRecords集合为空");
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }
        //if(colonoscopyExaminationVo.getExaminationStatus()!= Constans.EXAMINATION_STATUS1&&colonoscopyExaminationVo.getExaminationStatus()!=Constans.EXAMINATION_STATUS2){
/*		if(colonoscopyExaminationVo.getExaminationStatus()!= Constans.EXAMINATION_STATUS1){
			log.info("updateExaminationStatus 参数异常 getExaminationStatus值范围不对");
			throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE,GlobalErrorCode.PARAMETER_ERR_MSG);
		}*/
        colonoscopyExaminationVo.setExaminationOperator(doctorInfo.getId());
        //触发不同待办
        StringBuffer ids = new StringBuffer();
        for (HospitalColonoscopyRecord record : colonoscopyExaminationVo.getHospitalColonoscopyRecords()) {
            HospitalTodoEvent hospitalTodoEvent = new HospitalTodoEvent();
            if (colonoscopyExaminationVo.getExaminationStatus() == Constans.EXAMINATION_STATUS1) {

                //未检查
                List<ReserveAllocation> reserveAllocations = colonoscopyDao.queryAllocationByrecordId(record.getId());
                //判断时间是否超时
                if (reserveAllocations != null && reserveAllocations.size() > 0 && reserveAllocations.get(0) != null) {
                    String atime = reserveAllocations.get(0).getAtime();
                    if (!StringUtil.isEmpty(atime)) {
                        Date date = DateUtil.formatDateStr(atime, "yyyy-MM-dd");
                        boolean b = DateUtil.compareDate(date, new Date());
                        if (!b) {
                            log.info("updateExaminationStatus 获取放号时间错误 getHospitalColonoscopyRecords集合为空");
                            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_TIME);
                        }
                    }
                }


                // 触发 添加（未完成结肠镜检查）待办
                hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE6);
                HospitalReview review = personDao.findInfoBySid(record.getSid());
                hospitalTodoEvent.setCommunityDeptId(review.getCommunityDeptId());
                hospitalTodoEvent.setAreaDeptId(doctorInfo.getAreaDeptId());
                hospitalTodoEvent.setSid(record.getSid());
                hospitalTodoEvent.setDataId(record.getId());
                hospitalTodoEvent.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
                ids.append("'" + record.getId() + "',");
                colonoscopyExaminationVo.setRecordIds(ids.substring(0, ids.length() - 1));
                personDao.addTodoEvent(hospitalTodoEvent);
                // 触发 修改（待录入肠镜结果) 1个待办的完成状态
                eventDao.updateStatus(record.getSid(), record.getId(), Constans.PERSON_TODO_EVENT_TYPE8, EVENT_STATUS_DONE);
                //eventDao.updateStatus(record.getSid(),record.getId(),Constans.PERSON_TODO_EVENT_TYPE9,EVENT_STATUS_DONE);
                //eventDao.updateStatus(record.getSid(),record.getId(),Constans.PERSON_TODO_EVENT_TYPE10,EVENT_STATUS_DONE);
            } else {
                ids.append("'" + record.getId() + "',");
                colonoscopyExaminationVo.setRecordIds(ids.substring(0, ids.length() - 1));
            }
            //取消 待录入肠镜的操作 modify by maxiang 2018-04-30
//			else if(colonoscopyExaminationVo.getExaminationStatus()== Constans.EXAMINATION_STATUS2){
//				hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE8);
//			}
        }
        //修改用户检查记录的状态
        colonoscopyDao.updateExaminationStatus(colonoscopyExaminationVo);
    }


    /**
     * 添加肠镜检查记录
     * maxiang
     *
     * @param vo
     * @param loginName
     */
    @Transactional
    @Override
    public void addColonoscopyRecord(ColonoscopyVo vo, String loginName) {
        log.info("@addColonoscopyRecord start.");

        //获取当前用户信息
        DoctorInfo doctorInfo = getDoctorInfo(loginName);
        //查询资格审核表数据
        HospitalReview review = personDao.getBySid(vo.getSid());
        if (review == null || review.getSid() == null) {//受试者不存在
            throw new ItSysException(GlobalErrorCode.ERR_PERSON_NULL_CODE, GlobalErrorCode.ERR_PERSON_NULL_MSG);
        }
        if (Constans.PERSON_OVERALL_STATUS2.equals(review.getOverallStatusCy())) {
            log.info("addColonoscopyRecord 参数异常 该受试者已经退出研究");
            throw new ItSysException(GlobalErrorCode.PERSON_OVERALL_STATUS2_ERROR_CODE, GlobalErrorCode.PERSON_OVERALL_STATUS2_ERROR_MSG);
        }
        //新增一条肠镜检查记录
        HospitalColonoscopyRecord result = new HospitalColonoscopyRecord();
        result.setSid(vo.getSid());
        result.setStage(Constans.PERSON_COLONOSCOPY_STATUS1);
        result.setCommunity_dept_id(review.getCommunityDeptId());
        result.setArea_dept_id(review.getAreaDeptId());
        result.setSource_type(Constans.SOURCE_TYPE2);//表示新增
        //新增过来的数据就是已检查状态
        result.setExamination_status(Constans.EXAMINATION_STATUS2);
        result.setExamination_date(new Date());
        result.setExamination_check_date(new Date());
        result.setExamination_operator(doctorInfo.getId());
        //肠镜 变成未录入状态
        result.setResult_status(Constans.RESULT_STATUS1);
        result.setOperationSource(Constans.ADD_NEW_HOSPITAL_COLONOSCOPY_RECORD);
//		result.setPathology_status(Constans.PATHOLOGY_STATUS1);
//		result.setNotification_entry_status(Constans.NOTIFICATION_ENTRY_STATUS1);


        int dataId = personDao.addColonoscopyRecord(result);
        //暂无待办信息
        log.info("@addColonoscopyRecord end.");
    }

    /**
     * 国家   肠镜管理
     *
     * @param queryCondition
     * @param loginName
     * @return
     */
    @Override
    public ListPageUtil queryForNationList(ColonoscopyVo queryCondition, String loginName) {
        // TODO Auto-generated method stub
        //获取当前用户信息
        DoctorInfo doctorInfo = getDoctorInfo(loginName);
        log.info("@queryForNationList start.");
        int nationId = doctorInfo.getNationDeptId();
        ListPageUtil list = colonoscopyDao.queryForNationList(queryCondition, nationId, doctorInfo.getHospitalType(), true);
        log.info("@queryForNationList end.");
        return list;
    }

    @Override
    public List<ColonoscopyVo> queryForAreaExcel(ColonoscopyVo queryCondition, String loginName) {
        // TODO Auto-generated method stub
        // 查询登录用户信息
        DoctorInfo doctorInfo = getDoctorInfo(loginName);
        List<ColonoscopyVo> list = colonoscopyDao.queryForAreaExcel(queryCondition, queryCondition.getCommunityDeptId(), doctorInfo.getAreaDeptId(), doctorInfo.getHospitalType(), true);
        return list;
    }

    /**
     * 根据结果id查询检查信息
     *
     * @param id
     * @return
     */
    @Override
    public HospitalColonoscopyRecord findRecordByResultId(Integer id) {
        return personDao.findRecordByResultId(id);
    }

    /**
     * 根据病理id查询检查信息
     *
     * @param id
     * @return
     */
    @Override
    public HospitalColonoscopyRecord findRecordByPathologyId(Integer id) {
        return personDao.findRecordByPathologyId(id);
    }

    /**
     * 根据告知书id查询检查信息
     *
     * @param id
     * @return
     */
    @Override
    public HospitalColonoscopyRecord findRecordByNotificationId(Integer id) {
        return personDao.findRecordByNotificationId(id);
    }

    /**
     * 根据受试者创建者获取创建者信息
     *
     * @param sid
     * @return
     */
    @Override
    public List<ItsysUserDto> getCreateUser(String sid) {
        return userDao.getCreateUser(sid);
    }

    @Override
    public HospitalColonoscopyRecord queryByID(int communityDeptId) {
        return personDao.findRecordById(communityDeptId);
    }

    @Override
    public List<ItsysUserDto> querylowerLoginNamesByloginName(String loginName) {
        return userDao.querylowerLoginNamesByloginName(loginName);
    }

    @Override
    public List<ItsysUserDto> queryMyLoginNamesByloginName(String loginName) {
        return userDao.queryMyLoginNamesByloginName(loginName);
    }

    public static void main(String[] args) {
        String date = "2018-11-23 18:00:00";
        Date date1 = DateUtil.formatDateStr(date, "yyyy-MM-dd HH:mm:ss");
        boolean b = DateUtil.compareDate(date1, new Date());
        System.out.println(b);
    }

    public static void compareDate(Date date1, Date date2) {
        if (date1.before(date2)) {
            System.out.println(DateUtil.formatDate(date1, "yyyy-MM-dd HH:mm:ss") + "在" + DateUtil.formatDate(date2, "yyyy-MM-dd HH:mm:ss") + "前面");
        } else if (date1.after(date2)) {
            System.out.println(DateUtil.formatDate(date1, "yyyy-MM-dd HH:mm:ss") + "在" + DateUtil.formatDate(date2, "yyyy-MM-dd HH:mm:ss") + "后面");

        } else {
            System.out.println("是同一天的同一时间");
        }
    }


}
