package com.conduent.tpms.iag.dto;

import java.io.Serializable;

/**
 * Account api response dto
 * 
 * @author Urvashic
 *
 */
public class AccountApiInfoDto implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3150302360450873248L;
	private String etcAccountId;
	private Integer agencyId;
	private String accountType;
	private Boolean isDiscountEligible;
	private String accountNumber;
	private String acctFinStatus;
	private double accountBalance;
	private double thresholdAmount;
	
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

	public Boolean getIsDiscountEligible() {
		return isDiscountEligible;
	}

	public void setIsDiscountEligible(Boolean isDiscountEligible) {
		this.isDiscountEligible = isDiscountEligible;
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

	public double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public double getThresholdAmount() {
		return thresholdAmount;
	}

	public void setThresholdAmount(double thresholdAmount) {
		this.thresholdAmount = thresholdAmount;
	}

	@Override
	public String toString() {
		return "AccountApiInfoDto [etcAccountId=" + etcAccountId + ", agencyId=" + agencyId + ", accountType="
				+ accountType + ", isDiscountEligible=" + isDiscountEligible + ", accountNumber=" + accountNumber
				+ ", acctFinStatus=" + acctFinStatus + ", accountBalance=" + accountBalance + ", thresholdAmount="
				+ thresholdAmount + "]";
	}

}
