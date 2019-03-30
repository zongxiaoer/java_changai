package com.yuntongxun.itsys.base.controller;

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

import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.ResultUtil;
import com.yuntongxun.itsys.base.service.DictionaryTypeService;
import com.yuntongxun.itsys.base.vo.Result;

@RestController
public class DictionaryTypeController extends AbstractController {

	@Autowired
	private DictionaryTypeService typeserv;
	private final Logger log = LogManager.getLogger(DictionaryTypeController.class.getName());

	/**
	 * 查询数据字典类型
	 * 
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 * @throws ItSysException
	 */
	@RequestMapping(value = "/dictionaryType/query", method = RequestMethod.POST)
	public Result DictionaryTypeQuery(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {

		printStartTag("DictionaryTypeQuery");
		printHttpPacket(req, null);
		Result result = null;
		log.info("@Controller 数据字典DictionaryTypeQuery接收参数为：{}", body);
		try {
			result = typeserv.query(body);
		} catch (Exception e) {
			log.error("@Controller queryDictionary   error={}", e.getMessage());
			result = ResultUtil.error(GlobalErrorCode.RUNTIME_ERROR_CODE, e.getMessage(), null);
		}
		printEndTag("DictionaryTypeQuery");

		return result;
	}

	/**
	 * 根据id查询数据字典类型
	 * 
	 * @param req
	 * @param resp
	 * @param id
	 * @return
	 * @throws ItSysException
	 */
	@RequestMapping(value = "/dictionaryType/get/{id}", method = RequestMethod.POST)
	public Result DictionaryTypeGet(HttpServletRequest req, HttpServletResponse resp, @PathVariable String id) {
		printStartTag("DictionaryTypeGet");
		printHttpPacket(req, null);
		Result result = null;
		log.info("@Controller 数据字典DictionaryTypeGet接收参数为：{}", id);
		try {
			result = typeserv.get(id);
		} catch (Exception e) {
			log.error("@Controller queryDictionary   error={}", e.getMessage());
			result = ResultUtil.error(GlobalErrorCode.RUNTIME_ERROR_CODE, e.getMessage(), null);
		}
		printEndTag("DictionaryTypeGet");
		return result;
	}

	/**
	 * 新增类型
	 * 
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 * @throws ItSysException
	 */
	@RequestMapping(value = "/dictionaryType/insert", method = RequestMethod.POST)
	public Result DictionaryTypeInsert(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
		printStartTag("DictionaryTypeInsert");
		printHttpPacket(req, null);
		Result result = null;
		log.info("@Controller 数据字典DictionaryTypeInsert接收参数为：{}", body);
		try {
			result = typeserv.insert(body);
		} catch (Exception e) {
			log.error("@Controller queryDictionary   error={}", e.getMessage());
			result = ResultUtil.error(GlobalErrorCode.RUNTIME_ERROR_CODE, e.getMessage(), null);
		}
		printEndTag("DictionaryTypeInsert");
		return result;
	}

	/**
	 * 修改类型
	 * 
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 * @throws ItSysException
	 */
	@RequestMapping(value = "/dictionaryType/update", method = RequestMethod.POST)
	public Result DictionaryTypeUpdate(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
		printStartTag("DictionaryTypeUpdate");
		printHttpPacket(req, null);
		Result result = null;
		log.info("@Controller 数据字典DictionaryTypeUpdate接收参数为：{}", body);
		try {
			result = typeserv.update(body);
		} catch (Exception e) {
			log.error("@Controller queryDictionary   error={}", e.getMessage());
			result = ResultUtil.error(GlobalErrorCode.RUNTIME_ERROR_CODE, e.getMessage(), null);
		}
		printEndTag("DictionaryTypeUpdate");
		return result;
	}

	/**
	 * 删除类型
	 * 
	 * @param req
	 * @param resp
	 * @param id
	 * @return
	 * @throws ItSysException
	 */
	@RequestMapping(value = "/dictionaryType/del/{id}", method = RequestMethod.POST)
	public Result DictionaryTypeDel(HttpServletRequest req, HttpServletResponse resp, @PathVariable String id) {
		printStartTag("DictionaryTypeDel");
		printHttpPacket(req, null);
		Result result = null;
		log.info("@Controller 数据字典DictionaryTypeDel接收参数为：{}", id);
		try {
			result = typeserv.del(id);
		} catch (Exception e) {
			log.error("@Controller queryDictionary   error={}", e.getMessage());
			result = ResultUtil.error(GlobalErrorCode.RUNTIME_ERROR_CODE, e.getMessage(), null);
		}
		printEndTag("DictionaryTypeDel");
		return result;
	}

	@Override
	protected String getClassName() {
		// TODO Auto-generated method stub
		return DictionaryTypeController.class.getName();
	}

}
