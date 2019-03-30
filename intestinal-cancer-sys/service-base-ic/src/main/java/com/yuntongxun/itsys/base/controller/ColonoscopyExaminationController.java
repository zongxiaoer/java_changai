package com.yuntongxun.itsys.base.controller;

import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.CookieUtil;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.service.ColonoscopyService;
import com.yuntongxun.itsys.base.vo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 肠镜签到（已检查 未检查）管理
 *
 * @author maxiang
 */
@RestController
public class ColonoscopyExaminationController extends AbstractController {

    @Autowired
    private ColonoscopyService colonoscopyService;
    private final Logger log = LogManager.getLogger(ColonoscopyExaminationController.class);

    /**
     * 更新用户签到状态
     * @param req
     * @param resp
     * @param body
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/examination/updateExaminationStatus", method = RequestMethod.POST)
    public String updateExaminationStatus(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) throws ItSysException {
        log.info("updateExaminationStatus 传入包体:{}", body);
        String loginName=CookieUtil.getCookieByLoginName(req);
        colonoscopyService.updateExaminationStatus(body,loginName);
        return JSONUtils.toJson(new Response());
    }

    @Override
    protected String getClassName() {
        return ColonoscopyExaminationController.class.getName();
    }

}
