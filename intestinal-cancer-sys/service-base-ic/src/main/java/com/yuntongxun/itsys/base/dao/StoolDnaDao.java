/**
 * Project Name:service-base-ic
 * File Name:StoolDnaDao.java
 * Package Name:com.yuntongxun.itsys.base.dao
 * Date:2018年4月17日下午8:11:30
 * Copyright (c) 2018, ty All Rights Reserved.
 *
*/

package com.yuntongxun.itsys.base.dao;

import java.util.List;

import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.DnaSynLogPO;
import com.yuntongxun.itsys.base.po.StoolDna;
import com.yuntongxun.itsys.base.po.dto.ItsysUserDto;
import com.yuntongxun.itsys.base.vo.TodoVo;

/**
 * ClassName:StoolDnaDao <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年4月17日 下午8:11:30 <br/>
 * @author   ty
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public interface StoolDnaDao {

	public ListPageUtil query(StoolDna dna);

	public void updateDnaById(StoolDna dna);

	public void updateDnaEvent(StoolDna dna);

	public List<StoolDna> getStoolList(StoolDna dna);

	public ListPageUtil queryAreaDnaPage(StoolDna dna);

	ListPageUtil queryCountryDnaPage(StoolDna dna);

	List<StoolDna> queryById(StoolDna dna);

    void updateIssuedStatus(StoolDna dna);

    void updateDNATodoEvent(Integer status, Integer id, Integer type);
	public ListPageUtil notIssueDna(Integer communityDeptId, int status, TodoVo vo);

	public void updateCommDnaInformStatus(StoolDna dna);

	public Object getDnaInfoById (int id);

	List<ItsysUserDto> queryloginNameById(Integer id);

	void updateEditDnaById(StoolDna dna,boolean isArea);

	List<ItsysUserDto> queryloginNameByDId(Integer id);

	void updateDNATodoEventList(Integer status, String substring, Integer type);

	List<StoolDna> queryByIdS(String substring);

	void updateIssuedStatusList(List<StoolDna> listOk);

	List<StoolDna> queryByDNACode(String dnaCode);

    int addDnaSynLog(DnaSynLogPO synLogPO);
}

