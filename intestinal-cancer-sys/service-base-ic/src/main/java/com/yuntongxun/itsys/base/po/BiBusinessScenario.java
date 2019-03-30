/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: BiBusinessScenario
 * Author:   sun
 * Date:     2018/9/6 10:03
 * History:
 * <author>          <time>                <version>
 * sun           2018/9/6 10:03           v1.0.0
 */
package com.yuntongxun.itsys.base.po;

import java.io.Serializable;

/**
 * 一句话功能简述
 *
 * @author sun
 * @create 2018/9/6
 * @since v1.0.0
 */
public class BiBusinessScenario implements Serializable {
    private String id;

    private String name;

    private String message;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }
}