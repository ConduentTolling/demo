package com.conduent.tpms.iag.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.conduent.tpms.iag.validation.BaseVO;

public class FileNameDetailVO extends BaseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3724514409417122576L;

	private String fromAgencyId;
	private String toAgencyId;
	private String fileName;
	private String fileType;
	private LocalDateTime fileDateTime;
	private String underscore;
	
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public LocalDateTime getFileDateTime() {
		return fileDateTime;	
	}
	public String getToAgencyId() {
		return toAgencyId;
	}
	public void setToAgencyId(String toAgencyId) {
		this.toAgencyId = toAgencyId;
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
	
	public String getUnderscore() {
		return underscore;
	}
	public void setUnderscore(String underscore) {
		this.underscore = underscore;
	}
	@Override
	public String toString() {
		return "FileNameDetailVO [fromAgencyId=" + fromAgencyId + ", toAgencyId=" + toAgencyId + ", fileName="
				+ fileName + ", fileType=" + fileType + ", fileDateTime=" + fileDateTime + ", underscore=" + underscore
				+ "]";
	}
	
	
	
	
}
