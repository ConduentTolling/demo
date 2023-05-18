package com.conduent.transactionSearch.model;

import java.util.List;
import java.util.Optional;


public class TransactionApiResponse {
	private int totalPageNumbers;
	private int totalNumberOfRecords;
	private List<PageBreakPOJO> responseList;
	
    public int getTotalPageNumbers() {
		return totalPageNumbers;
	}
	public void setTotalPageNumbers(int totalPageNumbers) {
		this.totalPageNumbers = totalPageNumbers;
	}
	public int getTotalNumberOfRecords() {
		return totalNumberOfRecords;
	}
	public void setTotalNumberOfRecords(int totalNumberOfRecords) {
		this.totalNumberOfRecords = totalNumberOfRecords;
	}
	
	public List<PageBreakPOJO> getResponseList() {
		return responseList;
	}
	public void setResponseList(List<PageBreakPOJO> responseList) {
		this.responseList = responseList;
	}
	
}
