package com.conduent.tpms.qeval.BatchModel;

import java.sql.Date;

public class TPricingData {
	private String category;
	private String subCategory;
	private String accountType;
	private String rebillType;
	private Long revenueAgency;
	private Date effectiveDate;
	private Date expiryDate;
	private Long  unitPrice;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
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
	public Long getRevenueAgency() {
		return revenueAgency;
	}
	public void setRevenueAgency(Long revenueAgency) {
		this.revenueAgency = revenueAgency;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public Long getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Long unitPrice) {
		this.unitPrice = unitPrice;
	}

	
}
