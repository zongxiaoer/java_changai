/**
 * 
 */
package com.yuntongxun.itsys.base.common.util.excelutil.export.datareader;


import com.yuntongxun.itsys.base.common.util.excelutil.export.bo.Cell;
import com.yuntongxun.itsys.base.common.util.excelutil.export.bo.ExportObject;
import com.yuntongxun.itsys.base.common.util.excelutil.export.bo.Row;
import com.yuntongxun.itsys.base.common.util.excelutil.export.bo.Table;
import com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.ExcelConfigFactory;
import com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.bo.TDConfig;
import com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.bo.TRConfig;
import com.yuntongxun.itsys.base.common.util.excelutil.export.datareader.config.bo.TableConfig;

import java.util.Iterator;
import java.util.List;

/**
 * Title: ExcelFormatControl.java<br>
 * Description:Excel输出格式管理器<br>
 * Company: Orchis Copyright @ 2006 .All rights reserved.<br>
 * @author LiN
 * @version 2008-9-29 1.0
 */
public final class ExcelFormatControl {

	private List tableList;

	private ExcelFormatControl() {
	}

	/**
	 * 初始化Excel输出格式管理器
	 * 
	 * @param configName
	 *            模板名称
	 * @param obj
	 *            导出对象
	 * @return 初始化好的格式管理器
	 * @throws Exception
	 */
	public static ExcelFormatControl newInstance(String configName,
			ExportObject obj) throws Exception {
		ExcelFormatControl control = new ExcelFormatControl();
		control.tableList = ExcelConfigFactory.newInstance(configName, obj)
				.getTables();
		return control;
	}

	/**
	 * 返回Table迭代器
	 * 
	 * @return
	 */
	public Iterator getTableIterator() {
		return new TableIterator(tableList.iterator());
	}

	private static class TableIterator implements Iterator {

		private Iterator tableIterator;

		public TableIterator(Iterator tableIterator) {
			this.tableIterator = tableIterator;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see Iterator#hasNext()
		 */
		public boolean hasNext() {
			return tableIterator.hasNext();
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see Iterator#next()
		 */
		public Object next() {
			return new TableAdapter((TableConfig) tableIterator.next());
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see Iterator#remove()
		 */
		public void remove() {
			tableIterator.remove();
		}
	}

	private static class TableAdapter implements Table {

		private TableConfig tableConfig;

		public TableAdapter(TableConfig tableConfig) {
			this.tableConfig = tableConfig;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * //@see com.eorchis.module.excelutils.export.bo.Table#getWidth()
		 */
		public int getWidth() {
			return tableConfig.getWidth();
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.bo.Table#getRowIterator()
		 */
		public Iterator getRowIterator() {
			return new RowIterator(tableConfig.getTRList().iterator());
		}

		private static class RowIterator implements Iterator {

			private Iterator rowIterator;

			public RowIterator(Iterator rowIterator) {
				this.rowIterator = rowIterator;
			}

			/**
			 * (non-Javadoc)
			 * 
			 * @see Iterator#hasNext()
			 */
			public boolean hasNext() {
				return rowIterator.hasNext();
			}

			/**
			 * (non-Javadoc)
			 * 
			 * @see Iterator#next()
			 */
			public Object next() {
				return new RowAdapter((TRConfig) rowIterator.next());
			}

			/**
			 * (non-Javadoc)
			 * 
			 * @see Iterator#remove()
			 */
			public void remove() {
				rowIterator.remove();
			}
		}
	}

	private static class RowAdapter implements Row {

		private TRConfig trConfig;

		public RowAdapter(TRConfig trConfig) {
			this.trConfig = trConfig;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.bo.Row#getCellIterator()
		 */
		public Iterator getCellIterator() {
			return new RowIterator(trConfig.getTDList().iterator());
		}

		private static class RowIterator implements Iterator {

			private Iterator rowIterator;

			public RowIterator(Iterator rowIterator) {
				this.rowIterator = rowIterator;
			}

			/**
			 * (non-Javadoc)
			 * 
			 * @see Iterator#hasNext()
			 */
			public boolean hasNext() {
				return rowIterator.hasNext();
			}

			/**
			 * (non-Javadoc)
			 * 
			 * @see Iterator#next()
			 */
			public Object next() {
				return new CellAdapter((TDConfig) rowIterator.next());
			}

			/**
			 * (non-Javadoc)
			 * 
			 * @see Iterator#remove()
			 */
			public void remove() {
				rowIterator.remove();
			}
		}
	}

	private static class CellAdapter implements Cell {

		private TDConfig tdConfig;

		public CellAdapter(TDConfig tdConfig) {
			this.tdConfig = tdConfig;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.bo.Cell#getColSpan()
		 */
		public int getColSpan() {
			return tdConfig.getColspan() - 1;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.bo.Cell#getRowSpan()
		 */
		public int getRowSpan() {
			return tdConfig.getRowspan() - 1;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.bo.Cell#getStyleName()
		 */
		public String getStyleName() {
			return tdConfig.getStyle();
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see //com.eorchis.module.excelutils.export.bo.Cell#getText()
		 */
		public Object getText() {
			return tdConfig.getValue();
		}
	}
}
