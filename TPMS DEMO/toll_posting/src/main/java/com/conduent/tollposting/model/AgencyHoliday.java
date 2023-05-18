package com.conduent.tollposting.model;

import java.time.LocalDate;

public class AgencyHoliday 
{
	private Long agencyId;
	private LocalDate holiday;
	private String daysInd;
	private String description;
	
	public Long getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}
	public LocalDate getHoliday() {
		return holiday;
	}
	public void setHoliday(LocalDate holiday) {
		this.holiday = holiday;
	}
	public String getDaysInd() {
		return daysInd;
	}
	public void setDaysInd(String daysInd) {
		this.daysInd = daysInd;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "AgencyHoliday [agencyId=" + agencyId + ", holiday=" + holiday + ", daysInd=" + daysInd
				+ ", description=" + description + "]";
	}
}
