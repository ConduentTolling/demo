package com.conduent.tpms.iag.model;

import java.io.Serializable;

public class FTAGDevice  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	int agencyId;
	String deviceNo;
	String etcAccountId;
	String devicePrefix;
	int ftagTagStatus;
	int deviceStatus;
	
	
	int ftagInfo;
	String isRevPlanCount;
	String tagAccntInfo;
	boolean isRecordAddedToFile = false;
	
	@Override
	public String toString() {
		return "FTAGDevice [agencyId=" + agencyId + ", deviceNo=" + deviceNo + ", etcAccountId=" + etcAccountId
				+ ", devicePrefix=" + devicePrefix + ", ftagTagStatus=" + ftagTagStatus + ", deviceStatus="
				+ deviceStatus + ", ftagInfo=" + ftagInfo + ", isRevPlanCount=" + isRevPlanCount + ", tagAccntInfo="
				+ tagAccntInfo + ", isRecordAddedToFile=" + isRecordAddedToFile + "]";
	}
	public int getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public String getEtcAccountId() {
		return etcAccountId;
	}
	public void setEtcAccountId(String etcAccountId) {
		this.etcAccountId = etcAccountId;
	}
	public String getDevicePrefix() {
		return devicePrefix;
	}
	public void setDevicePrefix(String devicePrefix) {
		this.devicePrefix = devicePrefix;
	}
	public int getFtagTagStatus() {
		return ftagTagStatus;
	}
	public void setFtagTagStatus(int ftagTagStatus) {
		this.ftagTagStatus = ftagTagStatus;
	}
	public int getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(int deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	public int getFtagInfo() {
		return ftagInfo;
	}
	public void setFtagInfo(int ftagInfo) {
		this.ftagInfo = ftagInfo;
	}
	public String getIsRevPlanCount() {
		return isRevPlanCount;
	}
	public void setIsRevPlanCount(String isRevPlanCount) {
		this.isRevPlanCount = isRevPlanCount;
	}
	public String getTagAccntInfo() {
		return tagAccntInfo;
	}
	public void setTagAccntInfo(String tagAccntInfo) {
		this.tagAccntInfo = tagAccntInfo;
	}
	public boolean isRecordAddedToFile() {
		return isRecordAddedToFile;
	}
	public void setRecordAddedToFile(boolean isRecordAddedToFile) {
		this.isRecordAddedToFile = isRecordAddedToFile;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	//Nysta tag fields
	/*
	 * int nystatagStatus; int nysbatagStatus; int mtaTagStatus; int accountType;
	 * String mtagPlanStr; String serialNo; String fromPlaza; String tolaza; String
	 * thwyAcct;
	 */
	
	
	

	
	
}
