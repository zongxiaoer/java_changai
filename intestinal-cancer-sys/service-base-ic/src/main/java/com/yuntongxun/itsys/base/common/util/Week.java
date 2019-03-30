/*
  * Copyright (C), 2015-2018, 壹永科技有限公司
  * FileName: Week
  * Author:   wp_sp
  * Date:     2018/7/18 19:40
  * History:
  * <author>          <time>                <version>
  * wp_sp           2018/7/18 19:40           v1.0.0   
  */
package com.yuntongxun.itsys.base.common.util;

/**
 * 一句话功能简述
 *
 * @author wp_sp
 * @create 2018/7/18
 * @since v1.0.0
 */
public enum Week {

    MONDAY("星期一", "Monday", "Mon.", 1),
    TUESDAY("星期二", "Tuesday", "Tues.", 2),
    WEDNESDAY("星期三", "Wednesday", "Wed.", 3),
    THURSDAY("星期四", "Thursday", "Thur.", 4),
    FRIDAY("星期五", "Friday", "Fri.", 5),
    SATURDAY("星期六", "Saturday", "Sat.", 6),
    SUNDAY("星期日", "Sunday", "Sun.", 7);

    String name_cn;
    String name_en;
    String name_enShort;
    int number;

    Week(String name_cn, String name_en, String name_enShort, int number) {
        this.name_cn = name_cn;
        this.name_en = name_en;
        this.name_enShort = name_enShort;
        this.number = number;
    }

    public String getChineseName() {
        return name_cn;
    }

    public String getName() {
        return name_en;
    }

    public String getShortName() {
        return name_enShort;
    }

    public int getNumber() {
        return number;
    }
}