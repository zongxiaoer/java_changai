/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.bo;

import java.util.List;

/**
 * Title: TableConfig.java<br>
 * Description:table节点<br>
 * Company: Orchis Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-9-29 1.0
 */
public interface TableConfig {

	/** 获取table宽度* */
	public int getWidth();

	/**
	 * 获取table下的所有tr元素
	 * 
	 * @return 集合中的元素类型为:TRConfig
	 */
	public List getTRList();
}
