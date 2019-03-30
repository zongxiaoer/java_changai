/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: BiController
 * Author:   sun
 * Date:     2018/9/5 11:22
 * History:
 * <author>          <time>                <version>
 * sun           2018/9/5 11:22           v1.0.0
 */
package com.yuntongxun.itsys.base.controller;

import com.yuntongxun.itsys.base.cache.RedisManager;
import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.constans.RedisConstant;
import com.yuntongxun.itsys.base.common.constans.StatusConstant;
import com.yuntongxun.itsys.base.common.util.EncryptUtil;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.service.BiService;
import com.yuntongxun.itsys.base.vo.Response;
import com.yuntongxun.itsys.base.vo.UserPramVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 与BI项目交互
 *
 * @author sun
 * @create 2018/9/5
 * @since v1.0.0
 */
@RestController
public class BIController extends AbstractController {
    private final Logger log = LogManager.getLogger(BIController.class);

    @Autowired
    private BiService biService;

    @Autowired
    private RedisManager redisManager;

    @Value("${ytx.base.bi.token.biexpire}")
    private long expire;

    @Value("${ytx.base.bi.path}")
    private String BIPath;

    @Value("${ytx.base.bi.timeout}")
    private int timeout;

    @Value("${ytx.base.bi.readTimeout}")
    private int readTimeout;






    @RequestMapping(value = "/sendToken")
    public String sendToken(@RequestBody UserPramVo userPramVo){
        String key =  userPramVo.getUserId() + RedisConstant.HOSPITAL_BI_TOKEN_KEY;
        String token = StringUtil.getUUID();
        userPramVo.setAccessToken(token);

        String method = "POST";
        String path = BIPath ;
        String result = "";
        try{
            result = biService.sendAccessToken(method,path,timeout,readTimeout,JSONUtils.toJson(userPramVo));
        }catch (Exception e){
            e.printStackTrace();
        }

        if("success".equals(result)){
            redisManager.set(key,token,expire);
            return JSONUtils.toJson(new Response("发送accessToken成功"));
        }else{
            return JSONUtils.toJson(new Response(StatusConstant.ACCESS_TOKEN_SEND_ERROR_CODE,StatusConstant.ACCESS_TOKEN_SEND_ERROR_MSG,"发送accessToken成功"));
        }
    }

    @RequestMapping(value = "/checkToken")
    public void checkToken(@RequestBody UserPramVo userPramVo, HttpServletResponse response){
        UserPramVo userPram = new UserPramVo();
        String userId =  userPramVo.getUserId();
        String accessToken = userPramVo.getAccessToken();
        String token = redisManager.get(userId + RedisConstant.HOSPITAL_BI_TOKEN_KEY);
        if(accessToken.equalsIgnoreCase(EncryptUtil.md5(userId + token))){
            //查询登录人信息
            userPram = biService.getBiAuthorityByLoginName(userId);
            userPram.setStatus("success");
        }else{
            //返回错误
            userPram.setStatus("error");
        }

        PrintWriter out = null;

        try {
            response.setCharacterEncoding("UTF-8");
            out = response.getWriter();
            out.write(JSONUtils.toJson(userPram));
            redisManager.delete(userId + RedisConstant.HOSPITAL_BI_TOKEN_KEY);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    @Override
    protected String getClassName() {
        return BIController.class.getName();
    }
}
