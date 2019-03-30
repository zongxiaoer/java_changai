/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.bo;

/**
 * Title: Sheet.java<br>
 * Description:sheet<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-9-26 1.0
 */
public interface Sheet {
	/**
	 * 返回导出Sheet对象
	 * 
	 * @return the exportObject
	 */
	public ExportObject getExportObject();

	/**
	 * 返回Sheet模板名称
	 * 
	 * @return the exprotFormatName
	 */
	public String getExprotFormatName();

	/**
	 * 返回Sheet名称
	 * 
	 * @return the sheetName
	 */
	public String getSheetName();
}
