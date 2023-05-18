package com.conduent.tpms.qatp.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Account api response dto
 * 
 * @author Urvashi C
 *
 */
public class AccountApiInfoDto implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3150302360450873248L;
	private String etcAccountId;
	private Integer agencyId;
	private String accountType;
	private Double prepaidBalance = 0.0; 
	private Double postpaidBalance = 0.0; 
	private Double deviceDeposit = 0.0;  
	private Boolean isDiscountEligible;
	//private Long lastTollTxId;
	private Double postpaidFee = 0.0;
	private Double accountBalance = 0.0;
	private Double rebillAmount = 0.0;
	private Double thresholdAmount = 0.0; 
	private String crmAccountId;
	private String accountNumber;
	private String payType;
	private String acctFinStatus;
	
	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private String firstTollTimestamp;

	public String getFirstTollTimestamp() {
		return firstTollTimestamp;
	}

	public void setFirstTollTimestamp(String firstTollTimestamp) {
		this.firstTollTimestamp = firstTollTimestamp;
	}

	public Double getThresholdAmount() {
		return thresholdAmount;
	}

	public void setThresholdAmount(Double thresholdAmount) {
		this.thresholdAmount = thresholdAmount;
	}

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

	public Double getDeviceDeposit() {
		return deviceDeposit;
	}

	public void setDeviceDeposit(Double deviceDeposit) {
		this.deviceDeposit = deviceDeposit;
	}

	public Boolean getIsDiscountEligible() {
		return isDiscountEligible;
	}

	public void setIsDiscountEligible(Boolean isDiscountEligible) {
		this.isDiscountEligible = isDiscountEligible;
	}

//	public Long getLastTollTxId() {
//		return lastTollTxId;
//	}
//
//	public void setLastTollTxId(Long lastTollTxId) {
//		this.lastTollTxId = lastTollTxId;
//	}

	public Double getPostpaidFee() {
		return postpaidFee;
	}

	public void setPostpaidFee(Double postpaidFee) {
		this.postpaidFee = postpaidFee;
	}

	public Double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public Double getRebillAmount() {
		return rebillAmount;
	}

	public void setRebillAmount(Double rebillAmount) {
		this.rebillAmount = rebillAmount;
	}

	public String getCrmAccountId() {
		return crmAccountId;
	}

	public void setCrmAccountId(String crmAccountId) {
		this.crmAccountId = crmAccountId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
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
				+ ", deviceDeposit=" + deviceDeposit + ", isDiscountEligible=" + isDiscountEligible + ", postpaidFee="
				+ postpaidFee + ", accountBalance=" + accountBalance + ", rebillAmount=" + rebillAmount
				+ ", thresholdAmount=" + thresholdAmount + ", crmAccountId=" + crmAccountId + ", accountNumber="
				+ accountNumber + ", payType=" + payType + ", acctFinStatus=" + acctFinStatus + ", firstTollTimestamp="
				+ firstTollTimestamp + "]";
	}

	

}