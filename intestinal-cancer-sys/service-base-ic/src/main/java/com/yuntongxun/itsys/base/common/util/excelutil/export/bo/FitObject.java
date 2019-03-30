package com.yuntongxun.itsys.base.common.util.excelutil.export.bo;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: FitObject
 * Author:   zongtong
 * Date:     2018/8/22 上午9:11
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/8/22 上午9:11           v1.0.0
 */

import com.yuntongxun.itsys.base.vo.FitResultVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FitObject implements ExportObject {
    private ArrayList list = new ArrayList();
    private List<FitResultVo> fitResultVos;

    public ArrayList getList() {
        return list;
    }

    public void setList(ArrayList list) {
        this.list = list;
    }

    public FitObject( List<FitResultVo> fitResultVos) {
        if(fitResultVos!=null&&fitResultVos.size()>0){
            int i=1;
            for (FitResultVo fitResultVoL:fitResultVos){
                fitResultVoL.setNumber(i++);
            }
            list.addAll(fitResultVos);
        }
    }
}
