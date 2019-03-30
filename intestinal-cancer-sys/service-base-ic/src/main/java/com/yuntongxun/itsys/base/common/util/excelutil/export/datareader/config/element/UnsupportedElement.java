/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.element;

/**
 * Title: UnsupportedElement.java<br>
 * Description:节点类型不被支持异常<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2009-2-11 1.0
 */
public class UnsupportedElement extends RuntimeException {

	public UnsupportedElement(String message) {
		super(message);
	}
}
