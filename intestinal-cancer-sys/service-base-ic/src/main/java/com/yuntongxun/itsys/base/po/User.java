package com.yuntongxun.itsys.base.po;

public class User {

    private String authCode;//验证码
    private String capText;//校验码
    private String uuID;
    private String xtyPhone;//协调员电话
    private String xtyName;//协调员姓名

    private String newPwd;//新密码

    private String firstLogin;//登录状态

    public int id;
    public String nickName, loginName, pwd, employeeId, phone, tel, dateCreated, updateTime, encode, address, facsimile, email, qq;


    public User() {
        super();
    }

    public User(String authCode, String capText, String uuID, String xtyPhone, String xtyName, String newPwd, String firstLogin, int id, String nickName, String loginName, String pwd, String employeeId, String phone, String tel, String dateCreated, String updateTime, String encode, String address, String facsimile, String email, String qq) {
        this.authCode = authCode;
        this.capText = capText;
        this.uuID = uuID;
        this.xtyPhone = xtyPhone;
        this.xtyName = xtyName;
        this.newPwd = newPwd;
        this.firstLogin = firstLogin;
        this.id = id;
        this.nickName = nickName;
        this.loginName = loginName;
        this.pwd = pwd;
        this.employeeId = employeeId;
        this.phone = phone;
        this.tel = tel;
        this.dateCreated = dateCreated;
        this.updateTime = updateTime;
        this.encode = encode;
        this.address = address;
        this.facsimile = facsimile;
        this.email = email;
        this.qq = qq;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFacsimile() {
        return facsimile;
    }

    public void setFacsimile(String facsimile) {
        this.facsimile = facsimile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
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
