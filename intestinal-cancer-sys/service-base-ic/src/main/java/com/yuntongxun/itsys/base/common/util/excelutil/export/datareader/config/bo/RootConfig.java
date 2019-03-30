/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.bo;

import java.util.List;

/**
 * Title: RootConfig.java<br>
 * Description:根级Config<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-10-8 1.0
 */
public interface RootConfig {

	/**
	 * 获取table节点集合
	 * 
	 * @return 元素类型为TableConfig
	 */
	public List getTableList();
}
