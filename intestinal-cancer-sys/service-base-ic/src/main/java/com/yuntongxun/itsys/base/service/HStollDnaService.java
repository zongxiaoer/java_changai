package com.yuntongxun.itsys.base.service;

import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.vo.Result;
import com.yuntongxun.itsys.base.vo.TodoVo;

public interface HStollDnaService {
	//查询 未录入粪便DNA装置编号 受试者列表
	public ListPageUtil notEntryDNACodeList(TodoVo vo,String loginName) throws ItSysException;
	//根据市区id未录入dna粪便编号
    public Result notEntryDNAResultList(TodoVo vo,String loginName);  
	Integer saveDNA(TodoVo todoVo);

	TodoVo queryByDnaCode(String ndaCode);
}
