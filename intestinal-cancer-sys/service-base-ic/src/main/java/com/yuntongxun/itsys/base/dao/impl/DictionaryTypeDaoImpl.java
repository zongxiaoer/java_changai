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

import com.yuntongxun.itsys.base.common.exception.DaoException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.dao.DictionaryTypeDao;
import com.yuntongxun.itsys.base.po.DictionaryType;
/**
 * 数据字典类型 数据层实现类
 * @author Lake.zhang
 *
 */
@Repository
public class DictionaryTypeDaoImpl implements DictionaryTypeDao {

	@Autowired
	private JdbcTemplate jdbc;
	private final Logger log = LogManager.getLogger(DictionaryTypeDaoImpl.class.getName());
	@Override
	public ListPageUtil query(String id, String enname, String cnname, int pageNo, int pageSize) throws DaoException {
		// TODO Auto-generated method stub
		
		log.info("@Dao 数据字典类型 query  Start ");
		String sql="select `id`,enName,cnName from itsys_data_dictionary_type where 1=1 ";
		List param=new ArrayList();
		if(!StringUtil.isEmpty(id)){
			sql=sql+" and `id`=?";
			param.add(id);
		}
		if(!StringUtil.isEmpty(enname)){
			sql=sql+" and enname like ?";
			param.add("%"+enname+"%");
		}
		if(!StringUtil.isEmpty(cnname)){
			sql=sql+" and cnname like ?";
			param.add("%"+cnname+"%");
		}
		
		Map<String,String> translateParm=new HashMap();
		ListPageUtil listPage=new ListPageUtil(sql,param.toArray(),pageNo, pageSize,jdbc,translateParm);
		return listPage;
//		List list=jdbc.queryForList(sql, param.toArray());
//		log.info("@Dao 数据字典类型 query  End ");
//		return list;
	}

	@Override
	public void insert(DictionaryType type) throws DaoException {
		// TODO Auto-generated method stub
		log.info("@Dao 数据字典insert Start ");
		String sql="insert into itsys_data_dictionary_type(enName,cnName,dateCreated,updateTime) values(?,?,now(),now())";
		String [] parm=new String[2];
		parm[0]=type.getEnName();
		parm[1]=type.getCnName();
		jdbc.update(sql, parm);
		log.info("@Dao 数据字典insert End ");
	}

	@Override
	public void update(DictionaryType type) throws DaoException {
		// TODO Auto-generated method stub
		log.info("@Dao 数据字典update Start ");
		String sql="update itsys_data_dictionary_type set enName=?,cnName=?,updateTime=now() where `id`=?";
		String id=type.getId()+"";
		String [] parm=new String[3];
		parm[0]=type.getEnName();
		parm[1]=type.getCnName();
		parm[2]=id;
		jdbc.update(sql, parm);
		log.info("@Dao 数据字典update End ");
	}

	@Override
	public void del(String id) throws DaoException {
		// TODO Auto-generated method stub
		log.info("@Dao 数据字典del Start ");
		String sql="delete from itsys_data_dictionary_type where `id`=?";
		jdbc.update(sql, new String[]{id});
		log.info("@Dao 数据字典del End ");
	}

	@Override
	public List get(String id) throws DaoException {
		// TODO Auto-generated method stub
		log.info("@Dao 数据字典get Start ");
		String sql="select `id`,enName,cnName from itsys_data_dictionary_type where `id`=?";
		List list=jdbc.queryForList(sql, new String[]{id});
		log.info("@Dao 数据字典get End ");
		return list;
	}

}
