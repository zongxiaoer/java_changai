package com.yuntongxun.itsys.base.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yuntongxun.itsys.base.po.ResetPwd;
import com.yuntongxun.itsys.base.po.User;
import com.yuntongxun.itsys.base.vo.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.exception.ItSysDaoException;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.exception.ItSysServiceException;
import com.yuntongxun.itsys.base.common.util.CookieUtil;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.PageInfo;
import com.yuntongxun.itsys.base.po.ResourceAttr;
import com.yuntongxun.itsys.base.service.AuthService;
import com.yuntongxun.itsys.base.service.ResourceService;
import com.yuntongxun.itsys.base.vo.Response;

/**
 * 权限管理
 *
 * @author Lake.zhang
 */
@RestController
public class AuthController extends AbstractController {

    private final Logger log = LogManager.getLogger(AuthController.class);

    @Autowired
    private AuthService authserv;

    @Autowired
    private ResourceService resvice;

    /**
     * 获取系统菜单、页面、按钮关系 树
     *
     * @param req
     * @param resp
     * @return
     * @throws ItSysException
     * @throws ItSysDaoException
     * @throws ItSysServiceException
     */
    @RequestMapping(value = "/auth/getAuthTree", method = RequestMethod.POST)
    public String getAuthTree(HttpServletRequest req, HttpServletResponse resp) throws ItSysServiceException, ItSysDaoException {
        Object obj = authserv.getAuthTree();
        return JSONUtils.toJson(new Response(obj));
    }

    /**
     * 获取系统菜单关系树  菜单树
     *
     * @param req
     * @param resp
     * @return
     * @throws ItSysException
     */
    @PostMapping(value = "/auth/getMenuTree")
    public String getMenuTree(HttpServletRequest req, HttpServletResponse resp) throws ItSysException {
        Object obj = authserv.getMenuTree();
        return JSONUtils.toJson(new Response(obj));
    }


    /**
     * 获取系统菜单页面关系树  菜单页面树  去除按钮
     *
     * @param req
     * @param resp
     * @return
     * @throws ItSysException
     */
    @PostMapping(value = "/auth/getMenuPageTree")
    public String getMenuPageTree(HttpServletRequest req, HttpServletResponse resp) throws ItSysException {
        Object obj = authserv.getMenuPageTree();
        return JSONUtils.toJson(new Response(obj));
    }

    /**
     * 保存分配的权限数据
     *
     * @param req
     * @param resp
     * @param body
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/auth/saveAuth", method = RequestMethod.POST)
    public String saveAuthParm(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
        log.info("@Controller saveAuthParm接收参数为:{}", body);
        try {
            authserv.saveAuthParm(body);
        } catch (ItSysException e) {
            log.warn("@Controller saveAuthParm  error......message={}",e.getMessage());
            return JSONUtils.toJson(new Response("error",e.getMessage()));
        }
        return JSONUtils.toJson(new Response());
    }

    /**
     * 根据条件查询权限数据
     *
     * @param req
     * @param resp
     * @param body
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/auth/queryAuth", method = RequestMethod.POST)
    public String queryAuthParm(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) throws ItSysException {
        log.info("@Controller queryAuthParm接收参数为:{}", body);
        List list = authserv.queryAuthParm(body);

        return JSONUtils.toJson(new Response(list));
    }


    @RequestMapping(value = "/auth/queryPageAuth", method = RequestMethod.POST)
    public String queryAuthPage(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) throws ItSysException {
        log.info("@Controller queryAuthPage接收参数为:{}", body);
        JsonObject json = new JsonParser().parse(body).getAsJsonObject();
        ListPageUtil resource = authserv.queryAuthPage(json);
        PageInfo pageinfo = resource.getPageInfo();
        log.info("queryAuthPage 返回值:{},{}", resource.getResultList(), pageinfo);
        return JSONUtils.objectToJsonDateSerializer(new Response(resource.getResultList(), pageinfo), "yyyy-MM-dd HH:mm:ss");
    }


    @RequestMapping(value="/auth/get/{id}",method=RequestMethod.POST)
    public String getAuthById(HttpServletRequest req,HttpServletResponse resp,@PathVariable("id") String id) throws ItSysException{
        printStartTag("getAuthById");
        printHttpPacket(req, null);
        log.info("getAuthById 输入参数：{}",id);
        List list = authserv.getAuthParmById(id);
        Object obj=null;
        if(list.size()>0){
            obj=list.get(0);
        }
        log.info("getAuthById 返回值: {}",obj);
        printEndTag("getAuthById");
        return JSONUtils.objectToJsonDateSerializer(new Response(obj), "yyyy-MM-dd HH:mm:ss");
    }


    /**
     * 修改权限数据
     *
     * @param req
     * @param resp
     * @param body
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/auth/updateAuth", method = RequestMethod.POST)
    public String updateAuthParm(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) throws ItSysException {
        log.info("@Controller updateAuthParm接收参数为:{}", body);
        authserv.updateAuthParm(body);
        return JSONUtils.toJson(new Response());
    }

    /**
     * 根据id删除权限数据
     *
     * @param req
     * @param resp
     * @param id
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/auth/delAuth/{id}", method = RequestMethod.POST)
    public String delAuthParm(HttpServletRequest req, HttpServletResponse resp, @PathVariable("id") String id) throws ItSysException {
        log.info("@Controller delAuthParm接收参数为:{}", id);
        authserv.delAuthParm(id);
        return JSONUtils.toJson(new Response());
    }

    /**
     * 获取用户登录权限
     *
     * @param req
     * @param resp
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/user/resource", method = {RequestMethod.POST, RequestMethod.GET})
    public String getUserResource(HttpServletRequest req, HttpServletResponse resp) throws ItSysException {
        String Name = CookieUtil.getCookieByLoginName(req);
        log.info("getUserResource 输入参数: {}", Name);
        ResourceAttr resource = new ResourceAttr();
        resource = (ResourceAttr) authserv.getUserResource(Name);
//        log.info("getUserResource 返回值: {}", JSONUtils.toJson(new Response(resource)));
        return JSONUtils.toJson(new Response(resource));
    }

    /**
     * 重置密码
     * @param user
     * @return
     * @throws ItSysException
     */
    @PostMapping(value = "/auth/pwd/reset")
    public Result retrievePassword(@RequestBody ResetPwd resetPwd) throws ItSysException{
        log.info("======== @Controller retrievePassword 重置密码 Start 接收参数 = {} ========", JSONUtils.toJson(resetPwd));

        Result result = this.authserv.resetUserPwd(resetPwd);

        log.info("======== @Controller 返回结果集 result = {} End", JSONUtils.toJson(result));
        return result;
    }

    /**
     * 发送短信验证码
     * @param user
     * @return
     */
    @PostMapping(value = "/auth/smsvcode/pwd/reset")
    public Result sendAuthCode(@RequestBody User user) {
        log.info("======= @Controller sendAuthCode 发送验证码 接收参数 = {} ========", JSONUtils.toJson(user));

        Result result = this.authserv.sendAuthCode(user);

        log.info("======= @Controller sendAuthCode 返回结果集 result = {} =======", JSONUtils.toJson(result));
        return result;

    }

    @Override
    protected String getClassName() {
        return ResourceController.class.getName();
    }


}
