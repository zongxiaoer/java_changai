/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.bo;

/**
 * Title: TDConfig.java<br>
 * Description:TDConfig<br>
 * Company: Orchis Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-9-29 1.0
 */
public interface TDConfig {

	/**
	 * 获取行合并数
	 * 
	 * @return
	 */
	public int getRowspan();

	/**
	 * 获取列合并数
	 * 
	 * @return
	 */
	public int getColspan();

	/**
	 * 获取节点内容
	 * 
	 * @return
	 */
	public Object getValue();

	/**
	 * 获取单元样式
	 * 
	 * @return
	 */
	public String getStyle();
}
