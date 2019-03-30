package com.yuntongxun.itsys.gateway.module.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yuntongxun.itsys.gateway.module.dao.OperationDao;
import com.yuntongxun.itsys.gateway.module.pojo.Operation;

@Repository
public class OperationDaoImpl implements OperationDao {

	@Autowired
	private JdbcTemplate jdbctemplate;
	private final Logger log = LogManager.getLogger();

	@Override
	public void saveOperation(Operation op) {
		// TODO Auto-generated method stub
		log.info("@Dao saveOperation Start");
		String[] parm = new String[4];

		parm[0] = op.getUser();
		parm[1] = op.getType() + "";
		parm[2] = op.getContent();
		parm[3] = op.getResult() + "";

		String sql = "insert into itsys_operation_log(`user`,`type`,content,result,dateCreated) values(?,?,?,?,now())";
		jdbctemplate.update(sql, parm);
		log.info("@Dao saveOperation End");
	}

	@Override
	public List getResourceByUrl(String url) {
		log.info("@Dao getResourceByUrl Start");
		String sql="select `id`,`name`,`url`,`type`,`desc` from itsys_resource where 1=1 and `url`=?";
		List list=jdbctemplate.queryForList(sql,new Object[]{url});
		log.info("@Dao getResourceByUrl End ");
		return list;
	}

}
