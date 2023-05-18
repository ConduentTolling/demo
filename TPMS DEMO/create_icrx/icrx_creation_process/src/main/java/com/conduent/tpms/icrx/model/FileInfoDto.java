package com.conduent.tpms.icrx.model;

/**
 * 
 * @author urvashic
 *
 */
public class FileInfoDto {

	private String fileDescription;
	private String fileFormat;
	private String fileType;
	private String parentDirectory;
	private String processedDirectory;

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

	@Override
	public String toString() {
		return "FileInfoDto [fileDescription=" + fileDescription + ", fileFormat=" + fileFormat + ", fileType="
				+ fileType + ", parentDirectory=" + parentDirectory + ", processedDirectory=" + processedDirectory
				+ "]";
	}

}
