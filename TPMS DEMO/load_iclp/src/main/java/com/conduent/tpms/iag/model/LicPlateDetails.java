package com.conduent.tpms.iag.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class LicPlateDetails implements Serializable {

	/**
	 * T_OA_PLATE table class
	 */
	private static final long serialVersionUID = 1L;
	
	private String plateState;
	private String plateNumber;
	private String deviceNo;
	//private Integer plateType;
	private LocalDateTime lastFileTS;
	private LocalDateTime updateDeviceTs;
	private LocalDate startDate;
	private LocalDate endDate;
	
	private LocalDateTime licEffectiveTo;
	

	private LocalDateTime licEffectiveFrom;
	
	private String licHomeAgency;
	
	private String licAccntNo;
	
	private String licVin;
	
	private String licGuaranteed;
	
	private Long xferControlId;
	
	private String plateType;
	
	private String record;
	
	public String getRecord() {
		return record;
	}
	public void setRecord(String record) {
		this.record = record;
	}
	public String getPlateType() {
		return plateType;
	}
	public void setPlateType(String plateType) {
		this.plateType = plateType;
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
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	/*
	 * public Integer getPlateType() { return plateType; } public void
	 * setPlateType(Integer plateType) { this.plateType = plateType; }
	 */
	public LocalDateTime getLastFileTS() {
		return lastFileTS;
	}
	public void setLastFileTS(LocalDateTime lastFileTS) {
		this.lastFileTS = lastFileTS;
	}
	public LocalDateTime getUpdateDeviceTs() {
		return updateDeviceTs;
	}
	public void setUpdateDeviceTs(LocalDateTime updateDeviceTs) {
		this.updateDeviceTs = updateDeviceTs;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "LicPlateDetails [plateState=" + plateState + ", plateNumber=" + plateNumber + ", deviceNo=" + deviceNo
				+ ", plateType=" + plateType + ", lastFileTS=" + lastFileTS + ", updateDeviceTs=" + updateDeviceTs
				+ ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
	
	
	
	public String getLicHomeAgency() {
		return licHomeAgency;
	}
	public void setLicHomeAgency(String licHomeAgency) {
		this.licHomeAgency = licHomeAgency;
	}
	public String getLicAccntNo() {
		return licAccntNo;
	}
	public void setLicAccntNo(String licAccntNo) {
		this.licAccntNo = licAccntNo;
	}
	public String getLicVin() {
		return licVin;
	}
	public void setLicVin(String licVin) {
		this.licVin = licVin;
	}
	public String getLicGuaranteed() {
		return licGuaranteed;
	}
	public void setLicGuaranteed(String licGuaranteed) {
		this.licGuaranteed = licGuaranteed;
	}
	
	public Long getXferControlId() {
		return xferControlId;
	}
	public void setXferControlId(Long xferControlId) {
		this.xferControlId = xferControlId;
	}
	

	public LocalDateTime getLicEffectiveTo() {
		return licEffectiveTo;
	}
	public void setLicEffectiveTo(LocalDateTime licEffectiveTo) {
		this.licEffectiveTo = licEffectiveTo;
	}
	public LocalDateTime getLicEffectiveFrom() {
		return licEffectiveFrom;
	}
	public void setLicEffectiveFrom(LocalDateTime licEffectiveFrom) {
		this.licEffectiveFrom = licEffectiveFrom;
	}

	
	  
	 

	
}
