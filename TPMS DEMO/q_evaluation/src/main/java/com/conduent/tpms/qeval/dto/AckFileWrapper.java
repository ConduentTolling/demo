package com.conduent.tpms.qeval.dto;

import java.io.File;

public class AckFileWrapper {
	
	private AckFileInfoDto ackFileInfoDto;
	private File file;
	private String ackFileName;
	private String sbFileContent;
	private String fileDestDir;

	public AckFileInfoDto getAckFileInfoDto() {
		return ackFileInfoDto;
	}

	public void setAckFileInfoDto(AckFileInfoDto ackFileInfoDto) {
		this.ackFileInfoDto = ackFileInfoDto;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getAckFileName() {
		return ackFileName;
	}

	public void setAckFileName(String ackFileName) {
		this.ackFileName = ackFileName;
	}

	public String getSbFileContent() {
		return sbFileContent;
	}

	public void setSbFileContent(String sbFileContent) {
		this.sbFileContent = sbFileContent;
	}

	public String getFileDestDir() {
		return fileDestDir;
	}

	public void setFileDestDir(String fileDestDir) {
		this.fileDestDir = fileDestDir;
	}

}
