package com.conduent.tollposting.model;

public class FpmsAccount 
{
	private Double currentBalance;
	private Long etcAccountId;
	public Double getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}
	public Long getEtcAccountId() {
		return etcAccountId;
	}
	public void setEtcAccountId(Long etcAccountId) {
		this.etcAccountId = etcAccountId;
	}
	@Override
	public String toString() {
		return "FpmsAccount [currentBalance=" + currentBalance + ", etcAccountId=" + etcAccountId + "]";
	}

	
	
}
