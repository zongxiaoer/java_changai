package com.yuntongxun.itsys.base.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yuntongxun.itsys.base.common.util.*;
import com.yuntongxun.itsys.base.po.ItsysDepartment;
import com.yuntongxun.itsys.base.service.ColonoscopyService;
import com.yuntongxun.itsys.base.service.UserService;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.service.DepartMentService;
import com.yuntongxun.itsys.base.vo.Response;
import com.yuntongxun.itsys.base.vo.Result;

/**
 * 部门管理
 *
 * @author zhangzl
 */
@RestController
public class DepartMentController extends AbstractController {

    @Autowired
    private DepartMentService departserv;
    @Autowired
    private UserService userService;

    @Autowired
    private ColonoscopyService colonoscopyService;



    private final Logger log = LogManager.getLogger(DepartMentController.class);

    /**
     * 查询部门
     *
     * @param req
     * @param resp
     * @param body
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/department/query", method = RequestMethod.POST)
    public String DepartMentQuery(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) throws ItSysException {
        long sysTime = System.currentTimeMillis();
        log.info("进入 DepartMentQuery方法 时间={}", System.currentTimeMillis());
        log.debug("DepartMentQuery 传入参数为:{}", body);
        JsonObject json = new JsonParser().parse(body).getAsJsonObject();
        ListPageUtil listPage = departserv.queryDepartMent(json);
        log.info("结束 DepartMentQuery方法  返回值={},时间 = {}", listPage.getResultList(), System.currentTimeMillis() - sysTime);
        return JSONUtils.toJson(new Response(listPage.getResultList(), listPage.getPageInfo()));
    }

    /**
     * 查询部门 树形结构
     *
     * @param req
     * @param resp
     * @param body
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/department/getTree", method = RequestMethod.POST)
    public String DepartMentQueryByTree(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) throws ItSysException {
        log.info("进入 DepartMentQuery方法 ");
        log.debug("DepartMentQuery 传入参数为:{}", body);
        JsonObject json = new JsonParser().parse(body).getAsJsonObject();
        Object obj = departserv.queryDepartOnTree(json);
        log.info("结束 DepartMentQuery方法 ");
        return JSONUtils.toJson(new Response(obj));
    }

    /**
     * 根据id查询部门
     *
     * @param req
     * @param resp
     * @param id
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/department/get/{id}", method = RequestMethod.POST)
    public String getDepartMentById(HttpServletRequest req, HttpServletResponse resp, @PathVariable String id) throws ItSysException {
        log.info("进入 getDepartMentById方法 ");
        log.debug("getDepartMentById 传入参数为: id:{}", id);
        List list = departserv.getDepartMentById(id);
        Object obj = new Object();
        if (list.size() > 0) {
            obj = list.get(0);
        }
        log.info("结束 getDepartMentById方法 ");
        return JSONUtils.toJson(new Response(obj));
    }

    /**
     * 新增部门
     *
     * @param req
     * @param resp
     * @param body
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/department/insert", method = RequestMethod.POST)
    public String DepartMentInsert(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) throws ItSysException {
        log.info("进入 DepartMentInsert方法 ");
        log.debug("DepartMentInsert 传入参数为: body:{}", body);
        departserv.insert(body);
        log.info("结束 DepartMentInsert方法 ");
        return JSONUtils.toJson(new Response());
    }

    /**
     * 修改部门
     *
     * @param req
     * @param resp
     * @param body
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/department/update", method = RequestMethod.POST)
    public String DepartMentUpdate(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) throws ItSysException {
        log.info("进入 DepartMentUpdate方法 ");
        log.debug("DepartMentUpdate 传入参数为: body:{}", body);
        departserv.updateDepartMent(body);
        log.info("结束 DepartMentUpdate方法 ");
        return JSONUtils.toJson(new Response());
    }

    /**
     * 删除部门
     *
     * @param req
     * @param resp
     * @param id
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/department/delete/{id}", method = RequestMethod.POST)
    public String DepartMentDelete(HttpServletRequest req, HttpServletResponse resp, @PathVariable String id) throws ItSysException {
        log.info("进入 DepartMentDelete方法 ");
        log.debug("DepartMentDelete 传入参数为: id:{}", id);
        departserv.delDepartMentById(id);
        log.info("结束 DepartMentDelete方法 ");
        return JSONUtils.toJson(new Response());
    }

    /**
     * 保存部门成员关系
     *
     * @param req
     * @param resp
     * @param body
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/department/member/save", method = RequestMethod.POST)
    public String saveDepartMentMember(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) throws ItSysException {
        log.info("进入 saveDepartMentMember方法 ");
        log.debug("saveDepartMentMember 传入参数为: body:{}", body);
        departserv.saveDepartMentMemBer(body);
        log.info("结束 saveDepartMentMember方法 ");
        return JSONUtils.toJson(new Response());
    }

    /**
     * 删除部门成员关系
     *
     * @param req
     * @param resp
     * @param body
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/department/member/delete", method = RequestMethod.POST)
    public String delDepartMentMember(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) throws ItSysException {
        log.info("进入 delDepartMentMember方法 ");
        log.debug("delDepartMentMember 传入参数为: body:{}", body);
        departserv.delDepartMentMemberByUser(body);
        log.info("结束 delDepartMentMember方法 ");
        return JSONUtils.toJson(new Response());
    }


    /**
     * 根据部门Id获取部门成员
     *
     * @param req
     * @param resp
     * @param departId
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/department/member/get/{departId}", method = RequestMethod.POST)
    public String getDepartMentMember(HttpServletRequest req, HttpServletResponse resp, @PathVariable String departId) throws ItSysException {
        log.info("进入 getDepartMentMember方法 ");
        log.debug("getDepartMentMember 传入参数为: departId:{}", departId);
        List list = departserv.getDepartMentMember(departId);
        log.info("结束 getDepartMentMember方法 ");
        return JSONUtils.toJson(new Response(list));
    }

    /**
     * 根据部门id获取副经理
     *
     * @param req
     * @param resp
     * @param departId
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/department/manager/get/{departId}", method = RequestMethod.POST)
    public String getManagerByDepartId(HttpServletRequest req, HttpServletResponse resp, @PathVariable String departId) throws ItSysException {
        log.info("进入 getDepartMentMember方法 ");
        log.debug("getDepartMentMember 传入参数为: departId:{}", departId);
        List obj = departserv.getManagerByDepartId(departId);
        log.info("结束 getDepartMentMember方法 ");
        return JSONUtils.toJson(new Response(obj));
    }

    /**
     * 根据部门id获取总裁
     *
     * @param req
     * @param resp
     * @param departId
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/department/ceo/get/{departId}", method = RequestMethod.POST)
    public String getceoByDepartId(HttpServletRequest req, HttpServletResponse resp, @PathVariable String departId) throws ItSysException {
        log.info("进入 getceoByDepartId方法 ");
        log.debug("getceoByDepartId 传入参数为: departId:{}", departId);
        List obj = departserv.getCeoByDepartId(departId);
        log.info("结束 getceoByDepartId方法 ");
        return JSONUtils.toJson(new Response(obj));
    }

    /**
     * 根据部门id获取面试官
     *
     * @param req
     * @param resp
     * @param departId
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/department/interviewer/get/{departId}", method = RequestMethod.POST)
    public String getInterviewerByDepartId(HttpServletRequest req, HttpServletResponse resp, @PathVariable String departId) throws ItSysException {
        log.info("进入 getInterviewerByDepartId方法 ");
        log.debug("getInterviewerByDepartId 传入参数为: departId:{}", departId);
        List obj = departserv.getInterviewerByDepartId(departId);
        log.info("结束 getInterviewerByDepartId方法 ");
        return JSONUtils.toJson(new Response(obj));
    }

    /**
     * 根据部门id获取入职指导人
     *
     * @param req
     * @param resp
     * @param departId
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/department/induction/get/{departId}", method = RequestMethod.POST)
    public String getInductionByDepartId(HttpServletRequest req, HttpServletResponse resp, @PathVariable String departId) throws ItSysException {
        log.info("进入 getInductionByDepartId方法 ");
        log.debug("getInductionByDepartId 传入参数为: departId:{}", departId);
        List obj = departserv.getInductionByDepartId(departId);
        log.info("结束 getInductionByDepartId方法 ");
        return JSONUtils.toJson(new Response(obj));
    }


    /**
     * 根据id获取其他部门用户树形结构数据
     *
     * @param req
     * @param resp
     * @param departId
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/department/getOtherDepart/{id}", method = RequestMethod.POST)
    public String getOther(HttpServletRequest req, HttpServletResponse resp, @PathVariable String id) throws ItSysException {
        log.info("进入 getInductionByDepartId方法 ");
        log.debug("getInductionByDepartId 传入参数为: id:{}", id);
        String loginName = CookieUtil.getCookieByLoginName(req);
        ItsysDepartment itsysDepartment = userService.getCommunityIdAndAreaIdByLoginName(loginName);
        Object obj = departserv.getOtherDepartByPid(itsysDepartment.getId().toString());
        log.info("结束 getInductionByDepartId方法 ");
        return JSONUtils.toJson(new Response(obj));
    }
    /**
     * 根据id获取树形结构数据
     * @param req
     * @param resp
     * @param id
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/department/getAllOtherDepts/{id}", method = RequestMethod.POST)
    public String getAllOtherDepts(HttpServletRequest req, HttpServletResponse resp, @PathVariable String id) throws ItSysException {
        log.info("进入 getAllOtherDepts方法 ==start");
        log.debug("getInductionByDepartId 传入参数为: id:{}", id);
        String loginName = CookieUtil.getCookieByLoginName(req);
//        String loginName = "hunanadmin";
        ItsysDepartment itsysDepartment = userService.getAllDepts(loginName);
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        Object obj = departserv.getAllDepartByPid(itsysDepartment.getId().toString(),itsysDepartment.getLevel(),doctorInfo.getId());
        log.info("结束 getAllOtherDepts方法==end ");
        return JSONUtils.toJson(new Response(obj));
    }
    /**
     * 修改部门成员关系表
     *
     * @param req
     * @param resp
     * @param departId
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/department/member/update", method = RequestMethod.POST)
    public String delDepartMemberByMemberId(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) throws ItSysException {
        log.info("进入 delDepartMemberByMemberId方法 ");
        log.debug("delDepartMemberByMemberId 传入参数为: body:{}", body);
        departserv.updateDepartMember(body);
        log.info("结束 delDepartMemberByMemberId方法 ");
        return JSONUtils.toJson(new Response());
    }

    /**
     * 查询公司没有绑定部门的人员
     * * @return
     */
    @PostMapping(value = "/employee/notExists/department/query")
    public Result queryNotExistsDepartEmployee(@RequestBody String body) {
        logger.info("@Controller 查询公司没有绑定部门的人员....... body={}", body);
        Result result = null;
        try {
            result = departserv.queryNotExistsDepartEmployee(body);
        } catch (Exception ex) {
            result = ResultUtil.error(GlobalErrorCode.RUNTIME_ERROR_CODE, ex.getMessage());
        }
        return result;
    }

    /**
     * 查询当前登录用户所在医院下级所有医院
     * * @return
     */
    @PostMapping(value = "/department/underling/hospital/query")
    public Result findUnderlingHospital(HttpServletRequest request) {
        logger.info("@Controller findUnderlingHospital 查询当前下级所属医院员....... 参数 无");

        String loginName = CookieUtil.getCookieByLoginName(request);

        Result result = this.departserv.findHospitalByPid(loginName);
        logger.info("@Controller findUnderlingHospital 返回结果集 = {}", JSONUtils.toJson(result));
        return result;
    }

    /**
     * 根据当前登录用户取所在医院
     * @param req
     * @param resp
     * @param body
     * @return
     */
    @RequestMapping(value = "/department/hospital/findHospitalByLoginName", method = RequestMethod.POST)
    public String findHospitalByLoginName(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body){
        log.info("findHospitalByLoginName 输入参数:{}", body);
        String loginName= CookieUtil.getCookieByLoginName(req);
        log.info("findHospitalByLoginName loginName：{}", loginName);
        Object obj = departserv.findHospitalByLoginName(loginName);
        return JSONUtils.toJson(new Response(obj));
    }


    @Override
    protected String getClassName() {
        return DepartMentController.class.getName();
    }
}
