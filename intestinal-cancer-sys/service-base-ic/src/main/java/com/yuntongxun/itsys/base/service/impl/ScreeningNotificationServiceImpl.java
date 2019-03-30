package com.yuntongxun.itsys.base.service.impl;

import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.dao.*;
import com.yuntongxun.itsys.base.po.HospitalColonoscopyRecord;
import com.yuntongxun.itsys.base.po.HospitalReview;
import com.yuntongxun.itsys.base.po.HospitalTodoEvent;
import com.yuntongxun.itsys.base.po.ScreeningNotification;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
import com.yuntongxun.itsys.base.service.ColonoscopyResultService;
import com.yuntongxun.itsys.base.service.ColonoscopyService;
import com.yuntongxun.itsys.base.service.ScreeningNotificationService;
import com.yuntongxun.itsys.base.vo.ColonoscopyEntryVo;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 肠镜筛查告知书 serviceimpl
 * maxiang
 */
@Service
public class ScreeningNotificationServiceImpl implements ScreeningNotificationService {

    final Logger log = LogManager.getLogger(ScreeningNotificationServiceImpl.class);


    @Autowired
    ScreeningNotificationDao screeningNotificationDao;

    @Autowired
    private HtEventDao htEventDao;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private ColonoscopyDao colonoscopyDao;

    @Autowired
    private ColonoscopyService colonoscopyService;

    @Autowired
    private HospitalReferenceRecordDao hospitalReferenceRecordDao;

    @Autowired
    private ColonoscopyResultService colonoscopyResultService;

    /**
     * 添加肠镜筛查报告通知
     * @param body
     * @param loginName
     */
    @Override
    @Transactional
    public void addScreeningNotificationt(String body,String loginName) {
        ScreeningNotification screeningNotification = JSONUtils.jsonToBeanDateSerializer(body, ScreeningNotification.class,"yyyy-MM-dd");
//        String key = RedisConstant.HOSPITAL_KEY_INFO+loginName;
//        String value = redisManager.get(key);
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);

        if(StringUtil.isBlank(screeningNotification.getSid())){
            log.info("addScreeningNotificationt 参数异常 缺少sid");
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE,GlobalErrorCode.PARAMETER_ERR_MSG);
        }
        if(screeningNotification.getColonoscopyRecordId()<0){
            log.info("addScreeningNotificationt 参数异常 缺少检查记录colonoscopyRecordId");
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE,GlobalErrorCode.PARAMETER_ERR_MSG);
        }
        //获取登录人信息
        screeningNotification.setAreaDeptId(doctorInfo.getAreaDeptId());
        //获取受试者年度阶段
        HospitalReview hospitalReview = personDao.getBySid(screeningNotification.getSid());
        screeningNotification.setStage(hospitalReview.getStageCy());
        screeningNotification.setCommunityDeptId(hospitalReview.getCommunityDeptId());
        /**
         * 1.添加告知书 hospital_screening_notification
         * 2.修改表hospital_colonoscopy_record（
         * notification_id int 结肠镜告知书id ；
         * notification_entry_status int 结肠镜告知书录入状态，1：未录入，2：已录入 ；
         * notification_entry_date datetime 告知书录入时间 ；
         * notification_entry_operator int 告知书录入医生
         * notification_issue_status int 结肠镜告知书发放状态，1：未发放，2：已发放
         ）
         * 3.修改待办表变成已完成状态 hospital_todo_event
         * 4.添加下一个待办 hospital_todo_event
         */
        //添加操作
        Integer id = screeningNotificationDao.addScreeningNotification(screeningNotification);
        //修改表hospital_colonoscopy_record
        HospitalColonoscopyRecord record = personDao.findRecordByRecordId(screeningNotification.getColonoscopyRecordId());
        ColonoscopyEntryVo colonoscopyEntryVo = new ColonoscopyEntryVo();
        colonoscopyEntryVo.setNotificationEntryDate(screeningNotification.getScDate());
        colonoscopyEntryVo.setNotificationId(id);
        colonoscopyEntryVo.setNotificationEntryOperator(doctorInfo.getId());
        colonoscopyEntryVo.setColonoscopyRecordId(screeningNotification.getColonoscopyRecordId());
        colonoscopyEntryVo.setNotificationEntryStatus(Constans.NOTIFICATION_ENTRY_STATUS2);

        Integer issueStatus = record.getNotification_issue_status();

        if(issueStatus == null) {
            colonoscopyEntryVo.setNotificationIssueStatus(Constans.NOTIFICATION_ISSUE_STATUS1);
        }else {
            colonoscopyEntryVo.setNotificationIssueStatus(record.getNotification_issue_status());
        }
        colonoscopyDao.updateNotificationEntryStatus(colonoscopyEntryVo);
        //修改表hospital_colonoscopy_record

        //肠镜检查记录来源如果是预约 则要修改待办状态
        if(record!=null&&record.getSource_type()==Constans.SOURCE_TYPE1) {
            htEventDao.updateStatus(screeningNotification.getSid(), screeningNotification.getColonoscopyRecordId(), Constans.PERSON_TODO_EVENT_TYPE10, Constans.PERSON_TODO_EVENT_STATUS2);
        }
        if(issueStatus == null) {
            //添加 7：未发放筛查结果告知书
            HospitalTodoEvent hospitalTodoEvent = new HospitalTodoEvent();
            hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE7);
            hospitalTodoEvent.setCommunityDeptId(hospitalReview.getCommunityDeptId());
            hospitalTodoEvent.setAreaDeptId(doctorInfo.getAreaDeptId());
            hospitalTodoEvent.setSid(screeningNotification.getSid());
            hospitalTodoEvent.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
            hospitalTodoEvent.setDataId(screeningNotification.getColonoscopyRecordId());
            personDao.addTodoEvent(hospitalTodoEvent);
        }
        //添加 7：未发放筛查结果告知书
    }

    @Override
    public List<ScreeningNotification> queryById(Integer id) {
        List<ScreeningNotification> list = screeningNotificationDao.queryById(id);
        if (list == null||list.size()==0) {
            log.info("queryById 根据id查询结果集为空");
            throw new ItSysException(GlobalErrorCode.OBJECT_NOT_EXISTS+"", GlobalErrorCode.OBJECT_NOT_EXISTS_MSG);
        }
        return list;
    }

    @Override
    @Transactional
    public void updateScreeningNotificationt(ScreeningNotification screeningNotification, HospitalReferenceRecordDto hospitalReferenceRecordDto) {
        //根据id修改dna表
        try{
            HospitalReview hospitalReview = personDao.getBySid(screeningNotification.getSid());
            screeningNotification.setStage(hospitalReview.getStageCy());
            screeningNotification.setApplyStatus(Constans.APPLY_EDIT_STATUS1);
            screeningNotification.setApprovalStatus(null);
            screeningNotification.setEditStatus(Constans.EDIT_STATUS1);
            screeningNotificationDao.updateScreeningNotificationt(screeningNotification);
            hospitalReferenceRecordDao.updateForEdir(hospitalReferenceRecordDto);
        }catch (Exception e){
            System.out.println(e.toString());
            throw new ItSysException(
                    GlobalErrorCode.HOSPITAL_MESSAGE_CENTER_UPDATE_ERROR_CODE, GlobalErrorCode.HOSPITAL_MESSAGE_CENTER_UPDATE_ERROR_MSG);
        }
    }

    /**
     * 修改筛查告知书
     * @param
     * @param
     */
    @Override
    @Transactional
    public void updateScreeningNotificationt(ScreeningNotification newResult,String loginName,HospitalReferenceRecordDto hospitalReferenceRecordDto) {

        //准备数据 begin
        //查询肠镜检查信息
        HospitalColonoscopyRecord colonoscopyRecord = personDao.findRecordByRecordId(newResult.getColonoscopyRecordId());
        Integer issueStatus = colonoscopyRecord.getNotification_issue_status();
        if (newResult.getColonoscopyRecordId()==null||colonoscopyRecord==null) {
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        //获取受试者的基本信息
        HospitalReview hospitalReview = personDao.getBySid(colonoscopyRecord.getSid());

        if (hospitalReview == null) {
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }

        if(Constans.PERSON_OVERALL_STATUS2.equals(hospitalReview.getOverallStatusCy())){
            throw new ItSysException(GlobalErrorCode.PERSON_OVERALL_STATUS2_ERROR_CODE, GlobalErrorCode.PERSON_OVERALL_STATUS2_ERROR_MSG);
        }

        //准备数据 end

        //记录操作记录begin
        try {
            hospitalReferenceRecordDao.updateForEdir(hospitalReferenceRecordDto);
        }catch (Exception e){
            e.printStackTrace();
            log.info("updateScreeningNotificationt---------记录操作日志error"+JSONUtils.toJson(hospitalReferenceRecordDto));
        }
        //记录操作记录end

        //修改告知书 begin
        newResult.setStage(hospitalReview.getStageCy());
        newResult.setApplyStatus(Constans.APPLY_EDIT_STATUS1);
        newResult.setApprovalStatus(null);
        newResult.setEditStatus(Constans.EDIT_STATUS1);
        screeningNotificationDao.updateScreeningNotificationt(newResult);
        //修改告知书 end

        //是否要重新发放告知书 begin  reissueNotification 1是 2否
        log.info("--------updateScreeningNotificationt 是否要重新发放告知书:-------- "+newResult.getReissueNotification());

//        if (newResult.getReissueNotification()==null && colonoscopyRecord.getNotification_entry_status() ==2){
//            log.info("--------已发放过告知书，但是没有传值是否要重新发放告知书--------");
//            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
//        }

        if(newResult.getReissueNotification()!=null && newResult.getReissueNotification()==1){
            //重新发送告知书
            colonoscopyRecord = fZnatificationIssueInfoNUll(colonoscopyRecord,doctorInfo);
            colonoscopyRecord.setNotification_issue_status(1);
            //删除发放告知书待办
            htEventDao.delNotificationFFTodoEvent(colonoscopyRecord.getSid(), colonoscopyRecord.getId());
            //生成待办发放告知书待办
            HospitalTodoEvent hospitalTodoEvent = new HospitalTodoEvent();
            hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE7);
            hospitalTodoEvent.setCommunityDeptId(hospitalReview.getCommunityDeptId());
            hospitalTodoEvent.setAreaDeptId(doctorInfo.getAreaDeptId());
            hospitalTodoEvent.setSid(colonoscopyRecord.getSid());
            hospitalTodoEvent.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
            hospitalTodoEvent.setDataId(colonoscopyRecord.getId());
            personDao.addTodoEvent(hospitalTodoEvent);

            //发送消息给社区创建者和地区所有管理员
            colonoscopyResultService.sendMsgForDelNotification(loginName,colonoscopyRecord,"肠镜告知书");
        }
        //是否要重新发放告知书 end

        //更新检查记录表 begin
        colonoscopyRecord.setNotification_entry_date(new Date());
        colonoscopyRecord.setNotification_entry_operator(doctorInfo.getId());
        colonoscopyDao.updateColonoscopyRecordForCheckStatus(colonoscopyRecord);
        //更新检查记录表 end
    }

    /**
     * 赋值 肠镜检查---报告发放状态信息为null
     * @return
     */
    public HospitalColonoscopyRecord fZnatificationIssueInfoNUll(HospitalColonoscopyRecord colonoscopyRecord,DoctorInfo doctorInfo){
        colonoscopyRecord.setNotification_issue_status(null);
        colonoscopyRecord.setNotification_issue_date(null);
        colonoscopyRecord.setNotification_issue_operator(null);
        colonoscopyRecord.setNotification_issue_worker_code(null);
        colonoscopyRecord.setNotification_issue_mode(null);
        colonoscopyRecord.setNotification_issue_note(null);
        return  colonoscopyRecord;
    }

}
