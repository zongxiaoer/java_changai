package com.yuntongxun.itsys.base.vo;

import com.yuntongxun.itsys.base.po.*;
import com.yuntongxun.itsys.base.po.dto.HospitalRiskFactorDto;

import java.util.List;

/**
 * Description: 受试者详情请求体
 *
 * @author LuoKun on 2018-04-16.
 */
public class PersonInfo {

    private HospitalReview base;//base

    private List<HospitalFitResult> fit;//fit

    private List<HospitalStoolDNA> dna;//DNA
    //新增生物样本
    private List<HospitalBiologicalSampleResultVo> sample;  //生物样本

	private List<HospitalColonoscopyRecord> colonoscopy;

    private List<HospitalAbnormalEvent> hospitalAbnormalEvent;

    private HospitalRiskFactor HospitalRiskFactor;//危险因素



    public HospitalReview getBase() {
        return base;
    }

    public void setBase(HospitalReview base) {
        this.base = base;
    }

    public List<HospitalFitResult> getFit() {
        return fit;
    }

    public void setFit(List<HospitalFitResult> fit) {
        this.fit = fit;
    }

    public List<HospitalStoolDNA> getDna() {
        return dna;
    }

    public void setDna(List<HospitalStoolDNA> dna) {
        this.dna = dna;
    }

    public List<HospitalColonoscopyRecord> getColonoscopy() {
        return colonoscopy;
    }

    public void setColonoscopy(List<HospitalColonoscopyRecord> colonoscopy) {
        this.colonoscopy = colonoscopy;
    }

    public List<HospitalAbnormalEvent> getHospitalAbnormalEvent() {
        return hospitalAbnormalEvent;
    }

    public void setHospitalAbnormalEvent(List<HospitalAbnormalEvent> hospitalAbnormalEvent) {
        this.hospitalAbnormalEvent = hospitalAbnormalEvent;
    }

	public List<HospitalBiologicalSampleResultVo> getSample() {
		return sample;
	}

	public void setSample(List<HospitalBiologicalSampleResultVo> sample) {
		this.sample = sample;
	}

    public com.yuntongxun.itsys.base.po.HospitalRiskFactor getHospitalRiskFactor() {
        return HospitalRiskFactor;
    }

    public void setHospitalRiskFactor(com.yuntongxun.itsys.base.po.HospitalRiskFactor hospitalRiskFactor) {
        HospitalRiskFactor = hospitalRiskFactor;
    }
}
