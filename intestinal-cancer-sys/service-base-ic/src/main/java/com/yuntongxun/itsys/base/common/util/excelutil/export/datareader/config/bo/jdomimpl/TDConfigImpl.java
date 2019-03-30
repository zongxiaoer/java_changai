/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.bo.jdomimpl;

import com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.bo.TDConfig;
import com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.element.ElementBuilder;
import com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.utils.expression.Expression;
import com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.utils.expression.ExpressionFactory;
import org.jdom.Element;

import java.util.List;


/**
 * Title: TDConfigImpl.java<br>
 * Description:<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-10-8 1.0
 */
public class TDConfigImpl implements TDConfig, ElementBuilder {

	private int rowspan = 1;
	private int colspan = 1;
	private Object value;
	private String style;

	/**
	 * 构造TDConfig对象
	 * 
	 * @param tdE
	 *            JDOM中TD节点对象
	 * @param obj
	 *            包含导出数据的对象
	 */
	public TDConfigImpl(Element tdE, Object obj) {
		String rowspan = tdE.getAttributeValue("rowspan");
		String colspan = tdE.getAttributeValue("colspan");
		String style = tdE.getAttributeValue("style");
		String value = tdE.getText().replaceAll("\\s", "");
		if (rowspan != null && !"".equals(rowspan))
			this.rowspan = Integer
					.parseInt(buildValue(rowspan, obj).toString());
		if (colspan != null && !"".equals(colspan))
			this.colspan = Integer
					.parseInt(buildValue(colspan, obj).toString());
		if (style != null && !"".equals(style))
			this.style = style;
		this.value = value == null ? "" : buildValue(value, obj);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see //com.eorchis.module.excelutils.export.datareader.config.bo.TDConfig#getColspan()
	 */
	public int getColspan() {
		return colspan;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see //com.eorchis.module.excelutils.export.datareader.config.bo.TDConfig#getRowspan()
	 */
	public int getRowspan() {
		return rowspan;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eorchis.module.excelutils.export.datareader.config.bo.TDConfig#getValue()
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see //com.eorchis.module.excelutils.export.datareader.config.bo.TDConfig#getStyle()
	 */
	public String getStyle() {
		return style;
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
