package com.yuntongxun.itsys.base.controller;

import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.*;
import com.yuntongxun.itsys.base.po.*;
import com.yuntongxun.itsys.base.po.dto.HospitalRiskFactorDto;
import com.yuntongxun.itsys.base.po.dto.ItsysUserDto;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalCourierResultDto;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
import com.yuntongxun.itsys.base.po.dto.message.HospitalMessageCenterDto;
import com.yuntongxun.itsys.base.service.*;
import com.yuntongxun.itsys.base.vo.FitResultVo;
import com.yuntongxun.itsys.base.vo.HospitalBiologicalSampleResultVo;
import com.yuntongxun.itsys.base.vo.Response;
import com.yuntongxun.itsys.base.vo.Result;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 异常事件
 *
 * @author maxiang
 */
@RestController
public class HospitalMessageCenterController extends AbstractController {

    private final Logger log = LogManager.getLogger(HospitalMessageCenterController.class);


    @Autowired
    private ColonoscopyService colonoscopyService;

    @Autowired
    private HospitalCourierResultService hospitalCourierResultService;


    @Autowired
    private HospitalMessageCenterService hospitalMessageCenterService;


    @Autowired
    private HospitalReferenceRecordService hospitalReferenceRecordService;

    @Autowired
    private BiologSampleService biologSampleService;

    @Autowired
    private PersonService personService;

    @Autowired
    private AbnormalEventService abnormalEventService;
    
    @Autowired
    private StoolDnaService stoolDnaService;

    @Autowired
    private FitService fitService;

    @Autowired
    private ScreeningNotificationService screeningNotificationService;

    @Autowired
    private ColonoscopyResultService colonoscopyResultService;
    @Autowired
    private ColonoscopyPathologyResultService colonoscopyPathologyResultService;



    /*
     * 点击申请编辑模块
     *
     * @author ${zongt}
     * @since v1.0.0
     */
    @RequestMapping(value = "/area/courier/result/sendUpdateResult", method = RequestMethod.POST)
    public String sendUpdateResult(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalReferenceRecordDto hospitalReferenceRecordDto) throws ItSysException {
        //log.info("接受提交快递信息" + JSONUtils.toJson(hospitalReferenceRecordDto));

        //校验参数
        if (hospitalReferenceRecordDto.getId() == null || StringUtils.isEmpty(hospitalReferenceRecordDto.getFormType())) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
        String loginName = CookieUtil.getCookieByLoginName(req);
        List<ItsysUserDto> itsysUserDtos = colonoscopyService.queryloginNamesByloginName(loginName);

        String meaasge_typpe = "";
        String text = "";
        String meaasge_text_typpe = "";
        String courierNumber = "";
        String sid = "";
        String applyStatus = "";
        Integer id = null;
        String sample_type="";
        Integer main_id = null;//只用于肠镜表单编辑，用了存储肠镜检查表的id
        String remark=hospitalReferenceRecordDto.getRemark();
        //判断是快递模块
        if (Constans.HOSPITAL_COURIER_RESULT.equals(hospitalReferenceRecordDto.getFormType())) {
            List<HospitalCourierResultDto> hospitalCourierResultDtos = hospitalCourierResultService.queryById(hospitalReferenceRecordDto.getId());
            if (hospitalCourierResultDtos.size() != 1) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
            }
            if (!StringUtils.isEmpty(hospitalCourierResultDtos.get(0).getAcceptPerson())) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_UPDATE_HOSPITAL_COURIER_RESULT_IS_HAVE_ERROR_CODE, GlobalErrorCode.ERR_UPDATE_HOSPITAL_COURIER_RESULT_IS_HAVE_ERROR_MSG));
            }
            meaasge_typpe = Constans.meaasge_typpe3;
            meaasge_text_typpe = Constans.meaasge_text_typpe4;
            courierNumber = hospitalCourierResultDtos.get(0).getCourierNumber();
            applyStatus = hospitalCourierResultDtos.get(0).getApplyStatus();
            id = hospitalCourierResultDtos.get(0).getId();
        }

        //判断是生物模块
        if (Constans.HOSPITAL_BIOLOGICAL_SAMPLE_RESULT.equals(hospitalReferenceRecordDto.getFormType())) {
            List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVos = biologSampleService.queryById(hospitalReferenceRecordDto.getId(), null);
            if (hospitalBiologicalSampleResultVos.size() != 1) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
            }
            //判断原来的数据是否采集
            if (Constans.COLLECT_STATUS_PROVIDE.equals(hospitalBiologicalSampleResultVos.get(0).getCollectStatus())) {
                //校验原来的冷冻盒是否寄出
                //判断样本类型
                if (Constans.SAMPLE_TYPE6.equals(hospitalBiologicalSampleResultVos.get(0).getSampleType())) {
                    //根据samplyID查询冷冻盒是否已经寄出
                    List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVos1 = biologSampleService.queryByBloodId(hospitalBiologicalSampleResultVos.get(0).getId());
                    for (HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo : hospitalBiologicalSampleResultVos1) {
                        if(hospitalBiologicalSampleResultVo.getCourierStatus()!=null){
                            if (!Constans.COURIER_STATUS_CODE2.equals(hospitalBiologicalSampleResultVo.getCourierStatus())) {
                                return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_STOOL_FROZEN_BOX_CODE_ISHAVE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_STOOL_FROZEN_BOX_CODE_ISHAVE__ERROR_MSG));
                            }
                        }
                    }
                } else {
                    List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVos1 = biologSampleService.queryById(hospitalBiologicalSampleResultVos.get(0).getId(), null);
                    if (hospitalBiologicalSampleResultVos1.size() != 1) {
                        return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_STOOL_FROZEN_BOX_CODE_ISHAVE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_STOOL_FROZEN_BOX_CODE_ISHAVE__ERROR_MSG));
                    }
                    if (!Constans.COURIER_STATUS_CODE2.equals(hospitalBiologicalSampleResultVos1.get(0).getCourierStatus())) {
                        return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_STOOL_FROZEN_BOX_CODE_ISHAVE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_STOOL_FROZEN_BOX_CODE_ISHAVE__ERROR_MSG));
                    }
                }
            }
            meaasge_typpe = Constans.meaasge_typpe3;
            meaasge_text_typpe = Constans.meaasge_text_typpe5;
            applyStatus = hospitalBiologicalSampleResultVos.get(0).getApplyStatus();
            id = hospitalBiologicalSampleResultVos.get(0).getId();
            String sampleType = hospitalBiologicalSampleResultVos.get(0).getSampleType();
            sid = hospitalBiologicalSampleResultVos.get(0).getSid();
            if(Constans.SAMPLE_TYPE6.equals(sampleType)){
                text = "血液样本管理" + hospitalBiologicalSampleResultVos.get(0).getSid();
                sample_type=Constans.SAMPLE_TYPE6;
            }else if(Constans.SAMPLE_TYPE4.equals(sampleType)){
                text = "唾液样本管理" + hospitalBiologicalSampleResultVos.get(0).getSid();
                sample_type=Constans.SAMPLE_TYPE4;
            }else if(Constans.SAMPLE_TYPE5.equals(sampleType)){
                text = "粪便管理" + hospitalBiologicalSampleResultVos.get(0).getSid();
                sample_type=Constans.SAMPLE_TYPE5;
            }
        }

        //判断是Ai资格审核表
        if (Constans.HOSPITAL_INTESTINE_REVIEW.equals(hospitalReferenceRecordDto.getFormType())) {

            HospitalReview bySid = personService.getBySid(hospitalReferenceRecordDto.getSid());
            if (bySid == null) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
            }

            if(Constans.PERSON_OVERALL_STATUS2.equals(bySid.getOverallStatusCy())){
                return JSONUtils.toJson(new Response(GlobalErrorCode.PERSON_OVERALL_STATUS2_ERROR_CODE, GlobalErrorCode.PERSON_OVERALL_STATUS2_ERROR_MSG));
            }
            meaasge_typpe = Constans.meaasge_typpe3;
            meaasge_text_typpe = Constans.meaasge_text_typpe5;
            courierNumber ="";
            applyStatus = bySid.getApplyStatus();
            id = bySid.getId();
            text = "受试者管理--资格审核表" + bySid.getSid();
            sid = bySid.getSid();
        }

        //判断是否A2危险因素表
        if(Constans.HOSPITAL_INTESTINE_RISK_FACTOR.equals(hospitalReferenceRecordDto.getFormType())){
            //根据id查询结果
            List<HospitalRiskFactorDto> hospitalRiskFactors =personService.findRiskfactorById(hospitalReferenceRecordDto.getId());
            if (hospitalRiskFactors.size() != 1) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
            }
            meaasge_typpe = Constans.meaasge_typpe3;
            meaasge_text_typpe = Constans.meaasge_text_typpe5;
            courierNumber ="";
            applyStatus = hospitalRiskFactors.get(0).getApplyStatus();
            id = hospitalRiskFactors.get(0).getId();
            text = "受试者管理-危险因素表单" + hospitalRiskFactors.get(0).getSid();
            sid = hospitalRiskFactors.get(0).getSid();
        }


        //判断是D2表单
        if(Constans.HOSPITAL_VIOLATION_SCHEME.equals(hospitalReferenceRecordDto.getFormType())){
            ViolationScheme violationScheme = abnormalEventService.queryHospitalViolationSchemeById(hospitalReferenceRecordDto.getId(), hospitalReferenceRecordDto.getSid());
            if(violationScheme==null){
                return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
            }
            meaasge_typpe = Constans.meaasge_typpe3;
            meaasge_text_typpe = Constans.meaasge_text_typpe5;
            courierNumber ="";
            applyStatus = violationScheme.getApplyStatus();
            id = violationScheme.getId();
            text = "异常事件管理-违反方案事件表单" + violationScheme.getSid();
            sid =violationScheme.getSid();
        }

        //判断粪便DNA
        if(Constans.HOSPITAL_STOOL_DNA.equals(hospitalReferenceRecordDto.getFormType())){
            List<StoolDna> stoolDnas = stoolDnaService.queryById(hospitalReferenceRecordDto.getId());
            if(stoolDnas.size()!=1){
                return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
            }
            if(stoolDnas.get(0).getDna_check_result()!=null){
                return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_STOOL_DNA_RESULT_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_STOOL_DNA_RESULT_ERROR_MSG));
            }
            meaasge_typpe = Constans.meaasge_typpe3;
            meaasge_text_typpe = Constans.meaasge_text_typpe5;
            courierNumber ="";
            applyStatus = stoolDnas.get(0).getApplyStatus();
            id = stoolDnas.get(0).getId();
            text = "粪便DNA管理" + stoolDnas.get(0).getSid();
            sid =stoolDnas.get(0).getSid();
        }


        //判断fit编码
        if(Constans.HOSPITAL_FIT_RESULT.equals(hospitalReferenceRecordDto.getFormType())){
            FitResultVo fitResultVo = fitService.queryById(hospitalReferenceRecordDto.getId());
            if(fitResultVo==null){
                return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
            }

            meaasge_typpe = Constans.meaasge_typpe3;
            meaasge_text_typpe = Constans.meaasge_text_typpe5;
            courierNumber ="";
            applyStatus = fitResultVo.getApplyStatus();
            id = fitResultVo.getId();
            text = "fit管理" + fitResultVo.getSid();
            sid =fitResultVo.getSid();
        }

        String module = "";
        //判断告知书结果
        if(Constans.HOSPITAL_SCREENING_NOTIFICATION.equals(hospitalReferenceRecordDto.getFormType())){
            HospitalReview bySid = personService.getBySid(hospitalReferenceRecordDto.getSid());
            if (bySid == null) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
            }
            if(Constans.PERSON_OVERALL_STATUS2.equals(bySid.getOverallStatusCy())){
                return JSONUtils.toJson(new Response(GlobalErrorCode.PERSON_OVERALL_STATUS2_ERROR_CODE, GlobalErrorCode.PERSON_OVERALL_STATUS2_ERROR_MSG));
            }
            module = "cj";
            List<ScreeningNotification> screeningNotification =screeningNotificationService.queryById(hospitalReferenceRecordDto.getId());
            HospitalColonoscopyRecord record =colonoscopyService.findRecordByNotificationId(hospitalReferenceRecordDto.getId());
            if(screeningNotification.size()!=1||record == null ||record.getId()==null){
                return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
            }

            meaasge_typpe = Constans.meaasge_typpe3;
            meaasge_text_typpe = Constans.meaasge_text_typpe5;
            courierNumber ="";
            applyStatus = screeningNotification.get(0).getApplyStatus();
            id = screeningNotification.get(0).getId();
            text = "肠镜管理-告知书表单修改" + screeningNotification.get(0).getSid();
            sid =screeningNotification.get(0).getSid();
            main_id = record.getId();
        }
        //判断肠镜检查结果
        if(Constans.HOSPITAL_COLONOSCOPY_RESULT.equals(hospitalReferenceRecordDto.getFormType())){
            HospitalReview bySid = personService.getBySid(hospitalReferenceRecordDto.getSid());
            if (bySid == null) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
            }
            if(Constans.PERSON_OVERALL_STATUS2.equals(bySid.getOverallStatusCy())){
                return JSONUtils.toJson(new Response(GlobalErrorCode.PERSON_OVERALL_STATUS2_ERROR_CODE, GlobalErrorCode.PERSON_OVERALL_STATUS2_ERROR_MSG));
            }
            module = "cj";
           List<ColonoscopyResult> colonoscopyResults= colonoscopyResultService.queryById(hospitalReferenceRecordDto.getId());
            HospitalColonoscopyRecord record =colonoscopyService.findRecordByResultId(hospitalReferenceRecordDto.getId());
            if(colonoscopyResults.size()!=1||record == null ||record.getId()==null){
                return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
            }

            meaasge_typpe = Constans.meaasge_typpe3;
            meaasge_text_typpe = Constans.meaasge_text_typpe5;
            courierNumber ="";
            applyStatus = colonoscopyResults.get(0).getApplyStatus();
            id = colonoscopyResults.get(0).getId();
            text = "肠镜管理-肠镜检查结果表单修改" + colonoscopyResults.get(0).getSid();
            sid =colonoscopyResults.get(0).getSid();
            main_id = record.getId();
        }
        //判断病理结果
        if(Constans.HOSPITAL_COLONOSCOPY_PATHOLOGY_RESULT.equals(hospitalReferenceRecordDto.getFormType())){

            HospitalReview bySid = personService.getBySid(hospitalReferenceRecordDto.getSid());
            if (bySid == null) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
            }

            if(Constans.PERSON_OVERALL_STATUS2.equals(bySid.getOverallStatusCy())){
                return JSONUtils.toJson(new Response(GlobalErrorCode.PERSON_OVERALL_STATUS2_ERROR_CODE, GlobalErrorCode.PERSON_OVERALL_STATUS2_ERROR_MSG));
            }
            module = "cj";
            ColonoscopyPathologyResult colonoscopyPathologyResult = colonoscopyPathologyResultService.queryByID(hospitalReferenceRecordDto.getId(),hospitalReferenceRecordDto.getSid());
            HospitalColonoscopyRecord record =colonoscopyService.findRecordByPathologyId(hospitalReferenceRecordDto.getId());
            if(colonoscopyPathologyResult==null || record == null ||record.getId()==null){
                return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
            }
            meaasge_typpe = Constans.meaasge_typpe3;
            meaasge_text_typpe = Constans.meaasge_text_typpe5;
            courierNumber ="";
            applyStatus = colonoscopyPathologyResult.getApplyStatus();
            id = colonoscopyPathologyResult.getId();
            text = "肠镜管理-病理检查结果表单修改" + colonoscopyPathologyResult.getSid();
            sid =colonoscopyPathologyResult.getSid();
            main_id = record.getId();
        }

        //根据id查看是否可以点击编辑
        if (!Constans.APPLY_EDIT_STATUS1.equals(applyStatus)) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_EDIT_CODE, GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_EDIT_MSG));
        }
        HospitalCourierResultDto hospitalCourierResultDto = new HospitalCourierResultDto();
        hospitalCourierResultDto.setId(id);
        hospitalCourierResultDto.setApplyStatus(Constans.APPLY_EDIT_STATUS2);
        hospitalCourierResultDto.setEditStatus(Constans.EDIT_STATUS1);
        hospitalCourierResultDto.setApprovalStatus(Constans.APPROVAL_STATUS1);
        //添加消息中心
        List<HospitalMessageCenterDto> hospitalMessageCenterDtoList = new ArrayList<>();
        for (ItsysUserDto itsysUserDto : itsysUserDtos) {
            HospitalMessageCenterDto hospitalMessageCenterDto = new HospitalMessageCenterDto();
            hospitalMessageCenterDto.setSendUser(loginName);
            String accpetName = itsysUserDto.getLoginname();
            hospitalMessageCenterDto.setAcceptUser(accpetName);
            hospitalMessageCenterDto.setMessageType(meaasge_typpe);
            hospitalMessageCenterDto.setData_id(id);
            hospitalMessageCenterDto.setSid(sid);
            hospitalMessageCenterDto.setForm_type(hospitalReferenceRecordDto.getFormType());
            hospitalMessageCenterDto.setSample_type(sample_type);
            hospitalMessageCenterDto.setCourierNumber(courierNumber);
            hospitalMessageCenterDto.setMainDataId(main_id);
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
            String message = SendMessageCenter.getMessage(loginName, accpetName, meaasge_typpe, text, sid, meaasge_text_typpe, courierNumber,remark);
            hospitalMessageCenterDto.setMessageText(message);
            hospitalMessageCenterDto.setId(UUID.randomUUID().toString().replace("-", ""));
            hospitalMessageCenterDtoList.add(hospitalMessageCenterDto);
        }

        //添加编辑记录表
        HospitalReferenceRecordDto hospitalReferenceRecordDtos = new HospitalReferenceRecordDto();
        hospitalReferenceRecordDtos.setDataId(id);
        hospitalReferenceRecordDtos.setFormType(hospitalReferenceRecordDto.getFormType());
        hospitalReferenceRecordDtos.setRemark(hospitalReferenceRecordDto.getRemark());
        hospitalReferenceRecordDtos.setApplyStatus(Constans.APPLY_EDIT_STATUS1);
        hospitalReferenceRecordDtos.setSendPerson(loginName);
        hospitalReferenceRecordDtos.setMainDataId(main_id);
        try {
            hospitalMessageCenterService.save(hospitalMessageCenterDtoList, hospitalReferenceRecordDtos, Constans.APPLY_EDIT_STATUS2, Constans.EDIT_STATUS1, Constans.APPROVAL_STATUS1, id.toString(), hospitalReferenceRecordDto.getFormType(),true,module);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_SAVE_HOSPITAL_COURIER_RESULT_ERROR_CODE, GlobalErrorCode.ERR_SAVE_HOSPITAL_COURIER_RESULT_ERROR_MSG));
        }
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, null, null));
    }

    /*
     * 国家审核通过快递编辑
     *
     * @author ${zongt}
     * @since v1.0.0
     */
    @RequestMapping(value = "/area/courier/result/accpetUpdateCourierResult", method = RequestMethod.POST)
    public String accpetUpdateCourierResult(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalReferenceRecordDto hospitalReferenceRecordDtodata) throws ItSysException {
        // log.info("接受提交快递信息" + JSONUtils.toJson(hospitalReferenceRecordDtodata));

        //校验参数
        if (hospitalReferenceRecordDtodata.getId() == null || StringUtils.isEmpty(hospitalReferenceRecordDtodata.getApprovalStatus()) || StringUtils.isEmpty(hospitalReferenceRecordDtodata.getFormType())) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
        String loginName = CookieUtil.getCookieByLoginName(req);
        String meaasge_typpe = "";
        String text = "";
        String meaasge_text_typpe = "";
        String courierNumber = "";
        String sid = "";
        String applyStatus = "";
        Integer id = null;
        String sample_type="";
        String remark=hospitalReferenceRecordDtodata.getRemark();

        Integer main_id = null;//只用于肠镜表单编辑，用了存储肠镜检查表的id

        //判断是快递模块
        if (Constans.HOSPITAL_COURIER_RESULT.equals(hospitalReferenceRecordDtodata.getFormType())) {
            //根据id查询结果
            List<HospitalCourierResultDto> hospitalCourierResultDtoList = hospitalCourierResultService.queryById(hospitalReferenceRecordDtodata.getId());

            if (hospitalCourierResultDtoList.size() != 1) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
            }
            if (!Constans.APPROVAL_STATUS1.equals(hospitalCourierResultDtoList.get(0).getApprovalStatus())) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_APPROVAL_STATUS_IS_ERROR_CODE, GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_APPROVAL_STATUS_IS_ERROR_MSG));
            }
            id = hospitalCourierResultDtoList.get(0).getId();
            meaasge_typpe = Constans.meaasge_typpe4;
            meaasge_text_typpe = Constans.meaasge_text_typpe4;
            courierNumber = hospitalCourierResultDtoList.get(0).getCourierNumber();
            applyStatus = hospitalCourierResultDtoList.get(0).getApplyStatus();
        }
        //判断是否是生物样本模块
        if (Constans.HOSPITAL_BIOLOGICAL_SAMPLE_RESULT.equals(hospitalReferenceRecordDtodata.getFormType())) {
            //根据id查询结果
            List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVos = biologSampleService.queryById(hospitalReferenceRecordDtodata.getId(), null);

            if (hospitalBiologicalSampleResultVos.size() != 1) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
            }
            if (!Constans.APPROVAL_STATUS1.equals(hospitalBiologicalSampleResultVos.get(0).getApprovalStatus())) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_APPROVAL_STATUS_IS_ERROR_CODE, GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_APPROVAL_STATUS_IS_ERROR_MSG));
            }
            id = hospitalBiologicalSampleResultVos.get(0).getId();
            sid=hospitalBiologicalSampleResultVos.get(0).getSid();
            meaasge_typpe = Constans.meaasge_typpe4;
            meaasge_text_typpe = Constans.meaasge_text_typpe5;
            String sampleType = hospitalBiologicalSampleResultVos.get(0).getSampleType();
            if(Constans.SAMPLE_TYPE6.equals(sampleType)){
                text = "血液样本管理" + hospitalBiologicalSampleResultVos.get(0).getSid();
                sample_type=Constans.SAMPLE_TYPE6;
            }else if(Constans.SAMPLE_TYPE4.equals(sampleType)){
                text = "唾液样本管理" + hospitalBiologicalSampleResultVos.get(0).getSid();
                sample_type=Constans.SAMPLE_TYPE4;
            }else if(Constans.SAMPLE_TYPE5.equals(sampleType)){
                text = "粪便管理" + hospitalBiologicalSampleResultVos.get(0).getSid();
                sample_type=Constans.SAMPLE_TYPE5;

            }
        }

        //判断是Ai资格审核表
        if (Constans.HOSPITAL_INTESTINE_REVIEW.equals(hospitalReferenceRecordDtodata.getFormType())) {
            HospitalReview bySid = personService.getBySid(hospitalReferenceRecordDtodata.getSid());
            if (bySid == null) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
            }

            if (!Constans.APPROVAL_STATUS1.equals(bySid.getApprovalStatus())) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_APPROVAL_STATUS_IS_ERROR_CODE, GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_APPROVAL_STATUS_IS_ERROR_MSG));
            }
            id = bySid.getId();
            meaasge_typpe = Constans.meaasge_typpe4;
            meaasge_text_typpe = Constans.meaasge_text_typpe5;
            text = "受试者管理" + bySid.getSid();
            sid=bySid.getSid();
        }

        //判断是否A2危险因素表
        if(Constans.HOSPITAL_INTESTINE_RISK_FACTOR.equals(hospitalReferenceRecordDtodata.getFormType())){
            //根据id查询结果

            List<HospitalRiskFactorDto> riskfactorById = personService.findRiskfactorById(hospitalReferenceRecordDtodata.getId());

            if (riskfactorById.size() != 1) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
            }
            if (!Constans.APPROVAL_STATUS1.equals(riskfactorById.get(0).getApprovalStatus())) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_APPROVAL_STATUS_IS_ERROR_CODE, GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_APPROVAL_STATUS_IS_ERROR_MSG));
            }

            meaasge_typpe = Constans.meaasge_typpe4;
            meaasge_text_typpe = Constans.meaasge_text_typpe5;
            courierNumber ="";
            id = riskfactorById.get(0).getId();
            text = "受试者管理-危险因素表单" + riskfactorById.get(0).getSid();
            sid=riskfactorById.get(0).getSid();
        }


        //判断是否D2违反方案事件
        if(Constans.HOSPITAL_VIOLATION_SCHEME.equals(hospitalReferenceRecordDtodata.getFormType())){
            //根据id查询结果

            ViolationScheme violationScheme = abnormalEventService.queryHospitalViolationSchemeById(hospitalReferenceRecordDtodata.getId(), hospitalReferenceRecordDtodata.getSid());

            if (violationScheme == null) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
            }
            if (!Constans.APPROVAL_STATUS1.equals(violationScheme.getApprovalStatus())) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_APPROVAL_STATUS_IS_ERROR_CODE, GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_APPROVAL_STATUS_IS_ERROR_MSG));
            }

            meaasge_typpe = Constans.meaasge_typpe4;
            meaasge_text_typpe = Constans.meaasge_text_typpe5;
            courierNumber ="";
            id = violationScheme.getId();
            text = "异常事件管理-违反方案事件表单" + violationScheme.getSid();
            sid=violationScheme.getSid();
        }

        //判断DNA粪便
        if(Constans.HOSPITAL_STOOL_DNA.equals(hospitalReferenceRecordDtodata.getFormType())){

            //根据id查询结果
            List<StoolDna> stoolDnas = stoolDnaService.queryById(hospitalReferenceRecordDtodata.getId());


            if (stoolDnas.size() != 1) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
            }
            if (!Constans.APPROVAL_STATUS1.equals(stoolDnas.get(0).getApprovalStatus())) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_APPROVAL_STATUS_IS_ERROR_CODE, GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_APPROVAL_STATUS_IS_ERROR_MSG));
            }

            meaasge_typpe = Constans.meaasge_typpe4;
            meaasge_text_typpe = Constans.meaasge_text_typpe5;
            courierNumber ="";
            id = stoolDnas.get(0).getId();
            text = "粪便DNA管理" + stoolDnas.get(0).getSid();
            sid=stoolDnas.get(0).getSid();

        }

        //判断fir结果
        if(Constans.HOSPITAL_FIT_RESULT.equals(hospitalReferenceRecordDtodata.getFormType())){

            FitResultVo fitResultVo = fitService.queryById(hospitalReferenceRecordDtodata.getId());
            if(fitResultVo==null){
                return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
            }

            if (!Constans.APPROVAL_STATUS1.equals(fitResultVo.getApprovalStatus())) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_APPROVAL_STATUS_IS_ERROR_CODE, GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_APPROVAL_STATUS_IS_ERROR_MSG));
            }

            meaasge_typpe = Constans.meaasge_typpe4;
            meaasge_text_typpe = Constans.meaasge_text_typpe5;
            courierNumber ="";
            applyStatus = fitResultVo.getApplyStatus();
            id = fitResultVo.getId();
            text = "fit管理" + fitResultVo.getSid();
            sid=fitResultVo.getSid();
        }

        //判断告知书结果
        if(Constans.HOSPITAL_SCREENING_NOTIFICATION.equals(hospitalReferenceRecordDtodata.getFormType())){
            List<ScreeningNotification> screeningNotification =screeningNotificationService.queryById(hospitalReferenceRecordDtodata.getId());
            HospitalColonoscopyRecord record =colonoscopyService.findRecordByNotificationId(hospitalReferenceRecordDtodata.getId());
            if(screeningNotification.size()!=1 ||record == null ||record.getId() ==null){
                return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
            }

            if (!Constans.APPROVAL_STATUS1.equals(screeningNotification.get(0).getApprovalStatus())) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_APPROVAL_STATUS_IS_ERROR_CODE, GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_APPROVAL_STATUS_IS_ERROR_MSG));
            }

            meaasge_typpe = Constans.meaasge_typpe4;
            meaasge_text_typpe = Constans.meaasge_text_typpe5;
            courierNumber ="";
            applyStatus = screeningNotification.get(0).getApplyStatus();
            id = screeningNotification.get(0).getId();
            text = "肠镜管理-告知书表单修改" + screeningNotification.get(0).getSid();
            sid=screeningNotification.get(0).getSid();
            main_id = record.getId();

        }

        //判断肠镜检查结果
        if(Constans.HOSPITAL_COLONOSCOPY_RESULT.equals(hospitalReferenceRecordDtodata.getFormType())){
            List<ColonoscopyResult> colonoscopyResults= colonoscopyResultService.queryById(hospitalReferenceRecordDtodata.getId());
            HospitalColonoscopyRecord record =colonoscopyService.findRecordByResultId(hospitalReferenceRecordDtodata.getId());
            if(colonoscopyResults.size()!=1 ||record ==null || record.getId() ==null){
                return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
            }

            if (!Constans.APPROVAL_STATUS1.equals(colonoscopyResults.get(0).getApprovalStatus())) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_APPROVAL_STATUS_IS_ERROR_CODE, GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_APPROVAL_STATUS_IS_ERROR_MSG));
            }

            meaasge_typpe = Constans.meaasge_typpe4;
            meaasge_text_typpe = Constans.meaasge_text_typpe5;
            courierNumber ="";
            applyStatus = colonoscopyResults.get(0).getApplyStatus();
            id = colonoscopyResults.get(0).getId();
            text = "肠镜管理-肠镜检查结果表单修改" + colonoscopyResults.get(0).getSid();
            sid=colonoscopyResults.get(0).getSid();
            main_id = record.getId();
        }

        //判断病理结果
        if(Constans.HOSPITAL_COLONOSCOPY_PATHOLOGY_RESULT.equals(hospitalReferenceRecordDtodata.getFormType())){
            ColonoscopyPathologyResult colonoscopyPathologyResult=  colonoscopyPathologyResultService.queryByID(hospitalReferenceRecordDtodata.getId(),hospitalReferenceRecordDtodata.getSid());
            HospitalColonoscopyRecord record =colonoscopyService.findRecordByPathologyId(hospitalReferenceRecordDtodata.getId());
            if(colonoscopyPathologyResult == null || record == null || record.getId()==null){
                return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
            }
            if (!Constans.APPROVAL_STATUS1.equals(colonoscopyPathologyResult.getApprovalStatus())) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_APPROVAL_STATUS_IS_ERROR_CODE, GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_APPROVAL_STATUS_IS_ERROR_MSG));
            }

            meaasge_typpe = Constans.meaasge_typpe4;
            meaasge_text_typpe = Constans.meaasge_text_typpe5;
            courierNumber ="";
            applyStatus = colonoscopyPathologyResult.getApplyStatus();
            id = colonoscopyPathologyResult.getId();
            text = "肠镜管理-病理检查结果表单修改" + colonoscopyPathologyResult.getSid();
            sid=colonoscopyPathologyResult.getSid();
            main_id = record.getId();
        }
        //根据id、类型查询修改记录表
        HospitalReferenceRecordDto hospitalReferenceRecordDtos = new HospitalReferenceRecordDto();
        hospitalReferenceRecordDtos.setDataId(id);
        hospitalReferenceRecordDtos.setFormType(hospitalReferenceRecordDtodata.getFormType());
        hospitalReferenceRecordDtos.setStatus(Constans.HOSPITAL_REFERENCE_RECORD_STATUS1);
        List<HospitalReferenceRecordDto> hospitalReferenceRecordDto = hospitalReferenceRecordService.queryEntityByIdAndType(hospitalReferenceRecordDtos);
        if (hospitalReferenceRecordDto.size() != 1) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
        String accpetName = hospitalReferenceRecordDto.get(0).getSendPerson();

        HospitalCourierResultDto hospitalCourierResultDtoss = new HospitalCourierResultDto();
        hospitalCourierResultDtoss.setId(id);
        hospitalCourierResultDtoss.setApplyStatus(Constans.APPLY_EDIT_STATUS2);
        hospitalCourierResultDtoss.setEditStatus(Constans.EDIT_STATUS2);
        hospitalCourierResultDtoss.setApprovalStatus(Constans.APPROVAL_STATUS2);

        //添加消息中心
        List<HospitalMessageCenterDto> hospitalMessageCenterDtoList = new ArrayList<>();
        HospitalMessageCenterDto hospitalMessageCenterDto = new HospitalMessageCenterDto();
        hospitalMessageCenterDto.setSendUser(loginName);
        hospitalMessageCenterDto.setAcceptUser(accpetName);
        hospitalMessageCenterDto.setMessageType(meaasge_typpe);
        hospitalMessageCenterDto.setData_id(id);
        hospitalMessageCenterDto.setSid(sid);
        hospitalMessageCenterDto.setForm_type(hospitalReferenceRecordDtodata.getFormType());
        hospitalMessageCenterDto.setSample_type(sample_type);
        hospitalMessageCenterDto.setCourierNumber(courierNumber);
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
        String message = SendMessageCenter.getMessage(loginName, accpetName, meaasge_typpe, text, sid, meaasge_text_typpe, courierNumber,remark);
        hospitalMessageCenterDto.setMessageText(message);
        hospitalMessageCenterDto.setId(UUID.randomUUID().toString().replace("-", ""));
        hospitalMessageCenterDto.setMainDataId(main_id);
        hospitalMessageCenterDtoList.add(hospitalMessageCenterDto);

        //添加编辑记录表
        hospitalReferenceRecordDtos.setId(hospitalReferenceRecordDto.get(0).getId());
        hospitalReferenceRecordDtos.setAcceptPerson(loginName);
        hospitalReferenceRecordDtos.setNoApplyStatus(Constans.APPROVAL_STATUS1);
        hospitalReferenceRecordDtos.setStatus(Constans.HOSPITAL_REFERENCE_RECORD_STATUS_FINISH);
        try {
            hospitalMessageCenterService.update(hospitalMessageCenterDtoList, hospitalReferenceRecordDtos, Constans.APPLY_EDIT_STATUS2, Constans.EDIT_STATUS2, Constans.APPROVAL_STATUS2, id.toString(), hospitalReferenceRecordDtodata.getFormType());
        } catch (Exception e) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_SAVE_HOSPITAL_COURIER_RESULT_ERROR_CODE, GlobalErrorCode.ERR_SAVE_HOSPITAL_COURIER_RESULT_ERROR_MSG));
        }
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, null, null));
    }


    /*
     * 查询消息中心
     *
     * @author ${zongt}
     * @since v1.0.0
     */
    @RequestMapping(value = "/result/queryAllMessageCenter", method = RequestMethod.POST)
    public Result queryAllMessageCenter(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalMessageCenterDto hospitalMessageCenterDto) throws ItSysException {
        log.info("查询生物样本列表");
        log.info("传入包体:{}", hospitalMessageCenterDto);

        String loginName = CookieUtil.getCookieByLoginName(req);
        hospitalMessageCenterDto.setAcceptUser(loginName);
        ListPageUtil listPage = hospitalMessageCenterService.queryAllMessageCenter(hospitalMessageCenterDto);
        Result result = new Result(listPage.getResultList(), listPage.getPageInfo());
        printEndTag("根据条件查询生物样本列表（地区）");
        log.info("返回查询对象个数={}", listPage.getResultList().size());
        return result;
    }


    /*
     * 修改
     *
     * @author ${zongt}
     * @since v1.0.0
     */
    @RequestMapping(value = "/result/updateMessageCenterStatusById", method = RequestMethod.POST)
    public String updateMessageCenterStatusById(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalMessageCenterDto hospitalMessageCenterDto) throws ItSysException {
        log.info("修改消息中心为已阅读");
        log.info("传入包体:{}", hospitalMessageCenterDto);
        if (hospitalMessageCenterDto.getId() == null || StringUtils.isEmpty(hospitalMessageCenterDto.getReadStatus())) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.HOSPITAL_MESSAGE_CENTER_UPDATE_ERROR_CODE, GlobalErrorCode.HOSPITAL_MESSAGE_CENTER_UPDATE_ERROR_MSG));
        }
        try {
            hospitalMessageCenterService.updateMessageCenterStatusById(hospitalMessageCenterDto);
            List<HospitalMessageCenterDto> hospitalMessageCenter=hospitalMessageCenterService.queryEntityById(hospitalMessageCenterDto.getId());
            HospitalMessageCenterDto hospitalMessageCenterDtoEntity = hospitalMessageCenter.get(0);
            Integer data_id = hospitalMessageCenterDtoEntity.getData_id();
            String form_type = hospitalMessageCenterDtoEntity.getForm_type();
            if(StringUtils.isEmpty(form_type)||data_id==null){
                return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, null, null));
            }
            ListPageUtil listPage =hospitalMessageCenterService.queryByTableAndId(form_type,data_id);
            List<Map<String, String>> resultList=listPage.getResultList();
            if(resultList.size()!=1){
                return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, null, null));
            }
            Map<String, String> map = resultList.get(0);
            hospitalMessageCenterDtoEntity.setApply_status(map.get("applyStatus"));
            hospitalMessageCenterDtoEntity.setApproval_status(map.get("approvalStatus"));
            hospitalMessageCenterDtoEntity.setEdit_status(map.get("editStatus"));
            return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, hospitalMessageCenterDtoEntity, null));
        }catch (Exception e){
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
    }


    @Override
    protected String getClassName() {
        // TODO Auto-generated method stub
        return RoleController.class.getName();
    }
}
