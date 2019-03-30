package com.yuntongxun.itsys.base.common.util;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.yuntongxun.itsys.base.cache.RedisManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Set;

/**
 * Description: 发送短信通用工具
 * 相关短信参数在配置文件
 *
 * @author LuoKun on 2018-04-19.
 */
@Component
public class SendSms {

    private final Logger log = LogManager.getLogger(SendSms.class);

    @Value("${sms.accountSid}")
    private String accountSid;

    @Value("${sms.authToken}")
    private String authToken;

    @Value("${sms.serverIp}")
    private String serverIp;

    @Value("${sms.serverPort}")
    private String serverPort;

    @Value("${sms.appId}")
    private String appId;

    @Value("${login.authCode.codeTime}")
    private String codeTime;


    /**
     * 发送短信 通用
     *
     * @param phoneNum   手机号
     * @param templateId 模板id
     * @param parm       模板内容 数组
     * @return
     */
    public boolean getAuthCode(String phoneNum, String templateId, String[] parm) {
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
        restAPI.init(serverIp, serverPort);
        // 初始化服务器地址和端口，生产环境配置成app.cloopen.com，端口是8883.
        restAPI.setAccount(accountSid, authToken);
        // 初始化主账号名称和主账号令牌，登陆云通讯网站后，可在控制首页中看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN。
        restAPI.setAppId(appId);
        HashMap<String, Object> result = restAPI.sendTemplateSMS(phoneNum, templateId, parm);
        log.info("@Service getAuthCode statusCode={}", result.get("statusCode"));
        if ("000000".equals(result.get("statusCode"))) {
            //正常返回输出data包体信息（map）
            HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for (String key : keySet) {
                Object object = data.get(key);
                log.info(key + " = " + object);
            }
            return true;
        } else {
            //异常返回输出错误码和错误信息
            log.info("错误码 = {}, 错误信息 = {}", result.get("statusCode"), result.get("statusMsg"));
            return false;
        }
    }


}
