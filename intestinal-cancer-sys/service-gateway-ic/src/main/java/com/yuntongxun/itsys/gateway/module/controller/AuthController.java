package com.yuntongxun.itsys.gateway.module.controller;

import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.yuntongxun.itsys.gateway.common.cache.redis.RedisManager;
import com.yuntongxun.itsys.gateway.common.constants.RedisConstant;
import com.yuntongxun.itsys.gateway.common.util.CookieUtil;
import com.yuntongxun.itsys.gateway.module.pojo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.yuntongxun.itsys.gateway.common.base.AbstractController;
import com.yuntongxun.itsys.gateway.common.constants.CookieConstant;
import com.yuntongxun.itsys.gateway.common.constants.StatusConstant;
import com.yuntongxun.itsys.gateway.common.util.JSONUtil;
import com.yuntongxun.itsys.gateway.common.util.RequestUtil;
import com.yuntongxun.itsys.gateway.common.util.StringUtil;
import com.yuntongxun.itsys.gateway.common.vo.CookieParam;
import com.yuntongxun.itsys.gateway.common.vo.ResultMsg;
import com.yuntongxun.itsys.gateway.module.service.AuthService;
import com.yuntongxun.itsys.gateway.module.service.OperationService;
import com.yuntongxun.itsys.gateway.module.service.UserService;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/auth")
public class AuthController extends AbstractController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;
    @Autowired
    private OperationService operaserv;//操作日志
    @Value("${ytx.gateway.token.expire}")
    private long expire;// 过期时间
    @Value("${spring.profiles.active}")
    private String environment;
    @Autowired
    private RedisManager redis;
    @Autowired
    private DefaultKaptcha loginCaptchaProducer;
    @Value("${auth.image.exTime}")
    private String exTime;
    @Value("${auth.cookie.ip}")
    private String ip;

//    @Autowired
//    private FeignService feignService;

    private final Logger log = LogManager.getLogger(AuthController.class);

    @PostMapping(value = "/sendAuthCode")
    public ResultMsg sendAuthCode(@RequestBody User user) {
        ResultMsg resultMsg = new ResultMsg();

        String phone = user.getLoginName();//可能是手机号登录或者是用户名email登录
        String loginName = "";

        if (StringUtil.isEmpty(phone)) {
            resultMsg.setStatusCode(StatusConstant.LOGIN_NO_PHONE_SEND_CODE);
            resultMsg.setStatusMsg(StatusConstant.LOGIN_NO_PHONE_SEND_MSG);
        } else {
            if ("prod".equals(environment)) {
                User finduser = userService.findByPhone(phone);
                if (finduser != null) {
                    loginName = finduser.getLoginName();
                    resultMsg = userService.getAuthCode(phone, loginName);
                } else {
                    //说明是用户名登录不是手机号，需要查出手机号发送验证码
                    List<User> userList = userService.findByName(phone);
                    if (userList != null && userList.size() > 0) {
                        User userByName = userList.get(0);
                        if (StringUtil.isEmpty(userByName.getPhone())) {
                            resultMsg.setStatusCode(StatusConstant.LOGIN_NO_USER_PHONE_CODE);
                            resultMsg.setStatusMsg(StatusConstant.LOGIN_NO_USER_PHONE_MSG);
                        } else {
                            resultMsg = userService.getAuthCode(userByName.getPhone(), phone);
                        }
                    } else {
                        resultMsg.setStatusCode(StatusConstant.LOGIN_NO_USER_CODE);
                        resultMsg.setStatusMsg(StatusConstant.LOGIN_NO_USER_MSG);
                    }
                }

            } else {
                resultMsg.setStatusCode(StatusConstant.LOGIN_NO_ENVIRONMENT_CODE);
                resultMsg.setStatusMsg(StatusConstant.LOGIN_NO_ENVIRONMENT_MSG);
            }


        }
        return resultMsg;
    }

    /**
     * 登录接口
     *
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody User user, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {
        ThreadContext.push(StringUtil.getUUID());
        try {
            printStartTag("login");
            Cookie cookie = CookieUtil.getCookieByName(request, "login_images_uuId");
            String uuId = "";
            if (cookie != null) {
                uuId = cookie.getValue();
                logger.info("获取图片校验码login_images_uuId={}", uuId);
                user.setUuID(uuId);
            }

            // 登录验证
            ResultMsg resultMsg = userService.selectUserByLoginName(user, response);
//            boolean isRole = userService.queryRoleByLoginName(user.getLoginName());
            if (resultMsg.getStatusCode().equals(StatusConstant.SUCCESS_CODE)) {
                Map<String, Object> map = ( Map<String, Object>)resultMsg.getData();
                //当登录验证完成后图片校验码将不可用
                RequestUtil.setCookie(response, "login_images_uuId", null, 0);
                //当手机号登录时，查出用户名设置到cookie里
                RequestUtil.setCookie(response, CookieConstant.KEY_LOGING_NAME, user.getLoginName(), expire);
                RequestUtil.setCookie(response, CookieConstant.KEY_TOKEN, map.get("token").toString(), expire);
                super.logger.debug("set token success");
                //Map<String, Object> map = new HashMap<>();
                //map.put("token", resultMsg.getData());
                String doctorInfoJson = redis.get(RedisConstant.HOSPITAL_KEY_INFO + user.getLoginName());
                if (StringUtil.notEmpty(doctorInfoJson)) {
                    DoctorInfo doctorInfo = JSONUtil.toBean(doctorInfoJson, DoctorInfo.class);
                    map.put("hospitalType", doctorInfo.getHospitalType());
                    String id = doctorInfo.getId();
                    List<Role> roles = userService.queryRoleByUserId(Integer.parseInt(id));
                    boolean iscommunityType=false;
                    if(roles!=null&&roles.size()>0){
                        iscommunityType=true;
                    }
                    map.put("communityType", iscommunityType);
                }
                resultMsg.setData(map);
            }
            String result = JSONUtil.toJson(resultMsg);

            //写入操作日志
            String url = request.getServletPath();
            List resource = operaserv.getResourceByUrl(url);
            Resource res = null;
            if (resource.size() > 0) {
                res = JSONUtil.toBean(JSONUtil.toJson(resource.get(0)), Resource.class);
                Operation opera = new Operation();
                opera.setUser(user.getLoginName());
                opera.setType(res.getId());
                String content = "登录系统---";
                content += result;
                opera.setContent(content);
                opera.setResult(resultMsg.getStatusCode().equals("000000") ? 0 : 1);
                operaserv.saveOperation(opera);
            }
            super.debug("result：{}", result);
            return result;
        } finally {
            printEndTag("login");
            ThreadContext.removeStack();
        }
    }


/*
    *//*
     * 修改密码
     *
     * @author ${zongt}
     * @since v1.0.0
     *//*
    @RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
    public String updatePwd(@RequestBody User user, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {
        ThreadContext.push(StringUtil.getUUID());

        CookieParam cookieparam = RequestUtil.getTokenByCookie(request);
        if (cookieparam == null || StringUtil.isBlank(cookieparam.getLoginName()) || StringUtil.isBlank(cookieparam.getToken())) {
            logger.warn("Invalid request,cookie info:{}, loginName:{},token:{}",cookieparam,cookieparam==null?null:cookieparam.getLoginName(),cookieparam==null?null:cookieparam.getToken());
            return JSONUtil.toJson(new ResultMsg(StatusConstant.VALIDATE_FAIL_CODE,
                    StatusConstant.COOKIE_NO_TOKEN_LOGINNAME_MSG));
        }

        try {
            printStartTag("updatePwd");
            Cookie cookie = CookieUtil.getCookieByName(request, "update_images_uuId");
            String uuId = "";
            if (cookie != null) {
                uuId = cookie.getValue();
                logger.info("获取图片校验码update_images_uuId={}", uuId);
                user.setUuID(uuId);
            }

            // 登录验证
            ResultMsg resultMsg = userService.updateUserByLoginName(user);
            if (resultMsg.getStatusCode().equals(StatusConstant.SUCCESS_CODE)) {
                RequestUtil.setCookie(response, CookieConstant.KEY_LOGING_NAME, user.getLoginName(), expire);
                RequestUtil.setCookie(response, CookieConstant.KEY_TOKEN, null, 0);
                RequestUtil.setCookie(response, CookieConstant.COOKIE_NICKNAME, null, 0);
                RequestUtil.setCookie(response, CookieConstant.COOKIE_XTYNAME, null, 0);
                RequestUtil.setCookie(response, CookieConstant.COOKIE_XTYPHONE, null, 0);
                super.debug("设置token成功");

                //退出清空redis中的图片以及短信验证码
//                redis.delete(cookieParam.getLoginName());//清除key=用户名验证码
//                List<User> userList = userService.findByName(cookieParam.getLoginName());
//                if (userList != null && userList.size() > 0) {
//                    redis.delete(userList.get(0).getPhone());//清除key=手机号验证码
//                }

            }

            String result = JSONUtil.toJson(resultMsg);

            //写入操作日志
            String url = request.getServletPath();
            List resource = operaserv.getResourceByUrl(url);
            Resource res = null;
            if (resource.size() > 0) {
                res = JSONUtil.toBean(JSONUtil.toJson(resource.get(0)), Resource.class);
                Operation opera = new Operation();
                opera.setUser(user.getLoginName());
                opera.setType(res.getId());
                String content = "登录系统修改旧密码---";
                content += result;
                opera.setContent(content);
                opera.setResult(resultMsg.getStatusCode().equals("000000") ? 0 : 1);
                operaserv.saveOperation(opera);
            }
            super.debug("result：{}", result);
            return result;
        } finally {
            printEndTag("updatePwd");
            ThreadContext.removeStack();
        }
    }*/





    /**
     * 退出登录｛remove Token｝
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/loginout", method = RequestMethod.POST)
    public String loginOut(HttpServletRequest request, HttpServletResponse response) {
        printStartTag("loginOut");

        try {
            CookieParam cookieParam = RequestUtil.getTokenByCookie(request);
            if (cookieParam == null || StringUtil.isBlank(cookieParam.getLoginName())
                    || StringUtil.isBlank(cookieParam.getToken())) {
                if (cookieParam != null) {
                    super.debug("cookie-[YTX_LOGINNAME:{},YTX_TOKEN:{}]", cookieParam.getLoginName(),
                            cookieParam.getToken());
                }
                return JSONUtil.toJson(
                        new ResultMsg(StatusConstant.VALIDATE_FAIL_CODE, StatusConstant.COOKIE_NO_TOKEN_LOGINNAME_MSG));
            }
            ResultMsg resultMsg = authService.removeToken(cookieParam);
            if (resultMsg.getStatusCode().equals(StatusConstant.SUCCESS_CODE)) {
                RequestUtil.setCookie(response, CookieConstant.KEY_LOGING_NAME, cookieParam.getLoginName(), expire);
                RequestUtil.setCookie(response, CookieConstant.KEY_TOKEN, null, 0);
                RequestUtil.setCookie(response, CookieConstant.COOKIE_NICKNAME, null, 0);
                RequestUtil.setCookie(response, CookieConstant.COOKIE_XTYNAME, null, 0);
                RequestUtil.setCookie(response, CookieConstant.COOKIE_XTYPHONE, null, 0);
                super.debug("设置token成功");

                //退出清空redis中的图片以及短信验证码
//                redis.delete(cookieParam.getLoginName());//清除key=用户名验证码
//                List<User> userList = userService.findByName(cookieParam.getLoginName());
//                if (userList != null && userList.size() > 0) {
//                    redis.delete(userList.get(0).getPhone());//清除key=手机号验证码
//                }

            }

            String result = JSONUtil.toJson(resultMsg);


            /**
             * 记录到操作日志表
             */
            String url = request.getServletPath();
            List resource = operaserv.getResourceByUrl(url);
            System.out.println("记录操作日志的url为：" + url);
            Resource res = null;
            if (resource.size() > 0) {
                res = JSONUtil.toBean(JSONUtil.toJson(resource.get(0)), Resource.class);
                Operation opera = new Operation();
                opera.setUser(cookieParam.getLoginName());
                opera.setType(res.getId());
                String content = "退出系统---";
                content += result;
                opera.setContent(content);
                opera.setResult(resultMsg.getStatusCode().equals("000000") ? 0 : 1);
                operaserv.saveOperation(opera);
            }
            super.debug("result：{}", result);
            return result;
        } finally {
            printEndTag("loginOut");
        }
    }

    /**
     * 重置token
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/renewToken", method = RequestMethod.POST)
    public String renewToken(HttpServletRequest request, HttpServletResponse response) {
        ThreadContext.push(StringUtil.getUUID());
        printStartTag("renewToken");
        try {
            // 获取cookie
            CookieParam cookieParam = RequestUtil.getTokenByCookie(request);
            if (cookieParam == null || StringUtil.isBlank(cookieParam.getLoginName())
                    || StringUtil.isBlank(cookieParam.getToken())) {
                if (cookieParam != null) {
                    super.debug("cookie-[YTX_LOGINNAME:{},YTX_TOKEN:{}]", cookieParam.getLoginName(),
                            cookieParam.getToken());
                }
                return JSONUtil.toJson(
                        new ResultMsg(StatusConstant.VALIDATE_FAIL_CODE, StatusConstant.COOKIE_NO_TOKEN_LOGINNAME_MSG));
            }
            // token验证并重置
            ResultMsg resultMsg = authService.renewToken(cookieParam);
            if (resultMsg.getStatusCode().equals(StatusConstant.SUCCESS_CODE)) {
                RequestUtil.setCookie(response, CookieConstant.KEY_LOGING_NAME, cookieParam.getLoginName(), expire);
                RequestUtil.setCookie(response, CookieConstant.KEY_TOKEN, resultMsg.getData().toString(), expire);
                super.debug("renew token success");
                Map<String, Object> map = new HashMap<>();
                map.put("token", resultMsg.getData());
                resultMsg.setData(map);
            }

            String result = JSONUtil.toJson(resultMsg);


            /**
             * 记录到操作日志表
             */
            String url = request.getServletPath();
            List resource = operaserv.getResourceByUrl(url);
            Resource res = null;
            if (resource.size() > 0) {
                res = JSONUtil.toBean(JSONUtil.toJson(resource.get(0)), Resource.class);
                Operation opera = new Operation();
                opera.setUser(cookieParam.getLoginName());
                opera.setType(res.getId());
                String content = "重置token---";
                content += result;
                opera.setContent(content);
                opera.setResult(resultMsg.getStatusCode().equals("000000") ? 0 : 1);
                operaserv.saveOperation(opera);
            }

            super.debug("result：{}", result);
            return result;
        } finally {
            printEndTag("renewToken");
            ThreadContext.removeStack();
        }

    }


    /*
     * 修改密码验证码
     *
     * @author ${zongt}
     * @since v1.0.0
     */
    @RequestMapping(value = "/authCode/updateimages")
    public ModelAndView getUpdateImages(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("@Controller getKaptchaImage Start ");
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        printStartTag("getImage");
        String capText = loginCaptchaProducer.createText();
        log.info("@Controller getImage 验证码={}", capText);
        String uuID = StringUtil.getUUID();

        Cookie cookie = new Cookie("update_images_uuId", uuID);
        log.info("@Controller bindMeetingPage 设置cookie  -----------");
        cookie.setPath("/");
//        cookie.setDomain(ip);
        response.addCookie(cookie);

        log.info("@Controller getImage 将图片验证码存入redis ,capText={}", capText);
        redis.set(StatusConstant.UPDATE_IMAGE_KEY + uuID, capText, Long.parseLong(exTime));

        BufferedImage bi = loginCaptchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            printEndTag("getImage");
            log.info("@Controller getImage End ");
            out.close();
        }
        return null;
    }

    @RequestMapping(value = "/authCode/images")
    public ModelAndView getImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("@Controller getKaptchaImage Start ");
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        printStartTag("getImage");
        String capText = loginCaptchaProducer.createText();
        log.info("@Controller getImage 验证码={}", capText);
        String uuID = StringUtil.getUUID();

        Cookie cookie = new Cookie("login_images_uuId", uuID);
        log.info("@Controller bindMeetingPage 设置cookie  -----------");
        cookie.setPath("/");
//        cookie.setDomain(ip);
        response.addCookie(cookie);

        log.info("@Controller getImage 将图片验证码存入redis ,capText={}", capText);
        redis.set(StatusConstant.REDIS_KEY + uuID, capText, Long.parseLong(exTime));

        BufferedImage bi = loginCaptchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            printEndTag("getImage");
            log.info("@Controller getImage End ");
            out.close();
        }
        return null;
    }


    @Override
    protected Class<?> getClassName() {
        return AuthController.class;
    }
}
