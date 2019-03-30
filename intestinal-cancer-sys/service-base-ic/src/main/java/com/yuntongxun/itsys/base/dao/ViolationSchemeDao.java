package com.yuntongxun.itsys.base.dao;

import com.yuntongxun.itsys.base.po.ViolationScheme;

/**
 * 违反方案 dao
 */
public interface ViolationSchemeDao {
    public Integer addViolationScheme(ViolationScheme violationScheme);

    ViolationScheme queryHospitalViolationSchemeById(Integer id,String sid);
    //更改违反方案表,把内容添加
    public void updateViolationScheme(ViolationScheme violationScheme);
    //添加违反方案id，sid,status
    public int addVScheme(ViolationScheme violationScheme);
    //查询最大一个id
    public ViolationScheme searchByMax();



}
