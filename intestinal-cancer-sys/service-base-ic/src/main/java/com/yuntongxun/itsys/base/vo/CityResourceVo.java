package com.yuntongxun.itsys.base.vo;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: CityResourceVo
 * Author:   zongtong
 * Date:     2018/9/5 下午5:17
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/9/5 下午5:17           v1.0.0
 */

import com.yuntongxun.itsys.base.po.cancer.CityResourcePO;

import java.util.List;

public class CityResourceVo  extends CityResourcePO {
    private List<CityResourcePO> cityResourcePOS;

    private String label;

    private String value;

    public List<CityResourcePO> getCityResourcePOS() {
        return cityResourcePOS;
    }

    public void setCityResourcePOS(List<CityResourcePO> cityResourcePOS) {
        this.cityResourcePOS = cityResourcePOS;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
