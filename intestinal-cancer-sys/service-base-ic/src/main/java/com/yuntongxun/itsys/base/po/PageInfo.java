package com.yuntongxun.itsys.base.po;

/**
 * 分页数据 
 * @author zhangzl
 *
 */
public class PageInfo {
	

	/**
	 * 当前请求页码
	 */
	public int pageNo;
	/**
	 * 每页条数
	 */
	public int pageSize;
	/**
	 * 总页数
	 */
	public int totalPageCount;
	/**
	 * 总数据行数
	 */
	public int totalRowCount;
	
	public PageInfo(){
		
	}
	public PageInfo(int pageNo,int pageSize,int totalPageCount,int totalRowCount){
		this.pageNo=pageNo;
		this.pageSize=pageSize;
		this.totalPageCount=totalPageCount;
		this.totalRowCount=totalRowCount;
	}
	
	public int getPageNo() {
		return pageNo;
	}



	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}



	public int getPageSize() {
		return pageSize;
	}



	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}



	public int getTotalPageCount() {
		return totalPageCount;
	}



	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}



	public int getTotalRowCount() {
		return totalRowCount;
	}



	public void setTotalRowCount(int totalRowCount) {
		this.totalRowCount = totalRowCount;
	}



}
