/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.style;


/**
 * Title: CellStyle.java<br>
 * Description: 单元样式<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-9-28 1.0
 */
public interface CellStyle {

	/** 获取该样式标识* */
	public String getName();

	/**
	 * 获取对齐方式
	 * 
	 * @return
	 */
	public short getAlign();

	/**
	 * 获取背景色
	 * 
	 * @return
	 */
	public short getBackgroundColor();
	/**
	 * 获取单元格宽度
	 * @return
	 */
	public int getWidth();
	/**
	 * 获取字体样式
	 * 
	 * @return
	 */
	public FontStyle getFontStyle();

	/**
	 * 获取顶边框样式
	 * 
	 * @return
	 */
	public BorderStyle getBorderTopStyle();

	/**
	 * 获取左边框样式
	 * 
	 * @return
	 */
	public BorderStyle getBorderLeftStyle();

	/**
	 * 获取右边框样式
	 * 
	 * @return
	 */
	public BorderStyle getBorderRightStyle();

	/**
	 * 获取下边框样式
	 * 
	 * @return
	 */
	public BorderStyle getBorderBottomStyle();

	/** 获取背景填充样式* */
	public short getFillPattern();
	/**
	 * 获取列样式
	 * @return 返回poi定义的列样式类
	 */
	public org.apache.poi.ss.usermodel.CellStyle getHSSfCellStyle();
	/**
	 * 获取单元格是否自动换行
	 * @return
	 */
	public boolean getWrapped();
}
