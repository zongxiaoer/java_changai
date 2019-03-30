/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.bo;

/**
 * Title: Cell.java<br>
 * Description:单元对象<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-9-26 1.0
 */
public interface Cell {

	/**
	 * 返回单元格样式名称
	 * 
	 * @return the styleName
	 */
	public String getStyleName();

	/**
	 * 返回单元格行合并数
	 * 
	 * @return the rowSpan
	 */
	public int getRowSpan();

	/**
	 * 返回单元格列合并数
	 * 
	 * @return the colSpan
	 */
	public int getColSpan();

	/**
	 * 返回单元格内容
	 * 
	 * @return the Text
	 */
	public Object getText();

}
