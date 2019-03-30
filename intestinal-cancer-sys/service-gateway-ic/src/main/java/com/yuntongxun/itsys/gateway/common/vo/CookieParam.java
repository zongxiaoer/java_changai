package com.yuntongxun.itsys.gateway.common.vo;

public class CookieParam {

    private String loginName;
    private String token;
    public String getLoginName() {
        return loginName;
    }
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    
    /**
     * 验证是否获取全cookie
     * @return true已获取全
     */
    public boolean validate(){
        if(token!=null && loginName != null){
            return true;
        }
        return false;
    }
    
}
