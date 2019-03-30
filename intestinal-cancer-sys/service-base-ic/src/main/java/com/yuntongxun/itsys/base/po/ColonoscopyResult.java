package com.yuntongxun.itsys.base.po;

import java.util.Date;
import java.util.List;

/**
 * 肠镜结果记录表
 */
public class ColonoscopyResult {
    private Integer id;// int  物理主键，自增
    private String sid;// varchar(64) 受试者唯一标识
    private Integer todoId;//待办ID
    private Integer colonoscopyRecordId;//结肠镜检查记录
    private Integer stage;// int 阶段，1：T0，2：T1，3：T2
    private Date surveyDate;//datetime 调查日期
    private Integer item_2_1;// int 参与者是否进行了直肠指诊（无法耐受肠镜检查者）？1：是，2：否
    private Integer item_2_1_a;//int 有无肿块 1：有，2：无
    private Double item_2_1_b;//double 肿块距肛cm
    private Integer item_2_1_c;//几点钟
    private Integer item_2_1_d;//占据肠腔 分子
    private Integer item_2_1_e;//占据肠腔 分母
    private Integer item_2_2;// int 参与者是否完成了结肠镜检查？1：是，2：否
    private Integer item_3_1;// int 肠镜操作方式?1：单人，2：双人，3：单/双人
    private Integer item_3_2;// int 是否采用麻醉？1：是，2：否
    private Integer item_3_3;// int 肠镜到达深度？1：回肠末端，2：回盲瓣，3：升结肠，4：肝曲，5：横结肠，6：脾曲，7：降结肠，8：乙状结肠，9：直肠
    private Integer item_3_4;// int 肠道准备情况？1：I 级（肠道准备满意），2：II级（肠道准备比较满意），3：III级（肠道准备不满意）
    private Integer item_3_5;// int 肠镜完成时间，分钟
    private Integer item_3_6_1;// int 是否发生并发症（可多选）？无，1：true，2：false；
    private Integer item_3_6_2;// int 是否发生并发症（可多选）？肠道穿孔，1：true，2：false；
    private Integer item_3_6_3;// int 是否发生并发症（可多选）？出血，1：true，2：false；
    private String item_3_6_3_1;// varchar(64) 出血程度
    private String item_3_6_3_2;// varchar(64) 处理情况
    private Integer item_3_6_4;// int 是否发生并发症（可多选）？其他
    private String item_3_6_4_other;// varchar(64) 其他说明
    private Integer item_3_7;// int 是否检出息肉？1：是，2：否
    private String item_3_7_a;  //检出息肉的具体数（息肉具体个数的限制为：1-20的整数）
    private Integer item_3_8;// int 是否检出除息肉外其他病变？1：是，2：否
    private String item_3_8_other;// varchar(64) 其他病变类型（请说明）
    private String otherLesions;// varchar(1024) 其它病变
    private String endoscopicDiagnosis;// varchar(1024) 内镜下诊断
    private String diagnosisDoctor;// varchar(32) 诊断医生
    private Integer pathology;// int 是否做了病理 ？1：是，2：否
    private Integer communityDeptId;// int 社区ID
    private Integer areaDeptId;// int 地区医院ID
    private Date dateCreated;//  创建时间
    private Date updateTime;// 更新时间
    private Integer operationSourceId;//操作id
    private String editoperationSource;//渲染操作来源
    private String  applyStatus;
    private String  editStatus;
    private String  approvalStatus;
    private String imagePath;    //图片路径
    private List<FileUploadLogPO> fileUploads;  //图片详情
    private List<ColonoscopyLesionsRecord> lesionsRecordList;//病变记录

    private Integer delNotification;//是否删除告知书--非数据库字段  1是 2否

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

    public Date getSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(Date surveyDate) {
        this.surveyDate = surveyDate;
    }

    public Integer getItem_2_1() {
        return item_2_1;
    }

    public void setItem_2_1(Integer item_2_1) {
        this.item_2_1 = item_2_1;
    }

    public Integer getItem_2_2() {
        return item_2_2;
    }

    public void setItem_2_2(Integer item_2_2) {
        this.item_2_2 = item_2_2;
    }

    public Integer getItem_3_1() {
        return item_3_1;
    }

    public void setItem_3_1(Integer item_3_1) {
        this.item_3_1 = item_3_1;
    }

    public Integer getItem_3_2() {
        return item_3_2;
    }

    public void setItem_3_2(Integer item_3_2) {
        this.item_3_2 = item_3_2;
    }

    public Integer getItem_3_3() {
        return item_3_3;
    }

    public void setItem_3_3(Integer item_3_3) {
        this.item_3_3 = item_3_3;
    }

    public Integer getItem_3_4() {
        return item_3_4;
    }

    public void setItem_3_4(Integer item_3_4) {
        this.item_3_4 = item_3_4;
    }

    public Integer getItem_3_5() {
        return item_3_5;
    }

    public void setItem_3_5(Integer item_3_5) {
        this.item_3_5 = item_3_5;
    }

    public Integer getItem_3_6_1() {
        return item_3_6_1;
    }

    public void setItem_3_6_1(Integer item_3_6_1) {
        this.item_3_6_1 = item_3_6_1;
    }

    public Integer getItem_3_6_2() {
        return item_3_6_2;
    }

    public void setItem_3_6_2(Integer item_3_6_2) {
        this.item_3_6_2 = item_3_6_2;
    }

    public Integer getItem_3_6_3() {
        return item_3_6_3;
    }

    public void setItem_3_6_3(Integer item_3_6_3) {
        this.item_3_6_3 = item_3_6_3;
    }

    public String getItem_3_6_3_1() {
        return item_3_6_3_1;
    }

    public void setItem_3_6_3_1(String item_3_6_3_1) {
        this.item_3_6_3_1 = item_3_6_3_1;
    }

    public String getItem_3_6_3_2() {
        return item_3_6_3_2;
    }

    public void setItem_3_6_3_2(String item_3_6_3_2) {
        this.item_3_6_3_2 = item_3_6_3_2;
    }

    public Integer getItem_3_6_4() {
        return item_3_6_4;
    }

    public void setItem_3_6_4(Integer item_3_6_4) {
        this.item_3_6_4 = item_3_6_4;
    }

    public String getItem_3_6_4_other() {
        return item_3_6_4_other;
    }

    public void setItem_3_6_4_other(String item_3_6_4_other) {
        this.item_3_6_4_other = item_3_6_4_other;
    }

    public Integer getItem_3_7() {
        return item_3_7;
    }

    public void setItem_3_7(Integer item_3_7) {
        this.item_3_7 = item_3_7;
    }

    public Integer getItem_3_8() {
        return item_3_8;
    }

    public void setItem_3_8(Integer item_3_8) {
        this.item_3_8 = item_3_8;
    }

    public String getItem_3_8_other() {
        return item_3_8_other;
    }

    public void setItem_3_8_other(String item_3_8_other) {
        this.item_3_8_other = item_3_8_other;
    }

    public String getOtherLesions() {
        return otherLesions;
    }

    public void setOtherLesions(String otherLesions) {
        this.otherLesions = otherLesions;
    }

    public String getEndoscopicDiagnosis() {
        return endoscopicDiagnosis;
    }

    public void setEndoscopicDiagnosis(String endoscopicDiagnosis) {
        this.endoscopicDiagnosis = endoscopicDiagnosis;
    }

    public String getDiagnosisDoctor() {
        return diagnosisDoctor;
    }

    public void setDiagnosisDoctor(String diagnosisDoctor) {
        this.diagnosisDoctor = diagnosisDoctor;
    }

    public Integer getPathology() {
        return pathology;
    }

    public void setPathology(Integer pathology) {
        this.pathology = pathology;
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

    public List<ColonoscopyLesionsRecord> getLesionsRecordList() {
        return lesionsRecordList;
    }

    public void setLesionsRecordList(List<ColonoscopyLesionsRecord> lesionsRecordList) {
        this.lesionsRecordList = lesionsRecordList;
    }

    public Integer getItem_2_1_a() {
        return item_2_1_a;
    }

    public void setItem_2_1_a(Integer item_2_1_a) {
        this.item_2_1_a = item_2_1_a;
    }

    public Double getItem_2_1_b() {
        return item_2_1_b;
    }

    public void setItem_2_1_b(Double item_2_1_b) {
        this.item_2_1_b = item_2_1_b;
    }

    public Integer getItem_2_1_c() {
        return item_2_1_c;
    }

    public void setItem_2_1_c(Integer item_2_1_c) {
        this.item_2_1_c = item_2_1_c;
    }

    public Integer getItem_2_1_d() {
        return item_2_1_d;
    }

    public void setItem_2_1_d(Integer item_2_1_d) {
        this.item_2_1_d = item_2_1_d;
    }

    public Integer getItem_2_1_e() {
        return item_2_1_e;
    }

    public void setItem_2_1_e(Integer item_2_1_e) {
        this.item_2_1_e = item_2_1_e;
    }

    public String getItem_3_7_a() {
        return item_3_7_a;
    }

    public void setItem_3_7_a(String item_3_7_a) {
        this.item_3_7_a = item_3_7_a;
    }

    public Integer getOperationSourceId() {
        return operationSourceId;
    }

    public void setOperationSourceId(Integer operationSourceId) {
        this.operationSourceId = operationSourceId;
    }

    public String getEditoperationSource() {
        return editoperationSource;
    }

    public void setEditoperationSource(String editoperationSource) {
        this.editoperationSource = editoperationSource;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<FileUploadLogPO> getFileUploads() {
        return fileUploads;
    }

    public void setFileUploads(List<FileUploadLogPO> fileUploads) {
        this.fileUploads = fileUploads;
    }

    public Integer getDelNotification() {
        return delNotification;
    }

    public void setDelNotification(Integer delNotification) {
        this.delNotification = delNotification;
    }
}
