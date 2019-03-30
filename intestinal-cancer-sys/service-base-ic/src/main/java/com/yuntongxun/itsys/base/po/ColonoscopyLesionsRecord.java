package com.yuntongxun.itsys.base.po;

import java.util.Date;

/**
 * 结肠镜结果病变记录表
 */
public class ColonoscopyLesionsRecord {
    private Integer id;//  int  物理主键，自增
    private String sid;// varchar(64) 受试者唯一标识
    private Integer colonoscopyResultId;//int  结肠镜结果记录表ID
    private Integer item1;// int 活检部位,1：回肠末端，2：回盲瓣，3：升结肠，4：肝曲，5：横结肠，6：脾曲，7：降结肠，8：乙状结肠，9：直肠
    private Double item2;// int活检位置（距肛门距离），cm
    private String item3;//镜下考虑病变类型
    private String item4;// 病理标本号
    private Double item5;// 最大直径
    private Integer item6;// 形状    1：隆起;2:扁平;3:凹陷
    private Integer item7;// 有无蒂：1:有,2:无
    private Integer item8;// 蒂 形 状：1:广蒂,2:亚蒂
    private Integer item9;//颜色：1:红色,2:灰白色,3:其它
    private Integer item10;//有无出血 1出血,2不出血
    private Date dateCreated;// datetime 创建时间
    private Date updateTime;// datetime 更新时间
    private Integer index;//2018-8-29 zongtong

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

    public Integer getColonoscopyResultId() {
        return colonoscopyResultId;
    }

    public void setColonoscopyResultId(Integer colonoscopyResultId) {
        this.colonoscopyResultId = colonoscopyResultId;
    }

    public Integer getItem1() {
        return item1;
    }

    public void setItem1(Integer item1) {
        this.item1 = item1;
    }

    public Double getItem2() {
        return item2;
    }

    public void setItem2(Double item2) {
        this.item2 = item2;
    }

    public String getItem3() {
        return item3;
    }

    public void setItem3(String item3) {
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

    public Integer getItem6() {
        return item6;
    }

    public void setItem6(Integer item6) {
        this.item6 = item6;
    }

    public Integer getItem7() {
        return item7;
    }

    public void setItem7(Integer item7) {
        this.item7 = item7;
    }

    public Integer getItem8() {
        return item8;
    }

    public void setItem8(Integer item8) {
        this.item8 = item8;
    }

    public Integer getItem9() {
        return item9;
    }

    public void setItem9(Integer item9) {
        this.item9 = item9;
    }

    public Integer getItem10() {
        return item10;
    }

    public void setItem10(Integer item10) {
        this.item10 = item10;
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
