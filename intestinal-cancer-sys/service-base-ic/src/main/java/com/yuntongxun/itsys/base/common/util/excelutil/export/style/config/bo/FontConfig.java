/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.style.config.bo;

/**
 * Title: FontConfig.java<br>
 * Description:字体配置接口<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-9-28 1.0
 */
public interface FontConfig {

	/**
	 * 获取配置名称
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 获取字体样式
	 * 
	 * @return
	 */
	public String getFace();

	/**
	 * 获取字号
	 * 
	 * @return
	 */
	public String getSize();

	/**
	 * 获取字体颜色
	 * 
	 * @return
	 */
	public String getColor();

	/**
	 * 获取是否加粗
	 * 
	 * @return
	 */
	public boolean isBold();

	/**
	 * 获取下划线样式
	 * 
	 * @return
	 */
	public String getUnderLine();

	/**
	 * 获取是否为斜体字
	 * 
	 * @return
	 */
	public boolean isItalic();
}
