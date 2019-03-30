/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil;


import com.yuntongxun.itsys.base.common.util.excelutil.impl.POIImplExcelIO;

/**
 * Title: ExcelIOFactory.java<br>
 * Description:Excel输入输出工具工厂<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-10-8 1.0
 */
public class ExcelIOFactory {

	private ExcelIOFactory() {
	}

	public static ExcelIO getExcelIO() {
		return new POIImplExcelIO();
	}
}
