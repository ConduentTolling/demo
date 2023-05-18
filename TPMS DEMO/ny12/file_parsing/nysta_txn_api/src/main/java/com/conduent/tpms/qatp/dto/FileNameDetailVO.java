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
	private String fileDate;
	private String fileTime;
	
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
	
	
}
