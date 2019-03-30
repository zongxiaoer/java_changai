/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.supply.reflect;

import com.yuntongxun.itsys.base.common.util.excelutil.export.supply.reflect.exception.ExportObjectException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;


/**
 * Title: ExportObjectUtils.java<br>
 * Description: 反射导出对象<br>
 * Company: Orchis Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-9-29 1.0
 */
public class ExportObjectUtils {

	/**
	 * 获取反射对象中的集合迭代
	 * 
	 * @param fieldName
	 *            迭代属性名
	 * @return
	 * @throws ExportObjectException
	 */
	public static Iterator getFieldValue4Iterator(Object o, String fieldName)
			throws ExportObjectException {
		Object returnObj = getFieldValue4Obj(o, fieldName);
		if (returnObj instanceof Collection)
			return ((Collection) returnObj).iterator();
		else if (returnObj instanceof Map)
			return ((Map) returnObj).values().iterator();
		throw new ClassCastException(fieldName + "不是集合类型.");
	}

	/**
	 * 获取参数对象的指定属性名的属性值
	 * 
	 * @param obj
	 *            待反射的对象
	 * @param fieldName
	 *            待反射的属性名
	 * @return
	 * @throws ExportObjectException
	 */
	public static Object getFieldValue4Obj(Object o, String field)
			throws ExportObjectException {
		Method getMethod;
		Object returnObj;
		Class clazz = o.getClass();
		try {
			getMethod = clazz.getMethod(catchMethodName(field), null);
			returnObj = getMethod.invoke(o, null);
		} catch (SecurityException e) {
			throw new ExportObjectException(e);
		} catch (NoSuchMethodException e) {
			throw new ExportObjectException("don't found the " + field
					+ "'s getter.", e);
		} catch (IllegalArgumentException e) {
			throw new ExportObjectException("IllegalArgument by " + field
					+ " check your code.", e);
		} catch (IllegalAccessException e) {
			throw new ExportObjectException("the " + field
					+ "'s getter is can't access.", e);
		} catch (InvocationTargetException e) {
			throw new ExportObjectException(e);
		}
		return returnObj;
	}

	/**
	 * 执行参数对象的指定方法
	 * 
	 * @param target
	 *            待执行方法的对象
	 * @param methodName
	 *            待执行的方法名
	 * @param parameters
	 *            待执行方法所必须的参数
	 * @return 待执行方法的返回值
	 * @throws ExportObjectException
	 *             执行时出现的错误
	 */
	public static Object invoke(Object target, String methodName,
			Object[] parameters) throws ExportObjectException {
		Method invoker = null;
		Object result = null;
		Class[] parameterTypes = null;
		if (parameters != null) {
			parameterTypes = new Class[parameters.length];
			for (int i = 0; i < parameters.length; i++) {
				parameterTypes[i] = parameters[i].getClass();
			}
		}
		try {
			invoker = target.getClass().getMethod(methodName, parameterTypes);
		} catch (SecurityException e) {
			throw new ExportObjectException(e);
		} catch (NoSuchMethodException e) {
			throw new ExportObjectException("don't found the method: "
					+ methodName + "'.", e);
		}
		try {
			result = invoker.invoke(target, parameters);
		} catch (IllegalArgumentException e) {
			throw new ExportObjectException("IllegalArgument by " + parameters
					+ " check your code.", e);
		} catch (IllegalAccessException e) {
			throw new ExportObjectException("the method " + methodName
					+ " is can't access.", e);
		} catch (InvocationTargetException e) {
			throw new ExportObjectException(e);
		}
		return result;
	}

	private static String catchMethodName(String fieldName) {
		char[] _charArray = fieldName.toCharArray();
		_charArray[0] = Character.toUpperCase(_charArray[0]);
		return "get" + new String(_charArray);
	}
}
