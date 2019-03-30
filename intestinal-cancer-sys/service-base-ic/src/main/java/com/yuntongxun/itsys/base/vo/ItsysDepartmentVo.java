package com.yuntongxun.itsys.base.vo;

/**
 * 社区+未预约
 * @author zongt
 * @date 2018/4/24
 */
public class ItsysDepartmentVo {
    private String  communityName;  //社区名称
    private Integer allsums;//未预约
    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public Integer getAllsums() {
        return allsums;
    }

    public void setAllsums(Integer allsums) {
        this.allsums = allsums;
    }
}
