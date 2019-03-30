package com.yuntongxun.itsys.base.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yuntongxun.itsys.base.common.constans.CookieConstant;
import com.yuntongxun.itsys.base.po.CookieParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RequestUtil {
	private static final Logger logger = LogManager.getLogger();

	public static String getReqBody(HttpServletRequest request) {
		String jsonStr = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			String inputStr = null;
			StringBuffer input = new StringBuffer();
			while ((inputStr = br.readLine()) != null) {
				input.append(inputStr);
			}
			br.close();
			jsonStr = input.toString();
		} catch (IOException e) {
			logger.error("获取requestBody异常", e);
		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
					e.printStackTrace();
				}
			}
		}
		return jsonStr;
	}

	public static void responseOutWithJson(HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			System.out.println(out);
			logger.debug("返回是\n");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 判断请求与请求库是否匹配
	 * 
	 * @param requestUrlArr
	 * @param url
	 * @return
	 */
	public static boolean matchUrl(String[] requestUrlArr, String url) {
		String[] authUrlArr = url.split("/");
		// 判断长度是否一致
		if (requestUrlArr.length != authUrlArr.length) {
			return false;
		}
		for (int i = 1, j = authUrlArr.length; i < j; i++) {
			// 判断区间值是否相等
			if (!authUrlArr[i].equals(requestUrlArr[i])
					// 判断是否为匹配值
					&& !(authUrlArr[i].startsWith("{") && authUrlArr[i].endsWith("}"))) {
				break;// 不相等且不是匹配值，重新匹配下一个url
			}
			// 如果最后一项也相等，说明有权限
			if (i + 1 == j) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 描述:获取 post 请求的 byte[] 数组
	 * 
	 * <pre>
	 * 举例：
	 * </pre>
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static byte[] getRequestPostBytes(HttpServletRequest request) throws IOException {

		BufferedInputStream bufin = new BufferedInputStream(request.getInputStream());
		int buffSize = 1024;
		ByteArrayOutputStream out = new ByteArrayOutputStream(buffSize);

		// System.out.println("Available bytes:" + in.available());

		byte[] temp = new byte[buffSize];
		int size = 0;
		while ((size = bufin.read(temp)) != -1) {
			out.write(temp, 0, size);
		}
		bufin.close();

		byte[] content = out.toByteArray();
		return content;
	}

	/**
	 * 描述:获取 post 请求内容
	 * 
	 * <pre>
	 * 举例：
	 * </pre>
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String getRequestPostStr(HttpServletRequest request) throws IOException {
		byte buffer[] = getRequestPostBytes(request);
		String charEncoding = request.getCharacterEncoding();
		if (charEncoding == null) {
			charEncoding = "UTF-8";
		}
		return new String(buffer, charEncoding);
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
}
