package com.yuntongxun.itsys.base.common.util;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Vector;

public class ExcelData implements Serializable {

    private static final long serialVersionUID = 4444017239100620999L;
    private String headStr;
    private Vector titleVec;
    private int[] titleWidthAry;
    private String[][] bodyAry;
    private OutputStream os;
    private String sheetName;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getHeadStr() {
        return headStr;
    }

    public void setHeadStr(String headStr) {
        this.headStr = headStr;
    }

    public Vector getTitleVec() {
        return titleVec;
    }

    public void setTitleVec(Vector titleVec) {
        this.titleVec = titleVec;
    }

    public int[] getTitleWidthAry() {
        return titleWidthAry;
    }

    public void setTitleWidthAry(int[] titleWidthAry) {
        this.titleWidthAry = titleWidthAry;
    }

    public String[][] getBodyAry() {
        return bodyAry;
    }

    public void setBodyAry(String[][] bodyAry) {
        this.bodyAry = bodyAry;
    }

    public OutputStream getOs() {
        return os;
    }

    public void setOs(OutputStream os) {
        this.os = os;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }
}
