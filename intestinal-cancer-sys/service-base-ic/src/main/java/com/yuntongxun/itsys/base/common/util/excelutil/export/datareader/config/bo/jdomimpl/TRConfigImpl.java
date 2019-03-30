/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.bo.jdomimpl;

import com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.bo.TRConfig;
import com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.element.ElementBuilder;
import com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.element.ElementBuilderFactory;
import org.jdom.Element;

import java.util.ArrayList;
import java.util.List;


/**
 * Title: TRConfigImpl.java<br>
 * Description:<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-10-8 1.0
 */
public class TRConfigImpl implements TRConfig, ElementBuilder {

	private List tdList = new ArrayList();

	/**
	 * 构造TRConfig对象
	 * 
	 * @param trE
	 *            JDOM中TR节点对象
	 * @param exportObj
	 *            包含导出数据的对象
	 */
	public TRConfigImpl(Element trE, Object exportObj) {
		List subList = trE.getChildren();
		for (int i = 0; i < subList.size(); i++) {
			Element e = (Element) subList.get(i);
			ElementBuilderFactory.getInstance(e, exportObj).fill(tdList);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see //com.eorchis.module.excelutils.export.datareader.config.bo.TRConfig#getTDList()
	 */
	public List getTDList() {
		return tdList;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see //com.eorchis.module.excelutils.export.datareader.config.element.ElementBuilder#fill(List)
	 */
	public void fill(List elementList) {
		elementList.add(this);
	}
}
