package com.yuntongxun.itsys.base.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.ResultUtil;
import com.yuntongxun.itsys.base.dao.HStollDnaDao;
import com.yuntongxun.itsys.base.service.ColonoscopyService;
import com.yuntongxun.itsys.base.service.HStollDnaService;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
import com.yuntongxun.itsys.base.vo.Result;
import com.yuntongxun.itsys.base.vo.TodoVo;
/**
 * 查询 未录入粪便DNA装置编号 受试者列表
 * @author liugb
 * Date 2018 4 17
 */
@Service
public class HStollDnaServiceImpl implements HStollDnaService{
	
	private final Logger log = LogManager.getLogger(HStollDnaServiceImpl.class.getName());
	
	@Autowired
	private HStollDnaDao hStollDnaDao;
	@Autowired
	private ColonoscopyService colonoscopyService;
	/**
	 * 查询 未录入粪便DNA装置编号 受试者列表
	 */
	@Override
	public ListPageUtil notEntryDNACodeList(TodoVo vo,String loginName) throws ItSysException {
		log.info("@Service notEntryDNACodeList Start");
		//获取用户信息
		DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
		
		ListPageUtil listPage = hStollDnaDao.notEntryDNACodeList(doctorInfo.getCommunityDeptId(),1, vo);
		log.info("@Service notEntryDNACodeList End");
		return listPage;
	}
	/**
	 * 根据市区id查询未录入粪便DNA编号
	 */
	@Override
	public Result notEntryDNAResultList(TodoVo vo, String loginName) {
		// TODO Auto-generated method stub
		log.info("@Service notEntryDNAResultList Start");
		//获取用户信息
		DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
		int areaId = doctorInfo.getAreaDeptId();
		List list = hStollDnaDao.notStollDNAByAreaId(areaId, 1, vo);
		log.info("@Service notEntryDNAResultList End");
		return ResultUtil.success(list, null);
	}
	@Override
	public Integer saveDNA(TodoVo todoVo) {
		try{
			return hStollDnaDao.addDNA(todoVo);
		}catch (Exception e){
			log.info("saveDNA"+e.toString());
		}
		return 0;
	}

	@Override
	public TodoVo queryByDnaCode(String ndaCode) {

		return hStollDnaDao.queryByDNA(ndaCode);
	}
}
