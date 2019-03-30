package com.yuntongxun.itsys.base.po.cancer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HospitalCancerRecordPOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public HospitalCancerRecordPOExample() {
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

        public Criteria andPathologyIdIsNull() {
            addCriterion("pathology_id is null");
            return (Criteria) this;
        }

        public Criteria andPathologyIdIsNotNull() {
            addCriterion("pathology_id is not null");
            return (Criteria) this;
        }

        public Criteria andPathologyIdEqualTo(Integer value) {
            addCriterion("pathology_id =", value, "pathologyId");
            return (Criteria) this;
        }

        public Criteria andPathologyIdNotEqualTo(Integer value) {
            addCriterion("pathology_id <>", value, "pathologyId");
            return (Criteria) this;
        }

        public Criteria andPathologyIdGreaterThan(Integer value) {
            addCriterion("pathology_id >", value, "pathologyId");
            return (Criteria) this;
        }

        public Criteria andPathologyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("pathology_id >=", value, "pathologyId");
            return (Criteria) this;
        }

        public Criteria andPathologyIdLessThan(Integer value) {
            addCriterion("pathology_id <", value, "pathologyId");
            return (Criteria) this;
        }

        public Criteria andPathologyIdLessThanOrEqualTo(Integer value) {
            addCriterion("pathology_id <=", value, "pathologyId");
            return (Criteria) this;
        }

        public Criteria andPathologyIdIn(List<Integer> values) {
            addCriterion("pathology_id in", values, "pathologyId");
            return (Criteria) this;
        }

        public Criteria andPathologyIdNotIn(List<Integer> values) {
            addCriterion("pathology_id not in", values, "pathologyId");
            return (Criteria) this;
        }

        public Criteria andPathologyIdBetween(Integer value1, Integer value2) {
            addCriterion("pathology_id between", value1, value2, "pathologyId");
            return (Criteria) this;
        }

        public Criteria andPathologyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("pathology_id not between", value1, value2, "pathologyId");
            return (Criteria) this;
        }

        public Criteria andColonoscopyRecordIdIsNull() {
            addCriterion("colonoscopy_record_id is null");
            return (Criteria) this;
        }

        public Criteria andColonoscopyRecordIdIsNotNull() {
            addCriterion("colonoscopy_record_id is not null");
            return (Criteria) this;
        }

        public Criteria andColonoscopyRecordIdEqualTo(Integer value) {
            addCriterion("colonoscopy_record_id =", value, "colonoscopyRecordId");
            return (Criteria) this;
        }

        public Criteria andColonoscopyRecordIdNotEqualTo(Integer value) {
            addCriterion("colonoscopy_record_id <>", value, "colonoscopyRecordId");
            return (Criteria) this;
        }

        public Criteria andColonoscopyRecordIdGreaterThan(Integer value) {
            addCriterion("colonoscopy_record_id >", value, "colonoscopyRecordId");
            return (Criteria) this;
        }

        public Criteria andColonoscopyRecordIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("colonoscopy_record_id >=", value, "colonoscopyRecordId");
            return (Criteria) this;
        }

        public Criteria andColonoscopyRecordIdLessThan(Integer value) {
            addCriterion("colonoscopy_record_id <", value, "colonoscopyRecordId");
            return (Criteria) this;
        }

        public Criteria andColonoscopyRecordIdLessThanOrEqualTo(Integer value) {
            addCriterion("colonoscopy_record_id <=", value, "colonoscopyRecordId");
            return (Criteria) this;
        }

        public Criteria andColonoscopyRecordIdIn(List<Integer> values) {
            addCriterion("colonoscopy_record_id in", values, "colonoscopyRecordId");
            return (Criteria) this;
        }

        public Criteria andColonoscopyRecordIdNotIn(List<Integer> values) {
            addCriterion("colonoscopy_record_id not in", values, "colonoscopyRecordId");
            return (Criteria) this;
        }

        public Criteria andColonoscopyRecordIdBetween(Integer value1, Integer value2) {
            addCriterion("colonoscopy_record_id between", value1, value2, "colonoscopyRecordId");
            return (Criteria) this;
        }

        public Criteria andColonoscopyRecordIdNotBetween(Integer value1, Integer value2) {
            addCriterion("colonoscopy_record_id not between", value1, value2, "colonoscopyRecordId");
            return (Criteria) this;
        }

        public Criteria andCommunityDeptIdIsNull() {
            addCriterion("community_dept_id is null");
            return (Criteria) this;
        }

        public Criteria andCommunityDeptIdIsNotNull() {
            addCriterion("community_dept_id is not null");
            return (Criteria) this;
        }

        public Criteria andCommunityDeptIdEqualTo(Integer value) {
            addCriterion("community_dept_id =", value, "communityDeptId");
            return (Criteria) this;
        }

        public Criteria andCommunityDeptIdNotEqualTo(Integer value) {
            addCriterion("community_dept_id <>", value, "communityDeptId");
            return (Criteria) this;
        }

        public Criteria andCommunityDeptIdGreaterThan(Integer value) {
            addCriterion("community_dept_id >", value, "communityDeptId");
            return (Criteria) this;
        }

        public Criteria andCommunityDeptIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("community_dept_id >=", value, "communityDeptId");
            return (Criteria) this;
        }

        public Criteria andCommunityDeptIdLessThan(Integer value) {
            addCriterion("community_dept_id <", value, "communityDeptId");
            return (Criteria) this;
        }

        public Criteria andCommunityDeptIdLessThanOrEqualTo(Integer value) {
            addCriterion("community_dept_id <=", value, "communityDeptId");
            return (Criteria) this;
        }

        public Criteria andCommunityDeptIdIn(List<Integer> values) {
            addCriterion("community_dept_id in", values, "communityDeptId");
            return (Criteria) this;
        }

        public Criteria andCommunityDeptIdNotIn(List<Integer> values) {
            addCriterion("community_dept_id not in", values, "communityDeptId");
            return (Criteria) this;
        }

        public Criteria andCommunityDeptIdBetween(Integer value1, Integer value2) {
            addCriterion("community_dept_id between", value1, value2, "communityDeptId");
            return (Criteria) this;
        }

        public Criteria andCommunityDeptIdNotBetween(Integer value1, Integer value2) {
            addCriterion("community_dept_id not between", value1, value2, "communityDeptId");
            return (Criteria) this;
        }

        public Criteria andAreaDeptIdIsNull() {
            addCriterion("area_dept_id is null");
            return (Criteria) this;
        }

        public Criteria andAreaDeptIdIsNotNull() {
            addCriterion("area_dept_id is not null");
            return (Criteria) this;
        }

        public Criteria andAreaDeptIdEqualTo(Integer value) {
            addCriterion("area_dept_id =", value, "areaDeptId");
            return (Criteria) this;
        }

        public Criteria andAreaDeptIdNotEqualTo(Integer value) {
            addCriterion("area_dept_id <>", value, "areaDeptId");
            return (Criteria) this;
        }

        public Criteria andAreaDeptIdGreaterThan(Integer value) {
            addCriterion("area_dept_id >", value, "areaDeptId");
            return (Criteria) this;
        }

        public Criteria andAreaDeptIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("area_dept_id >=", value, "areaDeptId");
            return (Criteria) this;
        }

        public Criteria andAreaDeptIdLessThan(Integer value) {
            addCriterion("area_dept_id <", value, "areaDeptId");
            return (Criteria) this;
        }

        public Criteria andAreaDeptIdLessThanOrEqualTo(Integer value) {
            addCriterion("area_dept_id <=", value, "areaDeptId");
            return (Criteria) this;
        }

        public Criteria andAreaDeptIdIn(List<Integer> values) {
            addCriterion("area_dept_id in", values, "areaDeptId");
            return (Criteria) this;
        }

        public Criteria andAreaDeptIdNotIn(List<Integer> values) {
            addCriterion("area_dept_id not in", values, "areaDeptId");
            return (Criteria) this;
        }

        public Criteria andAreaDeptIdBetween(Integer value1, Integer value2) {
            addCriterion("area_dept_id between", value1, value2, "areaDeptId");
            return (Criteria) this;
        }

        public Criteria andAreaDeptIdNotBetween(Integer value1, Integer value2) {
            addCriterion("area_dept_id not between", value1, value2, "areaDeptId");
            return (Criteria) this;
        }

        public Criteria andCancerReportIdIsNull() {
            addCriterion("cancer_report_id is null");
            return (Criteria) this;
        }

        public Criteria andCancerReportIdIsNotNull() {
            addCriterion("cancer_report_id is not null");
            return (Criteria) this;
        }

        public Criteria andCancerReportIdEqualTo(Integer value) {
            addCriterion("cancer_report_id =", value, "cancerReportId");
            return (Criteria) this;
        }

        public Criteria andCancerReportIdNotEqualTo(Integer value) {
            addCriterion("cancer_report_id <>", value, "cancerReportId");
            return (Criteria) this;
        }

        public Criteria andCancerReportIdGreaterThan(Integer value) {
            addCriterion("cancer_report_id >", value, "cancerReportId");
            return (Criteria) this;
        }

        public Criteria andCancerReportIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("cancer_report_id >=", value, "cancerReportId");
            return (Criteria) this;
        }

        public Criteria andCancerReportIdLessThan(Integer value) {
            addCriterion("cancer_report_id <", value, "cancerReportId");
            return (Criteria) this;
        }

        public Criteria andCancerReportIdLessThanOrEqualTo(Integer value) {
            addCriterion("cancer_report_id <=", value, "cancerReportId");
            return (Criteria) this;
        }

        public Criteria andCancerReportIdIn(List<Integer> values) {
            addCriterion("cancer_report_id in", values, "cancerReportId");
            return (Criteria) this;
        }

        public Criteria andCancerReportIdNotIn(List<Integer> values) {
            addCriterion("cancer_report_id not in", values, "cancerReportId");
            return (Criteria) this;
        }

        public Criteria andCancerReportIdBetween(Integer value1, Integer value2) {
            addCriterion("cancer_report_id between", value1, value2, "cancerReportId");
            return (Criteria) this;
        }

        public Criteria andCancerReportIdNotBetween(Integer value1, Integer value2) {
            addCriterion("cancer_report_id not between", value1, value2, "cancerReportId");
            return (Criteria) this;
        }

        public Criteria andCancerReportStatusIsNull() {
            addCriterion("cancer_report_status is null");
            return (Criteria) this;
        }

        public Criteria andCancerReportStatusIsNotNull() {
            addCriterion("cancer_report_status is not null");
            return (Criteria) this;
        }

        public Criteria andCancerReportStatusEqualTo(String value) {
            addCriterion("cancer_report_status =", value, "cancerReportStatus");
            return (Criteria) this;
        }

        public Criteria andCancerReportStatusNotEqualTo(String value) {
            addCriterion("cancer_report_status <>", value, "cancerReportStatus");
            return (Criteria) this;
        }

        public Criteria andCancerReportStatusGreaterThan(String value) {
            addCriterion("cancer_report_status >", value, "cancerReportStatus");
            return (Criteria) this;
        }

        public Criteria andCancerReportStatusGreaterThanOrEqualTo(String value) {
            addCriterion("cancer_report_status >=", value, "cancerReportStatus");
            return (Criteria) this;
        }

        public Criteria andCancerReportStatusLessThan(String value) {
            addCriterion("cancer_report_status <", value, "cancerReportStatus");
            return (Criteria) this;
        }

        public Criteria andCancerReportStatusLessThanOrEqualTo(String value) {
            addCriterion("cancer_report_status <=", value, "cancerReportStatus");
            return (Criteria) this;
        }

        public Criteria andCancerReportStatusLike(String value) {
            addCriterion("cancer_report_status like", value, "cancerReportStatus");
            return (Criteria) this;
        }

        public Criteria andCancerReportStatusNotLike(String value) {
            addCriterion("cancer_report_status not like", value, "cancerReportStatus");
            return (Criteria) this;
        }

        public Criteria andCancerReportStatusIn(List<String> values) {
            addCriterion("cancer_report_status in", values, "cancerReportStatus");
            return (Criteria) this;
        }

        public Criteria andCancerReportStatusNotIn(List<String> values) {
            addCriterion("cancer_report_status not in", values, "cancerReportStatus");
            return (Criteria) this;
        }

        public Criteria andCancerReportStatusBetween(String value1, String value2) {
            addCriterion("cancer_report_status between", value1, value2, "cancerReportStatus");
            return (Criteria) this;
        }

        public Criteria andCancerReportStatusNotBetween(String value1, String value2) {
            addCriterion("cancer_report_status not between", value1, value2, "cancerReportStatus");
            return (Criteria) this;
        }

        public Criteria andCancerReportInStatusDateIsNull() {
            addCriterion("cancer_report_in_status_date is null");
            return (Criteria) this;
        }

        public Criteria andCancerReportInStatusDateIsNotNull() {
            addCriterion("cancer_report_in_status_date is not null");
            return (Criteria) this;
        }

        public Criteria andCancerReportInStatusDateEqualTo(Date value) {
            addCriterion("cancer_report_in_status_date =", value, "cancerReportInStatusDate");
            return (Criteria) this;
        }

        public Criteria andCancerReportInStatusDateNotEqualTo(Date value) {
            addCriterion("cancer_report_in_status_date <>", value, "cancerReportInStatusDate");
            return (Criteria) this;
        }

        public Criteria andCancerReportInStatusDateGreaterThan(Date value) {
            addCriterion("cancer_report_in_status_date >", value, "cancerReportInStatusDate");
            return (Criteria) this;
        }

        public Criteria andCancerReportInStatusDateGreaterThanOrEqualTo(Date value) {
            addCriterion("cancer_report_in_status_date >=", value, "cancerReportInStatusDate");
            return (Criteria) this;
        }

        public Criteria andCancerReportInStatusDateLessThan(Date value) {
            addCriterion("cancer_report_in_status_date <", value, "cancerReportInStatusDate");
            return (Criteria) this;
        }

        public Criteria andCancerReportInStatusDateLessThanOrEqualTo(Date value) {
            addCriterion("cancer_report_in_status_date <=", value, "cancerReportInStatusDate");
            return (Criteria) this;
        }

        public Criteria andCancerReportInStatusDateIn(List<Date> values) {
            addCriterion("cancer_report_in_status_date in", values, "cancerReportInStatusDate");
            return (Criteria) this;
        }

        public Criteria andCancerReportInStatusDateNotIn(List<Date> values) {
            addCriterion("cancer_report_in_status_date not in", values, "cancerReportInStatusDate");
            return (Criteria) this;
        }

        public Criteria andCancerReportInStatusDateBetween(Date value1, Date value2) {
            addCriterion("cancer_report_in_status_date between", value1, value2, "cancerReportInStatusDate");
            return (Criteria) this;
        }

        public Criteria andCancerReportInStatusDateNotBetween(Date value1, Date value2) {
            addCriterion("cancer_report_in_status_date not between", value1, value2, "cancerReportInStatusDate");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseIdIsNull() {
            addCriterion("`cancer_ diagnose_id` is null");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseIdIsNotNull() {
            addCriterion("`cancer_ diagnose_id` is not null");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseIdEqualTo(Integer value) {
            addCriterion("`cancer_ diagnose_id` =", value, "cancerDiagnoseId");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseIdNotEqualTo(Integer value) {
            addCriterion("`cancer_ diagnose_id` <>", value, "cancerDiagnoseId");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseIdGreaterThan(Integer value) {
            addCriterion("`cancer_ diagnose_id` >", value, "cancerDiagnoseId");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("`cancer_ diagnose_id` >=", value, "cancerDiagnoseId");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseIdLessThan(Integer value) {
            addCriterion("`cancer_ diagnose_id` <", value, "cancerDiagnoseId");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseIdLessThanOrEqualTo(Integer value) {
            addCriterion("`cancer_ diagnose_id` <=", value, "cancerDiagnoseId");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseIdIn(List<Integer> values) {
            addCriterion("`cancer_ diagnose_id` in", values, "cancerDiagnoseId");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseIdNotIn(List<Integer> values) {
            addCriterion("`cancer_ diagnose_id` not in", values, "cancerDiagnoseId");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseIdBetween(Integer value1, Integer value2) {
            addCriterion("`cancer_ diagnose_id` between", value1, value2, "cancerDiagnoseId");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseIdNotBetween(Integer value1, Integer value2) {
            addCriterion("`cancer_ diagnose_id` not between", value1, value2, "cancerDiagnoseId");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseStatusIsNull() {
            addCriterion("`cancer_ diagnose_status` is null");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseStatusIsNotNull() {
            addCriterion("`cancer_ diagnose_status` is not null");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseStatusEqualTo(String value) {
            addCriterion("`cancer_ diagnose_status` =", value, "cancerDiagnoseStatus");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseStatusNotEqualTo(String value) {
            addCriterion("`cancer_ diagnose_status` <>", value, "cancerDiagnoseStatus");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseStatusGreaterThan(String value) {
            addCriterion("`cancer_ diagnose_status` >", value, "cancerDiagnoseStatus");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseStatusGreaterThanOrEqualTo(String value) {
            addCriterion("`cancer_ diagnose_status` >=", value, "cancerDiagnoseStatus");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseStatusLessThan(String value) {
            addCriterion("`cancer_ diagnose_status` <", value, "cancerDiagnoseStatus");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseStatusLessThanOrEqualTo(String value) {
            addCriterion("`cancer_ diagnose_status` <=", value, "cancerDiagnoseStatus");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseStatusLike(String value) {
            addCriterion("`cancer_ diagnose_status` like", value, "cancerDiagnoseStatus");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseStatusNotLike(String value) {
            addCriterion("`cancer_ diagnose_status` not like", value, "cancerDiagnoseStatus");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseStatusIn(List<String> values) {
            addCriterion("`cancer_ diagnose_status` in", values, "cancerDiagnoseStatus");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseStatusNotIn(List<String> values) {
            addCriterion("`cancer_ diagnose_status` not in", values, "cancerDiagnoseStatus");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseStatusBetween(String value1, String value2) {
            addCriterion("`cancer_ diagnose_status` between", value1, value2, "cancerDiagnoseStatus");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseStatusNotBetween(String value1, String value2) {
            addCriterion("`cancer_ diagnose_status` not between", value1, value2, "cancerDiagnoseStatus");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseInStatusDateIsNull() {
            addCriterion("`cancer_ diagnose_in_status_date` is null");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseInStatusDateIsNotNull() {
            addCriterion("`cancer_ diagnose_in_status_date` is not null");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseInStatusDateEqualTo(Date value) {
            addCriterion("`cancer_ diagnose_in_status_date` =", value, "cancerDiagnoseInStatusDate");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseInStatusDateNotEqualTo(Date value) {
            addCriterion("`cancer_ diagnose_in_status_date` <>", value, "cancerDiagnoseInStatusDate");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseInStatusDateGreaterThan(Date value) {
            addCriterion("`cancer_ diagnose_in_status_date` >", value, "cancerDiagnoseInStatusDate");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseInStatusDateGreaterThanOrEqualTo(Date value) {
            addCriterion("`cancer_ diagnose_in_status_date` >=", value, "cancerDiagnoseInStatusDate");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseInStatusDateLessThan(Date value) {
            addCriterion("`cancer_ diagnose_in_status_date` <", value, "cancerDiagnoseInStatusDate");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseInStatusDateLessThanOrEqualTo(Date value) {
            addCriterion("`cancer_ diagnose_in_status_date` <=", value, "cancerDiagnoseInStatusDate");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseInStatusDateIn(List<Date> values) {
            addCriterion("`cancer_ diagnose_in_status_date` in", values, "cancerDiagnoseInStatusDate");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseInStatusDateNotIn(List<Date> values) {
            addCriterion("`cancer_ diagnose_in_status_date` not in", values, "cancerDiagnoseInStatusDate");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseInStatusDateBetween(Date value1, Date value2) {
            addCriterion("`cancer_ diagnose_in_status_date` between", value1, value2, "cancerDiagnoseInStatusDate");
            return (Criteria) this;
        }

        public Criteria andCancerDiagnoseInStatusDateNotBetween(Date value1, Date value2) {
            addCriterion("`cancer_ diagnose_in_status_date` not between", value1, value2, "cancerDiagnoseInStatusDate");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationIdIsNull() {
            addCriterion("`colorectal_cancer_ diagnose_ information_id` is null");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationIdIsNotNull() {
            addCriterion("`colorectal_cancer_ diagnose_ information_id` is not null");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationIdEqualTo(Integer value) {
            addCriterion("`colorectal_cancer_ diagnose_ information_id` =", value, "colorectalCancerDiagnoseInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationIdNotEqualTo(Integer value) {
            addCriterion("`colorectal_cancer_ diagnose_ information_id` <>", value, "colorectalCancerDiagnoseInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationIdGreaterThan(Integer value) {
            addCriterion("`colorectal_cancer_ diagnose_ information_id` >", value, "colorectalCancerDiagnoseInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("`colorectal_cancer_ diagnose_ information_id` >=", value, "colorectalCancerDiagnoseInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationIdLessThan(Integer value) {
            addCriterion("`colorectal_cancer_ diagnose_ information_id` <", value, "colorectalCancerDiagnoseInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationIdLessThanOrEqualTo(Integer value) {
            addCriterion("`colorectal_cancer_ diagnose_ information_id` <=", value, "colorectalCancerDiagnoseInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationIdIn(List<Integer> values) {
            addCriterion("`colorectal_cancer_ diagnose_ information_id` in", values, "colorectalCancerDiagnoseInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationIdNotIn(List<Integer> values) {
            addCriterion("`colorectal_cancer_ diagnose_ information_id` not in", values, "colorectalCancerDiagnoseInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationIdBetween(Integer value1, Integer value2) {
            addCriterion("`colorectal_cancer_ diagnose_ information_id` between", value1, value2, "colorectalCancerDiagnoseInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationIdNotBetween(Integer value1, Integer value2) {
            addCriterion("`colorectal_cancer_ diagnose_ information_id` not between", value1, value2, "colorectalCancerDiagnoseInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationStatusIsNull() {
            addCriterion("`colorectal_cancer_ diagnose_ information_status` is null");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationStatusIsNotNull() {
            addCriterion("`colorectal_cancer_ diagnose_ information_status` is not null");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationStatusEqualTo(String value) {
            addCriterion("`colorectal_cancer_ diagnose_ information_status` =", value, "colorectalCancerDiagnoseInformationStatus");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationStatusNotEqualTo(String value) {
            addCriterion("`colorectal_cancer_ diagnose_ information_status` <>", value, "colorectalCancerDiagnoseInformationStatus");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationStatusGreaterThan(String value) {
            addCriterion("`colorectal_cancer_ diagnose_ information_status` >", value, "colorectalCancerDiagnoseInformationStatus");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationStatusGreaterThanOrEqualTo(String value) {
            addCriterion("`colorectal_cancer_ diagnose_ information_status` >=", value, "colorectalCancerDiagnoseInformationStatus");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationStatusLessThan(String value) {
            addCriterion("`colorectal_cancer_ diagnose_ information_status` <", value, "colorectalCancerDiagnoseInformationStatus");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationStatusLessThanOrEqualTo(String value) {
            addCriterion("`colorectal_cancer_ diagnose_ information_status` <=", value, "colorectalCancerDiagnoseInformationStatus");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationStatusLike(String value) {
            addCriterion("`colorectal_cancer_ diagnose_ information_status` like", value, "colorectalCancerDiagnoseInformationStatus");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationStatusNotLike(String value) {
            addCriterion("`colorectal_cancer_ diagnose_ information_status` not like", value, "colorectalCancerDiagnoseInformationStatus");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationStatusIn(List<String> values) {
            addCriterion("`colorectal_cancer_ diagnose_ information_status` in", values, "colorectalCancerDiagnoseInformationStatus");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationStatusNotIn(List<String> values) {
            addCriterion("`colorectal_cancer_ diagnose_ information_status` not in", values, "colorectalCancerDiagnoseInformationStatus");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationStatusBetween(String value1, String value2) {
            addCriterion("`colorectal_cancer_ diagnose_ information_status` between", value1, value2, "colorectalCancerDiagnoseInformationStatus");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationStatusNotBetween(String value1, String value2) {
            addCriterion("`colorectal_cancer_ diagnose_ information_status` not between", value1, value2, "colorectalCancerDiagnoseInformationStatus");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationInStatusDateIsNull() {
            addCriterion("`colorectal_cancer_ diagnose_ information_in_status_date` is null");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationInStatusDateIsNotNull() {
            addCriterion("`colorectal_cancer_ diagnose_ information_in_status_date` is not null");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationInStatusDateEqualTo(Date value) {
            addCriterion("`colorectal_cancer_ diagnose_ information_in_status_date` =", value, "colorectalCancerDiagnoseInformationInStatusDate");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationInStatusDateNotEqualTo(Date value) {
            addCriterion("`colorectal_cancer_ diagnose_ information_in_status_date` <>", value, "colorectalCancerDiagnoseInformationInStatusDate");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationInStatusDateGreaterThan(Date value) {
            addCriterion("`colorectal_cancer_ diagnose_ information_in_status_date` >", value, "colorectalCancerDiagnoseInformationInStatusDate");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationInStatusDateGreaterThanOrEqualTo(Date value) {
            addCriterion("`colorectal_cancer_ diagnose_ information_in_status_date` >=", value, "colorectalCancerDiagnoseInformationInStatusDate");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationInStatusDateLessThan(Date value) {
            addCriterion("`colorectal_cancer_ diagnose_ information_in_status_date` <", value, "colorectalCancerDiagnoseInformationInStatusDate");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationInStatusDateLessThanOrEqualTo(Date value) {
            addCriterion("`colorectal_cancer_ diagnose_ information_in_status_date` <=", value, "colorectalCancerDiagnoseInformationInStatusDate");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationInStatusDateIn(List<Date> values) {
            addCriterion("`colorectal_cancer_ diagnose_ information_in_status_date` in", values, "colorectalCancerDiagnoseInformationInStatusDate");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationInStatusDateNotIn(List<Date> values) {
            addCriterion("`colorectal_cancer_ diagnose_ information_in_status_date` not in", values, "colorectalCancerDiagnoseInformationInStatusDate");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationInStatusDateBetween(Date value1, Date value2) {
            addCriterion("`colorectal_cancer_ diagnose_ information_in_status_date` between", value1, value2, "colorectalCancerDiagnoseInformationInStatusDate");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationInStatusDateNotBetween(Date value1, Date value2) {
            addCriterion("`colorectal_cancer_ diagnose_ information_in_status_date` not between", value1, value2, "colorectalCancerDiagnoseInformationInStatusDate");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformationIdIsNull() {
            addCriterion("`colorectal_cancer_treatment_ information_id` is null");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformationIdIsNotNull() {
            addCriterion("`colorectal_cancer_treatment_ information_id` is not null");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformationIdEqualTo(Integer value) {
            addCriterion("`colorectal_cancer_treatment_ information_id` =", value, "colorectalCancerTreatmentInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformationIdNotEqualTo(Integer value) {
            addCriterion("`colorectal_cancer_treatment_ information_id` <>", value, "colorectalCancerTreatmentInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformationIdGreaterThan(Integer value) {
            addCriterion("`colorectal_cancer_treatment_ information_id` >", value, "colorectalCancerTreatmentInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformationIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("`colorectal_cancer_treatment_ information_id` >=", value, "colorectalCancerTreatmentInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformationIdLessThan(Integer value) {
            addCriterion("`colorectal_cancer_treatment_ information_id` <", value, "colorectalCancerTreatmentInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformationIdLessThanOrEqualTo(Integer value) {
            addCriterion("`colorectal_cancer_treatment_ information_id` <=", value, "colorectalCancerTreatmentInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformationIdIn(List<Integer> values) {
            addCriterion("`colorectal_cancer_treatment_ information_id` in", values, "colorectalCancerTreatmentInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformationIdNotIn(List<Integer> values) {
            addCriterion("`colorectal_cancer_treatment_ information_id` not in", values, "colorectalCancerTreatmentInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformationIdBetween(Integer value1, Integer value2) {
            addCriterion("`colorectal_cancer_treatment_ information_id` between", value1, value2, "colorectalCancerTreatmentInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformationIdNotBetween(Integer value1, Integer value2) {
            addCriterion("`colorectal_cancer_treatment_ information_id` not between", value1, value2, "colorectalCancerTreatmentInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformatioStatusIsNull() {
            addCriterion("`colorectal_cancer_treatment_ informatio_status` is null");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformatioStatusIsNotNull() {
            addCriterion("`colorectal_cancer_treatment_ informatio_status` is not null");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformatioStatusEqualTo(String value) {
            addCriterion("`colorectal_cancer_treatment_ informatio_status` =", value, "colorectalCancerTreatmentInformatioStatus");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformatioStatusNotEqualTo(String value) {
            addCriterion("`colorectal_cancer_treatment_ informatio_status` <>", value, "colorectalCancerTreatmentInformatioStatus");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformatioStatusGreaterThan(String value) {
            addCriterion("`colorectal_cancer_treatment_ informatio_status` >", value, "colorectalCancerTreatmentInformatioStatus");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformatioStatusGreaterThanOrEqualTo(String value) {
            addCriterion("`colorectal_cancer_treatment_ informatio_status` >=", value, "colorectalCancerTreatmentInformatioStatus");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformatioStatusLessThan(String value) {
            addCriterion("`colorectal_cancer_treatment_ informatio_status` <", value, "colorectalCancerTreatmentInformatioStatus");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformatioStatusLessThanOrEqualTo(String value) {
            addCriterion("`colorectal_cancer_treatment_ informatio_status` <=", value, "colorectalCancerTreatmentInformatioStatus");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformatioStatusLike(String value) {
            addCriterion("`colorectal_cancer_treatment_ informatio_status` like", value, "colorectalCancerTreatmentInformatioStatus");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformatioStatusNotLike(String value) {
            addCriterion("`colorectal_cancer_treatment_ informatio_status` not like", value, "colorectalCancerTreatmentInformatioStatus");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformatioStatusIn(List<String> values) {
            addCriterion("`colorectal_cancer_treatment_ informatio_status` in", values, "colorectalCancerTreatmentInformatioStatus");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformatioStatusNotIn(List<String> values) {
            addCriterion("`colorectal_cancer_treatment_ informatio_status` not in", values, "colorectalCancerTreatmentInformatioStatus");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformatioStatusBetween(String value1, String value2) {
            addCriterion("`colorectal_cancer_treatment_ informatio_status` between", value1, value2, "colorectalCancerTreatmentInformatioStatus");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformatioStatusNotBetween(String value1, String value2) {
            addCriterion("`colorectal_cancer_treatment_ informatio_status` not between", value1, value2, "colorectalCancerTreatmentInformatioStatus");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformatioInStatusDateIsNull() {
            addCriterion("`colorectal_cancer_treatment_ informatio_in_status_date` is null");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformatioInStatusDateIsNotNull() {
            addCriterion("`colorectal_cancer_treatment_ informatio_in_status_date` is not null");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformatioInStatusDateEqualTo(Date value) {
            addCriterion("`colorectal_cancer_treatment_ informatio_in_status_date` =", value, "colorectalCancerTreatmentInformatioInStatusDate");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformatioInStatusDateNotEqualTo(Date value) {
            addCriterion("`colorectal_cancer_treatment_ informatio_in_status_date` <>", value, "colorectalCancerTreatmentInformatioInStatusDate");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformatioInStatusDateGreaterThan(Date value) {
            addCriterion("`colorectal_cancer_treatment_ informatio_in_status_date` >", value, "colorectalCancerTreatmentInformatioInStatusDate");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformatioInStatusDateGreaterThanOrEqualTo(Date value) {
            addCriterion("`colorectal_cancer_treatment_ informatio_in_status_date` >=", value, "colorectalCancerTreatmentInformatioInStatusDate");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformatioInStatusDateLessThan(Date value) {
            addCriterion("`colorectal_cancer_treatment_ informatio_in_status_date` <", value, "colorectalCancerTreatmentInformatioInStatusDate");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformatioInStatusDateLessThanOrEqualTo(Date value) {
            addCriterion("`colorectal_cancer_treatment_ informatio_in_status_date` <=", value, "colorectalCancerTreatmentInformatioInStatusDate");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformatioInStatusDateIn(List<Date> values) {
            addCriterion("`colorectal_cancer_treatment_ informatio_in_status_date` in", values, "colorectalCancerTreatmentInformatioInStatusDate");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformatioInStatusDateNotIn(List<Date> values) {
            addCriterion("`colorectal_cancer_treatment_ informatio_in_status_date` not in", values, "colorectalCancerTreatmentInformatioInStatusDate");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformatioInStatusDateBetween(Date value1, Date value2) {
            addCriterion("`colorectal_cancer_treatment_ informatio_in_status_date` between", value1, value2, "colorectalCancerTreatmentInformatioInStatusDate");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformatioInStatusDateNotBetween(Date value1, Date value2) {
            addCriterion("`colorectal_cancer_treatment_ informatio_in_status_date` not between", value1, value2, "colorectalCancerTreatmentInformatioInStatusDate");
            return (Criteria) this;
        }

        public Criteria andDeathCertificateIdIsNull() {
            addCriterion("`death_ certificate_id` is null");
            return (Criteria) this;
        }

        public Criteria andDeathCertificateIdIsNotNull() {
            addCriterion("`death_ certificate_id` is not null");
            return (Criteria) this;
        }

        public Criteria andDeathCertificateIdEqualTo(Integer value) {
            addCriterion("`death_ certificate_id` =", value, "deathCertificateId");
            return (Criteria) this;
        }

        public Criteria andDeathCertificateIdNotEqualTo(Integer value) {
            addCriterion("`death_ certificate_id` <>", value, "deathCertificateId");
            return (Criteria) this;
        }

        public Criteria andDeathCertificateIdGreaterThan(Integer value) {
            addCriterion("`death_ certificate_id` >", value, "deathCertificateId");
            return (Criteria) this;
        }

        public Criteria andDeathCertificateIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("`death_ certificate_id` >=", value, "deathCertificateId");
            return (Criteria) this;
        }

        public Criteria andDeathCertificateIdLessThan(Integer value) {
            addCriterion("`death_ certificate_id` <", value, "deathCertificateId");
            return (Criteria) this;
        }

        public Criteria andDeathCertificateIdLessThanOrEqualTo(Integer value) {
            addCriterion("`death_ certificate_id` <=", value, "deathCertificateId");
            return (Criteria) this;
        }

        public Criteria andDeathCertificateIdIn(List<Integer> values) {
            addCriterion("`death_ certificate_id` in", values, "deathCertificateId");
            return (Criteria) this;
        }

        public Criteria andDeathCertificateIdNotIn(List<Integer> values) {
            addCriterion("`death_ certificate_id` not in", values, "deathCertificateId");
            return (Criteria) this;
        }

        public Criteria andDeathCertificateIdBetween(Integer value1, Integer value2) {
            addCriterion("`death_ certificate_id` between", value1, value2, "deathCertificateId");
            return (Criteria) this;
        }

        public Criteria andDeathCertificateIdNotBetween(Integer value1, Integer value2) {
            addCriterion("`death_ certificate_id` not between", value1, value2, "deathCertificateId");
            return (Criteria) this;
        }

        public Criteria andDeathCertificateStatusIsNull() {
            addCriterion("`death_ certificate_status` is null");
            return (Criteria) this;
        }

        public Criteria andDeathCertificateStatusIsNotNull() {
            addCriterion("`death_ certificate_status` is not null");
            return (Criteria) this;
        }

        public Criteria andDeathCertificateStatusEqualTo(String value) {
            addCriterion("`death_ certificate_status` =", value, "deathCertificateStatus");
            return (Criteria) this;
        }

        public Criteria andDeathCertificateStatusNotEqualTo(String value) {
            addCriterion("`death_ certificate_status` <>", value, "deathCertificateStatus");
            return (Criteria) this;
        }

        public Criteria andDeathCertificateStatusGreaterThan(String value) {
            addCriterion("`death_ certificate_status` >", value, "deathCertificateStatus");
            return (Criteria) this;
        }

        public Criteria andDeathCertificateStatusGreaterThanOrEqualTo(String value) {
            addCriterion("`death_ certificate_status` >=", value, "deathCertificateStatus");
            return (Criteria) this;
        }

        public Criteria andDeathCertificateStatusLessThan(String value) {
            addCriterion("`death_ certificate_status` <", value, "deathCertificateStatus");
            return (Criteria) this;
        }

        public Criteria andDeathCertificateStatusLessThanOrEqualTo(String value) {
            addCriterion("`death_ certificate_status` <=", value, "deathCertificateStatus");
            return (Criteria) this;
        }

        public Criteria andDeathCertificateStatusLike(String value) {
            addCriterion("`death_ certificate_status` like", value, "deathCertificateStatus");
            return (Criteria) this;
        }

        public Criteria andDeathCertificateStatusNotLike(String value) {
            addCriterion("`death_ certificate_status` not like", value, "deathCertificateStatus");
            return (Criteria) this;
        }

        public Criteria andDeathCertificateStatusIn(List<String> values) {
            addCriterion("`death_ certificate_status` in", values, "deathCertificateStatus");
            return (Criteria) this;
        }

        public Criteria andDeathCertificateStatusNotIn(List<String> values) {
            addCriterion("`death_ certificate_status` not in", values, "deathCertificateStatus");
            return (Criteria) this;
        }

        public Criteria andDeathCertificateStatusBetween(String value1, String value2) {
            addCriterion("`death_ certificate_status` between", value1, value2, "deathCertificateStatus");
            return (Criteria) this;
        }

        public Criteria andDeathCertificateStatusNotBetween(String value1, String value2) {
            addCriterion("`death_ certificate_status` not between", value1, value2, "deathCertificateStatus");
            return (Criteria) this;
        }

        public Criteria andEndEventTypeIsNull() {
            addCriterion("end_event_type is null");
            return (Criteria) this;
        }

        public Criteria andEndEventTypeIsNotNull() {
            addCriterion("end_event_type is not null");
            return (Criteria) this;
        }

        public Criteria andEndEventTypeEqualTo(String value) {
            addCriterion("end_event_type =", value, "endEventType");
            return (Criteria) this;
        }

        public Criteria andEndEventTypeNotEqualTo(String value) {
            addCriterion("end_event_type <>", value, "endEventType");
            return (Criteria) this;
        }

        public Criteria andEndEventTypeGreaterThan(String value) {
            addCriterion("end_event_type >", value, "endEventType");
            return (Criteria) this;
        }

        public Criteria andEndEventTypeGreaterThanOrEqualTo(String value) {
            addCriterion("end_event_type >=", value, "endEventType");
            return (Criteria) this;
        }

        public Criteria andEndEventTypeLessThan(String value) {
            addCriterion("end_event_type <", value, "endEventType");
            return (Criteria) this;
        }

        public Criteria andEndEventTypeLessThanOrEqualTo(String value) {
            addCriterion("end_event_type <=", value, "endEventType");
            return (Criteria) this;
        }

        public Criteria andEndEventTypeLike(String value) {
            addCriterion("end_event_type like", value, "endEventType");
            return (Criteria) this;
        }

        public Criteria andEndEventTypeNotLike(String value) {
            addCriterion("end_event_type not like", value, "endEventType");
            return (Criteria) this;
        }

        public Criteria andEndEventTypeIn(List<String> values) {
            addCriterion("end_event_type in", values, "endEventType");
            return (Criteria) this;
        }

        public Criteria andEndEventTypeNotIn(List<String> values) {
            addCriterion("end_event_type not in", values, "endEventType");
            return (Criteria) this;
        }

        public Criteria andEndEventTypeBetween(String value1, String value2) {
            addCriterion("end_event_type between", value1, value2, "endEventType");
            return (Criteria) this;
        }

        public Criteria andEndEventTypeNotBetween(String value1, String value2) {
            addCriterion("end_event_type not between", value1, value2, "endEventType");
            return (Criteria) this;
        }

        public Criteria andValidDataIsNull() {
            addCriterion("valid_data is null");
            return (Criteria) this;
        }

        public Criteria andValidDataIsNotNull() {
            addCriterion("valid_data is not null");
            return (Criteria) this;
        }

        public Criteria andValidDataEqualTo(String value) {
            addCriterion("valid_data =", value, "validData");
            return (Criteria) this;
        }

        public Criteria andValidDataNotEqualTo(String value) {
            addCriterion("valid_data <>", value, "validData");
            return (Criteria) this;
        }

        public Criteria andValidDataGreaterThan(String value) {
            addCriterion("valid_data >", value, "validData");
            return (Criteria) this;
        }

        public Criteria andValidDataGreaterThanOrEqualTo(String value) {
            addCriterion("valid_data >=", value, "validData");
            return (Criteria) this;
        }

        public Criteria andValidDataLessThan(String value) {
            addCriterion("valid_data <", value, "validData");
            return (Criteria) this;
        }

        public Criteria andValidDataLessThanOrEqualTo(String value) {
            addCriterion("valid_data <=", value, "validData");
            return (Criteria) this;
        }

        public Criteria andValidDataLike(String value) {
            addCriterion("valid_data like", value, "validData");
            return (Criteria) this;
        }

        public Criteria andValidDataNotLike(String value) {
            addCriterion("valid_data not like", value, "validData");
            return (Criteria) this;
        }

        public Criteria andValidDataIn(List<String> values) {
            addCriterion("valid_data in", values, "validData");
            return (Criteria) this;
        }

        public Criteria andValidDataNotIn(List<String> values) {
            addCriterion("valid_data not in", values, "validData");
            return (Criteria) this;
        }

        public Criteria andValidDataBetween(String value1, String value2) {
            addCriterion("valid_data between", value1, value2, "validData");
            return (Criteria) this;
        }

        public Criteria andValidDataNotBetween(String value1, String value2) {
            addCriterion("valid_data not between", value1, value2, "validData");
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