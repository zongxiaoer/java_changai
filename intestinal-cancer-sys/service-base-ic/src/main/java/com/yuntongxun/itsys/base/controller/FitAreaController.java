package com.yuntongxun.itsys.base.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yuntongxun.itsys.base.cache.RedisManager;
import com.yuntongxun.itsys.base.common.constans.RedisConstant;
import com.yuntongxun.itsys.base.common.util.*;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
import com.yuntongxun.itsys.base.service.UserService;
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

import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.po.HospitalReview;
import com.yuntongxun.itsys.base.service.ColonoscopyService;
import com.yuntongxun.itsys.base.service.FitService;
import com.yuntongxun.itsys.base.service.PersonService;
import com.yuntongxun.itsys.base.vo.ColonoscopyIssueVo;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
import com.yuntongxun.itsys.base.vo.FitResultVo;
import com.yuntongxun.itsys.base.vo.Response;
import com.yuntongxun.itsys.base.vo.Result;

/**
 * 肠镜管理
 *
 * @author zhangzl
 */
@RestController
public class FitAreaController extends AbstractController {

    @Autowired
    private FitService fitService;
    private final Logger log = LogManager.getLogger(FitAreaController.class);

    @Autowired
    private PersonService personService;

    @Autowired
    private ColonoscopyService colonoscopyService;

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private UserService userService;

    /**
     * 1.4.1、根据条件查询受试者接口；
     *
     * @param req
     * @param resp
     * @param {    "sid":"TC0001", "name":"张三", "phone":"TC0001", "group":"A",
     *             "codeEntryStatus":1,//编码录入状态1：未录入，2：已录入
     *             "insert_status":1,//FIT结果录入状态 1：未录入，2：已录入 ,3:待审核
     *             "result":1,//FIT结果状态1 阳性 2阴性 3无效 4无结果 }
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/area/fit/result/query", method = RequestMethod.POST)
    public Result query(HttpServletRequest req, HttpServletResponse resp, @RequestBody FitResultVo queryCondition)
            throws ItSysException {
        log.info("根据条件查询受试者接口(地区)");
        log.info("传入包体:{}", queryCondition);

        String loginName = CookieUtil.getCookieByLoginName(req);
        // FitResultVo queryCondition = null;
        try {
            // queryCondition = JSONUtils.toBean(body, FitResultVo.class);
        } catch (Exception e) {
            throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, "Parse request body error.");
        }

        ListPageUtil listPage = fitService.queryArea(queryCondition, loginName);
        Result result = new Result(listPage.getResultList(), listPage.getPageInfo());
        printEndTag("根据条件查询受试者接口(地区)");
        log.info("返回查询对象个数={}", listPage.getResultList().size());
        return result;
    }

    /**
     * 1.4.2、录入FIT编码接口（噗噗管ID）(地区)；
     *
     * @param req
     * @param resp
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/area/fit/result/code/entry", method = RequestMethod.POST)
    public Result areaEntryCode(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body)
            throws ItSysException {
        log.info("录入FIT编码(地区)");
        log.info("传入包体，body:{}", body);
        String loginName = CookieUtil.getCookieByLoginName(req);
        FitResultVo vo = null;
        try {
            vo = JSONUtils.toBean(body, FitResultVo.class);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date releaseDate = df.parse(vo.getReleaseDate());
            vo.setReleaseDateForSql(releaseDate);
            vo.setInsertStatus(1);
        } catch (Exception e) {
            throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, "Parse request body error.");
        }

        // 校验噗噗id是否存在
        FitResultVo fitResultVoByFitCode = fitService.queryByFit_code(vo.getFitCode());
        if (fitResultVoByFitCode != null) {
            log.info("/fit/result/addFit  噗噗管id已经存在");
            return new Result(GlobalErrorCode.ERR_HOSPITAL_FIT_RESULT_FIT_CODE_ERROR_CODE,
                    GlobalErrorCode.ERR_HOSPITAL_FIT_RESULT_FIT_CODE_ERROR_MSG);
        }
        fitService.areaEntryCode(vo, loginName);
        Result result = new Result();
        log.info("返回查询对象：{}", JSONUtils.toJson(result));
        printEndTag("录入FIT编码(地区)");
        return result;
    }


    /*
     * 修改fit编码
     *
     * @author ${zongt}
     * @since v1.0.0
     */
    @RequestMapping(value = "/area/fit/result/code/updateEntry", method = RequestMethod.POST)
    public String updateEntry(HttpServletRequest req, HttpServletResponse resp, @RequestBody FitResultVo vo)
            throws ItSysException {
        log.info("录入FIT编码(地区)");
        log.info("传入包体，body:{}", vo);
        String loginName = CookieUtil.getCookieByLoginName(req);
        DoctorInfo doctorInfo = new DoctorInfo();
        String infoString = redisManager.get(RedisConstant.HOSPITAL_KEY_INFO + loginName);
        if (StringUtil.isEmpty(infoString)) {
            doctorInfo = userService.getHospitalInfo(loginName);
        } else {
            doctorInfo = JSONUtils.toBean(infoString, DoctorInfo.class);
        }

        boolean isArea=false;

        if(doctorInfo.getCommunityDeptId()==null){
            isArea=true;
        }
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date releaseDate = df.parse(vo.getReleaseDate());
            vo.setReleaseDateForSql(releaseDate);
            vo.setInsertStatus(1);
        } catch (Exception e) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_BODY_ERRO,
                    "Parse request body error."));
        }
        //根据id获取fit信息
        FitResultVo fitResultVo = fitService.queryById(vo.getId());

        if(fitResultVo==null){
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE,
                    GlobalErrorCode.PARAMETER_ERR_MSG));
        }

        if (!fitResultVo.getFitCode().equals(vo.getFitCode())) {
            // 校验噗噗id是否存在
            FitResultVo fitResultVoByFitCode = fitService.queryByFit_code(vo.getFitCode());
            if (fitResultVoByFitCode != null) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_FIT_RESULT_FIT_CODE_ERROR_CODE,
                        GlobalErrorCode.ERR_HOSPITAL_FIT_RESULT_FIT_CODE_ERROR_MSG));
            }
        }
        HospitalReferenceRecordDto hospitalReferenceRecordDto = new HospitalReferenceRecordDto();
        hospitalReferenceRecordDto.setStatus(Constans.HOSPITAL_REFERENCE_RECORD_STATUS_FINISH);
        hospitalReferenceRecordDto.setDataId(vo.getId());
        hospitalReferenceRecordDto.setFormType(Constans.HOSPITAL_FIT_RESULT);
        hospitalReferenceRecordDto.setEditPerson(loginName);
        String dataText = JSONUtils.toJson(fitResultVo);
        hospitalReferenceRecordDto.setOldData(dataText);
        try{
            fitService.updateFitCode(vo,hospitalReferenceRecordDto,isArea);
        }catch (Exception e){
            return JSONUtils.toJson(new Response(GlobalErrorCode.HOSPITAL_MESSAGE_CENTER_UPDATE_ERROR_CODE,
                    GlobalErrorCode.HOSPITAL_MESSAGE_CENTER_UPDATE_ERROR_MSG));
        }
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, null, null));
    }

    /**
     * 1.4.3、录入FIT结果接口(地区)；
     *
     * @param req
     * @param resp
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/area/fit/result/add", method = RequestMethod.POST)
    public Result areaEntryResult(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body)
            throws ItSysException {
        log.info("录入FIT结果(地区)");
        log.info("传入包体，body:{}", body);
        String loginName = CookieUtil.getCookieByLoginName(req);
        FitResultVo vo = null;
        try {
            vo = JSONUtils.toBean(body, FitResultVo.class);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date resultDate = df.parse(vo.getResultDate());
            vo.setResultDateForSql(resultDate);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, "Parse request body error.");
        }
        //根据id获取fit信息
        FitResultVo fitResultVo = fitService.queryById(vo.getId());


        if(fitResultVo==null){
            throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, "Parse request body error.");
        }else {
            // 查询到信息但已有结果录入
            if (fitResultVo.getInsertStatus() != null
                    && fitResultVo.getInsertStatus() == Constans.HOSPITAL_VIOLATION_SCHEME_ENTRY_STATUS2) {
                log.info("synResult fitResult has already existed");
                throw new ItSysException(GlobalErrorCode.ERR_HOSPITAL_STOOL_FITRESULT_ERROR_CODE,
                        GlobalErrorCode.ERR_HOSPITAL_STOOL_FITRESULT_ERROR_MSG);
            }
        }
        vo.setEditoperationSource(fitResultVo.getEditoperationSource());
        vo.setOperationSourceId(fitResultVo.getOperationSourceId());

        vo = fitService.entryResult(vo);
        Result result = new Result(vo);
        log.info("返回查询对象：{}", JSONUtils.toJson(result));
        printEndTag("录入FIT结果(地区)");
        return result;
    }

    /**
     * 新增FIT编码(地区)；
     *
     * @param req
     * @param resp
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/area/fit/result/addFit", method = RequestMethod.POST)
    public String areaAddFit(HttpServletRequest req, HttpServletResponse resp, @RequestBody FitResultVo fitResultVo)
            throws ItSysException {
        log.info("录入FIT编码(地区)");
        log.info("传入包体，body:{}", fitResultVo.toString());

        if (StringUtils.isEmpty(fitResultVo.getSid()) || StringUtils.isEmpty(fitResultVo.getFitCode())
                || StringUtils.isEmpty(fitResultVo.getReleasePersonCode())
                || StringUtils.isEmpty(fitResultVo.getReleaseDate()) || fitResultVo.getFitCode().length() != 8) {
            return JSONUtils
                    .toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date releaseDate = df.parse(fitResultVo.getReleaseDate());
            fitResultVo.setReleaseDateForSql(releaseDate);
        } catch (Exception e) {
            throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, "Parse request body error.");
        }

        // 校验噗噗id是否存在
        FitResultVo fitResultVoByFitCode = fitService.queryByFit_code(fitResultVo.getFitCode());
        if (fitResultVoByFitCode != null) {
            log.info("/area/fit/result/addFit  噗噗噗id已经存在");
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_FIT_RESULT_FIT_CODE_ERROR_CODE,
                    GlobalErrorCode.ERR_HOSPITAL_FIT_RESULT_FIT_CODE_ERROR_MSG));
        }

        // 校验sid 是否存在
        HospitalReview hospitalReview;
        try {
            hospitalReview = personService.getBySid(fitResultVo.getSid());
        } catch (EmptyResultDataAccessException e) {
            log.info("getBySid hospital_intestine_review is error data is null");
            hospitalReview = null;
        } catch (IncorrectResultSizeDataAccessException e) {
            log.info("getBySid hospital_intestine_review is error data size is >1");
            hospitalReview = null;
        }
        if (hospitalReview == null) {
            log.info("/area/fit/result/addFit  对应受试者sid不存在");
            return JSONUtils
                    .toJson(new Response(GlobalErrorCode.ERR_PERSON_NULL_CODE, GlobalErrorCode.ERR_PERSON_NULL_MSG));
        }
        String loginName = CookieUtil.getCookieByLoginName(req);
        String sid = fitResultVo.getSid();
        // 判断是否是本区sid
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        Integer areaDeptId1 = doctorInfo.getAreaDeptId();
        //地区医师没有社区id,只根据地区查找
        List<HospitalReview> hospitalReviews = personService.queryBySidIDAndAreaDeptId(sid, areaDeptId1);
        if (hospitalReviews.size() < 1) {
            log.info("/area/fit/result/addFit  该地区不存在sid");
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_STOOL_VIEW_ERROR_CODE,
                    GlobalErrorCode.ERR_HOSPITAL_STOOL_VIEW_ERROR_MSG));
        }
        // 获取stage
        Integer communityDeptId = hospitalReview.getCommunityDeptId();
        Integer areaDeptId = hospitalReview.getAreaDeptId();
        Integer stageCy = hospitalReview.getStageCy();
        fitResultVo.setCommunityDeptId(communityDeptId);
        fitResultVo.setAreaDeptId(areaDeptId);
        fitResultVo.setStage(stageCy);
        fitResultVo.setOperationSource(Constans.OPERATION_SOURCE_TYPE);
        fitResultVo.setCodeEntryStatus(2);
        fitResultVo.setInsertStatus(1);
        fitResultVo.setEditoperationSource(Constans.ADD_NEW_HOSPITAL_FIT_RESULT);
        fitResultVo.setSid(fitResultVo.getSid().toUpperCase());
        // 新增
        Integer integer = fitService.saveFit(fitResultVo);
        if (integer > 0) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, null, null));
        }
        return JSONUtils.toJson(new Response(GlobalErrorCode.UNKNOWN_ERROR, GlobalErrorCode.UNKNOWN_ERROR_MSG));
    }

    @Override
    protected String getClassName() {
        return FitAreaController.class.getName();
    }
}
