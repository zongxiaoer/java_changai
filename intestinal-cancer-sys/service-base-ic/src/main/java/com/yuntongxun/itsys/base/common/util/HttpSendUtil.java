/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: HttpSendUtil
 * Author:   sun
 * Date:     2018/9/5 11:28
 * History:
 * <author>          <time>                <version>
 * sun           2018/9/5 11:28           v1.0.0
 */
package com.yuntongxun.itsys.base.common.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * 接口调用工具类
 *
 * @author sun
 * @create 2018/9/5
 * @since v1.0.0
 */
public class HttpSendUtil {
    public static String defaultConnection(String method, String path, int timeout, int readTimeout, String data)
            throws Exception {
        URL url=null;
        URLConnection con=null;
        HttpURLConnection urlCon=null;
        String strResponse=null;
        StringBuilder sb=new StringBuilder();
        int intResponseCode = HttpURLConnection.HTTP_OK;
        OutputStream out=null;
        InputStream input=null;

        //第1步，建立连接
        try {
            url=new URL(path);
            //向某个特定协议对象返回表现http资源连接的引用
            con=url.openConnection();
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("http连接失败:"+e.getMessage());
        }

        //第2步，验证连接的类型，必须是HttpURLConnection
        if(!(con instanceof HttpURLConnection)){
            System.out.println("http连接失败，连接类型错误");
        }

        //第3步，发送报文
        try {
            //表明程序必须把名称/值对输出到服务器程序资源
            con.setConnectTimeout(timeout == 0 ? 1000 : timeout);
            con.setReadTimeout(readTimeout == 0 ? 1000 : readTimeout);
            con.setRequestProperty("Content-Type","application/json");


            con.setDoOutput(true);
            con.setDoInput(true);
            //表明只能返回有用的信息
            con.setUseCaches(false);
            urlCon=(HttpURLConnection)con;
            //设置HTTP请求方法
            urlCon.setRequestMethod(method);
            //获得输出流对象
            out=urlCon.getOutputStream();
            DataOutputStream dos=new DataOutputStream(out);
            dos.write(data.getBytes("utf-8"));
            dos.flush();
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("http发送失败:"+e.getMessage());
        }finally{
            out.close();
        }

        //第4步，校验返回状态
        try {
            intResponseCode=urlCon.getResponseCode();
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("http返回状态失败:"+e.getMessage());
        }
        if(intResponseCode!=HttpURLConnection.HTTP_OK){  //如果不为(HTTP_OK)200，说明服务器返回错误
            System.out.println("request "+ urlCon.getURL() +" fail. response: code="+intResponseCode +
                    ", message="+urlCon.getResponseMessage());
        }


        //第5步，接收报文
        try {
            input=urlCon.getInputStream();
            //将字节流转换为字符流
            BufferedReader br=new BufferedReader(new InputStreamReader(input));
            while((strResponse=br.readLine())!=null){
                sb.append(strResponse);
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("http接收错误:"+e.getMessage());
        }finally{
            input.close();
            //关闭和服务器的连接
            urlCon.disconnect();
        }
        return sb.toString();
    }
}
