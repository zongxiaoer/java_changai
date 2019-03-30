package com.yuntongxun.itsys.base.controller;

import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.*;
import com.yuntongxun.itsys.base.common.util.excelutil.ExcelIO;
import com.yuntongxun.itsys.base.common.util.excelutil.ExcelIOFactory;
import com.yuntongxun.itsys.base.common.util.excelutil.exception.ExcelIOException;
import com.yuntongxun.itsys.base.common.util.excelutil.export.bo.*;
import com.yuntongxun.itsys.base.po.*;
import com.yuntongxun.itsys.base.po.dto.ExeclData;
import com.yuntongxun.itsys.base.po.dto.HospitalReviewExport;
import com.yuntongxun.itsys.base.service.*;
import com.yuntongxun.itsys.base.vo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 导出管理
 *
 * @author zongt
 */
@RestController
public class ExportController extends AbstractController {

    private final Logger log = LogManager.getLogger(ExportController.class);

    @Autowired
    private BiologSampleService biologSampleService;

    @Autowired
    private ColonoscopyService colonoscopyService;

    @Autowired
    private PersonExportService personExportService;

    @Autowired
    private FitService fitService;

    @Autowired
    private ExcelService excelService;


    @Value("${execl.styleName}")
    private String styleName;


    @Value("${execl.isMergedRegion}")
    private Boolean isMergedRegion;

    /**
     * 导出血液样本列表
     *
     * @param req
     * @param response
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/sample/bloodSampleQueryExcel", method = RequestMethod.GET)
//hospital_biological_sample_result
    public void bloodSampleQueryExcel(HttpServletRequest req, HttpServletResponse response) throws ItSysException {
        printStartTag("/hospital/person/nationUsersQueryExcel");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
        log.info("@Controller导出excel查询生物样本列表登陆者账号 loginName：{}", loginName);

        //判断是否是本区sid
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo = new HospitalBiologicalSampleResultVo();
        hospitalBiologicalSampleResultVo.setSampleType(Constans.SAMPLE_TYPE6);
        hospitalBiologicalSampleResultVo.setAreaDeptId(doctorInfo.getAreaDeptId());
        hospitalBiologicalSampleResultVo.setCommunityDeptId(doctorInfo.getCommunityDeptId());
        ExcelData excelData = personExportService.bloodSampleQueryExcel(hospitalBiologicalSampleResultVo, response);
        try {

            ExportExcelUtil.excelOS(excelData);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        printEndTag("/hospital/sample/bloodSampleQueryExcel");
    }

    /**
     * 导出血液样本采集与快递信息列表   zhaoli   修改标题合并行
     *
     * @param req
     * @param response
     * @param execlData
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/excel/bloodSampleAndExpressQueryExcel", method = RequestMethod.POST)
//hospital_biological_sample_result
    public void bloodSampleAndExpressQueryExcel(HttpServletRequest req, HttpServletResponse response, @RequestBody ExeclData execlData) throws ItSysException {
        printStartTag("/hospital/excel/bloodSampleAndExpressQueryExcel");
        printHttpPacket(req, null);
        log.info("@Controller 接收血液样本采集与快递信息列表参数：{}", JSONUtils.toJson(execlData));
        HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo = new HospitalBiologicalSampleResultVo();
        hospitalBiologicalSampleResultVo.setSampleType(Constans.SAMPLE_TYPE6);
        ExcelData excelData = excelService.bloodSampleAndExpressQueryExcel(hospitalBiologicalSampleResultVo, response, execlData);
        try {
            ExportExcelUtil.excelOSCope(excelData);
//            ExportExcelUtil.excelOS(excelData);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        printEndTag("/hospital/excel/bloodSampleAndExpressQueryExcel");
    }

    /**
     * 导出生物样本列表（唾液/粪便）
     *
     * @param req
     * @param response
     * @param type     类型--唾液/粪便
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/sample/stoolSampleQueryExcel", method = RequestMethod.GET)
//hospital_biological_sample_result
    public void queryBiologicalSampleQueryExcel(HttpServletRequest req, HttpServletResponse response, @RequestParam String type) throws ItSysException {
        printStartTag("/hospital/sample/stoolSampleQueryExcel");
        printHttpPacket(req, null);

        if (type == null || (!"stool".equals(type) && !"saliva".equals(type))) {
            return;
        }
        String loginName = CookieUtil.getCookieByLoginName(req);
        log.info("@Controller导出excel查询生物样本列表登陆者账号 loginName：{}", loginName);
        ExcelData excelData = personExportService.queryBiologicalSampleResultExcel(loginName, type, response);
        try {
            ExportExcelUtil.excelOS(excelData);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        printEndTag("/hospital/sample/stoolSampleQueryExcel");
    }

    /**
     * 国家导出生物样本与快递信息列表（唾液/粪便）       zhaoli   修改标题合并行
     * type = stool  粪便  saliva 唾液
     * @param req
     * @param response
     * @param type
     * @param stage_cy
     * @param loginName
     * @param areaDeptId
     * @param communityDeptId
     * @param group
     * @param inGroupDateStart
     * @param inGroupDateEnd
     * @param siteId
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/sample/queryBiologicalSampleAndExpressQueryExcel", method = RequestMethod.GET)
    public void queryBiologicalSampleAndExpressQueryExcel(HttpServletRequest req, HttpServletResponse response, @RequestParam(value = "type") String type, @RequestParam(value = "stage_cy",required = false) Integer stage_cy,
                                                          @RequestParam(value = "loginName",required = false) String loginName, @RequestParam(value = "areaDeptId",required = false) Integer areaDeptId, @RequestParam(value = "communityDeptId",required = false) Integer communityDeptId,
                                                          @RequestParam(value = "group",required = false) String group, @RequestParam(value = "inGroupDateStart",required = false) String inGroupDateStart, @RequestParam(value = "inGroupDateEnd",required = false) String inGroupDateEnd,
                                                          @RequestParam(value = "siteId",required = false) Integer siteId) throws ItSysException {
        printStartTag("/hospital/sample/queryBiologicalSampleAndExpressQueryExcel");
        printHttpPacket(req, null);

        ExeclData execlData = new ExeclData();
        execlData.setType(type);
        execlData.setStage_cy(stage_cy);
        execlData.setLoginName(loginName);
        execlData.setAreaDeptId(areaDeptId);
        execlData.setCommunityDeptId(communityDeptId);
        execlData.setGroup(group);
        execlData.setInGroupDateStart(inGroupDateStart);
        execlData.setInGroupDateEnd(inGroupDateEnd);
        execlData.setSiteId(siteId);

        log.info("@Controller 接收导出生物样本与快递信息列表参数：{}", JSONUtils.toJson(execlData));
        if (execlData.getType() == null || (!"stool".equals(execlData.getType()) && !"saliva".equals(execlData.getType()))) {
            return;
        }
        ExcelData excelData = excelService.queryBiologicalSampleResultExcel(execlData, response);
        try {
            ExportExcelUtil.excelOSCope(excelData);
//            ExportExcelUtil.excelOS(excelData);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        printEndTag("/hospital/sample/queryBiologicalSampleAndExpressQueryExcel");
    }

    /*
     * fit导出
     *
     * @author ${zongt}
     * @since v2.2.1
     */
    @RequestMapping(value = "/hospital/excel/stoolFitQueryExcel", method = RequestMethod.POST)
//hospital_biological_sample_result
    public void stoolFitQueryExcel(HttpServletRequest req, HttpServletResponse response, @RequestBody ExeclData execlData) throws ItSysException {//,@RequestBody FitResultVo queryCondition
        printStartTag("/hospital/excel/stoolFitQueryExcel");
        printHttpPacket(req, null);
        log.info("@Controller 接受fit导入参数：{}", JSONUtils.toJson(execlData));
        List<FitResultVo> fitResultVos = fitService.queryCountryExecl(execlData);
        ExportBaseObject exportBaseObject = new ExportBaseObject();
        exportBaseObject.setExportResultList(fitResultVos);
        Sheet[] sheetArray = new Sheet[0];
        try {
            sheetArray = new Sheet[]{finallyTest2(exportBaseObject, "ExportFitConfig", "粪便潜血检验（噗噗管）发放和结果记录表")};
        } catch (ExcelIOException e) {
            e.printStackTrace();
        }
        ExcelIO excelio = ExcelIOFactory.getExcelIO();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/msexcel");
        String fileName = "粪便潜血检验（噗噗管）发放和结果记录表";
        try {
            response.setHeader("content-disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx\"");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        try {
            String path = "/Users/zongtong/Desktop/一楼相关信息/9月6号上线/fitQueryExcel.xlsx";
            excelio.exportExcel(response.getOutputStream(), styleName,
                    sheetArray, isMergedRegion);
        } catch (ExcelIOException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        printEndTag("/hospital/excel/stoolFitQueryExcel");
    }

    /**
     * 粪便DNA检测装置发放记录列表导出
     *
     * @param req
     * @param response
     * @param execlData
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/excel/stoolDnaQueryExcel", method = RequestMethod.POST)
    public void stoolDnaQueryExcel(HttpServletRequest req, HttpServletResponse response, @RequestBody ExeclData execlData) throws ItSysException {
        printStartTag("/hospital/excel/stoolDnaQueryExcel");
        printHttpPacket(req, null);
        log.info("@Controller 接收粪便DNA检测装置发放记录导入参数：{}", JSONUtils.toJson(execlData));
        List<StoolDna> stoolDnas = excelService.getDnaexcel(execlData);
        ExportBaseObject exportBaseObject = new ExportBaseObject();
        exportBaseObject.setExportResultList(stoolDnas);
        Sheet[] sheetArray = new Sheet[0];
        try {
            sheetArray = new Sheet[]{finallyTest2(exportBaseObject, "ExportDnaConfig", "粪便DNA检测装置发放记录表")};
        } catch (ExcelIOException e) {
            e.printStackTrace();
        }
        ExcelIO excelio = ExcelIOFactory.getExcelIO();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/msexcel");
        String fileName = "粪便DNA检测装置发放记录表";
        try {
            response.setHeader("content-disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx\"");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        try {
            excelio.exportExcel(response.getOutputStream(), styleName,
                    sheetArray, isMergedRegion);
        } catch (ExcelIOException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        printEndTag("/hospital/excel/stoolDnaQueryExcel");
    }


    /**
     * 筛查结果告知书发放记录列表
     *
     * @param req
     * @param response
     * @param execlData
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/excel/notificationRecordQueryExcel", method = RequestMethod.POST)
    public void notificationRecordQueryExcel(HttpServletRequest req, HttpServletResponse response, @RequestBody ExeclData execlData) throws ItSysException {
        printStartTag("/hospital/sample/notificationRecordQueryExcel");
        printHttpPacket(req, null);
        log.info("@Controller 筛查结果告知书发放记录导入参数：{}", JSONUtils.toJson(execlData));
        List<ColonoscopyVo> colonoscopyVos = excelService.getNotificationRecord(execlData);
        ExportBaseObject exportBaseObject = new ExportBaseObject();
        exportBaseObject.setExportResultList(colonoscopyVos);
        Sheet[] sheetArray = new Sheet[0];
        try {
            sheetArray = new Sheet[]{finallyTest2(exportBaseObject, "ExportNotificationConfig", "筛查结果告知书发放记录表")};
        } catch (ExcelIOException e) {
            e.printStackTrace();
        }
        ExcelIO excelio = ExcelIOFactory.getExcelIO();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/msexcel");
        String fileName = "筛查结果告知书发放记录表";
        try {
            response.setHeader("content-disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx\"");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        try {
            excelio.exportExcel(response.getOutputStream(), styleName,
                    sheetArray, isMergedRegion);
        } catch (ExcelIOException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        printEndTag("/hospital/excel/stoolReviewQueryExcel");
    }


    /**
     * 导出危险因素调查表
     *
     * @param req
     * @param response
     * @param execlData
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/excel/riskFactorQueryExcel", method = RequestMethod.POST)
    public void riskFactorQueryExcel(HttpServletRequest req, HttpServletResponse response, @RequestBody ExeclData execlData) throws ItSysException {
        printStartTag("/hospital/excel/riskFactorQueryExcel");
        printHttpPacket(req, null);
        log.info("@Controller 导出危险因素调查表导入参数：{}", JSONUtils.toJson(execlData));
        List<HospitalRiskFactorPO> hospitalRiskFactorPOS = excelService.getReviewRiskFactorDetails(execlData);
        ExportBaseObject exportBaseObject = new ExportBaseObject();
        exportBaseObject.setExportResultList(hospitalRiskFactorPOS);
        Sheet[] sheetArray = new Sheet[0];
        try {
            sheetArray = new Sheet[]{finallyTest2(exportBaseObject, "ExportRiskFactorConfig", "导出危险因素调查表")};
        } catch (ExcelIOException e) {
            e.printStackTrace();
        }
        ExcelIO excelio = ExcelIOFactory.getExcelIO();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/msexcel");
        String fileName = "导出危险因素调查表";
        try {
            response.setHeader("content-disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx\"");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        try {
            excelio.exportExcel(response.getOutputStream(), styleName,
                    sheetArray, isMergedRegion);
        } catch (ExcelIOException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        printEndTag("/hospital/excel/riskFactorQueryExcel");
    }

    /**
     * 导出违反方案表
     *
     * @param req
     * @param response
     * @param execlData
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/excel/violationSchemeQueryExcel", method = RequestMethod.POST)
    public void violationSchemeQueryExcel(HttpServletRequest req, HttpServletResponse response, @RequestBody ExeclData execlData) throws ItSysException {
        printStartTag("/hospital/excel/violationSchemeQueryExcel");
        printHttpPacket(req, null);
        log.info("@Controller 导出违反方案表导入参数：{}", JSONUtils.toJson(execlData));
        List<ViolationSchemePO> violationSchemePOs = excelService.getReviewViolationSchemeDetails(execlData);
        ExportBaseObject exportBaseObject = new ExportBaseObject();
        exportBaseObject.setExportResultList(violationSchemePOs);
        Sheet[] sheetArray = new Sheet[0];
        try {
            sheetArray = new Sheet[]{finallyTest2(exportBaseObject, "ExportViolationSchemeConfig", "导出违反方案表")};
        } catch (ExcelIOException e) {
            e.printStackTrace();
        }
        ExcelIO excelio = ExcelIOFactory.getExcelIO();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/msexcel");
        String fileName = "导出违反方案表";
        try {
            response.setHeader("content-disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx\"");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        try {
            excelio.exportExcel(response.getOutputStream(), styleName,
                    sheetArray, isMergedRegion);
        } catch (ExcelIOException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        printEndTag("/hospital/excel/violationSchemeQueryExcel");
    }

    /**
     * 导出肠镜病理结果表
     *
     * @param req
     * @param response
     * @param execlData
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/excel/pathologyQueryExcel", method = RequestMethod.POST)
    public void pathologyQueryExcel(HttpServletRequest req, HttpServletResponse response, @RequestBody ExeclData execlData) throws ItSysException {
        printStartTag("/hospital/excel/pathologyQueryExcel");
        printHttpPacket(req, null);
        log.info("@Controller 导出肠镜病理结果导入参数：{}", JSONUtils.toJson(execlData));
        List<PathologyExcelVO> pathologyExcelVOs = excelService.getPathologyQueryExcel(execlData);
        ColonoscopyPathologyResult colonoscopyPathologyResult = new ColonoscopyPathologyResult();
        List<ColonoscopyPathologyDiagnosisRecord> colonoscopyPathologyDiagnosisRecords = new ArrayList<>();
        colonoscopyPathologyResult.setColonoscopyPathologyDiagnosisRecordList(colonoscopyPathologyDiagnosisRecords);
        ExportBaseObject exportBaseObject = new ExportBaseObject();
        exportBaseObject.setExportResultList(pathologyExcelVOs);
        Sheet[] sheetArray = new Sheet[0];
        try {
            sheetArray = new Sheet[]{finallyTest2(exportBaseObject, "ExportPathologyConfig", "导出肠镜病理结果表")};
        } catch (ExcelIOException e) {
            e.printStackTrace();
        }
        ExcelIO excelio = ExcelIOFactory.getExcelIO();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/msexcel");
        String fileName = "导出肠镜病理结果表";
        try {
            response.setHeader("content-disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx\"");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        try {
            excelio.exportExcel(response.getOutputStream(), styleName,
                    sheetArray, isMergedRegion);
        } catch (ExcelIOException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        printEndTag("/hospital/excel/pathologyQueryExcel");
    }

    /*
     * 导出资格审核表
     *
     * @author ${zongt}
     * @since v2.2.1
     */
    @RequestMapping(value = "/hospital/excel/stoolReviewQueryExcel", method = RequestMethod.POST)
//hospital_biological_sample_result
    public void stoolReviewQueryExcel(HttpServletRequest req, HttpServletResponse response, @RequestBody ExeclData execlData) throws ItSysException {//,@RequestBody FitResultVo queryCondition
        printStartTag("/hospital/excel/stoolReviewQueryExcel");
        printHttpPacket(req, null);
        log.info("@Controller 接受stoolReviewQueryExcel导入参数：{}", JSONUtils.toJson(execlData));
        String path = "/Users/zongtong/Desktop/一楼相关信息/9月6号上线/stoolReviewQueryExcel.xlsx";
        List<HospitalReview> hospitalReviews = excelService.stoolReviewQueryExcel(execlData);
        ExportBaseObject exportBaseObject = new ExportBaseObject();
        exportBaseObject.setExportResultList(hospitalReviews);
        Sheet[] sheetArray = new Sheet[0];
        try {
            sheetArray = new Sheet[]{finallyTest2(exportBaseObject, "ExportReviewConfig", "资格审核表")};
        } catch (ExcelIOException e) {
            e.printStackTrace();
        }
        ExcelIO excelio = ExcelIOFactory.getExcelIO();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/msexcel");
        String fileName = "资格审核表";
        try {
            response.setHeader("content-disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx\"");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        try {
            excelio.exportExcel(response.getOutputStream(), styleName,
                    sheetArray, isMergedRegion);
        } catch (ExcelIOException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        printEndTag("/hospital/excel/stoolReviewQueryExcel");
    }


    /*
     *   导出筛查告知书详情
     *
     * @author ${zongt}
     * @since v2.2.1
     */
    @RequestMapping(value = "/hospital/excel/stoolNotificationQueryExcel", method = RequestMethod.POST)
//hospital_biological_sample_result
    public void stoolNotificationQueryExcel(HttpServletRequest req, HttpServletResponse response, @RequestBody ExeclData execlData) throws ItSysException {//,@RequestBody FitResultVo queryCondition
        printStartTag("/hospital/excel/stoolNotificationQueryExcel");//select t1.`name`,t2.*  from hospital_intestine_review t1,hospital_screening_notification t2 where t1.sid=t2.sid and t2.id=?";
        printHttpPacket(req, null);
        log.info("@Controller 接受stoolNotificationQueryExcel导入参数：{}", JSONUtils.toJson(execlData));
        //String path="/Users/zongtong/Desktop/一楼相关信息/9月6号上线/stoolNotificationQueryExcel.xlsx";
        List<ColonoscopyNotificationVo> colonoscopyNotificationVos = excelService.stoolNotificationQueryExcel(execlData);
        ExportBaseObject exportBaseObject = new ExportBaseObject();
        exportBaseObject.setExportResultList(colonoscopyNotificationVos);
        Sheet[] sheetArray = new Sheet[0];
        try {
            sheetArray = new Sheet[]{finallyTest2(exportBaseObject, "ExportNotificationMessageConfig", "筛查告知书信息")};
        } catch (ExcelIOException e) {
            e.printStackTrace();
        }
        ExcelIO excelio = ExcelIOFactory.getExcelIO();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/msexcel");
        String fileName = "筛查告知书信息";
        try {
            response.setHeader("content-disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx\"");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        try {
            excelio.exportExcel(response.getOutputStream(), styleName,
                    sheetArray, isMergedRegion);
        } catch (ExcelIOException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        printEndTag("/hospital/excel/stoolNotificationQueryExcel");
    }


    /*
     * 导出肠镜结果
     *
     * @author ${zongt}
     * @since v2.2.1
     */
    @RequestMapping(value = "/hospital/excel/stoolColonoscopyResultQueryExcel", method = RequestMethod.POST)
//hospital_biological_sample_result
    public void stoolColonoscopyResultQueryExcel(HttpServletRequest req, HttpServletResponse response, @RequestBody ExeclData execlData) throws ItSysException {//@RequestBody ExeclData execlData
        printStartTag("/hospital/excel/stoolColonoscopyResultQueryExcel");//select t1.`name`,t2.*  from hospital_intestine_review t1,hospital_screening_notification t2 where t1.sid=t2.sid and t2.id=?";
        printHttpPacket(req, null);
        log.info("@Controller stoolColonoscopyResultQueryExcel：{}", JSONUtils.toJson(execlData));
        String path = "/Users/zongtong/Downloads/stoolColonoscopyResultQueryExcel.xlsx";
        List<HospitalColonoscopyResultVo> hospitalColonoscopyResultVos = excelService.stoolColonoscopyResultQueryExcel(execlData);
        ExportBaseObject exportBaseObject = new ExportBaseObject();
        exportBaseObject.setExportResultList(hospitalColonoscopyResultVos);
        Sheet[] sheetArray = new Sheet[0];
        try {
            sheetArray = new Sheet[]{finallyTest2(exportBaseObject, "ColonoscopyResultConfig", "肠镜结果表")};
        } catch (ExcelIOException e) {
            e.printStackTrace();
        }
        ExcelIO excelio = ExcelIOFactory.getExcelIO();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/msexcel");
        String fileName = "肠镜结果表";
        try {
            response.setHeader("content-disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx\"");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        try {
            excelio.exportExcel(response.getOutputStream(), styleName,
                    sheetArray, isMergedRegion);
        } catch (ExcelIOException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        printEndTag("/hospital/excel/stoolColonoscopyResultQueryExcel");
    }


    private static Sheet finallyTest2(final ExportBaseObject exportBaseObject, final String exprotFormatName, final String sheetName) throws ExcelIOException {
        return new Sheet() {

            public ExportObject getExportObject() {
                return exportBaseObject;
            }

            public String getExprotFormatName() {
                return exprotFormatName;
            }

            public String getSheetName() {
                return sheetName;
            }

        };
    }


    /*
     * 导出血液样本列表
     *
     * @author ${zongt}
     * @since v2.2.1
     */
    @RequestMapping(value = "/hospital/excel/stollbloodSampleQueryExcel", method = RequestMethod.POST)
//hospital_biological_sample_result
    public void stollbloodSampleQueryExcel(HttpServletRequest req, HttpServletResponse response, @RequestBody ExeclData execlData) throws ItSysException {
        printStartTag("/hospital/excel/stollbloodSampleQueryExcel");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
        log.info("@Controller导出excel查询生物样本", JSONUtils.toJson(execlData));
        execlData.setType(Constans.SAMPLE_TYPE6);
        ExcelData excelData = personExportService.stollbloodSampleQueryExcel(execlData, response);
        try {
            ExportExcelUtil.excelOS(excelData);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        printEndTag("/hospital/excel/stollbloodSampleQueryExcel");
    }


    /*
     * 导出受试者列表              zhaoli   修改标题合并行
     *
     * @author ${zongt}
     * @since v2.2.1
     */
    @RequestMapping(value = "/hospital/excel/stollnationUsersQueryExcel", method = RequestMethod.POST)
    public void stollnationUsersQueryExcel(HttpServletRequest req, HttpServletResponse response, @RequestBody ExeclData execlData) throws ItSysException {
        printStartTag("/hospital/excel/stollnationUsersQueryExcel");
        printHttpPacket(req, null);
        log.info("@Controller导出excel查询受试者列表", JSONUtils.toJson(execlData));
        HospitalReviewExport hospitalReviewExport = new HospitalReviewExport();
        ExcelData excelData = personExportService.stollnationUsersQueryExcel(execlData, response);
        try {
            ExportExcelUtil.excelOSCope(excelData);
//            ExportExcelUtil.excelOS(excelData);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        printEndTag("/hospital/excel/stollnationUsersQueryExcel");
    }


    /*
     *  国家肠镜管理列表        zhaoli   修改标题合并行
     *
     * @author ${zongt}
     * @since v2.2.1
     */
    @RequestMapping(value = "/hospital/excel/queryFoStollCountryCJExcel", method = RequestMethod.POST)
    public void queryFoStollCountryCJExcel(HttpServletRequest req, HttpServletResponse response, @RequestBody ExeclData execlData) throws ItSysException {
        printStartTag("国家肠镜管理列表==》start /hospital/excel/queryFoStollCountryCJExcel");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
        log.info("@Controller导出excel国家肠镜数据管理导出 ");
        ColonoscopyVo queryCondition = new ColonoscopyVo();
        List<ColonoscopyVo> list = personExportService.queryFoStollCountryCJExcel(execlData);
        //导出逻辑
        String[][] array = new String[][]{};
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/msexcel");
        String fileName = "国家肠镜管理列表";
        try {
            response.setHeader("content-disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx\"");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        //获得表头
        Vector titleVec = new Vector();
        titleVec.add("SID");
        titleVec.add("姓名");
        titleVec.add("性别");
        titleVec.add("年龄");
        titleVec.add("手机号");
        titleVec.add("所属地区");
        titleVec.add("所属区");
        titleVec.add("所属社区");
        titleVec.add("分组");
        titleVec.add("入组日期");
        titleVec.add("年度状态");
        titleVec.add("预约状态");
        titleVec.add("预约时间");
        titleVec.add("检查状态");
        titleVec.add("完成情况");
        titleVec.add("检查时间");
        titleVec.add("肠镜结果");
        titleVec.add("病理结果");
        titleVec.add("告知书结果");
        titleVec.add("告知书发放");
        /*titleVec.add("创建人");*/
        String areaName = "";
        //为表的每一列设定列宽.
        int[] titleWidthAry = {15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15};
        if (list != null && list.size() > 0) {
            array = new String[list.size()][20];
            for (int i = 0; i < list.size(); i++) {
                ColonoscopyVo review = list.get(i);
                array[i][0] = review.getSid() != null ? review.getSid() : "";
                array[i][1] = review.getName() != null ? review.getName() : "";
                if (review.getSex() != null) {
                    if (review.getSex() == 1) {
                        array[i][2] = "男";
                    } else if (review.getSex() == 2) {
                        array[i][2] = "女";
                    }
                } else {
                    array[i][2] = review.getSex() != null ? review.getSex().toString() : "";
                }
                array[i][3] = review.getAge() != null ? review.getAge().toString() : "";
                array[i][4] = review.getPhone() != null ? review.getPhone() : "";
                array[i][5] = review.getAreaName() != null ? review.getAreaName() : "";
                array[i][6] = review.getDepName() != null ? review.getDepName() : "";
                array[i][7] = review.getCreateUser() != null ? review.getCreateUser().toString() : "";
                if (review.getGroup() != null && !review.getGroup().equals("")) {
                    if (review.getGroup().equals("A")) {
                        array[i][8] = "A组";
                    } else if (review.getGroup().equals("B")) {
                        array[i][8] = "B组";
                    } else if (review.getGroup().equals("C")) {
                        array[i][8] = "C组";
                    } else if (review.getGroup().equals("Cg")) {
                        array[i][8] = "C组高危";
                    } else if (review.getGroup().equals("Cd")) {
                        array[i][8] = "C组低危";
                    } else if (review.getGroup().equals("Cp")) {
                        array[i][8] = "C组未评估";
                    }
                } else {
                    array[i][8] = review.getGroup() != null ? review.getGroup() : "";
                }
                array[i][9] = review.getInGroupDate() != null ? DateUtil.formatDate(review.getInGroupDate(), "yyyy-MM-dd") : "";
                if (review.getOverallStatusCy() != null) {
                    if (review.getOverallStatusCy() == 1) {
                        array[i][10] = "正常";
                    } else if (review.getOverallStatusCy() == 2) {
                        array[i][10] = "退出";
                    } else if (review.getOverallStatusCy() == 3) {
                        array[i][10] = "肠癌";
                    } else if (review.getOverallStatusCy() == 4) {
                        array[i][10] = "死亡";
                    }
                } else {
                    array[i][10] = review.getOverallStatusCy() != null ? review.getOverallStatusCy().toString() : "";
                }
                if (review.getReserveStatus() != null && !review.getReserveStatus().equals("")) {
                    if (review.getReserveStatus().equals("1")) {
                        array[i][11] = "未预约";
                    } else if (review.getReserveStatus().equals("2")) {
                        array[i][11] = "已预约";
                    }
                } else {
                    array[i][11] = review.getReserveStatus() != null ? review.getReserveStatus().toString() : "";
                }
                array[i][12] = review.getReserveDate() != null ? DateUtil.formatDate(review.getReserveDate(), "yyyy-MM-dd") : "";
                if (review.getExaminationStatus() != null && !review.getExaminationStatus().equals("")) {
                    if (review.getExaminationStatus().equals("1")) {
                        array[i][13] = "未就诊";
                    } else if (review.getExaminationStatus().equals("2")) {
                        array[i][13] = "已就诊";
                    }
                } else {
                    array[i][13] = review.getExaminationStatus() != null ? review.getExaminationStatus().toString() : "";
                }
                if (review.getFinishedStatus() != null && !review.getFinishedStatus().equals("")) {
                    if (review.getFinishedStatus().equals("1")) {
                        array[i][14] = "未完成";
                    } else if (review.getFinishedStatus().equals("2")) {
                        array[i][14] = "已完成";
                    }
                } else {
                    array[i][14] = review.getFinishedStatus() != null ? review.getFinishedStatus().toString() : "";
                }
                /*array[i][13] = review.getExaminationDate() != null ? DateUtil.dateToStr(review.getExaminationDate(),11) : "";*/
                array[i][15] = review.getExaminationDate() != null ? DateUtil.formatDate(review.getExaminationDate(), "yyyy-MM-dd") : "";
                if (review.getResultStatus() != null && !review.getResultStatus().equals("")) {
                    if (review.getResultStatus().equals("1")) {
                        array[i][16] = "未录入";
                    } else if (review.getResultStatus().equals("2")) {
                        array[i][16] = "已录入";
                    }
                } else {
                    array[i][16] = review.getResultStatus() != null ? review.getResultStatus().toString() : "";
                }
                if (review.getPathologyStatus() != null && !review.getPathologyStatus().equals("")) {
                    if (review.getPathologyStatus().equals("1")) {
                        array[i][17] = "未录入";
                    } else if (review.getPathologyStatus().equals("2")) {
                        array[i][17] = "已录入";
                    }
                } else {
                    array[i][17] = review.getPathologyStatus() != null ? review.getPathologyStatus().toString() : "";
                }
                if (review.getNotificationEntryStatus() != null && !review.getNotificationEntryStatus().equals("")) {
                    if (review.getNotificationEntryStatus().equals("1")) {
                        array[i][18] = "未录入";
                    } else if (review.getNotificationEntryStatus().equals("2")) {
                        array[i][18] = "已录入";
                    }
                } else {
                    array[i][18] = review.getNotificationEntryStatus() != null ? review.getNotificationEntryStatus().toString() : "";
                }
                if (review.getNotificationIssueStatus() != null && !review.getNotificationIssueStatus().equals("")) {
                    if (review.getNotificationIssueStatus().equals("1")) {
                        array[i][19] = "未发放";
                    } else if (review.getNotificationIssueStatus().equals("2")) {
                        array[i][19] = "已发放";
                    }
                } else {
                    array[i][19] = review.getNotificationIssueStatus() != null ? review.getNotificationIssueStatus().toString() : "";
                }
                /*array[i][18] = review.getCreateUser() != null ? review.getCreateUser().toString() : "";*/
                areaName = review.getAreaName();
            }
        }
        try {
            ExcelData excelData = new ExcelData();
            excelData.setHeadStr("国家肠镜管理列表");
            excelData.setTitleVec(titleVec);
            excelData.setTitleWidthAry(titleWidthAry);
            excelData.setBodyAry(array);
            excelData.setSheetName("国家肠镜管理列表");
            excelData.setOs(response.getOutputStream());
            ExportExcelUtil.excelOSCope(excelData);
//            ExportExcelUtil.excelOS(excelData);
            //ExportExcelUtil.excelOS("国家肠镜管理列表", titleVec, titleWidthAry, array, response.getOutputStream(), areaName);
        } catch (IOException e) {
            e.printStackTrace();
            log.info(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
        printEndTag("/hospital/excel/queryFoStollCountryCJExcel");
    }

    @Override
    protected String getClassName() {
        return null;
    }

    public static void main(String[] args) {
        String path = "/Users/zongtong/Desktop/一楼相关信息/9月6号上线/stoolColonoscopyResultQueryExcel.xlsx";
        // List<HospitalColonoscopyResultVo> hospitalColonoscopyResultVos = excelService.stoolColonoscopyResultQueryExcel(execlData);
        HospitalColonoscopyResultVo hospitalColonoscopyResultVo = new HospitalColonoscopyResultVo();
        hospitalColonoscopyResultVo.setSid("a");
        ColonoscopyLesionsRecordVo colonoscopyLesionsRecordVo = new ColonoscopyLesionsRecordVo();
        colonoscopyLesionsRecordVo.setItem1(1);
        ColonoscopyLesionsRecordVo colonoscopyLesionsRecordVo1 = new ColonoscopyLesionsRecordVo();
        colonoscopyLesionsRecordVo1.setItem1(2);
        List<ColonoscopyLesionsRecordVo> asa = new ArrayList<>();
        asa.add(colonoscopyLesionsRecordVo);
        asa.add(colonoscopyLesionsRecordVo1);

        hospitalColonoscopyResultVo.setLesionsRecordList(asa);
        List<HospitalColonoscopyResultVo> hospitalColonoscopyResultVos = new ArrayList<>();
        hospitalColonoscopyResultVos.add(hospitalColonoscopyResultVo);
        ExportBaseObject exportBaseObject = new ExportBaseObject();
        exportBaseObject.setExportResultList(hospitalColonoscopyResultVos);
        Sheet[] sheetArray = new Sheet[0];
        try {
            sheetArray = new Sheet[]{finallyTest2(exportBaseObject, "ColonoscopyResultConfig", "肠镜结果表")};
        } catch (ExcelIOException e) {
            e.printStackTrace();
        }
        ExcelIO excelio = ExcelIOFactory.getExcelIO();

        try {
            excelio.exportExcel(path, "",
                    sheetArray, false);
        } catch (ExcelIOException e) {
            e.printStackTrace();
        }
    }
}
