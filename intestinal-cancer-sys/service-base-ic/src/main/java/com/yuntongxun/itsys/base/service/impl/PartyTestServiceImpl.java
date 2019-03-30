package com.yuntongxun.itsys.base.service.impl;

import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.*;
import com.yuntongxun.itsys.base.common.util.sftp.SFTPUtil;
import com.yuntongxun.itsys.base.dao.PartyTestDao;
import com.yuntongxun.itsys.base.dao.PersonDao;
import com.yuntongxun.itsys.base.dao.StoolDnaDao;
import com.yuntongxun.itsys.base.po.DnaSynLogPO;
import com.yuntongxun.itsys.base.po.HospitalStoolDnaPO;
import com.yuntongxun.itsys.base.po.HtEvent;
import com.yuntongxun.itsys.base.po.StoolDna;
import com.yuntongxun.itsys.base.po.dto.parytest.PartyTestInformDto;
import com.yuntongxun.itsys.base.po.dto.parytest.PartyTestQueryDto;
import com.yuntongxun.itsys.base.service.PartyTestService;
import com.yuntongxun.itsys.base.vo.FitResultVo;
import com.yuntongxun.itsys.base.vo.Response;
import com.yuntongxun.itsys.base.vo.Result;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wp_sp
 * @date 2018/5/10
 */
@Service
public class PartyTestServiceImpl implements PartyTestService {

    private final Logger log = LogManager.getLogger(PartyTestServiceImpl.class);

    @Value("${sftp.url.path}")
    private String sftppath;

    @Value("${sftp.url.name}")
    private String sftppathname;

    @Value("${sftp.url.ip}")
    private String sftppathip;

    @Value("${sftp.url.port}")
    private Integer sftppathport;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private PartyTestDao partyTestDao;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private StoolDnaDao stoolDnaDao;

    @Override
    public ListPageUtil queryDNAcodeListForPage(PartyTestQueryDto partyTestQueryDto) {
        log.info("@dao queryDNAcodeListForPage 查询参数 person = {}", partyTestQueryDto.toString());
        StringBuilder sql = new StringBuilder();
        List args = new ArrayList();
        sql.append("SELECT r.apply_status,r.edit_status,r.approval_status,r.id,r.sid,r.dna_code,dna_check_goal,r.dna_check_result,r.dna_check_quantum,r.dna_check_filePath FROM hospital_stool_dna  r LEFT JOIN hospital_intestine_review a ON r.sid = a.sid WHERE 1=1 and dna_code is not null and a.overall_status_cy <> 2 ");

        if (StringUtils.isNotBlank(partyTestQueryDto.getDnaCode())) {
            sql.append(" and r.dna_code like ? ");
            args.add("%" + partyTestQueryDto.getDnaCode() + "%");
        }

        if (null != partyTestQueryDto.getDnaCheckResult()) {
            sql.append(" and r.dna_check_result = ? ");
            args.add(partyTestQueryDto.getDnaCheckResult());
        }

        if (null != partyTestQueryDto.getDnaCheckEnterStatus()) {
            sql.append(" and r.dna_check_enter_status = ? ");
            args.add(partyTestQueryDto.getDnaCheckEnterStatus());
        }

        sql.append(" order by r.date_created desc");

        ListPageUtil listPage = new ListPageUtil(sql.toString(), args.toArray(), partyTestQueryDto.getPageNo(), partyTestQueryDto.getPageSize(), jdbc, null);
        return listPage;
    }

    @Override
    @Transactional
    public void informByDnaCodeAndSid(PartyTestInformDto partyTestQueryDto) {

        //根据id获取信息
        StoolDna dna = new StoolDna();
        dna.setId(partyTestQueryDto.getId());
        List<StoolDna> list = stoolDnaDao.queryById(dna);
        if (list.size() != 1) {
            throw new ItSysException(GlobalErrorCode.ERR_PARAM_NULL, "必填参数为空");
        }
        if (Constans.APPLY_EDIT_STATUS2.equals(list.get(0).getApplyStatus()) || Constans.EDIT_STATUS2.equals(list.get(0).getEditStatus())) {
            throw new ItSysException(GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_EDIT_IS_ERROR_CODE1, GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_EDIT_IS_ERROR_MSG1);
        }
        partyTestQueryDto.setDnaCheckEnterStatus(Constans.DNA_CHECK_INFORM_STATUS2);
        partyTestQueryDto.setDnaCheckInformStatus(Constans.DNA_COUNTRY_CHECK_INFORM_STATUS3);
        partyTestDao.informByDnaCodeAndSid(partyTestQueryDto);
        StoolDna stoolDna = list.get(0);
        //根据id获取
        HtEvent todo = new HtEvent();
        todo.setType(Constans.PERSON_TODO_EVENT_TYPE16);
        todo.setDataId(partyTestQueryDto.getId());
        todo.setStatus(Constans.PERSON_TODO_EVENT_STATUS1);
        todo.setSid(partyTestQueryDto.getSid());
        todo.setCommunityDeptId(stoolDna.getCommunityDeptId());
        todo.setAreaDeptId(stoolDna.getAreaDeptId());
        log.info("@Service-person-personInsert 新增一条未录入粪便DNA装置编号待办事件  参数：todo={}.", todo.toString());
        personDao.addTodoEvent(todo);
    }

    @Override
    public HospitalStoolDnaPO querybyid(PartyTestInformDto partyTestQueryDto) {
        return partyTestDao.querybyid(partyTestQueryDto);
    }

    @Override
    public List<String> queryAll() {
        List<HospitalStoolDnaPO> hospitalStoolDnaPOS = partyTestDao.queryAll();
        List<String> list = new ArrayList<>();
        for (HospitalStoolDnaPO hospitalStoolDnaPO : hospitalStoolDnaPOS) {
            list.add(hospitalStoolDnaPO.getDnaCode());
        }
        if (list.size() > 0) {
            return list;
        }
        return null;
    }

    @Override
    @Transactional
    public Integer synResult(PartyTestInformDto partyTestQueryDto) {
        // 非空验证
        if (StringUtils.isEmpty(partyTestQueryDto.getDataNumber())) {
            log.info("dnaResult DataNumber is null");
            throw new ItSysException(GlobalErrorCode.DNA_PARAM_ISNO_CODE, GlobalErrorCode.DNA_PARAM_ISNO_CODE_MSG);
        }
        if (StringUtils.isEmpty(partyTestQueryDto.getTimestamp())) {
            log.info("dnaResult timestamp is null");
            throw new ItSysException(GlobalErrorCode.DNA_PARAM_ISNO_CODE, GlobalErrorCode.DNA_PARAM_ISNO_CODE_MSG);
        } else {
            long timestamp = 0;
            try {
                timestamp = Long.parseLong(partyTestQueryDto.getTimestamp());
            } catch (NumberFormatException e) {
                log.info("dnaResult timestamp is error");
                throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
            }
        }

        if (partyTestQueryDto.getSign() == null || "".equals(partyTestQueryDto.getSign())) {
            log.info("dnaResult sign is null");
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        } else {
            if (!partyTestQueryDto.getSign().toUpperCase().equals(EncryptUtil.md5(partyTestQueryDto.getDataNumber() + partyTestQueryDto.getTimestamp() + "nhdnaresult"))) {
                log.info("dnaResult sign!=md5(DataNumber+timestamp+nhdnaresult) ");
                throw new ItSysException(GlobalErrorCode.DNA_SIGN_ERROR_CODE, GlobalErrorCode.DNA_SIGN_ERROR_CODE_MSG);
            }
        }

        //判断DNA编码存在
        if (StringUtils.isEmpty(partyTestQueryDto.getDnaCode())) {
            log.info(" dnaResult dnaCode is null");
            throw new ItSysException(GlobalErrorCode.DNA_PARAM_ISNO_CODE, GlobalErrorCode.DNA_PARAM_ISNO_CODE_MSG);
        }

        //分数是否存在
        if (StringUtils.isEmpty(partyTestQueryDto.getDnaCheckGoalToString())) {
            log.info(" dnaResult checkGoal is null");
            throw new ItSysException(GlobalErrorCode.DNA_PARAM_ISNO_CODE, GlobalErrorCode.DNA_PARAM_ISNO_CODE_MSG);
        }
        //将无效的分数
        if (Constans.DNA_CHECK_RESULT_WU_CODE.equals(partyTestQueryDto.getDnaCheckGoalToString())) {
            partyTestQueryDto.setDnaCheckResult(Constans.DNA_CHECK_RESULT_WU);
            partyTestQueryDto.setDnaCheckGoal(null);
            partyTestQueryDto.setDnaCheckFilepath(null);
            partyTestQueryDto.setDnaCheckQuantum(null);
        } else {//有结果
            double dnaCheckGoal;
            //将分数转化成Int类型
            try {
                dnaCheckGoal = Double.parseDouble(partyTestQueryDto.getDnaCheckGoalToString());
            } catch (Exception e) {
                log.info(" dnaResult checkGoal String to int is error" + e.toString());
                throw new ItSysException(GlobalErrorCode.DNA_FENSHU_ERROR_CODE, GlobalErrorCode.DNA_FENSHU_ERROR_CODE_MSG);
            }
            partyTestQueryDto.setDnaCheckGoal(dnaCheckGoal);
            //判断是否为空
            if (StringUtils.isEmpty(partyTestQueryDto.getDnaCheckFilepath()) || null == partyTestQueryDto.getDnaCheckQuantum() || null == partyTestQueryDto.getDnaCheckGoal()) {
                log.info(" dnaResult checkFilepath|dnaCheckQuantum|dnaCheckGoal is  null");
                throw new ItSysException(GlobalErrorCode.DNA_PARAM_ISNO_CODE, GlobalErrorCode.DNA_PARAM_ISNO_CODE_MSG);
            }

            //判断分值范围
            if (partyTestQueryDto.getDnaCheckGoal() < 0 || partyTestQueryDto.getDnaCheckGoal() > 999) {
                log.info(" dnaResult dna_check_result   checkGoal is  error");
                throw new ItSysException(GlobalErrorCode.DNA_RESULT_GOAL_ISERROR_CODE, GlobalErrorCode.DNA_RESULT_GOAL_ISERROR_CODE_MSG);
            }
            //dnaCheckGoal 0-999 >=165 dna_check_result 2 <165 dna_check_result 1     0-9999
            if (partyTestQueryDto.getDnaCheckGoal() >= 165) {
                partyTestQueryDto.setDnaCheckResult(Constans.DNA_CHECK_RESULT_YANG);
            } else if (partyTestQueryDto.getDnaCheckGoal() < 165) {
                partyTestQueryDto.setDnaCheckResult(Constans.DNA_CHECK_RESULT_YING);
            }

            SFTPUtil sftp=null;
            //校验pdf
            try{
                //链接sftp
                String path=ResourceUtils.getURL("classpath:").getPath()+sftppath;
                sftp = new SFTPUtil(sftppathname, sftppathip, sftppathport,path);
                sftp.login();
                sftp.listFiless(partyTestQueryDto.getDnaCheckFilepath());
                sftp.logout();
            }catch (Exception e){
                log.info(" concent sftp is  error"+e.toString());
                throw new ItSysException(GlobalErrorCode.DNA_SFTP_CONCENT_ISERROR_CODE, GlobalErrorCode.DNA_SFTP_CONCENT_ISERROR_CODE_MSG);
            }finally {
                if(sftp!=null){
                    sftp.logout();
                }
            }


        }
        // 查询不到信息 无法更新
        List<StoolDna> list = stoolDnaDao.queryByDNACode(partyTestQueryDto.getDnaCode());
        if (list.size() != 1) {
            log.info("dnaCode 存在异常");
            throw new ItSysException(GlobalErrorCode.DNA_CODE_ISNO_CODE,
                    GlobalErrorCode.DNA_CODE_ISNO_CODE_MSG);
        }

        // 查询到信息但已有结果录入dna_check_enter_status
        if (list.get(0).getDnaCheckEnterStatus() != null && list.get(0).getDnaCheckEnterStatus().equals(Constans.DNA_LURU_STATUS2)) {
            log.info("dnaCode 已经有结果");
            throw new ItSysException(GlobalErrorCode.DNA_IS_HAVE_ERROR_CODE,
                    GlobalErrorCode.DNA_IS_HAVE_ERROR_CODE_MSG);
        }

        partyTestQueryDto.setId(list.get(0).getId());
        partyTestQueryDto.setSid(list.get(0).getSid());
        this.informByDnaCodeAndSid(partyTestQueryDto);
        return list.get(0).getId();

    }

    @Override
    public void addDnaSynLog(DnaSynLogPO synLogPO) {
        stoolDnaDao.addDnaSynLog(synLogPO);
    }

}
