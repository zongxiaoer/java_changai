package com.yuntongxun.itsys.gateway.module.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yuntongxun.itsys.gateway.module.dao.ResourceDao;
import com.yuntongxun.itsys.gateway.module.pojo.Resource;

@Repository
public class ResourceDaoImpl implements ResourceDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private final Logger log = LogManager.getLogger();

	@Override
	public List<Resource> getResourceByType(String type) {
		// TODO Auto-generated method stub
		String sql = "select `id`,`name`,`type`,`url`,`desc`,saveLog  from itsys_resource where `type`=? order by `id` asc";
		List<Resource> result = jdbcTemplate.query(sql.toString(),new Object[]{type},
				new BeanPropertyRowMapper<Resource>(Resource.class));
		return result;
	}

}
