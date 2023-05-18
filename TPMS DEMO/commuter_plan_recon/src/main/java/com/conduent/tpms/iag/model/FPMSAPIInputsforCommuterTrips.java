package com.conduent.tpms.iag.model;

/**
 * @author taniyan
 * 
 */

public class FPMSAPIInputsforCommuterTrips {
	
	private Integer etcAccountId;
	private Integer accountAgency;
	private String category;
	private String subcategory;
	private String todId;
	private String reasonCode;
	private String description;
	private String accountType;
	private String rebillType;
	private String referenceId;
	private String tranType;
	private double amount;
	
	public Integer getEtcAccountId() {
		return etcAccountId;
	}
	public void setEtcAccountId(Integer etcAccountId) {
		this.etcAccountId = etcAccountId;
	}
	public Integer getAccountAgency() {
		return accountAgency;
	}
	public void setAccountAgency(Integer accountAgency) {
		this.accountAgency = accountAgency;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}
	public String getTodId() {
		return todId;
	}
	public void setTodId(String todId) {
		this.todId = todId;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getRebillType() {
		return rebillType;
	}
	public void setRebillType(String rebillType) {
		this.rebillType = rebillType;
	}
	public String getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	public String getTranType() {
		return tranType;
	}
	public void setTranType(String tranType) {
		this.tranType = tranType;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return "FPMSAPIInputsforCommuterTrips [etcAccountId=" + etcAccountId + ", accountAgency=" + accountAgency
				+ ", category=" + category + ", subcategory=" + subcategory + ", todId=" + todId + ", reasonCode="
				+ reasonCode + ", description=" + description + ", accountType=" + accountType + ", rebillType="
				+ rebillType + ", referenceId=" + referenceId + ", tranType=" + tranType + ", amount=" + amount + "]";
	}

}
