/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.style.config.bo;

/**
 * Title: BorderConfig.java<br>
 * Description:边框配置类<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-9-28 1.0
 */
public interface BorderConfig {

	public static final String TOP = "top";

	public static final String LEFT = "left";

	public static final String RIGHT = "right";

	public static final String BOTTOM = "bottom";

	/**
	 * 获取边框类型 top,left,right,bottom
	 * 
	 * @return
	 */
	public String getBorderType();

	/**
	 * 获取边框线样式
	 * 
	 * @return
	 */
	public String getLineType();

	/**
	 * 获取边框线颜色
	 * 
	 * @return
	 */
	public String getBorderLineColor();
}
