package com.yuntongxun.itsys.base.common.util.excelutil.util;

import com.yuntongxun.itsys.base.common.util.excelutil.ExcelIO;
import com.yuntongxun.itsys.base.common.util.excelutil.ExcelIOFactory;
import com.yuntongxun.itsys.base.common.util.excelutil.export.bo.ExportBaseObject;
import com.yuntongxun.itsys.base.common.util.excelutil.export.bo.ExportObject;
import com.yuntongxun.itsys.base.common.util.excelutil.export.bo.Sheet;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;


public class ExcelExportUtil {
	
	public static final String DEFAULT_EXPORT_STYLE = "ExportStyle";
	
	/**
	 * 根据条件构造sheet
	 * @param sheetName sheet名称
	 * @param styleConfigName 要生成的模版名称
	 * @param excelTitles 表头
	 * @param exportResults 结果集
	 * @return
	 * @author Lmk
	 */
	public static Sheet buildSheet(final String sheetName, final String styleConfigName,
			final String[] excelTitles, final List<?> exportResults) {
		Sheet sheet = new Sheet() {
			public String getSheetName() {
				return sheetName;
			}
			public String getExprotFormatName() {
				return styleConfigName;
			}
			public ExportObject getExportObject() {
				ExportBaseObject baseObject = new ExportBaseObject();
				baseObject.setExcelTitles(excelTitles);
				baseObject.setExportResultList(exportResults);
				return baseObject;
			}
		};
		return sheet;
	}
	
	public static Sheet buildSheet(final String sheetName, final String styleConfigName,
			final List<String> excelTitles, final List<?> exportResults) {
		return ExcelExportUtil.buildSheet(sheetName, styleConfigName, excelTitles.toArray(new String[excelTitles.size()]), exportResults);
	}
	
	/**
	 * 根据条件构造sheet，表头在模版中写死的情况
	 * @param sheetName sheet名称
	 * @param styleConfigName 要生成的模版名称
	 * @param exportResults 结果集
	 * @return
	 * @author Lmk
	 */
	public static Sheet buildSheet(final String sheetName, final String styleConfigName,final List<?> exportResults) {
		Sheet sheet = new Sheet() {
			public String getSheetName() {
				return sheetName;
			}
			public String getExprotFormatName() {
				return styleConfigName;
			}
			public ExportObject getExportObject() {
				ExportBaseObject baseObject = new ExportBaseObject();
				baseObject.setExportResultList(exportResults);
				return baseObject;
			}
		};
		return sheet;
	}
	
	/**
	 * 根据条件构造sheet
	 * @param sheetNamesheet 名
	 * @param styleConfigName 要生成的模版名称
	 * @param exportObject 要生成的表格内容，一般用于扩展 <? extends ExportObject>
	 * @return
	 * @author Lmk
	 */
	public static Sheet buildSheet(final String sheetName,final String styleConfigName,
			final ExportBaseObject exportObject) {
		Sheet sheet = new Sheet() {
			public String getSheetName() {
				return sheetName;
			}
			public String getExprotFormatName() {
				return styleConfigName;
			}
			public ExportObject getExportObject() {
				return exportObject;
			}
		};
		return sheet;
	}

	/**
	 * 导出excel方法
	 * @param response 设置导出信息
	 * @param fileName 文件名
	 * @param exportStyle 样式
	 * @param sheetArray sheet 数组
	 * @throws Exception 
	 * @author Lmk
	 */
	public static void exportExcel(HttpServletResponse response,String fileName,String exportStyle,Sheet...sheetArray) throws Exception {
		exportExcel(response, fileName, exportStyle, true, sheetArray);
	}
	
	/**
	 * 导出excel方法，使用默认的样式
	* @param response 设置导出信息
	 * @param fileName 文件名
	 * * @param isMergedRegion 是否格局xml定义的合并参数合并单元格
	 * @param sheetArray sheet 数组
	 * @author Lmk
	 */
	public static void exportExcel(HttpServletResponse response,String fileName,boolean isMergedRegion,Sheet...sheetArray) throws Exception {
		exportExcel(response, fileName, DEFAULT_EXPORT_STYLE, isMergedRegion, sheetArray);
	}
	
	/**
	 * 导出excel方法
	 * @param response 设置导出信息
	 * @param fileName 文件名
	 * @param exportStyle 样式
	 * * @param isMergedRegion 是否格局xml定义的合并参数合并单元格
	 * @param sheetArray sheet 数组
	 * @throws Exception 
	 * @author Lmk
	 */
	public static void exportExcel(HttpServletResponse response,String fileName,String exportStyle,boolean isMergedRegion,Sheet...sheetArray) throws Exception {
		response.setContentType("application/msexcel;charset=ISO8859-1");
		response.setHeader("Content-disposition","attachment; filename="+java.net.URLEncoder.encode(fileName, "UTF-8")+".xlsx");
		ExcelIO excelio = ExcelIOFactory.getExcelIO();
		excelio.exportExcel(response.getOutputStream(), exportStyle, sheetArray, isMergedRegion);
	}
	
	/**
	 * 导出excel方法，使用默认的样式
	* @param response 设置导出信息
	 * @param fileName 文件名
	 * @param sheetArray sheet 数组
	 * @author Lmk
	 */
	public static void exportExcel(HttpServletResponse response,String fileName,Sheet...sheetArray) throws Exception {
		ExcelExportUtil.exportExcel(response, fileName, DEFAULT_EXPORT_STYLE, sheetArray);
	}
	
	/**
	 * 通过传入输出流导出文件
	 * @param output 输出流
	 * @param exportStyle excel样式
	 * @param isMergedRegion 是否格局xml定义的合并参数合并单元格
	 * @param sheetArray sheet 数组
	 * @throws Exception
	 */
	public static void exportExcel(OutputStream output,String exportStyle,boolean isMergedRegion,Sheet...sheetArray) throws Exception {
		ExcelIO excelio = ExcelIOFactory.getExcelIO();
		excelio.exportExcel(output, exportStyle, sheetArray, isMergedRegion);
	}
	
	/**
	 * 通过传入输出流导出文件
	 * @param output 输出流
	 * @param isMergedRegion 是否格局xml定义的合并参数合并单元格
	 * @param sheetArray sheet 数组
	 * @throws Exception
	 */
	public static void exportExcel(OutputStream output,boolean isMergedRegion,Sheet...sheetArray) throws Exception {
		ExcelIO excelio = ExcelIOFactory.getExcelIO();
		excelio.exportExcel(output, DEFAULT_EXPORT_STYLE, sheetArray, isMergedRegion);
	}
	
	/**
	 * 通过传入输出流导出文件
	 * @param output 输出流
	 * @param sheetArray sheet 数组
	 * @throws Exception
	 */
	public static void exportExcel(OutputStream output,Sheet...sheetArray) throws Exception {
		ExcelIO excelio = ExcelIOFactory.getExcelIO();
		excelio.exportExcel(output, DEFAULT_EXPORT_STYLE, sheetArray, true);
	}
	
	/**
	 * 通过设置路径导出文件
	 * @param exportFileRealPath 导出文件路径
	 * @param exportStyle excel样式
	 * @param isMergedRegion是否格局xml定义的合并参数合并单元格
	 * @param sheetArraysheet 数组
	 * @throws Exception
	 */
	public static void ExportExcel(String exportFileRealPath, String exportStyle,boolean isMergedRegion,Sheet... sheetArray) throws Exception {
		ExcelIO excelio = ExcelIOFactory.getExcelIO();
		excelio.exportExcel(exportFileRealPath, exportStyle, sheetArray, isMergedRegion);
	}
	/**
	 * 通过设置路径导出文件
	 * @param exportFileRealPath 导出文件路径
	 * @param isMergedRegion是否格局xml定义的合并参数合并单元格
	 * @param sheetArraysheet 数组
	 * @throws Exception
	 */
	public static void ExportExcel(String exportFileRealPath,boolean isMergedRegion,Sheet... sheetArray) throws Exception {
		ExcelIO excelio = ExcelIOFactory.getExcelIO();
		excelio.exportExcel(exportFileRealPath, DEFAULT_EXPORT_STYLE, sheetArray, isMergedRegion);
	}
	
	/**
	 * 通过设置路径导出文件
	 * @param exportFileRealPath 导出文件路径
	 * @param sheetArraysheet 数组
	 * @throws Exception
	 */
	public static void ExportExcel(String exportFileRealPath,Sheet... sheetArray) throws Exception {
		ExcelIO excelio = ExcelIOFactory.getExcelIO();
		excelio.exportExcel(exportFileRealPath, DEFAULT_EXPORT_STYLE, sheetArray, true);
	}
}
