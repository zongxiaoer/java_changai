package com.yuntongxun.itsys.base.po.cancer;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class HospitalCancerInformationComplicationsPOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public HospitalCancerInformationComplicationsPOExample() {
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
            addCriterion("colorectal_cancer_diagnose_information_id is null");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationIdIsNotNull() {
            addCriterion("colorectal_cancer_diagnose_information_id is not null");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationIdEqualTo(Integer value) {
            addCriterion("colorectal_cancer_diagnose_information_id =", value, "colorectalCancerDiagnoseInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationIdNotEqualTo(Integer value) {
            addCriterion("colorectal_cancer_diagnose_information_id <>", value, "colorectalCancerDiagnoseInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationIdGreaterThan(Integer value) {
            addCriterion("colorectal_cancer_diagnose_information_id >", value, "colorectalCancerDiagnoseInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("colorectal_cancer_diagnose_information_id >=", value, "colorectalCancerDiagnoseInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationIdLessThan(Integer value) {
            addCriterion("colorectal_cancer_diagnose_information_id <", value, "colorectalCancerDiagnoseInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationIdLessThanOrEqualTo(Integer value) {
            addCriterion("colorectal_cancer_diagnose_information_id <=", value, "colorectalCancerDiagnoseInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationIdIn(List<Integer> values) {
            addCriterion("colorectal_cancer_diagnose_information_id in", values, "colorectalCancerDiagnoseInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationIdNotIn(List<Integer> values) {
            addCriterion("colorectal_cancer_diagnose_information_id not in", values, "colorectalCancerDiagnoseInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationIdBetween(Integer value1, Integer value2) {
            addCriterion("colorectal_cancer_diagnose_information_id between", value1, value2, "colorectalCancerDiagnoseInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerDiagnoseInformationIdNotBetween(Integer value1, Integer value2) {
            addCriterion("colorectal_cancer_diagnose_information_id not between", value1, value2, "colorectalCancerDiagnoseInformationId");
            return (Criteria) this;
        }

        public Criteria andComplicationsDateIsNull() {
            addCriterion("complications_date is null");
            return (Criteria) this;
        }

        public Criteria andComplicationsDateIsNotNull() {
            addCriterion("complications_date is not null");
            return (Criteria) this;
        }

        public Criteria andComplicationsDateEqualTo(Date value) {
            addCriterionForJDBCDate("complications_date =", value, "complicationsDate");
            return (Criteria) this;
        }

        public Criteria andComplicationsDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("complications_date <>", value, "complicationsDate");
            return (Criteria) this;
        }

        public Criteria andComplicationsDateGreaterThan(Date value) {
            addCriterionForJDBCDate("complications_date >", value, "complicationsDate");
            return (Criteria) this;
        }

        public Criteria andComplicationsDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("complications_date >=", value, "complicationsDate");
            return (Criteria) this;
        }

        public Criteria andComplicationsDateLessThan(Date value) {
            addCriterionForJDBCDate("complications_date <", value, "complicationsDate");
            return (Criteria) this;
        }

        public Criteria andComplicationsDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("complications_date <=", value, "complicationsDate");
            return (Criteria) this;
        }

        public Criteria andComplicationsDateIn(List<Date> values) {
            addCriterionForJDBCDate("complications_date in", values, "complicationsDate");
            return (Criteria) this;
        }

        public Criteria andComplicationsDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("complications_date not in", values, "complicationsDate");
            return (Criteria) this;
        }

        public Criteria andComplicationsDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("complications_date between", value1, value2, "complicationsDate");
            return (Criteria) this;
        }

        public Criteria andComplicationsDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("complications_date not between", value1, value2, "complicationsDate");
            return (Criteria) this;
        }

        public Criteria andComplicationsCodeIsNull() {
            addCriterion("complications_code is null");
            return (Criteria) this;
        }

        public Criteria andComplicationsCodeIsNotNull() {
            addCriterion("complications_code is not null");
            return (Criteria) this;
        }

        public Criteria andComplicationsCodeEqualTo(String value) {
            addCriterion("complications_code =", value, "complicationsCode");
            return (Criteria) this;
        }

        public Criteria andComplicationsCodeNotEqualTo(String value) {
            addCriterion("complications_code <>", value, "complicationsCode");
            return (Criteria) this;
        }

        public Criteria andComplicationsCodeGreaterThan(String value) {
            addCriterion("complications_code >", value, "complicationsCode");
            return (Criteria) this;
        }

        public Criteria andComplicationsCodeGreaterThanOrEqualTo(String value) {
            addCriterion("complications_code >=", value, "complicationsCode");
            return (Criteria) this;
        }

        public Criteria andComplicationsCodeLessThan(String value) {
            addCriterion("complications_code <", value, "complicationsCode");
            return (Criteria) this;
        }

        public Criteria andComplicationsCodeLessThanOrEqualTo(String value) {
            addCriterion("complications_code <=", value, "complicationsCode");
            return (Criteria) this;
        }

        public Criteria andComplicationsCodeLike(String value) {
            addCriterion("complications_code like", value, "complicationsCode");
            return (Criteria) this;
        }

        public Criteria andComplicationsCodeNotLike(String value) {
            addCriterion("complications_code not like", value, "complicationsCode");
            return (Criteria) this;
        }

        public Criteria andComplicationsCodeIn(List<String> values) {
            addCriterion("complications_code in", values, "complicationsCode");
            return (Criteria) this;
        }

        public Criteria andComplicationsCodeNotIn(List<String> values) {
            addCriterion("complications_code not in", values, "complicationsCode");
            return (Criteria) this;
        }

        public Criteria andComplicationsCodeBetween(String value1, String value2) {
            addCriterion("complications_code between", value1, value2, "complicationsCode");
            return (Criteria) this;
        }

        public Criteria andComplicationsCodeNotBetween(String value1, String value2) {
            addCriterion("complications_code not between", value1, value2, "complicationsCode");
            return (Criteria) this;
        }

        public Criteria andComplicationsCodeOtherIsNull() {
            addCriterion("complications_code_other is null");
            return (Criteria) this;
        }

        public Criteria andComplicationsCodeOtherIsNotNull() {
            addCriterion("complications_code_other is not null");
            return (Criteria) this;
        }

        public Criteria andComplicationsCodeOtherEqualTo(String value) {
            addCriterion("complications_code_other =", value, "complicationsCodeOther");
            return (Criteria) this;
        }

        public Criteria andComplicationsCodeOtherNotEqualTo(String value) {
            addCriterion("complications_code_other <>", value, "complicationsCodeOther");
            return (Criteria) this;
        }

        public Criteria andComplicationsCodeOtherGreaterThan(String value) {
            addCriterion("complications_code_other >", value, "complicationsCodeOther");
            return (Criteria) this;
        }

        public Criteria andComplicationsCodeOtherGreaterThanOrEqualTo(String value) {
            addCriterion("complications_code_other >=", value, "complicationsCodeOther");
            return (Criteria) this;
        }

        public Criteria andComplicationsCodeOtherLessThan(String value) {
            addCriterion("complications_code_other <", value, "complicationsCodeOther");
            return (Criteria) this;
        }

        public Criteria andComplicationsCodeOtherLessThanOrEqualTo(String value) {
            addCriterion("complications_code_other <=", value, "complicationsCodeOther");
            return (Criteria) this;
        }

        public Criteria andComplicationsCodeOtherLike(String value) {
            addCriterion("complications_code_other like", value, "complicationsCodeOther");
            return (Criteria) this;
        }

        public Criteria andComplicationsCodeOtherNotLike(String value) {
            addCriterion("complications_code_other not like", value, "complicationsCodeOther");
            return (Criteria) this;
        }

        public Criteria andComplicationsCodeOtherIn(List<String> values) {
            addCriterion("complications_code_other in", values, "complicationsCodeOther");
            return (Criteria) this;
        }

        public Criteria andComplicationsCodeOtherNotIn(List<String> values) {
            addCriterion("complications_code_other not in", values, "complicationsCodeOther");
            return (Criteria) this;
        }

        public Criteria andComplicationsCodeOtherBetween(String value1, String value2) {
            addCriterion("complications_code_other between", value1, value2, "complicationsCodeOther");
            return (Criteria) this;
        }

        public Criteria andComplicationsCodeOtherNotBetween(String value1, String value2) {
            addCriterion("complications_code_other not between", value1, value2, "complicationsCodeOther");
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