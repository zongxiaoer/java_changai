package com.yuntongxun.itsys.gateway.module.pojo;

/**
 * Description:
 * Created by LuoKun on 2018-01-18.
 */
public class Employees {


    private Integer id;

    private String name;

    private String email;

    private String phone;

    public Employees(Integer id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Employees() {
    }
}
