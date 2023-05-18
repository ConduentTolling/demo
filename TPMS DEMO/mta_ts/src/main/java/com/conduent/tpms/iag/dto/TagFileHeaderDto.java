package com.conduent.tpms.iag.dto;

public class TagFileHeaderDto {

	String recordTypeCode;
	String recordCount;
	String fileDateTime;
	String fileSerial;
	String goodbalTagCount;
	String lowbalTagCount;
	String zerobalTagCount;
	String lostTagCount;
	String goodbalTagNysbaCount;
	String lowbalTagNysbaCount;
	String zerobalTagNysbaCount;
	String lostTagNysbaCount;
	
	public String getRecordTypeCode() {
		return recordTypeCode;
	}
	public void setRecordTypeCode(String recordTypeCode) {
		this.recordTypeCode = recordTypeCode;
	}
	public String getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(String recordCount) {
		this.recordCount = recordCount;
	}
	public String getFileDateTime() {
		return fileDateTime;
	}
	public void setFileDateTime(String fileDateTime) {
		this.fileDateTime = fileDateTime;
	}
	public String getFileSerial() {
		return fileSerial;
	}
	public void setFileSerial(String fileSerial) {
		this.fileSerial = fileSerial;
	}
	public String getGoodbalTagCount() {
		return goodbalTagCount;
	}
	public void setGoodbalTagCount(String goodbalTagCount) {
		this.goodbalTagCount = goodbalTagCount;
	}
	public String getLowbalTagCount() {
		return lowbalTagCount;
	}
	public void setLowbalTagCount(String lowbalTagCount) {
		this.lowbalTagCount = lowbalTagCount;
	}
	public String getZerobalTagCount() {
		return zerobalTagCount;
	}
	public void setZerobalTagCount(String zerobalTagCount) {
		this.zerobalTagCount = zerobalTagCount;
	}
	public String getLostTagCount() {
		return lostTagCount;
	}
	public void setLostTagCount(String lostTagCount) {
		this.lostTagCount = lostTagCount;
	}
	public String getGoodbalTagNysbaCount() {
		return goodbalTagNysbaCount;
	}
	public void setGoodbalTagNysbaCount(String goodbalTagNysbaCount) {
		this.goodbalTagNysbaCount = goodbalTagNysbaCount;
	}
	public String getLowbalTagNysbaCount() {
		return lowbalTagNysbaCount;
	}
	public void setLowbalTagNysbaCount(String lowbalTagNysbaCount) {
		this.lowbalTagNysbaCount = lowbalTagNysbaCount;
	}
	public String getZerobalTagNysbaCount() {
		return zerobalTagNysbaCount;
	}
	public void setZerobalTagNysbaCount(String zerobalTagNysbaCount) {
		this.zerobalTagNysbaCount = zerobalTagNysbaCount;
	}
	public String getLostTagNysbaCount() {
		return lostTagNysbaCount;
	}
	public void setLostTagNysbaCount(String lostTagNysbaCount) {
		this.lostTagNysbaCount = lostTagNysbaCount;
	}
	
	@Override
	public String toString() {
		return "MtagFileHeaderDto [recordTypeCode=" + recordTypeCode + ", recordCount=" + recordCount
				+ ", fileDateTime=" + fileDateTime + ", fileSerial=" + fileSerial + ", goodbalTagCount="
				+ goodbalTagCount + ", lowbalTagCount=" + lowbalTagCount + ", zerobalTagCount=" + zerobalTagCount
				+ ", lostTagCount=" + lostTagCount + ", goodbalTagNysbaCount=" + goodbalTagNysbaCount
				+ ", lowbalTagNysbaCount=" + lowbalTagNysbaCount + ", zerobalTagNysbaCount=" + zerobalTagNysbaCount
				+ ", lostTagNysbaCount=" + lostTagNysbaCount + "]";
	}

	
	
}
