package com.conduent.tpms.iag.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.conduent.tpms.iag.validation.BaseVO;

/**
 * 
 * @author Urvashi.Chouhan
 *
 */
public class ICTXHeaderInfoVO extends BaseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4489240691078090844L;
	private String fileType;
	private String fromAgencyId;
	private String toAgencyId;
	private LocalDate fileDate;
	private String fileTime;
	private String recordCount;
	private String ictxFileNum;
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFromAgencyId() {
		return fromAgencyId;
	}
	public void setFromAgencyId(String fromAgencyId) {
		this.fromAgencyId = fromAgencyId;
	}
	public String getToAgencyId() {
		return toAgencyId;
	}
	public void setToAgencyId(String toAgencyId) {
		this.toAgencyId = toAgencyId;
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
	public String getIctxFileNum() {
		return ictxFileNum;
	}
	public void setIctxFileNum(String ictxFileNum) {
		this.ictxFileNum = ictxFileNum;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}