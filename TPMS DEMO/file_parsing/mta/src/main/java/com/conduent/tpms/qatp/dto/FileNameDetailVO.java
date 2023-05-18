package com.conduent.tpms.qatp.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.conduent.tpms.qatp.validation.BaseVO;

public class FileNameDetailVO extends BaseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3724514409417122576L;

	private String fromAgencyId;
	private String fileName;
	private String fileType;
	private LocalDateTime fileDateTime;
	
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public LocalDateTime getFileDateTime() {
		return fileDateTime;
	}
	public void setFileDateTime(LocalDateTime fileDateTime) {
		this.fileDateTime = fileDateTime;
	}
	public String getFromAgencyId() {
		return fromAgencyId;
	}
	public void setFromAgencyId(String fromAgencyId) {
		this.fromAgencyId = fromAgencyId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Override
	public String toString() {
		return "FileNameDetailDto [fromAgencyId=" + fromAgencyId + ", fileName=" + fileName + ", fileDateTime="
				+ fileDateTime + "]";
	}
	
}
