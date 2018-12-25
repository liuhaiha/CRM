package com.tydic.traffic.tafa.daf.page;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class Page<T> {

	/**标识分页的Mapper */
	@JsonIgnore
	private String id;

	/** 请求页码 */
	private int pageNo;
	
	/** 页大小 */
	private int pageSize;

	/** 总记录数 */
	private int totalSize;

	/** 总页数 */
	private int totalPage;
	
	/** 结果集*/
	private List<T> results;
	
	public Page() {
		
	}

	public Page(int pageNo, int pageSize, int totalSize, int totalPage) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalSize = totalSize;
		this.totalPage = totalPage;
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

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
