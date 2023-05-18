package com.conduent.tpms.qeval.BatchModel;

import java.sql.Date;

public class ReqPolicyData {
	
	private String isTagSale;
	private  Date startDate;
	private  Date endDate;
	private String accountType;
	private Long agencyId;
	private String allowDeletion;
	private Long freeTagCount;
	private String noDepositPlan;
	private String payType;
	private String stateId;
	private Long transferDeposit;
	public String getIsTagSale() {
		return isTagSale;
	}
	public void setIsTagSale(String isTagSale) {
		this.isTagSale = isTagSale;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public Long getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}
	public String getAllowDeletion() {
		return allowDeletion;
	}
	public void setAllowDeletion(String allowDeletion) {
		this.allowDeletion = allowDeletion;
	}
	public Long getFreeTagCount() {
		return freeTagCount;
	}
	public void setFreeTagCount(Long freeTagCount) {
		this.freeTagCount = freeTagCount;
	}
	public String getNoDepositPlan() {
		return noDepositPlan;
	}
	public void setNoDepositPlan(String noDepositPlan) {
		this.noDepositPlan = noDepositPlan;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getStateId() {
		return stateId;
	}
	public void setStateId(String stateId) {
		this.stateId = stateId;
	}
	public Long getTransferDeposit() {
		return transferDeposit;
	}
	public void setTransferDeposit(Long transferDeposit) {
		this.transferDeposit = transferDeposit;
	}
	
	

}


