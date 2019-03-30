package com.yuntongxun.itsys.base.dao;

import java.util.List;

import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.vo.TodoVo;

public interface HStollDnaDao {	
	//查询 未录入粪便DNA装置编号 受试者列表
	public ListPageUtil notEntryDNACodeList(int dept_id,int code_entry_status,TodoVo vo) throws ItSysException;
    //查询 未录入粪便DNA装置编号 按照地区查询
	public ListPageUtil notEntryDNACodeList1(int areaId,int code_entry_status,TodoVo vo) throws ItSysException;
	//根据市区id查询未录入粪便DNA编号
	public List notStollDNAByAreaId(int areaId,int code_entry_status,TodoVo vo);
	
	Integer addDNA(TodoVo todoVo);

	TodoVo queryByDNA(String dNACode);

}
