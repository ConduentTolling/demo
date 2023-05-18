package com.conduent.parking.posting.model;

import java.time.LocalDate;

public class TripHistory 
{
	private LocalDate tripStartDate;
	private LocalDate tripEndDate;
	private LocalDate recongDate;
	private Integer tripsMade;
	private Integer tripsLeft;
	private String reconFlag;
	private LocalDate lastTxDate;
	private Double usageAmount;
	private Double fullFareAmount;
	private Double discountedAmount;
	private Double amountCharged;
	private Double tripCharged;
	private String apdId;
	private Integer lateTrips;
	private Long etcAccountId;
	private Integer planTypeId;
	private Long lastLaneTxId;

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
	public Integer getTripsMade() {
		return tripsMade;
	}
	public void setTripsMade(Integer tripsMade) {
		this.tripsMade = tripsMade;
	}
	public Integer getTripsLeft() {
		return tripsLeft;
	}
	public void setTripsLeft(Integer tripsLeft) {
		this.tripsLeft = tripsLeft;
	}
	public String getReconFlag() {
		return reconFlag;
	}
	public void setReconFlag(String reconFlag) {
		this.reconFlag = reconFlag;
	}
	public LocalDate getLastTxDate() {
		return lastTxDate;
	}
	public void setLastTxDate(LocalDate lastTxDate) {
		this.lastTxDate = lastTxDate;
	}
	public Double getUsageAmount() {
		return usageAmount;
	}
	public void setUsageAmount(Double usageAmount) {
		this.usageAmount = usageAmount;
	}
	public Double getFullFareAmount() {
		return fullFareAmount;
	}
	public void setFullFareAmount(Double fullFareAmount) {
		this.fullFareAmount = fullFareAmount;
	}
	public Double getDiscountedAmount() {
		return discountedAmount;
	}
	public void setDiscountedAmount(Double discountedAmount) {
		this.discountedAmount = discountedAmount;
	}
	
	
	public String getApdId() {
		return apdId;
	}
	public void setApdId(String apdId) {
		this.apdId = apdId;
	}
	
	
	public LocalDate getRecongDate() {
		return recongDate;
	}
	public void setRecongDate(LocalDate recongDate) {
		this.recongDate = recongDate;
	}
	
	public Integer getLateTrips() {
		return lateTrips;
	}
	public void setLateTrips(Integer lateTrips) {
		this.lateTrips = lateTrips;
	}
	
	public Double getAmountCharged() {
		return amountCharged;
	}
	public void setAmountCharged(Double amountCharged) {
		this.amountCharged = amountCharged;
	}
	
	public Double getTripCharged() {
		return tripCharged;
	}
	public void setTripCharged(Double tripCharged) {
		this.tripCharged = tripCharged;
	}
	
	public Long getEtcAccountId() {
		return etcAccountId;
	}
	public void setEtcAccountId(Long etcAccountId) {
		this.etcAccountId = etcAccountId;
	}
	
	
	
	public Integer getPlanTypeId() {
		return planTypeId;
	}
	public void setPlanTypeId(Integer planTypeId) {
		this.planTypeId = planTypeId;
	}
	public Long getLastLaneTxId() {
		return lastLaneTxId;
	}
	public void setLastLaneTxId(Long lastLaneTxId) {
		this.lastLaneTxId = lastLaneTxId;
	}
	@Override
	public String toString() {
		return "TripHistory [tripStartDate=" + tripStartDate + ", tripEndDate=" + tripEndDate + ", recongDate="
				+ recongDate + ", tripsMade=" + tripsMade + ", tripsLeft=" + tripsLeft + ", reconFlag=" + reconFlag
				+ ", lastTxDate=" + lastTxDate + ", usageAmount=" + usageAmount + ", fullFareAmount=" + fullFareAmount
				+ ", discountedAmount=" + discountedAmount + ", amountCharged=" + amountCharged + ", tripCharged="
				+ tripCharged + ", apdId=" + apdId + ", lateTrips=" + lateTrips + ", etcAccountId=" + etcAccountId
				+ ", planTypeId=" + planTypeId + ", lastLaneTxId=" + lastLaneTxId + "]";
	}
	
}