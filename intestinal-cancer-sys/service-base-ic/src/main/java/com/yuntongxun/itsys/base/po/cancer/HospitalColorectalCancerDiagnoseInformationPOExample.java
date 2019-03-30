package com.yuntongxun.itsys.base.po.cancer;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class HospitalColorectalCancerDiagnoseInformationPOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public HospitalColorectalCancerDiagnoseInformationPOExample() {
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

        public Criteria andExcerptsDateIsNull() {
            addCriterion("excerpts_date is null");
            return (Criteria) this;
        }

        public Criteria andExcerptsDateIsNotNull() {
            addCriterion("excerpts_date is not null");
            return (Criteria) this;
        }

        public Criteria andExcerptsDateEqualTo(Date value) {
            addCriterionForJDBCDate("excerpts_date =", value, "excerptsDate");
            return (Criteria) this;
        }

        public Criteria andExcerptsDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("excerpts_date <>", value, "excerptsDate");
            return (Criteria) this;
        }

        public Criteria andExcerptsDateGreaterThan(Date value) {
            addCriterionForJDBCDate("excerpts_date >", value, "excerptsDate");
            return (Criteria) this;
        }

        public Criteria andExcerptsDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("excerpts_date >=", value, "excerptsDate");
            return (Criteria) this;
        }

        public Criteria andExcerptsDateLessThan(Date value) {
            addCriterionForJDBCDate("excerpts_date <", value, "excerptsDate");
            return (Criteria) this;
        }

        public Criteria andExcerptsDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("excerpts_date <=", value, "excerptsDate");
            return (Criteria) this;
        }

        public Criteria andExcerptsDateIn(List<Date> values) {
            addCriterionForJDBCDate("excerpts_date in", values, "excerptsDate");
            return (Criteria) this;
        }

        public Criteria andExcerptsDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("excerpts_date not in", values, "excerptsDate");
            return (Criteria) this;
        }

        public Criteria andExcerptsDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("excerpts_date between", value1, value2, "excerptsDate");
            return (Criteria) this;
        }

        public Criteria andExcerptsDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("excerpts_date not between", value1, value2, "excerptsDate");
            return (Criteria) this;
        }

        public Criteria andExcerptPurposeIsNull() {
            addCriterion("excerpt_purpose is null");
            return (Criteria) this;
        }

        public Criteria andExcerptPurposeIsNotNull() {
            addCriterion("excerpt_purpose is not null");
            return (Criteria) this;
        }

        public Criteria andExcerptPurposeEqualTo(Integer value) {
            addCriterion("excerpt_purpose =", value, "excerptPurpose");
            return (Criteria) this;
        }

        public Criteria andExcerptPurposeNotEqualTo(Integer value) {
            addCriterion("excerpt_purpose <>", value, "excerptPurpose");
            return (Criteria) this;
        }

        public Criteria andExcerptPurposeGreaterThan(Integer value) {
            addCriterion("excerpt_purpose >", value, "excerptPurpose");
            return (Criteria) this;
        }

        public Criteria andExcerptPurposeGreaterThanOrEqualTo(Integer value) {
            addCriterion("excerpt_purpose >=", value, "excerptPurpose");
            return (Criteria) this;
        }

        public Criteria andExcerptPurposeLessThan(Integer value) {
            addCriterion("excerpt_purpose <", value, "excerptPurpose");
            return (Criteria) this;
        }

        public Criteria andExcerptPurposeLessThanOrEqualTo(Integer value) {
            addCriterion("excerpt_purpose <=", value, "excerptPurpose");
            return (Criteria) this;
        }

        public Criteria andExcerptPurposeIn(List<Integer> values) {
            addCriterion("excerpt_purpose in", values, "excerptPurpose");
            return (Criteria) this;
        }

        public Criteria andExcerptPurposeNotIn(List<Integer> values) {
            addCriterion("excerpt_purpose not in", values, "excerptPurpose");
            return (Criteria) this;
        }

        public Criteria andExcerptPurposeBetween(Integer value1, Integer value2) {
            addCriterion("excerpt_purpose between", value1, value2, "excerptPurpose");
            return (Criteria) this;
        }

        public Criteria andExcerptPurposeNotBetween(Integer value1, Integer value2) {
            addCriterion("excerpt_purpose not between", value1, value2, "excerptPurpose");
            return (Criteria) this;
        }

        public Criteria andCfScreeningIsNull() {
            addCriterion("cf_screening is null");
            return (Criteria) this;
        }

        public Criteria andCfScreeningIsNotNull() {
            addCriterion("cf_screening is not null");
            return (Criteria) this;
        }

        public Criteria andCfScreeningEqualTo(Integer value) {
            addCriterion("cf_screening =", value, "cfScreening");
            return (Criteria) this;
        }

        public Criteria andCfScreeningNotEqualTo(Integer value) {
            addCriterion("cf_screening <>", value, "cfScreening");
            return (Criteria) this;
        }

        public Criteria andCfScreeningGreaterThan(Integer value) {
            addCriterion("cf_screening >", value, "cfScreening");
            return (Criteria) this;
        }

        public Criteria andCfScreeningGreaterThanOrEqualTo(Integer value) {
            addCriterion("cf_screening >=", value, "cfScreening");
            return (Criteria) this;
        }

        public Criteria andCfScreeningLessThan(Integer value) {
            addCriterion("cf_screening <", value, "cfScreening");
            return (Criteria) this;
        }

        public Criteria andCfScreeningLessThanOrEqualTo(Integer value) {
            addCriterion("cf_screening <=", value, "cfScreening");
            return (Criteria) this;
        }

        public Criteria andCfScreeningIn(List<Integer> values) {
            addCriterion("cf_screening in", values, "cfScreening");
            return (Criteria) this;
        }

        public Criteria andCfScreeningNotIn(List<Integer> values) {
            addCriterion("cf_screening not in", values, "cfScreening");
            return (Criteria) this;
        }

        public Criteria andCfScreeningBetween(Integer value1, Integer value2) {
            addCriterion("cf_screening between", value1, value2, "cfScreening");
            return (Criteria) this;
        }

        public Criteria andCfScreeningNotBetween(Integer value1, Integer value2) {
            addCriterion("cf_screening not between", value1, value2, "cfScreening");
            return (Criteria) this;
        }

        public Criteria andExcerptPersonIdIsNull() {
            addCriterion("excerpt_person_id is null");
            return (Criteria) this;
        }

        public Criteria andExcerptPersonIdIsNotNull() {
            addCriterion("excerpt_person_id is not null");
            return (Criteria) this;
        }

        public Criteria andExcerptPersonIdEqualTo(Integer value) {
            addCriterion("excerpt_person_id =", value, "excerptPersonId");
            return (Criteria) this;
        }

        public Criteria andExcerptPersonIdNotEqualTo(Integer value) {
            addCriterion("excerpt_person_id <>", value, "excerptPersonId");
            return (Criteria) this;
        }

        public Criteria andExcerptPersonIdGreaterThan(Integer value) {
            addCriterion("excerpt_person_id >", value, "excerptPersonId");
            return (Criteria) this;
        }

        public Criteria andExcerptPersonIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("excerpt_person_id >=", value, "excerptPersonId");
            return (Criteria) this;
        }

        public Criteria andExcerptPersonIdLessThan(Integer value) {
            addCriterion("excerpt_person_id <", value, "excerptPersonId");
            return (Criteria) this;
        }

        public Criteria andExcerptPersonIdLessThanOrEqualTo(Integer value) {
            addCriterion("excerpt_person_id <=", value, "excerptPersonId");
            return (Criteria) this;
        }

        public Criteria andExcerptPersonIdIn(List<Integer> values) {
            addCriterion("excerpt_person_id in", values, "excerptPersonId");
            return (Criteria) this;
        }

        public Criteria andExcerptPersonIdNotIn(List<Integer> values) {
            addCriterion("excerpt_person_id not in", values, "excerptPersonId");
            return (Criteria) this;
        }

        public Criteria andExcerptPersonIdBetween(Integer value1, Integer value2) {
            addCriterion("excerpt_person_id between", value1, value2, "excerptPersonId");
            return (Criteria) this;
        }

        public Criteria andExcerptPersonIdNotBetween(Integer value1, Integer value2) {
            addCriterion("excerpt_person_id not between", value1, value2, "excerptPersonId");
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

        public Criteria andResearchYearIsNull() {
            addCriterion("research_year is null");
            return (Criteria) this;
        }

        public Criteria andResearchYearIsNotNull() {
            addCriterion("research_year is not null");
            return (Criteria) this;
        }

        public Criteria andResearchYearEqualTo(String value) {
            addCriterion("research_year =", value, "researchYear");
            return (Criteria) this;
        }

        public Criteria andResearchYearNotEqualTo(String value) {
            addCriterion("research_year <>", value, "researchYear");
            return (Criteria) this;
        }

        public Criteria andResearchYearGreaterThan(String value) {
            addCriterion("research_year >", value, "researchYear");
            return (Criteria) this;
        }

        public Criteria andResearchYearGreaterThanOrEqualTo(String value) {
            addCriterion("research_year >=", value, "researchYear");
            return (Criteria) this;
        }

        public Criteria andResearchYearLessThan(String value) {
            addCriterion("research_year <", value, "researchYear");
            return (Criteria) this;
        }

        public Criteria andResearchYearLessThanOrEqualTo(String value) {
            addCriterion("research_year <=", value, "researchYear");
            return (Criteria) this;
        }

        public Criteria andResearchYearLike(String value) {
            addCriterion("research_year like", value, "researchYear");
            return (Criteria) this;
        }

        public Criteria andResearchYearNotLike(String value) {
            addCriterion("research_year not like", value, "researchYear");
            return (Criteria) this;
        }

        public Criteria andResearchYearIn(List<String> values) {
            addCriterion("research_year in", values, "researchYear");
            return (Criteria) this;
        }

        public Criteria andResearchYearNotIn(List<String> values) {
            addCriterion("research_year not in", values, "researchYear");
            return (Criteria) this;
        }

        public Criteria andResearchYearBetween(String value1, String value2) {
            addCriterion("research_year between", value1, value2, "researchYear");
            return (Criteria) this;
        }

        public Criteria andResearchYearNotBetween(String value1, String value2) {
            addCriterion("research_year not between", value1, value2, "researchYear");
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

        public Criteria andItem23IsNull() {
            addCriterion("item2_3 is null");
            return (Criteria) this;
        }

        public Criteria andItem23IsNotNull() {
            addCriterion("item2_3 is not null");
            return (Criteria) this;
        }

        public Criteria andItem23EqualTo(Integer value) {
            addCriterion("item2_3 =", value, "item23");
            return (Criteria) this;
        }

        public Criteria andItem23NotEqualTo(Integer value) {
            addCriterion("item2_3 <>", value, "item23");
            return (Criteria) this;
        }

        public Criteria andItem23GreaterThan(Integer value) {
            addCriterion("item2_3 >", value, "item23");
            return (Criteria) this;
        }

        public Criteria andItem23GreaterThanOrEqualTo(Integer value) {
            addCriterion("item2_3 >=", value, "item23");
            return (Criteria) this;
        }

        public Criteria andItem23LessThan(Integer value) {
            addCriterion("item2_3 <", value, "item23");
            return (Criteria) this;
        }

        public Criteria andItem23LessThanOrEqualTo(Integer value) {
            addCriterion("item2_3 <=", value, "item23");
            return (Criteria) this;
        }

        public Criteria andItem23In(List<Integer> values) {
            addCriterion("item2_3 in", values, "item23");
            return (Criteria) this;
        }

        public Criteria andItem23NotIn(List<Integer> values) {
            addCriterion("item2_3 not in", values, "item23");
            return (Criteria) this;
        }

        public Criteria andItem23Between(Integer value1, Integer value2) {
            addCriterion("item2_3 between", value1, value2, "item23");
            return (Criteria) this;
        }

        public Criteria andItem23NotBetween(Integer value1, Integer value2) {
            addCriterion("item2_3 not between", value1, value2, "item23");
            return (Criteria) this;
        }

        public Criteria andItem23OtherIsNull() {
            addCriterion("item2_3_other is null");
            return (Criteria) this;
        }

        public Criteria andItem23OtherIsNotNull() {
            addCriterion("item2_3_other is not null");
            return (Criteria) this;
        }

        public Criteria andItem23OtherEqualTo(Integer value) {
            addCriterion("item2_3_other =", value, "item23Other");
            return (Criteria) this;
        }

        public Criteria andItem23OtherNotEqualTo(Integer value) {
            addCriterion("item2_3_other <>", value, "item23Other");
            return (Criteria) this;
        }

        public Criteria andItem23OtherGreaterThan(Integer value) {
            addCriterion("item2_3_other >", value, "item23Other");
            return (Criteria) this;
        }

        public Criteria andItem23OtherGreaterThanOrEqualTo(Integer value) {
            addCriterion("item2_3_other >=", value, "item23Other");
            return (Criteria) this;
        }

        public Criteria andItem23OtherLessThan(Integer value) {
            addCriterion("item2_3_other <", value, "item23Other");
            return (Criteria) this;
        }

        public Criteria andItem23OtherLessThanOrEqualTo(Integer value) {
            addCriterion("item2_3_other <=", value, "item23Other");
            return (Criteria) this;
        }

        public Criteria andItem23OtherIn(List<Integer> values) {
            addCriterion("item2_3_other in", values, "item23Other");
            return (Criteria) this;
        }

        public Criteria andItem23OtherNotIn(List<Integer> values) {
            addCriterion("item2_3_other not in", values, "item23Other");
            return (Criteria) this;
        }

        public Criteria andItem23OtherBetween(Integer value1, Integer value2) {
            addCriterion("item2_3_other between", value1, value2, "item23Other");
            return (Criteria) this;
        }

        public Criteria andItem23OtherNotBetween(Integer value1, Integer value2) {
            addCriterion("item2_3_other not between", value1, value2, "item23Other");
            return (Criteria) this;
        }

        public Criteria andItem3CheckDateIsNull() {
            addCriterion("item3_check_date is null");
            return (Criteria) this;
        }

        public Criteria andItem3CheckDateIsNotNull() {
            addCriterion("item3_check_date is not null");
            return (Criteria) this;
        }

        public Criteria andItem3CheckDateEqualTo(Date value) {
            addCriterionForJDBCDate("item3_check_date =", value, "item3CheckDate");
            return (Criteria) this;
        }

        public Criteria andItem3CheckDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("item3_check_date <>", value, "item3CheckDate");
            return (Criteria) this;
        }

        public Criteria andItem3CheckDateGreaterThan(Date value) {
            addCriterionForJDBCDate("item3_check_date >", value, "item3CheckDate");
            return (Criteria) this;
        }

        public Criteria andItem3CheckDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("item3_check_date >=", value, "item3CheckDate");
            return (Criteria) this;
        }

        public Criteria andItem3CheckDateLessThan(Date value) {
            addCriterionForJDBCDate("item3_check_date <", value, "item3CheckDate");
            return (Criteria) this;
        }

        public Criteria andItem3CheckDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("item3_check_date <=", value, "item3CheckDate");
            return (Criteria) this;
        }

        public Criteria andItem3CheckDateIn(List<Date> values) {
            addCriterionForJDBCDate("item3_check_date in", values, "item3CheckDate");
            return (Criteria) this;
        }

        public Criteria andItem3CheckDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("item3_check_date not in", values, "item3CheckDate");
            return (Criteria) this;
        }

        public Criteria andItem3CheckDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("item3_check_date between", value1, value2, "item3CheckDate");
            return (Criteria) this;
        }

        public Criteria andItem3CheckDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("item3_check_date not between", value1, value2, "item3CheckDate");
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

        public Criteria andItem31EqualTo(Integer value) {
            addCriterion("item3_1 =", value, "item31");
            return (Criteria) this;
        }

        public Criteria andItem31NotEqualTo(Integer value) {
            addCriterion("item3_1 <>", value, "item31");
            return (Criteria) this;
        }

        public Criteria andItem31GreaterThan(Integer value) {
            addCriterion("item3_1 >", value, "item31");
            return (Criteria) this;
        }

        public Criteria andItem31GreaterThanOrEqualTo(Integer value) {
            addCriterion("item3_1 >=", value, "item31");
            return (Criteria) this;
        }

        public Criteria andItem31LessThan(Integer value) {
            addCriterion("item3_1 <", value, "item31");
            return (Criteria) this;
        }

        public Criteria andItem31LessThanOrEqualTo(Integer value) {
            addCriterion("item3_1 <=", value, "item31");
            return (Criteria) this;
        }

        public Criteria andItem31In(List<Integer> values) {
            addCriterion("item3_1 in", values, "item31");
            return (Criteria) this;
        }

        public Criteria andItem31NotIn(List<Integer> values) {
            addCriterion("item3_1 not in", values, "item31");
            return (Criteria) this;
        }

        public Criteria andItem31Between(Integer value1, Integer value2) {
            addCriterion("item3_1 between", value1, value2, "item31");
            return (Criteria) this;
        }

        public Criteria andItem31NotBetween(Integer value1, Integer value2) {
            addCriterion("item3_1 not between", value1, value2, "item31");
            return (Criteria) this;
        }

        public Criteria andItem32IsNull() {
            addCriterion("item3_2 is null");
            return (Criteria) this;
        }

        public Criteria andItem32IsNotNull() {
            addCriterion("item3_2 is not null");
            return (Criteria) this;
        }

        public Criteria andItem32EqualTo(Integer value) {
            addCriterion("item3_2 =", value, "item32");
            return (Criteria) this;
        }

        public Criteria andItem32NotEqualTo(Integer value) {
            addCriterion("item3_2 <>", value, "item32");
            return (Criteria) this;
        }

        public Criteria andItem32GreaterThan(Integer value) {
            addCriterion("item3_2 >", value, "item32");
            return (Criteria) this;
        }

        public Criteria andItem32GreaterThanOrEqualTo(Integer value) {
            addCriterion("item3_2 >=", value, "item32");
            return (Criteria) this;
        }

        public Criteria andItem32LessThan(Integer value) {
            addCriterion("item3_2 <", value, "item32");
            return (Criteria) this;
        }

        public Criteria andItem32LessThanOrEqualTo(Integer value) {
            addCriterion("item3_2 <=", value, "item32");
            return (Criteria) this;
        }

        public Criteria andItem32In(List<Integer> values) {
            addCriterion("item3_2 in", values, "item32");
            return (Criteria) this;
        }

        public Criteria andItem32NotIn(List<Integer> values) {
            addCriterion("item3_2 not in", values, "item32");
            return (Criteria) this;
        }

        public Criteria andItem32Between(Integer value1, Integer value2) {
            addCriterion("item3_2 between", value1, value2, "item32");
            return (Criteria) this;
        }

        public Criteria andItem32NotBetween(Integer value1, Integer value2) {
            addCriterion("item3_2 not between", value1, value2, "item32");
            return (Criteria) this;
        }

        public Criteria andItem33IsNull() {
            addCriterion("item3_3 is null");
            return (Criteria) this;
        }

        public Criteria andItem33IsNotNull() {
            addCriterion("item3_3 is not null");
            return (Criteria) this;
        }

        public Criteria andItem33EqualTo(Integer value) {
            addCriterion("item3_3 =", value, "item33");
            return (Criteria) this;
        }

        public Criteria andItem33NotEqualTo(Integer value) {
            addCriterion("item3_3 <>", value, "item33");
            return (Criteria) this;
        }

        public Criteria andItem33GreaterThan(Integer value) {
            addCriterion("item3_3 >", value, "item33");
            return (Criteria) this;
        }

        public Criteria andItem33GreaterThanOrEqualTo(Integer value) {
            addCriterion("item3_3 >=", value, "item33");
            return (Criteria) this;
        }

        public Criteria andItem33LessThan(Integer value) {
            addCriterion("item3_3 <", value, "item33");
            return (Criteria) this;
        }

        public Criteria andItem33LessThanOrEqualTo(Integer value) {
            addCriterion("item3_3 <=", value, "item33");
            return (Criteria) this;
        }

        public Criteria andItem33In(List<Integer> values) {
            addCriterion("item3_3 in", values, "item33");
            return (Criteria) this;
        }

        public Criteria andItem33NotIn(List<Integer> values) {
            addCriterion("item3_3 not in", values, "item33");
            return (Criteria) this;
        }

        public Criteria andItem33Between(Integer value1, Integer value2) {
            addCriterion("item3_3 between", value1, value2, "item33");
            return (Criteria) this;
        }

        public Criteria andItem33NotBetween(Integer value1, Integer value2) {
            addCriterion("item3_3 not between", value1, value2, "item33");
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

        public Criteria andItem41EqualTo(Integer value) {
            addCriterion("item4_1 =", value, "item41");
            return (Criteria) this;
        }

        public Criteria andItem41NotEqualTo(Integer value) {
            addCriterion("item4_1 <>", value, "item41");
            return (Criteria) this;
        }

        public Criteria andItem41GreaterThan(Integer value) {
            addCriterion("item4_1 >", value, "item41");
            return (Criteria) this;
        }

        public Criteria andItem41GreaterThanOrEqualTo(Integer value) {
            addCriterion("item4_1 >=", value, "item41");
            return (Criteria) this;
        }

        public Criteria andItem41LessThan(Integer value) {
            addCriterion("item4_1 <", value, "item41");
            return (Criteria) this;
        }

        public Criteria andItem41LessThanOrEqualTo(Integer value) {
            addCriterion("item4_1 <=", value, "item41");
            return (Criteria) this;
        }

        public Criteria andItem41In(List<Integer> values) {
            addCriterion("item4_1 in", values, "item41");
            return (Criteria) this;
        }

        public Criteria andItem41NotIn(List<Integer> values) {
            addCriterion("item4_1 not in", values, "item41");
            return (Criteria) this;
        }

        public Criteria andItem41Between(Integer value1, Integer value2) {
            addCriterion("item4_1 between", value1, value2, "item41");
            return (Criteria) this;
        }

        public Criteria andItem41NotBetween(Integer value1, Integer value2) {
            addCriterion("item4_1 not between", value1, value2, "item41");
            return (Criteria) this;
        }

        public Criteria andItem421IsNull() {
            addCriterion("item4_2_1 is null");
            return (Criteria) this;
        }

        public Criteria andItem421IsNotNull() {
            addCriterion("item4_2_1 is not null");
            return (Criteria) this;
        }

        public Criteria andItem421EqualTo(Integer value) {
            addCriterion("item4_2_1 =", value, "item421");
            return (Criteria) this;
        }

        public Criteria andItem421NotEqualTo(Integer value) {
            addCriterion("item4_2_1 <>", value, "item421");
            return (Criteria) this;
        }

        public Criteria andItem421GreaterThan(Integer value) {
            addCriterion("item4_2_1 >", value, "item421");
            return (Criteria) this;
        }

        public Criteria andItem421GreaterThanOrEqualTo(Integer value) {
            addCriterion("item4_2_1 >=", value, "item421");
            return (Criteria) this;
        }

        public Criteria andItem421LessThan(Integer value) {
            addCriterion("item4_2_1 <", value, "item421");
            return (Criteria) this;
        }

        public Criteria andItem421LessThanOrEqualTo(Integer value) {
            addCriterion("item4_2_1 <=", value, "item421");
            return (Criteria) this;
        }

        public Criteria andItem421In(List<Integer> values) {
            addCriterion("item4_2_1 in", values, "item421");
            return (Criteria) this;
        }

        public Criteria andItem421NotIn(List<Integer> values) {
            addCriterion("item4_2_1 not in", values, "item421");
            return (Criteria) this;
        }

        public Criteria andItem421Between(Integer value1, Integer value2) {
            addCriterion("item4_2_1 between", value1, value2, "item421");
            return (Criteria) this;
        }

        public Criteria andItem421NotBetween(Integer value1, Integer value2) {
            addCriterion("item4_2_1 not between", value1, value2, "item421");
            return (Criteria) this;
        }

        public Criteria andItem422IsNull() {
            addCriterion("item4_2_2 is null");
            return (Criteria) this;
        }

        public Criteria andItem422IsNotNull() {
            addCriterion("item4_2_2 is not null");
            return (Criteria) this;
        }

        public Criteria andItem422EqualTo(Double value) {
            addCriterion("item4_2_2 =", value, "item422");
            return (Criteria) this;
        }

        public Criteria andItem422NotEqualTo(Double value) {
            addCriterion("item4_2_2 <>", value, "item422");
            return (Criteria) this;
        }

        public Criteria andItem422GreaterThan(Double value) {
            addCriterion("item4_2_2 >", value, "item422");
            return (Criteria) this;
        }

        public Criteria andItem422GreaterThanOrEqualTo(Double value) {
            addCriterion("item4_2_2 >=", value, "item422");
            return (Criteria) this;
        }

        public Criteria andItem422LessThan(Double value) {
            addCriterion("item4_2_2 <", value, "item422");
            return (Criteria) this;
        }

        public Criteria andItem422LessThanOrEqualTo(Double value) {
            addCriterion("item4_2_2 <=", value, "item422");
            return (Criteria) this;
        }

        public Criteria andItem422In(List<Double> values) {
            addCriterion("item4_2_2 in", values, "item422");
            return (Criteria) this;
        }

        public Criteria andItem422NotIn(List<Double> values) {
            addCriterion("item4_2_2 not in", values, "item422");
            return (Criteria) this;
        }

        public Criteria andItem422Between(Double value1, Double value2) {
            addCriterion("item4_2_2 between", value1, value2, "item422");
            return (Criteria) this;
        }

        public Criteria andItem422NotBetween(Double value1, Double value2) {
            addCriterion("item4_2_2 not between", value1, value2, "item422");
            return (Criteria) this;
        }

        public Criteria andItem431IsNull() {
            addCriterion("item4_3_1 is null");
            return (Criteria) this;
        }

        public Criteria andItem431IsNotNull() {
            addCriterion("item4_3_1 is not null");
            return (Criteria) this;
        }

        public Criteria andItem431EqualTo(Integer value) {
            addCriterion("item4_3_1 =", value, "item431");
            return (Criteria) this;
        }

        public Criteria andItem431NotEqualTo(Integer value) {
            addCriterion("item4_3_1 <>", value, "item431");
            return (Criteria) this;
        }

        public Criteria andItem431GreaterThan(Integer value) {
            addCriterion("item4_3_1 >", value, "item431");
            return (Criteria) this;
        }

        public Criteria andItem431GreaterThanOrEqualTo(Integer value) {
            addCriterion("item4_3_1 >=", value, "item431");
            return (Criteria) this;
        }

        public Criteria andItem431LessThan(Integer value) {
            addCriterion("item4_3_1 <", value, "item431");
            return (Criteria) this;
        }

        public Criteria andItem431LessThanOrEqualTo(Integer value) {
            addCriterion("item4_3_1 <=", value, "item431");
            return (Criteria) this;
        }

        public Criteria andItem431In(List<Integer> values) {
            addCriterion("item4_3_1 in", values, "item431");
            return (Criteria) this;
        }

        public Criteria andItem431NotIn(List<Integer> values) {
            addCriterion("item4_3_1 not in", values, "item431");
            return (Criteria) this;
        }

        public Criteria andItem431Between(Integer value1, Integer value2) {
            addCriterion("item4_3_1 between", value1, value2, "item431");
            return (Criteria) this;
        }

        public Criteria andItem431NotBetween(Integer value1, Integer value2) {
            addCriterion("item4_3_1 not between", value1, value2, "item431");
            return (Criteria) this;
        }

        public Criteria andItem432IsNull() {
            addCriterion("item4_3_2 is null");
            return (Criteria) this;
        }

        public Criteria andItem432IsNotNull() {
            addCriterion("item4_3_2 is not null");
            return (Criteria) this;
        }

        public Criteria andItem432EqualTo(Double value) {
            addCriterion("item4_3_2 =", value, "item432");
            return (Criteria) this;
        }

        public Criteria andItem432NotEqualTo(Double value) {
            addCriterion("item4_3_2 <>", value, "item432");
            return (Criteria) this;
        }

        public Criteria andItem432GreaterThan(Double value) {
            addCriterion("item4_3_2 >", value, "item432");
            return (Criteria) this;
        }

        public Criteria andItem432GreaterThanOrEqualTo(Double value) {
            addCriterion("item4_3_2 >=", value, "item432");
            return (Criteria) this;
        }

        public Criteria andItem432LessThan(Double value) {
            addCriterion("item4_3_2 <", value, "item432");
            return (Criteria) this;
        }

        public Criteria andItem432LessThanOrEqualTo(Double value) {
            addCriterion("item4_3_2 <=", value, "item432");
            return (Criteria) this;
        }

        public Criteria andItem432In(List<Double> values) {
            addCriterion("item4_3_2 in", values, "item432");
            return (Criteria) this;
        }

        public Criteria andItem432NotIn(List<Double> values) {
            addCriterion("item4_3_2 not in", values, "item432");
            return (Criteria) this;
        }

        public Criteria andItem432Between(Double value1, Double value2) {
            addCriterion("item4_3_2 between", value1, value2, "item432");
            return (Criteria) this;
        }

        public Criteria andItem432NotBetween(Double value1, Double value2) {
            addCriterion("item4_3_2 not between", value1, value2, "item432");
            return (Criteria) this;
        }

        public Criteria andItem44IsNull() {
            addCriterion("item4_4 is null");
            return (Criteria) this;
        }

        public Criteria andItem44IsNotNull() {
            addCriterion("item4_4 is not null");
            return (Criteria) this;
        }

        public Criteria andItem44EqualTo(Integer value) {
            addCriterion("item4_4 =", value, "item44");
            return (Criteria) this;
        }

        public Criteria andItem44NotEqualTo(Integer value) {
            addCriterion("item4_4 <>", value, "item44");
            return (Criteria) this;
        }

        public Criteria andItem44GreaterThan(Integer value) {
            addCriterion("item4_4 >", value, "item44");
            return (Criteria) this;
        }

        public Criteria andItem44GreaterThanOrEqualTo(Integer value) {
            addCriterion("item4_4 >=", value, "item44");
            return (Criteria) this;
        }

        public Criteria andItem44LessThan(Integer value) {
            addCriterion("item4_4 <", value, "item44");
            return (Criteria) this;
        }

        public Criteria andItem44LessThanOrEqualTo(Integer value) {
            addCriterion("item4_4 <=", value, "item44");
            return (Criteria) this;
        }

        public Criteria andItem44In(List<Integer> values) {
            addCriterion("item4_4 in", values, "item44");
            return (Criteria) this;
        }

        public Criteria andItem44NotIn(List<Integer> values) {
            addCriterion("item4_4 not in", values, "item44");
            return (Criteria) this;
        }

        public Criteria andItem44Between(Integer value1, Integer value2) {
            addCriterion("item4_4 between", value1, value2, "item44");
            return (Criteria) this;
        }

        public Criteria andItem44NotBetween(Integer value1, Integer value2) {
            addCriterion("item4_4 not between", value1, value2, "item44");
            return (Criteria) this;
        }

        public Criteria andItem45IsNull() {
            addCriterion("item4_5 is null");
            return (Criteria) this;
        }

        public Criteria andItem45IsNotNull() {
            addCriterion("item4_5 is not null");
            return (Criteria) this;
        }

        public Criteria andItem45EqualTo(Integer value) {
            addCriterion("item4_5 =", value, "item45");
            return (Criteria) this;
        }

        public Criteria andItem45NotEqualTo(Integer value) {
            addCriterion("item4_5 <>", value, "item45");
            return (Criteria) this;
        }

        public Criteria andItem45GreaterThan(Integer value) {
            addCriterion("item4_5 >", value, "item45");
            return (Criteria) this;
        }

        public Criteria andItem45GreaterThanOrEqualTo(Integer value) {
            addCriterion("item4_5 >=", value, "item45");
            return (Criteria) this;
        }

        public Criteria andItem45LessThan(Integer value) {
            addCriterion("item4_5 <", value, "item45");
            return (Criteria) this;
        }

        public Criteria andItem45LessThanOrEqualTo(Integer value) {
            addCriterion("item4_5 <=", value, "item45");
            return (Criteria) this;
        }

        public Criteria andItem45In(List<Integer> values) {
            addCriterion("item4_5 in", values, "item45");
            return (Criteria) this;
        }

        public Criteria andItem45NotIn(List<Integer> values) {
            addCriterion("item4_5 not in", values, "item45");
            return (Criteria) this;
        }

        public Criteria andItem45Between(Integer value1, Integer value2) {
            addCriterion("item4_5 between", value1, value2, "item45");
            return (Criteria) this;
        }

        public Criteria andItem45NotBetween(Integer value1, Integer value2) {
            addCriterion("item4_5 not between", value1, value2, "item45");
            return (Criteria) this;
        }

        public Criteria andItem46IsNull() {
            addCriterion("item4_6 is null");
            return (Criteria) this;
        }

        public Criteria andItem46IsNotNull() {
            addCriterion("item4_6 is not null");
            return (Criteria) this;
        }

        public Criteria andItem46EqualTo(Integer value) {
            addCriterion("item4_6 =", value, "item46");
            return (Criteria) this;
        }

        public Criteria andItem46NotEqualTo(Integer value) {
            addCriterion("item4_6 <>", value, "item46");
            return (Criteria) this;
        }

        public Criteria andItem46GreaterThan(Integer value) {
            addCriterion("item4_6 >", value, "item46");
            return (Criteria) this;
        }

        public Criteria andItem46GreaterThanOrEqualTo(Integer value) {
            addCriterion("item4_6 >=", value, "item46");
            return (Criteria) this;
        }

        public Criteria andItem46LessThan(Integer value) {
            addCriterion("item4_6 <", value, "item46");
            return (Criteria) this;
        }

        public Criteria andItem46LessThanOrEqualTo(Integer value) {
            addCriterion("item4_6 <=", value, "item46");
            return (Criteria) this;
        }

        public Criteria andItem46In(List<Integer> values) {
            addCriterion("item4_6 in", values, "item46");
            return (Criteria) this;
        }

        public Criteria andItem46NotIn(List<Integer> values) {
            addCriterion("item4_6 not in", values, "item46");
            return (Criteria) this;
        }

        public Criteria andItem46Between(Integer value1, Integer value2) {
            addCriterion("item4_6 between", value1, value2, "item46");
            return (Criteria) this;
        }

        public Criteria andItem46NotBetween(Integer value1, Integer value2) {
            addCriterion("item4_6 not between", value1, value2, "item46");
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

        public Criteria andItem61EqualTo(Integer value) {
            addCriterion("item6_1 =", value, "item61");
            return (Criteria) this;
        }

        public Criteria andItem61NotEqualTo(Integer value) {
            addCriterion("item6_1 <>", value, "item61");
            return (Criteria) this;
        }

        public Criteria andItem61GreaterThan(Integer value) {
            addCriterion("item6_1 >", value, "item61");
            return (Criteria) this;
        }

        public Criteria andItem61GreaterThanOrEqualTo(Integer value) {
            addCriterion("item6_1 >=", value, "item61");
            return (Criteria) this;
        }

        public Criteria andItem61LessThan(Integer value) {
            addCriterion("item6_1 <", value, "item61");
            return (Criteria) this;
        }

        public Criteria andItem61LessThanOrEqualTo(Integer value) {
            addCriterion("item6_1 <=", value, "item61");
            return (Criteria) this;
        }

        public Criteria andItem61In(List<Integer> values) {
            addCriterion("item6_1 in", values, "item61");
            return (Criteria) this;
        }

        public Criteria andItem61NotIn(List<Integer> values) {
            addCriterion("item6_1 not in", values, "item61");
            return (Criteria) this;
        }

        public Criteria andItem61Between(Integer value1, Integer value2) {
            addCriterion("item6_1 between", value1, value2, "item61");
            return (Criteria) this;
        }

        public Criteria andItem61NotBetween(Integer value1, Integer value2) {
            addCriterion("item6_1 not between", value1, value2, "item61");
            return (Criteria) this;
        }

        public Criteria andItem71IsNull() {
            addCriterion("item7_1 is null");
            return (Criteria) this;
        }

        public Criteria andItem71IsNotNull() {
            addCriterion("item7_1 is not null");
            return (Criteria) this;
        }

        public Criteria andItem71EqualTo(Integer value) {
            addCriterion("item7_1 =", value, "item71");
            return (Criteria) this;
        }

        public Criteria andItem71NotEqualTo(Integer value) {
            addCriterion("item7_1 <>", value, "item71");
            return (Criteria) this;
        }

        public Criteria andItem71GreaterThan(Integer value) {
            addCriterion("item7_1 >", value, "item71");
            return (Criteria) this;
        }

        public Criteria andItem71GreaterThanOrEqualTo(Integer value) {
            addCriterion("item7_1 >=", value, "item71");
            return (Criteria) this;
        }

        public Criteria andItem71LessThan(Integer value) {
            addCriterion("item7_1 <", value, "item71");
            return (Criteria) this;
        }

        public Criteria andItem71LessThanOrEqualTo(Integer value) {
            addCriterion("item7_1 <=", value, "item71");
            return (Criteria) this;
        }

        public Criteria andItem71In(List<Integer> values) {
            addCriterion("item7_1 in", values, "item71");
            return (Criteria) this;
        }

        public Criteria andItem71NotIn(List<Integer> values) {
            addCriterion("item7_1 not in", values, "item71");
            return (Criteria) this;
        }

        public Criteria andItem71Between(Integer value1, Integer value2) {
            addCriterion("item7_1 between", value1, value2, "item71");
            return (Criteria) this;
        }

        public Criteria andItem71NotBetween(Integer value1, Integer value2) {
            addCriterion("item7_1 not between", value1, value2, "item71");
            return (Criteria) this;
        }

        public Criteria andItem8a1IsNull() {
            addCriterion("item8a_1 is null");
            return (Criteria) this;
        }

        public Criteria andItem8a1IsNotNull() {
            addCriterion("item8a_1 is not null");
            return (Criteria) this;
        }

        public Criteria andItem8a1EqualTo(Integer value) {
            addCriterion("item8a_1 =", value, "item8a1");
            return (Criteria) this;
        }

        public Criteria andItem8a1NotEqualTo(Integer value) {
            addCriterion("item8a_1 <>", value, "item8a1");
            return (Criteria) this;
        }

        public Criteria andItem8a1GreaterThan(Integer value) {
            addCriterion("item8a_1 >", value, "item8a1");
            return (Criteria) this;
        }

        public Criteria andItem8a1GreaterThanOrEqualTo(Integer value) {
            addCriterion("item8a_1 >=", value, "item8a1");
            return (Criteria) this;
        }

        public Criteria andItem8a1LessThan(Integer value) {
            addCriterion("item8a_1 <", value, "item8a1");
            return (Criteria) this;
        }

        public Criteria andItem8a1LessThanOrEqualTo(Integer value) {
            addCriterion("item8a_1 <=", value, "item8a1");
            return (Criteria) this;
        }

        public Criteria andItem8a1In(List<Integer> values) {
            addCriterion("item8a_1 in", values, "item8a1");
            return (Criteria) this;
        }

        public Criteria andItem8a1NotIn(List<Integer> values) {
            addCriterion("item8a_1 not in", values, "item8a1");
            return (Criteria) this;
        }

        public Criteria andItem8a1Between(Integer value1, Integer value2) {
            addCriterion("item8a_1 between", value1, value2, "item8a1");
            return (Criteria) this;
        }

        public Criteria andItem8a1NotBetween(Integer value1, Integer value2) {
            addCriterion("item8a_1 not between", value1, value2, "item8a1");
            return (Criteria) this;
        }

        public Criteria andItem8a2IsNull() {
            addCriterion("item8a_2 is null");
            return (Criteria) this;
        }

        public Criteria andItem8a2IsNotNull() {
            addCriterion("item8a_2 is not null");
            return (Criteria) this;
        }

        public Criteria andItem8a2EqualTo(String value) {
            addCriterion("item8a_2 =", value, "item8a2");
            return (Criteria) this;
        }

        public Criteria andItem8a2NotEqualTo(String value) {
            addCriterion("item8a_2 <>", value, "item8a2");
            return (Criteria) this;
        }

        public Criteria andItem8a2GreaterThan(String value) {
            addCriterion("item8a_2 >", value, "item8a2");
            return (Criteria) this;
        }

        public Criteria andItem8a2GreaterThanOrEqualTo(String value) {
            addCriterion("item8a_2 >=", value, "item8a2");
            return (Criteria) this;
        }

        public Criteria andItem8a2LessThan(String value) {
            addCriterion("item8a_2 <", value, "item8a2");
            return (Criteria) this;
        }

        public Criteria andItem8a2LessThanOrEqualTo(String value) {
            addCriterion("item8a_2 <=", value, "item8a2");
            return (Criteria) this;
        }

        public Criteria andItem8a2Like(String value) {
            addCriterion("item8a_2 like", value, "item8a2");
            return (Criteria) this;
        }

        public Criteria andItem8a2NotLike(String value) {
            addCriterion("item8a_2 not like", value, "item8a2");
            return (Criteria) this;
        }

        public Criteria andItem8a2In(List<String> values) {
            addCriterion("item8a_2 in", values, "item8a2");
            return (Criteria) this;
        }

        public Criteria andItem8a2NotIn(List<String> values) {
            addCriterion("item8a_2 not in", values, "item8a2");
            return (Criteria) this;
        }

        public Criteria andItem8a2Between(String value1, String value2) {
            addCriterion("item8a_2 between", value1, value2, "item8a2");
            return (Criteria) this;
        }

        public Criteria andItem8a2NotBetween(String value1, String value2) {
            addCriterion("item8a_2 not between", value1, value2, "item8a2");
            return (Criteria) this;
        }

        public Criteria andItem8b1IsNull() {
            addCriterion("item8b_1 is null");
            return (Criteria) this;
        }

        public Criteria andItem8b1IsNotNull() {
            addCriterion("item8b_1 is not null");
            return (Criteria) this;
        }

        public Criteria andItem8b1EqualTo(Date value) {
            addCriterionForJDBCDate("item8b_1 =", value, "item8b1");
            return (Criteria) this;
        }

        public Criteria andItem8b1NotEqualTo(Date value) {
            addCriterionForJDBCDate("item8b_1 <>", value, "item8b1");
            return (Criteria) this;
        }

        public Criteria andItem8b1GreaterThan(Date value) {
            addCriterionForJDBCDate("item8b_1 >", value, "item8b1");
            return (Criteria) this;
        }

        public Criteria andItem8b1GreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("item8b_1 >=", value, "item8b1");
            return (Criteria) this;
        }

        public Criteria andItem8b1LessThan(Date value) {
            addCriterionForJDBCDate("item8b_1 <", value, "item8b1");
            return (Criteria) this;
        }

        public Criteria andItem8b1LessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("item8b_1 <=", value, "item8b1");
            return (Criteria) this;
        }

        public Criteria andItem8b1In(List<Date> values) {
            addCriterionForJDBCDate("item8b_1 in", values, "item8b1");
            return (Criteria) this;
        }

        public Criteria andItem8b1NotIn(List<Date> values) {
            addCriterionForJDBCDate("item8b_1 not in", values, "item8b1");
            return (Criteria) this;
        }

        public Criteria andItem8b1Between(Date value1, Date value2) {
            addCriterionForJDBCDate("item8b_1 between", value1, value2, "item8b1");
            return (Criteria) this;
        }

        public Criteria andItem8b1NotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("item8b_1 not between", value1, value2, "item8b1");
            return (Criteria) this;
        }

        public Criteria andItem8b2IsNull() {
            addCriterion("item8b_2 is null");
            return (Criteria) this;
        }

        public Criteria andItem8b2IsNotNull() {
            addCriterion("item8b_2 is not null");
            return (Criteria) this;
        }

        public Criteria andItem8b2EqualTo(String value) {
            addCriterion("item8b_2 =", value, "item8b2");
            return (Criteria) this;
        }

        public Criteria andItem8b2NotEqualTo(String value) {
            addCriterion("item8b_2 <>", value, "item8b2");
            return (Criteria) this;
        }

        public Criteria andItem8b2GreaterThan(String value) {
            addCriterion("item8b_2 >", value, "item8b2");
            return (Criteria) this;
        }

        public Criteria andItem8b2GreaterThanOrEqualTo(String value) {
            addCriterion("item8b_2 >=", value, "item8b2");
            return (Criteria) this;
        }

        public Criteria andItem8b2LessThan(String value) {
            addCriterion("item8b_2 <", value, "item8b2");
            return (Criteria) this;
        }

        public Criteria andItem8b2LessThanOrEqualTo(String value) {
            addCriterion("item8b_2 <=", value, "item8b2");
            return (Criteria) this;
        }

        public Criteria andItem8b2Like(String value) {
            addCriterion("item8b_2 like", value, "item8b2");
            return (Criteria) this;
        }

        public Criteria andItem8b2NotLike(String value) {
            addCriterion("item8b_2 not like", value, "item8b2");
            return (Criteria) this;
        }

        public Criteria andItem8b2In(List<String> values) {
            addCriterion("item8b_2 in", values, "item8b2");
            return (Criteria) this;
        }

        public Criteria andItem8b2NotIn(List<String> values) {
            addCriterion("item8b_2 not in", values, "item8b2");
            return (Criteria) this;
        }

        public Criteria andItem8b2Between(String value1, String value2) {
            addCriterion("item8b_2 between", value1, value2, "item8b2");
            return (Criteria) this;
        }

        public Criteria andItem8b2NotBetween(String value1, String value2) {
            addCriterion("item8b_2 not between", value1, value2, "item8b2");
            return (Criteria) this;
        }

        public Criteria andItem8c1IsNull() {
            addCriterion("item8c_1 is null");
            return (Criteria) this;
        }

        public Criteria andItem8c1IsNotNull() {
            addCriterion("item8c_1 is not null");
            return (Criteria) this;
        }

        public Criteria andItem8c1EqualTo(Integer value) {
            addCriterion("item8c_1 =", value, "item8c1");
            return (Criteria) this;
        }

        public Criteria andItem8c1NotEqualTo(Integer value) {
            addCriterion("item8c_1 <>", value, "item8c1");
            return (Criteria) this;
        }

        public Criteria andItem8c1GreaterThan(Integer value) {
            addCriterion("item8c_1 >", value, "item8c1");
            return (Criteria) this;
        }

        public Criteria andItem8c1GreaterThanOrEqualTo(Integer value) {
            addCriterion("item8c_1 >=", value, "item8c1");
            return (Criteria) this;
        }

        public Criteria andItem8c1LessThan(Integer value) {
            addCriterion("item8c_1 <", value, "item8c1");
            return (Criteria) this;
        }

        public Criteria andItem8c1LessThanOrEqualTo(Integer value) {
            addCriterion("item8c_1 <=", value, "item8c1");
            return (Criteria) this;
        }

        public Criteria andItem8c1In(List<Integer> values) {
            addCriterion("item8c_1 in", values, "item8c1");
            return (Criteria) this;
        }

        public Criteria andItem8c1NotIn(List<Integer> values) {
            addCriterion("item8c_1 not in", values, "item8c1");
            return (Criteria) this;
        }

        public Criteria andItem8c1Between(Integer value1, Integer value2) {
            addCriterion("item8c_1 between", value1, value2, "item8c1");
            return (Criteria) this;
        }

        public Criteria andItem8c1NotBetween(Integer value1, Integer value2) {
            addCriterion("item8c_1 not between", value1, value2, "item8c1");
            return (Criteria) this;
        }

        public Criteria andItem8c11IsNull() {
            addCriterion("item8c_1_1 is null");
            return (Criteria) this;
        }

        public Criteria andItem8c11IsNotNull() {
            addCriterion("item8c_1_1 is not null");
            return (Criteria) this;
        }

        public Criteria andItem8c11EqualTo(Integer value) {
            addCriterion("item8c_1_1 =", value, "item8c11");
            return (Criteria) this;
        }

        public Criteria andItem8c11NotEqualTo(Integer value) {
            addCriterion("item8c_1_1 <>", value, "item8c11");
            return (Criteria) this;
        }

        public Criteria andItem8c11GreaterThan(Integer value) {
            addCriterion("item8c_1_1 >", value, "item8c11");
            return (Criteria) this;
        }

        public Criteria andItem8c11GreaterThanOrEqualTo(Integer value) {
            addCriterion("item8c_1_1 >=", value, "item8c11");
            return (Criteria) this;
        }

        public Criteria andItem8c11LessThan(Integer value) {
            addCriterion("item8c_1_1 <", value, "item8c11");
            return (Criteria) this;
        }

        public Criteria andItem8c11LessThanOrEqualTo(Integer value) {
            addCriterion("item8c_1_1 <=", value, "item8c11");
            return (Criteria) this;
        }

        public Criteria andItem8c11In(List<Integer> values) {
            addCriterion("item8c_1_1 in", values, "item8c11");
            return (Criteria) this;
        }

        public Criteria andItem8c11NotIn(List<Integer> values) {
            addCriterion("item8c_1_1 not in", values, "item8c11");
            return (Criteria) this;
        }

        public Criteria andItem8c11Between(Integer value1, Integer value2) {
            addCriterion("item8c_1_1 between", value1, value2, "item8c11");
            return (Criteria) this;
        }

        public Criteria andItem8c11NotBetween(Integer value1, Integer value2) {
            addCriterion("item8c_1_1 not between", value1, value2, "item8c11");
            return (Criteria) this;
        }

        public Criteria andItem8c12IsNull() {
            addCriterion("item8c_1_2 is null");
            return (Criteria) this;
        }

        public Criteria andItem8c12IsNotNull() {
            addCriterion("item8c_1_2 is not null");
            return (Criteria) this;
        }

        public Criteria andItem8c12EqualTo(String value) {
            addCriterion("item8c_1_2 =", value, "item8c12");
            return (Criteria) this;
        }

        public Criteria andItem8c12NotEqualTo(String value) {
            addCriterion("item8c_1_2 <>", value, "item8c12");
            return (Criteria) this;
        }

        public Criteria andItem8c12GreaterThan(String value) {
            addCriterion("item8c_1_2 >", value, "item8c12");
            return (Criteria) this;
        }

        public Criteria andItem8c12GreaterThanOrEqualTo(String value) {
            addCriterion("item8c_1_2 >=", value, "item8c12");
            return (Criteria) this;
        }

        public Criteria andItem8c12LessThan(String value) {
            addCriterion("item8c_1_2 <", value, "item8c12");
            return (Criteria) this;
        }

        public Criteria andItem8c12LessThanOrEqualTo(String value) {
            addCriterion("item8c_1_2 <=", value, "item8c12");
            return (Criteria) this;
        }

        public Criteria andItem8c12Like(String value) {
            addCriterion("item8c_1_2 like", value, "item8c12");
            return (Criteria) this;
        }

        public Criteria andItem8c12NotLike(String value) {
            addCriterion("item8c_1_2 not like", value, "item8c12");
            return (Criteria) this;
        }

        public Criteria andItem8c12In(List<String> values) {
            addCriterion("item8c_1_2 in", values, "item8c12");
            return (Criteria) this;
        }

        public Criteria andItem8c12NotIn(List<String> values) {
            addCriterion("item8c_1_2 not in", values, "item8c12");
            return (Criteria) this;
        }

        public Criteria andItem8c12Between(String value1, String value2) {
            addCriterion("item8c_1_2 between", value1, value2, "item8c12");
            return (Criteria) this;
        }

        public Criteria andItem8c12NotBetween(String value1, String value2) {
            addCriterion("item8c_1_2 not between", value1, value2, "item8c12");
            return (Criteria) this;
        }

        public Criteria andItem8c13IsNull() {
            addCriterion("item8c_1_3 is null");
            return (Criteria) this;
        }

        public Criteria andItem8c13IsNotNull() {
            addCriterion("item8c_1_3 is not null");
            return (Criteria) this;
        }

        public Criteria andItem8c13EqualTo(String value) {
            addCriterion("item8c_1_3 =", value, "item8c13");
            return (Criteria) this;
        }

        public Criteria andItem8c13NotEqualTo(String value) {
            addCriterion("item8c_1_3 <>", value, "item8c13");
            return (Criteria) this;
        }

        public Criteria andItem8c13GreaterThan(String value) {
            addCriterion("item8c_1_3 >", value, "item8c13");
            return (Criteria) this;
        }

        public Criteria andItem8c13GreaterThanOrEqualTo(String value) {
            addCriterion("item8c_1_3 >=", value, "item8c13");
            return (Criteria) this;
        }

        public Criteria andItem8c13LessThan(String value) {
            addCriterion("item8c_1_3 <", value, "item8c13");
            return (Criteria) this;
        }

        public Criteria andItem8c13LessThanOrEqualTo(String value) {
            addCriterion("item8c_1_3 <=", value, "item8c13");
            return (Criteria) this;
        }

        public Criteria andItem8c13Like(String value) {
            addCriterion("item8c_1_3 like", value, "item8c13");
            return (Criteria) this;
        }

        public Criteria andItem8c13NotLike(String value) {
            addCriterion("item8c_1_3 not like", value, "item8c13");
            return (Criteria) this;
        }

        public Criteria andItem8c13In(List<String> values) {
            addCriterion("item8c_1_3 in", values, "item8c13");
            return (Criteria) this;
        }

        public Criteria andItem8c13NotIn(List<String> values) {
            addCriterion("item8c_1_3 not in", values, "item8c13");
            return (Criteria) this;
        }

        public Criteria andItem8c13Between(String value1, String value2) {
            addCriterion("item8c_1_3 between", value1, value2, "item8c13");
            return (Criteria) this;
        }

        public Criteria andItem8c13NotBetween(String value1, String value2) {
            addCriterion("item8c_1_3 not between", value1, value2, "item8c13");
            return (Criteria) this;
        }

        public Criteria andItem8c14IsNull() {
            addCriterion("item8c_1_4 is null");
            return (Criteria) this;
        }

        public Criteria andItem8c14IsNotNull() {
            addCriterion("item8c_1_4 is not null");
            return (Criteria) this;
        }

        public Criteria andItem8c14EqualTo(String value) {
            addCriterion("item8c_1_4 =", value, "item8c14");
            return (Criteria) this;
        }

        public Criteria andItem8c14NotEqualTo(String value) {
            addCriterion("item8c_1_4 <>", value, "item8c14");
            return (Criteria) this;
        }

        public Criteria andItem8c14GreaterThan(String value) {
            addCriterion("item8c_1_4 >", value, "item8c14");
            return (Criteria) this;
        }

        public Criteria andItem8c14GreaterThanOrEqualTo(String value) {
            addCriterion("item8c_1_4 >=", value, "item8c14");
            return (Criteria) this;
        }

        public Criteria andItem8c14LessThan(String value) {
            addCriterion("item8c_1_4 <", value, "item8c14");
            return (Criteria) this;
        }

        public Criteria andItem8c14LessThanOrEqualTo(String value) {
            addCriterion("item8c_1_4 <=", value, "item8c14");
            return (Criteria) this;
        }

        public Criteria andItem8c14Like(String value) {
            addCriterion("item8c_1_4 like", value, "item8c14");
            return (Criteria) this;
        }

        public Criteria andItem8c14NotLike(String value) {
            addCriterion("item8c_1_4 not like", value, "item8c14");
            return (Criteria) this;
        }

        public Criteria andItem8c14In(List<String> values) {
            addCriterion("item8c_1_4 in", values, "item8c14");
            return (Criteria) this;
        }

        public Criteria andItem8c14NotIn(List<String> values) {
            addCriterion("item8c_1_4 not in", values, "item8c14");
            return (Criteria) this;
        }

        public Criteria andItem8c14Between(String value1, String value2) {
            addCriterion("item8c_1_4 between", value1, value2, "item8c14");
            return (Criteria) this;
        }

        public Criteria andItem8c14NotBetween(String value1, String value2) {
            addCriterion("item8c_1_4 not between", value1, value2, "item8c14");
            return (Criteria) this;
        }

        public Criteria andItem8c15IsNull() {
            addCriterion("item8c_1_5 is null");
            return (Criteria) this;
        }

        public Criteria andItem8c15IsNotNull() {
            addCriterion("item8c_1_5 is not null");
            return (Criteria) this;
        }

        public Criteria andItem8c15EqualTo(String value) {
            addCriterion("item8c_1_5 =", value, "item8c15");
            return (Criteria) this;
        }

        public Criteria andItem8c15NotEqualTo(String value) {
            addCriterion("item8c_1_5 <>", value, "item8c15");
            return (Criteria) this;
        }

        public Criteria andItem8c15GreaterThan(String value) {
            addCriterion("item8c_1_5 >", value, "item8c15");
            return (Criteria) this;
        }

        public Criteria andItem8c15GreaterThanOrEqualTo(String value) {
            addCriterion("item8c_1_5 >=", value, "item8c15");
            return (Criteria) this;
        }

        public Criteria andItem8c15LessThan(String value) {
            addCriterion("item8c_1_5 <", value, "item8c15");
            return (Criteria) this;
        }

        public Criteria andItem8c15LessThanOrEqualTo(String value) {
            addCriterion("item8c_1_5 <=", value, "item8c15");
            return (Criteria) this;
        }

        public Criteria andItem8c15Like(String value) {
            addCriterion("item8c_1_5 like", value, "item8c15");
            return (Criteria) this;
        }

        public Criteria andItem8c15NotLike(String value) {
            addCriterion("item8c_1_5 not like", value, "item8c15");
            return (Criteria) this;
        }

        public Criteria andItem8c15In(List<String> values) {
            addCriterion("item8c_1_5 in", values, "item8c15");
            return (Criteria) this;
        }

        public Criteria andItem8c15NotIn(List<String> values) {
            addCriterion("item8c_1_5 not in", values, "item8c15");
            return (Criteria) this;
        }

        public Criteria andItem8c15Between(String value1, String value2) {
            addCriterion("item8c_1_5 between", value1, value2, "item8c15");
            return (Criteria) this;
        }

        public Criteria andItem8c15NotBetween(String value1, String value2) {
            addCriterion("item8c_1_5 not between", value1, value2, "item8c15");
            return (Criteria) this;
        }

        public Criteria andItem8d11IsNull() {
            addCriterion("item8d_1_1 is null");
            return (Criteria) this;
        }

        public Criteria andItem8d11IsNotNull() {
            addCriterion("item8d_1_1 is not null");
            return (Criteria) this;
        }

        public Criteria andItem8d11EqualTo(Date value) {
            addCriterionForJDBCDate("item8d_1_1 =", value, "item8d11");
            return (Criteria) this;
        }

        public Criteria andItem8d11NotEqualTo(Date value) {
            addCriterionForJDBCDate("item8d_1_1 <>", value, "item8d11");
            return (Criteria) this;
        }

        public Criteria andItem8d11GreaterThan(Date value) {
            addCriterionForJDBCDate("item8d_1_1 >", value, "item8d11");
            return (Criteria) this;
        }

        public Criteria andItem8d11GreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("item8d_1_1 >=", value, "item8d11");
            return (Criteria) this;
        }

        public Criteria andItem8d11LessThan(Date value) {
            addCriterionForJDBCDate("item8d_1_1 <", value, "item8d11");
            return (Criteria) this;
        }

        public Criteria andItem8d11LessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("item8d_1_1 <=", value, "item8d11");
            return (Criteria) this;
        }

        public Criteria andItem8d11In(List<Date> values) {
            addCriterionForJDBCDate("item8d_1_1 in", values, "item8d11");
            return (Criteria) this;
        }

        public Criteria andItem8d11NotIn(List<Date> values) {
            addCriterionForJDBCDate("item8d_1_1 not in", values, "item8d11");
            return (Criteria) this;
        }

        public Criteria andItem8d11Between(Date value1, Date value2) {
            addCriterionForJDBCDate("item8d_1_1 between", value1, value2, "item8d11");
            return (Criteria) this;
        }

        public Criteria andItem8d11NotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("item8d_1_1 not between", value1, value2, "item8d11");
            return (Criteria) this;
        }

        public Criteria andItem8d12IsNull() {
            addCriterion("item8d_1_2 is null");
            return (Criteria) this;
        }

        public Criteria andItem8d12IsNotNull() {
            addCriterion("item8d_1_2 is not null");
            return (Criteria) this;
        }

        public Criteria andItem8d12EqualTo(String value) {
            addCriterion("item8d_1_2 =", value, "item8d12");
            return (Criteria) this;
        }

        public Criteria andItem8d12NotEqualTo(String value) {
            addCriterion("item8d_1_2 <>", value, "item8d12");
            return (Criteria) this;
        }

        public Criteria andItem8d12GreaterThan(String value) {
            addCriterion("item8d_1_2 >", value, "item8d12");
            return (Criteria) this;
        }

        public Criteria andItem8d12GreaterThanOrEqualTo(String value) {
            addCriterion("item8d_1_2 >=", value, "item8d12");
            return (Criteria) this;
        }

        public Criteria andItem8d12LessThan(String value) {
            addCriterion("item8d_1_2 <", value, "item8d12");
            return (Criteria) this;
        }

        public Criteria andItem8d12LessThanOrEqualTo(String value) {
            addCriterion("item8d_1_2 <=", value, "item8d12");
            return (Criteria) this;
        }

        public Criteria andItem8d12Like(String value) {
            addCriterion("item8d_1_2 like", value, "item8d12");
            return (Criteria) this;
        }

        public Criteria andItem8d12NotLike(String value) {
            addCriterion("item8d_1_2 not like", value, "item8d12");
            return (Criteria) this;
        }

        public Criteria andItem8d12In(List<String> values) {
            addCriterion("item8d_1_2 in", values, "item8d12");
            return (Criteria) this;
        }

        public Criteria andItem8d12NotIn(List<String> values) {
            addCriterion("item8d_1_2 not in", values, "item8d12");
            return (Criteria) this;
        }

        public Criteria andItem8d12Between(String value1, String value2) {
            addCriterion("item8d_1_2 between", value1, value2, "item8d12");
            return (Criteria) this;
        }

        public Criteria andItem8d12NotBetween(String value1, String value2) {
            addCriterion("item8d_1_2 not between", value1, value2, "item8d12");
            return (Criteria) this;
        }

        public Criteria andItem8e11IsNull() {
            addCriterion("item8e_1_1 is null");
            return (Criteria) this;
        }

        public Criteria andItem8e11IsNotNull() {
            addCriterion("item8e_1_1 is not null");
            return (Criteria) this;
        }

        public Criteria andItem8e11EqualTo(Integer value) {
            addCriterion("item8e_1_1 =", value, "item8e11");
            return (Criteria) this;
        }

        public Criteria andItem8e11NotEqualTo(Integer value) {
            addCriterion("item8e_1_1 <>", value, "item8e11");
            return (Criteria) this;
        }

        public Criteria andItem8e11GreaterThan(Integer value) {
            addCriterion("item8e_1_1 >", value, "item8e11");
            return (Criteria) this;
        }

        public Criteria andItem8e11GreaterThanOrEqualTo(Integer value) {
            addCriterion("item8e_1_1 >=", value, "item8e11");
            return (Criteria) this;
        }

        public Criteria andItem8e11LessThan(Integer value) {
            addCriterion("item8e_1_1 <", value, "item8e11");
            return (Criteria) this;
        }

        public Criteria andItem8e11LessThanOrEqualTo(Integer value) {
            addCriterion("item8e_1_1 <=", value, "item8e11");
            return (Criteria) this;
        }

        public Criteria andItem8e11In(List<Integer> values) {
            addCriterion("item8e_1_1 in", values, "item8e11");
            return (Criteria) this;
        }

        public Criteria andItem8e11NotIn(List<Integer> values) {
            addCriterion("item8e_1_1 not in", values, "item8e11");
            return (Criteria) this;
        }

        public Criteria andItem8e11Between(Integer value1, Integer value2) {
            addCriterion("item8e_1_1 between", value1, value2, "item8e11");
            return (Criteria) this;
        }

        public Criteria andItem8e11NotBetween(Integer value1, Integer value2) {
            addCriterion("item8e_1_1 not between", value1, value2, "item8e11");
            return (Criteria) this;
        }

        public Criteria andItem9IsNull() {
            addCriterion("item9 is null");
            return (Criteria) this;
        }

        public Criteria andItem9IsNotNull() {
            addCriterion("item9 is not null");
            return (Criteria) this;
        }

        public Criteria andItem9EqualTo(Date value) {
            addCriterionForJDBCDate("item9 =", value, "item9");
            return (Criteria) this;
        }

        public Criteria andItem9NotEqualTo(Date value) {
            addCriterionForJDBCDate("item9 <>", value, "item9");
            return (Criteria) this;
        }

        public Criteria andItem9GreaterThan(Date value) {
            addCriterionForJDBCDate("item9 >", value, "item9");
            return (Criteria) this;
        }

        public Criteria andItem9GreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("item9 >=", value, "item9");
            return (Criteria) this;
        }

        public Criteria andItem9LessThan(Date value) {
            addCriterionForJDBCDate("item9 <", value, "item9");
            return (Criteria) this;
        }

        public Criteria andItem9LessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("item9 <=", value, "item9");
            return (Criteria) this;
        }

        public Criteria andItem9In(List<Date> values) {
            addCriterionForJDBCDate("item9 in", values, "item9");
            return (Criteria) this;
        }

        public Criteria andItem9NotIn(List<Date> values) {
            addCriterionForJDBCDate("item9 not in", values, "item9");
            return (Criteria) this;
        }

        public Criteria andItem9Between(Date value1, Date value2) {
            addCriterionForJDBCDate("item9 between", value1, value2, "item9");
            return (Criteria) this;
        }

        public Criteria andItem9NotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("item9 not between", value1, value2, "item9");
            return (Criteria) this;
        }

        public Criteria andItem10IsNull() {
            addCriterion("item10 is null");
            return (Criteria) this;
        }

        public Criteria andItem10IsNotNull() {
            addCriterion("item10 is not null");
            return (Criteria) this;
        }

        public Criteria andItem10EqualTo(Integer value) {
            addCriterion("item10 =", value, "item10");
            return (Criteria) this;
        }

        public Criteria andItem10NotEqualTo(Integer value) {
            addCriterion("item10 <>", value, "item10");
            return (Criteria) this;
        }

        public Criteria andItem10GreaterThan(Integer value) {
            addCriterion("item10 >", value, "item10");
            return (Criteria) this;
        }

        public Criteria andItem10GreaterThanOrEqualTo(Integer value) {
            addCriterion("item10 >=", value, "item10");
            return (Criteria) this;
        }

        public Criteria andItem10LessThan(Integer value) {
            addCriterion("item10 <", value, "item10");
            return (Criteria) this;
        }

        public Criteria andItem10LessThanOrEqualTo(Integer value) {
            addCriterion("item10 <=", value, "item10");
            return (Criteria) this;
        }

        public Criteria andItem10In(List<Integer> values) {
            addCriterion("item10 in", values, "item10");
            return (Criteria) this;
        }

        public Criteria andItem10NotIn(List<Integer> values) {
            addCriterion("item10 not in", values, "item10");
            return (Criteria) this;
        }

        public Criteria andItem10Between(Integer value1, Integer value2) {
            addCriterion("item10 between", value1, value2, "item10");
            return (Criteria) this;
        }

        public Criteria andItem10NotBetween(Integer value1, Integer value2) {
            addCriterion("item10 not between", value1, value2, "item10");
            return (Criteria) this;
        }

        public Criteria andItem11IsNull() {
            addCriterion("item11 is null");
            return (Criteria) this;
        }

        public Criteria andItem11IsNotNull() {
            addCriterion("item11 is not null");
            return (Criteria) this;
        }

        public Criteria andItem11EqualTo(String value) {
            addCriterion("item11 =", value, "item11");
            return (Criteria) this;
        }

        public Criteria andItem11NotEqualTo(String value) {
            addCriterion("item11 <>", value, "item11");
            return (Criteria) this;
        }

        public Criteria andItem11GreaterThan(String value) {
            addCriterion("item11 >", value, "item11");
            return (Criteria) this;
        }

        public Criteria andItem11GreaterThanOrEqualTo(String value) {
            addCriterion("item11 >=", value, "item11");
            return (Criteria) this;
        }

        public Criteria andItem11LessThan(String value) {
            addCriterion("item11 <", value, "item11");
            return (Criteria) this;
        }

        public Criteria andItem11LessThanOrEqualTo(String value) {
            addCriterion("item11 <=", value, "item11");
            return (Criteria) this;
        }

        public Criteria andItem11Like(String value) {
            addCriterion("item11 like", value, "item11");
            return (Criteria) this;
        }

        public Criteria andItem11NotLike(String value) {
            addCriterion("item11 not like", value, "item11");
            return (Criteria) this;
        }

        public Criteria andItem11In(List<String> values) {
            addCriterion("item11 in", values, "item11");
            return (Criteria) this;
        }

        public Criteria andItem11NotIn(List<String> values) {
            addCriterion("item11 not in", values, "item11");
            return (Criteria) this;
        }

        public Criteria andItem11Between(String value1, String value2) {
            addCriterion("item11 between", value1, value2, "item11");
            return (Criteria) this;
        }

        public Criteria andItem11NotBetween(String value1, String value2) {
            addCriterion("item11 not between", value1, value2, "item11");
            return (Criteria) this;
        }

        public Criteria andReportUrlIsNull() {
            addCriterion("report_url is null");
            return (Criteria) this;
        }

        public Criteria andReportUrlIsNotNull() {
            addCriterion("report_url is not null");
            return (Criteria) this;
        }

        public Criteria andReportUrlEqualTo(String value) {
            addCriterion("report_url =", value, "reportUrl");
            return (Criteria) this;
        }

        public Criteria andReportUrlNotEqualTo(String value) {
            addCriterion("report_url <>", value, "reportUrl");
            return (Criteria) this;
        }

        public Criteria andReportUrlGreaterThan(String value) {
            addCriterion("report_url >", value, "reportUrl");
            return (Criteria) this;
        }

        public Criteria andReportUrlGreaterThanOrEqualTo(String value) {
            addCriterion("report_url >=", value, "reportUrl");
            return (Criteria) this;
        }

        public Criteria andReportUrlLessThan(String value) {
            addCriterion("report_url <", value, "reportUrl");
            return (Criteria) this;
        }

        public Criteria andReportUrlLessThanOrEqualTo(String value) {
            addCriterion("report_url <=", value, "reportUrl");
            return (Criteria) this;
        }

        public Criteria andReportUrlLike(String value) {
            addCriterion("report_url like", value, "reportUrl");
            return (Criteria) this;
        }

        public Criteria andReportUrlNotLike(String value) {
            addCriterion("report_url not like", value, "reportUrl");
            return (Criteria) this;
        }

        public Criteria andReportUrlIn(List<String> values) {
            addCriterion("report_url in", values, "reportUrl");
            return (Criteria) this;
        }

        public Criteria andReportUrlNotIn(List<String> values) {
            addCriterion("report_url not in", values, "reportUrl");
            return (Criteria) this;
        }

        public Criteria andReportUrlBetween(String value1, String value2) {
            addCriterion("report_url between", value1, value2, "reportUrl");
            return (Criteria) this;
        }

        public Criteria andReportUrlNotBetween(String value1, String value2) {
            addCriterion("report_url not between", value1, value2, "reportUrl");
            return (Criteria) this;
        }

        public Criteria andItem121IsNull() {
            addCriterion("item12_1 is null");
            return (Criteria) this;
        }

        public Criteria andItem121IsNotNull() {
            addCriterion("item12_1 is not null");
            return (Criteria) this;
        }

        public Criteria andItem121EqualTo(String value) {
            addCriterion("item12_1 =", value, "item121");
            return (Criteria) this;
        }

        public Criteria andItem121NotEqualTo(String value) {
            addCriterion("item12_1 <>", value, "item121");
            return (Criteria) this;
        }

        public Criteria andItem121GreaterThan(String value) {
            addCriterion("item12_1 >", value, "item121");
            return (Criteria) this;
        }

        public Criteria andItem121GreaterThanOrEqualTo(String value) {
            addCriterion("item12_1 >=", value, "item121");
            return (Criteria) this;
        }

        public Criteria andItem121LessThan(String value) {
            addCriterion("item12_1 <", value, "item121");
            return (Criteria) this;
        }

        public Criteria andItem121LessThanOrEqualTo(String value) {
            addCriterion("item12_1 <=", value, "item121");
            return (Criteria) this;
        }

        public Criteria andItem121Like(String value) {
            addCriterion("item12_1 like", value, "item121");
            return (Criteria) this;
        }

        public Criteria andItem121NotLike(String value) {
            addCriterion("item12_1 not like", value, "item121");
            return (Criteria) this;
        }

        public Criteria andItem121In(List<String> values) {
            addCriterion("item12_1 in", values, "item121");
            return (Criteria) this;
        }

        public Criteria andItem121NotIn(List<String> values) {
            addCriterion("item12_1 not in", values, "item121");
            return (Criteria) this;
        }

        public Criteria andItem121Between(String value1, String value2) {
            addCriterion("item12_1 between", value1, value2, "item121");
            return (Criteria) this;
        }

        public Criteria andItem121NotBetween(String value1, String value2) {
            addCriterion("item12_1 not between", value1, value2, "item121");
            return (Criteria) this;
        }

        public Criteria andItem122IsNull() {
            addCriterion("item12_2 is null");
            return (Criteria) this;
        }

        public Criteria andItem122IsNotNull() {
            addCriterion("item12_2 is not null");
            return (Criteria) this;
        }

        public Criteria andItem122EqualTo(String value) {
            addCriterion("item12_2 =", value, "item122");
            return (Criteria) this;
        }

        public Criteria andItem122NotEqualTo(String value) {
            addCriterion("item12_2 <>", value, "item122");
            return (Criteria) this;
        }

        public Criteria andItem122GreaterThan(String value) {
            addCriterion("item12_2 >", value, "item122");
            return (Criteria) this;
        }

        public Criteria andItem122GreaterThanOrEqualTo(String value) {
            addCriterion("item12_2 >=", value, "item122");
            return (Criteria) this;
        }

        public Criteria andItem122LessThan(String value) {
            addCriterion("item12_2 <", value, "item122");
            return (Criteria) this;
        }

        public Criteria andItem122LessThanOrEqualTo(String value) {
            addCriterion("item12_2 <=", value, "item122");
            return (Criteria) this;
        }

        public Criteria andItem122Like(String value) {
            addCriterion("item12_2 like", value, "item122");
            return (Criteria) this;
        }

        public Criteria andItem122NotLike(String value) {
            addCriterion("item12_2 not like", value, "item122");
            return (Criteria) this;
        }

        public Criteria andItem122In(List<String> values) {
            addCriterion("item12_2 in", values, "item122");
            return (Criteria) this;
        }

        public Criteria andItem122NotIn(List<String> values) {
            addCriterion("item12_2 not in", values, "item122");
            return (Criteria) this;
        }

        public Criteria andItem122Between(String value1, String value2) {
            addCriterion("item12_2 between", value1, value2, "item122");
            return (Criteria) this;
        }

        public Criteria andItem122NotBetween(String value1, String value2) {
            addCriterion("item12_2 not between", value1, value2, "item122");
            return (Criteria) this;
        }

        public Criteria andItem123IsNull() {
            addCriterion("item12_3 is null");
            return (Criteria) this;
        }

        public Criteria andItem123IsNotNull() {
            addCriterion("item12_3 is not null");
            return (Criteria) this;
        }

        public Criteria andItem123EqualTo(String value) {
            addCriterion("item12_3 =", value, "item123");
            return (Criteria) this;
        }

        public Criteria andItem123NotEqualTo(String value) {
            addCriterion("item12_3 <>", value, "item123");
            return (Criteria) this;
        }

        public Criteria andItem123GreaterThan(String value) {
            addCriterion("item12_3 >", value, "item123");
            return (Criteria) this;
        }

        public Criteria andItem123GreaterThanOrEqualTo(String value) {
            addCriterion("item12_3 >=", value, "item123");
            return (Criteria) this;
        }

        public Criteria andItem123LessThan(String value) {
            addCriterion("item12_3 <", value, "item123");
            return (Criteria) this;
        }

        public Criteria andItem123LessThanOrEqualTo(String value) {
            addCriterion("item12_3 <=", value, "item123");
            return (Criteria) this;
        }

        public Criteria andItem123Like(String value) {
            addCriterion("item12_3 like", value, "item123");
            return (Criteria) this;
        }

        public Criteria andItem123NotLike(String value) {
            addCriterion("item12_3 not like", value, "item123");
            return (Criteria) this;
        }

        public Criteria andItem123In(List<String> values) {
            addCriterion("item12_3 in", values, "item123");
            return (Criteria) this;
        }

        public Criteria andItem123NotIn(List<String> values) {
            addCriterion("item12_3 not in", values, "item123");
            return (Criteria) this;
        }

        public Criteria andItem123Between(String value1, String value2) {
            addCriterion("item12_3 between", value1, value2, "item123");
            return (Criteria) this;
        }

        public Criteria andItem123NotBetween(String value1, String value2) {
            addCriterion("item12_3 not between", value1, value2, "item123");
            return (Criteria) this;
        }

        public Criteria andItem124IsNull() {
            addCriterion("item12_4 is null");
            return (Criteria) this;
        }

        public Criteria andItem124IsNotNull() {
            addCriterion("item12_4 is not null");
            return (Criteria) this;
        }

        public Criteria andItem124EqualTo(String value) {
            addCriterion("item12_4 =", value, "item124");
            return (Criteria) this;
        }

        public Criteria andItem124NotEqualTo(String value) {
            addCriterion("item12_4 <>", value, "item124");
            return (Criteria) this;
        }

        public Criteria andItem124GreaterThan(String value) {
            addCriterion("item12_4 >", value, "item124");
            return (Criteria) this;
        }

        public Criteria andItem124GreaterThanOrEqualTo(String value) {
            addCriterion("item12_4 >=", value, "item124");
            return (Criteria) this;
        }

        public Criteria andItem124LessThan(String value) {
            addCriterion("item12_4 <", value, "item124");
            return (Criteria) this;
        }

        public Criteria andItem124LessThanOrEqualTo(String value) {
            addCriterion("item12_4 <=", value, "item124");
            return (Criteria) this;
        }

        public Criteria andItem124Like(String value) {
            addCriterion("item12_4 like", value, "item124");
            return (Criteria) this;
        }

        public Criteria andItem124NotLike(String value) {
            addCriterion("item12_4 not like", value, "item124");
            return (Criteria) this;
        }

        public Criteria andItem124In(List<String> values) {
            addCriterion("item12_4 in", values, "item124");
            return (Criteria) this;
        }

        public Criteria andItem124NotIn(List<String> values) {
            addCriterion("item12_4 not in", values, "item124");
            return (Criteria) this;
        }

        public Criteria andItem124Between(String value1, String value2) {
            addCriterion("item12_4 between", value1, value2, "item124");
            return (Criteria) this;
        }

        public Criteria andItem124NotBetween(String value1, String value2) {
            addCriterion("item12_4 not between", value1, value2, "item124");
            return (Criteria) this;
        }

        public Criteria andItem125IsNull() {
            addCriterion("item12_5 is null");
            return (Criteria) this;
        }

        public Criteria andItem125IsNotNull() {
            addCriterion("item12_5 is not null");
            return (Criteria) this;
        }

        public Criteria andItem125EqualTo(String value) {
            addCriterion("item12_5 =", value, "item125");
            return (Criteria) this;
        }

        public Criteria andItem125NotEqualTo(String value) {
            addCriterion("item12_5 <>", value, "item125");
            return (Criteria) this;
        }

        public Criteria andItem125GreaterThan(String value) {
            addCriterion("item12_5 >", value, "item125");
            return (Criteria) this;
        }

        public Criteria andItem125GreaterThanOrEqualTo(String value) {
            addCriterion("item12_5 >=", value, "item125");
            return (Criteria) this;
        }

        public Criteria andItem125LessThan(String value) {
            addCriterion("item12_5 <", value, "item125");
            return (Criteria) this;
        }

        public Criteria andItem125LessThanOrEqualTo(String value) {
            addCriterion("item12_5 <=", value, "item125");
            return (Criteria) this;
        }

        public Criteria andItem125Like(String value) {
            addCriterion("item12_5 like", value, "item125");
            return (Criteria) this;
        }

        public Criteria andItem125NotLike(String value) {
            addCriterion("item12_5 not like", value, "item125");
            return (Criteria) this;
        }

        public Criteria andItem125In(List<String> values) {
            addCriterion("item12_5 in", values, "item125");
            return (Criteria) this;
        }

        public Criteria andItem125NotIn(List<String> values) {
            addCriterion("item12_5 not in", values, "item125");
            return (Criteria) this;
        }

        public Criteria andItem125Between(String value1, String value2) {
            addCriterion("item12_5 between", value1, value2, "item125");
            return (Criteria) this;
        }

        public Criteria andItem125NotBetween(String value1, String value2) {
            addCriterion("item12_5 not between", value1, value2, "item125");
            return (Criteria) this;
        }

        public Criteria andItem131IsNull() {
            addCriterion("item13_1 is null");
            return (Criteria) this;
        }

        public Criteria andItem131IsNotNull() {
            addCriterion("item13_1 is not null");
            return (Criteria) this;
        }

        public Criteria andItem131EqualTo(Integer value) {
            addCriterion("item13_1 =", value, "item131");
            return (Criteria) this;
        }

        public Criteria andItem131NotEqualTo(Integer value) {
            addCriterion("item13_1 <>", value, "item131");
            return (Criteria) this;
        }

        public Criteria andItem131GreaterThan(Integer value) {
            addCriterion("item13_1 >", value, "item131");
            return (Criteria) this;
        }

        public Criteria andItem131GreaterThanOrEqualTo(Integer value) {
            addCriterion("item13_1 >=", value, "item131");
            return (Criteria) this;
        }

        public Criteria andItem131LessThan(Integer value) {
            addCriterion("item13_1 <", value, "item131");
            return (Criteria) this;
        }

        public Criteria andItem131LessThanOrEqualTo(Integer value) {
            addCriterion("item13_1 <=", value, "item131");
            return (Criteria) this;
        }

        public Criteria andItem131In(List<Integer> values) {
            addCriterion("item13_1 in", values, "item131");
            return (Criteria) this;
        }

        public Criteria andItem131NotIn(List<Integer> values) {
            addCriterion("item13_1 not in", values, "item131");
            return (Criteria) this;
        }

        public Criteria andItem131Between(Integer value1, Integer value2) {
            addCriterion("item13_1 between", value1, value2, "item131");
            return (Criteria) this;
        }

        public Criteria andItem131NotBetween(Integer value1, Integer value2) {
            addCriterion("item13_1 not between", value1, value2, "item131");
            return (Criteria) this;
        }

        public Criteria andItem132IsNull() {
            addCriterion("item13_2 is null");
            return (Criteria) this;
        }

        public Criteria andItem132IsNotNull() {
            addCriterion("item13_2 is not null");
            return (Criteria) this;
        }

        public Criteria andItem132EqualTo(Integer value) {
            addCriterion("item13_2 =", value, "item132");
            return (Criteria) this;
        }

        public Criteria andItem132NotEqualTo(Integer value) {
            addCriterion("item13_2 <>", value, "item132");
            return (Criteria) this;
        }

        public Criteria andItem132GreaterThan(Integer value) {
            addCriterion("item13_2 >", value, "item132");
            return (Criteria) this;
        }

        public Criteria andItem132GreaterThanOrEqualTo(Integer value) {
            addCriterion("item13_2 >=", value, "item132");
            return (Criteria) this;
        }

        public Criteria andItem132LessThan(Integer value) {
            addCriterion("item13_2 <", value, "item132");
            return (Criteria) this;
        }

        public Criteria andItem132LessThanOrEqualTo(Integer value) {
            addCriterion("item13_2 <=", value, "item132");
            return (Criteria) this;
        }

        public Criteria andItem132In(List<Integer> values) {
            addCriterion("item13_2 in", values, "item132");
            return (Criteria) this;
        }

        public Criteria andItem132NotIn(List<Integer> values) {
            addCriterion("item13_2 not in", values, "item132");
            return (Criteria) this;
        }

        public Criteria andItem132Between(Integer value1, Integer value2) {
            addCriterion("item13_2 between", value1, value2, "item132");
            return (Criteria) this;
        }

        public Criteria andItem132NotBetween(Integer value1, Integer value2) {
            addCriterion("item13_2 not between", value1, value2, "item132");
            return (Criteria) this;
        }

        public Criteria andItem133IsNull() {
            addCriterion("item13_3 is null");
            return (Criteria) this;
        }

        public Criteria andItem133IsNotNull() {
            addCriterion("item13_3 is not null");
            return (Criteria) this;
        }

        public Criteria andItem133EqualTo(Integer value) {
            addCriterion("item13_3 =", value, "item133");
            return (Criteria) this;
        }

        public Criteria andItem133NotEqualTo(Integer value) {
            addCriterion("item13_3 <>", value, "item133");
            return (Criteria) this;
        }

        public Criteria andItem133GreaterThan(Integer value) {
            addCriterion("item13_3 >", value, "item133");
            return (Criteria) this;
        }

        public Criteria andItem133GreaterThanOrEqualTo(Integer value) {
            addCriterion("item13_3 >=", value, "item133");
            return (Criteria) this;
        }

        public Criteria andItem133LessThan(Integer value) {
            addCriterion("item13_3 <", value, "item133");
            return (Criteria) this;
        }

        public Criteria andItem133LessThanOrEqualTo(Integer value) {
            addCriterion("item13_3 <=", value, "item133");
            return (Criteria) this;
        }

        public Criteria andItem133In(List<Integer> values) {
            addCriterion("item13_3 in", values, "item133");
            return (Criteria) this;
        }

        public Criteria andItem133NotIn(List<Integer> values) {
            addCriterion("item13_3 not in", values, "item133");
            return (Criteria) this;
        }

        public Criteria andItem133Between(Integer value1, Integer value2) {
            addCriterion("item13_3 between", value1, value2, "item133");
            return (Criteria) this;
        }

        public Criteria andItem133NotBetween(Integer value1, Integer value2) {
            addCriterion("item13_3 not between", value1, value2, "item133");
            return (Criteria) this;
        }

        public Criteria andItem134IsNull() {
            addCriterion("item13_4 is null");
            return (Criteria) this;
        }

        public Criteria andItem134IsNotNull() {
            addCriterion("item13_4 is not null");
            return (Criteria) this;
        }

        public Criteria andItem134EqualTo(Integer value) {
            addCriterion("item13_4 =", value, "item134");
            return (Criteria) this;
        }

        public Criteria andItem134NotEqualTo(Integer value) {
            addCriterion("item13_4 <>", value, "item134");
            return (Criteria) this;
        }

        public Criteria andItem134GreaterThan(Integer value) {
            addCriterion("item13_4 >", value, "item134");
            return (Criteria) this;
        }

        public Criteria andItem134GreaterThanOrEqualTo(Integer value) {
            addCriterion("item13_4 >=", value, "item134");
            return (Criteria) this;
        }

        public Criteria andItem134LessThan(Integer value) {
            addCriterion("item13_4 <", value, "item134");
            return (Criteria) this;
        }

        public Criteria andItem134LessThanOrEqualTo(Integer value) {
            addCriterion("item13_4 <=", value, "item134");
            return (Criteria) this;
        }

        public Criteria andItem134In(List<Integer> values) {
            addCriterion("item13_4 in", values, "item134");
            return (Criteria) this;
        }

        public Criteria andItem134NotIn(List<Integer> values) {
            addCriterion("item13_4 not in", values, "item134");
            return (Criteria) this;
        }

        public Criteria andItem134Between(Integer value1, Integer value2) {
            addCriterion("item13_4 between", value1, value2, "item134");
            return (Criteria) this;
        }

        public Criteria andItem134NotBetween(Integer value1, Integer value2) {
            addCriterion("item13_4 not between", value1, value2, "item134");
            return (Criteria) this;
        }

        public Criteria andItem135IsNull() {
            addCriterion("item13_5 is null");
            return (Criteria) this;
        }

        public Criteria andItem135IsNotNull() {
            addCriterion("item13_5 is not null");
            return (Criteria) this;
        }

        public Criteria andItem135EqualTo(Integer value) {
            addCriterion("item13_5 =", value, "item135");
            return (Criteria) this;
        }

        public Criteria andItem135NotEqualTo(Integer value) {
            addCriterion("item13_5 <>", value, "item135");
            return (Criteria) this;
        }

        public Criteria andItem135GreaterThan(Integer value) {
            addCriterion("item13_5 >", value, "item135");
            return (Criteria) this;
        }

        public Criteria andItem135GreaterThanOrEqualTo(Integer value) {
            addCriterion("item13_5 >=", value, "item135");
            return (Criteria) this;
        }

        public Criteria andItem135LessThan(Integer value) {
            addCriterion("item13_5 <", value, "item135");
            return (Criteria) this;
        }

        public Criteria andItem135LessThanOrEqualTo(Integer value) {
            addCriterion("item13_5 <=", value, "item135");
            return (Criteria) this;
        }

        public Criteria andItem135In(List<Integer> values) {
            addCriterion("item13_5 in", values, "item135");
            return (Criteria) this;
        }

        public Criteria andItem135NotIn(List<Integer> values) {
            addCriterion("item13_5 not in", values, "item135");
            return (Criteria) this;
        }

        public Criteria andItem135Between(Integer value1, Integer value2) {
            addCriterion("item13_5 between", value1, value2, "item135");
            return (Criteria) this;
        }

        public Criteria andItem135NotBetween(Integer value1, Integer value2) {
            addCriterion("item13_5 not between", value1, value2, "item135");
            return (Criteria) this;
        }

        public Criteria andItem136IsNull() {
            addCriterion("item13_6 is null");
            return (Criteria) this;
        }

        public Criteria andItem136IsNotNull() {
            addCriterion("item13_6 is not null");
            return (Criteria) this;
        }

        public Criteria andItem136EqualTo(Integer value) {
            addCriterion("item13_6 =", value, "item136");
            return (Criteria) this;
        }

        public Criteria andItem136NotEqualTo(Integer value) {
            addCriterion("item13_6 <>", value, "item136");
            return (Criteria) this;
        }

        public Criteria andItem136GreaterThan(Integer value) {
            addCriterion("item13_6 >", value, "item136");
            return (Criteria) this;
        }

        public Criteria andItem136GreaterThanOrEqualTo(Integer value) {
            addCriterion("item13_6 >=", value, "item136");
            return (Criteria) this;
        }

        public Criteria andItem136LessThan(Integer value) {
            addCriterion("item13_6 <", value, "item136");
            return (Criteria) this;
        }

        public Criteria andItem136LessThanOrEqualTo(Integer value) {
            addCriterion("item13_6 <=", value, "item136");
            return (Criteria) this;
        }

        public Criteria andItem136In(List<Integer> values) {
            addCriterion("item13_6 in", values, "item136");
            return (Criteria) this;
        }

        public Criteria andItem136NotIn(List<Integer> values) {
            addCriterion("item13_6 not in", values, "item136");
            return (Criteria) this;
        }

        public Criteria andItem136Between(Integer value1, Integer value2) {
            addCriterion("item13_6 between", value1, value2, "item136");
            return (Criteria) this;
        }

        public Criteria andItem136NotBetween(Integer value1, Integer value2) {
            addCriterion("item13_6 not between", value1, value2, "item136");
            return (Criteria) this;
        }

        public Criteria andItem137IsNull() {
            addCriterion("item13_7 is null");
            return (Criteria) this;
        }

        public Criteria andItem137IsNotNull() {
            addCriterion("item13_7 is not null");
            return (Criteria) this;
        }

        public Criteria andItem137EqualTo(Integer value) {
            addCriterion("item13_7 =", value, "item137");
            return (Criteria) this;
        }

        public Criteria andItem137NotEqualTo(Integer value) {
            addCriterion("item13_7 <>", value, "item137");
            return (Criteria) this;
        }

        public Criteria andItem137GreaterThan(Integer value) {
            addCriterion("item13_7 >", value, "item137");
            return (Criteria) this;
        }

        public Criteria andItem137GreaterThanOrEqualTo(Integer value) {
            addCriterion("item13_7 >=", value, "item137");
            return (Criteria) this;
        }

        public Criteria andItem137LessThan(Integer value) {
            addCriterion("item13_7 <", value, "item137");
            return (Criteria) this;
        }

        public Criteria andItem137LessThanOrEqualTo(Integer value) {
            addCriterion("item13_7 <=", value, "item137");
            return (Criteria) this;
        }

        public Criteria andItem137In(List<Integer> values) {
            addCriterion("item13_7 in", values, "item137");
            return (Criteria) this;
        }

        public Criteria andItem137NotIn(List<Integer> values) {
            addCriterion("item13_7 not in", values, "item137");
            return (Criteria) this;
        }

        public Criteria andItem137Between(Integer value1, Integer value2) {
            addCriterion("item13_7 between", value1, value2, "item137");
            return (Criteria) this;
        }

        public Criteria andItem137NotBetween(Integer value1, Integer value2) {
            addCriterion("item13_7 not between", value1, value2, "item137");
            return (Criteria) this;
        }

        public Criteria andItem138IsNull() {
            addCriterion("item13_8 is null");
            return (Criteria) this;
        }

        public Criteria andItem138IsNotNull() {
            addCriterion("item13_8 is not null");
            return (Criteria) this;
        }

        public Criteria andItem138EqualTo(Integer value) {
            addCriterion("item13_8 =", value, "item138");
            return (Criteria) this;
        }

        public Criteria andItem138NotEqualTo(Integer value) {
            addCriterion("item13_8 <>", value, "item138");
            return (Criteria) this;
        }

        public Criteria andItem138GreaterThan(Integer value) {
            addCriterion("item13_8 >", value, "item138");
            return (Criteria) this;
        }

        public Criteria andItem138GreaterThanOrEqualTo(Integer value) {
            addCriterion("item13_8 >=", value, "item138");
            return (Criteria) this;
        }

        public Criteria andItem138LessThan(Integer value) {
            addCriterion("item13_8 <", value, "item138");
            return (Criteria) this;
        }

        public Criteria andItem138LessThanOrEqualTo(Integer value) {
            addCriterion("item13_8 <=", value, "item138");
            return (Criteria) this;
        }

        public Criteria andItem138In(List<Integer> values) {
            addCriterion("item13_8 in", values, "item138");
            return (Criteria) this;
        }

        public Criteria andItem138NotIn(List<Integer> values) {
            addCriterion("item13_8 not in", values, "item138");
            return (Criteria) this;
        }

        public Criteria andItem138Between(Integer value1, Integer value2) {
            addCriterion("item13_8 between", value1, value2, "item138");
            return (Criteria) this;
        }

        public Criteria andItem138NotBetween(Integer value1, Integer value2) {
            addCriterion("item13_8 not between", value1, value2, "item138");
            return (Criteria) this;
        }

        public Criteria andItem139IsNull() {
            addCriterion("item13_9 is null");
            return (Criteria) this;
        }

        public Criteria andItem139IsNotNull() {
            addCriterion("item13_9 is not null");
            return (Criteria) this;
        }

        public Criteria andItem139EqualTo(Integer value) {
            addCriterion("item13_9 =", value, "item139");
            return (Criteria) this;
        }

        public Criteria andItem139NotEqualTo(Integer value) {
            addCriterion("item13_9 <>", value, "item139");
            return (Criteria) this;
        }

        public Criteria andItem139GreaterThan(Integer value) {
            addCriterion("item13_9 >", value, "item139");
            return (Criteria) this;
        }

        public Criteria andItem139GreaterThanOrEqualTo(Integer value) {
            addCriterion("item13_9 >=", value, "item139");
            return (Criteria) this;
        }

        public Criteria andItem139LessThan(Integer value) {
            addCriterion("item13_9 <", value, "item139");
            return (Criteria) this;
        }

        public Criteria andItem139LessThanOrEqualTo(Integer value) {
            addCriterion("item13_9 <=", value, "item139");
            return (Criteria) this;
        }

        public Criteria andItem139In(List<Integer> values) {
            addCriterion("item13_9 in", values, "item139");
            return (Criteria) this;
        }

        public Criteria andItem139NotIn(List<Integer> values) {
            addCriterion("item13_9 not in", values, "item139");
            return (Criteria) this;
        }

        public Criteria andItem139Between(Integer value1, Integer value2) {
            addCriterion("item13_9 between", value1, value2, "item139");
            return (Criteria) this;
        }

        public Criteria andItem139NotBetween(Integer value1, Integer value2) {
            addCriterion("item13_9 not between", value1, value2, "item139");
            return (Criteria) this;
        }

        public Criteria andItem1310IsNull() {
            addCriterion("item13_10 is null");
            return (Criteria) this;
        }

        public Criteria andItem1310IsNotNull() {
            addCriterion("item13_10 is not null");
            return (Criteria) this;
        }

        public Criteria andItem1310EqualTo(Integer value) {
            addCriterion("item13_10 =", value, "item1310");
            return (Criteria) this;
        }

        public Criteria andItem1310NotEqualTo(Integer value) {
            addCriterion("item13_10 <>", value, "item1310");
            return (Criteria) this;
        }

        public Criteria andItem1310GreaterThan(Integer value) {
            addCriterion("item13_10 >", value, "item1310");
            return (Criteria) this;
        }

        public Criteria andItem1310GreaterThanOrEqualTo(Integer value) {
            addCriterion("item13_10 >=", value, "item1310");
            return (Criteria) this;
        }

        public Criteria andItem1310LessThan(Integer value) {
            addCriterion("item13_10 <", value, "item1310");
            return (Criteria) this;
        }

        public Criteria andItem1310LessThanOrEqualTo(Integer value) {
            addCriterion("item13_10 <=", value, "item1310");
            return (Criteria) this;
        }

        public Criteria andItem1310In(List<Integer> values) {
            addCriterion("item13_10 in", values, "item1310");
            return (Criteria) this;
        }

        public Criteria andItem1310NotIn(List<Integer> values) {
            addCriterion("item13_10 not in", values, "item1310");
            return (Criteria) this;
        }

        public Criteria andItem1310Between(Integer value1, Integer value2) {
            addCriterion("item13_10 between", value1, value2, "item1310");
            return (Criteria) this;
        }

        public Criteria andItem1310NotBetween(Integer value1, Integer value2) {
            addCriterion("item13_10 not between", value1, value2, "item1310");
            return (Criteria) this;
        }

        public Criteria andItem1311IsNull() {
            addCriterion("item13_11 is null");
            return (Criteria) this;
        }

        public Criteria andItem1311IsNotNull() {
            addCriterion("item13_11 is not null");
            return (Criteria) this;
        }

        public Criteria andItem1311EqualTo(String value) {
            addCriterion("item13_11 =", value, "item1311");
            return (Criteria) this;
        }

        public Criteria andItem1311NotEqualTo(String value) {
            addCriterion("item13_11 <>", value, "item1311");
            return (Criteria) this;
        }

        public Criteria andItem1311GreaterThan(String value) {
            addCriterion("item13_11 >", value, "item1311");
            return (Criteria) this;
        }

        public Criteria andItem1311GreaterThanOrEqualTo(String value) {
            addCriterion("item13_11 >=", value, "item1311");
            return (Criteria) this;
        }

        public Criteria andItem1311LessThan(String value) {
            addCriterion("item13_11 <", value, "item1311");
            return (Criteria) this;
        }

        public Criteria andItem1311LessThanOrEqualTo(String value) {
            addCriterion("item13_11 <=", value, "item1311");
            return (Criteria) this;
        }

        public Criteria andItem1311Like(String value) {
            addCriterion("item13_11 like", value, "item1311");
            return (Criteria) this;
        }

        public Criteria andItem1311NotLike(String value) {
            addCriterion("item13_11 not like", value, "item1311");
            return (Criteria) this;
        }

        public Criteria andItem1311In(List<String> values) {
            addCriterion("item13_11 in", values, "item1311");
            return (Criteria) this;
        }

        public Criteria andItem1311NotIn(List<String> values) {
            addCriterion("item13_11 not in", values, "item1311");
            return (Criteria) this;
        }

        public Criteria andItem1311Between(String value1, String value2) {
            addCriterion("item13_11 between", value1, value2, "item1311");
            return (Criteria) this;
        }

        public Criteria andItem1311NotBetween(String value1, String value2) {
            addCriterion("item13_11 not between", value1, value2, "item1311");
            return (Criteria) this;
        }

        public Criteria andItem141IsNull() {
            addCriterion("item14_1 is null");
            return (Criteria) this;
        }

        public Criteria andItem141IsNotNull() {
            addCriterion("item14_1 is not null");
            return (Criteria) this;
        }

        public Criteria andItem141EqualTo(Integer value) {
            addCriterion("item14_1 =", value, "item141");
            return (Criteria) this;
        }

        public Criteria andItem141NotEqualTo(Integer value) {
            addCriterion("item14_1 <>", value, "item141");
            return (Criteria) this;
        }

        public Criteria andItem141GreaterThan(Integer value) {
            addCriterion("item14_1 >", value, "item141");
            return (Criteria) this;
        }

        public Criteria andItem141GreaterThanOrEqualTo(Integer value) {
            addCriterion("item14_1 >=", value, "item141");
            return (Criteria) this;
        }

        public Criteria andItem141LessThan(Integer value) {
            addCriterion("item14_1 <", value, "item141");
            return (Criteria) this;
        }

        public Criteria andItem141LessThanOrEqualTo(Integer value) {
            addCriterion("item14_1 <=", value, "item141");
            return (Criteria) this;
        }

        public Criteria andItem141In(List<Integer> values) {
            addCriterion("item14_1 in", values, "item141");
            return (Criteria) this;
        }

        public Criteria andItem141NotIn(List<Integer> values) {
            addCriterion("item14_1 not in", values, "item141");
            return (Criteria) this;
        }

        public Criteria andItem141Between(Integer value1, Integer value2) {
            addCriterion("item14_1 between", value1, value2, "item141");
            return (Criteria) this;
        }

        public Criteria andItem141NotBetween(Integer value1, Integer value2) {
            addCriterion("item14_1 not between", value1, value2, "item141");
            return (Criteria) this;
        }

        public Criteria andItem142IsNull() {
            addCriterion("item14_2 is null");
            return (Criteria) this;
        }

        public Criteria andItem142IsNotNull() {
            addCriterion("item14_2 is not null");
            return (Criteria) this;
        }

        public Criteria andItem142EqualTo(Integer value) {
            addCriterion("item14_2 =", value, "item142");
            return (Criteria) this;
        }

        public Criteria andItem142NotEqualTo(Integer value) {
            addCriterion("item14_2 <>", value, "item142");
            return (Criteria) this;
        }

        public Criteria andItem142GreaterThan(Integer value) {
            addCriterion("item14_2 >", value, "item142");
            return (Criteria) this;
        }

        public Criteria andItem142GreaterThanOrEqualTo(Integer value) {
            addCriterion("item14_2 >=", value, "item142");
            return (Criteria) this;
        }

        public Criteria andItem142LessThan(Integer value) {
            addCriterion("item14_2 <", value, "item142");
            return (Criteria) this;
        }

        public Criteria andItem142LessThanOrEqualTo(Integer value) {
            addCriterion("item14_2 <=", value, "item142");
            return (Criteria) this;
        }

        public Criteria andItem142In(List<Integer> values) {
            addCriterion("item14_2 in", values, "item142");
            return (Criteria) this;
        }

        public Criteria andItem142NotIn(List<Integer> values) {
            addCriterion("item14_2 not in", values, "item142");
            return (Criteria) this;
        }

        public Criteria andItem142Between(Integer value1, Integer value2) {
            addCriterion("item14_2 between", value1, value2, "item142");
            return (Criteria) this;
        }

        public Criteria andItem142NotBetween(Integer value1, Integer value2) {
            addCriterion("item14_2 not between", value1, value2, "item142");
            return (Criteria) this;
        }

        public Criteria andItem143IsNull() {
            addCriterion("item14_3 is null");
            return (Criteria) this;
        }

        public Criteria andItem143IsNotNull() {
            addCriterion("item14_3 is not null");
            return (Criteria) this;
        }

        public Criteria andItem143EqualTo(Integer value) {
            addCriterion("item14_3 =", value, "item143");
            return (Criteria) this;
        }

        public Criteria andItem143NotEqualTo(Integer value) {
            addCriterion("item14_3 <>", value, "item143");
            return (Criteria) this;
        }

        public Criteria andItem143GreaterThan(Integer value) {
            addCriterion("item14_3 >", value, "item143");
            return (Criteria) this;
        }

        public Criteria andItem143GreaterThanOrEqualTo(Integer value) {
            addCriterion("item14_3 >=", value, "item143");
            return (Criteria) this;
        }

        public Criteria andItem143LessThan(Integer value) {
            addCriterion("item14_3 <", value, "item143");
            return (Criteria) this;
        }

        public Criteria andItem143LessThanOrEqualTo(Integer value) {
            addCriterion("item14_3 <=", value, "item143");
            return (Criteria) this;
        }

        public Criteria andItem143In(List<Integer> values) {
            addCriterion("item14_3 in", values, "item143");
            return (Criteria) this;
        }

        public Criteria andItem143NotIn(List<Integer> values) {
            addCriterion("item14_3 not in", values, "item143");
            return (Criteria) this;
        }

        public Criteria andItem143Between(Integer value1, Integer value2) {
            addCriterion("item14_3 between", value1, value2, "item143");
            return (Criteria) this;
        }

        public Criteria andItem143NotBetween(Integer value1, Integer value2) {
            addCriterion("item14_3 not between", value1, value2, "item143");
            return (Criteria) this;
        }

        public Criteria andItem144IsNull() {
            addCriterion("item14_4 is null");
            return (Criteria) this;
        }

        public Criteria andItem144IsNotNull() {
            addCriterion("item14_4 is not null");
            return (Criteria) this;
        }

        public Criteria andItem144EqualTo(Integer value) {
            addCriterion("item14_4 =", value, "item144");
            return (Criteria) this;
        }

        public Criteria andItem144NotEqualTo(Integer value) {
            addCriterion("item14_4 <>", value, "item144");
            return (Criteria) this;
        }

        public Criteria andItem144GreaterThan(Integer value) {
            addCriterion("item14_4 >", value, "item144");
            return (Criteria) this;
        }

        public Criteria andItem144GreaterThanOrEqualTo(Integer value) {
            addCriterion("item14_4 >=", value, "item144");
            return (Criteria) this;
        }

        public Criteria andItem144LessThan(Integer value) {
            addCriterion("item14_4 <", value, "item144");
            return (Criteria) this;
        }

        public Criteria andItem144LessThanOrEqualTo(Integer value) {
            addCriterion("item14_4 <=", value, "item144");
            return (Criteria) this;
        }

        public Criteria andItem144In(List<Integer> values) {
            addCriterion("item14_4 in", values, "item144");
            return (Criteria) this;
        }

        public Criteria andItem144NotIn(List<Integer> values) {
            addCriterion("item14_4 not in", values, "item144");
            return (Criteria) this;
        }

        public Criteria andItem144Between(Integer value1, Integer value2) {
            addCriterion("item14_4 between", value1, value2, "item144");
            return (Criteria) this;
        }

        public Criteria andItem144NotBetween(Integer value1, Integer value2) {
            addCriterion("item14_4 not between", value1, value2, "item144");
            return (Criteria) this;
        }

        public Criteria andItem145IsNull() {
            addCriterion("item14_5 is null");
            return (Criteria) this;
        }

        public Criteria andItem145IsNotNull() {
            addCriterion("item14_5 is not null");
            return (Criteria) this;
        }

        public Criteria andItem145EqualTo(Integer value) {
            addCriterion("item14_5 =", value, "item145");
            return (Criteria) this;
        }

        public Criteria andItem145NotEqualTo(Integer value) {
            addCriterion("item14_5 <>", value, "item145");
            return (Criteria) this;
        }

        public Criteria andItem145GreaterThan(Integer value) {
            addCriterion("item14_5 >", value, "item145");
            return (Criteria) this;
        }

        public Criteria andItem145GreaterThanOrEqualTo(Integer value) {
            addCriterion("item14_5 >=", value, "item145");
            return (Criteria) this;
        }

        public Criteria andItem145LessThan(Integer value) {
            addCriterion("item14_5 <", value, "item145");
            return (Criteria) this;
        }

        public Criteria andItem145LessThanOrEqualTo(Integer value) {
            addCriterion("item14_5 <=", value, "item145");
            return (Criteria) this;
        }

        public Criteria andItem145In(List<Integer> values) {
            addCriterion("item14_5 in", values, "item145");
            return (Criteria) this;
        }

        public Criteria andItem145NotIn(List<Integer> values) {
            addCriterion("item14_5 not in", values, "item145");
            return (Criteria) this;
        }

        public Criteria andItem145Between(Integer value1, Integer value2) {
            addCriterion("item14_5 between", value1, value2, "item145");
            return (Criteria) this;
        }

        public Criteria andItem145NotBetween(Integer value1, Integer value2) {
            addCriterion("item14_5 not between", value1, value2, "item145");
            return (Criteria) this;
        }

        public Criteria andItem146IsNull() {
            addCriterion("item14_6 is null");
            return (Criteria) this;
        }

        public Criteria andItem146IsNotNull() {
            addCriterion("item14_6 is not null");
            return (Criteria) this;
        }

        public Criteria andItem146EqualTo(Integer value) {
            addCriterion("item14_6 =", value, "item146");
            return (Criteria) this;
        }

        public Criteria andItem146NotEqualTo(Integer value) {
            addCriterion("item14_6 <>", value, "item146");
            return (Criteria) this;
        }

        public Criteria andItem146GreaterThan(Integer value) {
            addCriterion("item14_6 >", value, "item146");
            return (Criteria) this;
        }

        public Criteria andItem146GreaterThanOrEqualTo(Integer value) {
            addCriterion("item14_6 >=", value, "item146");
            return (Criteria) this;
        }

        public Criteria andItem146LessThan(Integer value) {
            addCriterion("item14_6 <", value, "item146");
            return (Criteria) this;
        }

        public Criteria andItem146LessThanOrEqualTo(Integer value) {
            addCriterion("item14_6 <=", value, "item146");
            return (Criteria) this;
        }

        public Criteria andItem146In(List<Integer> values) {
            addCriterion("item14_6 in", values, "item146");
            return (Criteria) this;
        }

        public Criteria andItem146NotIn(List<Integer> values) {
            addCriterion("item14_6 not in", values, "item146");
            return (Criteria) this;
        }

        public Criteria andItem146Between(Integer value1, Integer value2) {
            addCriterion("item14_6 between", value1, value2, "item146");
            return (Criteria) this;
        }

        public Criteria andItem146NotBetween(Integer value1, Integer value2) {
            addCriterion("item14_6 not between", value1, value2, "item146");
            return (Criteria) this;
        }

        public Criteria andItem147IsNull() {
            addCriterion("item14_7 is null");
            return (Criteria) this;
        }

        public Criteria andItem147IsNotNull() {
            addCriterion("item14_7 is not null");
            return (Criteria) this;
        }

        public Criteria andItem147EqualTo(Integer value) {
            addCriterion("item14_7 =", value, "item147");
            return (Criteria) this;
        }

        public Criteria andItem147NotEqualTo(Integer value) {
            addCriterion("item14_7 <>", value, "item147");
            return (Criteria) this;
        }

        public Criteria andItem147GreaterThan(Integer value) {
            addCriterion("item14_7 >", value, "item147");
            return (Criteria) this;
        }

        public Criteria andItem147GreaterThanOrEqualTo(Integer value) {
            addCriterion("item14_7 >=", value, "item147");
            return (Criteria) this;
        }

        public Criteria andItem147LessThan(Integer value) {
            addCriterion("item14_7 <", value, "item147");
            return (Criteria) this;
        }

        public Criteria andItem147LessThanOrEqualTo(Integer value) {
            addCriterion("item14_7 <=", value, "item147");
            return (Criteria) this;
        }

        public Criteria andItem147In(List<Integer> values) {
            addCriterion("item14_7 in", values, "item147");
            return (Criteria) this;
        }

        public Criteria andItem147NotIn(List<Integer> values) {
            addCriterion("item14_7 not in", values, "item147");
            return (Criteria) this;
        }

        public Criteria andItem147Between(Integer value1, Integer value2) {
            addCriterion("item14_7 between", value1, value2, "item147");
            return (Criteria) this;
        }

        public Criteria andItem147NotBetween(Integer value1, Integer value2) {
            addCriterion("item14_7 not between", value1, value2, "item147");
            return (Criteria) this;
        }

        public Criteria andItem148IsNull() {
            addCriterion("item14_8 is null");
            return (Criteria) this;
        }

        public Criteria andItem148IsNotNull() {
            addCriterion("item14_8 is not null");
            return (Criteria) this;
        }

        public Criteria andItem148EqualTo(Integer value) {
            addCriterion("item14_8 =", value, "item148");
            return (Criteria) this;
        }

        public Criteria andItem148NotEqualTo(Integer value) {
            addCriterion("item14_8 <>", value, "item148");
            return (Criteria) this;
        }

        public Criteria andItem148GreaterThan(Integer value) {
            addCriterion("item14_8 >", value, "item148");
            return (Criteria) this;
        }

        public Criteria andItem148GreaterThanOrEqualTo(Integer value) {
            addCriterion("item14_8 >=", value, "item148");
            return (Criteria) this;
        }

        public Criteria andItem148LessThan(Integer value) {
            addCriterion("item14_8 <", value, "item148");
            return (Criteria) this;
        }

        public Criteria andItem148LessThanOrEqualTo(Integer value) {
            addCriterion("item14_8 <=", value, "item148");
            return (Criteria) this;
        }

        public Criteria andItem148In(List<Integer> values) {
            addCriterion("item14_8 in", values, "item148");
            return (Criteria) this;
        }

        public Criteria andItem148NotIn(List<Integer> values) {
            addCriterion("item14_8 not in", values, "item148");
            return (Criteria) this;
        }

        public Criteria andItem148Between(Integer value1, Integer value2) {
            addCriterion("item14_8 between", value1, value2, "item148");
            return (Criteria) this;
        }

        public Criteria andItem148NotBetween(Integer value1, Integer value2) {
            addCriterion("item14_8 not between", value1, value2, "item148");
            return (Criteria) this;
        }

        public Criteria andItem149IsNull() {
            addCriterion("item14_9 is null");
            return (Criteria) this;
        }

        public Criteria andItem149IsNotNull() {
            addCriterion("item14_9 is not null");
            return (Criteria) this;
        }

        public Criteria andItem149EqualTo(Integer value) {
            addCriterion("item14_9 =", value, "item149");
            return (Criteria) this;
        }

        public Criteria andItem149NotEqualTo(Integer value) {
            addCriterion("item14_9 <>", value, "item149");
            return (Criteria) this;
        }

        public Criteria andItem149GreaterThan(Integer value) {
            addCriterion("item14_9 >", value, "item149");
            return (Criteria) this;
        }

        public Criteria andItem149GreaterThanOrEqualTo(Integer value) {
            addCriterion("item14_9 >=", value, "item149");
            return (Criteria) this;
        }

        public Criteria andItem149LessThan(Integer value) {
            addCriterion("item14_9 <", value, "item149");
            return (Criteria) this;
        }

        public Criteria andItem149LessThanOrEqualTo(Integer value) {
            addCriterion("item14_9 <=", value, "item149");
            return (Criteria) this;
        }

        public Criteria andItem149In(List<Integer> values) {
            addCriterion("item14_9 in", values, "item149");
            return (Criteria) this;
        }

        public Criteria andItem149NotIn(List<Integer> values) {
            addCriterion("item14_9 not in", values, "item149");
            return (Criteria) this;
        }

        public Criteria andItem149Between(Integer value1, Integer value2) {
            addCriterion("item14_9 between", value1, value2, "item149");
            return (Criteria) this;
        }

        public Criteria andItem149NotBetween(Integer value1, Integer value2) {
            addCriterion("item14_9 not between", value1, value2, "item149");
            return (Criteria) this;
        }

        public Criteria andItem1410IsNull() {
            addCriterion("item14_10 is null");
            return (Criteria) this;
        }

        public Criteria andItem1410IsNotNull() {
            addCriterion("item14_10 is not null");
            return (Criteria) this;
        }

        public Criteria andItem1410EqualTo(String value) {
            addCriterion("item14_10 =", value, "item1410");
            return (Criteria) this;
        }

        public Criteria andItem1410NotEqualTo(String value) {
            addCriterion("item14_10 <>", value, "item1410");
            return (Criteria) this;
        }

        public Criteria andItem1410GreaterThan(String value) {
            addCriterion("item14_10 >", value, "item1410");
            return (Criteria) this;
        }

        public Criteria andItem1410GreaterThanOrEqualTo(String value) {
            addCriterion("item14_10 >=", value, "item1410");
            return (Criteria) this;
        }

        public Criteria andItem1410LessThan(String value) {
            addCriterion("item14_10 <", value, "item1410");
            return (Criteria) this;
        }

        public Criteria andItem1410LessThanOrEqualTo(String value) {
            addCriterion("item14_10 <=", value, "item1410");
            return (Criteria) this;
        }

        public Criteria andItem1410Like(String value) {
            addCriterion("item14_10 like", value, "item1410");
            return (Criteria) this;
        }

        public Criteria andItem1410NotLike(String value) {
            addCriterion("item14_10 not like", value, "item1410");
            return (Criteria) this;
        }

        public Criteria andItem1410In(List<String> values) {
            addCriterion("item14_10 in", values, "item1410");
            return (Criteria) this;
        }

        public Criteria andItem1410NotIn(List<String> values) {
            addCriterion("item14_10 not in", values, "item1410");
            return (Criteria) this;
        }

        public Criteria andItem1410Between(String value1, String value2) {
            addCriterion("item14_10 between", value1, value2, "item1410");
            return (Criteria) this;
        }

        public Criteria andItem1410NotBetween(String value1, String value2) {
            addCriterion("item14_10 not between", value1, value2, "item1410");
            return (Criteria) this;
        }

        public Criteria andItem151IsNull() {
            addCriterion("item15_1 is null");
            return (Criteria) this;
        }

        public Criteria andItem151IsNotNull() {
            addCriterion("item15_1 is not null");
            return (Criteria) this;
        }

        public Criteria andItem151EqualTo(Integer value) {
            addCriterion("item15_1 =", value, "item151");
            return (Criteria) this;
        }

        public Criteria andItem151NotEqualTo(Integer value) {
            addCriterion("item15_1 <>", value, "item151");
            return (Criteria) this;
        }

        public Criteria andItem151GreaterThan(Integer value) {
            addCriterion("item15_1 >", value, "item151");
            return (Criteria) this;
        }

        public Criteria andItem151GreaterThanOrEqualTo(Integer value) {
            addCriterion("item15_1 >=", value, "item151");
            return (Criteria) this;
        }

        public Criteria andItem151LessThan(Integer value) {
            addCriterion("item15_1 <", value, "item151");
            return (Criteria) this;
        }

        public Criteria andItem151LessThanOrEqualTo(Integer value) {
            addCriterion("item15_1 <=", value, "item151");
            return (Criteria) this;
        }

        public Criteria andItem151In(List<Integer> values) {
            addCriterion("item15_1 in", values, "item151");
            return (Criteria) this;
        }

        public Criteria andItem151NotIn(List<Integer> values) {
            addCriterion("item15_1 not in", values, "item151");
            return (Criteria) this;
        }

        public Criteria andItem151Between(Integer value1, Integer value2) {
            addCriterion("item15_1 between", value1, value2, "item151");
            return (Criteria) this;
        }

        public Criteria andItem151NotBetween(Integer value1, Integer value2) {
            addCriterion("item15_1 not between", value1, value2, "item151");
            return (Criteria) this;
        }

        public Criteria andItem152IsNull() {
            addCriterion("item15_2 is null");
            return (Criteria) this;
        }

        public Criteria andItem152IsNotNull() {
            addCriterion("item15_2 is not null");
            return (Criteria) this;
        }

        public Criteria andItem152EqualTo(Integer value) {
            addCriterion("item15_2 =", value, "item152");
            return (Criteria) this;
        }

        public Criteria andItem152NotEqualTo(Integer value) {
            addCriterion("item15_2 <>", value, "item152");
            return (Criteria) this;
        }

        public Criteria andItem152GreaterThan(Integer value) {
            addCriterion("item15_2 >", value, "item152");
            return (Criteria) this;
        }

        public Criteria andItem152GreaterThanOrEqualTo(Integer value) {
            addCriterion("item15_2 >=", value, "item152");
            return (Criteria) this;
        }

        public Criteria andItem152LessThan(Integer value) {
            addCriterion("item15_2 <", value, "item152");
            return (Criteria) this;
        }

        public Criteria andItem152LessThanOrEqualTo(Integer value) {
            addCriterion("item15_2 <=", value, "item152");
            return (Criteria) this;
        }

        public Criteria andItem152In(List<Integer> values) {
            addCriterion("item15_2 in", values, "item152");
            return (Criteria) this;
        }

        public Criteria andItem152NotIn(List<Integer> values) {
            addCriterion("item15_2 not in", values, "item152");
            return (Criteria) this;
        }

        public Criteria andItem152Between(Integer value1, Integer value2) {
            addCriterion("item15_2 between", value1, value2, "item152");
            return (Criteria) this;
        }

        public Criteria andItem152NotBetween(Integer value1, Integer value2) {
            addCriterion("item15_2 not between", value1, value2, "item152");
            return (Criteria) this;
        }

        public Criteria andItem153IsNull() {
            addCriterion("item15_3 is null");
            return (Criteria) this;
        }

        public Criteria andItem153IsNotNull() {
            addCriterion("item15_3 is not null");
            return (Criteria) this;
        }

        public Criteria andItem153EqualTo(Integer value) {
            addCriterion("item15_3 =", value, "item153");
            return (Criteria) this;
        }

        public Criteria andItem153NotEqualTo(Integer value) {
            addCriterion("item15_3 <>", value, "item153");
            return (Criteria) this;
        }

        public Criteria andItem153GreaterThan(Integer value) {
            addCriterion("item15_3 >", value, "item153");
            return (Criteria) this;
        }

        public Criteria andItem153GreaterThanOrEqualTo(Integer value) {
            addCriterion("item15_3 >=", value, "item153");
            return (Criteria) this;
        }

        public Criteria andItem153LessThan(Integer value) {
            addCriterion("item15_3 <", value, "item153");
            return (Criteria) this;
        }

        public Criteria andItem153LessThanOrEqualTo(Integer value) {
            addCriterion("item15_3 <=", value, "item153");
            return (Criteria) this;
        }

        public Criteria andItem153In(List<Integer> values) {
            addCriterion("item15_3 in", values, "item153");
            return (Criteria) this;
        }

        public Criteria andItem153NotIn(List<Integer> values) {
            addCriterion("item15_3 not in", values, "item153");
            return (Criteria) this;
        }

        public Criteria andItem153Between(Integer value1, Integer value2) {
            addCriterion("item15_3 between", value1, value2, "item153");
            return (Criteria) this;
        }

        public Criteria andItem153NotBetween(Integer value1, Integer value2) {
            addCriterion("item15_3 not between", value1, value2, "item153");
            return (Criteria) this;
        }

        public Criteria andItem154IsNull() {
            addCriterion("item15_4 is null");
            return (Criteria) this;
        }

        public Criteria andItem154IsNotNull() {
            addCriterion("item15_4 is not null");
            return (Criteria) this;
        }

        public Criteria andItem154EqualTo(Integer value) {
            addCriterion("item15_4 =", value, "item154");
            return (Criteria) this;
        }

        public Criteria andItem154NotEqualTo(Integer value) {
            addCriterion("item15_4 <>", value, "item154");
            return (Criteria) this;
        }

        public Criteria andItem154GreaterThan(Integer value) {
            addCriterion("item15_4 >", value, "item154");
            return (Criteria) this;
        }

        public Criteria andItem154GreaterThanOrEqualTo(Integer value) {
            addCriterion("item15_4 >=", value, "item154");
            return (Criteria) this;
        }

        public Criteria andItem154LessThan(Integer value) {
            addCriterion("item15_4 <", value, "item154");
            return (Criteria) this;
        }

        public Criteria andItem154LessThanOrEqualTo(Integer value) {
            addCriterion("item15_4 <=", value, "item154");
            return (Criteria) this;
        }

        public Criteria andItem154In(List<Integer> values) {
            addCriterion("item15_4 in", values, "item154");
            return (Criteria) this;
        }

        public Criteria andItem154NotIn(List<Integer> values) {
            addCriterion("item15_4 not in", values, "item154");
            return (Criteria) this;
        }

        public Criteria andItem154Between(Integer value1, Integer value2) {
            addCriterion("item15_4 between", value1, value2, "item154");
            return (Criteria) this;
        }

        public Criteria andItem154NotBetween(Integer value1, Integer value2) {
            addCriterion("item15_4 not between", value1, value2, "item154");
            return (Criteria) this;
        }

        public Criteria andItem155IsNull() {
            addCriterion("item15_5 is null");
            return (Criteria) this;
        }

        public Criteria andItem155IsNotNull() {
            addCriterion("item15_5 is not null");
            return (Criteria) this;
        }

        public Criteria andItem155EqualTo(Integer value) {
            addCriterion("item15_5 =", value, "item155");
            return (Criteria) this;
        }

        public Criteria andItem155NotEqualTo(Integer value) {
            addCriterion("item15_5 <>", value, "item155");
            return (Criteria) this;
        }

        public Criteria andItem155GreaterThan(Integer value) {
            addCriterion("item15_5 >", value, "item155");
            return (Criteria) this;
        }

        public Criteria andItem155GreaterThanOrEqualTo(Integer value) {
            addCriterion("item15_5 >=", value, "item155");
            return (Criteria) this;
        }

        public Criteria andItem155LessThan(Integer value) {
            addCriterion("item15_5 <", value, "item155");
            return (Criteria) this;
        }

        public Criteria andItem155LessThanOrEqualTo(Integer value) {
            addCriterion("item15_5 <=", value, "item155");
            return (Criteria) this;
        }

        public Criteria andItem155In(List<Integer> values) {
            addCriterion("item15_5 in", values, "item155");
            return (Criteria) this;
        }

        public Criteria andItem155NotIn(List<Integer> values) {
            addCriterion("item15_5 not in", values, "item155");
            return (Criteria) this;
        }

        public Criteria andItem155Between(Integer value1, Integer value2) {
            addCriterion("item15_5 between", value1, value2, "item155");
            return (Criteria) this;
        }

        public Criteria andItem155NotBetween(Integer value1, Integer value2) {
            addCriterion("item15_5 not between", value1, value2, "item155");
            return (Criteria) this;
        }

        public Criteria andItem156IsNull() {
            addCriterion("item15_6 is null");
            return (Criteria) this;
        }

        public Criteria andItem156IsNotNull() {
            addCriterion("item15_6 is not null");
            return (Criteria) this;
        }

        public Criteria andItem156EqualTo(Integer value) {
            addCriterion("item15_6 =", value, "item156");
            return (Criteria) this;
        }

        public Criteria andItem156NotEqualTo(Integer value) {
            addCriterion("item15_6 <>", value, "item156");
            return (Criteria) this;
        }

        public Criteria andItem156GreaterThan(Integer value) {
            addCriterion("item15_6 >", value, "item156");
            return (Criteria) this;
        }

        public Criteria andItem156GreaterThanOrEqualTo(Integer value) {
            addCriterion("item15_6 >=", value, "item156");
            return (Criteria) this;
        }

        public Criteria andItem156LessThan(Integer value) {
            addCriterion("item15_6 <", value, "item156");
            return (Criteria) this;
        }

        public Criteria andItem156LessThanOrEqualTo(Integer value) {
            addCriterion("item15_6 <=", value, "item156");
            return (Criteria) this;
        }

        public Criteria andItem156In(List<Integer> values) {
            addCriterion("item15_6 in", values, "item156");
            return (Criteria) this;
        }

        public Criteria andItem156NotIn(List<Integer> values) {
            addCriterion("item15_6 not in", values, "item156");
            return (Criteria) this;
        }

        public Criteria andItem156Between(Integer value1, Integer value2) {
            addCriterion("item15_6 between", value1, value2, "item156");
            return (Criteria) this;
        }

        public Criteria andItem156NotBetween(Integer value1, Integer value2) {
            addCriterion("item15_6 not between", value1, value2, "item156");
            return (Criteria) this;
        }

        public Criteria andItem16a1IsNull() {
            addCriterion("item16a_1 is null");
            return (Criteria) this;
        }

        public Criteria andItem16a1IsNotNull() {
            addCriterion("item16a_1 is not null");
            return (Criteria) this;
        }

        public Criteria andItem16a1EqualTo(Integer value) {
            addCriterion("item16a_1 =", value, "item16a1");
            return (Criteria) this;
        }

        public Criteria andItem16a1NotEqualTo(Integer value) {
            addCriterion("item16a_1 <>", value, "item16a1");
            return (Criteria) this;
        }

        public Criteria andItem16a1GreaterThan(Integer value) {
            addCriterion("item16a_1 >", value, "item16a1");
            return (Criteria) this;
        }

        public Criteria andItem16a1GreaterThanOrEqualTo(Integer value) {
            addCriterion("item16a_1 >=", value, "item16a1");
            return (Criteria) this;
        }

        public Criteria andItem16a1LessThan(Integer value) {
            addCriterion("item16a_1 <", value, "item16a1");
            return (Criteria) this;
        }

        public Criteria andItem16a1LessThanOrEqualTo(Integer value) {
            addCriterion("item16a_1 <=", value, "item16a1");
            return (Criteria) this;
        }

        public Criteria andItem16a1In(List<Integer> values) {
            addCriterion("item16a_1 in", values, "item16a1");
            return (Criteria) this;
        }

        public Criteria andItem16a1NotIn(List<Integer> values) {
            addCriterion("item16a_1 not in", values, "item16a1");
            return (Criteria) this;
        }

        public Criteria andItem16a1Between(Integer value1, Integer value2) {
            addCriterion("item16a_1 between", value1, value2, "item16a1");
            return (Criteria) this;
        }

        public Criteria andItem16a1NotBetween(Integer value1, Integer value2) {
            addCriterion("item16a_1 not between", value1, value2, "item16a1");
            return (Criteria) this;
        }

        public Criteria andItem16a11IsNull() {
            addCriterion("item16a_1_1 is null");
            return (Criteria) this;
        }

        public Criteria andItem16a11IsNotNull() {
            addCriterion("item16a_1_1 is not null");
            return (Criteria) this;
        }

        public Criteria andItem16a11EqualTo(Integer value) {
            addCriterion("item16a_1_1 =", value, "item16a11");
            return (Criteria) this;
        }

        public Criteria andItem16a11NotEqualTo(Integer value) {
            addCriterion("item16a_1_1 <>", value, "item16a11");
            return (Criteria) this;
        }

        public Criteria andItem16a11GreaterThan(Integer value) {
            addCriterion("item16a_1_1 >", value, "item16a11");
            return (Criteria) this;
        }

        public Criteria andItem16a11GreaterThanOrEqualTo(Integer value) {
            addCriterion("item16a_1_1 >=", value, "item16a11");
            return (Criteria) this;
        }

        public Criteria andItem16a11LessThan(Integer value) {
            addCriterion("item16a_1_1 <", value, "item16a11");
            return (Criteria) this;
        }

        public Criteria andItem16a11LessThanOrEqualTo(Integer value) {
            addCriterion("item16a_1_1 <=", value, "item16a11");
            return (Criteria) this;
        }

        public Criteria andItem16a11In(List<Integer> values) {
            addCriterion("item16a_1_1 in", values, "item16a11");
            return (Criteria) this;
        }

        public Criteria andItem16a11NotIn(List<Integer> values) {
            addCriterion("item16a_1_1 not in", values, "item16a11");
            return (Criteria) this;
        }

        public Criteria andItem16a11Between(Integer value1, Integer value2) {
            addCriterion("item16a_1_1 between", value1, value2, "item16a11");
            return (Criteria) this;
        }

        public Criteria andItem16a11NotBetween(Integer value1, Integer value2) {
            addCriterion("item16a_1_1 not between", value1, value2, "item16a11");
            return (Criteria) this;
        }

        public Criteria andItem16a12IsNull() {
            addCriterion("item16a_1_2 is null");
            return (Criteria) this;
        }

        public Criteria andItem16a12IsNotNull() {
            addCriterion("item16a_1_2 is not null");
            return (Criteria) this;
        }

        public Criteria andItem16a12EqualTo(Integer value) {
            addCriterion("item16a_1_2 =", value, "item16a12");
            return (Criteria) this;
        }

        public Criteria andItem16a12NotEqualTo(Integer value) {
            addCriterion("item16a_1_2 <>", value, "item16a12");
            return (Criteria) this;
        }

        public Criteria andItem16a12GreaterThan(Integer value) {
            addCriterion("item16a_1_2 >", value, "item16a12");
            return (Criteria) this;
        }

        public Criteria andItem16a12GreaterThanOrEqualTo(Integer value) {
            addCriterion("item16a_1_2 >=", value, "item16a12");
            return (Criteria) this;
        }

        public Criteria andItem16a12LessThan(Integer value) {
            addCriterion("item16a_1_2 <", value, "item16a12");
            return (Criteria) this;
        }

        public Criteria andItem16a12LessThanOrEqualTo(Integer value) {
            addCriterion("item16a_1_2 <=", value, "item16a12");
            return (Criteria) this;
        }

        public Criteria andItem16a12In(List<Integer> values) {
            addCriterion("item16a_1_2 in", values, "item16a12");
            return (Criteria) this;
        }

        public Criteria andItem16a12NotIn(List<Integer> values) {
            addCriterion("item16a_1_2 not in", values, "item16a12");
            return (Criteria) this;
        }

        public Criteria andItem16a12Between(Integer value1, Integer value2) {
            addCriterion("item16a_1_2 between", value1, value2, "item16a12");
            return (Criteria) this;
        }

        public Criteria andItem16a12NotBetween(Integer value1, Integer value2) {
            addCriterion("item16a_1_2 not between", value1, value2, "item16a12");
            return (Criteria) this;
        }

        public Criteria andItem16a13IsNull() {
            addCriterion("item16a_1_3 is null");
            return (Criteria) this;
        }

        public Criteria andItem16a13IsNotNull() {
            addCriterion("item16a_1_3 is not null");
            return (Criteria) this;
        }

        public Criteria andItem16a13EqualTo(Integer value) {
            addCriterion("item16a_1_3 =", value, "item16a13");
            return (Criteria) this;
        }

        public Criteria andItem16a13NotEqualTo(Integer value) {
            addCriterion("item16a_1_3 <>", value, "item16a13");
            return (Criteria) this;
        }

        public Criteria andItem16a13GreaterThan(Integer value) {
            addCriterion("item16a_1_3 >", value, "item16a13");
            return (Criteria) this;
        }

        public Criteria andItem16a13GreaterThanOrEqualTo(Integer value) {
            addCriterion("item16a_1_3 >=", value, "item16a13");
            return (Criteria) this;
        }

        public Criteria andItem16a13LessThan(Integer value) {
            addCriterion("item16a_1_3 <", value, "item16a13");
            return (Criteria) this;
        }

        public Criteria andItem16a13LessThanOrEqualTo(Integer value) {
            addCriterion("item16a_1_3 <=", value, "item16a13");
            return (Criteria) this;
        }

        public Criteria andItem16a13In(List<Integer> values) {
            addCriterion("item16a_1_3 in", values, "item16a13");
            return (Criteria) this;
        }

        public Criteria andItem16a13NotIn(List<Integer> values) {
            addCriterion("item16a_1_3 not in", values, "item16a13");
            return (Criteria) this;
        }

        public Criteria andItem16a13Between(Integer value1, Integer value2) {
            addCriterion("item16a_1_3 between", value1, value2, "item16a13");
            return (Criteria) this;
        }

        public Criteria andItem16a13NotBetween(Integer value1, Integer value2) {
            addCriterion("item16a_1_3 not between", value1, value2, "item16a13");
            return (Criteria) this;
        }

        public Criteria andItem16b1IsNull() {
            addCriterion("item16b_1 is null");
            return (Criteria) this;
        }

        public Criteria andItem16b1IsNotNull() {
            addCriterion("item16b_1 is not null");
            return (Criteria) this;
        }

        public Criteria andItem16b1EqualTo(Integer value) {
            addCriterion("item16b_1 =", value, "item16b1");
            return (Criteria) this;
        }

        public Criteria andItem16b1NotEqualTo(Integer value) {
            addCriterion("item16b_1 <>", value, "item16b1");
            return (Criteria) this;
        }

        public Criteria andItem16b1GreaterThan(Integer value) {
            addCriterion("item16b_1 >", value, "item16b1");
            return (Criteria) this;
        }

        public Criteria andItem16b1GreaterThanOrEqualTo(Integer value) {
            addCriterion("item16b_1 >=", value, "item16b1");
            return (Criteria) this;
        }

        public Criteria andItem16b1LessThan(Integer value) {
            addCriterion("item16b_1 <", value, "item16b1");
            return (Criteria) this;
        }

        public Criteria andItem16b1LessThanOrEqualTo(Integer value) {
            addCriterion("item16b_1 <=", value, "item16b1");
            return (Criteria) this;
        }

        public Criteria andItem16b1In(List<Integer> values) {
            addCriterion("item16b_1 in", values, "item16b1");
            return (Criteria) this;
        }

        public Criteria andItem16b1NotIn(List<Integer> values) {
            addCriterion("item16b_1 not in", values, "item16b1");
            return (Criteria) this;
        }

        public Criteria andItem16b1Between(Integer value1, Integer value2) {
            addCriterion("item16b_1 between", value1, value2, "item16b1");
            return (Criteria) this;
        }

        public Criteria andItem16b1NotBetween(Integer value1, Integer value2) {
            addCriterion("item16b_1 not between", value1, value2, "item16b1");
            return (Criteria) this;
        }

        public Criteria andItem16b11IsNull() {
            addCriterion("item16b_1_1 is null");
            return (Criteria) this;
        }

        public Criteria andItem16b11IsNotNull() {
            addCriterion("item16b_1_1 is not null");
            return (Criteria) this;
        }

        public Criteria andItem16b11EqualTo(Integer value) {
            addCriterion("item16b_1_1 =", value, "item16b11");
            return (Criteria) this;
        }

        public Criteria andItem16b11NotEqualTo(Integer value) {
            addCriterion("item16b_1_1 <>", value, "item16b11");
            return (Criteria) this;
        }

        public Criteria andItem16b11GreaterThan(Integer value) {
            addCriterion("item16b_1_1 >", value, "item16b11");
            return (Criteria) this;
        }

        public Criteria andItem16b11GreaterThanOrEqualTo(Integer value) {
            addCriterion("item16b_1_1 >=", value, "item16b11");
            return (Criteria) this;
        }

        public Criteria andItem16b11LessThan(Integer value) {
            addCriterion("item16b_1_1 <", value, "item16b11");
            return (Criteria) this;
        }

        public Criteria andItem16b11LessThanOrEqualTo(Integer value) {
            addCriterion("item16b_1_1 <=", value, "item16b11");
            return (Criteria) this;
        }

        public Criteria andItem16b11In(List<Integer> values) {
            addCriterion("item16b_1_1 in", values, "item16b11");
            return (Criteria) this;
        }

        public Criteria andItem16b11NotIn(List<Integer> values) {
            addCriterion("item16b_1_1 not in", values, "item16b11");
            return (Criteria) this;
        }

        public Criteria andItem16b11Between(Integer value1, Integer value2) {
            addCriterion("item16b_1_1 between", value1, value2, "item16b11");
            return (Criteria) this;
        }

        public Criteria andItem16b11NotBetween(Integer value1, Integer value2) {
            addCriterion("item16b_1_1 not between", value1, value2, "item16b11");
            return (Criteria) this;
        }

        public Criteria andItem16b2IsNull() {
            addCriterion("item16b_2 is null");
            return (Criteria) this;
        }

        public Criteria andItem16b2IsNotNull() {
            addCriterion("item16b_2 is not null");
            return (Criteria) this;
        }

        public Criteria andItem16b2EqualTo(Integer value) {
            addCriterion("item16b_2 =", value, "item16b2");
            return (Criteria) this;
        }

        public Criteria andItem16b2NotEqualTo(Integer value) {
            addCriterion("item16b_2 <>", value, "item16b2");
            return (Criteria) this;
        }

        public Criteria andItem16b2GreaterThan(Integer value) {
            addCriterion("item16b_2 >", value, "item16b2");
            return (Criteria) this;
        }

        public Criteria andItem16b2GreaterThanOrEqualTo(Integer value) {
            addCriterion("item16b_2 >=", value, "item16b2");
            return (Criteria) this;
        }

        public Criteria andItem16b2LessThan(Integer value) {
            addCriterion("item16b_2 <", value, "item16b2");
            return (Criteria) this;
        }

        public Criteria andItem16b2LessThanOrEqualTo(Integer value) {
            addCriterion("item16b_2 <=", value, "item16b2");
            return (Criteria) this;
        }

        public Criteria andItem16b2In(List<Integer> values) {
            addCriterion("item16b_2 in", values, "item16b2");
            return (Criteria) this;
        }

        public Criteria andItem16b2NotIn(List<Integer> values) {
            addCriterion("item16b_2 not in", values, "item16b2");
            return (Criteria) this;
        }

        public Criteria andItem16b2Between(Integer value1, Integer value2) {
            addCriterion("item16b_2 between", value1, value2, "item16b2");
            return (Criteria) this;
        }

        public Criteria andItem16b2NotBetween(Integer value1, Integer value2) {
            addCriterion("item16b_2 not between", value1, value2, "item16b2");
            return (Criteria) this;
        }

        public Criteria andItem16b12IsNull() {
            addCriterion("item16b_1_2 is null");
            return (Criteria) this;
        }

        public Criteria andItem16b12IsNotNull() {
            addCriterion("item16b_1_2 is not null");
            return (Criteria) this;
        }

        public Criteria andItem16b12EqualTo(Integer value) {
            addCriterion("item16b_1_2 =", value, "item16b12");
            return (Criteria) this;
        }

        public Criteria andItem16b12NotEqualTo(Integer value) {
            addCriterion("item16b_1_2 <>", value, "item16b12");
            return (Criteria) this;
        }

        public Criteria andItem16b12GreaterThan(Integer value) {
            addCriterion("item16b_1_2 >", value, "item16b12");
            return (Criteria) this;
        }

        public Criteria andItem16b12GreaterThanOrEqualTo(Integer value) {
            addCriterion("item16b_1_2 >=", value, "item16b12");
            return (Criteria) this;
        }

        public Criteria andItem16b12LessThan(Integer value) {
            addCriterion("item16b_1_2 <", value, "item16b12");
            return (Criteria) this;
        }

        public Criteria andItem16b12LessThanOrEqualTo(Integer value) {
            addCriterion("item16b_1_2 <=", value, "item16b12");
            return (Criteria) this;
        }

        public Criteria andItem16b12In(List<Integer> values) {
            addCriterion("item16b_1_2 in", values, "item16b12");
            return (Criteria) this;
        }

        public Criteria andItem16b12NotIn(List<Integer> values) {
            addCriterion("item16b_1_2 not in", values, "item16b12");
            return (Criteria) this;
        }

        public Criteria andItem16b12Between(Integer value1, Integer value2) {
            addCriterion("item16b_1_2 between", value1, value2, "item16b12");
            return (Criteria) this;
        }

        public Criteria andItem16b12NotBetween(Integer value1, Integer value2) {
            addCriterion("item16b_1_2 not between", value1, value2, "item16b12");
            return (Criteria) this;
        }

        public Criteria andItem16b13IsNull() {
            addCriterion("item16b_1_3 is null");
            return (Criteria) this;
        }

        public Criteria andItem16b13IsNotNull() {
            addCriterion("item16b_1_3 is not null");
            return (Criteria) this;
        }

        public Criteria andItem16b13EqualTo(Integer value) {
            addCriterion("item16b_1_3 =", value, "item16b13");
            return (Criteria) this;
        }

        public Criteria andItem16b13NotEqualTo(Integer value) {
            addCriterion("item16b_1_3 <>", value, "item16b13");
            return (Criteria) this;
        }

        public Criteria andItem16b13GreaterThan(Integer value) {
            addCriterion("item16b_1_3 >", value, "item16b13");
            return (Criteria) this;
        }

        public Criteria andItem16b13GreaterThanOrEqualTo(Integer value) {
            addCriterion("item16b_1_3 >=", value, "item16b13");
            return (Criteria) this;
        }

        public Criteria andItem16b13LessThan(Integer value) {
            addCriterion("item16b_1_3 <", value, "item16b13");
            return (Criteria) this;
        }

        public Criteria andItem16b13LessThanOrEqualTo(Integer value) {
            addCriterion("item16b_1_3 <=", value, "item16b13");
            return (Criteria) this;
        }

        public Criteria andItem16b13In(List<Integer> values) {
            addCriterion("item16b_1_3 in", values, "item16b13");
            return (Criteria) this;
        }

        public Criteria andItem16b13NotIn(List<Integer> values) {
            addCriterion("item16b_1_3 not in", values, "item16b13");
            return (Criteria) this;
        }

        public Criteria andItem16b13Between(Integer value1, Integer value2) {
            addCriterion("item16b_1_3 between", value1, value2, "item16b13");
            return (Criteria) this;
        }

        public Criteria andItem16b13NotBetween(Integer value1, Integer value2) {
            addCriterion("item16b_1_3 not between", value1, value2, "item16b13");
            return (Criteria) this;
        }

        public Criteria andItem1711IsNull() {
            addCriterion("item17_1_1 is null");
            return (Criteria) this;
        }

        public Criteria andItem1711IsNotNull() {
            addCriterion("item17_1_1 is not null");
            return (Criteria) this;
        }

        public Criteria andItem1711EqualTo(Integer value) {
            addCriterion("item17_1_1 =", value, "item1711");
            return (Criteria) this;
        }

        public Criteria andItem1711NotEqualTo(Integer value) {
            addCriterion("item17_1_1 <>", value, "item1711");
            return (Criteria) this;
        }

        public Criteria andItem1711GreaterThan(Integer value) {
            addCriterion("item17_1_1 >", value, "item1711");
            return (Criteria) this;
        }

        public Criteria andItem1711GreaterThanOrEqualTo(Integer value) {
            addCriterion("item17_1_1 >=", value, "item1711");
            return (Criteria) this;
        }

        public Criteria andItem1711LessThan(Integer value) {
            addCriterion("item17_1_1 <", value, "item1711");
            return (Criteria) this;
        }

        public Criteria andItem1711LessThanOrEqualTo(Integer value) {
            addCriterion("item17_1_1 <=", value, "item1711");
            return (Criteria) this;
        }

        public Criteria andItem1711In(List<Integer> values) {
            addCriterion("item17_1_1 in", values, "item1711");
            return (Criteria) this;
        }

        public Criteria andItem1711NotIn(List<Integer> values) {
            addCriterion("item17_1_1 not in", values, "item1711");
            return (Criteria) this;
        }

        public Criteria andItem1711Between(Integer value1, Integer value2) {
            addCriterion("item17_1_1 between", value1, value2, "item1711");
            return (Criteria) this;
        }

        public Criteria andItem1711NotBetween(Integer value1, Integer value2) {
            addCriterion("item17_1_1 not between", value1, value2, "item1711");
            return (Criteria) this;
        }

        public Criteria andItem1712IsNull() {
            addCriterion("item17_1_2 is null");
            return (Criteria) this;
        }

        public Criteria andItem1712IsNotNull() {
            addCriterion("item17_1_2 is not null");
            return (Criteria) this;
        }

        public Criteria andItem1712EqualTo(Integer value) {
            addCriterion("item17_1_2 =", value, "item1712");
            return (Criteria) this;
        }

        public Criteria andItem1712NotEqualTo(Integer value) {
            addCriterion("item17_1_2 <>", value, "item1712");
            return (Criteria) this;
        }

        public Criteria andItem1712GreaterThan(Integer value) {
            addCriterion("item17_1_2 >", value, "item1712");
            return (Criteria) this;
        }

        public Criteria andItem1712GreaterThanOrEqualTo(Integer value) {
            addCriterion("item17_1_2 >=", value, "item1712");
            return (Criteria) this;
        }

        public Criteria andItem1712LessThan(Integer value) {
            addCriterion("item17_1_2 <", value, "item1712");
            return (Criteria) this;
        }

        public Criteria andItem1712LessThanOrEqualTo(Integer value) {
            addCriterion("item17_1_2 <=", value, "item1712");
            return (Criteria) this;
        }

        public Criteria andItem1712In(List<Integer> values) {
            addCriterion("item17_1_2 in", values, "item1712");
            return (Criteria) this;
        }

        public Criteria andItem1712NotIn(List<Integer> values) {
            addCriterion("item17_1_2 not in", values, "item1712");
            return (Criteria) this;
        }

        public Criteria andItem1712Between(Integer value1, Integer value2) {
            addCriterion("item17_1_2 between", value1, value2, "item1712");
            return (Criteria) this;
        }

        public Criteria andItem1712NotBetween(Integer value1, Integer value2) {
            addCriterion("item17_1_2 not between", value1, value2, "item1712");
            return (Criteria) this;
        }

        public Criteria andItem1713IsNull() {
            addCriterion("item17_1_3 is null");
            return (Criteria) this;
        }

        public Criteria andItem1713IsNotNull() {
            addCriterion("item17_1_3 is not null");
            return (Criteria) this;
        }

        public Criteria andItem1713EqualTo(Integer value) {
            addCriterion("item17_1_3 =", value, "item1713");
            return (Criteria) this;
        }

        public Criteria andItem1713NotEqualTo(Integer value) {
            addCriterion("item17_1_3 <>", value, "item1713");
            return (Criteria) this;
        }

        public Criteria andItem1713GreaterThan(Integer value) {
            addCriterion("item17_1_3 >", value, "item1713");
            return (Criteria) this;
        }

        public Criteria andItem1713GreaterThanOrEqualTo(Integer value) {
            addCriterion("item17_1_3 >=", value, "item1713");
            return (Criteria) this;
        }

        public Criteria andItem1713LessThan(Integer value) {
            addCriterion("item17_1_3 <", value, "item1713");
            return (Criteria) this;
        }

        public Criteria andItem1713LessThanOrEqualTo(Integer value) {
            addCriterion("item17_1_3 <=", value, "item1713");
            return (Criteria) this;
        }

        public Criteria andItem1713In(List<Integer> values) {
            addCriterion("item17_1_3 in", values, "item1713");
            return (Criteria) this;
        }

        public Criteria andItem1713NotIn(List<Integer> values) {
            addCriterion("item17_1_3 not in", values, "item1713");
            return (Criteria) this;
        }

        public Criteria andItem1713Between(Integer value1, Integer value2) {
            addCriterion("item17_1_3 between", value1, value2, "item1713");
            return (Criteria) this;
        }

        public Criteria andItem1713NotBetween(Integer value1, Integer value2) {
            addCriterion("item17_1_3 not between", value1, value2, "item1713");
            return (Criteria) this;
        }

        public Criteria andItem18IsNull() {
            addCriterion("item18 is null");
            return (Criteria) this;
        }

        public Criteria andItem18IsNotNull() {
            addCriterion("item18 is not null");
            return (Criteria) this;
        }

        public Criteria andItem18EqualTo(String value) {
            addCriterion("item18 =", value, "item18");
            return (Criteria) this;
        }

        public Criteria andItem18NotEqualTo(String value) {
            addCriterion("item18 <>", value, "item18");
            return (Criteria) this;
        }

        public Criteria andItem18GreaterThan(String value) {
            addCriterion("item18 >", value, "item18");
            return (Criteria) this;
        }

        public Criteria andItem18GreaterThanOrEqualTo(String value) {
            addCriterion("item18 >=", value, "item18");
            return (Criteria) this;
        }

        public Criteria andItem18LessThan(String value) {
            addCriterion("item18 <", value, "item18");
            return (Criteria) this;
        }

        public Criteria andItem18LessThanOrEqualTo(String value) {
            addCriterion("item18 <=", value, "item18");
            return (Criteria) this;
        }

        public Criteria andItem18Like(String value) {
            addCriterion("item18 like", value, "item18");
            return (Criteria) this;
        }

        public Criteria andItem18NotLike(String value) {
            addCriterion("item18 not like", value, "item18");
            return (Criteria) this;
        }

        public Criteria andItem18In(List<String> values) {
            addCriterion("item18 in", values, "item18");
            return (Criteria) this;
        }

        public Criteria andItem18NotIn(List<String> values) {
            addCriterion("item18 not in", values, "item18");
            return (Criteria) this;
        }

        public Criteria andItem18Between(String value1, String value2) {
            addCriterion("item18 between", value1, value2, "item18");
            return (Criteria) this;
        }

        public Criteria andItem18NotBetween(String value1, String value2) {
            addCriterion("item18 not between", value1, value2, "item18");
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