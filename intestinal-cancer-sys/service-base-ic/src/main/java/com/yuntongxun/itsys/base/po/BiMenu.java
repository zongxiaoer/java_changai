/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: BiMenu
 * Author:   sun
 * Date:     2018/9/6 10:02
 * History:
 * <author>          <time>                <version>
 * sun           2018/9/6 10:02           v1.0.0
 */
package com.yuntongxun.itsys.base.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户对应的BI项目菜单
 *
 * @author sun
 * @create 2018/9/6
 * @since v1.0.0
 */
public class BiMenu implements Serializable {
    private String id;

    private String menuname;

    private String message;

    private String status;

    private String applicationdate;

    private Date applicationtime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname == null ? null : menuname.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getApplicationdate() {
        return applicationdate;
    }

    public void setApplicationdate(String applicationdate) {
        this.applicationdate = applicationdate == null ? null : applicationdate.trim();
    }

    public Date getApplicationtime() {
        return applicationtime;
    }

    public void setApplicationtime(Date applicationtime) {
        this.applicationtime = applicationtime;
    }
}