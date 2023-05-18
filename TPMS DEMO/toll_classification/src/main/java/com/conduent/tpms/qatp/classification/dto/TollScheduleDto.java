package com.conduent.tpms.qatp.classification.dto;

import java.time.LocalDate;
import java.util.List;

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
	
	private LocalDate effectiveDate; 
	private LocalDate expirayDate; 
	private Integer plazaId;
	private Integer entryPlazaId;
	private Integer vehicleClass;
	private List<String> revenueTypeIdList;
	private Long planTypeId;
	private Integer priceScheduleId;

	
	
	
	public LocalDate getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(LocalDate effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public LocalDate getExpirayDate() {
		return expirayDate;
	}

	public void setExpirayDate(LocalDate expirayDate) {
		this.expirayDate = expirayDate;
	}

	public Integer getPlazaId() {
		return plazaId;
	}

	public void setPlazaId(Integer plazaId) {
		this.plazaId = plazaId;
	}

	public Integer getEntryPlazaId() {
		return entryPlazaId;
	}

	public void setEntryPlazaId(Integer entryPlazaId) {
		this.entryPlazaId = entryPlazaId;
	}

	public Integer getVehicleClass() {
		return vehicleClass;
	}

	public void setVehicleClass(Integer vehicleClass) {
		this.vehicleClass = vehicleClass;
	}

	public List<String> getRevenueTypeIdList() {
		return revenueTypeIdList;
	}

	public void setRevenueTypeIdList(List<String> revenueTypeIdList) {
		this.revenueTypeIdList = revenueTypeIdList;
	}

	public Long getPlanTypeId() {
		return planTypeId;
	}

	public void setPlanTypeId(Long planTypeId) {
		this.planTypeId = planTypeId;
	}

	public Integer getPriceScheduleId() {
		return priceScheduleId;
	}

	public void setPriceScheduleId(Integer priceScheduleId) {
		this.priceScheduleId = priceScheduleId;
	}

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
				+ tollScheduleId + ", effectiveDate=" + effectiveDate + ", expirayDate=" + expirayDate + ", plazaId="
				+ plazaId + ", entryPlazaId=" + entryPlazaId + ", vehicleClass=" + vehicleClass + ", revenueTypeIdList="
				+ revenueTypeIdList + ", planTypeId=" + planTypeId + ", priceScheduleId=" + priceScheduleId + "]";
	}

	
	
}
