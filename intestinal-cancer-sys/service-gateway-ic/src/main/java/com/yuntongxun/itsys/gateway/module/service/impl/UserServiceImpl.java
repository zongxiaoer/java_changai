package com.yuntongxun.itsys.gateway.module.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import com.google.code.kaptcha.Constants;
import com.yuntongxun.itsys.gateway.common.constants.*;
import com.yuntongxun.itsys.gateway.common.util.EncryptUtil;
import com.yuntongxun.itsys.gateway.common.util.JSONUtil;
import com.yuntongxun.itsys.gateway.common.util.RequestUtil;
import com.yuntongxun.itsys.gateway.common.vo.LoginResult;
import com.yuntongxun.itsys.gateway.module.pojo.*;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yuntongxun.itsys.gateway.common.base.AbstractServiceImpl;
import com.yuntongxun.itsys.gateway.common.cache.redis.RedisManager;
import com.yuntongxun.itsys.gateway.common.util.StringUtil;
import com.yuntongxun.itsys.gateway.common.vo.ResultMsg;
import com.yuntongxun.itsys.gateway.module.dao.AuthDao;
import com.yuntongxun.itsys.gateway.module.dao.UserDao;
import com.yuntongxun.itsys.gateway.module.service.UserService;

@Service
public class UserServiceImpl extends AbstractServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthDao authDao;
    @Value("${ytx.gateway.token.expire}")
    private long expire;
    @Value("${ytx.gateway.token.timeout}")
    private long timeout;
    @Value("${zuul.routes.service-base.serviceId}")
    private String permissionsName;
    @Autowired
    private DefaultKaptcha captchaProducer;
    @Value("${login.authCode.codeTime}")
    private String codeTime;
    @Value("${spring.profiles.active}")
    private String environment;

    @PostConstruct
    public void init() {
        timeout = timeout * 1000;
    }

    @Autowired
    private RedisManager redis;

    private final Logger log = LogManager.getLogger(UserServiceImpl.class);

    public String getNumber() {
        String result = null;
        result = captchaProducer.createText();
        return result;
    }

    @Override
    public ResultMsg getAuthCode(String phoneNum, String loginName) {

        String[] parm = new String[2];
        String captchaNumber = getNumber();
        parm[0] = captchaNumber;
        parm[1] = "3分钟";
        ResultMsg resultMsg = new ResultMsg();
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
        restAPI.init("app.cloopen.com", "8883");
        // 初始化服务器地址和端口，生产环境配置成app.cloopen.com，端口是8883.
        restAPI.setAccount("aaf98f894738ec4801473cfe4e3000d8", "11a5a0c2c0d14e2d9ac3ad6e3ee20eaf");
        // 初始化主账号名称和主账号令牌，登陆云通讯网站后，可在控制首页中看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN。
        restAPI.setAppId("8aaf07085e6fff77015e73c6985100ca");
        HashMap<String, Object> result = restAPI.sendTemplateSMS(phoneNum, "205347", parm);
        log.info("@Service getAuthCode statusCode={}", result.get("statusCode"));
        if ("000000".equals(result.get("statusCode"))) {
            // 正常返回输出data包体信息（map）
            HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for (String key : keySet) {
                Object object = data.get(key);
                System.out.println(key + " = " + object);
            }
            // 用户名和手机号可以双登录
            redis.set(loginName, captchaNumber, Long.parseLong(codeTime));
            redis.set(phoneNum, captchaNumber, Long.parseLong(codeTime));
            resultMsg.setStatusCode(StatusConstant.SUCCESS_CODE);
            resultMsg.setStatusMsg(StatusConstant.SUCCESS_MSG);
            return resultMsg;
        } else {
            resultMsg.setStatusCode(StatusConstant.ERR_SEND_PCODE_CODE);
            resultMsg.setStatusMsg(StatusConstant.ERR_SEND_PCODE_MSG);
            // 异常返回输出错误码和错误信息
            log.info("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
            return resultMsg;
        }
    }

    @Override
    public User findByPhone(String phone) {

        User user = userDao.findByPhone(phone);

        return user;
    }

    @Override
    public List<User> findByName(String loginName) {
        List<User> employeesList = userDao.findByLoginName(loginName);
        return employeesList;
    }

    @Override
    public List<Employees> findByEmployeeName(String loginName) {
        List<Employees> employeesList = userDao.findByEmployeeName(loginName);

        return employeesList;
    }

    @Override
    public List<Employees> findByEmployeePhone(String phone) {

        List<Employees> employeesList = userDao.findByEmployeePhone(phone);

        return employeesList;
    }

    @Override
    public ResultMsg selectUserByLoginName(User user, HttpServletResponse response) throws UnsupportedEncodingException {
        log.info("@service selectUserByLoginName start");

        // 判断手机号是否为空
        if (StringUtil.isBlank(user.getLoginName())) {
            return new ResultMsg(StatusConstant.LOGIN_NO_PHONE_CODE, StatusConstant.LOGIN_NO_PHONE_MSG);
        }

        // 判断验证码是否为空
//		if (StringUtil.isBlank(user.getAuthCode())) {
//			return new ResultMsg(StatusConstant.LOGIN_NO_AUTHCODE_CODE, StatusConstant.LOGIN_NO_AUTHCODE_MSG);
//		}

//		 判断图片校验码是否失效
		String verifyCode = redis.get("itsys|" + user.getUuID());
		if (StringUtil.isEmpty(verifyCode)) {
			log.info("图片校验码verifyCode=null");
			return new ResultMsg(StatusConstant.LOGIN_VERIFY_EFFICACY_CODE, StatusConstant.LOGIN_VERIFY_EFFICACY_MSG);
		} else {
			// 判断图片校验码是否输入正确
			if (!user.getCapText().equals(verifyCode)) {
				return new ResultMsg(StatusConstant.LOGIN_VERIFY_CODE, StatusConstant.LOGIN_VERIFY_MSG);
			}
		}

        // 判断手机号用户名是否存在
//		String phone = user.getLoginName();
//		User finduser = userDao.findByPhone(phone);
//		if (finduser != null) {
//			log.info("手机号存在={}", phone);
//		} else {
//			List<User> userList = userDao.findByLoginName(phone);
//			if (userList != null && userList.size() > 0) {
//				log.info("用户名存在={}", phone);
//			} else {
//				return new ResultMsg(StatusConstant.LOGIN_NO_USER_CODE, StatusConstant.LOGIN_NO_USER_MSG);
//			}
//		}

        if (StringUtil.isBlank(user.getLoginName()) || StringUtil.isBlank(user.getPwd())) {
            return new ResultMsg(StatusConstant.LOGIN_NO_PARAM_CODE, StatusConstant.LOGIN_NO_PARAM_MSG);
        }
        String loginName = user.getLoginName();
        //根据loginName查询用户
        User dbUser = userDao.selectUserByLoginName(loginName);
        if (dbUser == null || StringUtil.isBlank(dbUser.getPwd())) {
            return new ResultMsg(StatusConstant.ACCOUNT_PASSWORD_CODE, StatusConstant.ACCOUNT_PASSWORD_MSG);
        }
        //判断密码是否正确
        if (!dbUser.getPwd().equals(EncryptUtil.md5(user.getPwd()))) {
            return new ResultMsg(StatusConstant.PASSWORD_ERROR_CODE, StatusConstant.PASSWORD_ERROR_MSG);
        }
        Map<String, Object> map = new HashMap<>();
        //判断是否是初始登录状态
        if( CookieConstant.FIRST_LOGIN_STATUS1.equals(dbUser.getFirstLogin())){
            map.put("firstLogin",true);
            //return new ResultMsg(StatusConstant.LOGIN_IS_FIRST_PASS_CODE, StatusConstant.LOGIN_IS_FIRST_PASS_MSG,map);
        }

        logger.debug("登陆成功，开始设置token");
        //登录成功设置token
        RequestUtil.setCookie(response, CookieConstant.COOKIE_NICKNAME, URLEncoder.encode(dbUser.getNickName(),"UTF-8"), expire);
        RequestUtil.setCookie(response, CookieConstant.COOKIE_XTYPHONE, URLEncoder.encode(dbUser.getXtyPhone(),"UTF-8"), expire);
        RequestUtil.setCookie(response, CookieConstant.COOKIE_XTYNAME, URLEncoder.encode(dbUser.getXtyName(),"UTF-8"), expire);
        UserToken userToken = new UserToken(StringUtil.getUUID(), System.currentTimeMillis() + timeout);
        authDao.setToken(CacheConstant.TOKEN_KEY_PREFIX + user.getLoginName(), userToken, expire);

        this.getHospitalInfo(user.getLoginName());//设置医院相关信息到redis中，时效60分钟
        map.put("token",userToken.getToken());
        return new ResultMsg(map);
    }

    /**
     * 设置相关信息到redis中
     *
     * @param loginName
     */
    @Override
    public DoctorInfo getHospitalInfo(String loginName) {
        //登录成功后根据loginName获取相关医院设置到redis中
        DepartMent departMent = userDao.findbyUserId(loginName);
        DoctorInfo doctorInfo = new DoctorInfo();
        if (departMent != null) {
            doctorInfo.setId(departMent.getUserId());//用户id
            doctorInfo.setLoginName(loginName);
            String hospitalType = departMent.getType();//医院类型
            String hospitalId = departMent.getId();//医院id
            if (hospitalType.equals("1")) {
                doctorInfo.setCommunityDeptId(departMent.getId());//社区
                doctorInfo.setAreaDeptId(departMent.getpId());//地区
                DepartMent departMentInfo = userDao.findByPid(departMent.getpId());
                doctorInfo.setNationDeptId(departMentInfo.getpId());//国家
                doctorInfo.setScreeningType(departMentInfo.getScreeningType());
            } else if (hospitalType.equals("2")) {
                doctorInfo.setAreaDeptId(hospitalId);//地区
                doctorInfo.setNationDeptId(departMent.getpId());//国家
                doctorInfo.setScreeningType(departMent.getScreeningType());
            } else if (hospitalType.equals("3")) {
                doctorInfo.setNationDeptId(hospitalId);

            }
            doctorInfo.setHospitalType(departMent.getType());
            redis.set(RedisConstant.HOSPITAL_KEY_INFO + loginName, JSONUtil.toJson(doctorInfo), expire);//医院相关信息
        }

        return doctorInfo;
    }

    @Override
    public ResultMsg updateUserByLoginName(User user) {
        log.info("@service updateUserByLoginName start");

        // 判断手机号是否为空
        if (StringUtil.isBlank(user.getLoginName())) {
            return new ResultMsg(StatusConstant.LOGIN_NO_PHONE_CODE, StatusConstant.LOGIN_NO_PHONE_MSG);
        }

        //判断密码是否为空
        if(StringUtils.isEmpty(user.getPwd())||StringUtils.isEmpty(user.getNewPwd())){
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
        User dbUser = userDao.selectUserByLoginName(loginName);


        //判断密码是否正确
        if (!dbUser.getPwd().equals(EncryptUtil.md5(user.getPwd()))) {
            return new ResultMsg(StatusConstant.LOGIN_NO_USER_OLDPASS_CODE, StatusConstant.LOGIN_NO_USER_OLDPASS_MSG);
        }

        if(dbUser.getPwd().equals(user.getNewPwd())){
            return new ResultMsg(StatusConstant.LOGIN_NO_USER_UPDATE_PASS_OLDPASS_CODE, StatusConstant.LOGIN_NO_USER_UPDATE_PASS_OLDPASS_MSG);
        }

        if (dbUser == null || StringUtil.isBlank(dbUser.getPwd())) {
            return new ResultMsg(StatusConstant.ACCOUNT_PASSWORD_CODE, StatusConstant.ACCOUNT_PASSWORD_MSG);
        }


        user.setNewPwd(EncryptUtil.md5(user.getNewPwd()));
        user.setFirstLogin(CookieConstant.FIRST_LOGIN_STATUS2);
        //修改密码和状态值
        Integer i=userDao.updateUserByLoginName(user);
        if(i<1){
            return new ResultMsg(StatusConstant.LOGIN_NO_USER_UPDATE_PASS_CODE, StatusConstant.LOGIN_NO_USER_UPDATE_PASS_MSG);
        }
        logger.debug("修改旧密码成功");


        return new ResultMsg();
    }

    @Override
    public List<Role> queryRoleByUserId(Integer userID) {
        return userDao.queryRoleByUserId(userID);
    }

    @Override
    @HystrixCommand(fallbackMethod = "returnNullString")
    public String selectPermissions(String loginName) {
        logger.info("selectPermissions is start，[loginName:{}]", loginName);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", CookieConstant.KEY_LOGING_NAME + "=" + loginName);
        String result = restTemplate.exchange("http://" + permissionsName + FilterConstant.PERMISSIONS_URL,
                HttpMethod.POST, new HttpEntity<String>(headers), String.class).getBody();
        logger.info("selectPermissions() is end,result：{}", result);
        return result;
    }

    /**
     * 熔断
     *
     * @param loginName
     * @return
     */
    public String returnNullString(String loginName) {
        logger.info("call returnNullString()");
        return "";
    }

    @Override
    protected Class<?> getClassName() {
        return UserServiceImpl.class;
    }

    @Override
    public boolean queryRoleByLoginName(String loginName) {
        boolean role = false;
        if (!StringUtil.isEmpty(loginName)) {
            role = this.userDao.queryRoleByLoginName(loginName);
        }
        return role;
    }

}
