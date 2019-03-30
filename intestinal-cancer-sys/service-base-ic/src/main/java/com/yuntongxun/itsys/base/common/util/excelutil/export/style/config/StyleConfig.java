/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.style.config;

import com.yuntongxun.itsys.base.common.util.excelutil.export.style.config.bo.CellConfig;

import java.util.Iterator;



/**
 * Title: StyleConfig.java<br>
 * Description: 样式配置<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-9-28 1.0
 */
public interface StyleConfig {

	public final String CONFIGROOT = "/excel-config/style/";

	public final String EXTEND = ".xml";

	public static final String FONT_STYLE = "font-style";

	public static final String FONT = "font";

	public static final String CELL_STYLE = "cell-style";

	public static final String CELL = "cell";

	public static final String BORDER = "border";

	/**
	 * 以Iterator形式返回所有单元样式 迭代中的枚举类型为CellConfig
	 * 
	 * @return
	 */
	public Iterator getCellStyleIterator();

	/**
	 * 根据样式名称获取样式
	 * 
	 * @param cellStyleName
	 *            样式名称
	 * @return
	 */
	public CellConfig getCellStyleSingle(String cellStyleName);
}
