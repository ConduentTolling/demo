package com.conduent.tpms.iag.dto;

public class ITAGICLPHeaderDto {

	
	String fileType;
	String agencyId;    
	String recordCount;
	//String fileDate;   
	//String fileTime;
	String fileDateTime;
	
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}
	public String getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(String recordCount) {
		this.recordCount = recordCount;
	}
	public String getFileDateTime() {
		return fileDateTime;
	}
	public void setFileDateTime(String fileDateTime) {
		this.fileDateTime = fileDateTime;
	}
	
	@Override
	public String toString() {
		return "ITAGICLPHeaderDto [fileType=" + fileType + ", agencyId=" + agencyId + ", recordCount=" + recordCount
				+ ", fileDateTime=" + fileDateTime + "]";
	}
	
}
