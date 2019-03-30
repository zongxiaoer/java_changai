/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.bo;

import java.util.Iterator;

/**
 * Title: Table.java<br>
 * Description:table操作对象<br>
 * Company: Orchis Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-9-29 1.0
 */
public interface Table {

	/**
	 * 获取table下的Row集合
	 * 
	 * @return 类型为Row的迭代器
	 */
	public Iterator getRowIterator();

	/** 获取table宽* */
	public int getWidth();
}
