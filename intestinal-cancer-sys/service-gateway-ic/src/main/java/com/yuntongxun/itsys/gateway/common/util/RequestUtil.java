package com.yuntongxun.itsys.gateway.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.yuntongxun.itsys.gateway.common.constants.CookieConstant;
import com.yuntongxun.itsys.gateway.common.vo.CookieParam;

public class RequestUtil {
    
    private static final Logger logger = LogManager.getLogger(RequestUtil.class);

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
            logger.error("获取requestBody异常", e);
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
    public static CookieParam getTokenByCookie(HttpServletRequest request) {
        
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
     * 设置cookie
     * @param response
     * @param key 键
     * @param value 值
     * @param expire 失效时间 单位：秒
     */
    public static void setCookie(HttpServletResponse response,String key,String value,long expire){
        Cookie cookie = new Cookie(key,value);
        cookie.setPath("/");
        cookie.setMaxAge((int)expire);
        response.addCookie(cookie);
    }
    
    /**
     * 匹配requestUrlArr是否符合url结构
     * @param requestUrlArr 需要验证的url
     * @param url 
     * @return true 匹配成功 false 失败
     */
    public static boolean matchUrl(String[] requestUrlArr, String url) {
        String[] authUrlArr = url.split("/");
        //判断长度是否一致 不一致在判断是否大于1 因为可能有contextPath
        boolean contextPathFlag = (requestUrlArr.length-1) == authUrlArr.length;
        if(requestUrlArr.length != authUrlArr.length && !contextPathFlag){
          return false;
        }
        for(int i = 1,j = authUrlArr.length;i < j;i++){
            int requestIndex = i;
            if(contextPathFlag){
                requestIndex = i+1;
            }
          //判断区间值是否相等
            if(!authUrlArr[i].equals(requestUrlArr[requestIndex]) 
                    //判断是否为匹配值
                    && !(authUrlArr[i].startsWith("{") && authUrlArr[i].endsWith("}")) ){
                break;//不相等且不是匹配值，重新匹配下一个url
            }
            //如果最后一项也相等，说明有权限
            if(i+1 == j){
                return true;
            }
        }
        return false;
    }
    
    
    /**      
     * 描述:获取 post 请求的 byte[] 数组
     * <pre>
     * 举例：
     * </pre>
     * @param request
     * @return
     * @throws IOException      
     */
    public static byte[] getRequestPostBytes(HttpServletRequest request)
           {
        int contentLength = request.getContentLength();
        if(contentLength<0){
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength;) {

            int readlen=-1;
			try {
				readlen = request.getInputStream().read(buffer, i,
				        contentLength - i);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }

}
