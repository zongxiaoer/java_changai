package com.yuntongxun.itsys.base.po.cancer;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class HospitalCancerDiagnosePOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public HospitalCancerDiagnosePOExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSidIsNull() {
            addCriterion("sid is null");
            return (Criteria) this;
        }

        public Criteria andSidIsNotNull() {
            addCriterion("sid is not null");
            return (Criteria) this;
        }

        public Criteria andSidEqualTo(String value) {
            addCriterion("sid =", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidNotEqualTo(String value) {
            addCriterion("sid <>", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidGreaterThan(String value) {
            addCriterion("sid >", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidGreaterThanOrEqualTo(String value) {
            addCriterion("sid >=", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidLessThan(String value) {
            addCriterion("sid <", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidLessThanOrEqualTo(String value) {
            addCriterion("sid <=", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidLike(String value) {
            addCriterion("sid like", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidNotLike(String value) {
            addCriterion("sid not like", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidIn(List<String> values) {
            addCriterion("sid in", values, "sid");
            return (Criteria) this;
        }

        public Criteria andSidNotIn(List<String> values) {
            addCriterion("sid not in", values, "sid");
            return (Criteria) this;
        }

        public Criteria andSidBetween(String value1, String value2) {
            addCriterion("sid between", value1, value2, "sid");
            return (Criteria) this;
        }

        public Criteria andSidNotBetween(String value1, String value2) {
            addCriterion("sid not between", value1, value2, "sid");
            return (Criteria) this;
        }

        public Criteria andWriteTableDateIsNull() {
            addCriterion("write_table_date is null");
            return (Criteria) this;
        }

        public Criteria andWriteTableDateIsNotNull() {
            addCriterion("write_table_date is not null");
            return (Criteria) this;
        }

        public Criteria andWriteTableDateEqualTo(Date value) {
            addCriterionForJDBCDate("write_table_date =", value, "writeTableDate");
            return (Criteria) this;
        }

        public Criteria andWriteTableDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("write_table_date <>", value, "writeTableDate");
            return (Criteria) this;
        }

        public Criteria andWriteTableDateGreaterThan(Date value) {
            addCriterionForJDBCDate("write_table_date >", value, "writeTableDate");
            return (Criteria) this;
        }

        public Criteria andWriteTableDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("write_table_date >=", value, "writeTableDate");
            return (Criteria) this;
        }

        public Criteria andWriteTableDateLessThan(Date value) {
            addCriterionForJDBCDate("write_table_date <", value, "writeTableDate");
            return (Criteria) this;
        }

        public Criteria andWriteTableDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("write_table_date <=", value, "writeTableDate");
            return (Criteria) this;
        }

        public Criteria andWriteTableDateIn(List<Date> values) {
            addCriterionForJDBCDate("write_table_date in", values, "writeTableDate");
            return (Criteria) this;
        }

        public Criteria andWriteTableDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("write_table_date not in", values, "writeTableDate");
            return (Criteria) this;
        }

        public Criteria andWriteTableDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("write_table_date between", value1, value2, "writeTableDate");
            return (Criteria) this;
        }

        public Criteria andWriteTableDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("write_table_date not between", value1, value2, "writeTableDate");
            return (Criteria) this;
        }

        public Criteria andYsCancerReportDateIsNull() {
            addCriterion("ys_cancer_report_date is null");
            return (Criteria) this;
        }

        public Criteria andYsCancerReportDateIsNotNull() {
            addCriterion("ys_cancer_report_date is not null");
            return (Criteria) this;
        }

        public Criteria andYsCancerReportDateEqualTo(Date value) {
            addCriterionForJDBCDate("ys_cancer_report_date =", value, "ysCancerReportDate");
            return (Criteria) this;
        }

        public Criteria andYsCancerReportDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("ys_cancer_report_date <>", value, "ysCancerReportDate");
            return (Criteria) this;
        }

        public Criteria andYsCancerReportDateGreaterThan(Date value) {
            addCriterionForJDBCDate("ys_cancer_report_date >", value, "ysCancerReportDate");
            return (Criteria) this;
        }

        public Criteria andYsCancerReportDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ys_cancer_report_date >=", value, "ysCancerReportDate");
            return (Criteria) this;
        }

        public Criteria andYsCancerReportDateLessThan(Date value) {
            addCriterionForJDBCDate("ys_cancer_report_date <", value, "ysCancerReportDate");
            return (Criteria) this;
        }

        public Criteria andYsCancerReportDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ys_cancer_report_date <=", value, "ysCancerReportDate");
            return (Criteria) this;
        }

        public Criteria andYsCancerReportDateIn(List<Date> values) {
            addCriterionForJDBCDate("ys_cancer_report_date in", values, "ysCancerReportDate");
            return (Criteria) this;
        }

        public Criteria andYsCancerReportDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("ys_cancer_report_date not in", values, "ysCancerReportDate");
            return (Criteria) this;
        }

        public Criteria andYsCancerReportDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ys_cancer_report_date between", value1, value2, "ysCancerReportDate");
            return (Criteria) this;
        }

        public Criteria andYsCancerReportDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ys_cancer_report_date not between", value1, value2, "ysCancerReportDate");
            return (Criteria) this;
        }

        public Criteria andCheckYearsIsNull() {
            addCriterion("check_years is null");
            return (Criteria) this;
        }

        public Criteria andCheckYearsIsNotNull() {
            addCriterion("check_years is not null");
            return (Criteria) this;
        }

        public Criteria andCheckYearsEqualTo(String value) {
            addCriterion("check_years =", value, "checkYears");
            return (Criteria) this;
        }

        public Criteria andCheckYearsNotEqualTo(String value) {
            addCriterion("check_years <>", value, "checkYears");
            return (Criteria) this;
        }

        public Criteria andCheckYearsGreaterThan(String value) {
            addCriterion("check_years >", value, "checkYears");
            return (Criteria) this;
        }

        public Criteria andCheckYearsGreaterThanOrEqualTo(String value) {
            addCriterion("check_years >=", value, "checkYears");
            return (Criteria) this;
        }

        public Criteria andCheckYearsLessThan(String value) {
            addCriterion("check_years <", value, "checkYears");
            return (Criteria) this;
        }

        public Criteria andCheckYearsLessThanOrEqualTo(String value) {
            addCriterion("check_years <=", value, "checkYears");
            return (Criteria) this;
        }

        public Criteria andCheckYearsLike(String value) {
            addCriterion("check_years like", value, "checkYears");
            return (Criteria) this;
        }

        public Criteria andCheckYearsNotLike(String value) {
            addCriterion("check_years not like", value, "checkYears");
            return (Criteria) this;
        }

        public Criteria andCheckYearsIn(List<String> values) {
            addCriterion("check_years in", values, "checkYears");
            return (Criteria) this;
        }

        public Criteria andCheckYearsNotIn(List<String> values) {
            addCriterion("check_years not in", values, "checkYears");
            return (Criteria) this;
        }

        public Criteria andCheckYearsBetween(String value1, String value2) {
            addCriterion("check_years between", value1, value2, "checkYears");
            return (Criteria) this;
        }

        public Criteria andCheckYearsNotBetween(String value1, String value2) {
            addCriterion("check_years not between", value1, value2, "checkYears");
            return (Criteria) this;
        }

        public Criteria andInvestigatorCodeIsNull() {
            addCriterion("investigator_code is null");
            return (Criteria) this;
        }

        public Criteria andInvestigatorCodeIsNotNull() {
            addCriterion("investigator_code is not null");
            return (Criteria) this;
        }

        public Criteria andInvestigatorCodeEqualTo(String value) {
            addCriterion("investigator_code =", value, "investigatorCode");
            return (Criteria) this;
        }

        public Criteria andInvestigatorCodeNotEqualTo(String value) {
            addCriterion("investigator_code <>", value, "investigatorCode");
            return (Criteria) this;
        }

        public Criteria andInvestigatorCodeGreaterThan(String value) {
            addCriterion("investigator_code >", value, "investigatorCode");
            return (Criteria) this;
        }

        public Criteria andInvestigatorCodeGreaterThanOrEqualTo(String value) {
            addCriterion("investigator_code >=", value, "investigatorCode");
            return (Criteria) this;
        }

        public Criteria andInvestigatorCodeLessThan(String value) {
            addCriterion("investigator_code <", value, "investigatorCode");
            return (Criteria) this;
        }

        public Criteria andInvestigatorCodeLessThanOrEqualTo(String value) {
            addCriterion("investigator_code <=", value, "investigatorCode");
            return (Criteria) this;
        }

        public Criteria andInvestigatorCodeLike(String value) {
            addCriterion("investigator_code like", value, "investigatorCode");
            return (Criteria) this;
        }

        public Criteria andInvestigatorCodeNotLike(String value) {
            addCriterion("investigator_code not like", value, "investigatorCode");
            return (Criteria) this;
        }

        public Criteria andInvestigatorCodeIn(List<String> values) {
            addCriterion("investigator_code in", values, "investigatorCode");
            return (Criteria) this;
        }

        public Criteria andInvestigatorCodeNotIn(List<String> values) {
            addCriterion("investigator_code not in", values, "investigatorCode");
            return (Criteria) this;
        }

        public Criteria andInvestigatorCodeBetween(String value1, String value2) {
            addCriterion("investigator_code between", value1, value2, "investigatorCode");
            return (Criteria) this;
        }

        public Criteria andInvestigatorCodeNotBetween(String value1, String value2) {
            addCriterion("investigator_code not between", value1, value2, "investigatorCode");
            return (Criteria) this;
        }

        public Criteria andTbTablePersonIsNull() {
            addCriterion("tb_table_person is null");
            return (Criteria) this;
        }

        public Criteria andTbTablePersonIsNotNull() {
            addCriterion("tb_table_person is not null");
            return (Criteria) this;
        }

        public Criteria andTbTablePersonEqualTo(String value) {
            addCriterion("tb_table_person =", value, "tbTablePerson");
            return (Criteria) this;
        }

        public Criteria andTbTablePersonNotEqualTo(String value) {
            addCriterion("tb_table_person <>", value, "tbTablePerson");
            return (Criteria) this;
        }

        public Criteria andTbTablePersonGreaterThan(String value) {
            addCriterion("tb_table_person >", value, "tbTablePerson");
            return (Criteria) this;
        }

        public Criteria andTbTablePersonGreaterThanOrEqualTo(String value) {
            addCriterion("tb_table_person >=", value, "tbTablePerson");
            return (Criteria) this;
        }

        public Criteria andTbTablePersonLessThan(String value) {
            addCriterion("tb_table_person <", value, "tbTablePerson");
            return (Criteria) this;
        }

        public Criteria andTbTablePersonLessThanOrEqualTo(String value) {
            addCriterion("tb_table_person <=", value, "tbTablePerson");
            return (Criteria) this;
        }

        public Criteria andTbTablePersonLike(String value) {
            addCriterion("tb_table_person like", value, "tbTablePerson");
            return (Criteria) this;
        }

        public Criteria andTbTablePersonNotLike(String value) {
            addCriterion("tb_table_person not like", value, "tbTablePerson");
            return (Criteria) this;
        }

        public Criteria andTbTablePersonIn(List<String> values) {
            addCriterion("tb_table_person in", values, "tbTablePerson");
            return (Criteria) this;
        }

        public Criteria andTbTablePersonNotIn(List<String> values) {
            addCriterion("tb_table_person not in", values, "tbTablePerson");
            return (Criteria) this;
        }

        public Criteria andTbTablePersonBetween(String value1, String value2) {
            addCriterion("tb_table_person between", value1, value2, "tbTablePerson");
            return (Criteria) this;
        }

        public Criteria andTbTablePersonNotBetween(String value1, String value2) {
            addCriterion("tb_table_person not between", value1, value2, "tbTablePerson");
            return (Criteria) this;
        }

        public Criteria andQualityControlPersonIsNull() {
            addCriterion("quality_control_person is null");
            return (Criteria) this;
        }

        public Criteria andQualityControlPersonIsNotNull() {
            addCriterion("quality_control_person is not null");
            return (Criteria) this;
        }

        public Criteria andQualityControlPersonEqualTo(String value) {
            addCriterion("quality_control_person =", value, "qualityControlPerson");
            return (Criteria) this;
        }

        public Criteria andQualityControlPersonNotEqualTo(String value) {
            addCriterion("quality_control_person <>", value, "qualityControlPerson");
            return (Criteria) this;
        }

        public Criteria andQualityControlPersonGreaterThan(String value) {
            addCriterion("quality_control_person >", value, "qualityControlPerson");
            return (Criteria) this;
        }

        public Criteria andQualityControlPersonGreaterThanOrEqualTo(String value) {
            addCriterion("quality_control_person >=", value, "qualityControlPerson");
            return (Criteria) this;
        }

        public Criteria andQualityControlPersonLessThan(String value) {
            addCriterion("quality_control_person <", value, "qualityControlPerson");
            return (Criteria) this;
        }

        public Criteria andQualityControlPersonLessThanOrEqualTo(String value) {
            addCriterion("quality_control_person <=", value, "qualityControlPerson");
            return (Criteria) this;
        }

        public Criteria andQualityControlPersonLike(String value) {
            addCriterion("quality_control_person like", value, "qualityControlPerson");
            return (Criteria) this;
        }

        public Criteria andQualityControlPersonNotLike(String value) {
            addCriterion("quality_control_person not like", value, "qualityControlPerson");
            return (Criteria) this;
        }

        public Criteria andQualityControlPersonIn(List<String> values) {
            addCriterion("quality_control_person in", values, "qualityControlPerson");
            return (Criteria) this;
        }

        public Criteria andQualityControlPersonNotIn(List<String> values) {
            addCriterion("quality_control_person not in", values, "qualityControlPerson");
            return (Criteria) this;
        }

        public Criteria andQualityControlPersonBetween(String value1, String value2) {
            addCriterion("quality_control_person between", value1, value2, "qualityControlPerson");
            return (Criteria) this;
        }

        public Criteria andQualityControlPersonNotBetween(String value1, String value2) {
            addCriterion("quality_control_person not between", value1, value2, "qualityControlPerson");
            return (Criteria) this;
        }

        public Criteria andItem1IsNull() {
            addCriterion("item1 is null");
            return (Criteria) this;
        }

        public Criteria andItem1IsNotNull() {
            addCriterion("item1 is not null");
            return (Criteria) this;
        }

        public Criteria andItem1EqualTo(String value) {
            addCriterion("item1 =", value, "item1");
            return (Criteria) this;
        }

        public Criteria andItem1NotEqualTo(String value) {
            addCriterion("item1 <>", value, "item1");
            return (Criteria) this;
        }

        public Criteria andItem1GreaterThan(String value) {
            addCriterion("item1 >", value, "item1");
            return (Criteria) this;
        }

        public Criteria andItem1GreaterThanOrEqualTo(String value) {
            addCriterion("item1 >=", value, "item1");
            return (Criteria) this;
        }

        public Criteria andItem1LessThan(String value) {
            addCriterion("item1 <", value, "item1");
            return (Criteria) this;
        }

        public Criteria andItem1LessThanOrEqualTo(String value) {
            addCriterion("item1 <=", value, "item1");
            return (Criteria) this;
        }

        public Criteria andItem1Like(String value) {
            addCriterion("item1 like", value, "item1");
            return (Criteria) this;
        }

        public Criteria andItem1NotLike(String value) {
            addCriterion("item1 not like", value, "item1");
            return (Criteria) this;
        }

        public Criteria andItem1In(List<String> values) {
            addCriterion("item1 in", values, "item1");
            return (Criteria) this;
        }

        public Criteria andItem1NotIn(List<String> values) {
            addCriterion("item1 not in", values, "item1");
            return (Criteria) this;
        }

        public Criteria andItem1Between(String value1, String value2) {
            addCriterion("item1 between", value1, value2, "item1");
            return (Criteria) this;
        }

        public Criteria andItem1NotBetween(String value1, String value2) {
            addCriterion("item1 not between", value1, value2, "item1");
            return (Criteria) this;
        }

        public Criteria andItem21IsNull() {
            addCriterion("item2_1 is null");
            return (Criteria) this;
        }

        public Criteria andItem21IsNotNull() {
            addCriterion("item2_1 is not null");
            return (Criteria) this;
        }

        public Criteria andItem21EqualTo(Integer value) {
            addCriterion("item2_1 =", value, "item21");
            return (Criteria) this;
        }

        public Criteria andItem21NotEqualTo(Integer value) {
            addCriterion("item2_1 <>", value, "item21");
            return (Criteria) this;
        }

        public Criteria andItem21GreaterThan(Integer value) {
            addCriterion("item2_1 >", value, "item21");
            return (Criteria) this;
        }

        public Criteria andItem21GreaterThanOrEqualTo(Integer value) {
            addCriterion("item2_1 >=", value, "item21");
            return (Criteria) this;
        }

        public Criteria andItem21LessThan(Integer value) {
            addCriterion("item2_1 <", value, "item21");
            return (Criteria) this;
        }

        public Criteria andItem21LessThanOrEqualTo(Integer value) {
            addCriterion("item2_1 <=", value, "item21");
            return (Criteria) this;
        }

        public Criteria andItem21In(List<Integer> values) {
            addCriterion("item2_1 in", values, "item21");
            return (Criteria) this;
        }

        public Criteria andItem21NotIn(List<Integer> values) {
            addCriterion("item2_1 not in", values, "item21");
            return (Criteria) this;
        }

        public Criteria andItem21Between(Integer value1, Integer value2) {
            addCriterion("item2_1 between", value1, value2, "item21");
            return (Criteria) this;
        }

        public Criteria andItem21NotBetween(Integer value1, Integer value2) {
            addCriterion("item2_1 not between", value1, value2, "item21");
            return (Criteria) this;
        }

        public Criteria andItem22IsNull() {
            addCriterion("item2_2 is null");
            return (Criteria) this;
        }

        public Criteria andItem22IsNotNull() {
            addCriterion("item2_2 is not null");
            return (Criteria) this;
        }

        public Criteria andItem22EqualTo(Integer value) {
            addCriterion("item2_2 =", value, "item22");
            return (Criteria) this;
        }

        public Criteria andItem22NotEqualTo(Integer value) {
            addCriterion("item2_2 <>", value, "item22");
            return (Criteria) this;
        }

        public Criteria andItem22GreaterThan(Integer value) {
            addCriterion("item2_2 >", value, "item22");
            return (Criteria) this;
        }

        public Criteria andItem22GreaterThanOrEqualTo(Integer value) {
            addCriterion("item2_2 >=", value, "item22");
            return (Criteria) this;
        }

        public Criteria andItem22LessThan(Integer value) {
            addCriterion("item2_2 <", value, "item22");
            return (Criteria) this;
        }

        public Criteria andItem22LessThanOrEqualTo(Integer value) {
            addCriterion("item2_2 <=", value, "item22");
            return (Criteria) this;
        }

        public Criteria andItem22In(List<Integer> values) {
            addCriterion("item2_2 in", values, "item22");
            return (Criteria) this;
        }

        public Criteria andItem22NotIn(List<Integer> values) {
            addCriterion("item2_2 not in", values, "item22");
            return (Criteria) this;
        }

        public Criteria andItem22Between(Integer value1, Integer value2) {
            addCriterion("item2_2 between", value1, value2, "item22");
            return (Criteria) this;
        }

        public Criteria andItem22NotBetween(Integer value1, Integer value2) {
            addCriterion("item2_2 not between", value1, value2, "item22");
            return (Criteria) this;
        }

        public Criteria andItem3IsNull() {
            addCriterion("item3 is null");
            return (Criteria) this;
        }

        public Criteria andItem3IsNotNull() {
            addCriterion("item3 is not null");
            return (Criteria) this;
        }

        public Criteria andItem3EqualTo(Integer value) {
            addCriterion("item3 =", value, "item3");
            return (Criteria) this;
        }

        public Criteria andItem3NotEqualTo(Integer value) {
            addCriterion("item3 <>", value, "item3");
            return (Criteria) this;
        }

        public Criteria andItem3GreaterThan(Integer value) {
            addCriterion("item3 >", value, "item3");
            return (Criteria) this;
        }

        public Criteria andItem3GreaterThanOrEqualTo(Integer value) {
            addCriterion("item3 >=", value, "item3");
            return (Criteria) this;
        }

        public Criteria andItem3LessThan(Integer value) {
            addCriterion("item3 <", value, "item3");
            return (Criteria) this;
        }

        public Criteria andItem3LessThanOrEqualTo(Integer value) {
            addCriterion("item3 <=", value, "item3");
            return (Criteria) this;
        }

        public Criteria andItem3In(List<Integer> values) {
            addCriterion("item3 in", values, "item3");
            return (Criteria) this;
        }

        public Criteria andItem3NotIn(List<Integer> values) {
            addCriterion("item3 not in", values, "item3");
            return (Criteria) this;
        }

        public Criteria andItem3Between(Integer value1, Integer value2) {
            addCriterion("item3 between", value1, value2, "item3");
            return (Criteria) this;
        }

        public Criteria andItem3NotBetween(Integer value1, Integer value2) {
            addCriterion("item3 not between", value1, value2, "item3");
            return (Criteria) this;
        }

        public Criteria andItem3aIsNull() {
            addCriterion("item3a is null");
            return (Criteria) this;
        }

        public Criteria andItem3aIsNotNull() {
            addCriterion("item3a is not null");
            return (Criteria) this;
        }

        public Criteria andItem3aEqualTo(Integer value) {
            addCriterion("item3a =", value, "item3a");
            return (Criteria) this;
        }

        public Criteria andItem3aNotEqualTo(Integer value) {
            addCriterion("item3a <>", value, "item3a");
            return (Criteria) this;
        }

        public Criteria andItem3aGreaterThan(Integer value) {
            addCriterion("item3a >", value, "item3a");
            return (Criteria) this;
        }

        public Criteria andItem3aGreaterThanOrEqualTo(Integer value) {
            addCriterion("item3a >=", value, "item3a");
            return (Criteria) this;
        }

        public Criteria andItem3aLessThan(Integer value) {
            addCriterion("item3a <", value, "item3a");
            return (Criteria) this;
        }

        public Criteria andItem3aLessThanOrEqualTo(Integer value) {
            addCriterion("item3a <=", value, "item3a");
            return (Criteria) this;
        }

        public Criteria andItem3aIn(List<Integer> values) {
            addCriterion("item3a in", values, "item3a");
            return (Criteria) this;
        }

        public Criteria andItem3aNotIn(List<Integer> values) {
            addCriterion("item3a not in", values, "item3a");
            return (Criteria) this;
        }

        public Criteria andItem3aBetween(Integer value1, Integer value2) {
            addCriterion("item3a between", value1, value2, "item3a");
            return (Criteria) this;
        }

        public Criteria andItem3aNotBetween(Integer value1, Integer value2) {
            addCriterion("item3a not between", value1, value2, "item3a");
            return (Criteria) this;
        }

        public Criteria andYfCancerDiagnosisDateIsNull() {
            addCriterion("yf_cancer_diagnosis_date is null");
            return (Criteria) this;
        }

        public Criteria andYfCancerDiagnosisDateIsNotNull() {
            addCriterion("yf_cancer_diagnosis_date is not null");
            return (Criteria) this;
        }

        public Criteria andYfCancerDiagnosisDateEqualTo(Date value) {
            addCriterionForJDBCDate("yf_cancer_diagnosis_date =", value, "yfCancerDiagnosisDate");
            return (Criteria) this;
        }

        public Criteria andYfCancerDiagnosisDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("yf_cancer_diagnosis_date <>", value, "yfCancerDiagnosisDate");
            return (Criteria) this;
        }

        public Criteria andYfCancerDiagnosisDateGreaterThan(Date value) {
            addCriterionForJDBCDate("yf_cancer_diagnosis_date >", value, "yfCancerDiagnosisDate");
            return (Criteria) this;
        }

        public Criteria andYfCancerDiagnosisDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("yf_cancer_diagnosis_date >=", value, "yfCancerDiagnosisDate");
            return (Criteria) this;
        }

        public Criteria andYfCancerDiagnosisDateLessThan(Date value) {
            addCriterionForJDBCDate("yf_cancer_diagnosis_date <", value, "yfCancerDiagnosisDate");
            return (Criteria) this;
        }

        public Criteria andYfCancerDiagnosisDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("yf_cancer_diagnosis_date <=", value, "yfCancerDiagnosisDate");
            return (Criteria) this;
        }

        public Criteria andYfCancerDiagnosisDateIn(List<Date> values) {
            addCriterionForJDBCDate("yf_cancer_diagnosis_date in", values, "yfCancerDiagnosisDate");
            return (Criteria) this;
        }

        public Criteria andYfCancerDiagnosisDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("yf_cancer_diagnosis_date not in", values, "yfCancerDiagnosisDate");
            return (Criteria) this;
        }

        public Criteria andYfCancerDiagnosisDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("yf_cancer_diagnosis_date between", value1, value2, "yfCancerDiagnosisDate");
            return (Criteria) this;
        }

        public Criteria andYfCancerDiagnosisDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("yf_cancer_diagnosis_date not between", value1, value2, "yfCancerDiagnosisDate");
            return (Criteria) this;
        }

        public Criteria andItem51IsNull() {
            addCriterion("item5_1 is null");
            return (Criteria) this;
        }

        public Criteria andItem51IsNotNull() {
            addCriterion("item5_1 is not null");
            return (Criteria) this;
        }

        public Criteria andItem51EqualTo(String value) {
            addCriterion("item5_1 =", value, "item51");
            return (Criteria) this;
        }

        public Criteria andItem51NotEqualTo(String value) {
            addCriterion("item5_1 <>", value, "item51");
            return (Criteria) this;
        }

        public Criteria andItem51GreaterThan(String value) {
            addCriterion("item5_1 >", value, "item51");
            return (Criteria) this;
        }

        public Criteria andItem51GreaterThanOrEqualTo(String value) {
            addCriterion("item5_1 >=", value, "item51");
            return (Criteria) this;
        }

        public Criteria andItem51LessThan(String value) {
            addCriterion("item5_1 <", value, "item51");
            return (Criteria) this;
        }

        public Criteria andItem51LessThanOrEqualTo(String value) {
            addCriterion("item5_1 <=", value, "item51");
            return (Criteria) this;
        }

        public Criteria andItem51Like(String value) {
            addCriterion("item5_1 like", value, "item51");
            return (Criteria) this;
        }

        public Criteria andItem51NotLike(String value) {
            addCriterion("item5_1 not like", value, "item51");
            return (Criteria) this;
        }

        public Criteria andItem51In(List<String> values) {
            addCriterion("item5_1 in", values, "item51");
            return (Criteria) this;
        }

        public Criteria andItem51NotIn(List<String> values) {
            addCriterion("item5_1 not in", values, "item51");
            return (Criteria) this;
        }

        public Criteria andItem51Between(String value1, String value2) {
            addCriterion("item5_1 between", value1, value2, "item51");
            return (Criteria) this;
        }

        public Criteria andItem51NotBetween(String value1, String value2) {
            addCriterion("item5_1 not between", value1, value2, "item51");
            return (Criteria) this;
        }

        public Criteria andItem52IsNull() {
            addCriterion("item5_2 is null");
            return (Criteria) this;
        }

        public Criteria andItem52IsNotNull() {
            addCriterion("item5_2 is not null");
            return (Criteria) this;
        }

        public Criteria andItem52EqualTo(String value) {
            addCriterion("item5_2 =", value, "item52");
            return (Criteria) this;
        }

        public Criteria andItem52NotEqualTo(String value) {
            addCriterion("item5_2 <>", value, "item52");
            return (Criteria) this;
        }

        public Criteria andItem52GreaterThan(String value) {
            addCriterion("item5_2 >", value, "item52");
            return (Criteria) this;
        }

        public Criteria andItem52GreaterThanOrEqualTo(String value) {
            addCriterion("item5_2 >=", value, "item52");
            return (Criteria) this;
        }

        public Criteria andItem52LessThan(String value) {
            addCriterion("item5_2 <", value, "item52");
            return (Criteria) this;
        }

        public Criteria andItem52LessThanOrEqualTo(String value) {
            addCriterion("item5_2 <=", value, "item52");
            return (Criteria) this;
        }

        public Criteria andItem52Like(String value) {
            addCriterion("item5_2 like", value, "item52");
            return (Criteria) this;
        }

        public Criteria andItem52NotLike(String value) {
            addCriterion("item5_2 not like", value, "item52");
            return (Criteria) this;
        }

        public Criteria andItem52In(List<String> values) {
            addCriterion("item5_2 in", values, "item52");
            return (Criteria) this;
        }

        public Criteria andItem52NotIn(List<String> values) {
            addCriterion("item5_2 not in", values, "item52");
            return (Criteria) this;
        }

        public Criteria andItem52Between(String value1, String value2) {
            addCriterion("item5_2 between", value1, value2, "item52");
            return (Criteria) this;
        }

        public Criteria andItem52NotBetween(String value1, String value2) {
            addCriterion("item5_2 not between", value1, value2, "item52");
            return (Criteria) this;
        }

        public Criteria andItem53IsNull() {
            addCriterion("item5_3 is null");
            return (Criteria) this;
        }

        public Criteria andItem53IsNotNull() {
            addCriterion("item5_3 is not null");
            return (Criteria) this;
        }

        public Criteria andItem53EqualTo(String value) {
            addCriterion("item5_3 =", value, "item53");
            return (Criteria) this;
        }

        public Criteria andItem53NotEqualTo(String value) {
            addCriterion("item5_3 <>", value, "item53");
            return (Criteria) this;
        }

        public Criteria andItem53GreaterThan(String value) {
            addCriterion("item5_3 >", value, "item53");
            return (Criteria) this;
        }

        public Criteria andItem53GreaterThanOrEqualTo(String value) {
            addCriterion("item5_3 >=", value, "item53");
            return (Criteria) this;
        }

        public Criteria andItem53LessThan(String value) {
            addCriterion("item5_3 <", value, "item53");
            return (Criteria) this;
        }

        public Criteria andItem53LessThanOrEqualTo(String value) {
            addCriterion("item5_3 <=", value, "item53");
            return (Criteria) this;
        }

        public Criteria andItem53Like(String value) {
            addCriterion("item5_3 like", value, "item53");
            return (Criteria) this;
        }

        public Criteria andItem53NotLike(String value) {
            addCriterion("item5_3 not like", value, "item53");
            return (Criteria) this;
        }

        public Criteria andItem53In(List<String> values) {
            addCriterion("item5_3 in", values, "item53");
            return (Criteria) this;
        }

        public Criteria andItem53NotIn(List<String> values) {
            addCriterion("item5_3 not in", values, "item53");
            return (Criteria) this;
        }

        public Criteria andItem53Between(String value1, String value2) {
            addCriterion("item5_3 between", value1, value2, "item53");
            return (Criteria) this;
        }

        public Criteria andItem53NotBetween(String value1, String value2) {
            addCriterion("item5_3 not between", value1, value2, "item53");
            return (Criteria) this;
        }

        public Criteria andItem54IsNull() {
            addCriterion("item5_4 is null");
            return (Criteria) this;
        }

        public Criteria andItem54IsNotNull() {
            addCriterion("item5_4 is not null");
            return (Criteria) this;
        }

        public Criteria andItem54EqualTo(String value) {
            addCriterion("item5_4 =", value, "item54");
            return (Criteria) this;
        }

        public Criteria andItem54NotEqualTo(String value) {
            addCriterion("item5_4 <>", value, "item54");
            return (Criteria) this;
        }

        public Criteria andItem54GreaterThan(String value) {
            addCriterion("item5_4 >", value, "item54");
            return (Criteria) this;
        }

        public Criteria andItem54GreaterThanOrEqualTo(String value) {
            addCriterion("item5_4 >=", value, "item54");
            return (Criteria) this;
        }

        public Criteria andItem54LessThan(String value) {
            addCriterion("item5_4 <", value, "item54");
            return (Criteria) this;
        }

        public Criteria andItem54LessThanOrEqualTo(String value) {
            addCriterion("item5_4 <=", value, "item54");
            return (Criteria) this;
        }

        public Criteria andItem54Like(String value) {
            addCriterion("item5_4 like", value, "item54");
            return (Criteria) this;
        }

        public Criteria andItem54NotLike(String value) {
            addCriterion("item5_4 not like", value, "item54");
            return (Criteria) this;
        }

        public Criteria andItem54In(List<String> values) {
            addCriterion("item5_4 in", values, "item54");
            return (Criteria) this;
        }

        public Criteria andItem54NotIn(List<String> values) {
            addCriterion("item5_4 not in", values, "item54");
            return (Criteria) this;
        }

        public Criteria andItem54Between(String value1, String value2) {
            addCriterion("item5_4 between", value1, value2, "item54");
            return (Criteria) this;
        }

        public Criteria andItem54NotBetween(String value1, String value2) {
            addCriterion("item5_4 not between", value1, value2, "item54");
            return (Criteria) this;
        }

        public Criteria andItem55IsNull() {
            addCriterion("item5_5 is null");
            return (Criteria) this;
        }

        public Criteria andItem55IsNotNull() {
            addCriterion("item5_5 is not null");
            return (Criteria) this;
        }

        public Criteria andItem55EqualTo(String value) {
            addCriterion("item5_5 =", value, "item55");
            return (Criteria) this;
        }

        public Criteria andItem55NotEqualTo(String value) {
            addCriterion("item5_5 <>", value, "item55");
            return (Criteria) this;
        }

        public Criteria andItem55GreaterThan(String value) {
            addCriterion("item5_5 >", value, "item55");
            return (Criteria) this;
        }

        public Criteria andItem55GreaterThanOrEqualTo(String value) {
            addCriterion("item5_5 >=", value, "item55");
            return (Criteria) this;
        }

        public Criteria andItem55LessThan(String value) {
            addCriterion("item5_5 <", value, "item55");
            return (Criteria) this;
        }

        public Criteria andItem55LessThanOrEqualTo(String value) {
            addCriterion("item5_5 <=", value, "item55");
            return (Criteria) this;
        }

        public Criteria andItem55Like(String value) {
            addCriterion("item5_5 like", value, "item55");
            return (Criteria) this;
        }

        public Criteria andItem55NotLike(String value) {
            addCriterion("item5_5 not like", value, "item55");
            return (Criteria) this;
        }

        public Criteria andItem55In(List<String> values) {
            addCriterion("item5_5 in", values, "item55");
            return (Criteria) this;
        }

        public Criteria andItem55NotIn(List<String> values) {
            addCriterion("item5_5 not in", values, "item55");
            return (Criteria) this;
        }

        public Criteria andItem55Between(String value1, String value2) {
            addCriterion("item5_5 between", value1, value2, "item55");
            return (Criteria) this;
        }

        public Criteria andItem55NotBetween(String value1, String value2) {
            addCriterion("item5_5 not between", value1, value2, "item55");
            return (Criteria) this;
        }

        public Criteria andInformationZlPersonIsNull() {
            addCriterion("Information_zl_person is null");
            return (Criteria) this;
        }

        public Criteria andInformationZlPersonIsNotNull() {
            addCriterion("Information_zl_person is not null");
            return (Criteria) this;
        }

        public Criteria andInformationZlPersonEqualTo(String value) {
            addCriterion("Information_zl_person =", value, "informationZlPerson");
            return (Criteria) this;
        }

        public Criteria andInformationZlPersonNotEqualTo(String value) {
            addCriterion("Information_zl_person <>", value, "informationZlPerson");
            return (Criteria) this;
        }

        public Criteria andInformationZlPersonGreaterThan(String value) {
            addCriterion("Information_zl_person >", value, "informationZlPerson");
            return (Criteria) this;
        }

        public Criteria andInformationZlPersonGreaterThanOrEqualTo(String value) {
            addCriterion("Information_zl_person >=", value, "informationZlPerson");
            return (Criteria) this;
        }

        public Criteria andInformationZlPersonLessThan(String value) {
            addCriterion("Information_zl_person <", value, "informationZlPerson");
            return (Criteria) this;
        }

        public Criteria andInformationZlPersonLessThanOrEqualTo(String value) {
            addCriterion("Information_zl_person <=", value, "informationZlPerson");
            return (Criteria) this;
        }

        public Criteria andInformationZlPersonLike(String value) {
            addCriterion("Information_zl_person like", value, "informationZlPerson");
            return (Criteria) this;
        }

        public Criteria andInformationZlPersonNotLike(String value) {
            addCriterion("Information_zl_person not like", value, "informationZlPerson");
            return (Criteria) this;
        }

        public Criteria andInformationZlPersonIn(List<String> values) {
            addCriterion("Information_zl_person in", values, "informationZlPerson");
            return (Criteria) this;
        }

        public Criteria andInformationZlPersonNotIn(List<String> values) {
            addCriterion("Information_zl_person not in", values, "informationZlPerson");
            return (Criteria) this;
        }

        public Criteria andInformationZlPersonBetween(String value1, String value2) {
            addCriterion("Information_zl_person between", value1, value2, "informationZlPerson");
            return (Criteria) this;
        }

        public Criteria andInformationZlPersonNotBetween(String value1, String value2) {
            addCriterion("Information_zl_person not between", value1, value2, "informationZlPerson");
            return (Criteria) this;
        }

        public Criteria andDateCreatedIsNull() {
            addCriterion("date_created is null");
            return (Criteria) this;
        }

        public Criteria andDateCreatedIsNotNull() {
            addCriterion("date_created is not null");
            return (Criteria) this;
        }

        public Criteria andDateCreatedEqualTo(Date value) {
            addCriterion("date_created =", value, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedNotEqualTo(Date value) {
            addCriterion("date_created <>", value, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedGreaterThan(Date value) {
            addCriterion("date_created >", value, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedGreaterThanOrEqualTo(Date value) {
            addCriterion("date_created >=", value, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedLessThan(Date value) {
            addCriterion("date_created <", value, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedLessThanOrEqualTo(Date value) {
            addCriterion("date_created <=", value, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedIn(List<Date> values) {
            addCriterion("date_created in", values, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedNotIn(List<Date> values) {
            addCriterion("date_created not in", values, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedBetween(Date value1, Date value2) {
            addCriterion("date_created between", value1, value2, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedNotBetween(Date value1, Date value2) {
            addCriterion("date_created not between", value1, value2, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andDeptCodeIsNull() {
            addCriterion("dept_code is null");
            return (Criteria) this;
        }

        public Criteria andDeptCodeIsNotNull() {
            addCriterion("dept_code is not null");
            return (Criteria) this;
        }

        public Criteria andDeptCodeEqualTo(Integer value) {
            addCriterion("dept_code =", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeNotEqualTo(Integer value) {
            addCriterion("dept_code <>", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeGreaterThan(Integer value) {
            addCriterion("dept_code >", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("dept_code >=", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeLessThan(Integer value) {
            addCriterion("dept_code <", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeLessThanOrEqualTo(Integer value) {
            addCriterion("dept_code <=", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeIn(List<Integer> values) {
            addCriterion("dept_code in", values, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeNotIn(List<Integer> values) {
            addCriterion("dept_code not in", values, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeBetween(Integer value1, Integer value2) {
            addCriterion("dept_code between", value1, value2, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeNotBetween(Integer value1, Integer value2) {
            addCriterion("dept_code not between", value1, value2, "deptCode");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNull() {
            addCriterion("create_user is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("create_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(Integer value) {
            addCriterion("create_user =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(Integer value) {
            addCriterion("create_user <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(Integer value) {
            addCriterion("create_user >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_user >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(Integer value) {
            addCriterion("create_user <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(Integer value) {
            addCriterion("create_user <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<Integer> values) {
            addCriterion("create_user in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<Integer> values) {
            addCriterion("create_user not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(Integer value1, Integer value2) {
            addCriterion("create_user between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(Integer value1, Integer value2) {
            addCriterion("create_user not between", value1, value2, "createUser");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}