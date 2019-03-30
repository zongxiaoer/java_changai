/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil;

import com.yuntongxun.itsys.base.common.util.excelutil.exception.ExcelIOException;
import com.yuntongxun.itsys.base.common.util.excelutil.export.bo.Sheet;

import java.io.OutputStream;



/**
 * Title: ExcelIO.java<br>
 * Description:Excel导入导出工具，使用前须将jvm内存大小调至最低-Xms256m -Xmx512m<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-9-26 1.0
 */
public interface ExcelIO {

	/**
	 * 导出算法
	 * 
	 * @param exportFileRealPath
	 *            导出文件路径
	 * @param styleName
	 *            样式配置名称，如果为null则不使用样式（数据量大的excel建议不使用，否则内存溢出）
	 * @param sheetArray
	 *            待导出的sheet集合
	 * @param isMergedRegion
	 *            是否需要合并单元格（数据量大的excel建议不开，否则内存溢出）
	 * @throws ExcelIOException
	 */
	public void exportExcel(String exportFileRealPath, String styleName,
							Sheet[] sheetArray, boolean isMergedRegion) throws ExcelIOException;

	/**
	 * 导出算法<br>
	 * 此方法自动关闭输出流
	 * 
	 * @param os
	 *            导出填充流
	 * @param styleName
	 *            样式配置名称，如果为null则不使用样式（数据量大的excel建议不使用，否则内存溢出）
	 * @param sheetArray
	 *            待导出的sheet集合
	 * @param isMergedRegion
	 *            是否需要合并单元格（数据量大的excel建议不开，否则内存溢出）
	 * @throws ExcelIOException
	 */
	public void exportExcel(OutputStream os, String styleName,
                            Sheet[] sheetArray, boolean isMergedRegion) throws ExcelIOException;
}
