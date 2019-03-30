/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.element;


import com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.bo.jdomimpl.*;
import org.jdom.Element;

/**
 * Title: ElementBuilderFactory.java<br>
 * Description: 节点配置创建工厂<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2009-2-11 1.0
 */
public class ElementBuilderFactory {

	private static final int BODY_HASHCODE = 0x2e39a2;
	private static final int ITERATOR_HASHCODE = 0x467c086e;
	private static final int TABLE_HASHCODE = 0x6903bce;
	private static final int TR_HASHCODE = 0xe7e;
	private static final int TD_HASHCODE = 0xe70;

	private ElementBuilderFactory() {
	}

	/**
	 * 根据参数节点对象及导出对象获取布局配置节点处理对象
	 * 
	 * @param element
	 *            节点信息
	 * @param exprotObject
	 * @return
	 */
	public static ElementBuilder getInstance(Element element,
			Object exprotObject) {
		int elementHashCode = element.getName().toLowerCase().hashCode();
		switch (elementHashCode) {
		case BODY_HASHCODE:
			return new RootConfigImpl(element, exprotObject);
		case ITERATOR_HASHCODE:
			return new IteratorConfig(element, exprotObject);
		case TABLE_HASHCODE:
			return new TableConfigImpl(element, exprotObject);
		case TR_HASHCODE:
			return new TRConfigImpl(element, exprotObject);
		case TD_HASHCODE:
			return new TDConfigImpl(element, exprotObject);
		default:
			throw new UnsupportedElement("the Type " + element.getName()
					+ " is not supported.");
		}
	}
}
