package com.conduent.tpms.qatp.dto;

import java.io.Serializable;


import com.conduent.tpms.qatp.validation.BaseVO;

public class PbaFileNameDetailVO extends BaseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8448937760682414500L;
	private String fromEntity;
	private String toEntity;
	private String fileDate;
	private String fileTime;
	private String fileDateTime;
	private String fileType;

	public String getFromEntity() {
		return fromEntity;
	}
	public void setFromEntity(String fromEntity) {
		this.fromEntity = fromEntity;
	}
	public String getToEntity() {
		return toEntity;
	}
	public void setToEntity(String toEntity) {
		this.toEntity = toEntity;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
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
	public String getFileDateTime() {
		return fileDateTime;
	}
	public void setFileDateTime(String fileDateTime) {
		this.fileDateTime = fileDateTime;
	}
	
	
	
}
