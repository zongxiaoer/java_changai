package com.yuntongxun.itsys.base.po.dto.sidrule;

import com.yuntongxun.itsys.base.po.ItsysSidRulePO;

/**
 * @author zongt
 * @date 2018/6/6
 */
public class DepartmentSidRuleDto extends ItsysSidRulePO  {
	
	private Integer groupRuleId;   //社区受试人员分组规则ID
	private String name;    //部门名称
	private Integer pid;    //pid
	private Integer type;   //部门类型
	private String msg;     //规则描述
//	private String ruleType;  //规则类型
	private int pageNo;
	private int pageSize;
	public Integer getGroupRuleId() {
		return groupRuleId;
	}
	public void setGroupRuleId(Integer groupRuleId) {
		this.groupRuleId = groupRuleId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
