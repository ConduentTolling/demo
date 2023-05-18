package com.conduent.tpms.iag.model;

public class AccountInfo {
	private Long etcAccountId;
	private String isPrevalidated;
	private String deviceStatusCode;
	private int iagTagStatus;
	private Integer accountType;
	private String unregistered;
	private int deviceStatus;
	private String acctFinStatus;
	private Double currentBalance;
	private int PrimaryRebillPayType;
	private int acctActStatus;
	private String accountSuspended;
	private String deviceNo;

	public int getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(int deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public String getUnregistered() {
		return unregistered;
	}

	public void setUnregistered(String unregistered) {
		this.unregistered = unregistered;
	}

	public Long getEtcAccountId() {
		return etcAccountId;
	}

	public void setEtcAccountId(Long etcAccountId) {
		this.etcAccountId = etcAccountId;
	}

	public String getIsPrevalidated() {
		return isPrevalidated;
	}

	public void setIsPrevalidated(String isPrevalidated) {
		this.isPrevalidated = isPrevalidated;
	}

	public String getDeviceStatusCode() {
		return deviceStatusCode;
	}

	public void setDeviceStatusCode(String deviceStatusCode) {
		this.deviceStatusCode = deviceStatusCode;
	}

	public Integer getIagTagStatus() {
		return iagTagStatus;
	}

	public void setIagTagStatus(Integer iagTagStatus) {
		this.iagTagStatus = iagTagStatus;
	}

	public String getAcctFinStatus() {
		return acctFinStatus;
	}

	public void setAcctFinStatus(String acctFinStatus) {
		this.acctFinStatus = acctFinStatus;
	}

	public Double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public int getPrimaryRebillPayType() {
		return PrimaryRebillPayType;
	}

	public void setPrimaryRebillPayType(int primaryRebillPayType) {
		PrimaryRebillPayType = primaryRebillPayType;
	}

	public int getAcctActStatus() {
		return acctActStatus;
	}

	public void setAcctActStatus(int acctActStatus) {
		this.acctActStatus = acctActStatus;
	}

	public String getAccountSuspended() {
		return accountSuspended;
	}

	public void setAccountSuspended(String accountSuspended) {
		this.accountSuspended = accountSuspended;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public void setIagTagStatus(int iagTagStatus) {
		this.iagTagStatus = iagTagStatus;
	}

	@Override
	public String toString() {
		return "AccountInfo [etcAccountId=" + etcAccountId + ", isPrevalidated=" + isPrevalidated
				+ ", deviceStatusCode=" + deviceStatusCode + ", iagTagStatus=" + iagTagStatus + ", accountType="
				+ accountType + ", unregistered=" + unregistered + ", deviceStatus=" + deviceStatus + ", acctFinStatus="
				+ acctFinStatus + ", currentBalance=" + currentBalance + ", PrimaryRebillPayType="
				+ PrimaryRebillPayType + ", acctActStatus=" + acctActStatus + ", accountSuspended=" + accountSuspended
				+ ", deviceNo=" + deviceNo + "]";
	}

}
