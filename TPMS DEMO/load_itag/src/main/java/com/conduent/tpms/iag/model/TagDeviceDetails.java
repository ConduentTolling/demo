package com.conduent.tpms.iag.model;

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

	private String tagHomeAgency;
	
	private String tagAcctInfo;
	

	private String tagAccountNo;
	
	private String tagACTypeIND;
	
	private String tagProtocol;
	
	private String tagType;
	
	private String tagMount;
	
	private String tagClass;
	
	private Long xferControlID;
	
	private String record;
   



	//@Override
	/*
	 * public String toString() { return "TagDeviceDetails [StartDate=" + StartDate
	 * + ", deviceNo=" + deviceNo + ", endDate=" + endDate + ", invalidAddressId=" +
	 * invalidAddressId + ", lastFileTS=" + lastFileTS + ", infileInd=" + infileInd
	 * + ", updateDeviceTs=" + updateDeviceTs + ", fileAgencyId=" + fileAgencyId +
	 * ", IAGTagStatus=" + IAGTagStatus + "]"; }
	 */
	
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
	
	
	public String getTagHomeAgency() {
		return tagHomeAgency;
	}


	public void setTagHomeAgency(String tagHomeAgency) {
		this.tagHomeAgency = tagHomeAgency;
	}


	public String getTagAccountNo() {
		return tagAccountNo;
	}


	public void setTagAccountNo(String tagAccountNo) {
		this.tagAccountNo = tagAccountNo;
	}


	public String getTagACTypeIND() {
		return tagACTypeIND;
	}


	public void setTagACTypeIND(String tagACTypeIND) {
		this.tagACTypeIND = tagACTypeIND;
	}


	public String getTagProtocol() {
		return tagProtocol;
	}


	public void setTagProtocol(String tagProtocol) {
		this.tagProtocol = tagProtocol;
	}


	public String getTagType() {
		return tagType;
	}


	public void setTagType(String tagType) {
		this.tagType = tagType;
	}


	public String getTagMount() {
		return tagMount;
	}


	public void setTagMount(String tagMount) {
		this.tagMount = tagMount;
	}


	public String getTagClass() {
		return tagClass;
	}


	public void setTagClass(String tagClass) {
		this.tagClass = tagClass;
	}


	public Long getXferControlID() {
		return xferControlID;
	}


	public void setXferControlID(Long xferControlID) {
		this.xferControlID = xferControlID;
	}

	public String getTagAcctInfo() {
		return tagAcctInfo;
	}


	public void setTagAcctInfo(String tagAcctInfo) {
		this.tagAcctInfo = tagAcctInfo;
	}
	

	public String getRecord() {
		return record;
	}


	public void setRecord(String record) {
		this.record = record;
	}

}
