package com.yuntongxun.itsys.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.yuntongxun.itsys.base.dao.UserDao;
import com.yuntongxun.itsys.base.po.ReviewResult;
import com.yuntongxun.itsys.base.po.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yuntongxun.itsys.base.cache.RedisManager;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.constans.RedisConstant;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.DictionaryUtil;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.ResultUtil;
import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.dao.DepartMentDao;
import com.yuntongxun.itsys.base.po.DepartMent;
import com.yuntongxun.itsys.base.po.DeptMember;
import com.yuntongxun.itsys.base.service.DepartMentService;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
import com.yuntongxun.itsys.base.vo.Result;

@Service
public class DepartMentServiceImpl implements DepartMentService {

    private final Logger log = LogManager.getLogger(DepartMentServiceImpl.class);

    @Autowired
    private DepartMentDao departdao;

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private UserDao userdao;

    @Override
    public ListPageUtil queryDepartMent(JsonObject json) throws ItSysException {
        String id = json.get("id") == null ? "" : json.get("id").getAsString();
        String name = json.get("name") == null ? "" : json.get("name").getAsString();
        int pageNo = json.get("pageNo") == null ? 0 : json.get("pageNo").getAsInt();
        int pageSize = json.get("pageSize") == null ? -1 : json.get("pageSize").getAsInt();
        boolean isTranslate = json.get("isTranslate") == null ? false : json.get("isTranslate").getAsBoolean();//是否翻译
        ListPageUtil listPage = departdao.queryDepartMent(id, name, true, isTranslate, pageNo, pageSize);
        return listPage;
    }

    @Override
    public List getDepartMentById(String id) throws ItSysException {
        List list = new ArrayList();
        if (StringUtil.isEmpty(id))
            throw new ItSysException(Constans.ERR_DEPART_ID_CODE, Constans.ERR_DEPART_ID_MSG);
        list = departdao.getDepartMentById(id);
        return list;
    }

    @Override
    @Transactional
    public void insert(String body) throws ItSysException {
        // TODO Auto-generated method stub
        log.info("@Service insert Start  ");
        DepartMent depart = (DepartMent) JSONUtils.JsonToObject(body, DepartMent.class);
        // if (StringUtil.isEmpty(depart.getName())) {
        // throw new ItSysException(
        // BuildError.buildException(Constans.ERR_DEPART_NAME_CODE,
        // Constans.ERR_DEPART_NAME_MSG));
        // }
        // if (StringUtil.isEmpty(depart.getLevel())) {
        // throw new ItSysException(
        // BuildError.buildException(Constans.ERR_DEPART_LEVEL_CODE,
        // Constans.ERR_DEPART_LEVEL_MSG));
        // }
        // if (StringUtil.isEmpty(depart.getpId())) {
        // throw new ItSysException(
        // BuildError.buildException(Constans.ERR_DEPART_PID_CODE,
        // Constans.ERR_DEPART_PID_MSG));
        // }
        // if (StringUtil.isEmpty(depart.getDesc())) {
        // throw new ItSysException(
        // BuildError.buildException(Constans.ERR_DEPART_DESC_CODE,
        // Constans.ERR_DEPART_DESC_MSG));
        // }
        // if (StringUtil.isEmpty(depart.getSort())) {
        // throw new ItSysException(
        // BuildError.buildException(Constans.ERR_DEPART_SORT_CODE,
        // Constans.ERR_DEPART_SORT_MSG));
        // }
        // if (StringUtil.isEmpty(depart.getType())) {
        // throw new ItSysException(
        // BuildError.buildException(Constans.ERR_DEPART_TYPE_CODE,
        // Constans.ERR_DEPART_TYPE_MSG));
        // }
        if (StringUtil.notEmpty(depart.getType())) {
            if (depart.getType().equals("2")) {
                if (null != depart.getScreeningType()) {
                    departdao.insert(depart);
                } else {
                    throw new ItSysException(GlobalErrorCode.ERR_SCREENINGTYPE_NULL_CODE, GlobalErrorCode.ERR_SCREENINGTYPE_NULL_MSG);
                }
            } else {
                departdao.insert(depart);
            }
        } else {
            throw new ItSysException(GlobalErrorCode.ERR_HOSPITAL_NULL_CODE, GlobalErrorCode.ERR_HOSPITAL_NULL_MSG);
        }

        log.info("@Service insert End  ");

    }

    @Transactional
    @Override
    public void delDepartMentById(String id) throws ItSysException {
        // TODO Auto-generated method stub
        if (StringUtil.isEmpty(id)) {
            throw new ItSysException(Constans.ERR_DEPART_ID_CODE, Constans.ERR_DEPART_ID_MSG);
        }
        departdao.delDepartMentMemberByDept(id);// 删除部门成员关系--部门id
        departdao.delDepartMentById(id);
    }

    @Override
    @Transactional
    public void updateDepartMent(String body) throws ItSysException {
        // TODO Auto-generated method stub
        DepartMent depart = (DepartMent) JSONUtils.JsonToObject(body, DepartMent.class);
        // if (StringUtil.isEmpty(depart.getId())) {
        // throw new ItSysException(
        // BuildError.buildException(Constans.ERR_DEPART_ID_CODE,
        // Constans.ERR_DEPART_ID_MSG));
        // }
        // if (StringUtil.isEmpty(depart.getName())) {
        // throw new ItSysException(
        // BuildError.buildException(Constans.ERR_DEPART_NAME_CODE,
        // Constans.ERR_DEPART_NAME_MSG));
        // }
        // if (StringUtil.isEmpty(depart.getLevel())) {
        // throw new ItSysException(
        // BuildError.buildException(Constans.ERR_DEPART_LEVEL_CODE,
        // Constans.ERR_DEPART_LEVEL_MSG));
        // }
        // if (StringUtil.isEmpty(depart.getpId())) {
        // throw new ItSysException(
        // BuildError.buildException(Constans.ERR_DEPART_PID_CODE,
        // Constans.ERR_DEPART_PID_MSG));
        // }
        // if (StringUtil.isEmpty(depart.getDesc())) {
        // throw new ItSysException(
        // BuildError.buildException(Constans.ERR_DEPART_DESC_CODE,
        // Constans.ERR_DEPART_DESC_MSG));
        // }
        // if (StringUtil.isEmpty(depart.getSort())) {
        // throw new ItSysException(
        // BuildError.buildException(Constans.ERR_DEPART_SORT_CODE,
        // Constans.ERR_DEPART_SORT_MSG));
        // }
        // if (StringUtil.isEmpty(depart.getType())) {
        // throw new ItSysException(
        // BuildError.buildException(Constans.ERR_DEPART_TYPE_CODE,
        // Constans.ERR_DEPART_TYPE_MSG));
        // }
        List list = departdao.getDepartMentById(depart.getId());
        if (list.size() == 0) {
            throw new ItSysException(Constans.ERR_DEPART_NULL_CODE, Constans.ERR_DEPART_NULL_MSG);
        }
        DepartMent dm = (DepartMent) JSONUtils.JsonToObject(JSONUtils.toJson(list.get(0)), DepartMent.class);
        if (dm.getpId().equals("0")) {
            depart.setpId("0");
        }
        departdao.updateDepartMent(depart);
    }

    @Override
    @Transactional
    public void saveDepartMentMemBer(String body) throws ItSysException {
        // TODO Auto-generated method stub
        // DeptMember dm = (DeptMember) JSONUtils.JsonToObject(body,
        // DeptMember.class);
        JsonArray json = new JsonParser().parse(body).getAsJsonArray();
        List list = new ArrayList();
        List list2 = new ArrayList();

        for (int i = 0; i < json.size(); i++) {
            DeptMember dms = (DeptMember) JSONUtils.JsonToObject(JSONUtils.toJson(json.get(i)), DeptMember.class);
            list.add(dms);
            list2.add(dms.getUserId());
        }

        departdao.delDepartMentMemberByUser(list2);
        departdao.saveDepartMentMemberOnList(list);
        ;// 保存部门成员
    }

    @Override
    @Transactional
    public void delDepartMentMemberByUser(String body) throws ItSysException {
        // TODO Auto-generated method stub
        JsonArray json = new JsonParser().parse(body).getAsJsonArray();
        List list = new ArrayList();
        for (int i = 0; i < json.size(); i++) {
            list.add(json.get(i));
        }
        departdao.delDepartMentMemberByUser(list);
    }

    @Override
    @Transactional
    public void delDepartMentMemberByDept(String deptid) throws ItSysException {
        // TODO Auto-generated method stub
        if (StringUtil.isEmpty(deptid)) {
            throw new ItSysException(Constans.ERR_DEPART_DEPTID_CODE, Constans.ERR_DEPART_DEPTID_MSG);
        }
        departdao.delDepartMentById(deptid);
    }

    @Override
    public Object queryDepartOnTree(JsonObject json) throws ItSysException {
        // TODO Auto-generated method stub
        String id = "";
        id = json.get("id") == null ? "" : json.get("id").getAsString();
        ListPageUtil listPage = departdao.queryDepartMent(null, null, false, true, 0, -1);
        DepartMent department = new DepartMent();
        List<DepartMent> departList = new ArrayList();
        List<DepartMent> root = new ArrayList();
        List queryDepartList = listPage.getResultList();
        if (queryDepartList != null && queryDepartList.size() > 0) {

            for (int i = 0; i < queryDepartList.size(); i++) {
                DepartMent depart = null;
                String departJson = JSONUtils.toJson(queryDepartList.get(i));
                depart = (DepartMent) JSONUtils.JsonToObject(departJson, DepartMent.class);
                departList.add(depart);
            }
            // 遍历部门获取部门树结
            for (DepartMent tree : departList) {
                String departPid = StringUtil.isEmpty(id) ? "0" : id;
                if (departPid.equals("0")) {
                    if (tree.getpId().equals(departPid)) {
                        root.add(tree);
                    }
                } else {
                    if (tree.getId().equals(departPid)) {
                        root.add(tree);
                    }
                }
                for (DepartMent t : departList) {
                    String pid = t.getpId() + "";
                    if (pid.equals(tree.getId())) {
                        if (tree.getChild() == null) {
                            List<DepartMent> myChildrens = new ArrayList<DepartMent>();
                            myChildrens.add(t);
                            tree.setChild(myChildrens);
                        } else {
                            tree.getChild().add(t);
                        }
                    }
                }
            }
            for (DepartMent tree : root) {
                department = tree;
            }

        }
        return department;
    }

    @Override
    public List getDepartMentMember(String departId) throws ItSysException {
        // TODO Auto-generated method stub
        if (StringUtil.isEmpty(departId)) {
            throw new ItSysException(Constans.ERR_DEPART_ID_CODE, Constans.ERR_DEPART_ID_MSG);
        }
        List list = departdao.getDepartMentMember(departId);

        List list2 = DictionaryUtil.translateList(list, "position", "POSITION");
        // List list2 = ItSysUtil.getZDList(list, Constans.CACHE_CUSTOM_CACHE,
        // "position", "EDUCATION");
        return list2;
    }

    @Override
    public List getManagerByDepartId(String departId) throws ItSysException {
        // TODO Auto-generated method stub
        List manager = (List) this.getRoleByDepartId(departId, "2");
        if (manager.size() == 0) {
            throw new ItSysException(Constans.ERR_DEPART_NOHAVE_CODE, Constans.ERR_DEPART_NOHAVE_MSG);
        }
        return manager;
    }

    /**
     * @param departId
     * @return
     * @throws ItSysException
     */
    public Object getRoleByDepartId(String departId, String type) throws ItSysException {
        List manager = new ArrayList();
        List list = departdao.getDepartMentMember(departId);
        if (list.size() == 0) {
            return manager;
        }
        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            JsonObject json = new JsonParser().parse(JSONUtils.toJson(obj)).getAsJsonObject();
            String level = json.get("level").getAsString();
            if (level.equals(type)) {
                manager.add(list.get(i));
                break;
            }
        }
        if (manager.size() == 0) {
            String pid = departdao.getPidByDepartId(departId);
            manager = (List) getRoleByDepartId(pid, type);

        }
        return manager;
    }

    @Override
    public List getCeoByDepartId(String departId) throws ItSysException {
        List manager = (List) this.getRoleByDepartId(departId, "1");
        if (manager.size() == 0) {
            throw new ItSysException(Constans.ERR_DEPART_NOHAVE_CODE, Constans.ERR_DEPART_NOHAVE_MSG);
        }
        return manager;
    }

    @Override
    public List getInterviewerByDepartId(String departId) throws ItSysException {
        List manager = (List) this.getOtherRole(departId, "7");
        if (manager.size() == 0) {
            throw new ItSysException(Constans.ERR_DEPART_NOHAVE_CODE, Constans.ERR_DEPART_NOHAVE_MSG);
        }
        return manager;
    }

    @Override
    public List getInductionByDepartId(String departId) throws ItSysException {
        List manager = (List) this.getOtherRole(departId, "6");
        if (manager.size() == 0) {
            throw new ItSysException(Constans.ERR_DEPART_NOHAVE_CODE, Constans.ERR_DEPART_NOHAVE_MSG);
        }
        return manager;
    }

    /**
     * @return
     * @throws ItSysException
     */
    public Object getOtherRole(String departId, String type) throws ItSysException {

        List manager = new ArrayList();
        List list = departdao.getDepartMentMember(departId);
        if (list.size() == 0) {
            return manager;
        }
        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            JsonObject json = new JsonParser().parse(JSONUtils.toJson(obj)).getAsJsonObject();
            String dccs = "";
            if (type.equals("6")) {
                dccs = json.get("induction").getAsString();// 入职引导
            } else {
                dccs = json.get("interviewer").getAsString();// 面试
            }
            if (dccs.equals("1")) {// 满足该用户为入职引导人或者是面试
                manager.add(list.get(i));
            }
        }
        if (manager.size() == 0) {
            String pid = departdao.getPidByDepartId(departId);
            manager = (List) getRoleByDepartId(pid, type);

        }
        return manager;

    }

    @Override
    public Object getOtherDepartByPid(String idx) throws ItSysException {

        // TODO Auto-generated method stub
        ListPageUtil listPage = departdao.queryDepartMent(null, null, false, true, 0, -1);
        DepartMent department = new DepartMent();
        List<DepartMent> departList = new ArrayList();
        List<DepartMent> root = new ArrayList();
        List queryDepartList = listPage.getResultList();
        for (int i = 0; i < queryDepartList.size(); i++) {
            DepartMent depart = null;
            String departJson = JSONUtils.toJson(queryDepartList.get(i));
            depart = (DepartMent) JSONUtils.JsonToObject(departJson, DepartMent.class);
            departList.add(depart);
        }
        // 遍历部门获取部门
        for (DepartMent tree : departList) {
            String departPid = "0";
            if (tree.getpId().equals(departPid)) {
                root.add(tree);
            }
            for (DepartMent t : departList) {
                if (t.getId().equals(idx)) {
                    System.out
                            .println("---------------------------------------------------------符合条件，不加入到树形结构中 ");
                } else {
                    String pid = t.getpId() + "";
                    if (pid.equals(tree.getId())) {
                        if (tree.getChild() == null) {
                            List<DepartMent> myChildrens = new ArrayList<DepartMent>();
                            myChildrens.add(t);
                            tree.setChild(myChildrens);
                        } else {
                            tree.getChild().add(t);
                        }
                    }

                }
            }
        }
        for (DepartMent tree : root) {
            if (tree.getId().equals(idx)) {
                department = tree;
                break;
            }
        }
        return department;

    }

    @Override
    @Transactional
    public void updateDepartMember(String body) throws ItSysException {
        // TODO Auto-generated method stub
        JsonObject json = new JsonParser().parse(body).getAsJsonObject();
        String memberId = json.get("memberId").getAsString();
        String deptId = json.get("deptId").getAsString();
        String employeeId = json.get("employeeId").getAsString();
        String position = json.get("position").getAsString();
        departdao.updateDepartMember(memberId, deptId, employeeId, position);
    }

    @Override
    public Result queryNotExistsDepartEmployee(String body) {
        JsonObject json = new JsonParser().parse(body).getAsJsonObject();
        int pageNo = json.get("pageNo") == null ? 0 : json.get("pageNo").getAsInt();
        int pageSize = json.get("pageSize") == null ? -1 : json.get("pageSize").getAsInt();
        ListPageUtil employeeList = departdao.queryNotExistsDepartEmployee(pageNo, pageSize);

        return ResultUtil.success(employeeList.getResultList(), "success");
    }


    @Override
    public Result findHospitalByPid(String loginName) {
        Result result = new Result();
        String hospitalInfo = redisManager.get(RedisConstant.HOSPITAL_KEY_INFO + loginName);

        if (StringUtil.notEmpty(hospitalInfo)) {
            DoctorInfo doctorInfo = JSONUtils.toBean(hospitalInfo, DoctorInfo.class);
            Integer hospitalType = doctorInfo.getHospitalType();

            if (Objects.equals(hospitalType, Constans.DEP_HOSPITAL_TYPE2)) {
                Integer currentId = doctorInfo.getAreaDeptId();
                List<DepartMent> departMentList = departdao.findHospitalByPid(currentId);
                if (departMentList != null && departMentList.size() > 0) {

                    result = ResultUtil.success(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, departMentList);
                } else {
                    result = ResultUtil.success(GlobalErrorCode.ERR_HOSPITAL_SUBORDINATE_NULL_CODE, GlobalErrorCode.ERR_HOSPITAL_SUBORDINATE_NULL_MSG, departMentList);
                }
            }
        }

        return result;
    }
    /**
     * 新增查询树形接口数据
     * @param pid
     * @return
     */
	@Override
	public Object getAllDepartByPid(String pid,Integer level,Integer userID) {
		List<DepartMent> tree = new ArrayList<DepartMent>(); 
		if(level != null && pid != null){
			//第一种国家账号登陆
			if(level == 1){
				//根据pid查询所对应的地区
				List<DepartMent> list1 = departdao.findAllNodesByPid(pid);   
				for (DepartMent departMent : list1) {
					//根据id查询所对应的社区
					List<DepartMent> list2 = departdao.findAllNodesByPid(departMent.getId());    
					List<DepartMent> myChildrens1 = new ArrayList<DepartMent>();
					for (DepartMent departMent2 : list2) {
						myChildrens1.add(departMent2);
						departMent.setChild(myChildrens1);	
						//根据id查询所对应的创建者
						List<DepartMent> list3 = departdao.findNickNameById(departMent2.getId(),null);
						List<DepartMent> myChildrens2 = new ArrayList<DepartMent>();
						for (DepartMent departMent3 : list3) {
                            departMent3.setId(departMent3.getUid().toString());
							departMent3.setName(departMent3.getNickName());
							myChildrens2.add(departMent3);
							departMent2.setChild(myChildrens2);
						}
					}
					tree.add(departMent);
				}
			}else if(level == 11){                   
				List<DepartMent> list1 = departdao.findAllNodesByPid(pid);
				for (DepartMent departMent2 : list1) {
					List<DepartMent> myChildrens1 = new ArrayList<DepartMent>();
					myChildrens1.add(departMent2);
					List<DepartMent> list3 = departdao.findNickNameById(departMent2.getId(),null);
					List<DepartMent> myChildrens2 = new ArrayList<DepartMent>();
					for (DepartMent departMent3 : list3) {
					    departMent3.setId(departMent3.getUid().toString());
                        departMent3.setName(departMent3.getNickName());
						myChildrens2.add(departMent3);
						departMent2.setChild(myChildrens2);
					}
					tree.add(departMent2);
				}
			}else if(level == 111){
			    //宗曈  修改
                //根据pid查询关联的ID
                List<Role> roles= departdao.queryRoleByUserId(userID);
                Integer userid=null;
                if(roles!=null&&roles.size()>0){
                    userid=userID;
                }
				List<DepartMent> list3 = departdao.findNickNameById(pid,userid);
				for (DepartMent departMent : list3) {
				    departMent.setId(departMent.getUid().toString());
				    departMent.setName(departMent.getNickName());
					tree.add(departMent);
				}
			}
		}
		return tree;
	}

    /**
     * 根据当前登录用户取所在医院
     * @param loginName
     * @return
     */
    public Object findHospitalByLoginName(String loginName){
        ReviewResult results = new ReviewResult();
	    DepartMent department = userdao.findbyUserId(loginName);
        DepartMent dept = new DepartMent();
        if(department != null){
            dept.setId(department.getId());
            dept.setName(department.getName());
        }
	    return dept;
    }


}
