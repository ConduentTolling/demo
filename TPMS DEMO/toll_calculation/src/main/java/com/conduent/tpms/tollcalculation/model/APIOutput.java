package com.conduent.tpms.tollcalculation.model;

public class APIOutput 
{
	private Integer priceScheduleId;
	private Double discountFare;
	private Double fullFare;
	private Double extraAxleCharge;
	private Double extraAxleChargeCash;
	
	public Integer getPriceScheduleId() {
		return priceScheduleId;
	}
	public void setPriceScheduleId(Integer priceScheduleId) {
		this.priceScheduleId = priceScheduleId;
	}
	public Double getDiscountFare() {
		return discountFare;
	}
	public void setDiscountFare(Double discountFare) {
		this.discountFare = discountFare;
	}
	public Double getFullFare() {
		return fullFare;
	}
	public void setFullFare(Double fullFare) {
		this.fullFare = fullFare;
	}
	public Double getExtraAxleCharge() {
		return extraAxleCharge;
	}
	public void setExtraAxleCharge(Double extraAxleCharge) {
		this.extraAxleCharge = extraAxleCharge;
	}
	public Double getExtraAxleChargeCash() {
		return extraAxleChargeCash;
	}
	public void setExtraAxleChargeCash(Double extraAxleChargeCash) {
		this.extraAxleChargeCash = extraAxleChargeCash;
	}
	@Override
	public String toString() {
		return "APIOutput [priceScheduleId=" + priceScheduleId + ", discountFare=" + discountFare + ", fullFare="
				+ fullFare + ", extraAxleCharge=" + extraAxleCharge + ", extraAxleChargeCash=" + extraAxleChargeCash
				+ "]";
	}
	
	
	
	
}
