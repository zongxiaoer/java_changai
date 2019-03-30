package com.yuntongxun.itsys.base.controller;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: HospitalCancerController
 * Author:   zongtong
 * Date:     2018/9/6 上午10:42
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/9/6 上午10:42           v1.0.0
 */

import com.alibaba.fastjson.JSON;
import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.*;
import com.yuntongxun.itsys.base.po.FileUploadLogPO;
import com.yuntongxun.itsys.base.po.dto.cancervo.*;
import com.yuntongxun.itsys.base.service.ColonoscopyService;
import com.yuntongxun.itsys.base.service.FileUploadService;
import com.yuntongxun.itsys.base.service.HospitalCancerService;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
import com.yuntongxun.itsys.base.vo.Response;
import com.yuntongxun.itsys.base.vo.Result;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.yuntongxun.itsys.base.common.constans.Constans;

@RestController
public class HospitalCancerController extends AbstractController {

    @Autowired
    private HospitalCancerService hospitalCancerService;

    @Autowired
    private ColonoscopyService colonoscopyService;

    @Autowired
    private FileUploadService fileUploadService;

    private final Logger log = LogManager.getLogger(HospitalCancerController.class.getName());


    /*
     * 根据条件查询癌症检查
     *
     * @author ${zongt}
     * @since v2.2.3
     */
    @RequestMapping(value = "/hospital/cancer/record/query", method = RequestMethod.POST)
    public Result query(HttpServletRequest req, HttpServletResponse response, @RequestBody HospitalCancerRecordVo hospitalCancerRecordVo) throws ItSysException {
        printStartTag("/hospital/cancer/record/query");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
        log.info("@Controller 根据条件查询癌症检查 ，输入参数：{},登录名：{}", JSONUtils.toJson(hospitalCancerRecordVo),loginName);
        ListPageUtil listPage = hospitalCancerService.query(hospitalCancerRecordVo, loginName);
        Result result = new Result(listPage.getResultList(), listPage.getPageInfo());
        printEndTag("/hospital/cancer/record/query 结束");
        log.info("返回查询对象个数={}", listPage.getResultList().size());
        return result;
    }

    /*
     * 添加C1癌症报告表
     *
     * @author ${zongt}
     * @since v2.2.3
     */
    @RequestMapping(value = "/hospital/cancer/addreport", method = RequestMethod.POST)
    public String addreport(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalCancerReportVo hospitalCancerReportVo) throws ItSysException {
        printStartTag("/hospital/cancer/addreport");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        log.info("@Controller 添加C1癌症报告表 ，输入参数：{},登录名：{}", JSONUtils.toJson(hospitalCancerReportVo),loginName);

        //校验参数
        if(hospitalCancerReportVo.getCancerRecordId()==null||StringUtils.isEmpty(hospitalCancerReportVo.getSid())||StringUtils.isEmpty(hospitalCancerReportVo.getTbTableDateToString())||StringUtils.isEmpty(hospitalCancerReportVo.getCheckYears())||StringUtils.isEmpty(hospitalCancerReportVo.getInvestigatorCode())||StringUtils.isEmpty(hospitalCancerReportVo.getTbTablePerson())||StringUtils.isEmpty(hospitalCancerReportVo.getQualityControlPerson())){
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE1, GlobalErrorCode.PARAMETER_ERR_MSG1));
        }
        if(hospitalCancerReportVo.getHospitalCancerReportMessagePOS()!=null&&hospitalCancerReportVo.getHospitalCancerReportMessagePOS().size()>0){
            List<HospitalCancerReportMessageVo> hospitalCancerReportMessagePOS = hospitalCancerReportVo.getHospitalCancerReportMessagePOS();
            for (HospitalCancerReportMessageVo hospitalCancerReportMessagePO:hospitalCancerReportMessagePOS) {
                if(StringUtils.isEmpty(hospitalCancerReportMessagePO.getCancerTypeSite())||StringUtils.isEmpty(hospitalCancerReportMessagePO.getReportDateToString())||StringUtils.isEmpty(hospitalCancerReportMessagePO.getDiagnoseSource())){
                    return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE1, GlobalErrorCode.PARAMETER_ERR_MSG1));
                }
                //转化时间
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date reportDate = df.parse(hospitalCancerReportMessagePO.getReportDateToString());
                    hospitalCancerReportMessagePO.setReportDate(reportDate);
                } catch (ParseException e) {
                    return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
                }
            }
        }

        //转化时间
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date tbTableDate = df.parse(hospitalCancerReportVo.getTbTableDateToString());
            hospitalCancerReportVo.setTbTableDate(tbTableDate);
        } catch (ParseException e) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
        hospitalCancerReportVo.setCreateUser(doctorInfo.getId());

        try {
            hospitalCancerService.saveReport(hospitalCancerReportVo);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG));
        }
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, null, null));
    }


    /*
     *  根据id查询癌症检查
     *
     * @author ${zongt}
     * @since v2.2.1
     */
    @RequestMapping(value = "/hospital/cancer/report/queryreportbyid", method = RequestMethod.POST)
    public String queryreportbyid(HttpServletRequest req, HttpServletResponse response, @RequestBody HospitalCancerRecordVo hospitalCancerRecordVo) throws ItSysException {
        printStartTag("/hospital/cancer/report/queryreportbyid");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
        log.info("@Controller 根据条件查询癌症检查 ，输入参数：{},登录名：{}", JSONUtils.toJson(hospitalCancerRecordVo),loginName);
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, hospitalCancerService.queryReportById(hospitalCancerRecordVo.getId(), doctorInfo.getCommunityDeptId(),doctorInfo.getAreaDeptId()), null));
    }

    /*
     * 修改C1癌症报告表
     *
     * @author ${zongt}
     * @since v2.2.3
     */
    @RequestMapping(value = "/hospital/cancer/updatereport", method = RequestMethod.POST)
    public String updatereport(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalCancerReportVo hospitalCancerReportVo) throws ItSysException {
        printStartTag("/hospital/cancer/addreport");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        log.info("@Controller 添加C1癌症报告表 ，输入参数：{},登录名：{}", JSONUtils.toJson(hospitalCancerReportVo),loginName);
        //校验参数
        if(hospitalCancerReportVo.getId()==null||StringUtils.isEmpty(hospitalCancerReportVo.getSid())||StringUtils.isEmpty(hospitalCancerReportVo.getTbTableDateToString())||StringUtils.isEmpty(hospitalCancerReportVo.getCheckYears())||StringUtils.isEmpty(hospitalCancerReportVo.getInvestigatorCode())||StringUtils.isEmpty(hospitalCancerReportVo.getTbTablePerson())||StringUtils.isEmpty(hospitalCancerReportVo.getQualityControlPerson())){
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE1, GlobalErrorCode.PARAMETER_ERR_MSG1));
        }
        if(hospitalCancerReportVo.getHospitalCancerReportMessagePOS()!=null&&hospitalCancerReportVo.getHospitalCancerReportMessagePOS().size()>0){
            List<HospitalCancerReportMessageVo> hospitalCancerReportMessagePOS = hospitalCancerReportVo.getHospitalCancerReportMessagePOS();
            for (HospitalCancerReportMessageVo hospitalCancerReportMessagePO:hospitalCancerReportMessagePOS) {
                if(StringUtils.isEmpty(hospitalCancerReportMessagePO.getCancerTypeSite())||StringUtils.isEmpty(hospitalCancerReportMessagePO.getReportDateToString())||StringUtils.isEmpty(hospitalCancerReportMessagePO.getDiagnoseSource())){
                    return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE1, GlobalErrorCode.PARAMETER_ERR_MSG1));
                }
                //转化时间
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date reportDate = df.parse(hospitalCancerReportMessagePO.getReportDateToString());
                    hospitalCancerReportMessagePO.setReportDate(reportDate);
                } catch (ParseException e) {
                    return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
                }
            }
        }
        //转化时间
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date tbTableDate = df.parse(hospitalCancerReportVo.getTbTableDateToString());
            hospitalCancerReportVo.setTbTableDate(tbTableDate);
        } catch (ParseException e) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
        hospitalCancerReportVo.setCreateUser(doctorInfo.getId());
        try {
            hospitalCancerService.updateReport(hospitalCancerReportVo);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG));
        }
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, null, null));
    }

    /*
     * 添加C2癌症诊断表
     *
     * @author ${zongt}
     * @since v2.2.3
     */
    @RequestMapping(value = "/hospital/cancer/adddiagnose", method = RequestMethod.POST)
    public String adddiagnose(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalCancerDiagnoseVo hospitalCancerDiagnoseVo) throws ItSysException {
        printStartTag("/hospital/cancer/adddiagnose");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        log.info("@Controller 癌症诊断表 ，输入参数：{},登录名：{}", JSONUtils.toJson(hospitalCancerDiagnoseVo),loginName);

        //校验参数
        if(hospitalCancerDiagnoseVo.getCancerRecordId()==null||StringUtils.isEmpty(hospitalCancerDiagnoseVo.getSid())||StringUtils.isEmpty(hospitalCancerDiagnoseVo.getYsCancerReportDateToString())||StringUtils.isEmpty(hospitalCancerDiagnoseVo.getWriteTableDateToString())||StringUtils.isEmpty(hospitalCancerDiagnoseVo.getCheckYears())||StringUtils.isEmpty(hospitalCancerDiagnoseVo.getInvestigatorCode())||StringUtils.isEmpty(hospitalCancerDiagnoseVo.getTbTablePerson())||StringUtils.isEmpty(hospitalCancerDiagnoseVo.getQualityControlPerson())){
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE1, GlobalErrorCode.PARAMETER_ERR_MSG1));
        }
        //转化时间
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date writeTableDate = df.parse(hospitalCancerDiagnoseVo.getWriteTableDateToString());
            hospitalCancerDiagnoseVo.setWriteTableDate(writeTableDate);

            Date ysCancerReportDate = df.parse(hospitalCancerDiagnoseVo.getYsCancerReportDateToString());
            hospitalCancerDiagnoseVo.setYsCancerReportDate(ysCancerReportDate);
            if(!StringUtils.isEmpty(hospitalCancerDiagnoseVo.getYfCancerDiagnosisDateToString())){
                Date yfCancerDiagnosisDate = df.parse(hospitalCancerDiagnoseVo.getYfCancerDiagnosisDateToString());
                hospitalCancerDiagnoseVo.setYfCancerDiagnosisDate(yfCancerDiagnosisDate);
            }
        } catch (ParseException e) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }

        hospitalCancerDiagnoseVo.setCreateUser(doctorInfo.getId());

        try {
            hospitalCancerService.saveDiagnose(hospitalCancerDiagnoseVo);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG));
        }
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, null, null));
    }


    /*
     * 根据id查询C2癌症诊断表
     *
     * @author ${zongt}
     * @since v2.2.1
     */
    @RequestMapping(value = "/hospital/cancer/report/queryDiagnoseById", method = RequestMethod.POST)
    public String queryDiagnoseById(HttpServletRequest req, HttpServletResponse response, @RequestBody HospitalCancerRecordVo hospitalCancerRecordVo) throws ItSysException {
        printStartTag("/hospital/cancer/report/queryDiagnoseById");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
        log.info("@Controller 根据条件查询C2癌症诊断表，输入参数：{},登录名：{}", JSONUtils.toJson(hospitalCancerRecordVo),loginName);
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        hospitalCancerService.queryDiagnoseById(hospitalCancerRecordVo.getId(), doctorInfo.getCommunityDeptId(),doctorInfo.getAreaDeptId());
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, hospitalCancerService.queryDiagnoseById(hospitalCancerRecordVo.getId(), doctorInfo.getCommunityDeptId(),doctorInfo.getAreaDeptId()), null));
    }


    /*
     *  修改C2癌症诊断表
     *
     * @author ${zongt}
     * @since v2.2.1
     */
    @RequestMapping(value = "/hospital/cancer/updateDiagnose", method = RequestMethod.POST)
    public String updateDiagnose(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalCancerDiagnoseVo hospitalCancerDiagnoseVo) throws ItSysException {
        printStartTag("/hospital/cancer/updateDiagnose");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        log.info("@Controller 修改癌症诊断表 ，输入参数：{},登录名：{}", JSONUtils.toJson(hospitalCancerDiagnoseVo),loginName);

        //校验参数
        if(hospitalCancerDiagnoseVo.getId()==null||hospitalCancerDiagnoseVo.getCancerRecordId()==null||StringUtils.isEmpty(hospitalCancerDiagnoseVo.getSid())||StringUtils.isEmpty(hospitalCancerDiagnoseVo.getYsCancerReportDateToString())||StringUtils.isEmpty(hospitalCancerDiagnoseVo.getWriteTableDateToString())||StringUtils.isEmpty(hospitalCancerDiagnoseVo.getCheckYears())||StringUtils.isEmpty(hospitalCancerDiagnoseVo.getInvestigatorCode())||StringUtils.isEmpty(hospitalCancerDiagnoseVo.getTbTablePerson())||StringUtils.isEmpty(hospitalCancerDiagnoseVo.getQualityControlPerson())){
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE1, GlobalErrorCode.PARAMETER_ERR_MSG1));
        }
        //转化时间
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date writeTableDate = df.parse(hospitalCancerDiagnoseVo.getWriteTableDateToString());
            hospitalCancerDiagnoseVo.setWriteTableDate(writeTableDate);

            Date ysCancerReportDate = df.parse(hospitalCancerDiagnoseVo.getYsCancerReportDateToString());
            hospitalCancerDiagnoseVo.setYsCancerReportDate(ysCancerReportDate);
            if(!StringUtils.isEmpty(hospitalCancerDiagnoseVo.getYfCancerDiagnosisDateToString())){
                Date yfCancerDiagnosisDate = df.parse(hospitalCancerDiagnoseVo.getYfCancerDiagnosisDateToString());
                hospitalCancerDiagnoseVo.setYfCancerDiagnosisDate(yfCancerDiagnosisDate);
            }
        } catch (ParseException e) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }

        hospitalCancerDiagnoseVo.setCreateUser(doctorInfo.getId());

        try {
            hospitalCancerService.updateDiagnose(hospitalCancerDiagnoseVo);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return JSONUtils.toJson(new Response(GlobalErrorCode.HOSPITAL_CANCER_RECORD_UPDATE_EVENT_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG));
        }
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, null, null));
    }


    /*
     * 添加表C4：结直肠癌治疗信息摘录表
     *
     * @author ${zongt}
     * @since v2.2.1
     */
    @RequestMapping(value = "/hospital/cancer/addTreatmentInformation", method = RequestMethod.POST)
    public String addTreatmentInformation(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalColorectalCancerTreatmentInformationVo hospitalColorectalCancerTreatmentInformationVo) throws ItSysException {
        printStartTag("/hospital/cancer/addTreatmentInformation");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        log.info("@Controller 结直肠癌治疗信息摘录表 ，输入参数：{},登录名：{}", JSONUtils.toJson(hospitalColorectalCancerTreatmentInformationVo),loginName);

        //校验参数
        if(hospitalColorectalCancerTreatmentInformationVo.getExcerptPurpose()==null||hospitalColorectalCancerTreatmentInformationVo.getCancerRecordId()==null||StringUtils.isEmpty(hospitalColorectalCancerTreatmentInformationVo.getSid())||StringUtils.isEmpty(hospitalColorectalCancerTreatmentInformationVo.getTbTableDateToString())||StringUtils.isEmpty(hospitalColorectalCancerTreatmentInformationVo.getCheckYears())||StringUtils.isEmpty(hospitalColorectalCancerTreatmentInformationVo.getInvestigatorCode())||StringUtils.isEmpty(hospitalColorectalCancerTreatmentInformationVo.getTbTablePerson())||StringUtils.isEmpty(hospitalColorectalCancerTreatmentInformationVo.getQualityControlPerson())){
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE1, GlobalErrorCode.PARAMETER_ERR_MSG1));
        }
        //转化时间
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date tbTableDate = df.parse(hospitalColorectalCancerTreatmentInformationVo.getTbTableDateToString());
            hospitalColorectalCancerTreatmentInformationVo.setTbTableDate(tbTableDate);

            if(!StringUtils.isEmpty(hospitalColorectalCancerTreatmentInformationVo.getItem31ToString())){
                Date item31 = df.parse(hospitalColorectalCancerTreatmentInformationVo.getItem31ToString());
                hospitalColorectalCancerTreatmentInformationVo.setItem31(item31);
            }
            if(!StringUtils.isEmpty(hospitalColorectalCancerTreatmentInformationVo.getItem41ToString())){
                Date item41 = df.parse(hospitalColorectalCancerTreatmentInformationVo.getItem41ToString());
                hospitalColorectalCancerTreatmentInformationVo.setItem41(item41);
            }
            if(!StringUtils.isEmpty(hospitalColorectalCancerTreatmentInformationVo.getItem51ToString())){
                Date item51 = df.parse(hospitalColorectalCancerTreatmentInformationVo.getItem51ToString());
                hospitalColorectalCancerTreatmentInformationVo.setItem51(item51);
            }
        } catch (ParseException e) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }

        hospitalColorectalCancerTreatmentInformationVo.setCreateUser(doctorInfo.getId());

        try {
            hospitalCancerService.saveTreatmentInformation(hospitalColorectalCancerTreatmentInformationVo);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG));
        }
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, null, null));
    }

    /*
     * 修改结直肠癌治疗信息摘录表c4
     *
     * @author ${zl}
     * @since v2.2.3
     */
    @RequestMapping(value = "/hospital/cancer/updatereportC4", method = RequestMethod.POST)
    public String updatereportC4(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalColorectalCancerTreatmentInformationVo param) throws ItSysException {
        printStartTag("/hospital/cancer/updatereportc4");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        log.info("@Controller 修改结直肠癌治疗信息摘录表c4 ，输入参数：{},登录名：{}", JSONUtils.toJson(param),loginName);
        //校验参数  主表
        if(param.getId()==null||StringUtils.isEmpty(param.getSid())||StringUtils.isEmpty(param.getCheckYears())||StringUtils.isEmpty(param.getInvestigatorCode())||StringUtils.isEmpty(param.getTbTablePerson())||StringUtils.isEmpty(param.getQualityControlPerson())){
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE1, GlobalErrorCode.PARAMETER_ERR_MSG1));
        }
        //判断字表相关修改  外科操作表
        if(param.getHospitalCancerSurgicalOperationVos()!=null&&param.getHospitalCancerSurgicalOperationVos().size()>0){
            List<HospitalCancerSurgicalOperationVo> hospitalCancerSurgicalOperationVos = param.getHospitalCancerSurgicalOperationVos();
            for (HospitalCancerSurgicalOperationVo hospitalCancerSurgicalOperationVo:hospitalCancerSurgicalOperationVos) {
                //校验表单入参  外科操作代码 及 完成日期
                if(StringUtils.isEmpty(hospitalCancerSurgicalOperationVo.getSurgicalOperationCode())||StringUtils.isEmpty(hospitalCancerSurgicalOperationVo.getFinshDateToString())){
                    return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE1, GlobalErrorCode.PARAMETER_ERR_MSG1));
                }

            }
        }


/*        //判断字表相关修改  治疗信息表   zongtong注释
        if(param.getHospitalCancerTreatmentInformationVos()!=null&&param.getHospitalCancerTreatmentInformationVos().size()>0){
            List<HospitalCancerTreatmentInformationVo> hospitalCancerTreatmentInformationVos = param.getHospitalCancerTreatmentInformationVos();
            for (HospitalCancerTreatmentInformationVo hospitalCancerTreatmentInformationVo:hospitalCancerTreatmentInformationVos) {
                //校验表单入参  治疗医生姓名 所在医疗机构名称 地址 邮编 传真 电话1 电话2 病历号
                if(StringUtils.isEmpty(hospitalCancerTreatmentInformationVo.getDoctorName())||StringUtils.isEmpty(hospitalCancerTreatmentInformationVo.getMedicalInstitutionName())||StringUtils.isEmpty(hospitalCancerTreatmentInformationVo.getAddress())||StringUtils.isEmpty(hospitalCancerTreatmentInformationVo.getEmail())||StringUtils.isEmpty(hospitalCancerTreatmentInformationVo.getFax())||StringUtils.isEmpty(hospitalCancerTreatmentInformationVo.getTelPhone1())||StringUtils.isEmpty(hospitalCancerTreatmentInformationVo.getTelPhone2())||StringUtils.isEmpty(hospitalCancerTreatmentInformationVo.getBlNumber())){
                    return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE1, GlobalErrorCode.PARAMETER_ERR_MSG1));
                }
            }
        }*/


        param.setCreateUser(doctorInfo.getId());  //修改人吗？

        try {
            hospitalCancerService.updateReportC4(param);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG));
        }
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, null, null));
    }

    /*
     * 修改结直肠癌诊断信息摘录表c3
     *
     * @author ${zl}
     * @since v2.2.3
     */
    @RequestMapping(value = "/hospital/cancer/updatereportC3", method = RequestMethod.POST)
    public String updatereportC3(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalColorectalCancerDiagnoseInformationVo param) throws ItSysException {
        printStartTag("/hospital/cancer/updatereportc3");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        log.info("@Controller 修改结直肠癌诊断信息摘录表c3 ，输入参数：{},登录名：{}", JSONUtils.toJson(param),loginName);
        //校验参数
        if(StringUtils.isEmpty(param.getExcerptsDateToString())||StringUtils.isEmpty(param.getExcerptPersonId())||StringUtils.isEmpty(param.getResearchYear())||StringUtils.isEmpty(param.getInvestigatorCode())||StringUtils.isEmpty(param.getTbTablePerson())||StringUtils.isEmpty(param.getQualityControlPerson())){
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE1, GlobalErrorCode.PARAMETER_ERR_MSG1));
        }

        //判断字表相关修改  诊断评估表
        if(param.getHospitalInformationDiagnoseEvaluationVos()!=null&&param.getHospitalInformationDiagnoseEvaluationVos().size()>0){
            List<HospitalInformationDiagnoseEvaluationVo> hospitalInformationDiagnoseEvaluationVos = param.getHospitalInformationDiagnoseEvaluationVos();
            for (HospitalInformationDiagnoseEvaluationVo hospitalInformationDiagnoseEvaluationVo:hospitalInformationDiagnoseEvaluationVos) {
                //校验表单入参  诊断方法
                if(StringUtils.isEmpty(hospitalInformationDiagnoseEvaluationVo.getDiagnosticMethods())){
                    return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE1, GlobalErrorCode.PARAMETER_ERR_MSG1));
                }

            }
        }
        if(param.getReportUrls()!=null&&param.getReportUrls().size()>0){
            param.setReportUrlToString(JSONUtils.toJson(param.getReportUrls()));
        }

        param.setCreateUser(doctorInfo.getId());  //修改人吗？

        try {
            hospitalCancerService.updateReportC3(param);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG));
        }
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, null, null));
    }

    /*
     * 添加c3结直肠癌诊断信息摘录表
     *
     * @author ${zongt}
     * @since v2.2.1
     */
    @RequestMapping(value = "/hospital/cancer/addDiagnoseInformation", method = RequestMethod.POST)
    public String addDiagnoseInformation(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalColorectalCancerDiagnoseInformationVo hospitalColorectalCancerDiagnoseInformationVo) throws ItSysException {
        printStartTag("/hospital/cancer/addTreatmentInformation");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        log.info("@Controller 结直肠癌诊断信息摘录表 ，输入参数：{},登录名：{}", JSONUtils.toJson(hospitalColorectalCancerDiagnoseInformationVo),loginName);

        //校验参数
        if(hospitalColorectalCancerDiagnoseInformationVo.getCancerRecordId()==null||StringUtils.isEmpty(hospitalColorectalCancerDiagnoseInformationVo.getSid())||StringUtils.isEmpty(hospitalColorectalCancerDiagnoseInformationVo.getExcerptsDateToString())||StringUtils.isEmpty(hospitalColorectalCancerDiagnoseInformationVo.getExcerptPersonId())||StringUtils.isEmpty(hospitalColorectalCancerDiagnoseInformationVo.getResearchYear())||StringUtils.isEmpty(hospitalColorectalCancerDiagnoseInformationVo.getInvestigatorCode())||StringUtils.isEmpty(hospitalColorectalCancerDiagnoseInformationVo.getTbTablePerson())||StringUtils.isEmpty(hospitalColorectalCancerDiagnoseInformationVo.getQualityControlPerson())){
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE1, GlobalErrorCode.PARAMETER_ERR_MSG1));
        }
        if(hospitalColorectalCancerDiagnoseInformationVo.getReportUrls()!=null&&hospitalColorectalCancerDiagnoseInformationVo.getReportUrls().size()>0){
            hospitalColorectalCancerDiagnoseInformationVo.setReportUrlToString(JSONUtils.toJson(hospitalColorectalCancerDiagnoseInformationVo.getReportUrls()));
        }
        hospitalColorectalCancerDiagnoseInformationVo.setCreateUser(doctorInfo.getId());

        try {
            hospitalCancerService.saveDiagnoseInformation(hospitalColorectalCancerDiagnoseInformationVo);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG));
        }
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, null, null));
    }

    /*
     *    根据id查询表C3-结直肠癌诊断信息摘录表
     *
     * @author ${zongt}
     * @since v2.2.1
     */
    @RequestMapping(value = "/hospital/cancer/queryDiagnoseInformationbyId", method = RequestMethod.POST)
    public String queryDiagnoseInformationbyId(HttpServletRequest req, HttpServletResponse response, @RequestBody HospitalColorectalCancerDiagnoseInformationVo param) throws ItSysException {
        printStartTag("/hospital/cancer/queryDiagnoseInformationbyId");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
        log.info("@Controller C3-结直肠癌诊断信息摘录表 ，输入参数：{},登录名：{}", JSONUtils.toJson(param),loginName);
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, hospitalCancerService.queryDiagnoseInformationbyId(param.getId(), doctorInfo.getCommunityDeptId(),doctorInfo.getAreaDeptId()), null));
    }

    /*
     *  根据id查询结直肠癌治疗信息摘录表及从表信息
     *
     * @author ${zl}
     * @since v2.2.1
     */
    @RequestMapping(value = "/hospital/cancer/report/queryreportC4byid", method = RequestMethod.POST)
    public String queryreportC4byid(HttpServletRequest req, HttpServletResponse response, @RequestBody HospitalColorectalCancerTreatmentInformationVo param) throws ItSysException {
        printStartTag("/hospital/cancer/report/queryreportC4byid");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
        log.info("@Controller 根据条件查询结直肠癌治疗信息摘录表 ，输入参数：{},登录名：{}", JSONUtils.toJson(param),loginName);
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, hospitalCancerService.queryReportC4ById(param.getId(), doctorInfo.getCommunityDeptId(),doctorInfo.getAreaDeptId()), null));
    }

    /**
     * c3文件上传操作
     * @param files
     * @param request
     * @return
     * @author ${zl}
     * @since v2.2.1
     */
    @RequestMapping(value = "/hospital/cancer/fileUpload", method = RequestMethod.POST)
    @ResponseBody
    public Result upload(@RequestParam("files") MultipartFile[] files, HttpServletRequest request) {
        log.debug("传入的文件参数：{}", JSON.toJSONString(files, true));
        Integer id=null;
//        if(!StringUtils.isEmpty(request.getParameter("id"))){
//            id = Integer.parseInt(request.getParameter("id"));
//        }
        if (files!=null&&files.length>0) {
            String loginName = CookieUtil.getCookieByLoginName(request);
            List<FileUploadLogPO> fileUploadLogPOs = fileUploadService.upLoadCancerFiles(files, loginName, Constans.FILE_SOURCE_TYPE_7,id);
            return ResultUtil.success(fileUploadLogPOs, "文件上传成功");
        } else {
            return ResultUtil.error(GlobalErrorCode.FILE_UPLOAD_FAIL_CODE, GlobalErrorCode.FILE_UPLOAD_FAIL_MSG);
        }
    }
    @Override
    protected String getClassName() {
        return HospitalCancerController.class.getName();
    }
}
