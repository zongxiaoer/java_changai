package com.yuntongxun.itsys.base.po;

import java.util.Date;

/**
 * Description: 粪便DNA 表：hospital_stool_dna
 *
 * @author LuoKun on 2018-04-16.
 */
public class HospitalStoolDNA {

    private Integer id;

    private String sid;

    private String dna_code;

    private String person_code;

    private Date release_date;

    private Integer stage;

    private Integer code_entry_status;

    private Integer remove_status;

    private Integer community_dept_id;

    private Integer area_dept_id;
    
    //新增字段
    private Integer dna_check_result;   //DNA检测结果
    
    private Double dna_check_quantum;   //DNA检测定量
    
    private Double dna_check_goal;      //DNA检测得分
    
    private String dna_check_filePath;   //PDF上传路径
    
    private Integer dna_check_enter_status; //1未录入 2已录入'' int   default 1   dna检测结果录入状态'

    private Integer  dna_check_inform_status;//

    private Integer   dna_community_inform_status;//

    private Integer  dna_community_inform_mode;

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
        this.sid = sid;
    }

    public String getDna_code() {
        return dna_code;
    }

    public void setDna_code(String dna_code) {
        this.dna_code = dna_code;
    }

    public String getPerson_code() {
        return person_code;
    }

    public void setPerson_code(String person_code) {
        this.person_code = person_code;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public Integer getCode_entry_status() {
        return code_entry_status;
    }

    public void setCode_entry_status(Integer code_entry_status) {
        this.code_entry_status = code_entry_status;
    }

    public Integer getRemove_status() {
        return remove_status;
    }

    public void setRemove_status(Integer remove_status) {
        this.remove_status = remove_status;
    }

    public Integer getCommunity_dept_id() {
        return community_dept_id;
    }

    public void setCommunity_dept_id(Integer community_dept_id) {
        this.community_dept_id = community_dept_id;
    }

    public Integer getArea_dept_id() {
        return area_dept_id;
    }

    public void setArea_dept_id(Integer area_dept_id) {
        this.area_dept_id = area_dept_id;
    }

    public HospitalStoolDNA() {
        super();
    }

    public HospitalStoolDNA(Integer id, String sid, String dna_code, String person_code, Date release_date, Integer stage, Integer code_entry_status, Integer remove_status, Integer community_dept_id, Integer area_dept_id) {
        this.id = id;
        this.sid = sid;
        this.dna_code = dna_code;
        this.person_code = person_code;
        this.release_date = release_date;
        this.stage = stage;
        this.code_entry_status = code_entry_status;
        this.remove_status = remove_status;
        this.community_dept_id = community_dept_id;
        this.area_dept_id = area_dept_id;
    }

	public Integer getDna_check_result() {
		return dna_check_result;
	}

	public void setDna_check_result(Integer dna_check_result) {
		this.dna_check_result = dna_check_result;
	}

	public Double getDna_check_quantum() {
		return dna_check_quantum;
	}

	public void setDna_check_quantum(Double dna_check_quantum) {
		this.dna_check_quantum = dna_check_quantum;
	}

	public Double getDna_check_goal() {
		return dna_check_goal;
	}

	public void setDna_check_goal(Double dna_check_goal) {
		this.dna_check_goal = dna_check_goal;
	}

	public String getDna_check_filePath() {
		return dna_check_filePath;
	}

	public void setDna_check_filePath(String dna_check_filePath) {
		this.dna_check_filePath = dna_check_filePath;
	}

	public Integer getDna_check_enter_status() {
		return dna_check_enter_status;
	}

	public void setDna_check_enter_status(Integer dna_check_enter_status) {
		this.dna_check_enter_status = dna_check_enter_status;
	}

    public Integer getDna_check_inform_status() {
        return dna_check_inform_status;
    }

    public void setDna_check_inform_status(Integer dna_check_inform_status) {
        this.dna_check_inform_status = dna_check_inform_status;
    }

    public Integer getDna_community_inform_status() {
        return dna_community_inform_status;
    }

    public void setDna_community_inform_status(Integer dna_community_inform_status) {
        this.dna_community_inform_status = dna_community_inform_status;
    }

    public Integer getDna_community_inform_mode() {
        return dna_community_inform_mode;
    }

    public void setDna_community_inform_mode(Integer dna_community_inform_mode) {
        this.dna_community_inform_mode = dna_community_inform_mode;
    }

}
