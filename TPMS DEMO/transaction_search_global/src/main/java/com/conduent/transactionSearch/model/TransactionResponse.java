package com.conduent.transactionSearch.model;

import java.util.List;

public class TransactionResponse {

	//private int fetchedResultCount;
	private List<TransactionResponsePOJO> content;
	private Pageable pageable;
	private int totalElements;
    private boolean last;
    private int totalPages;
	private int size;
	private int number;
	private SortClause sort;
	private int numberOfElements;
	private boolean first;
	private boolean empty;
	
	public List<TransactionResponsePOJO> getContent() {
		return content;
	}
	public void setContent(List<TransactionResponsePOJO> content) {
		this.content = content;
	}
	public Pageable getPageable() {
		return pageable;
	}
	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}
	public int getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}
	public boolean isLast() {
		return last;
	}
	public void setLast(boolean last) {
		this.last = last;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public SortClause getSort() {
		return sort;
	}
	public void setSort(SortClause sort) {
		this.sort = sort;
	}
	public int getNumberOfElements() {
		return numberOfElements;
	}
	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}
	public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
	public boolean isEmpty() {
		return empty;
	}
	public void setEmpty(boolean empty) {
		this.empty = empty;
	}
	
	
}
