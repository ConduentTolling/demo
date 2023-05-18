package com.conduent.tpms.intx.dto;

import java.time.LocalDate;

/**
 * Toll Fare Dto
 * 
 * @author deepeshb
 *
 */
public class TollFareDto {

	private Long planType;

	private Integer laneMode;

	private Integer actualClass;

	private Integer accountType;

	private String schedPrice;

	private LocalDate txDate;

	private String txTime;
	
	private Integer tollRevenueType;
	
	private Integer plazaAgencyId;
	
	private Integer plazaId;
	
	private Integer entryPlazaId;
	
	private Double fullFareAmount;
	
	private Double discountAmount;
	
	private Double extraAxleCharge;
	
	private Double extraAxleChargeCash;
	
	private String entryTime;

	public long getPlanType() {
		return planType;
	}

	public void setPlanType(long planType) {
		this.planType = planType;
	}

	public Integer getLaneMode() {
		return laneMode;
	}

	public void setLaneMode(Integer laneMode) {
		this.laneMode = laneMode;
	}

	public Integer getActualClass() {
		return actualClass;
	}

	public void setActualClass(Integer actualClass) {
		this.actualClass = actualClass;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public String getSchedPrice() {
		return schedPrice;
	}

	public void setSchedPrice(String schedPrice) {
		this.schedPrice = schedPrice;
	}

	

	public LocalDate getTxDate() {
		return txDate;
	}

	public void setTxDate(LocalDate txDate) {
		this.txDate = txDate;
	}

	public String getTxTime() {
		return txTime;
	}

	public void setTxTime(String txTime) {
		this.txTime = txTime;
	}

	public Integer getTollRevenueType() {
		return tollRevenueType;
	}

	public void setTollRevenueType(Integer tollRevenueType) {
		this.tollRevenueType = tollRevenueType;
	}

	public Integer getPlazaAgencyId() {
		return plazaAgencyId;
	}

	public void setPlazaAgencyId(Integer plazaAgencyId) {
		this.plazaAgencyId = plazaAgencyId;
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

	public Double getFullFareAmount() {
		return fullFareAmount;
	}

	public void setFullFareAmount(Double fullFareAmount) {
		this.fullFareAmount = fullFareAmount;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
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

	public String getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}
	
	
	

}
