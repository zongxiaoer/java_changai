package com.yuntongxun.itsys.base.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.alibaba.fastjson.JSONObject;
import com.yuntongxun.itsys.base.vo.CityResourceVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.yuntongxun.itsys.base.cache.CacheManager;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.dao.ResourceDao;
import com.yuntongxun.itsys.base.po.AllResource;
import com.yuntongxun.itsys.base.po.AuthRequestR;
import com.yuntongxun.itsys.base.po.Resource;
import com.yuntongxun.itsys.base.po.ResourcePages;
import com.yuntongxun.itsys.base.service.ResourceService;
import com.yuntongxun.itsys.base.service.RoleService;

@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceDao resdao;
	@Autowired
	private RoleService roleservice;
	@Autowired
	private CacheManager cacheManager;

	private final Logger log = LogManager.getLogger(ResourceServiceImpl.class);

	@Override
	public ListPageUtil QueryResource(JsonObject json) throws ItSysException {

		String name = json.get("name") == null ? "" : json.get("name").getAsString();
		String url = json.get("url") == null ? "" : json.get("url").getAsString();
		String type = json.get("type") == null ? "" : json.get("type").getAsString();
		int pageNo = json.get("pageNo") == null ? 0 : json.get("pageNo").getAsInt();
		int pageSize = json.get("pageSize") == null ? -1 : json.get("pageSize").getAsInt();
		ListPageUtil listPage = resdao.QueryResource(name, url, type, pageNo, pageSize);
		return listPage;
	}

	@Override
	public List getResourceById(String id) throws ItSysException {
		// TODO Auto-generated method stub
		List obj = null;
		if (!StringUtil.isEmpty(id)) {
			obj = resdao.getResourceById(id);
		}
		return obj;
	}

	@Override
	public Resource getResourceObjectById(String id) throws ItSysException {
		Resource resource = null;
		if (!StringUtil.isEmpty(id)) {
			resource = resdao.getResourceObjectById(id);
		}
		return resource;
	}

	@Override
	@Transactional
	public void addResource(Resource res) throws ItSysException {
		// TODO Auto-generated method stub
		if (res != null) {
			if (StringUtil.isEmpty(res.getName())) {
				throw new ItSysException(
						Constans.ERROR_RESOURCE_NAME_CODE, Constans.ERROR_RESOURCE_NAME_MSG);
			}
			if (StringUtil.isEmpty(res.getType())) {
				throw new ItSysException(
						Constans.ERROR_RESOURCE_TYPE_CODE, Constans.ERROR_RESOURCE_TYPE_MSG);
			}
			if (StringUtil.isEmpty(res.getSaveLog())) {
				throw new ItSysException(Constans.ERROR_RESOURCE_SAVELOG_CODE,
						Constans.ERROR_RESOURCE_SAVELOG_MSG);
			}
			if (StringUtil.isEmpty(res.getUrl())) {
				throw new ItSysException(
						Constans.ERROR_RESOURCE_URL_CODE, Constans.ERROR_RESOURCE_URL_MSG);
			}
			Object[] parm = new Object[] { res.getName(), res.getType(), res.getUrl(), res.getDesc(),
					res.getSaveLog() };
			resdao.addResource(parm);
			
			ListPageUtil requestList=resdao.QueryResource(res.getName(), null, null, -1, -1);
			if(requestList.getResultList()!=null&&requestList.getResultList().size()>0){
				Resource resource=JSONUtils.toBean(JSONUtils.toJson(requestList.getResultList().get(0)), Resource.class);
				String[] arr = res.getAscriptionArr();
				if (arr != null && arr.length > 0) {// 请求绑定的按钮、页面关系数据
					log.info("绑定请求页面/按钮关系....");
					bindAuthRequestR(resource.getId() + "", arr);
				}
			}
			
			log.info("刷新缓存....");
			// 重载缓存
			ReLoadCache();

		} else {
			throw new ItSysException(
					Constans.ERROR_RESOURCE_NULL_CODE, Constans.ERROR_RESOURCE_NULL_MSG);
		}
	}

	@Override
	@Transactional
	public void updateResource(Resource res) throws ItSysException {
		// TODO Auto-generated method stub
		if (res != null) {
			String id = res.getId() + "";
			if (!StringUtil.isEmpty(id)) {
				List list = (List) getResourceById(id + "");
				if (list.size() == 0) {
					throw new ItSysException(Constans.ERROR_RESOURCE_NOHAVE_CODE,
							Constans.ERROR_RESOURCE_NOHAVE_MSG);
				}
			} else {
				throw new ItSysException(
						Constans.ERROR_RESOURCE_ID_CODE, Constans.ERROR_RESOURCE_ID_MSG);
			}
			if (StringUtil.isEmpty(res.getName())) {
				throw new ItSysException(
						Constans.ERROR_RESOURCE_NAME_CODE, Constans.ERROR_RESOURCE_NAME_MSG);
			}
			if (StringUtil.isEmpty(res.getType())) {
				throw new ItSysException(
						Constans.ERROR_RESOURCE_TYPE_CODE, Constans.ERROR_RESOURCE_TYPE_MSG);
			}
			if (StringUtil.isEmpty(res.getSaveLog())) {
				throw new ItSysException(Constans.ERROR_RESOURCE_SAVELOG_CODE,
						Constans.ERROR_RESOURCE_SAVELOG_MSG);

			}
			if (StringUtil.isEmpty(res.getUrl())) {
				throw new ItSysException(
						Constans.ERROR_RESOURCE_URL_CODE, Constans.ERROR_RESOURCE_URL_MSG);
			}
			Object[] parm = new Object[] { res.getName(), res.getType() + "", res.getUrl(), res.getDesc(),
					res.getSaveLog(), res.getId() + "", };
			resdao.updateResource(parm);
			String[] arr = res.getAscriptionArr();
			if (arr != null && arr.length > 0) {// 请求绑定的按钮、页面关系数据
				log.info("绑定请求页面/按钮关系....");
				bindAuthRequestR(res.getId() + "", arr);
			}
			log.info("刷新缓存....");
			// 重载缓存
			ReLoadCache();
		}
	}

	@Override
	public List getAllRequest() throws ItSysException {
		// TODO Auto-generated method stub
		List list = resdao.getAllRequest();
		return list;
	}

	@Override
	public List getAllPages() throws ItSysException {
		// TODO Auto-generated method stub
		List list = resdao.getAllPages();
		return list;
	}

	@Override
	public List getAllButtonByPage(String page) throws ItSysException {
		// TODO Auto-generated method stub
		List list = resdao.getAllButtonByPage(page);
		return list;
	}

	@Override
	public Object getAllResource() throws ItSysException {
		// TODO Auto-generated method stub
		AllResource allresource = new AllResource();
		List<ResourcePages> repg = new ArrayList();

		List request = getAllRequest();
		List page = getAllPages();
		if (page != null) {
			for (int i = 0; i < page.size(); i++) {
				ResourcePages respage = new ResourcePages();
				respage = JSONUtils.toBean(JSONUtils.toJson(page.get(i)), ResourcePages.class);
				List button = getAllButtonByPage(respage.getId() + "");
				respage.setButtons(button == null ? "" : button);
				repg.add(respage);
			}
			allresource.setPages(repg);
			allresource.setRequests(request);
		}
		return allresource;
	}

	@Override
	@Transactional
	public void deleteResource(String resourceid) throws ItSysException {
		// TODO Auto-generated method stub
		if (StringUtil.isEmpty(resourceid)) {
			throw new ItSysException(
					Constans.ERROR_RESOURCE_ID_CODE, Constans.ERROR_RESOURCE_ID_MSG);
		}
		resdao.deleteResource(resourceid);// 删除资源
		/**
		 * 现已将按钮、页面和菜单绑定到auth表，页面和按钮与请求表resource对应。
		 * 角色没有直接对应请求，而是对应按钮、菜单和页面。所以该方法弃用
		 */
		// roleservice.delRoleResourceByResourceId(resourceid);// 删除角色资源级联数据

		// 删除请求对应页面和按钮关系数据表
		delAuthRequestR(resourceid);
		// 重载缓存
		ReLoadCache();
	}

	/**
	 * 对资源表所有type=3的资源增加缓存
	 */
	@Override
	public List getResourceByType(String type) throws ItSysException {
		// TODO Auto-generated method stub
		String cacheValue = cacheManager.get(Constans.CACHE_CUSTOM_RESOURCE_NAME, Constans.CACHE_CUSTOM_RESOURCE_KEY);
		List list = new ArrayList();
		if (!StringUtil.isEmpty(cacheValue)) {
			list = (List) JSONUtils.fromJson(cacheValue, new TypeToken<List>() {
			});
		} else {
			list = resdao.getResourceByType(type);
			String value = JSONUtils.objectToJsonDateSerializer(list, "");
			cacheManager.set(Constans.CACHE_CUSTOM_RESOURCE_NAME, Constans.CACHE_CUSTOM_RESOURCE_KEY, value,
					Constans.CACHE_LOCAL_DICTIONARY_EXPIRE);

		}
		return list;
	}

	/**
	 * 重载缓存
	 * 
	 * @throws ItSysException
	 */
	@PostConstruct
	public void ReLoadCache() throws ItSysException {
		List list = resdao.getResourceByType("3");// 3为查询所有请求
		log.info("@Service ReLoadCache 重新加载请求数据缓存,数据长度为：{}", list.size());
		String value = JSONUtils.objectToJsonDateSerializer(list, "");
		cacheManager.set(Constans.CACHE_CUSTOM_RESOURCE_NAME, Constans.CACHE_CUSTOM_RESOURCE_KEY, value,
				Constans.CACHE_LOCAL_DICTIONARY_EXPIRE);

	}

	@Override
	@Transactional
	public void bindAuthRequestR(String requestId, String[] authIds) throws ItSysException {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(requestId)) {
			throw new ItSysException(
					Constans.ERROR_RESOURCE_ID_CODE, Constans.ERROR_RESOURCE_ID_MSG);
		}
		if (authIds.length == 0) {
			throw new ItSysException(Constans.ERROR_RESOURCE_BIND_AUTH_NULL_CODE,
					Constans.ERROR_RESOURCE_BIND_AUTH_NULL_MSG);
		}
		resdao.bindAuthRequestR(requestId, authIds);
	}

	@Override
	@Transactional
	public void delAuthRequestR(String requestId) throws ItSysException {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(requestId)) {
			throw new ItSysException(
					Constans.ERROR_RESOURCE_ID_CODE, Constans.ERROR_RESOURCE_ID_MSG);
		}
		resdao.delAuthRequestR(requestId);
	}

	@Override
	public List<AuthRequestR> getAuthRequestByRequestId(String requestId) throws ItSysException {
		if (StringUtils.isEmpty(requestId)) {
			throw new ItSysException(
					Constans.ERROR_RESOURCE_ID_CODE, Constans.ERROR_RESOURCE_ID_MSG);

		}
		return resdao.getAuthRequestByRequestId(requestId);
	}

	@Override
	public List<Map<String,Object>> queryCity(List<Map<String,Object>> maps, Integer pid) {
		if (null == maps || maps.size() == 0) {
			List<CityResourceVo> cityResourceVoList= resdao.queryCity();
			String json = JSONObject.toJSONString(cityResourceVoList);
			maps=(List<Map<String, Object>>) JSONObject.parse(json);
		}
		List<Map<String, Object>> orgList = new ArrayList<>();
		if(null != maps || maps.size() > 0){
			for (Map<String, Object> item : maps) {
				if(pid.equals(item.get("pid"))){
					//将当前对象id做为pid递归调用当前方法，获取下级结果
					List<Map<String, Object>> children = queryCity(maps, Integer.valueOf(item.get("id").toString()));
					//将子结果集存入当前对象的children字段中
					item.put("children", children);
					//添加当前对象到主结果集中
					orgList.add(item);
				}
			}
		}
		return orgList;
/*		if (null == orgMaps || orgMaps.size() == 0) {
			List<StatusResponseCodeEntity> list = statusResponseCodeRepository.findAll();
			String json_list = JSONObject.toJSONString(list);
			orgMaps = (List<Map<String, Object>>) JSONObject.parse(json_list);
		}
		List<Map<String, Object>> orgList = new ArrayList<>();
		if (orgMaps != null && orgMaps.size() > 0) {
			for (Map<String, Object> item : orgMaps) {
				//比较传入pid与当前对象pid是否相等
				if (pid.equals(item.get("pid"))) {
					//将当前对象id做为pid递归调用当前方法，获取下级结果
					List<Map<String, Object>> children = generateOrgMapToTree(orgMaps, Integer.valueOf(item.get("id").toString()));
					//将子结果集存入当前对象的children字段中
					item.put("children", children);
					//添加当前对象到主结果集中
					orgList.add(item);
				}
			}
		}
		return orgList;*/
		//return null;
	}

}
