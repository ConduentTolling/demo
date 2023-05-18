package com.conduent.tpms.iag.dto;

import java.io.Serializable;
import java.time.LocalDate;


/**
 * 
 * @author urvashic
 *
 */
public class ITAGHeaderInfoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4489240691078090844L;
	private String fileType;
	private String fromAgencyId;
	private LocalDate fileDate;
	private String fileTime;
	private String recordCount;
	private String validCount1;
	private String lowCount2;
	private String invalidCount3;
	private String lostCount4;
	private String zeroCount;
	
	public String getZeroCount() {
		return zeroCount;
	}

	public void setZeroCount(String zeroCount) {
		this.zeroCount = zeroCount;
	}

	private String mergeFileName;
	
	public String getMergeFileName() {
		return mergeFileName;
	}

	public void setMergeFileName(String mergeFileName) {
		this.mergeFileName = mergeFileName;
	}

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

	public String getValidCount1() {
		return validCount1;
	}

	public void setValidCount1(String validCount1) {
		this.validCount1 = validCount1;
	}

	public String getLowCount2() {
		return lowCount2;
	}

	public void setLowCount2(String lowCount2) {
		this.lowCount2 = lowCount2;
	}

	public String getInvalidCount3() {
		return invalidCount3;
	}

	public void setInvalidCount3(String invalidCount3) {
		this.invalidCount3 = invalidCount3;
	}

	public String getLostCount4() {
		return lostCount4;
	}

	public void setLostCount4(String lostCount4) {
		this.lostCount4 = lostCount4;
	}

	@Override
	public String toString() {
		return "ITAGHeaderInfoVO [fileType=" + fileType + ", fromAgencyId=" + fromAgencyId + ", fileDate=" + fileDate
				+ ", fileTime=" + fileTime + ", recordCount=" + recordCount + ", validCount1=" + validCount1
				+ ", lowCount2=" + lowCount2 + ", invalidCount3=" + invalidCount3 + ", lostCount4=" + lostCount4
				+ ", mergeFileName=" + mergeFileName + "]";
	}
}
