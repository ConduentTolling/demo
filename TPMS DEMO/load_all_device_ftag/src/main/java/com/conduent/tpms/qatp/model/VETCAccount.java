package com.conduent.tpms.qatp.model;

import java.io.Serializable;

/**
 * ETC account response dto
 * 
 * @author Urvashi C
 *
 */

public class VETCAccount implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long etcAccountId;
	private String accountNo;
	private Integer acctActStatus;
	private int accountType;
	private Integer postPaidStatus;
	private Double rebillAmount;
	private Double rebillThreshold;
	private int agencyId;
	private String agencyReference;
	private String unregistered;
	private Integer primaryRebillPayType;
	private String accountSuspended;
	private String postpaidFlag;
	//private String deviceNo;
	//private int deviceTypeId;
	
	public long getEtcAccountId() {
		return etcAccountId;
	}
	public void setEtcAccountId(long etcAccountId) {
		this.etcAccountId = etcAccountId;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public Integer getAcctActStatus() {
		return acctActStatus;
	}
	public void setAcctActStatus(Integer acctActStatus) {
		this.acctActStatus = acctActStatus;
	}
	public int getAccountType() {
		return accountType;
	}
	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}
	public Integer getPostPaidStatus() {
		return postPaidStatus;
	}
	public void setPostPaidStatus(Integer postPaidStatus) {
		this.postPaidStatus = postPaidStatus;
	}
	public Double getRebillAmount() {
		return rebillAmount;
	}
	public void setRebillAmount(Double rebillAmount) {
		this.rebillAmount = rebillAmount;
	}
	public Double getRebillThreshold() {
		return rebillThreshold;
	}
	public void setRebillThreshold(Double rebillThreshold) {
		this.rebillThreshold = rebillThreshold;
	}
	public int getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}
	public String getAgencyReference() {
		return agencyReference;
	}
	public void setAgencyReference(String agencyReference) {
		this.agencyReference = agencyReference;
	}
	public String getUnregistered() {
		return unregistered;
	}
	public void setUnregistered(String unregistered) {
		this.unregistered = unregistered;
	}
	public Integer getPrimaryRebillPayType() {
		return primaryRebillPayType;
	}
	public void setPrimaryRebillPayType(Integer primaryRebillPayType) {
		this.primaryRebillPayType = primaryRebillPayType;
	}
	public String getAccountSuspended() {
		return accountSuspended;
	}
	public void setAccountSuspended(String accountSuspended) {
		this.accountSuspended = accountSuspended;
	}
	public String getPostpaidFlag() {
		return postpaidFlag;
	}
	public void setPostpaidFlag(String postpaidFlag) {
		this.postpaidFlag = postpaidFlag;
	}
	
	@Override
	public String toString() {
		return "VETCAccount [etcAccountId=" + etcAccountId + ", accountNo=" + accountNo + ", acctActStatus="
				+ acctActStatus + ", accountType=" + accountType + ", postPaidStatus=" + postPaidStatus
				+ ", rebillAmount=" + rebillAmount + ", rebillThreshold=" + rebillThreshold + ", agencyId=" + agencyId
				+ ", agencyReference=" + agencyReference + ", unregistered=" + unregistered + ", primaryRebillPayType="
				+ primaryRebillPayType + ", accountSuspended=" + accountSuspended + ", postpaidFlag=" + postpaidFlag
				+ "]";
	}
	
}
