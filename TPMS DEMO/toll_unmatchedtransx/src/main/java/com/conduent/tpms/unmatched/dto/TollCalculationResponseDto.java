package com.conduent.tpms.unmatched.dto;

public class TollCalculationResponseDto {

	
	private Double discountFare;
	private Double fullFare;
	private Double extraAxleCharge;
	
	
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
	
	@Override
	public String toString() {
		return "TollCalculationResponseDataDto [discountFare=" + discountFare + ", fullFare=" + fullFare
				+ ", extraAxleCharge=" + extraAxleCharge + "]";
	}
	
	
	
	
}
