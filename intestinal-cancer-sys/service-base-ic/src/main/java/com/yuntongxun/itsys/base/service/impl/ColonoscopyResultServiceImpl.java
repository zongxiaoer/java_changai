package com.yuntongxun.itsys.base.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.yuntongxun.itsys.base.cache.RedisManager;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.constans.RedisConstant;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.DateUtil;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.dao.*;
import com.yuntongxun.itsys.base.po.*;
import com.yuntongxun.itsys.base.po.dto.ItsysUserDto;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
import com.yuntongxun.itsys.base.po.dto.message.HospitalMessageCenterDto;
import com.yuntongxun.itsys.base.service.ColonoscopyResultService;
import com.yuntongxun.itsys.base.service.ColonoscopyService;
import com.yuntongxun.itsys.base.service.HospitalMessageCenterService;
import com.yuntongxun.itsys.base.service.UserService;
import com.yuntongxun.itsys.base.vo.*;
import net.sf.json.JSONArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 肠镜报告记录
 */
@Service
public class ColonoscopyResultServiceImpl implements ColonoscopyResultService {

    final Logger log = LogManager.getLogger(ColonoscopyResultServiceImpl.class);

    @Autowired
    private ColonoscopyPathologyResultDao colonoscopyPathologyResultDao;

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private UserService userService;

    @Autowired
    private ColonoscopyResultDao colonoscopyResultDao;

    @Autowired
    private ColonoscopyLesionsRecordDao colonoscopyLesionsRecordDao;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private ColonoscopyDao colonoscopyDao;

    @Autowired
    private HtEventDao htEventDao;

    @Autowired
    private ColonoscopyService colonoscopyService;

    @Autowired
    private HospitalReferenceRecordDao hospitalReferenceRecordDao;

    @Autowired
    private ScreeningNotificationDao screeningNotificationDao;

    @Autowired
    private HospitalCancerDao cancerDao;

    @Autowired
    private HospitalMessageCenterService hospitalMessageCenterService;

    @Override
    @Transactional
    public void addColonoscopyResult(String body, String loginName) {
        ColonoscopyResult colonoscopyResult = JSONUtils.jsonToBeanDateSerializer(body, ColonoscopyResult.class, "yyyy-MM-dd");
        //获取登录这信息
        String key = RedisConstant.HOSPITAL_KEY_INFO + loginName;
        String value = redisManager.get(key);

        if (colonoscopyResult.getColonoscopyRecordId() == null) {
            log.info("addColonoscopyResult 参数异常 缺少检查记录ID  getColonoscopyRecordId为空");
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }
        //根据getColonoscopyRecordId  查询   hospital_colonoscopy_record
        List<HospitalColonoscopyRecord> hospitalColonoscopyRecords = colonoscopyDao.queryById(colonoscopyResult.getColonoscopyRecordId());
        if (hospitalColonoscopyRecords.size() != 1) {
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }
        //获取登录人信息
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        colonoscopyResult.setAreaDeptId(doctorInfo.getAreaDeptId());
        //获取受试者年度阶段
        HospitalReview hospitalReview = personDao.getBySid(colonoscopyResult.getSid());
        colonoscopyResult.setStage(hospitalReview.getStageCy());
        colonoscopyResult.setCommunityDeptId(hospitalReview.getCommunityDeptId());
        /**
         * 1.添加肠镜结果记录表hospital_colonoscopy_result
         * 2.添加 hospital_colonoscopy_lesions_record
         * 3.修改表hospital_colonoscopy_record（examination_status 结肠镜检查就诊状态 result_id int 结肠镜检查结果id； result_status int 结肠镜结果状态，1：未录入，2：已录入；  result_date datetime 结果录入时间； result_operator int 结果录入医生）
         * 4.修改待办表变成已完成状态 hospital_todo_event
         * 5.根据报告的结果触发不同待办，主要看结肠镜是否正常完成
         * （未完成要修改 hospital_colonoscopy_record 的finished_status，触发未完成结肠镜检查待办，）
         * （已完成要修改 hospital_colonoscopy_record 的finished_status，如果有病理，触发待录入病理结果，如果没有病理触发待录入筛查结果告知书）
         * 6.添加下一个待办 hospital_todo_event
         */
        try{

        colonoscopyResult.setEditoperationSource(hospitalColonoscopyRecords.get(0).getEditoperationSource());
        colonoscopyResult.setOperationSourceId(colonoscopyResult.getColonoscopyRecordId());
        if(colonoscopyResult.getFileUploads() != null && colonoscopyResult.getFileUploads().size() > 0){
            colonoscopyResult.setImagePath(JSONUtils.objectToJsonDateSerializer(colonoscopyResult.getFileUploads(),"yyyy-MM-dd"));
        }
        Integer id = colonoscopyResultDao.addColonoscopyResult(colonoscopyResult);//添加肠镜报告
        //判断是否有肠镜结果

        //判断属否有肠镜图片没有添加图片代办
            if( Constans.item_2_21==colonoscopyResult.getItem_2_2()){
                if (colonoscopyResult.getFileUploads() == null || colonoscopyResult.getFileUploads().size() == 0) {
                    HospitalTodoEvent hospitalTodoEvent = new HospitalTodoEvent();
                    hospitalTodoEvent.setCommunityDeptId(hospitalReview.getCommunityDeptId());
                    hospitalTodoEvent.setAreaDeptId(doctorInfo.getAreaDeptId());
                    hospitalTodoEvent.setDataId(id);
                    hospitalTodoEvent.setSid(colonoscopyResult.getSid());
                    hospitalTodoEvent.setOperationSource(colonoscopyResult.getEditoperationSource());
                    hospitalTodoEvent.setOperationSourceId(id);
                    hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE19);
                    hospitalTodoEvent.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
                    personDao.addTodoEvent(hospitalTodoEvent);
                }
            }

        int count=0;
        //添加病变记录
        if (colonoscopyResult.getLesionsRecordList() != null && colonoscopyResult.getLesionsRecordList().size() > 0) {
            for (ColonoscopyLesionsRecord colonoscopyLesionsRecord : colonoscopyResult.getLesionsRecordList()) {
                colonoscopyLesionsRecord.setColonoscopyResultId(id);
                count++;
                colonoscopyLesionsRecord.setIndex(count);
                try {
                    colonoscopyLesionsRecordDao.addColonoscopyLesionsRecord(colonoscopyLesionsRecord);
                }catch (Exception e){
                    System.out.println(e.toString());
                }
            }
        }
        //添加病变记录
        HospitalColonoscopyRecord record = personDao.findRecordByRecordId(colonoscopyResult.getColonoscopyRecordId());
        ColonoscopyResultVo vo = this.getColonoscopyResultVo(colonoscopyResult, id, doctorInfo, colonoscopyResult.getColonoscopyRecordId(), record);
        //修改肠镜、告知书、病理在修改表hospital_colonoscopy_record中的字段变更
        colonoscopyDao.updateNotificationResultStatus(vo);//修改表hospital_colonoscopy_record


        //肠镜检查记录来源如果是预约 则要更新待办状态，新增的不会去触发待办
        if (record != null && record.getSource_type() == Constans.SOURCE_TYPE1) {
            // 更新待办完成状态
            htEventDao.updateStatus(colonoscopyResult.getSid(), colonoscopyResult.getColonoscopyRecordId(), Constans.PERSON_TODO_EVENT_TYPE8, Constans.PERSON_TODO_EVENT_STATUS2);//待办完成
            //添加下一步待办
            personDao.addTodoEvent(this.getHospitalTodoEvent(vo, doctorInfo, colonoscopyResult.getSid(), colonoscopyResult.getColonoscopyRecordId(), hospitalReview, colonoscopyResult.getEditoperationSource()));
        }

        }catch (Exception e){
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }
    }


    @Override
    public String queryColonoscopyResultToStringById(HospitalColonoscopyResult hospitalColonoscopyResult) {
        //校验参数是否存在
        if (null == hospitalColonoscopyResult.getId() || StringUtils.isEmpty(hospitalColonoscopyResult.getSid())) {
            log.info("queryColonoscopyResult 输入参数id或者sid不存在/错误");
            return JSONUtils.toJson(new Response(GlobalErrorCode.REQUIRED_PARAMETER_ERROR, GlobalErrorCode.PARAMETER_ERR_MSG));
        }

        if (hospitalColonoscopyResult.getIdType() != null && hospitalColonoscopyResult.getIdType().equals("record")) {
            //传输的id 为检查记录表中的主键，需要用检查记录表主键去查询肠镜结果表的id
            HospitalColonoscopyRecord record = personDao.findRecordByRecordId(hospitalColonoscopyResult.getId());
            if (record != null && record.getResult_id() != null) {
                hospitalColonoscopyResult.setId(record.getResult_id());
            } else {
                log.info("queryColonoscopyResultToStringById 根据recordID查询肠镜结果id为空");
                return JSONUtils.toJson(new Response(GlobalErrorCode.REQUIRED_PARAMETER_ERROR, GlobalErrorCode.PARAMETER_ERR_MSG));
            }
        }
        ColonoscopyResult hospitalColonoscopyResultData;
        try {
            //获取结果集
            hospitalColonoscopyResultData = colonoscopyResultDao.queryHospitalColonoscopyResultById(hospitalColonoscopyResult.getId(), hospitalColonoscopyResult.getSid());
            //判断结果集是否存在
            if (hospitalColonoscopyResultData == null) {
                log.info("queryColonoscopyResult 根据id查询结果集为空");
                return JSONUtils.toJson(new Response(GlobalErrorCode.OBJECT_NOT_EXISTS, GlobalErrorCode.OBJECT_NOT_EXISTS_MSG));
            }
            if (!StringUtils.isEmpty(hospitalColonoscopyResultData.getImagePath())) {
                hospitalColonoscopyResultData.setFileUploads((List<FileUploadLogPO>) JSONArray.toList(JSONArray.fromObject(hospitalColonoscopyResultData.getImagePath()), FileUploadLogPO.class));

            }
        } catch (Exception e) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.UNKNOWN_ERROR, GlobalErrorCode.UNKNOWN_ERROR_MSG));
        }
        //根据结肠镜结果记录表ID查询结肠镜结果病变记录表集合
        List<ColonoscopyLesionsRecord> colonoscopyLesionsRecords = colonoscopyLesionsRecordDao.queryByColonoscopyResultId(hospitalColonoscopyResult.getId());
        List<ColonoscopyLesionsRecordVo> data = new ArrayList<>();
        if (colonoscopyLesionsRecords.size() > 0) {
            for (ColonoscopyLesionsRecord colonoscopyPathologyDiagnosisRecord : colonoscopyLesionsRecords) {
                ColonoscopyLesionsRecordVo colonoscopyPathologyDiagnosisRecordVo = new ColonoscopyLesionsRecordVo();
                BeanUtils.copyProperties(colonoscopyPathologyDiagnosisRecord, colonoscopyPathologyDiagnosisRecordVo);
                data.add(colonoscopyPathologyDiagnosisRecordVo);
            }
        }
        //数据梳理
        HospitalColonoscopyResultVo hospitalColonoscopyResultVo = new HospitalColonoscopyResultVo();
        BeanUtils.copyProperties(hospitalColonoscopyResultData, hospitalColonoscopyResultVo);
        hospitalColonoscopyResultVo.setSurveyDate(hospitalColonoscopyResultData.getSurveyDate() == null ? "" : DateUtil.dateToStr(hospitalColonoscopyResultData.getSurveyDate(), DateUtil.YMR_SLASH));
        hospitalColonoscopyResultVo.setLesionsRecordList(data);
        if (!StringUtils.isEmpty(hospitalColonoscopyResultVo.getSid())) {
            //根据sid获取病例结果id
            List<ColonoscopyPathologyResult> colonoscopyPathologyResults = colonoscopyPathologyResultDao.queryColonoscopyPathologyResultById(hospitalColonoscopyResultVo.getSid(), hospitalColonoscopyResult.getId());
            if (colonoscopyPathologyResults.size() == 1) {
                hospitalColonoscopyResultVo.setPathologyId(colonoscopyPathologyResults.get(0).getId());
            }
        }
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, hospitalColonoscopyResultVo, null));
    }

    @Override
    public List<ColonoscopyResult> queryById(Integer id) {
        return colonoscopyResultDao.queryById(id);
    }

    @Override
    public List<ColonoscopyLesionsRecord> queryByColonoscopyResultId(Integer id) {
        return colonoscopyLesionsRecordDao.queryByColonoscopyResultId(id);
    }

    /**
     * 肠镜表单提交表单，如果告知书处于已录入状态，要提示前端是否删除报告，并重新录入
     */
    @Override
    public HospitalColonoscopyRecord delNotificationState(ColonoscopyResult newResult){
        HospitalColonoscopyRecord colonoscopyRecord = personDao.findRecordByRecordId(newResult.getColonoscopyRecordId());
        return colonoscopyRecord;
    }

    /**
     * 修改肠镜结果表
     */
    @Override
    @Transactional
    public void updateColonoscopyResult(ColonoscopyResult newResult, ColonoscopyResult oldResult, String loginName,HospitalReferenceRecordDto hospitalReferenceRecordDto){
        /**
         * 1.更新肠镜结果表（参与者是否完成了结肠镜检查？这个字段不可以修改）
         * hospital_colonoscopy_result  hospital_colonoscopy_lesions_record
         *
         * 都是在肠镜已完成的状态下才能去判断
         *
         * 2.根据前端传过来的状态是否删除告知书，还要判断一遍告知书是否已录入，
         * 如果是删除状态，则删除报告和待办，报告状态变为null，发放状态等变为null
         * 如果不是删除状态 并且报告处于未录入状态，则删除告知书录入待办和修改告知书录入状态为null
         *
         * 最终得出告知书的状态，已录入或者null
         *
         * 查看病理信息有没有改变
         *
         * 3.如果病理的状态信息没有改变
         * 3.1 有病理&&病理已录入  如果报告状态为null 生成未录入报告待办+肠镜报告状态变成未录入
         * 3.2 无病理 如果报告状态为null 生成未录入报告待办+肠镜报告状态变成未录入
         *
         * 4.病理  是-->否
         * 4.1 删除病理信息和病理待办信息
         * 4.2 如果报告状态为null 生成未录入报告待办+肠镜病理变成未录入
         *
         * 5.病理  否-->是
         * 5.1 生成未录入病理待办+肠镜报告状态变成未录入
         */
        //准备数据 begin
            //查询肠镜检查信息
        HospitalColonoscopyRecord colonoscopyRecord = personDao.findRecordByRecordId(newResult.getColonoscopyRecordId());
        if (newResult.getColonoscopyRecordId()==null||colonoscopyRecord==null) {
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }
        //当前登录人信息
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
            log.info("updateColonoscopyResult---------记录操作日志error"+JSONUtils.toJson(hospitalReferenceRecordDto));
        }
        //记录操作记录end

        // 更新肠镜结果表 begin
            if(newResult.getId()==null&&newResult.getId()<0){
                throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
            }
            newResult.setItem_2_2(oldResult.getItem_2_2());//参与者是否完成了结肠镜检查  这个字段不允许修改
            newResult.setApplyStatus(Constans.APPLY_EDIT_STATUS1);//申请按钮出现
            newResult.setApprovalStatus(null);//不出现审核按钮
            newResult.setEditStatus(Constans.EDIT_STATUS1);//不可编辑
            if(newResult.getFileUploads() != null && newResult.getFileUploads().size() > 0){
                newResult.setImagePath(JSONUtils.objectToJsonDateSerializer(newResult.getFileUploads(),"yyyy-MM-dd"));
            }else{
                newResult.setImagePath(null);
            }
            Integer id = colonoscopyResultDao.updateColonoscopyResult(newResult);//修改肠镜报告
            //删除原有病变记录
            colonoscopyLesionsRecordDao.deleteByResultId(newResult.getId());
            int count=0;
            //重新添加病变记录
            if (newResult.getLesionsRecordList() != null && newResult.getLesionsRecordList().size() > 0) {
                for (ColonoscopyLesionsRecord colonoscopyLesionsRecord : newResult.getLesionsRecordList()) {
                    colonoscopyLesionsRecord.setColonoscopyResultId(newResult.getId());
                    colonoscopyLesionsRecord.setIndex(count++);
                    colonoscopyLesionsRecordDao.addColonoscopyLesionsRecord(colonoscopyLesionsRecord);
                }
            }
        // 更新肠镜结果表 end

        if(colonoscopyRecord.getFinished_status()==2) { //以下的逻辑都是在肠镜已完成的状态下才能去判断
            //是否删除告知书 begin delNotification  1是  2否
            log.info("--------updateColonoscopyResult 是否要删除告知书:-------- "+newResult.getDelNotification());
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
                if(newResult.getDelNotification() !=null&&newResult.getDelNotification() == 1) {
                    //发送消息给社区创建者和地区所有管理员
                    sendMsgForDelNotification(loginName, colonoscopyRecord, "肠镜结果表");
                }
            }
            //是否删除告知书 end

            //查看病理信息有没有改变 begin
            if (newResult.getPathology() != null && newResult.getPathology() == oldResult.getPathology()) {
                //无病理 如果报告状态为null 生成未录入报告待办+肠镜报告状态变成未录入
                //有病理&&病理已录入  如果报告状态为null 生成未录入报告待办+肠镜报告状态变成未录入
                if ((newResult.getPathology() == 2 && colonoscopyRecord.getNotification_id() == null)
                        || (newResult.getPathology() == 1 && colonoscopyRecord.getPathology_status() == 2 && colonoscopyRecord.getNotification_id() == null)) {
                    colonoscopyRecord.setNotification_entry_status(1);
                    updateColonoscopyResultTriggerTODO(colonoscopyRecord,"createNotificationTodo");
                }
            } else if(newResult.getPathology() != null && newResult.getPathology() != oldResult.getPathology()){
                if (newResult.getPathology() == 1) {
                    log.info("-------------------否---》是----------------");
                    //生成未录入病理待办+肠镜病理状态变成未录入
                    colonoscopyRecord.setPathology_status(1);
                    colonoscopyRecord.setPathology_date(null);
                    colonoscopyRecord.setPathology_id(null);
                    colonoscopyRecord.setPathology_operator(null);
                    updateColonoscopyResultTriggerTODO(colonoscopyRecord,"createPathologyTodo");
                }
                if (newResult.getPathology() == 2) {
                    log.info("-------------------是---》否----------------");
                    //删除病理信息和病理待办信息 置空病理信息
                    colonoscopyPathologyResultDao.delPathologyById(colonoscopyRecord.getPathology_id());
                    htEventDao.delPathologyTodoEvent(colonoscopyRecord.getSid(), colonoscopyRecord.getId());
                    colonoscopyRecord.setPathology_status(null);
                    colonoscopyRecord.setPathology_date(null);
                    colonoscopyRecord.setPathology_id(null);
                    colonoscopyRecord.setPathology_operator(null);
                    //如果报告状态为null 生成未录入报告待办+肠镜报告状态变成未录入
                    if(colonoscopyRecord.getNotification_id() == null) {
                        colonoscopyRecord.setNotification_entry_status(1);
                        updateColonoscopyResultTriggerTODO(colonoscopyRecord, "createNotificationTodo");
                    }
                    //删除终点事件待办和数据
                    cancerDao.delCancerRecordForCJResult(colonoscopyRecord.getId(),colonoscopyRecord.getSid());
                    //更改人员状态
                    String stageCy = "overall_status_t" + (colonoscopyRecord.getStage() - 1);
                    personDao.updateOverallStatus(Constans.PERSON_OVERALL_STATUS1, colonoscopyRecord.getSid(), stageCy);//更新用户总体状态
                }
            }
            //查看病理信息有没有改变 end

            //更新检查记录表 begin
            colonoscopyRecord.setResult_date(new Date());
            colonoscopyRecord.setResult_operator(doctorInfo.getId());
            colonoscopyDao.updateColonoscopyRecordForCheckStatus(colonoscopyRecord);
            //更新检查记录表 end
        }
    }

    /**
     * 修改肠镜结果表触发的待办
     */

    public void updateColonoscopyResultTriggerTODO( HospitalColonoscopyRecord colonoscopyRecord,String type){
        //新增的不会触发待办
        if (colonoscopyRecord != null && colonoscopyRecord.getSource_type() == Constans.SOURCE_TYPE1) {
            if (colonoscopyRecord.getNotification_id() == null && type.equals("createNotificationTodo")) {
                HospitalTodoEvent hospitalTodoEvent = new HospitalTodoEvent();
                hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE10);
                hospitalTodoEvent.setCommunityDeptId(colonoscopyRecord.getCommunity_dept_id());
                hospitalTodoEvent.setAreaDeptId(colonoscopyRecord.getArea_dept_id());
                hospitalTodoEvent.setSid(colonoscopyRecord.getSid());
                hospitalTodoEvent.setDataId(colonoscopyRecord.getId());
                hospitalTodoEvent.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
                personDao.addTodoEvent(hospitalTodoEvent);
            }

            if (type.equals("createPathologyTodo")) {
                HospitalTodoEvent hospitalTodoEvent = new HospitalTodoEvent();
                hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE9);
                hospitalTodoEvent.setCommunityDeptId(colonoscopyRecord.getCommunity_dept_id());
                hospitalTodoEvent.setAreaDeptId(colonoscopyRecord.getArea_dept_id());
                hospitalTodoEvent.setSid(colonoscopyRecord.getSid());
                hospitalTodoEvent.setDataId(colonoscopyRecord.getId());
                hospitalTodoEvent.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
                personDao.addTodoEvent(hospitalTodoEvent);
            }
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
    @Transactional  //告知书的id都是recordID
    public void updateColonoscopyResult(ColonoscopyResult colonoscopyResult, ColonoscopyResult colonoscopyResult1, HospitalReferenceRecordDto hospitalReferenceRecordDto) {
        try {
            //判断原数据是否有病理
            Integer oldPathology = colonoscopyResult1.getPathology();
            //查看请求数据是否有病理
            Integer newPathology = colonoscopyResult.getPathology();//1：是，2：否
            //根据原来肠镜检查记录的来源+来源ID查hospital_colonoscopy_record的id
            String editoperationSource = colonoscopyResult1.getEditoperationSource();
            Integer operationSourceId = colonoscopyResult1.getOperationSourceId();
            List<HospitalColonoscopyRecord> hospitalColonoscopyRecords = colonoscopyDao.queryBySourceAndId(editoperationSource, operationSourceId);
            if (hospitalColonoscopyRecords.size() != 1) {
                throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
            }
            Integer pathology_id = hospitalColonoscopyRecords.get(0).getPathology_id();
            String pathologyEditoperationSource = hospitalColonoscopyRecords.get(0).getEditoperationSource();
            Integer notification_id = hospitalColonoscopyRecords.get(0).getNotification_id();
            ColonoscopyResultVo vo = new ColonoscopyResultVo();

            //原有病理，现在没有病理
            if (oldPathology == Constans.YES_PATHOLOGY && newPathology == Constans.NO_PATHOLOGY) {
                //查看原来是否病理结果
                if (pathology_id != null) {//有病理id
                    //将请求数据状态改成已录入，病理id  hospital_colonoscopy_record
                    vo.setPathologyStatus(Constans.PATHOLOGY_STATUS2);
                } else {

                    //判断来源是新增还是系统触发
                    if (!Constans.ADD_NEW_HOSPITAL_COLONOSCOPY_RECORD.equals(editoperationSource)) {
                        //删除病理结果的代办
                        //删除预约代办   根据sid+recordID+status是代办
                        int i = personDao.deleteEventBySourceIdAndType(colonoscopyResult1.getId(), editoperationSource, Constans.PERSON_TODO_EVENT_TYPE9);
                        if (i < 1) {
                            throw new ItSysException(
                                    GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG);
                        }
                    }
                    //将请求数据状态改成null  hospital_colonoscopy_record
                    vo.setPathologyStatus(null);
                }
                if (notification_id != null) {//有告知书iD

                } else {
                    if (pathology_id != null) {
                        if (!Constans.ADD_NEW_HOSPITAL_COLONOSCOPY_RECORD.equals(hospitalColonoscopyRecords.get(0).getEditoperationSource())) {
                            //根据病理ID+病理来源 删除代办
                            int i = personDao.deleteEventBySourceIdAndType(hospitalColonoscopyRecords.get(0).getId(), hospitalColonoscopyRecords.get(0).getEditoperationSource(), Constans.PERSON_TODO_EVENT_TYPE10);
                            if (i < 1) {
                                throw new ItSysException(
                                        GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG);
                            }
                        }
                    }
                    HospitalTodoEvent hospitalTodoEvent = new HospitalTodoEvent();
                    hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE10);
                    hospitalTodoEvent.setCommunityDeptId(colonoscopyResult1.getCommunityDeptId());
                    hospitalTodoEvent.setAreaDeptId(colonoscopyResult1.getAreaDeptId());
                    hospitalTodoEvent.setSid(colonoscopyResult1.getSid());
                    hospitalTodoEvent.setDataId(colonoscopyResult1.getId());
                    hospitalTodoEvent.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
                    hospitalTodoEvent.setOperationSourceId(colonoscopyResult.getId());
                    hospitalTodoEvent.setOperationSource(colonoscopyResult.getEditoperationSource());
                    personDao.addTodoEvent(hospitalTodoEvent);
                    //修改为未录入
                    vo.setNotificationEntryStatus(Constans.NOTIFICATION_ENTRY_STATUS1);
                }

            }
            //原来无病理，现在有病理
            if (oldPathology == Constans.NO_PATHOLOGY && newPathology == Constans.YES_PATHOLOGY) {

                if (notification_id != null) {//有告知书id

                } else {
                    if (!Constans.ADD_NEW_HOSPITAL_COLONOSCOPY_RECORD.equals(editoperationSource)) {
                        //删除代办    //删除预约代办   根据sid+recordID+status是代办
                        int i = personDao.deleteEventBySourceIdAndType(colonoscopyResult1.getId(), editoperationSource, Constans.PERSON_TODO_EVENT_TYPE10);
                        if (i < 1) {
                            throw new ItSysException(
                                    GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
                        }
                    }
                    //状态改为空
                    vo.setNotificationEntryStatus(null);
                }

                if (pathology_id != null) {//有病理

                } else {
                    if (!Constans.ADD_NEW_HOSPITAL_COLONOSCOPY_RECORD.equals(pathologyEditoperationSource)) {
                        //添加代办
                        HospitalTodoEvent hospitalTodoEvent = new HospitalTodoEvent();
                        hospitalTodoEvent.setCommunityDeptId(colonoscopyResult1.getCommunityDeptId());
                        hospitalTodoEvent.setAreaDeptId(colonoscopyResult1.getAreaDeptId());
                        hospitalTodoEvent.setDataId(hospitalColonoscopyRecords.get(0).getId());
                        hospitalTodoEvent.setSid(colonoscopyResult1.getSid());
                        hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE9);
                        hospitalTodoEvent.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
                        personDao.addTodoEvent(hospitalTodoEvent);
                    }
                    //改成未录入
                    vo.setNotificationEntryStatus(Constans.NOTIFICATION_ENTRY_STATUS1);
                }
            }


            //对检查记录进行修改
            colonoscopyResult.setApplyStatus(Constans.APPLY_EDIT_STATUS1);
            colonoscopyResult.setApprovalStatus(null);
            colonoscopyResult.setEditStatus(Constans.EDIT_STATUS1);
            Integer id = colonoscopyResultDao.updateColonoscopyResult(colonoscopyResult);//添加肠镜报告

            hospitalReferenceRecordDao.updateForEdir(hospitalReferenceRecordDto);
            if (id < 1) {
                throw new ItSysException(
                        GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG);
            }

            List<ColonoscopyLesionsRecord> lesionsRecordList = colonoscopyResult1.getLesionsRecordList();
            StringBuffer stringBuffer = new StringBuffer();
            if (lesionsRecordList != null && lesionsRecordList.size() > 0) {
                for (ColonoscopyLesionsRecord colonoscopyLesionsRecord : lesionsRecordList) {
                    stringBuffer.append("'" + colonoscopyLesionsRecord.getId() + "',");
                }
            }
            //删除原有病变记录
            if (!StringUtils.isEmpty(stringBuffer)) {
                colonoscopyLesionsRecordDao.deleteByIDs(stringBuffer.substring(0, stringBuffer.length() - 1));
            }
            int count=0;
            //添加病变记录
            if (colonoscopyResult.getLesionsRecordList() != null && colonoscopyResult.getLesionsRecordList().size() > 0) {
                for (ColonoscopyLesionsRecord colonoscopyLesionsRecord : colonoscopyResult.getLesionsRecordList()) {
                    colonoscopyLesionsRecord.setColonoscopyResultId(colonoscopyResult.getId());
                    count++;
                    colonoscopyLesionsRecord.setIndex(count);
                    colonoscopyLesionsRecordDao.addColonoscopyLesionsRecord(colonoscopyLesionsRecord);
                }
            }


        } catch (Exception e) {
            throw new ItSysException(
                    GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG);
        }
    }


    @Deprecated
    private ColonoscopyResultVo getColonoscopyResultVoOld(ColonoscopyResult colonoscopyResult, Integer id, DoctorInfo doctorInfo, Integer colonoscopyRecordId) {
        ColonoscopyResultVo vo = new ColonoscopyResultVo();
        vo.setResultId(id);
        vo.setResultStatus(Constans.RESULT_STATUS2);
        vo.setResultDate(colonoscopyResult.getSurveyDate());
        vo.setResultOperator(doctorInfo.getId());
        vo.setColonoscopyRecordId(colonoscopyRecordId);
        vo.setExaminationStatus(Constans.EXAMINATION_STATUS2);//就诊 检查状态
        vo.setExaminationOperator(doctorInfo.getId());//就诊检查医生
        if (colonoscopyResult.getItem_2_2() == 2) {
            vo.setFinishedStatus(Constans.FINISHED_STATUS1);
            vo.setAllState(1);//表示未完成
            return vo;
        } else if (colonoscopyResult.getItem_2_2() == 1) {
            vo.setFinishedStatus(Constans.FINISHED_STATUS2);
        } else {
            log.info("addColonoscopyResult 参数异常 getItem_2_1值异常");
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }

        if (colonoscopyResult.getItem_2_2() == 1 && colonoscopyResult.getPathology() == 1) {
            //完成肠镜&&有病理
            vo.setPathologyStatus(Constans.PATHOLOGY_STATUS1);
            vo.setAllState(2);
        } else if (colonoscopyResult.getItem_2_2() == 1 && colonoscopyResult.getPathology() == 2) {
            //完成肠镜但是没有病理
            vo.setNotificationEntryStatus(Constans.NOTIFICATION_ENTRY_STATUS1);
            vo.setAllState(3);
        } else {
            log.info("addColonoscopyResult 参数异常 getPathology值异常");
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }
        return vo;
    }


    private ColonoscopyResultVo getColonoscopyResultVo(ColonoscopyResult colonoscopyResult, Integer id, DoctorInfo doctorInfo, Integer colonoscopyRecordId, HospitalColonoscopyRecord record) {
        ColonoscopyResultVo vo = new ColonoscopyResultVo();
        vo.setResultId(id);
        vo.setResultStatus(Constans.RESULT_STATUS2);
        vo.setResultDate(colonoscopyResult.getSurveyDate());
        vo.setResultOperator(doctorInfo.getId());
        vo.setColonoscopyRecordId(colonoscopyRecordId);
        //添加数据来源

        if (record.getExamination_status() != null && record.getExamination_status() == 2) {
            vo.setExaminationStatus(Constans.EXAMINATION_STATUS2);//就诊 检查状态
            vo.setExaminationOperator(doctorInfo.getId());//就诊检查医生
            vo.setExamination_check_date(record.getExamination_check_date());
            vo.setExamination_date(record.getExamination_date());
        } else {
            vo.setExaminationStatus(Constans.EXAMINATION_STATUS2);//就诊 检查状态
            vo.setExaminationOperator(doctorInfo.getId());//就诊检查医生
            vo.setExamination_check_date(new Date());
            vo.setExamination_date(new Date());
        }
        if (colonoscopyResult.getItem_2_2() == 2) {
            vo.setFinishedStatus(Constans.FINISHED_STATUS1);
            vo.setAllState(1);//表示未完成
            return vo;
        } else if (colonoscopyResult.getItem_2_2() == 1) {
            vo.setFinishedStatus(Constans.FINISHED_STATUS2);
        } else {
            log.info("addColonoscopyResult 参数异常 getItem_2_1值异常");
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }
        if (colonoscopyResult.getItem_2_2() == 1 && colonoscopyResult.getPathology() == 1) {
            //完成肠镜&&有病理
            vo.setPathologyStatus(Constans.PATHOLOGY_STATUS1);
            vo.setAllState(2);
        } else if (colonoscopyResult.getItem_2_2() == 1 && colonoscopyResult.getPathology() == 2) {
            //完成肠镜但是没有病理
            vo.setNotificationEntryStatus(Constans.NOTIFICATION_ENTRY_STATUS1);
            vo.setAllState(3);
        } else {
            log.info("addColonoscopyResult 参数异常 getPathology值异常");
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }
        return vo;
    }

    /**
     * 获取下一步待办
     */

    public HospitalTodoEvent getHospitalTodoEvent(ColonoscopyResultVo vo, DoctorInfo doctorInfo, String sid, Integer colonoscopyRecordId, HospitalReview hospitalReview, String eritOproce) {
        HospitalTodoEvent hospitalTodoEvent = new HospitalTodoEvent();
        hospitalTodoEvent.setCommunityDeptId(hospitalReview.getCommunityDeptId());
        hospitalTodoEvent.setAreaDeptId(doctorInfo.getAreaDeptId());
        hospitalTodoEvent.setDataId(colonoscopyRecordId);
        hospitalTodoEvent.setSid(sid);
        hospitalTodoEvent.setOperationSource(eritOproce);
        hospitalTodoEvent.setOperationSourceId(vo.getResultId());
        if (vo.getAllState() == 1) {
            //触发未完成结肠镜检查待办
            hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE6);
            hospitalTodoEvent.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
        } else if (vo.getAllState() == 2) {
            //触发录入病理待办
            hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE9);
            hospitalTodoEvent.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
        } else if (vo.getAllState() == 3) {
            //触发录入告知书待办
            hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE10);
            hospitalTodoEvent.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
        }
        return hospitalTodoEvent;
    }

    /**
     * 未完成待办
     *
     * @param doctorInfo
     * @param sid
     * @param colonoscopyRecordId
     * @param hospitalReview
     * @return
     */
    @Deprecated
    public HospitalTodoEvent getHospitalTodoEventOld(DoctorInfo doctorInfo, String sid, Integer colonoscopyRecordId, HospitalReview hospitalReview) {
        HospitalTodoEvent hospitalTodoEvent = new HospitalTodoEvent();
        hospitalTodoEvent.setCommunityDeptId(hospitalReview.getCommunityDeptId());
        hospitalTodoEvent.setAreaDeptId(doctorInfo.getAreaDeptId());
        hospitalTodoEvent.setDataId(colonoscopyRecordId);
        hospitalTodoEvent.setSid(sid);
        hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE6);
        hospitalTodoEvent.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
        return hospitalTodoEvent;
    }


    /**
     * 删除告知书后，要给受试者创建者和地区所有人发送消息
     * @param loginName
     */
    @Override
    public void sendMsgForDelNotification(String loginName, HospitalColonoscopyRecord colonoscopyRecord, String formType){
        List<ItsysUserDto> itsysUserDtos = colonoscopyService.getCreateUser(colonoscopyRecord.getSid());
        List<ItsysUserDto> itsysUserDtosList = colonoscopyService.queryAllloginNamesByloginName(colonoscopyRecord.getArea_dept_id().toString());
        if(itsysUserDtosList!=null && itsysUserDtosList.size()>0){
            itsysUserDtos.addAll(itsysUserDtosList);
        }
        String text="";
        String sid=colonoscopyRecord.getSid();
        String meaasge_text_typpe=loginName+"编辑了肠镜管理-"+colonoscopyRecord.getSid()+"-"+formType+"，涉及告知书变更，请及时关注。";
        String remark="";
        Integer data_id=colonoscopyRecord.getId();
        //添加消息中心
        List<HospitalMessageCenterDto> hospitalMessageCenterDtoList = new ArrayList<>();
        for (ItsysUserDto itsysUserDto : itsysUserDtos) {
            HospitalMessageCenterDto hospitalMessageCenterDto = new HospitalMessageCenterDto();
            hospitalMessageCenterDto.setSendUser(loginName);
            String accpetName = itsysUserDto.getLoginname();
            hospitalMessageCenterDto.setAcceptUser(accpetName);
            hospitalMessageCenterDto.setMessageType(Constans.meaasge_typpe3);
            hospitalMessageCenterDto.setData_id(data_id);
            hospitalMessageCenterDto.setForm_type(Constans.HOSPITAL_COLONOSCOPY_RECORD);
            hospitalMessageCenterDto.setSid(colonoscopyRecord.getSid());
            //String message = SendMessageCenter.getMessage(loginName, accpetName, meaasge_typpe, text, sid,meaasge_text_typpe,null,remark);
            hospitalMessageCenterDto.setMessageText(meaasge_text_typpe);
            hospitalMessageCenterDto.setId(UUID.randomUUID().toString().replace("-", ""));
            hospitalMessageCenterDtoList.add(hospitalMessageCenterDto);
        }
        hospitalMessageCenterService.save(hospitalMessageCenterDtoList, null,Constans.APPLY_EDIT_STATUS2,Constans.EDIT_STATUS1,Constans.APPROVAL_STATUS1,"","",true,null);
    }

}
