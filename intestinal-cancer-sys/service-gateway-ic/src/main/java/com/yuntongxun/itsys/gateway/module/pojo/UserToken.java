package com.yuntongxun.itsys.gateway.module.pojo;

public class UserToken {

    private String token;//权限令牌 当token数据不存在时，用户需重新登录
    /**
     * 超时时间（时间戳），默认10分钟，该时长设置要小于token在redis中的TLL；即，token允许重置时长 = TTL时长-token过期时长；
     */
    private String expires;//
    private String oldToken;//重置
    
    public UserToken (String token,long expires){
        this.token = token;
        this.expires = expires+"";
    }
    
    
    public String getOldToken() {
        return oldToken;
    }


    public void setOldToken(String oldToken) {
        this.oldToken = oldToken;
    }


    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getExpires() {
        return expires;
    }
    public void setExpires(String expires) {
        this.expires = expires;
    }
    
    
}
