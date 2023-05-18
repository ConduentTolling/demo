package com.conduent.tpms.iag.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TTripHistory implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	Integer apdId;
	LocalDate tripStartDate;
	LocalDate tripEndDate;
	LocalDate reconDate;
	Integer tripsMade;
	Integer tripsCharged;
	Integer lateTrips;
	LocalDateTime updateTs;
	double amounttCharged;
	String reconFlag;
	Integer tripsLeft;
	LocalDate lastTxDate;
	double usageAmount;
	double fullFareAmount;
	double discountedAmount;
	Integer etcAccountId;
	Integer planType;
	//Integer lastlaneTxId;
	//LocalDateTime lastTxPostTime;
	Integer compositeTnId;
	String csclookupKey;
	
	/*
	 * public Number getLastLaneTxId() { return lastLaneTxId; }
	 * 
	 * 
	 * 
	 * public void setLastLaneTxId(Number lastLaneTxId) { this.lastLaneTxId =
	 * lastLaneTxId; }
	 * 
	 * 
	 * 
	 * public LocalDateTime getLastTxPostTimeStamp() { return lastTxPostTimeStamp; }
	 * 
	 * 
	 * 
	 * public void setLastTxPostTimeStamp(LocalDateTime lastTxPostTimeStamp) {
	 * this.lastTxPostTimeStamp = lastTxPostTimeStamp; }
	 */



	//Number lastLaneTxId;
	//LocalDateTime lastTxPostTimeStamp;
	
	
	/*
	 * Integer lastLaneTxId; LocalDateTime lastTxPostTimeStamp;
	 * 
	 * 
	 * public Integer getLastLaneTxId() { return lastLaneTxId; }
	 * 
	 * 
	 * 
	 * public void setLastLaneTxId(Integer lastLaneTxId) { this.lastLaneTxId =
	 * lastLaneTxId; }
	 * 
	 * 
	 * public LocalDateTime getLastTxPostTimeStamp() { return lastTxPostTimeStamp; }
	 * 
	 * 
	 * 
	 * public void setLastTxPostTimeStamp(LocalDateTime lastTxPostTimeStamp) {
	 * this.lastTxPostTimeStamp = lastTxPostTimeStamp; }
	 * 
	 */
	
	BigInteger lastLaneTxId;
	
	public BigInteger getLastLaneTxId() {
		return lastLaneTxId;
	}



	public void setLastLaneTxId(BigInteger lastLaneTxId) {
		this.lastLaneTxId = lastLaneTxId;
	}


	
//	  public Timestamp getLaneTxPostTimeStamp() { return laneTxPostTimeStamp; }
//	  
//	  
//	  
//	  public void setLaneTxPostTimeStamp(Timestamp laneTxPostTimeStamp) {
//	  this.laneTxPostTimeStamp = laneTxPostTimeStamp; }
	 



	//Timestamp lastTxPostTimeStamp;
	
	LocalDateTime lastTxPostTimeStamp;
	

	public LocalDateTime getLastTxPostTimeStamp() {
		return lastTxPostTimeStamp;
	}



	public void setLastTxPostTimeStamp(LocalDateTime lastTxPostTimeStamp) {
		this.lastTxPostTimeStamp = lastTxPostTimeStamp;
	}



	public Integer getApdId() {
		return apdId;
	}



	public void setApdId(Integer apdId) {
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



	public Integer getTripsMade() {
		return tripsMade;
	}



	public void setTripsMade(Integer tripsMade) {
		this.tripsMade = tripsMade;
	}



	public Integer getTripsCharged() {
		return tripsCharged;
	}



	public void setTripsCharged(Integer tripsCharged) {
		this.tripsCharged = tripsCharged;
	}



	public Integer getLateTrips() {
		return lateTrips;
	}



	public void setLateTrips(Integer lateTrips) {
		this.lateTrips = lateTrips;
	}



	public LocalDateTime getUpdateTs() {
		return updateTs;
	}



	public void setUpdateTs(LocalDateTime updateTs) {
		this.updateTs = updateTs;
	}





	public String getReconFlag() {
		return reconFlag;
	}



	public void setReconFlag(String reconFlag) {
		this.reconFlag = reconFlag;
	}



	public Integer getTripsLeft() {
		return tripsLeft;
	}



	public void setTripsLeft(Integer tripsLeft) {
		this.tripsLeft = tripsLeft;
	}



	public LocalDate getLastTxDate() {
		return lastTxDate;
	}



	public void setLastTxDate(LocalDate lastTxDate) {
		this.lastTxDate = lastTxDate;
	}

	public Integer getEtcAccountId() {
		return etcAccountId;
	}



	public void setEtcAccountId(Integer etcAccountId) {
		this.etcAccountId = etcAccountId;
	}



	public Integer getPlanType() {
		return planType;
	}



	public void setPlanType(Integer planType) {
		this.planType = planType;
	}



	/*
	 * public Integer getLastlaneTxId() { return lastlaneTxId; }
	 * 
	 * 
	 * 
	 * public void setLastlaneTxId(Integer lastlaneTxId) { this.lastlaneTxId =
	 * lastlaneTxId; }
	 */



	/*
	 * public LocalDateTime getLastTxPostTime() { return lastTxPostTime; }
	 * 
	 * 
	 * 
	 * public void setLastTxPostTime(LocalDateTime lastTxPostTime) {
	 * this.lastTxPostTime = lastTxPostTime; }
	 */



	public Integer getCompositeTnId() {
		return compositeTnId;
	}



	public void setCompositeTnId(Integer compositeTnId) {
		this.compositeTnId = compositeTnId;
	}



	public String getCsclookupKey() {
		return csclookupKey;
	}



	public void setCsclookupKey(String csclookupKey) {
		this.csclookupKey = csclookupKey;
	}
	

	public double getAmounttCharged() {
		return amounttCharged;
	}



	public void setAmounttCharged(double amounttCharged) {
		this.amounttCharged = amounttCharged;
	}



	public double getUsageAmount() {
		return usageAmount;
	}



	public void setUsageAmount(double usageAmount) {
		this.usageAmount = usageAmount;
	}



	public double getFullFareAmount() {
		return fullFareAmount;
	}



	public void setFullFareAmount(double fullFareAmount) {
		this.fullFareAmount = fullFareAmount;
	}



	public double getDiscountedAmount() {
		return discountedAmount;
	}



	public void setDiscountedAmount(double discountedAmount) {
		this.discountedAmount = discountedAmount;
	}



	@Override
	public String toString() {
		return "TTripHistory [apdId=" + apdId + ", tripStartDate=" + tripStartDate + ", tripEndDate=" + tripEndDate
				+ ", reconDate=" + reconDate + ", tripsMade=" + tripsMade + ", tripsCharged=" + tripsCharged
				+ ", lateTrips=" + lateTrips + ", updateTs=" + updateTs + ", amounttCharged=" + amounttCharged
				+ ", reconFlag=" + reconFlag + ", tripsLeft=" + tripsLeft + ", lastTxDate=" + lastTxDate
				+ ", usageAmount=" + usageAmount + ", fullFareAmount=" + fullFareAmount + ", discountedAmount="
				+ discountedAmount + ", etcAccountId=" + etcAccountId + ", planType=" + planType + ", lastlaneTxId="
				+ lastLaneTxId + ", lastTxPostTime=" + lastTxPostTimeStamp + ", compositeTnId=" + compositeTnId
				+ ", csclookupKey=" + csclookupKey + "]";
	}
	

	
}
