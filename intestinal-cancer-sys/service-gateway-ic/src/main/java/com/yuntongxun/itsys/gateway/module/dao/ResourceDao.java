package com.yuntongxun.itsys.gateway.module.dao;

import java.util.List;

import com.yuntongxun.itsys.gateway.module.pojo.Resource;

public interface ResourceDao {
	public List<Resource> getResourceByType(String type);
}
