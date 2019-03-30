package com.yuntongxun.itsys.base.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.yuntongxun.itsys.base.cache.RedisManager;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.constans.RedisConstant;
import com.yuntongxun.itsys.base.common.util.*;
import com.yuntongxun.itsys.base.po.FileUploadLogPO;
import com.yuntongxun.itsys.base.po.FitSynLogPO;
import com.yuntongxun.itsys.base.po.HospitalReview;
import com.yuntongxun.itsys.base.po.Role;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
import com.yuntongxun.itsys.base.service.*;
import com.yuntongxun.itsys.base.vo.*;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.web.bind.annotation.*;

import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import org.springframework.web.multipart.MultipartFile;

/**
 * 肠镜管理
 *
 * @author zhangzl
 */
@RestController
public class FitController extends AbstractController {

    @Autowired
    private PersonService personService;

    @Autowired
    private ColonoscopyService colonoscopyService;


    @Autowired
    private RedisManager redisManager;

    @Autowired
    private UserService userService;

    @Autowired
    private FitService fitService;

    @Autowired
    private FileUploadService fileUploadService;
    private final Logger log = LogManager.getLogger(FitController.class);

    /**
     * 1.4.1、根据条件查询受试者接口；
     *
     * @param req
     * @param resp
     * @param body,{ "sid":"TC0001",
     *               "name":"张三",
     *               "phone":"TC0001",
     *               "group":"A",
     *               "codeEntryStatus":1,//编码录入状态1：未录入，2：已录入
     *               "insert_status":1,//FIT结果录入状态  1：未录入，2：已录入 ,3:待审核
     *               "result":1,//FIT结果状态1 阳性 2阴性 3无效 4无结果
     *               }
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/fit/result/query", method = RequestMethod.POST)
    public Result query(HttpServletRequest req, HttpServletResponse resp, @RequestBody FitResultVo queryCondition) throws ItSysException {
        log.info("根据条件查询受试者接口");
        log.info("传入包体:{}", queryCondition.toString());

        String loginName = CookieUtil.getCookieByLoginName(req);
        ListPageUtil listPage = fitService.query(queryCondition, loginName);
        Result result = new Result(listPage.getResultList(), listPage.getPageInfo());
        printEndTag("根据条件查询受试者接口");
        log.info("返回查询对象个数={}", listPage.getResultList().size());
        return result;
    }

    /*
     * 根据id查询fit信息
     *
     * @author ${zongt}
     * @since v1.0.0
     */
    @RequestMapping(value = "/fit/result/queryById", method = RequestMethod.POST)
    public String queryById(HttpServletRequest req, HttpServletResponse resp, @RequestBody FitResultVo queryCondition) throws ItSysException {
        //校验这条fit数据是否存在
        FitResultVo fitResultVoByFitCode = fitService.queryById(queryCondition.getId());
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, fitResultVoByFitCode, null));
    }

    /**
     * 1.4.2、录入FIT编码接口（噗噗管ID）；
     *
     * @param req
     * @param resp
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/fit/result/code/entry", method = RequestMethod.POST)
    public Result entryCode(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) throws ItSysException {
        log.info("录入FIT编码");
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


        //校验噗噗id是否存在
        FitResultVo fitResultVoByFitCode = fitService.queryByFit_code(vo.getFitCode());
        if (fitResultVoByFitCode != null) {
            log.info("/fit/result/addFit  噗噗噗id已经存在");
            return new Result(GlobalErrorCode.ERR_HOSPITAL_FIT_RESULT_FIT_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_FIT_RESULT_FIT_CODE_ERROR_MSG);
        }


        fitService.entryCode(vo, loginName);
        Result result = new Result();
        log.info("返回查询对象：{}", JSONUtils.toJson(result));
        printEndTag("录入FIT编码");
        return result;
    }

    /**
     * 1.4.3、录入FIT结果接口；
     *
     * @param req
     * @param resp
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/fit/result/add", method = RequestMethod.POST)
    public Result entryResult(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) throws ItSysException {
        log.info("录入FIT结果");
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
        printEndTag("录入FIT结果");
        return result;
    }

    /*
     * 修改fit结果
     *
     * @author ${zongt}
     * @since v1.0.0
     */
    @RequestMapping(value = "/fit/result/editFitResult", method = RequestMethod.POST)
    public String  editFitResult(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) throws ItSysException {
        log.info("修改FIT结果");
        log.info("传入包体，body:{}", body);
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
        vo.setEditoperationSource(fitResultVo.getEditoperationSource());
        vo.setOperationSourceId(fitResultVo.getOperationSourceId());
        HospitalReferenceRecordDto hospitalReferenceRecordDto = new HospitalReferenceRecordDto();
        hospitalReferenceRecordDto.setStatus(Constans.HOSPITAL_REFERENCE_RECORD_STATUS_FINISH);
        hospitalReferenceRecordDto.setDataId(vo.getId());
        hospitalReferenceRecordDto.setFormType(Constans.HOSPITAL_FIT_RESULT);
        hospitalReferenceRecordDto.setEditPerson(loginName);
        String dataText = JSONUtils.toJson(fitResultVo);
        hospitalReferenceRecordDto.setOldData(dataText);
        vo=fitService.updateFitResult(fitResultVo,vo,hospitalReferenceRecordDto,isArea);
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, vo, null));

    }


    /**
     *跟第三方同步fit结果信息
     *
     * @param req
     * @param resp
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/fit/result/synresult", method = RequestMethod.POST)
    public Result synResult(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) throws ItSysException {
        log.info("第三方录入FIT结果");
        log.info("传入包体，body:{}", body);
        FitResultSynVo vo = null;
        try {
            vo = JSONUtils.toBean(body, FitResultSynVo.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, "Parse request body error.");
        }
        FitSynLogPO synLogPO = new FitSynLogPO();
        synLogPO.setDateCreated(new Date());
        synLogPO.setParamValue(body);
        try {
            Integer fitId = fitService.synResult(vo);
            synLogPO.setFitId(fitId);
            synLogPO.setResult(1);
            synLogPO.setResult_cont("succ");
        }catch (ItSysException e){
            synLogPO.setResult(2);
            synLogPO.setResult_code(e.getCode());
            synLogPO.setResult_cont(e.getMessage());
            //throw new ItSysException(e.getCode(),e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            synLogPO.setResult(2);
            synLogPO.setResult_code(GlobalErrorCode.RUNTIME_ERROR_CODE);
            synLogPO.setResult_cont(GlobalErrorCode.RUNTIME_ERROR_MSG);
            //throw new ItSysException(GlobalErrorCode.RUNTIME_ERROR_CODE, GlobalErrorCode.RUNTIME_ERROR_MSG);
        }
        //记录同步日志
        fitService.addFitSynLog(synLogPO);
        if(synLogPO.getResult()==2){
            throw new ItSysException(synLogPO.getResult_code(), synLogPO.getResult_cont());
        }
        Result result = new Result();
        printEndTag("第三方录入FIT结果");
        return result;
    }




    /**
     * 新增FIT编码；
     *
     * @param req
     * @param resp
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/fit/result/addFit", method = RequestMethod.POST)
    public String addFit(HttpServletRequest req, HttpServletResponse resp, @RequestBody FitResultVo fitResultVo) throws ItSysException {
        log.info("录入FIT编码");
        log.info("传入包体，body:{}", fitResultVo.toString());

        if (StringUtils.isEmpty(fitResultVo.getSid()) || StringUtils.isEmpty(fitResultVo.getFitCode()) || StringUtils.isEmpty(fitResultVo.getReleasePersonCode()) || StringUtils.isEmpty(fitResultVo.getReleaseDate())||fitResultVo.getFitCode().length()!=8) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date releaseDate = df.parse(fitResultVo.getReleaseDate());
            fitResultVo.setReleaseDateForSql(releaseDate);
        } catch (Exception e) {
            throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, "Parse request body error.");
        }


        //校验噗噗id是否存在
        FitResultVo fitResultVoByFitCode = fitService.queryByFit_code(fitResultVo.getFitCode());
        if (fitResultVoByFitCode != null) {
            log.info("/fit/result/addFit  噗噗噗id已经存在");
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_FIT_RESULT_FIT_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_FIT_RESULT_FIT_CODE_ERROR_MSG));
        }

        //校验sid 是否存在
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
            log.info("/fit/result/addFit  对应受试者sid不存在");
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_PERSON_NULL_CODE, GlobalErrorCode.ERR_PERSON_NULL_MSG));
        }

        String loginName = CookieUtil.getCookieByLoginName(req);
        String sid = fitResultVo.getSid();
        //判断是否是本区sid
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        Integer areaDeptId1 = doctorInfo.getAreaDeptId();
        Integer communityDeptId1 = doctorInfo.getCommunityDeptId();
        List<Role> roles = userService.queryRoleByUserId(doctorInfo.getId());
        String userName="";
        if(roles!=null&&roles.size()>0){
            userName=loginName;
        }
        List<HospitalReview> hospitalReviews = personService.queryBySidID(sid, areaDeptId1, communityDeptId1,userName);
        if(hospitalReviews.size()<1){
            log.info("/fit/result/addFit  该区不存在sid");
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_STOOL_VIEW_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_STOOL_VIEW_ERROR_MSG));
        }
        //获取stage
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
        //新增
        Integer integer = fitService.saveFit(fitResultVo);
        if (integer > 0) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, null, null));
        }
        return JSONUtils.toJson(new Response(GlobalErrorCode.UNKNOWN_ERROR, GlobalErrorCode.UNKNOWN_ERROR_MSG));
    }

    /**
     * @func
     * @desc 发送FIT短信
     * @author zongt
     * @create 2018/5/6 下午3:50
     * @request
     * @response
     **/
    @RequestMapping(value = "/fit/result/sendFit", method = RequestMethod.POST)
    public String sendFit(HttpServletRequest req, HttpServletResponse resp, @RequestBody FitResultVo fitResultVo) throws ItSysException {
        log.info("发送FIT短信");
        log.info("传入包体，body:{}", fitResultVo.toString());

        if (fitResultVo.getId() == null || StringUtils.isEmpty(fitResultVo.getPhone())) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }

        //校验这条fit数据是否存在
        FitResultVo fitResultVoByFitCode = fitService.queryById(fitResultVo.getId());
        if (fitResultVoByFitCode == null) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_FIT_RESULT_FIT_RESULT_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_FIT_RESULT_FIT_RESULT_ERROR_MSG));
        }
        //校验是否存在结果
        if (fitResultVoByFitCode.getResult() == null) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_FIT_RESULT_FIT_RESULT_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_FIT_RESULT_FIT_RESULT_ERROR_MSG));
        }
        fitResultVoByFitCode.setPhone(fitResultVo.getPhone());
        //发送短信
        boolean sendFit = fitService.sendFit(fitResultVoByFitCode);

        if (sendFit) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, null, null));
        }
        return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_FIT_RESULT_FIT_SEND_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_FIT_RESULT_FIT_SEND_ERROR_MSG));
    }

    /**
     * 根据sid获取用户最新的fit信息
     * @param req
     * @param resp
     * @param sid
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/fit/result/queryLatestFitInfo/{sid}", method = RequestMethod.POST)
    public Result queryLatestFitInfo(HttpServletRequest req, HttpServletResponse resp, @PathVariable String sid) throws ItSysException {
        log.info("根据sid"+sid+"条件查询受试者在某个阶段最新fit信息接口");
        String loginName = CookieUtil.getCookieByLoginName(req);
        FitResultVo fitResultVo = fitService.queryLatestFitInfo(sid,loginName);
        Result result=new Result(fitResultVo);
        log.info("返回查询对象：{}", JSONUtils.toJson(result));
        log.info("根据sid"+sid+"条件查询受试者在某个阶段最新fit信息接口");
        return result;
    }


    /*
     * fit图片上传
     *
     * @author ${zongt}
     * @since v1.0.0
     */
    @RequestMapping(value = "/fit/imgUpload", method = RequestMethod.POST)
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        log.debug("传入的文件参数：{}", JSON.toJSONString(file, true));
        Integer id=null;
        if(!StringUtils.isEmpty(request.getParameter("id"))){
            id = Integer.parseInt(request.getParameter("id"));
        }
        if (!file.isEmpty()) {
            String loginName = CookieUtil.getCookieByLoginName(request);
            FileUploadLogPO fileUploadLogPO = fileUploadService.upLoadImgeFile(file, loginName, Constans.FILE_SOURCE_TYPE_5,id);
            return ResultUtil.success(fileUploadLogPO, "文件上传成功");
        } else {
            return ResultUtil.error(GlobalErrorCode.FILE_UPLOAD_FAIL_CODE, GlobalErrorCode.FILE_UPLOAD_FAIL_MSG);
        }
    }




    @Override
    protected String getClassName() {
        return FitController.class.getName();
    }
}
