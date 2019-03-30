/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.impl;

import com.yuntongxun.itsys.base.common.util.excelutil.ExcelIO;
import com.yuntongxun.itsys.base.common.util.excelutil.exception.ExcelIOException;
import com.yuntongxun.itsys.base.common.util.excelutil.export.bo.Sheet;
import com.yuntongxun.itsys.base.common.util.excelutil.export.bo.Table;
import com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.ExcelFormatControl;
import com.yuntongxun.itsys.base.common.util.excelutil.export.style.StyleControl;
import com.yuntongxun.itsys.base.common.util.excelutil.export.supply.TableBuilder;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;



/**
 * Title: POIImplExcelIO.java<br>
 * Description:Excel导入导出工具,POI 3.1实现<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-9-26 1.0
 */
public class POIImplExcelIO implements ExcelIO {

	private StyleControl styleControl;
	private XSSFWorkbook workbook = new XSSFWorkbook();

	/**
	 * (non-Javadoc)
	 * 
	 * @see //com.eorchis.module.excelutils.ExcelIO#exportExcel(String,
	 *      java.util.List)
	 */
	public void exportExcel(String exportFileRealPath, String styleName,
							Sheet[] sheetArray, boolean isMergedRegion) throws ExcelIOException {
		createExcelBook(styleName, sheetArray, isMergedRegion);
		try {
			OutputStream fos = new FileOutputStream(exportFileRealPath);
			outputExcel(fos);
		} catch (IOException e) {
			throw new ExcelIOException(e);
		}
	}

	private void init(String styleFile) throws Exception {
		try {
			styleControl = StyleControl.newInstance(styleFile);
		} catch (Exception e) {
			throw e;
		}
		styleControl.buildWorkbookStyle(workbook);
	}

	private void createExcelBook(String styleName, Sheet[] sheetArray,
			boolean isMergedRegion) throws ExcelIOException {
		if (styleName != null && !"".equals(styleName)) {
			try {
				init(styleName);
			} catch (Exception e) {
				//throw new ExcelIOException(e);
			}
		}
		for (int i = 0; i < sheetArray.length; i++) {
			Sheet exportSheet = sheetArray[i];
			XSSFSheet sheet = workbook.createSheet();
			workbook.setSheetName(i, exportSheet.getSheetName());
			try {
				buildSheet(sheet, exportSheet, isMergedRegion);
			} catch (Exception e) {
				throw new ExcelIOException(e);
			}
		}
	}

	private void outputExcel(OutputStream os) throws IOException {
		try {
			workbook.write(os);
		} catch (IOException e) {
			throw e;
		} finally {
			os.flush();
			os.close();
		}
	}

	private void buildSheet(XSSFSheet sheet, Sheet exportSheet,
			boolean isMergedRegion) throws Exception {
		if (exportSheet.getExportObject() == null)
			throw new NullPointerException(new StringBuffer(exportSheet
					.getSheetName()).append("'s exportObject is null!")
					.toString());
		Iterator tableIterator = ExcelFormatControl.newInstance(
				exportSheet.getExprotFormatName(),
				exportSheet.getExportObject()).getTableIterator();
		buildTables(sheet, tableIterator, isMergedRegion);
	}

	private void buildTables(XSSFSheet sheet, Iterator tableIterator,
			boolean isMergedRegion) {
		while (tableIterator.hasNext()) {
			TableBuilder.createTable(sheet, styleControl, (Table) tableIterator
					.next(), isMergedRegion);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see //com.eorchis.module.excelutils.ExcelIO#exportExcel(OutputStream,
	 *      //String, com.eorchis.module.excelutils.export.bo.Sheet[])
	 */
	public void exportExcel(OutputStream os, String styleName,
			Sheet[] sheetArray, boolean isMergedRegion) throws ExcelIOException {
		createExcelBook(styleName, sheetArray, isMergedRegion);
		try {
			outputExcel(os);
		} catch (IOException e) {
			throw new ExcelIOException(e);
		}
	}

}
