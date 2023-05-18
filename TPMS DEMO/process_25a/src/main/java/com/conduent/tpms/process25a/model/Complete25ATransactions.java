package com.conduent.tpms.process25a.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Complete25ATransactions {

	private String laneTxId;
	private String txExternRefNo; 
	private String matchRefNo;
	private String txCompleteRefNo;   
	private String txMatchStatus;
	private long txSeqNumber;            
	private double externFileId;  
	private long laneId; 
	private Timestamp txTimeStamp; 
	private String txTypeInd;              
	private String txSubtypeInd;   
	private String tollSytemType;
	private long laneMode;               
	private long laneType;               
	private long laneState;              
	private long laneHealth;                           
	private long plazaAgencyId;          
	private long plazaId;  
	private long collectorId;           
	private long tourSegmentId; 
	private String enrtyDataSource;        
	private long enrtyLaneId;            
	private long enrtyPlazaId;           
	private long entryTxSeqNumber;       
	private int entryVehicleSpeed;      
	private long laneTxStetus;           
	private long laneTxType;            
	private long tollRevenueType;       
	private int actualClass;           
	private int actualAxles;           
	private int actualExtraAxles;      
	private int collectorClass;        
	private int collectorAxles;        
	private int preclassClass;         
	private int preclassAxles;         
	private long postclassClass;        
	private long postclassAxles;        
	private long forwardAxles;          
	private long reverseAxles;          
	private float discountedAmount;      
	private float collectedAmount;       
	private float unrealizedAmount;      
	private int vehicleSpeed;         
	private String deviceNO;              
	private int accountType;           
	private int deviceIagClass;        
	private int deviceAxles;           
	private long etcAccountId;          
	private int accounAgencyId ;       
	private int readAviClass;          
	private int readAviAxles;          
	private String deviceProgramStatus;   
	private String bufferedReadFlag;  
	private long laneDeviceStatus;      
	private long postDeviceStatus;      
	private float preTxnBalance;         
	private long etcTxStatus;
	private String speedVoilFlag;        
	private String imageTaken;            
	private String plateCounrty;          
	private String plateState;            
	private String plateNumber;           
	private Date revenueDate;   
	private double atpFileId;
	private Timestamp updateTs; 
	private Date externFileDate;         
	private Date txDate;
	private String cscLookupKey; 
	private String event;                 
	private String hovFlag;               
	private String isRecprocityTxn; 
	private String matchTxnExternalRefNo;
	private String isZeroToll;
	private String statusCd;
	private Date processedDate;
	private Date insertDate;
	private Date updateDate;
	private String summaryFlag;
	private float fullFareAmount;
	
	
	public String getLaneTxId() {
		return laneTxId;
	}
	public void setLaneTxId(String laneTxId) {
		this.laneTxId = laneTxId;
	}
	public String getTxExternRefNo() {
		return txExternRefNo;
	}
	public void setTxExternRefNo(String txExternRefNo) {
		this.txExternRefNo = txExternRefNo;
	}
	public String getMatchRefNo() {
		return matchRefNo;
	}
	public void setMatchRefNo(String matchRefNo) {
		this.matchRefNo = matchRefNo;
	}
	public String getTxCompleteRefNo() {
		return txCompleteRefNo;
	}
	public void setTxCompleteRefNo(String txCompleteRefNo) {
		this.txCompleteRefNo = txCompleteRefNo;
	}
	public String getTxMatchStatus() {
		return txMatchStatus;
	}
	public void setTxMatchStatus(String txMatchStatus) {
		this.txMatchStatus = txMatchStatus;
	}
	public long getTxSeqNumber() {
		return txSeqNumber;
	}
	public void setTxSeqNumber(long txSeqNumber) {
		this.txSeqNumber = txSeqNumber;
	}
	public double getExternFileId() {
		return externFileId;
	}
	public void setExternFileId(double externFileId) {
		this.externFileId = externFileId;
	}
	public long getLaneId() {
		return laneId;
	}
	public void setLaneId(long laneId) {
		this.laneId = laneId;
	}
	public Timestamp getTxTimeStamp() {
		return txTimeStamp;
	}
	public void setTxTimeStamp(Timestamp txTimeStamp) {
		this.txTimeStamp = txTimeStamp;
	}
	public String getTxTypeInd() {
		return txTypeInd;
	}
	public void setTxTypeInd(String txTypeInd) {
		this.txTypeInd = txTypeInd;
	}
	public String getTxSubtypeInd() {
		return txSubtypeInd;
	}
	public void setTxSubtypeInd(String txSubtypeInd) {
		this.txSubtypeInd = txSubtypeInd;
	}
	public String getTollSytemType() {
		return tollSytemType;
	}
	public void setTollSytemType(String tollSytemType) {
		this.tollSytemType = tollSytemType;
	}
	public long getLaneMode() {
		return laneMode;
	}
	public void setLaneMode(long laneMode) {
		this.laneMode = laneMode;
	}
	public long getLaneType() {
		return laneType;
	}
	public void setLaneType(long laneType) {
		this.laneType = laneType;
	}
	public long getLaneState() {
		return laneState;
	}
	public void setLaneState(long laneState) {
		this.laneState = laneState;
	}
	public long getLaneHealth() {
		return laneHealth;
	}
	public void setLaneHealth(long laneHealth) {
		this.laneHealth = laneHealth;
	}
	public long getPlazaAgencyId() {
		return plazaAgencyId;
	}
	public void setPlazaAgencyId(long plazaAgencyId) {
		this.plazaAgencyId = plazaAgencyId;
	}
	public long getPlazaId() {
		return plazaId;
	}
	public void setPlazaId(long plazaId) {
		this.plazaId = plazaId;
	}
	public long getCollectorId() {
		return collectorId;
	}
	public void setCollectorId(long collectorId) {
		this.collectorId = collectorId;
	}
	public long getTourSegmentId() {
		return tourSegmentId;
	}
	public void setTourSegmentId(long tourSegmentId) {
		this.tourSegmentId = tourSegmentId;
	}
	public String getEnrtyDataSource() {
		return enrtyDataSource;
	}
	public void setEnrtyDataSource(String enrtyDataSource) {
		this.enrtyDataSource = enrtyDataSource;
	}
	public long getEnrtyLaneId() {
		return enrtyLaneId;
	}
	public void setEnrtyLaneId(long enrtyLaneId) {
		this.enrtyLaneId = enrtyLaneId;
	}
	public long getEnrtyPlazaId() {
		return enrtyPlazaId;
	}
	public void setEnrtyPlazaId(long enrtyPlazaId) {
		this.enrtyPlazaId = enrtyPlazaId;
	}
	public long getEntryTxSeqNumber() {
		return entryTxSeqNumber;
	}
	public void setEntryTxSeqNumber(long entryTxSeqNumber) {
		this.entryTxSeqNumber = entryTxSeqNumber;
	}
	public int getEntryVehicleSpeed() {
		return entryVehicleSpeed;
	}
	public void setEntryVehicleSpeed(int entryVehicleSpeed) {
		this.entryVehicleSpeed = entryVehicleSpeed;
	}
	public long getLaneTxStetus() {
		return laneTxStetus;
	}
	public void setLaneTxStetus(long laneTxStetus) {
		this.laneTxStetus = laneTxStetus;
	}
	public long getLaneTxType() {
		return laneTxType;
	}
	public void setLaneTxType(long laneTxType) {
		this.laneTxType = laneTxType;
	}
	public long getTollRevenueType() {
		return tollRevenueType;
	}
	public void setTollRevenueType(long tollRevenueType) {
		this.tollRevenueType = tollRevenueType;
	}
	public int getActualClass() {
		return actualClass;
	}
	public void setActualClass(int actualClass) {
		this.actualClass = actualClass;
	}
	public int getActualAxles() {
		return actualAxles;
	}
	public void setActualAxles(int actualAxles) {
		this.actualAxles = actualAxles;
	}
	public int getActualExtraAxles() {
		return actualExtraAxles;
	}
	public void setActualExtraAxles(int actualExtraAxles) {
		this.actualExtraAxles = actualExtraAxles;
	}
	public int getCollectorClass() {
		return collectorClass;
	}
	public void setCollectorClass(int collectorClass) {
		this.collectorClass = collectorClass;
	}
	public int getCollectorAxles() {
		return collectorAxles;
	}
	public void setCollectorAxles(int collectorAxles) {
		this.collectorAxles = collectorAxles;
	}
	public int getPreclassClass() {
		return preclassClass;
	}
	public void setPreclassClass(int preclassClass) {
		this.preclassClass = preclassClass;
	}
	public int getPreclassAxles() {
		return preclassAxles;
	}
	public void setPreclassAxles(int preclassAxles) {
		this.preclassAxles = preclassAxles;
	}
	public long getPostclassClass() {
		return postclassClass;
	}
	public void setPostclassClass(long postclassClass) {
		this.postclassClass = postclassClass;
	}
	public long getPostclassAxles() {
		return postclassAxles;
	}
	public void setPostclassAxles(long postclassAxles) {
		this.postclassAxles = postclassAxles;
	}
	public long getForwardAxles() {
		return forwardAxles;
	}
	public void setForwardAxles(long forwardAxles) {
		this.forwardAxles = forwardAxles;
	}
	public long getReverseAxles() {
		return reverseAxles;
	}
	public void setReverseAxles(long reverseAxles) {
		this.reverseAxles = reverseAxles;
	}
	public float getDiscountedAmount() {
		return discountedAmount;
	}
	public void setDiscountedAmount(float discountedAmount) {
		this.discountedAmount = discountedAmount;
	}
	public float getCollectedAmount() {
		return collectedAmount;
	}
	public void setCollectedAmount(float collectedAmount) {
		this.collectedAmount = collectedAmount;
	}
	public float getUnrealizedAmount() {
		return unrealizedAmount;
	}
	public void setUnrealizedAmount(float unrealizedAmount) {
		this.unrealizedAmount = unrealizedAmount;
	}
	public int getVehicleSpeed() {
		return vehicleSpeed;
	}
	public void setVehicleSpeed(int vehicleSpeed) {
		this.vehicleSpeed = vehicleSpeed;
	}
	public String getDeviceNO() {
		return deviceNO;
	}
	public void setDeviceNO(String deviceNO) {
		this.deviceNO = deviceNO;
	}
	public int getAccountType() {
		return accountType;
	}
	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}
	public int getDeviceIagClass() {
		return deviceIagClass;
	}
	public void setDeviceIagClass(int deviceIagClass) {
		this.deviceIagClass = deviceIagClass;
	}
	public int getDeviceAxles() {
		return deviceAxles;
	}
	public void setDeviceAxles(int deviceAxles) {
		this.deviceAxles = deviceAxles;
	}
	public long getEtcAccountId() {
		return etcAccountId;
	}
	public void setEtcAccountId(long etcAccountId) {
		this.etcAccountId = etcAccountId;
	}
	public int getAccounAgencyId() {
		return accounAgencyId;
	}
	public void setAccounAgencyId(int accounAgencyId) {
		this.accounAgencyId = accounAgencyId;
	}
	public int getReadAviClass() {
		return readAviClass;
	}
	public void setReadAviClass(int readAviClass) {
		this.readAviClass = readAviClass;
	}
	public int getReadAviAxles() {
		return readAviAxles;
	}
	public void setReadAviAxles(int readAviAxles) {
		this.readAviAxles = readAviAxles;
	}
	public String getDeviceProgramStatus() {
		return deviceProgramStatus;
	}
	public void setDeviceProgramStatus(String deviceProgramStatus) {
		this.deviceProgramStatus = deviceProgramStatus;
	}
	public String getBufferedReadFlag() {
		return bufferedReadFlag;
	}
	public void setBufferedReadFlag(String bufferedReadFlag) {
		this.bufferedReadFlag = bufferedReadFlag;
	}
	public long getLaneDeviceStatus() {
		return laneDeviceStatus;
	}
	public void setLaneDeviceStatus(long laneDeviceStatus) {
		this.laneDeviceStatus = laneDeviceStatus;
	}
	public long getPostDeviceStatus() {
		return postDeviceStatus;
	}
	public void setPostDeviceStatus(long postDeviceStatus) {
		this.postDeviceStatus = postDeviceStatus;
	}
	public float getPreTxnBalance() {
		return preTxnBalance;
	}
	public void setPreTxnBalance(float preTxnBalance) {
		this.preTxnBalance = preTxnBalance;
	}
	public long getEtcTxStatus() {
		return etcTxStatus;
	}
	public void setEtcTxStatus(long etcTxStatus) {
		this.etcTxStatus = etcTxStatus;
	}
	public String getSpeedVoilFlag() {
		return speedVoilFlag;
	}
	public void setSpeedVoilFlag(String speedVoilFlag) {
		this.speedVoilFlag = speedVoilFlag;
	}
	public String getImageTaken() {
		return imageTaken;
	}
	public void setImageTaken(String imageTaken) {
		this.imageTaken = imageTaken;
	}
	public String getPlateCounrty() {
		return plateCounrty;
	}
	public void setPlateCounrty(String plateCounrty) {
		this.plateCounrty = plateCounrty;
	}
	public String getPlateState() {
		return plateState;
	}
	public void setPlateState(String plateState) {
		this.plateState = plateState;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public Date getRevenueDate() {
		return revenueDate;
	}
	public void setRevenueDate(Date revenueDate) {
		this.revenueDate = revenueDate;
	}
	public double getAtpFileId() {
		return atpFileId;
	}
	public void setAtpFileId(double atpFileId) {
		this.atpFileId = atpFileId;
	}
	public Timestamp getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(Timestamp updateTs) {
		this.updateTs = updateTs;
	}
	public Date getExternFileDate() {
		return externFileDate;
	}
	public void setExternFileDate(Date externFileDate) {
		this.externFileDate = externFileDate;
	}
	public Date getTxDate() {
		return txDate;
	}
	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}
	public String getCscLookupKey() {
		return cscLookupKey;
	}
	public void setCscLookupKey(String cscLookupKey) {
		this.cscLookupKey = cscLookupKey;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getHovFlag() {
		return hovFlag;
	}
	public void setHovFlag(String hovFlag) {
		this.hovFlag = hovFlag;
	}
	public String getIsRecprocityTxn() {
		return isRecprocityTxn;
	}
	public void setIsRecprocityTxn(String isRecprocityTxn) {
		this.isRecprocityTxn = isRecprocityTxn;
	}
	public String getMatchTxnExternalRefNo() {
		return matchTxnExternalRefNo;
	}
	public void setMatchTxnExternalRefNo(String matchTxnExternalRefNo) {
		this.matchTxnExternalRefNo = matchTxnExternalRefNo;
	}
	public String getIsZeroToll() {
		return isZeroToll;
	}
	public void setIsZeroToll(String isZeroToll) {
		this.isZeroToll = isZeroToll;
	}
	public String getStatusCd() {
		return statusCd;
	}
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}
	public Date getProcessedDate() {
		return processedDate;
	}
	public void setProcessedDate(Date processedDate) {
		this.processedDate = processedDate;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getSummaryFlag() {
		return summaryFlag;
	}
	public void setSummaryFlag(String summaryFlag) {
		this.summaryFlag = summaryFlag;
	}
	public float getFullFareAmount() {
		return fullFareAmount;
	}
	public void setFullFareAmount(float fullFareAmount) {
		this.fullFareAmount = fullFareAmount;
	}
	@Override
	public String toString() {
		return "Complete25ATransactions [laneTxId=" + laneTxId + ", txExternRefNo=" + txExternRefNo + ", matchRefNo="
				+ matchRefNo + ", txCompleteRefNo=" + txCompleteRefNo + ", txMatchStatus=" + txMatchStatus
				+ ", txSeqNumber=" + txSeqNumber + ", externFileId=" + externFileId + ", laneId=" + laneId
				+ ", txTimeStamp=" + txTimeStamp + ", txTypeInd=" + txTypeInd + ", txSubtypeInd=" + txSubtypeInd
				+ ", tollSytemType=" + tollSytemType + ", laneMode=" + laneMode + ", laneType=" + laneType
				+ ", laneState=" + laneState + ", laneHealth=" + laneHealth + ", plazaAgencyId=" + plazaAgencyId
				+ ", plazaId=" + plazaId + ", collectorId=" + collectorId + ", tourSegmentId=" + tourSegmentId
				+ ", enrtyDataSource=" + enrtyDataSource + ", enrtyLaneId=" + enrtyLaneId + ", enrtyPlazaId="
				+ enrtyPlazaId + ", entryTxSeqNumber=" + entryTxSeqNumber + ", entryVehicleSpeed=" + entryVehicleSpeed
				+ ", laneTxStetus=" + laneTxStetus + ", laneTxType=" + laneTxType + ", tollRevenueType="
				+ tollRevenueType + ", actualClass=" + actualClass + ", actualAxles=" + actualAxles
				+ ", actualExtraAxles=" + actualExtraAxles + ", collectorClass=" + collectorClass + ", collectorAxles="
				+ collectorAxles + ", preclassClass=" + preclassClass + ", preclassAxles=" + preclassAxles
				+ ", postclassClass=" + postclassClass + ", postclassAxles=" + postclassAxles + ", forwardAxles="
				+ forwardAxles + ", reverseAxles=" + reverseAxles + ", discountedAmount=" + discountedAmount
				+ ", collectedAmount=" + collectedAmount + ", unrealizedAmount=" + unrealizedAmount + ", vehicleSpeed="
				+ vehicleSpeed + ", deviceNO=" + deviceNO + ", accountType=" + accountType + ", deviceIagClass="
				+ deviceIagClass + ", deviceAxles=" + deviceAxles + ", etcAccountId=" + etcAccountId
				+ ", accounAgencyId=" + accounAgencyId + ", readAviClass=" + readAviClass + ", readAviAxles="
				+ readAviAxles + ", deviceProgramStatus=" + deviceProgramStatus + ", bufferedReadFlag="
				+ bufferedReadFlag + ", laneDeviceStatus=" + laneDeviceStatus + ", postDeviceStatus=" + postDeviceStatus
				+ ", preTxnBalance=" + preTxnBalance + ", etcTxStatus=" + etcTxStatus + ", speedVoilFlag="
				+ speedVoilFlag + ", imageTaken=" + imageTaken + ", plateCounrty=" + plateCounrty + ", plateState="
				+ plateState + ", plateNumber=" + plateNumber + ", revenueDate=" + revenueDate + ", atpFileId="
				+ atpFileId + ", updateTs=" + updateTs + ", externFileDate=" + externFileDate + ", txDate=" + txDate
				+ ", cscLookupKey=" + cscLookupKey + ", event=" + event + ", hovFlag=" + hovFlag + ", isRecprocityTxn="
				+ isRecprocityTxn + ", matchTxnExternalRefNo=" + matchTxnExternalRefNo + ", isZeroToll=" + isZeroToll
				+ ", statusCd=" + statusCd + ", processedDate=" + processedDate + ", insertDate=" + insertDate
				+ ", updateDate=" + updateDate + ", summaryFlag=" + summaryFlag + ", fullFareAmount=" + fullFareAmount
				+ "]";
	} 




}
