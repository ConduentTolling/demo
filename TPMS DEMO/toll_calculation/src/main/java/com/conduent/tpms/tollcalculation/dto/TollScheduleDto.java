package com.conduent.tpms.tollcalculation.dto;

/**
 * Toll Schedule Dto
 * 
 * 
 *
 */
public class TollScheduleDto {

	private Double fullFareAmount;
	private Double discountFareAmount;
	private Double extraAxleCharge;
	private Integer revenueTypeId;
	private Long tollScheduleId;

	public Double getFullFareAmount() {
		return fullFareAmount;
	}

	public void setFullFareAmount(Double fullFareAmount) {
		this.fullFareAmount = fullFareAmount;
	}

	public Double getDiscountFareAmount() {
		return discountFareAmount;
	}

	public void setDiscountFareAmount(Double discountFareAmount) {
		this.discountFareAmount = discountFareAmount;
	}

	public Double getExtraAxleCharge() {
		return extraAxleCharge;
	}

	public void setExtraAxleCharge(Double extraAxleCharge) {
		this.extraAxleCharge = extraAxleCharge;
	}

	public Integer getRevenueTypeId() {
		return revenueTypeId;
	}

	public void setRevenueTypeId(Integer revenueTypeId) {
		this.revenueTypeId = revenueTypeId;
	}

	public Long getTollScheduleId() {
		return tollScheduleId;
	}

	public void setTollScheduleId(Long tollScheduleId) {
		this.tollScheduleId = tollScheduleId;
	}

	@Override
	public String toString() {
		return "TollScheduleDto [fullFareAmount=" + fullFareAmount + ", discountFareAmount=" + discountFareAmount
				+ ", extraAxleCharge=" + extraAxleCharge + ", revenueTypeId=" + revenueTypeId + ", tollScheduleId="
				+ tollScheduleId + "]";
	}

	
}
