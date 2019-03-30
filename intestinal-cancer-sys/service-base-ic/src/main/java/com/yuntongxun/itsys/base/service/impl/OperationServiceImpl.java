package com.yuntongxun.itsys.base.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.dao.OperationDao;
import com.yuntongxun.itsys.base.po.Operation;
import com.yuntongxun.itsys.base.service.OperationService;

@Service
public class OperationServiceImpl implements OperationService {

	@Autowired
	private OperationDao operationdao;
	private final Logger log = LogManager.getLogger(OperationServiceImpl.class);

	@Override
	@Transactional
	public void saveOperation(Operation op) throws ItSysException {
		// TODO Auto-generated method stub
		if (op == null) {
			throw new ItSysException(
					Constans.ERROR_OPTRATION_NULL_CODE, Constans.ERROR_OPTRATION_NULL_MSG);
		}
		operationdao.saveOperation(op);
	}

	@Override
	public ListPageUtil getOperation(String body) throws ItSysException {
		// TODO Auto-generated method stub
		JsonObject json = new JsonParser().parse(body).getAsJsonObject();
		String user = json.get("user") == null ? "" : json.get("user").getAsString();
		String startTime = json.get("startTime") == null ? "" : json.get("startTime").getAsString();
		String endTime = json.get("endTime") == null ? "" : json.get("endTime").getAsString();
		int pageNo = json.get("pageNo") == null ? 0 : json.get("pageNo").getAsInt();
		int pageSize = json.get("pageSize") == null ? -1 : json.get("pageSize").getAsInt();
		return operationdao.getOperation(user, startTime, endTime, pageNo, pageSize);
	}

}
