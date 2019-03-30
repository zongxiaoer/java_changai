/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.bo.jdomimpl;

import com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.bo.RootConfig;
import com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.element.ElementBuilder;
import com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.element.ElementBuilderFactory;
import org.jdom.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



/**
 * Title: RootConfigImpl.java<br>
 * Description:<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-10-8 1.0
 */
public class RootConfigImpl implements RootConfig, ElementBuilder {

	private ArrayList tableList = new ArrayList();

	/**
	 * 构造RootConfig对象
	 * 
	 * @param body
	 *            模板配置文件中的body标签对象
	 * @param exportObject
	 *            包含导出数据的对象
	 */
	public RootConfigImpl(Element body, Object exportObject) {
		List subList = body.getChildren();
		Iterator subIterator = subList.iterator();
		while (subIterator.hasNext()) {
			Element e = (Element) subIterator.next();
			ElementBuilderFactory.getInstance(e, exportObject).fill(tableList);

		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see //com.eorchis.module.excelutils.export.datareader.config.bo.RootConfig#getTableList()
	 */
	public List getTableList() {
		return this.tableList;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see //com.eorchis.module.excelutils.export.datareader.config.element.ElementBuilder#fill(List)
	 */
	public void fill(List elementList) {
		throw new UnsupportedOperationException();
	}

}
