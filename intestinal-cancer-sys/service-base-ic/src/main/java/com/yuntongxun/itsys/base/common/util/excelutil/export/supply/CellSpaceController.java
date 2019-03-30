/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.supply;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Title: CellSpaceController.java<br>
 * Description:单元空间控制器<br>
 * ┏━━━━ x轴 ┃ ┃ ┃ y轴 起始坐标均为'0' 以y轴为基准,y包含x集合 Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-9-26 1.0
 */
public final class CellSpaceController {

	private HashMap y = new HashMap();
	private int rowWidth = -1;

	private XController xController = new XController();

	private class XController {
		HashMap integerMap = new HashMap();

		Integer newInstance(String key) {
			if (!integerMap.containsKey(key)) {
				integerMap.put(key, Integer.valueOf(key));
			}
			return (Integer) integerMap.get(key);
		}
	}

	private CellSpaceController() {
	}

	private CellSpaceController(int width) {
		this.rowWidth = width;
	}

	public static CellSpaceController newInstance() {
		return new CellSpaceController();
	}

	public static CellSpaceController newInstance(int _width) {
		return new CellSpaceController(_width - 1);
	}

	/**
	 * 在矩阵中注册给定的两个坐标间的单元集合
	 * 
	 * @param startY
	 *            对应Excel中的起始行坐标
	 * @param startX
	 *            对应Excel中的起始列坐标
	 * @param endY
	 *            对应Excel中的结束行坐标
	 * @param endX
	 *            对应Excel中的结束列坐标
	 */
	public void registerSpace(int startY, int startX, int endY, int endX) {
		if (!checkPosition(startY, startX))
			throw new IllegalArgumentException("this Position " + startY + ","
					+ startX + "has used");
		if (!checkPosition(endY, endX))
			throw new IllegalArgumentException("this Position " + endY + ","
					+ endX + "has used");
		while (startY <= endY) {
			Integer integerY = new Integer(startY);
			HashSet x = (HashSet) y.get(integerY);
			if (x == null) {
				x = new HashSet();
				y.put(integerY, x);
			}

			for (int i = startX; i <= endX; i++) {
				x.add(xController.newInstance(String.valueOf(i)));
			}
			startY++;
		}
	}

	/**
	 * 判断给定坐标所对应的单元是否可用 如果该控制器定义了行宽且给定的坐标超出了行宽则抛出IndexOutOfBoundsException异常
	 * 
	 * @param y
	 *            对应Excel中的行坐标
	 * @param x
	 *            对应Excel中的列坐标
	 * @return true if it useable
	 */
	public boolean checkPosition(int y, int x) {
		if (rowWidth == -1 ? false : x > rowWidth)
			throw new IndexOutOfBoundsException(x + "坐标超出了行末坐标" + rowWidth);
		Integer integerY = new Integer(y);
		if (!this.y.containsKey(integerY))
			return true;
		HashSet xAxis = (HashSet) this.y.get(integerY);
		return xAxis.contains(new Integer(x)) ? false : true;
	}

	/**
	 * 根据参数坐标获取可用的x坐标
	 * 
	 * @param y
	 *            对应Excel中的行坐标
	 * @param x
	 *            对应Excel中的列坐标
	 * @return
	 */
	public int getUseablePositionX(int y, int x) {
		return checkPosition(y, x) ? x : getuseableX(y, x + 1);
	}

	/**
	 * 根据坐标获取可用的X坐标
	 * 
	 * @param y
	 * @param x
	 * @return 如果定义了行宽,且x已经达到最大值.则返回-1
	 */
	private int getuseableX(int y, int x) {
		HashSet xAxis = (HashSet) this.y.get(new Integer(y));
		while (true) {
			if (!xAxis.contains(new Integer(x))) {
				return rowWidth == -1 ? x : (x > rowWidth ? -1 : x);
			}
			x++;
		}
	}
}
