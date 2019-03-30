/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.bo.jdomimpl;

import com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.bo.TableConfig;
import com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.element.ElementBuilder;
import com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.element.ElementBuilderFactory;
import com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.utils.expression.Expression;
import com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.utils.expression.ExpressionFactory;
import org.jdom.Element;

import java.util.ArrayList;
import java.util.List;



/**
 * Title: TableConfigImpl.java<br>
 * Description:Table配置实现类<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-10-8 1.0
 */
public class TableConfigImpl implements TableConfig, ElementBuilder {

	private int width = -1;
	private ArrayList trList = new ArrayList();

	/**
	 * TableConfigImpl构造方法
	 * 
	 * @param tableE
	 *            JDom解析的Table节点对象
	 * @param exprotObject
	 *            导出对象
	 */
	public TableConfigImpl(Element tableE, Object exprotObject) {
		String width = tableE.getAttributeValue("width");
		if (width != null && !"".equals(width))
			this.width = Integer.parseInt(buildValue(width, exprotObject)
					.toString());
		List subList = tableE.getChildren();
		for (int i = 0; i < subList.size(); i++) {
			Element e = (Element) subList.get(i);
			ElementBuilderFactory.getInstance(e, exprotObject).fill(trList);

		}

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see //com.eorchis.module.excelutils.export.datareader.config.bo.TableConfig#getTRList()
	 */
	public List getTRList() {
		return trList;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see //com.eorchis.module.excelutils.export.datareader.config.bo.TableConfig#getWidth()
	 */
	public int getWidth() {
		return width;
	}

	private Object buildValue(String v, Object obj) {
		Expression e = ExpressionFactory.getExpression(obj);
		return e.buildText(v);
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
