package com.yuntongxun.itsys.base.controller;

import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.*;
import com.yuntongxun.itsys.base.po.HospitalReview;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
import com.yuntongxun.itsys.base.service.BiologSampleService;
import com.yuntongxun.itsys.base.service.ColonoscopyService;
import com.yuntongxun.itsys.base.service.PersonService;
import com.yuntongxun.itsys.base.vo.*;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author zongt
 * @date 2018/5/11
 */
@RestController
public class BiologSampleAreaController extends AbstractController {

    private final Logger log = LogManager.getLogger(BiologSampleAreaController.class);

    @Autowired
    private PersonService personService;

    @Autowired
    private ColonoscopyService colonoscopyService;

    @Autowired
    private BiologSampleService biologSampleService;

    /**
     * 新增生物样本编码；
     *
     * @param req
     * @param resp
     * @return
     * @throws ItSysException
     */
/*    @RequestMapping(value = "/hospital/biological/sample/addSample", method = RequestMethod.POST)
    public String addSample(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO) throws ItSysException {
        log.info("新增样本记录");
        log.info("传入包体，body:{}", hospitalBiologicalSampleResultPO.toString());
        //校验参数
        if (StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getFrozenBoxCode())
                || StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getSampleColumn())
                || StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getSampleLine())
                || StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getSampleId())
                || StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getCollectStatusDateBySql())
                || StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getSampleType())
                ||StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getSid())
                ) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
        //校验sid 是否存在
        HospitalReview hospitalReview;
        try {
            hospitalReview = personService.getBySid(hospitalBiologicalSampleResultPO.getSid());
        } catch (EmptyResultDataAccessException e) {
            log.info("getBySid hospital_intestine_review is error data is null");
            hospitalReview = null;
        } catch (IncorrectResultSizeDataAccessException e) {
            log.info("getBySid hospital_intestine_review is error data size is >1");
            hospitalReview = null;
        }
        if (hospitalReview == null) {
            log.info("/fit/result/addFit  对应受试者sid不存在");
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_PERSON_NULL_CODE, GlobalErrorCode.ERR_PERSON_NULL_MSG));
        }

        String loginName = CookieUtil.getCookieByLoginName(req);
        String sid = hospitalBiologicalSampleResultPO.getSid();
        //判断是否是本区sid
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        Integer areaDeptId1 = doctorInfo.getAreaDeptId();
        List<HospitalReview> hospitalReviews = personService.queryByAreaID(sid, areaDeptId1);
        if (hospitalReviews.size() < 1) {
            log.info("/fit/result/addFit  该区不存在sid");
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_STOOL_VIEW_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_STOOL_VIEW_ERROR_MSG));
        }
        //获取stage
        Integer communityDeptId = hospitalReview.getCommunityDeptId();
        Integer areaDeptId = hospitalReview.getAreaDeptId();
        Integer stageCy = hospitalReview.getStageCy();
        hospitalBiologicalSampleResultPO.setCommunityDeptId(communityDeptId);
        hospitalBiologicalSampleResultPO.setAreaDeptId(areaDeptId);
        hospitalBiologicalSampleResultPO.setStage(stageCy);
        hospitalBiologicalSampleResultPO.setOperationSource(Constans.OPERATION_SOURCE_TYPE);
        hospitalBiologicalSampleResultPO.setAssociatedSampleId(UUID.randomUUID().toString().replace("-", ""));


        StringBuffer stringFrozenBoxCode=new StringBuffer();
        stringFrozenBoxCode.append(Constans.FROZEN_BOX_CODE_HEADER+doctorInfo.getScreeningType()+hospitalBiologicalSampleResultPO.getSampleType());
        //校验冷冻盒格式
        if(hospitalBiologicalSampleResultPO.getFrozenBoxCode().length()!=8||!hospitalBiologicalSampleResultPO.getFrozenBoxCode().substring(0,4).equals(stringFrozenBoxCode.toString())){
            log.info("queryByFrozenBoxCode in biologSampleService is question");
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_FORMAT_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_FORMAT_CODE_ERROR_MSG));
        }
        //校验样本ID格式
        if(hospitalBiologicalSampleResultPO.getSampleId().length()!=7||!hospitalBiologicalSampleResultPO.getSampleId().substring(0,3).equals(stringFrozenBoxCode.toString().substring(0,3))){
            log.info("queryByFrozenBoxCode in biologSampleService is question");
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_ID_FORMAT_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_ID_FORMAT_CODE_ERROR_MSG));
        }

        //校验位置是否存在
        List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVos1 = biologSampleService.queryBySampleColumnAndLine(hospitalBiologicalSampleResultPO.getFrozenBoxCode(), hospitalBiologicalSampleResultPO.getSampleColumn(), hospitalBiologicalSampleResultPO.getSampleLine());
        if (hospitalBiologicalSampleResultVos1.size() > 0) {
            log.info("queryByFrozenBoxCode in biologSampleService is question");
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_CODE_ERROR_MSG));
        }
        //校验冷冻盒必须是样本类型下面的
        boolean checkSample = biologSampleService.checkSample(hospitalBiologicalSampleResultPO.getFrozenBoxCode(), hospitalBiologicalSampleResultPO.getSampleType());
        if(!checkSample){
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_SAMPLETYPE_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_SAMPLETYPE_CODE_ERROR_MSG));
        }
        try {
            hospitalBiologicalSampleResultPO.setCollectStatusDate(DateUtil.getStringToDate(hospitalBiologicalSampleResultPO.getCollectStatusDateBySql()));
        } catch (ParseException e) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }

        //校验样本id是否存在多个SID
        List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVos= biologSampleService.queryBySampleIDAndSampletype(hospitalBiologicalSampleResultPO.getSampleId(),hospitalBiologicalSampleResultPO.getSampleType());
        if(hospitalBiologicalSampleResultVos.size()>0){
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_ID_ISHAVE_MORE5_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_ID_ISHAVE_MORE5_CODE_ERROR_MSG));
        }


        //判断是什么样本类型，如果是血清、血浆，位置自动加到5位
        if (hospitalBiologicalSampleResultPO.getSampleType().equals(Constans.SAMPLE_TYPE1) || hospitalBiologicalSampleResultPO.getSampleType().equals(Constans.SAMPLE_TYPE2)) {
            String sampleLine = hospitalBiologicalSampleResultPO.getSampleLine();
            if(!(sampleLine.equals(Constans.COLLECT_STATUS_SAMPLE_LINE1)||sampleLine.equals(Constans.COLLECT_STATUS_SAMPLE_LINE6))){
                return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
            }
            if (sampleLine.equals(Constans.COLLECT_STATUS_SAMPLE_LINE1)) {
                sampleLine = sampleLine + ",2,3,4,5";
            } else if (sampleLine.equals(Constans.COLLECT_STATUS_SAMPLE_LINE6)) {
                sampleLine = sampleLine + ",7,8,9,10";
            }
            hospitalBiologicalSampleResultPO.setSampleLine(sampleLine);
        }
        //新增
        try {
            hospitalBiologicalSampleResultPO.setCourierStatus(Constans.COURIER_STATUS_CODE2);
            hospitalBiologicalSampleResultPO.setCollectStatus(Constans.COLLECT_STATUS_PROVIDE);
            biologSampleService.saveBiologSample(hospitalBiologicalSampleResultPO);
        } catch (Exception e) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.UNKNOWN_ERROR, GlobalErrorCode.UNKNOWN_ERROR_MSG));
        }
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, null, null));
    }*/
    @RequestMapping(value = "/hospital/biological/sample/addSample", method = RequestMethod.POST)
    public String addSample(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO) throws ItSysException {
        log.info("新增样本记录");
        log.info("传入包体，body:{}", hospitalBiologicalSampleResultPO.toString());
        //校验参数
        if (StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getSampleId())
                || StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getCollectStatusDateBySql())
                || StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getSid())
                || hospitalBiologicalSampleResultPO.getSampleType() == null
                ) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
        if (hospitalBiologicalSampleResultPO.getHospitalBiologicalSampleResultPOList()!=null&&hospitalBiologicalSampleResultPO.getHospitalBiologicalSampleResultPOList().size() > 0) {
            for (HospitalBiologicalSampleResultVo vo : hospitalBiologicalSampleResultPO.getHospitalBiologicalSampleResultPOList()) {
                if (vo.isChecklist()) {
                    if (StringUtils.isEmpty(vo.getSampleType()) || vo.getSampleColumnAndLine().size() == 0 || StringUtils.isEmpty(vo.getFrozenBoxCode())) {
                        return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
                    }
                }
            }
        }
        //校验sid 是否存在
        HospitalReview hospitalReview;
        try {
            hospitalReview = personService.getBySid(hospitalBiologicalSampleResultPO.getSid());
        } catch (EmptyResultDataAccessException e) {
            log.info("getBySid hospital_intestine_review is error data is null");
            hospitalReview = null;
        } catch (IncorrectResultSizeDataAccessException e) {
            log.info("getBySid hospital_intestine_review is error data size is >1");
            hospitalReview = null;
        }
        if (hospitalReview == null) {
            log.info("/fit/result/addFit  对应受试者sid不存在");
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_PERSON_NULL_CODE, GlobalErrorCode.ERR_PERSON_NULL_MSG));
        }

        String loginName = CookieUtil.getCookieByLoginName(req);
        String sid = hospitalBiologicalSampleResultPO.getSid();
        //判断是否是本区sid
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        //筛查为6的地区，生物样本要将值更换为1 update by maxiang at 2018-07-30
        if(doctorInfo.getScreeningType()!=null&&doctorInfo.getScreeningType()==6){
            doctorInfo.setScreeningType(1);
        }
        Integer areaDeptId1 = doctorInfo.getAreaDeptId();
        List<HospitalReview> hospitalReviews = personService.queryByAreaID(sid, areaDeptId1);
        if (hospitalReviews.size() < 1) {
            log.info("/fit/result/addFit  该区不存在sid");
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_STOOL_VIEW_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_STOOL_VIEW_ERROR_MSG));
        }
        //获取stage
        Integer communityDeptId = hospitalReview.getCommunityDeptId();
        Integer areaDeptId = hospitalReview.getAreaDeptId();
        Integer stageCy = hospitalReview.getStageCy();
        hospitalBiologicalSampleResultPO.setCommunityDeptId(communityDeptId);
        hospitalBiologicalSampleResultPO.setAreaDeptId(areaDeptId);
        hospitalBiologicalSampleResultPO.setStage(stageCy);
        hospitalBiologicalSampleResultPO.setOperationSource(Constans.OPERATION_SOURCE_TYPE);
        hospitalBiologicalSampleResultPO.setAssociatedSampleId(UUID.randomUUID().toString().replace("-", ""));


        StringBuffer stringFrozenBoxCode = new StringBuffer();
        stringFrozenBoxCode.append(Constans.FROZEN_BOX_CODE_HEADER + doctorInfo.getScreeningType() + hospitalBiologicalSampleResultPO.getSampleType());

        //校验样本ID格式
        if (hospitalBiologicalSampleResultPO.getSampleId().length() != 7 || !hospitalBiologicalSampleResultPO.getSampleId().substring(0, 3).equals(stringFrozenBoxCode.toString().substring(0, 3))) {
            log.info("queryByFrozenBoxCode in biologSampleService is question");
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_ID_FORMAT_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_ID_FORMAT_CODE_ERROR_MSG));
        }

        try {
            hospitalBiologicalSampleResultPO.setCollectStatusDate(DateUtil.getStringToDate(hospitalBiologicalSampleResultPO.getCollectStatusDateBySql()));
        } catch (ParseException e) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }

        //校验样本id是否存在多个SID
        List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVos = biologSampleService.queryBySampleIDAndSampletype(hospitalBiologicalSampleResultPO.getSampleId(), hospitalBiologicalSampleResultPO.getSampleType());
        if (hospitalBiologicalSampleResultVos.size() > 0) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_ID_ISHAVE_MORE5_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_ID_ISHAVE_MORE5_CODE_ERROR_MSG));
        }
        //新增
        try {
            hospitalBiologicalSampleResultPO.setCourierStatus(Constans.COURIER_STATUS_CODE2);
            hospitalBiologicalSampleResultPO.setCollectStatus(Constans.COLLECT_STATUS_PROVIDE);
            //校验录入生物样本
            if (hospitalBiologicalSampleResultPO.getSampleType().equals(Constans.SAMPLE_TYPE6)) {
                if (hospitalBiologicalSampleResultPO.getCollectStatus().equals(Constans.COLLECT_STATUS_PROVIDE)) {
                    //校验冷冻盒格式
                    List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultPOList = hospitalBiologicalSampleResultPO.getHospitalBiologicalSampleResultPOList();
                    for (HospitalBiologicalSampleResultVo vo:hospitalBiologicalSampleResultPOList) {
                        if(vo.isChecklist()){
                            //校验冷冻盒格式
                            StringBuffer sampleA=new StringBuffer();
                            sampleA.append(Constans.FROZEN_BOX_CODE_HEADER + doctorInfo.getScreeningType() + vo.getSampleType());
                            if (vo.getFrozenBoxCode().length() != 7 || !vo.getFrozenBoxCode().substring(0, 4).equals(sampleA.toString())) {
                                log.info("queryByFrozenBoxCode in biologSampleService is question");
                                return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_FORMAT_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_FORMAT_CODE_ERROR_MSG));
                            }

                            String sampleColumn = "";
                            String sampleLine = "";
                            for (String sampleColumnAndLine : vo.getSampleColumnAndLine()) {
                                String column = sampleColumnAndLine.substring(0, 1);//行
                                String line = sampleColumnAndLine.substring(1, sampleColumnAndLine.length());//列
                                if (!sampleColumn.equals(column)) {
                                    sampleColumn = column;
                                }
                                if (!sampleLine.equals(line)) {
                                    sampleLine += line + ",";
                                }
                            }

                            if (!StringUtils.isEmpty(sampleLine)) {
                                sampleLine = sampleLine.substring(0, sampleLine.length() - 1);
                            }

                            vo.setSampleColumn(sampleColumn);
                            vo.setSampleLine(sampleLine);
                            //校验冷冻盒是否寄出
                            List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVoList = biologSampleService.queryByFrozenBoxCode(vo.getFrozenBoxCode(),Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE);
                            if(hospitalBiologicalSampleResultVoList.size()>0){
                                for (HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo:hospitalBiologicalSampleResultVoList) {
                                    if(hospitalBiologicalSampleResultVo.getCourierStatus()!=null){
                                        if(hospitalBiologicalSampleResultVo.getCourierStatus().equals(Constans.COURIER_STATUS_CODE1)||hospitalBiologicalSampleResultVo.getCourierStatus().equals(Constans.COURIER_STATUS_CODE3)){
                                            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_STOOL_FROZEN_BOX_CODE_ISHAVE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_STOOL_FROZEN_BOX_CODE_ISHAVE__ERROR_MSG));
                                        }
                                    }
                                }
                            }
                            if(Constans.SAMPLE_TYPE3.equals(vo.getSampleType())){
                                //校验位置是否存在
                                List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVos1 = biologSampleService.queryByBloodSampleColumnAndLineW(vo.getFrozenBoxCode(), vo.getSampleColumn(), vo.getSampleLine());
                                if (hospitalBiologicalSampleResultVos1.size() > 0) {
                                    log.info("queryByFrozenBoxCode in biologSampleService is question");
                                    return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_CODE_ERROR_CODE, StringUtil.changeSample(vo.getSampleType())+":"+GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_CODE_ERROR_MSG));
                                }
                            }else{
                                //校验位置是否存在
                                List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVos1 = biologSampleService.queryByBloodSampleColumnAndLine(vo.getFrozenBoxCode(), vo.getSampleColumn(), vo.getSampleLine());
                                if (hospitalBiologicalSampleResultVos1.size() > 0) {
                                    log.info("queryByFrozenBoxCode in biologSampleService is question");
                                    return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_CODE_ERROR_CODE, StringUtil.changeSample(vo.getSampleType())+":"+GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_CODE_ERROR_MSG));
                                }
                            }

                            //校验冷冻盒必须是样本类型下面的
                            boolean checkSample = biologSampleService.checkSample(vo.getFrozenBoxCode(), vo.getSampleType());
                            if (!checkSample) {
                                return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_SAMPLETYPE_CODE_ERROR_CODE,StringUtil.changeSample(vo.getSampleType())+":"+ GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_SAMPLETYPE_CODE_ERROR_MSG));
                            }
                        }

                    }
                }
                //根据样本id查询对象
                List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVoList = biologSampleService.queryBySampleID(hospitalBiologicalSampleResultPO.getSampleId());
                if(hospitalBiologicalSampleResultVoList.size()>0){
                    hospitalBiologicalSampleResultPO.setAssociatedSampleId(hospitalBiologicalSampleResultVoList.get(0).getAssociatedSampleId());
                }
                biologSampleService.saveBiologBloodSample(hospitalBiologicalSampleResultPO);
            } else {
                //校验冷冻盒格式
                if (hospitalBiologicalSampleResultPO.getFrozenBoxCode().length() != 7 || !hospitalBiologicalSampleResultPO.getFrozenBoxCode().substring(0, 4).equals(stringFrozenBoxCode.toString())) {
                    log.info("queryByFrozenBoxCode in biologSampleService is question");
                    return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_FORMAT_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_FORMAT_CODE_ERROR_MSG));
                }
                //校验位置是否存在
                List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVos1 = biologSampleService.queryBySampleColumnAndLine(hospitalBiologicalSampleResultPO.getFrozenBoxCode(), hospitalBiologicalSampleResultPO.getSampleColumn(), hospitalBiologicalSampleResultPO.getSampleLine());
                if (hospitalBiologicalSampleResultVos1.size() > 0) {
                    log.info("queryByFrozenBoxCode in biologSampleService is question");
                    return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_CODE_ERROR_MSG));
                }
                //校验冷冻盒必须是样本类型下面的
                boolean checkSample = biologSampleService.checkSample(hospitalBiologicalSampleResultPO.getFrozenBoxCode(), hospitalBiologicalSampleResultPO.getSampleType());
                if (!checkSample) {
                    return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_SAMPLETYPE_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_SAMPLETYPE_CODE_ERROR_MSG));
                }
                //校验冷冻盒是否寄出
                List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVoList1 = biologSampleService.queryByFrozenBoxCode(hospitalBiologicalSampleResultPO.getFrozenBoxCode(),Constans.BIOLOGICAL_SAMPLE_TABLE);
                if(hospitalBiologicalSampleResultVoList1.size()>0){
                    for (HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo:hospitalBiologicalSampleResultVoList1) {
                        if(hospitalBiologicalSampleResultVo.getCourierStatus()!=null){
                            if(hospitalBiologicalSampleResultVo.getCourierStatus().equals(Constans.COURIER_STATUS_CODE1)||hospitalBiologicalSampleResultVo.getCourierStatus().equals(Constans.COURIER_STATUS_CODE3)){
                                return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_STOOL_FROZEN_BOX_CODE_ISHAVE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_STOOL_FROZEN_BOX_CODE_ISHAVE__ERROR_MSG));
                            }
                        }
                    }
                }
                try {
                    hospitalBiologicalSampleResultPO.setCollectStatusDate(DateUtil.getStringToDate(hospitalBiologicalSampleResultPO.getCollectStatusDateBySql()));
                } catch (ParseException e) {
                    return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
                }
                //判断是什么样本类型，如果是血清、血浆，位置自动加到5位
                if (hospitalBiologicalSampleResultPO.getSampleType().equals(Constans.SAMPLE_TYPE1) || hospitalBiologicalSampleResultPO.getSampleType().equals(Constans.SAMPLE_TYPE2)) {
                    String sampleLine = hospitalBiologicalSampleResultPO.getSampleLine();
                    if (!(sampleLine.equals(Constans.COLLECT_STATUS_SAMPLE_LINE1) || sampleLine.equals(Constans.COLLECT_STATUS_SAMPLE_LINE6))) {
                        return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
                    }
                    if (sampleLine.equals(Constans.COLLECT_STATUS_SAMPLE_LINE1)) {
                        sampleLine = sampleLine + ",2,3,4,5";
                    } else if (sampleLine.equals(Constans.COLLECT_STATUS_SAMPLE_LINE6)) {
                        sampleLine = sampleLine + ",7,8,9,10";
                    }
                    hospitalBiologicalSampleResultPO.setSampleLine(sampleLine);
                }
                List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVoList = biologSampleService.queryBySampleID(hospitalBiologicalSampleResultPO.getSampleId());

                if(hospitalBiologicalSampleResultVoList.size()>0){
                    hospitalBiologicalSampleResultPO.setAssociatedSampleId(hospitalBiologicalSampleResultVoList.get(0).getAssociatedSampleId());
                }
                biologSampleService.saveBiologSample(hospitalBiologicalSampleResultPO);
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG));
        }
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, null, null));
    }

    /**
     * @func
     * @desc 查询生物样本列表
     * @author zongt
     * @create 2018/5/11 下午3:56
     * @request
     * @response
     **/
    @RequestMapping(value = "/area/biological/sample/query", method = RequestMethod.POST)
    public Result query(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO) throws ItSysException {
        log.info("查询生物样本列表");
        log.info("传入包体:{}", hospitalBiologicalSampleResultPO);

        String loginName = CookieUtil.getCookieByLoginName(req);
        ListPageUtil listPage = biologSampleService.queryArea(hospitalBiologicalSampleResultPO, loginName);
        Result result = new Result(listPage.getResultList(), listPage.getPageInfo());
        printEndTag("根据条件查询生物样本列表（地区）");
        log.info("返回查询对象个数={}", listPage.getResultList().size());
        return result;
    }


    /**
     * @func
     * @desc 校验冷冻盒位置
     * @author zongt
     * @create 2018/5/11 下午3:56
     * @request
     * @response
     **/
    @RequestMapping(value = "/area/biological/sample/checkFrozen", method = RequestMethod.POST)
    public String checkFrozen(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO) throws ItSysException {
        log.info("查询生物样本列表");
        log.info("传入包体:{}", hospitalBiologicalSampleResultPO);

        if (hospitalBiologicalSampleResultPO.getSampleType() == null || StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getFrozenBoxCode()) || hospitalBiologicalSampleResultPO.getSampleColumnAndLine() == null || hospitalBiologicalSampleResultPO.getSampleColumnAndLine().size() == 0) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
        String loginName = CookieUtil.getCookieByLoginName(req);
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        //筛查为6的地区，生物样本要将值更换为1 update by maxiang at 2018-07-30
        if(doctorInfo.getScreeningType()!=null&&doctorInfo.getScreeningType()==6){
            doctorInfo.setScreeningType(1);
        }

        StringBuffer stringFrozenBoxCode = new StringBuffer();
        stringFrozenBoxCode.append(Constans.FROZEN_BOX_CODE_HEADER + doctorInfo.getScreeningType() + hospitalBiologicalSampleResultPO.getSampleType());
        //校验冷冻盒格式
        if (hospitalBiologicalSampleResultPO.getFrozenBoxCode().length() != 7 || !hospitalBiologicalSampleResultPO.getFrozenBoxCode().substring(0, 4).equals(stringFrozenBoxCode.toString())) {
            log.info("queryByFrozenBoxCode in biologSampleService is question");
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_FORMAT_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_FORMAT_CODE_ERROR_MSG));
        }

        String sampleColumn = "";
        String sampleLine = "";
        for (String sampleColumnAndLine : hospitalBiologicalSampleResultPO.getSampleColumnAndLine()) {
            String column = sampleColumnAndLine.substring(0, 1);//行
            String line = sampleColumnAndLine.substring(1, sampleColumnAndLine.length());//列
            if (!sampleColumn.equals(column)) {
                sampleColumn = column;
            }
            if (!sampleLine.equals(line)) {
                sampleLine += line + ",";
            }
        }

        if (!StringUtils.isEmpty(sampleLine)) {
            sampleLine = sampleLine.substring(0, sampleLine.length() - 1);
        }

        hospitalBiologicalSampleResultPO.setSampleColumn(sampleColumn);
        hospitalBiologicalSampleResultPO.setSampleLine(sampleLine);
        String newfrozenBoxCode=hospitalBiologicalSampleResultPO.getFrozenBoxCode()+sampleColumn+sampleLine;
        boolean isok=true;
        //根据id
        if(hospitalBiologicalSampleResultPO.getId()!=null){
            List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVos = biologSampleService.queryBloodById(hospitalBiologicalSampleResultPO.getId());
            if(hospitalBiologicalSampleResultVos.size()!=1){
                return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
            }
            String oldFrozenBoxCode = hospitalBiologicalSampleResultVos.get(0).getFrozenBoxCode()+hospitalBiologicalSampleResultVos.get(0).getSampleColumn()+hospitalBiologicalSampleResultVos.get(0).getSampleLine();
            if(newfrozenBoxCode.equals(oldFrozenBoxCode)){
                isok=false;
            }
        }
        if(isok){
            if(Constans.SAMPLE_TYPE3.equals(hospitalBiologicalSampleResultPO.getSampleType())){
                //校验位置是否存在
                List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVos1 = biologSampleService.queryByBloodSampleColumnAndLineW(hospitalBiologicalSampleResultPO.getFrozenBoxCode(), hospitalBiologicalSampleResultPO.getSampleColumn(), hospitalBiologicalSampleResultPO.getSampleLine());
                if (hospitalBiologicalSampleResultVos1.size() > 0) {
                    log.info("queryByFrozenBoxCode in biologSampleService is question");
                    return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_CODE_ERROR_MSG));
                }
            }else{
                //校验位置是否存在
                List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVos1 = biologSampleService.queryByBloodSampleColumnAndLine(hospitalBiologicalSampleResultPO.getFrozenBoxCode(), hospitalBiologicalSampleResultPO.getSampleColumn(), hospitalBiologicalSampleResultPO.getSampleLine());
                if (hospitalBiologicalSampleResultVos1.size() > 0) {
                    log.info("queryByFrozenBoxCode in biologSampleService is question");
                    return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_CODE_ERROR_MSG));
                }
            }


        }

        //校验冷冻盒必须是样本类型下面的
        boolean checkSample = biologSampleService.checkSample(hospitalBiologicalSampleResultPO.getFrozenBoxCode(), hospitalBiologicalSampleResultPO.getSampleType());
        if (!checkSample) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_SAMPLETYPE_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_SAMPLETYPE_CODE_ERROR_MSG));
        }
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS));

    }

    /**
     * @func
     * @desc 录入生物样本结果
     * @author zongt
     * @create 2018/5/14 上午9:36
     * @request
     * @response
     **/
    @RequestMapping(value = "/area/sample/result/addSampleResult", method = RequestMethod.POST)
    public String addSampleResult(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO) throws ItSysException {
        log.info("录入样本信息");
        log.info("传入包体:{}", hospitalBiologicalSampleResultPO);

        StringBuffer stringFrozenBoxCode = new StringBuffer();
        //校验参数
        if (hospitalBiologicalSampleResultPO.getId() == null || hospitalBiologicalSampleResultPO.getCollectStatus() == null) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
        hospitalBiologicalSampleResultPO.setCourierStatus(Constans.COURIER_STATUS_CODE2);


        //校验是提供还是未提供
        if (hospitalBiologicalSampleResultPO.getCollectStatus().equals(Constans.COLLECT_STATUS_PROVIDE)) {
            //校验参数
            if (StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getSampleId())
                    || hospitalBiologicalSampleResultPO.getCollectStatus() == null
                    || StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getCollectStatusDateBySql())
                    || StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getSampleId())
                    ) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
            }
            if (hospitalBiologicalSampleResultPO.getHospitalBiologicalSampleResultPOList() != null && hospitalBiologicalSampleResultPO.getHospitalBiologicalSampleResultPOList().size() > 0) {
                for (HospitalBiologicalSampleResultVo vo : hospitalBiologicalSampleResultPO.getHospitalBiologicalSampleResultPOList()) {
                    if (vo.isChecklist()) {
                        if (StringUtils.isEmpty(vo.getSampleType()) || vo.getSampleColumnAndLine().size() == 0 || StringUtils.isEmpty(vo.getFrozenBoxCode())) {
                            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
                        }
                    }
                }
            }
        } else {
            hospitalBiologicalSampleResultPO.setSampleLine(null);
            hospitalBiologicalSampleResultPO.setSampleColumn(null);
            hospitalBiologicalSampleResultPO.setSampleId(null);
            hospitalBiologicalSampleResultPO.setFrozenBoxCode(null);
            hospitalBiologicalSampleResultPO.setCourierStatus(null);
        }

        //根据id校验是否存在
        String loginName = CookieUtil.getCookieByLoginName(req);
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        //筛查为6的地区，生物样本要将值更换为1 update by maxiang at 2018-07-30
        if(doctorInfo.getScreeningType()!=null&&doctorInfo.getScreeningType()==6){
            doctorInfo.setScreeningType(1);
        }
        Integer areaDeptId = doctorInfo.getAreaDeptId();
        //id、加地区信息  判断是否是在该区下的人的操作
        List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVos = biologSampleService.queryById(hospitalBiologicalSampleResultPO.getId(), areaDeptId);
        if (hospitalBiologicalSampleResultVos.size() != 1) {
            log.info("queryById in biologSampleService is question");
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
        if (hospitalBiologicalSampleResultVos.get(0).getCollectStatus() != -1) {
            log.info("queryById in biologSampleService is question");
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_SAVE_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_SAVE_CODE_ERROR_CODE_ERROR_MSG));
        }
        //校验冷冻盒编号是否唯一
        if (hospitalBiologicalSampleResultPO.getCollectStatus().equals(Constans.COLLECT_STATUS_PROVIDE)) {
            //校验冷冻盒格式
            stringFrozenBoxCode.append(Constans.FROZEN_BOX_CODE_HEADER + doctorInfo.getScreeningType() + hospitalBiologicalSampleResultVos.get(0).getSampleType());
            //校验样本ID格式
            if (hospitalBiologicalSampleResultPO.getSampleId().length() != 7 || !hospitalBiologicalSampleResultPO.getSampleId().substring(0, 3).equals(stringFrozenBoxCode.toString().substring(0, 3))) {
                log.info("queryByFrozenBoxCode in biologSampleService is question");
                return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_ID_FORMAT_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_ID_FORMAT_CODE_ERROR_MSG));
            }
            try {
                hospitalBiologicalSampleResultPO.setCollectStatusDate(DateUtil.getStringToDate(hospitalBiologicalSampleResultPO.getCollectStatusDateBySql()));
            } catch (ParseException e) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
            }
        }

        hospitalBiologicalSampleResultPO.setOperationSource(hospitalBiologicalSampleResultVos.get(0).getOperationSource());
        hospitalBiologicalSampleResultPO.setSampleType(hospitalBiologicalSampleResultVos.get(0).getSampleType());

        //校验样本id是否存在多个SID
        List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVoList = biologSampleService.queryBySampleIDAndSampletype(hospitalBiologicalSampleResultPO.getSampleId(), hospitalBiologicalSampleResultPO.getSampleType());
        if (hospitalBiologicalSampleResultVoList.size() > 0) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_ID_ISHAVE_MORE5_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_ID_ISHAVE_MORE5_CODE_ERROR_MSG));
        }
        //修改
        try {
            if (Constans.SAMPLE_TYPE6.equals(hospitalBiologicalSampleResultPO.getSampleType())) {
                if (hospitalBiologicalSampleResultPO.getCollectStatus().equals(Constans.COLLECT_STATUS_PROVIDE)) {
                    //校验冷冻盒格式
                    List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultPOList = hospitalBiologicalSampleResultPO.getHospitalBiologicalSampleResultPOList();
                    for (HospitalBiologicalSampleResultVo vo:hospitalBiologicalSampleResultPOList) {
                        if(vo.isChecklist()){
                            //校验冷冻盒格式
                            StringBuffer sampleA=new StringBuffer();
                            sampleA.append(Constans.FROZEN_BOX_CODE_HEADER + doctorInfo.getScreeningType() + vo.getSampleType());
                            if (vo.getFrozenBoxCode().length() != 7 || !vo.getFrozenBoxCode().substring(0, 4).equals(sampleA.toString())) {
                                log.info("queryByFrozenBoxCode in biologSampleService is question");
                                return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_FORMAT_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_FORMAT_CODE_ERROR_MSG));
                            }

                            String sampleColumn = "";
                            String sampleLine = "";
                            for (String sampleColumnAndLine : vo.getSampleColumnAndLine()) {
                                String column = sampleColumnAndLine.substring(0, 1);//行
                                String line = sampleColumnAndLine.substring(1, sampleColumnAndLine.length());//列
                                if (!sampleColumn.equals(column)) {
                                    sampleColumn = column;
                                }
                                if (!sampleLine.equals(line)) {
                                    sampleLine += line + ",";
                                }
                            }

                            if (!StringUtils.isEmpty(sampleLine)) {
                                sampleLine = sampleLine.substring(0, sampleLine.length() - 1);
                            }

                            vo.setSampleColumn(sampleColumn);
                            vo.setSampleLine(sampleLine);
                            if(Constans.SAMPLE_TYPE3.equals(vo.getSampleType())){
                                //校验位置是否存在
                                List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVos1 = biologSampleService.queryByBloodSampleColumnAndLineW(vo.getFrozenBoxCode(), vo.getSampleColumn(), vo.getSampleLine());
                                if (hospitalBiologicalSampleResultVos1.size() > 0) {
                                    log.info("queryByFrozenBoxCode in biologSampleService is question");
                                    return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_CODE_ERROR_CODE, StringUtil.changeSample(vo.getSampleType())+":"+GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_CODE_ERROR_MSG));
                                }
                            }else{
                                //校验位置是否存在
                                List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVos1 = biologSampleService.queryByBloodSampleColumnAndLine(vo.getFrozenBoxCode(), vo.getSampleColumn(), vo.getSampleLine());
                                if (hospitalBiologicalSampleResultVos1.size() > 0) {
                                    log.info("queryByFrozenBoxCode in biologSampleService is question");
                                    return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_CODE_ERROR_CODE, StringUtil.changeSample(vo.getSampleType())+":"+GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_CODE_ERROR_MSG));
                                }
                            }

                            //校验冷冻盒必须是样本类型下面的
                            boolean checkSample = biologSampleService.checkSample(vo.getFrozenBoxCode(), vo.getSampleType());
                            if (!checkSample) {
                                return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_SAMPLETYPE_CODE_ERROR_CODE, StringUtil.changeSample(vo.getSampleType())+":"+GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_SAMPLETYPE_CODE_ERROR_MSG));
                            }
                            //校验冷冻盒是否寄出
                            List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVoList1 = biologSampleService.queryByFrozenBoxCode(vo.getFrozenBoxCode(),Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE);
                            if(hospitalBiologicalSampleResultVoList1.size()>0){
                                for (HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo:hospitalBiologicalSampleResultVoList1) {
                                    if(hospitalBiologicalSampleResultVo.getCourierStatus()!=null){
                                        if(hospitalBiologicalSampleResultVo.getCourierStatus().equals(Constans.COURIER_STATUS_CODE1)||hospitalBiologicalSampleResultVo.getCourierStatus().equals(Constans.COURIER_STATUS_CODE3)){
                                            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_STOOL_FROZEN_BOX_CODE_ISHAVE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_STOOL_FROZEN_BOX_CODE_ISHAVE__ERROR_MSG));
                                        }
                                    }
                                }
                            }

                        }

                    }
                }
                biologSampleService.updateBiologBloodSample(hospitalBiologicalSampleResultPO);
            } else {
                if (hospitalBiologicalSampleResultPO.getCollectStatus().equals(Constans.COLLECT_STATUS_PROVIDE)) {
                    //校验冷冻盒格式
                    if (hospitalBiologicalSampleResultPO.getFrozenBoxCode().length() != 7 || !hospitalBiologicalSampleResultPO.getFrozenBoxCode().substring(0, 4).equals(stringFrozenBoxCode.toString())) {
                        log.info("queryByFrozenBoxCode in biologSampleService is question");
                        return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_FORMAT_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_FORMAT_CODE_ERROR_MSG));
                    }
                    //校验位置是否存在
                    List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVos1 = biologSampleService.queryBySampleColumnAndLine(hospitalBiologicalSampleResultPO.getFrozenBoxCode(), hospitalBiologicalSampleResultPO.getSampleColumn(), hospitalBiologicalSampleResultPO.getSampleLine());
                    if (hospitalBiologicalSampleResultVos1.size() > 0) {
                        log.info("queryByFrozenBoxCode in biologSampleService is question");
                        return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_CODE_ERROR_MSG));
                    }
                    //校验冷冻盒必须是样本类型下面的
                    boolean checkSample = biologSampleService.checkSample(hospitalBiologicalSampleResultPO.getFrozenBoxCode(), hospitalBiologicalSampleResultPO.getSampleType());
                    if (!checkSample) {
                        return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_SAMPLETYPE_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_SAMPLETYPE_CODE_ERROR_MSG));
                    }
                    try {
                        hospitalBiologicalSampleResultPO.setCollectStatusDate(DateUtil.getStringToDate(hospitalBiologicalSampleResultPO.getCollectStatusDateBySql()));
                    } catch (ParseException e) {
                        return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
                    }

                    //校验冷冻盒是否寄出
                    List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVoList1 = biologSampleService.queryByFrozenBoxCode(hospitalBiologicalSampleResultPO.getFrozenBoxCode(),Constans.BIOLOGICAL_SAMPLE_TABLE);


                    if(hospitalBiologicalSampleResultVoList1.size()>0){
                        for (HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo:hospitalBiologicalSampleResultVoList1) {
                            if(hospitalBiologicalSampleResultVo.getCourierStatus()!=null){
                                if(hospitalBiologicalSampleResultVo.getCourierStatus().equals(Constans.COURIER_STATUS_CODE1)||hospitalBiologicalSampleResultVo.getCourierStatus().equals(Constans.COURIER_STATUS_CODE3)){
                                    return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_STOOL_FROZEN_BOX_CODE_ISHAVE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_STOOL_FROZEN_BOX_CODE_ISHAVE__ERROR_MSG));
                                }
                            }
                        }
                    }
                }
                biologSampleService.updateBiologSample(hospitalBiologicalSampleResultPO);
            }
        } catch (Exception e) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.UNKNOWN_ERROR, GlobalErrorCode.UNKNOWN_ERROR_MSG));
        }
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, null, null));
    }


    /**
     * @func
     * @desc 回显样本ID接口
     * @author zongt
     * @create 2018/5/14 上午9:36
     * @request
     * @response
     **/
    @RequestMapping(value = "/area/sample/result/querySampleId", method = RequestMethod.POST)
    public String querySampleId(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO) throws ItSysException {
        log.info("回显样本ID接口");
        log.info("传入包体:{}", hospitalBiologicalSampleResultPO);


        //校验参数
        if (StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getAssociatedSampleId())) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
        //修改
        String sampleid = biologSampleService.queryByAssociatedSample(hospitalBiologicalSampleResultPO.getAssociatedSampleId());
        if (StringUtils.isEmpty(sampleid)) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_ID_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_ID_CODE_ERROR_MSG));
        }
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, sampleid, null));
    }

    /**
     * @func
     * @desc 根据血液ID获取数据
     * @author zongt
     * @create 2018/5/14 上午9:36
     * @request
     * @response
     **/
    @RequestMapping(value = "/area/sample/result/querySampleBloodId", method = RequestMethod.POST)
    public String querySampleBloodId(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO) throws ItSysException {
        log.info("血液样本ID查询接口");
        log.info("传入包体:{}", hospitalBiologicalSampleResultPO);
        String loginName = CookieUtil.getCookieByLoginName(req);
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        //筛查为6的地区，生物样本要将值更换为1 update by maxiang at 2018-07-30
        if(doctorInfo.getScreeningType()!=null&&doctorInfo.getScreeningType()==6){
            doctorInfo.setScreeningType(1);
        }
        Integer areaDeptId = doctorInfo.getAreaDeptId();
        Integer screeningType = doctorInfo.getScreeningType();
        //校验参数
        if (hospitalBiologicalSampleResultPO.getId() == null) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
        List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVoList = biologSampleService.queryById(hospitalBiologicalSampleResultPO.getId(), areaDeptId);
        List<HospitalBiologicalSampleResultVo> hospitalBiologicalBloodSampleResultVoList = biologSampleService.queryByBloodId(hospitalBiologicalSampleResultPO.getId());
        for (HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo : hospitalBiologicalBloodSampleResultVoList) {
            hospitalBiologicalSampleResultVo.setFrozenBoxCodeHeader(Constans.FROZEN_BOX_CODE_HEADER + screeningType + hospitalBiologicalSampleResultVo.getSampleType());
            String sampleLine = hospitalBiologicalSampleResultVo.getSampleLine();
            String sampleColumn = hospitalBiologicalSampleResultVo.getSampleColumn();
            if (sampleLine != null && !StringUtils.isEmpty(sampleLine) && !sampleLine.equals("null")) {
                List<String> columnLine = new ArrayList<>();
                if (sampleLine.contains(",")) {
                    String[] split = sampleLine.split(",");
                    for (String Line : split) {
                        columnLine.add(sampleColumn + Line);
                    }
                } else {
                    columnLine.add(sampleColumn + sampleLine);
                }
                hospitalBiologicalSampleResultVo.setSampleColumnAndLine(columnLine);
            }
            hospitalBiologicalSampleResultVo.setChecklist(false);
            if (hospitalBiologicalSampleResultVo.getReslutStatus() != null && hospitalBiologicalSampleResultVo.getReslutStatus().equals(Constans.COLLECT_STATUS_PROVIDE)) {
                hospitalBiologicalSampleResultVo.setChecklist(true);
            }
        }
        HospitalBiologicalSampleResultVo vo = hospitalBiologicalSampleResultVoList.get(0);
        vo.setCollectStatusDateBySql(DateUtil.dateToStr(vo.getCollectStatusDate(), DateUtil.YMR_SLASH));
        vo.setHospitalBiologicalSampleResultPOList(hospitalBiologicalBloodSampleResultVoList);
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, vo, null));
    }

    /*
     * 根据id查询数据
     *
     * @author ${zongt}
     * @since v1.0.0
     */
    @RequestMapping(value = "/area/sample/result/querySampleIdByMF", method = RequestMethod.POST)
    public  String querySampleIdByMF(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO) throws ItSysException {
        log.info("唾液样本ID查询接口");
        log.info("传入包体:{}", hospitalBiologicalSampleResultPO);
        if(hospitalBiologicalSampleResultPO.getId()==null||StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getSampleType())){
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, biologSampleService.queryById(hospitalBiologicalSampleResultPO.getId(), null), null));
    }

    @Override
    protected String getClassName() {
        return BiologSampleAreaController.class.getName();
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("A1");
        //list.add("A2");

        String sampleColumn = "";
        String sampleLine = "";
        for (String sampleColumnAndLine : list) {
            String column = sampleColumnAndLine.substring(0, 1);
            String line = sampleColumnAndLine.substring(1, 2);
            if (!sampleColumn.equals(column)) {
                sampleColumn = column;
            }
            if (!sampleLine.equals(line)) {
                sampleLine += line + ",";
            }
        }
        System.out.println(sampleColumn);
        System.out.println(sampleLine);
    }


    /*
     * 修改生物样本接口
     *
     * @author ${zongt}
     * @since v1.0.0
     */
    @RequestMapping(value = "/area/sample/result/updateSampleResult", method = RequestMethod.POST)
    public String updateSampleResult(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO) throws ItSysException {
        log.info("传入包体:{}", hospitalBiologicalSampleResultPO);
        String loginName=CookieUtil.getCookieByLoginName(req);
        StringBuffer stringFrozenBoxCode = new StringBuffer();

        //校验参数
        if (hospitalBiologicalSampleResultPO.getId() == null || hospitalBiologicalSampleResultPO.getCollectStatus() == null) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }

        List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVos2 = biologSampleService.queryById(hospitalBiologicalSampleResultPO.getId(), null);
        //根据id获取数据
        if(hospitalBiologicalSampleResultVos2.size()!=1){
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }

        //校验数据是否可以编辑
        if(!Constans.EDIT_STATUS2.equals(hospitalBiologicalSampleResultVos2.get(0).getEditStatus())){
            return JSONUtils.toJson(new Response(GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_EDIT_IS_ERROR_CODE1, GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_EDIT_IS_ERROR_MSG1));
        }

        List<String> oldFrozenBoxCode=new ArrayList<>();
        HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo1 = hospitalBiologicalSampleResultVos2.get(0);
        //判断原来的数据是否采集
        if(Constans.COLLECT_STATUS_PROVIDE.equals(hospitalBiologicalSampleResultVo1.getCollectStatus())){
            //校验原来的冷冻盒是否寄出
            //判断样本类型
            if(Constans.SAMPLE_TYPE6.equals(hospitalBiologicalSampleResultVo1.getSampleType())){
                //根据samplyID查询冷冻盒是否已经寄出
                List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVos = biologSampleService.queryByBloodId(hospitalBiologicalSampleResultVo1.getId());
                for (HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo:hospitalBiologicalSampleResultVos) {
                    if( hospitalBiologicalSampleResultVo.getCourierStatus()!=null){
                        if(!Constans.COURIER_STATUS_CODE2.equals( hospitalBiologicalSampleResultVo.getCourierStatus())){
                            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_STOOL_FROZEN_BOX_CODE_ISHAVE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_STOOL_FROZEN_BOX_CODE_ISHAVE__ERROR_MSG));
                        }
                    }
                    oldFrozenBoxCode.add(hospitalBiologicalSampleResultVo.getFrozenBoxCode()+hospitalBiologicalSampleResultVo.getSampleColumn()+hospitalBiologicalSampleResultVo.getSampleLine());
                }
                hospitalBiologicalSampleResultVo1.setHospitalBiologicalSampleResultPOList(hospitalBiologicalSampleResultVos);
            }else{
                List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVos = biologSampleService.queryById(hospitalBiologicalSampleResultVo1.getId(), null);
                if(hospitalBiologicalSampleResultVos.size()!=1){
                    return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_STOOL_FROZEN_BOX_CODE_ISHAVE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_STOOL_FROZEN_BOX_CODE_ISHAVE__ERROR_MSG));
                }
                if(!Constans.COURIER_STATUS_CODE2.equals( hospitalBiologicalSampleResultVos.get(0).getCourierStatus())){
                    return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_STOOL_FROZEN_BOX_CODE_ISHAVE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_STOOL_FROZEN_BOX_CODE_ISHAVE__ERROR_MSG));
                }
                oldFrozenBoxCode.add(hospitalBiologicalSampleResultVos.get(0).getFrozenBoxCode()+hospitalBiologicalSampleResultVos.get(0).getSampleColumn()+hospitalBiologicalSampleResultVos.get(0).getSampleLine());
            }

        }
        HospitalReferenceRecordDto hospitalReferenceRecordDto=new HospitalReferenceRecordDto();
        hospitalReferenceRecordDto.setStatus(Constans.HOSPITAL_REFERENCE_RECORD_STATUS_FINISH);
        hospitalReferenceRecordDto.setDataId(hospitalBiologicalSampleResultVo1.getId());
        hospitalReferenceRecordDto.setFormType(Constans.HOSPITAL_BIOLOGICAL_SAMPLE_RESULT);
        hospitalReferenceRecordDto.setEditPerson(loginName);
        String dataText = JSONUtils.toJson(hospitalBiologicalSampleResultVo1);
        hospitalReferenceRecordDto.setOldData(dataText);


        hospitalBiologicalSampleResultPO.setCourierStatus(Constans.COURIER_STATUS_CODE2);
        //校验是提供还是未提供
        if (hospitalBiologicalSampleResultPO.getCollectStatus().equals(Constans.COLLECT_STATUS_PROVIDE)) {
            //校验参数
            if (StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getSampleId())
                    || hospitalBiologicalSampleResultPO.getCollectStatus() == null
                    || StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getCollectStatusDateBySql())
                    || StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getSampleId())
                    ) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
            }
            if (hospitalBiologicalSampleResultPO.getHospitalBiologicalSampleResultPOList() != null && hospitalBiologicalSampleResultPO.getHospitalBiologicalSampleResultPOList().size() > 0) {
                for (HospitalBiologicalSampleResultVo vo : hospitalBiologicalSampleResultPO.getHospitalBiologicalSampleResultPOList()) {
                    if (vo.isChecklist()) {
                        if (StringUtils.isEmpty(vo.getSampleType()) || vo.getSampleColumnAndLine().size() == 0 || StringUtils.isEmpty(vo.getFrozenBoxCode())) {
                            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
                        }
                    }
                }
            }
        } else {
            hospitalBiologicalSampleResultPO.setSampleLine(null);
            hospitalBiologicalSampleResultPO.setSampleColumn(null);
            hospitalBiologicalSampleResultPO.setSampleId(null);
            hospitalBiologicalSampleResultPO.setFrozenBoxCode(null);
            hospitalBiologicalSampleResultPO.setCourierStatus(null);
        }

        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        Integer areaDeptId = doctorInfo.getAreaDeptId();
        //id、加地区信息  判断是否是在该区下的人的操作
        //筛查为6的地区，生物样本要将值更换为1 update by zongtong at 2018-08-28
        if(doctorInfo.getScreeningType()!=null&&doctorInfo.getScreeningType()==6){
            doctorInfo.setScreeningType(1);
        }
        List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVos = biologSampleService.queryById(hospitalBiologicalSampleResultPO.getId(), areaDeptId);
        if (hospitalBiologicalSampleResultVos.size() != 1) {
            log.info("queryById in biologSampleService is question");
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
/*        if (hospitalBiologicalSampleResultVos.get(0).getCollectStatus() != -1) {
            log.info("queryById in biologSampleService is question");
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_SAVE_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_SAVE_CODE_ERROR_CODE_ERROR_MSG));
        }*/
        //校验冷冻盒编号是否唯一
        if (hospitalBiologicalSampleResultPO.getCollectStatus().equals(Constans.COLLECT_STATUS_PROVIDE)) {
            //校验冷冻盒格式
            stringFrozenBoxCode.append(Constans.FROZEN_BOX_CODE_HEADER + doctorInfo.getScreeningType() + hospitalBiologicalSampleResultVos.get(0).getSampleType());
            //校验样本ID格式
            if (hospitalBiologicalSampleResultPO.getSampleId().length() != 7 || !hospitalBiologicalSampleResultPO.getSampleId().substring(0, 3).equals(stringFrozenBoxCode.toString().substring(0, 3))) {
                log.info("queryByFrozenBoxCode in biologSampleService is question");
                return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_ID_FORMAT_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_ID_FORMAT_CODE_ERROR_MSG));
            }
            try {
                hospitalBiologicalSampleResultPO.setCollectStatusDate(DateUtil.getStringToDate(hospitalBiologicalSampleResultPO.getCollectStatusDateBySql()));
            } catch (ParseException e) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
            }
        }

        hospitalBiologicalSampleResultPO.setOperationSource(hospitalBiologicalSampleResultVos.get(0).getOperationSource());
        hospitalBiologicalSampleResultPO.setSampleType(hospitalBiologicalSampleResultVos.get(0).getSampleType());

        if(hospitalBiologicalSampleResultPO.getCollectStatus().equals(Constans.COLLECT_STATUS_PROVIDE)){
            if(!hospitalBiologicalSampleResultPO.getSampleId().equals(hospitalBiologicalSampleResultVo1.getSampleId())){
                //校验样本id是否存在多个SID
                List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVoList = biologSampleService.queryBySampleIDAndSampletype(hospitalBiologicalSampleResultPO.getSampleId(), hospitalBiologicalSampleResultPO.getSampleType());
                if (hospitalBiologicalSampleResultVoList.size() > 0) {
                    return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_ID_ISHAVE_MORE5_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_ID_ISHAVE_MORE5_CODE_ERROR_MSG));
                }
            }
        }

        //修改
        try {
            if (Constans.SAMPLE_TYPE6.equals(hospitalBiologicalSampleResultPO.getSampleType())) {
                if (hospitalBiologicalSampleResultPO.getCollectStatus().equals(Constans.COLLECT_STATUS_PROVIDE)) {
                    //校验冷冻盒格式
                    List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultPOList = hospitalBiologicalSampleResultPO.getHospitalBiologicalSampleResultPOList();
                    for (HospitalBiologicalSampleResultVo vo:hospitalBiologicalSampleResultPOList) {
                        if(vo.isChecklist()){
                            //校验冷冻盒格式
                            StringBuffer sampleA=new StringBuffer();
                            sampleA.append(Constans.FROZEN_BOX_CODE_HEADER + doctorInfo.getScreeningType() + vo.getSampleType());
                            if (vo.getFrozenBoxCode().length() != 7 || !vo.getFrozenBoxCode().substring(0, 4).equals(sampleA.toString())) {
                                log.info("queryByFrozenBoxCode in biologSampleService is question");
                                return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_FORMAT_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_FORMAT_CODE_ERROR_MSG));
                            }

                            String sampleColumn = "";
                            String sampleLine = "";
                            for (String sampleColumnAndLine : vo.getSampleColumnAndLine()) {
                                String column = sampleColumnAndLine.substring(0, 1);//行
                                String line = sampleColumnAndLine.substring(1, sampleColumnAndLine.length());//列
                                if (!sampleColumn.equals(column)) {
                                    sampleColumn = column;
                                }
                                if (!sampleLine.equals(line)) {
                                    sampleLine += line + ",";
                                }
                            }

                            if (!StringUtils.isEmpty(sampleLine)) {
                                sampleLine = sampleLine.substring(0, sampleLine.length() - 1);
                            }

                            vo.setSampleColumn(sampleColumn);
                            vo.setSampleLine(sampleLine);


                            if(!oldFrozenBoxCode.contains(vo.getFrozenBoxCode()+vo.getSampleColumn()+ vo.getSampleLine())){
                                if(Constans.SAMPLE_TYPE3.equals(vo.getSampleType())){
                                    //校验位置是否存在
                                    List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVos1 = biologSampleService.queryByBloodSampleColumnAndLineW(vo.getFrozenBoxCode(), vo.getSampleColumn(), vo.getSampleLine());
                                    if (hospitalBiologicalSampleResultVos1.size() > 0) {
                                        log.info("queryByFrozenBoxCode in biologSampleService is question");
                                        return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_CODE_ERROR_CODE, StringUtil.changeSample(vo.getSampleType())+":"+GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_CODE_ERROR_MSG));
                                    }
                                }else{
                                    //校验位置是否存在
                                    List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVos1 = biologSampleService.queryByBloodSampleColumnAndLine(vo.getFrozenBoxCode(), vo.getSampleColumn(), vo.getSampleLine());
                                    if (hospitalBiologicalSampleResultVos1.size() > 0) {
                                        log.info("queryByFrozenBoxCode in biologSampleService is question");
                                        return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_CODE_ERROR_CODE, StringUtil.changeSample(vo.getSampleType())+":"+GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_CODE_ERROR_MSG));
                                    }
                                }
                            }


                            //校验冷冻盒必须是样本类型下面的
                            boolean checkSample = biologSampleService.checkSample(vo.getFrozenBoxCode(), vo.getSampleType());
                            if (!checkSample) {
                                return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_SAMPLETYPE_CODE_ERROR_CODE, StringUtil.changeSample(vo.getSampleType())+":"+GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_SAMPLETYPE_CODE_ERROR_MSG));
                            }
                            //校验冷冻盒是否寄出
                            List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVoList1 = biologSampleService.queryByFrozenBoxCode(vo.getFrozenBoxCode(),Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE);
                            if(hospitalBiologicalSampleResultVoList1.size()>0){
                                for (HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo:hospitalBiologicalSampleResultVoList1) {
                                    if(hospitalBiologicalSampleResultVo.getCourierStatus()!=null){
                                        if(hospitalBiologicalSampleResultVo.getCourierStatus().equals(Constans.COURIER_STATUS_CODE1)||hospitalBiologicalSampleResultVo.getCourierStatus().equals(Constans.COURIER_STATUS_CODE3)){
                                            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_STOOL_FROZEN_BOX_CODE_ISHAVE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_STOOL_FROZEN_BOX_CODE_ISHAVE__ERROR_MSG));
                                        }
                                    }
                                }
                            }

                        }
                    }

                }
                biologSampleService.updateEditBiologBloodSample(hospitalBiologicalSampleResultPO,hospitalReferenceRecordDto);
            } else {
                if (hospitalBiologicalSampleResultPO.getCollectStatus().equals(Constans.COLLECT_STATUS_PROVIDE)) {
                    //校验冷冻盒格式
                    if (hospitalBiologicalSampleResultPO.getFrozenBoxCode().length() != 7 || !hospitalBiologicalSampleResultPO.getFrozenBoxCode().substring(0, 4).equals(stringFrozenBoxCode.toString())) {
                        log.info("queryByFrozenBoxCode in biologSampleService is question");
                        return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_FORMAT_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_FORMAT_CODE_ERROR_MSG));
                    }
                    if(!oldFrozenBoxCode.contains(hospitalBiologicalSampleResultPO.getFrozenBoxCode()+hospitalBiologicalSampleResultPO.getSampleColumn()+hospitalBiologicalSampleResultPO.getSampleLine())){
                        //校验位置是否存在
                        List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVos1 = biologSampleService.queryBySampleColumnAndLine(hospitalBiologicalSampleResultPO.getFrozenBoxCode(), hospitalBiologicalSampleResultPO.getSampleColumn(), hospitalBiologicalSampleResultPO.getSampleLine());
                        if (hospitalBiologicalSampleResultVos1.size() > 0) {
                            log.info("queryByFrozenBoxCode in biologSampleService is question");
                            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_CODE_ERROR_MSG));
                        }
                    }
                    //校验冷冻盒必须是样本类型下面的
                    boolean checkSample = biologSampleService.checkSample(hospitalBiologicalSampleResultPO.getFrozenBoxCode(), hospitalBiologicalSampleResultPO.getSampleType());
                    if (!checkSample) {
                        return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_SAMPLETYPE_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_SAMPLETYPE_CODE_ERROR_MSG));
                    }
                    try {
                        hospitalBiologicalSampleResultPO.setCollectStatusDate(DateUtil.getStringToDate(hospitalBiologicalSampleResultPO.getCollectStatusDateBySql()));
                    } catch (ParseException e) {
                        return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
                    }

                    //校验冷冻盒是否寄出
                    List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVoList1 = biologSampleService.queryByFrozenBoxCode(hospitalBiologicalSampleResultPO.getFrozenBoxCode(),Constans.BIOLOGICAL_SAMPLE_TABLE);


                    if(hospitalBiologicalSampleResultVoList1.size()>0){
                        for (HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo:hospitalBiologicalSampleResultVoList1) {
                            if(hospitalBiologicalSampleResultVo.getCourierStatus()!=null){
                                if(hospitalBiologicalSampleResultVo.getCourierStatus().equals(Constans.COURIER_STATUS_CODE1)||hospitalBiologicalSampleResultVo.getCourierStatus().equals(Constans.COURIER_STATUS_CODE3)){
                                    return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_STOOL_FROZEN_BOX_CODE_ISHAVE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_STOOL_FROZEN_BOX_CODE_ISHAVE__ERROR_MSG));
                                }
                            }
                        }
                    }
                }
                biologSampleService.updateEditBiologSample(hospitalBiologicalSampleResultPO,hospitalReferenceRecordDto);
            }
        } catch (Exception e) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.UNKNOWN_ERROR, GlobalErrorCode.UNKNOWN_ERROR_MSG));
        }
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, null, null));
    }



}
