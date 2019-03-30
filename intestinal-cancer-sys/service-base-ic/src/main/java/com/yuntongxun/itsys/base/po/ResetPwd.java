package com.yuntongxun.itsys.base.po;

/**
 * Description: 找回密码
 *
 * @author LuoKun on 2018-04-08.
 */
public class ResetPwd {

    private String phone;
    private String vcode;
    private String pwd;
    private String againPwd;

    public ResetPwd() {
        super();
    }

    public ResetPwd(String phone, String vcode, String pwd, String againPwd) {
        this.phone = phone;
        this.vcode = vcode;
        this.pwd = pwd;
        this.againPwd = againPwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAgainPwd() {
        return againPwd;
    }

    public void setAgainPwd(String againPwd) {
        this.againPwd = againPwd;
    }
}
