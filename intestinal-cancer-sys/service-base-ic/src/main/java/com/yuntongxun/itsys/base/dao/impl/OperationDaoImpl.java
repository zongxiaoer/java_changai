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

import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.DateUtil;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.dao.OperationDao;
import com.yuntongxun.itsys.base.po.Operation;

@Repository
public class OperationDaoImpl implements OperationDao {

	@Autowired
	private JdbcTemplate jdbctemplate;
	private final Logger log = LogManager.getLogger();

	@Override
	public void saveOperation(Operation op) {
		// TODO Auto-generated method stub
		String[] parm = new String[4];

		parm[0] = op.getUser();
		parm[1] = op.getType() + "";
		parm[2] = op.getContent();
		parm[3] = op.getResult() + "";

		String sql = "insert into itsys_operation_log(`user`,`type`,content,result,dateCreated) values(?,?,?,?,now())";
		jdbctemplate.update(sql, parm);
	}

	@Override
	public ListPageUtil getOperation(String user, String startTime, String endTime, int pageNo, int pageSize) throws ItSysException {
		// TODO Auto-generated method stub
		StringBuffer str = new StringBuffer("");
		List parm = new ArrayList();
		parm.add(1);
		if (!StringUtil.isEmpty(user)) {
			str.append(" and oplog.`user`=?");
			parm.add(user);
		}

		if (!StringUtil.isEmpty(startTime) && !StringUtil.isEmpty(endTime)) {
			str.append(" and oplog.dateCreated between ? and ?");
			Long startL = Long.parseLong(startTime);
			Long endL = Long.parseLong(endTime);
			String st = DateUtil.changeTimeToString(startL);
			String et = DateUtil.changeTimeToString(endL);
			parm.add(st);
			parm.add(et);
		}

		String sql = "select oplog.`user`,res.`desc` as type ,oplog.content,oplog.result,oplog.dateCreated from itsys_operation_log oplog,itsys_resource res where res.id=oplog.type and 1=?  "
				+ str;

		Map<String,String> translateParm=new HashMap();
		ListPageUtil listPage=new ListPageUtil(sql,parm.toArray(),pageNo, pageSize,jdbctemplate,translateParm);
		return listPage;
		
	}

}
