/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.style;

import org.apache.poi.hssf.util.HSSFColor;

/**
 * Title: ColorManager.java<br>
 * Description:颜色管理器<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-9-28 1.0
 */
public final class ColorManager {

	private ColorManager() {
	}

	/**
	 * 根据颜色名称获取颜色标识 目前支持到POI 3.1
	 * 
	 * @param colorName
	 *            颜色名称
	 * @return
	 */
	public static short getColorIndex(String colorName) {
		if (colorName == null || "".equals(colorName))
			return HSSFColor.BLACK.index;
		if ("BLACK".equalsIgnoreCase(colorName))
			return HSSFColor.BLACK.index;
		if ("SEA_GREEN".equalsIgnoreCase(colorName))
			return HSSFColor.SEA_GREEN.index;
		if ("AQUA".equalsIgnoreCase(colorName))
			return HSSFColor.AQUA.index;
		if ("LIGHT_BLUE".equalsIgnoreCase(colorName))
			return HSSFColor.LIGHT_BLUE.index;
		if ("VIOLET".equalsIgnoreCase(colorName))
			return HSSFColor.VIOLET.index;
		if ("GREY_40_PERCENT".equalsIgnoreCase(colorName))
			return HSSFColor.GREY_40_PERCENT.index;
		if ("PINK".equalsIgnoreCase(colorName))
			return HSSFColor.PINK.index;
		if ("GOLD".equalsIgnoreCase(colorName))
			return HSSFColor.GOLD.index;
		if ("YELLOW".equalsIgnoreCase(colorName))
			return HSSFColor.YELLOW.index;
		if ("BRIGHT_GREEN".equalsIgnoreCase(colorName))
			return HSSFColor.BRIGHT_GREEN.index;
		if ("TURQUOISE".equalsIgnoreCase(colorName))
			return HSSFColor.TURQUOISE.index;
		if ("DARK_RED".equalsIgnoreCase(colorName))
			return HSSFColor.DARK_RED.index;
		if ("SKY_BLUE".equalsIgnoreCase(colorName))
			return HSSFColor.SKY_BLUE.index;
		if ("PLUM".equalsIgnoreCase(colorName))
			return HSSFColor.PLUM.index;
		if ("GREY_25_PERCENT".equalsIgnoreCase(colorName))
			return HSSFColor.GREY_25_PERCENT.index;
		if ("ROSE".equalsIgnoreCase(colorName))
			return HSSFColor.ROSE.index;
		if ("LIGHT_YELLOW".equalsIgnoreCase(colorName))
			return HSSFColor.LIGHT_YELLOW.index;
		if ("LIGHT_GREEN".equalsIgnoreCase(colorName))
			return HSSFColor.LIGHT_GREEN.index;
		if ("BROWN".equalsIgnoreCase(colorName))
			return HSSFColor.BROWN.index;
		if ("OLIVE_GREEN".equalsIgnoreCase(colorName))
			return HSSFColor.OLIVE_GREEN.index;
		if ("DARK_GREEN".equalsIgnoreCase(colorName))
			return HSSFColor.DARK_GREEN.index;
		if ("DARK_TEAL".equalsIgnoreCase(colorName))
			return HSSFColor.DARK_TEAL.index;
		if ("DARK_BLUE".equalsIgnoreCase(colorName))
			return HSSFColor.DARK_BLUE.index;
		if ("INDIGO".equalsIgnoreCase(colorName))
			return HSSFColor.INDIGO.index;
		if ("GREY_80_PERCENT".equalsIgnoreCase(colorName))
			return HSSFColor.GREY_80_PERCENT.index;
		if ("ORANGE".equalsIgnoreCase(colorName))
			return HSSFColor.ORANGE.index;
		if ("DARK_YELLOW".equalsIgnoreCase(colorName))
			return HSSFColor.DARK_YELLOW.index;
		if ("GREEN".equalsIgnoreCase(colorName))
			return HSSFColor.GREEN.index;
		if ("TEAL".equalsIgnoreCase(colorName))
			return HSSFColor.TEAL.index;
		if ("BLUE".equalsIgnoreCase(colorName))
			return HSSFColor.BLUE.index;
		if ("BLUE_GREY".equalsIgnoreCase(colorName))
			return HSSFColor.BLUE_GREY.index;
		if ("GREY_50_PERCENT".equalsIgnoreCase(colorName))
			return HSSFColor.GREY_50_PERCENT.index;
		if ("RED".equalsIgnoreCase(colorName))
			return HSSFColor.RED.index;
		if ("LIGHT_ORANGE".equalsIgnoreCase(colorName))
			return HSSFColor.LIGHT_ORANGE.index;
		if ("LIME".equalsIgnoreCase(colorName))
			return HSSFColor.LIME.index;
		if ("LIGHT_TURQUOISE".equalsIgnoreCase(colorName))
			return HSSFColor.LIGHT_TURQUOISE.index;
		if ("PALE_BLUE".equalsIgnoreCase(colorName))
			return HSSFColor.PALE_BLUE.index;
		if ("LAVENDER".equalsIgnoreCase(colorName))
			return HSSFColor.LAVENDER.index;
		if ("WHITE".equalsIgnoreCase(colorName))
			return HSSFColor.WHITE.index;
		if ("CORNFLOWER_BLUE".equalsIgnoreCase(colorName))
			return HSSFColor.CORNFLOWER_BLUE.index;
		if ("LEMON_CHIFFON".equalsIgnoreCase(colorName))
			return HSSFColor.LEMON_CHIFFON.index;
		if ("MAROON".equalsIgnoreCase(colorName))
			return HSSFColor.MAROON.index;
		if ("ORCHID".equalsIgnoreCase(colorName))
			return HSSFColor.ORCHID.index;
		if ("CORAL".equalsIgnoreCase(colorName))
			return HSSFColor.CORAL.index;
		if ("ROYAL_BLUE".equalsIgnoreCase(colorName))
			return HSSFColor.ROYAL_BLUE.index;
		if ("LIGHT_CORNFLOWER_BLUE".equalsIgnoreCase(colorName))
			return HSSFColor.LIGHT_CORNFLOWER_BLUE.index;

		throw new IllegalArgumentException("未找到" + colorName
				+ "匹配的颜色,请检查配置文件是否正确!");
	}
}
