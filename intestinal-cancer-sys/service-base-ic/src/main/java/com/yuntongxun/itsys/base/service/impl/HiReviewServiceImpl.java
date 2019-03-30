package com.yuntongxun.itsys.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.dao.BiologSampleDao;
import com.yuntongxun.itsys.base.dao.DepartMentDao;
import com.yuntongxun.itsys.base.dao.PersonDao;
import com.yuntongxun.itsys.base.po.Role;
import com.yuntongxun.itsys.base.vo.HospitalBiologicalSampleResultVo;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yuntongxun.itsys.base.common.exception.ServiceException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.ResultUtil;
import com.yuntongxun.itsys.base.dao.HiReviewDao;
import com.yuntongxun.itsys.base.po.HospitalReview;
import com.yuntongxun.itsys.base.service.ColonoscopyService;
import com.yuntongxun.itsys.base.service.HiReviewService;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
import com.yuntongxun.itsys.base.vo.Result;
import com.yuntongxun.itsys.base.vo.TodoVo;
import net.sf.json.JSONArray;
@Service
public class HiReviewServiceImpl implements HiReviewService{
	@Autowired
	private HiReviewDao hiReviewDao;
	@Autowired
	private ColonoscopyService colonoscopyService;

	@Autowired
	private PersonDao personDao;

	@Autowired
	private BiologSampleDao biologSampleDao;

	@Autowired
	private DepartMentDao  departMentDao;
	
	private final Logger log = LogManager.getLogger(HiReviewServiceImpl.class.getName());
	@Override
	public ListPageUtil notEntryRiskfactors(TodoVo vo, String loginName) throws ServiceException {
		log.info("@Service notEntryRiskfactors Start");
		//获取用户信息
		DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
		
		ListPageUtil listPage = hiReviewDao.notEntryRiskfactors(doctorInfo.getCommunityDeptId(),1, vo);
		log.info("@Service notEntryRiskfactors End");
		return listPage;
	}
	/**
	 * 入组的人数
	 */
	@Override
	public String getGroupBycounts(String loginName, TodoVo vo) {
		// TODO Auto-generated method stub
		  //获取用户信息
        log.info("@Service getGroupByAcounts Start  ");
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        int communityDeptId = doctorInfo.getCommunityDeptId();

		List<Role> roles = departMentDao.queryRoleByUserId(doctorInfo.getId());
		String userName="";
		if(roles!=null&&roles.size()>0){
			userName=loginName;
		}
		List list = hiReviewDao.getGroupBycounts(communityDeptId, vo,userName);
        log.info("@Service getGroupByAcounts end  ");
        JSONArray json = JSONArray.fromObject(list);  
        String result = "{\"statusCode\":\"000000\",\"statusMsg\":\"success\",\"data\":"+json+"}";
        return result;
	}
	/**
	 * 根据市区id查询入组人数
	 */
	@Override
	public String getGroupByAreaIds(String loginName, TodoVo vo) {
		// TODO Auto-generated method stub
		  //获取用户信息
        log.info("@Service getGroupByAreaIds Start  ");
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        int areaId = doctorInfo.getAreaDeptId();
        List list = hiReviewDao.getGroupByAreaId(areaId, vo);
		List list1 = hiReviewDao.getGroupSumsByAreaId(areaId, vo);
		list.addAll(list1);
        JSONArray json = JSONArray.fromObject(list);
        String result = "{\"statusCode\":\"000000\",\"statusMsg\":\"success\",\"data\":"+json+"}";
        log.info("@Service getGroupByAreaIds end  ");
        return result;
	}
	/**
	 * 根据市区id查询所对应的社区未完成危险因素调查表
	 */
	@Override
	public String notRiskFactorsByAreaId(TodoVo vo, String loginName) {
		// TODO Auto-generated method stub
		log.info("@Service notRiskFactorsByAreaId Start");
		//获取用户信息
		DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
		int areaId = doctorInfo.getAreaDeptId();
		List list = hiReviewDao.notRiskFactorsByAreaId(areaId, 1, vo);
		JSONArray json = JSONArray.fromObject(list);  
		String result = "{\"statusCode\":\"000000\",\"statusMsg\":\"success\",\"data\":"+json+"}";
		log.info("@Service notRiskFactorsByAreaId End");
		return result;
//		return listPage;
	}
	/**
	 * 查询国家受试者资格审核列表
	 */
	@Override
	public ListPageUtil queryReviewByNationIdPage(HospitalReview person, String loginName) {
		// TODO Auto-generated method stub
		DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
		int nationId = doctorInfo.getNationDeptId();
        log.info("@Service-person-queryAreaPage 国家医院id的值为  参数：nationId={}.", nationId);


        //ListPageUtil listPage = hiReviewDao.queryReviewByNationIdPage(person,nationId);
        //2018-08-30  zongtong
		ListPageUtil listPage = hiReviewDao.queryReviewByNationIdPageByG(person,nationId);
        return listPage;
	}
	/**
	 * 根据国家id查询所辖的市区
	 */
	@Override
	public Result getAreaByNationId(int nationId) {
		// TODO Auto-generated method stub
		List nations = hiReviewDao.getAreaByNationId(nationId);
		return ResultUtil.success(nations, null);
	}
	/**
	 * 根据id查询患者基本信息
	 */
	@Override
	public Result getAreaListById(String sid) {
		// TODO Auto-generated method stub
		List res = hiReviewDao.getNationListByid(sid);
		return ResultUtil.success(res, null);
	}
	/**
	 * 根据sid查询fit列表
	 */
	@Override
	public Result getFitListByNationId(String sid) {
		// TODO Auto-generated method stub
		List fits = hiReviewDao.getFitListByNationId(sid);
		return ResultUtil.success(fits, null);
//		return fits;
	}
	/**
	 * 根据sid查询DNA列表
	 */
	@Override
	public Result getStollDNABySid(String sid) {
		// TODO Auto-generated method stub
		List stollDNA = hiReviewDao.getStollDNAListByNationId(sid);
		return ResultUtil.success(stollDNA, null);
//		return stollDNA;
	}
	/**
	 * 根据sid查询结肠镜列表
	 */
	@Override
	public Result getColonoscopyBySid(String sid) {
		// TODO Auto-generated method stub
		List colonoscopy = hiReviewDao.getColonoscopyRecordBy(sid);
		return ResultUtil.success(colonoscopy, null);
	}
	/**
	 * 根据sid查询生物样本
	 */
/*	public Result getSampleType(String sid) {
		// TODO Auto-generated method stub
		List samples = hiReviewDao.getSampleResult(sid);
		return ResultUtil.success(samples, null);
	}*/
	@Override
	public String getSampleType(String sid){
		//修改生物样本相关查询---宗曈修改别人代码，后面将返回值形式变更
		//根据sid查询
		List<HospitalBiologicalSampleResultVo> sample = this.personDao.findSampleBySid(sid);

		StringBuffer addId=new StringBuffer();
		//根据血液id获取对应信息
		for(HospitalBiologicalSampleResultVo vo:sample){
			addId.append(vo.getId()+",");
		}
		List<HospitalBiologicalSampleResultVo> vos=new ArrayList<>();
		if(!StringUtils.isEmpty(addId.toString())){
			vos=biologSampleDao.queryInBloodSampleIds(addId.substring(0,addId.toString().length()-1).toString());
		}

		List<String> associatedSampleIdList = new ArrayList<>();

		for (HospitalBiologicalSampleResultVo vo : sample) {
			if (associatedSampleIdList.size() == 0) {
				associatedSampleIdList.add(vo.getAssociatedSampleId());
			} else if (!associatedSampleIdList.contains(vo.getAssociatedSampleId())) {
				associatedSampleIdList.add(vo.getAssociatedSampleId());
			}
		}

		List<HospitalBiologicalSampleResultVo> results = new ArrayList<>();
		for (String associatedSampleId : associatedSampleIdList) {
			HospitalBiologicalSampleResultVo entity = new HospitalBiologicalSampleResultVo();
			for (HospitalBiologicalSampleResultVo vo : sample) {
				if (associatedSampleId.equals(vo.getAssociatedSampleId())) {
					Integer collectStatus = vo.getCollectStatus();
					if (Constans.SAMPLE_TYPE4.equals(vo.getSampleType())) {
						entity.setMsid(vo.getId());
						entity.setSample_M(collectStatus);
						entity.setmCourierStatus(vo.getCourierStatus());
					} else if (Constans.SAMPLE_TYPE5.equals(vo.getSampleType())) {
						entity.setSample_F(collectStatus);
						entity.setFsid(vo.getId());
						entity.setfCourierStatus(vo.getCourierStatus());
					} else if (Constans.SAMPLE_TYPE6.equals(vo.getSampleType())) {
						entity.setAsid(vo.getId());
						entity.setSample_A(collectStatus);
						List<Map<String,String>> mapCourierStatus=new ArrayList<>();
						for (HospitalBiologicalSampleResultVo resultVo:vos) {//获取血液下面类型和快递状态
							if(vo.getId().equals(resultVo.getBloodSampleId())){
								Map<String,String> map=new HashMap<>();
								map.put(resultVo.getSampleType(),resultVo.getCourierStatus()==null?"":resultVo.getCourierStatus().toString());
								mapCourierStatus.add(map);
							}

						}
						entity.setMapCourierStatus(mapCourierStatus);
					}
				}
			}
			results.add(entity);
		}

		JSONArray json = JSONArray.fromObject(results);
		String result = "{\"statusCode\":\"000000\",\"statusMsg\":\"success\",\"data\":"+json+"}";
		log.info("@Service notRiskFactorsByAreaId End");
		return result;

	}
	
	/**
	 * 根据id查询生物样本详情
	 */
	@Override
	public Result getSampleDetailById(int id) {
		// TODO Auto-generated method stub
		List arrs = hiReviewDao.getSampleDetailById(id);
		return ResultUtil.success(arrs, null);
	}
	/**
	 * 国家统计按照地区统计各组人数
	 */
	@Override
	public String getGroupSumsByNationId(String loginName, TodoVo vo) {
		// TODO Auto-generated method stub
		log.info("@Service getGroupSumsByNationId Start  ");
		DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
		int nationId = doctorInfo.getNationDeptId();
		List list = hiReviewDao.getGroupSumsByNationId(nationId, vo);
		List list1 = hiReviewDao.getGroupSumsByNationId1(nationId,vo);
		list.addAll(list1);
		JSONArray json = JSONArray.fromObject(list);
		String result = "{\"statusCode\":\"000000\",\"statusMsg\":\"success\",\"data\":" + json + "}";
		log.info("@Service getGroupSumsByNationId end  ");
		return result;
	}

    /**
     * 国家统计病变结果各组人数
     * @param loginName
     * @return
     */
	@Override
    public String lesionStatisticsByNation(String loginName, TodoVo vo){
        log.info("@Service lesionStatisticsByNation Start  ");
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        int nationId = doctorInfo.getNationDeptId();
        List list = hiReviewDao.lesionStatisticsByNation(nationId, vo);
        JSONArray json = JSONArray.fromObject(list);
        String result = "{\"statusCode\":\"000000\",\"statusMsg\":\"success\",\"data\":" + json + "}";
        log.info("@Service lesionStatisticsByNation end  ");
        return result;
    }

    @Override
    public String lesionStatisticsByAreaId(String loginName, TodoVo vo) {
        //获取用户信息
        log.info("@Service lesionStatisticsByAreaId Start  ");
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        int areaId = doctorInfo.getAreaDeptId();
        List list = hiReviewDao.lesionStatisticsByArea(areaId, vo);
        JSONArray json = JSONArray.fromObject(list);
        String result = "{\"statusCode\":\"000000\",\"statusMsg\":\"success\",\"data\":"+json+"}";
        log.info("@Service lesionStatisticsByAreaId end  ");
        return result;
    }
}
