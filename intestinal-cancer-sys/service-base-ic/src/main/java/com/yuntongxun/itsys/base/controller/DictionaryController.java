package com.yuntongxun.itsys.base.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.ResultUtil;
import com.yuntongxun.itsys.base.po.Dictionary;
import com.yuntongxun.itsys.base.service.DictionaryService;
import com.yuntongxun.itsys.base.vo.Result;
/**
 * 数据字典管理
 * 提供对数据字典的增删改查相关操作接口。
 * @author Lake.zhang
 *
 */
@RestController
public class DictionaryController extends AbstractController {

	final Logger log = LogManager.getLogger(DictionaryController.class);
	@Autowired
	private DictionaryService dictionaryserv;

	@PostMapping(value = "/dictionary/query")
	public Result queryDictionary(@RequestBody String body, HttpServletRequest req) {
		printStartTag("queryDictionary");
		log.info("@Controller queryDictionary parm:body={}", body);
		Result result = null;
		printHttpPacket(req, null);
		try {
			result = dictionaryserv.queryDictionary(body);
		} catch (Exception e) {
			log.error("@Controller queryDictionary   error={}",e.getMessage());
			result = ResultUtil.error(GlobalErrorCode.RUNTIME_ERROR_CODE, e.getMessage(), null);
		}
		printEndTag("queryDictionary");
		return result;

	}

	@PostMapping(value="/dictionary/keyArray/query")
	public Result queryByKeyArray(@RequestBody String[] body){
		log.info("@Controller queryByKeyArray..... parm:body={}", body);
		Result result=null;
		try{
			result=dictionaryserv.queryByKeyArray(body);
		}catch(Exception ex){
			result=ResultUtil.error(GlobalErrorCode.RUNTIME_ERROR_CODE, ex.getMessage());
		}
		
		return result;
	}
	
	@PostMapping(value = "/dictionary/get/{id}")
	public Result getDictionary(@PathVariable String id, HttpServletRequest req) {
		printStartTag("getDictionary");
		log.info("@Controller getDictionary parm:id={}", id);
		Result result = null;
		printHttpPacket(req, null);
		try {
			result = dictionaryserv.getDictionary(id);
		} catch (Exception e) {
			log.error("@Controller getDictionary  error={}", e.getMessage());
			result = ResultUtil.error(GlobalErrorCode.RUNTIME_ERROR_CODE, e.getMessage(), null);
		}
		printEndTag("getDictionary");
		return result;
	}

	@PostMapping(value = "/dictionary/insert")
	public Result insertDictionary(@RequestBody Dictionary dictionary, HttpServletRequest req) {
		printStartTag("insertDictionary");
		log.info("@Controller insertDictionary parm:dictionary={}", dictionary);
		printHttpPacket(req, null);
		Result result = null;
		try {
			result = dictionaryserv.insertDictionary(dictionary);
		} catch (Exception e) {
			log.error("@Controller insertDictionary  error={}", e.getMessage());
			result = ResultUtil.error(GlobalErrorCode.RUNTIME_ERROR_CODE, e.getMessage(), null);
		}
		printEndTag("insertDictionary");
		return result;
	}

	@PostMapping(value = "/dictionary/update")
	public Result updateDictionary(@RequestBody Dictionary dictionary, HttpServletRequest req) {
		printStartTag("updateDictionary");
		log.info("@Controller updateDictionary parm:dictionary={}", dictionary);
		printHttpPacket(req, null);
		Result result = null;
		try {
			result = dictionaryserv.updateDictionary(dictionary);
		} catch (Exception e) {
			log.error("@Controller updateDictionary  error={}", e.getMessage());
			result = ResultUtil.error(GlobalErrorCode.RUNTIME_ERROR_CODE, e.getMessage(), null);
		}
		printEndTag("updateDictionary");
		return result;
	}

	@PostMapping(value = "/dictionary/del/{id}")
	public Result delDictionary(@PathVariable String id, HttpServletRequest req) {
		printStartTag("delDictionary");
		log.info("@Controller delDictionary id={}", id);
		printHttpPacket(req, null);
		Result result = null;
		try {
			result = dictionaryserv.delDictionary(id);
		} catch (Exception e) {
			log.error("@Controller delDictionary  error={}", e.getMessage());
			result = ResultUtil.error(GlobalErrorCode.RUNTIME_ERROR_CODE, e.getMessage(), null);
		}
		printEndTag("delDictionary");
		return result;
	}

	@Override
	protected String getClassName() {
		// TODO Auto-generated method stub
		return DictionaryController.class.getName();
	}

}
