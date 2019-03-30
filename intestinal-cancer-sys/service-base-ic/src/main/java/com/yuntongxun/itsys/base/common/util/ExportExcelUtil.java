package com.yuntongxun.itsys.base.common.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.OutputStream;
import java.util.Vector;

@SuppressWarnings("deprecation")
public class ExportExcelUtil {
	private XSSFWorkbook wb = null;

	private XSSFSheet sheet = null;

	/**
	 * @param wb
	 * @param sheet
	 */
	public ExportExcelUtil(XSSFWorkbook wb, XSSFSheet sheet) {
		this.wb = wb;
		this.sheet = sheet;
	}

	/**
	 * 合并单元格后给合并后的单元格加边框
	 * 
	 * @param region
	 * @param cs
	 */
	public void setRegionStyle(CellRangeAddress region, XSSFCellStyle cs) {

		int toprowNum = region.getFirstRow();
		for (int i = toprowNum; i <= region.getLastRow(); i++) {
			XSSFRow row = sheet.getRow(i);
			for (int j = region.getFirstColumn(); j <= region.getLastColumn(); j++) {
				XSSFCell cell = row.getCell(j);// XSSFCellUtil.getCell(row,
												// (short) j);
				cell.setCellStyle(cs);
			}
		}
	}

	/**
	 * 设置表头的单元格样式
	 * 
	 * @return
	 */
	public XSSFCellStyle getHeadStyle() {
		// 创建单元格样式
		XSSFCellStyle cellStyle = wb.createCellStyle();
		// 设置单元格的背景颜色为淡蓝色
		cellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		// 设置单元格居中对齐
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		// 设置单元格垂直居中对齐
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		// 创建单元格内容显示不下时自动换行
		cellStyle.setWrapText(true);
		// 设置单元格字体样式
		XSSFFont font = wb.createFont();
		// 设置字体加粗
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 200);
		cellStyle.setFont(font);
		// 设置单元格边框为细线条
		cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		return cellStyle;
	}

	/**
	 * 设置表体的单元格样式
	 * 
	 * @return
	 */
	public XSSFCellStyle getBodyStyle() {
		// 创建单元格样式
		XSSFCellStyle cellStyle = wb.createCellStyle();
		// 设置单元格居中对齐
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		// 设置单元格垂直居中对齐
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		// 创建单元格内容显示不下时自动换行
		cellStyle.setWrapText(true);
		// 设置单元格字体样式
		XSSFFont font = wb.createFont();
		// 设置字体加粗
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 200);
		cellStyle.setFont(font);
		// 设置单元格边框为细线条
		cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		return cellStyle;
	}
	
	
	/**
	 * 
	 * @param headStr			表格内大title
	 * @param titleVec			字段名称
	 * @param titleWidthAry		没列宽度
	 * @param bodyAry			内容
	 * @param os				输出流
	 * @param sheetName			EXCEL页标名称(sheet1)
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static void excelOS(String headStr,Vector titleVec,int[] titleWidthAry,String[][] bodyAry,OutputStream os,String sheetName) throws Exception{
	    try{
			XSSFWorkbook wb = new XSSFWorkbook();

			XSSFSheet  sheet = wb.createSheet("sheet");
			wb.setSheetName(0,sheetName);
			sheet.getPrintSetup().setLandscape(true);//true：横向  false：纵向 
			
			XSSFFont font = wb.createFont();
			font.setFontName(HSSFFont.FONT_ARIAL);
			font.setFontHeightInPoints((short)10);
            
			XSSFFont titleFont = wb.createFont();
			titleFont.setFontHeightInPoints((short)10);
			titleFont.setFontName("楷体_GB2312");
			titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			
			XSSFCellStyle titleStyle = wb.createCellStyle();
			titleStyle.setFont(titleFont);
			titleStyle.setBorderLeft((short) 1);
			titleStyle.setBorderRight((short) 1);
			titleStyle.setBorderTop((short) 1);
			titleStyle.setBorderBottom((short) 1);
			titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			titleStyle.setWrapText(true);
			titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			titleStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			
			
			XSSFCellStyle style = wb.createCellStyle();
			style.setFont(font);
			style.setBorderLeft((short) 1);
			style.setBorderRight((short) 1);
			style.setBorderTop((short) 1);
			style.setBorderBottom((short) 1);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style.setWrapText(true);


			XSSFFont headFont = wb.createFont();
			headFont.setFontHeightInPoints((short)18);
			headFont.setFontName("楷体_GB2312");
			headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

			XSSFCellStyle headStyle = wb.createCellStyle();
			headStyle.setFont(headFont);
			headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);

			setColumnWidth(sheet, titleWidthAry);

			//第一行合并
			sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short)(titleWidthAry.length-1)));//合并单元格
			XSSFRow headRowOne = sheet.createRow((short) 0);
			headRowOne.setHeight((short) 500);
			XSSFCell headCellOne = headRowOne.createCell((short) 0);
			//headCellOne.setEncoding(HSSFCell.ENCODING_UTF_16);
			headCellOne.setCellStyle(headStyle);
			headCellOne.setCellValue(headStr);
			
			//数据项描述
			XSSFRow rowTitle = sheet.createRow(1);
			for (int i = 0; i < titleVec.size(); i++) {
				XSSFCell titleCell = rowTitle.createCell((short) i);
				//titleCell.setEncoding(HSSFCell.ENCODING_UTF_16);
				titleCell.setCellValue((String) titleVec.get(i));
				titleCell.setCellStyle(titleStyle);
			}

			int listFlag = 2;
			if(bodyAry!=null){
				for (int i=0; i <bodyAry.length; i++) {
					XSSFRow row = sheet.createRow(listFlag);
					int dataFlag = 0;
					XSSFCell Contentcell = null;
					for (int j=0; j<bodyAry[i].length; j++) {
						Contentcell = row.createCell((short) (dataFlag));
						Contentcell.setCellStyle(style);
						//Contentcell.setEncoding(HSSFCell.ENCODING_UTF_16);
						Contentcell.setCellValue(bodyAry[i][j]);
						dataFlag++;
					}
					listFlag++;
				}
			}
			 os.flush();
			 wb.write(os);
	    }
		catch(Exception ex){
			System.out.println(ExportExcelUtil.class.getName()+".excelOS(String headStr,Vector titleVec,int[] titleWidthAry,String[][] bodyAry,OutputStream os)>>"+ex.toString());
	        System.out.println(ExportExcelUtil.class.getName()+".excelOS(String headStr,Vector titleVec,int[] titleWidthAry,String[][] bodyAry,OutputStream os)>>"+ex.toString());
			ex.printStackTrace();
		}
	}

	/**
	 *
	 * @param excelData
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static void excelOS(ExcelData excelData) throws Exception{
		try{
			XSSFWorkbook wb = new XSSFWorkbook();

			XSSFSheet  sheet = wb.createSheet("sheet");
			wb.setSheetName(0,excelData.getSheetName());
			sheet.getPrintSetup().setLandscape(true);//true：横向  false：纵向

			XSSFFont font = wb.createFont();
			font.setFontName(HSSFFont.FONT_ARIAL);
			font.setFontHeightInPoints((short)10);

			XSSFFont titleFont = wb.createFont();
			titleFont.setFontHeightInPoints((short)10);
			titleFont.setFontName("楷体_GB2312");
			titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

			XSSFCellStyle titleStyle = wb.createCellStyle();
			titleStyle.setFont(titleFont);
			titleStyle.setBorderLeft((short) 1);
			titleStyle.setBorderRight((short) 1);
			titleStyle.setBorderTop((short) 1);
			titleStyle.setBorderBottom((short) 1);
			titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			titleStyle.setWrapText(true);
			titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			titleStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);


			XSSFCellStyle style = wb.createCellStyle();
			style.setFont(font);
			style.setBorderLeft((short) 1);
			style.setBorderRight((short) 1);
			style.setBorderTop((short) 1);
			style.setBorderBottom((short) 1);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style.setWrapText(true);


			XSSFFont headFont = wb.createFont();
			headFont.setFontHeightInPoints((short)18);
			headFont.setFontName("楷体_GB2312");
			headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

			XSSFCellStyle headStyle = wb.createCellStyle();
			headStyle.setFont(headFont);
			headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);

			setColumnWidth(sheet, excelData.getTitleWidthAry());

			//第一行合并
			sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short)(excelData.getTitleWidthAry().length-1)));//合并单元格
			XSSFRow headRowOne = sheet.createRow((short) 0);
			headRowOne.setHeight((short) 500);
			XSSFCell headCellOne = headRowOne.createCell((short) 0);
			//headCellOne.setEncoding(HSSFCell.ENCODING_UTF_16);
			headCellOne.setCellStyle(headStyle);
			headCellOne.setCellValue(excelData.getHeadStr());

			//数据项描述
			XSSFRow rowTitle = sheet.createRow(1);
			for (int i = 0; i < excelData.getTitleVec().size(); i++) {
				XSSFCell titleCell = rowTitle.createCell((short) i);
				//titleCell.setEncoding(HSSFCell.ENCODING_UTF_16);
				titleCell.setCellValue((String) excelData.getTitleVec().get(i));
				titleCell.setCellStyle(titleStyle);
			}

			int listFlag = 2;
			if(excelData.getBodyAry()!=null){
				for (int i=0; i <excelData.getBodyAry().length; i++) {
					XSSFRow row = sheet.createRow(listFlag);
					int dataFlag = 0;
					XSSFCell Contentcell = null;
					for (int j=0; j<excelData.getBodyAry()[i].length; j++) {
						Contentcell = row.createCell((short) (dataFlag));
						Contentcell.setCellStyle(style);
						//Contentcell.setEncoding(HSSFCell.ENCODING_UTF_16);
						Contentcell.setCellValue(excelData.getBodyAry()[i][j]);
						dataFlag++;
					}
					listFlag++;
				}
			}
			excelData.getOs().flush();
			wb.write(excelData.getOs());
		}
		catch(Exception ex){
			System.out.println(ExportExcelUtil.class.getName()+".excelOS(String headStr,Vector titleVec,int[] titleWidthAry,String[][] bodyAry,OutputStream os)>>"+ex.toString());
			System.out.println(ExportExcelUtil.class.getName()+".excelOS(String headStr,Vector titleVec,int[] titleWidthAry,String[][] bodyAry,OutputStream os)>>"+ex.toString());
			ex.printStackTrace();
		}
	}

	/**
	 *
	 * @param excelData    zhaoli  添加方法  去掉标题行操作
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static void excelOSCope(ExcelData excelData) throws Exception{
		try{
			XSSFWorkbook wb = new XSSFWorkbook();

			XSSFSheet  sheet = wb.createSheet("sheet");
			wb.setSheetName(0,excelData.getSheetName());
			sheet.getPrintSetup().setLandscape(true);//true：横向  false：纵向

			XSSFFont font = wb.createFont();
			font.setFontName(HSSFFont.FONT_ARIAL);
			font.setFontHeightInPoints((short)10);

			XSSFFont titleFont = wb.createFont();
			titleFont.setFontHeightInPoints((short)10);
			titleFont.setFontName("楷体_GB2312");
			titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

			XSSFCellStyle titleStyle = wb.createCellStyle();
			titleStyle.setFont(titleFont);
			titleStyle.setBorderLeft((short) 1);
			titleStyle.setBorderRight((short) 1);
			titleStyle.setBorderTop((short) 1);
			titleStyle.setBorderBottom((short) 1);
			titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			titleStyle.setWrapText(true);
			titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			titleStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);


			XSSFCellStyle style = wb.createCellStyle();
			style.setFont(font);
			style.setBorderLeft((short) 1);
			style.setBorderRight((short) 1);
			style.setBorderTop((short) 1);
			style.setBorderBottom((short) 1);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style.setWrapText(true);


			XSSFFont headFont = wb.createFont();
			headFont.setFontHeightInPoints((short)18);
			headFont.setFontName("楷体_GB2312");
			headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

			XSSFCellStyle headStyle = wb.createCellStyle();
			headStyle.setFont(headFont);
			headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);

			setColumnWidth(sheet, excelData.getTitleWidthAry());


			//数据项描述
			XSSFRow rowTitle = sheet.createRow(0);
			for (int i = 0; i < excelData.getTitleVec().size(); i++) {
				XSSFCell titleCell = rowTitle.createCell((short) i);
				//titleCell.setEncoding(HSSFCell.ENCODING_UTF_16);
				titleCell.setCellValue((String) excelData.getTitleVec().get(i));
				titleCell.setCellStyle(titleStyle);
			}

			int listFlag = 1;
			if(excelData.getBodyAry()!=null){
				for (int i=0; i <excelData.getBodyAry().length; i++) {
					XSSFRow row = sheet.createRow(listFlag);
					int dataFlag = 0;
					XSSFCell Contentcell = null;
					for (int j=0; j<excelData.getBodyAry()[i].length; j++) {
						Contentcell = row.createCell((short) (dataFlag));
						Contentcell.setCellStyle(style);
						//Contentcell.setEncoding(HSSFCell.ENCODING_UTF_16);
						Contentcell.setCellValue(excelData.getBodyAry()[i][j]);
						dataFlag++;
					}
					listFlag++;
				}
			}
			excelData.getOs().flush();
			wb.write(excelData.getOs());
		}
		catch(Exception ex){
			System.out.println(ExportExcelUtil.class.getName()+".excelOS(String headStr,Vector titleVec,int[] titleWidthAry,String[][] bodyAry,OutputStream os)>>"+ex.toString());
			System.out.println(ExportExcelUtil.class.getName()+".excelOS(String headStr,Vector titleVec,int[] titleWidthAry,String[][] bodyAry,OutputStream os)>>"+ex.toString());
			ex.printStackTrace();
		}
	}
	
	//导出表格设置用
	private static void setColumnWidth(XSSFSheet sheet, int[] width) {
		for (int i = 0; i < width.length; i++) {
			sheet.setColumnWidth((short) i, (short) (width[i] * 256));
		}
	}
	
	public static void setSheetColumnWidth(Sheet sheet2) {
		 
		// 根据你数据里面的记录有多少列，就设置多少列
		sheet2.setColumnWidth(0, 5000);
		sheet2.setColumnWidth(1, 5000);
		sheet2.setColumnWidth(2, 5000);
		sheet2.setColumnWidth(3, 5000);
		sheet2.setColumnWidth(4, 5000);
		sheet2.setColumnWidth(5, 5000);
		sheet2.setColumnWidth(6, 5000);
		sheet2.setColumnWidth(7, 5000);
	}
	// 设置excel的title样式
	 
	public static CellStyle createTitleStyle(Workbook workbook) {
	 
		Font boldFont = workbook.createFont();
		 
		boldFont.setFontHeight((short) 200);
		 
		CellStyle style = workbook.createCellStyle();
		 
		style.setFont(boldFont);
		 
		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("###,##0.00"));
		 
		return style;
	 
	}
	
	
	// 创建Excel单元格
	 
	public static void createCell(Row row0, int column, CellStyle style,
	 
	int cellType, Object value) {
	 
	Cell cell = row0.createCell(column);
	 
	if (style != null) {
	 
	cell.setCellStyle(style);
	 
	}
	 
	switch (cellType) {
	 
	case Cell.CELL_TYPE_BLANK: {
	 
	}
	 
	break;
	 
	case Cell.CELL_TYPE_STRING: {
	 
	cell.setCellValue(value==null?"":value.toString());
	 
	}
	 
	break;
	 
	case Cell.CELL_TYPE_NUMERIC: {
	 
	cell.setCellType(Cell.CELL_TYPE_NUMERIC);
	 
	cell.setCellValue(Double.parseDouble(value.toString()));
	 
	}
	 
	break;
	 
	default:
	 
	break;
	 
	}

	}
}
