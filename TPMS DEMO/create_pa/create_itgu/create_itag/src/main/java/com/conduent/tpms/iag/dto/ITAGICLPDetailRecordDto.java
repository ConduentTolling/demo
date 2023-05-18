package com.conduent.tpms.iag.dto;

public class ITAGICLPDetailRecordDto {

	String tagAgencyId;
	String tagSerialNo;
	String tagStatus;      
	String subaccountId;
	
	public String getTagAgencyId() {
		return tagAgencyId;
	}
	public void setTagAgencyId(String tagAgencyId) {
		this.tagAgencyId = tagAgencyId;
	}
	public String getTagSerialNo() {
		return tagSerialNo;
	}
	public void setTagSerialNo(String tagSerialNo) {
		this.tagSerialNo = tagSerialNo;
	}
	public String getTagStatus() {
		return tagStatus;
	}
	public void setTagStatus(String tagStatus) {
		this.tagStatus = tagStatus;
	}
	public String getSubaccountId() {
		return subaccountId;
	}
	public void setSubaccountId(String subaccountId) {
		this.subaccountId = subaccountId;
	}
	
	@Override
	public String toString() {
		return "ITAGICLPDetailRecordDto [tagAgencyId=" + tagAgencyId + ", tagSerialNo=" + tagSerialNo + ", tagStatus="
				+ tagStatus + ", subaccountId=" + subaccountId + "]";
	}
	
}
