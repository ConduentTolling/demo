package com.conduent.tpms.qeval.BatchModel;

import java.io.Serializable;

public class ThresholdDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String accountType;
	private Integer agencyId;
	private Double amount;
	private String payType;
	private Double MinThresholdPercentage; 
	private Double minRebillAmt;
	
	public Double getMinRebillAmt() {
		return minRebillAmt;
	}
	public void setMinRebillAmt(Double minRebillAmt) {
		this.minRebillAmt = minRebillAmt;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public Integer getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public Double getMinThresholdPercentage() {
		return MinThresholdPercentage;
	}
	public void setMinThresholdPercentage(Double minThresholdPercentage) {
		MinThresholdPercentage = minThresholdPercentage;
	}
	
	

}
