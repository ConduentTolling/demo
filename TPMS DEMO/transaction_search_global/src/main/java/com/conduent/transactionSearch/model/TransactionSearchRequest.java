package com.conduent.transactionSearch.model;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class TransactionSearchRequest {
	
	@ApiModelProperty(value = "Agency Id", dataType = "Integer", required = true, example = "2")
	private String accountNumber;
	
	@ApiModelProperty(value = "Agency Id", dataType = "Integer", required = true, example = "2")
	private List<TransactionSearchFilter> transactionSearchFilter;
	
	@ApiModelProperty(value = "Agency Id", dataType = "Integer", required = true, example = "2")
	private Integer page;
	
	@ApiModelProperty(value = "Agency Id", dataType = "Integer", required = true, example = "2")
	private Integer size;
	
	@ApiModelProperty(value = "Agency Id", dataType = "Integer", required = true, example = "2")
	private List<SortingAndPaging> sortingAndPaging;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public List<TransactionSearchFilter> getTransactionSearchFilter() {
		return transactionSearchFilter;
	}

	public void setTransactionSearchFilter(List<TransactionSearchFilter> transactionSearchFilter) {
		this.transactionSearchFilter = transactionSearchFilter;
	}

	

	public List<SortingAndPaging> getSortingAndPaging() {
		return sortingAndPaging;
	}

	public void setSortingAndPaging(List<SortingAndPaging> sortingAndPaging) {
		this.sortingAndPaging = sortingAndPaging;
	}

	
	
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "TransactionSearchRequest [accountNumber=" + accountNumber + ", transactionSearchFilter="
				+ transactionSearchFilter + ", page=" + page + ", size=" + size + ", sortingAndPaging="
				+ sortingAndPaging + "]";
	}

	
	
}
