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
	
	private LocalDateTime lastFileTs;
	
	private String infileInd;
	
	private LocalDateTime updateDeviceTs;
	
	private String fileAgencyId;
	
	private String IagTagStatus;

	private String tagHomeAgency;
	
	private String tagAcctInfo;
	

	private String tagAccountNo;
	
	private String tagAcTypeInd;
	
	private String tagProtocol;
	
	private String tagType;
	
	private String tagMount;
	
	private String tagClass;
	
	private Long xferControlID;
	private String record;

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
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

	public LocalDateTime getLastFileTs() {
		return lastFileTs;
	}

	public void setLastFileTs(LocalDateTime lastFileTs) {
		this.lastFileTs = lastFileTs;
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

	public String getIagTagStatus() {
		return IagTagStatus;
	}

	public void setIagTagStatus(String iagTagStatus) {
		IagTagStatus = iagTagStatus;
	}

	public String getTagHomeAgency() {
		return tagHomeAgency;
	}

	public void setTagHomeAgency(String tagHomeAgency) {
		this.tagHomeAgency = tagHomeAgency;
	}

	public String getTagAcctInfo() {
		return tagAcctInfo;
	}

	public void setTagAcctInfo(String tagAcctInfo) {
		this.tagAcctInfo = tagAcctInfo;
	}

	public String getTagAccountNo() {
		return tagAccountNo;
	}

	public void setTagAccountNo(String tagAccountNo) {
		this.tagAccountNo = tagAccountNo;
	}

	

	public String getTagAcTypeInd() {
		return tagAcTypeInd;
	}

	public void setTagAcTypeInd(String tagAcTypeInd) {
		this.tagAcTypeInd = tagAcTypeInd;
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

	@Override
	public String toString() {
		return "TagDeviceDetails [StartDate=" + StartDate + ", deviceNo=" + deviceNo + ", endDate=" + endDate
				+ ", invalidAddressId=" + invalidAddressId + ", lastFileTs=" + lastFileTs + ", infileInd=" + infileInd
				+ ", updateDeviceTs=" + updateDeviceTs + ", fileAgencyId=" + fileAgencyId + ", IagTagStatus="
				+ IagTagStatus + ", tagHomeAgency=" + tagHomeAgency + ", tagAcctInfo=" + tagAcctInfo + ", tagAccountNo="
				+ tagAccountNo + ", tagAcTypeInd=" + tagAcTypeInd + ", tagProtocol=" + tagProtocol + ", tagType="
				+ tagType + ", tagMount=" + tagMount + ", tagClass=" + tagClass + ", xferControlID=" + xferControlID
				+ "]";
	}

	
}
