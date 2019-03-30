/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config;

import java.util.List;

/**
 * Title: ExcelConfig.java<br>
 * Description:Excel配置接口<br>
 * Company: Orchis Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-9-29 1.0
 */
public interface ExcelConfig {

	/**
	 * 获取根据配置文件格式构造好的Table集合
	 * 
	 * @return
	 */
	public List getTables();
}
