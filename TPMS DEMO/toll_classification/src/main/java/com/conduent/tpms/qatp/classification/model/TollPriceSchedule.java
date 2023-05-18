package com.conduent.tpms.qatp.classification.model;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Toll Price Schedule Model
 * 
 * @author deepeshb
 *
 */
public class TollPriceSchedule {

	private Integer priceScheduleId;
	private Integer agencyId;
	private String daysInd;
	private LocalDate effectiveDate;
	private String time;
	private LocalDate expiryDate;
	
	
	public Integer getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}

	public String getDaysInd() {
		return daysInd;
	}

	public void setDaysInd(String daysInd) {
		this.daysInd = daysInd;
	}

	public LocalDate getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(LocalDate effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Integer getPriceScheduleId() {
		return priceScheduleId;
	}

	public void setPriceScheduleId(Integer priceScheduleId) {
		this.priceScheduleId = priceScheduleId;
	}

	@Override
	public String toString() {
		return "TollPriceSchedule [priceScheduleId=" + priceScheduleId + ", agencyId=" + agencyId + ", daysInd="
				+ daysInd + ", effectiveDate=" + effectiveDate + ", time=" + time + ", expiryDate=" + expiryDate + "]";
	}

	
}
