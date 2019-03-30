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
import com.google.gson.JsonParser;
import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.service.RoleService;
import com.yuntongxun.itsys.base.vo.Response;

/**
 * 权限管理
 * 
 * @author zhangzl
 *
 */
@RestController
public class RoleController extends AbstractController {

	private final Logger log = LogManager.getLogger(RoleController.class);

	@Autowired
	private RoleService roleser;

	/**
	 * 查询角色
	 * 
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 * @throws ItSysException 
	 */
	@RequestMapping(value = "role/query", method = RequestMethod.POST)
	public String queryRole(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) throws ItSysException {

		log.info("queryRole 输入参数: {}", body);
		ListPageUtil listPage = roleser.queryRole(body);
		log.info("queryRole 返回值:{}", listPage.getResultList());
		return JSONUtils.toJson(new Response(listPage.getResultList(), listPage.getPageInfo()));
	}

	/**
	 * 根据id获取角色
	 * 
	 * @param id
	 * @param req
	 * @return
	 * @throws ItSysException
	 */
	@RequestMapping(value = "/role/get/{id}", method = RequestMethod.POST)
	public String getRoleById(HttpServletRequest req, HttpServletResponse resp, @PathVariable String id)
			throws ItSysException {
		log.info("getRoleById 输入参数:{}", id);
		List list = roleser.getRoleById(id);
		Object obj = new Object();
		if (list.size() > 0) {
			obj = list.get(0);
		}
		log.info("getRoleBYId 返回值：{}", obj);
		return JSONUtils.toJson(new Response(obj));
	}

	/**
	 * 保存角色
	 * 
	 * @param role
	 * @param req
	 * @return
	 * @throws ItSysException
	 */
	@RequestMapping(value = "/role/insert", method = RequestMethod.POST)
	public String addRole(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body)
			throws ItSysException {
		log.info("addRole 输入参数:{}", body);
		roleser.saveRole(body);
		return JSONUtils.toJson(new Response());
	}

	/**
	 * 
	 * 更新角色
	 * 
	 * @param role
	 * @param req
	 * @return
	 * @throws ItSysException
	 */
	@RequestMapping(value = "/role/update", method = RequestMethod.POST)
	public String updateRole(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body)
			throws ItSysException {
		log.info("updateRole 输入参数:{}", body);
		roleser.updateRole(body);
		return JSONUtils.toJson(new Response());
	}

	/**
	 * 查询角色菜单数据
	 * 
	 * @param roleid
	 * @param req
	 * @return
	 * @throws ItSysException
	 */
	@RequestMapping(value = "/role/menu/get/{roleId}", method = RequestMethod.POST)
	public String getRoleMenuByRoleId(HttpServletRequest req, HttpServletResponse resp,
			@PathVariable("roleId") String roleid) throws ItSysException {
		log.info("getRoleMenuByRoleId 输入参数: {}", roleid);
		Object obj = roleser.getRoleMenuById(roleid);
		log.info("getRoleMenuByRoleId 返回值 : {}", obj);
		return JSONUtils.toJson(new Response(obj));
	}

	/**
	 * 保存角色菜单
	 * 
	 * @param req
	 * @param body
	 * @return
	 * @throws ItSysException
	 */
	@RequestMapping(value = "/role/menu/save", method = RequestMethod.POST)
	public String saveRoleMenu(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body)
			throws ItSysException {
		JsonObject json = new JsonParser().parse(body).getAsJsonObject();
		log.info("saveRoleMenu 输入参数 : {}", json);
		roleser.saveRoleMenu(json);
		return JSONUtils.toJson(new Response());
	}

	/**
	 * 根据roleid获取角色资源
	 * 
	 * @param req
	 * @param roleid
	 * @return
	 * @throws ItSysException
	 */
	@RequestMapping(value = "/role/resource/get/{roleId}", method = RequestMethod.POST)
	public String getRoleSourceById(HttpServletRequest req, HttpServletResponse resp,
			@PathVariable("roleId") String roleid) throws ItSysException {
		log.info("getRoleSourceById 输入参数：{}", roleid);
		Object obj = roleser.getRoleResourceById(roleid);
		log.info("getRoleSourceById 返回值:{}", obj);
		return JSONUtils.objectToJsonDateSerializer(new Response(obj), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 根据id删除角色
	 * 
	 * @param req
	 * @param resp
	 * @param roleid
	 * @return
	 * @throws ItSysException
	 */
	@RequestMapping(value = "/role/delete/{roleId}", method = RequestMethod.POST)
	public String deleteRole(HttpServletRequest req, HttpServletResponse resp, @PathVariable("roleId") String roleid)
			throws ItSysException {
		log.info("deleteRole 输入参数：{}", roleid);
		roleser.deleteRole(roleid);
		return JSONUtils.toJson(new Response());
	}

	/**
	 * 保存角色资源
	 * 
	 * @param req
	 * @param body
	 * @return
	 * @throws ItSysException
	 */
	@RequestMapping(value = "/role/resource/save", method = RequestMethod.POST)
	public String saveRoleSource(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body)
			throws ItSysException {
		log.info("saveRoleSource 输入参数: {}", body);
		JsonObject json = new JsonParser().parse(body).getAsJsonObject();
		roleser.saveRoleResource(json);
		return JSONUtils.toJson(new Response());
	}

	@Override
	protected String getClassName() {
		// TODO Auto-generated method stub
		return RoleController.class.getName();
	}

}
