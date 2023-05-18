package com.conduent.parking.posting.model;

import java.time.LocalDate;

public class AccountPlanSuspension 
{
	private Integer apdId;
	private LocalDate createDate;
	private LocalDate startDate;
	private LocalDate endDate;
	private String etcAccountId;
	
	public Integer getApdId() {
		return apdId;
	}
	public void setApdId(Integer apdId) {
		this.apdId = apdId;
	}
	public LocalDate getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public String getEtcAccountId() {
		return etcAccountId;
	}
	public void setEtcAccountId(String etcAccountId) {
		this.etcAccountId = etcAccountId;
	}
	@Override
	public String toString() {
		return "AccountPlanSuspension [apdId=" + apdId + ", createDate="
				+ createDate + ", startDate=" + startDate + ", endDate=" + endDate + ", etcAccountId=" + etcAccountId
				+ "]";
	}
	
}