package com.conduent.tpms.iag.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.conduent.tpms.iag.validation.BaseVO;

/**
 * 
 * @author Urvashi.Chouhan
 *
 */
public class IITCHeaderInfoVO extends BaseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4489240691078090844L;
	private String fileType;
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

	@Override
	public String toString() {
		return "IITCHeaderInfoVO [fileType=" + fileType + ", fromAgencyId=" + fromAgencyId + ", fileDate=" + fileDate
				+ ", fileTime=" + fileTime + ", recordCount=" + recordCount + "]";
	}

	
	
}
