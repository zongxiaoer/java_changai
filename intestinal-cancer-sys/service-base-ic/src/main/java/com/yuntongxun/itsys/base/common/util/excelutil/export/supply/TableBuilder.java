/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.supply;

import com.yuntongxun.itsys.base.common.util.excelutil.export.bo.Cell;
import com.yuntongxun.itsys.base.common.util.excelutil.export.bo.Row;
import com.yuntongxun.itsys.base.common.util.excelutil.export.bo.Table;
import com.yuntongxun.itsys.base.common.util.excelutil.export.style.CellStyle;
import com.yuntongxun.itsys.base.common.util.excelutil.export.style.StyleControl;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.Iterator;

/**
 * Title: TableBuilder.java<br>
 * Description:table构造器<br>
 * Company: Orchis Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-9-29 1.0
 */
public class TableBuilder {

	private XSSFSheet sheet;
	private StyleControl styleControl;
	private CellSpaceController cellSpaceController;

	private TableBuilder() {
	}

	public static void createTable(XSSFSheet sheet, StyleControl styleControl,
								   Table table, boolean isMergedRegion) {
		TableBuilder builder = new TableBuilder();
		builder.sheet = sheet;
		builder.styleControl = styleControl;
		builder.cellSpaceController = table.getWidth() == -1 ? CellSpaceController
				.newInstance()
				: CellSpaceController.newInstance(table.getWidth());
		builder.buildSheetRows(table.getRowIterator(), isMergedRegion);
	}

	private void buildSheetRows(Iterator rowIterator, boolean isMergedRegion) {
		int lastRownum = sheet.getLastRowNum();
		//FIXME 以下代码只能支持布局xml文件中有1个table元素
				while (rowIterator.hasNext()) {
					XSSFRow row = getRow(lastRownum);
					Row exportRow = (Row) rowIterator.next();
					lastRownum = buildRowCells(row, exportRow.getCellIterator(),
							isMergedRegion);
					lastRownum++;
				}
				//FIXME 以下代码只能从第二行输出
//				while (rowIterator.hasNext()) {
//					HSSFRow row = getRow(lastRownum + 1);
//					Row exportRow = (Row) rowIterator.next();
//					lastRownum = buildRowCells(row, exportRow.getCellIterator(),
//							isMergedRegion);
//				}
	}

	/**
	 * 构造POI行内容
	 * 
	 * @param row
	 *            待填充的行对象
	 * @param cellIterator
	 *            填充行信息
	 * @return 返回最后一行行号
	 */
	private int buildRowCells(XSSFRow row, Iterator cellIterator,
			boolean isMergedRegion) {
		for (int i = 0; cellIterator.hasNext(); i++) {
			Cell exportCell = (Cell) cellIterator.next();
			if (buildCell(row, exportCell, isMergedRegion))
				continue;
			else {
				row = getRow(row.getRowNum() + 1);
				buildCell(row, exportCell, isMergedRegion);
			}
		}
		return row.getRowNum();
	}

	private boolean buildCell(XSSFRow row, Cell exportCell,
			boolean isMergedRegion) {
		int cellStartCol = cellSpaceController.getUseablePositionX(row
				.getRowNum(), 0);
		if (cellStartCol == -1)
			return false;
		int cellEndCol = cellSpaceController.getUseablePositionX(row
				.getRowNum(), cellStartCol + exportCell.getColSpan());
		if (cellEndCol == -1)
			throw new IllegalArgumentException("单元格超出了行宽边界.请检查配置文件是否正确!");
		XSSFCell cell = row.createCell(cellStartCol);
		fillCellValue(cell, exportCell.getText());
		if (isMergedRegion) {
			int firstRow = row.getRowNum();
			int lastRow =  row.getRowNum()+ exportCell.getRowSpan();
			int firstCol = cellStartCol;
			int lastCol = cellEndCol;
			CellRangeAddress cellRangeAddress = new CellRangeAddress(firstRow,lastRow,firstCol,lastCol);
			sheet.addMergedRegion(cellRangeAddress);
		}
		cellSpaceController.registerSpace(row.getRowNum(), cellStartCol, row
				.getRowNum()
				+ exportCell.getRowSpan(), cellEndCol);
		String style = exportCell.getStyleName();
		if (style == null)
			return true;
		CellStyle cellStyle = styleControl.getCellStyleByName(style);
		fillCellStyle(sheet, cellStyle.getHSSfCellStyle(), row.getRowNum(), cellStartCol, row
				.getRowNum()
				+ exportCell.getRowSpan(), cellEndCol);

		cell.setCellStyle(cellStyle.getHSSfCellStyle());
		sheet.setColumnWidth(cell.getColumnIndex(),cellStyle.getWidth());
		
		return true;
	}

	private void fillCellStyle(XSSFSheet sheet, org.apache.poi.ss.usermodel.CellStyle style,
			int fromRow, int fromCol, int toRow, int toCol) {
		while (fromRow <= toRow) {
			XSSFRow row = getRow(fromRow);
			int loopCol = fromCol;
			while (loopCol <= toCol) {
				XSSFCell cell = getCell(row, loopCol);
				cell.setCellStyle(style);
				loopCol++;
			}
			fromRow++;
		}
	}

	private XSSFRow getRow(int rownum) {
		XSSFRow _row = this.sheet.getRow(rownum);
		return _row == null ? this.sheet.createRow(rownum) : _row;
	}

	private XSSFCell getCell(XSSFRow row, int cellnum) {
		XSSFCell _cell = row.getCell(cellnum);
		return _cell == null ? row.createCell((short) cellnum) : _cell;
	}

	private void fillCellValue(XSSFCell cell, Object value) {
		if (value instanceof Number) {
			cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(Double.parseDouble(value.toString()));
		} else if (value instanceof String) {
			cell.setCellValue(new XSSFRichTextString(value.toString()));
//			if (((String) value).startsWith("=")) {
//				cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
//				cell.setCellFormula(value.toString().substring(1));
//			} else {
//				cell.setCellValue(new XSSFRichTextString(value.toString()));
//			}
		} else if (value instanceof Boolean)
			cell.setCellValue(new Boolean(value.toString()).booleanValue());
	}
}
