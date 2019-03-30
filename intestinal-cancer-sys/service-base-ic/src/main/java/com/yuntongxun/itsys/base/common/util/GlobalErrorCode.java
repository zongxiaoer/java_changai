package com.yuntongxun.itsys.base.common.util;

/**
 * 错误码
 * <p>
 * 错误码说明：
 * 1)使用9位整型数字来表示非成功返回码
 * 2)第1、2两位（从左边数起，下同）偶数来表示不同的系统。
 * 10：公共错误码  12：用户系统  99：系统级别错误
 * 3)第3、4位整数表示系统下的不同子系统或模块。由各系统负责人指定不同子系统或模块的代码。
 * 例如    1201：用户系统恒生用户系统同步模块
 * 4)第5位整数表示返回码类型
 * 0：不能确定归类的   1：处理结果   2：处理状态  3：异常信息
 * 例如	12012：用户系统恒生用户系统同步模块处理状态信息
 * 5)后6-9位整数为具体返回码，由各位开发人员讨论之后确定
 * 例如	120130001：用户系统恒生用户系统同步模块手机号码重复
 *
 * @author hanpengcheng
 * @Time 2016/11/24 13:36
 */
public class GlobalErrorCode {
    //通用
    public final static String NORMAL_RESPONSE = "000000";    //正常应答
    public final static String SUCCESS = "success";    //正常应答
    public final static int UNKNOWN_ERROR = 99;     //未知错误
    public final static int SYSTEM_BUSY = 100;       //系统繁忙请稍后再试
    public final static int REQUIRED_PARAMETER_ERROR = 101;     //请求参数不合法
    public final static int SERIALIZATION_ERROR = 102; //序列化出错
    public final static int CALL_REMOTE_SEVER_ERROR = 103;   //调用远端服务器错误
    public final static int NO_ACCESS_ERROR = 104;      //没有权限访问该资源
    public final static int TOKEN_ERROR = 105;          //token不正确
    public final static int OBJECT_NOT_EXISTS = 106;    //查询的对象不存在或已删除
    public final static int DATA_ALREADY_ASSOCIATED = 107;    //数据已被关联
    public final static String RUNTIME_ERROR_CODE = "108";//运行时错误


    public final static String RUNTIME_ERROR_MSG = "程序运行错误";//运行时错误
    public final static String PARAMETER_ERR_CODE = "123";
    public final static String PARAMETER_ERR_MSG = "参数错误";
    public final static String OBJECT_NOT_EXISTS_MSG = "数据已删除";
    public final static String UNKNOWN_ERROR_MSG = "未知错误";     //未知错误
    public final static String PARAMETER_ERR_TIME = "未到预约时间不能点击未检查";   //附件上传失败
    public final static String PARAMETER_ERR_CODE1 = "109";
    public final static String PARAMETER_ERR_MSG1 = "缺少必填参数";



    //文件上传
    public final static String FILE_UPLOAD_FAIL_CODE = "832000";   //文件上传失败
    public final static String FILE_UPLOAD_TYPE_CODE = "832001";   //文件格式错误
    public final static String FILE_UPLOAD_SIZE_ERROR = "832002";   //文件格式错误
    public final static String FILE_UPLOAD_TYPE_MSG = "文件格式不支持";
    public final static String FILE_UPLOAD_FAIL_MSG = "文件上传失败";   //附件上传失败
    public final static String FILE_UPLOAD_FAIL_MSG_SIZE = "文件上传失败，文件大小不能超过 2 MB。";   //附件上传失败

    public final static int OUT_SIZE = 160620009;           // 图片过大
    public final static int DELETE_FAIL = 160620010;        // 删除附件失败
    public final static int TOMANY_FAIL = 160620011;        // 附件达到上限
    public final static int TO0_BIG_FAIL = 160620012;       // 附件大小达到上限


    //数据字典
    public static final String ERR_DICTIONARY_NULL_CODE = "850004";
    public static final String ERR_DICTIONARY_NULL_MSG = "【字典】数据不能为空";
    public static final String ERR_DICTIONARY_KEY_NULL_CODE = "850005";
    public static final String ERR_DICTIONARY_KEY_NULL_MSG = "【字典】key不能为空";
    public static final String ERR_DICTIONARY_VALUE_NULL_CODE = "850006";
    public static final String ERR_DICTIONARY_VALUE_NULL_MSG = "【字典】value不能为空";
    public static final String ERR_DICTIONARY_ID_NULL_CODE = "850007";
    public static final String ERR_DICTIONARY_ID_NULL_MSG = "【字典】id不能为空";
    public static final String ERR_DICTIONARY_UPDATE_NULL_CODE = "850008";
    public static final String ERR_DICTIONARY_UPDATE_NULL_MSG = "【字典】要修改的数据为空";
    //数据字典类型
    public static final String ERR_DICTIONARY_TYPE_ID_NULL_CODE = "850009";
    public static final String ERR_DICTIONARY_TYPE_ID_NULL_MSG = "【字典类型】id不能为空";
    public static final String ERR_DICTIONARY_TYPE_NULL_CODE = "850010";
    public static final String ERR_DICTIONARY_TYPE_NULL_MSG = "【字典类型】要修改的数据为空";

    public static final String ERR_SCREENINGTYPE_NULL_CODE = "850011";
    public static final String ERR_SCREENINGTYPE_NULL_MSG = "【组织架构】筛查现场未选择";

    public static final String ERR_HOSPITAL_NULL_CODE = "850012";
    public static final String ERR_HOSPITAL_NULL_MSG = "【组织架构】医院类型不能为空";

    public static final String ERR_HOSPITAL_SUBORDINATE_NULL_CODE = "850013";
    public static final String ERR_HOSPITAL_SUBORDINATE_NULL_MSG = "【组织架构】无下属医院";

    //包体转换异常
    public static final String ERR_BODY_ERRO = "860001";
    //必选参数为空
    public static final String ERR_PARAM_NULL = "860002";

    //受试者错误码
    public static final String ERR_PERSON_AGE_EROOR_CODE = "860003";
    public static final String ERR_PERSON_AGE_EROOR_MSG = "受试者年龄不满足50-74岁之间";
    public static final String ERR_PERSON_IN_GROUP_CODE = "860004";
    public static final String ERR_PERSON_IN_GROUP_MSG = "受试者已经入组";
    public static final String ERR_PERSON_OUT_GROUP_CODE = "860005";
    public static final String ERR_PERSON_OUT_GROUP_MSG = "受试者之前被录入过";
    public static final String ERR_GROUP_STATUS_NULL_CODE = "860010";
    public static final String ERR_GROUP_STATUS_NULL_MSG = "退出研究失败";
    public static final String ERR_RISK_STATUS_CODE = "860011";
    public static final String ERR_RISK_STATUS_MSG = "危险等级状态更新失败";
    public static final String ERR_TODOEVENT_ADD_CODE = "860012";
    public static final String ERR_TODOEVENT_ADD_MSG = "待办新增失败";
    public static final String ERR_RISKFACTOR_ADD_CODE = "860013";
    public static final String ERR_RISKFACTOR_ADD_MSG = "危险因素添加失败";
    public static final String ERR_SEND_SMS_CODE = "860014";
    public static final String ERR_SEND_SMS_MSG = "短信发送失败";
    public static final String ERR_SEND_SMS_OFTEN_CODE = "860015";
    public static final String ERR_SEND_SMS_OFTEN_MSG = "该用户短信发送过于频繁";
    public static final String ERR_RISKFACTOR_NULL_CODE = "860016";
    public static final String ERR_RISKFACTOR_NULL_MSG = "危险因素不存在";
    public static final String ERR_PERSON_BIRTH_NULL_CODE = "860020";
    public static final String ERR_PERSON_BIRTH_NULL_MSG = "受试者身份证号为空";
    public static final String ERR_AREA_DEPID_NULL_CODE = "860021";
    public static final String ERR_AREA_DEPID_NULL_MSG = "根据用户名查询所属地区机构为空";
    public static final String ERR_COMMUNITY_DEPID_NULL_CODE = "860022";
    public static final String ERR_COMMUNITY_DEPID_NULL_MSG = "地区医院不允许操作受试者";
    public static final String ERR_USER_DEPID_NULL_CODE = "860023";
    public static final String ERR_USER_DEPID_NULL_MSG = "根据用户名查询所属机构id为空";
    public static final String ERR_USER_DEPID_ERRO_CODE = "860024";
    public static final String ERR_USER_DEPID_ERRO_MSG = "社区医院不允许操作地区受试者";
    public static final String ERR_PERSON_NULL_CODE = "860025";
    public static final String ERR_PERSON_NULL_MSG = "受试者不存在";
    public static final String ERR_GROUP_STATUS_RERESEARCH_CODE = "860026";
    public static final String ERR_GROUP_STATUS_RERESEARCH_MSG = "重新入组失败";
    public static final String ERR_PERSON_INSERT_CODE = "860027";
    public static final String ERR_PERSON_INSERT_MSG = "新增入组失败";
    public final static String ERR_PERSON_INSERT_PARAM_MUST_2_CODE="860028";//纳入标准2-10项的字段必须为2
    public final static String ERR_PERSON_INSERT_PARAM_MUST_2_MSG="纳入标准2-10项的字段必须为2";
    public final static String ERR_PERSON_INSERT_PHONE_IS_NULL_CODE="860029";//纳入标准2-10项的字段必须为2
    public final static String ERR_PERSON_INSERT_PHONE_IS_NULL_MSG="受试者校验手机号不能为空";//纳入标准2-10项的字段必须为2
    public final static String ERR_PERSON_INSERT_ID_CARD_BLACK_CODE="860030";//纳入标准2-10项的字段必须为2
    public final static String ERR_PERSON_INSERT_ID_CARD_BLACK_MSG="此参加者已经在5年内参加过结肠镜筛查项目，不满足本研究的要求";//纳入标准2-10项的字段必须为2


    //肠镜错误码   hospital_colonoscopy_record
    public static final String ERR_COLONOSCOPY_RESERVE_NUMBER_FULL_ERROR_CODE = "870001";
    public static final String ERR_COLONOSCOPY_RESERVE_NUMBER_FULL_ERROR_MSG = "预约肠镜人数已满。";
    public static final String ERR_USER_INFO_INCOMPLETE_ERROR_CODE = "870002";
    public static final String ERR_USER_INFO_INCOMPLETE_ERROR_MSG = "请求服务错误，用户信息不完整。";
    public static final String ERR_GET_ALLOCATION_ERROR = "870003";
    public static final String ERR_GET_ALLOCATION_MSG = "请求服务错误，未查询到放号记录。";


    //fit错误吗
    public static final String ERR_HOSPITAL_FIT_RESULT_FIT_CODE_ERROR_CODE = "880001";
    public static final String ERR_HOSPITAL_FIT_RESULT_FIT_CODE_ERROR_MSG = "已经存在噗噗管ID";

    public static final String ERR_HOSPITAL_FIT_RESULT_FIT_RESULT_ERROR_CODE = "880002";
    public static final String ERR_HOSPITAL_FIT_RESULT_FIT_RESULT_ERROR_MSG = "没有结果，不能发送";

    public static final String ERR_HOSPITAL_FIT_RESULT_FIT_SEND_ERROR_CODE = "880003";
    public static final String ERR_HOSPITAL_FIT_RESULT_FIT_SEND_ERROR_MSG = "发送失败";


    public static final String ERR_HOSPITAL_STOOL_DNA_ERROR_CODE = "880004";
    public static final String ERR_HOSPITAL_STOOL_DNA_ERROR_MSG = "DNA编码已经存在";

    public static final String ERR_HOSPITAL_STOOL_DNA_ERROR_CODE_5 = "880005";
    public static final String ERR_HOSPITAL_STOOL_DNA_ERROR_MSG_5 = "未传递必填参数";

    public static final String ERR_HOSPITAL_STOOL_DNA_SUCCESS_CODE_6 = "880006";
    public static final String ERR_HOSPITAL_STOOL_DNA_SUCCESS_MSG_6 = "成功录入DNA检测结果数据";

    public static final String ERR_HOSPITAL_STOOL_DNA_ERROR_CODE_7 = "880006";
    public static final String ERR_HOSPITAL_STOOL_DNA_ERROR_MSG_7 = "录入DNA结果失败";


    public static final String ERR_HOSPITAL_STOOL_VIEW_ERROR_CODE = "880007";
    public static final String ERR_HOSPITAL_STOOL_VIEW_ERROR_MSG = "系统不存在sid";

    public static final String PERSON_OVERALL_STATUS2_ERROR_CODE = "880008";
    public static final String PERSON_OVERALL_STATUS2_ERROR_MSG = "已经退出研究";

    public static final String ERR_HOSPITAL_STOOL_DNA_RESULT_ERROR_CODE = "880009";
    public static final String ERR_HOSPITAL_STOOL_DNA_RESULT_ERROR_MSG = "DNA结果已经存在";


    //国家
    public static final String ERR_HOSPITAL_COUNTRY_FIT_RESULT_FIT_CODE_ERROR_CODE = "890001";
    public static final String ERR_HOSPITAL_COUNTRY_FIT_RESULT_FIT_CODE_ERROR_MSG = "不是国家用户不能查看";


    //生物样本错误码
    public static final String ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_CODE_ERROR_CODE = "900001";
    public static final String ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_CODE_ERROR_MSG = "冷冻盒编码对应位置已经存在采集管";


    public static final String ERR_HOSPITAL_BIOLOGICAL_SAMPLE_ID_ERROR_CODE = "900002";
    public static final String ERR_HOSPITAL_BIOLOGICAL_SAMPLE_ID_CODE_ERROR_MSG = "样本id不存在，";

    public static final String ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_SAMPLETYPE_CODE_ERROR_CODE = "900003";
    public static final String ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_SAMPLETYPE_CODE_ERROR_MSG = "冷冻盒编码已经在其他样本类型存在";


    public static final String ERR_HOSPITAL_BIOLOGICAL_SAMPLE_SAVE_CODE_ERROR_CODE = "900004";
    public static final String ERR_HOSPITAL_BIOLOGICAL_SAMPLE_SAVE_CODE_ERROR_CODE_ERROR_MSG = "同一条数据不能多次录入";

    public static final String ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_FORMAT_CODE_ERROR_CODE = "900005";
    public static final String ERR_HOSPITAL_BIOLOGICAL_SAMPLE_FROZEN_BOX_FORMAT_CODE_ERROR_MSG = "冷冻盒编码格式存在问题";

    public static final String ERR_HOSPITAL_BIOLOGICAL_SAMPLE_ID_FORMAT_CODE_ERROR_CODE = "900006";
    public static final String ERR_HOSPITAL_BIOLOGICAL_SAMPLE_ID_FORMAT_CODE_ERROR_MSG = "样本ID格式存在问题";



    public static final String ERR_HOSPITAL_BIOLOGICAL_SAMPLE_ID_ISHAVE_CODE_ERROR_CODE = "900007";
    public static final String ERR_HOSPITAL_BIOLOGICAL_SAMPLE_ID_ISHAVE_CODE_ERROR_MSG = "样本ID不能同时绑定多个SID";

    public static final String ERR_HOSPITAL_BIOLOGICAL_SAMPLE_ID_ISHAVE_MORE5_CODE_ERROR_CODE = "900008";
    public static final String ERR_HOSPITAL_BIOLOGICAL_SAMPLE_ID_ISHAVE_MORE5_CODE_ERROR_MSG = "该样本类型已有样本ID，无法录入！";

    public static final String ERR_HOSPITAL_STOOL_FROZEN_BOX_CODE_ISHAVE_ERROR_CODE = "900010";
    public static final String ERR_HOSPITAL_STOOL_FROZEN_BOX_CODE_ISHAVE__ERROR_MSG = "有冷冻盒已经寄出";

    public static final String ERR_HOSPITAL_STOOL_FITRESULT_ERROR_CODE = "900009";
    public static final String ERR_HOSPITAL_STOOL_FITRESULT_ERROR_MSG = "FIT结果已存在";


    //快递模块
    public static final String ERR_HOSPITAL_COURIER_NUMBER_RESULT_ERROR_CODE = "1000001";   //唯一标示不能复用
    public static final String ERR_HOSPITAL_COURIER_NUMBER_RESULT_ERROR_MSG = "快递编号已经存在";//唯一标示不能复用

    public static final String ERR_SAVE_HOSPITAL_COURIER_RESULT_ERROR_CODE = "1000002";
    public static final String ERR_SAVE_HOSPITAL_COURIER_RESULT_ERROR_MSG = "保存快递信息错误";


    public static final String ERR_UPDATE_HOSPITAL_COURIER_RESULT_ERROR_CODE = "1000003";
    public static final String ERR_UPDATE_HOSPITAL_COURIER_RESULT_ERROR_MSG = "修改快递信息错误";

    public static final String ERR_UPDATE_HOSPITAL_COURIER_RESULT_IS_HAVE_ERROR_CODE = "1000004";
    public static final String ERR_UPDATE_HOSPITAL_COURIER_RESULT_IS_HAVE_ERROR_MSG  = "该快递已经接受";

    //消息中心

    public static final String HOSPITAL_MESSAGE_CENTER_INSERT_ERROR_CODE = "1100001";
    public static final String HOSPITAL_MESSAGE_CENTER_INSERT_ERROR_MSG  = "插入消息数据失败";

    public static final String HOSPITAL_MESSAGE_CENTER_UPDATE_ERROR_CODE = "1100002";
    public static final String HOSPITAL_MESSAGE_CENTER_UPDATE_ERROR_MSG  = "修改消息数据失败";

    //编辑
    public static final String HOSPITAL_REFERENCE_RECORD_EDIT_CODE = "1200001";  //不可点击编辑申请
    public static final String HOSPITAL_REFERENCE_RECORD_EDIT_MSG   = "不可点击编辑申请";

    public static final String HOSPITAL_REFERENCE_RECORD_EDIT_IS_ERROR_CODE = "1200002";  //
    public static final String HOSPITAL_REFERENCE_RECORD_EDIT_IS_ERROR_MSG   = "不可点击编辑申请";

    public static final String HOSPITAL_REFERENCE_RECORD_APPROVAL_STATUS_IS_ERROR_CODE = "1200003";  //
    public static final String HOSPITAL_REFERENCE_RECORD_APPROVAL_STATUS_IS_ERROR_MSG   = "不可审核";


    public static final String HOSPITAL_REFERENCE_RECORD_EDIT_IS_ERROR_CODE1 = "1200004";  //
    public static final String HOSPITAL_REFERENCE_RECORD_EDIT_IS_ERROR_MSG1   = "不可编辑";


    public static final String HOSPITAL_REFERENCE_RECORD_EDIT_IS_ERROR_CODE2 = "1200005";  //
    public static final String HOSPITAL_REFERENCE_RECORD_EDIT_IS_ERROR_MSG3   = "锁定状态，无法操作";



    //癌症
    public static final String HOSPITAL_CANCER_RECORD_SAVE_CODE = "1300001";  //
    public static final String HOSPITAL_CANCER_RECORD_SAVE_CODE_MSG   = "录入癌症检查记录表失败";


    public static final String HOSPITAL_CANCER_RECORD_SAVE_EVENT_CODE = "1300002";  //
    public static final String HOSPITAL_CANCER_RECORD_SAVE_CODE_EVENT_MSG   = "录入癌症相关代办失败";


    public static final String HOSPITAL_CANCER_RECORD_UPDATE_EVENT_CODE = "1300003";  //
    public static final String HOSPITAL_CANCER_RECORD_UPDATE_CODE_EVENT_MSG   = "修改癌症相关代办失败";


    //第三方批量dna
    public static final String DNA_PDF_ISNO_CODE = "1400001";  //
    public static final String DNA_PDF_ISNO_CODE_MSG   = "未找到对应的PDF文件";

    public static final String DNA_CODE_ISNO_CODE = "1400002";  //
    public static final String DNA_CODE_ISNO_CODE_MSG   = "dna编码不存在";

    public static final String PARAM_ISNO_CODE = "1400003";  //
    public static final String PARAM_ISNO_CODE_MSG    = "参数错误";

    public static final String DNA_PARAM_ISNO_CODE = "1400004";  //
    public static final String DNA_PARAM_ISNO_CODE_MSG   = "未填必填参数";

    public static final String DNA_NOKNOW_ERROR_CODE = "1400005";  //
    public static final String DNA_NOKNOW_ERROR_CODE_MSG   = "未知异常";

    public static final String DNA_IS_HAVE_ERROR_CODE = "1400006";  //
    public static final String DNA_IS_HAVE_ERROR_CODE_MSG   = "DNA录入结果已经存在";

    public static final String DNA_FENSHU_ERROR_CODE = "1400007";  //
    public static final String DNA_FENSHU_ERROR_CODE_MSG   = "分数参数错误";

    public static final String DNA_SIGN_ERROR_CODE = "1400008";  //
    public static final String DNA_SIGN_ERROR_CODE_MSG    = "固定签名存在错误";

    public static final String DNA_IN_EDIT_ERROR_CODE = "1400009";  //
    public static final String DNA_IN_EDIT_ERROR_CODE_MSG   = "粪便dna在编辑中，不可录入";

    public static final String DNA_QUERY_ISERROR_CODE = "1400010";  //
    public static final String DNA_QUERY_ISERROR_CODE_MSG   = "查询存在异常";

    public static final String DNA_RESULT_GOAL_ISERROR_CODE = "1400011";  //
    public static final String DNA_RESULT_GOAL_ISERROR_CODE_MSG   = "分值范围错误";

    public static final String DNA_NORESULT_GOAL_ISERROR_CODE = "1400012";  //
    public static final String DNA_NORESULT_GOAL_ISERROR_CODE_MSG   = "无效不应该多传其他参数";



    public static final String DNA_SFTP_CONCENT_ISERROR_CODE = "1400013";  //
    public static final String DNA_SFTP_CONCENT_ISERROR_CODE_MSG   = "sftp链接异常";



    public static final String NEWINSERT_HOSALLOCATION_RULE_ISERROR_CODE = "1500001";  //
    public static final String NEWINSERT_HOSALLOCATION_RULE_ISERROR_MSG   = "放号任务错误";



    public static final String DIAGNOSIS_ISERROR_CODE = "1500002";  //
    public static final String DIAGNOSIS_ISERROR_MSG   = "停诊异常";



    public static final String DIAGNOSIS_ISERROR_TIME_CODE = "1500003";  //
    public static final String DIAGNOSIS_ISERROR_TIME_MSG   = "停诊时间大于当天时间";




}
