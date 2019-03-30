package com.yuntongxun.itsys.base.po.cancer;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class HospitalInformationDiagnoseEvaluationPOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public HospitalInformationDiagnoseEvaluationPOExample() {
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

        public Criteria andCheckDateIsNull() {
            addCriterion("check_date is null");
            return (Criteria) this;
        }

        public Criteria andCheckDateIsNotNull() {
            addCriterion("check_date is not null");
            return (Criteria) this;
        }

        public Criteria andCheckDateEqualTo(Date value) {
            addCriterionForJDBCDate("check_date =", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("check_date <>", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateGreaterThan(Date value) {
            addCriterionForJDBCDate("check_date >", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("check_date >=", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateLessThan(Date value) {
            addCriterionForJDBCDate("check_date <", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("check_date <=", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateIn(List<Date> values) {
            addCriterionForJDBCDate("check_date in", values, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("check_date not in", values, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("check_date between", value1, value2, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("check_date not between", value1, value2, "checkDate");
            return (Criteria) this;
        }

        public Criteria andDiagnosticMethodsIsNull() {
            addCriterion("diagnostic_methods is null");
            return (Criteria) this;
        }

        public Criteria andDiagnosticMethodsIsNotNull() {
            addCriterion("diagnostic_methods is not null");
            return (Criteria) this;
        }

        public Criteria andDiagnosticMethodsEqualTo(String value) {
            addCriterion("diagnostic_methods =", value, "diagnosticMethods");
            return (Criteria) this;
        }

        public Criteria andDiagnosticMethodsNotEqualTo(String value) {
            addCriterion("diagnostic_methods <>", value, "diagnosticMethods");
            return (Criteria) this;
        }

        public Criteria andDiagnosticMethodsGreaterThan(String value) {
            addCriterion("diagnostic_methods >", value, "diagnosticMethods");
            return (Criteria) this;
        }

        public Criteria andDiagnosticMethodsGreaterThanOrEqualTo(String value) {
            addCriterion("diagnostic_methods >=", value, "diagnosticMethods");
            return (Criteria) this;
        }

        public Criteria andDiagnosticMethodsLessThan(String value) {
            addCriterion("diagnostic_methods <", value, "diagnosticMethods");
            return (Criteria) this;
        }

        public Criteria andDiagnosticMethodsLessThanOrEqualTo(String value) {
            addCriterion("diagnostic_methods <=", value, "diagnosticMethods");
            return (Criteria) this;
        }

        public Criteria andDiagnosticMethodsLike(String value) {
            addCriterion("diagnostic_methods like", value, "diagnosticMethods");
            return (Criteria) this;
        }

        public Criteria andDiagnosticMethodsNotLike(String value) {
            addCriterion("diagnostic_methods not like", value, "diagnosticMethods");
            return (Criteria) this;
        }

        public Criteria andDiagnosticMethodsIn(List<String> values) {
            addCriterion("diagnostic_methods in", values, "diagnosticMethods");
            return (Criteria) this;
        }

        public Criteria andDiagnosticMethodsNotIn(List<String> values) {
            addCriterion("diagnostic_methods not in", values, "diagnosticMethods");
            return (Criteria) this;
        }

        public Criteria andDiagnosticMethodsBetween(String value1, String value2) {
            addCriterion("diagnostic_methods between", value1, value2, "diagnosticMethods");
            return (Criteria) this;
        }

        public Criteria andDiagnosticMethodsNotBetween(String value1, String value2) {
            addCriterion("diagnostic_methods not between", value1, value2, "diagnosticMethods");
            return (Criteria) this;
        }

        public Criteria andDiagnosticMethodsOtherIsNull() {
            addCriterion("diagnostic_methods_other is null");
            return (Criteria) this;
        }

        public Criteria andDiagnosticMethodsOtherIsNotNull() {
            addCriterion("diagnostic_methods_other is not null");
            return (Criteria) this;
        }

        public Criteria andDiagnosticMethodsOtherEqualTo(String value) {
            addCriterion("diagnostic_methods_other =", value, "diagnosticMethodsOther");
            return (Criteria) this;
        }

        public Criteria andDiagnosticMethodsOtherNotEqualTo(String value) {
            addCriterion("diagnostic_methods_other <>", value, "diagnosticMethodsOther");
            return (Criteria) this;
        }

        public Criteria andDiagnosticMethodsOtherGreaterThan(String value) {
            addCriterion("diagnostic_methods_other >", value, "diagnosticMethodsOther");
            return (Criteria) this;
        }

        public Criteria andDiagnosticMethodsOtherGreaterThanOrEqualTo(String value) {
            addCriterion("diagnostic_methods_other >=", value, "diagnosticMethodsOther");
            return (Criteria) this;
        }

        public Criteria andDiagnosticMethodsOtherLessThan(String value) {
            addCriterion("diagnostic_methods_other <", value, "diagnosticMethodsOther");
            return (Criteria) this;
        }

        public Criteria andDiagnosticMethodsOtherLessThanOrEqualTo(String value) {
            addCriterion("diagnostic_methods_other <=", value, "diagnosticMethodsOther");
            return (Criteria) this;
        }

        public Criteria andDiagnosticMethodsOtherLike(String value) {
            addCriterion("diagnostic_methods_other like", value, "diagnosticMethodsOther");
            return (Criteria) this;
        }

        public Criteria andDiagnosticMethodsOtherNotLike(String value) {
            addCriterion("diagnostic_methods_other not like", value, "diagnosticMethodsOther");
            return (Criteria) this;
        }

        public Criteria andDiagnosticMethodsOtherIn(List<String> values) {
            addCriterion("diagnostic_methods_other in", values, "diagnosticMethodsOther");
            return (Criteria) this;
        }

        public Criteria andDiagnosticMethodsOtherNotIn(List<String> values) {
            addCriterion("diagnostic_methods_other not in", values, "diagnosticMethodsOther");
            return (Criteria) this;
        }

        public Criteria andDiagnosticMethodsOtherBetween(String value1, String value2) {
            addCriterion("diagnostic_methods_other between", value1, value2, "diagnosticMethodsOther");
            return (Criteria) this;
        }

        public Criteria andDiagnosticMethodsOtherNotBetween(String value1, String value2) {
            addCriterion("diagnostic_methods_other not between", value1, value2, "diagnosticMethodsOther");
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