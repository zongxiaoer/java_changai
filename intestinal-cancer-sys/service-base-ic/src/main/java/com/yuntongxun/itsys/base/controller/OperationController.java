package com.yuntongxun.itsys.base.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.service.OperationService;
import com.yuntongxun.itsys.base.vo.Response;

/**
 * 操作日志管理
 * 
 * @author Zhangzl
 *
 */
@RestController
public class OperationController {

	private final Logger log = LogManager.getLogger(OperationController.class);
	@Autowired
	private OperationService operserv;

	@RequestMapping(value = "/operation/query")
	public String queryOperatioin(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) throws ItSysException {

		log.info("@Controller queryOperatioin 接收到的参数为:{}" + body);
		ListPageUtil listPage = operserv.getOperation(body);
		log.info("@Controller queryOperatioin End   返回值={}",listPage.getResultList());
		return JSONUtils.toJson(new Response(listPage.getResultList(), listPage.getPageInfo()));
	}
}
