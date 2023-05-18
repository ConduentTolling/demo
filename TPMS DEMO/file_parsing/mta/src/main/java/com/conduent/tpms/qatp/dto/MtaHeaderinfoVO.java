package com.conduent.tpms.qatp.dto;

import java.io.Serializable;

import com.conduent.tpms.qatp.validation.BaseVO;

public class MtaHeaderinfoVO extends BaseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 215881096166965312L;
	
	
	private String recordType;
	private String serviceCenter;
	private String hubId;
	private String agencyId;
	private String lastUpload;
	private String currentTs;
	private String total;
	private String prevFileName;
	
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getServiceCenter() {
		return serviceCenter;
	}
	public void setServiceCenter(String serviceCenter) {
		this.serviceCenter = serviceCenter;
	}
	public String getHubId() {
		return hubId;
	}
	public void setHubId(String hubId) {
		this.hubId = hubId;
	}
	public String getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}
	public String getLastUpload() {
		return lastUpload;
	}
	public void setLastUpload(String lastUpload) {
		this.lastUpload = lastUpload;
	}
	public String getCurrentTs() {
		return currentTs;
	}
	public void setCurrentTs(String currentTs) {
		this.currentTs = currentTs;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getPrevFileName() {
		return prevFileName;
	}
	public void setPrevFileName(String prevFileName) {
		this.prevFileName = prevFileName;
	}

	
	

}
