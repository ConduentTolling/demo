package com.conduent.tpms.intx.dto;

/**
 * RevenueDateStatistics Dto
 * 
 * @author deepeshb
 *
 */
public class RevenueDateStatisticsDto {

	private Integer plazaId;

	private String revenueTime;

	private Integer revenueSecondOfDay;

	public Integer getPlazaId() {
		return plazaId;
	}

	public void setPlazaId(Integer plazaId) {
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

	@Override
	public String toString() {
		return "RevenueDateStatisticsDto [plazaId=" + plazaId + ", revenueTime=" + revenueTime + ", revenueSecondOfDay="
				+ revenueSecondOfDay + "]";
	}
	

}
