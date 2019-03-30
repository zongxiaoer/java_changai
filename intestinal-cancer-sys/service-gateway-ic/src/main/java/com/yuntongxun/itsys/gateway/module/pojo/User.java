package com.yuntongxun.itsys.gateway.module.pojo;

public class User {

    private String loginName;//账号
    private String pwd;//密码
    private String phone;//手机号
    private String authCode;//验证码
    private String capText;//校验码
    private String uuID;
    private String nickName;
    private String employeeId;
    private String xtyPhone;//协调员电话
    private String xtyName;//协调员姓名

    private String newPwd;//新密码

    private String firstLogin;//登录状态
    
    




    public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getLoginName() {
        return loginName;
    }
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getCapText() {
        return capText;
    }

    public void setCapText(String capText) {
        this.capText = capText;
    }

    public String getUuID() {
        return uuID;
    }

    public void setUuID(String uuID) {
        this.uuID = uuID;
    }

    public String getXtyPhone() {
        return xtyPhone;
    }

    public void setXtyPhone(String xtyPhone) {
        this.xtyPhone = xtyPhone;
    }

    public String getXtyName() {
        return xtyName;
    }

    public void setXtyName(String xtyName) {
        this.xtyName = xtyName;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(String firstLogin) {
        this.firstLogin = firstLogin;
    }
}
