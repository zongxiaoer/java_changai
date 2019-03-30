package com.yuntongxun.itsys.base.vo;

public class ReservationVo {

	private Integer id;
	private Integer reserveable;
	private String period;
	private String name;
	private String deptName;
	private String examinationName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getReserveable() {
		return reserveable;
	}

	public void setReserveable(Integer reserveable) {
		this.reserveable = reserveable;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getExaminationName() {
		return examinationName;
	}

	public void setExaminationName(String examinationName) {
		this.examinationName = examinationName;
	}

}
