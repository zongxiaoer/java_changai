/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.style.config;

/**
 * Title: StyleConfigFactory.java<br>
 * Description:样式配置工厂类<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-9-28 1.0
 */
public final class StyleConfigFactory {

	/**
	 * 初始化样式配置
	 * 
	 * @param configName
	 *            样式配置名称
	 * @return 该名称对应的样式配置
	 * @throws Exception
	 */
	public static StyleConfig getStyleConfig(String configName)
			throws Exception {
		return new JdomStyleConfigImpl(configName);
	}
}
