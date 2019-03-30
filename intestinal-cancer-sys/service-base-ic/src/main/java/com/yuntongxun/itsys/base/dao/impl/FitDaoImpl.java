package com.yuntongxun.itsys.base.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.yuntongxun.itsys.base.po.FitSynLogPO;
import com.yuntongxun.itsys.base.po.HospitalFitResult;

import com.yuntongxun.itsys.base.po.dto.ExeclData;
import com.yuntongxun.itsys.base.vo.HospitalBiologicalSampleResultVo;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.dao.FitDao;
import com.yuntongxun.itsys.base.vo.FitResultVo;

@Repository
public class FitDaoImpl implements FitDao {

    @Autowired
    private JdbcTemplate jdbctemp;

    private final Logger log = LogManager.getLogger(FitDaoImpl.class);

    public final static int DEPARTMENT_TYPE_COMMUNITY = 1;
    public final static int DEPARTMENT_TYPE_AREA = 2;
    public final static int DEPARTMENT_TYPE_NATION = 3;


    @Override
    public ListPageUtil query(FitResultVo queryCondition, int communityDeptId, int areaDeptId, int deptType, boolean isPage) {
        List<Object> parm = new ArrayList<Object>();
        String sql = "select t2.path_url as pathUrl,t2.apply_status as applyStatus,t2.edit_status as editStatus,t2.approval_status as approvalStatus,t2.`id` as `id`, t1.sid,t1.`name`,u.nickName,t1.phone,"
                + " case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C'   when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END 'group',"
                + "t1.overall_status_cy as overallStatusCy,"
                + "t2.code_entry_status as codeEntryStatus,t2.fit_code as fitCode,t2.release_date as releaseDate,"
                + "t2.result_status as resultStatus,t2.result,t2.result_date as resultDate,t2.insert_status as insertStatus "
                + " from hospital_intestine_review t1,hospital_fit_result t2 ,itsys_user u where t1.sid = t2.sid  AND t1.create_user = u.loginName and t1.overall_status_cy <> 2 ";
        if (DEPARTMENT_TYPE_COMMUNITY == deptType) {
            sql += " and t2.community_dept_id = ?";
            parm.add(communityDeptId);
        } else if (DEPARTMENT_TYPE_AREA == deptType) {
            sql += " and t2.area_dept_id = ?";
            parm.add(areaDeptId);
        } else if (DEPARTMENT_TYPE_NATION == deptType) {
            //国家不用加部门过滤条件，查看所有的数据
        }

        if(!StringUtil.isEmpty(queryCondition.getFitCode())){
            sql += " and t2.fit_code like ? ";
            parm.add("%" +queryCondition.getFitCode()+ "%");
        }

        if (!StringUtil.isEmpty(queryCondition.getSid())) {
            sql += " and t1.sid like ?";
            parm.add("%" + queryCondition.getSid() + "%");
        }
        if (!StringUtil.isEmpty(queryCondition.getName())) {
            sql += " and t1.`name` like ? ";
            parm.add("%" + queryCondition.getName() + "%");
        }
        if (!StringUtil.isEmpty(queryCondition.getPhone())) {
            sql += " and t1.`phone` like ? ";
            parm.add("%" + queryCondition.getPhone() + "%");
        }
        if (!StringUtil.isEmpty(queryCondition.getGroup())) {
            if (queryCondition.getGroup().equals("Cg")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                parm.add(2);
            } else if (queryCondition.getGroup().equals("Cd")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                parm.add(1);
            } else {
                sql += " and t1.`group` = ? ";
                parm.add(queryCondition.getGroup());
            }
        }
        if (queryCondition.getCodeEntryStatus() != null) {
            sql += " and t2.code_entry_status = ? ";
            parm.add(queryCondition.getCodeEntryStatus());
        }
        if (queryCondition.getInsertStatus() != null) {
            sql += " and t2.insert_status = ? ";
            parm.add(queryCondition.getInsertStatus());
        }
        if (queryCondition.getResult() != null) {
            sql += " and t2.result = ? ";
            parm.add(queryCondition.getResult());
        }
        if (!StringUtil.isEmpty(queryCondition.getLoginName())) {
            sql += " and u.loginName = ?";
            parm.add(queryCondition.getLoginName());
        }
        sql += " order by t2.date_created desc ";

        if (isPage) {
            ListPageUtil listPage = new ListPageUtil(sql, parm.toArray(), queryCondition.getPageNo(), queryCondition.getPageSize(), jdbctemp, null);
            return listPage;
        } else {
            ListPageUtil listPageNoPaging = new ListPageUtil(sql, parm.toArray(), 1, -1, jdbctemp, null);
            return listPageNoPaging;
        }
    }

    @Override
    public void updateFitCode(FitResultVo vo) {
        String sql = "update hospital_fit_result set fit_code=?,release_date=?,release_person_code=?,code_entry_status=?,update_time=now(),insert_status=?,code_entry_time=now() where `id`=?";
        jdbctemp.update(sql, new Object[]{vo.getFitCode(), vo.getReleaseDateForSql(), vo.getReleasePersonCode(), vo.getCodeEntryStatus(), vo.getInsertStatus(), vo.getId()});
    }

    @Override
    public void updateFitResult(FitResultVo vo) {
        String sql = "update hospital_fit_result set path_url=?,result_status=?,result_date=?,up_line_value=?,down_line_value=?,result=?,no_reson_result=?,insert_status=?,update_time=now(),result_sys_date=now(),in_ten_min=? where `id`=?";
        jdbctemp.update(sql, new Object[]{vo.getPathUrl(),vo.getResultStatus(), vo.getResultDateForSql(), vo.getUpLineValue(), vo.getDownLineValue(), vo.getResult(), vo.getNoResonResult(), vo.getInsertStatus(), vo.getInTenMin(), vo.getId()});
    }

    @Override
    public ListPageUtil queryArea(FitResultVo queryCondition, Integer communityDeptId, Integer areaDeptId,
                                  Integer hospitalType, boolean isPage) {
        List<Object> parm = new ArrayList<Object>();
        String sql = "select t2.path_url as pathUrl,t2.apply_status as applyStatus,t2.edit_status as editStatus,t2.approval_status as approvalStatus,d.`name` as depName,t2.`id` as `id`, t1.sid,t1.`name`,u.nickName,t1.phone,"
                + " case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C'   when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END 'group',"
                + "t1.overall_status_cy as overallStatusCy,t2.code_entry_status as codeEntryStatus,"
                + "t2.fit_code as fitCode,t2.release_date as releaseDate,t2.result_status as resultStatus,"
                + "t2.result,t2.result_date as resultDate,t2.insert_status as insertStatus "
                + " from hospital_intestine_review t1,hospital_fit_result t2,itsys_department d , itsys_user u  where t1.sid = t2.sid and t2.community_dept_id=d.id  AND t1.create_user = u.loginName  and t1.overall_status_cy <> 2 ";
        sql += " and t2.area_dept_id = ?";
        parm.add(areaDeptId);
        if (queryCondition.getCommunityDeptId() != null) {
            sql += " and t2.community_dept_id = ?";
            parm.add(communityDeptId);
        }

        if(!StringUtil.isEmpty(queryCondition.getFitCode())){
            sql += " and t2.fit_code like ? ";
            parm.add("%" +queryCondition.getFitCode()+ "%");
        }

        if (!StringUtil.isEmpty(queryCondition.getSid())) {
            sql += " and t1.sid like ?";
            parm.add("%" + queryCondition.getSid() + "%");
        }
        if (!StringUtil.isEmpty(queryCondition.getName())) {
            sql += " and t1.`name` like ? ";
            parm.add("%" + queryCondition.getName() + "%");
        }
        if (!StringUtil.isEmpty(queryCondition.getPhone())) {
            sql += " and t1.`phone` like ? ";
            parm.add("%" + queryCondition.getPhone() + "%");
        }
        if (!StringUtil.isEmpty(queryCondition.getGroup())) {
            if (queryCondition.getGroup().equals("Cg")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                parm.add(2);
            } else if (queryCondition.getGroup().equals("Cd")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                parm.add(1);
            } else {
                sql += " and t1.`group` = ? ";
                parm.add(queryCondition.getGroup());
            }
        }
        if (queryCondition.getCodeEntryStatus() != null) {
            sql += " and t2.code_entry_status = ? ";
            parm.add(queryCondition.getCodeEntryStatus());
        }
        if (queryCondition.getInsertStatus() != null) {
            sql += " and t2.insert_status = ? ";
            parm.add(queryCondition.getInsertStatus());
        }
        if (queryCondition.getResult() != null) {
            sql += " and t2.result = ? ";
            parm.add(queryCondition.getResult());
        }
        if (!StringUtil.isEmpty(queryCondition.getLoginName())) {
            sql += " and u.loginName = ?";
            parm.add(queryCondition.getLoginName());
        }
        sql += " order by t2.date_created desc ";

        if (isPage) {
            ListPageUtil listPage = new ListPageUtil(sql, parm.toArray(), queryCondition.getPageNo(), queryCondition.getPageSize(), jdbctemp, null);
            return listPage;
        } else {
            ListPageUtil listPageNoPaging = new ListPageUtil(sql, parm.toArray(), 1, -1, jdbctemp, null);
            return listPageNoPaging;
        }
    }

    @Override
    public ListPageUtil queryCountry(FitResultVo queryCondition
            , boolean isPage) {
        List<Object> parm = new ArrayList<Object>();
        String sql = "select  t2.path_url as pathUrl,t2.apply_status as applyStatus,t2.edit_status as editStatus,t2.approval_status as approvalStatus,d2.`name`  as AreaName,d.`name` as depName,t2.`id` as `id`, t1.sid,t1.`name`,u.nickName,t1.phone, case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C'   when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END 'group',t1.overall_status_cy as overallStatusCy,t2.code_entry_status as codeEntryStatus,t2.fit_code as fitCode,t2.release_date as releaseDate,t2.result_status as resultStatus,t2.result,t2.result_date as resultDate,t2.insert_status as insertStatus from hospital_intestine_review t1,hospital_fit_result t2,itsys_department d,itsys_department d2 , itsys_user u where t1.sid = t2.sid  and t1.overall_status_cy <> 2  and t2.community_dept_id=d.id and t2.area_dept_id=d2.id AND t1.create_user = u.loginName";
        if (queryCondition.getAreaDeptId() != null) {
            sql += " and t2.area_dept_id = ?";
            parm.add(queryCondition.getAreaDeptId());
        }
        if (queryCondition.getCommunityDeptId() != null) {
            sql += " and t2.community_dept_id = ?";
            parm.add(queryCondition.getCommunityDeptId());
        }

        if(!StringUtil.isEmpty(queryCondition.getFitCode())){
            sql += " and t2.fit_code like ? ";
            parm.add("%" +queryCondition.getFitCode()+ "%");
        }

        if (!StringUtil.isEmpty(queryCondition.getSid())) {
            sql += " and t1.sid like ?";
            parm.add("%" + queryCondition.getSid() + "%");
        }
        if (!StringUtil.isEmpty(queryCondition.getName())) {
            sql += " and t1.`name` like ? ";
            parm.add("%" + queryCondition.getName() + "%");
        }
        if (!StringUtil.isEmpty(queryCondition.getPhone())) {
            sql += " and t1.`phone` like ? ";
            parm.add("%" + queryCondition.getPhone() + "%");
        }
/*        if (!StringUtil.isEmpty(queryCondition.getGroup())) {
            sql += " and t1.`group` = ? ";
            parm.add(queryCondition.getGroup());
        }*/
        if (queryCondition.getCodeEntryStatus() != null) {
            sql += " and t2.code_entry_status = ? ";
            parm.add(queryCondition.getCodeEntryStatus());
        }
        if (queryCondition.getInsertStatus() != null) {
            sql += " and t2.insert_status = ? ";
            parm.add(queryCondition.getInsertStatus());
        }
        if (queryCondition.getResult() != null) {
            sql += " and t2.result = ? ";
            parm.add(queryCondition.getResult());
        }
        if (!StringUtil.isEmpty(queryCondition.getGroup())) {
            if (queryCondition.getGroup().equals("Cg")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                parm.add(2);
            } else if (queryCondition.getGroup().equals("Cd")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                parm.add(1);
            } else {
                sql += " and t1.`group` = ? ";
                parm.add(queryCondition.getGroup());
            }
        }

        if (!StringUtil.isEmpty(queryCondition.getLoginName())) {
            sql += " and u.loginName = ?";
            parm.add(queryCondition.getLoginName());
        }
        sql += " order by t2.date_created desc ";

        if (isPage) {
            ListPageUtil listPage = new ListPageUtil(sql, parm.toArray(), queryCondition.getPageNo(), queryCondition.getPageSize(), jdbctemp, null);
            return listPage;
        } else {
            ListPageUtil listPageNoPaging = new ListPageUtil(sql, parm.toArray(), 1, -1, jdbctemp, null);
            return listPageNoPaging;
        }
    }

    @Override
    public Integer addFit(FitResultVo vo) {
        String sql = "insert into hospital_fit_result(insert_status,sid,fit_code,stage,release_date,release_person_code,code_entry_status,area_dept_id,community_dept_id,operation_source,code_entry_time,date_created,update_time,editoperation_source,operation_source_id) values(?,?,?,?,?,?,?,?,?,?,now(),now(),now(),?,?)";
        return jdbctemp.update(sql, new Object[]{vo.getInsertStatus(), vo.getSid(), vo.getFitCode(), vo.getStage(), vo.getReleaseDateForSql(), vo.getReleasePersonCode(), vo.getCodeEntryStatus(), vo.getAreaDeptId(), vo.getCommunityDeptId(), vo.getOperationSource(),vo.getEditoperationSource(),vo.getOperationSourceId()});
    }

    @Override
    public FitResultVo queryByFit_code(String fitCode) {

        FitResultVo result;
        try {
            String sql = "select * from hospital_fit_result where fit_code=? ";
            result = jdbctemp.queryForObject(sql, new BeanPropertyRowMapper<FitResultVo>(FitResultVo.class), fitCode);
        } catch (EmptyResultDataAccessException e) {
            log.info("queryByFit_code hospital_fit_result is error data is null");
            return null;
        } catch (IncorrectResultSizeDataAccessException e) {
            log.info("queryByFit_code hospital_fit_result is error data size is >1");
            return new FitResultVo();
        }
        return result;
    }

    @Override
    public FitResultVo queryById(Integer id) {
        FitResultVo result;
        try {
            String sql = "select * from hospital_fit_result where id=?";
            result = jdbctemp.queryForObject(sql, new BeanPropertyRowMapper<FitResultVo>(FitResultVo.class), id);
        } catch (EmptyResultDataAccessException e) {
            log.info("queryByFit_code hospital_fit_result is error data is null");
            return null;
        } catch (IncorrectResultSizeDataAccessException e) {
            log.info("queryByFit_code hospital_fit_result is error data size is >1");
            return null;
        }
        return result;
    }

    /**
     * 获取用户某阶段最新的fit信息
     *
     * @param sid
     * @param stage
     * @return
     */
    @Override
    public FitResultVo queryLatestFitInfo(String sid, Integer stage) {
        FitResultVo result;
        try {
            String sql = "select * from hospital_fit_result where sid=? and stage=? and result is not null ORDER BY date_created DESC LIMIT 0,1";
            result = jdbctemp.queryForObject(sql, new BeanPropertyRowMapper<FitResultVo>(FitResultVo.class), new Object[]{sid, stage});
        } catch (EmptyResultDataAccessException e) {
            log.info("queryLatestFitInfo hospital_fit_result is error data is null");
            return null;
        } catch (IncorrectResultSizeDataAccessException e) {
            log.info("queryLatestFitInfo hospital_fit_result is error data size is >1");
            return null;
        }
        return result;
    }

    /**
     * 同步fit信息，获取最新的fit信息
     *
     * @param sid
     * @return
     */
    @Override
    public FitResultVo queryLatestFitInfoForSynResult(String sid) {
        FitResultVo result;
        try {
            String sql = "select * from hospital_fit_result where sid=? and code_entry_status=2 ORDER BY code_entry_time DESC LIMIT 0,1";
            result = jdbctemp.queryForObject(sql, new BeanPropertyRowMapper<FitResultVo>(FitResultVo.class), new Object[]{sid});
        } catch (EmptyResultDataAccessException e) {
            log.info("queryLatestFitInfoForSynResult is error data is null");
            return null;
        }
        return result;
    }

    /**
     * 第三方同步fit结果记录日志
     *
     * @param fitSynLogPO
     * @return
     */
    @Override
    public int addFitSynLog(final FitSynLogPO fitSynLogPO) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql = "INSERT INTO hospital_fit_syn_result_log(fit_id,param_value,result,result_code,result_cont,date_created,update_time) values(?,?,?,?,?,now(),now())";
        jdbctemp.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, fitSynLogPO.getFitId());
                ps.setObject(2, fitSynLogPO.getParamValue());
                ps.setObject(3, fitSynLogPO.getResult());
                ps.setObject(4, fitSynLogPO.getResult_code());
                ps.setObject(5, fitSynLogPO.getResult_cont());
                return ps;
            }

        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void updateEditFitResult(FitResultVo vo, boolean isArea) {
        if (isArea) {
            String sql = "update hospital_fit_result set path_url=?,result_status=?,result_date=?,up_line_value=?,down_line_value=?,result=?,no_reson_result=?,insert_status=?,update_time=now(),result_sys_date=now(),in_ten_min=?,fit_code=?,release_date=?,release_person_code=? where `id`=?";
            jdbctemp.update(sql, new Object[]{vo.getPathUrl(),vo.getResultStatus(), vo.getResultDateForSql(), vo.getUpLineValue(), vo.getDownLineValue(), vo.getResult(), vo.getNoResonResult(), vo.getInsertStatus(), vo.getInTenMin(),vo.getFitCode(),vo.getReleaseDate(),vo.getReleasePersonCode() ,vo.getId()});
        } else {
            String sql = "update hospital_fit_result set path_url=?,result_status=?,result_date=?,up_line_value=?,down_line_value=?,result=?,no_reson_result=?,insert_status=?,update_time=now(),result_sys_date=now(),in_ten_min=?,apply_status=?,edit_status=?,approval_status=?,fit_code=?,release_date=?,release_person_code=? where `id`=?";
            jdbctemp.update(sql, new Object[]{vo.getPathUrl(),vo.getResultStatus(), vo.getResultDateForSql(), vo.getUpLineValue(), vo.getDownLineValue(), vo.getResult(), vo.getNoResonResult(), vo.getInsertStatus(), vo.getInTenMin(), vo.getApplyStatus(), vo.getEditStatus(), vo.getApprovalStatus(),vo.getFitCode(),vo.getReleaseDate(),vo.getReleasePersonCode(), vo.getId()});
        }
    }

    @Override
    public Integer updateUrlByID(String filePath,Integer id) {
        log.info("@Dao updateUrlByID 更新fit上传地址：参数 sid={},危险等级={} ", id);
        String sql = "UPDATE hospital_fit_result SET path_url=?,update_time=now() WHERE id = ?";
        int result = jdbctemp.update(sql,filePath,id);
        log.info("@Dao updateUrlByID End  ");
        return result;
    }

    @Override
    public List<FitResultVo> queryCountryExecl(ExeclData execlData) {
        List<Object> parm = new ArrayList<Object>();
        StringBuffer sql=new StringBuffer();
        //(@row_number:=@row_number + 1) AS num  (SELECT @row_number:=0) AS t
        //zl添加
        sql.append("SELECT " +
                " (@row_number :=@row_number + 1) AS number, " +
                " ss.* " +
                " FROM" +
                " (" +
                "SELECT t2.community_dept_id as communityDeptId,t2.area_dept_id as areaDeptId,t2.no_reson_result as noResonResult,t2.in_ten_min as inTenMin,t2.down_line_value as downLineValue,t2.up_line_value as upLineValue,d2.`name`  as areaName,d.`name` as depName,t2.`id` as `id`, t1.sid,t1.`name`,u.nickName,t1.phone, case when t1.`group`= 'A' then 'A组' when t1.`group` = 'B'  then 'B组' when t1.`group` = 'C' and t1.risk_level is null then 'C组'   when t1.`group` = 'C' and t1.risk_level = 1 then 'C组低危' when t1.`group` = 'C' and  t1.risk_level = 2 then 'C组高危' ELSE NULL END 'group',t1.overall_status_cy as overallStatusCy,t2.code_entry_status as codeEntryStatus,t2.fit_code as fitCode,t2.release_date as releaseDate,t2.result_status as resultStatus,t2.result,t2.result_date as resultDate,t2.insert_status as insertStatus from hospital_intestine_review t1,hospital_fit_result t2,itsys_department d,itsys_department d2 , itsys_user u where t1.sid = t2.sid  and t1.overall_status_cy <> 2  and t2.community_dept_id=d.id and t2.area_dept_id=d2.id AND t1.create_user = u.loginName");
        sql.append(" and t2.id in (select max(t.id) from hospital_fit_result t group by t.sid) ");
        if (execlData.getAreaDeptId() != null) {
            sql.append(" and t2.area_dept_id = ?");
            parm.add(execlData.getAreaDeptId());
        }
        if (execlData.getCommunityDeptId() != null) {
            sql.append(" and t2.community_dept_id = ? ");
            parm.add(execlData.getCommunityDeptId());
        }

        if (!StringUtil.isEmpty(execlData.getGroup())) {
            if (execlData.getGroup().equals("Cg")) {
                sql.append(" and t1.`group` = 'C' and t1.risk_level = ? ");
                parm.add(2);
            } else if (execlData.getGroup().equals("Cd")) {
                sql.append(" and t1.`group` = 'C' and t1.risk_level = ? ");
                parm.add(1);
            } else {
                sql.append(" and t1.`group` = ? ");
                parm.add(execlData.getGroup());
            }
        }
        if (execlData.getInGroupDateStart() != null) {
            sql.append(" and t1.in_group_date >= ? ");
            parm.add(execlData.getInGroupDateStart());
        }

        if (execlData.getInGroupDateEnd() != null) {
            sql.append(" and t1.in_group_date <= ? ");
            parm.add(execlData.getInGroupDateEnd());
        }

        if (!StringUtil.isEmpty(execlData.getLoginName())) {
            sql.append("  and u.loginName = ? ");
            parm.add(execlData.getLoginName());
        }


        if (execlData.getSiteId()!=null) {
            sql.append("  and t1.site_id = ? ");
            parm.add(execlData.getSiteId());
        }

        if (execlData.getStage_cy() != null) {
            sql.append(" and t1.stage_cy = ? ");
            parm.add(execlData.getStage_cy());
        }

        //zl添加  入组开始时间
        if (execlData.getInGroupDateStart() != null) {
            sql.append(" AND t1.in_group_date >= ? ");
            parm.add(execlData.getInGroupDateStart());
        }
        //zl添加  入组结束时间
        if (execlData.getInGroupDateEnd() != null) {
            sql.append(" AND t1.in_group_date <= ? ");
            parm.add(execlData.getInGroupDateEnd());
        }

        sql.append(" order by t2.date_created desc ");
        //zl添加
        sql.append(" ) AS ss," +
                " (SELECT @row_number := 0) AS tnum ");
        if(parm.size()>0){
            return jdbctemp.query(sql.toString(), new BeanPropertyRowMapper<FitResultVo>(FitResultVo.class),parm.toArray());
        }
        return jdbctemp.query(sql.toString(), new BeanPropertyRowMapper<FitResultVo>(FitResultVo.class));
    }

}
