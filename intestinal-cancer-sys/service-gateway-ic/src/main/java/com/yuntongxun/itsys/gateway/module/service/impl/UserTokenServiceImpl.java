package com.yuntongxun.itsys.gateway.module.service.impl;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yuntongxun.itsys.gateway.common.base.AbstractServiceImpl;
import com.yuntongxun.itsys.gateway.common.constants.CacheConstant;
import com.yuntongxun.itsys.gateway.common.constants.StatusConstant;
import com.yuntongxun.itsys.gateway.common.util.JSONUtil;
import com.yuntongxun.itsys.gateway.common.util.StringUtil;
import com.yuntongxun.itsys.gateway.common.vo.CookieParam;
import com.yuntongxun.itsys.gateway.common.vo.ResultMsg;
import com.yuntongxun.itsys.gateway.module.dao.AuthDao;
import com.yuntongxun.itsys.gateway.module.pojo.UserToken;
import com.yuntongxun.itsys.gateway.module.service.AuthService;

@Service
public class UserTokenServiceImpl extends AbstractServiceImpl implements AuthService {

    private final Logger log = LogManager.getLogger(UserTokenServiceImpl.class);
    
    @Autowired
    private AuthDao authDao;
    @Value("${ytx.gateway.token.expire}")
    private long expire;
    @Value("${ytx.gateway.token.timeout}")
    private long timeout;
    //允许重置间隔 10 秒
    private final int renewTime = 10000;
    

   
    @PostConstruct
    public void init(){
        timeout = timeout*1000;
    }
    
    
    @Override
    public ResultMsg renewToken(CookieParam cookieParam) {
//        logger.info("进入renewToken(),参数：[{}]", JSONUtil.toJson(cookieParam));
        
       String tokenKey = CacheConstant.TOKEN_KEY_PREFIX+cookieParam.getLoginName();
       //查询缓存token
        UserToken userToken = authDao.getTokenForKey(tokenKey);
        //token超时需重新登录
        if(userToken == null){
            return new ResultMsg(StatusConstant.VALIDATE_FAIL_CODE, StatusConstant.TOKEN_TIMEOUT_MSG);   
        }
        if(!cookieParam.getToken().equals(userToken.getToken())
                && !cookieParam.getToken().equals(userToken.getOldToken())){
            return new ResultMsg(StatusConstant.VALIDATE_FAIL_CODE, StatusConstant.VALIDATE_FAIL_MSG);
        }
        long time = System.currentTimeMillis()-(Long.parseLong(userToken.getExpires())-timeout);
        //10秒之内不允许多次重置
        if(cookieParam.getToken().equals(userToken.getOldToken())
                ||(cookieParam.getToken().equals(userToken.getToken())
                && time < renewTime)){
            if(cookieParam.getToken().equals(userToken.getOldToken())){
                logger.debug("与old相同");
            }
            return new ResultMsg(StatusConstant.TOKEN_NOT_RENEW_CODE, StatusConstant.TOKEN_NOT_RENEW_MSG);
        }
        
        
        //设置缓存
        UserToken newUserToken = new UserToken(StringUtil.getUUID(), System.currentTimeMillis()+timeout);
        newUserToken.setOldToken(userToken.getToken());
        authDao.setToken(tokenKey, newUserToken,expire);
        
//        logger.debug("renewToken is end");
        return new ResultMsg(newUserToken.getToken());
    }


    @Override
    protected Class<?> getClassName() {
        return UserTokenServiceImpl.class;
    }


	@Override
	public ResultMsg removeToken(CookieParam cookieParam) {
		// TODO Auto-generated method stub
		 super.debug("进入removeToken(),参数：%s", JSONUtil.toJson(cookieParam));
//		 authDao.setToken(CacheConstant.TOKEN_KEY_PREFIX+user.getLoginName(), userToken, expire);
		 
		 String loginName=cookieParam.getLoginName();
		 String key=CacheConstant.TOKEN_KEY_PREFIX+loginName;
		 
		 String cookieToken =  cookieParam.getToken();
		 
		 UserToken cacheTokenObj = authDao.getTokenForKey(key);
		 if(cookieToken!=null&&cacheTokenObj!=null&&cookieToken.equals(cacheTokenObj.getToken())){
			 log.info("User logout,remove token.");
			 authDao.removeToken(key);
		 }else{
			 log.warn("User logout,redis token:{} is not equals cookie token:{},not remove token.",cacheTokenObj==null?null:cacheTokenObj.getToken(),cookieToken);
		 }
		 
		 return new ResultMsg(StatusConstant.SUCCESS_CODE, StatusConstant.SUCCESS_MSG);
	}
}
