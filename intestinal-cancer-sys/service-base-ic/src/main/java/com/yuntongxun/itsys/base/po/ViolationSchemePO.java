/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: ViolationSchemePO
 * Author:   lcy
 * Date:     2018/8/27 16:11
 * History:
 * <author>          <time>                <version>
 *     lcy         2018/8/27 16:11           v1.0.0
 */
package com.yuntongxun.itsys.base.po;

import java.io.Serializable;
import java.util.Date;

/**
 *  导出违反方案PO
 *
 * @author lcy
 * @create 2018/8/27
 * @since v1.0.0
 */
public class ViolationSchemePO extends ViolationScheme implements Serializable{

    private Integer overallStatusCy;
    private String name;
    private Integer siteId;
    private String tbDateS;
    private  String item2a1;
    private String item2b1;

    private String item3a2Time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getTbDateS() {
        return tbDateS;
    }

    public void setTbDateS(String tbDateS) {
        this.tbDateS = tbDateS;
    }

    public String getItem2a1() {
        return item2a1;
    }

    public void setItem2a1(String item2a1) {
        this.item2a1 = item2a1;
    }

    public String getItem2b1() {
        return item2b1;
    }

    public void setItem2b1(String item2b1) {
        this.item2b1 = item2b1;
    }

    public Integer getOverallStatusCy() {
        return overallStatusCy;
    }

    public void setOverallStatusCy(Integer overallStatusCy) {
        this.overallStatusCy = overallStatusCy;
    }

    public String getItem3a2Time() {
        return item3a2Time;
    }

    public void setItem3a2Time(String item3a2Time) {
        this.item3a2Time = item3a2Time;
    }
}
