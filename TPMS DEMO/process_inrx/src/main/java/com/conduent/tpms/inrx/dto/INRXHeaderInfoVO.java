package com.conduent.tpms.inrx.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.conduent.tpms.inrx.validation.BaseVO;

/**
 * 
 * @author Urvashi.Chouhan
 *
 */
public class INRXHeaderInfoVO extends BaseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4489240691078090844L;
	private String fileType;
	private String homeAgencyId;
	private String hostAgencyId;
	private LocalDate fileDate;
	private String fileTime;
	private String recordCount;
	private String intxFileNum;
	private String itxnFileNum;
	
	
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFromAgencyId() {
		return homeAgencyId;
	}
	public void setFromAgencyId(String fromAgencyId) {
		this.homeAgencyId = fromAgencyId;
	}
	public String getToAgencyId() {
		return hostAgencyId;
	}
	public void setToAgencyId(String toAgencyId) {
		this.hostAgencyId = toAgencyId;
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
	public String getIntxFileNum() {
		return intxFileNum;
	}
	public void setIntxFileNum(String intxFileNum) {
		this.intxFileNum = intxFileNum;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getItxcFileNum() {
		return itxnFileNum;
	}
	public void setItxnFileNum(String itxnFileNum) {
		this.itxnFileNum = itxnFileNum;
	}
	
	
}