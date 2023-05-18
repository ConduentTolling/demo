package com.conduent.tpms.process25a.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TagDeviceDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private LocalDate StartDate;
	private String deviceNo;
	private LocalDate endDate;
	private Integer invalidAddressId;
	private LocalDateTime lastFileTS;
	private String infileInd;
	private LocalDateTime updateDeviceTs;
	private String fileAgencyId;
	private String IAGTagStatus;


	@Override
	public String toString() {
		return "TagDeviceDetails [StartDate=" + StartDate + ", deviceNo=" + deviceNo + ", endDate=" + endDate
				+ ", invalidAddressId=" + invalidAddressId + ", lastFileTS=" + lastFileTS + ", infileInd=" + infileInd
				+ ", updateDeviceTs=" + updateDeviceTs + ", fileAgencyId=" + fileAgencyId + ", IAGTagStatus="
				+ IAGTagStatus + "]";
	}

	
	public LocalDate getStartDate() {
		return StartDate;
	}


	public void setStartDate(LocalDate startDate) {
		StartDate = startDate;
	}


	public String getDeviceNo() {
		return deviceNo;
	}


	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}


	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Integer getInvalidAddressId() {
		return invalidAddressId;
	}

	public void setInvalidAddressId(Integer invalidAddressId) {
		this.invalidAddressId = invalidAddressId;
	}

	public LocalDateTime getLastFileTS() {
		return lastFileTS;
	}

	public void setLastFileTS(LocalDateTime lastFileTS) {
		this.lastFileTS = lastFileTS;
	}

	public String getInfileInd() {
		return infileInd;
	}

	public void setInfileInd(String infileInd) {
		this.infileInd = infileInd;
	}

	public LocalDateTime getUpdateDeviceTs() {
		return updateDeviceTs;
	}

	public void setUpdateDeviceTs(LocalDateTime updateDeviceTs) {
		this.updateDeviceTs = updateDeviceTs;
	}

	public String getFileAgencyId() {
		return fileAgencyId;
	}

	public void setFileAgencyId(String fileAgencyId) {
		this.fileAgencyId = fileAgencyId;
	}

	public String getIAGTagStatus() {
		return IAGTagStatus;
	}

	public void setIAGTagStatus(String iAGTagStatus) {
		IAGTagStatus = iAGTagStatus;
	}


}
