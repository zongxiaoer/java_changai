/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.bo;

import java.util.Iterator;

/**
 * Title: Row.java<br>
 * Description:<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-9-27 1.0
 */
public interface Row {

	/**
	 * 返回此行单元格迭代器
	 * 
	 * @return the cellList
	 */
	public Iterator getCellIterator();
}
