package com.conduent.tpms.iag.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 
 * POJO class for TagStatusSortedRecord table structure
 * 
 * @author urvashic
 *
 */
public class TagStatusSortedRecord implements Serializable {

	private static final long serialVersionUID = 974376802303349683L;

	int agencyId;
	String accountNo;
	long etcAccountId;
	int accountType;
	int accountStatus;
	String deviceNo;
	int financialStatus;
	int tagOwningAgency;
	String thwyAcct;
	Double currentBalance = 0.0;
	int rebillPayType = 0;
	Double rebillAmount = 0.0;
	Double rebillThreshold = 0.0;
	int postPaidStatus = 0;
	String acctSuspFlag;
	String postPaidFlag;
	LocalDate firstTollDate;
	int retailTagStatus = 0;
	int speedAgency = 0;
	int allPlans = 0;
	int revPlanCount = 0;
	int postPaidPlanCnt = 0;
	int nystaNonRevCount = 0;
	int nystaAnnualPlanCount = 0;
	int nystaPostPaidComlCount = 0;
	int nystaTagStatus = 0;
	int nysbaNonRevCount = 0;
	int mtaNonRevCount = 0;
	int mtaSpecialPlanCount = 0;
	int mtaTagStatus = 0;
	int paNonRevCount = 0;
	int paBridgesCount = 0;
	int paSiBridgesCount = 0;
	int paCarpoolCount = 0;
	int rioNonRevCount = 0;
	int plzLimitedCount = 0;
	int paTagStatus = 0;
	int rioTagStatus = 0;
	int ftagTagStatus = 0;
	int iagCodedClass = 0;
	String optInOutFlag;
	String prevalidationFlag;
	String isCompanionTag;
	Double itagInfo = 0.0;
	int itagTagStatus = 0;
	String fromPlaza;
	String toPlaza;
	String tsCtrlStr;
	String plansStr;
	int mtagPlanStr = 0;
	String mtaCtrlStr;
	String rioCtrlStr;
	String ftagPlanInfo;
	int nysbaTagStatus;
	String cscLookupKey;
	LocalDateTime updateTs;
	
	//Extra Mappings
	private int deviceStatus;
	private String retailerStatus;
	private String isPrevalidated;
	private int primaryRebillPayType;
	private int tbnrfpCount = 0;
	private int mtafCount = 0;
	private int govtCount = 0;
	private int sirCount = 0;
	private int rrCount = 0;
	private int brCount = 0;
	private int qrCount = 0;
	private int deviceTypeId=0; 
	
	public int getDeviceTypeId() {
		return deviceTypeId;
	}
	public void setDeviceTypeId(int deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}
	public int getTbnrfpCount() {
		return tbnrfpCount;
	}
	public void setTbnrfpCount(int tbnrfpCount) {
		this.tbnrfpCount = tbnrfpCount;
	}
	public int getMtafCount() {
		return mtafCount;
	}
	public void setMtafCount(int mtafCount) {
		this.mtafCount = mtafCount;
	}
	public int getGovtCount() {
		return govtCount;
	}
	public void setGovtCount(int govtCount) {
		this.govtCount = govtCount;
	}
	public int getSirCount() {
		return sirCount;
	}
	public void setSirCount(int sirCount) {
		this.sirCount = sirCount;
	}
	public int getRrCount() {
		return rrCount;
	}
	public void setRrCount(int rrCount) {
		this.rrCount = rrCount;
	}
	public int getBrCount() {
		return brCount;
	}
	public void setBrCount(int brCount) {
		this.brCount = brCount;
	}
	public int getQrCount() {
		return qrCount;
	}
	public void setQrCount(int qrCount) {
		this.qrCount = qrCount;
	}
	public int getPrimaryRebillPayType() {
		return primaryRebillPayType;
	}
	public void setPrimaryRebillPayType(int primaryRebillPayType) {
		this.primaryRebillPayType = primaryRebillPayType;
	}
	public String getIsPrevalidated() {
		return isPrevalidated;
	}
	public void setIsPrevalidated(String isPrevalidated) {
		this.isPrevalidated = isPrevalidated;
	}
	public String getRetailerStatus() {
		return retailerStatus;
	}
	public void setRetailerStatus(String retailerStatus) {
		this.retailerStatus = retailerStatus;
	}
	public int getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public long getEtcAccountId() {
		return etcAccountId;
	}
	public void setEtcAccountId(long etcAccountId) {
		this.etcAccountId = etcAccountId;
	}
	public int getAccountType() {
		return accountType;
	}
	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}
	public int getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(int accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public int getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(int deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	public int getFinancialStatus() {
		return financialStatus;
	}
	public void setFinancialStatus(int financialStatus) {
		this.financialStatus = financialStatus;
	}
	public int getTagOwningAgency() {
		return tagOwningAgency;
	}
	public void setTagOwningAgency(int tagOwningAgency) {
		this.tagOwningAgency = tagOwningAgency;
	}
	public String getThwyAcct() {
		return thwyAcct;
	}
	public void setThwyAcct(String thwyAcct) {
		this.thwyAcct = thwyAcct;
	}
	public Double getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}
	public int getRebillPayType() {
		return rebillPayType;
	}
	public void setRebillPayType(int rebillPayType) {
		this.rebillPayType = rebillPayType;
	}
	public Double getRebillAmount() {
		return rebillAmount;
	}
	public void setRebillAmount(Double rebillAmount) {
		this.rebillAmount = rebillAmount;
	}
	public Double getRebillThreshold() {
		return rebillThreshold;
	}
	public void setRebillThreshold(Double rebillThreshold) {
		this.rebillThreshold = rebillThreshold;
	}
	public int getPostPaidStatus() {
		return postPaidStatus;
	}
	public void setPostPaidStatus(int postPaidStatus) {
		this.postPaidStatus = postPaidStatus;
	}
	public String getAcctSuspFlag() {
		return acctSuspFlag;
	}
	public void setAcctSuspFlag(String acctSuspFlag) {
		this.acctSuspFlag = acctSuspFlag;
	}
	public String getPostPaidFlag() {
		return postPaidFlag;
	}
	public void setPostPaidFlag(String postPaidFlag) {
		this.postPaidFlag = postPaidFlag;
	}
	public LocalDate getFirstTollDate() {
		return firstTollDate;
	}
	public void setFirstTollDate(LocalDate firstTollDate) {
		this.firstTollDate = firstTollDate;
	}
	public int getRetailTagStatus() {
		return retailTagStatus;
	}

	public void setRetailTagStatus(int retailTagStatus) {
		this.retailTagStatus = retailTagStatus;
	}
	public int getSpeedAgency() {
		return speedAgency;
	}
	public void setSpeedAgency(int speedAgency) {
		this.speedAgency = speedAgency;
	}
	public int getAllPlans() {
		return allPlans;
	}
	public void setAllPlans(int allPlans) {
		this.allPlans = allPlans;
	}
	public int getRevPlanCount() {
		return revPlanCount;
	}
	public void setRevPlanCount(int revPlanCount) {
		this.revPlanCount = revPlanCount;
	}
	public int getPostPaidPlanCnt() {
		return postPaidPlanCnt;
	}
	public void setPostPaidPlanCnt(int postPaidPlanCnt) {
		this.postPaidPlanCnt = postPaidPlanCnt;
	}
	public int getNystaNonRevCount() {
		return nystaNonRevCount;
	}
	public void setNystaNonRevCount(int nystaNonRevCount) {
		this.nystaNonRevCount = nystaNonRevCount;
	}
	public int getNystaAnnualPlanCount() {
		return nystaAnnualPlanCount;
	}
	public void setNystaAnnualPlanCount(int nystaAnnualPlanCount) {
		this.nystaAnnualPlanCount = nystaAnnualPlanCount;
	}
	public int getNystaPostPaidComlCount() {
		return nystaPostPaidComlCount;
	}
	public void setNystaPostPaidComlCount(int nystaPostPaidComlCount) {
		this.nystaPostPaidComlCount = nystaPostPaidComlCount;
	}
	public int getNystaTagStatus() {
		return nystaTagStatus;
	}
	public void setNystaTagStatus(int nystaTagStatus) {
		this.nystaTagStatus = nystaTagStatus;
	}
	public int getNysbaNonRevCount() {
		return nysbaNonRevCount;
	}
	public void setNysbaNonRevCount(int nysbaNonRevCount) {
		this.nysbaNonRevCount = nysbaNonRevCount;
	}
	public int getMtaNonRevCount() {
		return mtaNonRevCount;
	}
	public void setMtaNonRevCount(int mtaNonRevCount) {
		this.mtaNonRevCount = mtaNonRevCount;
	}
	public int getMtaSpecialPlanCount() {
		return mtaSpecialPlanCount;
	}
	public void setMtaSpecialPlanCount(int mtaSpecialPlanCount) {
		this.mtaSpecialPlanCount = mtaSpecialPlanCount;
	}
	public int getMtaTagStatus() {
		return mtaTagStatus;
	}
	public void setMtaTagStatus(int mtaTagStatus) {
		this.mtaTagStatus = mtaTagStatus;
	}
	public int getPaNonRevCount() {
		return paNonRevCount;
	}
	public void setPaNonRevCount(int paNonRevCount) {
		this.paNonRevCount = paNonRevCount;
	}
	public int getPaBridgesCount() {
		return paBridgesCount;
	}
	public void setPaBridgesCount(int paBridgesCount) {
		this.paBridgesCount = paBridgesCount;
	}
	public int getPaSiBridgesCount() {
		return paSiBridgesCount;
	}
	public void setPaSiBridgesCount(int paSiBridgesCount) {
		this.paSiBridgesCount = paSiBridgesCount;
	}
	public int getPaCarpoolCount() {
		return paCarpoolCount;
	}
	public void setPaCarpoolCount(int paCarpoolCount) {
		this.paCarpoolCount = paCarpoolCount;
	}
	public int getRioNonRevCount() {
		return rioNonRevCount;
	}
	public void setRioNonRevCount(int rioNonRevCount) {
		this.rioNonRevCount = rioNonRevCount;
	}
	public int getPlzLimitedCount() {
		return plzLimitedCount;
	}
	public void setPlzLimitedCount(int plzLimitedCount) {
		this.plzLimitedCount = plzLimitedCount;
	}
	public int getPaTagStatus() {
		return paTagStatus;
	}
	public void setPaTagStatus(int paTagStatus) {
		this.paTagStatus = paTagStatus;
	}
	public int getRioTagStatus() {
		return rioTagStatus;
	}
	public void setRioTagStatus(int rioTagStatus) {
		this.rioTagStatus = rioTagStatus;
	}
	public int getFtagTagStatus() {
		return ftagTagStatus;
	}
	public void setFtagTagStatus(int ftagTagStatus) {
		this.ftagTagStatus = ftagTagStatus;
	}
	public int getIagCodedClass() {
		return iagCodedClass;
	}
	public void setIagCodedClass(int iagCodedClass) {
		this.iagCodedClass = iagCodedClass;
	}
	public String getOptInOutFlag() {
		return optInOutFlag;
	}
	public void setOptInOutFlag(String optInOutFlag) {
		this.optInOutFlag = optInOutFlag;
	}
	public String getPrevalidationFlag() {
		return prevalidationFlag;
	}
	public void setPrevalidationFlag(String prevalidationFlag) {
		this.prevalidationFlag = prevalidationFlag;
	}
	public String getIsCompanionTag() {
		return isCompanionTag;
	}
	public void setIsCompanionTag(String isCompanionTag) {
		this.isCompanionTag = isCompanionTag;
	}
	public Double getItagInfo() {
		return itagInfo;
	}
	public void setItagInfo(Double itagInfo) {
		this.itagInfo = itagInfo;
	}
	public int getItagTagStatus() {
		return itagTagStatus;
	}
	public void setItagTagStatus(int itagTagStatus) {
		this.itagTagStatus = itagTagStatus;
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
	public String getTsCtrlStr() {
		return tsCtrlStr;
	}
	public void setTsCtrlStr(String tsCtrlStr) {
		this.tsCtrlStr = tsCtrlStr;
	}
	public String getPlansStr() {
		return plansStr;
	}
	public void setPlansStr(String plansStr) {
		this.plansStr = plansStr;
	}
	public int getMtagPlanStr() {
		return mtagPlanStr;
	}
	public void setMtagPlanStr(int mtagPlanStr) {
		this.mtagPlanStr = mtagPlanStr;
	}
	public String getMtaCtrlStr() {
		return mtaCtrlStr;
	}
	public void setMtaCtrlStr(String mtaCtrlStr) {
		this.mtaCtrlStr = mtaCtrlStr;
	}
	public String getRioCtrlStr() {
		return rioCtrlStr;
	}
	public void setRioCtrlStr(String rioCtrlStr) {
		this.rioCtrlStr = rioCtrlStr;
	}
	public String getFtagPlanInfo() {
		return ftagPlanInfo;
	}
	public void setFtagPlanInfo(String ftagPlanInfo) {
		this.ftagPlanInfo = ftagPlanInfo;
	}
	public int getNysbaTagStatus() {
		return nysbaTagStatus;
	}
	public void setNysbaTagStatus(int nysbaTagStatus) {
		this.nysbaTagStatus = nysbaTagStatus;
	}
	public String getCscLookupKey() {
		return cscLookupKey;
	}
	public void setCscLookupKey(String cscLookupKey) {
		this.cscLookupKey = cscLookupKey;
	}
	public LocalDateTime getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(LocalDateTime updateTs) {
		this.updateTs = updateTs;
	}
	
	@Override
	public String toString() {
		return "TagStatusSortedRecord [agencyId=" + agencyId + ", accountNo=" + accountNo + ", etcAccountId="
				+ etcAccountId + ", accountType=" + accountType + ", accountStatus=" + accountStatus + ", deviceNo="
				+ deviceNo + ", deviceStatus=" + deviceStatus + ", financialStatus=" + financialStatus
				+ ", tagOwningAgency=" + tagOwningAgency + ", thwyAcct=" + thwyAcct + ", currentBalance="
				+ currentBalance + ", rebillPayType=" + rebillPayType + ", rebillAmount=" + rebillAmount
				+ ", rebillThreshold=" + rebillThreshold + ", postPaidStatus=" + postPaidStatus + ", acctSuspFlag="
				+ acctSuspFlag + ", postPaidFlag=" + postPaidFlag + ", firstTollDate=" + firstTollDate
				+ ", retailTagStatus=" + retailTagStatus + ", speedAgency=" + speedAgency + ", allPlans=" + allPlans
				+ ", revPlanCount=" + revPlanCount + ", postPaidPlanCnt=" + postPaidPlanCnt + ", nystaNonRevCount="
				+ nystaNonRevCount + ", nystaAnnualPlanCount=" + nystaAnnualPlanCount + ", nystaPostPaidComlCount="
				+ nystaPostPaidComlCount + ", nystaTagStatus=" + nystaTagStatus + ", nysbaNonRevCount="
				+ nysbaNonRevCount + ", mtaNonRevCount=" + mtaNonRevCount + ", mtaSpecialPlanCount="
				+ mtaSpecialPlanCount + ", mtaTagStatus=" + mtaTagStatus + ", paNonRevCount=" + paNonRevCount
				+ ", paBridgesCount=" + paBridgesCount + ", paSiBridgesCount=" + paSiBridgesCount + ", paCarpoolCount="
				+ paCarpoolCount + ", rioNonRevCount=" + rioNonRevCount + ", plzLimitedCount=" + plzLimitedCount
				+ ", paTagStatus=" + paTagStatus + ", rioTagStatus=" + rioTagStatus + ", ftagTagStatus=" + ftagTagStatus
				+ ", iagCodedClass=" + iagCodedClass + ", optInOutFlag=" + optInOutFlag + ", prevalidationFlag="
				+ prevalidationFlag + ", isCompanionTag=" + isCompanionTag + ", itagInfo=" + itagInfo
				+ ", itagTagStatus=" + itagTagStatus + ", fromPlaza=" + fromPlaza + ", toPlaza=" + toPlaza
				+ ", tsCtrlStr=" + tsCtrlStr + ", plansStr=" + plansStr + ", mtagPlanStr=" + mtagPlanStr
				+ ", mtaCtrlStr=" + mtaCtrlStr + ", rioCtrlStr=" + rioCtrlStr + ", ftagPlanInfo=" + ftagPlanInfo
				+ ", nysbaTagStatus=" + nysbaTagStatus + ", cscLookupKey=" + cscLookupKey + ", updateTs=" + updateTs
				+ "]";
	}
	
}
