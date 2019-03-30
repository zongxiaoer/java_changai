package com.yuntongxun.itsys.base.po;

import java.util.Date;

/**
 * Description: FIT结果记录
 *
 * @author LuoKun on 2018-04-16.
 */
public class HospitalFitResult {

    private String sid;
    private String fitCode;//FIT编码，即噗噗管ID
    private Integer stage;//阶段，1：T0，2：T1，3：T2，4：T3
    private Date releaseDate;//发放日期
    private String relPerCode;//发放经手人工作编码
    private Date testDate;//检测日期
    private Integer resultStatus;//是否有结果，1：有结果，2：无结构
    private Date resultDate;
    private Double upLineValue;
    private Double downLineValue;
    private Integer result;
    private String noResonResult;
    private Integer codeEntryStatus;
    private Integer insertStatus;
    private Integer communityDeptId;
    private Integer areaDeptId;
    private Date dateCreated;
    private Date updateTime;
    private String inTenMin;//'结果是否在10分钟内读取. 1---是  0-----否'
    private String operationSource;
    private String   editoperationSource;
    private  Integer operationSourceId;
    private String pathUrl;


    public HospitalFitResult() {
        super();
    }

    public HospitalFitResult(String sid, String fitCode, Integer stage, Date releaseDate, String relPerCode, Date testDate, Integer resultStatus, Date resultDate, Double upLineValue, Double downLineValue, Integer result, String noResonResult, Integer codeEntryStatus, Integer insertStatus, Integer communityDeptId, Integer areaDeptId, Date dateCreated, Date updateTime, String inTenMin, String operationSource, String editoperationSource, Integer operationSourceId) {
        this.sid = sid;
        this.fitCode = fitCode;
        this.stage = stage;
        this.releaseDate = releaseDate;
        this.relPerCode = relPerCode;
        this.testDate = testDate;
        this.resultStatus = resultStatus;
        this.resultDate = resultDate;
        this.upLineValue = upLineValue;
        this.downLineValue = downLineValue;
        this.result = result;
        this.noResonResult = noResonResult;
        this.codeEntryStatus = codeEntryStatus;
        this.insertStatus = insertStatus;
        this.communityDeptId = communityDeptId;
        this.areaDeptId = areaDeptId;
        this.dateCreated = dateCreated;
        this.updateTime = updateTime;
        this.inTenMin = inTenMin;
        this.operationSource = operationSource;
        this.editoperationSource = editoperationSource;
        this.operationSourceId = operationSourceId;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getFitCode() {
        return fitCode;
    }

    public void setFitCode(String fitCode) {
        this.fitCode = fitCode;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRelPerCode() {
        return relPerCode;
    }

    public void setRelPerCode(String relPerCode) {
        this.relPerCode = relPerCode;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public Integer getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(Integer resultStatus) {
        this.resultStatus = resultStatus;
    }

    public Date getResultDate() {
        return resultDate;
    }

    public void setResultDate(Date resultDate) {
        this.resultDate = resultDate;
    }

    public Double getUpLineValue() {
		return upLineValue;
	}

	public void setUpLineValue(Double upLineValue) {
		this.upLineValue = upLineValue;
	}

	public Double getDownLineValue() {
		return downLineValue;
	}

	public void setDownLineValue(Double downLineValue) {
		this.downLineValue = downLineValue;
	}

	public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getNoResonResult() {
		return noResonResult;
	}

	public void setNoResonResult(String noResonResult) {
		this.noResonResult = noResonResult;
	}

	public Integer getCodeEntryStatus() {
        return codeEntryStatus;
    }

    public void setCodeEntryStatus(Integer codeEntryStatus) {
        this.codeEntryStatus = codeEntryStatus;
    }

    public Integer getInsertStatus() {
        return insertStatus;
    }

    public void setInsertStatus(Integer insertStatus) {
        this.insertStatus = insertStatus;
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

	@Override
	public String toString() {
		return "HospitalFitResult [sid=" + sid + ", fitCode=" + fitCode + ", stage=" + stage + ", releaseDate="
				+ releaseDate + ", relPerCode=" + relPerCode + ", testDate=" + testDate + ", resultStatus="
				+ resultStatus + ", resultDate=" + resultDate + ", upLineValue=" + upLineValue + ", downLineValue="
				+ downLineValue + ", result=" + result + ", noResonResult=" + noResonResult + ", codeEntryStatus="
				+ codeEntryStatus + ", insertStatus=" + insertStatus + ", communityDeptId=" + communityDeptId
				+ ", areaDeptId=" + areaDeptId + ", dateCreated=" + dateCreated + ", updateTime=" + updateTime + "]";
	}

    public String getInTenMin() {
        return inTenMin;
    }

    public void setInTenMin(String inTenMin) {
        this.inTenMin = inTenMin;
    }

    public String getOperationSource() {
        return operationSource;
    }

    public void setOperationSource(String operationSource) {
        this.operationSource = operationSource;
    }

    public String getEditoperationSource() {
        return editoperationSource;
    }

    public void setEditoperationSource(String editoperationSource) {
        this.editoperationSource = editoperationSource;
    }

    public Integer getOperationSourceId() {
        return operationSourceId;
    }

    public void setOperationSourceId(Integer operationSourceId) {
        this.operationSourceId = operationSourceId;
    }

    public String getPathUrl() {
        return pathUrl;
    }

    public void setPathUrl(String pathUrl) {
        this.pathUrl = pathUrl;
    }
}
