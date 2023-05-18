package com.conduent.tpms.cams.dto;

import java.sql.Timestamp;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

//@JsonInclude(Include.NON_NULL)
public class TTripHistory extends PageInfo{
	
	private Long apdId;
	@DateTimeFormat(iso = ISO.DATE)
	@JsonFormat(pattern = "MM/dd/yyyy")
	private LocalDate tripStartDate;
	@DateTimeFormat(iso = ISO.DATE)
	@JsonFormat(pattern = "MM/dd/yyyy")
	private LocalDate tripEndDate;
	private LocalDate reconDate;
	private Long tripsMade;
	private Long tripsCharged;
	private Long lateTrips;
	private String amountCharged;
	private String reconFlag;
	private Long tripsLeft;
	private Timestamp updateTs;
	private LocalDate lastTxDate;
	private String usageAmount;
	private String fullFareAmount;
	private String discountedAmount;
	private String etcAccountId;
	private Long planType;
	private Long lastLaneTxId;
	private Timestamp lastTxPostTimestamp;
	private Long compositeTransactionId;
	private String cscLookupKey;
	@JsonInclude(Include.NON_NULL)
	private String status;
	@JsonInclude(Include.NON_NULL)
	private Long recordCount;
	
	public Long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}
	@Override
	public String toString() {
		return "TTripHistory [apdId=" + apdId + ", tripStartDate=" + tripStartDate + ", tripEndDate=" + tripEndDate
				+ ", reconDate=" + reconDate + ", tripsMade=" + tripsMade + ", tripsCharged=" + tripsCharged
				+ ", lateTrips=" + lateTrips + ", amountCharged=" + amountCharged + ", reconFlag=" + reconFlag
				+ ", tripsLeft=" + tripsLeft + ", updateTs=" + updateTs + ", lastTxDate=" + lastTxDate
				+ ", usageAmount=" + usageAmount + ", fullFareAmount=" + fullFareAmount + ", discountedAmount="
				+ discountedAmount + ", etcAccountId=" + etcAccountId + ", planType=" + planType + ", lastLaneTxId="
				+ lastLaneTxId + ", lastTxPostTimestamp=" + lastTxPostTimestamp + ", compositeTransactionId="
				+ compositeTransactionId + ", cscLookupKey=" + cscLookupKey + ", status=" + status + "]";
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getApdId() {
		return apdId;
	}
	public void setApdId(Long apdId) {
		this.apdId = apdId;
	}
	public LocalDate getTripStartDate() {
		return tripStartDate;
	}
	public void setTripStartDate(LocalDate tripStartDate) {
		this.tripStartDate = tripStartDate;
	}
	public LocalDate getTripEndDate() {
		return tripEndDate;
	}
	public void setTripEndDate(LocalDate tripEndDate) {
		this.tripEndDate = tripEndDate;
	}
	public LocalDate getReconDate() {
		return reconDate;
	}
	public void setReconDate(LocalDate reconDate) {
		this.reconDate = reconDate;
	}
	public Long getTripsMade() {
		return tripsMade;
	}
	public void setTripsMade(Long tripsMade) {
		this.tripsMade = tripsMade;
	}
	public Long getTripsCharged() {
		return tripsCharged;
	}
	public void setTripsCharged(Long tripsCharged) {
		this.tripsCharged = tripsCharged;
	}
	public Long getLateTrips() {
		return lateTrips;
	}
	public void setLateTrips(Long lateTrips) {
		this.lateTrips = lateTrips;
	}
	public String getAmountCharged() {
		return amountCharged;
	}
	public void setAmountCharged(String amountCharged) {
		this.amountCharged = amountCharged;
	}
	public String getReconFlag() {
		return reconFlag;
	}
	public void setReconFlag(String reconFlag) {
		this.reconFlag = reconFlag;
	}
	public Long getTripsLeft() {
		return tripsLeft;
	}
	public void setTripsLeft(Long tripsLeft) {
		this.tripsLeft = tripsLeft;
	}
	public Timestamp getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(Timestamp updateTs) {
		this.updateTs = updateTs;
	}
	public LocalDate getLastTxDate() {
		return lastTxDate;
	}
	public void setLastTxDate(LocalDate lastTxDate) {
		this.lastTxDate = lastTxDate;
	}
	public String getUsageAmount() {
		return usageAmount;
	}
	public void setUsageAmount(String usageAmount) {
		this.usageAmount = usageAmount;
	}
	public String getFullFareAmount() {
		return fullFareAmount;
	}
	public void setFullFareAmount(String fullFareAmount) {
		this.fullFareAmount = fullFareAmount;
	}
	public String getDiscountedAmount() {
		return discountedAmount;
	}
	public void setDiscountedAmount(String discountedAmount) {
		this.discountedAmount = discountedAmount;
	}
	public String getEtcAccountId() {
		return etcAccountId;
	}
	public void setEtcAccountId(String etcAccountId) {
		this.etcAccountId = etcAccountId;
	}
	public Long getPlanType() {
		return planType;
	}
	public void setPlanType(Long planType) {
		this.planType = planType;
	}
	public Long getLastLaneTxId() {
		return lastLaneTxId;
	}
	public void setLastLaneTxId(Long lastLaneTxId) {
		this.lastLaneTxId = lastLaneTxId;
	}
	public Timestamp getLastTxPostTimestamp() {
		return lastTxPostTimestamp;
	}
	public void setLastTxPostTimestamp(Timestamp lastTxPostTimestamp) {
		this.lastTxPostTimestamp = lastTxPostTimestamp;
	}
	public Long getCompositeTransactionId() {
		return compositeTransactionId;
	}
	public void setCompositeTransactionId(Long compositeTransactionId) {
		this.compositeTransactionId = compositeTransactionId;
	}
	public String getCscLookupKey() {
		return cscLookupKey;
	}
	public void setCscLookupKey(String cscLookupKey) {
		this.cscLookupKey = cscLookupKey;
	}


}
