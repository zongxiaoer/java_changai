package com.yuntongxun.itsys.base.po;

import java.util.List;

/**
 * @author wp_sp
 */
public class DepartMent {

    private String id, name, level, pId, desc, sort, order, dateCreated, updateTime, type,nickName,loginName;
    private List<DepartMent> child;

    private Integer screeningType;//筛查现场id

    private Integer userId;

    private Integer uid;

    public DepartMent() {
        super();
    }
//	public DepartMent(String id,String name, String pId,String desc,String sort,String type){
//		this(name,pId,desc,sort,type);
//		this.id=id;
//	}

    public DepartMent(String id, String name, String level, String pId, String desc, String sort, String order, String dateCreated, String updateTime, String type, List<DepartMent> child, Integer screeningType, Integer userId,Integer uid, String nickName,String loginName) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.pId = pId;
        this.desc = desc;
        this.sort = sort;
        this.order = order;
        this.dateCreated = dateCreated;
        this.updateTime = updateTime;
        this.type = type;
        this.child = child;
        this.screeningType = screeningType;
        this.userId = userId;
        this.nickName = nickName;
        this.loginName = loginName;
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<DepartMent> getChild() {
        return child;
    }

    public void setChild(List<DepartMent> child) {
        this.child = child;
    }

    public Integer getScreeningType() {
        return screeningType;
    }

    public void setScreeningType(Integer screeningType) {
        this.screeningType = screeningType;
    }

	public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}
