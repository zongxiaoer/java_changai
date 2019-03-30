/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.style;

/**
 * Title: FontStyle.java<br>
 * Description:字体样式<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-9-28 1.0
 */
public interface FontStyle {

	/**
	 * 获取字体样式 宋体,黑体,幼圆
	 * 
	 * @return
	 */
	public String getFace();

	/**
	 * 获取字号
	 * 
	 * @return
	 */
	public short getFontSize();

	/**
	 * 获取字体颜色
	 * 
	 * @return
	 */
	public short getFontColor();

	/**
	 * 获取粗体
	 * 
	 * @return
	 */
	public short getFontBold();

	/**
	 * 获取下划线样式
	 * 
	 * @return
	 */
	public byte getUnderLine();

	/**
	 * 获取是否斜体
	 * 
	 * @return
	 */
	public boolean isItalic();
}
