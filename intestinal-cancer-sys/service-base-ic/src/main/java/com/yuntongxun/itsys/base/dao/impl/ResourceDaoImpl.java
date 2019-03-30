package com.yuntongxun.itsys.base.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuntongxun.itsys.base.vo.CityResourceVo;
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
import com.yuntongxun.itsys.base.dao.ResourceDao;
import com.yuntongxun.itsys.base.po.AuthRequestR;
import com.yuntongxun.itsys.base.po.Resource;
@Repository
public class ResourceDaoImpl implements ResourceDao {

	@Autowired
	private JdbcTemplate jdbc;
	private final Logger log = LogManager.getLogger();
	
	@Override
	public ListPageUtil QueryResource(String name,String url,String type,int pageNo,int pageSize) throws ItSysException {
		// TODO Auto-generated method stub
		StringBuilder sb=new StringBuilder("select `id`,`name`,`type`,`url`,`desc`,saveLog from itsys_resource where 1=1 ");
		List parm=new ArrayList();
		if(!StringUtil.isEmpty(name)){
			sb.append(" and `name` like ?");
			parm.add("%"+name+"%");
		}
		if(!StringUtil.isEmpty(url)){
			sb.append(" and `url` like ?");
			parm.add("%"+url+"%");
		}
		if(!StringUtil.isEmpty(type)){
			sb.append(" and `type`=?");
			parm.add(type);
		}
		
		
		Map<String,String> translateParm=new HashMap();
		ListPageUtil listPage=new ListPageUtil(sb.toString(),parm.toArray(),pageNo, pageSize,jdbc,translateParm);
		return listPage;
		
	}

	@Override
	public List getResourceById(String id) {
		// TODO Auto-generated method stub
		String sql="select `id`,`name`,type,url,`desc`,saveLog from itsys_resource  where 1=1 and `id`='"+id+"'";
		List list=jdbc.queryForList(sql);
		return list;
	}

	@Override
	public Resource getResourceObjectById(String id) throws ItSysException {
		String sql="select `id`,`name`,`type`,`url`,`desc`,`saveLog` from itsys_resource where 1=1 and `id`=?";
		Map<String, Object> map = jdbc.queryForMap(sql, new Object[]{id});
		return Resource.toObject(map);
	}

	@Override
	public void addResource(Object [] parm) {
		// TODO Auto-generated method stub
		String sql="insert INTO itsys_resource(`name`,`type`,`url`,`desc`,saveLog,dateCreated,updatetime) VALUES(?,?,?,?,?, NOW(),NOW())";
		jdbc.update(sql,parm);
	}

	@Override
	public void updateResource(Object [] parm) {
		// TODO Auto-generated method stub
			String sql="update itsys_resource set `name`=?,type=?,url=?,`desc`=?,saveLog=?,updatetime=now() where id=?";
			jdbc.update(sql,parm);
		
	}	

	@Override
	public void addPageButtonR(String pageid,String buttonid) {
		// TODO Auto-generated method stub
		log.info("@Dao addPageButtonR Start ");
		String sql="insert into itsys_page_button_r(pageid,buttonid) values('"+pageid+"','"+buttonid+"')";
		jdbc.execute(sql);
		log.info("@Dao addPageButtonR End ");
	}

	@Override
	public String getResourceIdByName(String name) {
		// TODO Auto-generated method stub
		String sql="select id from itsys_resource where name='"+name+"'";
		List<String> list=jdbc.queryForList(sql,String.class);
		return list.size()==0?"":list.get(0)==null?"":list.get(0);
	}

	@Override
	public String getPageButtonOnId(String buttonid) {
		String sql="select id from itsys_page_button_r where buttonid='"+buttonid+"'";
		List<String> list=jdbc.queryForList(sql,String.class);
		return list.size()==0?"":list.get(0)==null?"":list.get(0);
	}

	@Override
	public void updatePageButtonR(String pageid, String buttonid, String id) {
		String sql="update itsys_page_button_r set pageid='"+pageid+"',buttonid='"+buttonid+"' where id='"+id+"'";
		jdbc.execute(sql);
	}

	@Override
	public List getAllRequest() throws ItSysException {
		// TODO Auto-generated method stub
		String sql="select  `id`,`name`,`type`,`url`,`desc` from itsys_resource where `type`=3";
		List list=jdbc.queryForList(sql);
		return list;
	}

	@Override
	public List getAllPages() throws ItSysException {
		// TODO Auto-generated method stub
		String sql="select  `id`,`name`,`type`,`url`,`desc` from itsys_resource where `type`=1";
		List list=jdbc.queryForList(sql);
		return list;
	}

	@Override
	public List getAllButtonByPage(String page) throws ItSysException {
		// TODO Auto-generated method stub
		log.info("@Dao getAllButtonByPage Start ");
		String sql="select  `id`,`name`,`type`,`url`,`desc` from itsys_resource where `type`=2 and id in (select buttonid from itsys_page_button_r where pageid='"+page+"')";
		List list=jdbc.queryForList(sql);
		log.info("@Dao getAllButtonByPage End ");
		return list;
	}

	@Override
	public void deleteResource(String resourceid) throws ItSysException {
		// TODO Auto-generated method stub
		String sql="delete from itsys_resource where id='"+resourceid+"'";
		jdbc.execute(sql);
	}

	@Override
	public List getResourceByType(String type) throws ItSysException {
		// TODO Auto-generated method stub
		String sql="select `id`,`name`,`type`,`url`,`desc`,saveLog  from itsys_resource where `type`=?";
		List list=jdbc.queryForList(sql,new Object[]{type});
		return list;
	}

	@Override
	public void bindAuthRequestR(String requestId, String[] authIds) throws ItSysException {
		delAuthRequestR(requestId);//先删除关系
		String sql[]=new String[authIds.length];
		for(int i=0;i<authIds.length;i++){
				sql[i]="insert into itsys_auth_request_r(authId,requestId) values('"+authIds[i]+"','"+requestId+"')";
		}
		if(sql.length>0){
			jdbc.batchUpdate(sql);
		}
	}

	@Override
	public void delAuthRequestR(String requestId) throws ItSysException {
		String sql="delete from itsys_auth_request_r where requestId='"+requestId+"'";
		jdbc.execute(sql);
	}

	@Override
	public List<AuthRequestR> getAuthRequestByRequestId(String requestId) throws ItSysException {
		String sql = "select `id`,`authId`,`requestId` from itsys_auth_request_r where `requestId`=?";
		List<Map<String, Object>> list = jdbc.queryForList(sql, new  Object[]{requestId});
		return AuthRequestR.toObject(list);
	}

	@Override
	public List<CityResourceVo> queryCity() {
		String sql = "select *,name as label,name as value  from city_resource";//+id+" and sid='"+sid+"'"
		return jdbc.query(sql, new BeanPropertyRowMapper<CityResourceVo>(CityResourceVo.class));
	}


}
