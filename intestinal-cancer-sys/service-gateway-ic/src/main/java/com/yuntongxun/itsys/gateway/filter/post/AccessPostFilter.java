package com.yuntongxun.itsys.gateway.filter.post;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.yuntongxun.itsys.gateway.common.constants.CacheConstant;
import com.yuntongxun.itsys.gateway.common.constants.FilterConstant;
import com.yuntongxun.itsys.gateway.common.constants.StatusConstant;
import com.yuntongxun.itsys.gateway.common.simple.SimpleLogger;
import com.yuntongxun.itsys.gateway.common.util.RequestUtil;
import com.yuntongxun.itsys.gateway.common.util.StringUtil;
import com.yuntongxun.itsys.gateway.common.vo.CookieParam;
import com.yuntongxun.itsys.gateway.module.dao.AuthDao;

public class AccessPostFilter extends ZuulFilter{
    
    private final SimpleLogger logger = new SimpleLogger(AccessPostFilter.class);
    @Autowired
    private AuthDao authDao;
    
    @Value("${ytx.gateway.permissions.expire}")
    private long expire;
    
    
    

    @Override
    public boolean shouldFilter() {
        logger.printStartTag("post filter");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String requestUrl = request.getRequestURI();
        requestUrl = requestUrl.substring(StringUtils.indexOf(requestUrl, "/", 1));
        String[] requestUrlArr = requestUrl.split("/");
        logger.info("response statusCode is {}",ctx.getResponseStatusCode());
        //匹配到的url拦截
        if(RequestUtil.matchUrl(requestUrlArr, FilterConstant.PERMISSIONS_URL)){
           // logger.debug(request.getRequestURI()+" 进入拦截");
            return true;
        }
        logger.info("not intercept url is {}",request.getRequestURL().toString());
        logger.printEndTag("post filter");
        ThreadContext.removeStack();
        return false;
    }

    @Override
    public Object run() {
        
        try{
            RequestContext ctx = RequestContext.getCurrentContext();
            HttpServletRequest request = ctx.getRequest();
            HttpServletResponse response = ctx.getResponse();

            logger.info("post filter,contentType: {}",response.getContentType());
            logger.info("post filter,characterEncoding: {}",response.getCharacterEncoding());
            
            if(response.getContentType().indexOf("octet-stream")>=0){
            	return null;
            }
//            response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            logger.debug("request url is {}}",request.getRequestURL().toString());
            String responseBody = ctx.getResponseBody();
            if(StringUtil.isBlank(responseBody)){
                InputStream is = ctx.getResponseDataStream();
                if(is == null){
                    logger.info(request.getRequestURI()+"拦截数据为null");
                    return null;
                }
                StringBuilder sb = new StringBuilder();   
                String line = null;
                try {
//                 InputStream inputStream = new GZIPInputStream(is);
                    BufferedReader reader=new BufferedReader(new InputStreamReader(is,"UTF-8"));
                    while ((line = reader.readLine()) != null) {      
                        sb.append(line);      
                    }
                    responseBody = sb.toString();
             } catch (Exception e) {
                 logger.error("获取拦截数据异常", e);
                 return null;
             }  
            }
            
            logger.debug("拦截到的信息：{}",responseBody);
            //设置权限到缓存
            JsonObject obj = new JsonParser().parse(responseBody).getAsJsonObject();
            if(obj.has(StatusConstant.STATUS_CODE_STR) 
                    && StatusConstant.SUCCESS_CODE.equals(obj.get(StatusConstant.STATUS_CODE_STR).getAsString()) 
                    && obj.has(StatusConstant.DATA_STR)){
                JsonObject data = obj.get(StatusConstant.DATA_STR).getAsJsonObject();
                if(data.has(StatusConstant.REQUESTS_STR)){
                    //获取cookie
                    CookieParam cookie =  RequestUtil.getTokenByCookie(request);
                    authDao.setPermissions(CacheConstant.PERMISSIONS_KEY_PREFIX+cookie.getLoginName(), data.get(StatusConstant.REQUESTS_STR).toString(), expire);
                    
                    data.remove(StatusConstant.REQUESTS_STR);
                    responseBody = obj.toString();
                }
                
            }
            
            ctx.setResponseBody(responseBody);
//            try {
//                response.getWriter().write(responseBody);
//                response.getWriter().flush();
//                response.getWriter().close();
//            } catch (IOException e) {
//                logger.error("发送信息IO异常",e);
//            }
        }finally{
            logger.printEndTag("post filter");
            ThreadContext.removeStack();
        }
        
        return null;
    }

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

}
