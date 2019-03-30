package com.yuntongxun.itsys.gateway.filter.logpost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerMapping;

import com.alibaba.druid.support.json.JSONUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.yuntongxun.itsys.gateway.common.util.CookieUtil;
import com.yuntongxun.itsys.gateway.common.util.JSONUtil;
import com.yuntongxun.itsys.gateway.common.util.StringUtil;
import com.yuntongxun.itsys.gateway.module.pojo.Operation;
import com.yuntongxun.itsys.gateway.module.pojo.Resource;
import com.yuntongxun.itsys.gateway.module.service.OperationService;
import com.yuntongxun.itsys.gateway.module.service.ResourceService;

/**
 * 记录操作日志Filter
 * 
 * @author Lake.zhang
 *
 */
public class LogPostFilter extends ZuulFilter {

	private final Logger log = LogManager.getLogger(LogPostFilter.class);

	@Autowired
	private ResourceService reserv;
	@Autowired
	private OperationService operserv;

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		HttpServletResponse response = ctx.getResponse();


		Operation operation = new Operation();
		String loginName=null;
		try {
			loginName = CookieUtil.getCookieByLoginName(request);
		}catch (Exception e){
			e.printStackTrace();
			log.info("operationLog loginName null......");
		}
		// 请求URL
		String servletPath = request.getServletPath();/// role/get/60
		String path[] = servletPath.trim().split("/");

		String requestBody = null, responseBody = ctx.getResponseBody(), resourceName = null;
		StringBuilder responseStrBuilder = new StringBuilder();
		// 获取数据库中所有的请求资源
		List<Resource> resourceList = reserv.getResourceByType("3");
		// 是否记录操作日志
		boolean isSaveLog = false;
		// 请求资源ID
		Integer resourceId = null;
		// 请求响应
		boolean qequestStatus = false;
		// 请求及响应内容
		StringBuffer content = null;
		if (resourceList != null && resourceList.size() > 0) {
			for (int i = 0; i < resourceList.size(); i++) {
				Resource resource = JSONUtil.toBean(JSONUtil.toJson(resourceList.get(i)), Resource.class);
				String url_base = resource.getUrl();
				String url_base_arr[] = url_base.trim().split("/");
				if (url_base_arr.length == path.length && urlMatch(path, url_base_arr)) {// 匹配到url
					isSaveLog = resource.getSaveLog().equals("1") ? true : false;
					resourceId = resource.getId();
					resourceName = ("【" + resource.getDesc() + "】");
					break;
				}
			}
		}
		if (isSaveLog) {
			log.info("save operationLog begin......");
			try {
				BufferedReader streamReader = new BufferedReader(
						new InputStreamReader(request.getInputStream(), "UTF-8"));
				String inputStr;
				while ((inputStr = streamReader.readLine()) != null)
					responseStrBuilder.append(inputStr);
				requestBody = responseStrBuilder.toString();// 请求参数
				InputStream stream = ctx.getResponseDataStream();
				responseBody = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));// 响应包体
				ctx.setResponseBody(responseBody);

				if (!StringUtil.isEmpty(responseBody)) {
					JsonObject json = new JsonParser().parse(responseBody.toString()).getAsJsonObject();
					qequestStatus = (json.get("statusCode").getAsString().equals("000000")) ? true : false;
					content = new StringBuffer(resourceName).append("--请求参数:").append(requestBody).append("$")
							.append("返回数据:").append(responseBody);
				}
				operation.setUser(loginName);
				operation.setType(resourceId);
				operation.setContent(String.valueOf(content));
				operation.setResult(qequestStatus ? 0 : 1);
				log.info("@LogPostFilter..........记录操作日志 End..............");
				operserv.saveOperation(operation);

			} catch (Exception e) {
				log.error("记录操作日志异常--", e.getMessage());
				e.printStackTrace();
			} 
		}

		ctx.setResponseBody(responseBody);
//		try {
//			response.getWriter().write(responseBody);
//			response.getWriter().flush();
//			response.getWriter().close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			ThreadContext.removeStack();
//		}
		return null;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "post";
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 1;
	}

	/**
	 * 请求url与数据库资源表中的请求进行匹配
	 * 
	 * @param path
	 *            请求url
	 * @param basePath
	 *            数据库表中的url
	 * @return
	 */
	public boolean urlMatch(String path[], String basePath[]) {
		boolean b = false;
		if (path.length != basePath.length) {
			return b;
		}
		for (int i = 0, j = basePath.length; i < j; i++) {
			String iurl = path[i];
			String burl = basePath[i];
			if (!iurl.equals(burl) && !(burl.startsWith("{") && burl.endsWith("}"))) {
				break;
			}
			if (i + 1 == j) {
				b = true;
				break;
			}
		}
		return b;
	}
}
