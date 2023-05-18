package com.conduent.tpms.iag.dto;

public class FileInfoDto {

	String fileDescription;
	String fileFormat;  
	String fileType;  
	String parentDirectory;
	String processedDirectory;
	int agencyId;
	
	public String getFileDescription() {
		return fileDescription;
	}
	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}
	public String getFileFormat() {
		return fileFormat;
	}
	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getParentDirectory() {
		return parentDirectory;
	}
	public void setParentDirectory(String parentDirectory) {
		this.parentDirectory = parentDirectory;
	}
	public String getProcessedDirectory() {
		return processedDirectory;
	}
	public void setProcessedDirectory(String processedDirectory) {
		this.processedDirectory = processedDirectory;
	}
	public int getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}
	@Override
	public String toString() {
		return "FileInfoDto [fileDescription=" + fileDescription + ", fileFormat=" + fileFormat + ", fileType="
				+ fileType + ", parentDirectory=" + parentDirectory + ", processedDirectory=" + processedDirectory
				+ ", agencyId=" + agencyId + "]";
	}
	
	
}
