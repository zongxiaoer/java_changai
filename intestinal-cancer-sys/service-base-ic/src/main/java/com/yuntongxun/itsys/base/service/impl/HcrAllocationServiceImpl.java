package com.yuntongxun.itsys.base.service.impl;

import java.util.*;

import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.*;
import com.yuntongxun.itsys.base.dao.*;
import com.yuntongxun.itsys.base.po.*;
import com.yuntongxun.itsys.base.po.dto.allocation.*;
import com.yuntongxun.itsys.base.po.dto.message.HospitalMessageCenterDto;
import com.yuntongxun.itsys.base.vo.*;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuntongxun.itsys.base.common.exception.ServiceException;
import com.yuntongxun.itsys.base.service.ColonoscopyService;
import com.yuntongxun.itsys.base.service.HcrAllocationService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 根据社区医院ID获取放号数据列表实现类
 *
 * @author liugb
 * Date 2018 4 16
 */
@Service
public class HcrAllocationServiceImpl implements HcrAllocationService {

    @Autowired
    private HcrAllocationDao hcrAllocationDao;
    @Autowired
    private ReserveDetailDao reserveDetailDao;
    @Autowired
    private ColonoscopyService colonoscopyService;

    @Autowired
    private HosAllocationRuleInfoDao hosAllocationRuleInfoDao;

    @Autowired
    private HosAllocationRuleTimeDao hosAllocationRuleTimeInfo;

    @Autowired
    private HosAllocationRuleDeptDao hosAllocationRuleDeptDao;

    @Autowired
    private ResultAllocationDao resultAllocationDao;

    @Autowired
    private DepartMentDao departMentDao;

    @Autowired
    private ColonoscopyDao colonoscopyDao;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private HtEventDao eventDao;

    @Autowired
    private HospitalMessageCenterDao hospitalMessageCenterDao;

    public static final int EVENT_TYPE_8 = 8;


    //事件状态，1：待办，2：已办
    public static final int EVENT_STATUS_NOT_DO = 1;
    public static final int EVENT_STATUS_DONE = 2;

    private final Logger log = LogManager.getLogger(HcrAllocationServiceImpl.class.getName());


    @Autowired
    private SendSms sendSms;

    @Autowired
    private UserDao userDao;

    /**
     * 查询预约分配实现类
     */
    @Override
    public Result query(int communityDeptId, int pageNo, int pageSize) throws ServiceException {
        // TODO Auto-generated method stub
        log.info("@Service 查询预约分配query Start  ");
        ListPageUtil listPage = hcrAllocationDao.queryAllocation(communityDeptId, pageNo, pageSize);

        //遍历放号列表，去查询当前放号批次已预约多少人， 计算剩余预约多少人
        if (listPage.getResultList() != null && listPage.getResultList().size() > 0) {
            List<Object> resultList = new ArrayList<Object>();
            Map tempMap = null;
            for (Object obj : listPage.getResultList()) {
                tempMap = (Map) obj;
                Integer total = (Integer) tempMap.get("reserveable");
                Integer allocationId = (Integer) tempMap.get("id");
                //查询该预约分配记录已预约人数
                Integer reservedCount = reserveDetailDao.getReservedCount(allocationId);
                if (total - reservedCount > 0) {
                    tempMap.put("reserveable", total - reservedCount);
                    resultList.add(tempMap);
                }
            }
            listPage.setResultList(resultList);
        }
        log.info("@Service 查询预约分配query end  ");
        return ResultUtil.success(listPage.getResultList(), listPage.getPageInfo());
    }

    /**
     * 新增预约放号方法
     */
    @Override
    public Result insert(AllocationTodo allocationTodo, String loginName) throws ServiceException {
        // TODO Auto-generated method stub
        log.info("@Service 预约放号insert Start");
        //获取用户信息
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        int areaId = doctorInfo.getAreaDeptId();
        if (allocationTodo != null) {
            List<AllocationTodo> rs = allocationTodo.getCommunityDept();
            for (AllocationTodo allstodo : rs) {
                allocationTodo.setCommunityDeptId(allstodo.getCommunityDeptId());
                allocationTodo.setAmount(allstodo.getAmount());
                hcrAllocationDao.insert(allocationTodo, areaId);
            }
        }
        log.info("@Service 预约放号insert End");
        return ResultUtil.success("success");
    }

    /**
     * 根据市区id查询方法
     */
    @Override
    public String getByareaId(String loginName) throws ServiceException {
        //根据地区Id获取所有社区名称
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        int areaId = doctorInfo.getAreaDeptId();
        List<ItsysDepartmentVo> byAreaId = hcrAllocationDao.getByAreaId(areaId);
        log.info("@Service 数据字典类型get end  " + byAreaId);
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, byAreaId, null));
    }

    /**
     * 根据条件筛查签到列表
     */
    @Override
    public Result queryallnotlist(String loginName, TodoVo vo) throws ServiceException {
        // TODO Auto-generated method stub
        //获取用户信息
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        int areaId = doctorInfo.getAreaDeptId();
        ListPageUtil listPage = hcrAllocationDao.getlistByAreaId(areaId, vo);
        return ResultUtil.success(listPage.getResultList(), listPage.getPageInfo());
    }

    /**
     * 根据登陆用户名查询所对应的社区
     */
    @Override
    public Result getCommdepts(String loginName) {
        // TODO Auto-generated method stub
        //获取用户信息
        log.info("@Service getCommdepts Start  ");
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        int areaId = doctorInfo.getAreaDeptId();
        List list = hcrAllocationDao.getcommdeptsByAreaId(areaId);
        log.info("@Service getCommdepts end  ");
        return ResultUtil.success(list, null);
    }

    /**
     * 地区放号一览表
     */
    @Override
    public Result queryPutCodeByAreaId(String loginName, TodoVo vo) {
        // TODO Auto-generated method stub
        //获取用户信息
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        int areaId = doctorInfo.getAreaDeptId();
        ListPageUtil listPage = hcrAllocationDao.queryPutCodeByAreaId(areaId, vo);
        return ResultUtil.success(listPage.getResultList(), listPage.getPageInfo());
    }

    /**
     * 地区一览表查询详情
     */
    @Override
    public Result queryPutCodeDetailListByCommitId(ColonoscopyQueryResult queryparam) {
        // TODO Auto-generated method stub
        ListPageUtil listPage = hcrAllocationDao.queryPutCodeDetailListByCommitId(queryparam);
        return ResultUtil.success(listPage.getResultList(), listPage.getPageInfo());
    }

    /**
     * 地区导出列表
     */
    @Override
    public List<AreaListForExcelVo> queryForAreaExcel(TodoVo vo, String loginName) {
        // TODO Auto-generated method stub
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        int areaId = doctorInfo.getAreaDeptId();
        List<AreaListForExcelVo> list = hcrAllocationDao.queryForAreaExcel(areaId, vo);
        return list;
    }

    @Override
    public Result queryInArea(int communityDeptId, int pageNo, int pageSize) {
        log.info("@Service 地区查询预约分配query Start  ");
        ListPageUtil listPage = hcrAllocationDao.queryAllocationInArea(communityDeptId, pageNo, pageSize);

        //遍历放号列表，去查询当前放号批次已预约多少人， 计算剩余预约多少人
        if (listPage.getResultList() != null && listPage.getResultList().size() > 0) {
            List<Object> resultList = new ArrayList<Object>();
            Map tempMap = null;
            for (Object obj : listPage.getResultList()) {
                tempMap = (Map) obj;
                Integer total = (Integer) tempMap.get("reserveable");
                Integer allocationId = (Integer) tempMap.get("id");
                //查询该预约分配记录已预约人数
                Integer reservedCount = reserveDetailDao.getReservedCount(allocationId);
                if (total - reservedCount > 0) {
                    tempMap.put("reserveable", total - reservedCount);
                    resultList.add(tempMap);
                }
            }
            listPage.setResultList(resultList);
        }
        log.info("@Service 查询预约分配query end  ");
        return ResultUtil.success(listPage.getResultList(), listPage.getPageInfo());
    }

    @Override
    @Transactional
    public Result newInsert(HosAllocationRuleInfoDto allocationDto, String loginName) {

        //当天看是否在个14天外的时间
        //在 录入  不在 不录入

        /**
         * 获取用户信息
         * 先录入规则 (/0-13内 全部放完  begin_status 结束,//13以外放0-13天   begin_status 1开始)
         *根据规则区分
         * 1.整个地区走规则1  issue_type 1-全部社区
         *      根据时间录入时间表
         *      将部门融合成一个字符串
         *      录入一条部门表
         *          根据时间、和规则放号
         *          1.每天 录入放号数据 1./0-13内 全部放完 /13以外放0-13天  2.一周的 除了第一个以外（看号是否满足一周里面的）
         * 2.每个社区走规则2  issue_type 2-某个社区
         *      根据时间录入时间表
         *      根据部分录入时间表
         *          根据部分、和规则放号
         *          1.每天 录入放号数据 1./0-13内 全部放完 /13以外放0-13天  2.一周的 除了第一个以外（看号是否满足一周里面的）
         */
        //获取用户信息
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        int areaId = doctorInfo.getAreaDeptId();


        //初始化begin_status 未开始
        Integer begin_status = Constans.BEGIN_STATUS2;

        //算放号日期时间
        long beginIntervalDays = DateUtil.getIntervalDays(allocationDto.getRuleEnd(), allocationDto.getRuleBegin());

        if (0 <= beginIntervalDays && beginIntervalDays <= 13) { //0-13内 全部放完  begin_status 结束
            begin_status = Constans.BEGIN_STATUS3;
        } else if (beginIntervalDays > 13) {      //13天以外放14天   begin_status 1开始
            begin_status = Constans.BEGIN_STATUS1;
        }


        //先录入规则
        List<Integer> weeks = allocationDto.getWeeks();
        allocationDto.setWeekInfo(JSONUtils.toJson(weeks));
        allocationDto.setAreaDeptId(areaId);
        allocationDto.setBeginStatus(begin_status);
        allocationDto.setaUser(doctorInfo.getId());
        allocationDto.setaTime(new Date());
        allocationDto.setcTime(new Date());
        allocationDto.setUseStatus(Constans.USESTATUS1);
        int ruleid = hosAllocationRuleInfoDao.save(allocationDto);


        //根据规则区分
        if (Constans.ISSUETYPE1 == allocationDto.getIssueType()) { //整个地区走规则1 community_dept_id_info 填写 、issue_type 1-全部社区

            //根据时间录入时间表
            List<HosAllocationRuleTimeInfoDto> hosAllocationRuleTimeInfoDtos = allocationDto.getHosAllocationRuleTimeInfoDtos();
            for (HosAllocationRuleTimeInfoDto hosAllocationRuleTimeInfoDto : hosAllocationRuleTimeInfoDtos) {
                hosAllocationRuleTimeInfoDto.setRuleId(ruleid);
                try {
                    hosAllocationRuleTimeInfo.save(hosAllocationRuleTimeInfoDto);
                } catch (Exception e) {
                    log.info("newInsert  根据时间录入时间表 " + e.toString());
                    throw new ItSysException(GlobalErrorCode.NEWINSERT_HOSALLOCATION_RULE_ISERROR_CODE, GlobalErrorCode.NEWINSERT_HOSALLOCATION_RULE_ISERROR_MSG);
                }
            }
            //将部门融合成一个字符串
            String community_dept_id_info = "";
            StringBuffer id = new StringBuffer();
            List<HosAllocationRuleDeptInfoDto> hosAllocationRuleDeptInfoDtos = allocationDto.getHosAllocationRuleDeptInfoDtos();
            HashSet<Integer> list = new HashSet<>();
            for (HosAllocationRuleDeptInfoDto ids : hosAllocationRuleDeptInfoDtos) {
                list.add(ids.getCommunityDeptId());
            }
            for (Integer ids : list) {
                id.append(ids + ",");
            }
            community_dept_id_info = id.toString().substring(0, id.toString().length() - 1);
            //录入一条部门表
            HosAllocationRuleDeptInfoDto hosAllocationRuleDeptInfoDto = new HosAllocationRuleDeptInfoDto();
            hosAllocationRuleDeptInfoDto.setIssueType(allocationDto.getIssueType());
            hosAllocationRuleDeptInfoDto.setRuleId(ruleid);
            hosAllocationRuleDeptInfoDto.setCommunityDeptIdInfo(community_dept_id_info);
            try {
                hosAllocationRuleDeptDao.save(hosAllocationRuleDeptInfoDto);
            } catch (Exception e) {
                log.info("newInsert  根据时间录入部门表 " + e.toString());
                throw new ItSysException(GlobalErrorCode.NEWINSERT_HOSALLOCATION_RULE_ISERROR_CODE, GlobalErrorCode.NEWINSERT_HOSALLOCATION_RULE_ISERROR_MSG);

            }

            //录入放号
            List<AllocationDto> allocationDtos = new ArrayList<>();
            for (HosAllocationRuleTimeInfoDto hosAllocationRuleTimeInfoDto : hosAllocationRuleTimeInfoDtos) {
                AllocationDto allocationDtott = new AllocationDto();
                allocationDtott.setAmount(hosAllocationRuleTimeInfoDto.getNum());
                allocationDtott.setAreaDeptId(String.valueOf(areaId));
                allocationDtott.setStartTime(hosAllocationRuleTimeInfoDto.getBeginTime());
                allocationDtott.setEndTime(hosAllocationRuleTimeInfoDto.getEndTime());
                allocationDtott.setExaminationPlace(allocationDto.getExaminationPlace());
                allocationDtott.setSignature(allocationDto.getSignature());
                allocationDtott.setDateCreated(new Date());
                allocationDtott.setUpdateTime(new Date());
                allocationDtott.setRuleId(ruleid);
                allocationDtott.setUseStatus(Constans.USESTATUS1);
                allocationDtott.setIssueType(allocationDto.getIssueType());
                allocationDtott.setCommunityDeptIdInfo(community_dept_id_info);
                allocationDtos.add(allocationDtott);
            }

            List<AllocationDto> allocationDtos1 = getallocationDtos(allocationDto.getRuleEnd(), allocationDto.getRuleBegin(), allocationDto.getRuleType(), allocationDtos, Constans.DAYWAI13_1, JSONUtils.toJson(weeks));


            for (AllocationDto allocationDto1 : allocationDtos1) {
                try {
                    hcrAllocationDao.save(allocationDto1);
                } catch (Exception e) {
                    log.info("newInsert  根据时间录入放号表 " + e.toString());
                    throw new ItSysException(GlobalErrorCode.NEWINSERT_HOSALLOCATION_RULE_ISERROR_CODE, GlobalErrorCode.NEWINSERT_HOSALLOCATION_RULE_ISERROR_MSG);
                }

            }

            //录入附属表
            List<AllocationDto> allocationDtos2 = getallocationDtoAllDay(allocationDto.getRuleEnd(), allocationDto.getRuleBegin(), allocationDto.getRuleType(), allocationDtos, Constans.DAYWAI13_1, JSONUtils.toJson(weeks));
            for (AllocationDto allocationDto1 : allocationDtos2) {
                try {
                    hcrAllocationDao.saveByFuBiao(allocationDto1);
                } catch (Exception e) {
                    log.info("newInsert  根据时间录入放号表 " + e.toString());
                    throw new ItSysException(GlobalErrorCode.NEWINSERT_HOSALLOCATION_RULE_ISERROR_CODE, GlobalErrorCode.NEWINSERT_HOSALLOCATION_RULE_ISERROR_MSG);
                }

            }

        } else if (Constans.ISSUETYPE2 == allocationDto.getIssueType()) {

            //根据时间录入时间表
            List<HosAllocationRuleTimeInfoDto> hosAllocationRuleTimeInfoDtos = allocationDto.getHosAllocationRuleTimeInfoDtos();
            for (HosAllocationRuleTimeInfoDto hosAllocationRuleTimeInfoDto : hosAllocationRuleTimeInfoDtos) {
                hosAllocationRuleTimeInfoDto.setRuleId(ruleid);
                try {
                    hosAllocationRuleTimeInfo.save(hosAllocationRuleTimeInfoDto);
                } catch (Exception e) {
                    log.info("newInsert  根据时间录入时间表 " + e.toString());
                    throw new ItSysException(GlobalErrorCode.NEWINSERT_HOSALLOCATION_RULE_ISERROR_CODE, GlobalErrorCode.NEWINSERT_HOSALLOCATION_RULE_ISERROR_MSG);
                }
            }

            //录入部门表
            List<HosAllocationRuleDeptInfoDto> hosAllocationRuleDeptInfoDtos = allocationDto.getHosAllocationRuleDeptInfoDtos();
            for (HosAllocationRuleDeptInfoDto hosAllocationRuleDeptInfoDto : hosAllocationRuleDeptInfoDtos) {
                hosAllocationRuleDeptInfoDto.setIssueType(allocationDto.getIssueType());
                hosAllocationRuleDeptInfoDto.setRuleId(ruleid);
                try {
                    hosAllocationRuleDeptDao.save(hosAllocationRuleDeptInfoDto);
                } catch (Exception e) {
                    log.info("newInsert  根据时间录入部门表 " + e.toString());
                    throw new ItSysException(GlobalErrorCode.NEWINSERT_HOSALLOCATION_RULE_ISERROR_CODE, GlobalErrorCode.NEWINSERT_HOSALLOCATION_RULE_ISERROR_MSG);

                }
            }

            //处理放号信息
            List<AllocationDto> allocationDtos = new ArrayList<>();
            for (HosAllocationRuleDeptInfoDto hosAllocationRuleDeptInfoDto : hosAllocationRuleDeptInfoDtos) {
                AllocationDto allocationDtott = new AllocationDto();
                allocationDtott.setAmount(hosAllocationRuleDeptInfoDto.getNum());
                allocationDtott.setAreaDeptId(String.valueOf(areaId));
                allocationDtott.setStartTime(hosAllocationRuleDeptInfoDto.getBeginTime());
                allocationDtott.setEndTime(hosAllocationRuleDeptInfoDto.getEndTime());
                allocationDtott.setExaminationPlace(allocationDto.getExaminationPlace());
                allocationDtott.setSignature(allocationDto.getSignature());
                allocationDtott.setDateCreated(new Date());
                allocationDtott.setUpdateTime(new Date());
                allocationDtott.setRuleId(ruleid);
                allocationDtott.setUseStatus(Constans.USESTATUS1);
                allocationDtott.setIssueType(allocationDto.getIssueType());
                allocationDtott.setCommunityDeptId(String.valueOf(hosAllocationRuleDeptInfoDto.getCommunityDeptId()));
                allocationDtos.add(allocationDtott);
            }
            List<AllocationDto> allocationDtos1 = getallocationDtos(allocationDto.getRuleEnd(), allocationDto.getRuleBegin(), allocationDto.getRuleType(), allocationDtos, Constans.DAYWAI13_1, JSONUtils.toJson(weeks));
            for (AllocationDto allocationDto1 : allocationDtos1) {
                try {
                    hcrAllocationDao.save(allocationDto1);
                } catch (Exception e) {
                    log.info("newInsert  根据时间录入放号表 " + e.toString());
                    throw new ItSysException(GlobalErrorCode.NEWINSERT_HOSALLOCATION_RULE_ISERROR_CODE, GlobalErrorCode.NEWINSERT_HOSALLOCATION_RULE_ISERROR_MSG);
                }
            }

            //录入附属表
            List<AllocationDto> allocationDtos2 = getallocationDtoAllDay(allocationDto.getRuleEnd(), allocationDto.getRuleBegin(), allocationDto.getRuleType(), allocationDtos, Constans.DAYWAI13_1, JSONUtils.toJson(weeks));
            for (AllocationDto allocationDto1 : allocationDtos2) {
                try {
                    hcrAllocationDao.saveByFuBiao(allocationDto1);
                } catch (Exception e) {
                    log.info("newInsert  根据时间录入放号表 " + e.toString());
                    throw new ItSysException(GlobalErrorCode.NEWINSERT_HOSALLOCATION_RULE_ISERROR_CODE, GlobalErrorCode.NEWINSERT_HOSALLOCATION_RULE_ISERROR_MSG);
                }

            }

        } else {
            log.info("newInsert  HosAllocationRuleInfoDto 放号参数存在问题");
            throw new ItSysException(GlobalErrorCode.NEWINSERT_HOSALLOCATION_RULE_ISERROR_CODE, GlobalErrorCode.NEWINSERT_HOSALLOCATION_RULE_ISERROR_MSG);
        }
        return ResultUtil.success("success");
    }

    @Override
    public List<ResultAllocation> queryByRuleSataus(Integer beginStatus, Integer issuetype) {
        return resultAllocationDao.queryBystatus(beginStatus, issuetype);
    }

    @Override
    public int getRecruitByRuleIdAndBookingDay(Integer id, String afterDay) {

        return resultAllocationDao.getRecruitByRuleIdAndBookingDay(id, afterDay);
    }

    @Override
    public void updateById(Integer id, Integer status) {
        resultAllocationDao.updateById(id, status);
    }

    @Override
    public ListPageUtil queryRule(HosAllocationRuleInfoDto hosAllocationRuleInfoDto, String loginName) {
        ListPageUtil listPageUtil = hcrAllocationDao.queryRule(hosAllocationRuleInfoDto);
        List<Map<String, Object>> resultList = listPageUtil.getResultList();
        List<Map<String, Object>> resultListMap = new ArrayList<>();
        DepartMent departMent = userDao.findbyUserId(loginName);
        for (Map<String, Object> map : resultList) {
            String ruleId = String.valueOf(map.get("ruleId"));
            List<DoctorInfo> doctorInfo = new ArrayList<>();

            //根据规则id去找对应的社区
            //List<DoctorInfo> doctorInfo = hcrAllocationDao.queryCountryByRuleid(ruleId);

            String countryIds = "";
            List<HosAllocationRuleDeptInfoDto> hosAllocationRuleDeptInfoDtos = hcrAllocationDao.queryRuleDeptByRuleid(ruleId);

            //如果规则1
            if (Constans.ISSUETYPE1.equals(map.get("issueType"))) {
                for (HosAllocationRuleDeptInfoDto hosAllocationRuleDeptInfoDto : hosAllocationRuleDeptInfoDtos) {
                    countryIds = hosAllocationRuleDeptInfoDto.getCommunityDeptIdInfo();
                }
            } else {
                for (HosAllocationRuleDeptInfoDto hosAllocationRuleDeptInfoDto : hosAllocationRuleDeptInfoDtos) {
                    countryIds += hosAllocationRuleDeptInfoDto.getCommunityDeptId() + ",";
                }
                if (!StringUtils.isEmpty(countryIds)) {
                    countryIds = countryIds.substring(0, countryIds.length() - 1);
                }
            }
            String countyName = "";
            if (!StringUtils.isEmpty(countryIds)) {
                List<DepartMent> departMents = departMentDao.queryDepartMentInID(countryIds.substring(0, countryIds.length()));
                for (DepartMent departMent1 : departMents) {
                    countyName += departMent1.getName() + "|";
                }
            }


            String ruleBegin = DateUtil.formatDate((Date) map.get("ruleBegin"), "yyyy-MM-dd");
            String ruleEnd = DateUtil.formatDate((Date) map.get("ruleEnd"), "yyyy-MM-dd");
            //使用状态 1-停用 2-已停用
            String useStatus = map.get("useStatus").toString();
            String status = "";
            String date = DateUtil.formatDate(new Date(), "yyyy-MM-dd");

            //未开始：该放号规则开始时间小于当前时间；
            //预约中：当前时间在该放号规则开始时间、结束时间内；
            //已过期：该放号规则结束时间小于当前时间；
            long beginDays = DateUtil.getIntervalDays(date, ruleBegin);
            long endDays = DateUtil.getIntervalDays(date, ruleEnd);
            String fasongStatus = "";
            if (beginDays < 0) {//该放号规则开始时间小于当前时间；
                status = "1";//未开始
                fasongStatus = "1";//未开始
            } else if (beginDays >= 0 && endDays <= 0) {
                status = "2";//预约中
                fasongStatus = "2";//预约中
            } else if (endDays > 0) {
                status = "3";//已过期
                fasongStatus = "3";//已过期
            }
            //判断如果停诊
            if (Constans.USESTATUS2.equals(Integer.parseInt(useStatus))) {
                status = "4";
            }
            //根据登录名查名字

            if (null == map.get("fanghaoNum")) {
                map.put("fanghaoNum", 0);
            }
            if (null == map.get("shengyu")) {
                map.put("shengyu", 0);
            }
            map.put("diquName", departMent.getName());
            map.put("project", "结直肠");
            map.put("status", status);
            map.put("communityDeptIdInfo", countyName.substring(0,countyName.length()-1));
            map.put("fasongStatus", fasongStatus);
            resultListMap.add(map);

        }
        listPageUtil.setResultList(resultListMap);
        return listPageUtil;
    }

    @Override
    @Transactional
    public void diagnosisDown(ColonoscopyDto colonoscopyDto, String loginName) {
        try

        {
            String noticeStatus = colonoscopyDto.getNoticeStatus();
            DepartMent departMent = userDao.findbyUserId(loginName);


            if (Constans.NOTICE_STATUS.equals(noticeStatus)) {
                colonoscopyDto.setArea_dept_id(Integer.parseInt(departMent.getId()));
                //根据规则查人
                List<ColonoscopyDto> colonoscopyDtos = hcrAllocationDao.queryEntrtyByRuleId(colonoscopyDto);
                List<HospitalMessageCenterDto> hospitalMessageCenterDtos = new ArrayList<>();
                //根据放号时间+1  做发送短信、回滚机制
                for (ColonoscopyDto colonoscopyDto1 : colonoscopyDtos) {
                    long endDays = DateUtil.getIntervalDays(colonoscopyDto1.getReservationDate(), DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
                    if (endDays > 0 && colonoscopyDto1.getExamination_status() == null) {
                        //回退机制
                        ColonoscopyVo vo = new ColonoscopyVo();
                        BeanUtils.copyProperties(colonoscopyDto1, vo);
                        vo.setColonoscopyRecordId(colonoscopyDto1.getId());
                        cancelBooking(vo);
                        //colonoscopyService.cancelBooking(vo);
                        String[] parm = new String[2];
                        parm[0] = colonoscopyDto1.getReservationDate() + " " + colonoscopyDto1.getStartTime() + " " + colonoscopyDto1.getEndTime();
                        parm[1] = departMent.getName();

                        //发短信
                        sendSms.getAuthCode(colonoscopyDto1.getPhone(), "406001", parm);

                        //发送消息
                        HospitalMessageCenterDto hospitalMessageCenterDto = new HospitalMessageCenterDto();
                        hospitalMessageCenterDto.setSendUser(loginName);

                        hospitalMessageCenterDto.setAcceptUser(colonoscopyDto1.getCreateUser());
                        hospitalMessageCenterDto.setMessageType(Constans.meaasge_typpe1);
                        hospitalMessageCenterDto.setSid(colonoscopyDto1.getSid());
                        /**
                         * 1  异常    违反方案
                         *           sid
                         *           已经退出研究
                         *           sid、text
                         *           诊断为结直肠癌
                         *           sid
                         *申请|发放编辑
                         *          快递模块
                         *          sendUser、acceptUser、courierNumber
                         *          管理模块
                         *          sendUser、acceptUser、text（模块+sid）
                         *通知发放
                         *    public static String getMessage(String sendUser, String acceptUser, String meaasgeType, String text, String sid, String meaasgeTextType, String courierNumber) {
                         *
                         *
                         *
                         *
                         */
                        String message = SendMessageCenter.getMessage(loginName, colonoscopyDto1.getCreateUser(), Constans.meaasge_typpe1," "+colonoscopyDto1.getName()+" "+parm[0], colonoscopyDto1.getSid(), "", "", "");
                        hospitalMessageCenterDto.setMessageText(message);
                        hospitalMessageCenterDto.setId(UUID.randomUUID().toString().replace("-", ""));
                        hospitalMessageCenterDtos.add(hospitalMessageCenterDto);
                    }
                }

                hospitalMessageCenterDao.save(hospitalMessageCenterDtos);
            }

            //判断是规则的停诊还是放号的停诊
            if (!StringUtils.isEmpty(colonoscopyDto.getRuleId())) {
                Integer id = Integer.parseInt(colonoscopyDto.getRuleId());
                //根据规则id修改状态hos_allocation_rule_info\hospital_colonoscopy_reservation_allocation
                hcrAllocationDao.updateRule(id,departMent.getId());

                hcrAllocationDao.updateAllocationS(id,departMent.getId());
                //修改冗余表
                hcrAllocationDao.updateAllocationSALLDAy(id,departMent.getId());

            }
            if (!StringUtils.isEmpty(colonoscopyDto.getReservationDate())) {
                //根据时间和地区id
                hcrAllocationDao.updateAllocationSByDate(colonoscopyDto.getReservationDate(), colonoscopyDto.getArea_dept_id());
                //修改冗余表
                hcrAllocationDao.updateAllocationSByALLDay(colonoscopyDto.getReservationDate(), colonoscopyDto.getArea_dept_id());


            }

        } catch (Exception e) {
            log.info("diagnosisDown   停诊" + e.toString());
            throw new ItSysException(GlobalErrorCode.DIAGNOSIS_ISERROR_CODE, GlobalErrorCode.DIAGNOSIS_ISERROR_MSG);
        }

    }

    public void cancelBooking(ColonoscopyVo vo) {
        if (vo.getColonoscopyRecordId() == null) {
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }
        if (vo.getSid() == null) {
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }

        HospitalColonoscopyRecord record = personDao.findRecordByRecordId(vo.getColonoscopyRecordId());
        if (record == null) {
            throw new ItSysException(GlobalErrorCode.OBJECT_NOT_EXISTS + "", GlobalErrorCode.OBJECT_NOT_EXISTS_MSG);
        }
        //删除放号信息
        if (record.getAllocation_id() != null && record.getReserve_id() != null) {
            reserveDetailDao.updateReserveDetailByids(Integer.parseInt(record.getAllocation_id()), vo.getSid(), record.getReserve_id());
        }
        //完成待办数据,（待录入结肠镜检查）
        eventDao.updateStatus(vo.getSid(), vo.getColonoscopyRecordId(), EVENT_TYPE_8, EVENT_STATUS_DONE);

        //根据id查询Record表
        //修改来源
        List<HospitalColonoscopyRecord> hospitalColonoscopyRecords = colonoscopyDao.queryById(vo.getColonoscopyRecordId());
        if (hospitalColonoscopyRecords.size() != 1) {
            throw new ItSysException(GlobalErrorCode.ERR_COLONOSCOPY_RESERVE_NUMBER_FULL_ERROR_CODE, GlobalErrorCode.ERR_COLONOSCOPY_RESERVE_NUMBER_FULL_ERROR_MSG);
        }

        //更改预约状态
        colonoscopyDao.updateReserveStatusForCancelBooking(vo.getColonoscopyRecordId());
        //查询资格审核表数据
        HospitalReview review = personDao.getBySid(vo.getSid());
        //生成待办数据 ---增加待预约肠镜
        HospitalTodoEvent hospitalTodoEvent = new HospitalTodoEvent();
        hospitalTodoEvent.setCommunityDeptId(review.getCommunityDeptId());
        hospitalTodoEvent.setAreaDeptId(review.getAreaDeptId());
        hospitalTodoEvent.setDataId(vo.getColonoscopyRecordId());
        hospitalTodoEvent.setSid(vo.getSid());
        hospitalTodoEvent.setType(Constans.PERSON_TODO_EVENT_TYPE5);
        hospitalTodoEvent.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);

        //修改来源
        hospitalTodoEvent.setOperationSource(hospitalColonoscopyRecords.get(0).getEditoperationSource());
        hospitalTodoEvent.setOperationSourceId(hospitalColonoscopyRecords.get(0).getOperationSourceId());


        personDao.addTodoEvent(hospitalTodoEvent);
    }

    @Override
    public Result queryPutCodeByRuleId(String loginName, TodoVo vo) {
        // TODO Auto-generated method stub
        List<RestAllocation> restAllocations = new ArrayList<>();




        //获取用户信息
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        int areaId = doctorInfo.getAreaDeptId();

        ListPageUtil listPage;

        //校验是否含参数
        if (StringUtils.isEmpty(vo.getReservationDateToString())) {
            vo.setReservationDateToString(DateUtil.DateToString(new Date(), "yyyy-MM-dd"));
        }

        //如果没有放号信息返回空
        /**
         * vo.getRuleId()
         * vo.getReservationDateToString()
         * vo.getAreaDeptId()
         */
        vo.setAreaDeptId(areaId);
        List<AllocationDto> allocationDtos=hcrAllocationDao.queryAllocationByRuleIdAndDate(vo);
        if(allocationDtos.size()<1){
            return new Result(restAllocations);
        }

        /**
         * 放号记录详情，停诊规则某一天，日历表未排班 并且 数据暂无数据
         */
        for (AllocationDto allocationDto:allocationDtos) {
            if(Constans.USESTATUS2.equals(allocationDto.getUseStatus())){
                return new Result(restAllocations);
            }
        }

        //遍历
        if (Constans.ISSUETYPE1.equals(vo.getIssueType())) {
            //根据类型rule_id、类型 区分列表  有多少社区
            //select * from hos_allocation_rule_info rule ,hos_allocation_rule_dept_info d where rule.id=d.rule_id and rule.id=7
            List<HosAllocationRuleDeptInfoDto> deptInfoDtos = hcrAllocationDao.queryPutCodeByRuleId(areaId, vo);

            String[] split = deptInfoDtos.get(0).getCommunityDeptIdInfo().split(",");
            for (String communityDeptId : split) {
                //根据id和规则和时间、查放号表 查预约表
                int num = hcrAllocationDao.queryNumByRuleIdAndDate(vo.getRuleId(), vo.getReservationDateToString(), communityDeptId);
                RestAllocation restAllocation = new RestAllocation();
                restAllocation.setCommunityDeptId(communityDeptId);
                List<DepartMent> departMents = departMentDao.queryDepartMentByID(communityDeptId);
                restAllocation.setName(departMents.get(0).getName());
                restAllocation.setProject("结直肠");
                restAllocation.setRuleId(vo.getRuleId());
                restAllocation.setYuyueNum(num);
                restAllocation.setLevelName(departMents.get(0).getLevel());
                restAllocation.setReservationDate(vo.getReservationDateToString());
                restAllocations.add(restAllocation);
            }
        } else if (Constans.ISSUETYPE2.equals(vo.getIssueType())) {
            List<HosAllocationRuleDeptInfoDto> hosAllocationRuleDeptInfoDtos = hcrAllocationDao.queryRuleDeptByRuleid(vo.getRuleId());
            Set<String> countys=new HashSet<>();
            for (HosAllocationRuleDeptInfoDto hosAllocationRuleDeptInfoDto:hosAllocationRuleDeptInfoDtos){
                countys.add(hosAllocationRuleDeptInfoDto.getCommunityDeptId().toString());
            }

            Map<String,Integer> nums=new HashMap<>();
            for (HosAllocationRuleDeptInfoDto hosAllocationRuleDeptInfoDto : hosAllocationRuleDeptInfoDtos) {
                int num = hcrAllocationDao.queryNumByRuleIdAndDate(vo.getRuleId(), vo.getReservationDateToString(), hosAllocationRuleDeptInfoDto.getCommunityDeptId().toString());
                RestAllocation restAllocation = new RestAllocation();
                restAllocation.setCommunityDeptId(hosAllocationRuleDeptInfoDto.getCommunityDeptId().toString());
                List<DepartMent> departMents = departMentDao.queryDepartMentByID(hosAllocationRuleDeptInfoDto.getCommunityDeptId().toString());
                restAllocation.setName(departMents.get(0).getName());
                restAllocation.setProject("结直肠");
                restAllocation.setRuleId(vo.getRuleId());
                restAllocation.setYuyueNum(num);
                restAllocation.setLevelName(departMents.get(0).getLevel());
                restAllocation.setFanghaoNum(hosAllocationRuleDeptInfoDto.getNum());
                restAllocation.setReservationDate(vo.getReservationDateToString());
                //restAllocations.add(restAllocation);
                if(nums.get(hosAllocationRuleDeptInfoDto.getCommunityDeptId().toString())==null){
                    nums.put(hosAllocationRuleDeptInfoDto.getCommunityDeptId().toString(),hosAllocationRuleDeptInfoDto.getNum());
                }else{
                    nums.put(hosAllocationRuleDeptInfoDto.getCommunityDeptId().toString(),nums.get(hosAllocationRuleDeptInfoDto.getCommunityDeptId().toString())+hosAllocationRuleDeptInfoDto.getNum());
                }
            }
            for (String countyid:countys) {
                RestAllocation restAllocation = new RestAllocation();
                restAllocation.setCommunityDeptId(countyid);
                List<DepartMent> departMents = departMentDao.queryDepartMentByID(countyid);
                restAllocation.setName(departMents.get(0).getName());
                restAllocation.setProject("结直肠");
                restAllocation.setRuleId(vo.getRuleId());
                restAllocation.setLevelName(departMents.get(0).getLevel());
                int num = hcrAllocationDao.queryNumByRuleIdAndDate(vo.getRuleId(), vo.getReservationDateToString(), countyid);
                restAllocation.setFanghaoNum(nums.get(countyid));
                restAllocation.setReservationDate(vo.getReservationDateToString());
                restAllocation.setYuyueNum(num);
                restAllocations.add(restAllocation);
            }

        }
        return new Result(restAllocations);
    }

    @Override
    public Result queryCalendar(String loginName, TodoVo vo) {
        String afterDay = vo.getEndDate();
        String startDay = vo.getStartDate();


        List<BookingDateVo> list = new ArrayList<>();

        afterDay = DateUtil.DateToString(DateUtil.addDaytoDate(new Date(), 13), "yyyy-MM-dd");
        startDay = DateUtil.DateToString(new Date(), "yyyy-MM-dd");
        long beginIntervalDays = DateUtil.getIntervalDays(afterDay, startDay);

        List<ResultAllocation> resultAllocationss = hcrAllocationDao.queryRuleById(vo.getRuleId());

        for (int i = 0; i <= beginIntervalDays; i++) {
            //根据日期 和ruleid查询
            String date = DateUtil.DateToString(DateUtil.addDaytoDate(DateUtil.StringToDate(startDay), i), "yyyy-MM-dd");

            long beginDays = DateUtil.getIntervalDays(date, DateUtil.formatDate(resultAllocationss.get(0).getRuleBegin(), "yyyy-MM-dd"));
            long endDays = DateUtil.getIntervalDays(date, DateUtil.formatDate(resultAllocationss.get(0).getRuleEnd(), "yyyy-MM-dd"));

            if (beginDays >= 0 && endDays <= 0) {//时间在放号列表里
                //没有数据 未排班
                List<ResultAllocation> resultAllocations = hcrAllocationDao.queryByRuleIdAndDate(date, vo.getRuleId());
                if (resultAllocations.size() == 0) {
                    BookingDateVo bookingDateVo = new BookingDateVo();
                    int number = DateUtil.getWeek(date).getNumber();
                    bookingDateVo.setWeek(number);
                    bookingDateVo.setDay(date);
                    bookingDateVo.setStatus("未排班");
                    list.add(bookingDateVo);
                } else {
                    //预约人数
                    Integer yuyueNum = hcrAllocationDao.queryDetailByRuleIdAndDate(date, vo.getRuleId());
                    //剩余人数
                    int num = resultAllocations.get(0).getSumAmount() - yuyueNum;
                    BookingDateVo bookingDateVo = new BookingDateVo();
                    int number = DateUtil.getWeek(date).getNumber();
                    bookingDateVo.setWeek(number);
                    bookingDateVo.setDay(date);
                    bookingDateVo.setStatus(null);
                    bookingDateVo.setNum(num);
                    //查看是否是停诊
/*                    if(Constans.USESTATUS2.equals(resultAllocations.get(0).getUseStatus())){
                        BookingDateVo bookingDateVo1 = new BookingDateVo();
                        bookingDateVo1.setWeek(number);
                        bookingDateVo1.setDay(date);
                        bookingDateVo1.setStatus("未排班");//未排班
                        list.add(bookingDateVo1);
                    }else{*/
                        list.add(bookingDateVo);
                    //}
                }
            } else {//时间在放号列表时间之外
                BookingDateVo bookingDateVo = new BookingDateVo();
                int number = DateUtil.getWeek(date).getNumber();
                bookingDateVo.setWeek(number);
                bookingDateVo.setDay(date);
                bookingDateVo.setStatus("未排班");//未排班
                list.add(bookingDateVo);
            }
        }
        return new Result(list);
    }

    @Override
    public Result queryNumByRuleIdAndRule(String loginName, TodoVo vo) {
        Map<String, Integer> map = new HashMap<>();
        List<ResultAllocation> resultAllocations1 = hcrAllocationDao.queryRuleById(vo.getRuleId());
        if(Constans.ISSUETYPE1.equals(resultAllocations1.get(0).getIssueType())){
            List<ResultAllocation> resultAllocations = hcrAllocationDao.queryByRuleIdAndDateByUseStatus(vo.getReservationDateToString(), vo.getRuleId());
            int fanghaoNum=0;
            int shengyuNum=0;
            if(resultAllocations.size()<=0){

            }else {
                //抢号
                Integer yuyueNum = hcrAllocationDao.queryDetailByRuleIdAndDate(vo.getReservationDateToString(), vo.getRuleId());
                fanghaoNum=resultAllocations.get(0).getSumAmount();
                //剩余人数
                shengyuNum = resultAllocations.get(0).getSumAmount() - yuyueNum;
            }
            //List<ResultAllocation> resultAllocations = hcrAllocationDao.queryByRuleIdAndDate(date, vo.getRuleId());
            map.put("fanghaoNum", fanghaoNum);
            map.put("shengyuNum", shengyuNum);
        }else {
            //社区分开
            Integer sumNum = hcrAllocationDao.querySumNumBy(vo.getRuleId(), vo.getReservationDateToString(), vo.getCommunityDeptId().toString());
            int num = hcrAllocationDao.queryNumByRuleIdAndDate(vo.getRuleId(), vo.getReservationDateToString(), vo.getCommunityDeptId().toString());
            map.put("fanghaoNum", sumNum);
            map.put("shengyuNum", sumNum - num);
        }
        return new Result(map);
    }

    @Override
    public Result queryEntityByRuleIdAndRule(String loginName, TodoVo vo) {
        ListPageUtil listPageUtil = hcrAllocationDao.queryEntityByRuleIdAndRule(vo.getRuleId(), vo.getReservationDateToString(), vo.getCommunityDeptId(), vo.getPageNo(), vo.getPageSize());
        Result result = new Result(listPageUtil.getResultList(), listPageUtil.getPageInfo());
        return result;
    }

    @Override
    public Result queryPerSonByRuleIdAndRule(String loginName, TodoVo vo) {
        //查询正常用户
        ListPageUtil listPageUtil = hcrAllocationDao.queryPerSonByRuleIdAndRule(vo);
        //查询停诊用户
        ListPageUtil listPageUtil1 = hcrAllocationDao.queryPerSonByRuleIdAndRuleByTingZhen(vo);
        List resultList = listPageUtil.getResultList();
/*        if (listPageUtil1.getResultList().size() > 0) {
            resultList.addAll(listPageUtil1.getResultList());
        }*/
        listPageUtil.setResultList(resultList);
        Result result = new Result(listPageUtil.getResultList(), listPageUtil.getPageInfo());
        return result;
    }

    @Override
    public Result queryFanghao(String loginName, TodoVo vo) {
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        int areaId = doctorInfo.getAreaDeptId();
        vo.setAreaDeptId(areaId);
        ListPageUtil listPageUtil = hcrAllocationDao.queryFanghao(vo);
        List<Map<String, Object>> resultList = listPageUtil.getResultList();
        List<Map<String, Object>> resultListMap = new ArrayList<>();
        for (Map<String, Object> map : resultList) {
            //reservation_date,area_dept_id
            String reservation_date = DateUtil.formatDate((Date) map.get("reservation_date"), "yyyy-MM-dd");
            String area_dept_id = String.valueOf(map.get("area_dept_id"));

            //String fanghaoNum = String.valueOf(map.get("fanghaoNum"));
            //校验是否全部停诊还是放号

            //获取放号总数
            TodoVo vo1=new TodoVo();
            vo1.setAreaDeptId(Integer.parseInt(area_dept_id));
            vo1.setReservationDateToString(reservation_date);
            vo1.setUseStatus(Constans.USESTATUS1);
            int i= hcrAllocationDao.queryFanghaoNum(vo1);
/*            if(i<=0){
                vo1.setUseStatus(Constans.USESTATUS2);
                i=hcrAllocationDao.queryFanghaoNum(vo1);
            }*/
            String fanghaoNum = String.valueOf(i);

            List<DepartMent> departMents = departMentDao.queryDepartMentByID(area_dept_id);
            //根据时间、地区查询 总数
            String name = departMents.get(0).getName();
            map.put("deptName", name);
            //根据时间、地区查预约总数
            String num = String.valueOf(hcrAllocationDao.queryNumByAreaIdAndDate(area_dept_id, reservation_date,vo1.getUseStatus()));


            map.put("SumYuyue", fanghaoNum + "/" + String.valueOf((Integer.parseInt(fanghaoNum))-Integer.parseInt(num)));
            //根据时间、地区查预约id  冗余表
            List<AllocationDto> allocationDtos = hcrAllocationDao.queryEntityByAreaIdAndDate(area_dept_id, reservation_date);
            String allocationId = "";
            StringBuffer stringBuffer = new StringBuffer();
            Integer useStatus = Constans.USESTATUS2;
            for (AllocationDto allocationDto : allocationDtos) {
                //stringBuffer.append(allocationDto.getId() + ",");
                if (Constans.USESTATUS1.equals(allocationDto.getUseStatus())) {
                    useStatus = Constans.USESTATUS1;
                }
            }
            //根据时间、地区查预约id
            List<AllocationDto> allocationDtos2 = hcrAllocationDao.queryEntityByAreaIdAndDateNo(area_dept_id, reservation_date,vo1.getUseStatus());

            for (AllocationDto allocationDto : allocationDtos2) {
                stringBuffer.append(allocationDto.getId() + ",");
            }
            if (!StringUtils.isEmpty(stringBuffer.toString())) {
                allocationId = stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1);
                map.put("allocationId", allocationId);
            }

            String fasongStatus = "2";//停诊
            if (DateUtil.getIntervalDays(reservation_date, DateUtil.formatDate(new Date(), "yyyy-MM-dd")) <= 0) {
                fasongStatus = "3";//停诊置灰
            }
            map.put("yuyueNum", num);
            map.put("useStatus", useStatus);
            map.put("fasongStatus", fasongStatus);
            resultListMap.add(map);
        }
        listPageUtil.setResultList(resultListMap);
        return new Result(listPageUtil.getResultList(), listPageUtil.getPageInfo());
    }


    /**
     * 根据 1.每天|一周  2.13天计算
     *
     * @param args
     */
    public static List<AllocationDto> getallocationDtos(Date ruleEnd, Date ruleBegin, Integer ruleType, List<AllocationDto> list, Integer dayWai, String weekInfo) {
        List<AllocationDto> getallocationDtoss = new ArrayList<>();
        //计算13天的
        long beginIntervalDays = DateUtil.getIntervalDays(ruleEnd, ruleBegin);
        if (0 <= beginIntervalDays && beginIntervalDays <= 13) { //0-13内 全部放完  begin_status 结束
            if (Constans.RULE_TYPE1.equals(ruleType)) {
                for (AllocationDto allocationDto : list) {
                    for (int i = 0; i <= beginIntervalDays; i++) {
                        AllocationDto allocationDto1 = new AllocationDto();
                        BeanUtils.copyProperties(allocationDto, allocationDto1);
                        Date day = DateUtil.addDaytoDate(ruleBegin, i);
                        allocationDto1.setReservationDate(day);
                        getallocationDtoss.add(allocationDto1);
                    }
                }
            } else {
                for (AllocationDto allocationDto : list) {
                    List<Integer> integers = JSONUtils.toList(weekInfo, Integer.class);
                    for (int i = 0; i <= beginIntervalDays; i++) {
                        Date day = DateUtil.addDaytoDate(ruleBegin, i);
                        int number = DateUtil.getWeek(day).getNumber();
                        if (integers.contains(number)) {
                            AllocationDto allocationDto1 = new AllocationDto();
                            BeanUtils.copyProperties(allocationDto, allocationDto1);
                            allocationDto1.setReservationDate(day);
                            getallocationDtoss.add(allocationDto1);
                        }
                    }
                }
            }

        } else if (beginIntervalDays > 13) {      //13天以外放14天   begin_status 1开始
            if (Constans.RULE_TYPE1.equals(ruleType)) {
                for (AllocationDto allocationDto : list) {
                    for (int i = 0; i <= 13; i++) {
                        AllocationDto allocationDto1 = new AllocationDto();
                        BeanUtils.copyProperties(allocationDto, allocationDto1);
                        Date day = DateUtil.addDaytoDate(ruleBegin, i);
                        allocationDto1.setReservationDate(day);
                        getallocationDtoss.add(allocationDto1);
                    }
                }
            } else {
                for (AllocationDto allocationDto : list) {
                    List<Integer> integers = JSONUtils.toList(weekInfo, Integer.class);
                    for (int i = 0; i <= 13; i++) {
                        Date day = DateUtil.addDaytoDate(ruleBegin, i);
                        int number = DateUtil.getWeek(day).getNumber();
                        if (integers.contains(number)) {
                            AllocationDto allocationDto1 = new AllocationDto();
                            BeanUtils.copyProperties(allocationDto, allocationDto1);
                            allocationDto1.setReservationDate(day);
                            getallocationDtoss.add(allocationDto1);
                        }
                    }
                }
            }
        }

        return getallocationDtoss;
    }

    /**
     * 根据 每一天
     *
     * @param args
     */
    public static List<AllocationDto> getallocationDtoAllDay(Date ruleEnd, Date ruleBegin, Integer ruleType, List<AllocationDto> list, Integer dayWai, String weekInfo) {
        List<AllocationDto> getallocationDtoss = new ArrayList<>();
        long beginIntervalDays = DateUtil.getIntervalDays(ruleEnd, ruleBegin);
            if (Constans.RULE_TYPE1.equals(ruleType)) {
                for (AllocationDto allocationDto : list) {
                    for (int i = 0; i <= beginIntervalDays; i++) {
                        AllocationDto allocationDto1 = new AllocationDto();
                        BeanUtils.copyProperties(allocationDto, allocationDto1);
                        Date day = DateUtil.addDaytoDate(ruleBegin, i);
                        allocationDto1.setReservationDate(day);
                        getallocationDtoss.add(allocationDto1);
                    }
                }
            } else {
                for (AllocationDto allocationDto : list) {
                    List<Integer> integers = JSONUtils.toList(weekInfo, Integer.class);
                    for (int i = 0; i <= beginIntervalDays; i++) {
                        Date day = DateUtil.addDaytoDate(ruleBegin, i);
                        int number = DateUtil.getWeek(day).getNumber();
                        if (integers.contains(number)) {
                            AllocationDto allocationDto1 = new AllocationDto();
                            BeanUtils.copyProperties(allocationDto, allocationDto1);
                            allocationDto1.setReservationDate(day);
                            getallocationDtoss.add(allocationDto1);
                        }
                    }
                }
            }
        return getallocationDtoss;
    }
    public static void main(String[] args) {
        //未开始：该放号规则开始时间小于当前时间；
        //预约中：当前时间在该放号规则开始时间、结束时间内；
        //已过期：该放号规则结束时间小于当前时间；
        String date = "2019-1-28";
        String status = "";
        long beginDays = DateUtil.getIntervalDays(date, "2018-12-28");
        long endDays = DateUtil.getIntervalDays(date, "2019-1-28");
        if (beginDays < 0) {//该放号规则开始时间小于当前时间；
            status = "未开始";
        } else if (beginDays >= 0 && endDays <= 0) {
            status = "预约中";
        } else if (endDays > 0) {
            status = "已过期";
        }
        //System.out.println(status);

        List<DoctorInfo> doctorInfo = new ArrayList<>();

        DoctorInfo doctorInfo1 = new DoctorInfo();
        doctorInfo1.setLoginName("我");
        DoctorInfo doctorInfo2 = new DoctorInfo();
        doctorInfo2.setLoginName("是");
        doctorInfo.add(doctorInfo1);
        doctorInfo.add(doctorInfo2);
        //System.out.println(JSONUtils.toJson(doctorInfo));
        System.out.println(DateUtil.getIntervalDays("2019-01-08", DateUtil.formatDate(new Date(), "yyyy-MM-dd")));

    }
}
