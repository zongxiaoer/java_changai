package com.yuntongxun.itsys.base.po;

import java.util.Date;

/**
 * 违反方案
 */
public class ViolationScheme {


    private String dataId; //列表中的dataId属性 为空表示新增，不为空表示修改

    private String eventType;//1违反方案，2不良事件

    private Integer id;

    private String sid;

    private Integer stage;//阶段，1：T0，2：T1，3：T2，4：T3

    private Date tbDate;//填表日期

    private String tbrName;//填表人姓名

    private String zkzName;//质控者姓名

    private String deptCode;//筛查现场编码

    private String investigatorCode;// 筛查现场工作人员编码

    private String checkYear;//检查年份
    private Integer item_1a_1;// 筛查对象随机分配无效   1：是，2：否
    private Integer item_1a_2;//对筛查对象进行了一次以上的随机分配  1：是，2：否
    private String item_1a_2_id;// 初始ID
    private Integer item_1a_3;// 筛查对象在完成研究前未填写知情同意书   1：是，2：否
    private Integer item_1a_4;//筛查对象在筛查之前已报告或确诊患有癌症 1：是，2：否
    private Integer item_1a_5;//随机化分组后的对象接受了其它干预组提供的筛查   1：是，2：否
    private String item_1a_5_des;//具体说明
    private Integer item_1a_6;//向筛查对象报告了错误的结果   1：是，2：否
    private Integer item_1a_7;//进行了重复的筛查   1：是，2：否
    private Integer item_1a_8;//受保护的医疗信息遭泄露 1：是，2：否
    private Integer item_1a_9;//随机分组分配ID号后，因受试者主观原因 1：是，2：否
    private String item_1a_9_cause;//具体原因
    private String item_1a_9_des;//说明
    private Integer item_1a_10;//其它，请说明（请在下方详细说明任何上述未涉及的违规情况） 1：是，2：否
    private String item_1a_10_other;//其他
    private Date item_2a_1;// 方案违规事件发现日期
    private Date item_2b_1;// 方案违规事件发生日期
    private Integer item_3a_1;// 年龄小于50或大于74岁 1：是，2：否
    private Integer item_3a_2;// 曾有医生告诉过患有结直肠癌   1：是，2：否
    private Date item_3a_2_time;// 结直肠癌确诊日期
    private Integer item_3a_2_estimate;//为预估日期 1：是，2：否
    private Integer item_3a_3;// 进行过结直肠癌切除手术   1：是，2：否
    private Integer item_3a_4;//正在接受任何癌症相关的治疗（非黑色素皮肤癌除外）   1：是，2：否
    private Integer item_3a_5;//在过去5年里，做过结肠镜、乙状结肠镜检查、气钡双重造影、CT仿真结肠镜等结直肠癌筛查；或者CT肠道成像等结直肠癌筛查   1：是，2：否
    private Integer item_3a_6;//在过去1年里，接受过粪便潜血检测或者粪便DNA检测   1：是，2：否
    private Integer item_3a_7;//有下列下消化道疾病提示需要结肠镜进行确认？1）过去半年中长时间的直肠出血；2）明确诊断的缺铁性贫血；3）过去6个月中有记录的不明原因的体重下降（超过10%基础体重）1：是，2：否
    private Integer item_3a_8;//有其他严重疾病（包括严重的肺部疾病，晚期肾病，晚期肝病、严重的心衰、近期诊断为除黑色素皮肤癌外的其他癌症）   1：是，2：否
    private Integer item_3a_9;// 未签署知情同意书 1：是，2：否
    private Integer item_3a_10;// 随机分组后分配ID号后，因受试者主观原因在未接受任何筛查干预前决意退出试验   1：是，2：否
    private String item_3a_10_cause;//原因
    private String item_4a_1;//请依据以下几点描述违反方案情况：违规是如何被发现的？如何发生的？对受试者的影响是什么？此次违规进行了怎样的处理（包括与筛查对象联系、更换系统或流程、填写表格等）？为预防此类事件的再次发生，采取了哪些措施？
    private Integer communityDeptId;// int 社区ID
    private Integer areaDeptId;// int 地区医院ID

    private Integer createUser;//创建者
    private Integer updateUser;//修改者
    private Date dateCreated;// 创建时间
    private Date updateTime;//更新时间
    private Integer schemeType;
    private String quitLogId;
    private Integer entryStatus;   //1:已录入，2：未录入

    private String applyStatus;
    private String editStatus;
    private String approvalStatus;

    private String editType;//1编辑 2录入



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

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

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

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public Date getTbDate() {
        return tbDate;
    }

    public void setTbDate(Date tbDate) {
        this.tbDate = tbDate;
    }

    public String getTbrName() {
        return tbrName;
    }

    public void setTbrName(String tbrName) {
        this.tbrName = tbrName;
    }

    public String getZkzName() {
        return zkzName;
    }

    public void setZkzName(String zkzName) {
        this.zkzName = zkzName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getInvestigatorCode() {
        return investigatorCode;
    }

    public void setInvestigatorCode(String investigatorCode) {
        this.investigatorCode = investigatorCode;
    }

    public String getCheckYear() {
        return checkYear;
    }

    public void setCheckYear(String checkYear) {
        this.checkYear = checkYear;
    }

    public Integer getItem_1a_1() {
        return item_1a_1;
    }

    public void setItem_1a_1(Integer item_1a_1) {
        this.item_1a_1 = item_1a_1;
    }

    public Integer getItem_1a_2() {
        return item_1a_2;
    }

    public void setItem_1a_2(Integer item_1a_2) {
        this.item_1a_2 = item_1a_2;
    }

    public String getItem_1a_2_id() {
        return item_1a_2_id;
    }

    public void setItem_1a_2_id(String item_1a_2_id) {
        this.item_1a_2_id = item_1a_2_id;
    }

    public Integer getItem_1a_3() {
        return item_1a_3;
    }

    public void setItem_1a_3(Integer item_1a_3) {
        this.item_1a_3 = item_1a_3;
    }

    public Integer getItem_1a_4() {
        return item_1a_4;
    }

    public void setItem_1a_4(Integer item_1a_4) {
        this.item_1a_4 = item_1a_4;
    }

    public Integer getItem_1a_5() {
        return item_1a_5;
    }

    public void setItem_1a_5(Integer item_1a_5) {
        this.item_1a_5 = item_1a_5;
    }

    public String getItem_1a_5_des() {
        return item_1a_5_des;
    }

    public void setItem_1a_5_des(String item_1a_5_des) {
        this.item_1a_5_des = item_1a_5_des;
    }

    public Integer getItem_1a_6() {
        return item_1a_6;
    }

    public void setItem_1a_6(Integer item_1a_6) {
        this.item_1a_6 = item_1a_6;
    }

    public Integer getItem_1a_7() {
        return item_1a_7;
    }

    public void setItem_1a_7(Integer item_1a_7) {
        this.item_1a_7 = item_1a_7;
    }

    public Integer getItem_1a_8() {
        return item_1a_8;
    }

    public void setItem_1a_8(Integer item_1a_8) {
        this.item_1a_8 = item_1a_8;
    }

    public Integer getItem_1a_9() {
        return item_1a_9;
    }

    public void setItem_1a_9(Integer item_1a_9) {
        this.item_1a_9 = item_1a_9;
    }

    public String getItem_1a_9_cause() {
        return item_1a_9_cause;
    }

    public void setItem_1a_9_cause(String item_1a_9_cause) {
        this.item_1a_9_cause = item_1a_9_cause;
    }

    public String getItem_1a_9_des() {
        return item_1a_9_des;
    }

    public void setItem_1a_9_des(String item_1a_9_des) {
        this.item_1a_9_des = item_1a_9_des;
    }

    public Integer getItem_1a_10() {
        return item_1a_10;
    }

    public void setItem_1a_10(Integer item_1a_10) {
        this.item_1a_10 = item_1a_10;
    }

    public Date getItem_2a_1() {
        return item_2a_1;
    }

    public void setItem_2a_1(Date item_2a_1) {
        this.item_2a_1 = item_2a_1;
    }

    public Date getItem_2b_1() {
        return item_2b_1;
    }

    public void setItem_2b_1(Date item_2b_1) {
        this.item_2b_1 = item_2b_1;
    }

    public Integer getItem_3a_1() {
        return item_3a_1;
    }

    public void setItem_3a_1(Integer item_3a_1) {
        this.item_3a_1 = item_3a_1;
    }

    public Integer getItem_3a_2() {
        return item_3a_2;
    }

    public void setItem_3a_2(Integer item_3a_2) {
        this.item_3a_2 = item_3a_2;
    }

    public Date getItem_3a_2_time() {
        return item_3a_2_time;
    }

    public void setItem_3a_2_time(Date item_3a_2_time) {
        this.item_3a_2_time = item_3a_2_time;
    }

    public Integer getItem_3a_3() {
        return item_3a_3;
    }

    public void setItem_3a_3(Integer item_3a_3) {
        this.item_3a_3 = item_3a_3;
    }

    public Integer getItem_3a_4() {
        return item_3a_4;
    }

    public void setItem_3a_4(Integer item_3a_4) {
        this.item_3a_4 = item_3a_4;
    }

    public Integer getItem_3a_5() {
        return item_3a_5;
    }

    public void setItem_3a_5(Integer item_3a_5) {
        this.item_3a_5 = item_3a_5;
    }

    public Integer getItem_3a_6() {
        return item_3a_6;
    }

    public void setItem_3a_6(Integer item_3a_6) {
        this.item_3a_6 = item_3a_6;
    }

    public Integer getItem_3a_7() {
        return item_3a_7;
    }

    public void setItem_3a_7(Integer item_3a_7) {
        this.item_3a_7 = item_3a_7;
    }

    public Integer getItem_3a_8() {
        return item_3a_8;
    }

    public void setItem_3a_8(Integer item_3a_8) {
        this.item_3a_8 = item_3a_8;
    }

    public Integer getItem_3a_9() {
        return item_3a_9;
    }

    public void setItem_3a_9(Integer item_3a_9) {
        this.item_3a_9 = item_3a_9;
    }

    public Integer getItem_3a_10() {
        return item_3a_10;
    }

    public void setItem_3a_10(Integer item_3a_10) {
        this.item_3a_10 = item_3a_10;
    }

    public String getItem_3a_10_cause() {
        return item_3a_10_cause;
    }

    public void setItem_3a_10_cause(String item_3a_10_cause) {
        this.item_3a_10_cause = item_3a_10_cause;
    }

    public String getItem_4a_1() {
        return item_4a_1;
    }

    public void setItem_4a_1(String item_4a_1) {
        this.item_4a_1 = item_4a_1;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
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

    public Integer getItem_3a_2_estimate() {
        return item_3a_2_estimate;
    }

    public void setItem_3a_2_estimate(Integer item_3a_2_estimate) {
        this.item_3a_2_estimate = item_3a_2_estimate;
    }

    public String getItem_1a_10_other() {
        return item_1a_10_other;
    }

    public void setItem_1a_10_other(String item_1a_10_other) {
        this.item_1a_10_other = item_1a_10_other;
    }

    public Integer getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(Integer schemeType) {
        this.schemeType = schemeType;
    }

    public String getQuitLogId() {
        return quitLogId;
    }

    public void setQuitLogId(String quitLogId) {
        this.quitLogId = quitLogId;
    }
    public Integer getEntryStatus() {
        return entryStatus;
    }

    public void setEntryStatus(Integer entryStatus) {
        this.entryStatus = entryStatus;
    }

    public String getEditType() {
        return editType;
    }

    public void setEditType(String editType) {
        this.editType = editType;
    }
}
