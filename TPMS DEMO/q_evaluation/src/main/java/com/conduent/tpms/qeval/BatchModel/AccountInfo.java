package com.conduent.tpms.qeval.BatchModel;

import java.sql.Date;

public class AccountInfo {
	
private Long etcAccountId;
private Date firtTollTimestamp;
private Date evalStartDate;
private Date evalEndDate;
private Date openDate;
private Date lastTollTxTimestamp;
private Double  rebillAmount;
private Integer agencyId;
private String accountNo;
private String accountType;
private Long thresholdAmount=10L;
private String payType;
private String LandId;
private Double newRebillAmtForThreshold;
private Double calculatedRebillAmount;


private Double varience;
private Double newRebillAmount;

private Double tollUsage;

private int recordStatus;//1=RebillUp 2=RebillDown 3=skip

public Long getEtcAccountId() {
	return etcAccountId;
}

public void setEtcAccountId(Long etcAccountId) {
	this.etcAccountId = etcAccountId;
}

public Date getFirtTollTimestamp() {
	return firtTollTimestamp;
}

public void setFirtTollTimestamp(Date firtTollTimestamp) {
	this.firtTollTimestamp = firtTollTimestamp;
}

public Date getEvalStartDate() {
	return evalStartDate;
}

public void setEvalStartDate(Date evalStartDate) {
	this.evalStartDate = evalStartDate;
}

public Date getEvalEndDate() {
	return evalEndDate;
}

public void setEvalEndDate(Date evalEndDate) {
	this.evalEndDate = evalEndDate;
}

public Date getOpenDate() {
	return openDate;
}

public void setOpenDate(Date openDate) {
	this.openDate = openDate;
}

public Date getLastTollTxTimestamp() {
	return lastTollTxTimestamp;
}

public void setLastTollTxTimestamp(Date lastTollTxTimestamp) {
	this.lastTollTxTimestamp = lastTollTxTimestamp;
}

public Double getRebillAmount() {
	return rebillAmount;
}

public void setRebillAmount(Double rebillAmount) {
	this.rebillAmount = rebillAmount;
}

public Integer getAgencyId() {
	return agencyId;
}

public void setAgencyId(Integer agencyId) {
	this.agencyId = agencyId;
}

public String getAccountNo() {
	return accountNo;
}

public void setAccountNo(String accountNo) {
	this.accountNo = accountNo;
}

public String getAccountType() {
	return accountType;
}

public void setAccountType(String accountType) {
	this.accountType = accountType;
}

public Long getThresholdAmount() {
	return thresholdAmount;
}

public void setThresholdAmount(Long thresholdAmount) {
	this.thresholdAmount = thresholdAmount;
}

public String getPayType() {
	return payType;
}

public void setPayType(String payType) {
	this.payType = payType;
}

public String getLandId() {
	return LandId;
}

public void setLandId(String landId) {
	LandId = landId;
}

public Double getNewRebillAmtForThreshold() {
	return newRebillAmtForThreshold;
}

public void setNewRebillAmtForThreshold(Double newRebillAmtForThreshold) {
	this.newRebillAmtForThreshold = newRebillAmtForThreshold;
}


public Double getCalculatedRebillAmount() {
	return calculatedRebillAmount;
}

public void setCalculatedRebillAmount(Double calculatedRebillAmount) {
	this.calculatedRebillAmount = calculatedRebillAmount;
}

public Double getVarience() {
	return varience;
}

public void setVarience(Double varience) {
	this.varience = varience;
}

public Double getNewRebillAmount() {
	return newRebillAmount;
}

public void setNewRebillAmount(Double newRebillAmount) {
	this.newRebillAmount = newRebillAmount;
}

public Double getTollUsage() {
	return tollUsage;
}

public void setTollUsage(Double tollUsage) {
	this.tollUsage = tollUsage;
}

public int getRecordStatus() {
	return recordStatus;
}

public void setRecordStatus(int recordStatus) {
	this.recordStatus = recordStatus;
}



}
