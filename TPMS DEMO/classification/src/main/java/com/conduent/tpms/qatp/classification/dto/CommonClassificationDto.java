package com.conduent.tpms.qatp.classification.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.google.gson.annotations.Expose;

/**
 * Common Classification Dto
 * 
 * 
 *
 */
public class CommonClassificationDto {

	@Expose(serialize = true, deserialize = true)
	private Long laneTxId;

	@Expose(serialize = true, deserialize = true)
	private String txExternRefNo;

	@Expose(serialize = true, deserialize = true)
	private Long txSeqNumber;

	@Expose(serialize = true, deserialize = true)
	private Long externFileId;

	@Expose(serialize = true, deserialize = true)
	private Integer plazaAgencyId;

	@Expose(serialize = true, deserialize = true)
	private Integer plazaId;

	@Expose(serialize = true, deserialize = true)
	private Integer laneId;

	@Expose(serialize = true, deserialize = true)
	//private String txTimestamp;
	//private ZonedDateTime txTimestamp;
	private OffsetDateTime txTimestamp;

	@Expose(serialize = true, deserialize = true)
	private Integer txModSeq;

	@Expose(serialize = true, deserialize = true)
	private String txTypeInd;

	@Expose(serialize = true, deserialize = true)
	private String txSubtypeInd;

	@Expose(serialize = true, deserialize = true)
	private String tillSystemType;

	@Expose(serialize = true, deserialize = true)
	private Integer laneMode;

	@Expose(serialize = true, deserialize = true)
	private Long laneType;// Not in transation VO

	@Expose(serialize = true, deserialize = true)
	private Integer laneState;

	@Expose(serialize = true, deserialize = true)
	private Integer laneHealth;//

	@Expose(serialize = true, deserialize = true)
	private Long collectorId;

	@Expose(serialize = true, deserialize = true)
	private Long tourSegmentId;// Not in Transaction vo

	@Expose(serialize = true, deserialize = true)
	private String entryDataSource;

	@Expose(serialize = true, deserialize = true)
	private Integer entryLaneId;

	@Expose(serialize = true, deserialize = true)
	private Integer entryPlazaId;

	@Expose(serialize = true, deserialize = true)
	//private String entryTimestamp;
	//private ZonedDateTime entryTimestamp;
	private OffsetDateTime entryTimestamp;
	
	@Expose(serialize = true, deserialize = true)
	private Integer entryTxSeqNumber;

	@Expose(serialize = true, deserialize = true)
	private Integer entryVehicleSpeed;

	@Expose(serialize = true, deserialize = true)
	private Integer laneTxStatus;

	@Expose(serialize = true, deserialize = true)
	private Integer lanetxType;

	@Expose(serialize = true, deserialize = true)
	private Integer tollRevenueType;

	@Expose(serialize = true, deserialize = true)
	private Integer actualClass;

	@Expose(serialize = true, deserialize = true)
	private Integer actualAxles;

	@Expose(serialize = true, deserialize = true)
	private Integer actualExtraAxles;

	@Expose(serialize = true, deserialize = true)
	private Integer collectorClass;

	@Expose(serialize = true, deserialize = true)
	private Integer collectorAxles;

	@Expose(serialize = true, deserialize = true)
	private Integer preClassClass;

	@Expose(serialize = true, deserialize = true)
	private Integer preClassAxles;

	@Expose(serialize = true, deserialize = true)
	private Integer postClassClass;// Not in Transaction vo

	@Expose(serialize = true, deserialize = true)
	private Integer postClassAxles;// Not in Transaction vo

	@Expose(serialize = true, deserialize = true)
	private Integer forwardAxles;

	@Expose(serialize = true, deserialize = true)
	private Integer reverseAxles;

	@Expose(serialize = true, deserialize = true)
	private Double collectedAmount;

	@Expose(serialize = true, deserialize = true)
	private Double unrealizedAmount;

	@Expose(serialize = true, deserialize = true)
	private String isDiscountable;

	@Expose(serialize = true, deserialize = true)
	private String isMedianFare;

	@Expose(serialize = true, deserialize = true)
	private String isPeak;

	@Expose(serialize = true, deserialize = true)
	private Integer priceScheduleId;

	@Expose(serialize = true, deserialize = true)
	private Integer vehicleSpeed;

	@Expose(serialize = true, deserialize = true)
	private Integer receiptIssued;

	@Expose(serialize = true, deserialize = true)
	private String deviceNo;

	@Expose(serialize = true, deserialize = true)
	private Integer accountType;

	@Expose(serialize = true, deserialize = true)
	private Integer deviceCodedClass;// Not found

	@Expose(serialize = true, deserialize = true)
	private Integer deviceAgencyClass;// Not found

	@Expose(serialize = true, deserialize = true)
	private Integer deviceIagClass;

	@Expose(serialize = true, deserialize = true)
	private Integer deviceAxles;

	@Expose(serialize = true, deserialize = true)
	private Long etcAccountId;

	@Expose(serialize = true, deserialize = true)
	private Integer accountAgencyId;

	@Expose(serialize = true, deserialize = true)
	private Integer readAviClass;

	@Expose(serialize = true, deserialize = true)
	private Integer readAviAxles;

	@Expose(serialize = true, deserialize = true)
	private String deviceProgramStatus;

	@Expose(serialize = true, deserialize = true)
	private String bufferReadFlag;// Not fund

	@Expose(serialize = true, deserialize = true)
	private Long laneDeviceStatus;// Not found

	@Expose(serialize = true, deserialize = true)
	private Integer postDeviceStatus;

	@Expose(serialize = true, deserialize = true)
	private Double preTxnBalance;

	@Expose(serialize = true, deserialize = true)
	private Integer planTypeId;

//	@Expose(serialize = true, deserialize = true)
//	private Integer etcTxStatus;

	//sakshi
	@Expose(serialize = true, deserialize = true)
	private Integer txStatus;
	
	@Expose(serialize = true, deserialize = true)
	private String speedViolFlag;

	public Integer getTxStatus() {
		return txStatus;
	}

	public void setTxStatus(Integer txStatus) {
		this.txStatus = txStatus;
	}

	@Expose(serialize = true, deserialize = true)
	private String imageTaken;

	@Expose(serialize = true, deserialize = true)
	private String plateCountry;

	@Expose(serialize = true, deserialize = true)
	private String plateState;// Not found

	@Expose(serialize = true, deserialize = true)
	private String plateNumber;// Not found

	@Expose(serialize = true, deserialize = true)
	private String revenueDate;

	@Expose(serialize = true, deserialize = true)
	private LocalDate postedDate;

	@Expose(serialize = true, deserialize = true)
	private Long atpFileId;// ??

	@Expose(serialize = true, deserialize = true)
	private String isReversed;

	@Expose(serialize = true, deserialize = true)
	private Integer corrReasonId;

	@Expose(serialize = true, deserialize = true)
	private LocalDate reconDate;

	@Expose(serialize = true, deserialize = true)
	private Integer reconStatusInd;

	@Expose(serialize = true, deserialize = true)
	private Integer reconSubCodeInd;

	@Expose(serialize = true, deserialize = true)
	private LocalDate externFileDate;

	@Expose(serialize = true, deserialize = true)
	private Integer mileage;

	@Expose(serialize = true, deserialize = true)
	private Integer deviceReadCount;

	@Expose(serialize = true, deserialize = true)
	private Integer deviceWriteCount;

	@Expose(serialize = true, deserialize = true)
	private Integer entryDeviceReadCount;

	@Expose(serialize = true, deserialize = true)
	private Integer entryDeviceWriteCount;

	@Expose(serialize = true, deserialize = true)
	private String depositId;

	@Expose(serialize = true, deserialize = true)
	private String txDate;

	@Expose(serialize = true, deserialize = true)
	private String cscLookupKey;

	@Expose(serialize = true, deserialize = true)
	private String updateTs;

	@Expose(serialize = true, deserialize = true)
	private String tollSystemType;

	@Expose(serialize = true, deserialize = true)
	private String aetFlag;

	@Expose(serialize = true, deserialize = true)
	//private String eventTimestamp = LocalDateTime.now().toString();
	private LocalDateTime eventTimestamp ;//= LocalDateTime.now();

	@Expose(serialize = true, deserialize = true)
	private Integer eventType = 1;

	@Expose(serialize = true, deserialize = true)
	private Integer prevViolTxStatus = 0;
	@Expose(serialize = true, deserialize = true)
	private Integer violTxStatus = 3;
	@Expose(serialize = true, deserialize = true)
	private Integer violType = 3;
	@Expose(serialize = true, deserialize = true)
	private String reciprocityTrx;

	private Integer sourceSystem = 0;

	@Expose(serialize = true, deserialize = true)
	private Double etcFareAmount;
	@Expose(serialize = true, deserialize = true)
	private Double postedFareAmount;
	@Expose(serialize = true, deserialize = true)
	private Double expectedRevenueAmount;
	@Expose(serialize = true, deserialize = true)
	private Double videoFareAmount;
	@Expose(serialize = true, deserialize = true)
	private Double cashFareAmount;
	
	@Expose(serialize = true, deserialize = true)
	private Double discountedAmount;
	@Expose(serialize = true, deserialize = true)
	private Double discountedAmount2;
	
	@Expose(serialize = true, deserialize = true)
	private Integer etcViolObserved;
	
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

	public void setEntryTimestamp(OffsetDateTime entryTimeStamp) {
		this.entryTimestamp = entryTimeStamp;
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

	public void setPriceScheduleId(Integer priceScheduledId) {
		this.priceScheduleId = priceScheduledId;
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

	public Integer getPostDeviceStatus() {
		return postDeviceStatus;
	}

	public void setPostDeviceStatus(Integer postDeviceStatus) {
		this.postDeviceStatus = postDeviceStatus;
	}

	public Integer getPlanTypeId() {
		return planTypeId;
	}

	public void setPlanTypeId(Integer planTypeId) {
		this.planTypeId = planTypeId;
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

	public void setPlateState(String plateStat) {
		this.plateState = plateStat;
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

	public void setDepositId(String depositeId) {
		this.depositId = depositeId;
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

//	public String getEventTimestamp() {
//		return eventTimestamp;
//	}
//
//	public void setEventTimestamp(String eventTimestamp) {
//		this.eventTimestamp = eventTimestamp;
//	}
	
	public LocalDateTime getEventTimestamp() {
		return eventTimestamp;
	}

	public void setEventTimestamp(LocalDateTime offsetDateTime) {
		this.eventTimestamp = offsetDateTime;
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

	public Long getLaneType() {
		return laneType;
	}

	public void setLaneType(Long laneType) {
		this.laneType = laneType;
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

	public Double getPreTxnBalance() {
		return preTxnBalance;
	}

	public void setPreTxnBalance(Double preTxnBalance) {
		this.preTxnBalance = preTxnBalance;
	}

	public String getSpeedViolFlag() {
		return speedViolFlag;
	}

	public void setSpeedViolFlag(String speedViolFlag) {
		this.speedViolFlag = speedViolFlag;
	}

	public Long getAtpFileId() {
		return atpFileId;
	}

	public void setAtpFileId(Long atpFileId) {
		this.atpFileId = atpFileId;
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
	public Integer getEtcViolObserved() {
		return etcViolObserved;
	}

	public void setEtcViolObserved(Integer etcViolObserved) {
		this.etcViolObserved = etcViolObserved;
	}

	@Override
	public String toString() {
		return "CommonClassificationDto [laneTxId=" + laneTxId + ", txExternRefNo=" + txExternRefNo + ", txSeqNumber="
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
				+ ", discountedAmount2=" + discountedAmount2 + ", etcViolObserved=" + etcViolObserved + "]";
	}

	
}
