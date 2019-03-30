package com.yuntongxun.itsys.base.controller;

import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.CookieUtil;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.service.BiologSampleService;
import com.yuntongxun.itsys.base.service.ColonoscopyService;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
import com.yuntongxun.itsys.base.vo.HospitalBiologicalSampleResultVo;
import com.yuntongxun.itsys.base.vo.Result;
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
 * @author zongt
 * @date 2018/5/11
 */
@RestController
public class BiologSampleCountryController extends AbstractController {
    @Autowired
    private BiologSampleService biologSampleService;

    @Autowired
    private ColonoscopyService colonoscopyService;
    private final Logger log = LogManager.getLogger(BiologSampleCountryController.class);

    /**
     * @func
     * @desc 查询生物样本列表
     * @author zongt
     * @create 2018/5/11 下午3:56
     * @request
     * @response
     **/
    @RequestMapping(value = "/country/biological/sample/query", method = RequestMethod.POST)
    public Result query(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO) throws ItSysException {
        log.info("查询生物样本列表");
        log.info("传入包体:{}", hospitalBiologicalSampleResultPO);

        String loginName = CookieUtil.getCookieByLoginName(req);
        //查询登录用户信息
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);

        if(!doctorInfo.getHospitalType().equals(Constans.DEP_HOSPITAL_TYPE3)){
            Result result=new Result(GlobalErrorCode.ERR_HOSPITAL_COUNTRY_FIT_RESULT_FIT_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_COUNTRY_FIT_RESULT_FIT_CODE_ERROR_MSG);
            return result;
        }
        ListPageUtil listPage = biologSampleService.queryCountry(hospitalBiologicalSampleResultPO, loginName);
        Result result = new Result(listPage.getResultList(), listPage.getPageInfo());
        printEndTag("根据条件查询生物样本列表(国家)");
        log.info("返回查询对象个数={}", listPage.getResultList().size());
        return result;
    }
    @Override
    protected String getClassName() {
        return BiologSampleCountryController.class.getName();
    }
}
