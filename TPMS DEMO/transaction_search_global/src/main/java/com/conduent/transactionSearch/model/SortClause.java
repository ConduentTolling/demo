package com.conduent.transactionSearch.model;

public class SortClause {
	private boolean empty;
	private boolean sorted;
	private boolean unsorted;
	public boolean isEmpty() {
		return empty;
	}
	public void setEmpty(boolean empty) {
		this.empty = empty;
	}
	public boolean isSorted() {
		return sorted;
	}
	public void setSorted(boolean sorted) {
		this.sorted = sorted;
	}
	public boolean isUnsorted() {
		return unsorted;
	}
	public void setUnsorted(boolean unsorted) {
		this.unsorted = unsorted;
	}
	
    
}
