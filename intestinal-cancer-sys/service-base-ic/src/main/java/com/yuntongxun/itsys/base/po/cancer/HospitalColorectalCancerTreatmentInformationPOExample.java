package com.yuntongxun.itsys.base.po.cancer;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class HospitalColorectalCancerTreatmentInformationPOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public HospitalColorectalCancerTreatmentInformationPOExample() {
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

        public Criteria andTbTableDateIsNull() {
            addCriterion("tb_table_date is null");
            return (Criteria) this;
        }

        public Criteria andTbTableDateIsNotNull() {
            addCriterion("tb_table_date is not null");
            return (Criteria) this;
        }

        public Criteria andTbTableDateEqualTo(Date value) {
            addCriterionForJDBCDate("tb_table_date =", value, "tbTableDate");
            return (Criteria) this;
        }

        public Criteria andTbTableDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("tb_table_date <>", value, "tbTableDate");
            return (Criteria) this;
        }

        public Criteria andTbTableDateGreaterThan(Date value) {
            addCriterionForJDBCDate("tb_table_date >", value, "tbTableDate");
            return (Criteria) this;
        }

        public Criteria andTbTableDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("tb_table_date >=", value, "tbTableDate");
            return (Criteria) this;
        }

        public Criteria andTbTableDateLessThan(Date value) {
            addCriterionForJDBCDate("tb_table_date <", value, "tbTableDate");
            return (Criteria) this;
        }

        public Criteria andTbTableDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("tb_table_date <=", value, "tbTableDate");
            return (Criteria) this;
        }

        public Criteria andTbTableDateIn(List<Date> values) {
            addCriterionForJDBCDate("tb_table_date in", values, "tbTableDate");
            return (Criteria) this;
        }

        public Criteria andTbTableDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("tb_table_date not in", values, "tbTableDate");
            return (Criteria) this;
        }

        public Criteria andTbTableDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("tb_table_date between", value1, value2, "tbTableDate");
            return (Criteria) this;
        }

        public Criteria andTbTableDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("tb_table_date not between", value1, value2, "tbTableDate");
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

        public Criteria andDeptCodeEqualTo(String value) {
            addCriterion("dept_code =", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeNotEqualTo(String value) {
            addCriterion("dept_code <>", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeGreaterThan(String value) {
            addCriterion("dept_code >", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeGreaterThanOrEqualTo(String value) {
            addCriterion("dept_code >=", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeLessThan(String value) {
            addCriterion("dept_code <", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeLessThanOrEqualTo(String value) {
            addCriterion("dept_code <=", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeLike(String value) {
            addCriterion("dept_code like", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeNotLike(String value) {
            addCriterion("dept_code not like", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeIn(List<String> values) {
            addCriterion("dept_code in", values, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeNotIn(List<String> values) {
            addCriterion("dept_code not in", values, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeBetween(String value1, String value2) {
            addCriterion("dept_code between", value1, value2, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeNotBetween(String value1, String value2) {
            addCriterion("dept_code not between", value1, value2, "deptCode");
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

        public Criteria andItem1EqualTo(Integer value) {
            addCriterion("item1 =", value, "item1");
            return (Criteria) this;
        }

        public Criteria andItem1NotEqualTo(Integer value) {
            addCriterion("item1 <>", value, "item1");
            return (Criteria) this;
        }

        public Criteria andItem1GreaterThan(Integer value) {
            addCriterion("item1 >", value, "item1");
            return (Criteria) this;
        }

        public Criteria andItem1GreaterThanOrEqualTo(Integer value) {
            addCriterion("item1 >=", value, "item1");
            return (Criteria) this;
        }

        public Criteria andItem1LessThan(Integer value) {
            addCriterion("item1 <", value, "item1");
            return (Criteria) this;
        }

        public Criteria andItem1LessThanOrEqualTo(Integer value) {
            addCriterion("item1 <=", value, "item1");
            return (Criteria) this;
        }

        public Criteria andItem1In(List<Integer> values) {
            addCriterion("item1 in", values, "item1");
            return (Criteria) this;
        }

        public Criteria andItem1NotIn(List<Integer> values) {
            addCriterion("item1 not in", values, "item1");
            return (Criteria) this;
        }

        public Criteria andItem1Between(Integer value1, Integer value2) {
            addCriterion("item1 between", value1, value2, "item1");
            return (Criteria) this;
        }

        public Criteria andItem1NotBetween(Integer value1, Integer value2) {
            addCriterion("item1 not between", value1, value2, "item1");
            return (Criteria) this;
        }

        public Criteria andItem2IsNull() {
            addCriterion("item2 is null");
            return (Criteria) this;
        }

        public Criteria andItem2IsNotNull() {
            addCriterion("item2 is not null");
            return (Criteria) this;
        }

        public Criteria andItem2EqualTo(Integer value) {
            addCriterion("item2 =", value, "item2");
            return (Criteria) this;
        }

        public Criteria andItem2NotEqualTo(Integer value) {
            addCriterion("item2 <>", value, "item2");
            return (Criteria) this;
        }

        public Criteria andItem2GreaterThan(Integer value) {
            addCriterion("item2 >", value, "item2");
            return (Criteria) this;
        }

        public Criteria andItem2GreaterThanOrEqualTo(Integer value) {
            addCriterion("item2 >=", value, "item2");
            return (Criteria) this;
        }

        public Criteria andItem2LessThan(Integer value) {
            addCriterion("item2 <", value, "item2");
            return (Criteria) this;
        }

        public Criteria andItem2LessThanOrEqualTo(Integer value) {
            addCriterion("item2 <=", value, "item2");
            return (Criteria) this;
        }

        public Criteria andItem2In(List<Integer> values) {
            addCriterion("item2 in", values, "item2");
            return (Criteria) this;
        }

        public Criteria andItem2NotIn(List<Integer> values) {
            addCriterion("item2 not in", values, "item2");
            return (Criteria) this;
        }

        public Criteria andItem2Between(Integer value1, Integer value2) {
            addCriterion("item2 between", value1, value2, "item2");
            return (Criteria) this;
        }

        public Criteria andItem2NotBetween(Integer value1, Integer value2) {
            addCriterion("item2 not between", value1, value2, "item2");
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

        public Criteria andItem21EqualTo(String value) {
            addCriterion("item2_1 =", value, "item21");
            return (Criteria) this;
        }

        public Criteria andItem21NotEqualTo(String value) {
            addCriterion("item2_1 <>", value, "item21");
            return (Criteria) this;
        }

        public Criteria andItem21GreaterThan(String value) {
            addCriterion("item2_1 >", value, "item21");
            return (Criteria) this;
        }

        public Criteria andItem21GreaterThanOrEqualTo(String value) {
            addCriterion("item2_1 >=", value, "item21");
            return (Criteria) this;
        }

        public Criteria andItem21LessThan(String value) {
            addCriterion("item2_1 <", value, "item21");
            return (Criteria) this;
        }

        public Criteria andItem21LessThanOrEqualTo(String value) {
            addCriterion("item2_1 <=", value, "item21");
            return (Criteria) this;
        }

        public Criteria andItem21Like(String value) {
            addCriterion("item2_1 like", value, "item21");
            return (Criteria) this;
        }

        public Criteria andItem21NotLike(String value) {
            addCriterion("item2_1 not like", value, "item21");
            return (Criteria) this;
        }

        public Criteria andItem21In(List<String> values) {
            addCriterion("item2_1 in", values, "item21");
            return (Criteria) this;
        }

        public Criteria andItem21NotIn(List<String> values) {
            addCriterion("item2_1 not in", values, "item21");
            return (Criteria) this;
        }

        public Criteria andItem21Between(String value1, String value2) {
            addCriterion("item2_1 between", value1, value2, "item21");
            return (Criteria) this;
        }

        public Criteria andItem21NotBetween(String value1, String value2) {
            addCriterion("item2_1 not between", value1, value2, "item21");
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

        public Criteria andItem31IsNull() {
            addCriterion("item3_1 is null");
            return (Criteria) this;
        }

        public Criteria andItem31IsNotNull() {
            addCriterion("item3_1 is not null");
            return (Criteria) this;
        }

        public Criteria andItem31EqualTo(Date value) {
            addCriterionForJDBCDate("item3_1 =", value, "item31");
            return (Criteria) this;
        }

        public Criteria andItem31NotEqualTo(Date value) {
            addCriterionForJDBCDate("item3_1 <>", value, "item31");
            return (Criteria) this;
        }

        public Criteria andItem31GreaterThan(Date value) {
            addCriterionForJDBCDate("item3_1 >", value, "item31");
            return (Criteria) this;
        }

        public Criteria andItem31GreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("item3_1 >=", value, "item31");
            return (Criteria) this;
        }

        public Criteria andItem31LessThan(Date value) {
            addCriterionForJDBCDate("item3_1 <", value, "item31");
            return (Criteria) this;
        }

        public Criteria andItem31LessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("item3_1 <=", value, "item31");
            return (Criteria) this;
        }

        public Criteria andItem31In(List<Date> values) {
            addCriterionForJDBCDate("item3_1 in", values, "item31");
            return (Criteria) this;
        }

        public Criteria andItem31NotIn(List<Date> values) {
            addCriterionForJDBCDate("item3_1 not in", values, "item31");
            return (Criteria) this;
        }

        public Criteria andItem31Between(Date value1, Date value2) {
            addCriterionForJDBCDate("item3_1 between", value1, value2, "item31");
            return (Criteria) this;
        }

        public Criteria andItem31NotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("item3_1 not between", value1, value2, "item31");
            return (Criteria) this;
        }

        public Criteria andItem4IsNull() {
            addCriterion("item4 is null");
            return (Criteria) this;
        }

        public Criteria andItem4IsNotNull() {
            addCriterion("item4 is not null");
            return (Criteria) this;
        }

        public Criteria andItem4EqualTo(Integer value) {
            addCriterion("item4 =", value, "item4");
            return (Criteria) this;
        }

        public Criteria andItem4NotEqualTo(Integer value) {
            addCriterion("item4 <>", value, "item4");
            return (Criteria) this;
        }

        public Criteria andItem4GreaterThan(Integer value) {
            addCriterion("item4 >", value, "item4");
            return (Criteria) this;
        }

        public Criteria andItem4GreaterThanOrEqualTo(Integer value) {
            addCriterion("item4 >=", value, "item4");
            return (Criteria) this;
        }

        public Criteria andItem4LessThan(Integer value) {
            addCriterion("item4 <", value, "item4");
            return (Criteria) this;
        }

        public Criteria andItem4LessThanOrEqualTo(Integer value) {
            addCriterion("item4 <=", value, "item4");
            return (Criteria) this;
        }

        public Criteria andItem4In(List<Integer> values) {
            addCriterion("item4 in", values, "item4");
            return (Criteria) this;
        }

        public Criteria andItem4NotIn(List<Integer> values) {
            addCriterion("item4 not in", values, "item4");
            return (Criteria) this;
        }

        public Criteria andItem4Between(Integer value1, Integer value2) {
            addCriterion("item4 between", value1, value2, "item4");
            return (Criteria) this;
        }

        public Criteria andItem4NotBetween(Integer value1, Integer value2) {
            addCriterion("item4 not between", value1, value2, "item4");
            return (Criteria) this;
        }

        public Criteria andItem41IsNull() {
            addCriterion("item4_1 is null");
            return (Criteria) this;
        }

        public Criteria andItem41IsNotNull() {
            addCriterion("item4_1 is not null");
            return (Criteria) this;
        }

        public Criteria andItem41EqualTo(Date value) {
            addCriterionForJDBCDate("item4_1 =", value, "item41");
            return (Criteria) this;
        }

        public Criteria andItem41NotEqualTo(Date value) {
            addCriterionForJDBCDate("item4_1 <>", value, "item41");
            return (Criteria) this;
        }

        public Criteria andItem41GreaterThan(Date value) {
            addCriterionForJDBCDate("item4_1 >", value, "item41");
            return (Criteria) this;
        }

        public Criteria andItem41GreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("item4_1 >=", value, "item41");
            return (Criteria) this;
        }

        public Criteria andItem41LessThan(Date value) {
            addCriterionForJDBCDate("item4_1 <", value, "item41");
            return (Criteria) this;
        }

        public Criteria andItem41LessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("item4_1 <=", value, "item41");
            return (Criteria) this;
        }

        public Criteria andItem41In(List<Date> values) {
            addCriterionForJDBCDate("item4_1 in", values, "item41");
            return (Criteria) this;
        }

        public Criteria andItem41NotIn(List<Date> values) {
            addCriterionForJDBCDate("item4_1 not in", values, "item41");
            return (Criteria) this;
        }

        public Criteria andItem41Between(Date value1, Date value2) {
            addCriterionForJDBCDate("item4_1 between", value1, value2, "item41");
            return (Criteria) this;
        }

        public Criteria andItem41NotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("item4_1 not between", value1, value2, "item41");
            return (Criteria) this;
        }

        public Criteria andItem5IsNull() {
            addCriterion("item5 is null");
            return (Criteria) this;
        }

        public Criteria andItem5IsNotNull() {
            addCriterion("item5 is not null");
            return (Criteria) this;
        }

        public Criteria andItem5EqualTo(Integer value) {
            addCriterion("item5 =", value, "item5");
            return (Criteria) this;
        }

        public Criteria andItem5NotEqualTo(Integer value) {
            addCriterion("item5 <>", value, "item5");
            return (Criteria) this;
        }

        public Criteria andItem5GreaterThan(Integer value) {
            addCriterion("item5 >", value, "item5");
            return (Criteria) this;
        }

        public Criteria andItem5GreaterThanOrEqualTo(Integer value) {
            addCriterion("item5 >=", value, "item5");
            return (Criteria) this;
        }

        public Criteria andItem5LessThan(Integer value) {
            addCriterion("item5 <", value, "item5");
            return (Criteria) this;
        }

        public Criteria andItem5LessThanOrEqualTo(Integer value) {
            addCriterion("item5 <=", value, "item5");
            return (Criteria) this;
        }

        public Criteria andItem5In(List<Integer> values) {
            addCriterion("item5 in", values, "item5");
            return (Criteria) this;
        }

        public Criteria andItem5NotIn(List<Integer> values) {
            addCriterion("item5 not in", values, "item5");
            return (Criteria) this;
        }

        public Criteria andItem5Between(Integer value1, Integer value2) {
            addCriterion("item5 between", value1, value2, "item5");
            return (Criteria) this;
        }

        public Criteria andItem5NotBetween(Integer value1, Integer value2) {
            addCriterion("item5 not between", value1, value2, "item5");
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

        public Criteria andItem51EqualTo(Date value) {
            addCriterionForJDBCDate("item5_1 =", value, "item51");
            return (Criteria) this;
        }

        public Criteria andItem51NotEqualTo(Date value) {
            addCriterionForJDBCDate("item5_1 <>", value, "item51");
            return (Criteria) this;
        }

        public Criteria andItem51GreaterThan(Date value) {
            addCriterionForJDBCDate("item5_1 >", value, "item51");
            return (Criteria) this;
        }

        public Criteria andItem51GreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("item5_1 >=", value, "item51");
            return (Criteria) this;
        }

        public Criteria andItem51LessThan(Date value) {
            addCriterionForJDBCDate("item5_1 <", value, "item51");
            return (Criteria) this;
        }

        public Criteria andItem51LessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("item5_1 <=", value, "item51");
            return (Criteria) this;
        }

        public Criteria andItem51In(List<Date> values) {
            addCriterionForJDBCDate("item5_1 in", values, "item51");
            return (Criteria) this;
        }

        public Criteria andItem51NotIn(List<Date> values) {
            addCriterionForJDBCDate("item5_1 not in", values, "item51");
            return (Criteria) this;
        }

        public Criteria andItem51Between(Date value1, Date value2) {
            addCriterionForJDBCDate("item5_1 between", value1, value2, "item51");
            return (Criteria) this;
        }

        public Criteria andItem51NotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("item5_1 not between", value1, value2, "item51");
            return (Criteria) this;
        }

        public Criteria andItem6IsNull() {
            addCriterion("item6 is null");
            return (Criteria) this;
        }

        public Criteria andItem6IsNotNull() {
            addCriterion("item6 is not null");
            return (Criteria) this;
        }

        public Criteria andItem6EqualTo(Integer value) {
            addCriterion("item6 =", value, "item6");
            return (Criteria) this;
        }

        public Criteria andItem6NotEqualTo(Integer value) {
            addCriterion("item6 <>", value, "item6");
            return (Criteria) this;
        }

        public Criteria andItem6GreaterThan(Integer value) {
            addCriterion("item6 >", value, "item6");
            return (Criteria) this;
        }

        public Criteria andItem6GreaterThanOrEqualTo(Integer value) {
            addCriterion("item6 >=", value, "item6");
            return (Criteria) this;
        }

        public Criteria andItem6LessThan(Integer value) {
            addCriterion("item6 <", value, "item6");
            return (Criteria) this;
        }

        public Criteria andItem6LessThanOrEqualTo(Integer value) {
            addCriterion("item6 <=", value, "item6");
            return (Criteria) this;
        }

        public Criteria andItem6In(List<Integer> values) {
            addCriterion("item6 in", values, "item6");
            return (Criteria) this;
        }

        public Criteria andItem6NotIn(List<Integer> values) {
            addCriterion("item6 not in", values, "item6");
            return (Criteria) this;
        }

        public Criteria andItem6Between(Integer value1, Integer value2) {
            addCriterion("item6 between", value1, value2, "item6");
            return (Criteria) this;
        }

        public Criteria andItem6NotBetween(Integer value1, Integer value2) {
            addCriterion("item6 not between", value1, value2, "item6");
            return (Criteria) this;
        }

        public Criteria andItem61IsNull() {
            addCriterion("item6_1 is null");
            return (Criteria) this;
        }

        public Criteria andItem61IsNotNull() {
            addCriterion("item6_1 is not null");
            return (Criteria) this;
        }

        public Criteria andItem61EqualTo(String value) {
            addCriterion("item6_1 =", value, "item61");
            return (Criteria) this;
        }

        public Criteria andItem61NotEqualTo(String value) {
            addCriterion("item6_1 <>", value, "item61");
            return (Criteria) this;
        }

        public Criteria andItem61GreaterThan(String value) {
            addCriterion("item6_1 >", value, "item61");
            return (Criteria) this;
        }

        public Criteria andItem61GreaterThanOrEqualTo(String value) {
            addCriterion("item6_1 >=", value, "item61");
            return (Criteria) this;
        }

        public Criteria andItem61LessThan(String value) {
            addCriterion("item6_1 <", value, "item61");
            return (Criteria) this;
        }

        public Criteria andItem61LessThanOrEqualTo(String value) {
            addCriterion("item6_1 <=", value, "item61");
            return (Criteria) this;
        }

        public Criteria andItem61Like(String value) {
            addCriterion("item6_1 like", value, "item61");
            return (Criteria) this;
        }

        public Criteria andItem61NotLike(String value) {
            addCriterion("item6_1 not like", value, "item61");
            return (Criteria) this;
        }

        public Criteria andItem61In(List<String> values) {
            addCriterion("item6_1 in", values, "item61");
            return (Criteria) this;
        }

        public Criteria andItem61NotIn(List<String> values) {
            addCriterion("item6_1 not in", values, "item61");
            return (Criteria) this;
        }

        public Criteria andItem61Between(String value1, String value2) {
            addCriterion("item6_1 between", value1, value2, "item61");
            return (Criteria) this;
        }

        public Criteria andItem61NotBetween(String value1, String value2) {
            addCriterion("item6_1 not between", value1, value2, "item61");
            return (Criteria) this;
        }

        public Criteria andItem62IsNull() {
            addCriterion("item6_2 is null");
            return (Criteria) this;
        }

        public Criteria andItem62IsNotNull() {
            addCriterion("item6_2 is not null");
            return (Criteria) this;
        }

        public Criteria andItem62EqualTo(String value) {
            addCriterion("item6_2 =", value, "item62");
            return (Criteria) this;
        }

        public Criteria andItem62NotEqualTo(String value) {
            addCriterion("item6_2 <>", value, "item62");
            return (Criteria) this;
        }

        public Criteria andItem62GreaterThan(String value) {
            addCriterion("item6_2 >", value, "item62");
            return (Criteria) this;
        }

        public Criteria andItem62GreaterThanOrEqualTo(String value) {
            addCriterion("item6_2 >=", value, "item62");
            return (Criteria) this;
        }

        public Criteria andItem62LessThan(String value) {
            addCriterion("item6_2 <", value, "item62");
            return (Criteria) this;
        }

        public Criteria andItem62LessThanOrEqualTo(String value) {
            addCriterion("item6_2 <=", value, "item62");
            return (Criteria) this;
        }

        public Criteria andItem62Like(String value) {
            addCriterion("item6_2 like", value, "item62");
            return (Criteria) this;
        }

        public Criteria andItem62NotLike(String value) {
            addCriterion("item6_2 not like", value, "item62");
            return (Criteria) this;
        }

        public Criteria andItem62In(List<String> values) {
            addCriterion("item6_2 in", values, "item62");
            return (Criteria) this;
        }

        public Criteria andItem62NotIn(List<String> values) {
            addCriterion("item6_2 not in", values, "item62");
            return (Criteria) this;
        }

        public Criteria andItem62Between(String value1, String value2) {
            addCriterion("item6_2 between", value1, value2, "item62");
            return (Criteria) this;
        }

        public Criteria andItem62NotBetween(String value1, String value2) {
            addCriterion("item6_2 not between", value1, value2, "item62");
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

        public Criteria andStageIsNull() {
            addCriterion("stage is null");
            return (Criteria) this;
        }

        public Criteria andStageIsNotNull() {
            addCriterion("stage is not null");
            return (Criteria) this;
        }

        public Criteria andStageEqualTo(Integer value) {
            addCriterion("stage =", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageNotEqualTo(Integer value) {
            addCriterion("stage <>", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageGreaterThan(Integer value) {
            addCriterion("stage >", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageGreaterThanOrEqualTo(Integer value) {
            addCriterion("stage >=", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageLessThan(Integer value) {
            addCriterion("stage <", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageLessThanOrEqualTo(Integer value) {
            addCriterion("stage <=", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageIn(List<Integer> values) {
            addCriterion("stage in", values, "stage");
            return (Criteria) this;
        }

        public Criteria andStageNotIn(List<Integer> values) {
            addCriterion("stage not in", values, "stage");
            return (Criteria) this;
        }

        public Criteria andStageBetween(Integer value1, Integer value2) {
            addCriterion("stage between", value1, value2, "stage");
            return (Criteria) this;
        }

        public Criteria andStageNotBetween(Integer value1, Integer value2) {
            addCriterion("stage not between", value1, value2, "stage");
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