package com.yuntongxun.itsys.base.dao.impl;

import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.dao.*;
import com.yuntongxun.itsys.base.po.HtEvent;
import com.yuntongxun.itsys.base.vo.TodoVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class HtEventDaoImpl implements HtEventDao{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private final Logger log = LogManager.getLogger(HtEventDaoImpl.class.getName());
	@Autowired
	private HiReviewDao hireviewDao;
	@Autowired
	private HcRecordDao hcRecordDao;
	@Autowired
	private HFitResultDao hFitResultDao;
	@Autowired
	private HStollDnaDao hStollDnaDao;
	/**
	 * 查询社区医院各种待办事项个数
	 */
	@Override
	public TodoVo queryCommunityEvent(int dept_id, int status)
			throws ItSysException {
		// TODO Auto-generated method stub
		log.info("@Dao 各种待办事项个数 query  Start ");
		String sql = "select "
				+ "(select count(*) from hospital_todo_event where type =1  and community_dept_id = "+dept_id+" and status = 1)  as riskFactors,"
				+ "(select count(*) from hospital_todo_event where type =2  and community_dept_id = "+dept_id+" and status = 1) as notEntryFITCode,"
				+ "(select count(*) from hospital_todo_event where type =3  and community_dept_id = "+dept_id+" and status = 1) as notEntryFITResult,"
				+ "(select count(*) from hospital_todo_event where type =4  and community_dept_id = "+dept_id+" and status = 1) as notStoolDnaCode,"
				+ "(select count(*) from hospital_todo_event where type =5  and community_dept_id = "+dept_id+" and status = 1) as notReserve,"
				+ "(select count(*) from hospital_todo_event where type =6  and community_dept_id = "+dept_id+" and status = 1) as notFinishExamination,"
				+ "(select count(*) from hospital_todo_event where type =7  and community_dept_id = "+dept_id+" and status = 1) as notIssueNotification,"
				+ "(select count(*) from hospital_todo_event where type =17  and community_dept_id = "+dept_id+" and status = 1) as notIssueDna"
				+ " from dual";
		log.info("queryHtEvent"+sql);
		TodoVo vo = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<TodoVo>(TodoVo.class));
		return vo;
	}
	
	/**
	 * 查询地区医院各种待办事项个数
	 */
	@Override
	public TodoVo queryAreaEvent(int dept_id, int status)
			throws ItSysException {
		// TODO Auto-generated method stub
		log.info("@Dao 各种待办事项个数 query  Start ");
		String sql = "select "
				+ "(select count(*) from hospital_todo_event where type =8  and area_dept_id = "+dept_id+" and status = 1)  as notEntryColonoscopyResult,"
				+ "(select count(*) from hospital_todo_event where type =9  and area_dept_id = "+dept_id+" and status = 1) as notEntryPathologyResult,"
				+ "(select count(*) from hospital_todo_event where type =10  and area_dept_id = "+dept_id+" and status = 1) as notEntryNotificationResult,"
				+ "(select count(*) from hospital_todo_event where type ="+Constans.PERSON_TODO_EVENT_TYPE18+"  and area_dept_id = "+dept_id+" and status = 1) as noBloodSampleResult,"//血液 Constans.PERSON_TODO_EVENT_TYPE11+" or type ="+Constans.PERSON_TODO_EVENT_TYPE14+" or type ="+Constans.PERSON_TODO_EVENT_TYPE15+") and area_dept_id = "+dept_id+
				+ "(select count(*) from hospital_todo_event where type ="+Constans.PERSON_TODO_EVENT_TYPE12+"  and area_dept_id = "+dept_id+" and status = 1) as noFecesSampleResult,"//粪便
				+ "(select count(*) from hospital_todo_event where type ="+Constans.PERSON_TODO_EVENT_TYPE13+"  and area_dept_id = "+dept_id+" and status = 1) as noSalivaSampleResult,"//唾液
				+ "(select count(*) from hospital_todo_event where type ="+Constans.PERSON_TODO_EVENT_TYPE19+"  and area_dept_id = "+dept_id+" and status = 1) as noChangResultPicture,"//肠镜图片代办
				+ "(select count(*) from hospital_todo_event where (type ="+Constans.PERSON_TODO_EVENT_TYPE20+" or type ="+Constans.PERSON_TODO_EVENT_TYPE21+" or type ="+Constans.PERSON_TODO_EVENT_TYPE22+" or type ="+Constans.PERSON_TODO_EVENT_TYPE23+" ) and area_dept_id = "+dept_id+" and status = 1) as noCancerResult"//癌症    zongtong  2018-09-07
				+ " from dual";
		log.info("queryHtEvent"+sql);
		TodoVo vo = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<TodoVo>(TodoVo.class));
		return vo;
	}
	
	/**
	 * 查询国家医院各种待办事项个数
	 */
	@Override
	public TodoVo queryNationHtEvent(int dept_id, int status)
			throws ItSysException {
/*		// TODO Auto-generated method stub
		log.info("@Dao 各种待办事项个数 query  Start ");
		String sql = "select count(*) as toBeReceivedBiologicalSamples from hospital_todo_event where type =11 and status = 1";
		log.info("queryHtEvent"+sql);
		TodoVo vo = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<TodoVo>(TodoVo.class));
		return vo;*/
		log.info("@Dao 各种待办事项个数 query  Start ");
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append("select ");
		stringBuffer.append("(select count(*)  from hospital_todo_event where type =11 and status = 1) as  toBeReceivedBiologicalSamples, ");
		stringBuffer.append("(select count(*)  from hospital_todo_event where type ="+Constans.PERSON_TODO_EVENT_TYPE16+" and status = 1) as  noStoolDNAResult");//DNA结果代办
		stringBuffer.append(" from dual");
		log.info("queryHtEvent"+stringBuffer.toString());
		TodoVo vo = jdbcTemplate.queryForObject(stringBuffer.toString(),new BeanPropertyRowMapper<TodoVo>(TodoVo.class));
		return vo;
	}
	
	
	@Override
	public void updateStatus(String sid,int dataId, int type, int status) {
		String sql = "update hospital_todo_event set status=?,update_time=now() where sid=? and `data_id`=? and `type`=?";
		jdbcTemplate.update(sql, new Object[]{status,sid,dataId,type});
	}

	@Override
	public void insert(HtEvent event) {
		 String sql = "insert into hospital_todo_event(type,community_dept_id,area_dept_id,sid,status,data_id,date_created,update_time,operation_source,operation_source_id) values(?,?,?,?,?,?,now(),now(),?,?)";
		 jdbcTemplate.update(sql, new Object[]{event.getType(),event.getCommunityDeptId(),event.getAreaDeptId(),event.getSid(),event.getStatus(),event.getDataId(),event.getOperationSource(),event.getOperationSourceId()});
	}
    /**
     * 国家统计未录入肠镜结果人数、未录入病理结果人数、未录入筛查告知书人数
     * 新增 “未录入血清样本”、“未录入血浆样本”、“未录入白细胞样本”、“未录入唾液样本”、“未录入粪便样本”
     * 二修改：增加血液样本，去掉血浆、血清、白细胞
	 * zl  添加类型为 20 21 22 23 的统计数量列信息
     */
	@Override
	public List queryNationCountByNation(int nationId) {
		// TODO Auto-generated method stub
		List<Map<String,Object>> queryAreasByNationId = hireviewDao.getAreaByNationId(nationId);
		int areas = 0;
		String areaName = "";
		List<Map<String,Object>> areaSums = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> map : queryAreasByNationId) {
			Map<String,Object> ams = new HashMap<>();
			areas = Integer.parseInt(map.get("id").toString());
			areaName = map.get("name").toString();
			String sql = "select "
					+ "(select count(*) from hospital_todo_event where type =8  and area_dept_id = "+areas+" and status = 1)  as notEntryColonoscopyResult,"
					+ "(select count(*) from hospital_todo_event where type =9  and area_dept_id = "+areas+" and status = 1) as notEntryPathologyResult,"
					+ "(select count(*) from hospital_todo_event where type =10  and area_dept_id = "+areas+" and status = 1) as notEntryNotificationResult,"
					+ "(select count(*) from hospital_todo_event where type = 18 and area_dept_id = "+areas+" and status = 1) as notEntrySampleS,"
					/*+ "(select count(*) from hospital_todo_event where type = 14 and area_dept_id = "+areas+" and status = 1) as notEntrySampleP,"
					+ "(select count(*) from hospital_todo_event where type = 15 and area_dept_id = "+areas+" and status = 1) as notEntrySampleW,"*/
					+ "(select count(*) from hospital_todo_event where type = 13 and area_dept_id = "+areas+" and status = 1) as notEntrySampleM,"
					+ "(select count(*) from hospital_todo_event where type = 12 and area_dept_id = "+areas+" and status = 1) as notEntrySampleFC,"
					+ "(select count(*) from hospital_todo_event where type IN (20,21,22,23) and area_dept_id = "+areas+" and status = 1) as notEntryCancer"
					+ " from dual";
			List<Map<String,Object>> ars = jdbcTemplate.queryForList(sql);
			ams.put("areaName", areaName);
			ams.put("areaId",areas);
			ams.put("list", ars);
			areaSums.add(ams);
		}
		return areaSums;
	}
    /**
     * 按社区统计代办个数
     * 新增“未发放粪便DNA结果”
     */
	@Override
	public List getCommunitCountsByNation(int nationId) {
		// TODO Auto-generated method stub
		List<Map<String,Object>> queryAreasByNationId = hireviewDao.getAreaByNationId(nationId);
		int areasId = 0;
		String areaName = "";
		List<Map<String,Object>> areaSums = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> map : queryAreasByNationId) {
			Map<String,Object> ams = new HashMap<>();
			areasId = Integer.parseInt(map.get("id").toString());
			areaName = map.get("name").toString();
			String sql = "select(select count(*) from hospital_todo_event t1 where t1.area_dept_id = "+areasId+" and t1.type =1 and t1.status = 1) as riskFactors,"
					+ "(select count(*) from hospital_todo_event t1 where t1.area_dept_id = "+areasId+" and t1.type =2 and t1.status = 1) as notEntryFITCode,"
					+ "(select count(*) from hospital_todo_event t1 where t1.area_dept_id = "+areasId+" and t1.type =3 and t1.status = 1) as notEntryFITResult,"
					+ "(select count(*) from hospital_todo_event t1 where t1.area_dept_id = "+areasId+" and t1.type =4 and t1.status = 1) as notStoolDnaCode,"
					+ "(select count(*) from hospital_todo_event t1 where t1.area_dept_id = "+areasId+" and t1.type =5 and t1.status = 1) as notReserve,"
					+ "(select count(*) from hospital_todo_event t1 where t1.area_dept_id = "+areasId+" and t1.type =6 and t1.status = 1) as notFinishExamination,"
					+ "(select count(*) from hospital_todo_event t1 where t1.area_dept_id = "+areasId+" and t1.type =7 and t1.status = 1) as notIssueNotification,"
					+ "(select count(*) from hospital_todo_event t1 where t1.area_dept_id = "+areasId+" and t1.type =17 and t1.status = 1) as notStoolDNAput"
					+ " from dual;";
			List<Map<String,Object>> comms = jdbcTemplate.queryForList(sql);
			ams.put("areaName", areaName);
			ams.put("areaId",areasId);
			ams.put("list", comms);
			areaSums.add(ams);
		}
		return areaSums;
	}
    /**
     * 根据类型和id查询所对应的代办详情
     */
	@Override
	public List getlistDetailById(String type, int id) {
		// TODO Auto-generated method stub
		String sql = "select distinct id,sid from hospital_todo_event where type = ? and status = 1";
		List<Map<String,Object>> details = jdbcTemplate.queryForList(sql,type);
		return details;
	}

	/**
	 * 实现类型返回查询接口方法实现
	 * @param vo
	 * 地区账号:(c:社区代办)
	 * 国家账号:(a:地区代办，b:社区代办)
	 * @return
	 */
	@Override
	public ListPageUtil getlistDetailById(TodoVo vo) {
		ListPageUtil list = null;
		if(vo.getType().equals("a1")){                          //8：待录入肠镜结果
			vo.setType("8");
			list = hcRecordDao.notEntryColonoscopyResultQuery(vo.getAreaDeptId(), vo.getCommunityDeptId(), 1, vo);
		}else if(vo.getType().equals("a2")){                    //9：待录入病理结果
			vo.setType("9");
			list = hcRecordDao.notEntryPathologyResultQuery(vo.getAreaDeptId(),vo.getCommunityDeptId(),1,vo);
		}else if(vo.getType().equals("a3")){                    //10：待录入筛查结果告知书
			vo.setType("10");
			list = hcRecordDao.notEntryNotificationResultQuery(vo.getAreaDeptId(),vo.getCommunityDeptId(), 1, vo);
		}else if(vo.getType().equals("a4")){                    //12：待接收生物样本 粪便系类
			vo.setType("12");
			list = hcRecordDao.notEntrySampleF(vo.getAreaDeptId(),vo.getCommunityDeptId(),1,vo);
		}else if(vo.getType().equals("a5")){                    //13：待接收生物样本 唾液系列
			vo.setType("13");
			list = hcRecordDao.notEntrySampleM(vo.getAreaDeptId(),vo.getCommunityDeptId(),1,vo);
		}else if(vo.getType().equals("a6")){                    //18：待接收生物样本 血液系列
			vo.setType("18");
			list = hcRecordDao.notEntrySampleS(vo.getAreaDeptId(),vo.getCommunityDeptId(),1,vo);
		}else if(vo.getType().equals("b1")){                    //1、未完成危险因素
			vo.setType("1");
			list = hireviewDao.notEntryRiskfactors1(vo.getAreaDeptId(),1,vo);
		}else if(vo.getType().equals("b2")){                    //2、未录入fit编号
			vo.setType("2");
			list = hFitResultDao.notEntryFitCodeList1(vo.getAreaDeptId(),1,vo);
		}else if(vo.getType().equals("b3")){                    //3、未录入fit结果
			vo.setType("3");
			list = hFitResultDao.notEntryFitResultList1(vo.getAreaDeptId(),1,vo);
		}else if(vo.getType().equals("b4")){                    //4、未录入粪便DNA编号
			vo.setType("4");
			list = hStollDnaDao.notEntryDNACodeList1(vo.getAreaDeptId(),1,vo);
		}else if(vo.getType().equals("b5")){                    //5、未预约结肠镜检查
			vo.setType("5");
			list = hcRecordDao.notReserveColonoscopy1(vo.getAreaDeptId(),1,vo);
		}else if(vo.getType().equals("b6")){                    //6、未完成结肠镜检查
			vo.setType("6");
			list = hcRecordDao.notFinishColonoscopy1(vo.getAreaDeptId(),1,vo);
		}else if(vo.getType().equals("b7")){                    //7、未通知筛查结果告知书
			vo.setType("7");
			list = hcRecordDao.notIssueNotification1(vo.getAreaDeptId(),1,vo);
		}else if(vo.getType().equals("b8")){                    //17、未发放粪便DNA结果
			vo.setType("17");
			list = hcRecordDao.notEntryStollDNA(vo.getAreaDeptId(),1,vo);
        }else if(vo.getType().equals("c1")){                   //1、未完成危险因素
			vo.setType("1");
			list = hireviewDao.notEntryRiskfactors(vo.getCommunityDeptId(),1,vo);
		}else if(vo.getType().equals("c2")){                   //2、未录入fit编号
			vo.setType("2");
			list = hFitResultDao.notEntryFitCodeList(vo.getCommunityDeptId(),1,vo);
		}else if(vo.getType().equals("c3")){                   //3、未录入fit结果
			vo.setType("3");
			list = hFitResultDao.notEntryFitResultList(vo.getCommunityDeptId(),1,vo);
		}else if(vo.getType().equals("c4")){                   //4、未录入粪便DNA编号
			vo.setType("4");
			list = hStollDnaDao.notEntryDNACodeList(vo.getCommunityDeptId(),1,vo);
		}else if(vo.getType().equals("c5")){                   //5、未预约结肠镜检查
			vo.setType("5");
			list = hcRecordDao.notReserveColonoscopy(vo.getCommunityDeptId(),1,vo);
		}else if(vo.getType().equals("c6")){                   //6、未完成结肠镜检查
			vo.setType("6");
			list = hcRecordDao.notFinishColonoscopy(vo.getCommunityDeptId(),1,vo);
		}else if(vo.getType().equals("c7")){                   //7、未通知筛查结果告知书
			vo.setType("7");
			list = hcRecordDao.notIssueNotification(vo.getCommunityDeptId(),1,vo);
		}else if(vo.getType().equals("c8")){                   //17、未发放粪便DNA结果
			vo.setType("17");
			list = hcRecordDao.notEntryStollDNA1(vo.getCommunityDeptId(),1,vo);
        }else if(vo.getType().equals("c9")){                   //20,21,22,23：癌相关详情信息
			vo.setType("(20,21,22,23)");
			list = hcRecordDao.notEntryStoCancer(vo.getCommunityDeptId(),1,vo);
		}
		return list;
	}

	@Override
	public void updateStatusOperation(String sid, Integer colonoscopyRecordId, int eventType5, String editoperationSource, Integer operationSourceId) {
		String sql = "update hospital_todo_event set operation_source=?,operation_source_id=?,update_time=now() where sid=? and `data_id`=? and `type`=?";
		jdbcTemplate.update(sql, new Object[]{editoperationSource,operationSourceId,sid,colonoscopyRecordId,eventType5});
	}

	@Override
	public TodoVo queryCommunityAndUserEvent(int dept_id, int status,String userName) {
		// TODO Auto-generated method stub
		log.info("@Dao 社区操作员各种待办事项个数 query  Start ");
		String sql = "select "
				+ "(select count(*) from hospital_todo_event t,hospital_intestine_review r where t.type =1  and t.community_dept_id = "+dept_id+" and t.status = 1 and r.sid=t.sid and r.create_user='"+userName+"' )  as riskFactors,"
				+ "(select count(*) from hospital_todo_event t,hospital_intestine_review r where t.type =2  and t.community_dept_id = "+dept_id+" and t.status = 1 and r.sid=t.sid and r.create_user='"+userName+"')  as notEntryFITCode,"
				+ "(select count(*) from hospital_todo_event t,hospital_intestine_review r where t.type =3  and t.community_dept_id = "+dept_id+" and t.status = 1 and r.sid=t.sid and r.create_user='"+userName+"')  as notEntryFITResult,"
				+ "(select count(*) from hospital_todo_event t,hospital_intestine_review r where t.type =4  and t.community_dept_id = "+dept_id+" and t.status = 1 and r.sid=t.sid and r.create_user='"+userName+"')  as notStoolDnaCode,"
				+ "(select count(*) from hospital_todo_event t,hospital_intestine_review r where t.type =5  and t.community_dept_id = "+dept_id+" and t.status = 1 and r.sid=t.sid and r.create_user='"+userName+"')  as notReserve,"
				+ "(select count(*) from hospital_todo_event t,hospital_intestine_review r where t.type =6  and t.community_dept_id = "+dept_id+" and t.status = 1 and r.sid=t.sid and r.create_user='"+userName+"')  as notFinishExamination,"
				+ "(select count(*) from hospital_todo_event t,hospital_intestine_review r where t.type =7  and t.community_dept_id = "+dept_id+" and t.status = 1 and r.sid=t.sid and r.create_user='"+userName+"')  as notIssueNotification,"
				+ "(select count(*) from hospital_todo_event t,hospital_intestine_review r where t.type =17  and t.community_dept_id = "+dept_id+" and t.status = 1 and r.sid=t.sid and r.create_user='"+userName+"')  as notIssueDna"





/*				+ "(select count(*) from hospital_todo_event where type =2  and community_dept_id = "+dept_id+" and status = 1) as notEntryFITCode,"
				+ "(select count(*) from hospital_todo_event where type =3  and community_dept_id = "+dept_id+" and status = 1) as notEntryFITResult,"
				+ "(select count(*) from hospital_todo_event where type =4  and community_dept_id = "+dept_id+" and status = 1) as notStoolDnaCode,"
				+ "(select count(*) from hospital_todo_event where type =5  and community_dept_id = "+dept_id+" and status = 1) as notReserve,"
				+ "(select count(*) from hospital_todo_event where type =6  and community_dept_id = "+dept_id+" and status = 1) as notFinishExamination,"
				+ "(select count(*) from hospital_todo_event where type =7  and community_dept_id = "+dept_id+" and status = 1) as notIssueNotification,"
				+ "(select count(*) from hospital_todo_event where type =17  and community_dept_id = "+dept_id+" and status = 1) as notIssueDna"*/
				+ " from dual";
		log.info("queryCommunityAndUserEvent"+sql);
		TodoVo vo = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<TodoVo>(TodoVo.class));
		return vo;
	}

	/**
	 * 删除告知书录入待办信息 删除发放告知书待办信息
	 */
	@Override
	public void delNotificationTodoEvent(String sid, Integer dataId) {
		String sql = "DELETE FROM hospital_todo_event where sid=? and `data_id`=? and `type`=7";
		jdbcTemplate.update(sql, new Object[]{sid,dataId});

		String sql1 = "DELETE FROM hospital_todo_event where sid=? and `data_id`=? and `type`=10";
		jdbcTemplate.update(sql1, new Object[]{sid,dataId});
	}

	/**
	 * 删除病理待办信息
	 */
	@Override
	public void delPathologyTodoEvent(String sid,Integer dataId) {
		String sql = "DELETE FROM hospital_todo_event where sid=? and `data_id`=? and `type`=9";
		jdbcTemplate.update(sql, new Object[]{sid,dataId});
	}

	/**
	 * 删除删除发放告知书待办信息
	 */
	@Override
	public void delNotificationFFTodoEvent(String sid, Integer dataId) {
		String sql = "DELETE FROM hospital_todo_event where sid=? and `data_id`=? and `type`=7";
		jdbcTemplate.update(sql, new Object[]{sid,dataId});

	}
}
