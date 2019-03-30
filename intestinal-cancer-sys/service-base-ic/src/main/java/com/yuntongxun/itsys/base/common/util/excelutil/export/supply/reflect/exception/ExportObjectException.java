/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.supply.reflect.exception;

/**
 * Title: ExportObjectException.java<br>
 * Description:<br>
 * Company: Orchis Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-9-29 1.0
 */
public class ExportObjectException extends Exception {

	public ExportObjectException(String message, Exception e) {
		super(message, e);
	}

	public ExportObjectException(Exception e) {
		super(e);
	}

	public ExportObjectException(String message) {
		super(message);
	}
}
