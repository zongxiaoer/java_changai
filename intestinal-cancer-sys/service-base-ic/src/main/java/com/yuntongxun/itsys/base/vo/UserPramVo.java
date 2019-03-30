/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: UserPramVo
 * Author:   sun
 * Date:     2018/9/5 14:21
 * History:
 * <author>          <time>                <version>
 * sun           2018/9/5 14:21           v1.0.0
 */
package com.yuntongxun.itsys.base.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 一句话功能简述
 *
 * @author sun
 * @create 2018/9/5
 * @since v1.0.0
 */
public class UserPramVo implements Serializable {
    //userId
    private String userId;
    //公司id
    private String companyId;

    private String accessToken;

    private String userName;

    private String status;

    private String link;
    private List<String> bsIds;
    private List<MenuBean> menu;
    private List<ActionsBean> actions;

    //身份特征 community-社区,area-地区,country-国家
    private String identity;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<String> getBsIds() {
        return bsIds;
    }

    public void setBsIds(List<String> bsIds) {
        this.bsIds = bsIds;
    }

    public List<MenuBean> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuBean> menu) {
        this.menu = menu;
    }

    public List<ActionsBean> getActions() {
        return actions;
    }

    public void setActions(List<ActionsBean> actions) {
        this.actions = actions;
    }

    public static class MenuBean {
        /**
         * id : 菜单id
         * dashboardIds : 仪表盘id集合
         */

        private String id;
        private String dashboardIds;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDashboardIds() {
            return dashboardIds;
        }

        public void setDashboardIds(String dashboardIds) {
            this.dashboardIds = dashboardIds;
        }
    }

    public static class ActionsBean {
        /**
         * id : 动作字典表id
         * value : true/false
         */

        private String id;
        private String value;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
