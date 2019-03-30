/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: BiServiceImpl
 * Author:   sun
 * Date:     2018/9/5 11:35
 * History:
 * <author>          <time>                <version>
 * sun           2018/9/5 11:35           v1.0.0
 */
package com.yuntongxun.itsys.base.service.impl;

import com.yuntongxun.itsys.base.common.util.HttpSendUtil;
import com.yuntongxun.itsys.base.dao.UserDao;
import com.yuntongxun.itsys.base.po.*;
import com.yuntongxun.itsys.base.service.BiService;
import com.yuntongxun.itsys.base.vo.UserPramVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 一句话功能简述
 *
 * @author sun
 * @create 2018/9/5
 * @since v1.0.0
 */
@Service
public class BiServiceImpl implements BiService {
    @Autowired
    private UserDao userdao;

    @Override
    public String sendAccessToken(String method, String path, int timeout, int readTimeout, String accessToken) throws Exception{
        return HttpSendUtil.defaultConnection(method, path, timeout, readTimeout, accessToken);
    }

    @Override
    public UserPramVo getBiAuthorityByLoginName(String loginName) {
        UserPramVo userPram = new UserPramVo();
        User user = userdao.selectUserByLoginName(loginName);
        userPram.setUserName(user.getNickName());
        DepartMent department = userdao.findbyUserId(loginName);
        userPram.setCompanyId(department.getId());
        if("1".equals(department.getType())){//社区
            userPram.setIdentity("community");
        }else if("2".equals(department.getType())){//地区
            userPram.setIdentity("area");
        }else if("3".equals(department.getType())){//国家
            userPram.setIdentity("country");
        }

        List<UserPramVo.MenuBean> menu = new ArrayList<>();

        List<BiMenu> all = userdao.getAllMenuByLoginName(loginName);
        for (BiMenu menuPO : all) {
            UserPramVo.MenuBean sm = new UserPramVo.MenuBean();
            sm.setId(menuPO.getId());
            menu.add(sm);
        }
        userPram.setMenu(menu);
        List<BiBusinessScenario> allBs = userdao.getAllBusinessScenario(loginName);
//            List<String> collect = allBs.stream().map(n -> n.getId()).collect(Collectors.toList());
        List<String> collect = new ArrayList<String>();
        for(BiBusinessScenario BusinessScenarioPO : allBs){
            collect.add(BusinessScenarioPO.getId());
        }
        userPram.setBsIds(collect);
        List<UserPramVo.ActionsBean> actions = new ArrayList<UserPramVo.ActionsBean>();
        List<BiAction> acArr = userdao.getAllAction(loginName);
        for (BiAction actionPO : acArr) {
            UserPramVo.ActionsBean actionsBean = new UserPramVo.ActionsBean();
            actionsBean.setId(actionPO.getActionkey());
            actionsBean.setValue("true");
            actions.add(actionsBean);
        }
        userPram.setActions(actions);
        return userPram;
    }
}
