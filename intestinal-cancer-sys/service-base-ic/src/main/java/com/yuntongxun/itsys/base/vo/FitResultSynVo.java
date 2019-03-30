package com.yuntongxun.itsys.base.vo;

import java.util.Date;

/**
 * 同步诺辉第三方fit结果信息
 */
public class FitResultSynVo {

	private String sid;
	private Integer result;// 诺辉字典 0阴性 1阳性 2无效   壹永字典 1阴性，2阳性，3无效
	private Integer upLineValue;//上线值
	private Integer downLineValue;//下线值
	private Integer inTenMin;//诺辉字典 0否 1是  壹永字典 0否 1是
	private String resultDateForSql;//采样日期
	private String timestamp;
	private String sign;

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public Integer getUpLineValue() {
		return upLineValue;
	}

	public void setUpLineValue(Integer upLineValue) {
		this.upLineValue = upLineValue;
	}

	public Integer getDownLineValue() {
		return downLineValue;
	}

	public void setDownLineValue(Integer downLineValue) {
		this.downLineValue = downLineValue;
	}

	public Integer getInTenMin() {
		return inTenMin;
	}

	public void setInTenMin(Integer inTenMin) {
		this.inTenMin = inTenMin;
	}

	public String getResultDateForSql() {
		return resultDateForSql;
	}

	public void setResultDateForSql(String resultDateForSql) {
		this.resultDateForSql = resultDateForSql;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
