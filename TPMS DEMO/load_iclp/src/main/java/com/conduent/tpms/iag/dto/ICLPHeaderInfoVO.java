package com.conduent.tpms.iag.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.conduent.tpms.iag.validation.BaseVO;

/**
 * 
 * @author Urvashi.chouhan
 *
 */
public class ICLPHeaderInfoVO extends BaseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4489240691078090844L;
	private String fileType;
	private String version;
	private LocalDateTime filedatetime;
	private String fromAgencyId;
	private LocalDate fileDate;
	private String fileTime;
	private String recordCount;

	
	public String getFromAgencyId() {
		return fromAgencyId;
	}

	
	public void setFromAgencyId(String fromAgencyId) {
		this.fromAgencyId = fromAgencyId;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public LocalDate getFileDate() {
		return fileDate;
	}

	public void setFileDate(LocalDate fileDate) {
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public LocalDateTime getFiledatetime() {
		return filedatetime;
	}

	public void setFiledatetime(LocalDateTime filedatetime) {
		this.filedatetime = filedatetime;
	}

	
}
