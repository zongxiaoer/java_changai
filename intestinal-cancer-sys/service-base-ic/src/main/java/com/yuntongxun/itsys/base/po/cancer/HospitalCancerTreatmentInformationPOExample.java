package com.yuntongxun.itsys.base.po.cancer;

import java.util.ArrayList;
import java.util.List;

public class HospitalCancerTreatmentInformationPOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public HospitalCancerTreatmentInformationPOExample() {
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

        public Criteria andColorectalCancerTreatmentInformationIdIsNull() {
            addCriterion("colorectal_cancer_treatment_information_id is null");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformationIdIsNotNull() {
            addCriterion("colorectal_cancer_treatment_information_id is not null");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformationIdEqualTo(Integer value) {
            addCriterion("colorectal_cancer_treatment_information_id =", value, "colorectalCancerTreatmentInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformationIdNotEqualTo(Integer value) {
            addCriterion("colorectal_cancer_treatment_information_id <>", value, "colorectalCancerTreatmentInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformationIdGreaterThan(Integer value) {
            addCriterion("colorectal_cancer_treatment_information_id >", value, "colorectalCancerTreatmentInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformationIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("colorectal_cancer_treatment_information_id >=", value, "colorectalCancerTreatmentInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformationIdLessThan(Integer value) {
            addCriterion("colorectal_cancer_treatment_information_id <", value, "colorectalCancerTreatmentInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformationIdLessThanOrEqualTo(Integer value) {
            addCriterion("colorectal_cancer_treatment_information_id <=", value, "colorectalCancerTreatmentInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformationIdIn(List<Integer> values) {
            addCriterion("colorectal_cancer_treatment_information_id in", values, "colorectalCancerTreatmentInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformationIdNotIn(List<Integer> values) {
            addCriterion("colorectal_cancer_treatment_information_id not in", values, "colorectalCancerTreatmentInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformationIdBetween(Integer value1, Integer value2) {
            addCriterion("colorectal_cancer_treatment_information_id between", value1, value2, "colorectalCancerTreatmentInformationId");
            return (Criteria) this;
        }

        public Criteria andColorectalCancerTreatmentInformationIdNotBetween(Integer value1, Integer value2) {
            addCriterion("colorectal_cancer_treatment_information_id not between", value1, value2, "colorectalCancerTreatmentInformationId");
            return (Criteria) this;
        }

        public Criteria andDoctorNameIsNull() {
            addCriterion("doctor_name is null");
            return (Criteria) this;
        }

        public Criteria andDoctorNameIsNotNull() {
            addCriterion("doctor_name is not null");
            return (Criteria) this;
        }

        public Criteria andDoctorNameEqualTo(String value) {
            addCriterion("doctor_name =", value, "doctorName");
            return (Criteria) this;
        }

        public Criteria andDoctorNameNotEqualTo(String value) {
            addCriterion("doctor_name <>", value, "doctorName");
            return (Criteria) this;
        }

        public Criteria andDoctorNameGreaterThan(String value) {
            addCriterion("doctor_name >", value, "doctorName");
            return (Criteria) this;
        }

        public Criteria andDoctorNameGreaterThanOrEqualTo(String value) {
            addCriterion("doctor_name >=", value, "doctorName");
            return (Criteria) this;
        }

        public Criteria andDoctorNameLessThan(String value) {
            addCriterion("doctor_name <", value, "doctorName");
            return (Criteria) this;
        }

        public Criteria andDoctorNameLessThanOrEqualTo(String value) {
            addCriterion("doctor_name <=", value, "doctorName");
            return (Criteria) this;
        }

        public Criteria andDoctorNameLike(String value) {
            addCriterion("doctor_name like", value, "doctorName");
            return (Criteria) this;
        }

        public Criteria andDoctorNameNotLike(String value) {
            addCriterion("doctor_name not like", value, "doctorName");
            return (Criteria) this;
        }

        public Criteria andDoctorNameIn(List<String> values) {
            addCriterion("doctor_name in", values, "doctorName");
            return (Criteria) this;
        }

        public Criteria andDoctorNameNotIn(List<String> values) {
            addCriterion("doctor_name not in", values, "doctorName");
            return (Criteria) this;
        }

        public Criteria andDoctorNameBetween(String value1, String value2) {
            addCriterion("doctor_name between", value1, value2, "doctorName");
            return (Criteria) this;
        }

        public Criteria andDoctorNameNotBetween(String value1, String value2) {
            addCriterion("doctor_name not between", value1, value2, "doctorName");
            return (Criteria) this;
        }

        public Criteria andMedicalInstitutionNameIsNull() {
            addCriterion("medical_institution_name is null");
            return (Criteria) this;
        }

        public Criteria andMedicalInstitutionNameIsNotNull() {
            addCriterion("medical_institution_name is not null");
            return (Criteria) this;
        }

        public Criteria andMedicalInstitutionNameEqualTo(String value) {
            addCriterion("medical_institution_name =", value, "medicalInstitutionName");
            return (Criteria) this;
        }

        public Criteria andMedicalInstitutionNameNotEqualTo(String value) {
            addCriterion("medical_institution_name <>", value, "medicalInstitutionName");
            return (Criteria) this;
        }

        public Criteria andMedicalInstitutionNameGreaterThan(String value) {
            addCriterion("medical_institution_name >", value, "medicalInstitutionName");
            return (Criteria) this;
        }

        public Criteria andMedicalInstitutionNameGreaterThanOrEqualTo(String value) {
            addCriterion("medical_institution_name >=", value, "medicalInstitutionName");
            return (Criteria) this;
        }

        public Criteria andMedicalInstitutionNameLessThan(String value) {
            addCriterion("medical_institution_name <", value, "medicalInstitutionName");
            return (Criteria) this;
        }

        public Criteria andMedicalInstitutionNameLessThanOrEqualTo(String value) {
            addCriterion("medical_institution_name <=", value, "medicalInstitutionName");
            return (Criteria) this;
        }

        public Criteria andMedicalInstitutionNameLike(String value) {
            addCriterion("medical_institution_name like", value, "medicalInstitutionName");
            return (Criteria) this;
        }

        public Criteria andMedicalInstitutionNameNotLike(String value) {
            addCriterion("medical_institution_name not like", value, "medicalInstitutionName");
            return (Criteria) this;
        }

        public Criteria andMedicalInstitutionNameIn(List<String> values) {
            addCriterion("medical_institution_name in", values, "medicalInstitutionName");
            return (Criteria) this;
        }

        public Criteria andMedicalInstitutionNameNotIn(List<String> values) {
            addCriterion("medical_institution_name not in", values, "medicalInstitutionName");
            return (Criteria) this;
        }

        public Criteria andMedicalInstitutionNameBetween(String value1, String value2) {
            addCriterion("medical_institution_name between", value1, value2, "medicalInstitutionName");
            return (Criteria) this;
        }

        public Criteria andMedicalInstitutionNameNotBetween(String value1, String value2) {
            addCriterion("medical_institution_name not between", value1, value2, "medicalInstitutionName");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andFaxIsNull() {
            addCriterion("fax is null");
            return (Criteria) this;
        }

        public Criteria andFaxIsNotNull() {
            addCriterion("fax is not null");
            return (Criteria) this;
        }

        public Criteria andFaxEqualTo(String value) {
            addCriterion("fax =", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxNotEqualTo(String value) {
            addCriterion("fax <>", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxGreaterThan(String value) {
            addCriterion("fax >", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxGreaterThanOrEqualTo(String value) {
            addCriterion("fax >=", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxLessThan(String value) {
            addCriterion("fax <", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxLessThanOrEqualTo(String value) {
            addCriterion("fax <=", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxLike(String value) {
            addCriterion("fax like", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxNotLike(String value) {
            addCriterion("fax not like", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxIn(List<String> values) {
            addCriterion("fax in", values, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxNotIn(List<String> values) {
            addCriterion("fax not in", values, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxBetween(String value1, String value2) {
            addCriterion("fax between", value1, value2, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxNotBetween(String value1, String value2) {
            addCriterion("fax not between", value1, value2, "fax");
            return (Criteria) this;
        }

        public Criteria andTelPhone1IsNull() {
            addCriterion("tel_phone1 is null");
            return (Criteria) this;
        }

        public Criteria andTelPhone1IsNotNull() {
            addCriterion("tel_phone1 is not null");
            return (Criteria) this;
        }

        public Criteria andTelPhone1EqualTo(String value) {
            addCriterion("tel_phone1 =", value, "telPhone1");
            return (Criteria) this;
        }

        public Criteria andTelPhone1NotEqualTo(String value) {
            addCriterion("tel_phone1 <>", value, "telPhone1");
            return (Criteria) this;
        }

        public Criteria andTelPhone1GreaterThan(String value) {
            addCriterion("tel_phone1 >", value, "telPhone1");
            return (Criteria) this;
        }

        public Criteria andTelPhone1GreaterThanOrEqualTo(String value) {
            addCriterion("tel_phone1 >=", value, "telPhone1");
            return (Criteria) this;
        }

        public Criteria andTelPhone1LessThan(String value) {
            addCriterion("tel_phone1 <", value, "telPhone1");
            return (Criteria) this;
        }

        public Criteria andTelPhone1LessThanOrEqualTo(String value) {
            addCriterion("tel_phone1 <=", value, "telPhone1");
            return (Criteria) this;
        }

        public Criteria andTelPhone1Like(String value) {
            addCriterion("tel_phone1 like", value, "telPhone1");
            return (Criteria) this;
        }

        public Criteria andTelPhone1NotLike(String value) {
            addCriterion("tel_phone1 not like", value, "telPhone1");
            return (Criteria) this;
        }

        public Criteria andTelPhone1In(List<String> values) {
            addCriterion("tel_phone1 in", values, "telPhone1");
            return (Criteria) this;
        }

        public Criteria andTelPhone1NotIn(List<String> values) {
            addCriterion("tel_phone1 not in", values, "telPhone1");
            return (Criteria) this;
        }

        public Criteria andTelPhone1Between(String value1, String value2) {
            addCriterion("tel_phone1 between", value1, value2, "telPhone1");
            return (Criteria) this;
        }

        public Criteria andTelPhone1NotBetween(String value1, String value2) {
            addCriterion("tel_phone1 not between", value1, value2, "telPhone1");
            return (Criteria) this;
        }

        public Criteria andTelPhone2IsNull() {
            addCriterion("tel_phone2 is null");
            return (Criteria) this;
        }

        public Criteria andTelPhone2IsNotNull() {
            addCriterion("tel_phone2 is not null");
            return (Criteria) this;
        }

        public Criteria andTelPhone2EqualTo(String value) {
            addCriterion("tel_phone2 =", value, "telPhone2");
            return (Criteria) this;
        }

        public Criteria andTelPhone2NotEqualTo(String value) {
            addCriterion("tel_phone2 <>", value, "telPhone2");
            return (Criteria) this;
        }

        public Criteria andTelPhone2GreaterThan(String value) {
            addCriterion("tel_phone2 >", value, "telPhone2");
            return (Criteria) this;
        }

        public Criteria andTelPhone2GreaterThanOrEqualTo(String value) {
            addCriterion("tel_phone2 >=", value, "telPhone2");
            return (Criteria) this;
        }

        public Criteria andTelPhone2LessThan(String value) {
            addCriterion("tel_phone2 <", value, "telPhone2");
            return (Criteria) this;
        }

        public Criteria andTelPhone2LessThanOrEqualTo(String value) {
            addCriterion("tel_phone2 <=", value, "telPhone2");
            return (Criteria) this;
        }

        public Criteria andTelPhone2Like(String value) {
            addCriterion("tel_phone2 like", value, "telPhone2");
            return (Criteria) this;
        }

        public Criteria andTelPhone2NotLike(String value) {
            addCriterion("tel_phone2 not like", value, "telPhone2");
            return (Criteria) this;
        }

        public Criteria andTelPhone2In(List<String> values) {
            addCriterion("tel_phone2 in", values, "telPhone2");
            return (Criteria) this;
        }

        public Criteria andTelPhone2NotIn(List<String> values) {
            addCriterion("tel_phone2 not in", values, "telPhone2");
            return (Criteria) this;
        }

        public Criteria andTelPhone2Between(String value1, String value2) {
            addCriterion("tel_phone2 between", value1, value2, "telPhone2");
            return (Criteria) this;
        }

        public Criteria andTelPhone2NotBetween(String value1, String value2) {
            addCriterion("tel_phone2 not between", value1, value2, "telPhone2");
            return (Criteria) this;
        }

        public Criteria andBlNumberIsNull() {
            addCriterion("bl_number is null");
            return (Criteria) this;
        }

        public Criteria andBlNumberIsNotNull() {
            addCriterion("bl_number is not null");
            return (Criteria) this;
        }

        public Criteria andBlNumberEqualTo(String value) {
            addCriterion("bl_number =", value, "blNumber");
            return (Criteria) this;
        }

        public Criteria andBlNumberNotEqualTo(String value) {
            addCriterion("bl_number <>", value, "blNumber");
            return (Criteria) this;
        }

        public Criteria andBlNumberGreaterThan(String value) {
            addCriterion("bl_number >", value, "blNumber");
            return (Criteria) this;
        }

        public Criteria andBlNumberGreaterThanOrEqualTo(String value) {
            addCriterion("bl_number >=", value, "blNumber");
            return (Criteria) this;
        }

        public Criteria andBlNumberLessThan(String value) {
            addCriterion("bl_number <", value, "blNumber");
            return (Criteria) this;
        }

        public Criteria andBlNumberLessThanOrEqualTo(String value) {
            addCriterion("bl_number <=", value, "blNumber");
            return (Criteria) this;
        }

        public Criteria andBlNumberLike(String value) {
            addCriterion("bl_number like", value, "blNumber");
            return (Criteria) this;
        }

        public Criteria andBlNumberNotLike(String value) {
            addCriterion("bl_number not like", value, "blNumber");
            return (Criteria) this;
        }

        public Criteria andBlNumberIn(List<String> values) {
            addCriterion("bl_number in", values, "blNumber");
            return (Criteria) this;
        }

        public Criteria andBlNumberNotIn(List<String> values) {
            addCriterion("bl_number not in", values, "blNumber");
            return (Criteria) this;
        }

        public Criteria andBlNumberBetween(String value1, String value2) {
            addCriterion("bl_number between", value1, value2, "blNumber");
            return (Criteria) this;
        }

        public Criteria andBlNumberNotBetween(String value1, String value2) {
            addCriterion("bl_number not between", value1, value2, "blNumber");
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