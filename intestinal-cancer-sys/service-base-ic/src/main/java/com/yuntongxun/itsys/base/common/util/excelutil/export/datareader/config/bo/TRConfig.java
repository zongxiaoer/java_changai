/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.bo;

import java.util.List;

/**
 * Title: TRConfig.java<br>
 * Description:TRConfig<br>
 * Company: Orchis Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-9-29 1.0
 */
public interface TRConfig {

	/**
	 * 获取该TR下的td集合
	 * 
	 * @return 集合中的元素类型为:TDConfig
	 */
	public List getTDList();
}
