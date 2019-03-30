package com.yuntongxun.itsys.gateway.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.yuntongxun.itsys.gateway.common.constants.CookieConstant;
import com.yuntongxun.itsys.gateway.common.vo.CookieParam;

public class CookieUtil {
	private final Logger log = LogManager.getLogger();
	/**
	 * 根据request获取cookie中loginName
	 * @param req
	 * @return
	 */
	public static String getCookieByLoginName(HttpServletRequest req) {

		CookieParam parm=getParamByCookie(req);
		String Name=parm.getLoginName();
		return Name;
	}
	
	 public static String getReqBody(HttpServletRequest request) {
	        String jsonStr = null;
	        BufferedReader br = null;
	        try {
	            br = new BufferedReader(new InputStreamReader(
	                    request.getInputStream(), "UTF-8"));
	            String inputStr = null;
	            StringBuffer input = new StringBuffer();
	            while ((inputStr = br.readLine()) != null) {
	                input.append(inputStr);
	            }
	            br.close();
	            jsonStr = input.toString();
	        } catch (IOException e) {
	        	
	        }finally{
	            if(null!=br){
	                try {
	                    br.close();
	                } catch (IOException e) {
	                    br=null;
	                    e.printStackTrace();
	                }
	            }
	        }
	        return jsonStr;
	    }
	    
	    
	    /**
	     * 获取token和loginName
	     * @param request
	     * @return token为32位
	     */
	    public static CookieParam getParamByCookie(HttpServletRequest request) {
	        
	        Cookie[] cookies = request.getCookies();
	        if(cookies == null){
	            return null;
	        }
	        CookieParam cookieParam = new CookieParam();
	        for(Cookie cookie:cookies){
	            if(cookie.getName().equals(CookieConstant.KEY_LOGING_NAME)){
	                cookieParam.setLoginName(cookie.getValue());
	            }else if(cookie.getName().equals(CookieConstant.KEY_TOKEN)){
	                cookieParam.setToken(cookie.getValue());
	            }
	            if(cookieParam.validate()){
	                break;
	            }
	        }
	        return cookieParam;
	    }
	    
	    /**
	     * 根据名字获取cookie
	     * @param request
	     * @param name cookie名字
	     * @return
	     */
	    public static Cookie getCookieByName(HttpServletRequest request, String name){
	        Map<String,Cookie> cookieMap = ReadCookieMap(request);
	        if(cookieMap.containsKey(name)){
	            Cookie cookie = (Cookie)cookieMap.get(name);
	            return cookie;
	        }else{
	            return null;
	        }
	    }
	    /**
	     * 将cookie封装到Map里面
	     * @param request
	     * @return
	     */
	    private static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){
	        Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
	        Cookie[] cookies = request.getCookies();
	        if(null!=cookies){
	            for(Cookie cookie : cookies){
	                cookieMap.put(cookie.getName(), cookie);
	            }
	        }
	        return cookieMap;
	    }

	
}
