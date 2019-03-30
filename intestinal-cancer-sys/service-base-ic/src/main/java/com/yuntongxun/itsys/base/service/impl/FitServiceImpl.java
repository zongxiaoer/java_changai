package com.yuntongxun.itsys.base.service.impl;

import com.yuntongxun.itsys.base.cache.RedisManager;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.constans.RedisConstant;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.*;
import com.yuntongxun.itsys.base.dao.*;
import com.yuntongxun.itsys.base.po.*;
import com.yuntongxun.itsys.base.po.dto.ExeclData;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
import com.yuntongxun.itsys.base.service.ColonoscopyService;
import com.yuntongxun.itsys.base.service.FitService;
import com.yuntongxun.itsys.base.service.UserService;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
import com.yuntongxun.itsys.base.vo.FitResultSynVo;
import com.yuntongxun.itsys.base.vo.FitResultVo;
import com.yuntongxun.itsys.base.vo.HospitalBiologicalSampleResultVo;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class FitServiceImpl implements FitService {

    private final Logger log = LogManager.getLogger(FitServiceImpl.class);

    @Autowired
    private HiReviewDao hiReviewDao;

    @Autowired
    private BiologSampleDao biologSampleDao;

    @Autowired
    private SendSms sendSms;

    @Autowired
    private FitDao fitDao;
    @Autowired
    private UserService userService;
    @Autowired
    private HtEventDao eventDao;
    @Autowired
    private ColonoscopyService colonoscopyService;
    @Autowired
    private PersonDao personDao;
    @Autowired
    private ColonoscopyDao colonoscopyDao;
    @Autowired
    private ReserveDetailDao reserveDetailDao;

    @Autowired
    private HospitalReferenceRecordDao hospitalReferenceRecordDao;


    @Autowired
    private RedisManager redis;
    // 就诊状态，1：未就诊，2：已就诊
    public static final int EXAMINATION_STATUS_NOT_DO = 1;
    public static final int EXAMINATION_STATUS_DO = 2;

    // 结肠镜预约状态，1：未预约，2：已预约
    public static final int COLONOSCOPY_RESERVE_STATUS_NOT_RESERVED = 1;
    public static final int COLONOSCOPY_RESERVE_STATUS_RESERVED = 2;

    // 结肠镜告知书发放状态，1：未发放，2：已发放
    public static final int COLONOSCOPY_NOTIFICATION_NOT_ISSUE = 1;
    public static final int COLONOSCOPY_NOTIFICATION_ISSUED = 2;

    // 结肠镜告知书发放方式，1：受试者/家属到社区中心自取；2：社区工作人员入户递送；3：邻居从社区中心捎带取走；4：受试者/家属到医院自取；5：其它，请备注；
    public static final int COLONOSCOPY_NOTIFICATION_ISSUE_MODE_1 = 1;
    public static final int COLONOSCOPY_NOTIFICATION_ISSUE_MODE_2 = 2;
    public static final int COLONOSCOPY_NOTIFICATION_ISSUE_MODE_3 = 3;
    public static final int COLONOSCOPY_NOTIFICATION_ISSUE_MODE_4 = 4;
    public static final int COLONOSCOPY_NOTIFICATION_ISSUE_MODE_5 = 5;

    // 事件状态，1：待办，2：已办
    public static final int EVENT_STATUS_NOT_DO = 1;
    public static final int EVENT_STATUS_DONE = 2;

    // 事件类型1：未完成危险因素调查表，2：未录入FIT编号，3：未录入FIT结果，4：未录入粪便DNA装置编号，5：未预约结肠镜检查，
    // 6：未完成结肠镜检查，7：未发放筛查结果告知书，8：待录入肠镜结果，9：待录入病理结果，10：待录入筛查结果告知书，
    // 11：待接收生物样本
    public static final int EVENT_TYPE_1 = 1;
    public static final int EVENT_TYPE_2 = 2;
    public static final int EVENT_TYPE_3 = 3;
    public static final int EVENT_TYPE_4 = 4;
    public static final int EVENT_TYPE_5 = 5;
    public static final int EVENT_TYPE_6 = 6;
    public static final int EVENT_TYPE_7 = 7;
    public static final int EVENT_TYPE_8 = 8;
    public static final int EVENT_TYPE_9 = 9;
    public static final int EVENT_TYPE_10 = 10;
    public static final int EVENT_TYPE_11 = 11;

    // FIT编号录入状态，1：未录入，2：已录入
    public static final int FIT_CODE_ENTRY_STATUS_NOT_ENTYR = 1;
    public static final int FIT_CODE_ENTRY_STATUS_ENTYR = 2;
    // FIT结果录入状态，1：未录入，2：已录入
    public static final int FIT_RESULT_ENTRY_STATUS_NOT_ENTYR = 1;
    public static final int FIT_RESULT_ENTRY_STATUS_ENTYR = 2;

    // FIT结果状态，1：有结果，2：无结果
    public static final int FIT_RESULT_STATUS_YES = 1;
    public static final int FIT_RESULT_STATUS_NO = 2;

    // FIT结果，1：阴性，2：阳性，3：无效,4:无结果
    public static final int FIT_RESULT_NEGATIVE = 1;
    public static final int FIT_RESULT_POSITIVE = 2;
    public static final int FIT_RESULT_INVALID = 3;
    public static final int FIT_RESULT_NO_RESULT = 4;

    // FIT录入结果状态，1：未录入，2：已录入
    public static final int FIT_RESULT_INSERT_STATUS_NOT_ENTRY = 1;
    public static final int FIT_RESULT_INSERT_STATUS_ENTRY = 2;

    // 分组类型
    public static final String GROUP_TYPE_A = "A";
    public static final String GROUP_TYPE_B = "B";
    public static final String GROUP_TYPE_C = "C";

    // 风险等级 1:低位，2：高危
    public static final Integer RISK_LEVEL_LOW = 1;
    public static final Integer RISK_LEVEL_HIGH = 2;

    public static final Integer STAGE_T0 = 1;
    public static final Integer STAGE_T1 = 2;
    public static final Integer STAGE_T2 = 3;
    public static final Integer STAGE_T3 = 4;

    @Override
    public ListPageUtil query(FitResultVo queryCondition, String loginName) {
        // 查询登录用户信息
        DoctorInfo doctorInfo = getDoctorInfo(loginName);

        // 分页查询结肠镜检查数据
        ListPageUtil listPage = fitDao.query(queryCondition, doctorInfo.getCommunityDeptId(),
                doctorInfo.getAreaDeptId(), doctorInfo.getHospitalType(), true);
        return listPage;
    }

    private DoctorInfo getDoctorInfo(String loginName) {
        // 获取当前用户信息
        String key = RedisConstant.HOSPITAL_KEY_INFO + loginName;
        String value = redis.get(key);
        DoctorInfo doctorInfo = null;
        if (StringUtil.isNotBlank(value)) {
            doctorInfo = JSONUtils.toBean(value, DoctorInfo.class);
        }
        if (doctorInfo == null) {
            doctorInfo = userService.getHospitalInfo(loginName);
        }
        if (doctorInfo == null) {
            throw new ItSysException(GlobalErrorCode.ERR_USER_INFO_INCOMPLETE_ERROR_CODE,
                    GlobalErrorCode.ERR_USER_INFO_INCOMPLETE_ERROR_MSG);
        }
        return doctorInfo;
    }

    @Override
    @Transactional
    public void entryCode(FitResultVo vo, String loginName) {
        // 更新fit结果表字段
        vo.setCodeEntryStatus(FIT_CODE_ENTRY_STATUS_ENTYR);
        fitDao.updateFitCode(vo);

        // 完成 未录入fit编号 待办；
        eventDao.updateStatus(vo.getSid(), vo.getId(), EVENT_TYPE_2, EVENT_STATUS_DONE);

        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);

        HtEvent event = new HtEvent();
        event.setAreaDeptId(doctorInfo.getAreaDeptId());
        event.setCommunityDeptId(doctorInfo.getCommunityDeptId());
        event.setDataId(vo.getId());
        event.setSid(vo.getSid());
        event.setStatus(EVENT_STATUS_NOT_DO);
        event.setType(EVENT_TYPE_3);

        // 生成未录入待办
        eventDao.insert(event);

    }

    @Override
    @Transactional
    public Integer synResult(FitResultSynVo vo) {
        // TODO 要记录同步日志
        // 非空验证
        if (vo.getSid() == null || "".equals(vo.getSid())) {
            log.info("synResult sid is null");
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }
        if (vo.getDownLineValue() == null) {
            log.info("synResult downLineValue is null");
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }
        if (vo.getUpLineValue() == null) {
            log.info("synResult upLineValue is null");
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }
        if (vo.getInTenMin() == null) {
            log.info("synResult inTenMin is null");
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }
        if (vo.getResultDateForSql() == null || "".equals(vo.getResultDateForSql())) {
            log.info("synResult resultDateForSql is null");
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }
        if (vo.getTimestamp() == null || "".equals(vo.getTimestamp())) {
            log.info("synResult timestamp is null");
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);

        } else {
            long timestamp = 0;
            try {
                timestamp = Long.parseLong(vo.getTimestamp());
            } catch (NumberFormatException e) {
                log.info("synResult timestamp is error");
                throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
            }
            // if ((System.currentTimeMillis() - timestamp)>
            // TimeUnit.MILLISECONDS.convert(60, TimeUnit.SECONDS)){
            // log.info("synResult timestamp is expire");
            // throw new
            // ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE,GlobalErrorCode.PARAMETER_ERR_MSG);
            // }
        }
        if (vo.getSign() == null || "".equals(vo.getSign())) {
            log.info("synResult sign is null");
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        } else {
            if (!vo.getSign().toUpperCase().equals(EncryptUtil.md5(vo.getSid() + vo.getTimestamp() + "synresult"))) {
                log.info("synResult sign!=md5(sid+timestamp+synresult) ");
                throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
            }
        }

        // 查询不到信息 无法更新
        // add by sunhang 20180628 begin 查询sid是否存在
        String sid = vo.getSid();
        List hospitalReviews = personDao.getHospitalReviewBySid(sid);
        if (hospitalReviews == null || hospitalReviews.size() <= 0) {
            log.info("该sid不存在");
            throw new ItSysException(GlobalErrorCode.ERR_HOSPITAL_STOOL_VIEW_ERROR_CODE + "",
                    GlobalErrorCode.ERR_HOSPITAL_STOOL_VIEW_ERROR_MSG);
        }
        // add by sunhang 20180628 end 查询sid是否存在
        FitResultVo fitResultVo = fitDao.queryLatestFitInfoForSynResult(vo.getSid());
        if (fitResultVo == null) {
            log.info("synResult fitResultVo is null");
            throw new ItSysException(GlobalErrorCode.OBJECT_NOT_EXISTS + "", GlobalErrorCode.OBJECT_NOT_EXISTS_MSG);
        } else {
            // 查询到信息但已有结果录入
            if (fitResultVo.getInsertStatus() != null
                    && fitResultVo.getInsertStatus() == FIT_RESULT_ENTRY_STATUS_ENTYR) {
                log.info("synResult fitResult has already existed");
                throw new ItSysException(GlobalErrorCode.ERR_HOSPITAL_STOOL_FITRESULT_ERROR_CODE,
                        GlobalErrorCode.ERR_HOSPITAL_STOOL_FITRESULT_ERROR_MSG);
            } else {
                // 可以录入
                FitResultVo updateFitResult = new FitResultVo();
                // "id": 8,
                // "sid": "TC43002",
                // "resultStatus": "1",
                // "resultDate": "2018-05-03",
                // "upLineValue": "8",
                // "downLineValue": "8",
                // "noResonResult": "",
                // "inTenMin": "1"
                updateFitResult.setId(fitResultVo.getId());
                updateFitResult.setSid(vo.getSid());
                updateFitResult.setResultStatus(1);
                updateFitResult.setUpLineValue(vo.getUpLineValue());
                updateFitResult.setDownLineValue(vo.getDownLineValue());
                updateFitResult.setInTenMin(vo.getInTenMin() + "");
                updateFitResult.setEditoperationSource(fitResultVo.getEditoperationSource());
                updateFitResult.setOperationSourceId(fitResultVo.getId());
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date resultDate = df.parse(vo.getResultDateForSql());
                    updateFitResult.setResultDateForSql(resultDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                    log.info("synResult resultDateForSql convert error");
                    throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
                }
                this.entryResult(updateFitResult);
                return fitResultVo.getId();
            }
        }
    }

    @Override
    @Transactional
    public FitResultVo entryResult(FitResultVo vo) {

        if ("0".equals(vo.getInTenMin())) {
            vo.setResultStatus(2);
        }
        if (FIT_RESULT_STATUS_YES == vo.getResultStatus()) {
            // 有结果，判断结果是阴性，阳性，无效；
            int result = 0;
            if (vo.getUpLineValue() != null && vo.getUpLineValue() < 7) {
                log.info("Entry FIT result,upline:{},<7,so result is invalid.", vo.getUpLineValue());
                // 无效
                result = FIT_RESULT_INVALID;
            } else if (vo.getUpLineValue() != null && vo.getDownLineValue() != null && vo.getUpLineValue() >= 7
                    && vo.getDownLineValue() >= 4) {
                log.info("Entry FIT result,upline:{}>=7,downline:{}>=4,,so result is positive(+).");
                // 阳性
                result = FIT_RESULT_POSITIVE;
                HospitalReview hospitalReview = personDao.getBySid(vo.getSid());
                log.info("Persion group:{},risk level:{}", hospitalReview.getGroup(), hospitalReview.getRiskLevel());
                // 判断group，若B组，或C组低危，生成未预约肠镜待办，未录入粪便DNA编号待办
                if (GROUP_TYPE_B.equals(hospitalReview.getGroup()) || (GROUP_TYPE_C.equals(hospitalReview.getGroup())
                        && hospitalReview.getRiskLevel() == RISK_LEVEL_LOW)) {
                    // DoctorInfo doctorInfo =
                    // colonoscopyService.getDoctorInfo(loginName);
                    // 生成结肠镜检查记录；
                    HospitalColonoscopyRecord record = new HospitalColonoscopyRecord();
                    record.setSid(vo.getSid());
                    record.setStage(hospitalReview.getStageCy());
                    record.setArea_dept_id(hospitalReview.getAreaDeptId());
                    record.setCommunity_dept_id(hospitalReview.getCommunityDeptId());
                    record.setReserve_status(COLONOSCOPY_RESERVE_STATUS_NOT_RESERVED);
                    record.setSource_type(1);
                    record.setOperationSourceId(vo.getId());
                    record.setOperationSource( vo.getEditoperationSource());


                    int colonoscopyId = colonoscopyDao.add(record);
                    log.info("Insert colonoscopy record,id:{},body:{}", colonoscopyId, JSONUtils.toJson(record));

                    // 生成 未预约结肠镜检查待办；
                    HtEvent event1 = new HtEvent();
                    event1.setAreaDeptId(hospitalReview.getAreaDeptId());
                    event1.setCommunityDeptId(hospitalReview.getCommunityDeptId());
                    event1.setDataId(colonoscopyId);
                    event1.setSid(vo.getSid());
                    event1.setStatus(EVENT_STATUS_NOT_DO);
                    event1.setType(EVENT_TYPE_5);
                    event1.setOperationSourceId(vo.getId());
                    event1.setOperationSource( vo.getEditoperationSource());
                    log.info("Insert [not reserve colonoscopy todo event],body:{}", JSONUtils.toJson(event1));
                    eventDao.insert(event1);

                    // 生成 粪便DNA结果记录
                    StoolDna stoolDna = new StoolDna();
                    stoolDna.setSid(vo.getSid());
                    stoolDna.setStage(hospitalReview.getStageCy());
                    stoolDna.setAreaDeptId(hospitalReview.getAreaDeptId());
                    stoolDna.setCommunityDeptId(hospitalReview.getCommunityDeptId());
                    stoolDna.setOperationSource(Constans.OPERATION_SOURCE_TYPE_BY_SYSTEM);
                    stoolDna.setOperationSourceId(vo.getId());
                    stoolDna.setEditoperationSource( vo.getEditoperationSource());
                    int dnaId = personDao.addStoolDna(stoolDna);
                    log.info("Insert dna record,id:{},body:{}", dnaId, JSONUtils.toJson(stoolDna));

                    // 生成 未录入粪便DNA装置编号 待办
                    HtEvent event2 = new HtEvent();
                    event2.setAreaDeptId(hospitalReview.getAreaDeptId());
                    event2.setCommunityDeptId(hospitalReview.getCommunityDeptId());
                    event2.setDataId(dnaId);
                    event2.setSid(vo.getSid());
                    event2.setStatus(EVENT_STATUS_NOT_DO);
                    event2.setType(EVENT_TYPE_4);
                    event2.setOperationSourceId(vo.getId());
                    event2.setOperationSource( vo.getEditoperationSource());
                    eventDao.insert(event2);
                    log.info("Insert [not entry dna code todo event],body:{}", JSONUtils.toJson(event2));

                    // 生成生物样本
                    HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO = new HospitalBiologicalSampleResultVo();
                    String associatedSampleId = UUID.randomUUID().toString().replace("-", "");

                    // 新增一条生物样本粪便以及代办
                    hospitalBiologicalSampleResultPO.setSid(vo.getSid());
                    hospitalBiologicalSampleResultPO.setCommunityDeptId(hospitalReview.getCommunityDeptId());
                    hospitalBiologicalSampleResultPO.setAreaDeptId(hospitalReview.getAreaDeptId());
                    hospitalBiologicalSampleResultPO.setStage(Constans.PERSON_COLONOSCOPY_STATUS1);
                    hospitalBiologicalSampleResultPO.setOperationSource(Constans.OPERATION_SOURCE_TYPE_BY_SYSTEM);
                    hospitalBiologicalSampleResultPO.setAssociatedSampleId(associatedSampleId);
                    hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE5);
                    hospitalBiologicalSampleResultPO.setOperationSourceId(vo.getId());
                    hospitalBiologicalSampleResultPO.setEditoperationSource( vo.getEditoperationSource());

                    log.info("@Service-person-personInsert 新增生物样本数据粪便  参数：dna={}.",
                            hospitalBiologicalSampleResultPO.toString());
                    int sampleid = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO,
                            Constans.BIOLOGICAL_SAMPLE_TABLE);

                    HtEvent event3 = new HtEvent();
                    event3.setAreaDeptId(hospitalReview.getAreaDeptId());
                    event3.setCommunityDeptId(hospitalReview.getCommunityDeptId());
                    event3.setDataId(dnaId);
                    event3.setSid(vo.getSid());
                    event3.setStatus(EVENT_STATUS_NOT_DO);
                    event3.setType(Constans.PERSON_TODO_EVENT_TYPE12);
                    event3.setDataId(sampleid);
                    event3.setOperationSourceId(vo.getId());
                    event3.setOperationSource( vo.getEditoperationSource());
                    log.info("@Service-person-personInsert 新增一条未录入生物样本数据粪便待办事件  参数：todo={}.",
                            hospitalBiologicalSampleResultPO.toString());
                    eventDao.insert(event3);

                    // 新增一条生物样本唾液以及代办
                    hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE4);

                    log.info("@Service-person-personInsert 新增生物样本数据粪便  参数：dna={}.",
                            hospitalBiologicalSampleResultPO.toString());
                    int sampleidByM = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO,
                            Constans.BIOLOGICAL_SAMPLE_TABLE);
                    event3.setType(Constans.PERSON_TODO_EVENT_TYPE13);
                    event3.setDataId(sampleidByM);
                    log.info("@Service-person-personInsert 新增一条未录入生物样本数据唾液待办事件  参数：todo={}.",
                            hospitalBiologicalSampleResultPO.toString());
                    eventDao.insert(event3);

                    // 新增血液样本
                    hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE6);

                    log.info("@Service-person-personInsert 新增新增血液样本 参数：dna={}.",
                            hospitalBiologicalSampleResultPO.toString());
                    int sampleidByA = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO,
                            Constans.BIOLOGICAL_SAMPLE_TABLE);
                    event3.setType(Constans.PERSON_TODO_EVENT_TYPE18);
                    event3.setDataId(sampleidByA);
                    log.info("@Service-person-personInsert 新增一条未录入新增血液样本待办事件  参数：todo={}.",
                            hospitalBiologicalSampleResultPO.toString());
                    eventDao.insert(event3);

                    // 新增一条生物样本白细胞以及代办
                    hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE3);
                    hospitalBiologicalSampleResultPO.setBloodSampleId(sampleidByA);
                    log.info("@Service-person-personInsert 新增生物白细胞  参数：dna={}.",
                            hospitalBiologicalSampleResultPO.toString());
                    int sampleidByW = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO,
                            Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE);
                    /*
                     * event3.setType(Constans.PERSON_TODO_EVENT_TYPE15);
                     * event3.setDataId(sampleidByW); log.info(
                     * "@Service-person-personInsert 新增一条未录入生物样本数据白细胞待办事件  参数：todo={}."
                     * , hospitalBiologicalSampleResultPO.toString());
                     * eventDao.insert(event3);
                     */

                    // 新增一条生物样本血清以及代办
                    hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE1);

                    log.info("@Service-person-personInsert 新增生物样本数据血清  参数：dna={}.",
                            hospitalBiologicalSampleResultPO.toString());
                    int sampleidByS = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO,
                            Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE);
                    /*
                     * event3.setType(Constans.PERSON_TODO_EVENT_TYPE11);
                     * event3.setDataId(sampleidByS); log.info(
                     * "@Service-person-personInsert 新增一条未录入生物样本数据粪便待办事件  参数：todo={}."
                     * , hospitalBiologicalSampleResultPO.toString());
                     * eventDao.insert(event3);
                     */

                    // 新增一条生物样本血浆以及代办
                    hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE2);
                    log.info("@Service-person-personInsert 新增生物样本数据血浆  参数：dna={}.",
                            hospitalBiologicalSampleResultPO.toString());
                    int sampleidByP = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO,
                            Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE);
                    /*
                     * event3.setType(Constans.PERSON_TODO_EVENT_TYPE14);
                     * event3.setDataId(sampleidByP); log.info(
                     * "@Service-person-personInsert 新增一条未录入生物样本数据粪便待办事件  参数：todo={}."
                     * , hospitalBiologicalSampleResultPO.toString());
                     * eventDao.insert(event3);
                     */

                }

            } else if (vo.getUpLineValue() != null && vo.getDownLineValue() != null && vo.getUpLineValue() >= 7
                    && vo.getDownLineValue() < 4) {
                log.info("Entry FIT result,upline:{}>=7,downline:{}<4,,so result is negative(-).");
                // 阴性
                result = FIT_RESULT_NEGATIVE;
            }
            vo.setResult(result);
        } else {
            // 无结果
            vo.setResult(FIT_RESULT_NO_RESULT);
        }

        // 目前缺少审核步骤
        if ("0".equals(vo.getInTenMin())) {
            vo.setResult(3);
            vo.setResultStatus(1);
        }
        vo.setInsertStatus(FIT_RESULT_INSERT_STATUS_ENTRY);
        // 更新fit结果数据
        fitDao.updateFitResult(vo);
        log.info("Update fit result,body:{}", JSONUtils.toJson(vo));

        // 完成待办
        eventDao.updateStatus(vo.getSid(), vo.getId(), EVENT_TYPE_3, EVENT_STATUS_DONE);
        log.info("Finish [not entry fit result todo event] ,sid:{},id:{},eventType:{},eventStatus:{}", vo.getSid(),
                vo.getId(), EVENT_TYPE_3, EVENT_STATUS_DONE);
        return vo;
    }

    @Override
    public ListPageUtil queryArea(FitResultVo queryCondition, String loginName) {
        // 查询登录用户信息
        DoctorInfo doctorInfo = getDoctorInfo(loginName);

        // 分页查询结肠镜检查数据
        ListPageUtil listPage = fitDao.queryArea(queryCondition, queryCondition.getCommunityDeptId(),
                doctorInfo.getAreaDeptId(), doctorInfo.getHospitalType(), true);
        return listPage;
    }

    @Override
    public ListPageUtil queryCountry(FitResultVo queryCondition) {
        // 分页查询结肠镜检查数据
        ListPageUtil listPage = fitDao.queryCountry(queryCondition, true);
        return listPage;
    }

    @Override
    public Integer saveFit(FitResultVo fitResultVo) {
        try {
            return fitDao.addFit(fitResultVo);
        } catch (Exception e) {
            log.info("saveFit------------>error", e.toString());
        }
        return 0;
    }

    @Override
    public FitResultVo queryByFit_code(String fit_code) {
        return fitDao.queryByFit_code(fit_code);
    }

    @Override
    public FitResultVo queryById(Integer id) {
        return fitDao.queryById(id);
    }

    @Override
    public boolean sendFit(FitResultVo fitResultVo) {
        String[] parm = new String[1];
        parm[0] = "先生|女士";// 自己生成的四位随机数
        String templateId = "";
        // 1：阴性，2：阳性，3：无效
        if (fitResultVo.getResult() == 1) {
            templateId = "246926";
        } else if (fitResultVo.getResult() == 2) {
            templateId = "246928";
        } else if (fitResultVo.getResult() == 3) {
            templateId = "246931";
        }
        if (StringUtils.isEmpty(templateId)) {
            return false;
        }
        return sendSms.getAuthCode(fitResultVo.getPhone(), templateId, parm);
    }

    /**
     * 获取用户某阶段最新的fit信息
     *
     * @param sid
     * @param loginName
     * @return
     */
    @Override
    public FitResultVo queryLatestFitInfo(String sid, String loginName) {
        // 获取登录人信息
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        // 获取受试者年度阶段
        HospitalReview hospitalReview = personDao.getBySid(sid);
        return fitDao.queryLatestFitInfo(sid, hospitalReview.getStageCy());
    }

    /**
     * 第三方同步fit结果记录
     *
     * @param fitSynLogPO
     */
    @Override
    public void addFitSynLog(FitSynLogPO fitSynLogPO) {
        fitDao.addFitSynLog(fitSynLogPO);
    }

    public static void main(String[] args) {
        // String newSign = EncryptUtil.md5("TC00021201805251651synresult");
        // String sing = "91e4a95fff5394dea487f686bd8280b4";
        // System.out.println(newSign);
        // System.out.println(sing);
        // System.out.println(sing.length());
        System.out.println(TimeUnit.MILLISECONDS.convert(60, TimeUnit.SECONDS));
    }

    @Override
    @Transactional
    public void areaEntryCode(FitResultVo vo, String loginName) {
        // TODO Auto-generated method stub
        // 更新fit结果表字段
        vo.setCodeEntryStatus(FIT_CODE_ENTRY_STATUS_ENTYR);
        fitDao.updateFitCode(vo);
        // 完成 未录入fit编号 待办；
        eventDao.updateStatus(vo.getSid(), vo.getId(), EVENT_TYPE_2, EVENT_STATUS_DONE);
        HtEvent event = new HtEvent();
        HospitalReview hospitalReview = personDao.getBySid(vo.getSid());
        event.setCommunityDeptId(hospitalReview.getCommunityDeptId());
        event.setAreaDeptId(hospitalReview.getAreaDeptId());
        event.setDataId(vo.getId());
        event.setSid(vo.getSid());
        event.setStatus(EVENT_STATUS_NOT_DO);
        event.setType(EVENT_TYPE_3);
        // 生成未录入待办
        eventDao.insert(event);
    }

    @Override
    @Transactional
    public void updateFitCode(FitResultVo vo, HospitalReferenceRecordDto hospitalReferenceRecordDto, boolean isArea) {
        try {
            // 更新fit结果表字段
            vo.setCodeEntryStatus(FIT_CODE_ENTRY_STATUS_ENTYR);
            fitDao.updateFitCode(vo);
            if (isArea) {
                hospitalReferenceRecordDao.saveArea(hospitalReferenceRecordDto);
            } else {
                String table = Constans.HOSPITAL_FIT_RESULT;
                personDao.updateEdirStatus(Constans.APPLY_EDIT_STATUS1, Constans.EDIT_STATUS1, null, vo.getId(), table);
                hospitalReferenceRecordDao.updateForEdir(hospitalReferenceRecordDto);
            }

        } catch (Exception e) {
            throw new ItSysException(GlobalErrorCode.HOSPITAL_MESSAGE_CENTER_UPDATE_ERROR_CODE, GlobalErrorCode.HOSPITAL_MESSAGE_CENTER_UPDATE_ERROR_MSG);
        }
    }

    @Override
    @Transactional
    public FitResultVo updateFitResult(FitResultVo fitResultVo, FitResultVo vo, HospitalReferenceRecordDto hospitalReferenceRecordDto, boolean isArea) {
        Integer oldStatus = fitResultVo.getResultStatus();
        Integer newStatus = vo.getResultStatus();
        if ("0".equals(vo.getInTenMin())) {
            vo.setResultStatus(2);
        }
        //无结果转有结果
        if (Constans.FIT_RESULT_STATUS2.equals(oldStatus) && Constans.FIT_RESULT_STATUS1.equals(newStatus)) {
            if (FIT_RESULT_STATUS_YES == vo.getResultStatus()) {
                // 有结果，判断结果是阴性，阳性，无效；
                int result = 0;
                if (vo.getUpLineValue() != null && vo.getUpLineValue() < 7) {
                    log.info("Entry FIT result,upline:{},<7,so result is invalid.", vo.getUpLineValue());
                    // 无效
                    result = FIT_RESULT_INVALID;
                } else if (vo.getUpLineValue() != null && vo.getDownLineValue() != null && vo.getUpLineValue() >= 7
                        && vo.getDownLineValue() >= 4) {
                    log.info("Entry FIT result,upline:{}>=7,downline:{}>=4,,so result is positive(+).");
                    // 阳性
                    result = FIT_RESULT_POSITIVE;
                    HospitalReview hospitalReview = personDao.getBySid(vo.getSid());
                    log.info("Persion group:{},risk level:{}", hospitalReview.getGroup(), hospitalReview.getRiskLevel());
                    // 判断group，若B组，或C组低危，生成未预约肠镜待办，未录入粪便DNA编号待办
                    if (GROUP_TYPE_B.equals(hospitalReview.getGroup()) || (GROUP_TYPE_C.equals(hospitalReview.getGroup())
                            && hospitalReview.getRiskLevel() == RISK_LEVEL_LOW)) {
                        // DoctorInfo doctorInfo =
                        // colonoscopyService.getDoctorInfo(loginName);
                        // 生成结肠镜检查记录；
                        List<HospitalColonoscopyRecord> hospitalColonoscopyRecords = personDao.queryRecordByIdAndOperation(fitResultVo.getId(), vo.getEditoperationSource());
                        if (hospitalColonoscopyRecords.size() == 0) {
                            HospitalColonoscopyRecord record = new HospitalColonoscopyRecord();
                            record.setSid(vo.getSid());
                            record.setStage(hospitalReview.getStageCy());
                            record.setArea_dept_id(hospitalReview.getAreaDeptId());
                            record.setCommunity_dept_id(hospitalReview.getCommunityDeptId());
                            record.setReserve_status(COLONOSCOPY_RESERVE_STATUS_NOT_RESERVED);
                            record.setSource_type(1);
                            record.setOperationSourceId(vo.getId());
                            record.setOperationSource(vo.getEditoperationSource());
                            int colonoscopyId = colonoscopyDao.add(record);
                            log.info("Insert colonoscopy record,id:{},body:{}", colonoscopyId, JSONUtils.toJson(record));
                            // 生成 未预约结肠镜检查待办；
                            HtEvent event1 = new HtEvent();
                            event1.setAreaDeptId(hospitalReview.getAreaDeptId());
                            event1.setCommunityDeptId(hospitalReview.getCommunityDeptId());
                            event1.setDataId(colonoscopyId);
                            event1.setSid(vo.getSid());
                            event1.setStatus(EVENT_STATUS_NOT_DO);
                            event1.setType(EVENT_TYPE_5);
                            event1.setOperationSourceId(vo.getId());
                            event1.setOperationSource(vo.getEditoperationSource());
                            log.info("Insert [not reserve colonoscopy todo event],body:{}", JSONUtils.toJson(event1));
                            eventDao.insert(event1);
                        }
                        List<StoolDna> stoolDnas = personDao.queryDNAByIdAndOperation(fitResultVo.getId(),  vo.getEditoperationSource());
                        if (stoolDnas.size() == 0) {
                            // 生成 粪便DNA结果记录
                            StoolDna stoolDna = new StoolDna();
                            stoolDna.setSid(vo.getSid());
                            stoolDna.setStage(hospitalReview.getStageCy());
                            stoolDna.setAreaDeptId(hospitalReview.getAreaDeptId());
                            stoolDna.setCommunityDeptId(hospitalReview.getCommunityDeptId());
                            stoolDna.setOperationSource(Constans.OPERATION_SOURCE_TYPE_BY_SYSTEM);
                            stoolDna.setOperationSourceId(vo.getId());
                            stoolDna.setEditoperationSource(vo.getEditoperationSource());
                            int dnaId = personDao.addStoolDna(stoolDna);
                            log.info("Insert dna record,id:{},body:{}", dnaId, JSONUtils.toJson(stoolDna));

                            // 生成 未录入粪便DNA装置编号 待办
                            HtEvent event2 = new HtEvent();
                            event2.setAreaDeptId(hospitalReview.getAreaDeptId());
                            event2.setCommunityDeptId(hospitalReview.getCommunityDeptId());
                            event2.setDataId(dnaId);
                            event2.setSid(vo.getSid());
                            event2.setStatus(EVENT_STATUS_NOT_DO);
                            event2.setType(EVENT_TYPE_4);
                            event2.setOperationSourceId(vo.getId());
                            event2.setOperationSource( vo.getEditoperationSource());
                            eventDao.insert(event2);
                            log.info("Insert [not entry dna code todo event],body:{}", JSONUtils.toJson(event2));
                        }
                        List<HospitalBiologicalSampleResultVo> samples = personDao.querySampleByIdAndOperation(fitResultVo.getId(),  vo.getEditoperationSource());
                        if (samples.size() == 0) {
                            // 生成生物样本
                            HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO = new HospitalBiologicalSampleResultVo();
                            String associatedSampleId = UUID.randomUUID().toString().replace("-", "");

                            // 新增一条生物样本粪便以及代办
                            hospitalBiologicalSampleResultPO.setSid(vo.getSid());
                            hospitalBiologicalSampleResultPO.setCommunityDeptId(hospitalReview.getCommunityDeptId());
                            hospitalBiologicalSampleResultPO.setAreaDeptId(hospitalReview.getAreaDeptId());
                            hospitalBiologicalSampleResultPO.setStage(Constans.PERSON_COLONOSCOPY_STATUS1);
                            hospitalBiologicalSampleResultPO.setOperationSource(Constans.OPERATION_SOURCE_TYPE_BY_SYSTEM);
                            hospitalBiologicalSampleResultPO.setAssociatedSampleId(associatedSampleId);
                            hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE5);
                            hospitalBiologicalSampleResultPO.setOperationSourceId(vo.getId());
                            hospitalBiologicalSampleResultPO.setEditoperationSource( vo.getEditoperationSource());

                            log.info("@Service-person-personInsert 新增生物样本数据粪便  参数：dna={}.",
                                    hospitalBiologicalSampleResultPO.toString());
                            int sampleid = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO,
                                    Constans.BIOLOGICAL_SAMPLE_TABLE);

                            HtEvent event3 = new HtEvent();
                            event3.setAreaDeptId(hospitalReview.getAreaDeptId());
                            event3.setCommunityDeptId(hospitalReview.getCommunityDeptId());
                            event3.setDataId(sampleid);
                            event3.setSid(vo.getSid());
                            event3.setStatus(EVENT_STATUS_NOT_DO);
                            event3.setType(Constans.PERSON_TODO_EVENT_TYPE12);
                            event3.setDataId(sampleid);
                            event3.setOperationSourceId(vo.getId());
                            event3.setOperationSource( vo.getEditoperationSource());
                            log.info("@Service-person-personInsert 新增一条未录入生物样本数据粪便待办事件  参数：todo={}.",
                                    hospitalBiologicalSampleResultPO.toString());
                            eventDao.insert(event3);

                            // 新增一条生物样本唾液以及代办
                            hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE4);

                            log.info("@Service-person-personInsert 新增生物样本数据粪便  参数：dna={}.",
                                    hospitalBiologicalSampleResultPO.toString());
                            int sampleidByM = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO,
                                    Constans.BIOLOGICAL_SAMPLE_TABLE);
                            event3.setType(Constans.PERSON_TODO_EVENT_TYPE13);
                            event3.setDataId(sampleidByM);
                            log.info("@Service-person-personInsert 新增一条未录入生物样本数据唾液待办事件  参数：todo={}.",
                                    hospitalBiologicalSampleResultPO.toString());
                            eventDao.insert(event3);

                            // 新增血液样本
                            hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE6);

                            log.info("@Service-person-personInsert 新增新增血液样本 参数：dna={}.",
                                    hospitalBiologicalSampleResultPO.toString());
                            int sampleidByA = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO,
                                    Constans.BIOLOGICAL_SAMPLE_TABLE);
                            event3.setType(Constans.PERSON_TODO_EVENT_TYPE18);
                            event3.setDataId(sampleidByA);
                            log.info("@Service-person-personInsert 新增一条未录入新增血液样本待办事件  参数：todo={}.",
                                    hospitalBiologicalSampleResultPO.toString());
                            eventDao.insert(event3);

                            // 新增一条生物样本白细胞以及代办
                            hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE3);
                            hospitalBiologicalSampleResultPO.setBloodSampleId(sampleidByA);
                            log.info("@Service-person-personInsert 新增生物白细胞  参数：dna={}.",
                                    hospitalBiologicalSampleResultPO.toString());
                            int sampleidByW = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO,
                                    Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE);

                            // 新增一条生物样本血清以及代办
                            hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE1);

                            log.info("@Service-person-personInsert 新增生物样本数据血清  参数：dna={}.",
                                    hospitalBiologicalSampleResultPO.toString());
                            int sampleidByS = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO,
                                    Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE);


                            // 新增一条生物样本血浆以及代办
                            hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE2);
                            log.info("@Service-person-personInsert 新增生物样本数据血浆  参数：dna={}.",
                                    hospitalBiologicalSampleResultPO.toString());
                            int sampleidByP = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO,
                                    Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE);
                        }
                    }

                } else if (vo.getUpLineValue() != null && vo.getDownLineValue() != null && vo.getUpLineValue() >= 7
                        && vo.getDownLineValue() < 4) {
                    log.info("Entry FIT result,upline:{}>=7,downline:{}<4,,so result is negative(-).");
                    // 阴性
                    result = FIT_RESULT_NEGATIVE;
                }
                vo.setResult(result);
            } else {
                // 无结果
                vo.setResult(FIT_RESULT_NO_RESULT);
            }

            // 目前缺少审核步骤
            if ("0".equals(vo.getInTenMin())) {
                vo.setResult(3);
                vo.setResultStatus(1);
            }
        }
        //有结果变成无结果
        if (Constans.FIT_RESULT_STATUS1.equals(oldStatus) && Constans.FIT_RESULT_STATUS2.equals(newStatus)) {
            //判断是否是阳性
            if (FIT_RESULT_POSITIVE == fitResultVo.getResult()) {//是
                //查询生物样本
                //查看生物样本是否删除
                List<HospitalBiologicalSampleResultVo> samples = personDao.querySampleByIdAndOperation(fitResultVo.getId(), vo.getEditoperationSource());
                boolean idok = true;
                for (HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo : samples) {
                    if (Constans.COLLECT_STATUS_NO!=hospitalBiologicalSampleResultVo.getCollectStatus()) {
                        idok = false;
                    }
                }
                //删除生物样本数据+代办
                if (idok) {
                    //删除代办
                    int i = personDao.deleteEventBySourceIdAndType(fitResultVo.getId(),  vo.getEditoperationSource(), Constans.PERSON_TODO_EVENT_TYPE12);
                    if (i < 1) {
                        throw new ItSysException(
                                GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG);
                    }
                    int i1 = personDao.deleteEventBySourceIdAndType(fitResultVo.getId(),  vo.getEditoperationSource(), Constans.PERSON_TODO_EVENT_TYPE13);
                    if (i1 < 1) {
                        throw new ItSysException(
                                GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG);
                    }
                    int i2 = personDao.deleteEventBySourceIdAndType(fitResultVo.getId(),  vo.getEditoperationSource(), Constans.PERSON_TODO_EVENT_TYPE18);
                    if (i2 < 1) {
                        throw new ItSysException(
                                GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG);
                    }
                    //删除生物数据
                    int j = personDao.deleeBySourceId(fitResultVo.getId(), vo.getEditoperationSource(), Constans.HOSPITAL_BIOLOGICAL_SAMPLE_RESULT);
                    if (j < 1) {
                        throw new ItSysException(
                                GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG);
                    }
                    //删除血液数据
                    int j1 = personDao.deleeBySourceId(fitResultVo.getId(),  vo.getEditoperationSource(), Constans.HOSPITAL_BIOLOGICAL_BLOOD_SAMPLE_RESULT);
                    if (j1 < 1) {
                        throw new ItSysException(
                                GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG);
                    }

                }
                //判断是否检查
                List<HospitalColonoscopyRecord> hospitalColonoscopyRecords = personDao.queryRecordByIdAndOperation(fitResultVo.getId(),  vo.getEditoperationSource());
                if (hospitalColonoscopyRecords.get(0).getExamination_status() == null) {
                    //判断是否预约
                    if(hospitalColonoscopyRecords.get(0).getReserve_status()!=null&&hospitalColonoscopyRecords.get(0).getReserve_status()==2){
                        //删除放号信息
                        if(hospitalColonoscopyRecords.get(0).getAllocation_id()!=null){
                            reserveDetailDao.delReserveDetail(Integer.parseInt(hospitalColonoscopyRecords.get(0).getAllocation_id()),hospitalColonoscopyRecords.get(0).getSid());
                        }
                        //删除录入结果代办
                        int i= personDao.deleteEventBySourceIdAndType(hospitalColonoscopyRecords.get(0).getOperationSourceId(),hospitalColonoscopyRecords.get(0).getEditoperationSource(),Constans.PERSON_TODO_EVENT_TYPE8);
                        if(i<1){
                            throw new ItSysException(
                                    GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
                        }
                        //eventDao.updateStatus(hospitalColonoscopyRecords.get(0).getSid(),hospitalColonoscopyRecords.get(0).getId(),Constans.PERSON_TODO_EVENT_TYPE8,Constans.PERSON_TODO_EVENT_STATUS3);

                    }else{
                        //删除预约代办
                        int i= personDao.deleteEventBySourceIdAndType(fitResultVo.getId(),vo.getEditoperationSource(),Constans.PERSON_TODO_EVENT_TYPE5);
                        if(i<1){
                            throw new ItSysException(
                                    GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG);
                        }
                    }
/*                    //删除预约代办

                    int i = personDao.deleteEventBySourceIdAndType(fitResultVo.getId(),  vo.getEditoperationSource(), Constans.PERSON_TODO_EVENT_TYPE5);
                    if (i < 1) {
                        throw new ItSysException(
                                GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
                    }*/
                    //删除预约数据 hospital_colonoscopy_record
                    int j = personDao.deleeBySourceId(fitResultVo.getId(),  vo.getEditoperationSource(), Constans.HOSPITAL_COLONOSCOPY_RECORD);
                    if (j < 1) {
                        throw new ItSysException(
                                GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG);
                    }
                }
                //查看DNA是否删除
                //判断DNA是否有录入结果
                List<StoolDna> stoolDnas = personDao.queryDNAByIdAndOperation(fitResultVo.getId(),  vo.getEditoperationSource());
                if (StringUtils.isEmpty(stoolDnas.get(0).getDnaCode())) {
                    //删除代办
                    int i = personDao.deleteEventBySourceIdAndType(fitResultVo.getId(),  vo.getEditoperationSource(), Constans.PERSON_TODO_EVENT_TYPE4);
                    if (i < 1) {
                        throw new ItSysException(
                                GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG);
                    }
                    //删除数据
                    int j = personDao.deleeBySourceId(fitResultVo.getId(),  vo.getEditoperationSource(), Constans.HOSPITAL_STOOL_DNA);
                    if (j < 1) {
                        throw new ItSysException(
                                GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG);
                    }
                }
            }
            vo.setResult(FIT_RESULT_NO_RESULT);
        }
        //有结果 与有结果
        if (Constans.FIT_RESULT_STATUS1.equals(oldStatus) && Constans.FIT_RESULT_STATUS1.equals(newStatus)) {
            Integer oldResult = fitResultVo.getResult();
            int newResult = 0;
            if (FIT_RESULT_STATUS_YES == vo.getResultStatus()) {
                // 有结果，判断结果是阴性，阳性，无效；
                if (vo.getUpLineValue() != null && vo.getUpLineValue() < 7) {
                    log.info("Entry FIT result,upline:{},<7,so result is invalid.", vo.getUpLineValue());
                    // 无效
                    newResult = FIT_RESULT_INVALID;
                } else if (vo.getUpLineValue() != null && vo.getDownLineValue() != null && vo.getUpLineValue() >= 7
                        && vo.getDownLineValue() >= 4) {
                    log.info("Entry FIT result,upline:{}>=7,downline:{}>=4,,so result is positive(+).");
                    // 阳性
                    newResult = FIT_RESULT_POSITIVE;
                }else if (vo.getUpLineValue() != null && vo.getDownLineValue() != null && vo.getUpLineValue() >= 7
                        && vo.getDownLineValue() < 4) {
                    log.info("Entry FIT result,upline:{}>=7,downline:{}<4,,so result is negative(-).");
                    // 阴性
                    newResult = FIT_RESULT_NEGATIVE;
                }
            }
            vo.setResult(newResult);
            //阳性转阴性|无效
            if (FIT_RESULT_POSITIVE == oldResult && FIT_RESULT_POSITIVE != newResult) {
                //查询生物样本
                //查看生物样本是否删除
                List<HospitalBiologicalSampleResultVo> samples = personDao.querySampleByIdAndOperation(fitResultVo.getId(),  vo.getEditoperationSource());
                boolean idok = true;
                for (HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo : samples) {
                    if (Constans.COLLECT_STATUS_NO!=hospitalBiologicalSampleResultVo.getCollectStatus()) {
                        idok = false;
                    }
                }
                //删除生物样本数据+代办
                if (idok) {
                    //删除代办
                    int i = personDao.deleteEventBySourceIdAndType(fitResultVo.getId(),  vo.getEditoperationSource(), Constans.PERSON_TODO_EVENT_TYPE12);
                    if (i < 1) {
                        throw new ItSysException(
                                GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG);
                    }
                    int i1 = personDao.deleteEventBySourceIdAndType(fitResultVo.getId(),  vo.getEditoperationSource(), Constans.PERSON_TODO_EVENT_TYPE13);
                    if (i1 < 1) {
                        throw new ItSysException(
                                GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG);
                    }
                    int i2 = personDao.deleteEventBySourceIdAndType(fitResultVo.getId(),  vo.getEditoperationSource(), Constans.PERSON_TODO_EVENT_TYPE18);
                    if (i2 < 1) {
                        throw new ItSysException(
                                GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG);
                    }
                    //删除生物数据
                    int j = personDao.deleeBySourceId(fitResultVo.getId(),  vo.getEditoperationSource(), Constans.HOSPITAL_BIOLOGICAL_SAMPLE_RESULT);
                    if (j < 1) {
                        throw new ItSysException(
                                GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG);
                    }
                    //删除血液数据
                    int j1 = personDao.deleeBySourceId(fitResultVo.getId(),  vo.getEditoperationSource(), Constans.HOSPITAL_BIOLOGICAL_BLOOD_SAMPLE_RESULT);
                    if (j1 < 1) {
                        throw new ItSysException(
                                GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG);
                    }

                }
                //判断是否检查
                List<HospitalColonoscopyRecord> hospitalColonoscopyRecords = personDao.queryRecordByIdAndOperation(fitResultVo.getId(),  vo.getEditoperationSource());
                if (hospitalColonoscopyRecords.get(0).getExamination_status() == null) {
                    //判断是否预约
                    if(hospitalColonoscopyRecords.get(0).getReserve_status()!=null&&hospitalColonoscopyRecords.get(0).getReserve_status()==2){
                        //删除放号信息
                        if(hospitalColonoscopyRecords.get(0).getAllocation_id()!=null){
                            reserveDetailDao.delReserveDetail(Integer.parseInt(hospitalColonoscopyRecords.get(0).getAllocation_id()),hospitalColonoscopyRecords.get(0).getSid());
                        }
                        //删除录入结果代办
                        int i= personDao.deleteEventBySourceIdAndType(hospitalColonoscopyRecords.get(0).getOperationSourceId(),hospitalColonoscopyRecords.get(0).getEditoperationSource(),Constans.PERSON_TODO_EVENT_TYPE8);
                        if(i<1){
                            throw new ItSysException(
                                    GlobalErrorCode.ERR_PERSON_INSERT_CODE, GlobalErrorCode.ERR_PERSON_INSERT_MSG);
                        }
                    }else{
                        //删除预约代办
                        int i= personDao.deleteEventBySourceIdAndType(fitResultVo.getId(),vo.getEditoperationSource(),Constans.PERSON_TODO_EVENT_TYPE5);
                        if(i<1){
                            throw new ItSysException(
                                    GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG);
                        }
                    }

                    //删除预约数据 hospital_colonoscopy_record
                    int j = personDao.deleeBySourceId(fitResultVo.getId(),  vo.getEditoperationSource(), Constans.HOSPITAL_COLONOSCOPY_RECORD);
                    if (j < 1) {
                        throw new ItSysException(
                                GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG);
                    }
                }


                //查看DNA是否删除
                //判断DNA是否有录入结果
                List<StoolDna> stoolDnas = personDao.queryDNAByIdAndOperation(fitResultVo.getId(),  vo.getEditoperationSource());
                if (StringUtils.isEmpty(stoolDnas.get(0).getDnaCode())) {
                    //删除代办
                    int i = personDao.deleteEventBySourceIdAndType(fitResultVo.getId(),  vo.getEditoperationSource(), Constans.PERSON_TODO_EVENT_TYPE4);
                    if (i < 1) {
                        throw new ItSysException(
                                GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG);
                    }
                    //删除数据
                    int j = personDao.deleeBySourceId(fitResultVo.getId(),  vo.getEditoperationSource(), Constans.HOSPITAL_STOOL_DNA);
                    if (j < 1) {
                        throw new ItSysException(
                                GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG);
                    }
                }
            }
            //无效|阴性转阳性
            if (FIT_RESULT_POSITIVE != oldResult && FIT_RESULT_POSITIVE == newResult) {

                if ("0".equals(vo.getInTenMin())) {
                    vo.setResultStatus(2);
                }
                if (FIT_RESULT_STATUS_YES == vo.getResultStatus()) {
                    // 有结果，判断结果是阴性，阳性，无效；
                    int result = 0;
                    if (vo.getUpLineValue() != null && vo.getUpLineValue() < 7) {
                        log.info("Entry FIT result,upline:{},<7,so result is invalid.", vo.getUpLineValue());
                        // 无效
                        result = FIT_RESULT_INVALID;
                    } else if (vo.getUpLineValue() != null && vo.getDownLineValue() != null && vo.getUpLineValue() >= 7
                            && vo.getDownLineValue() >= 4) {
                        log.info("Entry FIT result,upline:{}>=7,downline:{}>=4,,so result is positive(+).");
                        // 阳性
                        result = FIT_RESULT_POSITIVE;
                        HospitalReview hospitalReview = personDao.getBySid(vo.getSid());
                        log.info("Persion group:{},risk level:{}", hospitalReview.getGroup(), hospitalReview.getRiskLevel());
                        // 判断group，若B组，或C组低危，生成未预约肠镜待办，未录入粪便DNA编号待办
                        if (GROUP_TYPE_B.equals(hospitalReview.getGroup()) || (GROUP_TYPE_C.equals(hospitalReview.getGroup())
                                && hospitalReview.getRiskLevel() == RISK_LEVEL_LOW)) {
                            // DoctorInfo doctorInfo =
                            // colonoscopyService.getDoctorInfo(loginName);
                            // 生成结肠镜检查记录；
                            List<HospitalColonoscopyRecord> hospitalColonoscopyRecords = personDao.queryRecordByIdAndOperation(fitResultVo.getId(),  vo.getEditoperationSource());
                            if (hospitalColonoscopyRecords.size() == 0) {
                                HospitalColonoscopyRecord record = new HospitalColonoscopyRecord();
                                record.setSid(vo.getSid());
                                record.setStage(hospitalReview.getStageCy());
                                record.setArea_dept_id(hospitalReview.getAreaDeptId());
                                record.setCommunity_dept_id(hospitalReview.getCommunityDeptId());
                                record.setReserve_status(COLONOSCOPY_RESERVE_STATUS_NOT_RESERVED);
                                record.setSource_type(1);
                                record.setOperationSourceId(vo.getId());
                                record.setOperationSource( vo.getEditoperationSource());


                                int colonoscopyId = colonoscopyDao.add(record);
                                log.info("Insert colonoscopy record,id:{},body:{}", colonoscopyId, JSONUtils.toJson(record));

                                // 生成 未预约结肠镜检查待办；
                                HtEvent event1 = new HtEvent();
                                event1.setAreaDeptId(hospitalReview.getAreaDeptId());
                                event1.setCommunityDeptId(hospitalReview.getCommunityDeptId());
                                event1.setDataId(colonoscopyId);
                                event1.setSid(vo.getSid());
                                event1.setStatus(EVENT_STATUS_NOT_DO);
                                event1.setType(EVENT_TYPE_5);
                                event1.setOperationSourceId(vo.getId());
                                event1.setOperationSource( vo.getEditoperationSource());
                                log.info("Insert [not reserve colonoscopy todo event],body:{}", JSONUtils.toJson(event1));
                                eventDao.insert(event1);
                            }
                            List<StoolDna> stoolDnas = personDao.queryDNAByIdAndOperation(fitResultVo.getId(),  vo.getEditoperationSource());
                            if (stoolDnas.size() == 0) {
                                // 生成 粪便DNA结果记录
                                StoolDna stoolDna = new StoolDna();
                                stoolDna.setSid(vo.getSid());
                                stoolDna.setStage(hospitalReview.getStageCy());
                                stoolDna.setAreaDeptId(hospitalReview.getAreaDeptId());
                                stoolDna.setCommunityDeptId(hospitalReview.getCommunityDeptId());
                                stoolDna.setOperationSource(Constans.OPERATION_SOURCE_TYPE_BY_SYSTEM);
                                stoolDna.setOperationSourceId(vo.getId());
                                stoolDna.setEditoperationSource( vo.getEditoperationSource());
                                int dnaId = personDao.addStoolDna(stoolDna);
                                log.info("Insert dna record,id:{},body:{}", dnaId, JSONUtils.toJson(stoolDna));

                                // 生成 未录入粪便DNA装置编号 待办
                                HtEvent event2 = new HtEvent();
                                event2.setAreaDeptId(hospitalReview.getAreaDeptId());
                                event2.setCommunityDeptId(hospitalReview.getCommunityDeptId());
                                event2.setDataId(dnaId);
                                event2.setSid(vo.getSid());
                                event2.setStatus(EVENT_STATUS_NOT_DO);
                                event2.setType(EVENT_TYPE_4);
                                event2.setOperationSourceId(vo.getId());
                                event2.setOperationSource( vo.getEditoperationSource());
                                eventDao.insert(event2);
                                log.info("Insert [not entry dna code todo event],body:{}", JSONUtils.toJson(event2));
                            }
                            List<HospitalBiologicalSampleResultVo> samples = personDao.querySampleByIdAndOperation(fitResultVo.getId(),  vo.getEditoperationSource());
                            if (samples.size() == 0) {
                                // 生成生物样本
                                HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO = new HospitalBiologicalSampleResultVo();
                                String associatedSampleId = UUID.randomUUID().toString().replace("-", "");

                                // 新增一条生物样本粪便以及代办
                                hospitalBiologicalSampleResultPO.setSid(vo.getSid());
                                hospitalBiologicalSampleResultPO.setCommunityDeptId(hospitalReview.getCommunityDeptId());
                                hospitalBiologicalSampleResultPO.setAreaDeptId(hospitalReview.getAreaDeptId());
                                hospitalBiologicalSampleResultPO.setStage(Constans.PERSON_COLONOSCOPY_STATUS1);
                                hospitalBiologicalSampleResultPO.setOperationSource(Constans.OPERATION_SOURCE_TYPE_BY_SYSTEM);
                                hospitalBiologicalSampleResultPO.setAssociatedSampleId(associatedSampleId);
                                hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE5);
                                hospitalBiologicalSampleResultPO.setOperationSourceId(vo.getId());
                                hospitalBiologicalSampleResultPO.setEditoperationSource( vo.getEditoperationSource());

                                log.info("@Service-person-personInsert 新增生物样本数据粪便  参数：dna={}.",
                                        hospitalBiologicalSampleResultPO.toString());
                                int sampleid = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO,
                                        Constans.BIOLOGICAL_SAMPLE_TABLE);

                                HtEvent event3 = new HtEvent();
                                event3.setAreaDeptId(hospitalReview.getAreaDeptId());
                                event3.setCommunityDeptId(hospitalReview.getCommunityDeptId());
                                event3.setDataId(sampleid);
                                event3.setSid(vo.getSid());
                                event3.setStatus(EVENT_STATUS_NOT_DO);
                                event3.setType(Constans.PERSON_TODO_EVENT_TYPE12);
                                event3.setDataId(sampleid);
                                event3.setOperationSourceId(vo.getId());
                                event3.setOperationSource( vo.getEditoperationSource());
                                log.info("@Service-person-personInsert 新增一条未录入生物样本数据粪便待办事件  参数：todo={}.",
                                        hospitalBiologicalSampleResultPO.toString());
                                eventDao.insert(event3);

                                // 新增一条生物样本唾液以及代办
                                hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE4);

                                log.info("@Service-person-personInsert 新增生物样本数据粪便  参数：dna={}.",
                                        hospitalBiologicalSampleResultPO.toString());
                                int sampleidByM = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO,
                                        Constans.BIOLOGICAL_SAMPLE_TABLE);
                                event3.setType(Constans.PERSON_TODO_EVENT_TYPE13);
                                event3.setDataId(sampleidByM);
                                log.info("@Service-person-personInsert 新增一条未录入生物样本数据唾液待办事件  参数：todo={}.",
                                        hospitalBiologicalSampleResultPO.toString());
                                eventDao.insert(event3);

                                // 新增血液样本
                                hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE6);

                                log.info("@Service-person-personInsert 新增新增血液样本 参数：dna={}.",
                                        hospitalBiologicalSampleResultPO.toString());
                                int sampleidByA = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO,
                                        Constans.BIOLOGICAL_SAMPLE_TABLE);
                                event3.setType(Constans.PERSON_TODO_EVENT_TYPE18);
                                event3.setDataId(sampleidByA);
                                log.info("@Service-person-personInsert 新增一条未录入新增血液样本待办事件  参数：todo={}.",
                                        hospitalBiologicalSampleResultPO.toString());
                                eventDao.insert(event3);

                                // 新增一条生物样本白细胞以及代办
                                hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE3);
                                hospitalBiologicalSampleResultPO.setBloodSampleId(sampleidByA);
                                log.info("@Service-person-personInsert 新增生物白细胞  参数：dna={}.",
                                        hospitalBiologicalSampleResultPO.toString());
                                int sampleidByW = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO,
                                        Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE);

                                // 新增一条生物样本血清以及代办
                                hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE1);

                                log.info("@Service-person-personInsert 新增生物样本数据血清  参数：dna={}.",
                                        hospitalBiologicalSampleResultPO.toString());
                                int sampleidByS = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO,
                                        Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE);


                                // 新增一条生物样本血浆以及代办
                                hospitalBiologicalSampleResultPO.setSampleType(Constans.SAMPLE_TYPE2);
                                log.info("@Service-person-personInsert 新增生物样本数据血浆  参数：dna={}.",
                                        hospitalBiologicalSampleResultPO.toString());
                                int sampleidByP = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO,
                                        Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE);
                            }

                        }
                    }

                }
            }
        }
        //无结果对无结果
        if(Constans.FIT_RESULT_STATUS2.equals(oldStatus) && Constans.FIT_RESULT_STATUS2.equals(newStatus)){
            vo.setResult(FIT_RESULT_NO_RESULT);
        }

        //添加编辑记录
        try {
            if ("0".equals(vo.getInTenMin())) {
                vo.setResult(3);
                vo.setResultStatus(1);
            }


            vo.setApplyStatus(Constans.APPLY_EDIT_STATUS1);
            vo.setApprovalStatus(null);
            vo.setEditStatus(Constans.EDIT_STATUS1);
            vo.setInsertStatus(FIT_RESULT_INSERT_STATUS_ENTRY);
            // 更新fit结果数据  Edit
            fitDao.updateEditFitResult(vo, isArea);
            log.info("Update fit result,body:{}", JSONUtils.toJson(vo));
            if (isArea) {
                hospitalReferenceRecordDao.saveArea(hospitalReferenceRecordDto);
            } else {
                hospitalReferenceRecordDao.updateForEdir(hospitalReferenceRecordDto);
            }
        } catch (Exception e) {
            throw new ItSysException(
                    GlobalErrorCode.HOSPITAL_MESSAGE_CENTER_UPDATE_ERROR_CODE, GlobalErrorCode.HOSPITAL_MESSAGE_CENTER_UPDATE_ERROR_MSG);
        }
        return vo;

    }

    @Override
    public List<FitResultVo> queryCountryExecl(ExeclData execlData) {
        return fitDao.queryCountryExecl(execlData);
    }

}
