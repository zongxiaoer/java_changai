package com.yuntongxun.itsys.base.po;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by LuoKun on 2017-10-26.
 */
public class AuthRequestR {
    public int id;
    public int authId;
    public int requestId;

    public AuthRequestR(int id, int authId, int requestId) {
        this.id = id;
        this.authId = authId;
        this.requestId = requestId;
    }

    public AuthRequestR() {
        super();
    }

    public static AuthRequestR toObject(Map<String, Object> map) {
        AuthRequestR authRequestR = new AuthRequestR();
        authRequestR.setId((Integer) map.get("id"));
        authRequestR.setAuthId((Integer) map.get("authId"));
        authRequestR.setRequestId((Integer) map.get("requestId"));
        return authRequestR;
    }

    public static List<AuthRequestR> toObject(List<Map<String, Object>> lists){
        List<AuthRequestR> authRequestRList = new ArrayList<AuthRequestR>();
        for (Map<String, Object> map : lists) {
            AuthRequestR authRequestR =  AuthRequestR.toObject(map);
            if (authRequestR != null) {
                authRequestRList.add(authRequestR);
            }
        }
        return authRequestRList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthId() {
        return authId;
    }

    public void setAuthId(int authId) {
        this.authId = authId;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }
}
