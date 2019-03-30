/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.style.config.bo;

/**
 * Title: CellConfig.java<br>
 * Description:单元格配置接口<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-9-28 1.0
 */
public interface CellConfig {

	/**
	 * 获取单元配置名称
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 获取对其方式
	 * 
	 * @return
	 */
	public String getAlign();

	/**
	 * 获取背景色
	 * 
	 * @return
	 */
	public String getBackgroundColor();

	/**
	 * 获取该单元配置中的字体配置
	 * 
	 * @return
	 */
	public FontConfig getFontConfig();

	/**
	 * 获取背景填充样式
	 * 
	 * @return
	 */
	public String getBackgroundFillStyle();
	/**
	 * 获取单元格宽度
	 * @return
	 */
	public int getWidth();
	/**
	 * 获取该样式的某个边框
	 * 
	 * @param flag
	 *            边框标识符,值为<a>BorderConfig</a>接口中声明的常量之一
	 * @return
	 */
	public BorderConfig getBorderConfig(String flag);
	/**
	 * 获取单元格是否自动换行
	 * @return
	 */
	public boolean getWrapped();
}
