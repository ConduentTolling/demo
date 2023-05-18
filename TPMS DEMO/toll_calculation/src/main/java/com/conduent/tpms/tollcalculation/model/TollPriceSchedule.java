package com.conduent.tpms.tollcalculation.model;

/**
 * Toll Price Schedule Model
 * 
 * @author deepeshb
 *
 */
public class TollPriceSchedule {

	private Integer priceScheduleId;

	public Integer getPriceScheduleId() {
		return priceScheduleId;
	}

	public void setPriceScheduleId(Integer priceScheduleId) {
		this.priceScheduleId = priceScheduleId;
	}

	@Override
	public String toString() {
		return "TollPriceSchedule [priceScheduleId=" + priceScheduleId + "]";
	}

	
}
