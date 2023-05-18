package com.conduent.tpms.unmatched.dto;

/**
 * Account api response dto
 * 
 * @author deepeshb
 *
 */
public class AccountApiInfoDto {
	private String etcAccountId;
	private Integer agencyId;
	private String accountType;
	private Double prepaidBalance;
	private Double postpaidBalance;
	private Double accountBalance;
	private String accountNumber;
	private String acctFinStatus;

	public String getEtcAccountId() {
		return etcAccountId;
	}

	public void setEtcAccountId(String etcAccountId) {
		this.etcAccountId = etcAccountId;
	}

	public Integer getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Double getPrepaidBalance() {
		return prepaidBalance;
	}

	public void setPrepaidBalance(Double prepaidBalance) {
		this.prepaidBalance = prepaidBalance;
	}

	public Double getPostpaidBalance() {
		return postpaidBalance;
	}

	public void setPostpaidBalance(Double postpaidBalance) {
		this.postpaidBalance = postpaidBalance;
	}

	public Double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAcctFinStatus() {
		return acctFinStatus;
	}

	public void setAcctFinStatus(String acctFinStatus) {
		this.acctFinStatus = acctFinStatus;
	}

	@Override
	public String toString() {
		return "AccountApiInfoDto [etcAccountId=" + etcAccountId + ", agencyId=" + agencyId + ", accountType="
				+ accountType + ", prepaidBalance=" + prepaidBalance + ", postpaidBalance=" + postpaidBalance
				+ ", accountBalance=" + accountBalance + ", accountNumber=" + accountNumber + ", acctFinStatus="
				+ acctFinStatus + "]";
	}

}
