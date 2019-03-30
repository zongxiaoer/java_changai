package com.yuntongxun.itsys.base.dao;

import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.HtEvent;
import com.yuntongxun.itsys.base.vo.TodoVo;

import java.util.List;
/**
 * 查询社区医院各种待办事项个数
 * @author liugb
 * Date 2018 4 16
 */
public interface HtEventDao {
	//查询社区医院各种待办事项个数接口
	public TodoVo queryCommunityEvent(int dept_id,int status) throws ItSysException;
	public TodoVo queryAreaEvent(int dept_id,int status) throws ItSysException;
	public TodoVo queryNationHtEvent(int dept_id,int status) throws ItSysException;

	public void updateStatus(String sid,int dataId, int type, int status);

	public void insert(HtEvent event);
	
	//国家统计未录入肠镜结果人数、未录入病理结果人数、未录入筛查告知书人数
	public List queryNationCountByNation(int nationId);
	//国家代办统计社区个数展示
	public List getCommunitCountsByNation(int nationId);
	//根据类型 和id查询代办详情
	public List getlistDetailById(String type,int id);
	//根据类型 和id查询代办详情
	public ListPageUtil getlistDetailById(TodoVo vo);

	void updateStatusOperation(String sid, Integer colonoscopyRecordId, int eventType5, String editoperationSource, Integer operationSourceId);

	TodoVo queryCommunityAndUserEvent(int deptId, int status,String userName);

    void delNotificationTodoEvent(String sid, Integer dataId);

	void delPathologyTodoEvent(String sid, Integer dataId);

    void delNotificationFFTodoEvent(String sid, Integer dataId);
}
