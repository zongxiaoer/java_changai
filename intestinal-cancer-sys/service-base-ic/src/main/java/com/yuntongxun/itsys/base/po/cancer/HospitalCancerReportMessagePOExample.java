package com.yuntongxun.itsys.base.po.cancer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HospitalCancerReportMessagePOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public HospitalCancerReportMessagePOExample() {
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

        public Criteria andCancerTypeSiteIsNull() {
            addCriterion("cancer_type_site is null");
            return (Criteria) this;
        }

        public Criteria andCancerTypeSiteIsNotNull() {
            addCriterion("cancer_type_site is not null");
            return (Criteria) this;
        }

        public Criteria andCancerTypeSiteEqualTo(String value) {
            addCriterion("cancer_type_site =", value, "cancerTypeSite");
            return (Criteria) this;
        }

        public Criteria andCancerTypeSiteNotEqualTo(String value) {
            addCriterion("cancer_type_site <>", value, "cancerTypeSite");
            return (Criteria) this;
        }

        public Criteria andCancerTypeSiteGreaterThan(String value) {
            addCriterion("cancer_type_site >", value, "cancerTypeSite");
            return (Criteria) this;
        }

        public Criteria andCancerTypeSiteGreaterThanOrEqualTo(String value) {
            addCriterion("cancer_type_site >=", value, "cancerTypeSite");
            return (Criteria) this;
        }

        public Criteria andCancerTypeSiteLessThan(String value) {
            addCriterion("cancer_type_site <", value, "cancerTypeSite");
            return (Criteria) this;
        }

        public Criteria andCancerTypeSiteLessThanOrEqualTo(String value) {
            addCriterion("cancer_type_site <=", value, "cancerTypeSite");
            return (Criteria) this;
        }

        public Criteria andCancerTypeSiteLike(String value) {
            addCriterion("cancer_type_site like", value, "cancerTypeSite");
            return (Criteria) this;
        }

        public Criteria andCancerTypeSiteNotLike(String value) {
            addCriterion("cancer_type_site not like", value, "cancerTypeSite");
            return (Criteria) this;
        }

        public Criteria andCancerTypeSiteIn(List<String> values) {
            addCriterion("cancer_type_site in", values, "cancerTypeSite");
            return (Criteria) this;
        }

        public Criteria andCancerTypeSiteNotIn(List<String> values) {
            addCriterion("cancer_type_site not in", values, "cancerTypeSite");
            return (Criteria) this;
        }

        public Criteria andCancerTypeSiteBetween(String value1, String value2) {
            addCriterion("cancer_type_site between", value1, value2, "cancerTypeSite");
            return (Criteria) this;
        }

        public Criteria andCancerTypeSiteNotBetween(String value1, String value2) {
            addCriterion("cancer_type_site not between", value1, value2, "cancerTypeSite");
            return (Criteria) this;
        }

        public Criteria andReportDateIsNull() {
            addCriterion("report_date is null");
            return (Criteria) this;
        }

        public Criteria andReportDateIsNotNull() {
            addCriterion("report_date is not null");
            return (Criteria) this;
        }

        public Criteria andReportDateEqualTo(Date value) {
            addCriterion("report_date =", value, "reportDate");
            return (Criteria) this;
        }

        public Criteria andReportDateNotEqualTo(Date value) {
            addCriterion("report_date <>", value, "reportDate");
            return (Criteria) this;
        }

        public Criteria andReportDateGreaterThan(Date value) {
            addCriterion("report_date >", value, "reportDate");
            return (Criteria) this;
        }

        public Criteria andReportDateGreaterThanOrEqualTo(Date value) {
            addCriterion("report_date >=", value, "reportDate");
            return (Criteria) this;
        }

        public Criteria andReportDateLessThan(Date value) {
            addCriterion("report_date <", value, "reportDate");
            return (Criteria) this;
        }

        public Criteria andReportDateLessThanOrEqualTo(Date value) {
            addCriterion("report_date <=", value, "reportDate");
            return (Criteria) this;
        }

        public Criteria andReportDateIn(List<Date> values) {
            addCriterion("report_date in", values, "reportDate");
            return (Criteria) this;
        }

        public Criteria andReportDateNotIn(List<Date> values) {
            addCriterion("report_date not in", values, "reportDate");
            return (Criteria) this;
        }

        public Criteria andReportDateBetween(Date value1, Date value2) {
            addCriterion("report_date between", value1, value2, "reportDate");
            return (Criteria) this;
        }

        public Criteria andReportDateNotBetween(Date value1, Date value2) {
            addCriterion("report_date not between", value1, value2, "reportDate");
            return (Criteria) this;
        }

        public Criteria andDiagnoseSourceIsNull() {
            addCriterion("diagnose_source is null");
            return (Criteria) this;
        }

        public Criteria andDiagnoseSourceIsNotNull() {
            addCriterion("diagnose_source is not null");
            return (Criteria) this;
        }

        public Criteria andDiagnoseSourceEqualTo(String value) {
            addCriterion("diagnose_source =", value, "diagnoseSource");
            return (Criteria) this;
        }

        public Criteria andDiagnoseSourceNotEqualTo(String value) {
            addCriterion("diagnose_source <>", value, "diagnoseSource");
            return (Criteria) this;
        }

        public Criteria andDiagnoseSourceGreaterThan(String value) {
            addCriterion("diagnose_source >", value, "diagnoseSource");
            return (Criteria) this;
        }

        public Criteria andDiagnoseSourceGreaterThanOrEqualTo(String value) {
            addCriterion("diagnose_source >=", value, "diagnoseSource");
            return (Criteria) this;
        }

        public Criteria andDiagnoseSourceLessThan(String value) {
            addCriterion("diagnose_source <", value, "diagnoseSource");
            return (Criteria) this;
        }

        public Criteria andDiagnoseSourceLessThanOrEqualTo(String value) {
            addCriterion("diagnose_source <=", value, "diagnoseSource");
            return (Criteria) this;
        }

        public Criteria andDiagnoseSourceLike(String value) {
            addCriterion("diagnose_source like", value, "diagnoseSource");
            return (Criteria) this;
        }

        public Criteria andDiagnoseSourceNotLike(String value) {
            addCriterion("diagnose_source not like", value, "diagnoseSource");
            return (Criteria) this;
        }

        public Criteria andDiagnoseSourceIn(List<String> values) {
            addCriterion("diagnose_source in", values, "diagnoseSource");
            return (Criteria) this;
        }

        public Criteria andDiagnoseSourceNotIn(List<String> values) {
            addCriterion("diagnose_source not in", values, "diagnoseSource");
            return (Criteria) this;
        }

        public Criteria andDiagnoseSourceBetween(String value1, String value2) {
            addCriterion("diagnose_source between", value1, value2, "diagnoseSource");
            return (Criteria) this;
        }

        public Criteria andDiagnoseSourceNotBetween(String value1, String value2) {
            addCriterion("diagnose_source not between", value1, value2, "diagnoseSource");
            return (Criteria) this;
        }

        public Criteria andDiagnoseSourceOtherIsNull() {
            addCriterion("diagnose_source_other is null");
            return (Criteria) this;
        }

        public Criteria andDiagnoseSourceOtherIsNotNull() {
            addCriterion("diagnose_source_other is not null");
            return (Criteria) this;
        }

        public Criteria andDiagnoseSourceOtherEqualTo(String value) {
            addCriterion("diagnose_source_other =", value, "diagnoseSourceOther");
            return (Criteria) this;
        }

        public Criteria andDiagnoseSourceOtherNotEqualTo(String value) {
            addCriterion("diagnose_source_other <>", value, "diagnoseSourceOther");
            return (Criteria) this;
        }

        public Criteria andDiagnoseSourceOtherGreaterThan(String value) {
            addCriterion("diagnose_source_other >", value, "diagnoseSourceOther");
            return (Criteria) this;
        }

        public Criteria andDiagnoseSourceOtherGreaterThanOrEqualTo(String value) {
            addCriterion("diagnose_source_other >=", value, "diagnoseSourceOther");
            return (Criteria) this;
        }

        public Criteria andDiagnoseSourceOtherLessThan(String value) {
            addCriterion("diagnose_source_other <", value, "diagnoseSourceOther");
            return (Criteria) this;
        }

        public Criteria andDiagnoseSourceOtherLessThanOrEqualTo(String value) {
            addCriterion("diagnose_source_other <=", value, "diagnoseSourceOther");
            return (Criteria) this;
        }

        public Criteria andDiagnoseSourceOtherLike(String value) {
            addCriterion("diagnose_source_other like", value, "diagnoseSourceOther");
            return (Criteria) this;
        }

        public Criteria andDiagnoseSourceOtherNotLike(String value) {
            addCriterion("diagnose_source_other not like", value, "diagnoseSourceOther");
            return (Criteria) this;
        }

        public Criteria andDiagnoseSourceOtherIn(List<String> values) {
            addCriterion("diagnose_source_other in", values, "diagnoseSourceOther");
            return (Criteria) this;
        }

        public Criteria andDiagnoseSourceOtherNotIn(List<String> values) {
            addCriterion("diagnose_source_other not in", values, "diagnoseSourceOther");
            return (Criteria) this;
        }

        public Criteria andDiagnoseSourceOtherBetween(String value1, String value2) {
            addCriterion("diagnose_source_other between", value1, value2, "diagnoseSourceOther");
            return (Criteria) this;
        }

        public Criteria andDiagnoseSourceOtherNotBetween(String value1, String value2) {
            addCriterion("diagnose_source_other not between", value1, value2, "diagnoseSourceOther");
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