package com.yuntongxun.itsys.gateway.filter.pre;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yuntongxun.itsys.gateway.module.dao.UserDao;
import com.yuntongxun.itsys.gateway.module.pojo.*;
import com.yuntongxun.itsys.gateway.module.service.AuthService;
import com.yuntongxun.itsys.gateway.module.service.OperationService;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.yuntongxun.itsys.gateway.common.constants.CacheConstant;
import com.yuntongxun.itsys.gateway.common.constants.CookieConstant;
import com.yuntongxun.itsys.gateway.common.constants.FilterConstant;
import com.yuntongxun.itsys.gateway.common.constants.StatusConstant;
import com.yuntongxun.itsys.gateway.common.simple.SimpleLogger;
import com.yuntongxun.itsys.gateway.common.util.JSONUtil;
import com.yuntongxun.itsys.gateway.common.util.RequestUtil;
import com.yuntongxun.itsys.gateway.common.util.StringUtil;
import com.yuntongxun.itsys.gateway.common.vo.CookieParam;
import com.yuntongxun.itsys.gateway.common.vo.ResultMsg;
import com.yuntongxun.itsys.gateway.module.dao.AuthDao;
import com.yuntongxun.itsys.gateway.module.service.UserService;

public class AccessPreFilter extends ZuulFilter {

	private final SimpleLogger logger = new SimpleLogger(AccessPreFilter.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private OperationService operaserv;//操作日志

	@Autowired
	private AuthService authService;



	@Autowired
	private AuthDao authDao;
	@Autowired
	private UserService userService;
	@Value("${ytx.gateway.token.expire}")
	private long tokenExpire;
	@Value("${ytx.gateway.token.timeout}")
	private long timeout;
	@Value("${ytx.gateway.permissions.expire}")
	private long permissionExpire;
	// 不拦截的接口
	private String[] excludeMappings = { "base/user/changepwd", "service-wechat/wx", "service-wechat/wx/bindPage",
			"service-wechat/wx/bindPageT", "service-wechat/wx/bindPhone", "service-wechat/wx/salaryInfo",
			"service-wechat/image","base/fit/result/synresult","base/partytest/uploadDna/queryDna","base/checkToken","base/partytest/uploadDna/inform"};

	@PostConstruct
	public void init() {
		timeout = timeout * 1000;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		logger.printStartTag("pre filter");
		ThreadContext.push(StringUtil.getUUID());
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String requestUrl = request.getRequestURI();
		requestUrl = requestUrl.substring(StringUtils.indexOf(requestUrl, "/"));
		String[] requestUrlArr = requestUrl.split("/");
		for (String mapping : excludeMappings) {
			// 匹配到的url 不拦截
			if (requestUrl.endsWith(mapping) || RequestUtil.matchUrl(requestUrlArr, mapping)) {
				logger.info("not intercept url is {}", request.getRequestURL().toString());
				logger.printEndTag("pre filter");
				return false;
			}
		}
		return true;
	}

	@Override
	public Object run() {

		try {
			RequestContext ctx = RequestContext.getCurrentContext();
			HttpServletRequest request = ctx.getRequest();
			HttpServletResponse response = ctx.getResponse();

            logger.info("pre filter,contentType: {}",request.getContentType());
            logger.info("pre filter,characterEncoding: {}",request.getCharacterEncoding());
			if (request.getRequestURI().indexOf(FilterConstant.CUSTOMER_RETETION) >= 0||request.getRequestURI().indexOf("Excel") >= 0) {

			} else {
				response.setContentType("text/html;charset=utf-8");
			}
			logger.debug("request url is {}", request.getRequestURL().toString());

			// 图片验证码
			if (request.getRequestURI().endsWith(FilterConstant.IMAGES_URL)) {
				return null;
			}
			// 忘记密码验证通过
			if (request.getRequestURI().endsWith(FilterConstant.CUSTOMER_PASS_RETETION)) {
				return null;
			}
			// 获取cookie LGINNAME和TOKEN
			CookieParam cookie = RequestUtil.getTokenByCookie(request);
			if (cookie == null || StringUtil.isBlank(cookie.getLoginName()) || StringUtil.isBlank(cookie.getToken())) {
				ctx.setSendZuulResponse(false);
				ctx.setResponseStatusCode(200);
				ctx.setResponseBody(JSONUtil.toJson(new ResultMsg(StatusConstant.VALIDATE_FAIL_CODE,
						StatusConstant.COOKIE_NO_TOKEN_LOGINNAME_MSG)));
				logger.warn("Invalid request,cookie info:{}, loginName:{},token:{}",cookie,cookie==null?null:cookie.getLoginName(),cookie==null?null:cookie.getToken());
				return null;
			}
			logger.info("get cookie，[{}:{},{}:{}]", CookieConstant.KEY_LOGING_NAME, cookie.getLoginName(),
					CookieConstant.KEY_TOKEN, cookie.getToken());

			String loginName = cookie.getLoginName();
			String token = cookie.getToken();




			User dbUser = userDao.selectUserByLoginName(loginName);
			String result = JSONUtil.toJson(dbUser);
			//判断是否是初始登录状态
			if( CookieConstant.FIRST_LOGIN_STATUS1.equals(dbUser.getFirstLogin())){
				String url = request.getServletPath();
				List resource = operaserv.getResourceByUrl(url);
				Resource res = null;
				if (resource.size() > 0) {
					res = JSONUtil.toBean(JSONUtil.toJson(resource.get(0)), Resource.class);
					Operation opera = new Operation();
					opera.setUser(loginName);
					opera.setType(res.getId());
					String content = "网关层触发首次登录状态---";
					content += result;
					opera.setContent(content);
					opera.setResult(0);
					operaserv.saveOperation(opera);
				}

				ResultMsg resultMsg = authService.removeToken(cookie);
				if (resultMsg.getStatusCode().equals(StatusConstant.SUCCESS_CODE)) {
					RequestUtil.setCookie(response, CookieConstant.KEY_LOGING_NAME, loginName, tokenExpire);
					RequestUtil.setCookie(response, CookieConstant.KEY_TOKEN, null, 0);
					RequestUtil.setCookie(response, CookieConstant.COOKIE_NICKNAME, null, 0);
					RequestUtil.setCookie(response, CookieConstant.COOKIE_XTYNAME, null, 0);
					RequestUtil.setCookie(response, CookieConstant.COOKIE_XTYPHONE, null, 0);

					//退出清空redis中的图片以及短信验证码
//                redis.delete(cookieParam.getLoginName());//清除key=用户名验证码
//                List<User> userList = userService.findByName(cookieParam.getLoginName());
//                if (userList != null && userList.size() > 0) {
//                    redis.delete(userList.get(0).getPhone());//清除key=手机号验证码
//                }
					ctx.setSendZuulResponse(false);
					ctx.setResponseStatusCode(200);
					ctx.setResponseBody(JSONUtil
							.toJson(new ResultMsg(StatusConstant.LOGIN_IS_LAN_FIRST_PASS_CODE, StatusConstant.LOGIN_IS_LAN_FIRST_PASS_MSG)));
					logger.warn("Token timeout,return {},need login.",StatusConstant.LOGIN_IS_FIRST_PASS_CODE);
					return null;
				}

			}





			// 查询token
			UserToken userToken = authDao.getTokenForKey(CacheConstant.TOKEN_KEY_PREFIX + loginName);
			// token已超时，需重新登录
			if (userToken == null) {
				ctx.setSendZuulResponse(false);
				ctx.setResponseStatusCode(200);
				ctx.setResponseBody(JSONUtil
						.toJson(new ResultMsg(StatusConstant.VALIDATE_FAIL_CODE, StatusConstant.TOKEN_TIMEOUT_MSG)));
				logger.warn("Token timeout,return {},need login.",StatusConstant.VALIDATE_FAIL_CODE);
				return null;
			}

			// 验证失败
			if (!token.equals(userToken.getToken())) {
				ctx.setSendZuulResponse(false);
				ctx.setResponseStatusCode(200);
				ctx.setResponseBody(JSONUtil
						.toJson(new ResultMsg(StatusConstant.VALIDATE_FAIL_CODE, StatusConstant.VALIDATE_FAIL_MSG)));
				logger.warn("Token is old,return {},need login.",StatusConstant.VALIDATE_FAIL_CODE);
				return null;
			}

			long expires = Long.parseLong(userToken.getExpires());

			// token已过期，需重置token
			if ((System.currentTimeMillis() - expires) > 0) {
				// logger.debug("token过期，需重置token");
				// if(userToken!=null&& cookie!=null
				// &&userToken.getOldToken()!=null&&
				// userToken.getOldToken().equals(cookie.getToken())){
				// logger.debug("与old相同");
				// }
				ctx.setSendZuulResponse(false);
				ctx.setResponseStatusCode(200);
				ctx.setResponseBody(JSONUtil
						.toJson(new ResultMsg(StatusConstant.TOKEN_EXPIRE_CODE, StatusConstant.TOKEN_EXPIRE_MSG)));
				logger.warn("Token is expired,return {},need reset token.",StatusConstant.VALIDATE_FAIL_CODE);
				return null;
			}

			logger.debug("check token is success，start set token");
			userToken.setExpires((System.currentTimeMillis() + timeout) + "");
			authDao.setToken(CacheConstant.TOKEN_KEY_PREFIX + loginName, userToken, tokenExpire);

			RequestUtil.setCookie(response, CookieConstant.KEY_LOGING_NAME, loginName, tokenExpire);
			RequestUtil.setCookie(response, CookieConstant.KEY_TOKEN, userToken.getToken(), tokenExpire);

			// 获取权限接口不验证权限
			if (request.getRequestURI().endsWith(FilterConstant.PERMISSIONS_URL)) {
				return null;
			}
			// 查询请求权限
			List<Request> requests = authDao.getPermissionsForKey(CacheConstant.PERMISSIONS_KEY_PREFIX + loginName);
			// 缓存中未查询到权限，调用接口查询
			if (requests == null) {
				String json = userService.selectPermissions(cookie.getLoginName());
				if (StringUtil.isNotBlank(json)) {
					JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
					if (obj.has(StatusConstant.STATUS_CODE_STR)
							&& StatusConstant.SUCCESS_CODE.equals(obj.get(StatusConstant.STATUS_CODE_STR).getAsString())
							&& obj.has(StatusConstant.DATA_STR)) {
						JsonObject data = obj.get(StatusConstant.DATA_STR).getAsJsonObject();
						if (data.has(StatusConstant.REQUESTS_STR)) {
							// 存储到缓存中
							requests = authDao.setPermissions(
									CacheConstant.PERMISSIONS_KEY_PREFIX + cookie.getLoginName(),
									data.get(StatusConstant.REQUESTS_STR).toString(), permissionExpire);
						}

					}
				}
			}
			logger.debug("start check auth");
			boolean flag = true;// true没有权限 false找到对应权限
			if (requests != null) {
				String requestUrl = request.getRequestURI();
				// 截取从第2个“/”开始
				requestUrl = requestUrl.substring(StringUtils.indexOf(requestUrl, "/"));
				String[] requestUrlArr = requestUrl.split("/");

				for (Request req : requests) {
					// 验证url是否有权限
					if (requestUrlArr.length == req.getUrl().split("/").length
							&& RequestUtil.matchUrl(requestUrlArr, req.getUrl())) {

						flag = false;// 有权限
						break;

					}
				}
			} else {
				logger.info("permissions requests is null");
			}

			if (flag) {
				// 没有请求权限
				ctx.setSendZuulResponse(false);
				ctx.setResponseStatusCode(200);
				ctx.setResponseBody(JSONUtil.toJson(
						new ResultMsg(StatusConstant.PERMISSIONS_ERROR_CODE, StatusConstant.PERMISSIONS_ERROR_MSG)));
				return null;
			}
			logger.debug("check auth is success");
		} finally {
			logger.printEndTag("pre filter");
		}
		return null;
	}

}
