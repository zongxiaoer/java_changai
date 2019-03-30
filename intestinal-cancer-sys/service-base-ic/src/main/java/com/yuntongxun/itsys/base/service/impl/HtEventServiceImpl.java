package com.yuntongxun.itsys.base.service.impl;

import java.util.List;

import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.ResultUtil;
import com.yuntongxun.itsys.base.dao.DepartMentDao;
import com.yuntongxun.itsys.base.po.Role;
import com.yuntongxun.itsys.base.vo.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.dao.HtEventDao;
import com.yuntongxun.itsys.base.service.ColonoscopyService;
import com.yuntongxun.itsys.base.service.HtEventService;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
import com.yuntongxun.itsys.base.vo.TodoVo;

import net.sf.json.JSONArray;
/**
 * 查询社区医院各种待办事项个数
 * @author liugb
 * Date 2018 4 16
 */
@Service
public class HtEventServiceImpl implements HtEventService{
	@Autowired
	private HtEventDao htEventDao;

	@Autowired
	private DepartMentDao  departMentDao;

	private final Logger log = LogManager.getLogger(HtEventServiceImpl.class.getName());
	@Autowired
	private ColonoscopyService colonoscopyService;
    /*
     * 查询社区医院各种待办事项个数
     */
	@Override
	public TodoVo getHtEventSum(int deptId, int status,DoctorInfo doctorInfo) throws ItSysException {
		// TODO Auto-generated method stub
		log.info("@Service 查询各种待办事项个数query Start  ");
		TodoVo vo = null;
		boolean idok=false;
		List<Role> roles = departMentDao.queryRoleByUserId(doctorInfo.getId());
		if(roles!=null&&roles.size()>0){
			idok=true;
		}

		if(doctorInfo.getHospitalType() == 1&&!idok){
			vo = htEventDao.queryCommunityEvent(deptId, status);
		}else if(doctorInfo.getHospitalType() == 2){
			vo = htEventDao.queryAreaEvent(deptId, status);
		}else if(doctorInfo.getHospitalType() == 3){
			vo = htEventDao.queryNationHtEvent(deptId, status);
		}else if(doctorInfo.getHospitalType() == 1&&idok){
			vo = htEventDao.queryCommunityAndUserEvent(deptId, status,doctorInfo.getLoginName());
		}
		
		
		log.info("@Service 查询各种待办事项个数query End  ");
		return vo;
	}
	/**
	 * 国家代办，统计未录入肠镜、未录入病理结果、未录入筛查告知书地区个数
	 */
	@Override
	public String getSumByNationId(String loginName) {
		// TODO Auto-generated method stub
		log.info("@Service getSumByNationId Start");
		//获取用户信息
		DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
		int nationId = doctorInfo.getNationDeptId();
		List list = htEventDao.queryNationCountByNation(nationId);
		JSONArray json = JSONArray.fromObject(list);  
		String result = "{\"statusCode\":\"000000\",\"statusMsg\":\"success\",\"data\":"+json+"}";
		log.info("@Service getSumByNationId End");
		return result;
	}
	/**
	 * 国家代办，统计社区按照地区个人数
	 */
	@Override
	public String getAreaSumsByNationId(String loginName) {
		// TODO Auto-generated method stub
		log.info("@Service getAreaSumsByNationId Start");
		// 获取用户信息
		DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
		int nationId = doctorInfo.getNationDeptId();
		List list = htEventDao.getCommunitCountsByNation(nationId);
		JSONArray json = JSONArray.fromObject(list);
		String result = "{\"statusCode\":\"000000\",\"statusMsg\":\"success\",\"data\":" + json + "}";
		log.info("@Service getSumByNationId End");
		return result;
	}
	/**
	 * 获取列表方法实现
	 * @param vo
	 * @return
	 */
	@Override
	public Result getlist(TodoVo vo) {
		ListPageUtil listPage = htEventDao.getlistDetailById(vo);
		return ResultUtil.success(listPage.getResultList(), listPage.getPageInfo());
	}
}
