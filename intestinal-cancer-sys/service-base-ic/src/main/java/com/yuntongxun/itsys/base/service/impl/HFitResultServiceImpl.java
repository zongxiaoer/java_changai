package com.yuntongxun.itsys.base.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yuntongxun.itsys.base.vo.Result;
import com.yuntongxun.itsys.base.common.exception.DaoException;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.exception.ServiceException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.ResultUtil;
import com.yuntongxun.itsys.base.dao.HFitResultDao;
import com.yuntongxun.itsys.base.po.HospitalFitResult;
import com.yuntongxun.itsys.base.service.ColonoscopyService;
import com.yuntongxun.itsys.base.service.HFitResultService;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
import com.yuntongxun.itsys.base.vo.TodoVo;
/**
 * 查询 未录入FIT编号 受试者列表实现
 * @author liugb
 * Date 2018 4 17
 */
@Service
public class HFitResultServiceImpl implements HFitResultService{
	@Autowired
	private HFitResultDao hFitResultDao;
	@Autowired
	private ColonoscopyService colonoscopyService;
	
	private final Logger log = LogManager.getLogger(HFitResultServiceImpl.class.getName());
	@Override
	public ListPageUtil notEntryFitCodeList(TodoVo vo, String loginName) throws ItSysException {
		log.info("@Service HFitResultServiceImpl Start");
		//获取用户信息
		DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
		
		ListPageUtil listPage = hFitResultDao.notEntryFitCodeList(doctorInfo.getCommunityDeptId(),1, vo);
		log.info("@Service HFitResultServiceImpl End");
		return listPage;
	}
	@Override
	public ListPageUtil notEntryFitResultList(TodoVo vo, String loginName) throws ItSysException {
		// TODO Auto-generated method stub
		log.info("@Service HFitResultServiceImpl Start");
		//获取用户信息
		DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
		
		ListPageUtil listPage = hFitResultDao.notEntryFitResultList(doctorInfo.getCommunityDeptId(),1, vo);
		log.info("@Service HFitResultServiceImpl End");
		return listPage;
	}
	//根据id查询详情
		@Override
		public HospitalFitResult getFitResultByid(int id) throws ServiceException, DaoException {
			// TODO Auto-generated method stub
			log.info("@Service HFitResultServiceImpl getFitResultByid Start");
			HospitalFitResult hospitalFitResult = hFitResultDao.get(id);
			log.info("@Service HFitResultServiceImpl getFitResultByid End");
			return hospitalFitResult;
		}
		//新增FIT记录
		@Override
		public Result insertFitResult(HospitalFitResult fitResult) throws ServiceException, DaoException {
			// TODO Auto-generated method stub
			log.info("@Service 数据字典类型insert Start  ");
		    if(fitResult != null){
		    	hFitResultDao.insert(fitResult);
		    }
			log.info("@Service 数据字典类型insert End  ");
			return ResultUtil.success("success");
		}
		//根据市区id查询未录入FIT编号
		@Override
		public Result notFitCodeByAreaIdList(TodoVo vo, String loginName) {
			// TODO Auto-generated method stub
			log.info("@Service notFitCodeByAreaIdList Start");
			//获取用户信息
			DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
			int areaId = doctorInfo.getAreaDeptId();
			List list = hFitResultDao.notFitCodeByAreaIdList(areaId, 1, vo);
//			ListPageUtil listPage = hFitResultDao.notFitCodeByAreaIdList(areaId, 1, vo);
			log.info("@Service notFitCodeByAreaIdList End");
			return ResultUtil.success(list, null);
		}
		//根据市区id查询未录入FIT结果列表
		@Override
		public Result notFitResultByAreaIdList(TodoVo vo, String loginName) throws ItSysException {
			// TODO Auto-generated method stub
			log.info("@Service notFitResultByAreaIdList Start");
			//获取用户信息
			DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
			int areaId = doctorInfo.getAreaDeptId();
			List list = hFitResultDao.notFitResultByAreaIdlist(areaId, 1, vo);
//			ListPageUtil listPage = hFitResultDao.notFitResultByAreaIdlist(areaId, 1, vo);
			log.info("@Service notFitResultByAreaIdList End");
			return ResultUtil.success(list, null);
		}

}
