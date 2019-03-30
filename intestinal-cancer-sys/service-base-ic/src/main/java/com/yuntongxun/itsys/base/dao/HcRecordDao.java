package com.yuntongxun.itsys.base.dao;

import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.HospitalColonoscopyRecord;
import com.yuntongxun.itsys.base.vo.TodoVo;
import com.yuntongxun.itsys.base.vo.TodoVoPo;

import java.util.List;

/**
 * 立即预约页面查询待预约受试者接口
 * @author liugb
 * Date 2018 4 16
 */
public interface HcRecordDao {
	//立即预约页面查询待预约受试者接口
    public ListPageUtil getHcRecordReview(int dept_id,TodoVo vo);
    //查询 未按时完成结肠镜检查 受试者列表
    public ListPageUtil notFinishColonoscopy(Integer communityDeptId, int status, TodoVo vo);
    //查询 未按时完成结肠镜检查 按照地区
    public ListPageUtil notFinishColonoscopy1(Integer areaId, int status, TodoVo vo);
    //查看该社区医院，未发放筛查结果告知书  受试者列表
    public ListPageUtil notIssueNotification(Integer communityDeptId, int status, TodoVo vo);
    //查询未发放筛查结果告知书，按照地区
    public ListPageUtil notIssueNotification1(Integer areaId, int status, TodoVo vo);
    //查询未预约结肠镜检查待办
	public ListPageUtil notReserveColonoscopy(Integer communityDeptId, int status, TodoVo vo);
    //查询未预约结肠镜按照地区查看
    public ListPageUtil notReserveColonoscopy1(Integer areaId, int status, TodoVo vo);
	
	public ListPageUtil notEntryColonoscopyResultQuery(Integer areaDeptId,Integer communityDeptId, int status, TodoVo vo);
	
	public ListPageUtil notEntryPathologyResultQuery(Integer areaDeptId, Integer communityDeptId, int status, TodoVo vo);
	
	public ListPageUtil notEntryNotificationResultQuery(Integer areaDeptId, Integer communityDeptId, int status, TodoVo vo);

    public ListPageUtil notEntryNoSampleResultQuery(Integer areaDeptId, String communityDeptId, int status, TodoVoPo vo);
    /**
     * @func
     * @desc    根据社区id获取对应状态数据
     * @author zongt
     * @create 2018/4/下午1:22:22
     * @request
     * @response
     **/
    List<HospitalColonoscopyRecord> getStatusByAllocation_id(String status,String ids);
    //根据市区id查询所对应的社区
    public List notEntryAllocation(Integer areaId, int status);
    //未完成结肠镜检查
    public List notEntryFinish(Integer areaId, int status, TodoVo vo);
    //根据市区id查询未发放筛查结果告知书
    public List notPutoutNotice(Integer areaId, int status, TodoVo vo);

    ListPageUtil notEntryNoDNAResultQuery(Integer areaDeptId, String communityDeptId, int status, TodoVoPo vo);
    //待接收生物样本 粪便系类  12
    public ListPageUtil notEntrySampleF(Integer areaDeptId,Integer communityDeptId,int status,TodoVo vo);
    //待接收生物样本 唾液系列  13
    public ListPageUtil notEntrySampleM(Integer areaDeptId,Integer communityDeptId,int status,TodoVo vo);
    //待接收生物样本 血液系列  18
    public ListPageUtil notEntrySampleS(Integer areaDeptId,Integer communityDeptId,int status,TodoVo vo);
    //新增未发放粪便DNA结果
    public ListPageUtil notEntryStollDNA(Integer areaDeptId,int status,TodoVo vo);
    //新增未发放粪便DNA结果社区
    public ListPageUtil notEntryStollDNA1(Integer communityDeptId, int status,TodoVo vo);

    //癌症地区代办
    ListPageUtil notEntryCancerQuery(Integer areaDeptId, String communityDeptId, int i, TodoVoPo vo);

    /**
     * 癌症国家代办   type 类型  20,21,22,23：癌相关详情信息  zl
     * @param communityDeptId
     * @param i
     * @param vo
     * @return
     */
    ListPageUtil notEntryStoCancer(Integer communityDeptId, int i, TodoVo vo);
}
