/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.bo.jdomimpl;

import com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.element.ElementBuilder;
import com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.element.ElementBuilderFactory;
import com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.utils.expression.ExpressionFactory;
import com.yuntongxun.itsys.base.common.util.excelutil.export.supply.reflect.ExportObjectUtils;
import com.yuntongxun.itsys.base.common.util.excelutil.export.supply.reflect.exception.ExportObjectException;
import org.jdom.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



/**
 * Title: IteratorConfig.java<br>
 * Description:迭代节点<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-10-8 1.0
 */
public final class IteratorConfig implements ElementBuilder {

	private ArrayList subList = new ArrayList();

	/**
	 * 初始化迭代配置对象
	 * 
	 * @param iteratorE
	 *            JDom解析出的iterator节点对象
	 * @param obj
	 *            对应此节点的导出对象
	 * @return 初始化好的Iterator对象
	 */
	public static IteratorConfig newInstance(Element iteratorE, Object obj) {
		return new IteratorConfig(iteratorE, obj);
	}

	/**
	 * IteratorConfig构造方法
	 * 
	 * @param iteratorE
	 *            JDom解析出的iterator节点对象
	 * @param obj
	 *            对应此节点的导出对象
	 */
	public IteratorConfig(Element iteratorE, Object obj) {
		String property = iteratorE.getAttributeValue("property");
		if (property == null || "".equals(property))
			throw new NullPointerException("the iterator property \""
					+ property + "\" is not found.");
		Iterator iterator;
		property = ExpressionFactory.getExpression(obj).buildText(property)
				.toString();
		try {
			iterator = ExportObjectUtils.getFieldValue4Iterator(obj, property);
		} catch (ExportObjectException e) {
			throw new RuntimeException(e);
		}
		while (iterator.hasNext()) {
			Object subObj = iterator.next();
			Iterator subNodeIterator = iteratorE.getChildren().iterator();
			while (subNodeIterator.hasNext()) {
				Element subE = (Element) subNodeIterator.next();
				ElementBuilderFactory.getInstance(subE, subObj).fill(subList);

			}
		}
	}

	/**
	 * 获取迭代内容集合
	 * 
	 * @return 迭代器中包含的子集合
	 */

	public void fill(List elementList) {
		elementList.addAll(subList);
	}
}
