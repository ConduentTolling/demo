package com.conduent.tollposting.model;

import java.time.LocalDate;

public class AccountPlan 
{
	private Long etcAccountId;
	private String apdId;
	private Integer planType;
	private Long planTypeForToll;
	private String deviceNo;
	private Integer status;
	private String commuteFlag;
	private String historyFlag;
	private LocalDate planStartDate;
	private LocalDate planEndDate;
	private LocalDate suspStartDate;
	private LocalDate suspEndDate;
	private Long suspendDays;
	private String suspendFlag="F";
	private String activeFlag;
	private String isDiscountEligible;
	private Double fullFareAmount;
	private Double discountAmount;
	private Double unrealizedAmount;
	private Double collectedAmount;
	private Double extraAxleCharge;
	private Integer tripPerTrx;
	private Long priceScheduleId;
	private Integer tripCount;
	private Long extraAxleChargeCash;
	private String isPeak;
	private TollSchedule tollSchedule;
	
	public Long getEtcAccountId() {
		return etcAccountId;
	}
	public void setEtcAccountId(Long etcAccountId) {
		this.etcAccountId = etcAccountId;
	}
	public String getApdId() {
		return apdId;
	}
	public void setApdId(String apdId) {
		this.apdId = apdId;
	}
	public Integer getPlanType() {
		return planType;
	}
	public void setPlanType(Integer planType) {
		this.planType = planType;
	}
	public Long getPlanTypeForToll() {
		return planTypeForToll;
	}
	public void setPlanTypeForToll(Long planTypeForToll) {
		this.planTypeForToll = planTypeForToll;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCommuteFlag() {
		return commuteFlag;
	}
	public void setCommuteFlag(String commuteFlag) {
		this.commuteFlag = commuteFlag;
	}
	public String getSuspendFlag() {
		return suspendFlag;
	}
	public void setSuspendFlag(String suspendFlag) {
		this.suspendFlag = suspendFlag;
	}
	public String getHistoryFlag() {
		return historyFlag;
	}
	public void setHistoryFlag(String historyFlag) {
		this.historyFlag = historyFlag;
	}
	public Long getSuspendDays() {
		return suspendDays;
	}
	public void setSuspendDays(Long suspendDays) {
		this.suspendDays = suspendDays;
	}
	
	public LocalDate getSuspStartDate() {
		return suspStartDate;
	}
	public void setSuspStartDate(LocalDate suspStartDate) {
		this.suspStartDate = suspStartDate;
	}
	public LocalDate getSuspEndDate() {
		return suspEndDate;
	}
	public void setSuspEndDate(LocalDate suspEndDate) {
		this.suspEndDate = suspEndDate;
	}
	public String getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}
	public String getIsDiscountEligible() {
		return isDiscountEligible;
	}
	public void setIsDiscountEligible(String isDiscountEligible) {
		this.isDiscountEligible = isDiscountEligible;
	}
	public Double getFullFareAmount() {
		return fullFareAmount;
	}
	public void setFullFareAmount(Double fullFareAmount) {
		this.fullFareAmount = fullFareAmount;
	}
	public Double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public Double getUnrealizedAmount() {
		return unrealizedAmount;
	}
	public void setUnrealizedAmount(Double unrealizedAmount) {
		this.unrealizedAmount = unrealizedAmount;
	}
	public Double getCollectedAmount() {
		return collectedAmount;
	}
	public void setCollectedAmount(Double collectedAmount) {
		this.collectedAmount = collectedAmount;
	}
	public Double getExtraAxleCharge() {
		return extraAxleCharge;
	}
	public void setExtraAxleCharge(Double extraAxleCharge) {
		this.extraAxleCharge = extraAxleCharge;
	}
	public Integer getTripPerTrx() {
		return tripPerTrx;
	}
	public void setTripPerTrx(Integer tripPerTrx) {
		this.tripPerTrx = tripPerTrx;
	}
	public Long getPriceScheduleId() {
		return priceScheduleId;
	}
	public void setPriceScheduleId(Long priceScheduleId) {
		this.priceScheduleId = priceScheduleId;
	}
	public Integer getTripCount() {
		return tripCount;
	}
	public void setTripCount(Integer tripCount) {
		this.tripCount = tripCount;
	}
	public Long getExtraAxleChargeCash() {
		return extraAxleChargeCash;
	}
	public void setExtraAxleChargeCash(Long extraAxleChargeCash) {
		this.extraAxleChargeCash = extraAxleChargeCash;
	}
	public String getIsPeak() {
		return isPeak;
	}
	public void setIsPeak(String isPeak) {
		this.isPeak = isPeak;
	}
	public LocalDate getPlanStartDate() {
		return planStartDate;
	}
	public void setPlanStartDate(LocalDate planStartDate) {
		this.planStartDate = planStartDate;
	}
	public LocalDate getPlanEndDate() {
		return planEndDate;
	}
	public void setPlanEndDate(LocalDate planEndDate) {
		this.planEndDate = planEndDate;
	}
	
	public TollSchedule getTollSchedule() {
		return tollSchedule;
	}
	public void setTollSchedule(TollSchedule tollSchedule) {
		this.tollSchedule = tollSchedule;
	}
	@Override
	public String toString() {
		return "AccountPlan [etcAccountId=" + etcAccountId + ", apdId=" + apdId + ", planType=" + planType
				+ ", planTypeForToll=" + planTypeForToll + ", deviceNo=" + deviceNo + ", status=" + status
				+ ", commuteFlag=" + commuteFlag + ", historyFlag=" + historyFlag + ", planStartDate=" + planStartDate
				+ ", planEndDate=" + planEndDate + ", suspStartDate=" + suspStartDate + ", suspEndDate=" + suspEndDate
				+ ", suspendDays=" + suspendDays + ", suspendFlag=" + suspendFlag + ", activeFlag=" + activeFlag
				+ ", isDiscountEligible=" + isDiscountEligible + ", fullFareAmount=" + fullFareAmount
				+ ", discountAmount=" + discountAmount + ", unrealizedAmount=" + unrealizedAmount + ", collectedAmount="
				+ collectedAmount + ", extraAxleCharge=" + extraAxleCharge + ", tripPerTrx=" + tripPerTrx
				+ ", priceScheduleId=" + priceScheduleId + ", tripCount=" + tripCount + ", extraAxleChargeCash="
				+ extraAxleChargeCash + ", isPeak=" + isPeak + "]";
	}
}