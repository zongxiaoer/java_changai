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
import com.yuntongxun.itsys.base.common.exception.DaoException;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.dao.DictionaryDao;
import com.yuntongxun.itsys.base.po.Dictionary;

@Repository
public class DictionaryDaoImpl implements DictionaryDao {

	final Logger log = LogManager.getLogger(DictionaryDaoImpl.class);
	@Autowired
	private JdbcTemplate jdbctemp;

	@Override
	public ListPageUtil queryDictionary(String key, int pageNo, int pageSize) throws DaoException {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"select `id`,`key`,`value`,sort,pId,isHidden,`desc` as label from itsys_data_dictionary where 1=1 ");
		List parm = new ArrayList();
		if (!StringUtils.isEmpty(key)) {
			sql.append(" and `key` =?");
			parm.add(key);
		}
		
		Map<String,String> translateParm=new HashMap();
		ListPageUtil listPage=new ListPageUtil(sql.toString(),parm.toArray(),pageNo, pageSize,jdbctemp,translateParm);
		return listPage;
//		
//		List list = jdbctemp.queryForList(sql.toString(), parm.toArray());
//		return list;
	}

	@Override
	public Dictionary getDictionary(String id) throws DaoException {
		// TODO Auto-generated method stub
		String sql = "select `id`,`key`,`value`,sort,pId,isHidden,`desc` as label  from itsys_data_dictionary where id='" + id
				+ "' ";
		Dictionary dict = null;
		List list = jdbctemp.queryForList(sql);
		if (list.size() > 0 && list != null) {
			dict = JSONUtils.toBean(JSONUtils.toJson(list.get(0)), Dictionary.class);
		}
		return dict;
	}

	@Override
	public void insertDictionary(Dictionary dictionary) throws DaoException {
		// TODO Auto-generated method stub
		Object[] obj = new Object[6];
		obj[0] = dictionary.getKey();
		obj[1] = dictionary.getValue();
		obj[2] = dictionary.getSort();
		obj[3] = dictionary.getpId();
		obj[4] = dictionary.getIshidden();
		obj[5] = dictionary.getLabel();

		String sql = "insert into itsys_data_dictionary(`key`,`value`,sort,pId,ishidden,`desc`,datecreated,updatetime) values(?,?,?,?,?,?,now(),now())";

		jdbctemp.update(sql, obj);
	}

	@Override
	public void updateDictionary(Dictionary dictionary) throws DaoException {
		// TODO Auto-generated method stub
		Object[] obj = new Object[7];
		obj[0] = dictionary.getKey();
		obj[1] = dictionary.getValue();
		obj[2] = dictionary.getSort();
		obj[3] = dictionary.getpId();
		obj[4] = dictionary.getIshidden();
		obj[5] = dictionary.getLabel();
		obj[6] = dictionary.getId();

		String sql = "update itsys_data_dictionary set `key`=?,`value`=?,sort=?,pId=?,ishidden=?,`desc`=?,updatetime=now() where id=?";
		jdbctemp.update(sql, obj);
	}

	@Override
	public void delDictionary(String id) throws DaoException {
		// TODO Auto-generated method stub
		String sql = "delete from itsys_data_dictionary where id='" + id + "'";
		jdbctemp.execute(sql);
	}

	@Override
	public List queryByKeyArray(String[] keys) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer("select `id`,`key`,`value`,sort,pId,isHidden,`desc` as label from itsys_data_dictionary where 1=1");
		if(keys!=null&&keys.length>0){
			StringBuffer sb=new StringBuffer();
			for(int i=0;i<keys.length;i++){
				if(i==keys.length-1){
					sb.append("'"+keys[i]+"'");
				}else{
					sb.append("'"+keys[i]+"',");
				}
			}
			sql.append(" and `key` in ("+sb+") ");
		}
		List queryResult=jdbctemp.queryForList(sql.toString());
		return queryResult;
	}

}
