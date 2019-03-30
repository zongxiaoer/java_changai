package com.yuntongxun.itsys.base.vo;

import com.yuntongxun.itsys.base.po.HospitalBiologicalSampleResultPO;

import java.util.List;
import java.util.Map;

/**
 * @author zongt
 * @date 2018/5/11
 */
public class HospitalBiologicalSampleResultVo extends HospitalBiologicalSampleResultPO {



    private String sSampleColumn;//血清位置
    private String sSampleLine;

    private String pSampleColumn;//血浆位置
    private String pSampleLine;

    private String wSampleColumn;//白细胞位置
    private String wSampleLine;
    private String wFrozenBoxCode;//白细胞冷冻盒
    private String sFrozenBoxCode;//血清冷冻盒
    private String pFrozenBoxCode;//血浆冷冻盒


    private String name;
    private String phone;
    private String group;
    private String collectStatusDateBySql;

    private String sampleTypeAll3;//血清、血浆、白细胞
    private String collectStatusStartDate;//开始时间
    private String collectStatusEndDate;//结束时间

    private Integer bloodSampleId;//血液样本

    private List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultPOList;

    private List<String> sampleColumnAndLine;

    private String frozenBoxCodeHeader;
    private boolean checklist;

    private Integer reslutStatus;

    private String areaName;//地区名称
    private String commName;//社区名称
    private String nickName;//创建人昵称


    private int pageSize;
    private int pageNo;

    private Integer pCourierStatus;//血浆快递
    private Integer sCourierStatus;//血清快递
    private Integer wCourierStatus;//白细胞快递
    private Integer mCourierStatus;//唾液快递
    private Integer fCourierStatus;//粪便快递

    private List<Map<String,String>> mapCourierStatus;//集合快递状态

  //新增
    private Integer ssid;
    private Integer sample_S;
    private Integer psid;
    private Integer sample_P;
    private Integer wsid;
    private Integer sample_W;
    private Integer msid;
    private Integer sample_M;
    private Integer fsid;
    private Integer sample_F;
    private Integer asid;
    private Integer sample_A;



    //新增登陆名称
    private String loginName;



   private String editoperationSource;
    private Integer operationSourceId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getCollectStatusDateBySql() {
        return collectStatusDateBySql;
    }

    public void setCollectStatusDateBySql(String collectStatusDateBySql) {
        this.collectStatusDateBySql = collectStatusDateBySql;
    }

    public String getSampleTypeAll3() {
        return sampleTypeAll3;
    }

    public void setSampleTypeAll3(String sampleTypeAll3) {
        this.sampleTypeAll3 = sampleTypeAll3;
    }

    public String getCollectStatusStartDate() {
        return collectStatusStartDate;
    }

    public void setCollectStatusStartDate(String collectStatusStartDate) {
        this.collectStatusStartDate = collectStatusStartDate;
    }

    public String getCollectStatusEndDate() {
        return collectStatusEndDate;
    }

    public void setCollectStatusEndDate(String collectStatusEndDate) {
        this.collectStatusEndDate = collectStatusEndDate;
    }

	public Integer getSsid() {
		return ssid;
	}

	public void setSsid(Integer ssid) {
		this.ssid = ssid;
	}

	public Integer getSample_S() {
		return sample_S;
	}

	public void setSample_S(Integer sample_S) {
		this.sample_S = sample_S;
	}

	public Integer getPsid() {
		return psid;
	}

	public void setPsid(Integer psid) {
		this.psid = psid;
	}

	public Integer getSample_P() {
		return sample_P;
	}

	public void setSample_P(Integer sample_P) {
		this.sample_P = sample_P;
	}

	public Integer getWsid() {
		return wsid;
	}

	public Integer getAsid() {
		return asid;
	}

	public void setAsid(Integer asid) {
		this.asid = asid;
	}

	public Integer getSample_A() {
		return sample_A;
	}

	public void setSample_A(Integer sample_A) {
		this.sample_A = sample_A;
	}

	public void setWsid(Integer wsid) {
		this.wsid = wsid;
	}

	public Integer getSample_W() {
		return sample_W;
	}

	public void setSample_W(Integer sample_W) {
		this.sample_W = sample_W;
	}

	public Integer getMsid() {
		return msid;
	}

	public void setMsid(Integer msid) {
		this.msid = msid;
	}

	public Integer getSample_M() {
		return sample_M;
	}

	public void setSample_M(Integer sample_M) {
		this.sample_M = sample_M;
	}

	public Integer getFsid() {
		return fsid;
	}

	public void setFsid(Integer fsid) {
		this.fsid = fsid;
	}

	public Integer getSample_F() {
		return sample_F;
	}

	public void setSample_F(Integer sample_F) {
		this.sample_F = sample_F;
	}

    public Integer getBloodSampleId() {
        return bloodSampleId;
    }

    public void setBloodSampleId(Integer bloodSampleId) {
        this.bloodSampleId = bloodSampleId;
    }

    public List<HospitalBiologicalSampleResultVo> getHospitalBiologicalSampleResultPOList() {
        return hospitalBiologicalSampleResultPOList;
    }

    public void setHospitalBiologicalSampleResultPOList(List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultPOList) {
        this.hospitalBiologicalSampleResultPOList = hospitalBiologicalSampleResultPOList;
    }

    public List<String> getSampleColumnAndLine() {
        return sampleColumnAndLine;
    }

    public void setSampleColumnAndLine(List<String> sampleColumnAndLine) {
        this.sampleColumnAndLine = sampleColumnAndLine;
    }

    public String getFrozenBoxCodeHeader() {
        return frozenBoxCodeHeader;
    }

    public void setFrozenBoxCodeHeader(String frozenBoxCodeHeader) {
        this.frozenBoxCodeHeader = frozenBoxCodeHeader;
    }

    public boolean isChecklist() {
        return checklist;
    }

    public void setChecklist(boolean checklist) {
        this.checklist = checklist;
    }

    public Integer getReslutStatus() {
        return reslutStatus;
    }

    public void setReslutStatus(Integer reslutStatus) {
        this.reslutStatus = reslutStatus;
    }

    public Integer getpCourierStatus() {
        return pCourierStatus;
    }

    public void setpCourierStatus(Integer pCourierStatus) {
        this.pCourierStatus = pCourierStatus;
    }

    public Integer getsCourierStatus() {
        return sCourierStatus;
    }

    public void setsCourierStatus(Integer sCourierStatus) {
        this.sCourierStatus = sCourierStatus;
    }

    public Integer getwCourierStatus() {
        return wCourierStatus;
    }

    public void setwCourierStatus(Integer wCourierStatus) {
        this.wCourierStatus = wCourierStatus;
    }

    public List<Map<String, String>> getMapCourierStatus() {
        return mapCourierStatus;
    }

    public void setMapCourierStatus(List<Map<String, String>> mapCourierStatus) {
        this.mapCourierStatus = mapCourierStatus;
    }

    public Integer getmCourierStatus() {
        return mCourierStatus;
    }

    public void setmCourierStatus(Integer mCourierStatus) {
        this.mCourierStatus = mCourierStatus;
    }

    public Integer getfCourierStatus() {
        return fCourierStatus;
    }

    public void setfCourierStatus(Integer fCourierStatus) {
        this.fCourierStatus = fCourierStatus;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCommName() {
        return commName;
    }

    public void setCommName(String commName) {
        this.commName = commName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getsSampleColumn() {
        return sSampleColumn;
    }

    public void setsSampleColumn(String sSampleColumn) {
        this.sSampleColumn = sSampleColumn;
    }

    public String getsSampleLine() {
        return sSampleLine;
    }

    public void setsSampleLine(String sSampleLine) {
        this.sSampleLine = sSampleLine;
    }

    public String getpSampleColumn() {
        return pSampleColumn;
    }

    public void setpSampleColumn(String pSampleColumn) {
        this.pSampleColumn = pSampleColumn;
    }

    public String getpSampleLine() {
        return pSampleLine;
    }

    public void setpSampleLine(String pSampleLine) {
        this.pSampleLine = pSampleLine;
    }

    public String getwSampleColumn() {
        return wSampleColumn;
    }

    public void setwSampleColumn(String wSampleColumn) {
        this.wSampleColumn = wSampleColumn;
    }

    public String getwSampleLine() {
        return wSampleLine;
    }

    public void setwSampleLine(String wSampleLine) {
        this.wSampleLine = wSampleLine;
    }

    public String getwFrozenBoxCode() {
        return wFrozenBoxCode;
    }

    public void setwFrozenBoxCode(String wFrozenBoxCode) {
        this.wFrozenBoxCode = wFrozenBoxCode;
    }

    public String getsFrozenBoxCode() {
        return sFrozenBoxCode;
    }

    public void setsFrozenBoxCode(String sFrozenBoxCode) {
        this.sFrozenBoxCode = sFrozenBoxCode;
    }

    public String getpFrozenBoxCode() {
        return pFrozenBoxCode;
    }

    public void setpFrozenBoxCode(String pFrozenBoxCode) {
        this.pFrozenBoxCode = pFrozenBoxCode;
    }

    public String getEditoperationSource() {
        return editoperationSource;
    }

    public void setEditoperationSource(String editoperationSource) {
        this.editoperationSource = editoperationSource;
    }

    public Integer getOperationSourceId() {
        return operationSourceId;
    }

    public void setOperationSourceId(Integer operationSourceId) {
        this.operationSourceId = operationSourceId;
    }


}
