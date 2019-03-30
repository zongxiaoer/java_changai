/**
 * Project Name:service-base-ic
 * File Name:StoolDnaServiceImpl.java
 * Package Name:com.yuntongxun.itsys.base.service.impl
 * Date:2018年4月17日下午8:10:11
 * Copyright (c) 2018, ty All Rights Reserved.
 */

package com.yuntongxun.itsys.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.util.SendSms;
import com.yuntongxun.itsys.base.dao.HospitalReferenceRecordDao;
import com.yuntongxun.itsys.base.dao.PersonDao;
import com.yuntongxun.itsys.base.po.HtEvent;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.dao.HtEventDao;
import com.yuntongxun.itsys.base.po.HospitalColonoscopyRecord;
import com.yuntongxun.itsys.base.po.HospitalColonoscopyResult;
import com.yuntongxun.itsys.base.po.dto.ItsysUserDto;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
import com.yuntongxun.itsys.base.service.ColonoscopyService;
import com.yuntongxun.itsys.base.vo.ColonoscopyIssueVo;
import com.yuntongxun.itsys.base.vo.ColonoscopyNotificationVo;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
import com.yuntongxun.itsys.base.vo.TodoVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.dao.StoolDnaDao;
import com.yuntongxun.itsys.base.dao.impl.PersonDaoImpl;
import com.yuntongxun.itsys.base.po.HospitalReview;
import com.yuntongxun.itsys.base.po.StoolDna;
import com.yuntongxun.itsys.base.service.StoolDnaService;

/**
 * ClassName:StoolDnaServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年4月17日 下午8:10:11 <br/>
 *
 * @author ty
 * @see
 * @since JDK 1.8
 */
@Service
public class StoolDnaServiceImpl implements StoolDnaService {


    @Autowired
    private SendSms sendSms;

    @Autowired
    private StoolDnaDao stoolDnaDao;


    @Autowired
    private PersonDao personDao;

    @Autowired
    private PersonServiceImpl personService;

    @Autowired
    private PersonDaoImpl personDaoImpl;

    @Autowired
    private ColonoscopyService colonoscopyService;

    @Autowired
    private HtEventDao htEventDao;

    @Autowired
    private HospitalReferenceRecordDao hospitalReferenceRecordDao;

    private final Logger log = LogManager.getLogger(StoolDnaServiceImpl.class);

    @Override
    public ListPageUtil query(StoolDna dna, String loginName) {
        Object[] ids = personService.getRedisKeyByLoginName(loginName);
        Integer communityDeptId = (Integer) ids[0];
        Integer areaDeptId = (Integer) ids[1];
        log.info("@Service-StoolDna-query 社区医院id和地区医院id的值分别为  参数：communityDeptId={},areaDeptId={}.", communityDeptId, areaDeptId);
        dna.setCommunityDeptId(communityDeptId);
        dna.setAreaDeptId(areaDeptId);
        log.info("@Service-StoolDna-query 分页查询粪便DNA管理根据条件查询受试者数据  参数：dna={}.", dna.toString());
        //分页查询粪便DNA管理根据条件查询受试者数据
        ListPageUtil listPage = stoolDnaDao.query(dna);
        return listPage;
    }

    @Transactional
    @Override
    public void addDnaCode(StoolDna dna) {
        //根据id修改dna表
        stoolDnaDao.updateDnaById(dna);
        log.info("@Service-StoolDna-addDnaCode 更改粪便DNA编码成功");
        //修改dna代办表
        stoolDnaDao.updateDnaEvent(dna);
        log.info("@Service-StoolDna-addDnaCode 更改粪便DNA代办表成功");
    }

    @Override
    public void addDnaRecord(StoolDna dna, String loginName) {
        Object[] ids = personService.getRedisKeyByLoginName(loginName);
        Integer communityDeptId = (Integer) ids[0];
        Integer areaDeptId = (Integer) ids[1];
        //根据sid查询受试者是否存在
        String sid = dna.getSid();
        HospitalReview infoBySid = personDaoImpl.findInfoBySid(sid);
        dna.setAreaDeptId(areaDeptId);
        dna.setCommunityDeptId(communityDeptId);
        if (infoBySid == null) {
            throw new ItSysException(GlobalErrorCode.ERR_PERSON_NULL_CODE, GlobalErrorCode.ERR_PERSON_NULL_MSG);
        }
        personDaoImpl.addStoolDna(dna);
        log.info("@Service-StoolDna-addDnaRecord 添加粪便DNA记录成功");
    }

    @Override
    public List<StoolDna> getStoolList(StoolDna dna) {
        List<StoolDna> list = stoolDnaDao.getStoolList(dna);
        log.info("@Service-StoolDna-query 列表查询粪便DNA管理根据条件查询受试者条数  参数：num={}.", list == null ? 0 : list.size());
        return list;
    }

    @Override
    public ListPageUtil queryAreaDna(StoolDna dna, String loginName) {
        Integer areaDeptId = personService.getAreaIdByLoginName(loginName);
        log.info("@Service-StoolDna-queryAreaDna 地区医院id的值为  参数：areaDeptId={}.", areaDeptId);
        dna.setAreaDeptId(areaDeptId);
        log.info("@Service-StoolDna-queryAreaDna 地区分页查询粪便DNA管理根据条件查询受试者数据  参数：dna={}.", dna.toString());
        //分页查询粪便DNA管理根据条件查询受试者数据
        ListPageUtil listPage = stoolDnaDao.queryAreaDnaPage(dna);
        return listPage;
    }

    @Override
    public ListPageUtil queryCountryDna(StoolDna dna, String loginName) {
        log.info("@Service-StoolDna-queryAreaDna 地区分页查询粪便DNA管理根据条件查询受试者数据  参数：dna={}.", dna.toString());
        //分页查询粪便DNA管理根据条件查询受试者数据
        ListPageUtil listPage = stoolDnaDao.queryCountryDnaPage(dna);
        return listPage;
    }

    @Override
    @Transactional
    public void updateIssuedStatus(StoolDna dna) {
        //判断是否发放
        if (dna.getDnaCheckInformStatus().equals(Constans.DNA_COUNTRY_CHECK_INFORM_STATUS2)) {//触发添加代办
            //根据id获取信息
            List<StoolDna> list = stoolDnaDao.queryById(dna);
            StoolDna stoolDna = list.get(0);
            HtEvent todo = new HtEvent();
            todo.setType(Constans.PERSON_TODO_EVENT_TYPE17);
            todo.setDataId(dna.getId());
            todo.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
            todo.setSid(stoolDna.getSid());
            todo.setCommunityDeptId(stoolDna.getCommunityDeptId());
            todo.setAreaDeptId(stoolDna.getAreaDeptId());
            log.info("@Service-person-personInsert 新增一条未录入粪便DNA装置编号待办事件  参数：todo={}.", todo.toString());
            personDao.addTodoEvent(todo);
            //添加未发放状态
            dna.setDnaCheckInformNationDate(new Date());
            dna.setDnaCommunityInformStatus(Constans.DNA_COMMUNITY_INFORM_STATUS2);
        }
        //修改国家DNA代办状态
        stoolDnaDao.updateDNATodoEvent(Constans.PERSON_TODO_EVENT_STATUS2, dna.getId(), Constans.PERSON_TODO_EVENT_TYPE16);
        //修改参数
        stoolDnaDao.updateIssuedStatus(dna);
    }

    @Override
    public boolean sendDna(String phone, int dnaCheckResult) {
        String[] parm = new String[1];
        parm[0] = String.valueOf(dnaCheckResult);
        //校验结果
        if (dnaCheckResult == Constans.DNA_CHECK_RESULT_YANG) {
            parm[0] = "阳性";
        } else if (dnaCheckResult == Constans.DNA_CHECK_RESULT_YING) {
            parm[0] = "阴性";
        } else {
            parm[0] = "无效";
        }
        return sendSms.getAuthCode(phone, "397095", parm);

    }

    @Override
    public List<ItsysUserDto> queryloginNameById(Integer id) {
        return stoolDnaDao.queryloginNameById(id);
    }

    @Override
    public List<StoolDna> queryById(Integer id) {
        StoolDna stoolDna = new StoolDna();
        ;
        stoolDna.setId(id);
        return stoolDnaDao.queryById(stoolDna);
    }

    @Override
    @Transactional
    public void updateDnaCode(StoolDna dna, HospitalReferenceRecordDto hospitalReferenceRecordDto, boolean isArea) {
        //根据id修改dna表
        try {
            stoolDnaDao.updateEditDnaById(dna, isArea);
            if (isArea) {
                hospitalReferenceRecordDao.saveArea(hospitalReferenceRecordDto);
            } else {
                hospitalReferenceRecordDao.updateForEdir(hospitalReferenceRecordDto);
            }
        } catch (Exception e) {
            throw new ItSysException(
                    GlobalErrorCode.HOSPITAL_MESSAGE_CENTER_UPDATE_ERROR_CODE, GlobalErrorCode.HOSPITAL_MESSAGE_CENTER_UPDATE_ERROR_MSG);
        }

    }

    @Override
    public List<ItsysUserDto> queryloginNameByDId(Integer id) {
        return stoolDnaDao.queryloginNameByDId(id);
    }

    @Override
    public void updateIssuedStatusList(List<StoolDna> dnas) {

        boolean isok = false;
        List<StoolDna> stoolDnas = new ArrayList<>();
        //将批量 审批通过|审批不通过分成2个集合
        for (StoolDna dna : dnas) {
            if (dna.getDnaCheckInformStatus().equals(Constans.DNA_COUNTRY_CHECK_INFORM_STATUS2)) {
                stoolDnas.add(dna);
                isok = true;
            } else if (dna.getDnaCheckInformStatus().equals(Constans.DNA_COUNTRY_CHECK_INFORM_STATUS)) {
                isok = false;
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (StoolDna dna : dnas) {
            stringBuffer.append("'" + dna.getId() + "',");
        }
        List<StoolDna> listOk = new ArrayList<>();
        //审评通过集合
        if (isok && stoolDnas.size() == dnas.size()) {
            //批量查询dnaID
            List<StoolDna> list = stoolDnaDao.queryByIdS(stringBuffer.toString().substring(0, stringBuffer.length() - 1));
            List<HtEvent> todoList = new ArrayList<>();
            for (StoolDna dna : list) {
                HtEvent todo = new HtEvent();
                todo.setType(Constans.PERSON_TODO_EVENT_TYPE17);
                todo.setDataId(dna.getId());
                todo.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
                todo.setSid(dna.getSid());
                todo.setCommunityDeptId(dna.getCommunityDeptId());
                todo.setAreaDeptId(dna.getAreaDeptId());
                todoList.add(todo);
                dna.setDnaCheckInformNationDate(new Date());
                dna.setDnaCommunityInformStatus(Constans.DNA_COMMUNITY_INFORM_STATUS2);
                dna.setDnaCheckInformStatus(Constans.DNA_COUNTRY_CHECK_INFORM_STATUS2);
                listOk.add(dna);
            }
            log.info("@Service-person-personInsert 批量插入一条国家粪便审批待办事件  参数：todo={}.", todoList.toString());
            personDao.addTodoEventList(todoList);
        }


        //审评通过集合
        //批量查询dnaID
        //批量添加代办
        //批量设置参数

        //修改国家DNA代办状态
        ////修改参数
        //审评不通过集合
        //修改国家DNA代办状态
        ////修改参数

        //修改国家DNA代办状态
        stoolDnaDao.updateDNATodoEventList(Constans.PERSON_TODO_EVENT_STATUS2, stringBuffer.toString().substring(0, stringBuffer.length() - 1), Constans.PERSON_TODO_EVENT_TYPE16);
        //修改参数
        if (isok && stoolDnas.size() == dnas.size()) {
            stoolDnaDao.updateIssuedStatusList(listOk);
        } else {
            stoolDnaDao.updateIssuedStatusList(dnas);
        }

    }

    /**
     * 查询 未发放dna结果列表 受试者列表
     */
    @Override
    public ListPageUtil notIssueDna(TodoVo vo, String loginName) {
        log.info("@Service notIssueDna Start");
        //获取用户信息
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        ListPageUtil listPage = stoolDnaDao.notIssueDna(doctorInfo.getCommunityDeptId(), 1, vo);
        log.info("@Service notIssueDna End");
        return listPage;
    }

    /**
     * 社区发放dna告知书
     *
     * @param dna
     */
    @Override
    @Transactional
    public void updateCommDnaInformStatus(StoolDna dna) {
        //1、修改状态
        stoolDnaDao.updateCommDnaInformStatus(dna);
        //2、完成 社区粪便DNA发放代办处理
        htEventDao.updateStatus(dna.getSid(), dna.getId(), Constans.PERSON_TODO_EVENT_TYPE17, Constans.PERSON_TODO_EVENT_STATUS2);
    }

    /**
     * 查询单个dna详情
     *
     * @param id
     * @return
     */
    @Override
    public Object getDnaInfoById(int id) {
        return stoolDnaDao.getDnaInfoById(id);
    }
}

