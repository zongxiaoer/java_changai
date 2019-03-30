package com.yuntongxun.itsys.base.common.constans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constans {
    // cookie
    public static final String KEY_LOGING_NAME = "YTX_LOGINNAME";
    public static final String KEY_TOKEN = "YTX_TOKEN";

    /**
     * redis短信键前缀 例：hospital001|admin
     */
    public static final String TOKEN_KEY_PASS_PREFIX = "hospital_PASS_SMS001|";
    public static final String TOKEN_KEY_NOTICESID_PREFIX = "hospital_NOTICESID_SMS001|";
    public static final String TOKEN_KEY_COLONOSCOPY_PREFIX = "hospital_COLONOSCOPY_SMS001|";

    // sys
    public static final String KEY_MENU = "MENU";
    public static final String KEY_SUCCESS_STATUS = "000000";
    public static final String KEY_SUCCESS_MSG = "success";
    public static final String KEY_USER = "USER";
    public static final String SYS_PWD_DEFAULT = "000000";
    //附件保存地址
    public static final String SYS_FILE_PATH = "E://FileTest";

    // 查询默认数量
    public static final int MESSAGE_RECEIPT_DEFAULT_PAGE_SIZE = 50;

    // 查询最大数量
    public static final int MESSAGE_RECEIPT_MAX_PAGE_SIZE = 100;

    //Cache
    public static final String CACHE_LOCAL_DICTIONARY_NAME = "cache_local_dictionary_name";
    public static final String CACHE_LOCAL_DICTIONARY_PAGEINFO_NAME = "cache_local_dictionary_pageinfo_name";
    public static final String CACHE_LOCAL_DICTIONARY_KEY = "cache_local_dictionary_key";
    public static final String CACHE_LOCAL_DICTIONARY_PAGEINFO_KEY = "cache_local_dictionary_pageinfo_key";
    public static final long CACHE_LOCAL_DICTIONARY_EXPIRE = 600l;
    public static final String CACHE_CUSTOM_CACHE = "custom_dictionary";
    public static final String CACHE_CUSTOM_DICTIONARY_TYPE_CAHCE = "custom_dictionaryType";
    public static final String CACHE_CUSTOM_RESOURCE_NAME = "custom_resource";
    public static final String CACHE_CUSTOM_RESOURCE_KEY = "cache_resource_url";

    // 员工
    public static final String ERR_USER_NAME_MSG = "【员工】员工名不能为空";
    public static final String ERR_USER_NAME_CODE = "811000";
    public static final String ERR_USER_ID_MSG = "【员工】ID不能为空";
    public static final String ERR_USER_ID_CODE = "811001";
    public static final String ERR_USER_NOHAVE_MSG = "【员工】修改的数据不存在";
    public static final String ERR_USER_NOHAVE_CODE = "811002";
    public static final String ERR_USER_NULL_MSG = "【员工】数据不能为空";
    public static final String ERR_USER_NULL_CODE = "811003";
    public static final String ERR_USER_AGE_MSG = "【员工】age不能为空";
    public static final String ERR_USER_AGE_CODE = "811004";
    public static final String ERR_USER_SEX_MSG = "【员工】SEX不能为空";
    public static final String ERR_USER_SEX_CODE = "811005";
    public static final String ERR_USER_DEPTID_CODE = "【员工】deptid不能为空";
    public static final String ERR_USER_DEPTID_MSG = "811006";
    public static final String ERR_USER_POSITIONID_CODE = "【员工】positionid不能为空";
    public static final String ERR_USER_POSITIONID_MSG = "811007";
    public static final String ERR_USER_ROLEIDS_CODE = "【员工】roleids不能为空";
    public static final String ERR_USER_ROLEIDS_MSG = "811008";


    //菜单
    public static final String ERROR_MENU_ID_MSG = "【菜单】ID不能为空";
    public static final String ERROR_MENU_ID_CODE = "812001";
    public static final String ERROR_MENU_BEAN_MSG = "【菜单】已存在该菜单名称";
    public static final String ERROR_MENU_BEAN_CODE = "812002";
    public static final String ERROR_MENU_NOHAVE_MSG = "【菜单】修改的数据不存在";
    public static final String ERROR_MENU_NOHAVE_CODE = "812003";
    public static final String ERROR_MENU_SORT_MSG = "【菜单】sort不能为空";
    public static final String ERROR_MENU_SORT_CODE = "812004";
    public static final String ERROR_MENU_OPERATION_MSG = "【菜单】operation不能为空";
    public static final String ERROR_MENU_OPERATION_CODE = "812005";
    public static final String ERROR_MENU_NULL_MSG = "【菜单】要修改的数据不存在";
    public static final String ERROR_MENU_NULL_CODE = "812006";
    public static final String ERROR_MENU_ROOT_MSG = "【菜单】root根节点不能修改";
    public static final String ERROR_MENU_ROOT_CODE = "812006";
    public static final String ERROR_MENU_PID_CODE = "812007";
    public static final String ERROR_MENU_PID_MSG = "【菜单】PID不能为空";


    // 操作
    public static final String ERROR_OPTRATION_NULL_CODE = "813001";
    public static final String ERROR_OPTRATION_NULL_MSG = "【操作】保存的操作类型不能为空";


    // 数据字典
    public static final String ERR_DICTIONARY_NULL_CODE = "814001";
    public static final String ERR_DICTIONARY_NULL_MSG = "【数据字典】添加或修改的数据字典不能为空";
    public static final String ERR_DICTIONARY_ID_CODE = "814002";
    public static final String ERR_DICTIONARY_ID_MSG = "【数据字典】添加或修改的数据字典id不能为空";
    public static final String ERR_DICTIONARY_NoHAVE_CODE = "814003";
    public static final String ERR_DICTIONARY_NoHAVE_MSG = "【数据字典】修改的数据不存在";
    public static final String ERROR_DICTIONARY_SORT_MSG = "【数据字典】sort不能为空";
    public static final String ERROR_DICTIONARY_SORT_CODE = "814004";
    public static final String ERROR_DICTIONARY_TYPE_MSG = "【数据字典】Type不能为空";
    public static final String ERROR_DICTIONARY_TYPE_CODE = "814005";
    public static final String ERROR_DICTIONARY_PID_MSG = "【数据字典】PID不能为空";
    public static final String ERROR_DICTIONARY_PID_CODE = "814006";
    public static final String ERROR_DICTIONARY_ISHIDDEN_MSG = "【数据字典】isHidden不能为空";
    public static final String ERROR_DICTIONARY_ISHIDDEN_CODE = "814007";
    //数据字典类型
    public static final String ERROR_DICTIONARY_TYPE_ID_MSG = "【数据字典类型】id不能为空";
    public static final String ERROR_DICTIONARY_TYPE_ID_CODE = "814008";
    public static final String ERROR_DICTIONARY_TYPE_NULL_MSG = "【数据字典类型】修改的数据不存在";
    public static final String ERROR_DICTIONARY_TYPE_NULL_CODE = "814009";


    //请求
    public static final String ERROR_RESOURCE_REPEAT_CODE = "815001";
    public static final String ERROR_RESOURCE_REPEAT_MSG = "【请求】已存在该请求名称";
    public static final String ERROR_RESOURCE_ID_CODE = "815002";
    public static final String ERROR_RESOURCE_ID_MSG = "【请求】请求id不能为空";
    public static final String ERROR_RESOURCE_NOHAVE_CODE = "815003";
    public static final String ERROR_RESOURCE_NOHAVE_MSG = "【请求】修改的数据不存在";
    public static final String ERROR_RESOURCE_NULL_CODE = "815004";
    public static final String ERROR_RESOURCE_NULL_MSG = "【请求】添加的数据不能为空";
    public static final String ERROR_RESOURCE_NAME_CODE = "815005";
    public static final String ERROR_RESOURCE_NAME_MSG = "【请求】name不能为空";
    public static final String ERROR_RESOURCE_TYPE_CODE = "815006";
    public static final String ERROR_RESOURCE_TYPE_MSG = "【请求】type不能为空";
    public static final String ERROR_RESOURCE_URL_CODE = "815007";
    public static final String ERROR_RESOURCE_URL_MSG = "【请求】url不能为空";
    public static final String ERROR_RESOURCE_SAVELOG_CODE = "815008";
    public static final String ERROR_RESOURCE_SAVELOG_MSG = "【请求】savelog不能为空";
    public static final String ERROR_RESOURCE_BIND_AUTH_NULL_CODE = "815009";
    public static final String ERROR_RESOURCE_BIND_AUTH_NULL_MSG = "【请求】绑定关系数据不能为空";


    //角色
    public static final String ERR_ROLE_ID_CODE = "816001";
    public static final String ERR_ROLE_ID_MSG = "【角色】ID不能为空";
    public static final String ERR_ROLE_NOHAVE_CODE = "816002";
    public static final String ERR_ROLE_NOHAVE_MSG = "【角色】修改或删除的数据不存在";
    public static final String ERR_ROLE_NULL_CODE = "816003";
    public static final String ERR_ROLE_NULL_MSG = "【角色】数据不能为空";
    public static final String ERR_ROLE_MENU_CODE = "816004";
    public static final String ERR_ROLE_MENU_MSG = "【角色】该角色已绑定了提交的数据";
    public static final String ERR_ROLE_RESOURCE_IDS_CODE = "816005";
    public static final String ERR_ROLE_RESOURCE_IDS_MSG = "【角色】resourceids不能为空";
    public static final String ERR_ROLE_MENU_IDS_CODE = "816006";
    public static final String ERR_ROLE_MENU_IDS_MSG = "【角色】menuids不能为空";
    public static final String ERR_ROLE_MENU_ID_CODE = "816007";
    public static final String ERR_ROLE_MENU_ID_MSG = "【角色】绑定菜单的roleid不能为空";
    public static final String ERR_ROLE_RESOURCE_ID_CODE = "816008";
    public static final String ERR_ROLE_RESOURCE_ID_MSG = "【角色】绑定资源的roleid不能为空";
    public static final String ERR_ROLE_NAME_CODE = "816009";
    public static final String ERR_ROLE_NAME_MSG = "【角色】name不能为空";
    public static final String ERR_ROLE_SUPERUSER_NDEL_MSG = "【角色】超级管理员不能删除";
    public static final String ERR_ROLE_SUPERUSER_NDEL_CODE = "816010";


    //部门
    public static final String ERR_DEPART_ID_CODE = "817001";
    public static final String ERR_DEPART_ID_MSG = "【部门】id不能为空";
    public static final String ERR_DEPART_NAME_CODE = "817002";
    public static final String ERR_DEPART_NAME_MSG = "【部门】name不能为空";
    public static final String ERR_DEPART_LEVEL_CODE = "817003";
    public static final String ERR_DEPART_LEVEL_MSG = "【部门】level不能为空";
    public static final String ERR_DEPART_PID_CODE = "817004";
    public static final String ERR_DEPART_PID_MSG = "【部门】pId不能为空";
    public static final String ERR_DEPART_DESC_CODE = "817005";
    public static final String ERR_DEPART_DESC_MSG = "【部门】desc不能为空";
    public static final String ERR_DEPART_SORT_CODE = "817006";
    public static final String ERR_DEPART_SORT_MSG = "【部门】sort不能为空";
    public static final String ERR_DEPART_USERID_CODE = "817007";
    public static final String ERR_DEPART_USERID_MSG = "【部门】userid不能为空";
    public static final String ERR_DEPART_POSITION_CODE = "817008";
    public static final String ERR_DEPART_POSITION_MSG = "【部门】position不能为空";
    public static final String ERR_DEPART_DEPTID_CODE = "817009";
    public static final String ERR_DEPART_DEPTID_MSG = "【部门】deptid不能为空";
    public static final String ERR_DEPART_TYPE_CODE = "817010";
    public static final String ERR_DEPART_TYPE_MSG = "【部门】type不能为空";
    public static final String ERR_DEPART_NULL_CODE = "817011";
    public static final String ERR_DEPART_NULL_MSG = "【部门】修改的数据不存在";
    public static final String ERR_DEPART_NOHAVE_CODE = "817012";
    public static final String ERR_DEPART_NOHAVE_MSG = "【部门】查询的角色不存在";
    public static final String ERR_DEPART_ROOT_CODE = "817013";
    public static final String ERR_DEPART_ROOT_MSG = "【部门】root根节点不能更改";


    //用户
    public static final String ERR_SYSUSER_ID_CODE = "824001";
    public static final String ERR_SYSUSER_ID_MSG = "【用户】id不能为空";
    public static final String ERR_SYSUSER_LOGINNAME_CODE = "824002";
    public static final String ERR_SYSUSER_LOGINNAME_MSG = "【用户】loginName不能为空";
    public static final String ERR_SYSUSER_NULL_CODE = "824003";
    public static final String ERR_SYSUSER_NULL_MSG = "【用户】要修改的数据不存在";
    public static final String ERR_LOGINNAME_HAVE_CODE = "824004";
    public static final String ERR_LOGINNAME_HAVE_MSG = "【用户】该人员已存在登录名";
    public static final String ERR_LOGINNAME_MAIL_NULL_CODE = "824005";
    public static final String ERR_LOGINNAME_MAIL_NULL_MSG = "【用户】创建用户公司邮箱不能为空";
    public static final String ERR_LOGINNAME_PWD_NULL_CODE = "824006";
    public static final String ERR_LOGINNAME_PWD_NULL_MSG = "【用户】密码不能为空";
    public static final String ERR_LOGINNAME_PWD_EXF_CODE = "824007";
    public static final String ERR_LOGINNAME_PWD_EXF_MSG = "【用户】用户原密码错误";
    public static final String ERR_USER_LOGINNAME_CODE = "824008";
    public static final String ERR_USER_LOGINNAME_MSG = "【用户】LoginName不能为空";

    //城市
    public static final String ERR_CITY_ID_CODE = "825001";
    public static final String ERR_CITY_ID_MSG = "【城市】ID不能为空";
    //LDAP
    public static final String ERR_LDAP_SN_CODE = "826001";
    public static final String ERR_LDAP_SN_MSG = "【LDAP】sn不能为空";
    public static final String ERR_LDAP_CN_CODE = "826002";
    public static final String ERR_LDAP_CN_MSG = "【LDAP】cn不能为空";
    public static final String ERR_LDAP_EXTENDS_CODE = "826003";
    public static final String ERR_LDAP_EXTENDS_MSG = "【LDAP】已存在此条目";
    public static final String ERR_LDAP_NOEXTENDS_CODE = "826004";
    public static final String ERR_LDAP_NOEXTENDS_MSG = "【LDAP】不存在此条目";
    public static final String ERR_LDAP_UID_CODE = "826005";
    public static final String ERR_LDAP_UID_MSG = "【LDAP】uid为空";

    //Auth
    public static final String ERR_AUTH_EXTENDS_MSG = "【权限】唯一标识重复";
    public static final String ERR_AUTH_EXTENDS_CODE = "827001";
    public static final String ERR_AUTH_NULL_MSG = "【权限】修改的数据不存在";
    public static final String ERR_AUTH_NULL_CODE = "827002";
    public static final String ERR_AUTH_PID_NULL_MSG = "【权限】Pid不能为空";
    public static final String ERR_AUTH_PID_NULL_CODE = "827003";
    public static final String ERR_AUTH_PHONE_NULL_MSG = "请输入手机号";
    public static final String ERR_AUTH_PHONE_NULL_CODE = "827004";
    public static final String ERR_AUTH_MSG_NULL_MSG = "验证码发送失败";
    public static final String ERR_AUTH_MSG_NULL_CODE = "827005";
    public static final String ERR_AUTH_USER_NULL_MSG = "手机用户不存在";
    public static final String ERR_AUTH_USER_NULL_CODE = "827006";
    public static final String ERR_AUTH_USER_SOLE_MSG = "手机号不唯一";
    public static final String ERR_AUTH_USER_SOLE_CODE = "827007";
    public static final String ERR_AUTH_USER_PWD_MSG = "两次输入密码不一致";
    public static final String ERR_AUTH_USER_PWD_CODE = "827008";
    public static final String ERR_AUTH_CODE_MSG = "请重新获取短信验证码";
    public static final String ERR_AUTH_CODE_CODE = "827009";
    public static final String ERR_AUTH_CODE_ERR_MSG = "验证码输入错误";
    public static final String ERR_AUTH_CODE_ERR_CODE = "827010";
    public static final String ERR_AUTH_CODE_NULL_MSG = "请输入验证码";
    public static final String ERR_AUTH_CODE_NULL_CODE = "827011";
    public static final String ERR_AUTH_PWD_NULL_MSG = "请输入密码";
    public static final String ERR_AUTH_PWD_NULL_CODE = "827012";
    public static final String ERR_AGAIN_PWD_NULL_MSG = "请输入确认密码";
    public static final String ERR_AGAIN_PWD_NULL_CODE = "827013";

    //person
    /*入组状态，1：正常*/
    public static final Integer PERSON_GROUPSTATUS_JOIN = 1;
    /*入组状态，2：脱组*/
    public static final Integer PERSON_GROUPSTATUS_QUIT = 2;

    public static final String GROUP_STATUS_CODE1 = "1";//未入组

    public static final String GROUP_STATUS_CODE2 = "2";//入组

    public static final String GROUP_STATUS_CODE3 = "3";//脱组

    public static final Integer PERSON_RISK_LEVEL1 = 1;//低风险

    public static final Integer PERSON_RISK_LEVEL2 = 2;//高风险

    public static final Integer DEP_HOSPITAL_TYPE1 = 1;//社区医院

    public static final Integer DEP_HOSPITAL_TYPE2 = 2;//地区医院

    public static final Integer DEP_HOSPITAL_TYPE3 = 3;//国家医院

    /*sid前缀*/
    public static final String PERSON_SID_ACC = "TC";

    /*受试者资格审年度阶段状态值，1：t0*/
    public static final Integer PERSON_STAGE_CY_STATUS0 = 1;
    /*受试者资格审年度阶段状态值，2：t1*/
    public static final Integer PERSON_STAGE_CY_STATUS1 = 2;
    /*受试者资格审年度阶段状态值，3：t2*/
    public static final Integer PERSON_STAGE_CY_STATUS2 = 3;
    /*受试者资格审年度阶段状态值，4：t3*/
    public static final Integer PERSON_STAGE_CY_STATUS3 = 4;

    /*受试者资格审当前年总体状态，1：正常*/
    public static final Integer PERSON_OVERALL_STATUS1 = 1;
    /*受试者资格审当前年总体状态，2：退出*/
    public static final Integer PERSON_OVERALL_STATUS2 = 2;
    /*受试者资格审当前年总体状态，3：肺癌*/
    public static final Integer PERSON_OVERALL_STATUS3 = 3;
    /*受试者资格审当前年总体状态，4：死亡*/
    public static final Integer PERSON_OVERALL_STATUS4 = 4;

    /*资格审核表状态，1：未通过*/
    public static final Integer PERSON_REVIEW_STATUS1 = 1;
    /*资格审核表状态，2：已通过*/
    public static final Integer PERSON_REVIEW_STATUS2 = 2;

    /*危险因素收集状态，1：未收集*/
    public static final Integer PERSON_RISK_FACTOR_STATUS1 = 1;
    /*危险因素收集状态，2：已收集*/
    public static final Integer PERSON_RISK_FACTOR_STATUS2 = 2;

    /*结肠镜结果记录表阶段状态值，1：t0*/
    public static final Integer PERSON_COLONOSCOPY_STATUS1 = 1;
    /*结肠镜结果记录表阶段状态值，2：t1*/
    public static final Integer PERSON_COLONOSCOPY_STATUS2 = 2;
    /*结肠镜结果记录表阶段状态值，3：t2*/
    public static final Integer PERSON_COLONOSCOPY_STATUS3 = 3;

    /*待办事件表状态值，未办*/
    public static final Integer PERSON_TODO_EVENT_STATUS1 = 1;
    /*待办事件表状态值，2：已办*/
    public static final Integer PERSON_TODO_EVENT_STATUS2 = 2;
    /*待办事件表状态值，3：移除待办*/
    public static final Integer PERSON_TODO_EVENT_STATUS3 = 3;
    //待办事件表状态值，4：退出    该情况下暂时不显示
    public static final Integer PERSON_TODO_EVENT_STATUS4 = 4;

    /*待办事件表事件类型，1：未完成危险因素调查表*/
    public static final Integer PERSON_TODO_EVENT_TYPE1 = 1;
    /*待办事件表事件类型，2：未录入FIT编号*/
    public static final Integer PERSON_TODO_EVENT_TYPE2 = 2;
    /*待办事件表事件类型，3：未录入FIT结果*/
    public static final Integer PERSON_TODO_EVENT_TYPE3 = 3;
    /*待办事件表事件类型，4：未录入粪便DNA装置编号*/
    /*待办事件表事件类型，4：未录入粪便DNA装置编号*/
    public static final Integer PERSON_TODO_EVENT_TYPE4 = 4;
    /*待办事件表事件类型，5：未预约结肠镜检查*/
    public static final Integer PERSON_TODO_EVENT_TYPE5 = 5;
    /*待办事件表事件类型，6：未完成结肠镜检查*/
    public static final Integer PERSON_TODO_EVENT_TYPE6 = 6;
    /*待办事件表事件类型，7：未发放筛查结果告知书*/
    public static final Integer PERSON_TODO_EVENT_TYPE7 = 7;
    /*待办事件表事件类型，8：待录入肠镜结果*/
    public static final Integer PERSON_TODO_EVENT_TYPE8 = 8;
    /*待办事件表事件类型，9：待录入病理结果*/
    public static final Integer PERSON_TODO_EVENT_TYPE9 = 9;
    /*待办事件表事件类型，10：待录入筛查结果告知书*/
    public static final Integer PERSON_TODO_EVENT_TYPE10 = 10;
    /*待办事件表事件类型，11:待接收生物样本  血清系列*/
    public static final Integer PERSON_TODO_EVENT_TYPE11 = 11;
    /*待办事件表事件类型，12:待接收生物样本  粪便系类 */
    public static final Integer PERSON_TODO_EVENT_TYPE12 = 12;
    /*待办事件表事件类型，13:待接收生物样本  唾液系列*/
    public static final Integer PERSON_TODO_EVENT_TYPE13 = 13;
    /*待办事件表事件类型，14:待接收生物样本  血浆系列*/
    public static final Integer PERSON_TODO_EVENT_TYPE14 = 14;
    /*待办事件表事件类型，15:待接收生物样本  白细胞系列*/
    public static final Integer PERSON_TODO_EVENT_TYPE15 = 15;

    /*待办事件表事件类型，16：国家粪便DNA审批代办*/
    public static final Integer PERSON_TODO_EVENT_TYPE16 = 16;

    /*待办事件表事件类型，17：社区粪便DNA发放代办处理*/
    public static final Integer PERSON_TODO_EVENT_TYPE17 = 17;

    /*待办事件表事件类型，18 :待接收生物样本  血液系类 */
    public static final Integer PERSON_TODO_EVENT_TYPE18 = 18;

    /*待办事件表事件类型，19 :肠镜上传图片代办 */
    public static final Integer PERSON_TODO_EVENT_TYPE19 = 19;



    /*待办事件表事件类型，20 :表C1：癌症报告表 */
    public static final Integer PERSON_TODO_EVENT_TYPE20 = 20;

    /*待办事件表事件类型，21 :表C2：癌症诊断表*/
    public static final Integer PERSON_TODO_EVENT_TYPE21 = 21;

    /*待办事件表事件类型，22 :表表C3-结直肠癌诊断信息摘录表*/
    public static final Integer PERSON_TODO_EVENT_TYPE22 = 22;

    /*待办事件表事件类型，23 :表C4：结直肠癌治疗信息摘录表*/
    public static final Integer PERSON_TODO_EVENT_TYPE23 = 23;



    //受试者退出试验日志表 0： 退出试验
    public static final Integer PERSON_QUIT_LOG_TYPE_0 = 0;
    //受试者退出试验日志表 1： 重新入组
    public static final Integer PERSON_QUIT_LOG_TYPE_1 = 1;


    /*粪便dna编码录入状态，1：未录入 ,2：已录入*/
    public static final Integer STOOL_CODE_ENTRY_STATUS1 = 1;//1未录入
    public static final Integer STOOL_CODE_ENTRY_STATUS2 = 2;//2已录入

    /*结肠镜告知书录入状态，1：未录入 ,2：已录入*/
    public static final Integer NOTIFICATION_ENTRY_STATUS1 = 1;
    public static final Integer NOTIFICATION_ENTRY_STATUS2 = 2;

    /*结肠镜检查预约状态，1：未预约，2：已预约*/
    public static final Integer RESERVE_STATUS1 = 1;
    public static final Integer RESERVE_STATUS2 = 2;

    /*结肠镜告知书发放状态，1：未发放，2：已发放*/
    public static final Integer NOTIFICATION_ISSUE_STATUS1 = 1;
    public static final Integer NOTIFICATION_ISSUE_STATUS2 = 2;

    /*结肠镜完成状态，1：未完成，2：已完成*/
    public static final Integer FINISHED_STATUS1 = 1;
    public static final Integer FINISHED_STATUS2 = 2;

    /*结肠镜结果状态，1：未录入，2：已录入 */
    public static final Integer RESULT_STATUS1 = 1;
    public static final Integer RESULT_STATUS2 = 2;

    /* 结肠镜病理录入状态，1：未录入，2：已录入 */
    public static final Integer PATHOLOGY_STATUS1 = 1;
    public static final Integer PATHOLOGY_STATUS2 = 2;

    /* 结肠镜病理是否有病理，1：是，2：否 */
    public static final Integer YES_PATHOLOGY = 1;
    public static final Integer NO_PATHOLOGY = 2;



    /*  结肠镜检查就诊状态，1：未就诊，2：已就诊 */
    public static final Integer EXAMINATION_STATUS1 = 1;
    public static final Integer EXAMINATION_STATUS2 = 2;

    /*  结肠镜检查就诊状态，1--未签到  2---签到 */
    public static final String EXAMINATION_STATUS11 = "1";
    public static final String EXAMINATION_STATUS22 = "2";

    /*  结肠镜检查就诊状态，1：预约，2：新增 */
    public static final Integer SOURCE_TYPE1 = 1;
    public static final Integer SOURCE_TYPE2 = 2;

    /*  结肠镜录入结果，1：有，2：无 */
    public static final Integer item_2_21 = 1;
    public static final Integer item_2_22 = 2;


    /**
     * 文件属性
     */
    // 1- 系统文件
    public static final Integer FILE_SOURCE_TYPE_1 = 1;
    // 2- 头像
    public static final Integer FILE_SOURCE_TYPE_2 = 2;
    // 3- 微信二维码
    public static final Integer FILE_SOURCE_TYPE_3 = 3;
    /**
     * 4- 第三方DNA编码检测结果文件
     */
    public static final Integer FILE_SOURCE_TYPE_4 = 4;

    /*
     *  fit图片上传
     */
    public static final Integer FILE_SOURCE_TYPE_5 = 5;

    /*
     *  肠镜上传
     */
    public static final Integer FILE_SOURCE_TYPE_6 = 6;

    /*
     *  癌C3上传
     */
    public static final Integer FILE_SOURCE_TYPE_7 = 7;

    public static final String OPERATION_SOURCE_TYPE = "1";//新增
    public static final String OPERATION_SOURCE_TYPE_BY_SYSTEM = "2";//系统

    //样本类型
    public static final String SAMPLE_TYPE1 = "S";//1--血清.
    public static final String SAMPLE_TYPE2 = "P";//2--血浆.
    public static final String SAMPLE_TYPE3 = "W";//3--白细胞
    public static final String SAMPLE_TYPE4 = "M";//4--唾液
    public static final String SAMPLE_TYPE5 = "F";//5--粪便'
    public static final String SAMPLE_TYPE6 = "A";//5--血液

    public static final String SAMPLE_TYPE1_S = "血清";//1--血清.
    public static final String SAMPLE_TYPE2_P = "血浆";//2--血浆.
    public static final String SAMPLE_TYPE3_W = "白细胞";//3--白细胞

    //分区类型
    public final static int DEPARTMENT_TYPE_COMMUNITY = 1; //社区类型
    public final static int DEPARTMENT_TYPE_AREA = 2;  //地区类型
    public final static int DEPARTMENT_TYPE_NATION = 3; //国家


    //生物样本
    public static final Integer COLLECT_STATUS_PROVIDE = 1;//采集
    public static final Integer COLLECT_STATUS_NOPROVIDE = 2;//未提供
    public static final Integer COLLECT_STATUS_NO = -1;//没有提供

    public static final String COLLECT_STATUS_SAMPLE_LINE1 = "1";//采集1
    public static final String COLLECT_STATUS_SAMPLE_LINE6 = "6";//采集6


    public static final String BIOLOGICAL_BLOOD_SAMPLE_TABLE = "hospital_biological_blood_sample_result";//血液样本表
    public static final String BIOLOGICAL_SAMPLE_TABLE = "hospital_biological_sample_result";//生物样本表

    //冷冻盒编号快递状态. 1--已接受 2--未寄出。3--已寄出
    public static final Integer COURIER_STATUS_CODE1 = 1;//已接受
    public static final Integer COURIER_STATUS_CODE2 = 2;//未寄出
    public static final Integer COURIER_STATUS_CODE3 = 3;//已寄出

    //冷冻盒编号头
    public static final String FROZEN_BOX_CODE_HEADER = "CS";//

    //黑名单类型
    public static final String IDENTITY_BLACKLIST_TYPE = "1";//类型代表身份证


    //DNA粪便
    public static final Integer DNA_CHECK_INFORM_STATUS1 = 1;// 1—已返回
    public static final Integer DNA_CHECK_INFORM_STATUS2 = 2;// 2--未返回


    //dna录入结果
    public static final  Integer DNA_LURU_STATUS1=1;//DNA结果未录入
    public static final  Integer DNA_LURU_STATUS2=2;//DNA结果录入


    //DNA粪便国家审批
    public static final Integer DNA_COUNTRY_CHECK_INFORM_STATUS = 1;// 1—国家审批不通过
    public static final Integer DNA_COUNTRY_CHECK_INFORM_STATUS2 = 2;// 2--国家审批通过
    public static final Integer DNA_COUNTRY_CHECK_INFORM_STATUS3 = 3;// 3--未审批

    //DNA粪便社区发放
    public static final Integer DNA_COMMUNITY_INFORM_STATUS = 1;// 1—DNA粪便社区已发放
    public static final Integer DNA_COMMUNITY_INFORM_STATUS2 = 2;// 2--DNA粪便社区未发放


    //DNA录入结果
    public static final Integer DNA_CHECK_RESULT_YANG = 2;// 阳性
    public static final Integer DNA_CHECK_RESULT_YING = 1;// 阴性
    public static final Integer DNA_CHECK_RESULT_WU = 3;//无效


    public static final String  DNA_CHECK_RESULT_WU_CODE = "invalid";//无效



    //文件上传格式
    public static final String FILE_PDF = "PDF";// pdf
    public static final String FILE_TXT = "TXT";// 阴性

    /**
     * 退出研究受试者D2表单筛选条件 - 0 未录入
     */
    public static final Integer PERSON_QUIT_LOG_D2_IN_STATUS_0 = 0;
    /**
     * 退出研究受试者D2表单筛选条件 - 1 已录入
     */
    public static final Integer PERSON_QUIT_LOG_D2_IN_STATUS_1 = 1;


    public static final String SUBQUERY = "SUBQUERY";//子查询工具类


    /**
     * 新增sid的规则类型
     */
    public static final String ADD_SID_RULE_0 = "A";//规则类型A

    public static final String ADD_SID_RULE_1 = "B";//规则类型B

    /**
     * 新增sid的是否有社区范围
     */
    public static final Integer COMMUNITY_SCOPE_STATUS_YES = 1;//是

    public static final Integer COMMUNITY_SCOPE_STATUS_NO = 2;//否

    public static final Integer PERSON_QUIT_SCHEME_TYPE1 = 1;//退出研究记录


    /**
     * 消息中心管理内容
     */

    //消息中文文本类型
    public static final String meaasge_text_typpe1 = "违反方案";//违反方案
    public static final String meaasge_text_typpe2 = "已经退出研究";//
    public static final String meaasge_text_typpe3 = "诊断为结直肠癌";//
    public static final String meaasge_text_typpe4 = "快递模块";//
    public static final String meaasge_text_typpe5 = "管理模块";//
    public static final String meaasge_text_typpe6 = "诊断为结直肠癌";//
    public static final String meaasge_text_typpe7 = "重新入组";//

    //消息中心类型
    public static final String meaasge_typpe1 = "0";//系统更新
    public static final String meaasge_typpe2 = "1";//异常类型
    public static final String meaasge_typpe3 = "2";//申请编辑
    public static final String meaasge_typpe4 = "3";//解锁编辑
    public static final String meaasge_typpe5 = "4";//通知发放
    public static final String meaasge_typpe6 = "5";//停诊告知


    public static final String update_typpe1 = "1";//还原原来数据
    public static final String update_typpe2 = "2";//修改现在数据


    //表单类型
    public static final String HOSPITAL_COURIER_RESULT = "HOSPITAL_COURIER_RESULT";//快递

    public static final String HOSPITAL_BIOLOGICAL_SAMPLE_RESULT = "HOSPITAL_BIOLOGICAL_SAMPLE_RESULT";//生物样本

    public static final String HOSPITAL_BIOLOGICAL_BLOOD_SAMPLE_RESULT = "HOSPITAL_BIOLOGICAL_BLOOD_SAMPLE_RESULT";//血液


    public static final String HOSPITAL_INTESTINE_REVIEW = "HOSPITAL_INTESTINE_REVIEW";//资格审核表


    public static final String HOSPITAL_INTESTINE_RISK_FACTOR = "HOSPITAL_INTESTINE_RISK_FACTOR";//危险因素表


    public static final String HOSPITAL_COLONOSCOPY_RECORD = "HOSPITAL_COLONOSCOPY_RECORD";//结肠镜检查记录表

    public static final String HOSPITAL_STOOL_DNA = "HOSPITAL_STOOL_DNA";//DNA

    public static final String HOSPITAL_FIT_RESULT = "HOSPITAL_FIT_RESULT";//fit

    public static final String HOSPITAL_VIOLATION_SCHEME = "HOSPITAL_VIOLATION_SCHEME";//D2表单

    public static final String HOSPITAL_SCREENING_NOTIFICATION = "HOSPITAL_SCREENING_NOTIFICATION";//修改告知书


    public static final String HOSPITAL_COLONOSCOPY_RESULT = "HOSPITAL_COLONOSCOPY_RESULT";//修改肠镜检查结果

    public static final String HOSPITAL_COLONOSCOPY_PATHOLOGY_RESULT="HOSPITAL_COLONOSCOPY_PATHOLOGY_RESULT";//病理结果

    public static final String APPLY_EDIT_STATUS1 = "0";//申请编辑状态
    public static final String APPLY_EDIT_STATUS2 = "1";//申请编辑状态消失


    public static final String EDIT_STATUS1 = "0";//不可编辑  1---编辑
    public static final String EDIT_STATUS2 = "1";//-编辑

    public static final String APPROVAL_STATUS1 = "0";//出现审核
    public static final String APPROVAL_STATUS2 = "1";//国家审核通过

    //编辑
    public static final String HOSPITAL_REFERENCE_RECORD_STATUS1 = "0";//申请中
    public static final String HOSPITAL_REFERENCE_RECORD_STATUS_FINISH = "1";//申请结束
    public static final String HOSPITAL_REFERENCE_RECORD_EDIT_FINISH = "2";//编辑结束


    public static final String APPLYSTATUS = "applyStatus";//
    public static final String ID = "id";//
    public static final String COURIERNUMBER = "courierNumber";//


    public static final String CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW = "CG_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW";//C组高危 添加表A2-联系信息与危险因素调查表系统触发

    public static final String CD_OR_BY_SYSTEM_SAVE_FIT = "CD_OR_BY_SYSTEM_SAVE_FIT";//C组低危

    public static final String A_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW_HOSPITAL_COLONOSCOPY_RECORD="A_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW_HOSPITAL_COLONOSCOPY_RECORD";//创建受试者A组触发肠镜检查记录
    public static final String A_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW_HOSPITAL_INTESTINE_RISK_FACTOR="A_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW_HOSPITAL_INTESTINE_RISK_FACTOR";//A组触发危险因素
    public static final String B_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW_HOSPITAL_INTESTINE_RISK_FACTOR="B_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW_HOSPITAL_INTESTINE_RISK_FACTOR";//B组触发危险因素
    public static final String C_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW_HOSPITAL_INTESTINE_RISK_FACTOR="C_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW_HOSPITAL_INTESTINE_RISK_FACTOR";//c组触发危险因素
    public static final String A_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW_DNA="A_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW_DNA";//创建受试者A组触发DNA编码
    public static final String B_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW_FIT="B_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW_FIT";//创建受试者B组触发FIT
    public static final String A_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW_SAMPLE="A_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW_SAMPLE";//创建受试者A组触发生物样本
    public static final String SAVE_HOSPITAL_COLONOSCOPY_RESULT="A_SYSTEM_SAVE_HOSPITAL_INTESTINE_REVIEW_HOSPITAL_COLONOSCOPY_RECORD_SAVE_HOSPITAL_COLONOSCOPY_RESULT";//填写肠镜检查结果记录

    public static final String ADD_NEW_HOSPITAL_FIT_RESULT="ADD_NEW_HOSPITAL_FIT_RESULT";//新增数据触发
    public static final String ADD_NEW_HOSPITAL_COLONOSCOPY_RECORD="ADD_NEW_HOSPITAL_COLONOSCOPY_RECORD";//新增肠镜价检查报告


    public static final Integer HOSPITAL_VIOLATION_SCHEME_ENTRY_STATUS1=1;//1:已录入

    public static final Integer HOSPITAL_VIOLATION_SCHEME_ENTRY_STATUS2=2;//2：未录入',fit

    //fit结果
    public static final Integer  FIT_RESULT_STATUS1=1;//有结果
    public static final Integer  FIT_RESULT_STATUS2=2;//无结果

    public static final List<String> PictureTypes= new ArrayList<>(Arrays.asList("BMP","JPG","PNG","TIFF","GIF","PCX","TGA","EXIF","FPX","SVG","JPEG"));

    //癌症报告结果状态，1：未录入，2：已录入. 3:作废无效
    public static final String   cancerReportStatus1="1";
    public static final String  cancerReportStatus2="2";

    //癌症诊断表结果状态   1：未录入，2：已录入. 3:作废无效',
    public static final String CANCER_DIAGNOSE_STATUS1="1";
    public static final String CANCER_DIAGNOSE_STATUS2="2";

    //结直肠癌诊断信息摘录状态   1：未录入，2：已录入. 3:作废无效',
    public static final String COLORECTAL_CANCER_DIAGNOSE_INFORMATION_STATUS1="1";
    public static final String COLORECTAL_CANCER_DIAGNOSE_INFORMATION_STATUS2="2";

    //'结直肠癌治疗信息摘录表状态  1：未录入，2：已录入. 3:作废无效',
    public static final String COLORECTAL_CANCER_TREATMENT_INFORMATIO_STATUS1="1";
    public static final String COLORECTAL_CANCER_TREATMENT_INFORMATIO_STATUS2="2";

    //终点事件类型  1---结直肠 2---死亡
    public static final String END_EVENT_TYPE1="1";
    public static final String END_EVENT_TYPE2="2";

    //有效数据1---有效 2----无效',
    public static final String VALID_DATA1="1";
    public static final String VALID_DATA2="2";

    //癌症表单类型 1--c1 2--c2 3--c3 4--c4
    public static final String CANCERTYPE1="1";
    public static final String CANCERTYPE2="2";
    public static final String CANCERTYPE3="3";
    public static final String CANCERTYPE4="4";


    //癌症字段sql
    public static final String CANCER_REPORT_ID="cancer_report_id";
    public static final String  CANCER_REPORT_STATUS="cancer_report_status";

    public static final String CANCER_REPORT_IN_STATUS_DATE="cancer_report_in_status_date";
    //癌症诊断表id
    public static final String CANCER_DIAGNOSE_ID="cancer_diagnose_id";
    //'癌症诊断表结果状态   1：未录入，2：已录入. 3:作废无效'
    public static final String CANCER_DIAGNOSE_STATUS="cancer_diagnose_status";
    //'癌症诊断表结果录入时间'
    public static final String CANCER_DIAGNOSE_IN_STATUS_DATE="cancer_diagnose_in_status_date";
  //'结直肠癌诊断信息摘录表ID'
    public static final String COLORECTAL_CANCER_DIAGNOSE_INFORMATION_ID="colorectal_cancer_diagnose_information_id";
    //'结直肠癌诊断信息摘录状态   1：未录入，2：已录入. 3:作废无效'
    public static final String COLORECTAL_CANCER_DIAGNOSE_INFORMATION_STATUS="colorectal_cancer_diagnose_information_status";
    //'结直肠癌诊断信息摘录状态录入时间 '
    public static final String COLORECTAL_CANCER_DIAGNOSE_INFORMATION_IN_STATUS_DATE="colorectal_cancer_diagnose_information_in_status_date";
    //' 结直肠癌治疗信息摘录表Id'
    public static final String COLORECTAL_CANCER_TREATMENT_INFORMATION_ID="colorectal_cancer_treatment_information_id";

    //'结直肠癌治疗信息摘录表状态  1：未录入，2：已录入. 3:作废无效'
    public static final String COLORECTAL_CANCER_TREATMENT_INFORMATIO_STATUS="colorectal_cancer_treatment_informatio_status";

    //'结直肠癌治疗信息摘录表录入时间'
    public static final String COLORECTAL_CANCER_TREATMENT_INFORMATIO_IN_STATUS_DATE="colorectal_cancer_treatment_informatio_in_status_date";

    //表C1：癌症报告表-癌症信息-
    public static final String HOSPITAL_CANCER_REPORT_MESSAGE=" hospital_cancer_report_message ";
    //表C4从表：结直肠癌治疗信息摘录表-外科操作表-
    public static final String HOSPITAL_CANCER_SURGICAL_OPERATION="hospital_cancer_surgical_operation";
    //表C4从表：结直肠癌治疗信息摘录表-治疗信息表-
    public static final String HOSPITAL_CANCER_TREATMENT_INFORMATION="hospital_cancer_treatment_information";
    //表C3从表：结直肠癌诊断信息摘录表c3-诊断评估表-
    public static final String HOSPITAL_INFORMATION_DIAGNOSE_EVALUATION="hospital_information_diagnose_evaluation";
    //表C3从表：结直肠癌诊断信息摘录表c3-并发症信息表-
    public static final String HOSPITAL_CANCER_INFORMATION_COMPLICATIONS="hospital_cancer_information_complications";


    //发放类型 1-社区抢号规则 2-社区不抢号规则
    public static final Integer  ISSUETYPE1=1;
    public static final Integer  ISSUETYPE2=2;
    //是否开始 1开始  2未开始 3结束
    public static final Integer BEGIN_STATUS1=1;
    public static final Integer BEGIN_STATUS2=2;
    public static final Integer BEGIN_STATUS3=3;

    //'使用状态 1-停用 2-已停用'
    public static final Integer  USESTATUS1=1;
    public static final Integer  USESTATUS2=2;

    //1 --计算13天的  2 --不计算13天的
    public static final Integer  DAYWAI13_1=1;
    public static final Integer  DAYWAI13_2=2;

    //是否每天 1是 2否
    public static final Integer RULE_TYPE1=1;
    public static final Integer RULE_TYPE2=2;

    //操作类型 1--单次操作 2--批量操作
    public static final Integer OPERATION_TYPE_FANGHAO1=1;
    public static final Integer OPERATION_TYPE_FANGHAO2=2;

    //停诊通知所有受试者
    public static final String NOTICE_STATUS="1";
}

