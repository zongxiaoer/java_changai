package com.yuntongxun.itsys.base.controller;

import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.CookieUtil;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.po.ColonoscopyPathologyDiagnosisRecord;
import com.yuntongxun.itsys.base.po.ColonoscopyPathologyResult;
import com.yuntongxun.itsys.base.po.HospitalColonoscopyResult;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
import com.yuntongxun.itsys.base.service.ColonoscopyPathologyResultService;
import com.yuntongxun.itsys.base.vo.Response;
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
import java.util.List;

/**
 * 结肠镜病理记录表
 * maxiang
 */
@RestController
public class ColonoscopyPathologyResultController extends AbstractController{

    private final Logger log = LogManager.getLogger(ColonoscopyPathologyResultController.class);

    @Autowired
    ColonoscopyPathologyResultService colonoscopyPathologyResultService;


    /**
     *添加结肠镜病理记录表
     * @param
     * @param req
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/colonoscopy/PathologyResult/addColonoscopyPathologyResult", method = RequestMethod.POST)
    public String addColonoscopyPathologyResult(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body)
            throws ItSysException {
        log.info("addColonoscopyPathologyResult 输入参数:{}", body);
        String loginName= CookieUtil.getCookieByLoginName(req);
        log.info("addColonoscopyPathologyResult loginName：{}", loginName);
        colonoscopyPathologyResultService.addColonoscopyPathologyResult(body,loginName);
        //

        return JSONUtils.toJson(new Response());
    }


    /*
     *修改结肠镜病理记录表
     *
     * @author ${zongt}
     * @since v1.0.0
     */
    @RequestMapping(value = "/colonoscopy/PathologyResult/updateColonoscopyPathologyResult", method = RequestMethod.POST)
    public String updateColonoscopyPathologyResult(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body)
            throws ItSysException {

        log.info("addColonoscopyPathologyResult 输入参数:{}", body);
        String loginName= CookieUtil.getCookieByLoginName(req);
        log.info("addColonoscopyPathologyResult loginName：{}", loginName);
        ColonoscopyPathologyResult colonoscopyPathologyResult = JSONUtils.jsonToBeanDateSerializer(body,  ColonoscopyPathologyResult.class,"yyyy-MM-dd");
        //校验id和sid
        if(colonoscopyPathologyResult.getId()==null||StringUtils.isEmpty(colonoscopyPathologyResult.getSid())){
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));

        }
        //根据id查数据
        ColonoscopyPathologyResult colonoscopyPathologyResult1 = colonoscopyPathologyResultService.queryByID(colonoscopyPathologyResult.getId(), colonoscopyPathologyResult.getSid());

        //根据id查病理结果记录
        List<ColonoscopyPathologyDiagnosisRecord> colonoscopyPathologyDiagnosisRecords = colonoscopyPathologyResultService.queryColonoscopyPathologyDiagnosisRecordsByPathologyResultId(colonoscopyPathologyResult.getId());
        colonoscopyPathologyResult1.setColonoscopyPathologyDiagnosisRecordList(colonoscopyPathologyDiagnosisRecords);

        //根据id查询病理结果
        HospitalReferenceRecordDto hospitalReferenceRecordDto = new HospitalReferenceRecordDto();
        hospitalReferenceRecordDto.setStatus(Constans.HOSPITAL_REFERENCE_RECORD_STATUS_FINISH);
        hospitalReferenceRecordDto.setDataId(colonoscopyPathologyResult.getId());
        hospitalReferenceRecordDto.setFormType(Constans.HOSPITAL_COLONOSCOPY_PATHOLOGY_RESULT);
        hospitalReferenceRecordDto.setEditPerson(loginName);
        String dataText = JSONUtils.toJson(colonoscopyPathologyResult1);
        hospitalReferenceRecordDto.setOldData(dataText);
        colonoscopyPathologyResultService.updatePathologyResult(colonoscopyPathologyResult,colonoscopyPathologyResult1,loginName,hospitalReferenceRecordDto);
        return JSONUtils.toJson(new Response());
    }

    /**
     * @func
     * @desc   根据Id获取病理检查结果详情
     * @author zongt
     * @create 2018/4/下午5:57:57
     * @request
     * @response
     **/
    @RequestMapping(value = "/colonoscopy/PathologyResult/queryColonoscopyPathologyResult", method = RequestMethod.POST)
    public String queryColonoscopyPathologyResult(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalColonoscopyResult hospitalColonoscopyResult)
            throws ItSysException {
        log.info("queryColonoscopyPathologyResult 输入参数:{}", JSONUtils.toJson(hospitalColonoscopyResult));
        return colonoscopyPathologyResultService.queryColonoscopyPathologyResult(hospitalColonoscopyResult);
    }

    @Override
    protected String getClassName() {
        // TODO Auto-generated method stub
        return RoleController.class.getName();
    }

}
