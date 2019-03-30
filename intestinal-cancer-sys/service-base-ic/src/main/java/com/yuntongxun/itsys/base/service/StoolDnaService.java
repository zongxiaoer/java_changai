/**
 * Project Name:service-base-ic
 * File Name:StoolDnaService.java
 * Package Name:com.yuntongxun.itsys.base.service
 * Date:2018年4月17日下午8:08:45
 * Copyright (c) 2018, ty All Rights Reserved.
 *
*/

package com.yuntongxun.itsys.base.service;


import java.util.List;

import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.StoolDna;
import com.yuntongxun.itsys.base.po.dto.ItsysUserDto;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
import com.yuntongxun.itsys.base.vo.TodoVo;

/**
 * ClassName:StoolDnaService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年4月17日 下午8:08:45 <br/>
 * @author   ty
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public interface StoolDnaService {

	/**
	 * 查询社区dna分页查询列表
	 * query:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author ty
	 * @param dna
	 * @param loginName
	 * @return
	 * @since JDK 1.8
	 */
	public ListPageUtil query(StoolDna dna,String loginName);

	public void addDnaCode(StoolDna dna);

	public void addDnaRecord(StoolDna dna,String loginName);
	
	public List<StoolDna> getStoolList(StoolDna dna);

	/**
	 * 查询地区dna分页查询列表
	 * queryAreaDna:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author ty
	 * @param dna
	 * @param loginName
	 * @return
	 * @since JDK 1.8
	 */
	public ListPageUtil queryAreaDna(StoolDna dna, String loginName);

	public ListPageUtil notIssueDna(TodoVo vo, String loginName);

	public void updateCommDnaInformStatus(StoolDna dna);
	public Object getDnaInfoById(int id);

	ListPageUtil queryCountryDna(StoolDna dna, String loginName);

	void updateIssuedStatus(StoolDna dna);



    boolean sendDna(String phone,int dnaCheckResult);

	List<ItsysUserDto> queryloginNameById(Integer id);



	List<StoolDna> queryById(Integer id);

	void updateDnaCode(StoolDna dna,HospitalReferenceRecordDto hospitalReferenceRecordDto,boolean isArea);

	List<ItsysUserDto> queryloginNameByDId(Integer id);

	void updateIssuedStatusList(List<StoolDna> dnas);
}

