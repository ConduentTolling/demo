package com.conduent.tpms.qatp.dto;

import java.io.Serializable;

import com.conduent.tpms.qatp.validation.BaseVO;

public class PaHeaderInfoVO extends BaseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 215881096166965312L;
	private String fileType;
	private String fromEntity;
	private String toEntity;
	private String fileDate;
	private String fileTime;
	private String recordCount;
	private String atrnFileNum;

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

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

	public String getAtrnFileNum() {
		return atrnFileNum;
	}

	public void setAtrnFileNum(String atrnFileNum) {
		this.atrnFileNum = atrnFileNum;
	}

	

}
