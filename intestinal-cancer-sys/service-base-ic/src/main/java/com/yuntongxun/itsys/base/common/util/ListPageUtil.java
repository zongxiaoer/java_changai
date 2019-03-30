package com.yuntongxun.itsys.base.common.util;

import java.util.List;
import java.util.Map;

import com.yuntongxun.itsys.base.common.constans.Constans;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.po.PageInfo;

public class ListPageUtil {

    private int rowCount;// 总条数
    private int currentPage;// 当前页
    private int pageSize;
    private int pageCount;// 页数
    private List resultList;
    private PageInfo pageInfo;// 分页数据

    private final Logger log = LogManager.getLogger(ListPageUtil.class.getName());

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List getResultList() {
        return resultList;
    }

    public void setResultList(List resultList) {
        this.resultList = resultList;
    }

    /**
     * 根据分页参数对查询结果进行分页
     *
     * @param list
     * @param pageNo
     * @param pageSize
     * @return
     */
    public static List getPageList(List list, int pageNo, int pageSize) {
        // ListPageUtil pg = new ListPageUtil(list, pageSize);
        // pg.setCurrentPage(pageNo);
        //
        // List resultList=new ArrayList(list);
        // if(pageSize!=-1){
        // if (pg != null && pageNo <= pg.getPageCount()) {
        // resultList = pg.getCurrentList();
        // }
        // }
        // PageInfo pageinfo=new PageInfo();
        // pageinfo.setPageNo(pageNo);
        // pageinfo.setPageSize(pageSize);
        // pageinfo.setTotalPageCount(pg.getPageCount());
        // pageinfo.setTotalRowCount(pg.getRowCount());
        // if(resultList!=null){
        // resultList.add(pageinfo);
        // }
        // return resultList;
        // return pageinfo;
        return null;
    }

    // public static final int NUMBERS_PER_PAGE = 10;
    // private int totalPages; // 总页数
    // private int page; // 当前页码
    // private List resultList; // 结果集存放List

    /**
     * 分页查询方法
     *
     * @param sql
     * @param currentPage
     * @param numPerPage
     * @param jTemplate
     * @throws ItSysException
     */
    public ListPageUtil(String sql, Object[] parm, int currentPage, int numPerPage, JdbcTemplate jTemplate,
                        Map<String, String> translateParm) throws ItSysException {
        if (jTemplate == null) {
            log.error("@ListPageUtil ERROR  jTemplate为空......");
            throw new IllegalArgumentException("jdbcTemplate 为空");
        } else if (sql == null || sql.equals("")) {
            log.error("@ListPageUtil ERROR  sql为空......");
            throw new IllegalArgumentException("sql为空");
        }

        String countSQL = getSQLCount(sql);
        log.info("@ListPageUtil 分页查询......查询总条数sql={}", countSQL);
        setCurrentPage(currentPage); // 设置当前页码
        setPageSize(numPerPage);
        setPageCountByRows(numPerPage, jTemplate.queryForObject(countSQL, parm, Integer.class)); // 设置总页数
        int startIndex = 1;
        String sqlLimitStr = "";
        if (numPerPage >= 1) {// 查询所有
            startIndex = (currentPage - 1) * numPerPage; // 数据读取起始index
            sqlLimitStr = " limit " + startIndex + "," + numPerPage;
        }
        this.pageInfo = new PageInfo(currentPage, numPerPage, this.pageCount, this.rowCount);
        StringBuffer paginationSQL = new StringBuffer(" ");
        paginationSQL.append(sql);
        paginationSQL.append(sqlLimitStr);
        log.info("@ListPageUtil 分页查询数据库SQL={},传入参数为={}", paginationSQL, parm);
        List queryList = jTemplate.queryForList(paginationSQL.toString(), parm);
        if (queryList != null && queryList.size() > 0) {
            if (translateParm != null) {
                for (Map.Entry<String, String> entry : translateParm.entrySet()) {

                    log.info("翻译参数为:  Key = {}, Value ={}", entry.getKey(), entry.getValue());
                    queryList = DictionaryUtil.translateList(queryList, entry.getKey(), entry.getValue());
                }
                log.info("翻译数据完成......");
            }
        }
        setResultList(queryList);
    }

    /**
     * 分页查询方法(兼容子查询和普通查询)
     *
     * @param sql
     * @param currentPage
     * @param numPerPage
     * @param jTemplate
     * @throws ItSysException
     */
    public ListPageUtil(String sql, Object[] parm, int currentPage, int numPerPage, JdbcTemplate jTemplate,
                        Map<String, String> translateParm,String status) throws ItSysException {
        if (jTemplate == null) {
            log.error("@ListPageUtil ERROR  jTemplate为空......");
            throw new IllegalArgumentException("jdbcTemplate 为空");
        } else if (sql == null || sql.equals("")) {
            log.error("@ListPageUtil ERROR  sql为空......");
            throw new IllegalArgumentException("sql为空");
        }
        String countSQL= getSQLCount(sql);
        if(Constans.SUBQUERY.equals(status)){
            countSQL= getSQLCountByChild(sql);
        }
        log.info("@ListPageUtil 分页查询......查询总条数sql={}", countSQL);
        setCurrentPage(currentPage); // 设置当前页码
        setPageSize(numPerPage);
        setPageCountByRows(numPerPage, jTemplate.queryForObject(countSQL, parm, Integer.class)); // 设置总页数
        int startIndex = 1;
        String sqlLimitStr = "";
        if (numPerPage >= 1) {// 查询所有
            startIndex = (currentPage - 1) * numPerPage; // 数据读取起始index
            sqlLimitStr = " limit " + startIndex + "," + numPerPage;
        }
        this.pageInfo = new PageInfo(currentPage, numPerPage, this.pageCount, this.rowCount);
        StringBuffer paginationSQL = new StringBuffer(" ");
        paginationSQL.append(sql);
        paginationSQL.append(sqlLimitStr);
        log.info("@ListPageUtil 分页查询数据库SQL={},传入参数为={}", paginationSQL, parm);
        List queryList = jTemplate.queryForList(paginationSQL.toString(), parm);
        if (queryList != null && queryList.size() > 0) {
            if (translateParm != null) {
                for (Map.Entry<String, String> entry : translateParm.entrySet()) {

                    log.info("翻译参数为:  Key = {}, Value ={}", entry.getKey(), entry.getValue());
                    queryList = DictionaryUtil.translateList(queryList, entry.getKey(), entry.getValue());
                }
                log.info("翻译数据完成......");
            }
        }
        setResultList(queryList);
    }

    public  String getSQLCount(String sql) {
        String sqlBak = sql.toLowerCase();
        String searchValue = " from ";
        String sqlCount = "select count(*) from "
                + sql.substring(sqlBak.indexOf(searchValue) + searchValue.length(), sqlBak.length());
        return sqlCount;
    }

    /**
     * @func
     * @desc   子查询count(*)
     * @author zongt
     * @create 2018/6/4 下下午3:55     * @request
     * @response
     **/
    public  String getSQLCountByChild(String sql) {
        String sqlBak = sql.toLowerCase();
        String searchValue = " from ";
        String sqlCount = "select count(*) from "
                + sql.substring(sqlBak.lastIndexOf(searchValue) + searchValue.length(), sqlBak.length());
        return sqlCount;
    }

    // 计算总页数
    public void setPageCountByRows(int numPerPage, int totalRows) {
        setRowCount(totalRows);
        if (numPerPage < 1) {// 查询所有--总页数为1
            this.pageCount = 1;
        } else {
            if (totalRows % numPerPage == 0) {
                this.pageCount = totalRows / numPerPage;
            } else {
                this.pageCount = (totalRows / numPerPage) + 1;
            }
        }
    }
    public static String getSQL(String sql) {
        String sqlBak = sql.toLowerCase();
        String searchValue = " from ";
        String sqlCount = "select count(*) from "
                + sql.substring(sqlBak.lastIndexOf(searchValue) + searchValue.length(), sqlBak.length());
        return sqlCount;
    }
    public static void main(String[] args) {
        String  sql="select t3.`name` as depName,t2.data_id as `id`,  t1.sid,t1.`name`,t1.sex,t1.age,t1.phone as cellPhone,t1.`group`,t1.in_group_date as inGroupDate,t1.overall_status_cy as overallStatusCy,t2.data_id as colonoscopyRecordId,t3.`name` as deptName from hospital_intestine_review t1,hospital_todo_event t2,itsys_department t3 where t1.sid = t2.sid  and t2.area_dept_id = t3.id ";
        String sql1="select (select name from itsys_department where id=t1.community_dept_id),t3.`name` as depName,t2.data_id as `id`,  t1.sid,t1.`name`,t1.sex,t1.age,t1.phone as cellPhone,t1.`group`,t1.in_group_date as inGroupDate,t1.overall_status_cy as overallStatusCy,t2.data_id as colonoscopyRecordId,t3.`name` as deptName from hospital_intestine_review t1,hospital_todo_event t2,itsys_department t3 where t1.sid = t2.sid  and t2.area_dept_id = t3.id ";
        String sqlCount = ListPageUtil.getSQL(sql1);
        System.out.println(sqlCount);
    }

}
