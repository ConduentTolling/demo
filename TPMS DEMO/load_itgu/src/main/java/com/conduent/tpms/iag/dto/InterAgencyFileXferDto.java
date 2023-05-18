package com.conduent.tpms.iag.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class InterAgencyFileXferDto {

	String fileExtension;
	String fromDevicePrefix;
	String toDevicePrefix;
	LocalDate fileDate;
	String fileTimeString;
	long recordCount;
	String xferType;
	String processStatus;
	LocalDateTime updateTs;
	String fileName;
	
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	public String getFromDevicePrefix() {
		return fromDevicePrefix;
	}
	public void setFromDevicePrefix(String fromDevicePrefix) {
		this.fromDevicePrefix = fromDevicePrefix;
	}
	public String getToDevicePrefix() {
		return toDevicePrefix;
	}
	public void setToDevicePrefix(String toDevicePrefix) {
		this.toDevicePrefix = toDevicePrefix;
	}
	public LocalDate getFileDate() {
		return fileDate;
	}
	public void setFileDate(LocalDate fileDate) {
		this.fileDate = fileDate;
	}
	public String getFileTimeString() {
		return fileTimeString;
	}
	public void setFileTimeString(String fileTimeString) {
		this.fileTimeString = fileTimeString;
	}
	public long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}
	public String getXferType() {
		return xferType;
	}
	public void setXferType(String xferType) {
		this.xferType = xferType;
	}
	public String getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	public LocalDateTime getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(LocalDateTime updateTs) {
		this.updateTs = updateTs;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
