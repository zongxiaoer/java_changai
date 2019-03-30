package com.yuntongxun.itsys.base.controller;

import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.CookieUtil;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.service.ColonoscopyService;
import com.yuntongxun.itsys.base.service.FitService;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
import com.yuntongxun.itsys.base.vo.FitResultVo;
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

/**
 * 肠镜管理
 *
 * @author zhangzl
 */
@RestController
public class FitCountryController extends AbstractController {

    @Autowired
    private FitService fitService;

    @Autowired
    private ColonoscopyService colonoscopyService;
    private final Logger log = LogManager.getLogger(FitCountryController.class);

    /**
     * 1.4.1、根据条件查询受试者接口；
     *
     * @param req
     * @param resp
     * @param {  "sid":"TC0001",
					  "name":"张三", 
					  "phone":"TC0001", 
					  "group":"A", 
					  "codeEntryStatus":1,//编码录入状态1：未录入，2：已录入 
					  "insert_status":1,//FIT结果录入状态  1：未录入，2：已录入 ,3:待审核
					  "result":1,//FIT结果状态1 阳性 2阴性 3无效 4无结果
					}
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/country/fit/result/query", method = RequestMethod.POST)
    public Result query(HttpServletRequest req, HttpServletResponse resp, @RequestBody FitResultVo queryCondition) throws ItSysException {
        log.info("根据条件查询受试者接口(国家)");
        log.info("传入包体:{}", queryCondition);
        
        String loginName=CookieUtil.getCookieByLoginName(req);
        //FitResultVo queryCondition = null;
        try{
        	//queryCondition = JSONUtils.toBean(body, FitResultVo.class);
        }catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }
        //查询登录用户信息
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        //校验是不是国家人查看

        if(!doctorInfo.getHospitalType().equals(Constans.DEP_HOSPITAL_TYPE3)){
            Result result=new Result(GlobalErrorCode.ERR_HOSPITAL_COUNTRY_FIT_RESULT_FIT_CODE_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_COUNTRY_FIT_RESULT_FIT_CODE_ERROR_MSG);
            return result;
        }
        ListPageUtil listPage = fitService.queryCountry(queryCondition);
		Result result=new Result(listPage.getResultList(),listPage.getPageInfo());
		printEndTag("根据条件查询受试者接口(国家)");
        log.info("返回查询对象个数={}", listPage.getResultList().size());
        return result;
    }
    
    @Override
    protected String getClassName() {
        return FitCountryController.class.getName();
    }
}
