package com.yuntongxun.itsys.base.service.impl;

import java.math.BigDecimal;
import java.util.*;

import com.yuntongxun.itsys.base.dao.impl.DataEnti;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.dao.RoleDao;
import com.yuntongxun.itsys.base.po.Role;
import com.yuntongxun.itsys.base.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roledao;
	private final Logger log = LogManager.getLogger(RoleServiceImpl.class);

	@Override
	public ListPageUtil queryRole(String body) throws ItSysException {
/*        List<DataEnti> dataEntis = roledao.queryRole1(1, 200000);
        BigDecimal bigDecimal = new BigDecimal(0);//最大直径
        Set<String> set = new HashSet<>();      //编码集合
        for (DataEnti dataEnti:dataEntis) {
        if(dataEnti.getL() != null && !"".equals(dataEnti.getL())){
            bigDecimal = new BigDecimal(Collections.max(Arrays.asList(dataEnti.getL().split(","))));
        }
        if(dataEnti.getK() != null && !"".equals(dataEnti.getK())){
            set = new HashSet<>(Arrays.asList(dataEnti.getK().split(",")));
            if(set.contains("17") || set.contains("18") || set.contains("19")){
				roledao.insert1(dataEnti);
            }else if((set.contains("07") || set.contains("11") || set.contains("12") || set.contains("14") || set.contains("15")) ||
                    ((set.contains("08") || set.contains("09") || set.contains("10") || set.contains("13") || set.contains("16")) && bigDecimal.compareTo(new BigDecimal(1.0)) >= 0)
                    ){
                roledao.insert(dataEnti);
            }else if((set.contains("08") || set.contains("10") || set.contains("13")) && bigDecimal.compareTo(new BigDecimal(1.0)) < 0){
				roledao.insert2(dataEnti);
            }else if((set.contains("02") || set.contains("03") || set.contains("04") || set.contains("05") || set.contains("06")) *//*&& bigDecimal.compareTo(new BigDecimal(1.0)) < 0*//*){
				roledao.insert3(dataEnti);
            }
        }
        }*/
        // TODO Auto-generated method stub
		JsonObject json = new JsonParser().parse(body).getAsJsonObject();
		String name = json.get("name") == null ? "" : json.get("name").getAsString();
		int pageNo = json.get("pageNo") == null ? 0 : json.get("pageNo").getAsInt();
		int pageSize = json.get("pageSize") == null ? -1 : json.get("pageSize").getAsInt();
		ListPageUtil listPage = roledao.queryRole(name, pageNo, pageSize);
		return listPage;
	}

	@Override
	public List getRoleById(String id) throws ItSysException {
		// TODO Auto-generated method stub
		List obj = null;
		if (!StringUtil.isEmpty(id)) {
			obj = roledao.getRoleById(id);
		} else {
			throw new ItSysException(Constans.ERR_ROLE_ID_CODE, Constans.ERR_ROLE_ID_MSG);
		}
		return obj;
	}

	@Override
	@Transactional
	public void saveRole(String json) throws ItSysException {
		if (json != null) {
			Role role = (Role) JSONUtils.JsonToObject(json, Role.class);
			if(StringUtil.isEmpty(role.getName())){
				throw new ItSysException(
						Constans.ERR_ROLE_NAME_CODE, Constans.ERR_ROLE_NAME_MSG);
			}
			roledao.saveRole(role);
		} else {
			throw new ItSysException(
					Constans.ERR_ROLE_NULL_CODE, Constans.ERR_ROLE_NULL_MSG);
		}
	}

	@Override
	@Transactional
	public void updateRole(String json) throws ItSysException {
		// TODO Auto-generated method stub
		if (json != null) {
			Role role = (Role) JSONUtils.JsonToObject(json, Role.class);
			String id = role.getId() + "";
			if (!StringUtil.isEmpty(id)) {
				List list = (List) getRoleById(id);
				if (list.size() == 0) {
					throw new ItSysException(
							Constans.ERR_ROLE_NOHAVE_CODE, Constans.ERR_ROLE_NOHAVE_MSG);
				}
			} else {
				throw new ItSysException(
						Constans.ERR_ROLE_ID_CODE, Constans.ERR_ROLE_ID_MSG);
			}
			if (!StringUtil.isEmpty(id)) {
				roledao.updateRole(role);
			}
		} else {
			throw new ItSysException(
					Constans.ERR_ROLE_NULL_CODE, Constans.ERR_ROLE_NULL_MSG);
		}
	}

	@Override
	public Object getRoleMenuById(String id) throws ItSysException {
		// TODO Auto-generated method stub
		Object obj = null;
		if (!StringUtil.isEmpty(id)) {
			obj = roledao.getRoleMenuById(id);
		} else {
			throw new ItSysException(Constans.ERR_ROLE_ID_CODE, Constans.ERR_ROLE_ID_MSG);
		}
		return obj;
	}

	@Override
	@Transactional
	public void saveRoleMenu(JsonObject json) throws ItSysException {
		// TODO Auto-generated method stub
		if (json != null) {
			String id = json.get("roleId") == null ? "" : json.get("roleId").getAsString();
			if (StringUtil.isEmpty(id)) {
				throw new ItSysException(
						Constans.ERR_ROLE_MENU_ID_CODE, Constans.ERR_ROLE_MENU_ID_MSG);
			}
			JsonArray jsonarr = json.get("menuIds").getAsJsonArray();
			if (jsonarr.size() != 0) {
				String[] menuids = new String[jsonarr.size()];
				for (int i = 0; i < jsonarr.size(); i++) {
					menuids[i] = jsonarr.get(i).toString();
				}

				roledao.delRoleMenuRByRoleId(id);// 删除该角色对应的菜单
				roledao.saveRoleMenu(menuids, id);// 添加角色菜单
			} else {
				roledao.delRoleMenuRByRoleId(id);// 删除该角色对应的菜单
			}
		} else {
			throw new ItSysException(
					Constans.ERR_ROLE_NULL_CODE, Constans.ERR_ROLE_NULL_MSG);
		}
	}

	@Override
	public Object getRoleResourceById(String roleid) throws ItSysException {
		// TODO Auto-generated method stub
		Object obj = null;
		if (!StringUtil.isEmpty(roleid)) {
			obj = roledao.getRoleResourceById(roleid);
		} else {
			throw new ItSysException(Constans.ERR_ROLE_ID_CODE, Constans.ERR_ROLE_ID_MSG);
		}
		return obj;
	}

	@Override
	@Transactional
	public void saveRoleResource(JsonObject json) throws ItSysException {
		// TODO Auto-generated method stub
		if (json != null) {
			String id = json.get("roleId") == null ? "" : json.get("roleId").getAsString();
			if (StringUtil.isEmpty(id)) {
				throw new ItSysException(Constans.ERR_ROLE_RESOURCE_ID_CODE,
						Constans.ERR_ROLE_RESOURCE_ID_MSG);
			}
			JsonArray jsonarr = json.get("reSourceIds").getAsJsonArray();
			if (jsonarr.size() == 0) {
				// throw new
				// ItSysException(BuildError.buildException(Constans.ERR_ROLE_RESOURCE_IDS_CODE,
				// Constans.ERR_ROLE_RESOURCE_IDS_MSG));
				roledao.delRoleResourceRByRoleId(id);// 删除角色资源数据
			} else {
				String[] resIds = new String[jsonarr.size()];
				for (int i = 0; i < jsonarr.size(); i++) {
					resIds[i] = jsonarr.get(i).toString();
				}
				log.debug("@Service 角色资源绑定数据为   资源id:{},角色id:{}", resIds, id);
				roledao.delRoleResourceRByRoleId(id);// 删除角色资源数据
				roledao.saveRoleResource(resIds, id);// 保存角色资源数据

			}
		} else {
			throw new ItSysException(
					Constans.ERR_ROLE_NULL_CODE, Constans.ERR_ROLE_NULL_MSG);
		}
	}

	@Override
	@Transactional
	public void deleteRole(String roleid) throws ItSysException {
		// TODO Auto-generated method stub
		if (StringUtil.isEmpty(roleid)) {
			throw new ItSysException(Constans.ERR_ROLE_ID_CODE, Constans.ERR_ROLE_ID_MSG);
		}
		List list=roledao.getRoleById(roleid);
		Role role=null;
		if(list.size()>0){
			role=JSONUtils.toBean(JSONUtils.toJson(list.get(0)), Role.class);
		}
		if(role!=null && role.getName().equals("超级管理员")){
			throw new ItSysException(Constans.ERR_ROLE_SUPERUSER_NDEL_CODE, Constans.ERR_ROLE_SUPERUSER_NDEL_MSG);
		}
		roledao.deleteRole(roleid);
		roledao.delRoleMenuRByRoleId(roleid);// 删除角色菜单关联数据
		/**
		 * 现已将按钮、页面和菜单绑定到auth表，页面和按钮与请求表resource对应。
		 * 角色没有直接对应请求，而是对应按钮、菜单和页面。所以该方法弃用
		 */
//		roledao.delRoleResourceRByRoleId(roleid);// 删除角色资源管理数据
		roledao.delRoleUserRByRoleId(roleid);// 删除用户角色关系
	}

	@Override
	public void delRoleMenuRByMenuId(String menuid) throws ItSysException {
		// TODO Auto-generated method stub
		roledao.delRoleMenuRByMenuId(menuid);
	}

	@Override
	public void delRoleResourceByResourceId(String resourceid) throws ItSysException {
		// TODO Auto-generated method stub
		roledao.delRoleResourceByResourceId(resourceid);
	}

	@Override
	public void delUserRoleRByUserId(String userid) throws ItSysException {
		// TODO Auto-generated method stub
		roledao.delUserRoleRByUserId(userid);
	}

	@Override
	public void delRoleMenuRByRoleId(String roleid) throws ItSysException {
		// TODO Auto-generated method stub
		roledao.delRoleMenuRByRoleId(roleid);
	}

	@Override
	public void delRoleResourceRByRoleId(String roleid) throws ItSysException {
		// TODO Auto-generated method stub
		roledao.delRoleResourceRByRoleId(roleid);
	}

	@Override
	public void delRoleUserRByRoleId(String roleid) throws ItSysException {
		// TODO Auto-generated method stub

	}

}
