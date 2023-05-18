package com.conduent.tpms.iag.dto;

/**
 * 
 * POJO class for ITAG file name structure
 * 
 * @author urvashic
 *
 */
public class FileNameDto {

	
	String fromAgencyId;
	String fileDateTime;
	
	public String getFromAgencyId() {
		return fromAgencyId;
	}
	public void setFromAgencyId(String fromAgencyId) {
		this.fromAgencyId = fromAgencyId;
	}
	public String getFileDateTime() {
		return fileDateTime;
	}
	public void setFileDateTime(String fileDateTime) {
		this.fileDateTime = fileDateTime;
	}
	
	@Override
	public String toString() {
		return "FileNameDto [fromAgencyId=" + fromAgencyId + ", fileDateTime=" + fileDateTime + "]";
	}
	
}
