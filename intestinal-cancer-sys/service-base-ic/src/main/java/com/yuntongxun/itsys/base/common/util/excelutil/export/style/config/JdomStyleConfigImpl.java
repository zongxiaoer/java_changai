/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.style.config;

import com.yuntongxun.itsys.base.common.util.excelutil.export.style.config.bo.BorderConfig;
import com.yuntongxun.itsys.base.common.util.excelutil.export.style.config.bo.CellConfig;
import com.yuntongxun.itsys.base.common.util.excelutil.export.style.config.bo.FontConfig;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


/**
 * Title: JdomStyleConfigImpl.java<br>
 * Description:读取styleJDom实现<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-9-28 1.0
 */
public class JdomStyleConfigImpl implements StyleConfig {

	private HashMap<String,FontConfig> fontMap = new HashMap<String,FontConfig>();

	private HashMap<String,CellConfig> cellMap = new HashMap<String,CellConfig>();

	public JdomStyleConfigImpl(String configName) throws Exception {
		Document doc = loadConfig(configName);
		initConfig(doc);
	}

	private Document loadConfig(String configName) throws Exception {
//		String filePath = PathUtil.getClassPathPath() + CONFIGROOT + configName
//				+ EXTEND;
		String filePath = this.getClass().getClassLoader().getResource("")+CONFIGROOT + configName+ EXTEND ;
		return new SAXBuilder().build(filePath);
	}

	private void initConfig(Document doc) {
		Element root = doc.getRootElement();
		Element fontStyle = (Element) root.getChildren(FONT_STYLE).get(0);
		Element cellStyle = (Element) root.getChildren(CELL_STYLE).get(0);
		buildFontStyle(fontStyle.getChildren(FONT));
		buildCellStyle(cellStyle.getChildren(CELL));
	}

	private void buildFontStyle(List fontList) {
		Iterator fontIterator = fontList.iterator();
		while (fontIterator.hasNext()) {
			Element font = (Element) fontIterator.next();
			InnerFontConfig fontConfig = new InnerFontConfig(font
					.getAttributeValue("name"));
			String face = font.getAttributeValue("face");
			String size = font.getAttributeValue("size");
			String color = font.getAttributeValue("color");
			String bold = font.getAttributeValue("bold");
			String italic = font.getAttributeValue("italic");
			String underLine = font.getAttributeValue("underLine");
			if (face != null && !"".equals(face))
				fontConfig.face = face;
			if (size != null && !"".equals(size))
				fontConfig.size = size;
			if (color != null && !"".equals(color))
				fontConfig.color = color;
			if (bold != null && !"".equals(bold))
				fontConfig.isBold = Boolean.valueOf(bold).booleanValue();
			if (italic != null && !"".equals(italic))
				fontConfig.isItalic = Boolean.valueOf(italic).booleanValue();
			if (underLine != null && !"".equals(underLine))
				fontConfig.underLine = underLine;
			this.fontMap.put(fontConfig.name, fontConfig);
		}
	}

	private void buildCellStyle(List cellList) {
		Iterator cellIterator = cellList.iterator();
		while (cellIterator.hasNext()) {
			Element cell = (Element) cellIterator.next();
			InnerCellConfig cellConfig = new InnerCellConfig(cell
					.getAttributeValue("name"));
			String align = cell.getAttributeValue("align");
			String bgColor = cell.getAttributeValue("backgroundColor");
			String fillStyle = cell.getAttributeValue("fillStyle");
			String width = cell.getAttributeValue("width");
			String wrapped = cell.getAttributeValue("wrapped");
			if (align != null && !"".equals(align))
				cellConfig.align = align;
			if (bgColor != null && !"".equals(bgColor))
				cellConfig.bgColor = bgColor;
			if (fillStyle != null && !"".equals(fillStyle))
				cellConfig.bgStyle = fillStyle;
			if (width != null && !"".equals(width)) 
				cellConfig.width = Integer.parseInt(width);
			if (wrapped != null && !"".equals(wrapped)) 
				cellConfig.isWrapped = Boolean.parseBoolean(wrapped);
			buildCellFontStyle(cell, cellConfig);
			buildCellBordersStyle(cell, cellConfig);
			cellMap.put(cellConfig.name, cellConfig);
		}
	}

	private void buildCellFontStyle(Element e, InnerCellConfig c) {
		if (e.getAttributeValue("fontStyle") == null
				|| this.fontMap.get(e.getAttributeValue("fontStyle")) == null)
			throw new NullPointerException(c.name + "样式中的字体样式不存在,请检查配置文件!");
		else {
			c.fontStyle = (FontConfig) this.fontMap.get(e
					.getAttributeValue("fontStyle"));
		}
	}

	private void buildCellBordersStyle(Element e, InnerCellConfig c) {
		Iterator borderIterator = e.getChildren(BORDER).iterator();
		while (borderIterator.hasNext()) {
			Element border = (Element) borderIterator.next();
			InnerBorderConfig borderConfig = new InnerBorderConfig(border
					.getAttributeValue("type"));
			String lineType = border.getAttributeValue("lineType");
			String color = border.getAttributeValue("color");
			if (lineType != null && !"".equals(lineType))
				borderConfig.lineType = lineType;
			if (color != null && !"".equals(color))
				borderConfig.color = color;
			c.borderMap.put(borderConfig.type, borderConfig);
		}
		if (!c.borderMap.containsKey("top") || !c.borderMap.containsKey("left")
				|| !c.borderMap.containsKey("right")
				|| !c.borderMap.containsKey("bottom"))
			throw new IllegalArgumentException("边框不完整,请检查配置文件是否正确!");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see //com.eorchis.module.excelutils.export.style.config.StyleConfig#getCellStyleIterator()
	 */
	public Iterator getCellStyleIterator() {
		return cellMap.values().iterator();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see //com.eorchis.module.excelutils.export.style.config.StyleConfig#getCellStyleSingle(String)
	 */
	public CellConfig getCellStyleSingle(String cellStyleName) {
		return (CellConfig) cellMap.get(cellStyleName);
	}

	private class InnerFontConfig implements FontConfig {
		private String name;
		private String face = "宋体";
		private String size = "10";
		private String color;
		private boolean isBold = false;
		private boolean isItalic = false;
		private String underLine;

		public InnerFontConfig(String name) {
			if (name == null)
				throw new NullPointerException(
						"构造字体样式时,没有找到name属性.请检查配置文件是否正确!");
			this.name = name;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.config.bo.FontConfig#getColor()
		 */
		public String getColor() {
			return this.color;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.config.bo.FontConfig#getFace()
		 */
		public String getFace() {
			return this.face;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.config.bo.FontConfig#getName()
		 */
		public String getName() {
			return this.name;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.config.bo.FontConfig#getSize()
		 */
		public String getSize() {
			return this.size;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.config.bo.FontConfig#getUnderLine()
		 */
		public String getUnderLine() {
			return this.underLine;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.config.bo.FontConfig#isBold()
		 */
		public boolean isBold() {
			return this.isBold;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.config.bo.FontConfig#isItalic()
		 */
		public boolean isItalic() {
			return isItalic;
		}
	}

	private class InnerCellConfig implements CellConfig {
		private HashMap<String,BorderConfig> borderMap = new HashMap<String,BorderConfig>();
		private String name;
		private String align;
		private String bgColor;
		private String bgStyle;
		private FontConfig fontStyle;
		private int width = 3000;
		private boolean isWrapped=false;
		public InnerCellConfig(String name) {
			if (name == null)
				throw new NullPointerException(
						"构造单元样式时,没有找到name属性.请检查配置文件是否正确!");
			this.name = name;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.config.bo.CellConfig#getAlign()
		 */
		public String getAlign() {
			return this.align;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.config.bo.CellConfig#getBackgroundColor()
		 */
		public String getBackgroundColor() {
			return this.bgColor;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.config.bo.CellConfig#getBorderConfig(String)
		 */
		public BorderConfig getBorderConfig(String flag) {
			return (BorderConfig) this.borderMap.get(flag);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.config.bo.CellConfig#getFontConfig()
		 */
		public FontConfig getFontConfig() {
			return this.fontStyle;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.config.bo.CellConfig#getName()
		 */
		public String getName() {
			return this.name;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.config.bo.CellConfig#getBackgroundFillStyle()
		 */
		public String getBackgroundFillStyle() {
			return this.bgStyle;
		}
		/**#
		 * @see //com.eorchis.module.excelutils.export.style.config.bo.CellConfig#getWidth()
		 */
		public int getWidth() {
			return this.width;
		}

		public boolean getWrapped() {
			return this.isWrapped;
		}
		
	}

	private class InnerBorderConfig implements BorderConfig {

		private String type;
		private String lineType;
		private String color;

		public InnerBorderConfig(String type) {
			if (type == null)
				throw new NullPointerException(
						"构造边框样式时,没有找到type属性.请检查配置文件是否正确!");
			this.type = type;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.config.bo.BorderConfig#getBorderLineColor()
		 */
		public String getBorderLineColor() {
			return this.color;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.config.bo.BorderConfig#getBorderType()
		 */
		public String getBorderType() {
			return this.type;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.config.bo.BorderConfig#getLineType()
		 */
		public String getLineType() {
			return this.lineType;
		}

	}
}
