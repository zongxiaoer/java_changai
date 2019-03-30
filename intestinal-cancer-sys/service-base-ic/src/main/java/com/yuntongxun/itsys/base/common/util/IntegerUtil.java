/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: IntegerUtil
 * Author:   sun
 * Date:     2018/11/27 11:09
 * History:
 * <author>          <time>                <version>
 *   sun         2018/11/27 11:09           v1.0.0
 */
package com.yuntongxun.itsys.base.common.util;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * 一句话功能简述
 *
 * @author sun
 * @create 2018/11/27
 * @since v1.0.0
 */
public class IntegerUtil {

    /**
     * 计算两个数值的百分比,保留两位小数
     * @param num1
     * @param num2
     */
    public static String calculationProportion(Integer num1, Integer num2){
        BigDecimal num = new BigDecimal(0);
        if(num1 == null || num2 == null || num2 == 0){
            //为空或除数为0,返回0.00%
        }else {
            BigDecimal d1 = new BigDecimal(String.valueOf(num1));
            BigDecimal d2 = new BigDecimal(String.valueOf(num2));
            num = d1.divide(d2, 6, BigDecimal.ROUND_HALF_UP);//保留6位小数
        }
        //输出
        System.out.println("小数："+ num);
        //获取格式化对象
        NumberFormat nt = NumberFormat.getPercentInstance();
        //设置百分数精确度2即保留两位小数
        nt.setMinimumFractionDigits(2);
        //最后格式化并输出
        return nt.format(num);
    }
}