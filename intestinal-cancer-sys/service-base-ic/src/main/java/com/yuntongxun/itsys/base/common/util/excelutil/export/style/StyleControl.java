/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.style;

import com.yuntongxun.itsys.base.common.util.excelutil.export.style.config.StyleConfig;
import com.yuntongxun.itsys.base.common.util.excelutil.export.style.config.StyleConfigFactory;
import com.yuntongxun.itsys.base.common.util.excelutil.export.style.config.bo.BorderConfig;
import com.yuntongxun.itsys.base.common.util.excelutil.export.style.config.bo.CellConfig;
import com.yuntongxun.itsys.base.common.util.excelutil.export.style.config.bo.FontConfig;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.HashMap;
import java.util.Iterator;


/**
 * Title: StyleControl.java<br>
 * Description:样式管理器<br>
 * Company: EOrchis<br>
 * Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-9-28 1.0
 */
public final class StyleControl {

	private StyleConfig config;

	private HashMap<String,CellStyle> cellStyleMap = new HashMap<String,CellStyle>();

	private StyleControl() {
	}

	/**
	 * 获取样式管理器
	 * 
	 * @param stylename
	 *            样式配置文件名
	 * @return 样式管理器
	 * @throws Exception
	 */
	public static StyleControl newInstance(String stylename) throws Exception {
		StyleControl control = new StyleControl();
		control.config = StyleConfigFactory.getStyleConfig(stylename);
		return control;
	}

	/**
	 * 初始化Excel样式库
	 * 
	 * @param workbook
	 *            POI类库中ExcelWorkBook对象
	 */
	public void buildWorkbookStyle(XSSFWorkbook workbook) {
		Iterator cellConfigIterator = config.getCellStyleIterator();
		while (cellConfigIterator.hasNext()) {
			CellConfig cc = (CellConfig) cellConfigIterator.next();
			CellStyle cs = new InnerCellStyle(cc);
			registerPOICellStyle(workbook, cs);
		}
	}

	private XSSFFont registerPOIFontStyle(XSSFWorkbook workbook,
										  FontStyle argFontStyle) {
		XSSFFont fontStyle = workbook.createFont();
		fontStyle.setBoldweight(argFontStyle.getFontBold());
		fontStyle.setColor(argFontStyle.getFontColor());
		fontStyle.setFontHeightInPoints(argFontStyle.getFontSize());
		fontStyle.setFontName(argFontStyle.getFace());
		fontStyle.setUnderline(argFontStyle.getUnderLine());
		fontStyle.setItalic(argFontStyle.isItalic());
		return fontStyle;
	}

	private void registerPOICellStyle(XSSFWorkbook workbook, CellStyle cs) {
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(cs.getAlign());
		cellStyle.setFillForegroundColor(cs.getBackgroundColor());
		cellStyle.setFillPattern(cs.getFillPattern());
		cellStyle.setFont(registerPOIFontStyle(workbook, cs.getFontStyle()));
		cellStyle.setWrapText(cs.getWrapped());
		buildBorder(cellStyle, cs);
		((InnerCellStyle)cs).setHssfCellStyle(cellStyle);
		cellStyleMap.put(cs.getName(), cs);
	}

	private void buildBorder(XSSFCellStyle cellStyle, CellStyle cs) {
		BorderStyle bottom = cs.getBorderBottomStyle();
		cellStyle.setBorderBottom(bottom.getLineType());
		cellStyle.setBottomBorderColor(bottom.getBorderColor());

		BorderStyle top = cs.getBorderTopStyle();
		cellStyle.setBorderTop(top.getLineType());
		cellStyle.setTopBorderColor(top.getBorderColor());

		BorderStyle left = cs.getBorderLeftStyle();
		cellStyle.setBorderLeft(left.getLineType());
		cellStyle.setLeftBorderColor(left.getBorderColor());

		BorderStyle right = cs.getBorderRightStyle();
		cellStyle.setBorderRight(right.getLineType());
		cellStyle.setRightBorderColor(right.getBorderColor());
	}

	public CellStyle getCellStyleByName(String name) {
		return (CellStyle) this.cellStyleMap.get(name);
	}

	private class InnerCellStyle implements CellStyle {

		private CellConfig cellConfig;
		private org.apache.poi.ss.usermodel.CellStyle hssfCellStyle;
		public InnerCellStyle(CellConfig cellConfig) {
			this.cellConfig = cellConfig;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.CellStyle#getFillPattern()
		 */
		public short getFillPattern() {
			String pattern = cellConfig.getBackgroundFillStyle();
			if (pattern == null || "".equals(pattern))
				return XSSFCellStyle.NO_FILL;
			pattern = pattern.toUpperCase();
			if ("NO_FILL".equals(pattern))
				return XSSFCellStyle.NO_FILL;
			if ("SOLID_FOREGROUND".equals(pattern))
				return XSSFCellStyle.SOLID_FOREGROUND;
			if ("FINE_DOTS".equals(pattern))
				return XSSFCellStyle.FINE_DOTS;
			if ("ALT_BARS".equals(pattern))
				return XSSFCellStyle.ALT_BARS;
			if ("SPARSE_DOTS".equals(pattern))
				return XSSFCellStyle.ALT_BARS;
			if ("THICK_HORZ_BANDS".equals(pattern))
				return XSSFCellStyle.THICK_HORZ_BANDS;
			if ("THICK_VERT_BANDS".equals(pattern))
				return XSSFCellStyle.THICK_VERT_BANDS;
			if ("THICK_BACKWARD_DIAG".equals(pattern))
				return XSSFCellStyle.THICK_BACKWARD_DIAG;
			if ("THICK_FORWARD_DIAG".equals(pattern))
				return XSSFCellStyle.THICK_FORWARD_DIAG;
			if ("BIG_SPOTS".equals(pattern))
				return XSSFCellStyle.BIG_SPOTS;
			if ("BRICKS".equals(pattern))
				return XSSFCellStyle.BRICKS;
			if ("THIN_HORZ_BANDS".equals(pattern))
				return XSSFCellStyle.THIN_HORZ_BANDS;
			if ("THIN_VERT_BANDS".equals(pattern))
				return XSSFCellStyle.THIN_VERT_BANDS;
			if ("THIN_BACKWARD_DIAG".equals(pattern))
				return XSSFCellStyle.THIN_BACKWARD_DIAG;
			if ("THIN_FORWARD_DIAG".equals(pattern))
				return XSSFCellStyle.THIN_FORWARD_DIAG;
			if ("SQUARES".equals(pattern))
				return XSSFCellStyle.SQUARES;
			if ("DIAMONDS".equals(pattern))
				return XSSFCellStyle.DIAMONDS;
			throw new IllegalArgumentException("未找到背景填充方式" + pattern
					+ "请检查配置文件是否正确!");
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.CellStyle#getAlign()
		 */
		public short getAlign() {
			String align = cellConfig.getAlign();
			if (align == null || "".equals(align))
				return XSSFCellStyle.ALIGN_GENERAL;
			align = align.toUpperCase();
			if ("CENTER".equals(align))
				return XSSFCellStyle.ALIGN_CENTER_SELECTION;
			if ("LEFT".equals(align))
				return XSSFCellStyle.ALIGN_LEFT;
			if ("RIGHT".equals(align))
				return XSSFCellStyle.ALIGN_RIGHT;
			if ("CENTER_SELECTION".equals(align))
				return XSSFCellStyle.ALIGN_CENTER_SELECTION;
			if ("FILL".equals(align))
				return XSSFCellStyle.ALIGN_FILL;
			if ("GENERAL".equals(align))
				return XSSFCellStyle.ALIGN_GENERAL;
			if ("JUSTIFY".equals(align))
				return XSSFCellStyle.ALIGN_JUSTIFY;
			throw new IllegalArgumentException("未找到对齐方式" + align
					+ "请检查配置文件是否正确!");
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.CellStyle#getBackgroundColor()
		 */
		public short getBackgroundColor() {
			String color = cellConfig.getBackgroundColor();
			return ColorManager.getColorIndex(color);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.CellStyle#getFontStyle()
		 */
		public FontStyle getFontStyle() {
			return new InnerFontStyle(cellConfig.getFontConfig());
		}

		public int getWidth() {
			return cellConfig.getWidth();
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.CellStyle#getBorderBottomStyle()
		 */
		public BorderStyle getBorderBottomStyle() {
			return new InnerBorderStyle(cellConfig
					.getBorderConfig(BorderConfig.BOTTOM));
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.CellStyle#getBorderLeftStyle()
		 */
		public BorderStyle getBorderLeftStyle() {
			return new InnerBorderStyle(cellConfig
					.getBorderConfig(BorderConfig.LEFT));
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.CellStyle#getBorderRightStyle()
		 */
		public BorderStyle getBorderRightStyle() {
			return new InnerBorderStyle(cellConfig
					.getBorderConfig(BorderConfig.RIGHT));
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.CellStyle#getBorderTopStyle()
		 */
		public BorderStyle getBorderTopStyle() {
			return new InnerBorderStyle(cellConfig
					.getBorderConfig(BorderConfig.TOP));
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.CellStyle#getName()
		 */
		public String getName() {
			return cellConfig.getName();
		}

		public org.apache.poi.ss.usermodel.CellStyle getHSSfCellStyle() {
			return hssfCellStyle;
		}

		public void setHssfCellStyle(org.apache.poi.ss.usermodel.CellStyle hssfCellStyle) {
			this.hssfCellStyle = hssfCellStyle;
		}

		public boolean getWrapped() {
			return cellConfig.getWrapped();
		}
		
	}

	private class InnerBorderStyle implements BorderStyle {

		private BorderConfig borderConfig;

		public InnerBorderStyle(BorderConfig borderConfig) {
			this.borderConfig = borderConfig;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.BorderStyle#getBorderColor()
		 */
		public short getBorderColor() {
			return ColorManager
					.getColorIndex(borderConfig.getBorderLineColor());
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.BorderStyle#getLineType()
		 */
		public short getLineType() {
			String lineType = borderConfig.getLineType();
			if (lineType == null || "".equals(lineType))
				return XSSFCellStyle.BORDER_NONE;
			lineType = lineType.toUpperCase();
			if ("BORDER_DASH_DOT".equals(lineType))
				return XSSFCellStyle.BORDER_DASH_DOT;
			if ("BORDER_DASH_DOT_DOT".equals(lineType))
				return XSSFCellStyle.BORDER_DASH_DOT_DOT;
			if ("BORDER_DASHED".equals(lineType))
				return XSSFCellStyle.BORDER_DASHED;
			if ("BORDER_DOTTED".equals(lineType))
				return XSSFCellStyle.BORDER_DOTTED;
			if ("BORDER_DOUBLE".equals(lineType))
				return XSSFCellStyle.BORDER_DOUBLE;
			if ("BORDER_HAIR".equals(lineType))
				return XSSFCellStyle.BORDER_HAIR;
			if ("BORDER_MEDIUM".equals(lineType))
				return XSSFCellStyle.BORDER_MEDIUM;
			if ("BORDER_MEDIUM_DASH_DOT".equals(lineType))
				return XSSFCellStyle.BORDER_MEDIUM_DASH_DOT;
			if ("BORDER_MEDIUM_DASH_DOT_DOT".equals(lineType))
				return XSSFCellStyle.BORDER_MEDIUM_DASH_DOT_DOT;
			if ("BORDER_MEDIUM_DASHED".equals(lineType))
				return XSSFCellStyle.BORDER_MEDIUM_DASHED;
			if ("BORDER_NONE".equals(lineType))
				return XSSFCellStyle.BORDER_NONE;
			if ("BORDER_SLANTED_DASH_DOT".equals(lineType))
				return XSSFCellStyle.BORDER_SLANTED_DASH_DOT;
			if ("BORDER_THICK".equals(lineType))
				return XSSFCellStyle.BORDER_THICK;
			if ("BORDER_THIN".equals(lineType))
				return XSSFCellStyle.BORDER_THIN;
			throw new IllegalArgumentException("未找到边框类型" + lineType
					+ "请检查配置文件是否正确!");
		}
	}

	private class InnerFontStyle implements FontStyle {

		private FontConfig fontConfig;

		public InnerFontStyle(FontConfig fontConfig) {
			this.fontConfig = fontConfig;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.FontStyle#getFace()
		 */
		public String getFace() {
			return fontConfig.getFace();
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.FontStyle#getFontBold()
		 */
		public short getFontBold() {
			if (fontConfig.isBold())
				return XSSFFont.BOLDWEIGHT_BOLD;
			return XSSFFont.BOLDWEIGHT_NORMAL;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.FontStyle#getFontColor()
		 */
		public short getFontColor() {
			return ColorManager.getColorIndex(fontConfig.getColor());
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.FontStyle#getFontSize()
		 */
		public short getFontSize() {
			return Short.parseShort(fontConfig.getSize());
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.FontStyle#getUnderLine()
		 */
		public byte getUnderLine() {
			String underLine = fontConfig.getUnderLine();
			if (underLine == null || "".equals(underLine))
				return XSSFFont.U_NONE;
			underLine = underLine.toUpperCase();
			if ("NO_UNDERLINE".equals(underLine))
				return XSSFFont.U_NONE;
			if ("SINGLE_UNDERLINE".equals(underLine))
				return XSSFFont.U_SINGLE;
			if ("DOUBLE_UNDERLINE".equals(underLine))
				return XSSFFont.U_DOUBLE;
			if ("U_SINGLE_UNDERLINE".equals(underLine))
				return XSSFFont.U_SINGLE_ACCOUNTING;
			if ("U_DOUBLE_UNDERLINE".equals(underLine))
				return XSSFFont.U_DOUBLE_ACCOUNTING;
			throw new IllegalArgumentException("未找到下划线类型" + underLine
					+ "请检查配置文件是否正确!");
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.style.FontStyle#isItalic()
		 */
		public boolean isItalic() {
			return fontConfig.isItalic();
		}
	}
}
