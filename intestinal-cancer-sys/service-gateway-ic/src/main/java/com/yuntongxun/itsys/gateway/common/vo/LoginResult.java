package com.yuntongxun.itsys.gateway.common.vo;

/**
 * Description:
 *
 * @author LuoKun on 2018-04-21.
 */
public class LoginResult {

    private String token;

    private String hospitalType;

    public LoginResult() {
        super();
    }

    public LoginResult(String token, String hospitalType) {
        this.token = token;
        this.hospitalType = hospitalType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getHospitalType() {
        return hospitalType;
    }

    public void setHospitalType(String hospitalType) {
        this.hospitalType = hospitalType;
    }
}
