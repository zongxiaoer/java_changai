package com.yuntongxun.itsys.base.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.JsonObject;
import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.CookieUtil;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.ResultUtil;
import com.yuntongxun.itsys.base.po.HospitalFitResult;
import com.yuntongxun.itsys.base.service.HFitResultService;
import com.yuntongxun.itsys.base.vo.Response;
import com.yuntongxun.itsys.base.vo.Result;
import com.yuntongxun.itsys.base.vo.TodoVo;
import com.google.gson.JsonParser;
/**
 * 查询 未录入FIT编号 受试者列表；
 * @author liugb
 * Date 2018 4 17
 */
@RestController
public class HFitResultController extends AbstractController{
    @Autowired
    private HFitResultService hFitResultService;
    private final Logger log = LogManager.getLogger(HFitResultController.class.getName());    
    /**
     * 查询 未录入FIT编号 受试者列表
     * @param req
     * @param resp
     * @param body
     * @return
     */
    @RequestMapping(value = "/hospital/person/notentry/fitcode/query", method = RequestMethod.POST)
	public Result notEntryFitCodeList(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
		printStartTag("查询待录入FIT编号待办");
		printHttpPacket(req, null);
		String loginName=CookieUtil.getCookieByLoginName(req);
		log.info("@Controller HFitResultController接收参数为：{}", body);
		TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
		try{
	       	vo = JSONUtils.toBean(body, TodoVo.class);
        }catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }
		ListPageUtil listPage = hFitResultService.notEntryFitCodeList(vo, loginName);
		
		Result result=new Result(listPage.getResultList(),listPage.getPageInfo());
		printEndTag("查询待录入FIT编号待办");
        log.info("返回查询对象个数={}", listPage.getResultList().size());
		return result;
	}
    /**
     * 查询 未录入FIT结果 受试者列表
     * @param req
     * @param resp
     * @param body
     * @return
     */
    @RequestMapping(value = "/hospital/person/notentry/fitresult/query", method = RequestMethod.POST)
   	public Result notEntryFitResultList(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
   		printStartTag("查询待录入FIT结果待办");
   		printHttpPacket(req, null);
		String loginName=CookieUtil.getCookieByLoginName(req);
		log.info("@Controller HFitResultController接收参数为：{}", body);
		TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
		try{
	       	vo = JSONUtils.toBean(body, TodoVo.class);
        }catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }
		ListPageUtil listPage = hFitResultService.notEntryFitResultList(vo, loginName);
		
		Result result=new Result(listPage.getResultList(),listPage.getPageInfo());
   		printEndTag("查询待录入FIT结果待办");
   		return result;
   	}
    /**
     * 根据id查询
     * @param req
     * @param resp
     * @param body
     * @return
     */
    @RequestMapping(value = "/fit/result/getFItResult/{id}", method = RequestMethod.POST)
	public Result getFItResultById(HttpServletRequest req, HttpServletResponse resp, @PathVariable int id) {
		printStartTag("getFItResultById");
		printHttpPacket(req, null);
		log.info("id：{}", id);
		if(id<=0){
        	log.error("Request param error,id={}.",id);
        	throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Request param error.");
        }
		HospitalFitResult hospitalFitResult= hFitResultService.getFitResultByid(id);
		Result result=new Result(hospitalFitResult);
		log.info("返回查询对象：{}", JSONUtils.toJson(result));
		printEndTag("getFItResultById");
		return result;
	}
    /**
     * 新增fit结果----作废没有用到
     * @param req
     * @param resp
     * @param fitResult
     * @return
     */
	@Deprecated
	@RequestMapping(value = "/fit/result/insertFItResult", method = RequestMethod.POST)
	public Result insertFItResult(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalFitResult fitResult) {
		printStartTag("insertFItResult");
		printHttpPacket(req, null);
		Result result = null;
		log.info("@Controller insertFItResult接收参数为：{}", fitResult);
		try {
			result = hFitResultService.insertFitResult(fitResult);
		} catch (Exception e) {
			log.error("@Controller insertDictionary  error={}", e.getMessage());
			result = ResultUtil.error(GlobalErrorCode.RUNTIME_ERROR_CODE, e.getMessage(), null);
		}
		log.info("返回查询对象：{}", JSONUtils.toJson(result));
		printEndTag("insertFItResult");
		return result;
	}
	  /**
	   * 根据市区id查询未录入的Fit编号
	   * @param req
	   * @param resp
	   * @param body
	   * @return
	   */
	  @RequestMapping(value = "/hospital/person/notentry/fitcode/notFitCodeByAreaIdList", method = RequestMethod.POST)
		public Result notFitCodeByAreaIdList(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
			printStartTag("根据市区id查询未录入的Fit编号");
			printHttpPacket(req, null);
			String loginName=CookieUtil.getCookieByLoginName(req);
			log.info("@Controller HFitResultController接收参数为：{}", body);
			TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
			Result result = null;
			try{
		       	vo = JSONUtils.toBean(body, TodoVo.class);
		       	result = hFitResultService.notFitCodeByAreaIdList(vo, loginName);
	        }catch(Exception e){
				throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
	        }
			printEndTag("根据市区id查询未录入的Fit编号");
			return result;
		}
	  /**
	   * 根据市区id查询所对应社区未录入FIT结果
	   * @param req
	   * @param resp
	   * @param body
	   * @return
	   */
	@RequestMapping(value = "/hospital/person/notentry/fitcode/notFitResultByAreaIdList", method = RequestMethod.POST)
	public Result notFitResultByAreaIdList(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
		printStartTag("根据市区id查询未录入的Fit编号");
		printHttpPacket(req, null);
		String loginName = CookieUtil.getCookieByLoginName(req);
		log.info("@Controller HFitResultController接收参数为：{}", body);
		TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
		Result result = null;
		try {
			vo = JSONUtils.toBean(body, TodoVo.class);
			result = hFitResultService.notFitResultByAreaIdList(vo, loginName);
		} catch (Exception e) {
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, "Parse request body error.");
		}
		printEndTag("根据市区id查询未录入的Fit编号");
		return result;
	}
	@Override
	protected String getClassName() {
		// TODO Auto-generated method stub
		return HFitResultController.class.getName();
	}

}
