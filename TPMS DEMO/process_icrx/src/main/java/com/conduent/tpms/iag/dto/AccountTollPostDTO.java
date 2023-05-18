package com.conduent.tpms.iag.dto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.annotations.Expose;

public class AccountTollPostDTO {
	
	private static final Logger log = LoggerFactory.getLogger(AccountTollPostDTO.class);

	@Expose(serialize = true, deserialize = true)
	private Long laneTxId;

	@Expose(serialize = true, deserialize = true)
	private Integer txStatus;

	@Expose(serialize = true, deserialize = true)
	private String txTypeInd;

	@Expose(serialize = true, deserialize = true)
	private String txSubtypeInd;

	@Expose(serialize = true, deserialize = true)
	private LocalDate txDate;

	@Expose(serialize = true, deserialize = true)
	//private LocalDateTime txTimestamp;
	private OffsetDateTime txTimestamp;

	@Expose(serialize = true, deserialize = true)
	private String txExternRefNo;

	@Expose(serialize = true, deserialize = true)
	private Long externFileId;

	@Expose(serialize = true, deserialize = true)
	private LocalDate externFileDate;

	@Expose(serialize = true, deserialize = true)
	private Integer txModSeq;

	@Expose(serialize = true, deserialize = true)
	private Long txSeqNumber;

	@Expose(serialize = true, deserialize = true)
	private Integer entryLaneId;

	@Expose(serialize = true, deserialize = true)
	private Integer entryPlazaId;

	@Expose(serialize = true, deserialize = true)
	//private LocalDateTime entryTimestamp;
	private OffsetDateTime entryTimestamp;

	@Expose(serialize = true, deserialize = true)
	private Integer entryTxSeqNumber;

	@Expose(serialize = true, deserialize = true)
	private Integer entryVehicleSpeed;

	@Expose(serialize = true, deserialize = true)
	private String entryDataSource;

	@Expose(serialize = true, deserialize = true)
	private Integer entryDeviceReadCount;

	@Expose(serialize = true, deserialize = true)
	private Integer entryDeviceWriteCount;

	@Expose(serialize = true, deserialize = true)
	private Integer plazaAgencyId;

	@Expose(serialize = true, deserialize = true)
	private Integer plazaId;

	@Expose(serialize = true, deserialize = true)
	private Integer laneId;

	@Expose(serialize = true, deserialize = true)
	private Integer laneMode;

	@Expose(serialize = true, deserialize = true)
	private Integer laneType;

	@Expose(serialize = true, deserialize = true)
	private Integer laneState;

	@Expose(serialize = true, deserialize = true)
	private Integer laneHealth;

	@Expose(serialize = true, deserialize = true)
	private Integer laneTxStatus;

	@Expose(serialize = true, deserialize = true)
	private Integer lanetxType;

	@Expose(serialize = true, deserialize = true)
	private String laneDeviceStatus;

	@Expose(serialize = true, deserialize = true)
	private Integer collectorId;

	@Expose(serialize = true, deserialize = true)
	private Integer collectorClass;

	@Expose(serialize = true, deserialize = true)
	private Integer collectorAxles;

	@Expose(serialize = true, deserialize = true)
	private Integer tourSegmentId;

	@Expose(serialize = true, deserialize = true)
	private String tollSystemType;

	@Expose(serialize = true, deserialize = true)
	private Integer tollRevenueType;

	@Expose(serialize = true, deserialize = true)
	private Integer actualClass;

	@Expose(serialize = true, deserialize = true)
	private Integer actualAxles;

	@Expose(serialize = true, deserialize = true)
	private Integer actualExtraAxles;

	@Expose(serialize = true, deserialize = true)
	private Integer preclassClass;

	@Expose(serialize = true, deserialize = true)
	private Integer preclassAxles;

	@Expose(serialize = true, deserialize = true)
	private Integer postclassClass;

	@Expose(serialize = true, deserialize = true)
	private Integer postclassAxles;

	@Expose(serialize = true, deserialize = true)
	private Integer forwardAxles;

	@Expose(serialize = true, deserialize = true)
	private Integer reverseAxles;

	@Expose(serialize = true, deserialize = true)
	private Double videoFareAmount;

	@Expose(serialize = true, deserialize = true)
	private Double etcFareAmount;

	@Expose(serialize = true, deserialize = true)
	private Double cashFareAmount;

	@Expose(serialize = true, deserialize = true)
	private Double postedFareAmount;

	@Expose(serialize = true, deserialize = true)
	private Double collectedAmount;

	@Expose(serialize = true, deserialize = true)
	private Double unrealizedAmount;

	@Expose(serialize = true, deserialize = true)
	private Double expectedRevenueAmount;

	@Expose(serialize = true, deserialize = true)
	private Double preTxnBalance;

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
	private Integer deviceClass;

	@Expose(serialize = true, deserialize = true)
	private Integer deviceCodedClass;

	@Expose(serialize = true, deserialize = true)
	private Integer deviceAgencyClass;

	@Expose(serialize = true, deserialize = true)
	private Integer deviceIagClass;

	@Expose(serialize = true, deserialize = true)
	private Integer deviceAxles;

	@Expose(serialize = true, deserialize = true)
	private String deviceProgramStatus;

	@Expose(serialize = true, deserialize = true)
	private Long etcAccountId;

	@Expose(serialize = true, deserialize = true)
	private Integer accountType;

	@Expose(serialize = true, deserialize = true)
	private Integer accountAgencyId;

	@Expose(serialize = true, deserialize = true)
	private Integer readAviClass;

	@Expose(serialize = true, deserialize = true)
	private Integer readAviAxles;

	@Expose(serialize = true, deserialize = true)
	private String bufferReadFlag;

	@Expose(serialize = true, deserialize = true)
	private Integer postDeviceStatus;

	@Expose(serialize = true, deserialize = true)
	private Integer planTypeId;

	@Expose(serialize = true, deserialize = true)
	private String speedViolFlag;

	@Expose(serialize = true, deserialize = true)
	private String imageTaken;

	@Expose(serialize = true, deserialize = true)
	private String plateCountry;

	@Expose(serialize = true, deserialize = true)
	private String plateState;

	@Expose(serialize = true, deserialize = true)
	private String plateNumber;

	@Expose(serialize = true, deserialize = true)
	private LocalDate revenueDate;

	@Expose(serialize = true, deserialize = true)
	private LocalDate postedDate;

	@Expose(serialize = true, deserialize = true)
	private String atpFileId;

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
	private Integer mileage;

	@Expose(serialize = true, deserialize = true)
	private Integer deviceReadCount;

	@Expose(serialize = true, deserialize = true)
	private Integer deviceWriteCount;

	@Expose(serialize = true, deserialize = true)
	private String cscLookupKey;

	//@Expose(serialize = true, deserialize = true)
	private LocalDateTime updateTs;

	@Expose(serialize = true, deserialize = true)
	private String aetFlag = "N";

	//@Expose(serialize = true, deserialize = true)
	private LocalDateTime eventTimestamp = LocalDateTime.now();  //: TODO IBTS 

	@Expose(serialize = true, deserialize = true)
	private Integer eventType = 1;                                      //: TODO IBTS 

	@Expose(serialize = true, deserialize = true)
	private Integer prevViolTxStatus = 0;                             //: TODO IBTS 

	@Expose(serialize = true, deserialize = true)
	private Integer violTxStatus = 3;                                //: TODO IBTS 

	@Expose(serialize = true, deserialize = true)
	private Integer violType = 3;                                         //: TODO IBTS 

	@Expose(serialize = true, deserialize = true)
	private Integer fareTblId;                                         //: TODO IBTS 
	
	@Expose(serialize = true, deserialize = true)
	private String lprEnabled;
	
	@Expose(serialize = true, deserialize = true)
	private String postPaidFlag;
	
	@Expose(serialize = true, deserialize = true)
	private Long duplSerialNum;
	

	public Long getDuplSerialNum() {
		return duplSerialNum;
	}

	public void setDuplSerialNum(Long duplSerialNum) {
		this.duplSerialNum = duplSerialNum;
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

	public String getTollSystemType() {
		return tollSystemType;
	}

	public void setTollSystemType(String tollSystemType) {
		this.tollSystemType = tollSystemType;
	}

	public Integer getLaneMode() {
		return laneMode;
	}

	public void setLaneMode(Integer laneMode) {
		this.laneMode = laneMode;
	}

	public Integer getLaneType() {
		return laneType;
	}

	public void setLaneType(Integer laneType) {
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

	public Integer getCollectorId() {
		return collectorId;
	}

	public void setCollectorId(Integer collectorId) {
		this.collectorId = collectorId;
	}


	public OffsetDateTime getTxTimestamp() {
		return txTimestamp;
	}

	public void setTxTimestamp(OffsetDateTime txTimestamp) {
		this.txTimestamp = txTimestamp;
	}

	public OffsetDateTime getEntryTimestamp() {
		return entryTimestamp;
	}

	public void setEntryTimestamp(OffsetDateTime entryTimestamp) {
		this.entryTimestamp = entryTimestamp;
	}

	public Integer getTourSegmentId() {
		return tourSegmentId;
	}

	public void setTourSegmentId(Integer tourSegmentId) {
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

	public Integer getPreclassClass() {
		return preclassClass;
	}

	public void setPreclassClass(Integer preclassClass) {
		this.preclassClass = preclassClass;
	}

	public Integer getPreclassAxles() {
		return preclassAxles;
	}

	public void setPreclassAxles(Integer preclassAxles) {
		this.preclassAxles = preclassAxles;
	}

	public Integer getPostclassClass() {
		return postclassClass;
	}

	public void setPostclassClass(Integer postclassClass) {
		this.postclassClass = postclassClass;
	}

	public Integer getPostclassAxles() {
		return postclassAxles;
	}

	public void setPostclassAxles(Integer postclassAxles) {
		this.postclassAxles = postclassAxles;
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

	public Double getPostedFareAmount() {
		return postedFareAmount;
	}

	public void setPostedFareAmount(Double postedFareAmount) {
		this.postedFareAmount = postedFareAmount;
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

	public Double getExpectedRevenueAmount() {
		return expectedRevenueAmount;
	}

	public void setExpectedRevenueAmount(Double expectedRevenueAmount) {
		this.expectedRevenueAmount = expectedRevenueAmount;
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

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
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

	public String getLaneDeviceStatus() {
		return laneDeviceStatus;
	}

	public void setLaneDeviceStatus(String laneDeviceStatus) {
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

	public LocalDate getRevenueDate() {
		return revenueDate;
	}

	public void setRevenueDate(LocalDate revenueDate) {
		this.revenueDate = revenueDate;
	}

	public void setTxDate(LocalDate txDate) {
		this.txDate = txDate;
	}

	

	public void setUpdateTs(LocalDateTime updateTs) {
		this.updateTs = updateTs;
	}

	public void setEventTimestamp(LocalDateTime eventTimestamp) {
		this.eventTimestamp = eventTimestamp;
	}

	public LocalDate getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(LocalDate postedDate) {
		this.postedDate = postedDate;
	}

	public String getAtpFileId() {
		return atpFileId;
	}

	public void setAtpFileId(String atpFileId) {
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

	public Long getExternFileId() {
		return externFileId;
	}

	public void setExternFileId(Long externFileId) {
		this.externFileId = externFileId;
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

	public String getCscLookupKey() {
		return cscLookupKey;
	}

	public void setCscLookupKey(String cscLookupKey) {
		this.cscLookupKey = cscLookupKey;
	}

	public String getAetFlag() {
		return aetFlag;
	}

	public void setAetFlag(String aetFlag) {
		this.aetFlag = aetFlag;
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

	public Integer getFareTblId() {
		return fareTblId;
	}

	public void setFareTblId(Integer fareTblId) {
		this.fareTblId = fareTblId;
	}

	public Integer getDeviceClass() {
		return deviceClass;
	}

	public void setDeviceClass(Integer deviceClass) {
		this.deviceClass = deviceClass;
	}

	public Integer getTxStatus() {
		return txStatus;
	}

	public void setTxStatus(Integer txStatus) {
		this.txStatus = txStatus;
	}

	public LocalDate getTxDate() {
		return txDate;
	}

	public LocalDateTime getUpdateTs() {
		return updateTs;
	}

	public LocalDateTime getEventTimestamp() {
		return eventTimestamp;
	}

	public String getLprEnabled() {
		return lprEnabled;
	}

	public void setLprEnabled(String lprEnabled) {
		this.lprEnabled = lprEnabled;
	}

	public String getPostPaidFlag() {
		return postPaidFlag;
	}

	public void setPostPaidFlag(String postPaidFlag) {
		this.postPaidFlag = postPaidFlag;
	}
	
	public static void init(AccountTollPostDTO toll,AgencyTxPendingDto agencyTxPending)
	{
		try
		{
			
			toll.setLaneTxId(agencyTxPending.getLaneTxId());
			toll.setTxExternRefNo(agencyTxPending.getTxExternRefNo());
			toll.setTxSeqNumber(agencyTxPending.getTxSeqNumber());
			toll.setExternFileId(agencyTxPending.getExternFileId());
			toll.setPlazaAgencyId(agencyTxPending.getPlazaAgencyId());
			toll.setPlazaId(agencyTxPending.getPlazaId());
			toll.setLaneId(agencyTxPending.getLaneId());
			toll.setTxTimestamp(agencyTxPending.getTxTimestamp());
			toll.setTxModSeq(agencyTxPending.getTxModSeq());
			toll.setTxTypeInd(agencyTxPending.getTxType());
			toll.setTxSubtypeInd(agencyTxPending.getTxSubtype());
			toll.setTollSystemType(agencyTxPending.getTollSystemType());
			toll.setLaneMode(agencyTxPending.getLaneMode());
			toll.setLaneType(agencyTxPending.getLaneType());
			toll.setLaneState(agencyTxPending.getLaneState());
			toll.setLaneHealth(agencyTxPending.getLaneHealth());
			toll.setCollectorId(agencyTxPending.getCollectorId());
			toll.setTourSegmentId(agencyTxPending.getTourSegmentId());
			toll.setEntryDataSource(agencyTxPending.getEntryDataSource());
			toll.setEntryLaneId(agencyTxPending.getEntryLaneId());
			toll.setEntryPlazaId(agencyTxPending.getEntryPlazaId());
			toll.setEntryTimestamp(agencyTxPending.getEntryTimestamp());
			toll.setEntryTxSeqNumber(agencyTxPending.getEntryTxSeqNumber());
			toll.setEntryVehicleSpeed(agencyTxPending.getEntryVehicleSpeed());
			toll.setLaneTxStatus(agencyTxPending.getLaneTxStatus());
			toll.setLanetxType(agencyTxPending.getLanetxType());
			toll.setTollRevenueType(agencyTxPending.getTollRevenueType());
			toll.setActualClass(agencyTxPending.getActualClass());
			toll.setActualAxles(agencyTxPending.getActualAxles());
			toll.setActualExtraAxles(agencyTxPending.getActualExtraAxles());
			toll.setCollectorClass(agencyTxPending.getCollectorClass());
			toll.setCollectorAxles(agencyTxPending.getCollectorAxles());
			toll.setPreclassClass(agencyTxPending.getCollectorClass());
			toll.setPreclassAxles(agencyTxPending.getPreclassAxles());
			toll.setPostclassClass(agencyTxPending.getPostclassClass());
			toll.setPostclassAxles(agencyTxPending.getPostclassAxles());
			toll.setForwardAxles(agencyTxPending.getForwardAxles());
			toll.setReverseAxles(agencyTxPending.getReverseAxles());
			toll.setVideoFareAmount(agencyTxPending.getVideoFareAmount());
			toll.setEtcFareAmount(agencyTxPending.getEtcFareAmount());
			toll.setCashFareAmount(agencyTxPending.getCashFareAmount());
			toll.setPostedFareAmount(agencyTxPending.getPostedFareAmount());
			toll.setCollectedAmount(agencyTxPending.getCollectedAmount());
			toll.setUnrealizedAmount(agencyTxPending.getUnrealizedAmount());
			toll.setIsDiscountable(agencyTxPending.getIsDiscountable());
			toll.setIsMedianFare(agencyTxPending.getIsMedianFare());
			toll.setIsPeak(agencyTxPending.getIsPeak());
			toll.setPriceScheduleId(agencyTxPending.getPriceScheduleId());
			toll.setVehicleSpeed(agencyTxPending.getVehicleSpeed());
			toll.setReceiptIssued(agencyTxPending.getReceiptIssued());
			toll.setDeviceNo(agencyTxPending.getDeviceNo());
			toll.setAccountType(agencyTxPending.getAccountType());
			toll.setDeviceCodedClass(agencyTxPending.getDeviceCodedClass());
			toll.setDeviceAgencyClass(agencyTxPending.getDeviceAgencyClass());
			toll.setDeviceIagClass(agencyTxPending.getDeviceIagClass());
			toll.setDeviceAxles(agencyTxPending.getDeviceAxles());
			toll.setEtcAccountId(agencyTxPending.getEtcAccountId());
			toll.setAccountAgencyId(agencyTxPending.getAccountAgencyId());
			toll.setReadAviClass(agencyTxPending.getReadAviClass());
			toll.setReadAviAxles(agencyTxPending.getReadAviAxles());
			toll.setDeviceProgramStatus(agencyTxPending.getDeviceProgramStatus());
			toll.setBufferReadFlag(agencyTxPending.getBufferReadFlag());
			toll.setLaneDeviceStatus(agencyTxPending.getLaneDeviceStatus());
			toll.setPostDeviceStatus(agencyTxPending.getPostDeviceStatus());
			toll.setPreTxnBalance(agencyTxPending.getPreTxnBalance());
			toll.setPlanTypeId(agencyTxPending.getPlanTypeId());
			toll.setTxStatus(agencyTxPending.getTxStatus());
			toll.setSpeedViolFlag(agencyTxPending.getSpeedViolFlag());
			toll.setImageTaken(agencyTxPending.getImageTaken());
			toll.setPlateCountry(agencyTxPending.getPlateCountry());
			toll.setPlateState(agencyTxPending.getPlateState());
			toll.setPlateNumber(agencyTxPending.getPlateNumber());
			toll.setRevenueDate(agencyTxPending.getRevenueDate());
			toll.setPostedDate(agencyTxPending.getPostedDate());
			toll.setAtpFileId(agencyTxPending.getAtpFileId());
			toll.setIsReversed(agencyTxPending.getIsReversed());
			toll.setCorrReasonId(agencyTxPending.getCorrReasonId());
			toll.setReconDate(agencyTxPending.getReconDate());
			toll.setReconStatusInd(agencyTxPending.getReconStatusInd());
			toll.setReconSubCodeInd(agencyTxPending.getReconSubCodeInd());
			toll.setExternFileDate(agencyTxPending.getExternFileDate());
			toll.setMileage(agencyTxPending.getMileage());
			toll.setDeviceReadCount(agencyTxPending.getDeviceReadCount());
			toll.setDeviceWriteCount(agencyTxPending.getDeviceWriteCount());
			toll.setEntryDeviceReadCount(agencyTxPending.getEntryDeviceReadCount());
			toll.setDeviceWriteCount(agencyTxPending.getDeviceWriteCount());
			toll.setTxDate(agencyTxPending.getTxDate());
			toll.setCscLookupKey(agencyTxPending.getCscLookupKey());
			toll.setUpdateTs(agencyTxPending.getUpdateTs());
			toll.setExpectedRevenueAmount(agencyTxPending.getExpectedRevenueAmount());
			toll.setDuplSerialNum(agencyTxPending.getDuplSerialNum());
			/* toll.setTxStatus(agencyTxPending.getEtcTxStatus()); */
			//System.out.println("toll.getDuplSerialNum::"+toll.getDuplSerialNum());
	}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}