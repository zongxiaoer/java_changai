package com.yuntongxun.itsys.base.controller;

import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.*;
import com.yuntongxun.itsys.base.po.HospitalColonoscopyResult;
import com.yuntongxun.itsys.base.service.ColonoscopyService;
import com.yuntongxun.itsys.base.vo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * 肠镜管理
 *
 * @author zhangzl
 */
@RestController
public class ColonoscopyController extends AbstractController {

    @Autowired
    private ColonoscopyService colonoscopyService;
    private final Logger log = LogManager.getLogger(ColonoscopyController.class);

    /**
     * 1.3.1、根据条件查询受试者接口（社区）；
     *
     * @param req
     * @param resp
     * @param body,检索条件，name，sid，phone，group，reserveStatus，examinationStatus，finishedStatus，notificationIssueStatus,pageNo，pageSize
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/colonoscopy/record/query", method = RequestMethod.POST)
    public Result query(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) throws ItSysException {
		printStartTag("根据条件查询受试者接口（社区）");
        log.info("根据条件查询受试者接口（社区）");
        log.info("传入包体:{}", body);

        String loginName=CookieUtil.getCookieByLoginName(req);
        ColonoscopyVo queryCondition = null;
        try{
        	queryCondition = JSONUtils.toBean(body, ColonoscopyVo.class);
        }catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }

        ListPageUtil listPage = colonoscopyService.query(queryCondition,loginName);
		Result result=new Result(listPage.getResultList(),listPage.getPageInfo());
		printEndTag("根据条件查询受试者接口（社区）");
        log.info("返回查询对象个数={}", listPage.getResultList().size());
        return result;
    }

    /**
     * 1.3.1、根据条件查询受试者接口（地区）;
     *
     * @param req
     * @param resp
     * @param body,检索条件，name，sid，phone，group，reserveStatus，examinationStatus，finishedStatus，notificationIssueStatus,pageNo，pageSize
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/area/colonoscopy/record/query", method = RequestMethod.POST)
    public Result queryForArea(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) throws ItSysException {
		printStartTag("根据条件查询受试者接口（地区）");
        log.info("传入包体:{}", body);

        String loginName=CookieUtil.getCookieByLoginName(req);
        ColonoscopyVo queryCondition = null;
        try{
        	queryCondition = JSONUtils.toBean(body, ColonoscopyVo.class);
        }catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }

        ListPageUtil listPage = colonoscopyService.queryForArea(queryCondition,loginName);
		Result result=new Result(listPage.getResultList(),listPage.getPageInfo());
		printEndTag("根据条件查询受试者接口（地区）");
        log.info("返回查询对象个数={}", listPage.getResultList().size());
        return result;
    }
    /**
     * 国家   肠镜管理根据条件查询
     * @param req
     * @param resp
     * @param body
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/nation/colonoscopy/record/querynationList", method = RequestMethod.POST)
    public Result queryForNationById(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) throws ItSysException {
    	printStartTag("根据条件查询国家肠镜管理（国家）");
        log.info("传入包体:{}", body);
        String loginName=CookieUtil.getCookieByLoginName(req);
        ColonoscopyVo queryCondition = null;
        try{
        	queryCondition = JSONUtils.toBean(body, ColonoscopyVo.class);
        }catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }
        ListPageUtil listPage = colonoscopyService.queryForNationList(queryCondition, loginName);
		Result result=new Result(listPage.getResultList(),listPage.getPageInfo());
        printEndTag("根据条件查询国家肠镜管理（国家）");
        log.info("返回查询对象个数={}", listPage.getResultList().size());
    	return result;
    }



	/**
	 *1.3.2、地区为受试者预约场景接口
	 *
	 * @param req
	 * @param resp
	 * @param body,{ "sid":"TC10001",   "allocationId":1 //结肠镜预约分配表id }
	 * @return
	 * @throws ItSysException
	 */
	@RequestMapping(value = "/hospital/colonoscopy/record/booking", method = RequestMethod.POST)
	public Result booking(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) throws ItSysException {
		printStartTag("为受试者预约结肠镜检查社区|地区");
		log.info("传入包体:{}", body);
		String loginName=CookieUtil.getCookieByLoginName(req);
		ColonoscopyVo vo = null;
		try{
			vo = JSONUtils.jsonToBeanDateSerializer(body, ColonoscopyVo.class,"yyyy-MM-dd");
		}catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
		}


		colonoscopyService.booking(vo,loginName);
		Result result=new Result();
		log.info("返回查询对象：{}", JSONUtils.toJson(result));
		printEndTag("为受试者预约结肠镜检查");
		return result;
	}

    /**
     *1.3.2、为受试者预约场景接口
     *
     * @param req
     * @param resp
     * @param body,{ "sid":"TC10001",   "allocationId":1 //结肠镜预约分配表id }
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/colonoscopy/record/rebooking", method = RequestMethod.POST)
    public Result rebooking(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) throws ItSysException {
		printStartTag("为受试者 重新 预约结肠镜检查");
        log.info("传入包体:{}", body);
        String loginName=CookieUtil.getCookieByLoginName(req);
        ColonoscopyVo vo = null;
        try{
        	vo = JSONUtils.toBean(body, ColonoscopyVo.class);
        }catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }
        colonoscopyService.rebooking(vo,loginName);
		Result result=new Result();
		log.info("返回查询对象：{}", JSONUtils.toJson(result));
		printEndTag("为受试者 重新 预约结肠镜检查");
        return result;
    }

    /**
     * 取消预约操作1
     * @param req
     * @param resp
     * @param body
     * @return
     */
    @RequestMapping(value = "/hospital/colonoscopy/record/cancelBooking", method = RequestMethod.POST)
    public Result cancelBooking(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body){
		printStartTag("取消预约操作 社区|地区");
        /**
         * 检查状态为空的时候 都可以取消预约
         * 如果取消预约在预约时间之前的话，放号的数量要+1
         */
        String loginName=CookieUtil.getCookieByLoginName(req);
        ColonoscopyVo vo = null;
        try{
            vo = JSONUtils.toBean(body, ColonoscopyVo.class);
        }catch(Exception e){
            throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }
        colonoscopyService.cancelBooking(vo,loginName);
        Result result=new Result();
        return result;
    }

    /**
     * 1.3.5、查看结肠镜检查预约单接口
     *
     * @param req
     * @param resp
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/colonoscopy/record/reserve/detail/{id}", method = RequestMethod.POST)
    public Result getReserveDetail(HttpServletRequest req, HttpServletResponse resp,@PathVariable int id) throws ItSysException {
		printStartTag("查询预约单信息");
        log.info("传入id:{}", id);

//        String loginName=CookieUtil.getCookieByLoginName(req);
        if(id<=0){
        	log.error("Request param error,id={}.",id);
        	throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Request param error.");
        }
        ReserveDetail reserveDetail = colonoscopyService.getReserveDetail(id);
		Result result=new Result(reserveDetail);
		log.info("返回查询对象：{}", JSONUtils.toJson(result));
		printEndTag("查询预约单信息");
        return result;
    }

    /**
     * 1.3.7、为受试者发放结肠镜结果记录接口；
     *
     * @param req
     * @param resp
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/colonoscopy/notification/issue", method = RequestMethod.POST)
    public Result issueNotification(HttpServletRequest req, HttpServletResponse resp,@RequestBody String body) throws ItSysException {
		printStartTag("发放告知书记录");
        log.info("传入包体，body:{}", body);
        String loginName=CookieUtil.getCookieByLoginName(req);

        ColonoscopyIssueVo vo = null;
        try{
        	vo = JSONUtils.toBean(body, ColonoscopyIssueVo.class);
    		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    		Date issueDate = df.parse(vo.getIssueDate());
    		vo.setIssueDateForSql(issueDate);
        }catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }

        colonoscopyService.issueNotification(vo,loginName);
		Result result=new Result();
		log.info("返回查询对象：{}", JSONUtils.toJson(result));
		printEndTag("发放告知书记录");
        return result;
    }

    /**
     * 1.3.8、查看结肠镜告知书接口；
     *
     * @param req
     * @param resp
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/colonoscopy/notification/get/{id}", method = RequestMethod.POST)
    public Result getNotification(HttpServletRequest req, HttpServletResponse resp,@PathVariable int id,@RequestBody HospitalColonoscopyResult hospitalColonoscopyResult) throws ItSysException {
		printStartTag("查看结肠镜告知书");
        log.info("传入id:{}", id);
//        String loginName=CookieUtil.getCookieByLoginName(req);
        if(id<=0){
        	log.error("Request param error,id={}.",id);
            log.info("getNotification 输入参数:{}", JSONUtils.toJson(hospitalColonoscopyResult));
        	throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Request param error.");
        }
        ColonoscopyNotificationVo vo = colonoscopyService.getNotification(id,hospitalColonoscopyResult);
		Result result=new Result(vo);
		log.info("返回查询对象：{}", JSONUtils.toJson(result));
		printEndTag("查看结肠镜告知书");
        return result;
    }

    @Override
    protected String getClassName() {
        return ColonoscopyController.class.getName();
    }


	/**
	 * 根据预约id查询预约批次剩余可预约信息
	 * @param req
	 * @param resp
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/hospital/community/reservation/get/{id}", method = RequestMethod.POST)
	public Result getReservation(HttpServletRequest req, HttpServletResponse resp, @PathVariable int id) {
		printStartTag("getReservation");
		printHttpPacket(req, null);
		log.info("id:{}.",id);
		ReservationVo vo = colonoscopyService.getReservation(id);
		Result result=new Result(vo);
		log.info("返回查询对象：{}", JSONUtils.toJson(result));
		printEndTag("getReservation");
		return result;
	}


    /**
     *1.3.2 新增肠镜检查记录表
     *
     * @param req
     * @param resp
     * @param body,
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/colonoscopy/record/addColonoscopyRecord", method = RequestMethod.POST)
    public Result addColonoscopyRecord(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) throws ItSysException {
        printStartTag("为受试者 添加 结肠镜检查记录");
        log.info("传入包体:{}", body);
        String loginName=CookieUtil.getCookieByLoginName(req);
        ColonoscopyVo vo = null;
        try{
            vo = JSONUtils.toBean(body, ColonoscopyVo.class);
        }catch(Exception e){
            throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }
        colonoscopyService.addColonoscopyRecord(vo,loginName);
        Result result=new Result();
        log.info("返回查询对象：{}", JSONUtils.toJson(result));
        printEndTag("为受试者 添加 结肠镜检查记录");
        return result;
    }
    /**
     * 地区肠镜数据管理导出
     * @param req
     * @param response
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/colonoscopy/queryForAreaListExcel", method = RequestMethod.GET)
	public void queryForAreaListExcel(HttpServletRequest req, HttpServletResponse response) throws ItSysException{
		printStartTag("地区肠镜管理列表==》start");
		printHttpPacket(req, null);
		String loginName=CookieUtil.getCookieByLoginName(req);
//		String loginName = "hunanadmin";
		log.info("@Controller导出excel地区肠镜数据管理导出 ");
//		List<HospitalReview> list = personService.queryExcel(person,loginName);
		ColonoscopyVo queryCondition = new ColonoscopyVo();
		List<ColonoscopyVo> list = colonoscopyService.queryForAreaExcel(queryCondition, loginName);
		//导出逻辑
		String[][] array = new String[][]{};
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/msexcel");
		String fileName ="地区肠镜管理列表";
		try {
			response.setHeader("content-disposition", "attachment; filename=\""+ URLEncoder.encode(fileName, "UTF-8")+".xlsx\"");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		//获得表头
		Vector titleVec = new Vector();
		titleVec.add("SID");
		titleVec.add("姓名");
		titleVec.add("性别");
		titleVec.add("年龄");
		titleVec.add("手机号");
		titleVec.add("所属区");
		titleVec.add("所属社区");
		titleVec.add("分组");
		titleVec.add("入组日期");
		titleVec.add("年度状态");
		titleVec.add("预约状态");
		titleVec.add("预约时间");
		titleVec.add("检查状态");
		titleVec.add("完成情况");
		titleVec.add("检查时间");
		titleVec.add("肠镜结果");
		titleVec.add("病理结果");
		titleVec.add("告知书结果");
		titleVec.add("告知书发放");
		/*titleVec.add("创建人");*/
		String areaName = "";
		//为表的每一列设定列宽.
		int [] titleWidthAry = {15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15};
		if(list!=null&& list.size()>0) {
			array = new String[list.size()][19];
			for (int i = 0; i < list.size(); i++) {
				ColonoscopyVo  review = list.get(i);
				array[i][0] = review.getSid() != null ? review.getSid() : "";
				array[i][1] = review.getName() != null ? review.getName() : "";
				if(review.getSex() != null){
					if(review.getSex() == 1){
						array[i][2] = "男";
					}else if(review.getSex() == 2){
						array[i][2] = "女";
					}
				}else{
					array[i][2] = review.getSex() != null ? review.getSex().toString() : "";
				}
				array[i][3] = review.getAge() != null ? review.getAge().toString() : "";
				array[i][4] = review.getPhone() != null ? review.getPhone() : "";
				array[i][5] = review.getDepName() != null ? review.getDepName() : "";
				array[i][6] = review.getCreateUser() != null ? review.getCreateUser().toString() : "";
				if(review.getGroup() != null && !review.getGroup().equals("")){
					if(review.getGroup().equals("A")){
						array[i][7] = "A组";
					}else if(review.getGroup().equals("B")){
						array[i][7] = "B组";
					}else if(review.getGroup().equals("C")){
						array[i][7] = "C组";
					}else if(review.getGroup().equals("Cg")){
						array[i][7] = "C组高危";
					}else if(review.getGroup().equals("Cd")){
						array[i][7] = "C组低危";
					}else if(review.getGroup().equals("Cp")){
						array[i][7] = "C组未评估";
					}
				}else{
					array[i][7] = review.getGroup() != null ? review.getGroup() : "";
				}
				array[i][8] = review.getInGroupDate() != null ? DateUtil.formatDate(review.getInGroupDate(), "yyyy-MM-dd") : "";
				if(review.getOverallStatusCy() != null){
					if(review.getOverallStatusCy() == 1){
						array[i][9] = "正常";
					}else if(review.getOverallStatusCy() == 2){
						array[i][9] = "退出";
					}else if(review.getOverallStatusCy() == 3){
						array[i][9] = "肠癌";
					}else if(review.getOverallStatusCy() == 4){
						array[i][9] = "死亡";
					}
				}else{
					array[i][9] = review.getOverallStatusCy() != null ? review.getOverallStatusCy().toString() : "";
				}
				if(review.getReserveStatus() != null && !review.getReserveStatus().equals("")){
					if(review.getReserveStatus().equals("1")){
						array[i][10] = "未预约";
					}else if(review.getReserveStatus().equals("2")){
						array[i][10] = "已预约";
					}
				}else{
					array[i][10] = review.getReserveStatus() != null ? review.getReserveStatus().toString() : "";
				}
				array[i][11] = review.getReserveDate() != null ? DateUtil.formatDate(review.getReserveDate(),"yyyy-MM-dd") : "";
				if(review.getExaminationStatus() != null && !review.getExaminationStatus().equals("")){
					if(review.getExaminationStatus().equals("1")){
						array[i][12] = "未就诊";
					}else if(review.getExaminationStatus().equals("2")){
						array[i][12] = "已就诊";
					}
				}else{
					array[i][12] = review.getExaminationStatus() != null ? review.getExaminationStatus().toString() : "";
				}
				if(review.getFinishedStatus() != null && !review.getFinishedStatus().equals("")){
					if(review.getFinishedStatus().equals("1")){
						array[i][13] = "未完成";
					}else if(review.getFinishedStatus().equals("2")){
						array[i][13] = "已完成";
					}
				}else{
					array[i][13] = review.getFinishedStatus() != null ? review.getFinishedStatus().toString() : "";
				}
				/*array[i][13] = review.getExaminationDate() != null ? DateUtil.dateToStr(review.getExaminationDate(),11) : "";*/
				array[i][14] = review.getExaminationDate() != null ? DateUtil.formatDate(review.getExaminationDate(), "yyyy-MM-dd") : "";
				if(review.getResultStatus() != null && !review.getResultStatus().equals("")){
					if(review.getResultStatus().equals("1")){
						array[i][15] = "未录入";
					}else if(review.getResultStatus().equals("2")){
						array[i][15] = "已录入";
					}
				}else{
					array[i][15] = review.getResultStatus() != null ? review.getResultStatus().toString() : "";
				}
				if(review.getPathologyStatus() != null && !review.getPathologyStatus().equals("")){
					if(review.getPathologyStatus().equals("1")){
						array[i][16] = "未录入";
					}else if(review.getPathologyStatus().equals("2")){
						array[i][16] = "已录入";
					}
				}else{
					array[i][16] = review.getPathologyStatus() != null ? review.getPathologyStatus().toString() : "";
				}
				if(review.getNotificationEntryStatus() != null && !review.getNotificationEntryStatus().equals("")){
					if(review.getNotificationEntryStatus().equals("1")){
						array[i][17] = "未录入";
					}else if(review.getNotificationEntryStatus().equals("2")){
						array[i][17] = "已录入";
					}
				}else{
					array[i][17] = review.getNotificationEntryStatus() != null ? review.getNotificationEntryStatus().toString() : "";
				}
				if(review.getNotificationIssueStatus() != null && !review.getNotificationIssueStatus().equals("")){
					if(review.getNotificationIssueStatus().equals("1")){
						array[i][18] = "未发放";
					}else if(review.getNotificationIssueStatus().equals("2")){
						array[i][18] = "已发放";
					}
				}else{
					array[i][18] = review.getNotificationIssueStatus() != null ? review.getNotificationIssueStatus().toString() : "";
				}
				/*array[i][18] = review.getCreateUser() != null ? review.getCreateUser().toString() : "";*/
				areaName = review.getAreaName();
			}
		}
		try {
			ExportExcelUtil.excelOS("地区肠镜管理列表", titleVec,titleWidthAry, array,response.getOutputStream(), areaName);
		} catch (IOException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		printEndTag("/hospital/colonoscopy/queryForAreaListExcel");
	}

}
