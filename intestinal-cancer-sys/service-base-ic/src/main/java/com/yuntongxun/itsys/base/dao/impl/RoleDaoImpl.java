package com.yuntongxun.itsys.base.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuntongxun.itsys.base.vo.HospitalBiologicalSampleResultVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.dao.RoleDao;
import com.yuntongxun.itsys.base.po.Role;

@Repository
public class RoleDaoImpl implements RoleDao {
	@Autowired
	private JdbcTemplate jdbc;
	private final Logger log = LogManager.getLogger();
	
	@Override
	public ListPageUtil queryRole(String name, int pageNo, int pageSize) throws ItSysException {
		// TODO Auto-generated method stub
		String str="";
		if(!StringUtil.isEmpty(name)){
			str=" and `name` like '%"+name+"%'";
		}
		String sql="select `id`,`name`,`desc` from itsys_role where 1=1 "+str;
		Map<String,String> translateParm=new HashMap();
		List<String> parm=new ArrayList<String>();
		ListPageUtil listPage=new ListPageUtil(sql,parm.toArray(),pageNo, pageSize,jdbc,translateParm);
		return listPage;
	}

	@Override
	public List getRoleById(String id) {
		// TODO Auto-generated method stub
		String sql="select `id`,`name`,`desc` from itsys_role where `id`='"+id+"'";
		List list =jdbc.queryForList(sql);
		return list;
	}

	@Override
	public void saveRole(Role role) {
		String sql="insert into itsys_role(`name`,`desc`,datecreated,updatetime) values(?,?,now(),now())";
		jdbc.update(sql,new String[]{role.getName(),role.getDesc()});
	}

	@Override
	public void updateRole(Role role) {
		// TODO Auto-generated method stub
		String sql="update itsys_role set `name`=?,`desc`=?,updatetime=now() where id=?";
		jdbc.update(sql,new String[]{role.getName(),role.getDesc(),role.getId()+""});
	}

	@Override
	public List getRoleMenuById(String id) {
		String sql="select m.id,m.`name`,m.displayName,m.`level`,m.pId,m.url,m.sort from itsys_role r,itsys_auth m,itsys_role_menu_r mrr where r.id=mrr.roleId and m.id=mrr.menuId  and r.id='"+id+"'";
		List list=jdbc.queryForList(sql);
		return list;
	}

	@Override
	public void saveRoleMenu(String [] parm,String id) {
		int length=parm.length;
		String sql[]=new String[length];
		for(int i=0;i<length;i++){
			if(parm[i]==null){
				continue;
			}else{
				sql[i]="insert into itsys_role_menu_r(roleid,menuId) values('"+id+"','"+parm[i]+"')";
			}
		}
		jdbc.batchUpdate(sql);
	}

	@Override
	public List getRoleResourceById(String roleid) {
		// TODO Auto-generated method stub
		String sql="select res.id,res.`name`,res.type,res.url,res.`desc`,res.dateCreated,res.updateTime from itsys_role r,itsys_resource res,itsys_role_resource_r rrr WHERE r.id=rrr.roleId and res.id=rrr.resourceId and r.id='"+roleid+"'";
		List list=jdbc.queryForList(sql);
		return list;
	}

	@Override
	public void saveRoleResource(String [] pram,String id) {
		// TODO Auto-generated method stub
		int length=pram.length;
		String sql[]=new String[length];
		for(int i=0;i<length;i++){
			if(pram[i]==null){
				continue;
			}else{
				sql[i]="insert into itsys_role_resource_r (roleid,resourceid) VALUES('"+id+"','"+pram[i]+"')";
			}
		}
		jdbc.batchUpdate(sql);

	}


	@Override
	public void deleteRole(String roleid) throws ItSysException {
		String sql="delete from itsys_role where `id`='"+roleid+"'";
		jdbc.update(sql);
	}

	@Override
	public void delRoleMenuRByMenuId(String menuid) throws ItSysException {
		String sql="delete from itsys_role_menu_r where menuid='"+menuid+"'";
		jdbc.update(sql);
	}

	@Override
	public void delRoleResourceByResourceId(String resourceid) throws ItSysException {
		String sql="delete from itsys_role_resource_r where resourceid='"+resourceid+"'";
		jdbc.update(sql);
	}

	@Override
	public void delUserRoleRByUserId(String userid) throws ItSysException {
		String sql="delete from itsys_user_role_r where userid='"+userid+"'";
		jdbc.execute(sql);
	}

	@Override
	public void delRoleMenuRByRoleId(String roleid) throws ItSysException {
		String sql="delete from itsys_role_menu_r where roleid='"+roleid+"'";
		jdbc.execute(sql);
	}

	@Override
	public void delRoleResourceRByRoleId(String roleid) throws ItSysException {
		String sql="delete from itsys_role_resource_r where roleid='"+roleid+"'";
		jdbc.execute(sql);
	}

	@Override
	public void delRoleUserRByRoleId(String roleid) throws ItSysException {
		Object[] obj=new Object[]{roleid};
		String sql="delete from itsys_user_role_r where roleid=?";
		jdbc.update(sql,obj);
	}

	@Override
	public List<DataEnti> queryRole1(int i, int i1) {
		// TODO Auto-generated method stub

		String sql="select * from Sheet_new_shai ";
		return  jdbc.query(sql, new BeanPropertyRowMapper<DataEnti>(DataEnti.class));

	}

	@Override
	public void insert(DataEnti dataEnti) {
		// TODO Auto-generated method stub
		String sql = "insert into Sheet2(a,b,c,d,e,f,g,h,i,j,k,l) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbc.update(sql, new Object[] { dataEnti.getA(),dataEnti.getB(),dataEnti.getC(),dataEnti.getD(),dataEnti.getE(),dataEnti.getF(),dataEnti.getG(),dataEnti.getH(),dataEnti.getI(),dataEnti.getJ(),dataEnti.getK(),dataEnti.getL()});
	}

	@Override
	public void insert1(DataEnti dataEnti) {
		// TODO Auto-generated method stub
		String sql = "insert into Sheet3(name,b,sid,`group`,risk_level,community_dept_id,area_dept_id,h,i,j,bl,jj) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbc.update(sql, new Object[] { dataEnti.getA(),dataEnti.getB(),dataEnti.getC(),dataEnti.getD(),dataEnti.getE(),dataEnti.getF(),dataEnti.getG(),dataEnti.getH(),dataEnti.getI(),dataEnti.getJ(),dataEnti.getK(),dataEnti.getL()});
	}

	@Override
	public void insert2(DataEnti dataEnti) {
		// TODO Auto-generated method stub
		String sql = "insert into Sheet4(name,b,sid,`group`,risk_level,community_dept_id,area_dept_id,h,i,j,bl,jj) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbc.update(sql, new Object[] { dataEnti.getA(),dataEnti.getB(),dataEnti.getC(),dataEnti.getD(),dataEnti.getE(),dataEnti.getF(),dataEnti.getG(),dataEnti.getH(),dataEnti.getI(),dataEnti.getJ(),dataEnti.getK(),dataEnti.getL()});

	}

	@Override
	public void insert3(DataEnti dataEnti) {
		// TODO Auto-generated method stub
		String sql = "insert into Sheet5(name,b,sid,`group`,risk_level,community_dept_id,area_dept_id,h,i,j,bl,jj) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbc.update(sql, new Object[] { dataEnti.getA(),dataEnti.getB(),dataEnti.getC(),dataEnti.getD(),dataEnti.getE(),dataEnti.getF(),dataEnti.getG(),dataEnti.getH(),dataEnti.getI(),dataEnti.getJ(),dataEnti.getK(),dataEnti.getL()});

	}


}
