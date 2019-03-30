package com.yuntongxun.itsys.base.po.cancer;

import java.io.Serializable;
public class HospitalCancerTreatmentInformationPO implements Serializable {
    private Integer id;

    private Integer colorectalCancerTreatmentInformationId;

    private String doctorName;

    private String medicalInstitutionName;

    private String address;

    private String email;

    private String fax;

    private String telPhone1;

    private String telPhone2;

    private String blNumber;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getColorectalCancerTreatmentInformationId() {
        return colorectalCancerTreatmentInformationId;
    }

    public void setColorectalCancerTreatmentInformationId(Integer colorectalCancerTreatmentInformationId) {
        this.colorectalCancerTreatmentInformationId = colorectalCancerTreatmentInformationId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName == null ? null : doctorName.trim();
    }

    public String getMedicalInstitutionName() {
        return medicalInstitutionName;
    }

    public void setMedicalInstitutionName(String medicalInstitutionName) {
        this.medicalInstitutionName = medicalInstitutionName == null ? null : medicalInstitutionName.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getTelPhone1() {
        return telPhone1;
    }

    public void setTelPhone1(String telPhone1) {
        this.telPhone1 = telPhone1 == null ? null : telPhone1.trim();
    }

    public String getTelPhone2() {
        return telPhone2;
    }

    public void setTelPhone2(String telPhone2) {
        this.telPhone2 = telPhone2 == null ? null : telPhone2.trim();
    }

    public String getBlNumber() {
        return blNumber;
    }

    public void setBlNumber(String blNumber) {
        this.blNumber = blNumber == null ? null : blNumber.trim();
    }
}