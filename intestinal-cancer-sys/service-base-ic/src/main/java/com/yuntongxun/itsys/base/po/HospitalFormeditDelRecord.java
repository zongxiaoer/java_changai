/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: HospitalFormeditDelRecord
 * Author:   sun
 * Date:     2018/8/7 17:54
 * History:
 * <author>          <time>                <version>
 * sun           2018/8/7 17:54           v1.0.0
 */
package com.yuntongxun.itsys.base.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 一句话功能简述
 *
 * @author sun
 * @create 2018/8/7
 * @since v1.0.0
 */
public class HospitalFormeditDelRecord implements Serializable {

    private Integer id;
    private Integer referenceId;
    private String delJson;
    private Date dateCreated;
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Integer referenceId) {
        this.referenceId = referenceId;
    }

    public String getDelJson() {
        return delJson;
    }

    public void setDelJson(String delJson) {
        this.delJson = delJson;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
