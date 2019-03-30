package com.yuntongxun.itsys.base.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yuntongxun.itsys.base.vo.CityResourceVo;
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
import com.yuntongxun.itsys.base.po.AuthRequestR;
import com.yuntongxun.itsys.base.po.PageInfo;
import com.yuntongxun.itsys.base.po.Resource;
import com.yuntongxun.itsys.base.service.ResourceService;
import com.yuntongxun.itsys.base.vo.Response;

/**
 * 资源操作
 * @author zhangzl
 *
 */
@RestController
public class ResourceController extends AbstractController {
	
	private final Logger log = LogManager.getLogger(ResourceController.class);
	
	@Autowired
	private ResourceService resvice;

	/**
	 * 查询资源
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 * @throws ItSysException 
	 */
	@RequestMapping(value="/resource/query",method=RequestMethod.POST)
	public String queryResource(HttpServletRequest req,HttpServletResponse resp, @RequestBody String body) throws ItSysException{
		printStartTag("queryResource");
		printHttpPacket(req, null);
		JsonObject json=new JsonParser().parse(body).getAsJsonObject();
		log.info("queryResource 输入参数:{}",json);
		PageInfo pageinfo=null;
		ListPageUtil listPage = resvice.QueryResource(json);
		log.info("queryResource 返回值:{}",listPage.getResultList());
		printEndTag("queryResource");
		return JSONUtils.objectToJsonDateSerializer(new Response(listPage.getResultList(),listPage.getPageInfo()), "yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 根据Id查询资源
	 * @param req
	 * @param resp
	 * @param id
	 * @return
	 * @throws ItSysException 
	 */
	@RequestMapping(value="/resource/get/{id}", method=RequestMethod.POST)
	public String getResourceById(HttpServletRequest req, HttpServletResponse resp, @PathVariable String id) throws ItSysException{
		printStartTag("getResourceById");
		printHttpPacket(req, null);
		log.info("getResourceById 输入参数：{}",id);
		Resource resource = resvice.getResourceObjectById(id);

		List<AuthRequestR> authRequestRList = resvice.getAuthRequestByRequestId(Integer.toString(resource.getId()));
		String[] authIdArr = new String[authRequestRList.size()];
		for (int i = 0; i < authRequestRList.size(); i++){
			authIdArr[i] = Integer.toString(authRequestRList.get(i).getAuthId());
		}
		resource.setAscriptionArr(authIdArr);
		log.info("getResourceById 返回值: {}", resource);
		printEndTag("getResourceById");
		return JSONUtils.objectToJsonDateSerializer(new Response(resource), "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 资源新增保存
	 * @param req
	 * @param resp
	 * @param res
	 * @return
	 * @throws ItSysException 
	 */
	@RequestMapping(value="/resource/insert",method=RequestMethod.POST)
	public String addResource(HttpServletRequest req,HttpServletResponse resp,@RequestBody Resource res) throws ItSysException{
		printStartTag("addResource");
		printHttpPacket(req, null);
		resvice.addResource(res);
		printEndTag("addResource");
		return JSONUtils.toJson(new Response());
	}
	
	/**
	 * 更新资源
	 * @param req
	 * @param resp
	 * @param res
	 * @return
	 * @throws ItSysException 
	 */
	@RequestMapping(value="/resource/update",method=RequestMethod.POST)
	public String updateResource(HttpServletRequest req,HttpServletResponse resp,@RequestBody Resource res) throws ItSysException{
		printStartTag("updateResource");
		printHttpPacket(req, null);
		resvice.updateResource(res);
		printEndTag("updateResource");
		return JSONUtils.toJson(new Response());
	}
	/**
	 * 获取所有资源 pages buttons requests  
	 * @param req
	 * @param resp
	 * @return
	 * @throws ItSysException
	 */
	@RequestMapping(value="/resource/getall",method=RequestMethod.POST)
	public String getAllResource(HttpServletRequest req,HttpServletResponse resp) throws ItSysException{
		printStartTag("getAllResource");
		printHttpPacket(req, null);
		log.info("进入geAllResource 方法");
		Object ojb=resvice.getAllResource();
		printEndTag("getAllResource");
		log.info("getAllResource 返回结果为：{}",JSONUtils.toJson(new Response(ojb)));
		return JSONUtils.toJson(new Response(ojb));
	}
	


	/*
	 * 初始化地址地点
	 *
	 * @author ${zongt}
	 * @since v2.2.1
	 */
	@RequestMapping(value="/resource/city/query/{pid}")
	public String deleteResource(HttpServletRequest req,HttpServletResponse resp ,@PathVariable Integer pid) throws ItSysException{
		printStartTag("/resource/city/query");
/*		printHttpPacket(req, null);
		log.info("deleteResource 输入参数：{}",resourceId);*/
		List<Map<String,Object>> maps=resvice.queryCity(null,pid) ;
		//CityResourceVo cityResourceVo=resvice.queryCity(level);
		printEndTag("/resource/city/query");
		return JSONUtils.toJson(new Response(maps));
	}
	@Override
	protected String getClassName() {
		// TODO Auto-generated method stub
		return ResourceController.class.getName();
	}

}
