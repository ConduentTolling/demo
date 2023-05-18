package com.conduent.tollposting.dto;

import com.google.gson.annotations.Expose;

public class TollCalculationResponseDTO {

	@Expose(serialize = true, deserialize = true)
	private Long priceScheduleId;
	
	@Expose(serialize = true, deserialize = true)
	private Double discountFare;
	
	@Expose(serialize = true, deserialize = true)
	private Double fullFare;
	
	@Expose(serialize = true, deserialize = true)
	private Double extraAxleCharge;
	
	@Expose(serialize = true, deserialize = true)
	private Double extraAxleChargeCash;

	public Long getPriceScheduleId() {
		return priceScheduleId;
	}

	public void setPriceScheduleId(Long priceScheduleId) {
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

	
  /*  "amounts": {
        "priceScheduleId": 1,
        "discountFare": 4.0,
        "fullFare": 2.1,
        "extraAxleCharge": 2.0,
        "extraAxleChargeCash": 1.0
    }
*/
	
}
