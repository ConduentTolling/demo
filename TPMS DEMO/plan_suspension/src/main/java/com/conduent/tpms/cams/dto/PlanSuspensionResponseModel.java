package com.conduent.tpms.cams.dto;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PlanSuspensionResponseModel {
	
	private Long etcAccountId;
	private Long apdId;
	@DateTimeFormat(iso = ISO.DATE)
	@JsonFormat(pattern = "dd-MMM-yy")
	private String startDate;
	private String endDate;
	private Long suspensionStatus;
	private String updateTS;
	private String createDate;
	private String cscLookupKey;
    private String userId;
    
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCscLookupKey() {
		return cscLookupKey;
	}

	public void setCscLookupKey(String cscLookupKey) {
		this.cscLookupKey = cscLookupKey;
	}

	public Long getEtcAccountId() {
		return etcAccountId;
	}

	public void setEtcAccountId(Long etcAccountId) {
		this.etcAccountId = etcAccountId;
	}
	
	public Long getApdId() {
		return apdId;
	}

	public void setApdId(Long apdId) {
		this.apdId = apdId;
	}	

	

	
	public Long getSuspensionStatus() {
		return suspensionStatus;
	}

	public void setSuspensionStatus(Long suspensionStatus) {
		this.suspensionStatus = suspensionStatus;
	}

	

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getUpdateTS() {
		return updateTS;
	}

	public void setUpdateTS(String updateTS) {
		this.updateTS = updateTS;
	}

	@Override
	public String toString() {
		return "PlanSuspensionResponseModel [etcAccountId=" + etcAccountId + ", apdId=" + apdId + ", startDate="
				+ startDate + ", endDate=" + endDate + ", suspensionStatus=" + suspensionStatus + ", updateTS="
				+ updateTS + ", createDate=" + createDate + ", cscLookupKey=" + cscLookupKey + ", userId=" + userId
				+ "]";
	}

	
	
}
