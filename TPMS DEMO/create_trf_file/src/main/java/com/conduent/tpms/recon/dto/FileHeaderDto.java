package com.conduent.tpms.recon.dto;

/**
 * 
 * POJO class for ITAG file header structure
 * 
 * @author taniyan
 *
 */
public class FileHeaderDto {
	
	String fileType;
	String fromAgencyId;
	String fileDate; 
	String fileTime; 
	String recordCount; 
	String countStat1; 
	String countStat2; 
	String countStat3; 
	String countStat4; 
	String delimiter;
	String fileDateTime;
	
	
	
	public String getFileDateTime() {
		return fileDateTime;
	}
	public void setFileDateTime(String fileDateTime) {
		this.fileDateTime = fileDateTime;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFromAgencyId() {
		return fromAgencyId;
	}
	public void setFromAgencyId(String fromAgencyId) {
		this.fromAgencyId = fromAgencyId;
	}
	public String getFileDate() {
		return fileDate;
	}
	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}
	public String getFileTime() {
		return fileTime;
	}
	public void setFileTime(String fileTime) {
		this.fileTime = fileTime;
	}
	public String getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(String recordCount) {
		this.recordCount = recordCount;
	}
	public String getCountStat1() {
		return countStat1;
	}
	public void setCountStat1(String countStat1) {
		this.countStat1 = countStat1;
	}
	public String getCountStat2() {
		return countStat2;
	}
	public void setCountStat2(String countStat2) {
		this.countStat2 = countStat2;
	}
	public String getCountStat3() {
		return countStat3;
	}
	public void setCountStat3(String countStat3) {
		this.countStat3 = countStat3;
	}
	public String getCountStat4() {
		return countStat4;
	}
	public void setCountStat4(String countStat4) {
		this.countStat4 = countStat4;
	}
	public String getDelimiter() {
		return delimiter;
	}
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
	
	@Override
	public String toString() {
		return "FileHeaderDto [fileType=" + fileType + ", fromAgencyId=" + fromAgencyId + ", fileDate=" + fileDate
				+ ", fileTime=" + fileTime + ", recordCount=" + recordCount + ", countStat1=" + countStat1
				+ ", countStat2=" + countStat2 + ", countStat3=" + countStat3 + ", countStat4=" + countStat4
				+ ", delimiter=" + delimiter + ", fileDateTime=" + fileDateTime + "]";
	}
	
}
