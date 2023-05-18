package com.conduent.tpms.unmatched.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import com.google.gson.annotations.Expose;

/**
 * Common Classification Dto
 * 
 * 
 *
 */
public class TransactionDto {

	
	private Long laneTxId;

	
	private String txExternRefNo;

	
	private Long txSeqNumber;

	
	private Long externFileId;

	
	private Integer plazaAgencyId;

	
	private Integer plazaId;

	
	private Integer laneId;

	
	// private String txTimestamp;
	// private ZonedDateTime txTimestamp;
	private OffsetDateTime txTimestamp;

	
	private Integer txModSeq;

	
	private String txTypeInd;

	
	private String txSubtypeInd;

	
	private String tillSystemType;

	
	private Integer laneMode;

	
	private Long laneType;// Not in transation VO

	
	private Integer laneState;

	
	private Integer laneHealth;//

	
	private Long collectorId;

	
	private Long tourSegmentId;// Not in Transaction vo

	
	private String entryDataSource;

	
	private Integer entryLaneId;

	
	private Integer entryPlazaId;

	
	// private String entryTimestamp;
	// private ZonedDateTime entryTimestamp;
	private OffsetDateTime entryTimestamp;

	
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

	
	private Integer postClassClass;// Not in Transaction vo

	
	private Integer postClassAxles;// Not in Transaction vo

	
	private Integer forwardAxles;

	
	private Integer reverseAxles;

	
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

	
	private Integer deviceCodedClass;// Not found

	
	private Integer deviceAgencyClass;// Not found

	
	private Integer deviceIagClass;

	
	private Integer deviceAxles;

	
	private Long etcAccountId;

	
	private Integer accountAgencyId;

	
	private Integer readAviClass;

	
	private Integer readAviAxles;

	
	private String deviceProgramStatus;

	
	private String bufferReadFlag;// Not fund

	
	private Long laneDeviceStatus;// Not found

	
	private Integer postDeviceStatus;

	
	private Double preTxnBalance;

	
	private Integer planTypeId;

//	
//	private Integer etcTxStatus;

	// sakshi
	
	private Integer txStatus;

	
	private String speedViolFlag;

	public Integer getTxStatus() {
		return txStatus;
	}

	public void setTxStatus(Integer txStatus) {
		this.txStatus = txStatus;
	}

	
	private String imageTaken;

	
	private String plateCountry;

	
	private String plateState;// Not found

	
	private String plateNumber;// Not found

	
	private String revenueDate;

	
	private LocalDate postedDate;

	
	private Long atpFileId;// ??

	
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

	
	private String txDate;

	
	private String cscLookupKey;

	
	private String updateTs;

	
	private String tollSystemType;

	
	private String aetFlag;

	
	private String eventTimestamp = LocalDateTime.now().toString();

	
	private Integer eventType = 1;

	
	private Integer prevViolTxStatus = 0;
	
	private Integer violTxStatus = 3;
	
	private Integer violType = 3;
	
	private String reciprocityTrx;

	private Integer sourceSystem = 0;

	
	private Double etcFareAmount;
	
	private Double postedFareAmount;
	
	private Double expectedRevenueAmount;
	
	private Double videoFareAmount;
	
	private Double cashFareAmount;

	
	private Double discountedAmount;
	
	private Double discountedAmount2;
	
	private String isViolation;

	public TransactionDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TransactionDto(Long laneTxId, String txExternRefNo, Long txSeqNumber, Long externFileId,
			Integer plazaAgencyId, Integer plazaId, Integer laneId, OffsetDateTime txTimestamp, Integer txModSeq,
			String txTypeInd, String txSubtypeInd, String tillSystemType, Integer laneMode, Long laneType,
			Integer laneState, Integer laneHealth, Long collectorId, Long tourSegmentId, String entryDataSource,
			Integer entryLaneId, Integer entryPlazaId, OffsetDateTime entryTimestamp, Integer entryTxSeqNumber,
			Integer entryVehicleSpeed, Integer laneTxStatus, Integer lanetxType, Integer tollRevenueType,
			Integer actualClass, Integer actualAxles, Integer actualExtraAxles, Integer collectorClass,
			Integer collectorAxles, Integer preClassClass, Integer preClassAxles, Integer postClassClass,
			Integer postClassAxles, Integer forwardAxles, Integer reverseAxles, Double collectedAmount,
			Double unrealizedAmount, String isDiscountable, String isMedianFare, String isPeak, Integer priceScheduleId,
			Integer vehicleSpeed, Integer receiptIssued, String deviceNo, Integer accountType, Integer deviceCodedClass,
			Integer deviceAgencyClass, Integer deviceIagClass, Integer deviceAxles, Long etcAccountId,
			Integer accountAgencyId, Integer readAviClass, Integer readAviAxles, String deviceProgramStatus,
			String bufferReadFlag, Long laneDeviceStatus, Integer postDeviceStatus, Double preTxnBalance,
			Integer planTypeId, Integer txStatus, String speedViolFlag, String imageTaken, String plateCountry,
			String plateState, String plateNumber, String revenueDate, LocalDate postedDate, Long atpFileId,
			String isReversed, Integer corrReasonId, LocalDate reconDate, Integer reconStatusInd,
			Integer reconSubCodeInd, LocalDate externFileDate, Integer mileage, Integer deviceReadCount,
			Integer deviceWriteCount, Integer entryDeviceReadCount, Integer entryDeviceWriteCount, String depositId,
			String txDate, String cscLookupKey, String updateTs, String tollSystemType, String aetFlag,
			String eventTimestamp, Integer eventType, Integer prevViolTxStatus, Integer violTxStatus, Integer violType,
			String reciprocityTrx, Integer sourceSystem, Double etcFareAmount, Double postedFareAmount,
			Double expectedRevenueAmount, Double videoFareAmount, Double cashFareAmount, Double discountedAmount,
			Double discountedAmount2, String isViolation) {
		super();
		this.laneTxId = laneTxId;
		this.txExternRefNo = txExternRefNo;
		this.txSeqNumber = txSeqNumber;
		this.externFileId = externFileId;
		this.plazaAgencyId = plazaAgencyId;
		this.plazaId = plazaId;
		this.laneId = laneId;
		this.txTimestamp = txTimestamp;
		this.txModSeq = txModSeq;
		this.txTypeInd = txTypeInd;
		this.txSubtypeInd = txSubtypeInd;
		this.tillSystemType = tillSystemType;
		this.laneMode = laneMode;
		this.laneType = laneType;
		this.laneState = laneState;
		this.laneHealth = laneHealth;
		this.collectorId = collectorId;
		this.tourSegmentId = tourSegmentId;
		this.entryDataSource = entryDataSource;
		this.entryLaneId = entryLaneId;
		this.entryPlazaId = entryPlazaId;
		this.entryTimestamp = entryTimestamp;
		this.entryTxSeqNumber = entryTxSeqNumber;
		this.entryVehicleSpeed = entryVehicleSpeed;
		this.laneTxStatus = laneTxStatus;
		this.lanetxType = lanetxType;
		this.tollRevenueType = tollRevenueType;
		this.actualClass = actualClass;
		this.actualAxles = actualAxles;
		this.actualExtraAxles = actualExtraAxles;
		this.collectorClass = collectorClass;
		this.collectorAxles = collectorAxles;
		this.preClassClass = preClassClass;
		this.preClassAxles = preClassAxles;
		this.postClassClass = postClassClass;
		this.postClassAxles = postClassAxles;
		this.forwardAxles = forwardAxles;
		this.reverseAxles = reverseAxles;
		this.collectedAmount = collectedAmount;
		this.unrealizedAmount = unrealizedAmount;
		this.isDiscountable = isDiscountable;
		this.isMedianFare = isMedianFare;
		this.isPeak = isPeak;
		this.priceScheduleId = priceScheduleId;
		this.vehicleSpeed = vehicleSpeed;
		this.receiptIssued = receiptIssued;
		this.deviceNo = deviceNo;
		this.accountType = accountType;
		this.deviceCodedClass = deviceCodedClass;
		this.deviceAgencyClass = deviceAgencyClass;
		this.deviceIagClass = deviceIagClass;
		this.deviceAxles = deviceAxles;
		this.etcAccountId = etcAccountId;
		this.accountAgencyId = accountAgencyId;
		this.readAviClass = readAviClass;
		this.readAviAxles = readAviAxles;
		this.deviceProgramStatus = deviceProgramStatus;
		this.bufferReadFlag = bufferReadFlag;
		this.laneDeviceStatus = laneDeviceStatus;
		this.postDeviceStatus = postDeviceStatus;
		this.preTxnBalance = preTxnBalance;
		this.planTypeId = planTypeId;
		this.txStatus = txStatus;
		this.speedViolFlag = speedViolFlag;
		this.imageTaken = imageTaken;
		this.plateCountry = plateCountry;
		this.plateState = plateState;
		this.plateNumber = plateNumber;
		this.revenueDate = revenueDate;
		this.postedDate = postedDate;
		this.atpFileId = atpFileId;
		this.isReversed = isReversed;
		this.corrReasonId = corrReasonId;
		this.reconDate = reconDate;
		this.reconStatusInd = reconStatusInd;
		this.reconSubCodeInd = reconSubCodeInd;
		this.externFileDate = externFileDate;
		this.mileage = mileage;
		this.deviceReadCount = deviceReadCount;
		this.deviceWriteCount = deviceWriteCount;
		this.entryDeviceReadCount = entryDeviceReadCount;
		this.entryDeviceWriteCount = entryDeviceWriteCount;
		this.depositId = depositId;
		this.txDate = txDate;
		this.cscLookupKey = cscLookupKey;
		this.updateTs = updateTs;
		this.tollSystemType = tollSystemType;
		this.aetFlag = aetFlag;
		this.eventTimestamp = eventTimestamp;
		this.eventType = eventType;
		this.prevViolTxStatus = prevViolTxStatus;
		this.violTxStatus = violTxStatus;
		this.violType = violType;
		this.reciprocityTrx = reciprocityTrx;
		this.sourceSystem = sourceSystem;
		this.etcFareAmount = etcFareAmount;
		this.postedFareAmount = postedFareAmount;
		this.expectedRevenueAmount = expectedRevenueAmount;
		this.videoFareAmount = videoFareAmount;
		this.cashFareAmount = cashFareAmount;
		this.discountedAmount = discountedAmount;
		this.discountedAmount2 = discountedAmount2;
		this.isViolation = isViolation;
	}

	public Long getLaneTxId() {
		return laneTxId;
	}

	public void setLaneTxId(Long laneTxId) {
		this.laneTxId = laneTxId;
	}

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

	public OffsetDateTime getTxTimestamp() {
		return txTimestamp;
	}

	public void setTxTimestamp(OffsetDateTime txTimestamp) {
		this.txTimestamp = txTimestamp;
	}

	public Integer getTxModSeq() {
		return txModSeq;
	}

	public void setTxModSeq(Integer txModSeq) {
		this.txModSeq = txModSeq;
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

	public OffsetDateTime getEntryTimestamp() {
		return entryTimestamp;
	}

	public void setEntryTimestamp(OffsetDateTime entryTimestamp) {
		this.entryTimestamp = entryTimestamp;
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

	public String getBufferReadFlag() {
		return bufferReadFlag;
	}

	public void setBufferReadFlag(String bufferReadFlag) {
		this.bufferReadFlag = bufferReadFlag;
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

	public String getRevenueDate() {
		return revenueDate;
	}

	public void setRevenueDate(String revenueDate) {
		this.revenueDate = revenueDate;
	}

	public LocalDate getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(LocalDate postedDate) {
		this.postedDate = postedDate;
	}

	public Long getAtpFileId() {
		return atpFileId;
	}

	public void setAtpFileId(Long atpFileId) {
		this.atpFileId = atpFileId;
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

	public String getTxDate() {
		return txDate;
	}

	public void setTxDate(String txDate) {
		this.txDate = txDate;
	}

	public String getCscLookupKey() {
		return cscLookupKey;
	}

	public void setCscLookupKey(String cscLookupKey) {
		this.cscLookupKey = cscLookupKey;
	}

	public String getUpdateTs() {
		return updateTs;
	}

	public void setUpdateTs(String updateTs) {
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

	public Integer getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(Integer sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public Double getEtcFareAmount() {
		return etcFareAmount;
	}

	public void setEtcFareAmount(Double etcFareAmount) {
		this.etcFareAmount = etcFareAmount;
	}

	public Double getPostedFareAmount() {
		return postedFareAmount;
	}

	public void setPostedFareAmount(Double postedFareAmount) {
		this.postedFareAmount = postedFareAmount;
	}

	public Double getExpectedRevenueAmount() {
		return expectedRevenueAmount;
	}

	public void setExpectedRevenueAmount(Double expectedRevenueAmount) {
		this.expectedRevenueAmount = expectedRevenueAmount;
	}

	public Double getVideoFareAmount() {
		return videoFareAmount;
	}

	public void setVideoFareAmount(Double videoFareAmount) {
		this.videoFareAmount = videoFareAmount;
	}

	public Double getCashFareAmount() {
		return cashFareAmount;
	}

	public void setCashFareAmount(Double cashFareAmount) {
		this.cashFareAmount = cashFareAmount;
	}

	public Double getDiscountedAmount() {
		return discountedAmount;
	}

	public void setDiscountedAmount(Double discountedAmount) {
		this.discountedAmount = discountedAmount;
	}

	public Double getDiscountedAmount2() {
		return discountedAmount2;
	}

	public void setDiscountedAmount2(Double discountedAmount2) {
		this.discountedAmount2 = discountedAmount2;
	}

	public String getIsViolation() {
		return isViolation;
	}

	public void setIsViolation(String isViolation) {
		this.isViolation = isViolation;
	}

	@Override
	public String toString() {
		return "TransactionDto [laneTxId=" + laneTxId + ", txExternRefNo=" + txExternRefNo + ", txSeqNumber="
				+ txSeqNumber + ", externFileId=" + externFileId + ", plazaAgencyId=" + plazaAgencyId + ", plazaId="
				+ plazaId + ", laneId=" + laneId + ", txTimestamp=" + txTimestamp + ", txModSeq=" + txModSeq
				+ ", txTypeInd=" + txTypeInd + ", txSubtypeInd=" + txSubtypeInd + ", tillSystemType=" + tillSystemType
				+ ", laneMode=" + laneMode + ", laneType=" + laneType + ", laneState=" + laneState + ", laneHealth="
				+ laneHealth + ", collectorId=" + collectorId + ", tourSegmentId=" + tourSegmentId
				+ ", entryDataSource=" + entryDataSource + ", entryLaneId=" + entryLaneId + ", entryPlazaId="
				+ entryPlazaId + ", entryTimestamp=" + entryTimestamp + ", entryTxSeqNumber=" + entryTxSeqNumber
				+ ", entryVehicleSpeed=" + entryVehicleSpeed + ", laneTxStatus=" + laneTxStatus + ", lanetxType="
				+ lanetxType + ", tollRevenueType=" + tollRevenueType + ", actualClass=" + actualClass
				+ ", actualAxles=" + actualAxles + ", actualExtraAxles=" + actualExtraAxles + ", collectorClass="
				+ collectorClass + ", collectorAxles=" + collectorAxles + ", preClassClass=" + preClassClass
				+ ", preClassAxles=" + preClassAxles + ", postClassClass=" + postClassClass + ", postClassAxles="
				+ postClassAxles + ", forwardAxles=" + forwardAxles + ", reverseAxles=" + reverseAxles
				+ ", collectedAmount=" + collectedAmount + ", unrealizedAmount=" + unrealizedAmount
				+ ", isDiscountable=" + isDiscountable + ", isMedianFare=" + isMedianFare + ", isPeak=" + isPeak
				+ ", priceScheduleId=" + priceScheduleId + ", vehicleSpeed=" + vehicleSpeed + ", receiptIssued="
				+ receiptIssued + ", deviceNo=" + deviceNo + ", accountType=" + accountType + ", deviceCodedClass="
				+ deviceCodedClass + ", deviceAgencyClass=" + deviceAgencyClass + ", deviceIagClass=" + deviceIagClass
				+ ", deviceAxles=" + deviceAxles + ", etcAccountId=" + etcAccountId + ", accountAgencyId="
				+ accountAgencyId + ", readAviClass=" + readAviClass + ", readAviAxles=" + readAviAxles
				+ ", deviceProgramStatus=" + deviceProgramStatus + ", bufferReadFlag=" + bufferReadFlag
				+ ", laneDeviceStatus=" + laneDeviceStatus + ", postDeviceStatus=" + postDeviceStatus
				+ ", preTxnBalance=" + preTxnBalance + ", planTypeId=" + planTypeId + ", txStatus=" + txStatus
				+ ", speedViolFlag=" + speedViolFlag + ", imageTaken=" + imageTaken + ", plateCountry=" + plateCountry
				+ ", plateState=" + plateState + ", plateNumber=" + plateNumber + ", revenueDate=" + revenueDate
				+ ", postedDate=" + postedDate + ", atpFileId=" + atpFileId + ", isReversed=" + isReversed
				+ ", corrReasonId=" + corrReasonId + ", reconDate=" + reconDate + ", reconStatusInd=" + reconStatusInd
				+ ", reconSubCodeInd=" + reconSubCodeInd + ", externFileDate=" + externFileDate + ", mileage=" + mileage
				+ ", deviceReadCount=" + deviceReadCount + ", deviceWriteCount=" + deviceWriteCount
				+ ", entryDeviceReadCount=" + entryDeviceReadCount + ", entryDeviceWriteCount=" + entryDeviceWriteCount
				+ ", depositId=" + depositId + ", txDate=" + txDate + ", cscLookupKey=" + cscLookupKey + ", updateTs="
				+ updateTs + ", tollSystemType=" + tollSystemType + ", aetFlag=" + aetFlag + ", eventTimestamp="
				+ eventTimestamp + ", eventType=" + eventType + ", prevViolTxStatus=" + prevViolTxStatus
				+ ", violTxStatus=" + violTxStatus + ", violType=" + violType + ", reciprocityTrx=" + reciprocityTrx
				+ ", sourceSystem=" + sourceSystem + ", etcFareAmount=" + etcFareAmount + ", postedFareAmount="
				+ postedFareAmount + ", expectedRevenueAmount=" + expectedRevenueAmount + ", videoFareAmount="
				+ videoFareAmount + ", cashFareAmount=" + cashFareAmount + ", discountedAmount=" + discountedAmount
				+ ", discountedAmount2=" + discountedAmount2 + ", isViolation=" + isViolation + "]";
	}

	
}
