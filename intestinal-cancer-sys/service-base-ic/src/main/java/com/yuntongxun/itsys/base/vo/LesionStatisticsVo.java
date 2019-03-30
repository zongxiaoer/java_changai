/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: LesionStatisticsVo
 * Author:   sun
 * Date:     2018/11/26 17:18
 * History:
 * <author>          <time>                <version>
 *   sun         2018/11/26 17:18           v1.0.0
 */
package com.yuntongxun.itsys.base.vo;

/**
 * 癌症病变统计工具类
 *
 * @author sun
 * @create 2018/11/26
 * @since v1.0.0
 */
public class LesionStatisticsVo {
    private String sid;
    private String group;
    private Integer riskLevel;
    private Integer communityDeptId;
    private Integer areaDeptId;
    private Integer recordId;
    private Integer pathologyId;
    private Integer resultId;
    private String bm;//病理编码
    private String zj;//病变部位最大直径

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(Integer riskLevel) {
        this.riskLevel = riskLevel;
    }

    public Integer getCommunityDeptId() {
        return communityDeptId;
    }

    public void setCommunityDeptId(Integer communityDeptId) {
        this.communityDeptId = communityDeptId;
    }

    public Integer getAreaDeptId() {
        return areaDeptId;
    }

    public void setAreaDeptId(Integer areaDeptId) {
        this.areaDeptId = areaDeptId;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getPathologyId() {
        return pathologyId;
    }

    public void setPathologyId(Integer pathologyId) {
        this.pathologyId = pathologyId;
    }

    public Integer getResultId() {
        return resultId;
    }

    public void setResultId(Integer resultId) {
        this.resultId = resultId;
    }

    public String getBm() {
        return bm;
    }

    public void setBm(String bm) {
        this.bm = bm;
    }

    public String getZj() {
        return zj;
    }

    public void setZj(String zj) {
        this.zj = zj;
    }
}