package com.yuntongxun.itsys.base.controller;

import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.CookieUtil;
import com.yuntongxun.itsys.base.common.util.DateUtil;
import com.yuntongxun.itsys.base.common.util.ExcelData;
import com.yuntongxun.itsys.base.common.util.ExportExcelUtil;
import com.yuntongxun.itsys.base.po.dto.HospitalReviewExport;
import com.yuntongxun.itsys.base.service.PersonExportService;
import com.yuntongxun.itsys.base.vo.ColonoscopyVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Vector;

/**
 * 导出信息管理
 * ClassName:PersonExportController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年6月6日 下午1:41:37 <br/>
 *
 * @author maxiang
 * @see
 * @since JDK 1.7
 */
@RestController
public class PersonExportController extends AbstractController {
    private final Logger log = LogManager.getLogger(PersonExportController.class);

    @Autowired
    private PersonExportService personExportService;

    /**
     * 社区导出人员
     * @param req
     * @param response
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/person/commUsersQueryExcel", method = RequestMethod.GET)
    public void commUsersQueryExcel(HttpServletRequest req, HttpServletResponse response) throws ItSysException {
        printStartTag("/hospital/person/commUsersQueryExcel");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
        log.info("@Controller导出excel查询受试者列表登陆者账号 loginName：{}", loginName);
        HospitalReviewExport hospitalReviewExport = new HospitalReviewExport();
        ExcelData excelData = personExportService.commUsersQueryExcel(hospitalReviewExport,loginName,response);
        try {
            ExportExcelUtil.excelOS(excelData);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        printEndTag("/hospital/person/commUsersQueryExcel");
    }

    /**
     * 地区导出人员
     * @param req
     * @param response
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/person/areaUsersQueryExcel", method = RequestMethod.GET)
    public void areaUsersQueryExcel(HttpServletRequest req, HttpServletResponse response) throws ItSysException {
        printStartTag("/hospital/person/areaUsersQueryExcel");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
        log.info("@Controller导出excel查询受试者列表登陆者账号 loginName：{}", loginName);
        HospitalReviewExport hospitalReviewExport = new HospitalReviewExport();
        ExcelData excelData = personExportService.areaUsersQueryExcel(hospitalReviewExport,loginName,response);
        try {
            ExportExcelUtil.excelOS(excelData);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        printEndTag("/hospital/person/areaUsersQueryExcel");
    }

    /**
     * 国家导出人员
     * @param req
     * @param response
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/person/nationUsersQueryExcel", method = RequestMethod.GET)
    public void nationUsersQueryExcel(HttpServletRequest req, HttpServletResponse response) throws ItSysException {
        printStartTag("/hospital/person/nationUsersQueryExcel");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
        log.info("@Controller导出excel查询受试者列表登陆者账号 loginName：{}", loginName);
        HospitalReviewExport hospitalReviewExport = new HospitalReviewExport();
        ExcelData excelData = personExportService.nationUsersQueryExcel(hospitalReviewExport,loginName,response);
        try {
            ExportExcelUtil.excelOS(excelData);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        printEndTag("/hospital/person/nationUsersQueryExcel");
    }

    /**
     * 地区肠镜数据管理导出
     * @param req
     * @param response
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/colonoscopy/queryFoCountryCJExcel", method = RequestMethod.GET)
    public void queryFoCountryCJExcel(HttpServletRequest req, HttpServletResponse response) throws ItSysException{
        printStartTag("国家肠镜管理列表==》start");
        printHttpPacket(req, null);
        String loginName=CookieUtil.getCookieByLoginName(req);
        log.info("@Controller导出excel国家肠镜数据管理导出 ");
        ColonoscopyVo queryCondition = new ColonoscopyVo();
        List<ColonoscopyVo> list = personExportService.queryFoCountryCJExcel(queryCondition, loginName);
        //导出逻辑
        String[][] array = new String[][]{};
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/msexcel");
        String fileName ="国家肠镜管理列表";
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
        titleVec.add("所属地区");
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
        int [] titleWidthAry = {15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15};
        if(list!=null&& list.size()>0) {
            array = new String[list.size()][20];
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
                array[i][5] = review.getAreaName() != null ? review.getAreaName() : "";
                array[i][6] = review.getDepName() != null ? review.getDepName() : "";
                array[i][7] = review.getCreateUser() != null ? review.getCreateUser().toString() : "";
                if(review.getGroup() != null && !review.getGroup().equals("")){
                    if(review.getGroup().equals("A")){
                        array[i][8] = "A组";
                    }else if(review.getGroup().equals("B")){
                        array[i][8] = "B组";
                    }else if(review.getGroup().equals("C")){
                        array[i][8] = "C组";
                    }else if(review.getGroup().equals("Cg")){
                        array[i][8] = "C组高危";
                    }else if(review.getGroup().equals("Cd")){
                        array[i][8] = "C组低危";
                    }else if(review.getGroup().equals("Cp")){
                        array[i][8] = "C组未评估";
                    }
                }else{
                    array[i][8] = review.getGroup() != null ? review.getGroup() : "";
                }
                array[i][9] = review.getInGroupDate() != null ? DateUtil.formatDate(review.getInGroupDate(), "yyyy-MM-dd") : "";
                if(review.getOverallStatusCy() != null){
                    if(review.getOverallStatusCy() == 1){
                        array[i][10] = "正常";
                    }else if(review.getOverallStatusCy() == 2){
                        array[i][10] = "退出";
                    }else if(review.getOverallStatusCy() == 3){
                        array[i][10] = "肠癌";
                    }else if(review.getOverallStatusCy() == 4){
                        array[i][10] = "死亡";
                    }
                }else{
                    array[i][10] = review.getOverallStatusCy() != null ? review.getOverallStatusCy().toString() : "";
                }
                if(review.getReserveStatus() != null && !review.getReserveStatus().equals("")){
                    if(review.getReserveStatus().equals("1")){
                        array[i][11] = "未预约";
                    }else if(review.getReserveStatus().equals("2")){
                        array[i][11] = "已预约";
                    }
                }else{
                    array[i][11] = review.getReserveStatus() != null ? review.getReserveStatus().toString() : "";
                }
                array[i][12] = review.getReserveDate() != null ? DateUtil.formatDate(review.getReserveDate(),"yyyy-MM-dd") : "";
                if(review.getExaminationStatus() != null && !review.getExaminationStatus().equals("")){
                    if(review.getExaminationStatus().equals("1")){
                        array[i][13] = "未就诊";
                    }else if(review.getExaminationStatus().equals("2")){
                        array[i][13] = "已就诊";
                    }
                }else{
                    array[i][13] = review.getExaminationStatus() != null ? review.getExaminationStatus().toString() : "";
                }
                if(review.getFinishedStatus() != null && !review.getFinishedStatus().equals("")){
                    if(review.getFinishedStatus().equals("1")){
                        array[i][14] = "未完成";
                    }else if(review.getFinishedStatus().equals("2")){
                        array[i][14] = "已完成";
                    }
                }else{
                    array[i][14] = review.getFinishedStatus() != null ? review.getFinishedStatus().toString() : "";
                }
				/*array[i][13] = review.getExaminationDate() != null ? DateUtil.dateToStr(review.getExaminationDate(),11) : "";*/
                array[i][15] = review.getExaminationDate() != null ? DateUtil.formatDate(review.getExaminationDate(), "yyyy-MM-dd") : "";
                if(review.getResultStatus() != null && !review.getResultStatus().equals("")){
                    if(review.getResultStatus().equals("1")){
                        array[i][16] = "未录入";
                    }else if(review.getResultStatus().equals("2")){
                        array[i][16] = "已录入";
                    }
                }else{
                    array[i][16] = review.getResultStatus() != null ? review.getResultStatus().toString() : "";
                }
                if(review.getPathologyStatus() != null && !review.getPathologyStatus().equals("")){
                    if(review.getPathologyStatus().equals("1")){
                        array[i][17] = "未录入";
                    }else if(review.getPathologyStatus().equals("2")){
                        array[i][17] = "已录入";
                    }
                }else{
                    array[i][17] = review.getPathologyStatus() != null ? review.getPathologyStatus().toString() : "";
                }
                if(review.getNotificationEntryStatus() != null && !review.getNotificationEntryStatus().equals("")){
                    if(review.getNotificationEntryStatus().equals("1")){
                        array[i][18] = "未录入";
                    }else if(review.getNotificationEntryStatus().equals("2")){
                        array[i][18] = "已录入";
                    }
                }else{
                    array[i][18] = review.getNotificationEntryStatus() != null ? review.getNotificationEntryStatus().toString() : "";
                }
                if(review.getNotificationIssueStatus() != null && !review.getNotificationIssueStatus().equals("")){
                    if(review.getNotificationIssueStatus().equals("1")){
                        array[i][19] = "未发放";
                    }else if(review.getNotificationIssueStatus().equals("2")){
                        array[i][19] = "已发放";
                    }
                }else{
                    array[i][19] = review.getNotificationIssueStatus() != null ? review.getNotificationIssueStatus().toString() : "";
                }
				/*array[i][18] = review.getCreateUser() != null ? review.getCreateUser().toString() : "";*/
                areaName = review.getAreaName();
            }
        }
        try {
            ExportExcelUtil.excelOS("国家肠镜管理列表", titleVec,titleWidthAry, array,response.getOutputStream(), areaName);
        } catch (IOException e) {
            e.printStackTrace();
            log.info(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
        printEndTag("/hospital/colonoscopy/queryFoCountryCJExcel");
    }


    @Override
    protected String getClassName() {
        return FitController.class.getName();
    }
}


