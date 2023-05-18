package com.conduent.tpms.iag.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.relational.core.mapping.Column;

public class AgencyInfoVO implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private Integer agencyId;
	private String agencyName;
	private String agencyShortName;
	private String consortium;
	private String devicePrefix;
	private String isHomeAgency;
	private String scheduledPricingFlag;
	private String clmmAdjAllowed;
	private String currentIagVersion;
	private String isIagStyleReciprocity;
	private String calculateTollAmount;
	private String filePrefix;
	private Integer parentAgencyId;
	private LocalDateTime updateTs;
	private String stmtDescription;
	private Integer stmtDelivery;
	private Integer stmtFrequency;
	private Integer stmtDefaultDuration;
	private String appendImages;
	private String invoiceEnavled;
	private String fileProcessingStatus = "N";
	
	
	public String getFileProcessingStatus() {
		return fileProcessingStatus;
	}
	public void setFileProcessingStatus(String fileProcessingStatus) {
		this.fileProcessingStatus = fileProcessingStatus;
	}
	public Integer getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public String getAgencyShortName() {
		return agencyShortName;
	}
	public void setAgencyShortName(String agencyShortName) {
		this.agencyShortName = agencyShortName;
	}
	public String getConsortium() {
		return consortium;
	}
	public void setConsortium(String consortium) {
		this.consortium = consortium;
	}
	public String getDevicePrefix() {
		return devicePrefix;
	}
	public void setDevicePrefix(String devicePrefix) {
		this.devicePrefix = devicePrefix;
	}
	public String getIsHomeAgency() {
		return isHomeAgency;
	}
	public void setIsHomeAgency(String isHomeAgency) {
		this.isHomeAgency = isHomeAgency;
	}
	public String getScheduledPricingFlag() {
		return scheduledPricingFlag;
	}
	public void setScheduledPricingFlag(String scheduledPricingFlag) {
		this.scheduledPricingFlag = scheduledPricingFlag;
	}
	public String getClmmAdjAllowed() {
		return clmmAdjAllowed;
	}
	public void setClmmAdjAllowed(String clmmAdjAllowed) {
		this.clmmAdjAllowed = clmmAdjAllowed;
	}
	public String getCurrentIagVersion() {
		return currentIagVersion;
	}
	public void setCurrentIagVersion(String currentIagVersion) {
		this.currentIagVersion = currentIagVersion;
	}
	public String getIsIagStyleReciprocity() {
		return isIagStyleReciprocity;
	}
	public void setIsIagStyleReciprocity(String isIagStyleReciprocity) {
		this.isIagStyleReciprocity = isIagStyleReciprocity;
	}
	public String getCalculateTollAmount() {
		return calculateTollAmount;
	}
	public void setCalculateTollAmount(String calculateTollAmount) {
		this.calculateTollAmount = calculateTollAmount;
	}
	public String getFilePrefix() {
		return filePrefix;
	}
	public void setFilePrefix(String filePrefix) {
		this.filePrefix = filePrefix;
	}
	public Integer getParentAgencyId() {
		return parentAgencyId;
	}
	public void setParentAgencyId(Integer parentAgencyId) {
		this.parentAgencyId = parentAgencyId;
	}
	public LocalDateTime getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(LocalDateTime updateTs) {
		this.updateTs = updateTs;
	}
	public String getStmtDescription() {
		return stmtDescription;
	}
	public void setStmtDescription(String stmtDescription) {
		this.stmtDescription = stmtDescription;
	}
	public Integer getStmtDelivery() {
		return stmtDelivery;
	}
	public void setStmtDelivery(Integer stmtDelivery) {
		this.stmtDelivery = stmtDelivery;
	}
	public Integer getStmtFrequency() {
		return stmtFrequency;
	}
	public void setStmtFrequency(Integer stmtFrequency) {
		this.stmtFrequency = stmtFrequency;
	}
	public Integer getStmtDefaultDuration() {
		return stmtDefaultDuration;
	}
	public void setStmtDefaultDuration(Integer stmtDefaultDuration) {
		this.stmtDefaultDuration = stmtDefaultDuration;
	}
	public String getAppendImages() {
		return appendImages;
	}
	public void setAppendImages(String appendImages) {
		this.appendImages = appendImages;
	}
	public String getInvoiceEnavled() {
		return invoiceEnavled;
	}
	public void setInvoiceEnavled(String invoiceEnavled) {
		this.invoiceEnavled = invoiceEnavled;
	}
	
	@Override
	public String toString() {
		return "AgencyInfoVO [agencyId=" + agencyId + ", agencyName=" + agencyName + ", agencyShortName="
				+ agencyShortName + ", consortium=" + consortium + ", devicePrefix=" + devicePrefix + ", isHomeAgency="
				+ isHomeAgency + ", scheduledPricingFlag=" + scheduledPricingFlag + ", clmmAdjAllowed=" + clmmAdjAllowed
				+ ", currentIagVersion=" + currentIagVersion + ", isIagStyleReciprocity=" + isIagStyleReciprocity
				+ ", calculateTollAmount=" + calculateTollAmount + ", filePrefix=" + filePrefix + ", parentAgencyId="
				+ parentAgencyId + ", updateTs=" + updateTs + ", stmtDescription=" + stmtDescription + ", stmtDelivery="
				+ stmtDelivery + ", stmtFrequency=" + stmtFrequency + ", stmtDefaultDuration=" + stmtDefaultDuration
				+ ", appendImages=" + appendImages + ", invoiceEnavled=" + invoiceEnavled + ", fileProcessingStatus="
				+ fileProcessingStatus + "]";
	}
	
}
