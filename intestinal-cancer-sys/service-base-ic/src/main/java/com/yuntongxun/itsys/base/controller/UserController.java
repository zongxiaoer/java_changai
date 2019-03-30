package com.yuntongxun.itsys.base.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yuntongxun.itsys.base.common.constans.CookieConstant;
import com.yuntongxun.itsys.base.common.constans.StatusConstant;
import com.yuntongxun.itsys.base.common.util.*;
import com.yuntongxun.itsys.base.po.CookieParam;
import com.yuntongxun.itsys.base.po.ResultMsg;
import com.yuntongxun.itsys.base.po.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.service.RoleService;
import com.yuntongxun.itsys.base.service.UserService;
import com.yuntongxun.itsys.base.vo.Response;
/**
 * 用户管理
 * @author Lake.zhang
 *
 */
@RestController
public class UserController extends AbstractController {

	@Autowired
	private UserService userv;
	@Autowired
	private RoleService roleserv;
	private final Logger log = LogManager.getLogger(UserController.class);
	/**
	 * 用户查询
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 * @throws ItSysException
	 */
	@RequestMapping(value="/user/query",method=RequestMethod.POST)
	public String queryUser(HttpServletRequest req,HttpServletResponse resp,@RequestBody String body)throws ItSysException{
		
		log.info("@Controller queryUser 接收到的参数：body={}",body);
		ListPageUtil listPage=userv.queryUser(body);
		log.info("@Controller queryUser 返回值={}",listPage.getResultList());
		return JSONUtils.toJson(new Response(listPage.getResultList(),listPage.getPageInfo()));
	}
	
	
	
	/**
	 * 根据id查询用户
	 * @param req
	 * @param resp
	 * @param id
	 * @return
	 * @throws ItSysException
	 */
	@RequestMapping(value="/user/get/{id}",method=RequestMethod.POST)
	public String getUserById(HttpServletRequest req,HttpServletResponse resp,@PathVariable String id)throws ItSysException{
		
		log.info("@Controller getUserById 接收到的参数：id={}",id);
		List list=userv.getUserById(id);
		Object obj=new Object();
		if(list.size()>0){
			obj=list.get(0);
		}
		log.info("@Controller getUserById 返回值={}",obj);
		
		return JSONUtils.toJson(new Response(obj));
	}
	
	/**
	 * 新增用户
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 * @throws ItSysException
	 * @throws NoSuchAlgorithmException 
	 */
	@RequestMapping(value="/user/insert",method=RequestMethod.POST)
	public String insertUser(HttpServletRequest req,HttpServletResponse resp,@RequestBody String body)throws ItSysException, NoSuchAlgorithmException{
		
		log.info("@Controller insertUser 接收到的参数：body={}",body);
		userv.addUser(body);
		
		return JSONUtils.toJson(new Response());
	}
	
	/**
	 * 修改用户
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 * @throws ItSysException
	 */
	@RequestMapping(value="/user/update",method=RequestMethod.POST)
	public String updateUser(HttpServletRequest req,HttpServletResponse resp,@RequestBody String body)throws ItSysException{
		
		log.info("@Controller updateUser 接收到的参数：body={}",body);
		userv.updateUser(body);
		
		return JSONUtils.toJson(new Response());
	}
	
	/**
	 * 根据id删除用户
	 * @param req
	 * @param resp
	 * @param id
	 * @return
	 * @throws ItSysException
	 */
	@RequestMapping(value="/user/del/{id}",method=RequestMethod.POST)
	public String delUser(HttpServletRequest req,HttpServletResponse resp,@PathVariable String id)throws ItSysException{
		
		log.info("@Controller delUser 接收到的参数：id={}",id);
		userv.delUser(id);
		
		return JSONUtils.toJson(new Response());
	}
	
	/**
	 * 生成用户
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 * @throws ItSysException
	 * @throws NoSuchAlgorithmException 
	 */
	@RequestMapping(value="/user/make",method=RequestMethod.POST)
	public String makeUser(HttpServletRequest req,HttpServletResponse resp,@RequestBody String body)throws ItSysException, NoSuchAlgorithmException{
		
		log.info("@Controller makeUser 接收到的参数：body={}",body);
		userv.makeUser(body);
		
		return JSONUtils.toJson(new Response());
	}
	
	
	/**
	 * 获取用户角色id
	 * @param req
	 * @param resp
	 * @param id
	 * @return
	 * @throws ItSysException 
	 */
	@RequestMapping(value="/user/role/get/{id}",method=RequestMethod.POST)
	public String getUserRoleById(HttpServletRequest req,HttpServletResponse resp,@PathVariable String id) throws ItSysException{
		printStartTag("getUserById");
		printHttpPacket(req, null);
		log.info("getUserRoleById 输入参数:{}",id);
		Object obj=userv.getUserRoleById(id);
		log.info("getUserRoleById 返回值: {}",obj);
		printEndTag("getUserById");
		return JSONUtils.toJson(new Response(obj));
	}
	
	/**
	 * 保存用户角色信息
	 * @param req
	 * @param resp
	 * @return
	 * @throws ItSysException 
	 */
	@RequestMapping(value="/user/role/save",method=RequestMethod.POST)
	public String addUserRole(HttpServletRequest req,HttpServletResponse resp,@RequestBody String body) throws ItSysException{
		printStartTag("addUserRole");
		printHttpPacket(req, null);
		JsonObject json = new JsonParser().parse(body).getAsJsonObject();
		String userid=json.get("userId")==null?"":json.get("userId").getAsString();
		JsonArray jsonarr=json.get("roleIds").getAsJsonArray();
		if(jsonarr.size()==0){
			roleserv.delUserRoleRByUserId(userid);// 根据用户id先删除用户角色关系
//			throw new ItSysException(BuildError.buildException(Constans.ERR_USER_ROLEIDS_CODE, Constans.ERR_USER_ROLEIDS_MSG));
		}else{
		String [] roleids=new String[jsonarr.size()];
		for(int i=0;i<jsonarr.size();i++){
			roleids[i]=jsonarr.get(i).toString();
		}
		userv.addUserRole(userid, roleids);
		printEndTag("addUserRole");
		}
		return JSONUtils.toJson(new Response());
	}
	/**
	 * 修改用户登录密码
	 * @param req
	 * @param resp
	 * @return
	 * @throws ItSysException 
	 */
	@RequestMapping(value="/user/changepwd",method=RequestMethod.POST)
	public String changepwd(HttpServletRequest request,HttpServletResponse response,@RequestBody User user) throws ItSysException{
/*		printStartTag("changepwd");
		printHttpPacket(req, null);
		JsonObject json = new JsonParser().parse(body).getAsJsonObject();
		String loginName=CookieUtil.getCookieByLoginName(req);
		String pwd=json.get("pwd")==null?"":json.get("pwd").getAsString();
		String oldpwd=json.get("oldpwd")==null?"":json.get("oldpwd").getAsString();
		log.info("changepwd 输入参数:{},{}",loginName,pwd);
		userv.changepwd(loginName, pwd,oldpwd);
		printEndTag("changepwd");
		return JSONUtils.toJson(new Response());*/
		printStartTag("changepwd");
		log.info("changepwd 输入参数:capText=", user.getCapText());

		CookieParam cookieparam = RequestUtil.getTokenByCookie(request);
		if (cookieparam == null || StringUtil.isBlank(cookieparam.getLoginName()) || StringUtil.isBlank(cookieparam.getToken())) {
			logger.warn("Invalid request,cookie info:{}, loginName:{},token:{}", cookieparam, cookieparam == null ? null : cookieparam.getLoginName(), cookieparam == null ? null : cookieparam.getToken());
			return JSONUtils.toJson(new ResultMsg(StatusConstant.VALIDATE_FAIL_CODE,
					StatusConstant.COOKIE_NO_TOKEN_LOGINNAME_MSG));
		}

		printStartTag("updatePwd");
		Cookie cookie = CookieUtil.getCookieByName(request, "update_images_uuId");
		String uuId = "";
		if (cookie != null) {
			uuId = cookie.getValue();
			logger.info("获取图片校验码update_images_uuId={}", uuId);
			user.setUuID(uuId);
		}
		user.setLoginName(cookieparam.getLoginName());
		// 登录验证
		ResultMsg resultMsg = userv.updateUserByLoginName(user);
		if (resultMsg.getStatusCode().equals(StatusConstant.SUCCESS_CODE)) {
			RequestUtil.setCookie(response, CookieConstant.KEY_LOGING_NAME, null, 0);
			RequestUtil.setCookie(response, CookieConstant.KEY_TOKEN, null, 0);
			RequestUtil.setCookie(response, CookieConstant.COOKIE_NICKNAME, null, 0);
			RequestUtil.setCookie(response, CookieConstant.COOKIE_XTYNAME, null, 0);
			RequestUtil.setCookie(response, CookieConstant.COOKIE_XTYPHONE, null, 0);
		}

		String result = JSONUtils.toJson(resultMsg);
		return result;
	}

	@Override
	protected String getClassName() {
		// TODO Auto-generated method stub
		return UserController.class.getName();
	}



}
