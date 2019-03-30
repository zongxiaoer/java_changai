package com.yuntongxun.itsys.base.common.util.excelutil.exception;

/**
 * Title: ExcelIOException.java<br>
 * Description: Excel导出异常基类<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-9-26 1.0
 */
public class ExcelIOException extends Exception {

	/**
	 * 
	 * @param message
	 */
	public ExcelIOException(String message) {
		super(message);
	}

	public ExcelIOException(String message, Exception e) {
		super(message, e);
	}

	public ExcelIOException(Exception e) {
		super(e);
	}
}
