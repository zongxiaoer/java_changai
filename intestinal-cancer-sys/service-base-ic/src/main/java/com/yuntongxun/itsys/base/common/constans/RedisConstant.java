package com.yuntongxun.itsys.base.common.constans;

/**
 * Description: redis key使用
 *
 * @author LuoKun on 2018-04-16.
 */
public class RedisConstant {


	 /**
     * redis键 地区医院的受试者序号
     */
    public static final String HOSPITAL_AREA_PERSON_NUMBER = "hospital001|";

    /**
     * redis键 筛查现场的受试者序号
     */
    public static final String HOSPITAL_SITE_ID_NUMBER = "hospital002|";


    /**
     * redis键前缀 例：hospital001|admin
     */
    public static final String HOSPITAL_KEY_INFO = "hospital003|";//医师相关信息

    public static final String HOSPITAL_BI_TOKEN_KEY = "|intestinalToken|";//BItoken
}
