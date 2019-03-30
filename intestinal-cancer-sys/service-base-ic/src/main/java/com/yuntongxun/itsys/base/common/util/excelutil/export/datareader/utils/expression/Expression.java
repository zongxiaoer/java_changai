package com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.utils.expression;

/**
 * Title: Expression.java<br>
 * Description: 表达式<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2009-2-10 1.0
 */
public interface Expression {

	/**
	 * 根据表达式获取内容
	 * 
	 * @param t
	 *            表达式
	 * @return 表达式中包含的内容
	 */
	public Object buildText(String t);
}
