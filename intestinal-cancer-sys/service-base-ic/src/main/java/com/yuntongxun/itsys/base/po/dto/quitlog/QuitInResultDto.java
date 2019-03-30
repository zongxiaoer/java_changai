package com.yuntongxun.itsys.base.po.dto.quitlog;

import com.yuntongxun.itsys.base.po.HospitalReview;

/**
 * @author wp_sp
 * @date 2018/5/27
 */
public class QuitInResultDto {
    private String sid;
    private String quitLogId;

    private String schemeId;//违反表id



    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getQuitLogId() {
        return quitLogId;
    }

    public void setQuitLogId(String quitLogId) {
        this.quitLogId = quitLogId;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }
}
