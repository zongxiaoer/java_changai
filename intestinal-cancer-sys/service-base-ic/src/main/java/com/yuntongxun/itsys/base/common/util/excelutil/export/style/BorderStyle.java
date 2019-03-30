/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.style;

/**
 * Title: BorderStyle.java<br>
 * Description: 边框样式接口<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-9-28 1.0
 */
public interface BorderStyle {

	/**
	 * 获取边框边线样式
	 * 
	 * @return
	 */
	public short getLineType();

	/**
	 * 获取边框颜色
	 * 
	 * @return
	 */
	public short getBorderColor();
}
