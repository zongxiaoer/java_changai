package com.yuntongxun.itsys.base.po;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wp_sp
 */
public class HospitalStoolDnaPO implements Serializable {
    private Integer id;

    private String sid;

    private String dnaCode;

    private String personCode;

    private Date releaseDate;

    private Integer stage;

    private Integer codeEntryStatus;

    private Date entryTime;

    private Integer removeStatus;

    private Integer communityDeptId;

    private Integer areaDeptId;

    private Integer dnaCheckResult;

    private Double dnaCheckQuantum;

    private Double dnaCheckGoal;

    private String dnaCheckFilepath;

    private Date dnaCheckEnterDate;

    private Integer dnaCheckEnterStatus;

    private Integer dnaCheckInformStatus;

    private Date dnaCheckInformNationDate;

    private Integer dnaCommunityInformStatus;

    private Date dnaCheckInformCommunityDate;

    private Date dateCreated;

    private Date updateTime;

    private String operationSource;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    public String getDnaCode() {
        return dnaCode;
    }

    public void setDnaCode(String dnaCode) {
        this.dnaCode = dnaCode == null ? null : dnaCode.trim();
    }

    public String getPersonCode() {
        return personCode;
    }

    public void setPersonCode(String personCode) {
        this.personCode = personCode == null ? null : personCode.trim();
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public Integer getCodeEntryStatus() {
        return codeEntryStatus;
    }

    public void setCodeEntryStatus(Integer codeEntryStatus) {
        this.codeEntryStatus = codeEntryStatus;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public Integer getRemoveStatus() {
        return removeStatus;
    }

    public void setRemoveStatus(Integer removeStatus) {
        this.removeStatus = removeStatus;
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

    public Integer getDnaCheckResult() {
        return dnaCheckResult;
    }

    public void setDnaCheckResult(Integer dnaCheckResult) {
        this.dnaCheckResult = dnaCheckResult;
    }

    public Double getDnaCheckQuantum() {
        return dnaCheckQuantum;
    }

    public void setDnaCheckQuantum(Double dnaCheckQuantum) {
        this.dnaCheckQuantum = dnaCheckQuantum;
    }

    public Double getDnaCheckGoal() {
        return dnaCheckGoal;
    }

    public void setDnaCheckGoal(Double dnaCheckGoal) {
        this.dnaCheckGoal = dnaCheckGoal;
    }

    public String getDnaCheckFilepath() {
        return dnaCheckFilepath;
    }

    public void setDnaCheckFilepath(String dnaCheckFilepath) {
        this.dnaCheckFilepath = dnaCheckFilepath == null ? null : dnaCheckFilepath.trim();
    }

    public Date getDnaCheckEnterDate() {
        return dnaCheckEnterDate;
    }

    public void setDnaCheckEnterDate(Date dnaCheckEnterDate) {
        this.dnaCheckEnterDate = dnaCheckEnterDate;
    }

    public Integer getDnaCheckEnterStatus() {
        return dnaCheckEnterStatus;
    }

    public void setDnaCheckEnterStatus(Integer dnaCheckEnterStatus) {
        this.dnaCheckEnterStatus = dnaCheckEnterStatus;
    }

    public Integer getDnaCheckInformStatus() {
        return dnaCheckInformStatus;
    }

    public void setDnaCheckInformStatus(Integer dnaCheckInformStatus) {
        this.dnaCheckInformStatus = dnaCheckInformStatus;
    }

    public Date getDnaCheckInformNationDate() {
        return dnaCheckInformNationDate;
    }

    public void setDnaCheckInformNationDate(Date dnaCheckInformNationDate) {
        this.dnaCheckInformNationDate = dnaCheckInformNationDate;
    }

    public Integer getDnaCommunityInformStatus() {
        return dnaCommunityInformStatus;
    }

    public void setDnaCommunityInformStatus(Integer dnaCommunityInformStatus) {
        this.dnaCommunityInformStatus = dnaCommunityInformStatus;
    }

    public Date getDnaCheckInformCommunityDate() {
        return dnaCheckInformCommunityDate;
    }

    public void setDnaCheckInformCommunityDate(Date dnaCheckInformCommunityDate) {
        this.dnaCheckInformCommunityDate = dnaCheckInformCommunityDate;
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

    public String getOperationSource() {
        return operationSource;
    }

    public void setOperationSource(String operationSource) {
        this.operationSource = operationSource == null ? null : operationSource.trim();
    }
}