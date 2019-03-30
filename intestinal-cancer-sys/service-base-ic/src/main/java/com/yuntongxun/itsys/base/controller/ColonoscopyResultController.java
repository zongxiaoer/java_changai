package com.yuntongxun.itsys.base.controller;

import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.CookieUtil;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.po.ColonoscopyLesionsRecord;
import com.yuntongxun.itsys.base.po.ColonoscopyResult;
import com.yuntongxun.itsys.base.po.HospitalColonoscopyResult;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
import com.yuntongxun.itsys.base.service.ColonoscopyPathologyResultService;
import com.yuntongxun.itsys.base.service.ColonoscopyResultService;
import com.yuntongxun.itsys.base.vo.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 肠镜结果记录表
 * maxiang
 */
@RestController
public class ColonoscopyResultController extends AbstractController {

    private final Logger log = LogManager.getLogger(ColonoscopyResultController.class);

    @Autowired
    private ColonoscopyResultService colonoscopyResultService;

    @Autowired
    private ColonoscopyPathologyResultService colonoscopyPathologyResultService;


    /**
     *添加肠镜结果记录表
     * @param
     * @param req
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/colonoscopy/result/addColonoscopyResult", method = RequestMethod.POST)
    public String addColonoscopyResult(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body)
            throws ItSysException {
        log.info("addColonoscopyResult 输入参数:{}", body);
        String loginName= CookieUtil.getCookieByLoginName(req);
        log.info("addColonoscopyResult loginName：{}", loginName);
        colonoscopyResultService.addColonoscopyResult(body,loginName);
        return JSONUtils.toJson(new Response());
    }

    /*
     * 修改肠镜结果记录表
     *
     * @author ${zongt}
     * @since v1.0.0
     */
    @RequestMapping(value = "/colonoscopy/result/updateColonoscopyResult", method = RequestMethod.POST)
    public String updateColonoscopyResult(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body)
            throws ItSysException {
        log.info("addColonoscopyResult 输入参数:{}", body);
        String loginName= CookieUtil.getCookieByLoginName(req);
        log.info("addColonoscopyResult loginName：{}", loginName);
        ColonoscopyResult colonoscopyResult = JSONUtils.jsonToBeanDateSerializer(body, ColonoscopyResult.class,"yyyy-MM-dd");

        //根据id获取数据
        List<ColonoscopyResult> colonoscopyResults = colonoscopyResultService.queryById(colonoscopyResult.getId());
        if(colonoscopyResults.size()!=1){
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
        //根据检查结果id查询病变记录
        List<ColonoscopyLesionsRecord> colonoscopyLesionsRecords= colonoscopyResultService.queryByColonoscopyResultId(colonoscopyResult.getId());
        HospitalReferenceRecordDto hospitalReferenceRecordDto = new HospitalReferenceRecordDto();
        hospitalReferenceRecordDto.setStatus(Constans.HOSPITAL_REFERENCE_RECORD_STATUS_FINISH);
        hospitalReferenceRecordDto.setDataId(colonoscopyResult.getId());
        hospitalReferenceRecordDto.setFormType(Constans.HOSPITAL_COLONOSCOPY_RESULT);
        hospitalReferenceRecordDto.setEditPerson(loginName);
        String dataText = JSONUtils.toJson(colonoscopyResults.get(0));
        hospitalReferenceRecordDto.setOldData(dataText);
        //colonoscopyResultService.updateColonoscopyResult(colonoscopyResult,colonoscopyResults.get(0),hospitalReferenceRecordDto);
        colonoscopyResultService.updateColonoscopyResult(colonoscopyResult,colonoscopyResults.get(0),loginName,hospitalReferenceRecordDto);
        return JSONUtils.toJson(new Response());
    }
    /**
     * @func   结肠镜检查结果   hospital_colonoscopy_lesions_record \hospital_colonoscopy_result
     * @desc   根据id获取结肠镜检查结果详情
     * @author zongt
     * @create 2018/4/20 上午11:34
     * @request
     * @response
     **/
    @RequestMapping(value = "/colonoscopy/result/queryColonoscopyResult", method = RequestMethod.POST)
    public String queryColonoscopyResult(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalColonoscopyResult hospitalColonoscopyResult)
            throws ItSysException {
        log.info("queryColonoscopyResult 输入参数:{}", JSONUtils.toJson(hospitalColonoscopyResult));
        return colonoscopyResultService.queryColonoscopyResultToStringById(hospitalColonoscopyResult);
    }

    /**
     * 查询检查记录的详情
     * @param req
     * @param resp
     * @param body
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/colonoscopy/result/queryColonoscopyInfo", method = RequestMethod.POST)
    public String queryColonoscopyResult(HttpServletRequest req, HttpServletResponse resp,@RequestBody String body)
            throws ItSysException {
        ColonoscopyResult colonoscopyResult = JSONUtils.jsonToBeanDateSerializer(body, ColonoscopyResult.class,"yyyy-MM-dd");
        return JSONUtils.toJson(new Response(colonoscopyResultService.delNotificationState(colonoscopyResult)));
    }

    @Override
    protected String getClassName() {
        return RoleController.class.getName();
    }
}
