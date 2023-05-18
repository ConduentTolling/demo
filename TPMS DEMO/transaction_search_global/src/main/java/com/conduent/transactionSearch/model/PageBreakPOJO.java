package com.conduent.transactionSearch.model;

import java.util.List;

public class PageBreakPOJO {
	private int pageNo;
	
    private List<TransactionResponsePOJO> responseList;
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public List<TransactionResponsePOJO> getResponseList() {
		return responseList;
	}
	public void setResponseList(List<TransactionResponsePOJO> responseList) {
		this.responseList = responseList;
	}
	

}
