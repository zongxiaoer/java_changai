/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.element;

import java.util.List;

/**
 * Title: ElementBuilder.java<br>
 * Description: 布局配置节点处理接口<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2009-2-11 1.0
 */
public interface ElementBuilder {

	/**
	 * 根据节点类型填充参数集合
	 * 
	 * @param elementList
	 *            待填充本节点信息的集合对象
	 */
	public void fill(List elementList);
}
