package com.yuntongxun.itsys.base.po;

import java.util.Date;

/**
 * 结肠镜病理诊断记录表
 */
public class ColonoscopyPathologyDiagnosisRecord {

    private Integer id;//  int  物理主键，自增
    private String sid;// varchar(64) 受试者唯一标识
    private Integer pathologyResultId;//int  结肠镜结果记录表ID
    private String item1;// varchar(64) 病理标本号
    private Integer item2;// int 活检部位,1：回肠末端，2：回盲瓣，3：升结肠，4：肝曲，5：横结肠，6：脾曲，7：降结肠，8：乙状结肠，9：直肠
    private Double item3;// int活检位置（距肛门距离），cm
    private String item4;// varchar(256) 病理诊断（编码）
    private Double item5;// double(6,2) 高级别上皮内瘤变比例（%）
    private Double item6_1;// int 腺瘤性息肉的结构比例（%），管状
    private Double item6_2;// int 腺瘤性息肉的结构比例（%），绒毛状
    private String item7;// varchar(256) 7.备注
    private Date dateCreated;// datetime 创建时间
    private Date updateTime;// datetime 更新时间
    private Integer index; //2018-8-29 zongtong


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Integer getPathologyResultId() {
        return pathologyResultId;
    }

    public void setPathologyResultId(Integer pathologyResultId) {
        this.pathologyResultId = pathologyResultId;
    }

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public Integer getItem2() {
        return item2;
    }

    public void setItem2(Integer item2) {
        this.item2 = item2;
    }

    public Double getItem3() {
        return item3;
    }

    public void setItem3(Double item3) {
        this.item3 = item3;
    }

    public String getItem4() {
        return item4;
    }

    public void setItem4(String item4) {
        this.item4 = item4;
    }

    public Double getItem5() {
        return item5;
    }

    public void setItem5(Double item5) {
        this.item5 = item5;
    }

    public Double getItem6_1() {
        return item6_1;
    }

    public void setItem6_1(Double item6_1) {
        this.item6_1 = item6_1;
    }

    public Double getItem6_2() {
        return item6_2;
    }

    public void setItem6_2(Double item6_2) {
        this.item6_2 = item6_2;
    }

    public String getItem7() {
        return item7;
    }

    public void setItem7(String item7) {
        this.item7 = item7;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
