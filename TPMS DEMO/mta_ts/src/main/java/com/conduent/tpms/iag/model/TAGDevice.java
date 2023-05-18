package com.conduent.tpms.iag.model;

import java.io.Serializable;

/**
*
* Model class for ITAG properties received from DB
* 
* @author Urvashic
*
*/
public class TAGDevice  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	int agencyId;
	String deviceNo;
	String etcAccountId;
	String devicePrefix;
	int itagTagStatus;
	int deviceStatus;
	int itagInfo;
	String isRevPlanCount;
	String tagAccntInfo;
	boolean isRecordAddedToFile = false;
	
	//Nysta tag fields
	int nystaTagStatus;
	int nysbaTagStatus;
	int mtaTagStatus;
	int accountType;
	int mtagPlanStr;
	long serialNo;
	String fromPlaza;
	String toPlaza;
	String thwyAcct;
	int financialStatus;
	int iagCodedClass;

	//TS
	String tsCtrlStr;
	String mtaCtrlStr;
	String rioCtrlStr;
	
	
	

	String tagClass;
	String tagType;
	String tagHomeAgency;
	String tagProtocol;
	String tagMount;
	String AccountNo;
	String accountTypeCd;
	boolean isaway;
	
	
	
	//int tagOwningAgency;
	
	public boolean isIsaway() {
		return isaway;
	}
	public void setIsaway(boolean isaway) {
		this.isaway = isaway;
	}

	Long tagOwningAgency;
	
	
	public String getRioCtrlStr() {
		return rioCtrlStr;
	}
	public void setRioCtrlStr(String rioCtrlStr) {
		this.rioCtrlStr = rioCtrlStr;
	}
	
	public Long getTagOwningAgency() {
		return tagOwningAgency;
	}
	public void setTagOwningAgency(Long tagOwningAgency) {
		this.tagOwningAgency = tagOwningAgency;
	}
	public String getAccountTypeCd() {
		return accountTypeCd;
	}
	public void setAccountTypeCd(String accountTypeCd) {
		this.accountTypeCd = accountTypeCd;
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
	
	public String getTagHomeAgency() {
		return tagHomeAgency;
	}
	public void setTagHomeAgency(String tagHomeAgency) {
		this.tagHomeAgency = tagHomeAgency;
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
	
	
	public String getAccountNo() {
		return AccountNo;
	}
	public void setAccountNo(String tagAccountNo) {
		this.AccountNo = tagAccountNo;
	}

	
	
	
	
	
	public String getTsCtrlStr() {
		return tsCtrlStr;
	}
	public void setTsCtrlStr(String tsCtrlStr) {
		this.tsCtrlStr = tsCtrlStr;
	}
	public int getIagCodedClass() {
		return iagCodedClass;
	}
	public void setIagCodedClass(int iagCodedClass) {
		this.iagCodedClass = iagCodedClass;
	}
	public int getFinancialStatus() {
		return financialStatus;
	}
	public void setFinancialStatus(int financialStatus) {
		this.financialStatus = financialStatus;
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
	public int getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(int deviceStatus) {
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
	public int getNystaTagStatus() {
		return nystaTagStatus;
	}
	public void setNystaTagStatus(int nystaTagStatus) {
		this.nystaTagStatus = nystaTagStatus;
	}
	public int getNysbaTagStatus() {
		return nysbaTagStatus;
	}
	public void setNysbaTagStatus(int nysbaTagStatus) {
		this.nysbaTagStatus = nysbaTagStatus;
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
	public int getMtagPlanStr() {
		return mtagPlanStr;
	}
	public void setMtagPlanStr(int mtagPlanStr) {
		this.mtagPlanStr = mtagPlanStr;
	}
	public long getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(long serialNo) {
		this.serialNo = serialNo;
	}
	public String getFromPlaza() {
		return fromPlaza;
	}
	public void setFromPlaza(String fromPlaza) {
		this.fromPlaza = fromPlaza;
	}
	public String getToPlaza() {
		return toPlaza;
	}
	public void setToPlaza(String toPlaza) {
		this.toPlaza = toPlaza;
	}
	public String getThwyAcct() {
		return thwyAcct;
	}
	public void setThwyAcct(String thwyAcct) {
		this.thwyAcct = thwyAcct;
	}

	/*
	 * public int getTagOwningAgency() { return tagOwningAgency; } public void
	 * setTagOwningAgency(int tagOwningAgency) { this.tagOwningAgency =
	 * tagOwningAgency; }
	 */
	public String getMtaCtrlStr() {
		return mtaCtrlStr;
	}
	public void setMtaCtrlStr(String mtaCtrlStr) {
		this.mtaCtrlStr = mtaCtrlStr;
	}
	
	@Override
	public String toString() {
		return "TAGDevice [agencyId=" + agencyId + ", deviceNo=" + deviceNo + ", etcAccountId=" + etcAccountId
				+ ", devicePrefix=" + devicePrefix + ", itagTagStatus=" + itagTagStatus + ", deviceStatus="
				+ deviceStatus + ", itagInfo=" + itagInfo + ", isRevPlanCount=" + isRevPlanCount + ", tagAccntInfo="
				+ tagAccntInfo + ", isRecordAddedToFile=" + isRecordAddedToFile + ", nystaTagStatus=" + nystaTagStatus
				+ ", nysbaTagStatus=" + nysbaTagStatus + ", mtaTagStatus=" + mtaTagStatus + ", accountType="
				+ accountType + ", mtagPlanStr=" + mtagPlanStr + ", serialNo=" + serialNo + ", fromPlaza=" + fromPlaza
				+ ", toPlaza=" + toPlaza + ", thwyAcct=" + thwyAcct + ", financialStatus=" + financialStatus
				+ ", iagCodedClass=" + iagCodedClass + ", tsCtrlStr=" + tsCtrlStr + ", mtaCtrlStr=" + mtaCtrlStr
				+ ", tagOwningAgency=" + tagOwningAgency 
				+  ", tagClass=" + tagClass + 
				  ", tagType=" + tagType + 
				"]";
	}
	
}
