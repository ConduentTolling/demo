package com.conduent.parking.posting.dto;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.conduent.parking.posting.constant.AccountStatus;
import com.conduent.parking.posting.constant.TxStatus;
import com.conduent.parking.posting.model.AccountPlan;
import com.conduent.parking.posting.model.PlanPolicy;

public class AccountInfoDTO 
{
	private String acctFinStatus;
	private Double currentBalance;
	private Double postPaidBalance;
	private Double postPaidUnbilledBalance;
	private Integer accountType;
	private Integer agencyId;
	private Integer acctAccountStatus;
	private String rebillPayType;
	private Integer rebillThreshold;
	private String ccAccountNumberEncrypt;
	private String abaNumber;
	private LocalDate ccExpirationDate;
	private LocalDateTime lastTollTxTimestamp;
	private String ccAccountNumberEncrypt1;
	private String postPaidStatus;
	private String unregistered;
	private Long etcAccountId;
	private String deviceNumber;
    private Double prepaidBalance;
    private Double deviceDeposit;
    private Boolean isDiscountEligible;
    private Long lastTollTxId;
    //"lastTollPostedDate": null,
    //"lastTollTxAmt": 0.0,
    //"firstTollTimestamp": null,
    //"updateTs": "2021-04-05 08:14:31.804",
    //"openDate": "2021-04-05",
    //"lastTollTxDate": null,
    //"lastFinTxDate": null,
    //"lastPaymentTxId": null,
    //"lastPaymentDate": null,
    //"lastFinTxId": null,
    //"lastFinTxAmt": null,
     private Double postpaidFee;
     private Double accountBalance;
     private Double rebillAmount;
     private Double thresholdAmount;
     private String crmAccountId;
     private String accountNumber;
     private String payType;
     private String postPaidFlag;
      
    private List<AccountPlan> accountPlanList;
 	private Integer selectedPlanType;
 	private AccountPlan selectedPlan;
 	private PlanPolicy selectedPlanPolicy;

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
	public Double getPostPaidBalance() {
		return postPaidBalance;
	}
	public void setPostPaidBalance(Double postPaidBalance) {
		this.postPaidBalance = postPaidBalance;
	}
	public Double getPostPaidUnbilledBalance() {
		return postPaidUnbilledBalance;
	}
	public void setPostPaidUnbilledBalance(Double postPaidUnbilledBalance) {
		this.postPaidUnbilledBalance = postPaidUnbilledBalance;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	public Integer getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}
	
	public Integer getAcctAccountStatus() {
		return acctAccountStatus;
	}
	public void setAcctAccountStatus(Integer acctAccountStatus) {
		this.acctAccountStatus = acctAccountStatus;
	}
	public String getRebillPayType() {
		return rebillPayType;
	}
	public void setRebillPayType(String rebillPayType) {
		this.rebillPayType = rebillPayType;
	}
	public Integer getRebillThreshold() {
		return rebillThreshold;
	}
	public void setRebillThreshold(Integer rebillThreshold) {
		this.rebillThreshold = rebillThreshold;
	}
	
	public String getCcAccountNumberEncrypt() {
		return ccAccountNumberEncrypt;
	}
	public void setCcAccountNumberEncrypt(String ccAccountNumberEncrypt) {
		this.ccAccountNumberEncrypt = ccAccountNumberEncrypt;
	}
	public String getAbaNumber() {
		return abaNumber;
	}
	public void setAbaNumber(String abaNumber) {
		this.abaNumber = abaNumber;
	}
	public LocalDate getCcExpirationDate() {
		return ccExpirationDate;
	}
	public void setCcExpirationDate(LocalDate ccExpirationDate) {
		this.ccExpirationDate = ccExpirationDate;
	}
	public LocalDateTime getLastTollTxTimestamp() {
		return lastTollTxTimestamp;
	}
	public void setLastTollTxTimestamp(LocalDateTime lastTollTxTimestamp) {
		this.lastTollTxTimestamp = lastTollTxTimestamp;
	}
	
	public String getPostPaidStatus() {
		return postPaidStatus;
	}
	public void setPostPaidStatus(String postPaidStatus) {
		this.postPaidStatus = postPaidStatus;
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

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public Double getPrepaidBalance() {
		return prepaidBalance;
	}

	public void setPrepaidBalance(Double prepaidBalance) {
		this.prepaidBalance = prepaidBalance;
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

	public Long getLastTollTxId() {
		return lastTollTxId;
	}

	public void setLastTollTxId(Long lastTollTxId) {
		this.lastTollTxId = lastTollTxId;
	}

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
	public Double getThresholdAmount() {
		return thresholdAmount;
	}
	public void setThresholdAmount(Double thresholdAmount) {
		this.thresholdAmount = thresholdAmount;
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
	public String getPostPaidFlag() {
		return postPaidFlag;
	}
	public void setPostPaidFlag(String postPaidFlag) {
		this.postPaidFlag = postPaidFlag;
	}
	public String getCcAccountNumberEncrypt1() {
		return ccAccountNumberEncrypt1;
	}
	public void setCcAccountNumberEncrypt1(String ccAccountNumberEncrypt1) {
		this.ccAccountNumberEncrypt1 = ccAccountNumberEncrypt1;
	}
	public List<AccountPlan> getAccountPlanList() {
		return accountPlanList;
	}
	public void setAccountPlanList(List<AccountPlan> accountPlanList) {
		this.accountPlanList = accountPlanList;
	}
	
	public Integer getSelectedPlanType() {
		return selectedPlanType;
	}
	public void setSelectedPlanType(Integer selectedPlanType) {
		this.selectedPlanType = selectedPlanType;
	}
	public AccountPlan getSelectedPlan() {
		return selectedPlan;
	}
	public void setSelectedPlan(AccountPlan selectedPlan) {
		this.selectedPlan = selectedPlan;
	}
	
	
	public PlanPolicy getSelectedPlanPolicy() {
		return selectedPlanPolicy;
	}
	public void setSelectedPlanPolicy(PlanPolicy selectedPlanPolicy) {
		this.selectedPlanPolicy = selectedPlanPolicy;
	}
	public static TxStatus validateStatus(AccountInfoDTO accountInfo)
	{
		if(accountInfo.getAcctAccountStatus().intValue()==AccountStatus.PENDING.getCode())
		{
			return TxStatus.TX_STATUS_INVACPEND;
		}
		else if(accountInfo.getAcctAccountStatus().intValue()==AccountStatus.RVKF.getCode())
		{
			return TxStatus.TX_STATUS_INVACRVKF;
		}
		else if(accountInfo.getAcctAccountStatus().intValue()==AccountStatus.CLOSED.getCode())
		{
			return TxStatus.TX_STATUS_INVACCLOS;
		}
		else if(accountInfo.getAcctAccountStatus().intValue()==AccountStatus.ACTIVE.getCode() ||
				accountInfo.getAcctAccountStatus().intValue()==AccountStatus.RVKW.getCode() ||
				accountInfo.getAcctAccountStatus().intValue()==AccountStatus.CLOSE_PEND.getCode())
		{
			return null;
		}
		else
		{
			return TxStatus.TX_STATUS_INVACC;
		}
	}
	@Override
	public String toString() {
		return "AccountInfoDTO [acctFinStatus=" + acctFinStatus + ", currentBalance=" + currentBalance
				+ ", postPaidBalance=" + postPaidBalance + ", postPaidUnbilledBalance=" + postPaidUnbilledBalance
				+ ", accountType=" + accountType + ", agencyId=" + agencyId + ", acctAccountStatus=" + acctAccountStatus
				+ ", rebillPayType=" + rebillPayType + ", rebillThreshold=" + rebillThreshold
				+ ", ccAccountNumberEncrypt=" + ccAccountNumberEncrypt + ", abaNumber=" + abaNumber
				+ ", ccExpirationDate=" + ccExpirationDate + ", lastTollTxTimestamp=" + lastTollTxTimestamp
				+ ", ccAccountNumberEncrypt1=" + ccAccountNumberEncrypt1 + ", postPaidStatus=" + postPaidStatus
				+ ", unregistered=" + unregistered + ", etcAccountId=" + etcAccountId + ", deviceNumber=" + deviceNumber
				+ ", prepaidBalance=" + prepaidBalance + ", deviceDeposit=" + deviceDeposit + ", isDiscountEligible="
				+ isDiscountEligible + ", lastTollTxId=" + lastTollTxId + ", postpaidFee=" + postpaidFee
				+ ", accountBalance=" + accountBalance + ", rebillAmount=" + rebillAmount + ", thresholdAmount="
				+ thresholdAmount + ", crmAccountId=" + crmAccountId + ", accountNumber=" + accountNumber + ", payType="
				+ payType + ", postPaidFlag=" + postPaidFlag + ", accountPlanList=" + accountPlanList
				+ ", selectedPlanType=" + selectedPlanType + ", selectedPlan=" + selectedPlan + "]";
	}
}