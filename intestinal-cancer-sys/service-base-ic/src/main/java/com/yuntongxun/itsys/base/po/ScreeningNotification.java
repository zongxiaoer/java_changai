package com.yuntongxun.itsys.base.po;

import java.util.Date;

/**
 * 筛查告知书
 */
public class ScreeningNotification {

    private Integer id; //物理主键，自增
    private String sid;//受试者唯一编号
    private Integer todoId;//待办ID
    private Integer colonoscopyRecordId;//结肠镜检查记录
    private Date scDate;//筛查日期
    private Integer stage;//阶段，1：T0，2：T1，3：T2，4：T3
    private String xtyName;//协调员项目
    private String xtyPhone;//协调员电话
    private String hosName;//肿瘤医院名称
    private Integer item_a_type;//结肠镜镜下检查结果 1：是，2：否
    private Integer item_a_r_1;//正常  1：是，2：否
    private Integer item_a_r_2;//息肉   1：是，2：否
    private Integer item_a_r_3;//直肠癌   1：是，2：否
    private Integer item_a_r_4;//疑似直肠癌   1：是，2：否
    private Integer item_a_r_5;//结肠癌   1：是，2：否
    private Integer item_a_r_6;//疑似结肠癌   1：是，2：否
    private Integer item_a_r_7;//克隆病   1：是，2：否
    private Integer item_a_r_8;//溃疡性结肠炎   1：是，2：否
    private Integer item_a_r_9;//非典型结肠炎 1：是，2：否
    private Integer item_a_r_10;//血管畸形   1：是，2：否
    private Integer item_a_r_11;// int 憩室  1：是，2：否
    private Integer item_a_r_12;// int 肛裂  1：是，2：否
    private Integer item_a_r_13;// int 痔疮 1：是，2：否
    private Integer item_a_r_14;//int 直肠脱垂 1：是，2：否
    private Integer item_a_r_15;// int 其它 1：是，2：否
    private String item_a_r_15_other;// varchar(255) 其它内容
    private Integer item_a_s_1;// int 待病理诊断 1：是，2：否
    private Integer item_a_s_2;// int 密切观察 1：是，2：否
    private Integer item_a_s_3;// int 保守治疗 1：是，2：否
    private Integer item_a_s_4;// int 内镜治疗 1：是，2：否
    private Integer item_a_s_5;// int 手术治疗 1：是，2：否
    private Integer item_a_s_6;// int 其它 1：是，2：否
    private String item_a_s_6_other;// varchar(255) 其它内容
    private Integer item_b_type;// int  FIT大便潜血试验 1：是，2：否
    private Integer item_b_r_1;// int 阴性 1：是，2：否
    private Integer item_b_r_2;// int 阳性 1：是，2：否
    private Integer item_b_r_3;// int 无效 1：是，2：否
    private Integer item_b_s_1;// int 第二年回访 1：是，2：否
    private Integer item_b_s_2;// int 尽快进行全肠镜检查   1：是，2：否
    private Integer item_b_s_3;// int 重新取粪便标本   1：是，2：否
    private Integer item_c_type;// int 结肠镜病理检查结果 1：是，2：否
    private Integer item_c_r_1;// int 良性肿瘤  1：是，2：否
    private Integer item_c_r_2;// int 管状腺瘤 1：是，2：否
    private Integer item_c_r_3;// int 管状绒毛状腺瘤 1：是，2：否
    private Integer item_c_r_4;// int 绒毛状腺瘤 1：是，2：否
    private Integer item_c_r_5;// int 扁平腺瘤（管状型） 1：是，2：否
    private Integer item_c_r_6;// int 扁平腺瘤（管状绒毛状型） 1：是，2：否
    private Integer item_c_r_7;// int 扁平腺瘤（绒毛状型） 1：是，2：否
    private Integer item_c_r_8;// int 混合型腺瘤 1：是，2：否
    private Integer item_c_r_9;// int 其它 1：是，2：否
    private String item_c_r_9_other;// vachar(255) 其它内容
    private Integer item_c_r_10;// int 增生性息肉 1：是，2：否
    private Integer item_c_r_11;// int 肉芽肿性息肉 1：是，2：否
    private Integer item_c_r_12;// int 炎性息肉 1：是，2：否
    private Integer item_c_r_13;// int 其它 1：是，2：否
    private String item_c_r_13_other;// int 其它内容
    private Integer item_c_r_14;// int 低级别上皮内瘤变 1：是，2：否
    private Integer item_c_r_15;// int 高级别上皮内瘤变 1：是，2：否
    private Integer item_c_r_16;// int 高分化腺癌 1：是，2：否
    private Integer item_c_r_17;// int 中分化腺癌 1：是，2：否
    private Integer item_c_r_18;// int 低分化腺癌 1：是，2：否
    private Integer item_c_r_19;// int 其它 1：是，2：否
    private String item_c_r_19_other;// varchar(255) 其它内容
    private Integer item_c_s_1;// 密切观察 1：是，2：否
    private Integer item_c_s_2;// 保守治疗 1：是，2：否
    private Integer item_c_s_3;//int 内镜治疗 1：是，2：否
    private Integer item_c_s_4;// int 手术治疗 1：是，2：否
    private Integer item_c_s_5;// int 其它 1：是，2：否
    private String item_c_s_5_other;// varchar(255) 其它内容
    private Integer communityDeptId;// int 社区ID
    private Integer areaDeptId;// int 地区医院ID

    private Date dateCreated;// datetime 创建时间
    private Date updateTime;//datetime 更新时间

    private String applyStatus;

    private String editStatus;

    private String approvalStatus;

    private String	editoperationSource;

    private Integer	operationSourceId;

    private Integer reissueNotification; //是否重新发放告知书 1是 2否  非数据库字段

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

    public Date getScDate() {
        return scDate;
    }

    public void setScDate(Date scDate) {
        this.scDate = scDate;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public String getXtyName() {
        return xtyName;
    }

    public void setXtyName(String xtyName) {
        this.xtyName = xtyName;
    }

    public String getXtyPhone() {
        return xtyPhone;
    }

    public void setXtyPhone(String xtyPhone) {
        this.xtyPhone = xtyPhone;
    }

    public String getHosName() {
        return hosName;
    }

    public void setHosName(String hosName) {
        this.hosName = hosName;
    }

    public Integer getItem_a_type() {
        return item_a_type;
    }

    public void setItem_a_type(Integer item_a_type) {
        this.item_a_type = item_a_type;
    }

    public Integer getItem_a_r_1() {
        return item_a_r_1;
    }

    public void setItem_a_r_1(Integer item_a_r_1) {
        this.item_a_r_1 = item_a_r_1;
    }

    public Integer getItem_a_r_2() {
        return item_a_r_2;
    }

    public void setItem_a_r_2(Integer item_a_r_2) {
        this.item_a_r_2 = item_a_r_2;
    }

    public Integer getItem_a_r_3() {
        return item_a_r_3;
    }

    public void setItem_a_r_3(Integer item_a_r_3) {
        this.item_a_r_3 = item_a_r_3;
    }

    public Integer getItem_a_r_4() {
        return item_a_r_4;
    }

    public void setItem_a_r_4(Integer item_a_r_4) {
        this.item_a_r_4 = item_a_r_4;
    }

    public Integer getItem_a_r_5() {
        return item_a_r_5;
    }

    public void setItem_a_r_5(Integer item_a_r_5) {
        this.item_a_r_5 = item_a_r_5;
    }

    public Integer getItem_a_r_6() {
        return item_a_r_6;
    }

    public void setItem_a_r_6(Integer item_a_r_6) {
        this.item_a_r_6 = item_a_r_6;
    }

    public Integer getItem_a_r_7() {
        return item_a_r_7;
    }

    public void setItem_a_r_7(Integer item_a_r_7) {
        this.item_a_r_7 = item_a_r_7;
    }

    public Integer getItem_a_r_8() {
        return item_a_r_8;
    }

    public void setItem_a_r_8(Integer item_a_r_8) {
        this.item_a_r_8 = item_a_r_8;
    }

    public Integer getItem_a_r_9() {
        return item_a_r_9;
    }

    public void setItem_a_r_9(Integer item_a_r_9) {
        this.item_a_r_9 = item_a_r_9;
    }

    public Integer getItem_a_r_10() {
        return item_a_r_10;
    }

    public void setItem_a_r_10(Integer item_a_r_10) {
        this.item_a_r_10 = item_a_r_10;
    }

    public Integer getItem_a_r_11() {
        return item_a_r_11;
    }

    public void setItem_a_r_11(Integer item_a_r_11) {
        this.item_a_r_11 = item_a_r_11;
    }

    public Integer getItem_a_r_12() {
        return item_a_r_12;
    }

    public void setItem_a_r_12(Integer item_a_r_12) {
        this.item_a_r_12 = item_a_r_12;
    }

    public Integer getItem_a_r_13() {
        return item_a_r_13;
    }

    public void setItem_a_r_13(Integer item_a_r_13) {
        this.item_a_r_13 = item_a_r_13;
    }

    public Integer getItem_a_r_14() {
        return item_a_r_14;
    }

    public void setItem_a_r_14(Integer item_a_r_14) {
        this.item_a_r_14 = item_a_r_14;
    }

    public Integer getItem_a_r_15() {
        return item_a_r_15;
    }

    public void setItem_a_r_15(Integer item_a_r_15) {
        this.item_a_r_15 = item_a_r_15;
    }

    public String getItem_a_r_15_other() {
        return item_a_r_15_other;
    }

    public void setItem_a_r_15_other(String item_a_r_15_other) {
        this.item_a_r_15_other = item_a_r_15_other;
    }

    public Integer getItem_a_s_1() {
        return item_a_s_1;
    }

    public void setItem_a_s_1(Integer item_a_s_1) {
        this.item_a_s_1 = item_a_s_1;
    }

    public Integer getItem_a_s_2() {
        return item_a_s_2;
    }

    public void setItem_a_s_2(Integer item_a_s_2) {
        this.item_a_s_2 = item_a_s_2;
    }

    public Integer getItem_a_s_3() {
        return item_a_s_3;
    }

    public void setItem_a_s_3(Integer item_a_s_3) {
        this.item_a_s_3 = item_a_s_3;
    }

    public Integer getItem_a_s_4() {
        return item_a_s_4;
    }

    public void setItem_a_s_4(Integer item_a_s_4) {
        this.item_a_s_4 = item_a_s_4;
    }

    public Integer getItem_a_s_5() {
        return item_a_s_5;
    }

    public void setItem_a_s_5(Integer item_a_s_5) {
        this.item_a_s_5 = item_a_s_5;
    }

    public Integer getItem_a_s_6() {
        return item_a_s_6;
    }

    public void setItem_a_s_6(Integer item_a_s_6) {
        this.item_a_s_6 = item_a_s_6;
    }

    public String getItem_a_s_6_other() {
        return item_a_s_6_other;
    }

    public void setItem_a_s_6_other(String item_a_s_6_other) {
        this.item_a_s_6_other = item_a_s_6_other;
    }

    public Integer getItem_b_type() {
        return item_b_type;
    }

    public void setItem_b_type(Integer item_b_type) {
        this.item_b_type = item_b_type;
    }

    public Integer getItem_b_r_1() {
        return item_b_r_1;
    }

    public void setItem_b_r_1(Integer item_b_r_1) {
        this.item_b_r_1 = item_b_r_1;
    }

    public Integer getItem_b_r_2() {
        return item_b_r_2;
    }

    public void setItem_b_r_2(Integer item_b_r_2) {
        this.item_b_r_2 = item_b_r_2;
    }

    public Integer getItem_b_r_3() {
        return item_b_r_3;
    }

    public void setItem_b_r_3(Integer item_b_r_3) {
        this.item_b_r_3 = item_b_r_3;
    }

    public Integer getItem_b_s_1() {
        return item_b_s_1;
    }

    public void setItem_b_s_1(Integer item_b_s_1) {
        this.item_b_s_1 = item_b_s_1;
    }

    public Integer getItem_b_s_2() {
        return item_b_s_2;
    }

    public void setItem_b_s_2(Integer item_b_s_2) {
        this.item_b_s_2 = item_b_s_2;
    }

    public Integer getItem_b_s_3() {
        return item_b_s_3;
    }

    public void setItem_b_s_3(Integer item_b_s_3) {
        this.item_b_s_3 = item_b_s_3;
    }

    public Integer getItem_c_type() {
        return item_c_type;
    }

    public void setItem_c_type(Integer item_c_type) {
        this.item_c_type = item_c_type;
    }

    public Integer getItem_c_r_1() {
        return item_c_r_1;
    }

    public void setItem_c_r_1(Integer item_c_r_1) {
        this.item_c_r_1 = item_c_r_1;
    }

    public Integer getItem_c_r_2() {
        return item_c_r_2;
    }

    public void setItem_c_r_2(Integer item_c_r_2) {
        this.item_c_r_2 = item_c_r_2;
    }

    public Integer getItem_c_r_3() {
        return item_c_r_3;
    }

    public void setItem_c_r_3(Integer item_c_r_3) {
        this.item_c_r_3 = item_c_r_3;
    }

    public Integer getItem_c_r_4() {
        return item_c_r_4;
    }

    public void setItem_c_r_4(Integer item_c_r_4) {
        this.item_c_r_4 = item_c_r_4;
    }

    public Integer getItem_c_r_5() {
        return item_c_r_5;
    }

    public void setItem_c_r_5(Integer item_c_r_5) {
        this.item_c_r_5 = item_c_r_5;
    }

    public Integer getItem_c_r_6() {
        return item_c_r_6;
    }

    public void setItem_c_r_6(Integer item_c_r_6) {
        this.item_c_r_6 = item_c_r_6;
    }

    public Integer getItem_c_r_7() {
        return item_c_r_7;
    }

    public void setItem_c_r_7(Integer item_c_r_7) {
        this.item_c_r_7 = item_c_r_7;
    }

    public Integer getItem_c_r_8() {
        return item_c_r_8;
    }

    public void setItem_c_r_8(Integer item_c_r_8) {
        this.item_c_r_8 = item_c_r_8;
    }

    public Integer getItem_c_r_9() {
        return item_c_r_9;
    }

    public void setItem_c_r_9(Integer item_c_r_9) {
        this.item_c_r_9 = item_c_r_9;
    }

    public String getItem_c_r_9_other() {
        return item_c_r_9_other;
    }

    public void setItem_c_r_9_other(String item_c_r_9_other) {
        this.item_c_r_9_other = item_c_r_9_other;
    }

    public Integer getItem_c_r_10() {
        return item_c_r_10;
    }

    public void setItem_c_r_10(Integer item_c_r_10) {
        this.item_c_r_10 = item_c_r_10;
    }

    public Integer getItem_c_r_11() {
        return item_c_r_11;
    }

    public void setItem_c_r_11(Integer item_c_r_11) {
        this.item_c_r_11 = item_c_r_11;
    }

    public Integer getItem_c_r_12() {
        return item_c_r_12;
    }

    public void setItem_c_r_12(Integer item_c_r_12) {
        this.item_c_r_12 = item_c_r_12;
    }

    public Integer getItem_c_r_13() {
        return item_c_r_13;
    }

    public void setItem_c_r_13(Integer item_c_r_13) {
        this.item_c_r_13 = item_c_r_13;
    }

    public String getItem_c_r_13_other() {
        return item_c_r_13_other;
    }

    public void setItem_c_r_13_other(String item_c_r_13_other) {
        this.item_c_r_13_other = item_c_r_13_other;
    }

    public Integer getItem_c_r_14() {
        return item_c_r_14;
    }

    public void setItem_c_r_14(Integer item_c_r_14) {
        this.item_c_r_14 = item_c_r_14;
    }

    public Integer getItem_c_r_15() {
        return item_c_r_15;
    }

    public void setItem_c_r_15(Integer item_c_r_15) {
        this.item_c_r_15 = item_c_r_15;
    }

    public Integer getItem_c_r_16() {
        return item_c_r_16;
    }

    public void setItem_c_r_16(Integer item_c_r_16) {
        this.item_c_r_16 = item_c_r_16;
    }

    public Integer getItem_c_r_17() {
        return item_c_r_17;
    }

    public void setItem_c_r_17(Integer item_c_r_17) {
        this.item_c_r_17 = item_c_r_17;
    }

    public Integer getItem_c_r_18() {
        return item_c_r_18;
    }

    public void setItem_c_r_18(Integer item_c_r_18) {
        this.item_c_r_18 = item_c_r_18;
    }

    public Integer getItem_c_r_19() {
        return item_c_r_19;
    }

    public void setItem_c_r_19(Integer item_c_r_19) {
        this.item_c_r_19 = item_c_r_19;
    }

    public String getItem_c_r_19_other() {
        return item_c_r_19_other;
    }

    public void setItem_c_r_19_other(String item_c_r_19_other) {
        this.item_c_r_19_other = item_c_r_19_other;
    }

    public Integer getItem_c_s_1() {
        return item_c_s_1;
    }

    public void setItem_c_s_1(Integer item_c_s_1) {
        this.item_c_s_1 = item_c_s_1;
    }

    public Integer getItem_c_s_2() {
        return item_c_s_2;
    }

    public void setItem_c_s_2(Integer item_c_s_2) {
        this.item_c_s_2 = item_c_s_2;
    }

    public Integer getItem_c_s_3() {
        return item_c_s_3;
    }

    public void setItem_c_s_3(Integer item_c_s_3) {
        this.item_c_s_3 = item_c_s_3;
    }

    public Integer getItem_c_s_4() {
        return item_c_s_4;
    }

    public void setItem_c_s_4(Integer item_c_s_4) {
        this.item_c_s_4 = item_c_s_4;
    }

    public Integer getItem_c_s_5() {
        return item_c_s_5;
    }

    public void setItem_c_s_5(Integer item_c_s_5) {
        this.item_c_s_5 = item_c_s_5;
    }

    public String getItem_c_s_5_other() {
        return item_c_s_5_other;
    }

    public void setItem_c_s_5_other(String item_c_s_5_other) {
        this.item_c_s_5_other = item_c_s_5_other;
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

    public Integer getCommunityDeptId() {
        return communityDeptId;
    }

    public void setCommunityDeptId(Integer communityDeptId) {
        this.communityDeptId = communityDeptId;
    }

    public Integer getAreaDeptId() {
        return areaDeptId;
    }

    public void setAreaDeptId(Integer areaDeptId) {
        this.areaDeptId = areaDeptId;
    }

    public Integer getTodoId() {
        return todoId;
    }

    public void setTodoId(Integer todoId) {
        this.todoId = todoId;
    }

    public Integer getColonoscopyRecordId() {
        return colonoscopyRecordId;
    }

    public void setColonoscopyRecordId(Integer colonoscopyRecordId) {
        this.colonoscopyRecordId = colonoscopyRecordId;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(String editStatus) {
        this.editStatus = editStatus;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getEditoperationSource() {
        return editoperationSource;
    }

    public void setEditoperationSource(String editoperationSource) {
        this.editoperationSource = editoperationSource;
    }

    public Integer getOperationSourceId() {
        return operationSourceId;
    }

    public void setOperationSourceId(Integer operationSourceId) {
        this.operationSourceId = operationSourceId;
    }

    public Integer getReissueNotification() {
        return reissueNotification;
    }

    public void setReissueNotification(Integer reissueNotification) {
        this.reissueNotification = reissueNotification;
    }
}
