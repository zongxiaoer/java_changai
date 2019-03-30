/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config;


import com.yuntongxun.itsys.base.common.util.excelutil.export.bo.ExportObject;

/**
 * Title: ExcelConfigFactory.java<br>
 * Description: 模板配置工厂<br>
 * Company: Orchis Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-9-29 1.0
 */
public final class ExcelConfigFactory {

	private ExcelConfigFactory() {
	}

	/**
	 * 初始化Excel配置对象
	 * 
	 * @param configName
	 *            模板名称
	 * @param obj
	 *            导出对象
	 * @return 初始化好的Excel配置对象
	 * @throws Exception
	 */
	public static ExcelConfig newInstance(String configName, ExportObject obj)
			throws Exception {
		return new JdomExcelConfigImpl(configName, obj);
	}
}
