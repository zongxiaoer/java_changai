package com.yuntongxun.itsys.base.controller;

import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.*;
import com.yuntongxun.itsys.base.po.DnaSynLogPO;
import com.yuntongxun.itsys.base.po.FitSynLogPO;
import com.yuntongxun.itsys.base.po.HospitalStoolDnaPO;
import com.yuntongxun.itsys.base.po.dto.parytest.PartyTestInformDto;
import com.yuntongxun.itsys.base.po.dto.parytest.PartyTestQueryDto;
import com.yuntongxun.itsys.base.service.PartyTestService;
import com.yuntongxun.itsys.base.vo.FitResultSynVo;
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
import java.util.Date;
import java.util.List;

/**
 * @author wp_sp
 * @date 2018/5/10
 */
@RestController
@RequestMapping("/partytest")
public class PartyTestController {
    private final Logger log = LogManager.getLogger(PartyTestController.class);
    @Autowired
    private PartyTestService partyTestService;


    /**
     * 第三方DNA检测机构分页/指定查询条件查询列表
     *
     * @param request
     * @param partyTestQueryDto
     * @return
     */
    @RequestMapping(value = "querydnacodelistforpage", method = RequestMethod.POST)
    public Result queryDNAcodeListForPage(HttpServletRequest request, @RequestBody PartyTestQueryDto partyTestQueryDto) {
        ListPageUtil listPage = partyTestService.queryDNAcodeListForPage(partyTestQueryDto);
        Result result = new Result(listPage.getResultList(), listPage.getPageInfo());
        log.info("返回查询对象个数={}", listPage.getResultList() == null ? 0 : listPage.getResultList().size());
        return result;
    }


    /**
     * 根据sid 和 dna编码录入检测结果
     *
     * @param request
     * @param partyTestQueryDto
     * @return
     */
    @RequestMapping(value = "inform", method = RequestMethod.POST)
    public Result informByDnaCodeAndSid(HttpServletRequest request, @RequestBody PartyTestInformDto partyTestQueryDto) {


        if (partyTestQueryDto.getId()==null || StringUtil.isEmpty(partyTestQueryDto.getDnaCode()) || StringUtil.isEmpty(partyTestQueryDto.getSid())) {
            return ResultUtil.error(GlobalErrorCode.ERR_HOSPITAL_STOOL_DNA_ERROR_CODE_5, GlobalErrorCode.ERR_HOSPITAL_STOOL_DNA_ERROR_MSG_5);
        }

        if(null == partyTestQueryDto.getDnaCheckResult()){
            return ResultUtil.error(GlobalErrorCode.ERR_HOSPITAL_STOOL_DNA_ERROR_CODE_5, GlobalErrorCode.ERR_HOSPITAL_STOOL_DNA_ERROR_MSG_5);

        }

        if (!partyTestQueryDto.getDnaCheckResult().equals(Constans.DNA_CHECK_RESULT_WU)){
            if(null == partyTestQueryDto.getDnaCheckQuantum() ||
                    null == partyTestQueryDto.getDnaCheckGoal() ||
                    StringUtil.isEmpty(partyTestQueryDto.getDnaCheckFilepath())
                    ) {
                throw new ItSysException(GlobalErrorCode.ERR_PARAM_NULL, "必填参数为空");
            }
        }
        if(StringUtil.isEmpty(partyTestQueryDto.getDnaCheckFilepath())){
            partyTestQueryDto.setDnaCheckFilepath(null);
        }
        //添加代办
        try{
            partyTestService.informByDnaCodeAndSid(partyTestQueryDto);
        }catch (Exception e){
            return ResultUtil.error(GlobalErrorCode.ERR_HOSPITAL_STOOL_DNA_ERROR_CODE_7, GlobalErrorCode.ERR_HOSPITAL_STOOL_DNA_ERROR_MSG_7);

        }
        return ResultUtil.success(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS);

    }


    /*
     * 第三方查询获取数据
     *
     * @author ${zongt}
     * @since v2.2.1
     */
    @RequestMapping(value = "/uploadDna/queryDna", method = RequestMethod.POST)
    public Result informByqueryDna(HttpServletRequest request, @RequestBody PartyTestInformDto partyTestQueryDto) {
        log.info("第三方查询dna");
        log.info("传入包体，body:{}", JSONUtils.toJson(partyTestQueryDto));

        //校验参数是否传值
        if(StringUtils.isEmpty(partyTestQueryDto.getDataNumber())||StringUtils.isEmpty(partyTestQueryDto.getSign())||StringUtils.isEmpty(partyTestQueryDto.getTimestamp())){
            log.info("nhdnaresult dataNumber is null | sign is null | timestamp is null ");
            return ResultUtil.error(GlobalErrorCode.DNA_PARAM_ISNO_CODE, GlobalErrorCode.DNA_PARAM_ISNO_CODE_MSG);

        }

        //校验固定签名是否正确
        if (!partyTestQueryDto.getSign().toUpperCase().equals(EncryptUtil.md5(partyTestQueryDto.getDataNumber() + partyTestQueryDto.getTimestamp() + "nhdnaresult"))) {
            log.info("nhdnaresult sign!=md5(sid+timestamp+synresult) ");
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }
        List<String> dnas=null;
        //添加代办
        try{
            dnas=partyTestService.queryAll();
            if(dnas==null){
                log.info("nhdnaresult query is null  ");
                return ResultUtil.error(GlobalErrorCode.DNA_CODE_ISNO_CODE, GlobalErrorCode.DNA_CODE_ISNO_CODE_MSG);
            }
        }catch (Exception e){
            log.info("nhdnaresult query is error  "+e.getMessage());
            return ResultUtil.error(GlobalErrorCode.DNA_QUERY_ISERROR_CODE, GlobalErrorCode.DNA_QUERY_ISERROR_CODE_MSG);
        }
        return ResultUtil.success(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS,dnas);

    }


    /*
     * 第三方录入dna信息
     *
     * @author ${zongt}
     * @since v2.2.1
     */
    @RequestMapping(value = "/uploadDna/inform", method = RequestMethod.POST)
    public Result informByUploadDna(HttpServletRequest request, @RequestBody PartyTestInformDto partyTestQueryDto) {
        log.info("第三方录入FIT结果");
        log.info("传入包体，body:{}", JSONUtils.toJson(partyTestQueryDto));
        FitResultSynVo vo = null;
        DnaSynLogPO synLogPO = new DnaSynLogPO();
        synLogPO.setParamValue(JSONUtils.toJson(partyTestQueryDto));
        try {

            Integer dnaId = partyTestService.synResult(partyTestQueryDto);
            synLogPO.setDnaId(dnaId);
            synLogPO.setResult(1);
            synLogPO.setDnaCode(partyTestQueryDto.getDnaCode());
            synLogPO.setResultCont("succ");
            synLogPO.setDataNumber(partyTestQueryDto.getDataNumber());
        }catch (ItSysException e){
            synLogPO.setResult(2);
            synLogPO.setResultCode(e.getCode());
            synLogPO.setResultCont(e.getMessage());
            //throw new ItSysException(e.getCode(),e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            synLogPO.setResult(2);
            synLogPO.setResultCont(GlobalErrorCode.RUNTIME_ERROR_CODE);
            synLogPO.setResultCont(GlobalErrorCode.RUNTIME_ERROR_MSG);
            //throw new ItSysException(GlobalErrorCode.RUNTIME_ERROR_CODE, GlobalErrorCode.RUNTIME_ERROR_MSG);
        }
       //记录同步日志
        partyTestService.addDnaSynLog(synLogPO);
        if(synLogPO.getResult()==2){
            throw new ItSysException(synLogPO.getResultCode(), synLogPO.getResultCont());
        }
        Result result = new Result();
        //printEndTag("第三方录入FIT结果");
        return result;

    }

    public static void main(String[] args) {
        System.out.println(EncryptUtil.md5("1223121" + "1531195922" + "nhdnaresult"));
    }
    /**
     * 根据ID查询详情
     *
     * @param request
     * @param partyTestQueryDto
     * @return
     */
    @RequestMapping(value = "querybyid", method = RequestMethod.POST)
    public Result querybyid(HttpServletRequest request, @RequestBody PartyTestInformDto partyTestQueryDto) {
        if (null == partyTestQueryDto.getId()) {
            return ResultUtil.error(GlobalErrorCode.ERR_HOSPITAL_STOOL_DNA_ERROR_CODE_5, GlobalErrorCode.ERR_HOSPITAL_STOOL_DNA_ERROR_MSG_5);
        }

        try {
            HospitalStoolDnaPO hospitalStoolDnaPO = partyTestService.querybyid(partyTestQueryDto);
            return new Result(hospitalStoolDnaPO);
        } catch (Exception e) {
            return ResultUtil.error(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG);
        }
    }


}
