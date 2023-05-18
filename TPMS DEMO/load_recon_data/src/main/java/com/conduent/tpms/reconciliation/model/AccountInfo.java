package com.conduent.tpms.reconciliation.model;

/**
 * Account Info model
 * 
 * @author deepeshb
 *
 */
public class AccountInfo {

	private Double accountTotalBalance;

	private Double postPaidBalance;

	private Double currentBalance;

	private Integer accountType;

	private Integer postPaidStatus;

	private Integer primaryRebillPayType;

	private Integer agencyId;

	private Integer acctActStatus;

	private Integer acctFinStatus;

	public Double getAccountTotalBalance() {
		return accountTotalBalance;
	}

	public void setAccountTotalBalance(Double accountTotalBalance) {
		this.accountTotalBalance = accountTotalBalance;
	}

	public Double getPostPaidBalance() {
		return postPaidBalance;
	}

	public void setPostPaidBalance(Double postPaidBalance) {
		this.postPaidBalance = postPaidBalance;
	}

	public Double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public Integer getPostPaidStatus() {
		return postPaidStatus;
	}

	public void setPostPaidStatus(Integer postPaidStatus) {
		this.postPaidStatus = postPaidStatus;
	}

	public Integer getPrimaryRebillPayType() {
		return primaryRebillPayType;
	}

	public void setPrimaryRebillPayType(Integer primaryRebillPayType) {
		this.primaryRebillPayType = primaryRebillPayType;
	}

	public Integer getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}

	public Integer getAcctActStatus() {
		return acctActStatus;
	}

	public void setAcctActStatus(Integer acctActStatus) {
		this.acctActStatus = acctActStatus;
	}

	public Integer getAcctFinStatus() {
		return acctFinStatus;
	}

	public void setAcctFinStatus(Integer acctFinStatus) {
		this.acctFinStatus = acctFinStatus;
	}

	@Override
	public String toString() {
		return "AccountInfo [accountTotalBalance=" + accountTotalBalance + ", postPaidBalance=" + postPaidBalance
				+ ", currentBalance=" + currentBalance + ", accountType=" + accountType + ", postPaidStatus="
				+ postPaidStatus + ", primaryRebillPayType=" + primaryRebillPayType + ", agencyId=" + agencyId
				+ ", acctActStatus=" + acctActStatus + ", acctFinStatus=" + acctFinStatus + "]";
	}

}
