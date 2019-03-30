package com.yuntongxun.itsys.gateway.module.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuntongxun.itsys.gateway.module.dao.OperationDao;
import com.yuntongxun.itsys.gateway.module.pojo.Operation;
import com.yuntongxun.itsys.gateway.module.service.OperationService;

@Service
public class OperationServiceImpl implements OperationService {

	@Autowired
	private OperationDao operationdao;
	private final Logger log = LogManager.getLogger(OperationServiceImpl.class);

	@Override
	@Transactional
	public void saveOperation(Operation op){
		operationdao.saveOperation(op);
	}

	@Override
	public List getResourceByUrl(String url) {
		List list=operationdao.getResourceByUrl(url);
		return list;
	}

	

}
