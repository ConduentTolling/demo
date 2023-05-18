package com.conduent.tpms.intx.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AwayTransaction {

	private String etcCorrReasonId;
	
	private String etcTrxSerialNum;
	
	private String etcRevenueDate;
	
	private Integer etcFacAgency;
	
	private String etcTrxType;
	
	private String etcEntryDate;
	
	private String etcEntryTime;
	
	private String etcEntryPlaza;
	
	private String etcEntryLane;
	
	private String etcTagAgency;
	
	private Long etcTagSerialNumber;
	
	private String etcReadPerformance;
	
	private String etcWritePerf;
	
	private String etcTagPgmStatus;
	
	private String etcLaneMode;
	
	private Integer etcValidationStatus;
	
	private String etcLicState;
	
	private String etcLicNumber;
	
	private String etcClassCharged;
	
	private Integer etcActualAxles;
	
	private Integer etcExitSpeed;
	
	private String etcOverSpeed;
	
	private String etcExitDate;
	
	private String etcExitTime;
	
	private String etcExitPlaza;
	
	private String etcExitLane;
	
	private String etcDebitCredit;
	
	private Double etcAmountDue;
	
	private Long laneTxId;
	
	private Long txStatus;
	
	private Long atpFileId;
	
	private String txSubType;
	
	private String txType;
	
	private Long externFileId;
	
	private LocalDateTime etcTxTimestamp;
	
	private LocalDateTime etcEntryTimestamp;

	public LocalDateTime getEtcTxTimestamp() {
		return etcTxTimestamp;
	}

	public void setEtcTxTimestamp(LocalDateTime etcTxTimestamp) {
		this.etcTxTimestamp = etcTxTimestamp;
	}

	public LocalDateTime getEtcEntryTimestamp() {
		return etcEntryTimestamp;
	}

	public void setEtcEntryTimestamp(LocalDateTime etcEntryTimestamp) {
		this.etcEntryTimestamp = etcEntryTimestamp;
	}

	public Long getLaneTxId() {
		return laneTxId;
	}

	public void setLaneTxId(Long laneTxId) {
		this.laneTxId = laneTxId;
	}

	public Long getTxStatus() {
		return txStatus;
	}

	public void setTxStatus(Long txStatus) {
		this.txStatus = txStatus;
	}

	public Long getAtpFileId() {
		return atpFileId;
	}

	public void setAtpFileId(Long atpFileId) {
		this.atpFileId = atpFileId;
	}

	public String getTxSubType() {
		return txSubType;
	}

	public void setTxSubType(String txSubType) {
		this.txSubType = txSubType;
	}

	public String getEtcCorrReasonId() {
		return etcCorrReasonId;
	}

	public void setEtcCorrReasonId(String etcCorrReasonId) {
		this.etcCorrReasonId = etcCorrReasonId;
	}

	public String getEtcTrxSerialNum() {
		return etcTrxSerialNum;
	}

	public void setEtcTrxSerialNum(String etcTrxSerialNum) {
		this.etcTrxSerialNum = etcTrxSerialNum;
	}

	public String getEtcRevenueDate() {
		return etcRevenueDate;
	}

	public void setEtcRevenueDate(String etcRevenueDate) {
		this.etcRevenueDate = etcRevenueDate;
	}

	public Integer getEtcFacAgency() {
		return etcFacAgency;
	}

	public void setEtcFacAgency(Integer etcFacAgency) {
		this.etcFacAgency = etcFacAgency;
	}

	public String getEtcTrxType() {
		return etcTrxType;
	}

	public void setEtcTrxType(String etcTrxType) {
		this.etcTrxType = etcTrxType;
	}

	public String getEtcEntryDate() {
		return etcEntryDate;
	}

	public void setEtcEntryDate(String etcEntryDate) {
		this.etcEntryDate = etcEntryDate;
	}

	public String getEtcEntryTime() {
		return etcEntryTime;
	}

	public void setEtcEntryTime(String etcEntryTime) {
		this.etcEntryTime = etcEntryTime;
	}

	public String getEtcEntryPlaza() {
		return etcEntryPlaza;
	}

	public void setEtcEntryPlaza(String etcEntryPlaza) {
		this.etcEntryPlaza = etcEntryPlaza;
	}

	public String getEtcEntryLane() {
		return etcEntryLane;
	}

	public void setEtcEntryLane(String etcEntryLane) {
		this.etcEntryLane = etcEntryLane;
	}

	public String getEtcTagAgency() {
		return etcTagAgency;
	}

	public void setEtcTagAgency(String etcTagAgency) {
		this.etcTagAgency = etcTagAgency;
	}

	public Long getEtcTagSerialNumber() {
		return etcTagSerialNumber;
	}

	public void setEtcTagSerialNumber(Long etcTagSerialNumber) {
		this.etcTagSerialNumber = etcTagSerialNumber;
	}

	public String getEtcReadPerformance() {
		return etcReadPerformance;
	}

	public void setEtcReadPerformance(String etcReadPerformance) {
		this.etcReadPerformance = etcReadPerformance;
	}

	public String getEtcWritePerf() {
		return etcWritePerf;
	}

	public void setEtcWritePerf(String etcWritePerf) {
		this.etcWritePerf = etcWritePerf;
	}

	public String getEtcTagPgmStatus() {
		return etcTagPgmStatus;
	}

	public void setEtcTagPgmStatus(String etcTagPgmStatus) {
		this.etcTagPgmStatus = etcTagPgmStatus;
	}

	public String getEtcLaneMode() {
		return etcLaneMode;
	}

	public void setEtcLaneMode(String etcLaneMode) {
		this.etcLaneMode = etcLaneMode;
	}

	public Integer getEtcValidationStatus() {
		return etcValidationStatus;
	}

	public void setEtcValidationStatus(Integer etcValidationStatus) {
		this.etcValidationStatus = etcValidationStatus;
	}

	public String getEtcLicState() {
		return etcLicState;
	}

	public void setEtcLicState(String etcLicState) {
		this.etcLicState = etcLicState;
	}

	public String getEtcLicNumber() {
		return etcLicNumber;
	}

	public void setEtcLicNumber(String etcLicNumber) {
		this.etcLicNumber = etcLicNumber;
	}

	public String getEtcClassCharged() {
		return etcClassCharged;
	}

	public void setEtcClassCharged(String etcClassCharged) {
		this.etcClassCharged = etcClassCharged;
	}

	public Integer getEtcActualAxles() {
		return etcActualAxles;
	}

	public void setEtcActualAxles(Integer etcActualAxles) {
		this.etcActualAxles = etcActualAxles;
	}

	public Integer getEtcExitSpeed() {
		return etcExitSpeed;
	}

	public void setEtcExitSpeed(Integer etcExitSpeed) {
		this.etcExitSpeed = etcExitSpeed;
	}

	public String getEtcOverSpeed() {
		return etcOverSpeed;
	}

	public void setEtcOverSpeed(String etcOverSpeed) {
		this.etcOverSpeed = etcOverSpeed;
	}

	public String getEtcExitDate() {
		return etcExitDate;
	}

	public void setEtcExitDate(String etcExitDate) {
		this.etcExitDate = etcExitDate;
	}

	public String getEtcExitTime() {
		return etcExitTime;
	}

	public void setEtcExitTime(String etcExitTime) {
		this.etcExitTime = etcExitTime;
	}

	public String getEtcExitPlaza() {
		return etcExitPlaza;
	}

	public void setEtcExitPlaza(String etcExitPlaza) {
		this.etcExitPlaza = etcExitPlaza;
	}

	public String getEtcExitLane() {
		return etcExitLane;
	}

	public void setEtcExitLane(String etcExitLane) {
		this.etcExitLane = etcExitLane;
	}

	public String getEtcDebitCredit() {
		return etcDebitCredit;
	}

	public void setEtcDebitCredit(String etcDebitCredit) {
		this.etcDebitCredit = etcDebitCredit;
	}

	public Double getEtcAmountDue() {
		return etcAmountDue;
	}

	public void setEtcAmountDue(Double etcAmountDue) {
		this.etcAmountDue = etcAmountDue;
	}
	
	public String getTxType() {
		return txType;
	}

	public void setTxType(String txType) {
		this.txType = txType;
	}

	private String txExternRefNo;

	private Long txSeqNumber;

	private Integer plazaAgencyId;

	private Integer plazaId;

	private Integer laneId;

	// private String txTimestamp;
	private LocalDateTime txTimestamp;

	private Integer txModSeq;

	private String tillSystemType;

	private Integer laneMode;

	private Long laneType;

	private Integer laneState;

	private Integer laneHealth;

	private Long collectorId;

	private Long tourSegmentId;

	private String entryDataSource;

	private Integer entryLaneId;

	private Integer entryPlazaId;

	// private String entryTimestamp;
	private LocalDateTime entryTimestamp;

	private Integer entryTxSeqNumber;

	private Integer entryVehicleSpeed;

	private Integer laneTxStatus;

	private Integer lanetxType;

	private Integer tollRevenueType;

	private Integer actualClass;

	private Integer actualAxles;

	private Integer actualExtraAxles;

	private Integer collectorClass;

	private Integer collectorAxles;

	private Integer preClassClass;

	private Integer preClassAxles;

	private Integer postClassClass;

	private Integer postClassAxles;

	private Integer forwardAxles;

	private Integer reverseAxles;

	private Double fullFareAmount;

	private Double discountedAmount;

	private Double collectedAmount;

	private Double unrealizedAmount;

	private String isDiscountable;

	private String isMedianFare;

	private String isPeak;

	private Integer priceScheduleId;

	private Integer vehicleSpeed;

	private Integer receiptIssued;

	private String deviceNo;

	private Integer accountType;

	private Integer deviceCodedClass;

	private Integer deviceAgencyClass;

	private Integer deviceIagClass;

	private Integer deviceAxles;

	private Long etcAccountId;

	private Integer accountAgencyId;

	private Integer readAviClass;

	private Integer readAviAxles;

	private String deviceProgramStatus;

	private String bufferedReadFlag;

	private Long laneDeviceStatus;

	private Integer postDeviceStatus;

	private Double preTxnBalance;

	private Integer planTypeId;

	private Integer etcTxStatus;

	private String speedViolFlag;

	private String imageTaken;

	private String plateCountry;

	private String plateState;

	private String plateNumber;

	// private String revenueDate;
	private LocalDate revenueDate;

	private LocalDate postedDate;

	private String isReversed;

	private Integer corrReasonId;

	private LocalDate reconDate;

	private Integer reconStatusInd;

	private Integer reconSubCodeInd;

	private LocalDate externFileDate;

	private Integer mileage;

	private Integer deviceReadCount;

	private Integer deviceWriteCount;

	private Integer entryDeviceReadCount;

	private Integer entryDeviceWriteCount;

	private String depositId;

	private LocalDate txDate;

	private String cscLookupKey;

	private LocalDateTime updateTs;

	private String tollSystemType;

	private String aetFlag;

	private String eventTimestamp;

	private Integer eventType;

	private Integer prevViolTxStatus;

	private Integer violTxStatus;

	private Integer violType;

	private String reciprocityTrx;

	private Double videoFareAmount;
	private Double etcFareAmount;
	private Double cashFareAmount;
	private Double expectedRevenueAmount;
	private Double postedFareAmount;

	private Long dstAtpFileId;
	
	private String isInTxPending;

	public String getTxExternRefNo() {
		return txExternRefNo;
	}

	public void setTxExternRefNo(String txExternRefNo) {
		this.txExternRefNo = txExternRefNo;
	}

	public Long getTxSeqNumber() {
		return txSeqNumber;
	}

	public void setTxSeqNumber(Long txSeqNumber) {
		this.txSeqNumber = txSeqNumber;
	}

	public Long getExternFileId() {
		return externFileId;
	}

	public void setExternFileId(Long externFileId) {
		this.externFileId = externFileId;
	}

	public Integer getPlazaAgencyId() {
		return plazaAgencyId;
	}

	public void setPlazaAgencyId(Integer plazaAgencyId) {
		this.plazaAgencyId = plazaAgencyId;
	}

	public Integer getPlazaId() {
		return plazaId;
	}

	public void setPlazaId(Integer plazaId) {
		this.plazaId = plazaId;
	}

	public Integer getLaneId() {
		return laneId;
	}

	public void setLaneId(Integer laneId) {
		this.laneId = laneId;
	}

	public Integer getTxModSeq() {
		return txModSeq;
	}

	public void setTxModSeq(Integer txModSeq) {
		this.txModSeq = txModSeq;
	}

	public String getTillSystemType() {
		return tillSystemType;
	}

	public void setTillSystemType(String tillSystemType) {
		this.tillSystemType = tillSystemType;
	}

	public Integer getLaneMode() {
		return laneMode;
	}

	public void setLaneMode(Integer laneMode) {
		this.laneMode = laneMode;
	}

	public Long getLaneType() {
		return laneType;
	}

	public void setLaneType(Long laneType) {
		this.laneType = laneType;
	}

	public Integer getLaneState() {
		return laneState;
	}

	public void setLaneState(Integer laneState) {
		this.laneState = laneState;
	}

	public Integer getLaneHealth() {
		return laneHealth;
	}

	public void setLaneHealth(Integer laneHealth) {
		this.laneHealth = laneHealth;
	}

	public Long getCollectorId() {
		return collectorId;
	}

	public void setCollectorId(Long collectorId) {
		this.collectorId = collectorId;
	}

	public Long getTourSegmentId() {
		return tourSegmentId;
	}

	public void setTourSegmentId(Long tourSegmentId) {
		this.tourSegmentId = tourSegmentId;
	}

	public String getEntryDataSource() {
		return entryDataSource;
	}

	public void setEntryDataSource(String entryDataSource) {
		this.entryDataSource = entryDataSource;
	}

	public Integer getEntryLaneId() {
		return entryLaneId;
	}

	public void setEntryLaneId(Integer entryLaneId) {
		this.entryLaneId = entryLaneId;
	}

	public Integer getEntryPlazaId() {
		return entryPlazaId;
	}

	public void setEntryPlazaId(Integer entryPlazaId) {
		this.entryPlazaId = entryPlazaId;
	}

	public Integer getEntryTxSeqNumber() {
		return entryTxSeqNumber;
	}

	public void setEntryTxSeqNumber(Integer entryTxSeqNumber) {
		this.entryTxSeqNumber = entryTxSeqNumber;
	}

	public Integer getEntryVehicleSpeed() {
		return entryVehicleSpeed;
	}

	public void setEntryVehicleSpeed(Integer entryVehicleSpeed) {
		this.entryVehicleSpeed = entryVehicleSpeed;
	}

	public Integer getLaneTxStatus() {
		return laneTxStatus;
	}

	public void setLaneTxStatus(Integer laneTxStatus) {
		this.laneTxStatus = laneTxStatus;
	}

	public Integer getLanetxType() {
		return lanetxType;
	}

	public void setLanetxType(Integer lanetxType) {
		this.lanetxType = lanetxType;
	}

	public Integer getTollRevenueType() {
		return tollRevenueType;
	}

	public void setTollRevenueType(Integer tollRevenueType) {
		this.tollRevenueType = tollRevenueType;
	}

	public Integer getActualClass() {
		return actualClass;
	}

	public void setActualClass(Integer actualClass) {
		this.actualClass = actualClass;
	}

	public Integer getActualAxles() {
		return actualAxles;
	}

	public void setActualAxles(Integer actualAxles) {
		this.actualAxles = actualAxles;
	}

	public Integer getActualExtraAxles() {
		return actualExtraAxles;
	}

	public void setActualExtraAxles(Integer actualExtraAxles) {
		this.actualExtraAxles = actualExtraAxles;
	}

	public Integer getCollectorClass() {
		return collectorClass;
	}

	public void setCollectorClass(Integer collectorClass) {
		this.collectorClass = collectorClass;
	}

	public Integer getCollectorAxles() {
		return collectorAxles;
	}

	public void setCollectorAxles(Integer collectorAxles) {
		this.collectorAxles = collectorAxles;
	}

	public Integer getPreClassClass() {
		return preClassClass;
	}

	public void setPreClassClass(Integer preClassClass) {
		this.preClassClass = preClassClass;
	}

	public Integer getPreClassAxles() {
		return preClassAxles;
	}

	public void setPreClassAxles(Integer preClassAxles) {
		this.preClassAxles = preClassAxles;
	}

	public Integer getPostClassClass() {
		return postClassClass;
	}

	public void setPostClassClass(Integer postClassClass) {
		this.postClassClass = postClassClass;
	}

	public Integer getPostClassAxles() {
		return postClassAxles;
	}

	public void setPostClassAxles(Integer postClassAxles) {
		this.postClassAxles = postClassAxles;
	}

	public Integer getForwardAxles() {
		return forwardAxles;
	}

	public void setForwardAxles(Integer forwardAxles) {
		this.forwardAxles = forwardAxles;
	}

	public Integer getReverseAxles() {
		return reverseAxles;
	}

	public void setReverseAxles(Integer reverseAxles) {
		this.reverseAxles = reverseAxles;
	}

	public Double getFullFareAmount() {
		return fullFareAmount;
	}

	public void setFullFareAmount(Double fullFareAmount) {
		this.fullFareAmount = fullFareAmount;
	}

	public Double getDiscountedAmount() {
		return discountedAmount;
	}

	public void setDiscountedAmount(Double discountedAmount) {
		this.discountedAmount = discountedAmount;
	}

	public Double getCollectedAmount() {
		return collectedAmount;
	}

	public void setCollectedAmount(Double collectedAmount) {
		this.collectedAmount = collectedAmount;
	}

	public Double getUnrealizedAmount() {
		return unrealizedAmount;
	}

	public void setUnrealizedAmount(Double unrealizedAmount) {
		this.unrealizedAmount = unrealizedAmount;
	}

	public String getIsDiscountable() {
		return isDiscountable;
	}

	public void setIsDiscountable(String isDiscountable) {
		this.isDiscountable = isDiscountable;
	}

	public String getIsMedianFare() {
		return isMedianFare;
	}

	public void setIsMedianFare(String isMedianFare) {
		this.isMedianFare = isMedianFare;
	}

	public String getIsPeak() {
		return isPeak;
	}

	public void setIsPeak(String isPeak) {
		this.isPeak = isPeak;
	}

	public Integer getPriceScheduleId() {
		return priceScheduleId;
	}

	public void setPriceScheduleId(Integer priceScheduleId) {
		this.priceScheduleId = priceScheduleId;
	}

	public Integer getVehicleSpeed() {
		return vehicleSpeed;
	}

	public void setVehicleSpeed(Integer vehicleSpeed) {
		this.vehicleSpeed = vehicleSpeed;
	}

	public Integer getReceiptIssued() {
		return receiptIssued;
	}

	public void setReceiptIssued(Integer receiptIssued) {
		this.receiptIssued = receiptIssued;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public Integer getDeviceCodedClass() {
		return deviceCodedClass;
	}

	public void setDeviceCodedClass(Integer deviceCodedClass) {
		this.deviceCodedClass = deviceCodedClass;
	}

	public Integer getDeviceAgencyClass() {
		return deviceAgencyClass;
	}

	public void setDeviceAgencyClass(Integer deviceAgencyClass) {
		this.deviceAgencyClass = deviceAgencyClass;
	}

	public Integer getDeviceIagClass() {
		return deviceIagClass;
	}

	public void setDeviceIagClass(Integer deviceIagClass) {
		this.deviceIagClass = deviceIagClass;
	}

	public Integer getDeviceAxles() {
		return deviceAxles;
	}

	public void setDeviceAxles(Integer deviceAxles) {
		this.deviceAxles = deviceAxles;
	}

	public Long getEtcAccountId() {
		return etcAccountId;
	}

	public void setEtcAccountId(Long etcAccountId) {
		this.etcAccountId = etcAccountId;
	}

	public Integer getAccountAgencyId() {
		return accountAgencyId;
	}

	public void setAccountAgencyId(Integer accountAgencyId) {
		this.accountAgencyId = accountAgencyId;
	}

	public Integer getReadAviClass() {
		return readAviClass;
	}

	public void setReadAviClass(Integer readAviClass) {
		this.readAviClass = readAviClass;
	}

	public Integer getReadAviAxles() {
		return readAviAxles;
	}

	public void setReadAviAxles(Integer readAviAxles) {
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

	public Long getLaneDeviceStatus() {
		return laneDeviceStatus;
	}

	public void setLaneDeviceStatus(Long laneDeviceStatus) {
		this.laneDeviceStatus = laneDeviceStatus;
	}

	public Integer getPostDeviceStatus() {
		return postDeviceStatus;
	}

	public void setPostDeviceStatus(Integer postDeviceStatus) {
		this.postDeviceStatus = postDeviceStatus;
	}

	public Double getPreTxnBalance() {
		return preTxnBalance;
	}

	public void setPreTxnBalance(Double preTxnBalance) {
		this.preTxnBalance = preTxnBalance;
	}

	public Integer getPlanTypeId() {
		return planTypeId;
	}

	public void setPlanTypeId(Integer planTypeId) {
		this.planTypeId = planTypeId;
	}

	public Integer getEtcTxStatus() {
		return etcTxStatus;
	}

	public void setEtcTxStatus(Integer etcTxStatus) {
		this.etcTxStatus = etcTxStatus;
	}

	public String getSpeedViolFlag() {
		return speedViolFlag;
	}

	public void setSpeedViolFlag(String speedViolFlag) {
		this.speedViolFlag = speedViolFlag;
	}

	public String getImageTaken() {
		return imageTaken;
	}

	public void setImageTaken(String imageTaken) {
		this.imageTaken = imageTaken;
	}

	public String getPlateCountry() {
		return plateCountry;
	}

	public void setPlateCountry(String plateCountry) {
		this.plateCountry = plateCountry;
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

	public LocalDate getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(LocalDate postedDate) {
		this.postedDate = postedDate;
	}

	public String getIsReversed() {
		return isReversed;
	}

	public void setIsReversed(String isReversed) {
		this.isReversed = isReversed;
	}

	public Integer getCorrReasonId() {
		return corrReasonId;
	}

	public void setCorrReasonId(Integer corrReasonId) {
		this.corrReasonId = corrReasonId;
	}

	public LocalDate getReconDate() {
		return reconDate;
	}

	public void setReconDate(LocalDate reconDate) {
		this.reconDate = reconDate;
	}

	public Integer getReconStatusInd() {
		return reconStatusInd;
	}

	public void setReconStatusInd(Integer reconStatusInd) {
		this.reconStatusInd = reconStatusInd;
	}

	public Integer getReconSubCodeInd() {
		return reconSubCodeInd;
	}

	public void setReconSubCodeInd(Integer reconSubCodeInd) {
		this.reconSubCodeInd = reconSubCodeInd;
	}

	public LocalDate getExternFileDate() {
		return externFileDate;
	}

	public void setExternFileDate(LocalDate externFileDate) {
		this.externFileDate = externFileDate;
	}

	public Integer getMileage() {
		return mileage;
	}

	public void setMileage(Integer mileage) {
		this.mileage = mileage;
	}

	public Integer getDeviceReadCount() {
		return deviceReadCount;
	}

	public void setDeviceReadCount(Integer deviceReadCount) {
		this.deviceReadCount = deviceReadCount;
	}

	public Integer getDeviceWriteCount() {
		return deviceWriteCount;
	}

	public void setDeviceWriteCount(Integer deviceWriteCount) {
		this.deviceWriteCount = deviceWriteCount;
	}

	public Integer getEntryDeviceReadCount() {
		return entryDeviceReadCount;
	}

	public void setEntryDeviceReadCount(Integer entryDeviceReadCount) {
		this.entryDeviceReadCount = entryDeviceReadCount;
	}

	public Integer getEntryDeviceWriteCount() {
		return entryDeviceWriteCount;
	}

	public void setEntryDeviceWriteCount(Integer entryDeviceWriteCount) {
		this.entryDeviceWriteCount = entryDeviceWriteCount;
	}

	public String getDepositId() {
		return depositId;
	}

	public void setDepositId(String depositId) {
		this.depositId = depositId;
	}

	public LocalDate getTxDate() {
		return txDate;
	}

	public void setTxDate(LocalDate txDate) {
		this.txDate = txDate;
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

	public String getTollSystemType() {
		return tollSystemType;
	}

	public void setTollSystemType(String tollSystemType) {
		this.tollSystemType = tollSystemType;
	}

	public String getAetFlag() {
		return aetFlag;
	}

	public void setAetFlag(String aetFlag) {
		this.aetFlag = aetFlag;
	}

	public String getEventTimestamp() {
		return eventTimestamp;
	}

	public void setEventTimestamp(String eventTimestamp) {
		this.eventTimestamp = eventTimestamp;
	}

	public Integer getEventType() {
		return eventType;
	}

	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}

	public Integer getPrevViolTxStatus() {
		return prevViolTxStatus;
	}

	public void setPrevViolTxStatus(Integer prevViolTxStatus) {
		this.prevViolTxStatus = prevViolTxStatus;
	}

	public Integer getViolTxStatus() {
		return violTxStatus;
	}

	public void setViolTxStatus(Integer violTxStatus) {
		this.violTxStatus = violTxStatus;
	}

	public Integer getViolType() {
		return violType;
	}

	public void setViolType(Integer violType) {
		this.violType = violType;
	}

	public String getReciprocityTrx() {
		return reciprocityTrx;
	}

	public void setReciprocityTrx(String reciprocityTrx) {
		this.reciprocityTrx = reciprocityTrx;
	}

	public LocalDateTime getTxTimestamp() {
		return txTimestamp;
	}

	public void setTxTimestamp(LocalDateTime txTimestamp) {
		this.txTimestamp = txTimestamp;
	}

	public LocalDateTime getEntryTimestamp() {
		return entryTimestamp;
	}

	public void setEntryTimestamp(LocalDateTime entryTimestamp) {
		this.entryTimestamp = entryTimestamp;
	}

	public LocalDate getRevenueDate() {
		return revenueDate;
	}

	public void setRevenueDate(LocalDate revenueDate) {
		this.revenueDate = revenueDate;
	}

	public Long getDstAtpFileId() {
		return dstAtpFileId;
	}

	public void setDstAtpFileId(Long dstAtpFileId) {
		this.dstAtpFileId = dstAtpFileId;
	}

	public Double getVideoFareAmount() {
		return videoFareAmount;
	}

	public void setVideoFareAmount(Double videoFareAmount) {
		this.videoFareAmount = videoFareAmount;
	}

	public Double getEtcFareAmount() {
		return etcFareAmount;
	}

	public void setEtcFareAmount(Double etcFareAmount) {
		this.etcFareAmount = etcFareAmount;
	}

	public Double getCashFareAmount() {
		return cashFareAmount;
	}

	public void setCashFareAmount(Double cashFareAmount) {
		this.cashFareAmount = cashFareAmount;
	}

	public Double getExpectedRevenueAmount() {
		return expectedRevenueAmount;
	}

	public void setExpectedRevenueAmount(Double expectedRevenueAmount) {
		this.expectedRevenueAmount = expectedRevenueAmount;
	}

	public Double getPostedFareAmount() {
		return postedFareAmount;
	}

	public void setPostedFareAmount(Double postedFareAmount) {
		this.postedFareAmount = postedFareAmount;
	}

	public String getIsInTxPending() {
		return isInTxPending;
	}

	public void setIsInTxPending(String isInTxPending) {
		this.isInTxPending = isInTxPending;
	}

	@Override
	public String toString() {
		return "AwayTransaction [etcCorrReasonId=" + etcCorrReasonId + ", etcTrxSerialNum=" + etcTrxSerialNum
				+ ", etcRevenueDate=" + etcRevenueDate + ", etcFacAgency=" + etcFacAgency + ", etcTrxType=" + etcTrxType
				+ ", etcEntryDate=" + etcEntryDate + ", etcEntryTime=" + etcEntryTime + ", etcEntryPlaza="
				+ etcEntryPlaza + ", etcEntryLane=" + etcEntryLane + ", etcTagAgency=" + etcTagAgency
				+ ", etcTagSerialNumber=" + etcTagSerialNumber + ", etcReadPerformance=" + etcReadPerformance
				+ ", etcWritePerf=" + etcWritePerf + ", etcTagPgmStatus=" + etcTagPgmStatus + ", etcLaneMode="
				+ etcLaneMode + ", etcValidationStatus=" + etcValidationStatus + ", etcLicState=" + etcLicState
				+ ", etcLicNumber=" + etcLicNumber + ", etcClassCharged=" + etcClassCharged + ", etcActualAxles="
				+ etcActualAxles + ", etcExitSpeed=" + etcExitSpeed + ", etcOverSpeed=" + etcOverSpeed
				+ ", etcExitDate=" + etcExitDate + ", etcExitTime=" + etcExitTime + ", etcExitPlaza=" + etcExitPlaza
				+ ", etcExitLane=" + etcExitLane + ", etcDebitCredit=" + etcDebitCredit + ", etcAmountDue="
				+ etcAmountDue + ", laneTxId=" + laneTxId + ", txStatus=" + txStatus + ", atpFileId=" + atpFileId
				+ ", txSubType=" + txSubType + ", txType=" + txType + ", txExternRefNo=" + txExternRefNo
				+ ", txSeqNumber=" + txSeqNumber + ", externFileId=" + externFileId + ", plazaAgencyId=" + plazaAgencyId
				+ ", plazaId=" + plazaId + ", laneId=" + laneId + ", txTimestamp=" + txTimestamp + ", txModSeq="
				+ txModSeq + ", tillSystemType=" + tillSystemType + ", laneMode=" + laneMode + ", laneType=" + laneType
				+ ", laneState=" + laneState + ", laneHealth=" + laneHealth + ", collectorId=" + collectorId
				+ ", tourSegmentId=" + tourSegmentId + ", entryDataSource=" + entryDataSource + ", entryLaneId="
				+ entryLaneId + ", entryPlazaId=" + entryPlazaId + ", entryTimestamp=" + entryTimestamp
				+ ", entryTxSeqNumber=" + entryTxSeqNumber + ", entryVehicleSpeed=" + entryVehicleSpeed
				+ ", laneTxStatus=" + laneTxStatus + ", lanetxType=" + lanetxType + ", tollRevenueType="
				+ tollRevenueType + ", actualClass=" + actualClass + ", actualAxles=" + actualAxles
				+ ", actualExtraAxles=" + actualExtraAxles + ", collectorClass=" + collectorClass + ", collectorAxles="
				+ collectorAxles + ", preClassClass=" + preClassClass + ", preClassAxles=" + preClassAxles
				+ ", postClassClass=" + postClassClass + ", postClassAxles=" + postClassAxles + ", forwardAxles="
				+ forwardAxles + ", reverseAxles=" + reverseAxles + ", fullFareAmount=" + fullFareAmount
				+ ", discountedAmount=" + discountedAmount + ", collectedAmount=" + collectedAmount
				+ ", unrealizedAmount=" + unrealizedAmount + ", isDiscountable=" + isDiscountable + ", isMedianFare="
				+ isMedianFare + ", isPeak=" + isPeak + ", priceScheduleId=" + priceScheduleId + ", vehicleSpeed="
				+ vehicleSpeed + ", receiptIssued=" + receiptIssued + ", deviceNo=" + deviceNo + ", accountType="
				+ accountType + ", deviceCodedClass=" + deviceCodedClass + ", deviceAgencyClass=" + deviceAgencyClass
				+ ", deviceIagClass=" + deviceIagClass + ", deviceAxles=" + deviceAxles + ", etcAccountId="
				+ etcAccountId + ", accountAgencyId=" + accountAgencyId + ", readAviClass=" + readAviClass
				+ ", readAviAxles=" + readAviAxles + ", deviceProgramStatus=" + deviceProgramStatus
				+ ", bufferedReadFlag=" + bufferedReadFlag + ", laneDeviceStatus=" + laneDeviceStatus
				+ ", postDeviceStatus=" + postDeviceStatus + ", preTxnBalance=" + preTxnBalance + ", planTypeId="
				+ planTypeId + ", etcTxStatus=" + etcTxStatus + ", speedViolFlag=" + speedViolFlag + ", imageTaken="
				+ imageTaken + ", plateCountry=" + plateCountry + ", plateState=" + plateState + ", plateNumber="
				+ plateNumber + ", revenueDate=" + revenueDate + ", postedDate=" + postedDate + ", isReversed="
				+ isReversed + ", corrReasonId=" + corrReasonId + ", reconDate=" + reconDate + ", reconStatusInd="
				+ reconStatusInd + ", reconSubCodeInd=" + reconSubCodeInd + ", externFileDate=" + externFileDate
				+ ", mileage=" + mileage + ", deviceReadCount=" + deviceReadCount + ", deviceWriteCount="
				+ deviceWriteCount + ", entryDeviceReadCount=" + entryDeviceReadCount + ", entryDeviceWriteCount="
				+ entryDeviceWriteCount + ", depositId=" + depositId + ", txDate=" + txDate + ", cscLookupKey="
				+ cscLookupKey + ", updateTs=" + updateTs + ", tollSystemType=" + tollSystemType + ", aetFlag="
				+ aetFlag + ", eventTimestamp=" + eventTimestamp + ", eventType=" + eventType + ", prevViolTxStatus="
				+ prevViolTxStatus + ", violTxStatus=" + violTxStatus + ", violType=" + violType + ", reciprocityTrx="
				+ reciprocityTrx + ", videoFareAmount=" + videoFareAmount + ", etcFareAmount=" + etcFareAmount
				+ ", cashFareAmount=" + cashFareAmount + ", expectedRevenueAmount=" + expectedRevenueAmount
				+ ", postedFareAmount=" + postedFareAmount + ", dstAtpFileId=" + dstAtpFileId + ", isInTxPending="
				+ isInTxPending + "]";
	}
	
}
