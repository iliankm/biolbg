package com.biol.biolbg.web.util;

import java.io.Serializable;

public class ViewCredentials implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String sortByFieldName;
	private String sortType;
	private Integer showRowsCount;
	private Integer pageNumber;
	private Integer totalPages;
	private Object customCredentials = null;
	
	public String getSortByFieldName() {
		return sortByFieldName;
	}
	public void setSortByFieldName(String sortByFieldName) {
		this.sortByFieldName = sortByFieldName;
	}
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	public Integer getShowRowsCount() {
		return showRowsCount;
	}
	public void setShowRowsCount(Integer showRowsCount) {
		this.showRowsCount = showRowsCount;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Integer getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	public Object getCustomCredentials() {
		return customCredentials;
	}
	public void setCustomCredentials(Object customCredentials) {
		this.customCredentials = customCredentials;
	}

	
}
