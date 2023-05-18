package com.conduent.transactionSearch.model;

public class SortingAndPaging {
    private String sortType;
    private String sortFieldName;
//    private Integer pageNumber;
//    private Integer numberOfRecords;
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	public String getSortFieldName() {
		return sortFieldName;
	}
	public void setSortFieldName(String sortFieldName) {
		this.sortFieldName = sortFieldName;
	}
	
}
