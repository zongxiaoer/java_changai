package com.yuntongxun.itsys.base.common.util.excelutil.export.bo;

import java.util.ArrayList;
import java.util.List;

public class ExportBaseObject implements ExportObject {
	private List<?> exportResultList;
	private Object object;
	private List<ExportExcelTitle> excelTitles;
	
	public ExportBaseObject() {
		excelTitles = new ArrayList<ExportExcelTitle>();
	}
	
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public List<?> getExportResultList() {
		return exportResultList;
	}
	public void setExportResultList(List<?> exportResultList) {
		this.exportResultList = exportResultList;
	}

	public List<ExportExcelTitle> getExcelTitles() {
		return excelTitles;
	}
	
	public String[] getExcelTitleArray() {
		if(excelTitles!=null) {
			return excelTitles.toArray(new String[excelTitles.size()]);
		}
		return null;
	}

	public void setExcelTitles(String[] excelTitles) {
		if(excelTitles == null) {
			return ;
		}
		for(String excelTitle : excelTitles) {
			ExportExcelTitle exportExcelTitle = new ExportExcelTitle();
			exportExcelTitle.setExcelTitle(excelTitle);
			this.excelTitles.add(exportExcelTitle);
		}
	}
}
