package com.conduent.tpms.iag.dto;

/**
 * 
 * POJO class for ITAG file detail record structure
 * 
 * @author urvashic
 *
 */
public class FileDetailRecordDto {
	
	String tagAgencyId; 
	String tagSerialNumber; 
	String tagStatus; 
	String tagAcctInfo;  
	String delimiter;
	
	public String getTagAgencyId() {
		return tagAgencyId;
	}
	public void setTagAgencyId(String tagAgencyId) {
		this.tagAgencyId = tagAgencyId;
	}
	public String getTagSerialNumber() {
		return tagSerialNumber;
	}
	public void setTagSerialNumber(String tagSerialNumber) {
		this.tagSerialNumber = tagSerialNumber;
	}
	public String getTagStatus() {
		return tagStatus;
	}
	public void setTagStatus(String tagStatus) {
		this.tagStatus = tagStatus;
	}
	public String getTagAcctInfo() {
		return tagAcctInfo;
	}
	public void setTagAcctInfo(String tagAcctInfo) {
		this.tagAcctInfo = tagAcctInfo;
	}
	public String getDelimiter() {
		return delimiter;
	}
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
	
	@Override
	public String toString() {
		return "FileDetailRecordDto [tagAgencyId=" + tagAgencyId + ", tagSerialNumber=" + tagSerialNumber
				+ ", tagStatus=" + tagStatus + ", tagAcctInfo=" + tagAcctInfo + ", delimiter=" + delimiter + "]";
	}
	
}
