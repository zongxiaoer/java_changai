/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config;

import com.yuntongxun.itsys.base.common.util.excelutil.export.bo.ExportObject;
import com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.bo.RootConfig;
import com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.element.ElementBuilderFactory;
import com.yuntongxun.itsys.base.common.util.excelutil.export.supply.reflect.exception.ExportObjectException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import java.util.List;


/**
 * Title: JdomExcelConfigImpl.java<br>
 * Description:解析输出格式Jdom实现<br>
 * Company: Orchis Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-9-29 1.0
 */
public class JdomExcelConfigImpl implements ExcelConfig {
	public static final String CONFIGROOT = "/excel-config/";
	public static final String EXTEND = ".xml";

	private RootConfig rootConfig;

	/**
	 * 模板配置JDOM实现类
	 * 
	 * @param configName
	 *            模板名称
	 * @param obj
	 *            导出对象
	 * @throws Exception
	 */
	public JdomExcelConfigImpl(String configName, ExportObject obj)
			throws Exception {
		Document doc = loadConfig(configName);
		initConfig(doc, obj);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see //com.eorchis.module.excelutils.export.datareader.config.ExcelConfig#getTables()
	 */
	public List getTables() {
		return this.rootConfig.getTableList();
	}

	private Document loadConfig(String configName) throws Exception {
//		String filePath = PathUtil.getClassPathPath() + CONFIGROOT + configName
//				+ EXTEND;
		String filePath = this.getClass().getClassLoader().getResource("")+CONFIGROOT + configName+ EXTEND ;

		return new SAXBuilder().build(filePath);
	}

	private void initConfig(Document doc, ExportObject obj)
			throws ExportObjectException {
		Element root = doc.getRootElement();
		Element body = (Element) root.getChildren("body").get(0);
		this.rootConfig = (RootConfig) ElementBuilderFactory.getInstance(body,
				obj);
	}
}
