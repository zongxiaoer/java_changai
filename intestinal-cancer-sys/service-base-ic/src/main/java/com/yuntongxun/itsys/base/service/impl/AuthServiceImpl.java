package com.yuntongxun.itsys.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.yuntongxun.itsys.base.common.util.*;
import com.yuntongxun.itsys.base.dao.UserDao;
import com.yuntongxun.itsys.base.po.*;
import com.yuntongxun.itsys.base.vo.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yuntongxun.itsys.base.cache.RedisManager;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysDaoException;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.exception.ItSysServiceException;
import com.yuntongxun.itsys.base.dao.AuthDao;
import com.yuntongxun.itsys.base.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final Logger log = LogManager.getLogger(AuthServiceImpl.class);
    @Autowired
    private AuthDao authdao;

    @Autowired
    private RedisManager redis;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserDao userDao;

    @Value("${login.authCode.codeTime}")
    private String codeTime;

    @Autowired
    private SendSms sendSms;

    @Autowired
    private DefaultKaptcha captchaProducer;

    @Override
    public Object getAuthTree() throws ItSysServiceException, ItSysDaoException {
        List list = (List) authdao.getAuthTree();
        AuthModel auth = null;
        List<AuthModel> menuList = new ArrayList();
        List<AuthModel> root = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            AuthModel menus = null;
            String json = JSONUtils.toJson(list.get(i));
            menus = (AuthModel) JSONUtils.JsonToObject(json, AuthModel.class);
            menuList.add(menus);
        }
        for (AuthModel tree : menuList) {
            if (tree.getpId().equals("0")) {
                root.add(tree);
            }
            for (AuthModel t : menuList) {
                String pid = t.getpId() + "";
                if (pid.equals(tree.getId() + "")) {
                    if (tree.getChild() == null) {
                        List<AuthModel> myChildrens = new ArrayList<AuthModel>();
                        myChildrens.add(t);
                        tree.setChild(myChildrens);
                    } else {
                        tree.getChild().add(t);
                    }
                }
            }
        }
        for (AuthModel tree : root) {
            auth = tree;
            System.out.println(tree.toString());
        }


        return auth;
    }

    @Override
    @Transactional
    public void saveAuthParm(String body) throws ItSysException {
        // TODO Auto-generated method stub
        AuthModel auth = JSONUtils.toBean(body, AuthModel.class);
        String name = auth.getName();
//		ListPageUtil authList = authdao.queryAuthParm(null, name, null,null,1,-1);//查询唯一标识是否存在
        List authRepeat = authdao.queryAuthRepeat(name);//查询唯一标识是否重复
        if (authRepeat != null && authRepeat.size() > 0) {
            log.warn("@Service saveAuthParm 唯一标识重复  name={}", name);
            throw new ItSysException(Constans.ERR_AUTH_EXTENDS_CODE, Constans.ERR_AUTH_EXTENDS_MSG);
        }
        if (StringUtils.isEmpty(auth.getpId())) {
            log.warn("@Service saveAuthParm Pid为空");
            throw new ItSysException(Constans.ERR_AUTH_PID_NULL_CODE, Constans.ERR_AUTH_PID_NULL_MSG);
        }
        authdao.saveAuthParm(auth);
    }

    @Override
    public List getAuthParmById(String id) throws ItSysException {
        log.info("@Service getAuthParmById Start  ");
        List list = authdao.getAuthParmById(id);
        log.info("@Service getAuthParmById End  ");
        return list;
    }

    @Override
    @Transactional
    public void updateAuthParm(String body) throws ItSysException {
        AuthModel auth = JSONUtils.toBean(body, AuthModel.class);
        List authList = authdao.getAuthParmById(auth.getId() + "");//查询是否存在该修改对象
        if (authList != null && authList.size() < 0) {
            throw new ItSysException(Constans.ERR_AUTH_NULL_CODE, Constans.ERR_AUTH_NULL_MSG);
        }
        String name = auth.getName();
//		ListPageUtil authList2 = authdao.queryAuthParm(null, name, null,null,1,-1);//查询唯一标识是否重复
        List authRepeat = authdao.queryAuthRepeat(name);//查询唯一标识是否重复
        if (authRepeat != null && authRepeat.size() >= 2) {
            throw new ItSysException(Constans.ERR_AUTH_EXTENDS_CODE, Constans.ERR_AUTH_EXTENDS_MSG);
        }
        authdao.updateAuthParm(auth);
    }

    @Override
    @Transactional
    public void delAuthParm(String id) throws ItSysException {
        authdao.delAuthParm(id);
    }

    @Override
    public List queryAuthParm(String body) throws ItSysException {
        JsonObject json = new JsonParser().parse(body).getAsJsonObject();
        String id = json.get("id") == null ? "" : json.get("id").getAsString();
        String name = json.get("name") == null ? "" : json.get("name").getAsString();
        String type = json.get("type") == null ? "" : json.get("type").getAsString();
        String url = json.get("url") == null ? "" : json.get("url").getAsString();
        int pageNo = json.get("pageNo") == null ? 1 : json.get("pageNo").getAsInt();
        int pageSize = json.get("pageSize") == null ? -1 : json.get("pageSize").getAsInt();
        ListPageUtil listPage = authdao.queryAuthParm(id, name, type, url, pageNo, pageSize);
        return listPage.getResultList();
    }


    @Override
    public ListPageUtil queryAuthPage(JsonObject json) throws ItSysException {
        String id = json.get("id") == null ? "" : json.get("id").getAsString();
        String name = json.get("name") == null ? "" : json.get("name").getAsString();
        String type = json.get("type") == null ? "" : json.get("type").getAsString();
        String url = json.get("url") == null ? "" : json.get("url").getAsString();
        int pageNo = json.get("pageNo") == null ? 0 : json.get("pageNo").getAsInt();
        int pageSize = json.get("pageSize") == null ? -1 : json.get("pageSize").getAsInt();
        ListPageUtil listPage = authdao.queryAuthParm(id, name, type, url, pageNo, pageSize);

//        List endList = new ArrayList();
//        if (list.size() > 0 && list != null) {
//            for (int i = 0; i < list.size(); i++) {
//                JsonObject jsonObject = new JsonParser().parse(JSONUtils.toJson(list.get(i))).getAsJsonObject();
//                String key = "type_" + (jsonObject.get("type").isJsonNull() ? null : jsonObject.get("type").getAsString());
//                String typeName = redis.get(key);
//                if (StringUtils.isEmpty(typeName)) {
//                    dictionaryService.queryDictionaryOnCache();// 刷新数据库数据及缓存
//                    typeName = redis.get(key);
//                }
//                jsonObject.addProperty("type", typeName);
//                endList.add(jsonObject);
//            }	
//        }

//        List resultList = ListPageUtil.getPageList(endList, pageNo, pageSize);
        return listPage;
    }

    @Override
    public Object getUserResource(String Name) throws ItSysException {
        // List<Menu> menu=menudao.menuQueryOnLogin(Name);
        List<Menu> list = new ArrayList();
        list = authdao.menuQueryOnLogin(Name);
        Menu menu = null;
        List<Menu> menuList = new ArrayList();
        List<Menu> root = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Menu menus = null;
            String json = JSONUtils.toJson(list.get(i));
            menus = (Menu) JSONUtils.JsonToObject(json, Menu.class);
            menuList.add(menus);
        }
        for (Menu tree : menuList) {
            if (tree.getpId().equals("0")) {
                root.add(tree);
            }
            for (Menu t : menuList) {
                int pid = Integer.parseInt(t.getpId());
                if (pid == tree.getId()) {
                    if (tree.getChild() == null) {
                        List<Menu> myChildrens = new ArrayList<Menu>();
                        myChildrens.add(t);
                        tree.setChild(myChildrens);
                    } else {
                        tree.getChild().add(t);
                    }
                }
            }
        }
        for (Menu tree : root) {
            menu = tree;
            System.out.println(tree.toString());
        }

        List page = (List) getPageResource(Name);
        List button = (List) getButtonResource(Name);
        List request = (List) getMenuRequests(Name);
        ResourceAttr res = new ResourceAttr();
        res.setMenu(menu);
        res.setButtons(button);
        res.setPages(page);
        res.setRequests(request);
        return res;
    }


    @Override
    public Object getPageResource(String Name) throws ItSysException {
        log.info("@Service getPageResource Start");
        if (StringUtil.isEmpty(Name)) {
            throw new ItSysException(
                    Constans.ERR_USER_NAME_CODE, Constans.ERR_USER_NAME_MSG);
        }
        log.info("@Service getPageResource End");
        return authdao.getPageResource(Name);
    }

    @Override
    public Object getButtonResource(String Name) throws ItSysException {
        log.info("@Service getButtonResource Start");
        if (StringUtil.isEmpty(Name)) {
            throw new ItSysException(
                    Constans.ERR_USER_NAME_CODE, Constans.ERR_USER_NAME_MSG);
        }
        log.info("@Service getButtonResource End");
        return authdao.getButtonResource(Name);
    }

    @Override
    public Object getMenuRequests(String Name) throws ItSysException {
        log.info("@Service getMenuRequests Start");
        if (StringUtil.isEmpty(Name)) {
            throw new ItSysException(
                    Constans.ERR_USER_NAME_CODE, Constans.ERR_USER_NAME_MSG);
        }
        log.info("@Service getMenuRequests End");
        return authdao.getMenuRequests(Name);
    }

    @Override
    public Object getMenuTree() throws ItSysException {
        log.info("@Service getAuthTree Strat  ");
        List list = (List) authdao.getMenuTree();
        AuthModel auth = null;
        List<AuthModel> menuList = new ArrayList();
        List<AuthModel> root = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            AuthModel menus = null;
            String json = JSONUtils.toJson(list.get(i));
            menus = (AuthModel) JSONUtils.JsonToObject(json, AuthModel.class);
            menuList.add(menus);
        }
        for (AuthModel tree : menuList) {
            if (tree.getpId().equals("0")) {
                root.add(tree);
            }
            for (AuthModel t : menuList) {
                String pid = t.getpId() + "";
                if (pid.equals(tree.getId() + "")) {
                    if (tree.getChild() == null) {
                        List<AuthModel> myChildrens = new ArrayList<AuthModel>();
                        myChildrens.add(t);
                        tree.setChild(myChildrens);
                    } else {
                        tree.getChild().add(t);
                    }
                }
            }
        }
        for (AuthModel tree : root) {
            auth = tree;
            System.out.println(tree.toString());
        }


        log.info("@Service getAuthTree End  ");
        return auth;
    }

    @Override
    public Object getMenuPageTree() throws ItSysException {
        log.info("@Service getAuthTree Strat  ");
        List list = (List) authdao.getMenuPageTree();
        AuthModel auth = null;
        List<AuthModel> menuList = new ArrayList();
        List<AuthModel> root = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            AuthModel menus = null;
            String json = JSONUtils.toJson(list.get(i));
            menus = (AuthModel) JSONUtils.JsonToObject(json, AuthModel.class);
            menuList.add(menus);
        }
        for (AuthModel tree : menuList) {
            if (tree.getpId().equals("0")) {
                root.add(tree);
            }
            for (AuthModel t : menuList) {
                String pid = t.getpId() + "";
                if (pid.equals(tree.getId() + "")) {
                    if (tree.getChild() == null) {
                        List<AuthModel> myChildrens = new ArrayList<AuthModel>();
                        myChildrens.add(t);
                        tree.setChild(myChildrens);
                    } else {
                        tree.getChild().add(t);
                    }
                }
            }
        }
        for (AuthModel tree : root) {
            auth = tree;
            System.out.println(tree.toString());
        }


        log.info("@Service getAuthTree End  ");
        return auth;
    }


    @Override
    public Result resetUserPwd(ResetPwd resetPwd) throws ItSysException {
        Result result = new Result();
        String phone = resetPwd.getPhone();
        // 判断验证码是否为空
        String vcode = resetPwd.getVcode();
        if (StringUtil.isEmpty(vcode)) {
            return ResultUtil.success(Constans.ERR_AUTH_CODE_NULL_CODE, Constans.ERR_AUTH_CODE_NULL_MSG);
        }
        // 判断手机号是否为空
        if (StringUtil.isEmpty(phone)) {
            return ResultUtil.success(Constans.ERR_AUTH_PHONE_NULL_CODE, Constans.ERR_AUTH_PHONE_NULL_MSG);
        }
        // 判断密码是否为空
        String pwd = resetPwd.getPwd();
        if (StringUtil.isEmpty(pwd)) {
            return ResultUtil.success(Constans.ERR_AUTH_PWD_NULL_CODE, Constans.ERR_AUTH_PWD_NULL_MSG);
        }
        // 判断重新输入密码是否为空
        String againPwd = resetPwd.getAgainPwd();
        if (StringUtil.isEmpty(againPwd)) {
            return ResultUtil.success(Constans.ERR_AGAIN_PWD_NULL_CODE, Constans.ERR_AGAIN_PWD_NULL_MSG);
        }

        List<User> userList = this.userDao.findByPhone(phone);
        if (userList.size() > 1) {
            result = ResultUtil.success(Constans.ERR_AUTH_USER_SOLE_CODE, Constans.ERR_AUTH_USER_SOLE_MSG);
        } else {
            String authCodeByPhone = redis.get(Constans.TOKEN_KEY_PASS_PREFIX + phone);
            if (StringUtil.isEmpty(authCodeByPhone)) {
                result = ResultUtil.success(Constans.ERR_AUTH_CODE_CODE, Constans.ERR_AUTH_CODE_MSG);
            } else {
                if (authCodeByPhone.equals(vcode)) {
                    if (pwd.equals(againPwd)) {
                        this.userDao.updateReset(resetPwd);
                    } else {
                        result = ResultUtil.success(Constans.ERR_AUTH_USER_PWD_CODE, Constans.ERR_AUTH_USER_PWD_MSG);
                    }
                } else {
                    result = ResultUtil.success(Constans.ERR_AUTH_CODE_ERR_CODE, Constans.ERR_AUTH_CODE_ERR_MSG);
                }
            }

        }
        return result;
    }

    /**
     * 获取随机数
     * @return
     */
    public String getNumber() {
        String result = null;
        result = captchaProducer.createText();
        return result;
    }

    @Override
    public Result sendAuthCode(User user) throws ItSysException {
        Result result = new Result();
        String phone = user.getPhone();
        if (StringUtil.isEmpty(phone)) {
            result = ResultUtil.success(Constans.ERR_AUTH_PHONE_NULL_CODE, Constans.ERR_AUTH_PHONE_NULL_MSG);
        } else {
            User findUser = userService.findByPhone(phone);
            if (findUser != null) {
                String[] parm = new String[2];
                String codeNum = this.getNumber();
                parm[0] = codeNum;//自己生成的四位随机数
                parm[1] = "3";//短信验证码时效3分钟
                boolean authResult = this.sendSms.getAuthCode(phone, "244695", parm);
                if (authResult == true) {
                    redis.set(Constans.TOKEN_KEY_PASS_PREFIX + phone, codeNum, Long.parseLong(codeTime));
                    result = ResultUtil.success(Constans.KEY_SUCCESS_STATUS, Constans.KEY_SUCCESS_MSG);
                } else {
                    result = ResultUtil.success(Constans.ERR_AUTH_MSG_NULL_CODE, Constans.ERR_AUTH_MSG_NULL_MSG);
                }
            } else {
                result = ResultUtil.success(Constans.ERR_AUTH_USER_NULL_CODE, Constans.ERR_AUTH_USER_NULL_MSG);
            }

        }
        return result;
    }


}
