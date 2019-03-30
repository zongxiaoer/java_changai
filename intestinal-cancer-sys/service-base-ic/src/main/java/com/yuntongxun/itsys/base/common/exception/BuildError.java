package com.yuntongxun.itsys.base.common.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.vo.Response;

/**
 * 构建错误消息 json字符
 * @author zhangzl
 *
 */
public final class BuildError {
	private static final Logger log = LogManager.getLogger();

	public static String buildException(String code, String msg) {
		String result = null;
		if(!StringUtil.isEmpty(code)&&!StringUtil.isEmpty(msg)){
			Response resp=new Response(code,msg);
			result=JSONUtils.toJson(resp);
		}else{
			log.error("构建异常失败,返回状态码和返回消息为空!");
		}
		
		return result;
	}
}
