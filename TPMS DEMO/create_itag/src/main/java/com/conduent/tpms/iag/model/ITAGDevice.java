package com.conduent.tpms.iag.model;

import java.io.Serializable;

/**
*
* Model class for ITAG properties received from DB
* 
* @author Urvashic
*
*/
public class ITAGDevice  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	int agencyId;
	String deviceNo;
	String etcAccountId;
	String devicePrefix;
	int itagTagStatus;
	String deviceStatus;
	int itagInfo;
	String isRevPlanCount;
	String tagAccntInfo;
	boolean isRecordAddedToFile = false;
	Long tagOwningAgency ;
	

	public Long getTagOwningAgency() {
		return tagOwningAgency;
	}
	public void setTagOwningAgency(Long tagOwningAgency) {
		this.tagOwningAgency = tagOwningAgency;
	}
	//Nysta tag fields
	int nystatagStatus;
	int nysbatagStatus;
	int mtaTagStatus;
	int accountType;
	String mtagPlanStr;
	String serialNo;
	String fromPlaza;
	String tolaza;
	String thwyAcct;
	
	String tagClass;
	String tagType;
	String tagProtocol;
	String tagMount;
	
	String accountNo;
	
	
	String accountTypeCd;
	
	public String getAccountTypeCd() {
		return accountTypeCd;
	}
	public void setAccountTypeCd(String accountTypeCd) {
		this.accountTypeCd = accountTypeCd;
	}
	
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getTagClass() {
		return tagClass;
	}
	public void setTagClass(String tagClass) {
		this.tagClass = tagClass;
	}
	public String getTagType() {
		return tagType;
	}
	public void setTagType(String tagType) {
		this.tagType = tagType;
	}
	public String getTagProtocol() {
		return tagProtocol;
	}
	public void setTagProtocol(String tagProtocol) {
		this.tagProtocol = tagProtocol;
	}
	public String getTagMount() {
		return tagMount;
	}
	public void setTagMount(String tagMount) {
		this.tagMount = tagMount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getTagAccntInfo() {
		return tagAccntInfo;
	}
	public void setTagAccntInfo(String tagAccntInfo) {
		this.tagAccntInfo = tagAccntInfo;
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
	public int getItagTagStatus() {
		return itagTagStatus;
	}
	public void setItagTagStatus(int itagTagStatus) {
		this.itagTagStatus = itagTagStatus;
	}
	public String getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	public int getItagInfo() {
		return itagInfo;
	}
	public void setItagInfo(int itagInfo) {
		this.itagInfo = itagInfo;
	}
	public String getIsRevPlanCount() {
		return isRevPlanCount;
	}
	public void setIsRevPlanCount(String isRevPlanCount) {
		this.isRevPlanCount = isRevPlanCount;
	}
	
	//Nysta fields
	
	public boolean isRecordAddedToFile() {
		return isRecordAddedToFile;
	}
	public void setRecordAddedToFile(boolean isRecordAddedToFile) {
		this.isRecordAddedToFile = isRecordAddedToFile;
	}
	public int getNystatagStatus() {
		return nystatagStatus;
	}
	public void setNystatagStatus(int nystatagStatus) {
		this.nystatagStatus = nystatagStatus;
	}
	public int getNysbatagStatus() {
		return nysbatagStatus;
	}
	public void setNysbatagStatus(int nysbatagStatus) {
		this.nysbatagStatus = nysbatagStatus;
	}
	public int getMtaTagStatus() {
		return mtaTagStatus;
	}
	public void setMtaTagStatus(int mtaTagStatus) {
		this.mtaTagStatus = mtaTagStatus;
	}
	public int getAccountType() {
		return accountType;
	}
	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}
	public String getMtagPlanStr() {
		return mtagPlanStr;
	}
	public void setMtagPlanStr(String mtagPlanStr) {
		this.mtagPlanStr = mtagPlanStr;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getFromPlaza() {
		return fromPlaza;
	}
	public void setFromPlaza(String fromPlaza) {
		this.fromPlaza = fromPlaza;
	}
	public String getTolaza() {
		return tolaza;
	}
	public void setTolaza(String tolaza) {
		this.tolaza = tolaza;
	}
	public String getThwyAcct() {
		return thwyAcct;
	}
	public void setThwyAcct(String thwyAcct) {
		this.thwyAcct = thwyAcct;
	}
	@Override
	public String toString() {
		return "ITAGDevice [agencyId=" + agencyId + ", deviceNo=" + deviceNo + ", etcAccountId=" + etcAccountId
				+ ", devicePrefix=" + devicePrefix + ", itagTagStatus=" + itagTagStatus + ", deviceStatus="
				+ deviceStatus + ", itagInfo=" + itagInfo + ", isRevPlanCount=" + isRevPlanCount + ", tagAccntInfo="
				+ tagAccntInfo + ", isRecordAddedToFile=" + isRecordAddedToFile + ", nystatagStatus=" + nystatagStatus
				+ ", nysbatagStatus=" + nysbatagStatus + ", mtaTagStatus=" + mtaTagStatus + ", accountType="
				+ accountType + ", mtagPlanStr=" + mtagPlanStr + ", serialNo=" + serialNo + "]";
	}
	
	
}
