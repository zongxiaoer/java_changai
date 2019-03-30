package com.yuntongxun.itsys.base.controller;

import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.*;
import com.yuntongxun.itsys.base.po.dto.ItsysUserDto;
import com.yuntongxun.itsys.base.po.dto.courier.FrozenBoxCodeDto;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalCourierResultDto;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
import com.yuntongxun.itsys.base.po.dto.message.HospitalMessageCenterDto;
import com.yuntongxun.itsys.base.service.ColonoscopyService;
import com.yuntongxun.itsys.base.service.HospitalCourierResultService;
import com.yuntongxun.itsys.base.service.HospitalMessageCenterService;
import com.yuntongxun.itsys.base.service.HospitalReferenceRecordService;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
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
import java.util.UUID;

/**
 * @author zongt
 * @date 2018/6/21
 */
@RestController
public class HospitalCourierResultController extends AbstractController {

    private final Logger log = LogManager.getLogger(HospitalCourierResultController.class);


    @Autowired
    private ColonoscopyService colonoscopyService;

    @Autowired
    private HospitalCourierResultService hospitalCourierResultService;


    @Autowired
    private HospitalMessageCenterService hospitalMessageCenterService;


    @Autowired
    private HospitalReferenceRecordService hospitalReferenceRecordService;


    /**
     * @func
     * @desc 查询冷冻盒列表
     * @author zongt
     * @create 2018/6/21 下午7:19
     * @request
     * @response
     **/
    @RequestMapping(value = "/area/courier/result/queryFrozenBoxCodes", method = RequestMethod.POST)
    public String queryFrozenBoxCodes(HttpServletRequest req, HttpServletResponse resp) throws ItSysException {
        log.info("冷冻盒列表查询");
        String loginName = CookieUtil.getCookieByLoginName(req);
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        Integer areaDeptId = doctorInfo.getAreaDeptId();
        Integer communityDeptId = doctorInfo.getCommunityDeptId();
        String table = "";
        //根据地区社区信息查询血液样本信息
        table = Constans.BIOLOGICAL_SAMPLE_TABLE;
        List<FrozenBoxCodeDto> FrozenBoxCodes = hospitalCourierResultService.queryFrozenBoxCodesInSample(table, areaDeptId, communityDeptId);

        //根据地区社区信息查询生物样本信息
        table = Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE;

        List<FrozenBoxCodeDto> FrozenBoxCodes2 = hospitalCourierResultService.queryFrozenBoxCodesInBloodSample(table, areaDeptId, communityDeptId);
        FrozenBoxCodes.addAll(FrozenBoxCodes2);
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, FrozenBoxCodes, null));
    }

    /**
     * @func
     * @desc 提交快递信息
     * @author zongt
     * @create 2018/6/22 上午10:06
     * @request
     * @response
     **/
    @RequestMapping(value = "/area/courier/result/saveCourierResult", method = RequestMethod.POST)
    public String saveCourierResult(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalCourierResultDto hospitalCourierResultDto) throws ItSysException {
        log.info("接受提交快递信息" + JSONUtils.toJson(hospitalCourierResultDto));

        //校验参数
        if (StringUtils.isEmpty(hospitalCourierResultDto.getCourierCompany()) || StringUtils.isEmpty(hospitalCourierResultDto.getSendDateByString()) || StringUtils.isEmpty(hospitalCourierResultDto.getCourierNumber()) || StringUtils.isEmpty(hospitalCourierResultDto.getSendPerson()) || StringUtils.isEmpty(hospitalCourierResultDto.getSendPersonPhone()) || hospitalCourierResultDto.getFrozenBoxCodeDtos().size() < 1) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
        String loginName = CookieUtil.getCookieByLoginName(req);
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        Integer areaDeptId = doctorInfo.getAreaDeptId();
        Integer screeningType = doctorInfo.getScreeningType();
        hospitalCourierResultDto.setAreaDeptId(areaDeptId);
        hospitalCourierResultDto.setScreeningtype(screeningType.toString());

        //区分成2个冷冻盒编号集合体 血液、生物样本
        List<FrozenBoxCodeDto> frozenBoxCodeDtos = hospitalCourierResultDto.getFrozenBoxCodeDtos();
        List<FrozenBoxCodeDto> frozenBoxCodeDtosBoole = new ArrayList<>();
        List<FrozenBoxCodeDto> frozenBoxCodeDtosSamply = new ArrayList<>();
        for (FrozenBoxCodeDto frozenBoxCodeDto : frozenBoxCodeDtos) {
            String frozenBoxCode = frozenBoxCodeDto.getFrozenBoxCode();
            if (frozenBoxCode.length() == 7) {
                String samplyType = frozenBoxCode.substring(3, 4);
                FrozenBoxCodeDto frozenBoxCodeDto1 = new FrozenBoxCodeDto();
                if (Constans.SAMPLE_TYPE3.equals(samplyType) || Constans.SAMPLE_TYPE2.equals(samplyType) || Constans.SAMPLE_TYPE1.equals(samplyType)) {
                    frozenBoxCodeDto1.setFrozenBoxCode(frozenBoxCode);
                    frozenBoxCodeDtosBoole.add(frozenBoxCodeDto1);
                } else if (Constans.SAMPLE_TYPE4.equals(samplyType) || Constans.SAMPLE_TYPE5.equals(samplyType)) {
                    frozenBoxCodeDto1.setFrozenBoxCode(frozenBoxCode);
                    frozenBoxCodeDtosSamply.add(frozenBoxCodeDto1);
                }
            }
        }

        //查询是否有编辑中的
        int i=0;
        if(frozenBoxCodeDtosBoole.size()>0){
            i = hospitalCourierResultService.queryByBloodFrozenBoxCodeDto(frozenBoxCodeDtosBoole, Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE);
        }
        int j=0;
        if(frozenBoxCodeDtosSamply.size()>0){
            j = hospitalCourierResultService.queryByFrozenBoxCodeDto(frozenBoxCodeDtosSamply, Constans.BIOLOGICAL_SAMPLE_TABLE);
        }
        if(i+j>0){
            return JSONUtils.toJson(new Response(GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_EDIT_IS_ERROR_CODE2, GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_EDIT_IS_ERROR_MSG3));
        }

        //根据快递编号查看是否存在
        List<HospitalCourierResultDto> hospitalCourierResultDtos = hospitalCourierResultService.queryByCourierNumber(hospitalCourierResultDto.getCourierNumber());
        if (hospitalCourierResultDtos.size() > 0) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_COURIER_NUMBER_RESULT_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_COURIER_NUMBER_RESULT_ERROR_MSG));
        }

        //入库快递信息
        try {
            hospitalCourierResultService.saveCourier(hospitalCourierResultDto, frozenBoxCodeDtosBoole, frozenBoxCodeDtosSamply);
        } catch (Exception e) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_SAVE_HOSPITAL_COURIER_RESULT_ERROR_CODE, GlobalErrorCode.ERR_SAVE_HOSPITAL_COURIER_RESULT_ERROR_MSG));
        }
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, null, null));
    }


    /*
     * 修改快递信息
     *
     * @author ${zongt}
     * @since v1.0.0
     */
    @RequestMapping(value = "/area/courier/result/updateCourierResultById", method = RequestMethod.POST)
    public String updateCourierResultById(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalCourierResultDto hospitalCourierResultDto) throws ItSysException {
        //   log.info("接受提交快递信息" + JSONUtils.toJson(hospitalCourierResultDto));

        //校验参数
        if (StringUtils.isEmpty(hospitalCourierResultDto.getCourierCompany()) || StringUtils.isEmpty(hospitalCourierResultDto.getSendDateByString()) || StringUtils.isEmpty(hospitalCourierResultDto.getCourierNumber()) || StringUtils.isEmpty(hospitalCourierResultDto.getSendPerson()) || StringUtils.isEmpty(hospitalCourierResultDto.getSendPersonPhone()) || hospitalCourierResultDto.getFrozenBoxCodeDtos().size() < 1 || hospitalCourierResultDto.getId() == null) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
        String loginName = CookieUtil.getCookieByLoginName(req);
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        Integer areaDeptId = doctorInfo.getAreaDeptId();
        Integer screeningType = doctorInfo.getScreeningType();
        hospitalCourierResultDto.setAreaDeptId(areaDeptId);
        hospitalCourierResultDto.setScreeningtype(screeningType.toString());

        //根据id查询结果
        List<HospitalCourierResultDto> hospitalCourierResultDtos = hospitalCourierResultService.queryById(hospitalCourierResultDto.getId());


        //校验是否有接受信息
        if (hospitalCourierResultDtos.size()!= 1) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
        if (!Constans.EDIT_STATUS2.equals(hospitalCourierResultDtos.get(0).getEditStatus())) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_EDIT_IS_ERROR_CODE1, GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_EDIT_IS_ERROR_MSG1));
        }

        if(hospitalCourierResultDtos.get(0).getCourierStatus()!=null){
            if (hospitalCourierResultDtos.get(0).getCourierStatus().equals(Constans.COURIER_STATUS_CODE1)) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_UPDATE_HOSPITAL_COURIER_RESULT_IS_HAVE_ERROR_CODE, GlobalErrorCode.ERR_UPDATE_HOSPITAL_COURIER_RESULT_IS_HAVE_ERROR_MSG));

            }
        }

        //根据快递id查询所有生物样本信息
        //根据快递id查生物样本冷冻盒信息
        List<FrozenBoxCodeDto> frozenBoxCodeDtosBooleResult = hospitalCourierResultService.querySampleByCourierId(hospitalCourierResultDto.getCourierNumber(), Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE);

        List<FrozenBoxCodeDto> frozenBoxCodeDtosSamplyReslut = hospitalCourierResultService.querySampleByCourierId(hospitalCourierResultDto.getCourierNumber(), Constans.BIOLOGICAL_SAMPLE_TABLE);


        //请求成2个冷冻盒编号集合体 血液、生物样本
        List<FrozenBoxCodeDto> frozenBoxCodeDtos = hospitalCourierResultDto.getFrozenBoxCodeDtos();
        List<FrozenBoxCodeDto> frozenBoxCodeDtosBoole = new ArrayList<>();
        List<FrozenBoxCodeDto> frozenBoxCodeDtosSamply = new ArrayList<>();
        for (FrozenBoxCodeDto frozenBoxCodeDto : frozenBoxCodeDtos) {
            String frozenBoxCode = frozenBoxCodeDto.getFrozenBoxCode();
            if (frozenBoxCode.length() == 7) {
                String samplyType = frozenBoxCode.substring(3, 4);
                FrozenBoxCodeDto frozenBoxCodeDto1 = new FrozenBoxCodeDto();
                if (Constans.SAMPLE_TYPE3.equals(samplyType) || Constans.SAMPLE_TYPE2.equals(samplyType) || Constans.SAMPLE_TYPE1.equals(samplyType)) {
                    frozenBoxCodeDto1.setFrozenBoxCode(frozenBoxCode);
                    frozenBoxCodeDtosBoole.add(frozenBoxCodeDto1);
                } else if (Constans.SAMPLE_TYPE4.equals(samplyType) || Constans.SAMPLE_TYPE5.equals(samplyType)) {
                    frozenBoxCodeDto1.setFrozenBoxCode(frozenBoxCode);
                    frozenBoxCodeDtosSamply.add(frozenBoxCodeDto1);
                }
            }
        }

        //根据快递编号查看是否存在
        if (!hospitalCourierResultDtos.get(0).getCourierNumber().equals(hospitalCourierResultDto.getCourierNumber())) {
            List<HospitalCourierResultDto> hospitalCourierResultDtoss = hospitalCourierResultService.queryByCourierNumber(hospitalCourierResultDto.getCourierNumber());
            if (hospitalCourierResultDtoss.size() > 0) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_COURIER_NUMBER_RESULT_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_COURIER_NUMBER_RESULT_ERROR_MSG));
            }
        }

/*
        List<FrozenBoxCodeDto> frozenBoxCodeDtosBooleResult = hospitalCourierResultService.querySampleByCourierId(hospitalCourierResultDtoList.get(0).getCourierNumber(), Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE);

        List<FrozenBoxCodeDto> frozenBoxCodeDtosSamplyReslut = hospitalCourierResultService.querySampleByCourierId(hospitalCourierResultDtoList.get(0).getCourierNumber(), Constans.BIOLOGICAL_SAMPLE_TABLE);
        hospitalCourierResultDtoList.get(0).setFrozenBoxCodeDtos(frozenBoxCodeDtosBooleResult);
        hospitalCourierResultDtoList.get(0).setFrozenBoxCodeDtos(frozenBoxCodeDtosSamplyReslut);
        String dataText = JSONUtils.toJson(hospitalCourierResultDtoList.get(0));
        hospitalReferenceRecordDtos.setOldData(dataText);
*/
        HospitalReferenceRecordDto hospitalReferenceRecordDto = new HospitalReferenceRecordDto();
        hospitalReferenceRecordDto.setStatus(Constans.HOSPITAL_REFERENCE_RECORD_STATUS_FINISH);
        hospitalReferenceRecordDto.setDataId(hospitalCourierResultDtos.get(0).getId());
        hospitalReferenceRecordDto.setFormType(Constans.HOSPITAL_COURIER_RESULT);
        hospitalCourierResultDtos.get(0).setFrozenBoxCodeDtos(frozenBoxCodeDtosBooleResult);
        hospitalCourierResultDtos.get(0).setFrozenBoxCodeDtos(frozenBoxCodeDtosSamplyReslut);
        hospitalReferenceRecordDto.setEditPerson(loginName);
        String dataText = JSONUtils.toJson(hospitalCourierResultDtos.get(0));
        hospitalReferenceRecordDto.setOldData(dataText);
        //入库快递信息
        try {
            //hospitalCourierResultService.saveCourier(hospitalCourierResultDto, frozenBoxCodeDtosBoole, frozenBoxCodeDtosSamply);
            hospitalCourierResultService.updateCourier(frozenBoxCodeDtosBooleResult, frozenBoxCodeDtosSamplyReslut, hospitalCourierResultDto, frozenBoxCodeDtosBoole, frozenBoxCodeDtosSamply, hospitalReferenceRecordDto);
        } catch (Exception e) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_SAVE_HOSPITAL_COURIER_RESULT_ERROR_CODE, GlobalErrorCode.ERR_SAVE_HOSPITAL_COURIER_RESULT_ERROR_MSG));
        }
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, null, null));
    }


    /**
     * @func
     * @desc 校验快递单号
     * @author zongt
     * @create 2018/6/22 上午10:06
     * @request
     * @response
     **/
    @RequestMapping(value = "/area/courier/result/checkCourierNumber", method = RequestMethod.POST)
    public String checkCourierNumber(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalCourierResultDto hospitalCourierResultDto) throws ItSysException {
        log.info("接受提交快递信息" + JSONUtils.toJson(hospitalCourierResultDto));

        //校验参数
        if (StringUtils.isEmpty(hospitalCourierResultDto.getCourierNumber())) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
        boolean isok=false;
        if(hospitalCourierResultDto.getId()!=null){
            List<HospitalCourierResultDto> hospitalCourierResultDtos = hospitalCourierResultService.queryById(hospitalCourierResultDto.getId());
            if(hospitalCourierResultDtos.size()!=1){
                return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_COURIER_NUMBER_RESULT_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_COURIER_NUMBER_RESULT_ERROR_MSG));
            }
            HospitalCourierResultDto hospitalCourierResultDto1 = hospitalCourierResultDtos.get(0);
            if(hospitalCourierResultDto.getCourierNumber().equals(hospitalCourierResultDto1.getCourierNumber())){
                isok=true;
            }
        }

        if(hospitalCourierResultDto.getId()==null){
            //根据快递编号查看是否存在
            List<HospitalCourierResultDto> hospitalCourierResultDtos = hospitalCourierResultService.queryByCourierNumber(hospitalCourierResultDto.getCourierNumber());
            if (hospitalCourierResultDtos.size() > 0) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_COURIER_NUMBER_RESULT_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_COURIER_NUMBER_RESULT_ERROR_MSG));
            }
        }
        if(hospitalCourierResultDto.getId()!=null){
            if(!isok){
                List<HospitalCourierResultDto> hospitalCourierResultDtos = hospitalCourierResultService.queryByCourierNumber(hospitalCourierResultDto.getCourierNumber());
                if (hospitalCourierResultDtos.size() > 0) {
                    return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_COURIER_NUMBER_RESULT_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_COURIER_NUMBER_RESULT_ERROR_MSG));
                }
            }
        }

        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, null, null));
    }


    /**
     * @func
     * @desc 查询模块信息接口
     * @author zongt
     * @create 2018/6/22 下午6:04
     * @request
     * @response
     **/
    @RequestMapping(value = "/courier/result/queryCourierResult", method = RequestMethod.POST)
    public Result queryCourierResult(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalCourierResultDto hospitalCourierResultDto) throws ItSysException {
        log.info("查询快递信息接口" + JSONUtils.toJson(hospitalCourierResultDto));
        String loginName = CookieUtil.getCookieByLoginName(req);
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        Integer areaDeptId = doctorInfo.getAreaDeptId();
        hospitalCourierResultDto.setAreaDeptId(areaDeptId);
        ListPageUtil listPageUtil = hospitalCourierResultService.queryByEntity(hospitalCourierResultDto);
        Result result = new Result(listPageUtil.getResultList(), listPageUtil.getPageInfo());
        return result;
    }

    /**
     * @func
     * @desc 确认接受信息
     * @author zongt
     * @create 2018/6/25 上午10:10
     * @request
     * @response
     **/
    @RequestMapping(value = "/area/courier/result/updateCourierResult", method = RequestMethod.POST)
    public String updateCourierResult(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalCourierResultDto hospitalCourierResultDto) throws ItSysException {
        log.info("接受提交快递信息" + JSONUtils.toJson(hospitalCourierResultDto));

        //校验参数
        if (hospitalCourierResultDto.getId() == null || StringUtils.isEmpty(hospitalCourierResultDto.getAcceptDateByString()) || StringUtils.isEmpty(hospitalCourierResultDto.getAcceptPerson()) || StringUtils.isEmpty(hospitalCourierResultDto.getCourierNumber())) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }

        List<HospitalCourierResultDto> hospitalCourierResultDtos = hospitalCourierResultService.queryByIdANDCourierNumber(hospitalCourierResultDto.getId(), hospitalCourierResultDto.getCourierNumber());
        if (hospitalCourierResultDtos.size() != 1) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
        if (Constans.APPLY_EDIT_STATUS2.equals(hospitalCourierResultDtos.get(0).getApplyStatus()) || Constans.EDIT_STATUS2.equals(hospitalCourierResultDtos.get(0).getEditStatus())) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_EDIT_IS_ERROR_CODE2, GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_EDIT_IS_ERROR_MSG3));
        }
        //根据快递单号修改
        try {
            hospitalCourierResultService.updateCourierByAccept(hospitalCourierResultDto);
        } catch (Exception e) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_UPDATE_HOSPITAL_COURIER_RESULT_ERROR_CODE, GlobalErrorCode.ERR_UPDATE_HOSPITAL_COURIER_RESULT_ERROR_MSG));
        }
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, null, null));
    }


    /**
     * @func
     * @desc 根据运单号查询详情
     * @author zongt
     * @create 2018/6/25 上午10:10
     * @request
     * @response
     **/
    @RequestMapping(value = "/area/courier/result/queryCourierResultById", method = RequestMethod.POST)
    public String queryCourierResultById(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalCourierResultDto hospitalCourierResultDto) throws ItSysException {
        //根据相关信息
        List<FrozenBoxCodeDto> frozenBoxCodeDtos = new ArrayList<>();

        if (StringUtils.isEmpty(hospitalCourierResultDto.getCourierNumber())) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
        String tableName = Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE;
        List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVos = hospitalCourierResultService.querySamplyByCourierNumber(hospitalCourierResultDto.getCourierNumber(), tableName);
        for (HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo : hospitalBiologicalSampleResultVos) {
            String frozenBoxCode = hospitalBiologicalSampleResultVo.getFrozenBoxCode();
            if (!StringUtils.isEmpty(frozenBoxCode)) {
                String samplyType = frozenBoxCode.substring(3, 4);
                FrozenBoxCodeDto frozenBoxCodeDto = new FrozenBoxCodeDto();
                frozenBoxCodeDto.setSamplyType(samplyType);
                frozenBoxCodeDto.setFrozenBoxCode(frozenBoxCode);
                frozenBoxCodeDtos.add(frozenBoxCodeDto);
            }
        }
        tableName = Constans.BIOLOGICAL_SAMPLE_TABLE;
        List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVoList = hospitalCourierResultService.querySamplyByCourierNumber(hospitalCourierResultDto.getCourierNumber(), tableName);
        for (HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo : hospitalBiologicalSampleResultVoList) {
            String frozenBoxCode = hospitalBiologicalSampleResultVo.getFrozenBoxCode();
            if (!StringUtils.isEmpty(frozenBoxCode)) {
                String samplyType = frozenBoxCode.substring(3, 4);
                FrozenBoxCodeDto frozenBoxCodeDto = new FrozenBoxCodeDto();
                frozenBoxCodeDto.setSamplyType(samplyType);
                frozenBoxCodeDto.setFrozenBoxCode(frozenBoxCode);
                frozenBoxCodeDtos.add(frozenBoxCodeDto);
            }
        }

        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, frozenBoxCodeDtos, null));
    }


    /*
     * 根据id查看详情
     *
     * @author ${zongt}
     * @since v1.0.0
     */
    @RequestMapping(value = "/area/courier/result/queryCourierResultId", method = RequestMethod.POST)
    public String queryCourierResultId(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalCourierResultDto hospitalCourierResultDto) throws ItSysException {
        //根据相关信息


        if (hospitalCourierResultDto.getId() == null) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
        List<HospitalCourierResultDto> hospitalCourierResultDtos = hospitalCourierResultService.queryById(hospitalCourierResultDto.getId());
        if (hospitalCourierResultDtos.size() != 1) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
        HospitalCourierResultDto hospitalCourierResultDto1 = hospitalCourierResultDtos.get(0);
        hospitalCourierResultDto1.setSendDateByString(hospitalCourierResultDto1.getSendDate() == null ? "" : DateUtil.dateToStr(hospitalCourierResultDto1.getSendDate(), DateUtil.YMR_SLASH));
        hospitalCourierResultDto1.setAcceptDateByString(hospitalCourierResultDto1.getAcceptDate() == null ? "" : DateUtil.dateToStr(hospitalCourierResultDto1.getAcceptDate(), DateUtil.YMR_SLASH));
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, hospitalCourierResultDto1, null));
    }

    @Override
    protected String getClassName() {
        return null;
    }

    public static void main(String[] args) {
        String s = "af1s";
        System.out.println(s.substring(2, 3));
    }
}
