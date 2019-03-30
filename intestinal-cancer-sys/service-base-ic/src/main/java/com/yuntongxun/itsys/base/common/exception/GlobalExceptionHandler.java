package com.yuntongxun.itsys.base.common.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.yuntongxun.itsys.base.common.util.ResultUtil;
import com.yuntongxun.itsys.base.vo.Response;
import com.yuntongxun.itsys.base.vo.Result;
/**
 * 全局统一异常处理
 * @author zhangzl
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	private Logger logger = LogManager.getLogger();

	@ExceptionHandler(value = ItSysException.class)
	@ResponseBody
	public Result baseErrorHandler(ItSysException e){
		logger.error("---ItSysException Handler---ERROR: code={},msg={}", e.getCode(),e.getMessage());
		return ResultUtil.error(e.getCode(), e.getMessage(),"");
	}

	
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Object defaultErrorHandler(Exception e){
		logger.error("---DefaultException Handler---ERROR: {}", e);
		e.printStackTrace();
		return new Response("899999","系统异常，请联系管理员。");
	}
}
