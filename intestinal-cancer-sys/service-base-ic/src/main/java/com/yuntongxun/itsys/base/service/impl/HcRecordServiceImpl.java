package com.yuntongxun.itsys.base.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.vo.TodoVoPo;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.ResultUtil;
import com.yuntongxun.itsys.base.dao.HcRecordDao;
import com.yuntongxun.itsys.base.service.ColonoscopyService;
import com.yuntongxun.itsys.base.service.HcRecordService;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
import com.yuntongxun.itsys.base.vo.Result;
import com.yuntongxun.itsys.base.vo.TodoVo;
@Service
public class HcRecordServiceImpl implements HcRecordService{
	@Autowired
	private HcRecordDao hcRecordDao;
	@Autowired
	private ColonoscopyService colonoscopyService;
	private final Logger log = LogManager.getLogger(HcRecordServiceImpl.class.getName());
	/**
	 * 立即预约页面查询待预约受试者
	 */
	@Override
	public Result getRecordView(TodoVo vo,String loginName) throws ItSysException {
		// TODO Auto-generated method stub
		log.info("@Service HcRecordServiceImpl getRecordView Start  ");
		//获取用户信息
		DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
		int deptId = doctorInfo.getCommunityDeptId();
		ListPageUtil listPage = hcRecordDao.getHcRecordReview(deptId, vo);
		log.info("@Service HcRecordServiceImpl getRecordView End  ");
		return ResultUtil.success(listPage.getResultList(), listPage.getPageInfo());
	}
	/**
	 * 查询 未按时完成结肠镜检查 受试者列表
	 */
	@Override
	public ListPageUtil notFinishColonoscopy(TodoVo vo, String loginName) {
		log.info("@Service notFinishColonoscopy Start");
		//获取用户信息
		DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);

		ListPageUtil listPage = hcRecordDao.notFinishColonoscopy(doctorInfo.getCommunityDeptId(),1, vo);
		log.info("@Service notFinishColonoscopy End");
		return listPage;
	}
	/**
	 * 查询 未发放筛查结果告知书 受试者列表
	 */
	@Override
	public ListPageUtil notIssueNotification(TodoVo vo, String loginName) {
		log.info("@Service notReserveColonoscopy Start");
		//获取用户信息
		DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);

		ListPageUtil listPage = hcRecordDao.notIssueNotification(doctorInfo.getCommunityDeptId(),1, vo);
		log.info("@Service notReserveColonoscopy End");
		return listPage;
	}
	@Override
	public ListPageUtil notReserveColonoscopy(TodoVo vo, String loginName) {
		log.info("@Service notReserveColonoscopy Start");
		//获取用户信息
		DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);

		ListPageUtil listPage = hcRecordDao.notReserveColonoscopy(doctorInfo.getCommunityDeptId(),1, vo);
		log.info("@Service notReserveColonoscopy End");
		return listPage;
	}
	@Override
	public ListPageUtil notEntryColonoscopyResultQuery(TodoVo vo, String loginName) {
		log.info("@Service notEntryColonoscopyResultQuery Start");
		//获取用户信息
		DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);

		ListPageUtil listPage = hcRecordDao.notEntryColonoscopyResultQuery(doctorInfo.getAreaDeptId(),vo.getCommunityDeptId(),1, vo);
		log.info("@Service notEntryColonoscopyResultQuery End");
		return listPage;
	}
	@Override
	public ListPageUtil notEntryPathologyResultQuery(TodoVo vo, String loginName) {
		log.info("@Service notEntryPathologyResultQuery Start");
		//获取用户信息
		DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);

		ListPageUtil listPage = hcRecordDao.notEntryPathologyResultQuery(doctorInfo.getAreaDeptId(),vo.getCommunityDeptId(),1, vo);
		log.info("@Service notEntryPathologyResultQuery End");
		return listPage;
	}
	@Override
	public ListPageUtil notEntryNotificationResultQuery(TodoVo vo, String loginName) {
		log.info("@Service notEntryNotificationResultQuery Start");
		//获取用户信息
		DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);

		ListPageUtil listPage = hcRecordDao.notEntryNotificationResultQuery(doctorInfo.getAreaDeptId(),vo.getCommunityDeptId(),1, vo);
		log.info("@Service notEntryNotificationResultQuery End");
		return listPage;
	}

	@Override
	public ListPageUtil notEntryNoSampleResultQuery(TodoVoPo vo, String loginName) {
		log.info("@Service notEntryNoSampleResultQuery Start");
		//获取用户信息
		DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
		//筛查为6的地区，生物样本要将值更换为1 update by zongtong at 2018-08-22
		if(doctorInfo.getScreeningType()==6){
			doctorInfo.setScreeningType(1);
		}
		Integer screeningType = doctorInfo.getScreeningType();

        ListPageUtil listPage = hcRecordDao.notEntryNoSampleResultQuery(doctorInfo.getAreaDeptId(),vo.getCommunityDeptId(),1, vo);
		log.info("@Service notEntryNoSampleResultQuery End");
        List<Map<String, String>> resultList = listPage.getResultList();
        List<Map<String, String>> resultListMap = new ArrayList<>();
        for (Map<String, String> map : resultList) {
            String sampleType = map.get("sampleType");
            map.put("frozenBoxCodeHeader", Constans.FROZEN_BOX_CODE_HEADER + screeningType + sampleType);
            map.put("sampleIdHeader", Constans.FROZEN_BOX_CODE_HEADER + screeningType);
            resultListMap.add(map);
        }
        listPage.setResultList(resultListMap);
		return listPage;
	}



	//根据市区id查询未预约结肠镜检查
	@Override
	public Result notEntryAllocation(String loginName) {
		// TODO Auto-generated method stub
		log.info("@Service notEntryAllocation Start");
		//获取用户信息
		DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
		int areaId = doctorInfo.getAreaDeptId();
		List list = hcRecordDao.notEntryAllocation(areaId, 1);
		log.info("@Service notEntryAllocation End");
		return ResultUtil.success(list, null);
	}
	//根据市区id查询未完成结肠镜检查
	@Override
	public Result notFinishCheck(TodoVo vo, String loginName) {
		// TODO Auto-generated method stub
		log.info("@Service notFinishCheck Start");
		//获取用户信息
		DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
		int areaId = doctorInfo.getAreaDeptId();
		List list = hcRecordDao.notEntryFinish(areaId, 1, vo);
		log.info("@Service notFinishCheck End");
		return ResultUtil.success(list, null);
	}
	//未通知筛查发放结果告知书
	@Override
	public Result notPutOutNotices(TodoVo vo, String loginName) {
		// TODO Auto-generated method stub
		log.info("@Service notPutOutNotices Start");
		//获取用户信息
		DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
		int areaId = doctorInfo.getAreaDeptId();
		List list = hcRecordDao.notPutoutNotice(areaId, 1, vo);
		log.info("@Service notPutOutNotices End");
		return ResultUtil.success(list, null);
	}

	@Override
	public ListPageUtil notEntryNoDNAResultQuery(TodoVoPo vo, String loginName) {
		log.info("@Service notEntryNoDNAResultQuery Start");
		ListPageUtil listPage = hcRecordDao.notEntryNoDNAResultQuery(vo.getAreaDeptId(),vo.getCommunityDeptId(),1, vo);
		log.info("@Service notEntryNoDNAResultQuery End");
		return listPage;
	}

	@Override
	public ListPageUtil notEntryCancerQuery(TodoVoPo vo, String loginName) {
		log.info("@Service notEntryPathologyResultQuery Start");
		//获取用户信息
		DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);

		ListPageUtil listPage = hcRecordDao.notEntryCancerQuery(doctorInfo.getAreaDeptId(),vo.getCommunityDeptId(),1, vo);
		log.info("@Service notEntryPathologyResultQuery End");
		return listPage;
	}
}
