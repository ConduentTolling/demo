package com.conduent.tpms.reconciliation.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class AccountTollDto {

	
	private Long laneTxId;
	private String txExternRefNo;
	private Double postedFareAmount;
	private String accountAgencyId;
	private LocalDate postedDate;
	private LocalDate revenueDate;
	private String plateCountry;
	private String plateState;
	private String plateNumber;
	private String txTypeInd;
	private String txStatus;
	private String planTypeId;
	private Integer plazaAgencyId;
	private Long externFileId;
	//for MTA
	private Long collectorId;
	private Integer laneMode;
	private String deviceNo;
	private Integer plazaId;
	private Integer laneId;
	private OffsetDateTime txTimeStamp;
	
	public Long getLaneTxId() {
		return laneTxId;
	}
	public void setLaneTxId(Long laneTxId) {
		this.laneTxId = laneTxId;
	}
	public String getTxExternRefNo() {
		return txExternRefNo;
	}
	public void setTxExternRefNo(String txExternRefNo) {
		this.txExternRefNo = txExternRefNo;
	}
	public Double getPostedFareAmount() {
		return postedFareAmount;
	}
	public void setPostedFareAmount(Double postedFareAmount) {
		this.postedFareAmount = postedFareAmount;
	}
	public String getAccountAgencyId() {
		return accountAgencyId;
	}
	public void setAccountAgencyId(String accountAgencyId) {
		this.accountAgencyId = accountAgencyId;
	}
	public LocalDate getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(LocalDate postedDate) {
		this.postedDate = postedDate;
	}
	public LocalDate getRevenueDate() {
		return revenueDate;
	}
	public void setRevenueDate(LocalDate revenueDate) {
		this.revenueDate = revenueDate;
	}
	public String getPlateCountry() {
		return plateCountry;
	}
	public void setPlateCountry(String plateCountry) {
		this.plateCountry = plateCountry;
	}
	public String getPlateState() {
		return plateState;
	}
	public void setPlateState(String plateState) {
		this.plateState = plateState;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public String getTxTypeInd() {
		return txTypeInd;
	}
	public void setTxTypeInd(String txTypeInd) {
		this.txTypeInd = txTypeInd;
	}
	public String getTxStatus() {
		return txStatus;
	}
	public void setTxStatus(String txStatus) {
		this.txStatus = txStatus;
	}
	public String getPlanTypeId() {
		return planTypeId;
	}
	public void setPlanTypeId(String planTypeId) {
		this.planTypeId = planTypeId;
	}
	public Integer getPlazaAgencyId() {
		return plazaAgencyId;
	}
	public void setPlazaAgencyId(Integer plazaAgencyId) {
		this.plazaAgencyId = plazaAgencyId;
	}
	public Long getExternFileId() {
		return externFileId;
	}
	public void setExternFileId(Long externFileId) {
		this.externFileId = externFileId;
	}
	public Long getCollectorId() {
		return collectorId;
	}
	public void setCollectorId(Long collectorId) {
		this.collectorId = collectorId;
	}
	public Integer getLaneMode() {
		return laneMode;
	}
	public void setLaneMode(Integer laneMode) {
		this.laneMode = laneMode;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public Integer getPlazaId() {
		return plazaId;
	}
	public void setPlazaId(Integer plazaId) {
		this.plazaId = plazaId;
	}
	public Integer getLaneId() {
		return laneId;
	}
	public void setLaneId(Integer laneId) {
		this.laneId = laneId;
	}
	public OffsetDateTime getTxTimeStamp() {
		return txTimeStamp;
	}
	public void setTxTimeStamp(OffsetDateTime txTimeStamp) {
		this.txTimeStamp = txTimeStamp;
	}
	@Override
	public String toString() {
		return "AccountTollDto [laneTxId=" + laneTxId + ", txExternRefNo=" + txExternRefNo + ", postedFareAmount="
				+ postedFareAmount + ", accountAgencyId=" + accountAgencyId + ", postedDate=" + postedDate
				+ ", revenueDate=" + revenueDate + ", plateCountry=" + plateCountry + ", plateState=" + plateState
				+ ", plateNumber=" + plateNumber + ", txTypeInd=" + txTypeInd + ", txStatus=" + txStatus
				+ ", planTypeId=" + planTypeId + ", plazaAgencyId=" + plazaAgencyId + ", externFileId=" + externFileId
				+ ", collectorId=" + collectorId + ", laneMode=" + laneMode + ", deviceNo=" + deviceNo + ", plazaId="
				+ plazaId + ", laneId=" + laneId + ", txTimeStamp=" + txTimeStamp + "]";
	}
	
	
	
}
