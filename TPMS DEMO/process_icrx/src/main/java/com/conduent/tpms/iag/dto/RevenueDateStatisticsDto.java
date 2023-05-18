package com.conduent.tpms.iag.dto;

public class RevenueDateStatisticsDto {

	private Long plazaId;
	private String revenueTime;
	private Integer revenueSecondOfDay;

	public Long getPlazaId() {
		return plazaId;
	}

	public void setPlazaId(Long plazaId) {
		this.plazaId = plazaId;
	}

	public String getRevenueTime() {
		return revenueTime;
	}

	public void setRevenueTime(String revenueTime) {
		this.revenueTime = revenueTime;
	}

	public Integer getRevenueSecondOfDay() {
		return revenueSecondOfDay;
	}

	public void setRevenueSecondOfDay(Integer revenueSecondOfDay) {
		this.revenueSecondOfDay = revenueSecondOfDay;
	}
}
