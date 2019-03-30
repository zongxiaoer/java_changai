package com.yuntongxun.itsys.base.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.alibaba.druid.util.StringUtils;
import com.yuntongxun.itsys.base.common.exception.ItSysDaoException;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.dao.AuthDao;
import com.yuntongxun.itsys.base.po.AuthModel;

@Repository
public class AuthDaoImpl implements AuthDao {
	private final Logger log = LogManager.getLogger(AuthDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbc;

	@Override
	public List getAuthTree() throws ItSysDaoException {
		// TODO Auto-generated method stub
		String sql = "select m.id,m.`name`,m.displayName,m.level,m.pId,m.sort,m.operation,m.url,m.type,m.desc from itsys_auth m where 1=1 ";
		List list = jdbc.queryForList(sql);
		return list;
	}

	@Override
	public void saveAuthParm(AuthModel auth) throws ItSysException {
		// TODO Auto-generated method stub
		String sql = "insert into itsys_auth(`name`,displayName,pid,sort,operation,url,`type`,`level`,`desc`,dateCreated,updateTime) values(?,?,?,?,?,?,?,?,?,now(),now())";
		jdbc.update(sql, new Object[] { auth.getName(), auth.getDisplayName(), auth.getpId(), auth.getSort(),
				auth.getOperation(), auth.getUrl(), auth.getType(),auth.getLevel(),auth.getDesc() });
	}

	@Override
	public List getAuthParmById(String id) throws ItSysException {
		// TODO Auto-generated method stub
		String sql = "select m.id,m.`name`,m.displayName,m.level,m.pId,m.sort,m.operation,m.url,m.type,m.desc from itsys_auth m where 1=1 and `id`=? ";
		List list = jdbc.queryForList(sql, new Object[] { id });
		return list;
	}

	@Override
	public void updateAuthParm(AuthModel auth) throws ItSysException {
		// TODO Auto-generated method stub
		String sql = "update itsys_auth set `name`=?,displayName=?"
				+ ",url=?, `desc`=?, sort=?,operation=?, pid=?,`type`=?,`level`=?,updatetime=now() where `id`=?";
		jdbc.update(sql, new Object[]{ auth.getName(), auth.getDisplayName(), auth.getUrl(),auth.getDesc(), auth.getSort(),
				auth.getOperation(),auth.getpId(), auth.getType(),auth.getLevel(),auth.getId()});
	}

	@Override
	public void delAuthParm(String id) throws ItSysException {
		// TODO Auto-generated method stub
		String sql="delete from itsys_auth where `id`=?";
		jdbc.update(sql, new Object[]{id});
	}

	@Override
	public ListPageUtil queryAuthParm(String id, String name, String type,String url,int pageNo,int pageSize) throws ItSysException {
		// TODO Auto-generated method stub
		List parm=new ArrayList();
		StringBuffer sb=new StringBuffer("");
		if(!StringUtil.isEmpty(id)){
			parm.add(id);
			sb.append(" and `id`=?");
		}
		if(!StringUtil.isEmpty(name)){
			parm.add("%"+name+"%");
			sb.append(" and `name` like ?");
		}
		if(!StringUtil.isEmpty(type)){
			parm.add(type);
			sb.append(" and `type`=?");
		}
		if(!StringUtils.isEmpty(url)){
			parm.add("%"+url+"%");
			sb.append(" and `url` like ?");
		}
		String sql = "select m.id,m.`name`,m.displayName,m.level,m.pId,m.sort,m.operation,m.url,m.type,m.desc from itsys_auth m where 1=1 "+sb;
		Map<String,String> translateParm=new HashMap();
		translateParm.put("type", "type");
		ListPageUtil listPage=new ListPageUtil(sql,parm.toArray(),pageNo, pageSize,jdbc,translateParm);
		return listPage;
//		List list = jdbc.queryForList(sql, parm.toArray());

//		log.info("@Dao queryAuthParm Start  ");
//		return list;

	}

	@Override
	public List menuQueryOnLogin(String name) {
		// TODO Auto-generated method stub
		List list=new ArrayList();
		String sql="select distinct m.id,m.`name`,m.displayName,m.pId,m.sort,m.operation,m.url from"
				+ " itsys_user u,itsys_role r,itsys_user_role_r urr,itsys_role_menu_r rmr,itsys_auth m where u.id=urr.userId"
				+ " and urr.roleId=r.id and rmr.roleId=r.id and rmr.menuId=m.id and (u.loginname='"+name+"' or u.phone='" + name + "') and m.type='1' order by m.`sort`  asc";
		list=jdbc.queryForList(sql);
		return list;
	}

	@Override
	public List getPageResource(String name) {
		// TODO Auto-generated method stub
//		String sql="SELECT DISTINCT	rs.`name` FROM `itsys_user` u,itsys_user_role_r urr,itsys_role r,itsys_resource rs,itsys_role_resource_r rrr,itsys_page_button_r pbr WHERE u.employeeID = urr.userId AND urr.roleId = r.id AND rs.id = rrr.resourceId AND rrr.roleId = r.id AND rs.type = '1' AND u.`loginname` = '"+name+"'";
		String sql="SELECT DISTINCT	au.`name` FROM	`itsys_user` u,	itsys_user_role_r urr,	itsys_role r, 	itsys_auth au,	itsys_role_menu_r rmr WHERE 	u.id = urr.userId AND urr.roleId = r.id AND au.id = rmr.menuId  AND rmr.roleId = r.id AND au.type = '2' and (u.`loginname` = '" + name + "' OR u.phone = '" + name + "')";
		List<String> list=jdbc.queryForList(sql, String.class);
		return list;
	}

	@Override
	public List getButtonResource(String name) {
//		String sql="SELECT DISTINCT	rs.`name` FROM `itsys_user` u,itsys_user_role_r urr,itsys_role r,itsys_resource rs,itsys_role_resource_r rrr,itsys_page_button_r pbr WHERE u.employeeId = urr.userId AND urr.roleId = r.id AND rs.id = rrr.resourceId AND rrr.roleId = r.id AND rs.type = '2' AND u.`loginname` = '"+name+"'";
		String sql="SELECT DISTINCT	au.`name` FROM	`itsys_user` u,	itsys_user_role_r urr,	itsys_role r, 	itsys_auth au,	itsys_role_menu_r rmr WHERE 	u.id = urr.userId AND urr.roleId = r.id AND au.id = rmr.menuId  AND rmr.roleId = r.id AND au.type = '3' and (u.`loginname` = '" + name + "' OR u.phone = '" + name + "')";
		List<String> list=jdbc.queryForList(sql, String.class);
		return list;
	}

	@Override
	public List getMenuRequests(String name) {
		// TODO Auto-generated method stub
//		String sql="select DISTINCT res.id,res.url from itsys_user u,itsys_role r,itsys_user_role_r urr,itsys_resource res,itsys_role_resource_r rrr where u.Id=urr.userId and r.id=urr.roleId and res.id=rrr.resourceId and r.id=rrr.roleId and u.loginName='"+name+"' and `type`='3' ";
		String sql="SELECT DISTINCT	res.id,	res.url FROM 	itsys_user u,	itsys_role r, itsys_user_role_r urr,	itsys_resource res,	itsys_auth_request_r ar,	itsys_auth auth WHERE 	auth.id=ar.authId and ar.requestId=res.id and 	u.Id = urr.userId AND r.id = urr.roleId and (u.`loginname` = '" + name + "' OR u.phone = '" + name + "')";
		List list=jdbc.queryForList(sql);
		return list;
	}

	@Override
	public List getMenuTree() throws ItSysException {
		String sql = "select m.id,m.`name`,m.displayName,m.level,m.pId,m.sort,m.operation,m.url,m.type,m.desc from itsys_auth m where 1=1 and m.`type`='1'";
		List list = jdbc.queryForList(sql);
		return list;
	}

	@Override
	public List getMenuPageTree() throws ItSysException {
		String sql = "select m.id,m.`name`,m.displayName,m.level,m.pId,m.sort,m.operation,m.url,m.type,m.desc from itsys_auth m where 1=1 and m.`type`!='3'";
		List list = jdbc.queryForList(sql);
		return list;
	}

	@Override
	public List queryAuthRepeat(String name) throws ItSysException {
		List parm=new ArrayList();
		StringBuffer sb=new StringBuffer("");
		if(!StringUtil.isEmpty(name)){
			parm.add("'"+name+"'");
			sb.append(" and `name` = ?");
		}
		String sql = "select m.id,m.`name`,m.displayName,m.level,m.pId,m.sort,m.operation,m.url,m.type,m.desc from itsys_auth m where 1=1 "+sb;
		log.info("@DaoImpl 根据唯一标识查询数据库中的数据.........sql={},param={}",sql,parm);
		List result=jdbc.queryForList(sql,parm.toArray());
		return result;
	}


}
