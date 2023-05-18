package com.conduent.tpms.iag.model;


/**
 * @author taniyan
 * 
 */

public class FPMSAPIInputsforDiscountPlan {
	
	private Integer etcAccountId;
	private Integer accountAgency;
	private Integer qty;
	private double unitAmount;
	private double totalAmount;
	private String category;
	private String subcategory;
	private String todId;
	private String reasonCode;
	private String tranType;
	
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
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public double getUnitAmount() {
		return unitAmount;
	}
	public void setUnitAmount(double unitAmount) {
		this.unitAmount = unitAmount;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
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
	
	public String getTranType() {
		return tranType;
	}
	public void setTranType(String tranType) {
		this.tranType = tranType;
	}
	@Override
	public String toString() {
		return "FPMSAPIInputsforDiscountPlan [etcAccountId=" + etcAccountId + ", accountAgency=" + accountAgency
				+ ", qty=" + qty + ", unitAmount=" + unitAmount + ", totalAmount=" + totalAmount + ", category="
				+ category + ", subcategory=" + subcategory + ", todId=" + todId + ", reasonCode=" + reasonCode
				+ ", tranType=" + tranType + "]";
	}

}
