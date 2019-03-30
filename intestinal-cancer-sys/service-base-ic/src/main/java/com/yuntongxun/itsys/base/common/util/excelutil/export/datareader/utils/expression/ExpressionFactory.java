/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.utils.expression;


import com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.utils.expression.impl.ExpressionImpl;

/**
 * Title: ExpressionFactory.java<br>
 * Description:表达式工厂类<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2009-2-10 1.0
 */
public class ExpressionFactory {

	private ExpressionFactory() {
	};

	/**
	 * 根据参数对象构造基于该参数的表达式
	 * 
	 * @param obj
	 * @return
	 */
	public static Expression getExpression(Object obj) {
		return new ExpressionImpl(obj);
	}
}
