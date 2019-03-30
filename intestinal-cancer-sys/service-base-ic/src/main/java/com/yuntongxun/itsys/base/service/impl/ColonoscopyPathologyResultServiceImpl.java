package com.yuntongxun.itsys.base.service.impl;

import com.yuntongxun.itsys.base.cache.RedisManager;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.constans.RedisConstant;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.*;
import com.yuntongxun.itsys.base.dao.*;
import com.yuntongxun.itsys.base.po.*;
import com.yuntongxun.itsys.base.po.dto.ItsysUserDto;
import com.yuntongxun.itsys.base.po.dto.cancervo.HospitalCancerRecordVo;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
import com.yuntongxun.itsys.base.po.dto.message.HospitalMessageCenterDto;
import com.yuntongxun.itsys.base.service.*;
import com.yuntongxun.itsys.base.vo.*;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 肠镜病理记录表
 * maxiang
 */
@Service
public class ColonoscopyPathologyResultServiceImpl implements ColonoscopyPathologyResultService {

    final Logger log = LogManager.getLogger(ColonoscopyPathologyResultServiceImpl.class);

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private UserService userService;

    @Autowired
    private ColonoscopyPathologyResultDao colonoscopyPathologyResultDao;

    @Autowired
    private ColonoscopyPathologyDiagnosisRecordDao colonoscopyPathologyDiagnosisRecordDao;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private HtEventDao htEventDao;

    @Autowired
    private ColonoscopyDao colonoscopyDao;

    @Autowired
    private ColonoscopyService colonoscopyService;

    @Autowired
    private HospitalMessageCenterService hospitalMessageCenterService;

    @Autowired
    private HospitalReferenceRecordDao hospitalReferenceRecordDao;

    @Autowired
    private HospitalCancerDao cancerDao;

    @Autowired
    private ScreeningNotificationDao screeningNotificationDao;

    @Autowired
    private ColonoscopyResultService colonoscopyResultService;

    /**
     * 添加肠镜病理记录表
     * @param loginName
     * @param body
     */
    @Override
    @Transactional
    public void addColonoscopyPathologyResult(String body,String loginName) {
        ColonoscopyPathologyResult colonoscopyPathologyResult = JSONUtils.jsonToBeanDateSerializer(body,  ColonoscopyPathologyResult.class,"yyyy-MM-dd");
        //获取登录人信息
        String key = RedisConstant.HOSPITAL_KEY_INFO+loginName;
        String value = redisManager.get(key);
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);

        if(StringUtil.isBlank(colonoscopyPathologyResult.getSid())){
            log.info("addOrUpdateAbnormalEvent 参数异常 缺少sid");
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE,GlobalErrorCode.PARAMETER_ERR_MSG);
        }
        if(colonoscopyPathologyResult.getColonoscopyRecordId()==null){
            log.info("addColonoscopyResult 参数异常 缺少检查记录ID  getColonoscopyRecordId为空");
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE,GlobalErrorCode.PARAMETER_ERR_MSG);
        }else{

            //根据检查记录id获取肠镜结果id
            colonoscopyPathologyResult.setColonoscopyResultId(personDao.findRecordByRecordId(colonoscopyPathologyResult.getColonoscopyRecordId()).getResult_id());
            colonoscopyPathologyResult.setEditoperationSource(personDao.findRecordByRecordId(colonoscopyPathologyResult.getColonoscopyRecordId()).getEditoperationSource());
            colonoscopyPathologyResult.setOperationSourceId(personDao.findRecordByRecordId(colonoscopyPathologyResult.getColonoscopyRecordId()).getResult_id());
        }

        //获取登录人信息
        colonoscopyPathologyResult.setAreaDeptId(doctorInfo.getAreaDeptId());
        //获取受试者年度阶段
        HospitalReview hospitalReview = personDao.getBySid(colonoscopyPathologyResult.getSid());
        //获取检查信息
        HospitalColonoscopyRecord record = personDao.findRecordByRecordId(colonoscopyPathologyResult.getColonoscopyRecordId());
        colonoscopyPathologyResult.setStage(record.getStage());
        colonoscopyPathologyResult.setCommunityDeptId(hospitalReview.getCommunityDeptId());
        /**
         * 1.添加肠镜病理记录表hospital_colonoscopy_pathology_result
         * 2.添加结肠镜病理诊断记录表 hospital_colonoscopy_pathology_diagnosis_record
         * 3.修改表hospital_colonoscopy_record（pathology_id int 结肠镜病理结果id  pathology_status int 结肠镜病理录入状态，1：未录入，2：已录入   pathology_date datetime 病理录入时间  pathology_operator int 病理录入医生  ）
         * 4.修改待办表变成已完成状态 hospital_todo_event
         * 5.如果患结直肠癌 则要触发终点事件  修改受试者表
         * 6.触发待录入筛查结果告知书待办 hospital_todo_event
        */
       //添加肠镜病理记录表
        Integer id = colonoscopyPathologyResultDao.addColonoscopyPathologyResult(colonoscopyPathologyResult);
        //添加结肠镜病理诊断记录表
        List<ColonoscopyPathologyDiagnosisRecord> diagnosisRecordList = colonoscopyPathologyResult.getColonoscopyPathologyDiagnosisRecordList();
        if(diagnosisRecordList!=null&&diagnosisRecordList.size()>0){
           int count=0;
            for(ColonoscopyPathologyDiagnosisRecord diagnosisRecord:diagnosisRecordList){
                diagnosisRecord.setPathologyResultId(id);
                diagnosisRecord.setIndex(count++);
                colonoscopyPathologyDiagnosisRecordDao.addColonoscopyPathologyDiagnosisRecord(diagnosisRecord);
            }
        }
        //添加结肠镜病理诊断记录表
        //修改表hospital_colonoscopy_record

        ColonoscopyPathologyStatusVo vo = new ColonoscopyPathologyStatusVo();
        vo.setPathologyId(id);
        vo.setColonoscopyRecordId(colonoscopyPathologyResult.getColonoscopyRecordId());
        if (record.getNotification_id()==null) {
            vo.setNotificationEntryStatus(Constans.NOTIFICATION_ENTRY_STATUS1);
        }else{
            vo.setNotificationEntryStatus(record.getNotification_entry_status());
        }
        vo.setPathologyDate(colonoscopyPathologyResult.getSurveyDate());
        vo.setPathologyOperator(doctorInfo.getId());
        vo.setPathologyStatus(Constans.PATHOLOGY_STATUS2);
        colonoscopyDao.updateNotificationPathologyStatus(vo);
        //修改表hospital_colonoscopy_record

        //肠镜检查记录来源如果是预约 则要修改待办状态
        if(record!=null&&record.getSource_type()==Constans.SOURCE_TYPE1) {
            htEventDao.updateStatus(colonoscopyPathologyResult.getSid(), colonoscopyPathologyResult.getColonoscopyRecordId(), Constans.PERSON_TODO_EVENT_TYPE9, Constans.PERSON_TODO_EVENT_STATUS2);//待办完成
            //触发下一步待办
            if (record.getNotification_id() == null){
                HospitalTodoEvent hospitalTodoEvent = new HospitalTodoEvent();
                hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE10);
                hospitalTodoEvent.setCommunityDeptId(hospitalReview.getCommunityDeptId());
                hospitalTodoEvent.setAreaDeptId(doctorInfo.getAreaDeptId());
                hospitalTodoEvent.setSid(colonoscopyPathologyResult.getSid());
                hospitalTodoEvent.setDataId(colonoscopyPathologyResult.getColonoscopyRecordId());
                hospitalTodoEvent.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
                hospitalTodoEvent.setOperationSourceId(id);
                hospitalTodoEvent.setOperationSource(colonoscopyPathologyResult.getEditoperationSource());
                personDao.addTodoEvent(hospitalTodoEvent);
            }
        }

        if(colonoscopyPathologyResult.getItem3()==1){
            //患癌症
            String stageCy = "overall_status_t" + (hospitalReview.getStageCy() - 1);
            personDao.updateOverallStatus(Constans.PERSON_OVERALL_STATUS3, colonoscopyPathologyResult.getSid(), stageCy);//更新用户总体状态


            //zongtong   2018-09-06
            //	新增癌症检查结果
            int i;
            try{
                HospitalCancerRecordVo hospitalCancerRecordVo=new HospitalCancerRecordVo();
                hospitalCancerRecordVo.setSid(hospitalReview.getSid());
                hospitalCancerRecordVo.setAreaDeptId(hospitalReview.getAreaDeptId());
                hospitalCancerRecordVo.setCommunityDeptId(hospitalReview.getCommunityDeptId());
                hospitalCancerRecordVo.setStage(hospitalReview.getStageCy());
                hospitalCancerRecordVo.setPathologyId(id);
                hospitalCancerRecordVo.setColonoscopyRecordId(colonoscopyPathologyResult.getColonoscopyRecordId());
                hospitalCancerRecordVo.setCancerReportStatus(Constans.cancerReportStatus1);
                hospitalCancerRecordVo.setCancerDiagnoseStatus(Constans.CANCER_DIAGNOSE_STATUS1);
                hospitalCancerRecordVo.setColorectalCancerDiagnoseInformationStatus(Constans.COLORECTAL_CANCER_DIAGNOSE_INFORMATION_STATUS1);
                hospitalCancerRecordVo.setColorectalCancerTreatmentInformatioStatus(Constans.COLORECTAL_CANCER_TREATMENT_INFORMATIO_STATUS1);
                hospitalCancerRecordVo.setEndEventType(Constans.END_EVENT_TYPE1);
                hospitalCancerRecordVo.setValidData(Constans.VALID_DATA1);
                hospitalCancerRecordVo.setDeptCode(hospitalReview.getSiteId());
                hospitalCancerRecordVo.setCreateUser(doctorInfo.getId());
                i=cancerDao.save(hospitalCancerRecordVo);
            }catch (Exception e){
                throw new ItSysException(GlobalErrorCode.HOSPITAL_CANCER_RECORD_SAVE_CODE,GlobalErrorCode.HOSPITAL_CANCER_RECORD_SAVE_CODE_MSG);

            }

            //	触发4个患癌事件
            HospitalTodoEvent hospitalTodoEvent = new HospitalTodoEvent();
            hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE20);
            hospitalTodoEvent.setCommunityDeptId(hospitalReview.getCommunityDeptId());
            hospitalTodoEvent.setAreaDeptId(doctorInfo.getAreaDeptId());
            hospitalTodoEvent.setSid(colonoscopyPathologyResult.getSid());
            hospitalTodoEvent.setDataId(i);
            hospitalTodoEvent.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
            hospitalTodoEvent.setOperationSourceId(id);
            hospitalTodoEvent.setOperationSource(colonoscopyPathologyResult.getEditoperationSource());
            //表C1：癌症报告表
            int c1=personDao.addTodoEvent(hospitalTodoEvent);
            if(c1<1){
                throw new ItSysException(GlobalErrorCode.HOSPITAL_CANCER_RECORD_SAVE_EVENT_CODE,GlobalErrorCode.HOSPITAL_CANCER_RECORD_SAVE_CODE_EVENT_MSG);
            }
            //表C2：癌症诊断表
            hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE21);
            int c2=personDao.addTodoEvent(hospitalTodoEvent);
            if(c2<1){
                throw new ItSysException(GlobalErrorCode.HOSPITAL_CANCER_RECORD_SAVE_EVENT_CODE,GlobalErrorCode.HOSPITAL_CANCER_RECORD_SAVE_CODE_EVENT_MSG);
            }
            //表C3-结直肠癌诊断信息摘录表
            hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE22);
            int c3=personDao.addTodoEvent(hospitalTodoEvent);
            if(c3<1){
                throw new ItSysException(GlobalErrorCode.HOSPITAL_CANCER_RECORD_SAVE_EVENT_CODE,GlobalErrorCode.HOSPITAL_CANCER_RECORD_SAVE_CODE_EVENT_MSG);
            }
            //表C4：结直肠癌治疗信息摘录表
            hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE23);
            int c4=personDao.addTodoEvent(hospitalTodoEvent);
            if(c4<1){
                throw new ItSysException(GlobalErrorCode.HOSPITAL_CANCER_RECORD_SAVE_EVENT_CODE,GlobalErrorCode.HOSPITAL_CANCER_RECORD_SAVE_CODE_EVENT_MSG);
            }
            //添加消息中心
            List<ItsysUserDto> itsysUserDtos = colonoscopyService.queryloginNamesByloginName(loginName);
            List<ItsysUserDto> itsysUserDtosList = colonoscopyService.queryAllloginNamesByloginName(hospitalReview.getCommunityDeptId().toString());
            if(itsysUserDtosList.size()!=0){
                itsysUserDtos.addAll(itsysUserDtosList);
            }
            String meaasge_typpe=Constans.meaasge_typpe2;
            //获取退出原因
            String text="";
            String sid=colonoscopyPathologyResult.getSid();
            String meaasge_text_typpe=Constans.meaasge_text_typpe3;
            String courierNumber="";
            String remark="";
            Integer data_id=colonoscopyPathologyResult.getColonoscopyRecordId();
            //添加消息中心
            List<HospitalMessageCenterDto> hospitalMessageCenterDtoList = new ArrayList<>();
            for (ItsysUserDto itsysUserDto : itsysUserDtos) {
                HospitalMessageCenterDto hospitalMessageCenterDto = new HospitalMessageCenterDto();
                hospitalMessageCenterDto.setSendUser(loginName);
                String accpetName = itsysUserDto.getLoginname();
                hospitalMessageCenterDto.setAcceptUser(accpetName);
                hospitalMessageCenterDto.setMessageType(Constans.meaasge_typpe2);
                hospitalMessageCenterDto.setData_id(data_id);
                hospitalMessageCenterDto.setForm_type(Constans.HOSPITAL_COLONOSCOPY_RECORD);
                hospitalMessageCenterDto.setSid(colonoscopyPathologyResult.getSid());
                /**
                 * 1  异常    违反方案
                 *           sid
                 *           已经退出研究
                 *           sid、text
                 *           诊断为结直肠癌
                 *           sid
                 *申请|发放编辑
                 *          快递模块
                 *          sendUser、acceptUser、courierNumber
                 *          管理模块
                 *          sendUser、acceptUser、text（模块+sid）
                 *通知发放
                 *    public static String getMessage(String sendUser, String acceptUser, String meaasgeType, String text, String sid, String meaasgeTextType, String courierNumber) {
                 *
                 *
                 *
                 *
                 */
                String message = SendMessageCenter.getMessage(loginName, accpetName, meaasge_typpe, text, sid,meaasge_text_typpe,courierNumber,remark);
                hospitalMessageCenterDto.setMessageText(message);
                hospitalMessageCenterDto.setId(UUID.randomUUID().toString().replace("-", ""));
                hospitalMessageCenterDtoList.add(hospitalMessageCenterDto);
            }
            hospitalMessageCenterService.save(hospitalMessageCenterDtoList, null,Constans.APPLY_EDIT_STATUS2,Constans.EDIT_STATUS1,Constans.APPROVAL_STATUS1,"","",true,null);
        }
    }



    @Override
    public String queryColonoscopyPathologyResult(HospitalColonoscopyResult hospitalColonoscopyResult) {
        //校验参数是否存在
        if (null == hospitalColonoscopyResult.getId() || StringUtils.isEmpty(hospitalColonoscopyResult.getSid())) {
            log.info("queryColonoscopyPathologyResult 输入参数id或者sid不存在/错误");
            return JSONUtils.toJson(new Response(GlobalErrorCode.REQUIRED_PARAMETER_ERROR, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
        if(hospitalColonoscopyResult.getIdType()!=null&&hospitalColonoscopyResult.getIdType().equals("record")){
            //传输的id 为检查记录表中的主键，需要用检查记录表主键去查询肠镜结果表的id
            HospitalColonoscopyRecord record = personDao.findRecordByRecordId(hospitalColonoscopyResult.getId());
            if(record!=null&&record.getPathology_id()!=null){
                hospitalColonoscopyResult.setId(record.getPathology_id());
            }else{
                log.info("queryColonoscopyResultToStringById 根据recordID查询肠镜结果id为空");
                return JSONUtils.toJson(new Response(GlobalErrorCode.REQUIRED_PARAMETER_ERROR, GlobalErrorCode.PARAMETER_ERR_MSG));
            }
        }

        ColonoscopyPathologyResult hospitalColonoscopyResultData;
        try {
            //获取结果集
            hospitalColonoscopyResultData = colonoscopyPathologyResultDao.queryColonoscopyPathologyResultById(hospitalColonoscopyResult.getId(), hospitalColonoscopyResult.getSid());
            //判断结果集是否存在
            if (hospitalColonoscopyResultData == null) {
                log.info("queryColonoscopyPathologyResult 根据id查询结果集为空");
                return JSONUtils.toJson(new Response(GlobalErrorCode.OBJECT_NOT_EXISTS, GlobalErrorCode.OBJECT_NOT_EXISTS_MSG));
            }
        } catch (Exception e) {
            log.info("queryColonoscopyPathologyResultById is error"+e.toString());
            return JSONUtils.toJson(new Response(GlobalErrorCode.UNKNOWN_ERROR, GlobalErrorCode.UNKNOWN_ERROR_MSG));
        }
        //根据结肠镜病理结果ID查询集合
        List<ColonoscopyPathologyDiagnosisRecord> colonoscopyPathologyDiagnosisRecords = colonoscopyPathologyDiagnosisRecordDao.queryColonoscopyPathologyDiagnosisRecordsByPathologyResultId(hospitalColonoscopyResult.getId());
        List<ColonoscopyPathologyDiagnosisRecordVo> data = new ArrayList<>();
        //data = colonoscopyPathologyDiagnosisRecords.stream().map(n -> new ColonoscopyPathologyDiagnosisRecordVo(n)).collect(Collectors.toList());
        if(colonoscopyPathologyDiagnosisRecords.size()>0){
            for (ColonoscopyPathologyDiagnosisRecord colonoscopyPathologyDiagnosisRecord:colonoscopyPathologyDiagnosisRecords) {
                ColonoscopyPathologyDiagnosisRecordVo colonoscopyPathologyDiagnosisRecordVo=new ColonoscopyPathologyDiagnosisRecordVo(colonoscopyPathologyDiagnosisRecord);
                data.add(colonoscopyPathologyDiagnosisRecordVo);
            }
        }
        //数据梳理
        HospitalColonoscopyPathologyResultVo hospitalColonoscopyResultVo=new HospitalColonoscopyPathologyResultVo();
        BeanUtils.copyProperties(hospitalColonoscopyResultData, hospitalColonoscopyResultVo);
        hospitalColonoscopyResultVo.setSurveyDate(hospitalColonoscopyResultData.getSurveyDate()==null?"": DateUtil.dateToStr(hospitalColonoscopyResultData.getSurveyDate(), DateUtil.YMR_SLASH));
        hospitalColonoscopyResultVo.setDiagnosisDate(hospitalColonoscopyResultData.getDiagnosisDate()==null?"":DateUtil.dateToStr(hospitalColonoscopyResultData.getDiagnosisDate(), DateUtil.YMR_SLASH));
        hospitalColonoscopyResultVo.setColonoscopyPathologyDiagnosisRecordList(data);
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, hospitalColonoscopyResultVo, null));
    }

    @Override
    public ColonoscopyPathologyResult queryByID(Integer id, String sid) {
        return colonoscopyPathologyResultDao.queryColonoscopyPathologyResultById(id,sid);
    }

    @Override
    public List<ColonoscopyPathologyDiagnosisRecord> queryColonoscopyPathologyDiagnosisRecordsByPathologyResultId(Integer id) {
        return colonoscopyPathologyDiagnosisRecordDao.queryColonoscopyPathologyDiagnosisRecordsByPathologyResultId(id);
    }


    /**
     * 修改肠镜病理表
     */
    @Override
    @Transactional
    public void updatePathologyResult(ColonoscopyPathologyResult newResult, ColonoscopyPathologyResult oldResult,String loginName,HospitalReferenceRecordDto hospitalReferenceRecordDto){
        /**
         * 1.更新肠镜病理表
         * hospital_colonoscopy_pathology_result  hospital_colonoscopy_pathology_diagnosis_record
         *
         * 2.根据前端传过来的状态是否删除告知书，还要判断一遍告知书是否已录入，
         * 如果是删除状态，则删除报告和待办，报告状态变为null（未录入），发放状态变为null
         * 如果不是删除状态并且报告处于未录入状态，则删除告知书录入待办和修改告知书录入状态为null
         *
         * 最终得出告知书的状态  已录入(不用处理) 或者null(生成未录入报告待办+肠镜报告状态变成未录入)
         *
         * 如果癌症信息不变 则不用去处理
         *
         * 4.癌  是-->否（要删除哪些数据？人员状态的癌症信息）
         * 4.1 删除终点事件待办和数据
         * 4.2 更改人员癌症状态
         *
         * 5.癌  否-->是
         * 5.1 生成终点事件待办和数据
         * 5.2 更改人员癌症状态
         */

        //准备数据 begin
        //查询肠镜检查信息
        HospitalColonoscopyRecord colonoscopyRecord = personDao.findRecordByRecordId(newResult.getColonoscopyRecordId());
        if (newResult.getColonoscopyRecordId()==null||colonoscopyRecord==null) {
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }

        //获取登录人信息
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        //获取受试者信息
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
            log.info("updatePathologyResult---------记录操作日志error"+JSONUtils.toJson(hospitalReferenceRecordDto));
        }
        //记录操作记录end

        // 更新肠镜病理表 begin
        if(newResult.getId()==null&&newResult.getId()<0){
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }
        newResult.setApplyStatus(Constans.APPLY_EDIT_STATUS1);
        newResult.setApprovalStatus(null);
        newResult.setEditStatus(Constans.EDIT_STATUS1);
        Integer id = colonoscopyPathologyResultDao.updateColonoscopyPathologyResult(newResult);
        //删除原有的诊断记录
        colonoscopyPathologyDiagnosisRecordDao.deleteByResultId(newResult.getId());
        //重新添加结肠镜病理诊断记录表
        List<ColonoscopyPathologyDiagnosisRecord> diagnosisRecordList = newResult.getColonoscopyPathologyDiagnosisRecordList();
        if(diagnosisRecordList!=null&&diagnosisRecordList.size()>0){
            int count=0;
            for(ColonoscopyPathologyDiagnosisRecord diagnosisRecord:diagnosisRecordList){
                diagnosisRecord.setPathologyResultId(newResult.getId());
                diagnosisRecord.setIndex(count++);
                colonoscopyPathologyDiagnosisRecordDao.addColonoscopyPathologyDiagnosisRecord(diagnosisRecord);
            }
        }
      //更新肠镜病理表 end

        //是否删除告知书 begin delNotification  1是  2否
        log.info("--------updatePathologyResult 是否要删除告知书:-------- "+newResult.getDelNotification());
        if (newResult.getDelNotification()==null && colonoscopyRecord.getNotification_id() != null){
            log.info("--------存在告知书，但是没有传值是否删除告知书--------");
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }
        if (newResult.getDelNotification() !=null && newResult.getDelNotification() == 2 && colonoscopyRecord.getNotification_id() != null) {
            log.info("--------不删除已存在的告知书-----------sid:" + colonoscopyRecord.getSid() + "----------肠镜检查id:" + colonoscopyRecord.getId());
        } else {
            log.info("--------删除已存在的告知书-----------sid:" + colonoscopyRecord.getSid() + "----------肠镜检查id:" + colonoscopyRecord.getId());
            //删除数据+待办
            screeningNotificationDao.delNotificationById(colonoscopyRecord.getNotification_id());
            htEventDao.delNotificationTodoEvent(colonoscopyRecord.getSid(), colonoscopyRecord.getId());
            //肠镜检查信息--报告信息置空
            colonoscopyRecord = this.fZnatificationInfoNUll(colonoscopyRecord);
            //生成未录入报告待办+肠镜报告状态变成未录入
            colonoscopyRecord.setNotification_entry_status(1);
            updateColonoscopyResultTriggerTODO(colonoscopyRecord, "createNotificationTodo");
            if(newResult.getDelNotification() !=null && newResult.getDelNotification() == 1) {
                //发送消息给社区创建者和地区所有管理员
                colonoscopyResultService.sendMsgForDelNotification(loginName, colonoscopyRecord, "病理结果表");
            }
        }
        //是否删除告知书 end

        //癌症信息是否发生变化 begin
        if(newResult.getItem3()!=oldResult.getItem3()){
            log.info(colonoscopyRecord.getSid()+"的癌症信息发生变化");
            if(newResult.getItem3()==1){
                log.info(colonoscopyRecord.getSid()+"的癌症信息发生变化,由否---->是");
                //生成终点事件待办和数据
                triggerAddZDSJ(hospitalReview,newResult.getId(),colonoscopyRecord,doctorInfo,loginName);
                //更改人员状态
                String stageCy = "overall_status_t" + (colonoscopyRecord.getStage() - 1);
                personDao.updateOverallStatus(Constans.PERSON_OVERALL_STATUS3, colonoscopyRecord.getSid(), stageCy);//更新用户总体状态

            }else if(newResult.getItem3()==2){
                log.info(colonoscopyRecord.getSid()+"的癌症信息发生变化,由是---->否");
                //删除终点事件待办和数据
                cancerDao.delCancerRecordForCJResult(colonoscopyRecord.getId(),colonoscopyRecord.getSid());
                //更改人员状态
                String stageCy = "overall_status_t" + (colonoscopyRecord.getStage() - 1);
                personDao.updateOverallStatus(Constans.PERSON_OVERALL_STATUS1, colonoscopyRecord.getSid(), stageCy);//更新用户总体状态
            }
        }else {
            log.info(colonoscopyRecord.getSid()+"的癌症信息没有发生变化");
        }
        //癌症信息是否发生变化 end

        //更新检查记录表 begin
        colonoscopyDao.updateColonoscopyRecordForCheckStatus(colonoscopyRecord);
        //更新检查记录表 end
    }

    //触发生成终点事件信息
    public void triggerAddZDSJ(HospitalReview hospitalReview,Integer pathologyId,HospitalColonoscopyRecord colonoscopyRecord,DoctorInfo doctorInfo,String loginName){
        HospitalCancerRecordVo hospitalCancerRecordVo=new HospitalCancerRecordVo();
        hospitalCancerRecordVo.setSid(hospitalReview.getSid());
        hospitalCancerRecordVo.setAreaDeptId(hospitalReview.getAreaDeptId());
        hospitalCancerRecordVo.setCommunityDeptId(hospitalReview.getCommunityDeptId());
        hospitalCancerRecordVo.setStage(hospitalReview.getStageCy());
        hospitalCancerRecordVo.setPathologyId(pathologyId);
        hospitalCancerRecordVo.setColonoscopyRecordId(colonoscopyRecord.getId());
        hospitalCancerRecordVo.setCancerReportStatus(Constans.cancerReportStatus1);
        hospitalCancerRecordVo.setCancerDiagnoseStatus(Constans.CANCER_DIAGNOSE_STATUS1);
        hospitalCancerRecordVo.setColorectalCancerDiagnoseInformationStatus(Constans.COLORECTAL_CANCER_DIAGNOSE_INFORMATION_STATUS1);
        hospitalCancerRecordVo.setColorectalCancerTreatmentInformatioStatus(Constans.COLORECTAL_CANCER_TREATMENT_INFORMATIO_STATUS1);
        hospitalCancerRecordVo.setEndEventType(Constans.END_EVENT_TYPE1);
        hospitalCancerRecordVo.setValidData(Constans.VALID_DATA1);
        hospitalCancerRecordVo.setDeptCode(hospitalReview.getSiteId());
        hospitalCancerRecordVo.setCreateUser(doctorInfo.getId());
        int i=cancerDao.save(hospitalCancerRecordVo);
        //	触发4个患癌事件
        HospitalTodoEvent hospitalTodoEvent = new HospitalTodoEvent();
        hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE20);
        hospitalTodoEvent.setCommunityDeptId(hospitalReview.getCommunityDeptId());
        hospitalTodoEvent.setAreaDeptId(hospitalReview.getAreaDeptId());
        hospitalTodoEvent.setSid(hospitalReview.getSid());
        hospitalTodoEvent.setDataId(i);
        hospitalTodoEvent.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
        //表C1：癌症报告表
        int c1=personDao.addTodoEvent(hospitalTodoEvent);
        //表C2：癌症诊断表
        hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE21);
        int c2=personDao.addTodoEvent(hospitalTodoEvent);
        //表C3-结直肠癌诊断信息摘录表
        hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE22);
        int c3=personDao.addTodoEvent(hospitalTodoEvent);
        //表C4：结直肠癌治疗信息摘录表
        hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE23);
        int c4=personDao.addTodoEvent(hospitalTodoEvent);

        //添加消息中心
        List<ItsysUserDto> itsysUserDtos = colonoscopyService.queryloginNamesByloginName(loginName);
        List<ItsysUserDto> itsysUserDtosList = colonoscopyService.queryAllloginNamesByloginName(hospitalReview.getCommunityDeptId().toString());
        if(itsysUserDtosList.size()!=0){
            itsysUserDtos.addAll(itsysUserDtosList);
        }
        String meaasge_typpe=Constans.meaasge_typpe2;
        //获取退出原因
        String text="";
        String sid=colonoscopyRecord.getSid();
        String meaasge_text_typpe=Constans.meaasge_text_typpe3;
        String courierNumber="";
        String remark="";
        Integer data_id=colonoscopyRecord.getId();
        //添加消息中心
        List<HospitalMessageCenterDto> hospitalMessageCenterDtoList = new ArrayList<>();
        for (ItsysUserDto itsysUserDto : itsysUserDtos) {
            HospitalMessageCenterDto hospitalMessageCenterDto = new HospitalMessageCenterDto();
            hospitalMessageCenterDto.setSendUser(loginName);
            String accpetName = itsysUserDto.getLoginname();
            hospitalMessageCenterDto.setAcceptUser(accpetName);
            hospitalMessageCenterDto.setMessageType(Constans.meaasge_typpe2);
            hospitalMessageCenterDto.setData_id(data_id);
            hospitalMessageCenterDto.setForm_type(Constans.HOSPITAL_COLONOSCOPY_RECORD);
            hospitalMessageCenterDto.setSid(colonoscopyRecord.getSid());
            String message = SendMessageCenter.getMessage(loginName, accpetName, meaasge_typpe, text, sid,meaasge_text_typpe,courierNumber,remark);
            hospitalMessageCenterDto.setMessageText(message);
            hospitalMessageCenterDto.setId(UUID.randomUUID().toString().replace("-", ""));
            hospitalMessageCenterDtoList.add(hospitalMessageCenterDto);
        }
        hospitalMessageCenterService.save(hospitalMessageCenterDtoList, null,Constans.APPLY_EDIT_STATUS2,Constans.EDIT_STATUS1,Constans.APPROVAL_STATUS1,"","",true,null);
    }
    /**
     * 触发告知书待办
     *
     * @param colonoscopyRecord
     * @param type
     */
    public void updateColonoscopyResultTriggerTODO( HospitalColonoscopyRecord colonoscopyRecord,String type){
        if(colonoscopyRecord.getNotification_id() == null&&type.equals("createNotificationTodo") && colonoscopyRecord.getSource_type() == Constans.SOURCE_TYPE1){
            HospitalTodoEvent hospitalTodoEvent = new HospitalTodoEvent();
            hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE10);
            hospitalTodoEvent.setCommunityDeptId(colonoscopyRecord.getCommunity_dept_id());
            hospitalTodoEvent.setAreaDeptId(colonoscopyRecord.getArea_dept_id());
            hospitalTodoEvent.setSid(colonoscopyRecord.getSid());
            hospitalTodoEvent.setDataId(colonoscopyRecord.getId());
            hospitalTodoEvent.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
            personDao.addTodoEvent(hospitalTodoEvent);
        }

    }

    /**
     * 赋值 肠镜检查---报告信息为null
     * @return
     */
    public HospitalColonoscopyRecord fZnatificationInfoNUll(HospitalColonoscopyRecord colonoscopyRecord){
        colonoscopyRecord.setNotification_id(null);
        colonoscopyRecord.setNotification_entry_status(null);
        colonoscopyRecord.setNotification_entry_date(null);
        colonoscopyRecord.setNotification_entry_operator(null);
        colonoscopyRecord.setNotification_issue_status(null);
        colonoscopyRecord.setNotification_issue_date(null);
        colonoscopyRecord.setNotification_issue_operator(null);
        colonoscopyRecord.setNotification_issue_worker_code(null);
        colonoscopyRecord.setNotification_issue_mode(null);
        colonoscopyRecord.setNotification_issue_note(null);
        return  colonoscopyRecord;
    }


    @Override
    @Transactional
    public void updateColonoscopyPathologyResult(ColonoscopyPathologyResult colonoscopyPathologyResult1, ColonoscopyPathologyResult colonoscopyPathologyResult, HospitalReferenceRecordDto hospitalReferenceRecordDto) {
       try{

        //获取受试者年度阶段
        HospitalReview hospitalReview = personDao.getBySid(colonoscopyPathologyResult.getSid());
        //单表修改病理结果
        colonoscopyPathologyResult.setApplyStatus(Constans.APPLY_EDIT_STATUS1);
        colonoscopyPathologyResult.setApprovalStatus(null);
        colonoscopyPathologyResult.setEditStatus(Constans.EDIT_STATUS1);

        Integer id = colonoscopyPathologyResultDao.updateColonoscopyPathologyResult(colonoscopyPathologyResult);
        hospitalReferenceRecordDao.updateForEdir(hospitalReferenceRecordDto);


        List<ColonoscopyPathologyDiagnosisRecord> colonoscopyPathologyDiagnosisRecordList = colonoscopyPathologyResult1.getColonoscopyPathologyDiagnosisRecordList();
        StringBuffer stringBuffer=new StringBuffer();
        if(colonoscopyPathologyDiagnosisRecordList!=null && colonoscopyPathologyDiagnosisRecordList.size()>0){
            for (ColonoscopyPathologyDiagnosisRecord colonoscopyPathologyDiagnosisRecord:colonoscopyPathologyDiagnosisRecordList) {
                stringBuffer.append("'"+colonoscopyPathologyDiagnosisRecord.getId()+"',");
            }
        }
        //删除原有病变记录
        if(!com.alibaba.druid.util.StringUtils.isEmpty(stringBuffer)){
            colonoscopyPathologyResultDao.deleteByIDs(stringBuffer.substring(0,stringBuffer.length()-1));
        }

        //添加结肠镜病理诊断记录表
        List<ColonoscopyPathologyDiagnosisRecord> diagnosisRecordList = colonoscopyPathologyResult.getColonoscopyPathologyDiagnosisRecordList();
        if(diagnosisRecordList!=null&&diagnosisRecordList.size()>0){
            int count=0;
            for(ColonoscopyPathologyDiagnosisRecord diagnosisRecord:diagnosisRecordList){
                diagnosisRecord.setPathologyResultId(colonoscopyPathologyResult1.getId());
                diagnosisRecord.setIndex(count++);
                colonoscopyPathologyDiagnosisRecordDao.addColonoscopyPathologyDiagnosisRecord(diagnosisRecord);
            }
        }


        if(colonoscopyPathologyResult.getItem3()==1){
            //患癌症
            String stageCy = "overall_status_t" + (hospitalReview.getStageCy() - 1);
            personDao.updateOverallStatus(Constans.PERSON_OVERALL_STATUS3, colonoscopyPathologyResult.getSid(), stageCy);//更新用户总体状态

            //添加消息中心
            List<ItsysUserDto> itsysUserDtos = colonoscopyService.queryloginNamesByloginName(hospitalReferenceRecordDto.getEditPerson());
            List<ItsysUserDto> itsysUserDtosList = colonoscopyService.queryAllloginNamesByloginName(hospitalReview.getCommunityDeptId().toString());
            if(itsysUserDtosList.size()!=0){
                itsysUserDtos.addAll(itsysUserDtosList);
            }
            String meaasge_typpe=Constans.meaasge_typpe2;
            //获取退出原因
            String text="";
            String sid=colonoscopyPathologyResult.getSid();
            String meaasge_text_typpe=Constans.meaasge_text_typpe3;
            String courierNumber="";
            String remark="";

            //添加消息中心
            List<HospitalMessageCenterDto> hospitalMessageCenterDtoList = new ArrayList<>();
            for (ItsysUserDto itsysUserDto : itsysUserDtos) {
                HospitalMessageCenterDto hospitalMessageCenterDto = new HospitalMessageCenterDto();
                hospitalMessageCenterDto.setSendUser(hospitalReferenceRecordDto.getEditPerson());
                String accpetName = itsysUserDto.getLoginname();
                hospitalMessageCenterDto.setAcceptUser(accpetName);
                hospitalMessageCenterDto.setMessageType(Constans.meaasge_typpe2);
                /**
                 * 1  异常    违反方案
                 *           sid
                 *           已经退出研究
                 *           sid、text
                 *           诊断为结直肠癌
                 *           sid
                 *申请|发放编辑
                 *          快递模块
                 *          sendUser、acceptUser、courierNumber
                 *          管理模块
                 *          sendUser、acceptUser、text（模块+sid）
                 *通知发放
                 *    public static String getMessage(String sendUser, String acceptUser, String meaasgeType, String text, String sid, String meaasgeTextType, String courierNumber) {
                 *
                 *
                 *
                 *
                 */
                String message = SendMessageCenter.getMessage(hospitalReferenceRecordDto.getEditPerson(), accpetName, meaasge_typpe, text, sid,meaasge_text_typpe,courierNumber,remark);
                hospitalMessageCenterDto.setMessageText(message);
                hospitalMessageCenterDto.setId(UUID.randomUUID().toString().replace("-", ""));
                hospitalMessageCenterDtoList.add(hospitalMessageCenterDto);
            }
            hospitalMessageCenterService.save(hospitalMessageCenterDtoList, null,Constans.APPLY_EDIT_STATUS2,Constans.EDIT_STATUS1,Constans.APPROVAL_STATUS1,"","",true,null);
        }
       }catch (Exception e){
           throw new ItSysException(
                   GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG);
       }
    }
}
