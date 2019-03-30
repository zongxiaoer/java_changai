package com.yuntongxun.itsys.base.controller;

import com.yuntongxun.itsys.base.common.util.CookieUtil;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.service.ColonoscopyReservationAllocationService;
import com.yuntongxun.itsys.base.vo.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zongt
 * @date 2018/4/20
 */
@RestController
public class ColonoscopyReservationAllocation {

    private final Logger log = LogManager.getLogger(ColonoscopyPathologyResultController.class);

    @Autowired
    private ColonoscopyReservationAllocationService colonoscopyReservationAllocationService;



    @RequestMapping(value = "/colonoscopy/ReservaAllocation/queryColonoResAllocation", method = RequestMethod.POST)
    public String queryColonoResAllocation(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
        log.info("queryColonoResAllocation 输入参数:{}",body);
        String loginName= CookieUtil.getCookieByLoginName(req);
        log.info("addColonoscopyPathologyResult loginName：{}", loginName);
        return colonoscopyReservationAllocationService.queryColonoscopyReservationAllocation(body,loginName);
    }
}
