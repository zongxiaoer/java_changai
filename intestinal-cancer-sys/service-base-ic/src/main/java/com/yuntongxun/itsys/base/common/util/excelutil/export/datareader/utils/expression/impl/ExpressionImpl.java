/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.utils.expression.impl;

import com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.utils.expression.Expression;
import com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.utils.expression.ExpressionException;
import com.yuntongxun.itsys.base.common.util.excelutil.export.supply.reflect.ExportObjectUtils;
import com.yuntongxun.itsys.base.common.util.excelutil.export.supply.reflect.exception.ExportObjectException;

import java.util.regex.Pattern;



/**
 * Title: ExpressionImpl.java<br>
 * Description:表达式默认实现<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2009-2-10 1.0
 */
public class ExpressionImpl implements Expression {

	private Object exportObject;

	public ExpressionImpl(Object exportObject) {
		this.exportObject = exportObject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eorchis.module.excelutils.export.datareader.utils.Expression#buildText(java.lang.String)
	 */
	public Object buildText(String t) {
		/**
		 * 当以$开头,且第一个}的下标+1==v的长度.说明只有一个表达式 返回此表达式属性的类型
		 * 否则,返回该表达式属性的toString()形式.
		 */
		if (t.startsWith("${") && (t.indexOf("}") + 1) == t.length()) {
			return getContent(t);
		}
		while (t.indexOf("${") != -1) {
			int expressionStartIndex = t.indexOf("${");
			int expressionEndIndex = getEndRegexIndex(t);
			String expression = t.substring(expressionStartIndex,
					expressionEndIndex + 1);
			Object field = getContent(expression);
			t = t
					.replaceFirst(
							"[\\$][\\{][[\\w]*[.]?[\\w]*]*[\\(]?[[\\w]*[,]?[\\w]*]*[\\)]?\\}",
							field == null ? "" : field.toString());
		}
		return t;
	}

	private int getEndRegexIndex(String str) {
		int startIndex = 0;
		while (startIndex != -1) {
			int endEIndex = str.indexOf("}", startIndex);
			if (str.charAt(endEIndex - 1) != '\\')
				return endEIndex;
			startIndex = endEIndex;
		}
		throw new NullPointerException(
				"in "
						+ str
						+ " can't found the end of Expression sign '}',check your config file.");
	}

	/**
	 * 根据表达式获取其内容
	 * 
	 * @param expression
	 * @return
	 */
	private Object getContent(String expression) {
		String v = expression.substring(expression.indexOf("{") + 1, expression
				.length() - 1);
		if (Pattern.matches(
				"[[\\w]+[.]?[\\w]]+[\\(]{1}[[\\w]+[,]?[\\w]+]*[\\)]{1}", v))
			return invokeMethod(this.exportObject, v);
		else
			return getFieldValue(this.exportObject, v);
	}

	/**
	 * 根据属性名获取其值
	 * 
	 * @param target
	 *            包含属性的对象
	 * @param field
	 *            待获取的属性,如果获取成员变量中的属性则为"成员变量.属性名",否则为"属性名"
	 * @return 属性对应的值 <strong>如果调用成员变量中的属性,则对象中必须声明该成员变量的getter</strong>
	 *         <h4>Example</h4>
	 * 
	 * <blockquote>
	 * 
	 * <pre>
	 * 	Class Base{
	 * 		private String baseValue;
	 * 		private Bean bean;
	 * 
	 * 		public bean getBean(){
	 * 			return this.bean;
	 * 		}
	 * 	}
	 * 	Class Bean{
	 * 		private String beanValue;
	 * 	}
	 * </pre>
	 * 
	 * </blockquote> 调用本对象中的属性 baseValue<br>
	 * 调用成员变量中的属性 bean.beanValue
	 */
	private Object getFieldValue(Object target, String field) {
		int pointIndex = field.indexOf(".");
		if (pointIndex != -1) {
			try {
				target = ExportObjectUtils.getFieldValue4Obj(target, field
						.substring(0, pointIndex));
			} catch (ExportObjectException e) {
				throw new ExpressionException(e);
			}
			return getFieldValue(target, field.substring(pointIndex + 1, field
					.length()));
		}
		try {
			return ExportObjectUtils.getFieldValue4Obj(target, field);
		} catch (ExportObjectException e) {
			throw new ExpressionException(e);
		}
	}

	/**
	 * 根据目标对象及方法名,执行该方法
	 * 
	 * @param target
	 *            执行方法所在的根对象
	 * @param method
	 *            执行方法名
	 * @return 该方法的返回值 <strong>如果调用成员变量中的方法,则对象中必须声明该成员变量的getter</strong>
	 *         <h4>Example</h4>
	 * 
	 * <blockquote>
	 * 
	 * <pre>
	 * 	Class Base{
	 * 		private Bean bean;
	 * 		public String baseM(){}
	 * 
	 * 		public bean getBean(){
	 * 			return this.bean;
	 * 		}
	 * 	}
	 * 	Class Bean{
	 * 		public void beanM(){}
	 * 	}
	 * </pre>
	 * 
	 * </blockquote> 调用本对象的方法 baseM()<br>
	 * 调用成员变量中的方法 bean.beanM()
	 */
	private Object invokeMethod(Object target, String method) {
		int pointIndex = method.indexOf(".");
		if (pointIndex != -1) {
			try {
				target = ExportObjectUtils.getFieldValue4Obj(target, method
						.substring(0, pointIndex));
			} catch (ExportObjectException e) {
				throw new ExpressionException(e);
			}
			return invokeMethod(target, method.substring(pointIndex + 1, method
					.length()));
		}
		try {
			return ExportObjectUtils.invoke(target, cacheMethodName(method),
					cacheMethodArgs(method));
		} catch (ExportObjectException e) {
			throw new ExpressionException(e);
		}
	}

	/**
	 * 根据方法全名获取方法名 如果方法包含参数则只参数类型必须为String
	 * 
	 * @param methodFN
	 *            方法全名
	 * @return 参数中的方法名 String methodN = cacheMethodName("method(arg)"); methodN =
	 *         "method";
	 */
	private String cacheMethodName(String methodFN) {
		return methodFN.substring(0, methodFN.indexOf("("));
	}

	/**
	 * 根据方法全名获取方法参数集
	 * 
	 * @param methodFN
	 *            方法全名
	 * @return 方法的参数集,如果没有参数则返回null
	 * 
	 * String[] args = cacheMethodArgs("method(a,b,c,d)"); args =
	 * ["a","b","c","d"];
	 */
	private String[] cacheMethodArgs(String methodFN) {
		int leftParenthesis = methodFN.indexOf("(");
		int rightParenthesis = methodFN.indexOf(")", leftParenthesis);
		if (rightParenthesis - leftParenthesis == 1)
			return null;
		String _arg = methodFN.substring(leftParenthesis + 1, rightParenthesis);
		return _arg.split(",");
	}
}
