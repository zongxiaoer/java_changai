package com.yuntongxun.itsys.base.service.impl;

import com.yuntongxun.itsys.base.cache.RedisManager;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.constans.RedisConstant;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.*;
import com.yuntongxun.itsys.base.dao.AbnormalEventDao;
import com.yuntongxun.itsys.base.dao.HospitalReferenceRecordDao;
import com.yuntongxun.itsys.base.dao.PersonDao;
import com.yuntongxun.itsys.base.dao.ViolationSchemeDao;
import com.yuntongxun.itsys.base.po.*;
import com.yuntongxun.itsys.base.po.dto.abnormalevent.AbnormalEventDto;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
import com.yuntongxun.itsys.base.service.AbnormalEventService;
import com.yuntongxun.itsys.base.service.ColonoscopyService;
import com.yuntongxun.itsys.base.service.UserService;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
import com.yuntongxun.itsys.base.vo.Response;
import com.yuntongxun.itsys.base.vo.ViolationSchemeVo;
import net.sf.json.JSONArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常事件 serviceimpl
 *
 * @author maxiang
 */
@Service
public class AbnormalEventServiceImpl implements AbnormalEventService {

    final Logger log = LogManager.getLogger(AbnormalEventServiceImpl.class);

    @Autowired
    ViolationSchemeDao violationSchemeDao;

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private UserService userService;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private AbnormalEventDao abnormalEventDao;

    @Autowired
    private ColonoscopyService colonoscopyService;

    @Autowired
    private HospitalReferenceRecordDao hospitalReferenceRecordDao;

    /**
     * 添加或修改异常事件
     *
     * @param body
     */
    @Override
    @Transactional
    public void addOrUpdateAbnormalEvent(String body, String loginName) throws ItSysException {
        /**
         * 传参中dataId值不为空，表示修改，为空则添加
         * 传参中eventType  1违反方案，2不良事件
         */
        ViolationScheme violationScheme = JSONUtils.jsonToBeanDateSerializer(body, ViolationScheme.class, "yyyy-MM-dd");

        //获取登录人信息
        String key = RedisConstant.HOSPITAL_KEY_INFO + loginName;
        String value = redisManager.get(key);
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        if (StringUtil.isBlank(violationScheme.getSid())) {
            log.info("addOrUpdateAbnormalEvent 参数异常 缺少sid");
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }
        if (StringUtil.isNotBlank(violationScheme.getEventType()) && violationScheme.getEventType().equals("1")) {
            /**
             * 新增hospital_violation_scheme违反方案表；
             * 新增hospital_abnormal_event 异常事件表，
             * 修改受试者的违反方案状态
             */
            violationScheme.setDeptCode(doctorInfo.getScreeningType().toString());

            violationScheme.setAreaDeptId(doctorInfo.getAreaDeptId());
            //获取受试者年度阶段
            HospitalReview hospitalReview = personDao.getBySid(violationScheme.getSid());
            violationScheme.setStage(hospitalReview.getStageCy());
            violationScheme.setCommunityDeptId(hospitalReview.getCommunityDeptId());
            //新增违反方案
            Integer id = violationSchemeDao.addViolationScheme(violationScheme);
            //新增异常事件表hospital_abnormal_event
            AbnormalEvent abnormalEvent = new AbnormalEvent();
            abnormalEvent.setSid(violationScheme.getSid());
            abnormalEvent.setStage(hospitalReview.getStageCy());
            abnormalEvent.setEventType(1);
            abnormalEvent.setDataId(id);
            abnormalEvent.setCommunityDeptId(hospitalReview.getCommunityDeptId());
            abnormalEvent.setAreaDeptId(doctorInfo.getAreaDeptId());
            //abnormalEventDao.addAbnormalEvent(abnormalEvent);
            //新增异常事件表hospital_abnormal_event
            //更新受试者的违反方案状态
            String stageCy = "violation_plan_status_t" + (hospitalReview.getStageCy() - 1);
            personDao.updateViolationPlanStatus(1, violationScheme.getSid(), stageCy);
        }
    }

    @Override
    public String queryHospitalViolationSchemeToStringById(ViolationScheme violationScheme) {

        //校验参数是否存在
        if (null == violationScheme.getId() || org.apache.commons.lang.StringUtils.isEmpty(violationScheme.getSid())) {
            log.info("queryHospitalViolationScheme 输入参数id或者sid不存在/错误");
            return JSONUtils.toJson(new Response(GlobalErrorCode.REQUIRED_PARAMETER_ERROR, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
        ViolationScheme hospitalViolationScheme;
        try {
            //获取结果集
            hospitalViolationScheme = violationSchemeDao.queryHospitalViolationSchemeById(violationScheme.getId(), violationScheme.getSid());
            //判断结果集是否存在
            if (hospitalViolationScheme == null) {
                log.info("queryHospitalViolationScheme 根据id查询结果集为空");
                return JSONUtils.toJson(new Response(GlobalErrorCode.OBJECT_NOT_EXISTS, GlobalErrorCode.OBJECT_NOT_EXISTS_MSG));
            }
        } catch (Exception e) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.UNKNOWN_ERROR, GlobalErrorCode.UNKNOWN_ERROR_MSG));
        }

        //数据梳理
        ViolationSchemeVo violationSchemeVo = new ViolationSchemeVo();
        BeanUtils.copyProperties(hospitalViolationScheme, violationSchemeVo);
        violationSchemeVo.setItem_2a_1(hospitalViolationScheme.getItem_2a_1() == null ? "" : DateUtil.dateToStr(hospitalViolationScheme.getItem_2a_1(), DateUtil.YMR_SLASH));
        violationSchemeVo.setTbDate(hospitalViolationScheme.getTbDate() == null ? "" : DateUtil.dateToStr(hospitalViolationScheme.getTbDate(), DateUtil.YMR_SLASH));
        violationSchemeVo.setItem_2b_1(hospitalViolationScheme.getItem_2b_1() == null ? "" : DateUtil.dateToStr(hospitalViolationScheme.getItem_2b_1(), DateUtil.YMR_SLASH));
        violationSchemeVo.setItem_3a_2_time(hospitalViolationScheme.getItem_3a_2_time() == null ? "" : DateUtil.dateToStr(hospitalViolationScheme.getItem_3a_2_time(), DateUtil.YMR_SLASH));
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, violationSchemeVo, null));
    }


    @Override
    public ListPageUtil queryAbnormalEventByUser(AbnormalEventDto abnormalEventDto, ItsysDepartment itsysDepartment, Integer depHospitalType1) {
        log.info("@Service-abnormalEventDao-queryAbnormalEventByUser 社区医院查看全部异常事件  参数：abnormalEventDto={}.", abnormalEventDto);
        return abnormalEventDao.queryAbnormalEventByUser(abnormalEventDto, itsysDepartment, depHospitalType1);
    }

    /**
     * 点击确定后，第一次录入违反方案值
     *
     * @param violationScheme
     * @param loginName
     */
    @Override
    @Transactional
    public Object addVScheme(ViolationScheme violationScheme, String loginName) {
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        Map<String, Object> map = new HashMap<String, Object>();
        ViolationScheme violationScheme1 = new ViolationScheme();
        violationScheme.setAreaDeptId(doctorInfo.getAreaDeptId());
        //获取受试者年度阶段
        HospitalReview hospitalReview = personDao.getBySid(violationScheme.getSid());
        violationScheme1.setSid(violationScheme.getSid());
        violationScheme1.setStage(hospitalReview.getStageCy());
        violationScheme1.setCommunityDeptId(hospitalReview.getCommunityDeptId());
        violationScheme1.setAreaDeptId(hospitalReview.getAreaDeptId());
//            violationScheme1.setQuitLogId(violationScheme.getQuitLogId());
        violationScheme1.setSchemeType(2);
        violationScheme1.setEntryStatus(2);
        Integer id = violationSchemeDao.addVScheme(violationScheme1);
        //更新受试者的违反方案状态
        String stageCy = "violation_plan_status_t" + (hospitalReview.getStageCy() - 1);
        personDao.updateViolationPlanStatus(1, violationScheme.getSid(), stageCy);
        map.put("id", id);


        return map;
//        }
    }

    /**
     * 点击提交后，补充填充违反方案值
     *
     * @param violationScheme
     * @param loginName
     */
    @Override
    @Transactional
    public void updateVScheme(ViolationScheme violationScheme, String loginName,HospitalReferenceRecordDto hospitalReferenceRecordDto) {
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        ViolationScheme v = new ViolationScheme();
        v.setTbDate(violationScheme.getTbDate());
        v.setTbrName(violationScheme.getTbrName());
        v.setZkzName(violationScheme.getZkzName());
        v.setDeptCode(violationScheme.getDeptCode());
        v.setInvestigatorCode(violationScheme.getInvestigatorCode());
        v.setCheckYear(violationScheme.getCheckYear());
        v.setItem_1a_1(violationScheme.getItem_1a_1());
        v.setItem_1a_2(violationScheme.getItem_1a_2());
        v.setItem_1a_2_id(violationScheme.getItem_1a_2_id());
        v.setItem_1a_3(violationScheme.getItem_1a_3());
        v.setItem_1a_4(violationScheme.getItem_1a_4());
        v.setItem_1a_5(violationScheme.getItem_1a_5());
        v.setItem_1a_5_des(violationScheme.getItem_1a_5_des());
        v.setItem_1a_6(violationScheme.getItem_1a_6());
        v.setItem_1a_7(violationScheme.getItem_1a_7());
        v.setItem_1a_8(violationScheme.getItem_1a_8());
        v.setItem_1a_9(violationScheme.getItem_1a_9());
        v.setItem_1a_9_cause(violationScheme.getItem_1a_9_cause());
        v.setItem_1a_9_des(violationScheme.getItem_1a_9_des());
        v.setItem_1a_10(violationScheme.getItem_1a_10());
        v.setItem_1a_10_other(violationScheme.getItem_1a_10_other());
        v.setItem_2a_1(violationScheme.getItem_2a_1());
        v.setItem_2b_1(violationScheme.getItem_2b_1());
        v.setItem_3a_1(violationScheme.getItem_3a_1());
        v.setItem_3a_2(violationScheme.getItem_3a_2());
        v.setItem_3a_2_time(violationScheme.getItem_3a_2_time());
        v.setItem_3a_2_estimate(violationScheme.getItem_3a_2_estimate());
        v.setItem_3a_3(violationScheme.getItem_3a_3());
        v.setItem_3a_4(violationScheme.getItem_3a_4());
        v.setItem_3a_5(violationScheme.getItem_3a_5());
        v.setItem_3a_6(violationScheme.getItem_3a_6());
        v.setItem_3a_7(violationScheme.getItem_3a_7());
        v.setItem_3a_8(violationScheme.getItem_3a_8());
        v.setItem_3a_9(violationScheme.getItem_3a_9());
        v.setItem_3a_10(violationScheme.getItem_3a_10());
        v.setItem_3a_10_cause(violationScheme.getItem_3a_10_cause());
        v.setItem_4a_1(violationScheme.getItem_4a_1());
        v.setCommunityDeptId(doctorInfo.getCommunityDeptId());
        v.setAreaDeptId(doctorInfo.getAreaDeptId());
        v.setId(violationScheme.getId());
        if(hospitalReferenceRecordDto!=null){
            //添加编辑记录
            try{
                hospitalReferenceRecordDao.updateForEdir(hospitalReferenceRecordDto);
                hospitalReferenceRecordDao.updateForEdirStatus(Constans.APPLY_EDIT_STATUS1,Constans.EDIT_STATUS1,v.getId());
            }catch (Exception e){
                throw new ItSysException(
                        GlobalErrorCode.HOSPITAL_MESSAGE_CENTER_UPDATE_ERROR_CODE, GlobalErrorCode.HOSPITAL_MESSAGE_CENTER_UPDATE_ERROR_MSG);
            }
        }
        violationSchemeDao.updateViolationScheme(v);
    }

    /**
     * 获取当前系统时间
     *
     * @return
     */
    @Override
    public Object getTime() {
        SimpleDateFormat aDate = new SimpleDateFormat("yyyy-MM-dd");
        String time = aDate.format(new Date());
        return time;
    }

    @Override
    public ViolationScheme queryHospitalViolationSchemeById(Integer id, String sid) {
        return violationSchemeDao.queryHospitalViolationSchemeById(id, sid);
    }

    @Override
    public void updateVSchemeInArea(ViolationScheme violationScheme, Integer communityDeptId, Integer areaDeptId, HospitalReferenceRecordDto hospitalReferenceRecordDto) {
        ViolationScheme v = new ViolationScheme();
        v.setTbDate(violationScheme.getTbDate());
        v.setTbrName(violationScheme.getTbrName());
        v.setZkzName(violationScheme.getZkzName());
        v.setDeptCode(violationScheme.getDeptCode());
        v.setInvestigatorCode(violationScheme.getInvestigatorCode());
        v.setCheckYear(violationScheme.getCheckYear());
        v.setItem_1a_1(violationScheme.getItem_1a_1());
        v.setItem_1a_2(violationScheme.getItem_1a_2());
        v.setItem_1a_2_id(violationScheme.getItem_1a_2_id());
        v.setItem_1a_3(violationScheme.getItem_1a_3());
        v.setItem_1a_4(violationScheme.getItem_1a_4());
        v.setItem_1a_5(violationScheme.getItem_1a_5());
        v.setItem_1a_5_des(violationScheme.getItem_1a_5_des());
        v.setItem_1a_6(violationScheme.getItem_1a_6());
        v.setItem_1a_7(violationScheme.getItem_1a_7());
        v.setItem_1a_8(violationScheme.getItem_1a_8());
        v.setItem_1a_9(violationScheme.getItem_1a_9());
        v.setItem_1a_9_cause(violationScheme.getItem_1a_9_cause());
        v.setItem_1a_9_des(violationScheme.getItem_1a_9_des());
        v.setItem_1a_10(violationScheme.getItem_1a_10());
        v.setItem_1a_10_other(violationScheme.getItem_1a_10_other());
        v.setItem_2a_1(violationScheme.getItem_2a_1());
        v.setItem_2b_1(violationScheme.getItem_2b_1());
        v.setItem_3a_1(violationScheme.getItem_3a_1());
        v.setItem_3a_2(violationScheme.getItem_3a_2());
        v.setItem_3a_2_time(violationScheme.getItem_3a_2_time());
        v.setItem_3a_2_estimate(violationScheme.getItem_3a_2_estimate());
        v.setItem_3a_3(violationScheme.getItem_3a_3());
        v.setItem_3a_4(violationScheme.getItem_3a_4());
        v.setItem_3a_5(violationScheme.getItem_3a_5());
        v.setItem_3a_6(violationScheme.getItem_3a_6());
        v.setItem_3a_7(violationScheme.getItem_3a_7());
        v.setItem_3a_8(violationScheme.getItem_3a_8());
        v.setItem_3a_9(violationScheme.getItem_3a_9());
        v.setItem_3a_10(violationScheme.getItem_3a_10());
        v.setItem_3a_10_cause(violationScheme.getItem_3a_10_cause());
        v.setItem_4a_1(violationScheme.getItem_4a_1());
        v.setCommunityDeptId(communityDeptId);
        v.setAreaDeptId(areaDeptId);
        v.setId(violationScheme.getId());
        if(hospitalReferenceRecordDto!=null){
            //添加编辑记录
            try{
                hospitalReferenceRecordDao.updateForEdir(hospitalReferenceRecordDto);
                hospitalReferenceRecordDao.updateForEdirStatus(Constans.APPLY_EDIT_STATUS1,Constans.EDIT_STATUS1,v.getId());
            }catch (Exception e){
                throw new ItSysException(
                        GlobalErrorCode.HOSPITAL_MESSAGE_CENTER_UPDATE_ERROR_CODE, GlobalErrorCode.HOSPITAL_MESSAGE_CENTER_UPDATE_ERROR_MSG);
            }
        }
        violationSchemeDao.updateViolationScheme(v);
    }
}
