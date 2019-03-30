package com.yuntongxun.itsys.base.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.yuntongxun.itsys.base.cache.RedisManager;
import com.yuntongxun.itsys.base.common.constans.CookieConstant;
import com.yuntongxun.itsys.base.common.constans.RedisConstant;
import com.yuntongxun.itsys.base.common.constans.StatusConstant;
import com.yuntongxun.itsys.base.dao.DepartMentDao;
import com.yuntongxun.itsys.base.dao.SidRuleDao;
import com.yuntongxun.itsys.base.po.*;
import com.yuntongxun.itsys.base.po.dto.sidrule.DepartmentSidRuleDto;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.keyvalue.core.KeyValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.EncryptUtil;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.dao.UserDao;
import com.yuntongxun.itsys.base.service.RoleService;
import com.yuntongxun.itsys.base.service.UserService;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userdao;
    @Autowired
    private RoleService roleserv;

    @Autowired
    private DepartMentDao departMentDao;


    @Autowired
    private RedisManager redis;

    private final Logger log = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public ListPageUtil queryUser(String body) throws ItSysException {
        JsonObject json = new JsonParser().parse(body).getAsJsonObject();
        String id = json.get("id") == null ? "" : json.get("id").getAsString();
        String nickName = json.get("nickName") == null ? "" : json.get("nickName").getAsString();
        String loginName = json.get("loginName") == null ? "" : json.get("loginName").getAsString();
        // 员工id
        String userId = json.get("employeeId") == null ? "" : json.get("employeeId").getAsString();
        int pageNo = json.get("pageNo") == null ? 0 : json.get("pageNo").getAsInt();
        int pageSize = json.get("pageSize") == null ? -1 : json.get("pageSize").getAsInt();
        ListPageUtil listPage = userdao.queryUser(id, nickName, loginName, userId, pageNo, pageSize);
        return listPage;
    }

    @Override
    public List getUserById(String id) throws ItSysException {
        // TODO Auto-generated method stub

        if (StringUtil.isEmpty(id)) {
            throw new ItSysException(
                    Constans.ERR_SYSUSER_ID_CODE, Constans.ERR_SYSUSER_ID_MSG);
        }
        List list = userdao.getUserById(id);
        return list;
    }

    @Override
    @Transactional
    public void addUser(String body) throws ItSysException, NoSuchAlgorithmException {
        User user = (User) JSONUtils.JsonToObject(body, User.class);
        if (StringUtil.isEmpty(user.getLoginName())) {
            throw new ItSysException(
                    Constans.ERR_SYSUSER_LOGINNAME_CODE, Constans.ERR_SYSUSER_LOGINNAME_MSG);
        }
        if (StringUtil.isEmpty(user.getPwd())) {
            user.setPwd(Constans.SYS_PWD_DEFAULT);
        }
        String encodePwd = EncryptUtil.md5(user.getPwd());
        user.setPwd(encodePwd);
        userdao.addUser(user);
    }

    @Override
    @Transactional
    public void updateUser(String body) throws ItSysException {
        User user = (User) JSONUtils.JsonToObject(body, User.class);
        String id = user.getId() + "";
        if (StringUtil.isEmpty(id)) {
            throw new ItSysException(
                    Constans.ERR_SYSUSER_ID_CODE, Constans.ERR_SYSUSER_ID_MSG);
        }
        if (StringUtil.isEmpty(user.getLoginName())) {
            throw new ItSysException(
                    Constans.ERR_SYSUSER_LOGINNAME_CODE, Constans.ERR_SYSUSER_LOGINNAME_MSG);

        }

        List list = userdao.getUserById(id);
        if (list.size() == 0) {
            throw new ItSysException(
                    Constans.ERR_SYSUSER_NULL_CODE, Constans.ERR_SYSUSER_NULL_MSG);
        }
        String a = "";
        //更新用户
        userdao.updateUser(user);
    }

    @Override
    @Transactional
    public void delUser(String id) throws ItSysException {
        // TODO Auto-generated method stub
        if (StringUtil.isEmpty(id)) {
            throw new ItSysException(
                    Constans.ERR_SYSUSER_ID_CODE, Constans.ERR_SYSUSER_ID_MSG);

        }
        //删除用户
        userdao.delUser(id);
    }

    @Override
    @Transactional
    public void makeUser(String body) throws ItSysException, NoSuchAlgorithmException {
        // TODO Auto-generated method stub
        JsonObject json = new JsonParser().parse(body).getAsJsonObject();
        String employeeId = json.get("employeeId").isJsonNull() ? "" : json.get("employeeId").getAsString();
        String companyMail = json.get("companyMail").isJsonNull() ? "" : json.get("companyMail").getAsString();
        User user = new User();
        if (StringUtil.isEmpty(companyMail)) {
            throw new ItSysException(
                    Constans.ERR_LOGINNAME_MAIL_NULL_CODE, Constans.ERR_LOGINNAME_MAIL_NULL_MSG);
        }
        user.setEmployeeId(employeeId);
        user.setLoginName(companyMail);
        user.setNickName(companyMail);
        String pwd = Constans.SYS_PWD_DEFAULT;
        String encodePwd = EncryptUtil.md5(pwd);
        user.setPwd(encodePwd);
        ListPageUtil listPage = userdao.queryUser(null, null, companyMail, null, 0, -1);
        if (listPage.getResultList() != null && listPage.getResultList().size() > 0) {
            throw new ItSysException(
                    Constans.ERR_LOGINNAME_HAVE_CODE, Constans.ERR_LOGINNAME_HAVE_MSG);
        }
        userdao.addUserByPosition(user);
    }

    @Override
    public List getUserPwd(String LoginName) throws ItSysException {
        List list = userdao.getUserPwd(LoginName);
        return list;
        // TODO Auto-generated method stub

    }


    @Override
    public List getUserRoleById(String id) throws ItSysException {
        // TODO Auto-generated method stub
        if (StringUtil.isEmpty(id)) {
            throw new ItSysException(Constans.ERR_USER_ID_CODE, Constans.ERR_USER_ID_MSG);
        }
        return userdao.getUserRoleById(id);
    }

    @Override
    @Transactional
    public void addUserRole(String userid, String[] roleids) throws ItSysException {
        // TODO Auto-generated method stub
        if (!StringUtil.isEmpty(userid)) {
            roleserv.delUserRoleRByUserId(userid);// 根据用户id先删除用户角色关系
            userdao.addUserRole(userid, roleids);// 新增用户角色
        } else {
            throw new ItSysException(Constans.ERR_USER_ID_CODE, Constans.ERR_USER_ID_MSG);
        }
    }

    @Override
    @Transactional
    public void changepwd(String loginName, String pwd, String oldPwd) throws ItSysException {
        // TODO Auto-generated method stub
        log.info("@ChangPwd 参数：pwd={},oldpwd={}", pwd, oldPwd);
        if (StringUtil.isEmpty(pwd)) {
            throw new ItSysException(Constans.ERR_LOGINNAME_PWD_NULL_CODE,
                    Constans.ERR_LOGINNAME_PWD_NULL_MSG);
        }
        try {
            if (!StringUtil.isEmpty(loginName)) {
                List list = getUserPwd(loginName);
                log.info("@ChangPwd 根据loginName查询用户获取到的数据长度为:{}", list.size());
                if (list.size() > 0) {
                    JsonObject json = (JsonObject) new JsonParser().parse(JSONUtils.toJson(list.get(0)));
                    String oldpwd2 = json.get("pwd").getAsString();
                    log.info("@ChangPwd 根据loginName查询到的用户为:{},密码为：{}", loginName, oldpwd2);
                    oldPwd = EncryptUtil.md5(oldPwd);
                    if (!StringUtil.isEmpty(oldpwd2) && oldpwd2.equals(oldPwd)) {
                        log.info("匹配密码成功，修改密码。。。。");
                        userdao.changepwd(loginName, EncryptUtil.md5(pwd), oldpwd2);
                    } else {
                        throw new ItSysException(Constans.ERR_LOGINNAME_PWD_EXF_CODE,
                                Constans.ERR_LOGINNAME_PWD_EXF_MSG);
                    }
                }
            } else {
                throw new ItSysException(
                        Constans.ERR_USER_LOGINNAME_CODE, Constans.ERR_USER_LOGINNAME_MSG);
            }
        } catch (Exception ex) {
            log.error("error  修改密码失败");
            ex.printStackTrace();
        }
    }

    @Override
    public User findByPhone(String phone) {
        User user = null;
        List<User> userList = userdao.findByPhone(phone);
        if (userList != null && userList.size() > 0) {
            user = userList.get(0);
        }
        return user;
    }

    /**
     * 设置相关信息到redis中
     *
     * @param loginName
     */
    @Override
    public DoctorInfo getHospitalInfo(String loginName) {
        //登录成功后根据loginName获取相关医院设置到redis中
        DepartMent departMent = userdao.findbyUserId(loginName);
        DoctorInfo doctorInfo = new DoctorInfo();
        doctorInfo.setId(departMent.getUserId());//用户id
        doctorInfo.setLoginName(loginName);
        String hospitalType = departMent.getType();//医院类型
        String hospitalId = departMent.getId();//医院id
        if (hospitalType.equals("1")) {
            doctorInfo.setCommunityDeptId(Integer.valueOf(hospitalId));//社区
            doctorInfo.setAreaDeptId(Integer.valueOf(departMent.getpId()));//地区
            DepartMent departMentInfo = userdao.findByPid(departMent.getpId());
            doctorInfo.setNationDeptId(Integer.valueOf(departMentInfo.getpId()));//国家
        } else if (hospitalType.equals("2")) {
            doctorInfo.setAreaDeptId(Integer.valueOf(hospitalId));//地区
            doctorInfo.setNationDeptId(Integer.valueOf(departMent.getpId()));//国家
        } else if (hospitalType.equals("3")) {
            doctorInfo.setNationDeptId(Integer.valueOf(hospitalId));

        }
        doctorInfo.setScreeningType(departMent.getScreeningType());
        doctorInfo.setHospitalType(Integer.valueOf(departMent.getType()));
        redis.set(RedisConstant.HOSPITAL_KEY_INFO + loginName, JSONUtils.toJson(doctorInfo), 3600);//医院相关信息
        return doctorInfo;
    }

    @Override
    public ItsysDepartment getCommunityIdAndAreaIdByLoginName(String loginName) {
        ItsysDepartment itsysDepartment = userdao.getCommunityIdAndAreaIdByLoginName(loginName);
        return itsysDepartment;
    }

    @Override
    public int getPersonNumber(ItsysDepartment itsysDepartment) {
//        areaId
//        select sid from hospital_intestine_review where sid like "%TC5%";

        int maxOrder = itsysDepartment.getOrder() * 1000;
        int minOrder = maxOrder - 999;
        int maxPerson = 800;

        String sid = userdao.finAllSidBySiteId(itsysDepartment.getId(), itsysDepartment.getPid());
        if (StringUtils.isEmpty(sid)) {
            return minOrder;
        }

        String[] total = sid.split("TC");
        String codes = total[1];
        int code = Integer.parseInt(codes.substring(1, codes.length()));
        if (code >= maxOrder) {
            return -1;
        }
        if (code >= 1000) {
            int num = Integer.parseInt(String.valueOf(code).substring(1, String.valueOf(code).length()));
            if (num >= maxPerson) {
                return -1;
            }
        }else if (code<1000 && code >= maxPerson){
            return -1;
        }
        return code + 1;
    }

    @Override
    public int getPersonNumberByRule(DepartmentSidRuleDto departmentSidRuleDto) {
        int code=0;
        //校验来自规则A还是规则B
        if(Constans.ADD_SID_RULE_0.equals(departmentSidRuleDto.getRuleType())){//A直接
            //根据地区id获取最末尾sid+1
            String sid = userdao.findEndSidByAreaIdOrComunityId(departmentSidRuleDto.getPid(),null);
            if(!StringUtils.isEmpty(sid)){
                //校验人数是否小于max_person
                String[] total = sid.split("TC");
                String codes = total[1];
                code=Integer.parseInt(codes.substring(1, codes.length()));
/*                if(code>=departmentSidRuleDto.getMaxPerson()){
                    return -1;
                }*/
            }

        }else if(Constans.ADD_SID_RULE_1.equals(departmentSidRuleDto.getRuleType())){  //B
            //根据地区获取最末尾sid+1
            String sid = userdao.findEndSidByAreaIdOrComunityId(departmentSidRuleDto.getPid(),departmentSidRuleDto.getDepartmentId());
            String communityScope = departmentSidRuleDto.getCommunityScope();
            // todo 非空
            String[] split = communityScope.split(":");
            int minPerson=Integer.parseInt(split[0]);
            int maxPerson=Integer.parseInt(split[1]);
            if(!StringUtils.isEmpty(sid)){
                String[] total = sid.split("TC");
                String codes = total[1];
                code=Integer.parseInt(codes.substring(1, codes.length()));
                //校验人数是否小于max_person
                if((code-minPerson+1)>=departmentSidRuleDto.getMaxPerson()){
                    return -1;
                }
                //判断是否有社区范围  看是否在波段内
                if(Constans.COMMUNITY_SCOPE_STATUS_YES.equals(departmentSidRuleDto.getCommunityScopeStatus())){
                    if(code<minPerson||code>=maxPerson){
                        return -1;
                    }
                }
            }else if(StringUtils.isEmpty(sid)){
                code=minPerson-1;
            }
        }
        return code+1;
    }

    @Override
    public ResultMsg updateUserByLoginName(User user) {
        log.info("@service updateUserByLoginName start");

        // 判断手机号是否为空
        if (StringUtil.isBlank(user.getLoginName())) {
            return new ResultMsg(StatusConstant.LOGIN_NO_PHONE_CODE, StatusConstant.LOGIN_NO_PHONE_MSG);
        }

        //判断密码是否为空
        if(org.apache.commons.lang.StringUtils.isEmpty(user.getPwd())|| org.apache.commons.lang.StringUtils.isEmpty(user.getNewPwd())){
            return new ResultMsg(StatusConstant.LOGIN_NO_USER_OLDPASS_CODE, StatusConstant.LOGIN_NO_USER_OLDPASS_MSG);
        }

        if(user.getNewPwd().length()<6||user.getNewPwd().length()>16){
            return new ResultMsg(StatusConstant.LOGIN_IS_PASS_LENGTH_CODE, StatusConstant.LOGIN_IS_PASS_LENGTH_MSG);
        }

//		 判断图片校验码是否失效
        String verifyCode = redis.get(StatusConstant.UPDATE_IMAGE_KEY + user.getUuID());
        if (StringUtil.isEmpty(verifyCode)) {
            log.info("图片校验码verifyCode=null");
            return new ResultMsg(StatusConstant.LOGIN_VERIFY_EFFICACY_CODE, StatusConstant.LOGIN_VERIFY_EFFICACY_MSG);
        } else {
            // 判断图片校验码是否输入正确
            if (!user.getCapText().equals(verifyCode)) {
                return new ResultMsg(StatusConstant.LOGIN_VERIFY_CODE, StatusConstant.LOGIN_VERIFY_MSG);
            }
        }


        if (StringUtil.isBlank(user.getLoginName()) || StringUtil.isBlank(user.getPwd())) {
            return new ResultMsg(StatusConstant.LOGIN_NO_PARAM_CODE, StatusConstant.LOGIN_NO_PARAM_MSG);
        }

        String loginName = user.getLoginName();
        //根据loginName查询用户
        User dbUser = userdao.selectUserByLoginName(loginName);


        //判断密码是否正确
        if (!dbUser.getPwd().equals(EncryptUtil.md5(user.getPwd()))) {
            return new ResultMsg(StatusConstant.LOGIN_NO_USER_OLDPASS_CODE, StatusConstant.LOGIN_NO_USER_OLDPASS_MSG);
        }

        if(dbUser.getPwd().equals(EncryptUtil.md5(user.getNewPwd()))){
            return new ResultMsg(StatusConstant.LOGIN_NO_USER_UPDATE_PASS_OLDPASS_CODE, StatusConstant.LOGIN_NO_USER_UPDATE_PASS_OLDPASS_MSG);
        }

        if (dbUser == null || StringUtil.isBlank(dbUser.getPwd())) {
            return new ResultMsg(StatusConstant.ACCOUNT_PASSWORD_CODE, StatusConstant.ACCOUNT_PASSWORD_MSG);
        }


        user.setNewPwd(EncryptUtil.md5(user.getNewPwd()));
        user.setFirstLogin(CookieConstant.FIRST_LOGIN_STATUS2);
        //修改密码和状态值
        Integer i=userdao.updateUserByLoginName(user);
        if(i<1){
            return new ResultMsg(StatusConstant.LOGIN_NO_USER_UPDATE_PASS_CODE, StatusConstant.LOGIN_NO_USER_UPDATE_PASS_MSG);
        }

        return new ResultMsg();
    }

    @Override
    public List<Role> queryRoleByUserId(Integer id) {
        return departMentDao.queryRoleByUserId(id);
    }

    @Override
	public ItsysDepartment getAllDepts(String loginName) {
		// TODO Auto-generated method stub
		ItsysDepartment itsysDepartment = userdao.getAllDepts(loginName);
		return itsysDepartment;
	}
}
