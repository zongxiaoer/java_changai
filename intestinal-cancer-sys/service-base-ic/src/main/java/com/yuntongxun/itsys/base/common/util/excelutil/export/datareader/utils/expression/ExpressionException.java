/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.utils.expression;

/**
 * Title: ExpressionException.java<br>
 * Description: 解析表达式异常类<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2009-2-10 1.0
 */
public class ExpressionException extends RuntimeException {

	public ExpressionException(String message) {
		super(message);
	}

	public ExpressionException(Throwable t) {
		super(t);
	}
}
